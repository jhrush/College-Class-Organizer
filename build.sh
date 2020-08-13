javac -classpath WebScraper/src:WebScraper/lib/htmlunit-2.41.0-OSGi.jar "WebScraper/src/scraperDriver.java"
FILE=CIST.txt
if test -f "$FILE"; then
rm CIST.txt
rm CSCI.txt
rm CYBR.txt
rm MATH.txt
rm REQS_1.txt
rm REQS_2.txt
rm REQS_3.txt
fi
