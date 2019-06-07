package com.awarematics.postmedia.algorithms.similarity;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;

import com.awarematics.postmedia.types.mediamodel.FoV;
import com.awarematics.postmedia.types.mediamodel.MGeometry;
import com.awarematics.postmedia.types.mediamodel.MPhoto;
import com.awarematics.postmedia.types.mediamodel.MVideo;

public class MLCVSwithMBT {
	MVideo mv1;
	MVideo mv2;

	public MLCVSwithMBT() {
	
	}

	
	public double measure(MVideo mv1, MVideo mv2, double delta) {
		double[][] c = null;
		
		c = new double[mv1.numOf() + 1][mv2.numOf() + 1];
		for (int i = 0; i < mv1.numOf(); i++) {
			c[i][0] = 0;
		}
		for (int j = 0; j <= mv2.numOf(); j++) {
			c[0][j] = 0;
		}

		FoV[] fov1 = mv1.getFov();
		FoV[] fov2 = mv2.getFov();
		for (int i = 1; i <= mv1.numOf(); i++) {
			for (int j = 1; j <= mv2.numOf(); j++) {
				if (Math.abs(j - i) <= delta) {
					c[i][j] = c[i - 1][j - 1] + calcCVW( fov1[i - 1], fov2[j - 1]);
				} else if (c[i - 1][j] <= c[i][j - 1]) {
					c[i][j] = c[i][j - 1];
				} else {
					c[i][j] = c[i - 1][j];
				}
			}
		}
		return c[mv1.numOf()][mv2.numOf()];
	}

	public double measure(MGeometry mv1, MGeometry mv2, double delta) {
		double[][] c = null;
		
		c = new double[mv1.numOf() + 1][mv2.numOf() + 1];
		for (int i = 0; i < mv1.numOf(); i++) {
			c[i][0] = 0;
		}
		for (int j = 0; j <= mv2.numOf(); j++) {
			c[0][j] = 0;
		}
		FoV[] fov1 = null;
		FoV[] fov2 = null;
		if(mv1 instanceof MPhoto){
		fov1 = ((MPhoto)mv1).getFov();
		}
		if(mv1 instanceof MVideo){
			fov1 = ((MVideo)mv1).getFov();
			}
		if(mv2 instanceof MPhoto){
			fov2 = ((MPhoto)mv2).getFov();
			}
			if(mv2 instanceof MVideo){
				fov2 = ((MVideo)mv2).getFov();
				}
		for (int i = 1; i <= mv1.numOf(); i++) {
			for (int j = 1; j <= mv2.numOf(); j++) {
				if (Math.abs(j - i) <= delta) {
					c[i][j] = c[i - 1][j - 1] + calcCVW( fov1[i - 1], fov2[j - 1]);
				} else if (c[i - 1][j] <= c[i][j - 1]) {
					c[i][j] = c[i][j - 1];
				} else {
					c[i][j] = c[i - 1][j];
				}
			}
		}
		return c[mv1.numOf()][mv2.numOf()];
	}

	private double calcCVW( FoV f1, FoV f2) {
		double cvw = 0.0;
		Polygon polygon = genFoVArea(f1.getCoord().x, f1.getCoord().y, f1);
		Polygon polygon2 = genFoVArea(f2.getCoord().x, f2.getCoord().y, f2);
		if ( polygon.getEnvelope().intersects(polygon2.getEnvelope()) ) {
			Geometry pl3 = polygon.intersection(polygon2);
			Geometry pl5 = polygon2.union(polygon);
			cvw = pl3.getArea() / pl5.getArea();
		}
		return cvw;
	}

	
	
	private Polygon genFoVArea(double x, double y, FoV fov) {
		int times = 1;
		double x4 = (double) x;
		double y4 = (double) y;
		
		double x2 = (double) x
				+ fov.getDistance() * 2/Math.sqrt(3) * Math.sin(Math.toRadians(fov.getDirection() + (fov.getViewAngle() )/ 2)) * times;
		double y2 = (double) y
				+ fov.getDistance() *  2/Math.sqrt(3) *Math.cos(Math.toRadians(fov.getDirection() +(fov.getViewAngle() )/ 2)) * times;// left
		double x3 = (double) x
				+ fov.getDistance() *  2/Math.sqrt(3) *Math.sin(Math.toRadians(fov.getDirection() - (fov.getViewAngle() ) / 2)) * times;
		double y3 = (double) y
				+ fov.getDistance() * 2/Math.sqrt(3) * Math.cos(Math.toRadians(fov.getDirection() -(fov.getViewAngle() ) / 2)) * times;// right
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

	
	public double similarity(MVideo mg1, MVideo mg2, double omega) {
		double lcss_distance = 0.0;
		double similarity = 0.0;
		lcss_distance = measure(mg1, mg2, omega);
		similarity = lcss_distance / (Math.min(mg1.numOf(), mg2.numOf()));
		return similarity;
	}
	public double similarity(MGeometry mg1, MGeometry mg2, double omega) {
		double lcss_distance = 0.0;
		double similarity = 0.0;
		lcss_distance = measure(mg1, mg2, omega);
		similarity = lcss_distance / (Math.min(mg1.numOf(), mg2.numOf()));
		return similarity;
	}

	// Structural and Logical Thinking

	/*
	 * public static class CalcFoVMBTR2 implements CalcFoV { public double
	 * calcFoV(Polygon g1, Polygon g2) { Geometry pl3 = g1.intersection(g2);
	 * Geometry pl5 = g2.union(g1); double temp = pl3.getArea() / pl5.getArea();
	 * 
	 * 
	 * return 0.0; } } public void setCalcMethod( CalcFoV calcMethod ) {
	 * this.calcMethod = calcMethod; }
	 * 
	 * 
	 * private static Polygon makeFoVPolygon(double x, double y, double vangle,
	 * double direction, double radius) { // comments final long TIMES = 1000;
	 * double x4 = (double) (x * TIMES); double y4 = (double) (y * TIMES);
	 * double x1 = (double) (x4 + radius * Math.sin(Math.toRadians(direction)) *
	 * TIMES); double y1 = (double) (y4 + radius *
	 * Math.cos(Math.toRadians(direction)) * TIMES);// center // point double x2
	 * = (double) (x4 + radius * Math.sin(Math.toRadians(direction + vangle /
	 * 2)) * TIMES); double y2 = (double) (y4 + radius *
	 * Math.cos(Math.toRadians(direction + vangle / 2)) * TIMES);// left //
	 * point double x3 = (double) (x4 + radius *
	 * Math.sin(Math.toRadians(direction - vangle / 2)) * TIMES); double y3 =
	 * (double) (y4 + radius * Math.cos(Math.toRadians(direction - vangle / 2))
	 * * TIMES);// right // point Coordinate[] coor1 = new Coordinate[5];
	 * 
	 * GeometryFactory geometryFactory = new GeometryFactory();
	 * 
	 * coor1[0] = new Coordinate(x4, y4); coor1[2] = new Coordinate(x1, y1);
	 * coor1[1] = new Coordinate(x2, y2); coor1[3] = new Coordinate(x3, y3);
	 * coor1[4] = new Coordinate(x4, y4);
	 * 
	 * Polygon c = geometryFactory.createPolygon(coor1);
	 * 
	 * return c; } public static void main() {
	 * 
	 * M_LCVS lcvs1 = new M_LCVS( new MVideo(), new MVideo(), new CalcFoVNaive()
	 * ); M_LCVS lcvs2 = new M_LCVS( new MVideo(), new MVideo(), new
	 * CalcFoVMBTR() ); //M_LCVS lcvs3 = new M_LCVS( new MVideo(), new MVideo(),
	 * new CalcFoVMBTR2() );
	 * 
	 * }
	 */

}
