package com.awarematics.postmedia.test;

import java.io.IOException;
import java.text.ParseException;
import com.awarematics.postmedia.io.MWKTReader;
import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.MBool;
import com.awarematics.postmedia.types.mediamodel.MDouble;
import com.awarematics.postmedia.types.mediamodel.MGeometry;
import com.awarematics.postmedia.types.mediamodel.MInt;
import com.awarematics.postmedia.types.mediamodel.MLineString;
import com.awarematics.postmedia.types.mediamodel.MMultiPoint;
import com.awarematics.postmedia.types.mediamodel.MPhoto;
import com.awarematics.postmedia.types.mediamodel.MPoint;
import com.awarematics.postmedia.types.mediamodel.MPolygon;
import com.awarematics.postmedia.types.mediamodel.MString;
import com.awarematics.postmedia.types.mediamodel.MVideo;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.WKTReader;

@SuppressWarnings("unused")
public class MWKTReaderTest {

	// private static MPointDistance MPhotoDistance;

	/**
	 * @param args
	 * @throws ParseException
	 * 
	 * @throws IOException
	 * @throws org.locationtech.jts.io.ParseException
	 * @throws org.locationtech.jts.io.ParseException 
	 */
	public static void main(String[] args)
			throws IOException, ParseException, org.locationtech.jts.io.ParseException{

		  //PrecisionModel precisionModel = new PrecisionModel(1000);
		  MGeometryFactory geometryFactory = new MGeometryFactory();
		  MWKTReader reader = new MWKTReader(geometryFactory);	  		  
		/*
		 * MPoint  moving point -----> gps
		 * MPhoto  moving photo -----> exif()
		 */
		  

		//MPoint mk = (MPoint)reader.read("MPOINT ((0 0) 1481480632123, (2 5) 1481480637123, (34 333) 1481480638000, (0 0) 1481480639123, (3 3) 1481480641123, (333 333) 1481480642556, (0 0) 1481480643123, (2 7) 1481480644123, (333 333) 1481480645556)");
		
		//System.out.println(mk.toGeoString()); // OK
		
		
		//System.out.println(mk.numOf());  // OK
		
		//long queryTime = 1481480638000L;
		//Point p = (Point)mk.snapshot(queryTime);

		//System.out.println(p.toText());
		

		//long x = 1000;
		//System.out.println(mk.atomize( x).toGeoString());  //return    each point by x times decade
		//System.out.println(mk.lattice( x).toGeoString());  //return    each point nearest time by x times decade 
		
		//System.out.println(mk.getDuration());
		
		//long start = 1481480635000L;
		//long end = 1481480645000L;
		//System.out.println(mk.slice(start, end).toGeoString());
		//System.out.println("--------------------------------");
		
		/* int n=5;
		 System.out.println(mp.first(mp));
		 System.out.println(mp.last(mp));
		 System.out.println(mp.at(mp,n));
		 System.out.println(mp.numOf(mp));
		
	/*	v 
		
		MLineString ml = new MLineString ("MLINESTRING (0 0 2016-12-12 03:23:52.000, 2 5 2016-12-12 03:23:57.123)");
		System.out.println(ml.toGeoString());
		
		
		MDouble md = (MDouble) reader.read("MDOUBLE (39.1 1481480632000, 34.3 1481480633000, 41.2 1481480635000)" );
		System.out.println(md.toGeoString());
		MBool mb = (MBool)reader.read( "MBOOL (true 1481480632000, false 1481480633000, false 1481480634000, true 1481480635000)");//judge  time
		System.out.println(mb.toGeoString());
		MInt mi = (MInt)reader.read( "MINT (10 1481480632000, 15 1481480633000, 20 1481480634000, 17 1481480636000)");  // number  time 
		System.out.println(mi.toGeoString());
		MString ms = (MString)reader.read("MSTRING ('Augustus' 1181480638000, 'Nero' 1431480638000, 'Constantine' 1981480638000)");// person   time
		System.out.println(ms.toGeoString());
*/
		//(uri, hangle, vangle, distance, direction, attitude, x, y),time
	/*	MPhoto  mphoto = (MPhoto)reader.read( "MPHOTO (('https://github.com/awarematics/mgeometry/test.jpg' 120 120 30 3 200 100 100) 1981480638000, ('https://github.com/awarematics/mgeometry/test2.jpg' 120 120 30 3 100 101 101) 1981480639000)");
		System.out.println(mphoto.toGeoString());
		*/MVideo  mv = (MVideo)reader.read( "MVIDEO (('https://github.com/awarematics/mgeometry/test.jpg' 120 120 30 3 200 100 100) 1981480638000, ('https://github.com/awarematics/mgeometry/test2.jpg' 120 120 30 3 100 101 101) 1981480639000)");
		System.out.println(mv.toGeoString());
		
	/*	MMultiPoint mmp = (MMultiPoint)reader.read("MMULTIPOINT (((3 6) 1481480632000, (111 333) 1481480633000, (0 2) 1481480634000, (2 7) 1481480635000, (33 93) 1481480636000), ((3 6) 1481480632000, (111 333) 1481480633000, (1 3) 1481480634000, (3 8) 1481480635000, (30 93) 1481480636000))") ;// (MPoint1), (MPoint2)
		System.out.println(mmp.toGeoString());
		
		MLineString ml = (MLineString)reader.read( "MLINESTRING ((10 10, 20 20, 30 40) 1481480635000, (10 20, 20 20, 21 21, 30 40) 1481480636000, (10 30, 20 20, 21 21, 30 50) 1481480637000)");// point  time, point time// To be implemented		
		System.out.println(ml.toGeoString());
		MPolygon mpol = (MPolygon)reader.read( "MPOLYGON ((10 10, 10 20, 20 20, 20 15, 10 10) 1481480635000, (10 30, 10 20, 20 30, 20 15, 10 30) 1481480635000, (10 20, 10 20, 20 20, 20 15, 10 20) 1481480635000, (20 10, 10 20, 20 30, 20 15, 10 50, 20 10) 1481480635000)");  // To be implemented*/
	/*	System.out.println(mpol.toGeoString());
		
		
		 GeometryFactory geometryFactorys = new GeometryFactory(precisionModel, 0);
		 WKTReader readers = new WKTReader( geometryFactorys );
		LineString geometry1 = (LineString) readers.read("LINESTRING(0 0, 2 0, 5 0)");
        LineString geometry2 = (LineString) readers.read("LINESTRING(0 0, 0 2, 5 0)");
        Geometry interPoint = geometry1.intersection(geometry2);
        System.out.println(interPoint.getNumPoints());
        Coordinate[] pp = interPoint.getCoordinates();
        for(int i=0;i< pp.length;i++)
        	System.out.println(pp[i].x+"\t"+pp[i].y);
        System.out.println(interPoint.toText());*/// POINT (0 0)  or MULTIPOINT ((0 0), (5 0))
	}

}