package Classes;

public class Course {
	
	public String College;
	public int Number;
	public Course[] Prereq;
	
	public Course(String name, int number, Course[] prereq) {
		College = name;
		Number = number;
		Prereq = prereq;
	}
	
	public String toString(boolean Outside)
	{
		
		String output;
		
		if(Outside)
		{
			output = String.format("%s %d \n", College, Number);
			for(Course course: Prereq)
			{
				output += String.format("%s,", course.toString(false));
			}
		}
		else
		{
			output = String.format("%s %d", College, Number);
		}
		
		return output;
		
	}
	
}
