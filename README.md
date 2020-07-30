# College Class Organizer
<h2>
Overview
</h2>
<p>
I wish there were an app to take as input a college program (i.e. Computer Science) and as output would show a top-down structure similar to a tree of the required courses. Each parent branch(es) of the tree would be the prerequisite course(s) for the following courses. Then when a student logs in to their account they would pull up the app, and it would show indicators on each of the courses of what classes they have already taken (for example; if they had taken CSCI 700 it would be striked through, or highlighted in green, while something like CSCI 1980 would be highlighted in red because they had yet to take CSCI 1000 which is a prerequisite, but if they had taken both CSCI 700, and CSCI 1000 it would be highlighted in yellow because it is available). 
</p>

<p>
I'm interested in this, because when I plan on what courses I'd like to take for the following semesters, I find it easier to know which classes I can/can't take yet, and it's easier for me to understand this visually then bouncing back and forth between checking each individual course's information about prerequisites.
</p>

<p>
I’ve mostly had recent experience in Entity Framework Core, and Blazor, coupled with SQL Server. Blazor is somewhat new, but it allows C# code to be implemented inside a web page. I have taken several courses over a wide array of programming languages over the years so I’m a little rusty on some languages compared to others.
</p>

<p>
I propose that the software be developed with changing degree requirements and paths in mind. Consequently, the software should employ a web-scrapper that utilizes the known templates provided by the UNO degree webpage to find and download necessary degree information. Further, the information should be processed into discrete “class” objects, that contains the class name, and its necessary prereg list. This information may then be stored in a sorted database, and upon query, display in a unidirectional graph format. 
</p>

<p>
One challenge is determining how we will scrape web pages. Web pages concerning degree information won’t remain static forever and are subject to change; additionally, dependent webpages may simply cease to exist. The planned application will most likely work during this semester. Although, it’s worth noting that functionality might eventually break or become deprecated. Finding a source of degree info that won’t radically change this semester is key to our project's success. Making our project somewhat tolerant of our source changing should be a goal.
</p>

<h2>
Code Milestone #1 Release Notes
</h2>
<p>
Note: Before running the webscraper make sure to add htmlunit to the build path!
</p>
<p>
Additionally, if you are on a unix system you can execute the build.sh and exec.sh to build and run the scraper.
</p>
<p>
We implemented a basic web scraping java program via gargoyle software's HtmlUnit.
</p>
<p>
Additionally, the information will be exported as a csv file where "or" and "OR" are marked as "*".
</p>
<p>
For example:
</p>
<p>
CSCI 4560  NUMBER THEORY & CRYPTOGRAPHY (3 credits)

An overview of one of the many beautiful areas of mathematics and its modern application to secure communication. The course is ideal for any student who wants a taste of mathematics outside of, or in addition to, the calculus sequence. Topics to be covered include: prime numbers, congruences, perfect numbers, primitive roots, quadratic reciprocity, sums of squares, and Diophantine equations. Applications include error-correcting codes, symmetric and public key cryptography, secret sharing, and zero knowledge proofs. (Cross-listed with CSCI 8566, MATH 4560, MATH 8566).

Prerequisite(s)/Corequisite(s): MATH 2230 with a C- or better or MATH 2030 with a C- or better or CSCI 2030 with a C- or better or permission of instructor
</p>

<p>
Desired output:
</p>

<pre>
CSCI 4560,MATH 2230,*,MATH 2030,*,CSCI 2030,
</pre>


<h2>
Code Milestone #2 Release Notes
</h2>
<p>
Refactored and added JUnit testing for the simpleScraper and the Course classes. Changed the output within the CSV file and began extending functionality of the scraper itself.
</p>
<p>
The tests themselves are quite simple. They simply verify that that functions work as previously specified. The test for the simpleScraper is simply a system test of sorts that makes sure that the CSV file matches what has previously been expected.
</p>
<p>
We began development of the blazor app frontend which within a version controlled repository <a href="https://github.com/TravisWisecup/Course_Display">on github here.</a>
</p>
<p>
It is being hosted via AWS Elastic Beanstalk. The URL to view the frontend is <a href="http://coursedisplay-dev.us-west-2.elasticbeanstalk.com/">here at this link.</a>
</p>
<p>
Development for our database has begun and it is being hosted via AWS. Although, we need to work on its web integration with our Java webscraper.
</p>

<p>
Course conversion into a CSV line now appears as such:
</p>
<p>
CSCI 4560  NUMBER THEORY & CRYPTOGRAPHY (3 credits)

An overview of one of the many beautiful areas of mathematics and its modern application to secure communication. The course is ideal for any student who wants a taste of mathematics outside of, or in addition to, the calculus sequence. Topics to be covered include: prime numbers, congruences, perfect numbers, primitive roots, quadratic reciprocity, sums of squares, and Diophantine equations. Applications include error-correcting codes, symmetric and public key cryptography, secret sharing, and zero knowledge proofs. (Cross-listed with CSCI 8566, MATH 4560, MATH 8566).

Prerequisite(s)/Corequisite(s): MATH 2230 with a C- or better or MATH 2030 with a C- or better or CSCI 2030 with a C- or better or permission of instructor
</p>

<p>
Desired output for CVS line:
</p>

<pre>
CSCI 4560,3,"MATH 2230,*,MATH 2030,*,CSCI 2030,"
</pre>


<h2>
Code Milestone #3 Release Notes
</h2>


