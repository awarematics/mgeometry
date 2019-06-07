package com.awarematics.postmedia.types.pm;

class SMLineStringUnit
{
	SInterval interval;
	SMapSegment[] mapSegment; //map_line_values
}

class SMapSegment
{
	SMapPoint startMapPoint;
	SMapPoint endMapPoint;
}

public class SMLineString extends PMModelMGeometry {

	SMLineStringUnit[] sMLineStringUnits;

	
	
}
