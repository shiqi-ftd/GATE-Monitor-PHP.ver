/*
 * This is the mac file updater for SPECT simulation.
 * The input format is: String params[], String pathtofile
 * The params inclueds: type,parm,value
 * The new macro conf file will be generated based on the input.
 * By Shiqi Zhong
 * Start: 7/13/2015
 * Last Update: 1/24/2016
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class set_param {
	String type;
	String parm;
	String value;
	String path;
	
	List<String> tags = new ArrayList<String>();
	
	public set_param(String params[], String pathtofile) {
		type = params[0];
		parm = params[1];
		value = params[2];
		path=pathtofile;
	}
	
	public void set_tags(set_param sp){
		if(sp.type.equals("SPECT") && sp.parm.equals("Collimator_type")){
			tags.add("# Collimator");
			tags.add("# Housing");
		}
		if(sp.type.equals("SPECT") && sp.parm.equals("Radius_of_rotation")){
			tags.add("# ROR");
		}
		if(sp.type.equals("SPECT") && sp.parm.equals("Isotope")){
			tags.add("# Isotope");
		}
	}
	
	public ArrayList<String> get_config(set_param sp){
		ArrayList<String> new_config = new ArrayList<String>();
		if(sp.type.equals("SPECT") && sp.parm.equals("Collimator_type")){
			switch (sp.value){
				case "1MGP10.mac":
					new_config.add("/control/execute 1MGP10_inveonhousing.mac");
					new_config.add("/control/execute ../mac/1MGP10.mac");
					break;
				case "1MHR05.mac":
					new_config.add("/control/execute 1MHR05_inveonHousing.mac");
					new_config.add("/control/execute ../mac/1MGP05.mac");
					break;
				case "1MME30.mac":
					new_config.add("/control/execute 1MME30_inveonHousing.mac");
					new_config.add("/control/execute ../mac/1MME30.mac");
					break;
				case "5MWB10.mac":
					new_config.add("/control/execute 5MWB10_inveonHousing.mac");
					new_config.add("/control/execute ../mac/5MWB10.mac");
					break;
	            default: new_config.add("Invalid Params for Collimator_type");
                break;
			}
		}		
		if(sp.type.equals("SPECT") && sp.parm.equals("Radius_of_rotation")){
			switch (Integer.parseInt(sp.value)){
			case 25:
				new_config.add("/gate/SPECThead/placement/setTranslation 123.5 0. 0. mm");
				break;
			case 30:
				new_config.add("/gate/SPECThead/placement/setTranslation  127.5 0. 0. mm");
				break;
			case 360:
				new_config.add("/gate/SPECThead/placement/setTranslation 457.5 0. 0. mm");
				break;
            default: new_config.add("Invalid Params for Radius_of_rotation");
            break;
		}
		}
		
		if(sp.type.equals("SPECT") && sp.parm.equals("Isotope")){
			switch (sp.value){
			case "COBALT57_digitizer":
				new_config.add("/control/execute ../mac/COBALT57_digitizer.mac");
				break;
			case "I123_digitizer_HI":
				new_config.add("/control/execute ../mac/I123_digitizer_HI.mac");
				break;
			case "I123_digitizer_LO":
				new_config.add("/control/execute ../mac/I123_digitizer_LO.mac");
				break;
			case "I125_digitizer":
				new_config.add("/control/execute ../mac/I125_digitizer.mac");
				break;
			case "T99M_digitizersp20":
				new_config.add("/control/execute ../mac/T99M_digitizersp20.mac");
				break;
            default: new_config.add("Invalid Params for Isotope");
            break;
		}
		}
		
		return new_config;
	}

	public void set_conf(set_param sp) throws IOException {
		
		 // On windows
//		 BufferedReader file = new BufferedReader(new FileReader(
//		 sp.path + '\\' + "WebContent\\conf" + '\\' +
//		 sp.type + '\\' + "configuration.mac"));
		 //On Linux
		String a =
				 sp.path + '/' + "conf" + '/' +
				 sp.type + '/' + "mac"+ '/' + sp.type + ".mac";
		System.out.println(a);
		 BufferedReader file = new BufferedReader(new FileReader(
		 sp.path + '/' + "conf" + '/' +
		 sp.type + '/' + "mac"+ '/' + sp.type + ".mac"));

/*		BufferedReader file = new BufferedReader(new FileReader("C:" + '\\'
				+ "Users" + '\\' + "szhong4" + '\\' + "Desktop" + '\\'
				+ "GATE-Interactive-Monitor" + '\\' + "WebContent\\conf" + '\\'
				+ sp.type + '\\' + "configuration.mac"));
*/		
		ArrayList<String> new_config = get_config(sp);
		set_tags(sp);
		
		String line = "";
		String output = "";	
		Boolean match = false;
		int counter = 0;
		System.out.println(new_config.get(0));

		while ((line = file.readLine()) != null) {
			if (match) {
				output += new_config.get(counter) + '\n';
				System.out.println(new_config.get(counter));
				counter++;
				match = false;
			}else{
			if (tags.contains(line)) {
				match = true;
				output += line + '\n';
			} else {
				output += line + '\n';
				match = false;
			}
			}
		}
//		System.out.println(counter);
		System.out.println(output);

		file.close();

		// overwrite the conf file.
		// On windows
//		 File cf = new File(sp.path + '\\' +
//		 "WebContent\\conf" + '\\' + sp.type + '\\' + "configuration.mac");
		//On Linux
		 File cf = new File(
				 sp.path + '/' + "conf" + '/' +
				 sp.type + '/' + "mac"+ '/' + sp.type + ".mac");
		
		/// set false to overwrite.
/*		File cf = new File("C:" + '\\' + "Users" + '\\' + "szhong4" + '\\'
				+ "Desktop" + '\\' + "GATE-Interactive-Monitor" + '\\'
				+ "WebContent\\conf" + '\\' + sp.type + '\\'
				+ "configuration.mac");
*/		FileWriter wf = new FileWriter(cf, false);
		wf.write(output);
		wf.close();
	}
	
	public static void main(String args[]) {
		try {
			// System.out.println(System.getProperty("user.dir"));
			String cmd;
			if (args.length > 1) {
				cmd = args[0].toString() + " " + args[1].toString();
			} else {
				cmd = args[0].toString();
			}
			
			System.out.println(cmd);

			String[] params = cmd.split(",");
			
			String path = "/Users/ShiqiZhong/Documents/GATE-Monitor/GATE-Interactive-Monitor/";
			
			set_param sp = new set_param(params, path);
			sp.set_conf(sp);
			/*
			 * System.out.print(System.getProperty("user.dir") + '\\' +
			 * "WebContent\\conf" + '\\' + sp.type + '\\' +
			 * "configuration.mac");
			 */
			
			// Test Arguments: 
			// SPECT,Radius_of_rotation,360
			// SPECT,Collimator_type,1MGP10.mac
			// SPECT,Radius_of_rotation,360
		} catch (Exception e) {
		}

	}
}