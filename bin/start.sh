cd `dirname $0`
cd ..
mvn clean install
nohup java -jar target/yasso.jar -c yasso-conf.yml > yasso.log &
echo Yasso Bootup Successful !!
