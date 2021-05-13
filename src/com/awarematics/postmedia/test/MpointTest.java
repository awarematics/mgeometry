package com.awarematics.postmedia.test;

import java.io.IOException;

import com.awarematics.postmedia.algorithms.distance.MovingDistance;
import com.awarematics.postmedia.algorithms.similarity.MHausdorff;
import com.awarematics.postmedia.io.MWKTReader;
import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.MPoint;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

public class MpointTest {


	static MHausdorff mh;
	public static void main(String[] args) throws IOException, ParseException, java.text.ParseException
	{		
		GeometryFactory geometryFactory2 = new GeometryFactory();
		WKTReader reader2 = new WKTReader(geometryFactory2);	  		  
		//PrecisionModel precisionModel = new PrecisionModel(1000);
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);	  		  
		MPoint mp3 = (MPoint)reader.read("MPOINT ((13.43593 52.41721) 1180191600000, (13.43593 52.41721) 1180309182881)");
		Point mp2 = (Point)reader2.read("POINT (13.4359 52.41721)");
		double k = 1180309182881d;
		//System.out.println(mp3.snapshot((long)k));	
		System.out.println((mp3.time(1)).toGeoString());	
		Point point = geometryFactory2.createPoint(new Coordinate(mp3.spatial().getCoordinates()[0]));
		
		System.out.println((point.toText()));	
	}
}