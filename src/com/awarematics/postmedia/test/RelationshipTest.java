package com.awarematics.postmedia.test;
import java.io.IOException;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import com.awarematics.postmedia.io.MWKTReader;
import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.MPoint;

public class RelationshipTest {

	public static void main(String[] args) throws IOException, ParseException, java.text.ParseException
	{		
		
		//PrecisionModel precisionModel = new PrecisionModel(1000);
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);	  		  
		
		MPoint mp = (MPoint)reader.read("MPOINT ((0 0) 1481480632123, (2 200) 1481480638000, (5 0) 1481480639123, (13 13) 1481480641123, (30 33) 1481480642556, (70 70) 1481480643223, (102 63) 1481480644123, (103 33) 1481480645556)");
		MPoint mp2 = (MPoint)reader.read("MPOINT ((0 2) 1481480633123, (2 0) 1481480638000, (5 0) 1481480639123, (13 13) 1481480641123, (30 33) 1481480642556, (63 50) 1481480643523, (102 70) 1481480644123, (103 333) 1481480645556)");
		//MVideo mv = (MVideo)reader.read("MVIDEO (('localhost:///tmp/drone/test1.jpg' 200 200 60 0 0.1 30 0 0 'annotation' 'exif' 0 0) 1481480632123, ('localhost:///tmp/drone/test1.jpg' 200 200 60 0 0.1 30 0 0 'annotation' 'exif' 1 1) 1481480634123), ('localhost:///tmp/drone/test1.jpg' 200 200 60 0 0.1 30 0 0 'annotation' 'exif' 101.99 62.9) 1481480644123)");
		//MVideo mv2 = (MVideo)reader.read("MVIDEO (('localhost:///tmp/drone/test1.jpg' 200 200 60 0 0.1 30 0 0 'annotation' 'exif' -0.0001 -0.0001) 1481480632123, ('localhost:///tmp/drone/test1.jpg' 200 200 60 0 0.1 30 0 0 'annotation' 'exif' 0.99 0.99) 1481480634123), ('localhost:///tmp/drone/test1.jpg' 200 200 60 0 0.1 30 0 0 'annotation' 'exif' 102 63) 1481480644123)");
		//MPoint mp3 = (MPoint)reader.read("MPOINT ((0 0) 1581480632123, (0 2) 1581480638000)");
		//System.out.println(mp.atomize(1000).toGeoString());
		//MPolygon mpl = (MPolygon) reader.read("MPOLYGON ((0 0, 1 1, 1 0, 0 0) 1000)");
	//	System.out.println(mpl.toGeoString());
		
	//	System.out.println(mp.relationship(mp, mp2));
// System.out.println( mp2.slice(1000, 1481480640000l).toGeoString());
		
		//System.out.println(mp2.time().toGeoString());
		//MovingDistance mds = null;
		//System.out.println(mds.distance(mp, mp2).toGeoString());
		//System.out.println(mp3.spatial());
		//---------------------------------------------------------------------	
	/*	MInstant mi = (MInstant) reader.read("MINSTANT (1000, 2000, 3000)");
		System.out.println(mi.toGeoString());

		System.out.println(mp.time().toGeoString());

		System.out.println(mp.time(5).toGeoString());
		System.out.println(mp.startTime());
		System.out.println(mp.endTime());
		System.out.println(mp.btime().toGeoString());
		System.out.println(mp.bbox().toText());
		System.out.println(mp3.snapToGrid(3).toGeoString());

		System.out.println(mp.timeAtCummulativeDistance(20));*/
		//MovingDistance md = null;
		//System.out.println(md.distance(mp, mp2).toGeoString());
		//System.out.println(mp.accelerationAtTimeTime(1481480638000l));

//		System.out.println(mp.timeAtCummulativeDistance(20));
	//	System.out.println(mp.veolocityAtTimeTime(1481480638000l));
		//System.out.println(mv.area().toGeoString());
	
		//MBool ms = MGeometry.disjoint(mp, mp3);
		
		//	System.out.println(MGeometry.relationship(mv, mp).toGeoString());
			
			//System.out.println(mp3.btime().toGeoString());
		//System.out.println(MGeometry.meet(mp, mp).toGeoString());
		//System.out.println(MGeometry.intersects(mp, mp).toGeoString());
		/*
		MInstant ms2 = MGeometry.eventTime(mv, mv2);
		System.out.println(ms2.toGeoString());
		//System.out.println(g1.intersects(g2));
		System.out.println("---------------------------");
		MBool mool = MGeometry.intersects(mp, mp2);
		System.out.println("intersect\n"+mool.toGeoString());		
		MBool mool3 = MGeometry.disjoint(mp, mp2);
		System.out.println("disjoint\n"+mool3.toGeoString());
		MBool mool4 = MGeometry.meet(mp, mp2);
		System.out.println("meet\n"+mool4.toGeoString());
		
		MBool mool5 = MGeometry.overlaps(mp, mp2);
		System.out.println("overlaps\n"+mool5.toGeoString());
		MBool mool6 = MGeometry.inside(mp, mp2);
		System.out.println("inside\n"+mool6.toGeoString());
		
		MString mst = MGeometry.relationship(mv, mv2);
		System.out.println(mst.toGeoString());
*/
		//System.out.println("---------------------------");
//MBool mool2 = MGeometry.overlaps(mp, mp2);
		//System.out.println(mool2.toGeoString());

		//System.out.println("---------------------------");
		GeometryFactory geometryFactor = new GeometryFactory();
		WKTReader readers = new WKTReader(geometryFactor);
	     
	        Point p = (Point) readers.read("POINT (0 0)");
	        Point p2 = (Point) readers.read("POINT (0.25 0.15)");
	        Polygon geometry3 = (Polygon) readers.read("POLYGON ((0 0, 0 1, 1 1, 1 0, 0 0))"); 
	        LineString line = (LineString) readers.read("LINESTRING (0 0, 0.5 0.5, 0.5 0.75)");
	        LineString line2 = (LineString) readers.read("LINESTRING (0 0, 0.5 0.5, 0 2)");
	        LineString line3 = (LineString) readers.read("LINESTRING (0 0, 0 1, 0 2)");
	        System.out.println(line.overlaps(geometry3));
	        System.out.println(line2.overlaps(geometry3));
	        System.out.println(line2.crosses(geometry3));
	        System.out.println(line3.overlaps(geometry3));
	        System.out.println(line3.touches(geometry3));
	      //  System.out.println(p2.within(geometry3));
	     /*   //线和线存在
	        System.out.println(geometry1.overlaps(geometry2));//ture
	       //多边形存在
	        System.out.println(geometry3.overlaps(geometry4));//ture

	        System.out.println("----------------");
	        //线和线存在
	        System.out.println(geometry1.contains(geometry5));//ture
	       //多边形存在
	        System.out.println(geometry3.contains(geometry6));//ture
	       //线和多边形
	        System.out.println(geometry3.contains(geometry7));//true
	       //点和多边形
	        System.out.println(geometry3.contains(p2));//true  buneng touches
	       //点和线
	      //true
	        //dian  to dian
	        System.out.println(p.contains(p));//true
	        System.out.println("----------------");
	        //线和线存在
	        System.out.println(geometry5.within(geometry1));//ture
	       //多边形存在
	        System.out.println(geometry6.within(geometry3));//ture
	       //线和多边形
	        System.out.println(geometry7.within(geometry7));//true
	       //点和多边形
	        System.out.println(p2.within(geometry3));//true  buneng touches
	       //点和线
	      //true
	        //dian  to dian
	        System.out.println(p.within(p));//true
	        */
		}
	
}