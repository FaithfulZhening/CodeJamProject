var http = require('http');
//var fs = require('fs');
//var formidable = require("formidable");
//var util = require('util');
var express = require('express');
var app = express();
var bodyParser = require('body-parser');
//app.use(bodyParser.json());
//app.use(bodyParser.urlencoded({extended:true}));

var urlencodedParser = bodyParser.urlencoded({extended:false});
/**
var dns = require('dns');
var cors = require('cors');
**/
var path = require('path');
//var harps = require('harp');

app.set('ip', process.env.IP);
app.set('views', path.join(__dirname, 'views'));
app.use(express.static(__dirname));
app.use(express.static('public'));
app.get('/', function(req, res){
	res.sendFile(__dirname+'/index.html');
});
  


var port = process.env.PORT || 8080;

/**
app.get('/users', urlencodedParser, function(req, res) {
	response = { userId: req.body.ID};
	console.log(response);
  	res.send(JSON.stringify(response));
});
**/
app.listen(port);


console.log('Check site on port ' + port);
