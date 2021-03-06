package com.awarematics.postmedia.operation;

import java.text.ParseException;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.WKTReader;

import com.awarematics.postmedia.io.MWKTReader;
import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.MDouble;
//import org.postgresql.pljava.annotation.Function;
import com.awarematics.postmedia.types.mediamodel.MGeometry;
import com.awarematics.postmedia.types.mediamodel.MPoint;
import com.awarematics.postmedia.types.mediamodel.MVideo;

public class TemporalOP {

	// @Function
	public static boolean M_tOverlaps(String mgstring, String periodstring)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);
		String from = periodstring.split(", ")[0].replace("Period (", "");
		String to = periodstring.split(", ")[1].replace(")", "");
		if (mg1.startTime() >= Long.parseLong(from) && mg1.endTime() <= Long.parseLong(to))
			return true;
		return false; 
	}

	// @Function
	public static boolean M_tContains(String mgstring, String periodstring)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);
		String from = periodstring.split(", ")[0].replace("Period (", "");
		String to = periodstring.split(", ")[1].replace(")", "");
		if (mg1.startTime() <= Long.parseLong(from) && mg1.endTime() >= Long.parseLong(to))
			return true;
		return false;
	}
	// @Function
		public static boolean M_tIntersects(String mgstring, String periodstring)
				throws ParseException, org.locationtech.jts.io.ParseException {
			MGeometryFactory geometryFactory = new MGeometryFactory();
			MWKTReader reader = new MWKTReader(geometryFactory);
			MGeometry mg1 = (MGeometry) reader.read(mgstring);
			String from = periodstring.split(", ")[0].replace("Period (", "");
			String to = periodstring.split(", ")[1].replace(")", "");
			if (mg1.startTime() <= Long.parseLong(from) && mg1.endTime() >= Long.parseLong(from) && mg1.startTime() <= Long.parseLong(to) && mg1.endTime() <= Long.parseLong(to)||
					mg1.startTime() <= Long.parseLong(to) && mg1.endTime() >= Long.parseLong(to) && mg1.startTime() >= Long.parseLong(from) && mg1.endTime() >= Long.parseLong(from))
				return true;
			return false;
		}
	
	// @Function
	public static boolean M_tEquals(String mgstring, String periodstring)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);
		String from = periodstring.split(", ")[0].replace("Period (", "");
		String to = periodstring.split(", ")[1].replace(")", "");
		if (mg1.startTime() == Long.parseLong(from) && mg1.endTime() == Long.parseLong(to))
			return true;
		return false;
	}

	// @Function
	public static boolean M_tImmPrecedes(String mgstring, String periodstring)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);
		String from = periodstring.split(", ")[0].replace("Period (", "");
		if (mg1.startTime() <= Long.parseLong(from) && mg1.endTime() >= Long.parseLong(from))
			return true;
		return false;
	}

	// @Function
	public static boolean M_tImmSucceeds(String mgstring, String periodstring)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);
		String to = periodstring.split(", ")[1].replace(")", "");
		if (mg1.startTime() <= Long.parseLong(to) && mg1.endTime() >= Long.parseLong(to))
			return true;
		return false;
	}

	// @Function
	public static boolean M_tPrecedes(String mgstring, String periodstring)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);
		String from = periodstring.split(", ")[0].replace("Period (", "");
		if (mg1.endTime() < Long.parseLong(from))
			return true;
		return false;
	}

	// @Function
	public static boolean M_tSucceeds(String mgstring, String periodstring)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);
		String to = periodstring.split(", ")[1].replace(")", "");
		if (mg1.startTime() > Long.parseLong(to))
			return true;
		return false;
	}

	// @Function
	public static String M_Slice(String mgstring, String periodstring)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		GeometryFactory geometry = new GeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		WKTReader readers = new WKTReader(geometry);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);
		if (periodstring.contains("Period")) {
			String from = periodstring.split(", ")[0].replace("Period (", "");
			String to = periodstring.split(", ")[1].replace(")", "");
			if (mg1.slice(Long.parseLong(from), Long.parseLong(to)) == null) {
				if (mg1 instanceof MVideo)
					return "MVIDEO ()";
				if (mg1 instanceof MPoint)
					return "MPOINT ()";
				if (mg1 instanceof MDouble)
					return "MDOUBLE ()";
			} else
				return mg1.slice(Long.parseLong(from), Long.parseLong(to)).toGeoString();
		} else {
			Polygon mpol = (Polygon) readers.read(periodstring);
			if (mg1.slice(mpol) == null) {
				if (mg1 instanceof MVideo)
					return "MVIDEO ()";
				if (mg1 instanceof MPoint)
					return "MPOINT ()";
				if (mg1 instanceof MDouble)
					return "MDOUBLE ()";
			} else
				return mg1.slice(mpol).toGeoString();
		}
		return null;
	}

	// @Function
	public static String M_Slice(String mgstring, String periodstring, String polygonstring)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		GeometryFactory geometry = new GeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		WKTReader readers = new WKTReader(geometry);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);
		MGeometry mg2 = null;
		MGeometry mg3 = null;
		String from = periodstring.split(", ")[0].replace("Period (", "");
		String to = periodstring.split(", ")[1].replace(")", "");
		if (mg1.slice(Long.parseLong(from), Long.parseLong(to)) == null) {
			if (mg1 instanceof MVideo)
				return "MVIDEO ()";
			if (mg1 instanceof MPoint)
				return "MPOINT ()";
			if (mg1 instanceof MDouble)
				return "MDOUBLE ()";
		} else
			mg2 =  mg1.slice(Long.parseLong(from), Long.parseLong(to));
		System.out.println(mg2.toGeoString());
		Polygon mpol = (Polygon) readers.read(polygonstring);
		if (mg2.slice(mpol) == null) {
			System.out.println("llll");
			if (mg2 instanceof MVideo)
				return "MVIDEO ()";
			if (mg2 instanceof MPoint)
				return "MPOINT ()";
			if (mg2 instanceof MDouble)
				return "MDOUBLE ()";
		} else{
			mg3 = mg2.slice(mpol);}
		return mg3.toGeoString();
	}

	// @Function
	public static String M_At(String mgstring, int n) throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);
		if (n > mg1.numOf()) {
			if (mg1 instanceof MVideo)
				return "MVIDEO ()";
			if (mg1 instanceof MPoint)
				return "MPOINT ()";
			if (mg1 instanceof MDouble)
				return "MDOUBLE ()";
		}
		return mg1.at(n).toGeoString();
	}

	// @Function
	public static Integer M_NumOf(String mgstring) throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);
		return mg1.numOf();
	}

	// @Function
	public static String M_Time(String mgstring) throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);
		return mg1.time().toGeoString();// (1503828255000 1503828295000)
	}

	// @Function
	public static long M_StartTime(String mgstring) throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);
		return mg1.startTime();
	}

	// @Function
	public static long M_EndTime(String mgstring) throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);
		return mg1.endTime();
	}

	// @Function
	public static String M_Spatial(String mgstring) throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);
		return mg1.spatial().toText();
	}

	// @Function
	public static String M_Snapshot(String mgstring, long instant)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);
		if (mg1.startTime() > instant || mg1.endTime() < instant) {
			if (mg1 instanceof MVideo)
				return "MVIDEO ()";
			if (mg1 instanceof MPoint)
				return "MPOINT ()";
			if (mg1 instanceof MDouble)
				return "MDOUBLE ()";
		}
		return mg1.snapshot(instant).toText();
	}
	// @Function
		public static String M_Snapshot(String mgstring, String instant)
				throws ParseException, org.locationtech.jts.io.ParseException {
			MGeometryFactory geometryFactory = new MGeometryFactory();
			MWKTReader reader = new MWKTReader(geometryFactory);
			MGeometry mg1 = (MGeometry) reader.read(mgstring);
			if (mg1.startTime() > Long.parseLong(instant) || mg1.endTime() < Long.parseLong(instant)) {
				if (mg1 instanceof MVideo)
					return "MVIDEO ()";
				if (mg1 instanceof MPoint)
					return "MPOINT ()";
				if (mg1 instanceof MDouble)
					return "MDOUBLE ()";
			}
			return mg1.snapshot(Long.parseLong(instant)).toText();
		}

	// @Function
	public static String M_Lattice(String mgstring, long instant)
			throws ParseException, org.locationtech.jts.io.ParseException {
		MGeometryFactory geometryFactory = new MGeometryFactory();
		MWKTReader reader = new MWKTReader(geometryFactory);
		MGeometry mg1 = (MGeometry) reader.read(mgstring);
		return mg1.atomize(instant).toGeoString();
	}
}
