package com.awarematics.postmedia.test;

import java.io.IOException;

import com.awarematics.postmedia.algorithms.similarity.MEDLCV;
import com.awarematics.postmedia.algorithms.similarity.MEDVVS;
import com.awarematics.postmedia.algorithms.similarity.MVVS;
import com.awarematics.postmedia.io.MWKTReader;
import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.MVideo;

import org.locationtech.jts.io.ParseException;

public class VVSTest {


	public static void main(String[] args) throws IOException, ParseException, java.text.ParseException
	{		
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		
		MVideo mv1 = (MVideo)reader.read("MVIDEO (uri, MPOINT ((0.0 1.0) 1481480632123, (2.0 5.0) 1481480635123, (32.0 333.0) 1481480638000), FRAME ((60 1 1 1 1), (60 1 1 1 2), (60 1 1 1 3)))");	
		MVideo mv2 = (MVideo)reader.read("MVIDEO (uri, MPOINT ((0.0 0.0) 1481480632123, (2.0 5.0) 1481480635123, (34.0 33.0) 1481480638000), FRAME ((60 1 1 1 1), (60 1 1 1 2), (60 1 1 1 3)))");
		
		MEDLCV mv = new MEDLCV();
		
		System.out.println(mv.calculate(mv1, mv2));
	
		}
}