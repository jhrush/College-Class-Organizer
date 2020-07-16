import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.javascript.host.dom.NodeList;

import Classes.Course;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class simpleScraper {
	
	public static void main(String args[])
	{
		 ArrayList<Course> classes;
		
		 System.out.println("Scraping classes....");
		 
		 classes = scrape("https://catalog.unomaha.edu/undergraduate/college-information-science-technology/computer-science/#coursestextcontainer");
		 
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
		 
		 System.out.println("Done!!");
		 
		 for(Course item: classes)
		 {
			 System.out.print(item.toString(true));
		 }
		 
		 printToCSV(classes, "csvFile.txt");
		 		 
	}
	
	/**
	 * Prints to csv file with each class structured as 
	 * "CSCI 4560,MATH 2230,* ,MATH 2030,* ,CSCI 2030," 
	 * where "* " dictates or
	 * 
	 * @param classes - the list of classes.
	 * @param fileName - the filename the classes are going to outputed to.
	 */
	public static void printToCSV(ArrayList<Course> classes, String fileName)
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
	public static ArrayList<Course> scrape(String url)
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
		            	
		            	for(int i = 0; i < 2; i++)
		            	{
		            		
		            		if(i == 0)
		            		{
		            			HtmlElement curElement = (HtmlElement) Elements.next();
		            			title = curElement.getFirstElementChild().asText().split("\\h+");
		            		}
		            		else if(i == 1)
		            		{
		            			Elements.next();
		            			HtmlElement curElement = (HtmlElement) Elements.next();
		            			
		            			Iterator<HtmlElement> aTags = curElement.getElementsByTagName("a").iterator();
		            			
		            			//CurElement hold preq info at this point
		            			String wholePreReq = curElement.asText();
		            			//System.out.println(wholePreReq);
		            			
		            			while(aTags.hasNext())
		            			{
		            				String[] req = aTags.next().asText().split("\\h+");
		            				
		            				if (wholePreReq.contains( "or " + req[0] + " " + Integer.valueOf(req[1]).intValue() ))
		            				{
		            					prereqs.add(new Course("*", "", null, true));
		            				}
		            				
		            				prereqs.add(new Course(req[0], /**Integer.valueOf(req[1]).intValue()**/ req[1], null, true));
		            			}
		            		}
		            		

		            	}
		            	
		            	courses.add(new Course(title[0], /**Integer.valueOf(title[1]).intValue()**/ title[1], prereqs.toArray(new Course[prereqs.size()]), true));

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
		
	public static Course preReqFinder(Course preReq)
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
				catalogNbr.setSelectedAttribute(subject.getOptionByValue(/**Integer.toString**/(preReq.Number)), true);
				
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
	
	/**
	 * Returns true if the string contains a number.
	 * Referenced from here https://www.moreofless.co.uk/check-string-contains-number-using-java/
	 * @param s - the string to check
	 * @return - true or false
	 */
	public static boolean stringContainsNumber( String s )
	{
	    return Pattern.compile( "[0-9]" ).matcher( s ).find();
	}

}
