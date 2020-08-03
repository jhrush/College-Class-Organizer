import Classes.simpleScraper;

public class scraperDriver {
	public static void main(String args[])
	{
		//Computer science
		simpleScraper.getCourseLists("https://catalog.unomaha.edu/undergraduate/college-information-science-technology/computer-science/#coursestextcontainer", 
				"CSCI.txt");
		//Math
		simpleScraper.getCourseLists("https://catalog.unomaha.edu/undergraduate/coursesaz/math/", "MATH.txt");
		//CIST
		simpleScraper.getCourseLists("https://catalog.unomaha.edu/undergraduate/coursesaz/cist/", "CIST.txt");
		//CYBER
		simpleScraper.getCourseLists("https://catalog.unomaha.edu/undergraduate/coursesaz/cybr/", "CYBER.txt");
		//Core requirements
		simpleScraper.getCoreReqs("https://catalog.unomaha.edu/undergraduate/college-information-science-technology/computer-science/computer-science-bs/#requirementstext");
	}
}
