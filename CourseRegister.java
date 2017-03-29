package systemModel;

import java.util.ArrayList;
import java.util.Collections;

public class CourseRegister {
	private int classesTotal;
	ArrayList<Student> students; 
	ArrayList<Course> courses;
	ArrayList<Section> sections; 
	private int total;
	
	public CourseRegister(ArrayList<Student> student,ArrayList<Section> section,ArrayList<Course> course){
		this.students = student;
		this.sections = section;
		this.courses = course;
		this.total = 0;
	}
	
	public void generateResult(){
		this.total = 0;
		for (int sectionIndex = 0; sectionIndex < this.sections.size(); sectionIndex++){
			Section section = this.sections.get(sectionIndex);
			if (section.getNum() >= 20) {
				continue;
			}
			for (int studentIndex = 0; studentIndex < this.students.size(); studentIndex++){
				Student student = this.students.get(studentIndex);
				if (student.checkAvail(section) == true){
					student.addCourese(section);
					section.addRegisteredStudent(student);
					total++;
				}
			}
		}
	}
	
	public int getTotal(){
		return this.total;
	}
	
	public ArrayList<Student> getStudents(){
		return this.students;
	}
	
	public ArrayList<Section> getSections(){
		return this.sections;
	}
	
	public ArrayList<Course> getCourses(){
		return this.courses;
	}
	
}
