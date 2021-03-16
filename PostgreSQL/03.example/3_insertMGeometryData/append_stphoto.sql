
	UPDATE userstphotos 
	SET st  = append(st, 'STPHOTO (http://data.u-gis.net/img/001.jpg 100 200 (10 0 60 30 0.001) null (40.63 -73.56) 1000)')
   	WHERE id = 1;
   	
	UPDATE userstphotos 
	SET st  = append(st, 'STPHOTO (http://data.u-gis.net/img/002.jpg 100 200 (10 0 60 30 0.001) null (40.64 -73.56) 2000)')
   	WHERE id = 2;
   	
	UPDATE userstphotos 
	SET st  = append(st, 'STPHOTO (http://data.u-gis.net/img/003.jpg 100 200 (10 0 60 30 0.001) null (40.65 -73.56) 3000)')
   	WHERE id = 3;
   	
   	---(2,00001.mp4?t=2,100,200,"1970-01-01 00:33:20",,"(40.64,-73.56,)","(10,,10,,0.1)")
   	