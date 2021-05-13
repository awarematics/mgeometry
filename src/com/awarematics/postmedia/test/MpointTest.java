package com.awarematics.postmedia.test;

import java.io.IOException;

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
		MPoint mp3 = (MPoint)reader.read("MPOINT ((13.27861 52.57814) 1180334807943, (13.27861 52.57814) 1180389003917)");
		Point mp2 = (Point)reader2.read("POINT (13.4359 52.41721)");
		String k = "1180334807943";
		
		

		System.out.println(mp3.snapshot(Long.valueOf(k)));	
		//System.out.println((mp3.time(1)).toGeoString());	
		//Point point = geometryFactory2.createPoint(new Coordinate(mp3.spatial().getCoordinates()[0]));
		
		//System.out.println((point.toText()));	
	}
}