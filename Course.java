package systemModel;

//import java.util.ArrayList;

/*
 * Course has two sections
 * */
public class Course {
	private int ID;
	private String name;
	public Section section1;
	public Section section2;
	//private ArrayList<Student> studentsRegistered;
	/*
	 * Course constructor
	 * */
	public Course(int ID, String name, Time time1, Time time2){
		this.ID = ID;
		this.name = name;
		this.section1 = new Section(time1,name,ID,this,1);
		this.section2 = new Section(time2,name,ID,this,2);
	}
	
	/*
	 * check if the student has registered in another section
	 * return false if the student is registered
	 * return true otherwise;
	 */
	public boolean check(Student stu, Section sect){
		if (sect.getSectionNum() == 1){
			if (sect.getParent().section2.getResi().contains(stu)){
				return false;
			} 
		}
		else if (sect.getParent().section1.getResi().contains(stu)) {
			return false;
		}
		
		return true;
	}
	
	public String getName(){
		return this.name;
	}
}
