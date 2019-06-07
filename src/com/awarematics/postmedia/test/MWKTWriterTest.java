package com.awarematics.postmedia.test;

import java.io.IOException;
import java.text.ParseException;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.PrecisionModel;

import com.awarematics.postmedia.io.MWKTWriter;
import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.MPoint;

public class MWKTWriterTest {

	public static void main(String[] args)
			throws IOException, ParseException, org.locationtech.jts.io.ParseException{
	PrecisionModel precisionModel = new PrecisionModel(1000);
	MGeometryFactory geometryFactory = new MGeometryFactory(precisionModel, 0);
	MWKTWriter writer = new MWKTWriter();
	
	
	// MPoint
	Coordinate[] points = { new Coordinate(10, 10, 0),
            new Coordinate(20, 20, 0),
            new Coordinate(30, 40, 0) };
	long[] value = {1481480632123L,1481480632123L,1481480639123L};
	MPoint mline = new MPoint(points,value);
	MPoint lineString = geometryFactory.createMPoint(mline);
	System.out.println(writer.write(lineString));
	
	//MDouble
	//MLineString
	//MMultiPoint
	//Mint
	//MString
	//MBool
	//MVideo
	//MPhoto
	
	}
}
