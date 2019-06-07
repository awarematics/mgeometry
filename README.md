
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

	





