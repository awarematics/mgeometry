package com.awarematics.postmedia.types.mediamodel;

import org.locationtech.jts.geom.Coordinate;

public class FoV {
	double viewAngle;
	double verticalAngle; // vertical angle of view

	public double getVerticalAngle() {
		return verticalAngle;
	}

	public void setVerticalAngle(double verticalAngle) {
		this.verticalAngle = verticalAngle;
	}

	double distance;
	double direction;
	Coordinate coord;

	// double y;
	public FoV(double vangle, double distance, double direction, Coordinate coord) {
		this.viewAngle = vangle;
		this.distance = distance;
		this.direction = direction;
		this.coord = coord;
		// this.y = y;
	}

	public FoV() {
		// TODO Auto-generated constructor stub
	}

	public double getViewAngle() {
		return viewAngle;
	}

	public void setViewAngle(double vangle) {
		this.viewAngle = vangle;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public Coordinate getCoord() {
		return coord;
	}

	public void setCoord(Coordinate coord) {
		this.coord = coord;
	}

}

