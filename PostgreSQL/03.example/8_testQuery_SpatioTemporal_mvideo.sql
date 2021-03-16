
	
SELECT M_TimeAtCummulative(mv, 2) FROM uservideos;
	------>Return: 1504354471666

SELECT M_Slice(mv, 'POLYGON ((39 -74, 39 -72, 43 -72, 43 -74, 39 -74))') FROM uservideos;
	------>Return:(1,286114,"{""(40.77,-73.95)"",""(40.77,-73.96)""}","{""2017-09-02 08:14:22"",""2017-09-02 08:14:23""}") 

SELECT M_SnapToGrid(mv, 1) FROM uservideos;
	------>Return:(1,286114,"{""(40.8,-74.0)"",""(40.8,-74.0)""}","{""2017-09-02 08:14:22"",""2017-09-02 08:14:23""}") 

SELECT M_mEnters(mv, 'POLYGON ((39 -74, 39 -72, 43 -72, 43 -74, 39 -74))') FROM uservideos;
	------>Return:false

SELECT M_mBypasses(mv, 'POLYGON ((39 -74, 39 -72, 43 -72, 43 -74, 39 -74))') FROM uservideos;
	------>Return:false

SELECT M_mStayIn(mv, 'POLYGON ((39 -74, 39 -72, 43 -72, 43 -74, 39 -74))') FROM uservideos;
	------>Return:true

SELECT M_mLeaves(mv, 'POLYGON ((39 -74, 39 -72, 43 -72, 43 -74, 39 -74))') FROM uservideos;
	------>Return:false

SELECT M_mCrosses(mv, 'POLYGON ((39 -74, 39 -72, 43 -72, 43 -74, 39 -74))') FROM uservideos;
	------>Return:false 
	
SELECT M_Area(mv) FROM uservideos;
	------>Return: (1,286114,"{57.7,57.7}","{""2017-09-02 08:14:22"",""2017-09-02 08:14:23""}") 
	
SELECT M_Direction(mv) FROM uservideos;
	------>Return: (1,286114,"{0,-0.08}","{""2017-09-02 08:14:22"",""2017-09-02 08:14:23""}") 
	
SELECT M_VelocityAtTime(mv, 2000) FROM uservideos;
	------>Return: 1.37
	
SELECT M_AccelerationAtTime(mv, 2000) FROM uservideos;
	------>Return: 0.0006
	
		
		