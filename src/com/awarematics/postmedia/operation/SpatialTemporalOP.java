package com.awarematics.postmedia.operation;

import java.text.ParseException;
import java.util.Arrays;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.WKTReader;

import com.awarematics.postmedia.algorithms.distance.MovingDistance;
import com.awarematics.postmedia.algorithms.similarity.MHausdorff;
import com.awarematics.postmedia.algorithms.similarity.MLCSS;
import com.awarematics.postmedia.algorithms.similarity.MLCVS;
import com.awarematics.postmedia.algorithms.similarity.MTrajHaus;
import com.awarematics.postmedia.io.MWKTReader;
import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.FoV;
import com.awarematics.postmedia.types.mediamodel.MBool;
import com.awarematics.postmedia.types.mediamodel.MDouble;
import com.awarematics.postmedia.types.mediamodel.MGeometry;
//import org.postgresql.pljava.annotation.Function;
import com.awarematics.postmedia.types.mediamodel.MVideo;



/*
 * 测试mpoint 
 * index函数
 * 测试所有函数的返回结果
 * big----small
 * distance 确认
 */
public class SpatialTemporalOP {
	// @Function
	public static long M_TimeAtCummulative(String mgstring, double distance)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);
		long res = mg1.timeAtCummulativeDistance(distance);
		return res;
	}
	
	
	// @Function
	public double timeAtCummulativeDistance(String mgstring) throws ParseException, org.locationtech.jts.io.ParseException{
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);

		double res =0;
		for (int i = 1; i < mg1.numOf()-1; i++) {
			res = res + mg1.calDistance(mg1.getCoords()[i], mg1.getCoords()[i + 1]);
		}
		System.out.println(res);		
		return res;
	}

	
	
	
	
	// @Function
	public static String M_SnapToGrid(String mgstring, int n)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);
		String res = mg1.snapToGrid(n).toGeoString();
		return res;
	}

	// @Function
		public static String M_BBox(String mgstring)
				throws ParseException, org.locationtech.jts.io.ParseException {
			MGeometryFactory geometryFactory = new MGeometryFactory();
			MWKTReader reader = new MWKTReader(geometryFactory);
			MGeometry mg1 = (MGeometry) reader.read(mgstring);
			String res = mg1.bbox().toText();
			return res;
		}
	// @Function
	public static boolean M_mIntersects(String mgs1, String mgs2)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		GeometryFactory geometry = new GeometryFactory();
		WKTReader read = new WKTReader(geometry);
		Geometry g2 = (Geometry) read.read(mgs2);
		int numof = 0;
		for (int i = 0; i < mg1.numOf(); i++) {
			if (mg1.snapshot(mg1.getTimes()[i]).intersects(g2))
				numof = 1;
		}
		if (numof == 1)
			return true;
		else
			return false;
	}

	
	// @Function
	public static String M_Intersects(String mgs1, String mgs2)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		if (mgs2.contains("M")) {
			MGeometry mg1 = (MGeometry) reader.read(mgs1);
			MGeometry mg2 = (MGeometry) reader.read(mgs2);
			String res = mg1.intersects(mg1, mg2).toGeoString();
			return res;
		} else if (mgs1.contains("M")) {
			MGeometry mg1 = (MGeometry) reader.read(mgs1);
			GeometryFactory geometry = new GeometryFactory();
			WKTReader read = new WKTReader(geometry);
			Geometry g2 = (Geometry) read.read(mgs2);
			boolean[] mb = new boolean[mg1.numOf()];
			long[] times = mg1.getTimes();
			for (int i = 0; i < mg1.numOf(); i++)
				if (mg1.snapshot(mg1.getTimes()[i]).intersects(g2))
					mb[i] = true;
				else
					mb[i] = false;
			MBool res = geometryFactory.createMBool(mb, times);
			return res.toGeoString();
		} else {
			Polygon[] mp1 = createPolygon(mgs1);
			Polygon[] mp2 = createPolygon(mgs2);
			boolean[] mb = new boolean[mp1.length];
			long[] times = new long[mp1.length];
			int num = 0;
			for (int i = 0; i < mp1.length; i++) {
				num = 0;
				for (int j = 0; j < mp2.length; j++) {
					if (mp1[i].intersects(mp2[j]))
						num = 1;
				}
				if (num == 1)
					mb[i] = true;
				else
					mb[i] = false;
				times[i] = -1;
			}
			return geometryFactory.createMBool(mb, times).toGeoString();
		}
	}

	// @Function
	public static String M_Inside(String mgs1, String mgs2)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		MGeometry mg2 = (MGeometry) reader.read(mgs2);
		if (mg1.inside(mg1, mg2).equals("NULL")) {
			return "MBOOL ()";
		} else {
			String res = mg1.inside(mg1, mg2).toGeoString();
			return res;
		}
	}

	// @Function
	public static String M_Overlaps(String mgs1, String mgs2)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		MGeometry mg2 = (MGeometry) reader.read(mgs2);
		if (mg1.overlaps(mg1, mg2).equals("NULL")) {
			return "MBOOL ()";
		} else {
			String res = mg1.overlaps(mg1, mg2).toGeoString();
			return res;
		}
	}

	// @Function
	public static String M_Equal(String mgs1, String mgs2)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		MGeometry mg2 = (MGeometry) reader.read(mgs2);
		if (mg1.equal(mg1, mg2).equals("NULL")) {
			return "MBOOL ()";
		} else {
			String res = mg1.equal(mg1, mg2).toGeoString();
			return res;
		}
	}

	// @Function
	public static String M_Disjoint(String mgs1, String mgs2)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		MGeometry mg2 = (MGeometry) reader.read(mgs2);
		if (mg1.disjoint(mg1, mg2).equals("NULL")) {
			return "MBOOL ()";
		} else {
			String res = mg1.disjoint(mg1, mg2).toGeoString();
			return res;
		}
	}

	// @Function
	public static String M_Meet(String mgs1, String mgs2)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		MGeometry mg2 = (MGeometry) reader.read(mgs2);
		if (mg1.meet(mg1, mg2).equals("NULL")) {
			return "MBOOL ()";
		} else {
			String res = mg1.meet(mg1, mg2).toGeoString();
			return res;
		}
	}

	public static Polygon[] createPolygon(String mgs) {
		mgs = mgs.replaceAll("\\(|\\)", "");
		mgs = mgs.replaceAll("\\{|\\}", "");
		String[] fovs = mgs.split("\",\"");
		Polygon[] pl = new Polygon[fovs.length];
		for (int i = 0; i < fovs.length; i++) {
			String temp = fovs[i].replaceAll("\\\\", "");
			temp = temp.replaceAll("\"", "");
			String[] level = temp.split(",");
			FoV fov = new FoV();
			fov.setDirection2d(Double.parseDouble(level[3]));
			fov.setDistance(Double.parseDouble(level[4]));
			fov.setHorizontalAngle(Double.parseDouble(level[2]));
			pl[i] = genFoVArea(Double.valueOf(level[0]), Double.valueOf(level[1]), fov);
		}
		return pl;
	}

	public static Polygon genFoVArea(double x, double y, FoV fov) {
		int times = 1;
		double x4 = (double) x;
		double y4 = (double) y;

		double x2 = (double) x + fov.getDistance() * 2 / Math.sqrt(3)
				* Math.sin(Math.toRadians(fov.getDirection2d() + (fov.getHorizontalAngle()) / 2)) * times;
		double y2 = (double) y + fov.getDistance() * 2 / Math.sqrt(3)
				* Math.cos(Math.toRadians(fov.getDirection2d() + (fov.getHorizontalAngle()) / 2)) * times;// left
		double x3 = (double) x + fov.getDistance() * 2 / Math.sqrt(3)
				* Math.sin(Math.toRadians(fov.getDirection2d() - (fov.getHorizontalAngle()) / 2)) * times;
		double y3 = (double) y + fov.getDistance() * 2 / Math.sqrt(3)
				* Math.cos(Math.toRadians(fov.getDirection2d() - (fov.getHorizontalAngle()) / 2)) * times;// right
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

	// @Function
	public static boolean M_mEnters(String mgs1, String mgs2)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		GeometryFactory geometryFactorys = new GeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		WKTReader readers = new WKTReader(geometryFactorys);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		Geometry mg2 = (Geometry) readers.read(mgs2);
		@SuppressWarnings("deprecation")
		LineString mp = geometryFactorys.createLineString(mg1.getCoords());
		if (mp.touches(mg2))
			return true;
		else
			return false;
		
	}

	// @Function
	public static boolean M_mBypasses(String mgs1, String mgs2)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		GeometryFactory geometryFactorys = new GeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		WKTReader readers = new WKTReader(geometryFactorys);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		Geometry mg2 = (Geometry) readers.read(mgs2);
		if (mg1.spatial().touches(mg2))//
			return true;
		else
			return false;
	}
	
	// @Function
		public static boolean M_passes(String mgs1, String mgs2)
				throws ParseException, org.locationtech.jts.io.ParseException {
			MGeometryFactory geometryFactory = new MGeometryFactory();
			GeometryFactory geometryFactorys = new GeometryFactory();
			MWKTReader reader = new MWKTReader(geometryFactory);
			WKTReader readers = new WKTReader(geometryFactorys);
			MGeometry mg1 = (MGeometry) reader.read(mgs1);
			Geometry mg2 = (Geometry) readers.read(mgs2);
			if (mg2.within(mg1.spatial()))//
				return true;
			else
				return false;
		}

	// @Function
	public static boolean M_mCrosses(String mgs1, String mgs2)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		GeometryFactory geometryFactorys = new GeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		WKTReader readers = new WKTReader(geometryFactorys);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		Geometry mg2 = (Geometry) readers.read(mgs2);
		if (mg1.spatial().crosses(mg2))//
			return true;
		else
			return false;
	}


	// @Function
	public static boolean M_mLeaves(String mgs1, String mgs2)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		GeometryFactory geometryFactorys = new GeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		WKTReader readers = new WKTReader(geometryFactorys);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		Geometry mg2 = (Geometry) readers.read(mgs2);
		@SuppressWarnings("deprecation")
		LineString mp = geometryFactorys.createLineString(mg1.getCoords());
		if (mp.touches(mg2))
			return true;
		else
			return false;
	}

	// @Function
	public static boolean M_mStayIn(String mgs1, String mgs2)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		GeometryFactory geometryFactorys = new GeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		WKTReader readers = new WKTReader(geometryFactorys);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		Geometry mg2 = (Geometry) readers.read(mgs2);
		@SuppressWarnings("deprecation")
		MultiPoint mp = geometryFactorys.createMultiPoint(mg1.getCoords());
		for (int i = 0; i < mp.getNumGeometries(); i++) {
			if (mp.getGeometryN(i).within(mg2))// within
				return true;
		}
		return false;
	}

	// @Function
	public static String M_EventTime(String mgs1, String mgs2)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		MGeometry mg2 = (MGeometry) reader.read(mgs2);
		if (mg1.startTime() > mg2.endTime() || mg2.startTime() > mg1.endTime()) {
			return ("NULL");
		}
		String res = mg1.eventTime(mg1, mg2).toGeoString();
		return res;
	}
	
	
	
	// @Function
		public static long m_eventPosition(String mgs1, String mgs2)
				throws ParseException, org.locationtech.jts.io.ParseException {
			MGeometryFactory geometryFactory = new MGeometryFactory();
			MWKTReader reader = new MWKTReader(geometryFactory);
			MGeometry mg1 = (MGeometry) reader.read(mgs1);
			String searchstring = mgs2.replace("POINT(", "");
			searchstring = searchstring.replace(")", "");
			searchstring = searchstring.replace(" ", ", ");
			searchstring = "("+searchstring + ", NaN)";
			System.out.println(searchstring); 
			String[] arrays = new String[mg1.getCoords().length];
			for(int i=0;i<arrays.length;i++)
				arrays[i] = String.valueOf(mg1.getCoords()[i]);
			int position = Arrays.binarySearch(arrays, searchstring);
			System.out.println(position); 
			if (position <= 0)
				return -1;
			else return mg1.getTimes()[position];
		}

	// @Function
		public static String M_EventTime(String mgs1, String mgs2, double distance)
				throws ParseException, org.locationtech.jts.io.ParseException {
			MGeometryFactory geometryFactory = new MGeometryFactory();
			MWKTReader reader = new MWKTReader(geometryFactory);
			MGeometry mg1 = (MGeometry) reader.read(mgs1);
			MGeometry mg2 = (MGeometry) reader.read(mgs2);
			if (mg1.startTime() > mg2.endTime() || mg2.startTime() > mg1.endTime()) {
				return ("NULL");
			}
			String res = mg1.eventTime(mg1, mg2, distance).toGeoString();
			return res;
		}
		
		// @Function
				public static String M_EventPosition(String mgs1, String mgs2, double distance)
						throws ParseException, org.locationtech.jts.io.ParseException {
					MGeometryFactory geometryFactory = new MGeometryFactory();
					MWKTReader reader = new MWKTReader(geometryFactory);
					MGeometry mg1 = (MGeometry) reader.read(mgs1);
					MGeometry mg2 = (MGeometry) reader.read(mgs2);
					if (mg1.startTime() > mg2.endTime() || mg2.startTime() > mg1.endTime()) {
						return ("NULL");
					}
					String res = mg1.eventPosition(mg1, mg2, distance).toGeoString();
					return res;
				}
	// @Function
	public static String M_Relationship(String mgs1, String mgs2)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		MGeometry mg2 = (MGeometry) reader.read(mgs2);
		String res = mg1.relationship(mg1, mg2).toGeoString();
		return res;
	}

	// @Function
	public static String M_Distance(String mgs1, String mgs2)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		if (mgs2.contains("M")) {
			MGeometry mg2 = (MGeometry) reader.read(mgs2);
			if (mg1.startTime() > mg2.endTime() || mg2.startTime() > mg1.endTime()) {
				return ("NULL");
			}
			String res = MovingDistance.distance(mg1, mg2).toGeoString();
			return res;
		} else {
			GeometryFactory geometry = new GeometryFactory();
			WKTReader read = new WKTReader(geometry);
			Geometry g2 = (Geometry) read.read(mgs2);
			double[] doubles = new double[mg1.numOf()];
			long[] times = mg1.getTimes();
			for (int i = 0; i < mg1.numOf(); i++)
				doubles[i] = mg1.snapshot(mg1.getTimes()[i]).distance(g2);
			MDouble res = geometryFactory.createMDouble(doubles, times);
			return res.toGeoString();
		}
	}

	// @Function
	public static String M_Area(String mgs1) throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		String res = mg1.area().toGeoString();
		return res;
	}

	// @Function
	public static String M_Direction(String mgs1) throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		String res = mg1.direction().toGeoString();
		return res;
	}

	// @Function
	public static double M_VelocityAtTime(String mgs1, long instant)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		if (mg1.startTime() > instant || mg1.endTime() < instant)
			return -1;
		double res = mg1.veolocityAtTimeTime(instant);
		return res;
	}

	// @Function
	public static double M_AccelerationAtTime(String mgs1, long instant)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		if (mg1.startTime() > instant || mg1.endTime() < instant)
			return -1;
		double res = mg1.accelerationAtTimeTime(instant);
		return res;
	}

	// @Function
	public static double M_Hausdorff(String mgs1, String mgs2)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		MGeometry mg2 = (MGeometry) reader.read(mgs2);
		MHausdorff mhaus = new MHausdorff();
		double res = mhaus.measure(mg1, mg2);
		return res;
	}

	// @Function
	public static double M_LCSS(String mgs1, String mgs2, double epsilon, double omega)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		MGeometry mg2 = (MGeometry) reader.read(mgs2);
		MLCSS mhaus = new MLCSS();
		double res = mhaus.similarity(mg1, mg2, epsilon, omega);
		return res;
	}

	// @Function
	public static double M_Traclus(String mgs1, String mgs2)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		MGeometry mg2 = (MGeometry) reader.read(mgs2);
		MTrajHaus mhaus = new MTrajHaus();
		double res = mhaus.calculate(mg1, mg2);
		return res;
	}

	// @Function
	public static double M_LCVS(String mgs1, String mgs2, double delta)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MVideo mg1 = (MVideo) reader.read(mgs1);
		MVideo mg2 = (MVideo) reader.read(mgs2);
		MLCVS mhaus = new MLCVS();
		double res = mhaus.similarity(mg1, mg2, delta);
		return res;
	}

	// @Function
	public static boolean M_ANY(String mgs1) {
		if (mgs1.contains("t"))
			return true;
		else
			return false;
	}

	// @Function
	public static double M_MAX(String mgs) throws ParseException, org.locationtech.jts.io.ParseException {
		if (mgs.contains("MDOUBLE")) {
			MGeometryFactory geometryFactory = new MGeometryFactory();
			MWKTReader reader = new MWKTReader(geometryFactory);
			MDouble mg1 = (MDouble) reader.read(mgs);
			double max = -1;
			for (int i = 0; i < mg1.numOf(); i++) {
				if ((mg1.getValue()[i]) > max)
					max = (mg1.getValue()[i]);
			}
			return max;
		} else {
			String res = mgs.replace("\\(|\\)", "").replace("{", "").replace("}", "");
			String[] value = res.split(",");
			double max = -1;
			for (int i = 0; i < value.length; i++) {
				if (Double.parseDouble(value[i]) > max)
					max = Double.parseDouble(value[i]);
			}
			return max;
		}
	}

	// @Function
	public static double M_MIN(String mgs) throws ParseException, org.locationtech.jts.io.ParseException {
		if (mgs.contains("MDOUBLE")) {
			MGeometryFactory geometryFactory = new MGeometryFactory();
			MWKTReader reader = new MWKTReader(geometryFactory);
			MDouble mg1 = (MDouble) reader.read(mgs);
			double min = 0x1.0p-1022;
						//"1503828255000"
			for (int i = 0; i < mg1.numOf(); i++) {
				if ((mg1.getValue()[i]) < min)
					min = (mg1.getValue()[i]);
			}
			return min;
		} else {
			String res = mgs.replace("\\(|\\)", "").replace("{", "").replace("}", "");
			String[] value = res.split(",");
			double min = 0x1.0p-1022;
			for (int i = 0; i < value.length; i++) {
				if (Double.parseDouble(value[i]) < min)
					min = Double.parseDouble(value[i]);
			}
			return min;
		}
	}

	// @Function
	public static double M_AVG(String mgs) throws ParseException, org.locationtech.jts.io.ParseException {
		if (mgs.contains("MDOUBLE")) {
			MGeometryFactory geometryFactory = new MGeometryFactory();
			MWKTReader reader = new MWKTReader(geometryFactory);
			MDouble mg1 = (MDouble) reader.read(mgs);
			double avg = 0;
			for (int i = 0; i < mg1.numOf(); i++) {
				avg = avg + mg1.getValue()[i];
			}
			avg = avg / mg1.numOf();
			return avg;
		} else {
			String res = mgs.replace("\\(|\\)", "").replace("{", "").replace("}", "");
			String[] value = res.split(",");
			double avg = 0;
			for (int i = 0; i < value.length; i++) {
				avg = avg + Double.parseDouble(value[i]);
			}
			avg = avg / value.length;
			return avg;
		}
	}

	// @Function
	public static boolean M_DWithin(String mgs1, String mgs2, double withins)
			throws ParseException, org.locationtech.jts.io.ParseException {
		String mb = M_Distance(mgs1, mgs2);// time,
		if (mb.equals("NULL")) {
			return false;
		} else {
			MGeometryFactory geometryFactory = new MGeometryFactory();
			MWKTReader reader = new MWKTReader(geometryFactory);
			MDouble mg1 = (MDouble) reader.read(mb);
			for (int i = 0; i < mg1.numOf(); i++) {
				if (mg1.getValue()[i] < withins)
					return true;
			}
		}
		return false;
	}

}
