/*
 * This is the mac file spliter for SPECT simulation.
 * The new macro conf files will be generated based on the number of projections.
 * By Default, split the simulation to 64 cores.
 * Usage: java -jar split.jar path_to_GATE_project number_of_cores(optional)
 * By Shiqi Zhong
 * Start: 2/16/2016
 * Last Update: 3/10/2016
 */

package split;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class split {
	String path = "";
	String pathW = "";
	int cores = 64;
	
	// Value 30, 45, 60,  
	int projections;
	int slice;
	int stop;
	
	String part0 = "";
	String part1 = "";
	String part2 = "";
	
	public split(String path, int cores) throws IOException{
		this.path = path + "/conf/SPECT/mac/SPECT.mac";
		this.pathW = path;
		this.cores = cores;
		readin_macro();
		projections = stop/slice;
//		System.out.println(this.start);
//		System.out.println(this.stop);
//		System.out.println(this.projections);
//		System.out.println(part0);
//		System.out.println(part1);
//		System.out.println(part2);
		generate_macro();
	}
	
	private void readin_macro() throws IOException{
		BufferedReader file = new BufferedReader(new FileReader(path));
		String line = "";
		int counter = 0;
		while ((line = file.readLine()) != null) {
			String[] m= line.split(" ");
			if(m[0].equals("/gate/output/root/setFileName")){
				counter++;
				continue;
			}
			if(m[0].equals("/gate/application/setTimeSlice")){
				this.slice = Integer.parseInt(m[1]);
				counter++;				
				continue;
			}
			if(m[0].equals("/gate/application/setTimeStop")){
				this.stop = Integer.parseInt(m[1]);
				counter++;				
				continue;
			}
			if(m[0].equals("/gate/application/setTimeStart")){
				continue;
			}
			if(counter == 0)
				part0 += line + '\n';
			if(counter == 1)
				part1 += line + '\n';
			if(counter > 2)
				part2 += line + '\n';
			}
		file.close();
	}
	private void generate_macro() throws IOException{
		int num = Math.min(this.cores, this.projections);
		for(int i = 0; i < num; i++){
			String fileindex = String.format("%03d", i);
			File cf = new File(pathW + "/conf/SPECT/mac/SPECT" + fileindex + ".mac");
//			System.out.println(pathW + "/conf/SPECT/mac/SPECT" + fileindex + ".mac");
			String output = part0;
			output = output + "/gate/output/root/setFileName " + "Simu" + fileindex + '\n';
			output = output + part1;
			output = output + "/gate/application/setTimeSlice " + slice + " s" + '\n';
			output = output + "/gate/application/setTimeStart " + slice*i + " s" + '\n';
			output = output + "/gate/application/setTimeStop " + slice*(i+1) + " s" + '\n';
			output = output + part2;
			FileWriter wf = new FileWriter(cf, false);			
			wf.write(output);
			wf.close();
			
			// Generate the related scripts
			File sf = new File(pathW + "/conf/SPECT/mac/script_" + fileindex + ".sh");
			String shell = "cd " + pathW + "/conf/SPECT/mac/";
			shell = shell + '\n' + "nice -n 1 Gate " + pathW + "/conf/SPECT/mac/SPECT" + 
			fileindex + ".mac &" + '\n' + "disown -h";
			FileWriter wsf = new FileWriter(sf, false);			
			wsf.write(shell);
			wsf.close();
		}
		// Generate the execute sh
		File ex = new File(pathW + "/conf/SPECT/mac/execute.sh");
		String exShell = "#!/bin/bash" + '\n';
		exShell = exShell + "let count = " + num + '\n';
		exShell = exShell + "echo $count" + '\n' + "for i in {000..029}" + '\n'
				+ "do" + '\n' + " taskset -c $count ./script_$i.sh 42800" + '\n'
				+ " sleep 10s" + '\n' + "let count=1+count" + '\n' + "echo $count"
				+ '\n' + '\n' + "done";
		FileWriter exwf = new FileWriter(ex, false);			
		exwf.write(exShell);
		exwf.close();
	}
	
	public static void main(String args[]) throws IOException {
		String path = "";
		// By defalut, 64 cores.
		int cores = 64;
		
		if(args.length == 1){
		// Path to GATE project
		path = args[0];
		}
		if(args.length == 2){
		// Path to GATE project
		path = args[0];
		cores = Integer.parseInt(args[1]);
		}		
		//For Testing
//		String pathtofile = "/Users/ShiqiZhong/Documents/GATE-Monitor/GATE-Interactive-Monitor";
//		split s = new split(pathtofile, cores);
		split s = new split(path, cores);
	}

}
