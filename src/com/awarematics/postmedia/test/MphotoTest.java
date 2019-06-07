package com.awarematics.postmedia.test;

import java.io.IOException;

import com.awarematics.postmedia.algorithms.distance.MovingDistance;
import com.awarematics.postmedia.algorithms.similarity.MHausdorff;
import com.awarematics.postmedia.io.MWKTReader;
import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.MPhoto;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;

public class MphotoTest {


	static MHausdorff mh;
	public static void main(String[] args) throws IOException, ParseException, java.text.ParseException
	{		
		
		//PrecisionModel precisionModel = new PrecisionModel(1000);
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);	  		  
/*
CREATE TYPE stphoto as(
	uri				text,	--name
	width			int,  --type
	height			int,
	viewAngle		float(4),			
	verticalAngle  	float(4),
	distance 	 	float(4),
	direction		float(4),
	direction3d		float(4),
	altitude		float(4),
	annotationJson	json,
	exifJson		json,
	loc				GEOMETRY(POINT, 4326),
	creationTime	bigint
);*/
		MPhoto mp = (MPhoto)reader.read("MPHOTO (('localhost:///tmp/drone/test1.jpg' 200 200 60 0 0.1 30 0 0 'annotation' 'exif' 100 100) 1481480632123, ('localhost:///tmp/drone/test1.jpg' 200 200 60 0 0.1 30 0 0 'annotation' 'exif' 101 101) 1481480634123), ('localhost:///tmp/drone/test1.jpg' 200 200 60 0 0.1 30 0 0 'annotation' 'exif' 102 102) 1481480637123)");
		MPhoto mp2 = (MPhoto)reader.read("MPHOTO (('localhost:///tmp/drone/test1.jpg' 200 200 60 0 0.1 30 0 0 'annotation' 'exif' 100 100) 1481480632123, ('localhost:///tmp/drone/test1.jpg' 200 200 60 0 0.1 30 0 0 'annotation' 'exif' 101 101) 1481480634123), ('localhost:///tmp/drone/test1.jpg' 200 200 60 0 0.1 30 0 0 'annotation' 'exif' 102 102) 1481480637123)");
		//---------------------------------------------------------------------	
		/*
		System.out.println(mp.toGeoString());
		System.out.println(mp.numOf());
		System.out.println(mp.snapshot(1481480635000l));
		System.out.println(mp.slice(1481480633000l, 1481480638000l).toGeoString());
		System.out.println(mp.atomize(1000).toGeoString());
		System.out.println("-----------------------");
		System.out.println(mp.lattice(1000).toGeoString());
		System.out.println(mp.first().toGeoString());
		System.out.println(mp.last().toGeoString());
		System.out.println(mp.at(2).toGeoString());
		*/
		//---------------------------------------------------------------------
		
		
		Geometry cood = mp.spatial();
		System.out.println(cood.getCoordinates()[0].x);	
		
		System.out.println(MovingDistance.distance(mp,mp2).toGeoString());
		mh = new MHausdorff();
		MHausdorff mh;
		mh = new MHausdorff();
		System.out.println(mh.measure(mp, mp2));
		
		}
}