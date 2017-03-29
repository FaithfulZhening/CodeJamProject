package systemModel;

import java.util.ArrayList;

public class Student {
	private String name;
	private int ID;
	private int numOfCourse;
	private ArrayList<Time> availTime;      		//list of available time, object is Time
	private ArrayList<Section> courseRegistered;	//list of course registered, object is section
	private ArrayList<Section> potentialList;		//list of potential sections matched
	
	/*
	 * Constructor for student object
	 * */
	public Student(int ID, String name){
		this.name = name;
		this.ID = ID;
		numOfCourse = 0;
		availTime = new ArrayList<Time>();
		courseRegistered = new ArrayList<Section>();
		potentialList = new ArrayList<Section>();
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getNumCourse(){
		return this.numOfCourse;
	}
	
	public int getID(){
		return this.ID;
	}
	
	public ArrayList<Time> getTime(){
		return availTime;
	}
	
	public void addAvailTime(Time t){
		availTime.add(t);
	}
	
	public void addCourese(Section s){
		if (numOfCourse<=5){
			courseRegistered.add(s);
			numOfCourse++;
		}
	}
	
	public void addPotential(Section s){
		potentialList.add(s);
	}
	
	public ArrayList<Section> getReg(){
		return courseRegistered;
	}
	
	public ArrayList<Section> getPotential(){
		return potentialList;
	}
	
	//function determining if a student can register a course
	public boolean checkAvail(Section s){
		int check = 0;
		if ((s.getNum()>=20) || (s.getParent().check(this, s) == false) || (this.numOfCourse >= 5)){
			return false;
		}
		for (int i = 0; i < availTime.size(); i++) {
			Time sT = availTime.get(i);
			if (Time.compare(sT, s.getTime()) == true){
				for (int j=0; j<this.getReg().size();j++){
					if (!Time.compare2(sT, this.getReg().get(j).getTime())){
						check = 1;
					}
				}
				if (check == 0){
					return true;
				}
			}
		}
		return false;
	}
	
}
