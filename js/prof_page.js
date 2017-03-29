var jsonUrl ="https://raw.githubusercontent.com/EmolLi/Leetcode/master/codejam-challenge-example.json";
	



//make calendar




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

			defaultView: 'listWeek',
			defaultDate: '2016-11-21',  //Default: Today
			navLinks: true, // can click day/week names to navigate views
			editable: true,
			eventLimit: true, // allow "more" link when too many events
			
			events: [{
					title: 'Mathematics',
					start: '2016-11-21T08:30:00',
					end: '2016-11-21T10:30:00'
				},

				{
					title: 'Mathematics',
					start: '2016-11-22T08:00:00',
					end: '2016-11-22T10:00:00'
				}
				]
		};





//read json 

function readJson(jsonUrl, showList){
	$.ajax({
		url: jsonUrl,
		success: function (data) {
			if (data){
				var obj = JSON.parse(data);
				console.log(obj);
				var classes = obj.classes;
				console.log(classes);
		    	var maths = classes['Mathematics'];
		    	console.log(maths);
		    	var result = getStudents(maths);
		    	console.log(result);
		    	var listA = result[0];
		    	var listB = result[1];
				console.log(listA);

				
				var to_add;
				var name;
				var id;
				var len = listA.length;
				for (i = 0; i<len; i++){
					name = listA[i]['name'];
					id = listA[i]['id']; 
					to_add = '<tr><th>'+name+'</th><th>'+id+'</th></tr>';
					console.log(to_add);
					$("#c1").append(to_add);
				}

				len = listB.length;
				for (i = 1; i<=len; i++){
					name = listB[i]['name'];
					id = listB[i]['id']; 
					to_add = '<tr><th>'+name+'</th><th>'+id+'</th></tr>';
					console.log(to_add);
					$("#c2").append(to_add);
				}

			}
		},
		error: function(){
			alert("fail to load json file!");
		}
	});
}




$(document).ready(function() {
	//========change this url later=====================
	readJson(jsonUrl);
	
		console.log(cals);
		
		$('#calendar').fullCalendar(cals);

    });
