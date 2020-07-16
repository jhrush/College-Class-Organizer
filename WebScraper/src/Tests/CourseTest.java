package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Classes.Course;

public class CourseTest {

	@Test
	public void addPreReqTest() {
		Course[] tmp = new Course[0];
		Course[] arr = new Course[4];
		
		Course newCrs = new Course("hey", "1699", tmp, true);
		arr[0] = newCrs;
		
		newCrs = new Course("hey", "1699", tmp, true);
		arr[1] = newCrs;
		
		newCrs = new Course("hello", "1000", tmp, true);
		arr[2] = newCrs;
		
		newCrs = new Course("hi", "69", tmp, true);
		arr[3] = newCrs;
		
		Course crs = new Course("goodbye", "6969", arr , true);
		
		Course equivCrs = new Course ("goodbye", "6969", arr , true);
		equivCrs.addPreReq(arr[0]);
		equivCrs.addPreReq(arr[1]);
		equivCrs.addPreReq(arr[2]);
		equivCrs.addPreReq(arr[3]);
		assertEquals(crs.toString(false), equivCrs.toString(false));
		
		newCrs = new Course("fun", "101", tmp, true);
		assertEquals(newCrs.toString(false), "fun 101");
		
		newCrs = new Course("*", "101", tmp, true);
		assertEquals(newCrs.toString(false), "*");
		
		newCrs = new Course("fun", "101", arr, true);
		assertEquals(newCrs.toString(true), "fun 101,hey 1699,hey 1699,hello 1000,hi 69,\n");
		
		newCrs = new Course("*", "101", tmp, true);
		assertEquals(newCrs.toString(true), "*,\n");
		
		
	}

}
