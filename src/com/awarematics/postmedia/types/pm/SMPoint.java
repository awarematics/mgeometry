package com.awarematics.postmedia.types.pm;

import org.locationtech.jts.geom.Point;

class SMPointUnit
{
	SInterval 	interval;
	SMapPoint	mapPoint;
}

class SMapPoint
{
	Point startPoint;
	Point endPoint;
}

public class SMPoint extends PMModelMGeometry {
	
	SMPointUnit[] smPointUnits;

	
}
