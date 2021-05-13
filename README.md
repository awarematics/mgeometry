
# MGeometry Java Library(MJL)


## Supported MGemoetry Types

	MPeriod :  MPERIOD ((1556911345 1556911346), (1556911346 1556911347), ….)
	
	MDuration :  MDURATION (1000, 1000, 1000….)
	
 	MInstant : MINSTANT (15569113450, 15569114450, 15569115450 ….)
	
	MInt :  MINT (2 1556911346, 3 1556911347, ….)
	
 	MBool :  MBOOL (ture 1000, false 1000, true 1000….)   
	
 	MDouble : MDOUBLE (1743.6106216698727 1556811344, 1587.846969956488 1556911345 ….)
	
	MMultiPoint :  MMUltiPoint (((0 0) 1589302899, (1 1) 1589305899, …)...)

 	MString :  MSTRING (disjoint 1481480632123, meet 1481480637123…)

	MPoint :  MPOINT ((0.0 0.0) 1481480632123, (2.0 5.0) 1481480637123…)
	 
 	MLineString :  MLINESTRING ((-1 0, 0 0, 0 0.5, 5 5) 1481480632123, (0 0, -1 0) 1481480637123…)
	
 	MPolygon : MPOLYGON ((0 0, 1 1, 1 0, 0 0) 1000, (0 0, 1 1, 1 0, 0 0) 2000...)
	
	MVideo :  MVIDEO (('localhost:///tmp/drone/test1.jpg' 60 0 0.1 30 0 0 'annotation' 'exif' 100 100) 1481480632123…)
 	
	MPhoto :  MPHOTO (('localhost:///tmp/drone/test1.jpg' 200 200 60 0 0.1 30 0 0 'annotation' 'exif' 100 100) 1481480632123…)


## Supported basic Function 

1. public MGeometry snapshot(MGeometry mg, String ts)

return: MPoint value

example: 

		String queryTime ="2016-12-12 03:23:55.123";
		
		MPoint p = (MPoint)mp.snapshot(mp,queryTime);	
		
2. public MGeometry Slice(MGeometry mg, String fromInstant, String toInstant)

return: MPoint value

example:

		MPoint mpp = (MPoint) mp.Slice(mp, "2017-12-12 03:23:55.000", "2017-12-12 03:23:59.000");	
		
3. public MGeometry lattice(MGeometry mg, long duration)  AND  public MGeometry NormalizeByTime(MGeometry mg, long duration)

return MPoint value

example:
		long duration = 2000;
		
		mp.NormalizeByTime(mp, duration);
		
		mp.lattice(mp, duration);
		
4. public Geometry spatial(MGeometry mg)

5. public long timeInterval(MGeometry mg)

6. public String toGeoString()

7. public abstract MGeometry first(MPoint mp)throws ParseException;

   public abstract MGeometry last(MPoint mp) throws ParseException;
   
   public abstract MGeometry at(MPoint mp, int n) throws ParseException;
   
   public abstract int numof(MPoint mp)throws ParseException; 
   
8. public String LongToString (long duration)  AND  public long StringToLong (String s)

## similarity distance Function 

	M_Hausdorff

	M_LCSS

	M_LCVS

	M_MCAS

	M_TrajHaus

	Default: M_Hausdorff

Example:

		MDouble distance = MPointDistance.distance(MPoint mp1, MPoint mp2 ,"String or null");
		
return: MDouble

		MPointDistance.getResult();
		
return: similarity distance value


## MGeometry SQL Real Examples

### CREATE TABLE examples with MPoint, MPhoto, MVideo, and MDouble types

```
CREATE TABLE BDDTrack
(
  id integer,
  mission character varying(20),
  auxinfo character varying(100),
  times timestamp without time zone
);


CREATE TABLE BDDGeophoto
(
  id integer,
  mission character varying(20),
  auxinfo character varying(100),
  times timestamp without time zone
);


CREATE TABLE BDDMPhoto
(
  id integer,
  mission character varying(20),
  auxinfo character varying(100),
  times timestamp without time zone
);

CREATE TABLE BDDMVideo
(
  id integer,
  mission character varying(20),
  auxinfo character varying(100),
  times timestamp without time zone
);


CREATE TABLE BDDSensor
(
  id integer,
  mission character varying(20),
  auxinfo character varying(100),
  times timestamp without time zone
);


CREATE TABLE mgeometry_columns
(
	f_table_catalog character varying(256) NOT NULL,
	f_table_schema character varying(256) NOT NULL,
	f_table_name character varying(256) NOT NULL,
	f_mgeometry_column character varying(256) NOT NULL,
	f_mgeometry_segtable_name character varying(256) NOT NULL,
	mgeometry_compress character varying(256),
	coord_dimension integer,
	srid	integer,
	"type" character varying(30),
	f_mgeometrytableid character varying(256) NOT NULL,
	f_sequence_name character varying(256) NOT NULL,
	mgeounit_size	integer
)
WITH (
  OIDS=TRUE
);

select * from mgeometry_columns;
```
### INSERT Examples 
```
INSERT into BDDTrack(id, mission,auxinfo,times) values(1,'kunsan','test work1','2018-10-25 01:00:55');
INSERT into BDDTrack(id, mission,auxinfo,times) values(2,'kunsan','test work2','2018-10-25 01:00:57');
select * from bddtrack;
INSERT into BDDMPhoto(id, mission,auxinfo,times) values(1,'kunsan','test work3','2018-10-25 01:00:55');
INSERT into BDDMPhoto(id, mission,auxinfo,times) values(2,'kunsan','test work4','2018-10-25 01:00:57');
select * from bddmphoto;
INSERT into BDDMVideo(id, mission,auxinfo,times) values(1,'kunsan','test work5','2018-10-25 01:00:55');
INSERT into BDDMVideo(id, mission,auxinfo,times) values(2,'kunsan','test work6','2018-10-25 01:00:57');
select * from bddmvideo;
INSERT into BDDSensor(id, mission,auxinfo,times) values(1,'kunsan','test work7','2018-10-25 01:00:55');
INSERT into BDDSensor(id, mission,auxinfo,times) values(2,'kunsan','test work8','2018-10-25 01:00:57');
select * from bddsensor;
INSERT into BDDGeophoto(id, mission,auxinfo,times) values(1,'kunsan','test work9','2018-10-25 01:00:55');
INSERT into BDDGeophoto(id, mission,auxinfo,times) values(2,'kunsan','test work10','2018-10-25 01:00:57');
select * from bddgeophoto;


select addmgeometrycolumns('public','bddtrack','mpoint',4326,'mpoint',2, 50);
select * from bddtrack;
select addmgeometrycolumns('public','bddmphoto','mphoto',4326,'mphoto',2, 50);
select * from bddmphoto;
select addmgeometrycolumns('public','bddmvideo','mvideo',4326,'mvideo',2, 50);
select * from bddmvideo;
select addmgeometrycolumns('public','bddsensor','acc_x',4326,'mdouble',2, 50);
select addmgeometrycolumns('public','bddsensor','acc_y',4326,'mdouble',2, 50);
select addmgeometrycolumns('public','bddsensor','acc_z',4326,'mdouble',2, 50);
select addmgeometrycolumns('public','bddsensor','gyro_x',4326,'mdouble',2, 50);
select addmgeometrycolumns('public','bddsensor','gyro_y',4326,'mdouble',2, 50);
select addmgeometrycolumns('public','bddsensor','gyro_z',4326,'mdouble',2, 50);
select * from bddsensor;
select addmgeometrycolumns('public','bddgeophoto','mgeo',4326,'stphoto',2, 50);
select * from bddgeophoto;



``` 

QUERY EXAMPLES
###  1. What are the models of the vehicles with license plate numbers from Licenses?
```


explain analyze SELECT L.Licence, C.Model AS Model
FROM Cars C, Licences L
WHERE C.Licence = L.Licence;
```
### 2. How many vehicles exist that are "passenger" cars?
```
explain analyze SELECT COUNT (Licence)
FROM Cars C
WHERE Type = 'passenger';
```
### 3. Where have the vehicles with licenses from Licences been at each of the instants from QueryInstants?
```
	
explain analyze 
WITH CarList AS (
SELECT C.Licence As Licence, C.mt AS mpoint
FROM Cars C, Licences L
WHERE C.licence = L.licence
)
SELECT I.Instant AS Instant, C.Licence, m_times(C.mpoint, I.instant) As Intersects 
FROM QueryInstants I, CarList C;

```
### 4. Which vehicles have passed the points from QueryPoints?
```
	

explain analyze 
WITH CarList AS (
SELECT  P.PointId, P.geom, m_sintersects(C.mt, P.geom) AS intersects
FROM Cars C, QueryPoints P
)
SELECT C.PointId, C.geom
FROM CarList C
where C.intersects;

```
### 5. What is the minimum distance between places, where a vehicle with a license from Licences and a vehicle with a license from Licences have been?
```
explain analyze 
SELECT Distinct C1.Licence AS Licence1, C2.Licence AS Licence2, m_mindistance(C1.mt, C2.mt) AS MinDist
FROM Cars C1, Licences L1, Cars C2, Licences L2
WHERE C1.CarId < C2.CarId 
AND L1.LicenceId = C1.CarId AND C2.CarId = L2.LicenceId
Order by MinDist
``` 
###  6. What are the pairs of trips from Licences that have ever been as close as 10m or less to each other?
```

explain analyze 
WITH CarList AS (
SELECT  C1.carid AS carid1, C2.carid AS carid2,  m_dwithin(C1.mt, C2.mt, 10) AS Intersects
FROM Cars C1, Cars C2
WHERE C1.CarId < C2.CarId 
)
SELECT C.Intersects, C.carid1 , C.carid2 
FROM CarList C
WHERE C.Intersects

```
###  7. What are the licence plate numbers of the "passenger" cars that have reached the points from QueryPoints first of all "passenger" cars during the complete observation period?
```
WITH CarList AS (
SELECT C.Licence, P.PointId, P.geom, m_eventtime(C.mt, P.geom) AS Instant
FROM Cars C, QueryPoints P
WHERE st_dwithin(m_spatial(C.mp), P.geom, 0.0)
AND C.Type = 'passenger'
)
SELECT CL1.Licence, CL1.PointId, CL1.geom, CL1.Instant
FROM CarList CL1 
WHERE CL1.Instant <= All(
SELECT CL2.Instant
FROM  CarList CL2 
WHERE CL1.PointId = CL2.PointId 
ORDER BY CL1.PointId, CL1.Licence
)
```
### 8. What are the overall travelled distances of the vehicles with licence plate numbers from Licences during the periods from QueryPeriods?
```
SELECT C.Licence, P.PeriodId, P.Period,
SUM(m_timeAtCummulative(m_slice(C.mt, P.Period))) AS Distance
FROM Cars C, QueryPeriods P
WHERE M_tIntersects(C.mt, P.Period) IS NOT NULL
GROUP BY P.PeriodId, P.Period, C.Licence,C.mt
```
### 9. What is the longest distance that was travelled by a vehicle during each of the periods from QueryPeriods?
```
SELECT C.CarId, P.PeriodId, P.Period,
max(m_timeAtCummulative(m_slice(C.mt, P.Period))) AS Distance
FROM Cars C, QueryPeriods P
WHERE m_tintersects(C.mt, P.Period) IS NOT NULL
GROUP BY P.PeriodId, P.Period, C.CarId,C.mt
```
### 10. When and where did the vehicles with licence plate numbers from Licences meet other vehicles (distance < 3m) and what are the latters' licences?
```
SELECT C1.Licence AS QueryLicence , C2.Licence AS OtherLicence,
m_eventposition(C1.mt, C2.mt, 3) AS meetPos, m_eventtime(C1.mt, C2.mt, 3) AS meetTime
FROM Cars C1, Cars C2, Licences L1
WHERE C1.Licence = L1.Licence AND C2.Licence <> C1.Licence
AND st_dwithin(m_spatial(C1.mt), m_spatial(C2.mt), 3.0)
AND m_tintersects(C1.mt, m_time(C2.mp));
```
### 11. Which vehicles passed a point from QueryPoints at one of the instants from QueryInstants?
 ```
SELECT C.licence AS Licence, P.geom AS QueryPoint, I.instant As Instant
FROM Cars C, QueryPoints P, QueryInstants I
WHERE m_passes(C.mt,P.geom)
AND m_tintersects(C.mt, I.instant)
```
### 12. Which vehicles met at a point from QueryPoints at an instant from QueryInstants?
```
SELECT C1.licence AS Licence1, P.geom AS QueryPoint, I.instant As Instant
FROM Cars C1, QueryPoints P, QueryInstants I
WHERE m_meets(C1.mt, P.geom)
AND m_tintersects(C1.mt, I.instant)
```
### 13. Which vehicles travelled within one of the regions from QueryRegions during the periods from QueryPeriods?
```
SELECT C1.CarId AS CarId, P.period AS Period, R.geom AS QueryRegions
FROM Cars C1, QueryPeriods P, QueryRegions R
WHERE m_tintersects(C1.mt, P.period)
AND m_mstayIn(C1.mt, R.geom)
```
### 14. Which vehicles travelled within one of the regions from QueryRegions at one of the instants from QueryInstants?
```
SELECT C1.CarId AS CarId, I.Instant AS Instant, R.geom AS QueryRegions
FROM Cars C1, QueryInstants I, QueryRegions R
WHERE ST_WITHIN(m_spatial(C1.mt), R.geom) 
AND m_tintersects(C1.mt, I.instant)
```
### 15. Which vehicles passed a point from QueryPoints during a period from QueryPeriods?
```
SELECT C1.CarId AS CarId, Pr.period AS Period, P.geom AS QueryPoint
FROM Cars C1, QueryPeriods Pr, QueryPoints P
WHERE m_passes(C1.mt, P.geom)
AND m_tintersects(C1.mt, Pr.period)
```
### 16. List the pairs of licences for vehicles from Licences where the corresponding vehicles are both present within a region from Regions1 during a period from QueryPeriod, but do not meet each other there and then.
```
SELECT C1.Licence, C2.Licence, P.period, R.geom 
FROM Cars C1, Cars C2, QueryPeriods P, QueryRegions R
WHERE m_insides(C1.mt, R.geom) 
AND m_insides(C2.mt, R.geom)
And m_disjoints(C1.mt,C2.mt,P.period)
```
### 17. Which points from Points have been visited by a maximum number of different vehicles?
```

WITH PointCount AS (
SELECT P.PointId, COUNT(C.CarId) AS Hits
FROM Cars C, QueryPoints P
WHERE m_sintersects(C.mt, P.geom )
GROUP BY P.PointId 
)
SELECT PointId, max(P.Hits) 
FROM PointCount P

```


### SELECT Examples 

#### functions
	
SELECT m_distance('Point(....)' , 'MPoint(...)');

SELECT m_distance('Point(....)' , 'MPhoto(...)');

SELECT m_distance('Point(....)' , 'MVideo(...)');
	
SELECT m_distance('LineString(....)' , 'MPoint(...)');

SELECT m_distance('LineString(....)' , 'MPhoto(...)');

SELECT m_distance('LineString(....)' , 'MVideo(...)');

SELECT m_distance('Polygon(....)' , 'MPoint(...)');

SELECT m_distance('Polygon(....)' , 'MPhoto(...)');

SELECT m_distance('Polygon(....)' , 'MVideo(...)');

SELECT m_distance('MPoint(....)' , 'MPoint(...)');

SELECT m_distance('MPoint(....)' , 'MPhoto(...)');

SELECT m_distance('MPoint(....)' , 'MVideo(...)');

SELECT m_distance('MPhoto(....)' , 'MPhoto(...)');

SELECT m_distance('MPhoto(....)' , 'MVideo(...)');

SELECT m_distance('MVideo(....)' , 'MVideo(...)');

SELECT function_name1(parameter1 , parameter2 );

SELECT function_name2(parameter1 , parameter2 );

#### for each tables

	select m_distance(a.track, 'POINT (1405 1175)') FROM taxi a where carnumber='2';
	
	select m_distance(a.track, 'POLYGON ((0 0, 0 1, 1 1, 1 0, 0 0))') FROM taxi a where carnumber='2';
	
	select m_distance(a.track, 'LINESTRING(-1 0, 0 0, 0 0.5, 5 5)') FROM taxi a where carnumber='2';
	
	select m_min((select m_distance( a.TRACK, 'POINT (1405 1175)') from taxi a where carnumber='2').doubles, 100.0);
	
	select m_max((select m_distance( a.TRACK, 'POINT (1405 1175)') from taxi a where carnumber='2').doubles, 100.0);
	
	select m_asText(a.TRACK) from taxi a where carnumber='2';

### Join Examples



	SELECT m_snapshot( a.TRACK, b.TRACK) from taxi a, taxi b where carnumber='2';

	SELECT m_relationship( a.TRACK, 'POINT (1405 1175)') from taxi a where carnumber='2';
	
	SELECT m_pass( a.TRACK, 'POINT (1405 1175)') from taxi a where carnumber='2';
	

---------------

	





