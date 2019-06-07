package com.awarematics.postmedia.types.pm;



class SMPolygonUnit
{
	SInterval interval;
	SMLineString[] mapLineString; // Cycle == { LineString, but closed }
}

public class SMPolygon extends PMModelMGeometry {

	SMPolygonUnit[] sMPolygonUnits;
}
