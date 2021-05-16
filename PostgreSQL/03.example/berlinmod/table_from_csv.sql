-- datamcar

create table Cars(
	carid	integer,
	Licence	text,
	Type	text,
	Model	text
);

COPY Cars  -- table name
FROM '/tmp/proj/datamcar.csv' --path
delimiter ',' csv HEADER;

select * from Cars;

select addmgeometrycolumn('public','cars','mt',4326,'mpoint',2, 50);



-- queryinstants


create table Instants(
	InstantId	integer,
	Instant	timestamptz
);

COPY Instants  -- table name
FROM '/tmp/proj/queryinstants.csv' --path
delimiter ',' csv HEADER;

select * from Instants;

create table queryInstants as (
select InstantId, EXTRACT(EPOCH FROM (instant AT TIME ZONE 'UTC'))*1000 AS Insstant from Instants)


select * from queryInstants;


-- query licences



create table Licences(
	Licence	text,
	Licenceid	integer
);

COPY Licences  -- table name
FROM '/tmp/proj/querylicences.csv' --path
delimiter ',' csv HEADER;

select * from Licences;



-- query periods


create table Periods(
	PeriodId	integer,
	BeginTime	timestamptz,
	EndTime	timestamptz
);

COPY Periods  -- table name
FROM '/tmp/proj/queryperiods.csv' --path
delimiter ',' csv HEADER;

select * from Periods;


create table queryPeriods as (
select PeriodId, ('(' || EXTRACT(EPOCH FROM (begintime AT TIME ZONE 'UTC'))*1000 ||','|| EXTRACT(EPOCH FROM (endtime AT TIME ZONE 'UTC'))*1000 ||')')::int8range AS period from periods);


select * from queryPeriods;

-- query points





create table Points(
	PointId	integer,
	Pointx	double precision,
	Pointy	double precision
);

COPY Points  -- table name
FROM '/tmp/proj/querypoints.csv' --path
delimiter ',' csv HEADER;

select * from Points;

create table queryPoints as (
select PointId, St_makePoint(pointx, pointy) AS Geom from Points)


select * from queryPoints;



-- query regions



create table Regions(
	NoId	integer,
	RegionId	integer,
	Xstart	double precision,
	Ystart	double precision,
	Xend	double precision,
	Yend	double precision
);

COPY Regions   -- table name
FROM '/tmp/proj/queryregions.csv' --path
delimiter ',' csv HEADER;

select * from Regions;

create table queryRegions as (
select RegionId, st_makebox2d(St_makePoint(Xstart, Ystart), St_makePoint(Xend, Yend))::geometry AS Geom from Regions)


select * from queryRegions;




-- trips


-- important
---select st_astext(Box2D(ST_GeomFromText('MULTIPOINT ((1510 1210), (1461 1037))')));












