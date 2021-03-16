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
<script src="https://cdn.bootcss.com/layer/3.1.0/layer.js"></script>
<script src="js/common.js"></script>
<script src="js/ol.js"></script>
<script src="js/layer.js"></script>
<link href="css/css_slider.css" type="text/css" rel="stylesheet"
	media="all">
<link href="css/bootstrap.css" rel='stylesheet' type='text/css' />
<link href="css/style.css" rel='stylesheet' type='text/css' />
<link href="css/font-awesome.min.css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/proj4js/2.4.3/proj4.js"></script>
<script src="http://epsg.io/32628.js"></script>
<style type="text/css">
</style>
<script type="text/javascript"> 
    
    </script>
</head>
<body onload="trajectory();">
	<div style="margin-left:100px">
		<img style="width:200px; height:120px" src="img/logo.png">
	</div>
	<div class="shine_red"
		style="width:30%; height: 20%; position:absolute; top:15%; left:5%;border:1px solid #009FCC;">
		<input type="text" class="form_input" id="submit"
			 value="select m_astext(mt) from usertrajs where id<10 and id>1;">
		<!--  select m_astext(mt) from usertrajs where id<10 and id>1; -->
	</div>

	<form action="#" method="post">
		<div style="width:30%; position:absolute; top:25%; left:9.5%;">
			<input class="btn btn-banner my-sm-3 mb-3" type="button"
				value="Execute" onclick="doRequestUsingGET();" />
			<input class="btn btn-banner1 my-sm-3 mb-3" type="button"
				value="Show in Map" onclick="trajectory();window.opener.location.reload();" />
			<input class="btn btn-banner my-sm-3 mb-3" type="button"
				value="Clear" onclick="location.reload();" />
				
		</div>
		<div class="shine_red"
			style="width:30%; height: 50%; position:absolute; top:40%; left:5%;border:1px solid #009FCC;	overflow:auto;">
		
				<table id="tbdata" >
				</table>
			</div>
		
	</form>

	<div class="shine_red" id="map"
		style="width:55%; height:85%; left:40%;top:5%; position:absolute;border:1px solid #009FCC;"></div>
	<div id="popup" class="ol-popup">
		<a href="#" id="popup-closer" class="ol-popup-closer"></a>
		<div id="popup-content"></div>
	</div>
	
	
	
	<div style="display:none">
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
			zoom : 15
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
		return false; // click
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
	}
	;
	function setEvents(select) {
		var selectedFeatures = select.getFeatures();
		select.on('change:active', function() {
			selectedFeatures.forEach(selectedFeatures.remove, selectedFeatures);
		});
	}
	;
	init();
	function setActive(active, select, modify) {
		select.setActive(active);
		modify.setActive(active);
	}
	;
	typeSelect.change(function() {
		map.removeInteraction(draw);
		Draw();
	});
	Draw();
	setActive(false, select, modify);
</script>
