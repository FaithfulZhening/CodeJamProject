package systemModel;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import javax.xml.stream.events.StartDocument;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;


public class mainClass {
	@SuppressWarnings("unchecked")
	static public ArrayList<Student> students = new ArrayList<Student>();
	static public ArrayList<Course> courses = new ArrayList<Course>();
	static public ArrayList<Section> sections = new ArrayList<Section>();
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
		/*
		 * put all data read from classes.json into courses Arraylist
		 */
	    //readClassJSON("classes.json");
	    /*
		 * put all data read from studentsByAvailability.json into courses Arraylist
		 */  					
		//readStudentJSON("studentsByAvailability.json");
		
		
		double maxEnergy = 0;
		
		
		Random rand = new Random();
		
		ArrayList<Section> finalSection = new ArrayList<Section>();
		ArrayList<Student> finalStudents = new ArrayList<Student>(); 
		ArrayList<Course>  finalCourses = new ArrayList<Course>();
		//change the order of students and sections

		
		//do this sa multiple times to improve performance...
		for (int i=0; i< 10000; i++){
			double oldEnergy = 0;
			double newEnergy;
			double temp = 1000;   			//this is the start temp
			double miniTemp = 1;			//this is the start temp
			double alpha = 0.8;
			
			int startPosition = 0;
			readClassJSON("classes.json");
			Collections.shuffle(students);
			readStudentJSON("studentsByAvailability.json");
			Collections.shuffle(sections);
			
			/*
			 * these are the temp arrayList for section,student and courses
			 * the order decides the result of courseRegister
			 * */
			ArrayList<Section> tempSection;
			ArrayList<Student> tempStudent;
			ArrayList<Course>  tempCourse;
			
			while(temp > miniTemp){
				tempStudent = new ArrayList<Student>(students); 
				//find one random neighbor
				startPosition = rand.nextInt(tempStudent.size()-startPosition) + startPosition;
				Collections.shuffle(tempStudent.subList(startPosition, tempStudent.size()));
				CourseRegister courseRegister = new CourseRegister(tempStudent,sections,courses);
				courseRegister.generateResult();
				newEnergy = (double)courseRegister.getTotal();
				//if the result is the best so far, store them;
				if (newEnergy > maxEnergy){
					maxEnergy = newEnergy;
					finalSection = courseRegister.getSections();
					finalStudents = courseRegister.getStudents();
					finalCourses = courseRegister.getCourses();
					System.out.println(maxEnergy);
				}
				
				//if the neighbor is better, move to it
				if (newEnergy > oldEnergy){
					oldEnergy = newEnergy;
					students = new ArrayList<Student>(tempStudent);
				}
				else{
					//if the neighbor is worse, move to it with a probability
					if (accept(oldEnergy,newEnergy,temp)){
						oldEnergy = newEnergy;
						students = new ArrayList<Student>(tempStudent);
					}
				}
				temp = temp * alpha;
			}
		}
		//end of loop
		
		//output to output.json
		JSONObject obj = new JSONObject();
		for (int i=0; i < finalCourses.size(); i++){
			Course c = finalCourses.get(i);
			obj.put(c.getName(),getSectionObject(c));
		}
		
		JSONObject classObj = new JSONObject();
		
		JSONObject studentObj = new JSONObject();
		for (int i=0; i<finalStudents.size(); i++){
			studentObj.put("student" + (i+1), formObject(finalStudents.get(i)));
		}
		//System.out.println(studentObj);
		JSONObject total = new JSONObject();
		total.put("classes", obj);
		total.put("students", studentObj);
		
	    FileWriter file = new FileWriter("output.json");
	    file.write(total.toJSONString());
	    file.flush();
	    file.close();
	    //end of output to output.json 		
		//System.out.println(max);
		
		//start of output to requiredByEmol.json :3
		JSONObject newObject = new JSONObject();
		newObject.put("students", sListObject(finalStudents));
		//System.out.println(newObject);
		FileWriter newfile = new FileWriter("requiredByEmol.json");
	    newfile.write(newObject.toJSONString());
	    newfile.flush();
	    newfile.close();
		
		//end of output to requiredByEmol.json :3
	   }
	
	//with some probability, still accpet the worse move
	private static boolean accept(double oldE,double newE, double temp){
		double power = -(oldE - newE)/ temp;
		double probability = Math.exp(power);
		//System.out.println("p = " + probability);
		if (Math.random() < probability)
			return true;
		else 
			return false;
	}
	
	/*
	 *function for reading class JSON 
	 */
	public  static void readClassJSON(String path) throws FileNotFoundException, IOException{	
		JSONParser parser = new JSONParser();
		courses = new ArrayList<Course>();
		sections = new ArrayList<Section>();
		try{
			Object obj = parser.parse(new FileReader(path));
			JSONObject jsonObject = (JSONObject)obj;
	        JSONObject classes = (JSONObject)jsonObject.get("classes");
	        for(Iterator iterator = classes.keySet().iterator(); iterator.hasNext();) {
	       	 	String key = (String) iterator.next();
	        	JSONObject info = (JSONObject)classes.get(key);
	        	String names = (String)info.get("name");
	        	JSONObject times = (JSONObject)info.get("times");
	        	JSONObject time1 = (JSONObject)times.get("time1");
	        	JSONObject time2 = (JSONObject)times.get("time2");
	        	Course c = new Course(Integer.parseInt(key),names,Time.convert(time1),Time.convert(time2));
	        	courses.add(c);
	        	sections.add(c.section1);
	        	sections.add(c.section2);
	        	}
	        }
		catch(ParseException pe){	
			System.out.println("position: " + pe.getPosition());
	        System.out.println(pe);
	        }
		}
	
	/*
	 *function for reading student JSON
	 */
	public  static void readStudentJSON(String path) throws FileNotFoundException, IOException{	
		JSONParser parser = new JSONParser();
		students = new ArrayList<Student>();
		try{
			Object obj = parser.parse(new FileReader(path));
			JSONObject jsonObject = (JSONObject)obj;
			for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				JSONArray student = (JSONArray)jsonObject.get(key);
				String name = (String) student.get(0);
				Student s = new Student(Integer.parseInt(key),name);
				JSONObject avail = (JSONObject) student.get(1);
				for(Iterator iterator1 = avail.keySet().iterator(); iterator1.hasNext();) {
					String key1 = (String) iterator1.next();
					JSONObject availTime = (JSONObject)avail.get(key1);
					Time t = Time.convert(availTime);
					s.addAvailTime(t);
		        	}
				students.add(s);
				}
	        }
		catch(ParseException pe){	
			System.out.println("position: " + pe.getPosition());
	        System.out.println(pe);
	        }
		}
	
	//print student name in all sections; debug purpose
		public static void printName(ArrayList<Section> s){
			for (int i = 0; i < s.size(); i++) {
				ArrayList<Student> b = s.get(i).getResi();
				System.out.println(s.get(i).getName());
				for (int j = 0; j < b.size(); j++){
					System.out.println(b.get(j).getName());
				}
			}
		}
		
		public static JSONObject getIDName(Student s){
			JSONObject obj = new JSONObject();
			obj.put("name", s.getName());
			obj.put("id", s.getID());
			return obj;
		}
		
		public static JSONObject getStudentObject(Section s){
			JSONObject obj = new JSONObject();
			for (int i = 0; i < s.getResi().size(); i++){
				String index = "student" +  (i+1);
				JSONObject studentInfo = getIDName(s.getResi().get(i));
				obj.put(index,studentInfo);
			}
			return obj;
		}
		
		public static JSONObject getSectionObject(Course c){
			JSONObject obj = new JSONObject();
			String day = c.section1.getTime().getTimeString()[0];
			String start = c.section1.getTime().getTimeString()[1];
			String end = c.section1.getTime().getTimeString()[2];
			obj.put(day+"-"+start+"-"+end,getStudentObject(c.section1));
			String day1 = c.section2.getTime().getTimeString()[0];
			String start1 = c.section2.getTime().getTimeString()[1];
			String end1 = c.section2.getTime().getTimeString()[2];
			obj.put(day1+"-"+start1+"-"+end1,getStudentObject(c.section2));			
			return obj;
		}
		
		public static JSONObject formObject(Student input){
			String outAll="";
			ArrayList<Section> courses = input.getReg();
			for(int i=0; i< courses.size();i++){
				Time t = courses.get(i).getTime();
				String outTime = t.getTimeString()[0]+"-"+t.getTimeString()[1]+"-"+t.getTimeString()[2];
				outAll += courses.get(i).getName()+"-"+outTime+",";
			}
			
			JSONObject obj = new JSONObject();
			obj.put("id",input.getID());
			obj.put("name", input.getName());
			obj.put("classTaken",outAll.substring(0, outAll.length()));
			return obj;
		}
		
		public static JSONObject timeObject(Section s){
			JSONObject obj = new JSONObject();
			JSONObject timeOb = new JSONObject();
			timeOb.put("day", s.getTime().getTimeString()[0]);
			timeOb.put("start", s.getTime().getTimeString()[1]);
			timeOb.put("end", s.getTime().getTimeString()[2]);
			return timeOb; 
		}
		
		public static JSONObject nameObject(Section s){
			JSONObject obj = new JSONObject();
			obj.put("name", s.getName());
			obj.put("time", timeObject(s));
			return obj;
		}
		
		public static JSONObject cIdObject(Student s){
			JSONObject obj = new JSONObject();
			ArrayList<Section> section = s.getReg();
			for (int i=0; i < section.size(); i++){
				obj.put(section.get(i).getID(), nameObject(section.get(i)));
			}
			return obj;
		}
		
		public static JSONObject sIdObject(Student s){
			JSONObject obj = new JSONObject();
			obj.put("name", s.getName());
			obj.put("classes", cIdObject(s));
			return obj;
		}
		
		public static JSONObject sListObject(ArrayList<Student> s){
			JSONObject obj = new JSONObject();
			for (int i=0; i< s.size();i++){
				obj.put(s.get(i).getID(), sIdObject(s.get(i)));
			}
			return obj;
		}
}
