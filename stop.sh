pids=$(ps -ef |grep 'java -jar yasso.jar'| awk '{print $2}') 
for pid in $pids; do
    kill -9 $pid
    echo $pid killed
done
echo Stop Successful !!