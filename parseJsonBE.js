var outFileName = "./studentCourseReg.json";
var json = require('./studentCourseReg.json');
var students = json['students'];

// a boolean
function checkExist(StudentId){
	var student = students[StudentId];
	if (student == null) return false;
	return true;
}

//return student name
function getStudentName(StudentId){
	var student = students[StudentId];
	return student['name'];
}

//return a list of course object
function getStudentCourses(StudentId){
	var student = students[StudentId];
	return student['classes'];
}


function getCalEvent(courses){
	var course;
	var name;
	var day;
	var startTime;
	var endTime;
	var events = [];
	for (var key in courses){
		course = courses[key];
		name = course['name']+" "+key;
		day = course['time']['day'];
		startTime = course['time']['start'];
		endTime = course['time']['end'];
		events.push({"title": name, "start": convertTime(startTime), "end": convertTime(endTime)});
	}
	return events;
}


//convert am/pm to 24h
function convertHour(time){
	var len = time.length;
	var amOrPm = time.substring(len-2, len);
	if (amOrPm == 'am') return time.substring(0,len-2);
	var hour = parseInt(time.substring(0,2));
	hour += 12;
	return hour.toString() + time.substring(2,len-2);
}

//convert time to the style used in calender
function convertTime(time, day){
	var date;
	switch (day){
		case "Monday":
			date = '2016-11-21';
			break;
		case "Tuesday":
			date = '2016-11-22';
			break;
		case "Wednesday":
			date = '2016-11-23';
			break;
		case "Thursday":
			date = '2016-11-24';
			break;
		case "Friday":
			date = '2016-11-25';
			break;
		default:
			date = '-1';
	}
	return date+"T"+convertHour(time);
}