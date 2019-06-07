function rangequery(responseRange) {
	// var responseRange = document.getElementById("serverRange");
	var rangeSplit = new Array();
	rangeSplit = responseRange.split("#");
	for (i = 1; i < rangeSplit.length; i++) {
		var pl = [];
		var obj = new Array();
		obj = rangeSplit[i].split("@");
		var table = document.getElementById("tb");
		var newRow = table.insertRow();

		var newCell1 = newRow.insertCell(0);
		newCell1.innerHTML = "<td>" + obj[0] + "</td>";
		var newCell2 = newRow.insertCell(1);
		newCell2.innerHTML = "<td>" + obj[1] + "</td>";
		var newCell3 = newRow.insertCell(2);
		newCell3.innerHTML = "<td>" + obj[2].replace('"', "").replace('"', "") + "</td>";
		var newCell4 = newRow.insertCell(3);
		newCell4.innerHTML = "<td>" + obj[3].replace('"', "").replace('"', "") + "</td>";
		var newCell5 = newRow.insertCell(4);
		newCell5.innerHTML = "<td>" + obj[4] + "</td>";
	}
	geometrydeal = [];
}
function trajectory(layerdata) {
	var fileList = new Array();
	var layerjson = new Array();
	fileList = layerdata.split("@");
	for (i = 0; i < fileList.length - 1; i++) {
		var pl = [];
		var obj = JSON.parse(fileList[i]);

		if (fileList[i].indexOf("mpoint") != -1) {
			for (j = 0; j < obj.mpoint.length; j++) {
				pl.push([ obj.mpoint[j].y, obj.mpoint[j].x ]);
			}
		}
		if (fileList[i].indexOf("mpolygon") != -1){
		
			for (j = 0; j < obj.mpolygon.length; j++) {
			//	alert(obj.mpolygon[j].polygon);
				var y;
				var x;
				//pl.push([ obj.mpolygon[j].polygon[0].y, obj.mpolygon[j].polygon[0].x ]);
			}
		}
			
		if (fileList[i].indexOf("mdouble") != -1){
			for (j = 0; j < obj.mdouble.length; j++) {
				pl.push([ obj.mdouble[j].y, obj.mdouble[j].x ]);
			}
		}
			
		if (fileList[i].indexOf("mvideo") != -1){
			for (j = 0; j < obj.mvideo.length; j++) {
				pl.push([ obj.mvideo[j].y, obj.mvideo[j].x ]);
			}
		}
			
		if (fileList[i].indexOf("mphoto") != -1){
			for (j = 0; j < obj.mphoto.length; j++) {
				pl.push([ obj.mphoto[j].y, obj.mphoto[j].x ]);
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

function pathBack(trailLayer, points) {
	map.getView().setCenter(ol.proj.transform(points[0], 'EPSG:4326', 'EPSG:3857'));
	// map.getView().setCenter(ol.proj.transform([-73.83363056934796,40.67460574679139], 'EPSG:4326', 'EPSG:3857'));
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
		var pointFeature = new ol.Feature({
			geometry : new ol.geom.Point(ol.proj.transform(points[i], 'EPSG:4326', 'EPSG:3857'))
		})
		if (pointFeature != null) {
			pointList.push(pointFeature);
		}
	}
	var j = 0;
	pathBackTimer = setInterval(function() {
		if (j + 1 <= pointList.length) {
			if (j > 0) {
				var twoCoordinatePoint = new Array();
				var coordinateFirst = pointList[j - 1].getGeometry().getCoordinates();
				var coordinateSecond = pointList[j].getGeometry().getCoordinates();
				twoCoordinatePoint.push(coordinateFirst);
				twoCoordinatePoint.push(coordinateSecond);
				var lineString = new ol.geom.LineString(twoCoordinatePoint);

				var lineFeature = new ol.Feature({
					geometry : lineString
				});
				lineFeature.setStyle(lineStyle);
				trailLayer.getSource().addFeature(lineFeature);
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