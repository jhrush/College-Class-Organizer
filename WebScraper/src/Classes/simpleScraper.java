package Classes;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.dom.NodeList;

import Classes.Course;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 
 * @author jake
 *
 */
public class simpleScraper {
	
	private static ArrayList<Course> classes = new ArrayList<Course>();
	//private static ArrayList<String> coreReqs = new ArrayList<String>();
	
	public simpleScraper() //static void main(String args[])
	{
		
	}
	
	/**
	public static void getCoreReqs(String urlToScrape, String fileName)
	{
		//coreReqs = 
		coreScrape(urlToScrape, fileName);
		for (int i = 0; i < coreReqs.size(); i++)
		{
			System.out.println(coreReqs.get(i));
		}
		
		try 
		{
			FileWriter fileWriter = new FileWriter(fileName);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			
			for (int i = 0; i < coreReqs.size(); i++)
			{
				printWriter.println(coreReqs.get(i));
			}
			
			printWriter.close();
		}
		catch(IOException e) 
		{
			e.printStackTrace();
			return;
		}
	}
	**/
	
	/**
	 * Scraping for core requirements.
	 * @param url - the url that the core requirements are at.
	 * @param fileName - the file name that the course list will be printed to.
	 */
	public static void getCoreReqs(String url)
	{
		ArrayList<String> courses = new ArrayList<String>();
		
		 try (final WebClient webClient = new WebClient()) {
		        try{
		        	
		        	final HtmlPage page = webClient.getPage(url);
		        	
		        	//System.out.println(page.asXml());
		        	
		            //final Iterator<Object> nodesIterator = page.getByXPath("//td[@class='codecol']").iterator();
		        	
		        	DomNodeList<DomElement> x = page.getElementsByTagName("table");
		        	var nTables = x.getLength() - 1;
		        	int tableNum = 1;
		        	
		        	for(int j = 0; j < nTables ; j++)
		        	{
			        	final HtmlTable table = (HtmlTable) page.getByXPath("//table[@class='sc_courselist']").get(j);
			        	
			        	for (final HtmlTableRow row : table.getRows()) {
			        	    //System.out.println("Found row");
			        		
			        		
			        	    int cellCount = 0;
			        	    ArrayList<String> cells = new ArrayList<String>();
			        	    
			        	    for (final HtmlTableCell cell : row.getCells()) {
			        	    	cellCount++;
			        	        //System.out.println("   Found cell: " + cell.asText());
			        	        cells.add(cell.asText());
			        	        
			        	        String cname = cells.get(0);
			        	        
			        	        //checks if there is 3 cells in a row and makes sure that the first row is equal to 2.
			        	        if (cellCount == 3 && cname.split("\\s+").length == 2)
			        	        {
			        	        	courses.add(cname);
			        	        	//System.out.println(cname);
			        	        }
			        	    }
			        	}
			        	if (!courses.isEmpty())
			        	{
			        		System.out.println("Table " + tableNum + " below!");
			        		printReqToFile(courses, "REQS_" + tableNum + ".txt");
			        	
			        	
				        	tableNum++;
				        	courses.clear();
			        	}
		        	}
		        }
		        
		        catch(IOException e) 
		        {
		        	e.printStackTrace();
		        }
		 }
	}
	
	private static void printReqToFile(ArrayList<String> coreReqs, String fileName)
	{
		for (int i = 0; i < coreReqs.size(); i++)
		{
			System.out.println(coreReqs.get(i));
		}
		
		try 
		{
			FileWriter fileWriter = new FileWriter(fileName);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			
			for (int i = 0; i < coreReqs.size(); i++)
			{
				printWriter.println(coreReqs.get(i));
			}
			
			printWriter.close();
		}
		catch(IOException e) 
		{
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * Scrapes course pages for classes and their prerequistes.
	 * 
	 * Example of courses webpage:
	 * https://catalog.unomaha.edu/undergraduate/college-information-science-technology/computer-science/#coursestextcontainer
	 * 
	 * The information gathered will then be exported as a CSV file.
	 * 
	 * @param urlToScrape - the URL that will be scraped.
	 * @param fileName - The name of the file the info that is scraped will be exported to.
	 */
	public static void getCourseLists(String urlToScrape, String fileName)
	{
		 System.out.println("Scraping classes....");
		 
		 classes = (scrape(urlToScrape));
		 
		 /**
		 String College = classes.get(0).College;
		 
		 System.out.println("Determining prereqs....");
		 
		 for(Course item: classes)
		 {
			 for(Course preReq: item.Prereq)
			 {
				 if(!preReq.College.equals(College) && classes.indexOf(preReq) != -1)
				 {
					 classes.add(preReqFinder(preReq));
				 }
			 }
		 }
		 **/
		 
		 System.out.println("Done!!");
		 
		 for(Course item: classes)
		 {
			 System.out.print(item.toString(true));
		 }
		 
		 printToCSV(classes, fileName);
	}
	
	/**
	 * Prints to CSV file with each class structured as 
	 * "CSCI 4560,MATH 2230,* ,MATH 2030,* ,CSCI 2030," 
	 * where "* " dictates or
	 * 
	 * @param classes - the list of classes.
	 * @param fileName - the filename the classes are going to outputed to.
	 */
	private static void printToCSV(ArrayList<Course> classes, String fileName)
	{
		try 
		{
			FileWriter fileWriter = new FileWriter(fileName);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			
			for(Course item: classes)
			{
				printWriter.print(item.toString(true));
			}
			
			printWriter.close();
		}
		catch(IOException e) 
		{
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * Scrapes a specified url for a tagged classes and their subsequent prereqs.
	 * 
	 * Note: not all prereqs are "a" tagged.
	 * 
	 * @param url The url of the page to scrape.
	 * @return an array list of courses.
	 */
	private static ArrayList<Course> scrape(String url)
	{
		ArrayList<Course> courses = new ArrayList<Course>();
		
		 try (final WebClient webClient = new WebClient()) {
		        try{
		        	
		        	final HtmlPage page = webClient.getPage(url);
		        	
		            final Iterator<Object> nodesIterator = page.getByXPath("//div[@class='courseblock']").iterator();
		            
		            while(nodesIterator.hasNext()) {
		            	DomElement curClass = (DomElement) nodesIterator.next();
		            	
		            	Iterator<HtmlElement> Elements = curClass.getElementsByTagName("p").iterator();
		            	
		            	String[] title = new String[2];
		            	
		            	ArrayList<Course> prereqs = new ArrayList<Course>();
		            	
		            	String discipline = "";
		            	String courseNum = "";
		            	String creditNum = "";
		            	
		            	for(int i = 0; i < 2; i++)
		            	{
		            		//Gets main course name.
		            		if(i == 0)
		            		{
		            			HtmlElement curElement = (HtmlElement) Elements.next();
		            			title = curElement.getFirstElementChild().asText().split("\\h+");
		            			discipline = title[0];
		            			courseNum = title[1];
		            			creditNum = title[title.length - 2].replaceAll("[()]", "");
		            			//System.out.printf("Title: %s\n", creditNum);
		            		}
		            		else if(i == 1 && Elements.hasNext())
		            		{
		            			Elements.next();
		            			
		            			if (Elements.hasNext())
		            			{
			            			HtmlElement curElement = (HtmlElement) Elements.next();
			            			
			            			Iterator<HtmlElement> aTags = curElement.getElementsByTagName("a").iterator();
			            			
			            			//CurElement hold preq info at this point
			            			String wholePreReq = curElement.asText();
			            			//System.out.println(wholePreReq);
			            			
			            			while(aTags.hasNext())
			            			{			            				
			            				String[] req = aTags.next().asText().split("\\h+");
			            				
			            				if (wholePreReq.contains( "or " + req[0] + " " + Integer.valueOf(req[1]).intValue()))
			            				{
			            					prereqs.add(new Course("*", "", null, true));
			            				}
			            				
			            				Course prereq = new Course(req[0], req[1], null, true, creditNum);
			            				
			            				//Prereqs can't be equal to course it attached to.
			            				if(!prereq.Number.equals(courseNum))
			            				{
			            					prereqs.add(prereq);
			            				}
			            			}
		            			}
		            		}
		            		

		            	}
		            	
		            	removeOrAtEnd(prereqs);
		            	courses.add(new Course(discipline, courseNum, prereqs.toArray(new Course[prereqs.size()]), true, creditNum));

		            }
		            
		            return courses;
		            
		        }
		        
		        catch(IOException e) 
		        {
		        	e.printStackTrace();
		        	return null;
		        }
		 }
	}
	
	private static void removeOrAtEnd(ArrayList<Course> prereqs)
	{
		while(prereqs.size() != 0 && prereqs.get(prereqs.size() - 1).College.equals("*"))
		{
			prereqs.remove(prereqs.size() - 1);
		}
	}
	
		
	/**
	 * Returns true if the string contains a number.
	 * Referenced from here https://www.moreofless.co.uk/check-string-contains-number-using-java/
	 * @param s - the string to check
	 * @return - true or false
	 */
	/**
	private static boolean stringContainsNumber( String s )
	{
	    return Pattern.compile( "[0-9]" ).matcher( s ).find();
	}
	**/
	
	/**
	private static Course preReqFinder(Course preReq)
	{
		
		try (final WebClient webClient = new WebClient()) {
			
			try 
			{
				final HtmlPage page = webClient.getPage("https://www.unomaha.edu/registrar/students/before-you-enroll/class-search/index.php");
				List<HtmlForm> forms = page.getForms();
				HtmlForm form = forms.get(0);
				
				HtmlSelect subject = form.getSelectByName("subject");
				subject.setSelectedAttribute(subject.getOptionByValue(preReq.College), true);
			
				HtmlSelect catalogNbr = form.getSelectByName("catalog_nbr");
				**/
	
				//catalogNbr.setSelectedAttribute(subject.getOptionByValue(/**Integer.toString**/(preReq.Number)), true);
				/**
				HtmlInput submit = form.getInputByValue("Submit");
				final HtmlPage PreReqPage = (HtmlPage) submit.setChecked(true);
				
				final Iterator<Object> nodesIterator = PreReqPage.getByXPath("//div[@class='dotted-bottom']").iterator();
				while(nodesIterator.hasNext())
				{
					DomElement curDiv = (DomElement) nodesIterator.next();
					
					DomNodeList<HtmlElement> Elements = curDiv.getElementsByTagName("p");
					
					if (!Elements.isEmpty())
					{

						for(HtmlElement element: Elements)
						{
							if(element.asText().contains("Prereq:"))
							{
								String[] wordList = element.asText().split("\\s+");
								
								boolean nextRequired = false;
								
								for(int i = 0; i < wordList.length; i++)
								{
									String name = "";
									String number = "";
									boolean required = true;
									
									if(wordList[i].equals(wordList[i].toUpperCase()) && stringContainsNumber(wordList[i + 1]))
									{
										name = wordList[i];
										
										if(wordList[i - 1].equals("or") && !nextRequired)
										{
											required = false;
										}
										nextRequired = false;
										
										if(wordList[i + 1].charAt(wordList[i + 1].length()) == ',')
										{
											number = wordList[i + 1].substring(0, wordList[i + 1].length() - 1); //Integer.parseInt(wordList[i + 1].substring(0, wordList[i + 1].length() - 1));
											nextRequired = true;
										}
										else
										{
											number = wordList[i + 1]; //Integer.parseInt(wordList[i + 1]);
										}
										

									}
									
									preReq.addPreReq(new Course(name, number, null, required));
									
									
								}
							}
						}
					}
				}
				
				
				return preReq;
			}
			finally
			{
				
			}
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
		
		
	}
	**/

}
