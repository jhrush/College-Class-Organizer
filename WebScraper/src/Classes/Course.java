package Classes;

public class Course {
	public String College;
	public String Number;
	public Course[] Prereq;
	public boolean Required;
	
	public Course(String name, String number, Course[] prereq, boolean required) {
		College = name;
		Number = number;
		Prereq = prereq;
		Required = required;
	}
	
	/**
	 * Add a prerequisite to a course.
	 * 
	 * @param req - the prerequisite to add to the course array.
	 */
	public void addPreReq(Course req)
	{
		Course[] copy = Prereq.clone();
		Prereq = new Course[Prereq.length + 1];
		
		for (int i = 0; i < copy.length; i++)
		{
			Prereq[i] = copy[i];
		}
	}
	
	/**
	 * Prints out a class along with its prerequisites.
	 * 
	 * @param Outside dictates whether or not a course is outside.
	 * @return 
	 */
	public String toString(boolean Outside)
	{
		String output = "";
		
		if(Outside)
		{	
			if (College.equals("*"))
			{
				output = College + ",";
			}
			else
			{
				output = String.format("%s %s,", College, Number);
			}
			
			for(Course course: Prereq)
			{
				output += String.format("%s,", course.toString(false));
			}
			output += "\n";
		}
		else
		{
			if (College.equals("*"))
			{
				output = College;
			}
			else
			{
				output = String.format("%s %s", College, Number);
			}
		}
		
		return output;
		
	}
	
}
