package com.awarematics.postmedia.test;

import java.io.IOException;

import com.awarematics.postmedia.algorithms.similarity.MHausdorff;
import com.awarematics.postmedia.io.MWKTReader;
import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.MGeometry;
import com.awarematics.postmedia.types.mediamodel.MPoint;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
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
		String k = "[1180389003919,1190477866060)";
		
		Geometry g = (Geometry)reader2.read("LINESTRING(13.58725 52.52363,13.58725 52.52355,13.58726 52.52334,13.58726 52.52309,13.58727 52.52284,13.58727 52.52259,13.58728 52.52234,13.58728 52.52209,13.58729 52.52184,13.58729 52.52178,13.58741 52.52154,13.5875 52.52134,13.58762 52.5211,13.58774 52.52086,13.58785 52.52062,13.588 52.52038,13.58808 52.52014,13.5882 52.5199,13.58831 52.51967,13.58843 52.51943,13.58846 52.51937,13.58851 52.5192,13.58858 52.519,13.58865 52.51871,13.58872 52.51846,13.58878 52.51822,13.58885 52.518,13.58892 52.51772,13.58893 52.51769,13.589 52.51762,13.589 52.51741,13.58908 52.51716,13.58914 52.517,13.5892 52.51673,13.58927 52.51648,13.58934 52.51624,13.58941 52.516,13.58948 52.51575,13.58948 52.51574,13.58969 52.51553,13.5899 52.51531,13.5901 52.5151,13.59031 52.51488,13.59051 52.51467,13.59072 52.51445,13.59079 52.51437,13.59071 52.51432,13.59047 52.51416,13.5902 52.514,13.58992 52.51379,13.58964 52.51361,13.58945 52.51348,13.58917 52.5133,13.58888 52.51312,13.58868 52.513,13.5884 52.51282,13.58816 52.51267,13.58775 52.51268,13.58734 52.51268,13.58694 52.51268,13.58682 52.51268,13.58647 52.5127,13.58606 52.51271,13.5857 52.51273,13.58531 52.51274,13.585 52.51275,13.58473 52.51258,13.58452 52.51244,13.58465 52.51232,13.58477 52.51219,13.58485 52.51212,13.58509 52.51211,13.58534 52.51211,13.58558 52.5121,13.58583 52.5121,13.58607 52.51209,13.58632 52.51209,13.58655 52.51208,13.58674 52.51208,13.587 52.51208,13.587 52.51207,13.58693 52.512,13.58676 52.51191,13.5866 52.5118,13.58643 52.51169,13.58627 52.51158,13.5861 52.51147,13.58594 52.51136,13.58578 52.51124,13.58561 52.51113,13.58545 52.511,13.58533 52.51094,13.58517 52.51084,13.585 52.51073,13.58484 52.51061,13.58468 52.5105,13.58451 52.51039,13.58435 52.51028,13.58418 52.51017,13.584 52.51006,13.58385 52.50995,13.58369 52.50984,13.58352 52.50973,13.58336 52.50961,13.58325 52.50954,13.58308 52.50943,13.58292 52.50932,13.58275 52.50921,13.58259 52.5091,13.58258 52.50909,13.58239 52.509,13.58219 52.50891,13.582 52.50882,13.5818 52.50873,13.5816 52.50865,13.58146 52.50858,13.58135 52.50862,13.58114 52.50868,13.58091 52.50874,13.58069 52.50881,13.58047 52.50887,13.58025 52.50893,13.58 52.509,13.57981 52.50906,13.57959 52.50912,13.57936 52.50919,13.57914 52.50925,13.57892 52.50932,13.5787 52.50938,13.57858 52.50942)");
		
		
		System.out.println((M_EventTime(mp3.toGeoString(),mp3.toGeoString(),3)));	
		//Point point = geometryFactory2.createPoint(new Coordinate(mp3.spatial().getCoordinates()[0]));
		
		//System.out.println((point.toText()));	
	}
	
	public static long M_EventTime(String mgs1, String mgs2, double doubles)
			throws ParseException, org.locationtech.jts.io.ParseException, java.text.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		MGeometry mg2 = (MGeometry) reader.read(mgs2);
		GeometryFactory geometryFactory2 = new GeometryFactory();
		WKTReader reader2 = new WKTReader(geometryFactory2);
		Geometry geo2 = mg2.spatial();
		Geometry geo1 = mg1.spatial();
		if (geo1.isWithinDistance(geo2,doubles)) {
			for(int i =0;i<mg1.numOf();i++)
			{
				Point point = geometryFactory2.createPoint(new Coordinate(geo1.getCoordinates()[i]));
				if(point.isWithinDistance(geo2,doubles))
				{
					return (long)(mg1.getTimes()[i]);
				}
			}
		}
		
		return 0;
	}
}