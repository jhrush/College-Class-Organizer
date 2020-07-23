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
		
		simpleScraper.begin("https://catalog.unomaha.edu/undergraduate/college-information-science-technology/computer-science/#coursestextcontainer");
		simpleScraper.begin("https://catalog.unomaha.edu/undergraduate/coursesaz/math/");
		simpleScraper.begin("https://catalog.unomaha.edu/undergraduate/coursesaz/cist/");
		
		File fileExpected = new File("csvFileExpected.txt");		
		File fileActual = new File("csvFile.txt");
		
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
