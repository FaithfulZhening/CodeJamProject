package systemModel;

import java.util.ArrayList;

public class Section {
	public static final int max = 20;  
	private Time sectionTime;
	private int ID;
	private String name;
	private Course parent;
	private int sectionNumber;
	private ArrayList<Student> studentsRegistered;
	private ArrayList<Student> studentsAvailable;
	private int numOfStudent = 0;
	
	public Section(Time t,String name,int ID, Course parent, int sectionNumber){
		this.sectionTime = t;
		this.ID = ID;
		this.name = name;
		this.parent = parent;
		this.sectionNumber = sectionNumber;
		this.studentsRegistered = new ArrayList<Student>();
		this.studentsAvailable = new ArrayList<Student>();
	}
	
	public void addRegisteredStudent(Student s){
		studentsRegistered.add(s);
		numOfStudent++;
	}
	
	public void addAvailableStudent(Student s){
		studentsAvailable.add(s);
	}
	
	public ArrayList<Student> getResi(){
		return this.studentsRegistered;
	}
	
	public ArrayList<Student> getAvail(){
		return this.studentsAvailable;
	}
	
	public Time getTime(){
		return this.sectionTime;
	}
	
	public int getID(){
		return this.ID;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getNum(){
		return this.numOfStudent;
	}
	
	public Course getParent(){
		return this.parent;
	}
	
	public int getSectionNum(){
		return this.sectionNumber;
	}
	
}
	
