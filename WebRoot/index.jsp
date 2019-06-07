<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>PostGeoMedia</title>
<link rel="stylesheet" type="text/css" href="css/ol.css">
<link rel="stylesheet" type="text/css" href="css/common.css">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.0.0.min.js"></script>
<script src="https://cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
<script src="js/ol.js"></script>
<script src="js/layer.js"></script>
<style type="text/css">
table {
	border-collapse: collapse;
	width: 1000px;
	margin: 0 auto;
	margin-bottom: 60px;
}

th {
	background-color: rgb(128, 102, 160);
	color: white;
	font-weight: bold;
}

td {
	color: black;
}

tr, th {
	border-width: 1px;
	border-style: solid;
	border-color: rgb(128, 102, 160);
	text-align: center;
	height: 30px;
}
</style>
<script type="text/javascript"> 
    
    </script>
</head>
<body onload="doRequestUsingGET_Result();">
	<div style="margin-top:20px;margin-left:100px">
		<img style="width:200px; height:120px" src="img/logo.png">
	</div>
	<div class="shine_red"
		style="width:700px; height: 16%; position:absolute; top:180px; left:100px;border:1px solid #009FCC;">
		<input type="text" class="form_input" id="submit"
			value="select * from uservideos where id =1;">
		<!--  Input PostgreSQL Query-->
	</div>

	<form action="#" method="post">
		<div style="width:500px; position:absolute; top:25%; left:100px;">
			<input style="height:30px; margin-left:220px;" type="button" value="     Execute     " onclick="doRequestUsingGET();" /> 
			<input style="height:30px; margin-left:50px;" type="button" value="     Show in Map     " /> 
			<input style="display:none;" type="button" value="show" onclick="doRequestUsingPost()" />
		</div>
		<div class="shine_red" style="width:700px; height: 60%; position:absolute; top:33%; left:100px;border:1px solid #009FCC;">
			<!--  <table border=1 style="width: 100%">
				<tr>
    				<td> 国家 </td>
    				<td> 姓名 </td>
    				<td> 年龄 </td>
				</tr>
			</table>-->
			<div  style="height:80%;width:700px;overflow:auto">
				<table id="tbdata">
				<tr>
					
				</tr>
				</table>
			</div>
		</div>
	</form>

	<div class="shine_red" id="map"
		style="width:1600px; height:85%; left:850px;top:100px; position:absolute;border:1px solid #009FCC;"></div>
	<div id="popup" class="ol-popup">
		<a href="#" id="popup-closer" class="ol-popup-closer"></a>
		<div id="popup-content"></div>
	</div>
	<div style="display:block">
		<div id="serverResponse"></div>
	</div>
</body>
</html>
<script type="text/javascript">
	var center = [ -73.83363056934796, 40.67460574679139 ];
	var markSvgSize = [ 30, 30 ] //
	var pathBackTimer = null; //
	var so = new ol.source.Vector();
	var source = new ol.source.OSM();
	var tile = new ol.layer.Tile({
		source : source
	});
	var vectorLayer = new ol.layer.Vector({
		source : so
	});
	var map = new ol.Map({
		layers : [ //
			tile, vectorLayer
		],
		view : new ol.View({
			center : center,
			minZoom : 3,
			maxZoom : 20,
			zoom : 11
		}),
		target : 'map'
	});

	var container = document.getElementById('popup');
	var content = document.getElementById('popup-content');
	var closer = document.getElementById('popup-closer');
	var popup = new ol.Overlay({
		element : container
	});
	closer.onclick = function() {
		popup.setPosition(undefined);
		closer.blur();
		return false;
	};
	map.addOverlay(popup);
	var geometrydeal = [];
	map.on('singleclick', function(evt) {
		var coordinate = evt.coordinate;
		geometrydeal.push(coordinate);
		var hdms = ol.coordinate.toStringHDMS(ol.proj.toLonLat(coordinate));
		showInfo(coordinate, hdms);
	});
	map.getView().setCenter(ol.proj.transform(center, 'EPSG:4326', 'EPSG:3857'));

	self.resizeTo(800, 600);
	self.focus();
	var websocket;
	var host = "ws://echo.websocket.org/";
	if ('WebSocket' in window) {
		websocket = new WebSocket(host);
	} else {
		alert('No WebSocket');
	}
	websocket.onopen = function(evt) {
		onOpen(evt)
	};
	websocket.onclose = function(evt) {
		onClose(evt)
	};
	websocket.onmessage = function(evt) {
		onMessage(evt)
	};
	websocket.onerror = function(evt) {
		onError(evt)
	};

	$('#map').on('contextmenu', function() {
		 return false;// click
	})
	$('#rightlayer').on('contextmenu', function() {
		return false;
	})

	var select;
	var modify;
	var draw;
	var typeSelect = $("#type");
	var result = false;
	var featureArray = new ol.Collection();

	function Draw() {
		var value = $("#type option:selected").val();
		draw = new ol.interaction.Draw({
			source : so,
			type : value,
			wrapX : false,
			active : false,
			style : new ol.style.Style({
				fill : new ol.style.Fill({
					color : 'rgba(255, 255, 255, 0.2)'
				}),
				stroke : new ol.style.Stroke({
					color : '#ffcc33',
					width : 2
				}),
				image : new ol.style.Circle({
					radius : 7,
					fill : new ol.style.Fill({
						color : '#ffcc33'
					})
				})
			})
		});
		draw.setActive(result);
		map.addInteraction(draw);
	}
	function init() {
		select = new ol.interaction.Select();
		map.addInteraction(select);
		modify = new ol.interaction.Modify({
			features : select.getFeatures()
		});
		map.addInteraction(modify);
		setEvents(select);
	};
	function setEvents(select) {
		var selectedFeatures = select.getFeatures();
		select.on('change:active', function() {
			selectedFeatures.forEach(selectedFeatures.remove, selectedFeatures);
		});
	};
	init();
	function setActive(active, select, modify) {
		select.setActive(active);
		modify.setActive(active);
	};
	typeSelect.change(function() {
		map.removeInteraction(draw);
		Draw();
	});
	Draw();
	setActive(false, select, modify);
</script>
