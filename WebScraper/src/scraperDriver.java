import Classes.simpleScraper;

public class scraperDriver {
	public static void main(String args[])
	{
		simpleScraper scrape = new simpleScraper();
		//Computer science
		simpleScraper.courseScrape("https://catalog.unomaha.edu/undergraduate/college-information-science-technology/computer-science/#coursestextcontainer", "CSCI.txt");
		//Math
		simpleScraper.courseScrape("https://catalog.unomaha.edu/undergraduate/coursesaz/math/", "MATH.txt");
		//CIST
		simpleScraper.courseScrape("https://catalog.unomaha.edu/undergraduate/coursesaz/cist/", "CIST.txt");
	}
}
