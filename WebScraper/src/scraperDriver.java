import Classes.simpleScraper;

public class scraperDriver {
	public static void main(String args[])
	{
		simpleScraper scrape = new simpleScraper();
		//Computer science
		simpleScraper.begin("https://catalog.unomaha.edu/undergraduate/college-information-science-technology/computer-science/#coursestextcontainer");
		//Math
		simpleScraper.begin("https://catalog.unomaha.edu/undergraduate/coursesaz/math/");
		//CIST
		simpleScraper.begin("https://catalog.unomaha.edu/undergraduate/coursesaz/cist/");
	}
}
