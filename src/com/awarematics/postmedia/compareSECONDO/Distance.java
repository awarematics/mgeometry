package com.awarematics.postmedia.compareSECONDO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.operation.distance.DistanceOp;

import com.awarematics.postmedia.algorithms.distance.MovingDistance;
import com.awarematics.postmedia.io.MWKTReader;
import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.MBool;
import com.awarematics.postmedia.types.mediamodel.MDouble;
import com.awarematics.postmedia.types.mediamodel.MGeometry;
import com.awarematics.postmedia.types.mediamodel.MLineString;
import com.awarematics.postmedia.types.mediamodel.MPoint;
import com.awarematics.postmedia.types.mediamodel.MPolygon;

public class Distance {
	static MGeometryFactory geometryFactory = new MGeometryFactory();
	static MWKTReader reader = new MWKTReader(geometryFactory);

	static GeometryFactory geometry = new GeometryFactory();
	static WKTReader readers = new WKTReader(geometry);


	public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {

		File file = new File("d://mfs/mpolygon2" + ".json");// 000f157f-dab3a407
		// //000f8d37-d4c09a0f
		String content = FileUtils.readFileToString(file, "UTF-8");
		JSONObject jsonObject = new JSONObject(content);
		MPolygon mpolygon = null;
		MPoint mp = null;
		MPoint mp2 = null;
		MLineString mline = null;
		GeometryFactory geom = new GeometryFactory();
		WKTReader readers = new WKTReader(geom);
		try {
			Polygon[] pol;
			long[] timeArray;

			JSONArray getJsonArray = jsonObject.getJSONArray("mpolygon");
			int num = getJsonArray.length();
			pol = new Polygon[num];
			timeArray = new long[num];

			for (int j = 0; j < num; j++) {
				String[] array = getJsonArray.get(j).toString().split(":");
				String pst1 = array[1].split(",\\\"")[0].replaceAll("\\\"", "");
				String time = array[2].split(",\\\"")[0].replaceAll("\\}", "");
				time = time.replaceAll("\\\"", "");
				pol[j] = (Polygon) readers.read(pst1);
				timeArray[j] = (Long.parseLong(time));
			}
			mpolygon = geometryFactory.createMPolygon(pol, timeArray);
		} catch (Exception e) {

		}
		System.out.println(mpolygon.toGeoString());

		File file2 = new File("d://mfs/mpoint3" + ".json");// 000f157f-dab3a407
		// //000f8d37-d4c09a0f
		String content2 = FileUtils.readFileToString(file2, "UTF-8");
		JSONObject jsonObject2 = new JSONObject(content2);

		try {
			Coordinate[] pol2;
			long[] timeArray2;

			JSONArray getJsonArray = jsonObject2.getJSONArray("mpoint");
			int num = getJsonArray.length();
			pol2 = new Coordinate[num];
			timeArray2 = new long[num];

			for (int j = 0; j < num; j++) {
				String[] array = getJsonArray.get(j).toString().split(":");
				String x = array[1].split(",\\\"")[0].replaceAll("\\\"", "");
				String y = array[2].split(",\\\"")[0].replaceAll("\\}", "");
				String time2 = array[3].split(",\\\"")[0].replaceAll("\\}", "");
				time2 = time2.replaceAll("\\\"", "");

				pol2[j] = new Coordinate();
				pol2[j].x = Double.parseDouble(x);
				pol2[j].y = Double.parseDouble(y);
				timeArray2[j] = (Long.parseLong(time2));
			}
			mp = geometryFactory.createMPoint(pol2, timeArray2);
		} catch (Exception e) {

		}
		
		File file3 = new File("d://mfs/mpoint1" + ".json");// 000f157f-dab3a407
		// //000f8d37-d4c09a0f
		String content3 = FileUtils.readFileToString(file3, "UTF-8");
		JSONObject jsonObject3 = new JSONObject(content3);

		try {
			Coordinate[] pol2;
			long[] timeArray2;

			JSONArray getJsonArray = jsonObject3.getJSONArray("mpoint");
			int num = getJsonArray.length();
			pol2 = new Coordinate[num];
			timeArray2 = new long[num];

			for (int j = 0; j < num; j++) {
				String[] array = getJsonArray.get(j).toString().split(":");
				String x = array[1].split(",\\\"")[0].replaceAll("\\\"", "");
				String y = array[2].split(",\\\"")[0].replaceAll("\\}", "");
				String time2 = array[3].split(",\\\"")[0].replaceAll("\\}", "");
				time2 = time2.replaceAll("\\\"", "");

				pol2[j] = new Coordinate();
				pol2[j].x = Double.parseDouble(x);
				pol2[j].y = Double.parseDouble(y);
				timeArray2[j] = (Long.parseLong(time2));
			}
			mp2 = geometryFactory.createMPoint(pol2, timeArray2);
		} catch (Exception e) {

		}
		
		File file4 = new File("d://mfs/mlinestring2" + ".json");// 000f157f-dab3a407
		// //000f8d37-d4c09a0f
		String content4 = FileUtils.readFileToString(file4, "UTF-8");
		JSONObject jsonObject4 = new JSONObject(content4);

		try {
			LineString[] pol2;
			long[] timeArray2;

			JSONArray getJsonArray = jsonObject4.getJSONArray("mlinestring");
			int num = getJsonArray.length();
			pol2 = new LineString[num];
			timeArray2 = new long[num];

			for (int j = 0; j < num; j++) {
				String[] array = getJsonArray.get(j).toString().split(":");
				String pst2 = array[2].split(",\\\"")[0].replaceAll("\\\"", "");
				String time = array[1].split(",\\\"")[0].replaceAll("\\}", "");
				time = time.replaceAll("\\\"", "");
				pst2 = pst2.replaceAll("\\}", "");
				pol2[j] = (LineString) readers.read(pst2);
				timeArray2[j] = (Long.parseLong(time));
				//System.out.println(pst2);
			}
			mline = geometryFactory.createMLineString(pol2, timeArray2);
		} catch (Exception e) {

		}
		/*
		 * mpoint to mpolygon
		 */
		long startTime = System.currentTimeMillis();
		MDouble mb = distance(mp, mpolygon);
		System.out.println(mb.toGeoString());
		long endTime = System.currentTimeMillis();
		System.out.println("running time:  " + (endTime - startTime) + "ms");
		
		/*
		 * mpoint to mpoint
		 */
		//MDouble mb22 = distance(mp, mp2);
		//if(mb22 ==null) System.out.println("VVVVVVV");
		//else
		//System.out.println(mb22.toGeoString());	
		/*
		 * mpoint to mlinestring
		 */
		
	//	MDouble mb33 = distance(mp, mline);
	//	System.out.println(mb33.toGeoString());
		/*
		 * mpoint to point
		 */
		//40.76102639552276,"y":-73.98719290278292
		//Coordinate c = new Coordinate();
		//c.x= 40.76102639552276;
		//c.y = -73.98719290278292;
		//Point vp = new Point(c, null, 0);
		//System.out.println(distance(mp, vp).toGeoString());
		/*
		 * mpoint t0 polygon  POLYGON ((40.75988315166806 -73.98802529210175, 40.75988515166806 -73.98802529210175, 40.75988515166806 -73.98801929210174, 40.75988315166806 -73.98801929210174, 40.75988315166806 -73.98802529210175))
		 */
		//Polygon p = (Polygon) readers.read("POLYGON ((40.75988315166806 -73.98802529210175, 40.75988515166806 -73.98802529210175, 40.75988515166806 -73.98801929210174, 40.75988315166806 -73.98801929210174, 40.75988315166806 -73.98802529210175))");
		//System.out.println(p.toText());
		//System.out.println(distance(mp, p).toGeoString());
		/*
		 * mpoint to LineString LINESTRING (40.75988415166806 -73.98802229210175, 40.75988423548709 -73.98802019662595, 40.75988423548709 -73.98802019662595, 40.759891904928494 -73.9880197775308)
		 */
		//LineString linestring = (LineString) readers.read("LINESTRING (40.75988415166806 -73.98802229210175, 40.75988423548709 -73.98802019662595, 40.75988423548709 -73.98802019662595, 40.759891904928494 -73.9880197775308)");
		//System.out.println(distance(mp, linestring).toGeoString());
		
		long startTime2 = System.currentTimeMillis();
		MDouble mb2 = MovingDistance.distance(mp, mpolygon);
		if(mb2==null) System.out.println("fff");else
		System.out.println(mb2.toGeoString());
		long endTime2 = System.currentTimeMillis();
		System.out.println("running time:   " + (endTime2 - startTime2) + "ms");

	}

	private static MDouble distance(MPoint mp, Geometry vp) {
		
		double[] mdistance = new double[mp.numOf()];
		for(int i=0;i<mp.numOf();i++)
		{
			if(vp instanceof Point)
				mdistance[i] = d(vp.getCoordinate(), mp.getCoords()[i]);
			if(vp instanceof Polygon){
				Point p = new Point(mp.getCoords()[i], null, 0);
				Coordinate[] c = DistanceOp.nearestPoints(vp, p);
				mdistance[i] = d(c[0], mp.getCoords()[i]);
			}
			if(vp instanceof LineString)
			{
				Point p = new Point(mp.getCoords()[i], null, 0);
				Coordinate[] c = DistanceOp.nearestPoints(vp, p);
				mdistance[i] = d(c[0], mp.getCoords()[i]);
			}
		}
		return geometryFactory.createMDouble(mdistance, mp.getTimes());
	}

	public static MDouble distance(MGeometry mp, MGeometry mpolygon1) {

	/*	
		MBool mb = NotInside(mp, mpolygon1);
		// System.out.println(mb.numOf());
		if(mb !=null)
		{
		double[] mdistance = new double[mb.numOf()];

		for (int i = 0; i < mb.numOf(); i++) {
			if (mb.getBools()[i] == true) {
				// the distance is 0
				mdistance[i] = 0;
			} else {
				// step 1 get time interval
				// step 2 get time projection
				// step 3 get distance result
				if(mpolygon1 instanceof MPolygon){
				Coordinate[] c = DistanceOp.nearestPoints(mpolygon1.getListPolygon()[i], mp.snapshot(mb.getTimes()[i]));
				mdistance[i] = d(c[0], mp.snapshot(mb.getTimes()[i]).getCoordinates()[0]);
				}
				else if(mpolygon1 instanceof MPoint){
					Coordinate c = mpolygon1.getCoords()[i];
					mdistance[i] = d(c, mp.snapshot(mb.getTimes()[i]).getCoordinates()[0]);
				}
				else if(mpolygon1 instanceof MLineString)
				{
					LineString lines = ((MLineString) mpolygon1).getPoints()[i];
					Coordinate[] c = DistanceOp.nearestPoints(lines, mp.snapshot(mb.getTimes()[i]));
					mdistance[i] = d(c[0], mp.snapshot(mb.getTimes()[i]).getCoordinates()[0]);
					
				}
			}
		}
		return geometryFactory.createMDouble(mdistance, mb.getTimes());
		}
		else */return null;
	}

	private static MBool NotInside(MGeometry mg1, MGeometry mg2) {
		return at(mg1, mg2);
	}

	private static MBool at(MGeometry mg1, MGeometry mg2) {
		/*long overlappedStartTime = Math.max(mg1.getTimes()[0], mg2.getTimes()[0]);
		long overlappedEndTime = Math.min(mg1.getTimes()[mg1.numOf() - 1], mg2.getTimes()[mg2.numOf() - 1]);
		boolean[] tempBool = null ;
		long[] tempList = null;
		long[] doubletime = new long[mg1.numOf() +  mg2.numOf()];
		for( int i =0;i< mg1.numOf();i++)
			doubletime[i] = mg1.getTimes()[i];
		for( int i =0;i< mg2.numOf();i++)
			doubletime[i+mg2.numOf()] = mg2.getTimes()[i];
		
		ArrayList<Long> doubletimes = new ArrayList<Long>();
		int tnum;
		for(int i =0;i<doubletime.length;i++){
			tnum =0;
			for(int j =i+1; j<doubletime.length;j++)
			{
				if(doubletime[j] == doubletime[i]){
					tnum =1;
				}
			}
			if(tnum !=1){
				doubletimes.add(doubletime[i]);
			}
		}
		
		long[] addtime = doubletimes.stream().filter(i -> i != null).mapToLong(i -> i).toArray();
		System.out.println(addtime.length);
		ArrayList<Boolean> bool = new ArrayList<Boolean>();
		ArrayList<Long> time = new ArrayList<Long>();
		if (overlappedStartTime <= overlappedEndTime) {

			for (int i = 0; i < mg2.numOf(); i++) {
				if (mg2.getTimes()[i] >= overlappedStartTime && mg2.getTimes()[i] <= overlappedEndTime) {
					time.add(mg2.getTimes()[i]);
				}
			}
			HashSet<Long> hashset_temp = new HashSet<Long>(time);
			time = new ArrayList<Long>(hashset_temp);
			tempList = time.stream().filter(i -> i != null).mapToLong(i -> i).toArray();
			Arrays.sort(tempList);
			for (int j = 0; j < mg2.numOf(); j++) {
				if (mg2.getTimes()[j] >= overlappedStartTime && mg2.getTimes()[j] <= overlappedEndTime){
					if(mg2 instanceof MPolygon)
						bool.add(mg1.snapshot(mg2.getTimes()[j]).within(mg2.getListPolygon()[j]));
					else if(mg2 instanceof MPoint)
						bool.add(mg1.snapshot(mg2.getTimes()[j]).equals(mg2.getCoords()[j]));
					else {
						LineString lines = ((MLineString) mg2).getPoints()[j];
						bool.add(mg1.snapshot(mg2.getTimes()[j]).within(lines));
					}
				}
			}
			tempBool = new boolean[tempList.length];
			for (int i = 0; i < bool.size(); i++) {
				tempBool[i] = bool.get(i);
			}
			System.out.println(addtime.length); 
			MBool mb = geometryFactory.createMBool(tempBool, tempList);
			return mb;
		}
	
		//MBool mb = geometryFactory.createMBool(tempBool, addtime);
		//System.out.println("mb"+mb.toGeoString());*/
		return null;
	}

	public static double d(Coordinate p1, Coordinate p2) {
		double x1 = p1.x;
		double y1 = p1.y;
		double x2 = p2.x;
		double y2 = p2.y;
		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}
}
