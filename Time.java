package systemModel;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

/*
 * time class with start and finish time;
 */
public class Time {
	private int start;
	private int finish;
	private String[] timeString = new String[3];
	public Time(int start,int finish, String[] input){
		this.start = start;
		this.finish = finish;
		timeString = input;
	}
	
	/*
	 * start time getter
	 */
	public int getStart(){
		return this.start;
	}
	
	/*
	 * finish time getter
	 * */
	public int getFinish(){
		return this.finish;
	}	
	
	public String[] getTimeString(){
		return this.timeString;
	}
	/*
	 * convert JSONObject to a time object
	 * convert time to a time line represented by 240 half hours
	 * i.e five days a week
	 */
	public static Time convert(JSONObject o){
		String start = (String)o.get("start");
		String end = (String)o.get("end");
		String day = (String)o.get("day");
		String timeString[] = new String[3];
		timeString[0] = day.toUpperCase();
		timeString[1] = start;
		timeString[2] = end;
		String temp;
		if ((start.equals("NA")) || (end.equals("NA"))){
			return new Time(0,0,timeString);
		}
		int startTime = 0;
		switch(day){
		case "Monday":
			startTime = 0;
			break;
		case "Tuesday":
			startTime = 48;
			break;
		case "Wednesday":
			startTime = 96;
			break;
		case "Thursday":
			startTime = 144;
			break;
		case "Friday":
			startTime = 192;
			break;
		}
		int endTime = startTime;
		//start of getting start time
		temp = start.substring(Math.max(start.length() - 2, 0));
		timeString[1] = temp;
		if (temp.equals("pm")){
			startTime += 24;
		}
		start = start.substring(0, start.length()-2);
		
		String[] tuples = start.split(":");
		timeString[1] = (timeString[1] + tuples[0]+tuples[1]).toUpperCase();
		temp = tuples[0];
		if (Integer.parseInt(temp) != 12){
			startTime += Integer.parseInt(temp)*2;
		} 
		temp = tuples[1];
		if (Integer.parseInt(temp) == 30){
			startTime += 1;
		}
		// end of getting start time
		
		//start of getting end time
		temp = end.substring(Math.max(end.length() - 2, 0));
		timeString[2] = temp;
		if (temp.equals("pm")){
			endTime += 24;
		}
		end= end.substring(0, end.length()-2);
		tuples = end.split(":");
		timeString[2] = (timeString[2] + tuples[0]+tuples[1]).toUpperCase();
		temp = tuples[0];
		if (Integer.parseInt(temp) != 12){
			endTime += Integer.parseInt(temp)*2;
		} 
		temp = tuples[1];
		if (Integer.parseInt(temp) == 30){
			endTime += 1;
		}
		
		Time t = new Time(startTime,endTime,timeString);
		//end of getting end time
		return t;
	}
	
	/*
	 *	Function used to compare student time and course time; 
	 * 	If student time starts early and finish later than course time,return true;
	 * 	i.e. student have time for this course
	 * 	else return false;
	 */
	public static boolean compare(Time sTime,Time cTime){
		if ((sTime.getStart()<=cTime.getStart()) && (sTime.getFinish()>=cTime.getFinish()) ){
			return true;
		}
		return false;
	}
	
	public static boolean compare2(Time sTime1,Time sTime2){
		if ((sTime1.getStart()>=sTime2.getFinish()) || (sTime1.getFinish()<=sTime2.getStart())){
			return true;
		}
		return false;
	}
}
