package com.awarematics.postmedia.algorithms.similarity;


import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;

import com.awarematics.postmedia.types.mediamodel.FoV;
import com.awarematics.postmedia.types.mediamodel.MVideo;

public class MLCVSwithMBR2 {
	MVideo mv1;
	MVideo mv2;

	public static final int EPSILON_R = 2;
	public MLCVSwithMBR2() {

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
					c[i][j] = c[i - 1][j - 1] + calcCVW(fov1[i - 1], fov2[j - 1]);
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

		double x2 = x + fov.getDistance()
				* Math.sin(Math.toRadians(fov.getDirection() - fov.getViewAngle() / 2)) * times;
		double y2 = y + fov.getDistance() 
				* Math.cos(Math.toRadians(fov.getDirection() - fov.getViewAngle() / 2)) * times;// left-top
		double x3 = x + fov.getDistance()
				* Math.sin(Math.toRadians(fov.getDirection() + fov.getViewAngle() / 2)) * times;
		double y3 = y + fov.getDistance() 
				* Math.cos(Math.toRadians(fov.getDirection() + fov.getViewAngle() / 2)) * times;// right-top

		double x1 = x + fov.getDistance() * Math.sin(Math.toRadians(fov.getDirection())) * times;
		double y1 = y + fov.getDistance() * Math.cos(Math.toRadians(fov.getDirection())) * times;

		double cx = (x1 + x) / 2;
		double cy = (y1 + y) / 2;// central point

		double x4 = 2 * cx - x3;// left-bottom
		double y4 = 2 * cy - y3;

		double x5 = 2 * cx - x2;
		double y5 = 2 * cy - y2;// right-bottom
		
		Coordinate[] coor1 = new Coordinate[5];
		coor1[0] = new Coordinate(x4, y4);
		coor1[1] = new Coordinate(x2, y2);
		coor1[2] = new Coordinate(x3, y3);
		coor1[3] = new Coordinate(x5, y5);
		coor1[4] = new Coordinate(x4, y4);
		GeometryFactory geometryFactory = new GeometryFactory();
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

}
