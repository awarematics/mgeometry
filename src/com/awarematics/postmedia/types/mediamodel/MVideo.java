package com.awarematics.postmedia.types.mediamodel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import com.awarematics.postmedia.mgeom.MGeometryFactory;

public class MVideo extends MGeometry {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] uri;
	double[] viewAngle;
	double[] verticalAngle;
	double[] distance;	
	double[] direction;
	double[] direction3d;
	double[] altitude;
	String[] annotationJson;	
	String[] exifJson;
	Coordinate[] coords;
	long[] creationTime;
	Polygon[] listPolygon;
	FoV[] fov;

	public MVideo() {

	}

	public MVideo(String[] uri, double[] viewAngle,double[] verticalAngle,double[] distance,double[] direction,
			double[] direction3d,double[] altitude,String[] annotationJson,	String[] exifJson,Coordinate[] coords,long[] creationTime, Polygon[] listPolygon, FoV[] fov) {
		this.uri = uri;
		this.viewAngle = viewAngle;
		this.verticalAngle = verticalAngle;
		this.distance = distance;
		this.direction = direction;
		this.direction3d = direction3d;
		this.altitude = altitude;
		this.annotationJson = annotationJson;
		this.exifJson = exifJson;
		this.coords = coords;
		this.creationTime = creationTime;
		this.listPolygon = listPolygon;
		this.fov = fov;
	}


	public MVideo(MVideo mv) {
		uri = mv.uri;
		viewAngle =  mv.viewAngle;
		verticalAngle =  mv.verticalAngle;
		distance =  mv.distance;
		direction =  mv.direction;
		direction3d =  mv.direction3d;
		altitude =  mv.altitude;
		annotationJson =  mv.annotationJson;
		exifJson =  mv.exifJson;
		coords =  mv.coords;
		creationTime =  mv.creationTime;
		listPolygon =  mv.listPolygon;
		fov =  mv.fov;
	}

	public Geometry snapshot(long instant) {
		int searchedPosition = 0;	
		if (creationTime == null )
			return null; 
		if ( (creationTime.length > 0) && ( ( instant < creationTime[0] ) || ( instant > creationTime[creationTime.length-1] ) ))
			return null;
			
		searchedPosition = Arrays.binarySearch( creationTime, instant );
		if ( searchedPosition >= 0 )
		{			
			Polygon coord = this.listPolygon[ searchedPosition ];
			return coord;
		}	
		int startPosition = (searchedPosition*-1)-2;
		int endPosition = (searchedPosition*-1)-1;		
		long startTime = creationTime[startPosition];
		long endTime = creationTime[endPosition];	
		// Assure endTime is not equal startTime 
		double dx =  (instant-startTime) *( coords[endPosition].x - coords[startPosition].x ) / ( endTime - startTime );
		double dy =  (instant-startTime) *( coords[endPosition].y - coords[startPosition].y ) / ( endTime - startTime );
		
		Polygon pol =genFoVArea( coords[startPosition].x + dx , coords[startPosition].y + dy, fov[startPosition]);		
		return pol;
	}


	@Override
	public MGeometry atomize(long duration) {
		long str_start = creationTime[0];
		long str_end = creationTime[numOf() - 1];
		long now = 0;
		
		ArrayList<String> uriNor= new ArrayList<String>();
		ArrayList<Double> viewAngleNor= new ArrayList<Double>();
		ArrayList<Double> verticalAngleNor= new ArrayList<Double>();
		ArrayList<Double> directionNor= new ArrayList<Double>();
		ArrayList<Double> direction3dNor = new ArrayList<Double>();
		ArrayList<Double> distanceNor= new ArrayList<Double>();
		ArrayList<Double> altitudeNor= new ArrayList<Double>();
		ArrayList<String> annotationJsonNor= new ArrayList<String>();
		ArrayList<String> exifJsonNor= new ArrayList<String>();
		ArrayList<Coordinate> coordsNor = new ArrayList<Coordinate>();
		ArrayList<Long> creationTimeNor = new ArrayList<Long>();

		ArrayList<Polygon> listPolygonNor= new ArrayList<Polygon>();
		ArrayList<FoV> fovNor= new ArrayList<FoV>();
		for (int i = 0; i < numOf(); i++) {

			for(long j=now ;j<str_end;j+=duration){
			if (now == 0) {
				now = creationTime[i] - creationTime[i] % duration;
			} else if (now >= str_end) {
				break;
			} else {
				now = now + duration;
			}
			if (now < str_end && now > str_start) {
				listPolygonNor.add((Polygon) snapshot(now));
				creationTimeNor.add(now);
				uriNor.add(uri[i]);
				viewAngleNor.add(viewAngle[i]);
				verticalAngleNor.add(verticalAngle[i]);
				directionNor.add(direction[i]);
				distanceNor.add(distance[i]);
				direction3dNor.add(direction3d[i]);
				altitudeNor.add(altitude[i]);
				annotationJsonNor.add(annotationJson[i]);
				exifJsonNor.add(exifJson[i]);
				coordsNor.add( snapshot(now).getCoordinate());
				fovNor.add(fov[i]);
			}
			}
		}
		//notice:  the method is not support in java-version 8     version 9 is OK
		long[] creationTime = new long[creationTimeNor.size()];
		double[] viewAngle = new double[creationTimeNor.size()];
		double[] direction = new double[creationTimeNor.size()];
		double[] verticalAngle = new double[creationTimeNor.size()];
		double[] distance = new double[creationTimeNor.size()];
		double[] direction3d = new double[creationTimeNor.size()];
		double[] altitude = new double[creationTimeNor.size()];
		for(int i=0;i<creationTimeNor.size();i++){
			creationTime[i] = creationTimeNor.get(i);
			viewAngle[i] = viewAngleNor.get(i);
			verticalAngle[i] = verticalAngleNor.get(i);
			direction[i] = directionNor.get(i);
			distance[i] = distanceNor.get(i);
			direction3d[i] = direction3dNor.get(i);
			altitude[i] = altitudeNor.get(i);
		}	
		//long[] creationTime = creationTimeNor.stream().mapToLong(i -> i).toArray();
		//double[] width = widthNor.stream().mapToDouble(i -> i).toArray();
		//double[] height = heightNor.stream().mapToDouble(i -> i).toArray();
		//double[] viewAngle = viewAngleNor.stream().mapToDouble(i -> i).toArray();
		//double[] verticalAngle = verticalAngleNor.stream().mapToDouble(i -> i).toArray();
		//double[] direction = directionNor.stream().mapToDouble(i -> i).toArray();
		//double[] distance = distanceNor.stream().mapToDouble(i -> i).toArray();
		//double[] direction3d = direction3dNor.stream().mapToDouble(i -> i).toArray();
		//double[] altitude = altitudeNor.stream().mapToDouble(i -> i).toArray();
		Coordinate[] coords = new Coordinate[coordsNor.size()];
		FoV[] fov = new FoV[coordsNor.size()];
		Polygon[] listPolygon = new Polygon[coordsNor.size()];
		String[] uri = new String[coordsNor.size()];
		String[] annotationJson = new String[coordsNor.size()];
		String[] exifJson = new String[coordsNor.size()];
		for (int i = 0; i < coordsNor.size(); i++){
			coords[i] = coordsNor.get(i);
			listPolygon[i] = listPolygonNor.get(i);
			uri[i] = uriNor.get(i);
			fov[i] = fovNor.get(i);
			annotationJson[i] = annotationJsonNor.get(i);
			exifJson[i] = exifJsonNor.get(i);
		}
		MGeometryFactory geometryFactory = new MGeometryFactory();
		return geometryFactory.createMVideo(uri,viewAngle,verticalAngle, distance, direction,direction3d, altitude,annotationJson,exifJson,coords,creationTime, listPolygon, fov );
	}

	@Override
	public MGeometry lattice(long duration) {
		MGeometry pt = atomize(duration);
		Coordinate[] coords = new Coordinate[2];
		long[] creationTime = new long[2];
		double[] viewAngle = new double[2];
		double[] verticalAngle = new double[2];
		double[] direction = new double[2];
		double[] distance = new double[2];
		double[] direction3d = new double[2];
		double[] altitude = new double[2];
		FoV[] fov = new FoV[2];
		Polygon[] listPolygon = new Polygon[2];
		String[] uri = new String[2];
		String[] annotationJson = new String[2];
		String[] exifJson = new String[2];
		
		coords[0] = ((MVideo) pt).getCoords()[0];
		coords[1] = ((MVideo) pt).getCoords()[pt.numOf()-1];
		creationTime[0] = pt.getTimes()[0];
		creationTime[1] = pt.getTimes()[pt.numOf()-1];
		viewAngle[0] = ((MVideo) pt).getViewAngle()[0];
		viewAngle[1] = ((MVideo) pt).getViewAngle()[pt.numOf()-1];
		verticalAngle[0] = ((MVideo) pt).getVerticalAngle()[0];
		verticalAngle[1] = ((MVideo) pt).getVerticalAngle()[pt.numOf()-1];
		direction[0] = ((MVideo) pt).getDirection()[0];
		direction[1] = ((MVideo) pt).getDirection()[pt.numOf()-1];
		distance[0] = ((MVideo) pt).getDistance()[0];
		distance[1] = ((MVideo) pt).getDistance()[pt.numOf()-1];
		direction3d[0] = ((MVideo) pt).getDirection3d()[0];
		direction3d[1] = ((MVideo) pt).getDirection3d()[pt.numOf()-1];
		altitude[0] = ((MVideo) pt).getAltitude()[0];
		altitude[1] = ((MVideo) pt).getAltitude()[pt.numOf()-1];
		fov[0] = ((MVideo) pt).getFov()[0];
		fov[1] = ((MVideo) pt).getFov()[pt.numOf()-1];
		listPolygon[0] = ((MVideo) pt).getListPolygon()[0];
		listPolygon[1] = ((MVideo) pt).getListPolygon()[pt.numOf()-1];
		uri[0] = ((MVideo) pt).getUri()[0];
		uri[1] = ((MVideo) pt).getUri()[pt.numOf()-1];
		annotationJson[0] = ((MVideo) pt).getAnnotationJson()[0];
		annotationJson[1] = ((MVideo) pt).getAnnotationJson()[pt.numOf()-1];
		exifJson[0] = ((MVideo) pt).getExifJson()[0];
		exifJson[1] = ((MVideo) pt).getExifJson()[pt.numOf()-1];
		MGeometryFactory geometryFactory = new MGeometryFactory();
		return geometryFactory.createMVideo(uri,viewAngle,verticalAngle, distance, direction,direction3d, altitude,annotationJson,exifJson,coords,creationTime, listPolygon, fov);
	}

	@Override
	public int numOf() {
		return uri.length;
	}

	

	@Override
	public String toGeoString() {
		String mphotoString = "MVIDEO (";
		for (int i = 0; i < numOf(); i++) {
			if (i == 0){
				mphotoString = mphotoString +"("+ uri[i] + " " + viewAngle[i]+" "+ verticalAngle[i]+" "+distance[i]+" "+direction[i]+" "+direction3d[i]+" "+altitude[i]+" "+annotationJson[i]+" "+exifJson[i]+" "+coords[i].x+" "+coords[i].y+") "+ creationTime[i];
			} 
			else{
				mphotoString = mphotoString +", ("+ uri[i] + " " +  viewAngle[i]+" "+ verticalAngle[i]+" "+distance[i]+" "+direction[i]+" "+direction3d[i]+" "+altitude[i]+" "+annotationJson[i]+" "+exifJson[i]+" "+coords[i].x+" "+coords[i].y+") "+ creationTime[i];
				}
		}
		mphotoString = mphotoString + ")";
		return mphotoString;
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public Geometry spatial() {
		GeometryFactory geo = new GeometryFactory();
		if(numOf()>1)
			return geo.createLineString(coords);
		else
			return geo.createMultiPoint(coords);
	}

	@Override
	public MGeometry spatials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MGeometry lattice(MDuration duration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MGeometry slice(long fromTime, long toTime) {
		MGeometryFactory factory = null;	
		if ( creationTime == null )
			return null;
		
		if ( ( toTime < creationTime[0] ) || ( fromTime > creationTime[ creationTime.length-1]))
			return null;
		
		if( fromTime > toTime )
			return null;
		
        long overlappedStartTime = Math.max(  creationTime[0], fromTime );
        long overlappedEndTime = Math.min( creationTime[ creationTime.length-1], toTime );
        factory = new MGeometryFactory();  
        if ( creationTime.length == 1)
        {        
			return factory.createMVideo( uri,viewAngle,verticalAngle, distance, direction,direction3d, altitude,annotationJson,exifJson,coords,creationTime, listPolygon, fov );			
        }	
        int num = 0;
        for( int i = 0; i < creationTime.length; i++)
        {
        	if( creationTime[i] <= overlappedEndTime && creationTime[i] >= overlappedStartTime )
        	{
        		num++;
        	}
        }		
        if(num==0)
        	return null;
        String[] uril = new String[num];
		double[] viewAnglel = new double[num];
		double[] verticalAnglel = new double[num];
		double[] distancel = new double[num];	
		double[] directionl = new double[num];
		double[] direction3dl = new double[num];
		double[] altitudel = new double[num];
		String[] annotationJsonl = new String[num];	
		String[] exifJsonl = new String[num];
		Coordinate[] coordsl = new Coordinate[num];
		long[] creationTimel = new long[num];
		Polygon[] listPolygonl = new Polygon[num];
		FoV[] fovl = new FoV[num];

        int value = 0;      
        for( int i = 0; i < creationTime.length; i++)
        {
        	if( creationTime[i] <= overlappedEndTime && creationTime[i] >= overlappedStartTime )
        	{
        		uril[value] = uri[i];
        		viewAnglel[value] =  viewAngle[i];
        		verticalAnglel[value] =  verticalAngle[i];
        		distancel[value] = distance[i];
        		directionl[value] = direction[i];
        		direction3dl[value] = direction3d[i];
        		altitudel[value] = altitude[i];
        		annotationJsonl[value] = annotationJson[i];
        		exifJsonl[value] = exifJson[i];
        		coordsl[value] = coords[i];
        		creationTimel[value] = creationTime[i];
        		listPolygonl[value] = listPolygon[i];
        		fovl[value] = fov[i]; 		
        		value++;
        	}
        }    
	    return factory.createMVideo(uril, viewAnglel,verticalAnglel, distancel, directionl,direction3dl, altitudel,annotationJsonl,exifJsonl,coordsl, creationTimel, listPolygonl, fovl );
	}

	@Override
	public MGeometry snapshot(MInstant ts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MGeometry first() {
		String[] uril = new String[1];
		double[] viewAnglel = new double[1];
		double[] verticalAnglel = new double[1];
		double[] distancel = new double[1];	
		double[] directionl = new double[1];
		double[] direction3dl = new double[1];
		double[] altitudel = new double[1];
		String[] annotationJsonl = new String[1];	
		String[] exifJsonl = new String[1];
		Coordinate[] coordsl = new Coordinate[1];
		long[] creationTimel = new long[1];
		Polygon[] listPolygonl = new Polygon[1];
		FoV[] fovl = new FoV[1];
		coordsl[0] = coords[0];
		creationTimel[0] = creationTime[0];
		uril[0] = uri[0];
		viewAnglel[0] = viewAngle[0];
		verticalAnglel[0] = verticalAngle[0];
		distancel[0] = distance[0];
		directionl[0] = direction[0];
		direction3dl[0] = direction3d[0];
		altitudel[0] = altitude[0];
		annotationJsonl[0] = annotationJson[0];
		exifJsonl[0] = exifJson[0];
		listPolygonl[0] = listPolygon[0];
		fovl[0] = fov[0];
		return new MVideo(uril,viewAnglel,verticalAnglel, distancel, directionl,direction3dl, altitudel,annotationJsonl,exifJsonl,coordsl, creationTimel, listPolygonl, fovl);
	}

	@Override
	public MGeometry last() {
		String[] uril = new String[1];
		double[] viewAnglel = new double[1];
		double[] verticalAnglel = new double[1];
		double[] distancel = new double[1];	
		double[] directionl = new double[1];
		double[] direction3dl = new double[1];
		double[] altitudel = new double[1];
		String[] annotationJsonl = new String[1];	
		String[] exifJsonl = new String[1];
		Coordinate[] coordsl = new Coordinate[1];
		long[] creationTimel = new long[1];
		Polygon[] listPolygonl = new Polygon[1];
		FoV[] fovl = new FoV[1];
		coordsl[0] = coords[coords.length-1];
		creationTimel[0] = creationTime[coords.length-1];
		uril[0] = uri[coords.length-1];
		viewAnglel[0] = viewAngle[coords.length-1];
		verticalAnglel[0] = verticalAngle[coords.length-1];
		distancel[0] = distance[coords.length-1];
		directionl[0] = direction[coords.length-1];
		direction3dl[0] = direction3d[coords.length-1];
		altitudel[0] = altitude[coords.length-1];
		annotationJsonl[0] = annotationJson[coords.length-1];
		exifJsonl[0] = exifJson[coords.length-1];
		listPolygonl[0] = listPolygon[coords.length-1];
		fovl[0] = fov[coords.length-1];
		return new MVideo(uril, viewAnglel,verticalAnglel, distancel, directionl,direction3dl, altitudel,annotationJsonl,exifJsonl,coordsl, creationTimel, listPolygonl, fovl);
	}

	@Override
	public MGeometry at(int n) {
		String[] uril = new String[1];
		double[] viewAnglel = new double[1];
		double[] verticalAnglel = new double[1];
		double[] distancel = new double[1];	
		double[] directionl = new double[1];
		double[] direction3dl = new double[1];
		double[] altitudel = new double[1];
		String[] annotationJsonl = new String[1];	
		String[] exifJsonl = new String[1];
		Coordinate[] coordsl = new Coordinate[1];
		long[] creationTimel = new long[1];
		Polygon[] listPolygonl = new Polygon[1];
		FoV[] fovl = new FoV[1];

		coordsl[0] = coords[n-1];
		creationTimel[0] = creationTime[n-1];
		uril[0] = uri[n-1];
		viewAnglel[0] = viewAngle[n-1];
		verticalAnglel[0] = verticalAngle[n-1];
		distancel[0] = distance[n-1];
		directionl[0] = direction[n-1];
		direction3dl[0] = direction3d[n-1];
		altitudel[0] = altitude[n-1];
		annotationJsonl[0] = annotationJson[n-1];
		exifJsonl[0] = exifJson[n-1];
		listPolygonl[0] = listPolygon[n-1];
		fovl[0] = fov[n-1];		
		return new MVideo(uril,viewAnglel,verticalAnglel, distancel, directionl,direction3dl, altitudel,annotationJsonl,exifJsonl,coordsl, creationTimel, listPolygonl, fovl);
	}

	public String[] getUri() {
		return uri;
	}

	public void setUri(String[] uri) {
		this.uri = uri;
	}
	
	public double[] getViewAngle() {
		return viewAngle;
	}

	public void setViewAngle(double[] viewAngle) {
		this.viewAngle = viewAngle;
	}

	public double[] getVerticalAngle() {
		return verticalAngle;
	}

	public void setVerticalAngle(double[] verticalAngle) {
		this.verticalAngle = verticalAngle;
	}

	public double[] getDistance() {
		return distance;
	}

	public void setDistance(double[] distance) {
		this.distance = distance;
	}

	public double[] getDirection() {
		return direction;
	}

	public void setDirection(double[] direction) {
		this.direction = direction;
	}

	public double[] getDirection3d() {
		return direction3d;
	}

	public void setDirection3d(double[] direction3d) {
		this.direction3d = direction3d;
	}

	public double[] getAltitude() {
		return altitude;
	}

	public void setAltitude(double[] altitude) {
		this.altitude = altitude;
	}

	public String[] getAnnotationJson() {
		return annotationJson;
	}

	public void setAnnotationJson(String[] annotationJson) {
		this.annotationJson = annotationJson;
	}

	public String[] getExifJson() {
		return exifJson;
	}

	public void setExifJson(String[] exifJson) {
		this.exifJson = exifJson;
	}

	public Coordinate[] getCoords() {
		return coords;
	}

	public void setCoords(Coordinate[] coords) {
		this.coords = coords;
	}

	public long[] getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long[] creationTime) {
		this.creationTime = creationTime;
	}

	public Polygon[] getListPolygon() {
		return listPolygon;
	}

	public void setListPolygon(Polygon[] listPolygon) {
		this.listPolygon = listPolygon;
	}

	public FoV[] getFov() {
		return fov;
	}

	public void setFov(FoV[] fov) {
		this.fov = fov;
	}
	private Polygon genFoVArea(double x, double y, FoV fov) {
		int times = 1;
		double x4 = (double) x;
		double y4 = (double) y;
		
		double x2 = (double) x
				+ fov.getDistance() * 2/Math.sqrt(3) * Math.sin(Math.toRadians(fov.getDirection() + (fov.getViewAngle() )/ 2)) * times;
		double y2 = (double) y
				+ fov.getDistance() *  2/Math.sqrt(3) *Math.cos(Math.toRadians(fov.getDirection() +(fov.getViewAngle() )/ 2)) * times;// left
		double x3 = (double) x
				+ fov.getDistance() *  2/Math.sqrt(3) *Math.sin(Math.toRadians(fov.getDirection() - (fov.getViewAngle() ) / 2)) * times;
		double y3 = (double) y
				+ fov.getDistance() * 2/Math.sqrt(3) * Math.cos(Math.toRadians(fov.getDirection() -(fov.getViewAngle() ) / 2)) * times;// right
		GeometryFactory geometryFactory = new GeometryFactory();
		Coordinate[] coor1 = new Coordinate[4];
		coor1[0] = new Coordinate(x4, y4);
		coor1[1] = new Coordinate(x2, y2);
		coor1[2] = new Coordinate(x3, y3);
		coor1[3] = new Coordinate(x4, y4);
		LinearRing line = geometryFactory.createLinearRing(coor1);
		Polygon pl1 = geometryFactory.createPolygon(line, null);
		return pl1;
	}

	@Override
	public long[] getTimes() {
		// TODO Auto-generated method stub
		return creationTime;
	}

	@Override
	public double veolocityAtTimeTime(long instant) {
		double  veolocity = 0;
		int searchedPosition = 0;
		if (creationTime == null) {
			return 0;
		}

		if ((creationTime.length > 0) && ((instant < creationTime[0]) || (instant > creationTime[creationTime.length - 1]))) {
			return 0;
		}
		searchedPosition = Arrays.binarySearch(creationTime, instant);
		if (searchedPosition >= 0) {
			Coordinate coord = this.coords[searchedPosition];
			if(searchedPosition==0)
				return 0;
			else{
				veolocity = calDistance(coord, this.coords[searchedPosition-1])/ (instant-this.creationTime[searchedPosition-1]);
				return veolocity;
			}
		}

		int startPosition = (searchedPosition * -1) - 2;
		int endPosition = (searchedPosition * -1) - 1;
		long startTime = creationTime[startPosition];
		long endTime = creationTime[endPosition];

		// Assure endTime is not equal startTime
		double dx = (instant - startTime) * (coords[endPosition].x - coords[startPosition].x) / (endTime - startTime);
		double dy = (instant - startTime) * (coords[endPosition].y - coords[startPosition].y) / (endTime - startTime);
		Coordinate newCoord = new Coordinate(coords[startPosition].x + dx, coords[startPosition].y + dy);
		veolocity = calDistance(newCoord, coords[startPosition])/ (instant-creationTime[startPosition]);
		return veolocity;
	}
	@Override
	public double accelerationAtTimeTime(long instant) {
		double  veolocityt = 0;
		double  veolocity0 = 0;
		double  acceleration =0;
		int searchedPosition = 0;
		if (creationTime == null) {
			return 0;
		}

		if ((creationTime.length > 0) && ((instant < creationTime[0]) || (instant > creationTime[creationTime.length - 1]))) {
			return 0;
		}
		searchedPosition = Arrays.binarySearch(creationTime, instant);
		if (searchedPosition >= 0) {
			Coordinate coord = this.coords[searchedPosition];
			if(searchedPosition==0)
				return 0;
			else{
				veolocityt = calDistance(coord, this.coords[searchedPosition-1])/ (instant-this.creationTime[searchedPosition-1]);
				veolocity0 = veolocityAtTimeTime(this.creationTime[searchedPosition-1]);
				acceleration = 2*(veolocityt - veolocity0 * (instant-this.creationTime[searchedPosition-1]))/(Math.pow((instant-this.creationTime[searchedPosition-1]), 2));
				return acceleration*1000;
			}
		}

		int startPosition = (searchedPosition * -1) - 2;
		int endPosition = (searchedPosition * -1) - 1;
		long startTime = creationTime[startPosition];
		long endTime = creationTime[endPosition];

		// Assure endTime is not equal startTime
		double dx = (instant - startTime) * (coords[endPosition].x - coords[startPosition].x) / (endTime - startTime);
		double dy = (instant - startTime) * (coords[endPosition].y - coords[startPosition].y) / (endTime - startTime);
		Coordinate newCoord = new Coordinate(coords[startPosition].x + dx, coords[startPosition].y + dy);
		veolocityt = calDistance(newCoord, coords[startPosition])/ (instant-creationTime[startPosition]);
		veolocity0 = veolocityAtTimeTime(creationTime[startPosition]);
		acceleration = 2*(veolocityt - veolocity0 * (instant-creationTime[startPosition]))/(Math.pow((instant-creationTime[startPosition]), 2));
		return acceleration*1000;
	}

	@Override
	public long timeAtCummulativeDistance(double distance) {
		double[] value = new double[numOf()];
		//System.out.println(distance);
		long time = -1;
		value[0] = 0;
		for (int i = 1; i < numOf(); i++) {
			value[i] = value[i - 1] + calDistance(coords[i], coords[i - 1]);
		}
		for (int i = 1; i < numOf(); i++) {
			//System.out.println(value[i]);
			if (distance >= value[i - 1] && distance < value[i]) {
				double leftpart = distance - value[i - 1];
				double total = value[i] - value[i - 1];
				time = (long) ((creationTime[i] - creationTime[i - 1]) * leftpart / total + creationTime[i - 1]);
			}
		}
		return time;
	}

	//@Override
	public MGeometry snapToGrid(int cellSize) {
		MGeometryFactory mgeometryFactory = new MGeometryFactory();
		String[] uriGrid = uri;
		double[] viewAngleGrid =viewAngle;
		double[] verticalAngleGrid =verticalAngle;
		double[] distanceGrid =  distance;	
		double[] directionGrid =  direction;
		double[] direction3dGrid =  direction3d;
		double[] altitudeGrid = altitude;
		String[] annotationJsonGrid = annotationJson;	
		String[] exifJsonGrid = exifJson;
		Polygon[] listPolygonGrid = listPolygon;
		FoV[] fovGrid = fov;
		Coordinate[] coordGrid = new Coordinate[numOf()];
		long[] timesGrid = creationTime;
		
		for(int i=0;i< numOf();i++)
		{
			
				coordGrid[i] = new Coordinate();
				BigDecimal bgx = new BigDecimal(coords[i].x);
				coordGrid[i].x = bgx.setScale(cellSize, BigDecimal.ROUND_HALF_UP).doubleValue();
				BigDecimal bgy = new BigDecimal( coords[i].y);
				coordGrid[i].y = bgy.setScale(cellSize, BigDecimal.ROUND_HALF_UP).doubleValue();
		}	
		return mgeometryFactory.createMVideo(uriGrid,viewAngleGrid,verticalAngleGrid, distanceGrid, directionGrid,direction3dGrid, altitudeGrid,annotationJsonGrid,exifJsonGrid,coordGrid, timesGrid, listPolygonGrid, fovGrid );
	
	}

	@Override
	public Geometry bbox() {
		Geometry gg = spatial();
		return gg.getEnvelope();
	}

	@Override
	public MPeriod btime() {
		//System.out.println("gg");
		MPeriod mp = new MPeriod();
		mp.period = new Period[numOf()];
		for(int i=1;i<numOf();i++)
		{
			mp.period[i] = new Period();
			mp.period[i].from=( first().getTimes()[0]);
			mp.period[i].to=(at(i+1).getTimes()[0]);
		}	
		return mp;
	}

	@Override
	public Period time() {
		Period[] mp = new Period[numOf()];
		for(int i=1;i<numOf();i++)
		{
			mp[i] = new Period();
			mp[i].from=( first().getTimes()[0]);
			mp[i].to=(at(i+1).getTimes()[0]); 
		}		
		return mp[numOf()-1];
		/*
		 * Period[] mp = new Period[numOf()];
		for (int i = 1; i < numOf(); i++) {
			mp[i] = new Period();
			mp[i].from = (first().getTimes()[0]);
			mp[i].to = (at(i+1).getTimes()[0]);
		}
		return mp[numOf() - 1];
		 */
	}
	@Override
	public Period time(int n) {
		Period[] mp = new Period[numOf()];
		for(int i=1;i<numOf();i++)
		{
			//mp.period[i] = new Period();
			mp[i] = new Period();
			mp[i].from =  first().getTimes()[0];
			mp[i].to = at(i+1).getTimes()[0];
		}		
		return mp[n];
	}
	@Override
	public long startTime() {
		return getTimes()[0];
	}
	@Override
	public long endTime() {
		// TODO Auto-generated method stub
		return getTimes()[numOf()-1];
	}

	@Override
	public MDouble timeToDistance() {
		MGeometryFactory mgeometryFactory = new MGeometryFactory();
		double[] mdistance = new double[numOf()];
		mdistance[0] = 0;
		for (int i = 1; i < numOf(); i++) {
			mdistance[i] =  calDistance(coords[i], coords[0]);
		}	
		return mgeometryFactory.createMDouble(mdistance,creationTime);
	}
	@Override
	public ArrayList<Long> timeAtDistance(double distance) {
		double[] mdistance = new double[numOf()];
		 ArrayList<Long> time = new  ArrayList<Long>();
		mdistance[0] = 0;
		for (int i = 1; i < numOf(); i++) {
			mdistance[i] = calDistance(coords[i], coords[0]);
		}
		for (int i = 1; i < numOf(); i++) {
			if ((distance >= mdistance[i - 1] && distance < mdistance[i]) || (distance < mdistance[i - 1] && distance >= mdistance[i])) {
				double leftpart = distance - mdistance[i - 1];
				double total = mdistance[i] - mdistance[i - 1];
				time.add((long) ((creationTime[i] - creationTime[i - 1]) * leftpart / total + creationTime[i - 1]));
			}
		}
		return time;
	}

	@Override
	public MDouble area() {
		MGeometryFactory mgeometryFactory = new MGeometryFactory();
		double[] area = new double[numOf()];
		for(int i =0;i<numOf();i++){
			area[i] = listPolygon[i].getArea();
		}
		return mgeometryFactory.createMDouble(area, creationTime);
	}

	@Override
	public MDouble direction() {
		MGeometryFactory mgeometryFactory = new MGeometryFactory();
		return mgeometryFactory.createMDouble(direction, creationTime);
	}

	@Override
	public MInt count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MDouble velocity() {
		MGeometryFactory mgeometryFactory = new MGeometryFactory();
		double[] veolocity  = new double[numOf()];
		veolocity[0] = 0;
		for(int i =1; i< numOf();i++)
		{
			veolocity[i] = calDistance(coords[i],coords[i-1])/ (creationTime[i]-creationTime[i-1]);
		}
		
		return mgeometryFactory.createMDouble(veolocity, creationTime);
	}

}
