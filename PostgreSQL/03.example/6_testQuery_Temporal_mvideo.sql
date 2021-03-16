
	
SELECT M_At(mv, 2) FROM uservideos;
	------>Return: (1,286114,"{""(40.77,-73.96)""}","{""2017-09-02 08:14:23""}")

SELECT M_NumOf(mv) FROM uservideos;
	------>Return: 2

SELECT M_Time(mv) FROM uservideos;
	------>Return:(1504354462000,1504354501000)

SELECT M_StartTime(mv) FROM uservideos;
	------>Return:1504354462000

SELECT M_EndTime(mv) FROM uservideos;
	------>Return:1504354501000

SELECT M_Spatial(mv) FROM uservideos;
	------>Return:LINESTRING(40.77 -73.95,40.77 -73.96)

SELECT M_Snapshot(mv, 1000) FROM uservideos;
	------>Return:POINT(40.77 -73.95)

SELECT M_Slice(mv, 'Period (1100, 1200)') FROM uservideos;
	------>Return:(1,286114,"{""(40.77,-73.95)"",""(40.77,-73.96)""}","{""2017-09-02 08:14:22"",""2017-09-02 08:14:23""}")

SELECT M_Lattice(mv, 2000) FROM uservideos;
	------>Return:(1,286114,"{""(40.77,-73.95)""}","{""2017-09-02 08:14:22""}")

SELECT M_tOverlaps(mv, 'Period (1100, 2200)') FROM uservideos;
	------>Return: true