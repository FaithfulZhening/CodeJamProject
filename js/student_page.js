//=============test if external js is loaded================
/*	var len = $('script[src*="lib/moment.js"]').length; 

	if (len === 0) {
        alert('script not loaded');

        loadScript('lib/moment.js');

        if ($('script[src*="lib/moment.js"]').length === 0) {
            alert('still not loaded');
        }
        else {
            alert('loaded now');
        }
    }
    else {
        alert('script loaded');
    }*/
	//=============test end======================


var StudentId = '<?php echo $_SESSION[ID] ?>';
	alert("studentID is"+studentID);


//make calendar
$(document).ready(function() {
	//alert($_SESSION[ID]);
	//var studentID = '<?php echo $_SESSION[ID] ?>';
	//alert(studentID);

	$('#calendar-show').hide();
});


//===============calendar objects===========================


//small calender object
var cals = {
			height: 400, //auto for alternative
			header: {
				left: 'prev,next today',
				//center: 'title',
				right: 'listDay,listWeek'
			},

			// customize the button names,
			// otherwise they'd all just say "list"
			views: {
				listDay: { buttonText: 'Daily' },
				listWeek: { buttonText: 'Weekly' }
			},

			defaultView: 'listDay',
			defaultDate: '2016-11-21',  //Default: Today
			navLinks: true, // can click day/week names to navigate views
			editable: true,
			eventLimit: true, // allow "more" link when too many events
			
			events: []
		};

//large calender object
var call = {
			aspectRatio: 2, //width = 2 * height
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'listDay,listWeek,  agendaWeek'
			},

			// customize the button names,
			// otherwise they'd all just say "list"
			views: {
				agendaWeek:{buttonText: 'Agenda'},
				listDay: { buttonText: 'Daily' },
				listWeek: { buttonText: 'Weekly' }
			},

			defaultView: 'agendaWeek',
			defaultDate: '2016-11-21',  //Default: Today
			navLinks: true, // can click day/week names to navigate views
			editable: true,
			eventLimit: true, // allow "more" link when too many events
			
			events: []
		};

//=====================Logout php session=========================
function logout() {
    var xhr = new XMLHttpRequest();
   	xhr.open('GET', 'logout.php', true);
    xhr.send();
}




//read json 

function readJson(jsonUrl, showCal, showList){
	$.ajax({
		url: jsonUrl,
		success: function (data) {
			if (data){
				var obj = JSON.parse(data);
				var students = obj.students;
		    		//change this to student id

				//var StudentId = studentID;
				if (!checkExist(students, StudentId)){
					logout();
				}
				var result = getCalEvent(students, StudentId);
				var cevents = result[0];
				var classInfo = result[1];
				console.log(result[1]);

	    		showCal(cevents);
	    		alert(classInfo.length);
	    		showList(classInfo);
			}
		},
		error: function(){
			alert("fail to load json file!");
		}
	});
} 



//show calendar
function showCal(cevents){
	//console.log(cevents);
	cals['events'] = cals['events'].concat(cevents);
	//console.log(cals['events']);
	//$(document).trigger('readJson_complete');
	//$('#calendar').fullCalendar(cals);

	call['events'] = call['events'].concat(cevents);
	//console.log(call['events']);
	$(document).trigger('readJson_complete');
	//$('#hidden').fullCalendar(call);
}




//class list
function showList(classInfo){
	console.log(classInfo);
	var len = classInfo.length;
	alert(len);
	var selector;
	var to_add;
	var cevents = result[0];
	for (i = 1; i<=len; i++){
		alert(i);
		selector = "#c"+i+"name";
		//$(selector).text(classInfo[i-1]['title']);
		console.log(classInfo[i-1]['title']);
		selector = "#c"+i+"detail";
		//$(selector).html(classInfo[i-1]['info']);
		console.log(classInfo[i-1]['info']);
		to_add = '<div class="panel panel-default" id="c'+i+'"><div class="panel-heading"><h4 class="panel-title"><a data-toggle="collapse"  href="#course'+i+' "id="c'+i+'name">'+classInfo[i-1]['title']+'</a></h4></div><div id="course'+i+'" class="panel-collapse collapse in"><div class="panel-body" id="c'+i+'detail">'+classInfo[i-1]['info']+'</div></div></div>';
		console.log(to_add);
		$(".panel-group").append(to_add);

	}
	for (j = 1; j<=len; j++){
		selector = "#c"+j;
		//alert(selector);
		$(selector).show();
	}
}





$(document).ready(function() {
	$(".btn-info").click(function(){
		alert("click");
		$(".btn-info").css('background-color','black');
	});
	//========change this url later=====================
	var jsonUrl ="https://raw.githubusercontent.com/EmolLi/Leetcode/master/studentCourseReg.json";
	
	readJson(jsonUrl, showCal, showList);
	$(document).bind('readJson_complete', function(){
		$('#calendar-show').hide();

		$('#calendar-show').click(function(){
			$('#calendar-show').hide();
			$('#hidden').hide();
			$('#calendar').show();
			$('#big-calendar').show();
		});

		//big calendar
		$('#big-calendar').click(function(){
			$('#calendar').hide();
			$('#big-calendar').hide();
			$('#calendar-show').show();
			$('#hidden').show();
			$('#hidden').fullCalendar(call);
		});
		$('#calendar').fullCalendar(cals);
	});

});


