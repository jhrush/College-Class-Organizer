import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.javascript.host.dom.NodeList;

import Classes.Course;
import java.util.Iterator;
import java.util.ArrayList;

public class simpleScraper {
	
	public static void main(String args[])
	{
		
		 ArrayList<Course> courses = new ArrayList<Course>();
		 
		 int counter = 0;
		
		 try (final WebClient webClient = new WebClient()) {
		        try{
		        	
		        	final HtmlPage page = webClient.getPage("https://catalog.unomaha.edu/undergraduate/college-information-science-technology/computer-science/#coursestextcontainer");
		        	
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
		            			
		            			
		            			while(aTags.hasNext())
		            			{
		            				String[] req = aTags.next().asText().split("\\h+");
		            				prereqs.add(new Course(req[0], Integer.valueOf(req[1]).intValue(), null));
		            			}
		            		}
		            		

		            	}
		            	
		            	courses.add(new Course(title[0], Integer.valueOf(title[1]).intValue(), prereqs.toArray(new Course[prereqs.size()])));
		            	
		            	System.out.println("added Course!");
		            	System.out.println(courses.get(counter).toString(true));
		            	
		            	counter += 1;
		            }
		        	
		            System.out.println("Done!");
		            
		        }catch(IOException e) {
		        	e.printStackTrace();
		        }finally {
		        	
		        }

		        
		    }
		
	}

}
