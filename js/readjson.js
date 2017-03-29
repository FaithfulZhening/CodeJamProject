
function getStudents(courses){
	var listA=[];
	var listB=[];
	for (var key in courses["MONDAY-AM0830-AM1030"]){
		listA.push(courses["MONDAY-AM0830-AM1030"][key]);
	}
	for (var key in courses["TUESDAY-AM0800-AM1000"]){
		listB.push(courses["TUESDAY-AM0800-AM1000"][key]);
	}
	return [listA,listB];
}


// a boolean
function checkExist(students, StudentId){
	var student = students[StudentId];
	if (student == null) return false;
	return true;
}



//return student name
function getStudentName(students, StudentId){
	var student = students[StudentId];
	return student['name'];
}

//return a list of course object
function getStudentCourses(students, StudentId){
	var student = students[StudentId];
	return student['classes'];
}

function getCalEvent(students, StudentId){
	var courses = getStudentCourses(students, StudentId);
	return getCalEventH(students, courses);
}



function getCalEventH(students, courses){
	var course;
	var name;
	var day;
	var startTime;
	var endTime;
	var info;
	var classesInfoList = [];
	var events = [];
	for (var key in courses){
		course = courses[key];
		name = course['name']+" "+key;
		day = course['time']['day'];
		startTime = course['time']['start'];
		endTime = course['time']['end'];
		info = convertHour(startTime)+'-'+convertHour(endTime)+" "+course['time']['day']+"<br>"+"Lecturer: Prof.X";
		classesInfoList.push({"title": name, "info": info});
		events.push({"title": name, "start": convertHour(startTime), "end": convertHour(endTime), "dow": [convertTime(day)]});
	}
	var result = [];
	result.push(events);
	result.push(classesInfoList);
	return result;
}



//convert am/pm to 24h
function convertHour(time){
	var len = time.length;
	var amOrPm = time.substring(0, 2);
	if (amOrPm == 'AM') {
		//AM8000 
		var hourNum;
		if(len==5) hourNum = time.substring(2,3);
		else hourNum = time.substring(2,4);
		return  hourNum+":"+time.substring(len-2,len);
	}
	else{
		//PM300
		var hour;
		if(len==5) hour = parseInt(time.substring(2,3));
		else hour = parseInt(time.substring(2,4));
		hour += 12;
		return hour.toString() +":"++ time.substring(len-2,len);
	}
	
}



//convert time to the style used in calender
function convertTime(day){
	var date;
	switch (day){
		case "MONDAY":
			date = 1;
			break;
		case "TUESDAY":
			date = 2;
			break;
		case "WEDNESDAY":
			date = 3;
			break;
		case "THURSDAY":
			date = 4;
			break;
		case "FRIDAY":
			date = 5;
			break;
		default:
			date = '-1';
	}
	return date;
}

