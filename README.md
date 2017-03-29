# CodeJamProject

  
  
Back end:
  Back end is mainly in java.
  The program to process JSON file is written in JAVA. To make the programming running, a jspn-simple to process JSON file-1.1.1.jar is needed. 

Also, the classes.json and studentCourseReg.json need to be put in the project directory so that the program can access them. If not in project directory, a path needs to be added manually in mainClass.java.

Algorithm:
Keep generating a solution using simulated annealing and keep track of the one with best performance.

There is a for loop in mainClass.java in which the value of “i”(the number of iteration) can be changed manually. The higher is “i”, the more precise the final result will be. But the running time will rise as well.

This java program will output two JSON files: output.json and requiredByEmol.json,
the output.json is the required output and the other one is used for further data processing and User Interface configuring.


Client Side:
  Used javaScript, Jquery, Ajax, php, Bootstrap, FullCalender API.
  Bootstrap for the main UI framework, Ajax and Jquery to process json data output, FullCalender API to generate a calender for student.
  Students are allowed to view their course schedule and course details in list view or calendar view. For more details, they can also click on the calender for a more detailed weekly/daily view.
  Professors can see the courses that they are teaching, all the students who are taking that course, and a small calendar visualizing their schedule.
  
  

To access our UI: 

Access our website from server
Connect to cs.mcgill.ca/~zzhang348/
There are two parts in the webpage: Student Schedule and Professor Schedule.
For students, click on Student Schedule, enter student ID and click on  Student

Put the package on server
Require: environment for PHP
