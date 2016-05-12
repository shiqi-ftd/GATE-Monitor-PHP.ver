#!/bin/bash
max=15
count=0
echo $count
for i in $(seq -f "%03g" 0 29)
do
 taskset -c $count ./script_$i.sh 42800
 sleep 10s
 let count=1+count
 echo $count
 if [ $count+1 == $max ];
then
 break
 fi
done