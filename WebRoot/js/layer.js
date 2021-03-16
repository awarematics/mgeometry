

var xmlHttp;
function createXMLHttpRequest() {
	if (window.ActiveXObject) {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	} else if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest();
	}
}
function createQueryString() {
	var firstName = document.getElementById("submit").value;
	index = firstName.lastIndexOf("\\");
	firstName = firstName.substring(index + 1);
	var queryString = "submit=" + firstName;
	return queryString;
}

function doRequestUsingGET() {
	createXMLHttpRequest();
	var queryString = "GetAndPostExample?";
	queryString = queryString + createQueryString()
	+ "&timeStamp=" + new Date().getTime();
	// alert(queryString);
	xmlHttp.open("GET", queryString, true);
	xmlHttp.onreadystatechange = handleStateChange;
	xmlHttp.send();
}


function doRequestUsingPOST() {
	createXMLHttpRequest();
	var url = "GetAndPostExample?timeStamp=" + new Date().getTime();
	var queryString = createQueryString();
	xmlHttp.open("POST", url, true);
	xmlHttp.onreadystatechange = handleStateChange;
	xmlHttp.setRequestHeader("Content-Type",
		"application/x-www-form-urlencoded;");
	xmlHttp.send(queryString);
}

function handleStateChange() {
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			parseResults();
		}
	// parseResults();
	}
}

function parseResults() {
	document.getElementById('serverResponse').innerHTML = xmlHttp.responseText;
	var responseDiv = document.getElementById("serverResponse");
	if (responseDiv.hasChildNodes()) {
		responseDiv.removeChild(responseDiv.childNodes[0]);
	}
	var responseText = document.createTextNode(xmlHttp.responseText);
	// alert("后台返回的返回值： "+xmlHttp.responseText);  
	responseDiv.appendChild(responseText);
	dealData();
}
function dealData() {
	var word = document.getElementById("serverResponse").innerText;
	var data = word.split("*")[1];
	var typename = word.split("*")[0];

	var dataSplit = new Array();
	dataSplit = data.split("#");

	var dataSplit2 = new Array();
	dataSplit2 = typename;

	var obj2 = new Array();
	obj2 = dataSplit2.split("#");
	var table = document.getElementById("tbdata");
	var newRow2 = table.insertRow();

	for (j = 0; j < obj2.length - 1; j++) {
		var newCell2 = newRow2.insertCell(j);
		if (j == 0)
			newCell2.innerHTML = "<td>" + "&nbsp;&nbsp;" + obj2[j] + "</td>";
		else
			newCell2.innerHTML = "<td>" + "&nbsp;&nbsp;" + obj2[j] + "</td>";
	}
	for (i = 0; i < dataSplit.length - 1; i++) {
		var obj = new Array();
		obj = dataSplit[i].split("@");
		var table2 = document.getElementById("tbdata");
		var newRow = table2.insertRow();

		for (j = 0; j < obj.length; j++) {
			var newCell = newRow.insertCell(j);
			if (j == 0)
				newCell.innerHTML = "<td>" + "&nbsp;&nbsp;" + obj[j] + "</td>";
			else
				newCell.innerHTML = "<td>" + "&nbsp;&nbsp;" + obj[j] + "</td>";
		}
	}
}



function mgeo(trajs, lengthss) {
	for (i = 0; i < lengthss; i++) {
		var pl = [];
		var coordinate = [];
		coordinate = trajs[i].split(") ");
		var x; var y; var point;
		/*
		if (trajs[i].indexOf("MVIDEO") != -1) {
			for (j = 0; j < coordinate.length; j++) {
				var point = coordinate[j].split(" 'null' 'null' ")[1];
				var x = point.split(" ")[0];
				var y = point.split(" ")[1];
				pl.push([ y, x ]);
			}
		}
*/
		if (trajs[i].indexOf("MPOINT") != -1) {
			for (j = 0; j < coordinate.length-1; j++) {
				if (j == 0) {
					point = coordinate[j].split("((")[1];
					x = point.split(" ")[0];
					y = point.split(" ")[1];
					pl.push([ parseFloat(y), parseFloat(x) ]);
				
				} else if(j !=coordinate.length-1 ){
					point = coordinate[j].split("(")[1];
					x = point.split(" ")[0];
					y = point.split(" ")[1];				
					pl.push([ parseFloat(y), parseFloat(x) ]);
				} 
			}	
		}
		pathBack(vectorLayer, pl);
	}
	map.on('contextmenu', function(evt) {
		var e_ = window.event || evt;
		var x = e_.clientX,
			y = e_.clientY;
		var divObj = document.getElementById('rightlayer');
		divObj.style.position = 'absolute';
		divObj.style.top = y;
		divObj.style.left = x;
		$('#rightlayer').show();
	});
	map.on('pointermove', function(evt) {
		$('#rightlayer').hide();
	});
}

function trajectory() {
	var word = document.getElementById("serverResponse").innerText;
	var data = word.split("*")[1];
	var dataSplit = new Array();
	dataSplit = data.split("#");
	mgeo(dataSplit, dataSplit.length);
}

function pathBack(trailLayer, points) {
	
	map.getView().setCenter(ol.proj.transform([-73.9516338520440684,40.7780543994538291], 'EPSG:4326', 'EPSG:3857'));
	//map.getView().setCenter(ol.proj.transform([-73.83363056934796,40.67460574679139], 'EPSG:4326', 'EPSG:3857'));
	pathBackTimer = null;
	if (pathBackTimer)
		clearInterval(pathBackTimer);
	var lineStyle = new ol.style.Style({
		stroke : new ol.style.Stroke({
			width : 3,
			color : [ 255, 0, 0, 1 ]
		})
	});
	var pointList = new Array();
	for (var i = 0; i < points.length; i++) {
		//alert(points[i]);
		var pointFeature = new ol.Feature({
			geometry : new ol.geom.Point(ol.proj.transform(points[i], 'EPSG:4326', 'EPSG:3857'))
		})
		if (pointFeature != null) {
			pointList.push(pointFeature);
		}
	}
	var j = 0;
	pathBackTimer = setInterval(function() {
		var siteStyle = {
				  // style_definition
				};
		if (j + 1 <= pointList.length) {
			if (j > 0) {
				var twoCoordinatePoint = new Array();
				var threeCoordinatePoint = new Array();
				
				var coordinateFirst = pointList[j - 1].getGeometry().getCoordinates();
				var coordinateSecond = pointList[j].getGeometry().getCoordinates();
				var left = [];
				var right = [];
				left.push([ pointList[j - 1].x+1, pointList[j - 1].y+1]);
				right.push([ pointList[j - 1].x-1, pointList[j - 1].y-1]);
				
				twoCoordinatePoint.push(coordinateFirst);
				twoCoordinatePoint.push(coordinateSecond);
				threeCoordinatePoint.push(coordinateFirst);
				threeCoordinatePoint.push(left);
				threeCoordinatePoint.push(right);
				threeCoordinatePoint.push(coordinateFirst);
				
				var lineString = new ol.geom.LineString(twoCoordinatePoint);
				var LinearRing = new ol.geom.LinearRing(threeCoordinatePoint);
				var polygonFeature = new OpenLayers.Feature.Vector(LinearRing, null, siteStyle);
				var lineFeature = new ol.Feature({
					geometry : lineString
				});
				var linearFeature = new ol.Feature({
					geometry : LinearRing
				});
				lineFeature.setStyle(lineStyle);
				linearFeature.setStyle(lineStyle);
				trailLayer.getSource().addFeature(lineFeature);
				trailLayer.getSource().addFeature(polygonFeature);
				if (j > 1) {
					pointList[j - 1].setStyle(null);
				}
				pointList[j].setStyle(getPngStyle(coordinateFirst, coordinateSecond));
			}
			if (j == 0 || j == (pointList.length - 1)) {
				pointList[j].setStyle(getSvgStyle());
			}
			trailLayer.getSource().addFeature(pointList[j]);
		} else {
			clearInterval(pathBackTimer);
			j = -1;
		//trailLayer.getSource().clear();
		}
		j++;
	}, 6);
}
function getPngStyle(start, end) {
	var dx = end[0] - start[0];
	var dy = end[1] - start[1];
	var rotation = Math.atan2(dy, dx);
	return new ol.style.Style({
		// geometry: new ol.geom.Point(end),
		image : new ol.style.Icon(({
			//color: [255, 0, 0, 1],
			//crossOrigin: 'anonymous',
			anchor : [ 0.75, 0.5 ],
			name : "5555",
			src : 'arrow2.png',
			rotateWithView : true,
			rotation : -rotation
		}))
	})
}
function getSvgStyle() {
	//
	var svg = '<svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" width="30px" height="30px" viewBox="0 0 30 30" enable-background="new 0 0 30 30" xml:space="preserve">' +
		'<path fill="#FF0000" d="M22.906,10.438c0,4.367-6.281,14.312-7.906,17.031c-1.719-2.75-7.906-12.665-7.906-17.031S10.634,2.531,15,2.531S22.906,6.071,22.906,10.438z"/>' +
		'<circle fill="#FFFFFF" cx="15" cy="10.677" r="3.291"/></svg>';
	var mysvg = new Image();
	mysvg.src = 'data:image/svg+xml,' + escape(svg);
	return new ol.style.Style({
		image : new ol.style.Icon({
			img : mysvg, // 
			imgSize : markSvgSize
		})
	});
}
function showInfo(coordinate, info) {
	content.innerHTML = '<p>You clicked here:</p><code>' + info +
		'</code>';
	popup.setPosition(coordinate)
}

function onOpen(evt) {
	console.log("Connected to WebSocket server.");
}
function onClose(evt) {
	console.log("Disconnected");
}
function onMessage(evt) {
	console.log('Retrieved data from server: ' + evt.data);
}
function onError(evt) {
	console.log('Error occured: ' + evt.data);
}
window.onbeforeunload = function() {
	onClose(evt);
}