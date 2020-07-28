package Tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;



import Classes.simpleScraper;

public class simpleScraperTest {

	/**
	 * Test for the simpleScraper class object to make sure its generated the expected csv file.
	 */
	@Test
	public void csvChecktest() {
		simpleScraper scrape = new simpleScraper();
		
		simpleScraper.getCourseLists("https://catalog.unomaha.edu/undergraduate/college-information-science-technology/computer-science/#coursestextcontainer", "CSCI.txt");
		simpleScraper.getCourseLists("https://catalog.unomaha.edu/undergraduate/coursesaz/math/", "MATH.txt");
		simpleScraper.getCourseLists("https://catalog.unomaha.edu/undergraduate/coursesaz/cist/", "CIST.txt");
		
		File fileExpected = new File("CIST.txt");		
		File fileActual = new File("CISTExpected.txt");
		
		try 
		{
			assertTrue("Difference detected!", FileUtils.contentEquals(fileExpected, fileActual));
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
