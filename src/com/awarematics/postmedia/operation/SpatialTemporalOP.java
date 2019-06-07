package com.awarematics.postmedia.operation;

import java.text.ParseException;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.io.WKTReader;

import com.awarematics.postmedia.algorithms.distance.MovingDistance;
import com.awarematics.postmedia.algorithms.similarity.MHausdorff;
import com.awarematics.postmedia.algorithms.similarity.MLCSS;
import com.awarematics.postmedia.algorithms.similarity.MLCVS;
import com.awarematics.postmedia.algorithms.similarity.MTrajHaus;
import com.awarematics.postmedia.io.MWKTReader;
import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.MBool;
import com.awarematics.postmedia.types.mediamodel.MDouble;
import com.awarematics.postmedia.types.mediamodel.MGeometry;
//import org.postgresql.pljava.annotation.Function;

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
	public static String M_SnapToGrid(String mgstring, int n)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);
		String res = mg1.snapToGrid(n).toGeoString();
		return res;
	}

	// @Function
	public static String M_Intersects(String mgs1, String mgs2)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		if (mgs2.contains("M")) {
			MGeometry mg2 = (MGeometry) reader.read(mgs2);
			String res = mg1.intersects(mg1, mg2).toGeoString();
			return res;
		} else {
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
		}
	}

	// @Function
	public static boolean M_Enters(String mgs1, String mgs2)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		GeometryFactory geometryFactorys = new GeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		WKTReader readers = new WKTReader(geometryFactorys);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		Geometry mg2 = (Geometry) readers.read(mgs2);
		@SuppressWarnings("deprecation")
		MultiPoint mp = geometryFactorys.createMultiPoint(mg1.getCoords());
		if (mp.getGeometryN(0).disjoint(mg2) && mp.getGeometryN(mp.getNumGeometries() - 1).within(mg2))// disjoint--within
			return true;
		else
			return false;
	}

	// @Function
	public static boolean M_Bypasses(String mgs1, String mgs2)
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
	public static boolean M_Crosses(String mgs1, String mgs2)
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
	public static boolean M_Leaves(String mgs1, String mgs2)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		GeometryFactory geometryFactorys = new GeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		WKTReader readers = new WKTReader(geometryFactorys);
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		Geometry mg2 = (Geometry) readers.read(mgs2);
		@SuppressWarnings("deprecation")
		MultiPoint mp = geometryFactorys.createMultiPoint(mg1.getCoords());
		if (mp.getGeometryN(0).within(mg2) && mp.getGeometryN(mp.getNumGeometries() - 1).disjoint(mg2))// within--disjoint
			return true;
		else
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
	public static double M_AccelerationAtTimeTime(String mgs1, long instant)
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
		MGeometry mg1 = (MGeometry) reader.read(mgs1);
		MGeometry mg2 = (MGeometry) reader.read(mgs2);
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
	public static double M_MAX(String mgs) {
		String res = mgs.replace("\\(|\\)", "").replace("{", "").replace("}", "");
		String[] value = res.split(",");
		double max = -100000000;
		for (int i = 0; i < value.length; i++) {
			if (Double.parseDouble(value[i]) > max)
				max = Double.parseDouble(value[i]);
		}
		return max;
	}

	// @Function
	public static double M_MIN(String mgs) {
		String res = mgs.replace("\\(|\\)", "").replace("{", "").replace("}", "");
		String[] value = res.split(",");
		double min = 100000000;
		for (int i = 0; i < value.length; i++) {
			if (Double.parseDouble(value[i]) < min)
				min = Double.parseDouble(value[i]);
		}
		return min;
	}

	// @Function
	public static double M_AVG(String mgs) {
		String res = mgs.replace("\\(|\\)", "").replace("{", "").replace("}", "");
		String[] value = res.split(",");
		double avg = 0;
		for (int i = 0; i < value.length; i++) {
			avg = avg + Double.parseDouble(value[i]);
		}
		avg = avg / value.length;
		return avg;
	}

	// @Function
	public static boolean M_DWithin(String mgs1, String mgs2, double within)
			throws ParseException, org.locationtech.jts.io.ParseException {
		String mb = M_Distance(mgs1, mgs2);//time, 
		if(mb.equals("NULL"))
		{
			return false;
		}
		else
		{
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MDouble mg1 = (MDouble) reader.read(mb);
		for (int i = 0; i < mg1.numOf(); i++) {
			if (mg1.getValue()[i] < within)
				return true;
		}
		}
		return false;
	}

}
