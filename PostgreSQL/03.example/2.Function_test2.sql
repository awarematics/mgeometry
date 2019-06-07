
	select sqlj.remove_jar('myjar', true);
	select sqlj.remove_jar('myjar2', true);
	select sqlj.install_jar('file:/tmp/proj/target/proj-0.0.1-SNAPSHOT.jar', 'myjar', true);
	select sqlj.install_jar('file:/tmp/ding/jts-core-1.15.0-SNAPSHOT.jar', 'myjar2', true);	
  	select sqlj.set_classpath('public', 'myjar:myjar2');
	select sqlj.get_classpath('public');

insert into usersensors(id, carnumber) values(1,'000f8d37-d4c09a0f');
insert into usersensors(id, carnumber) values(2,'000f157f-dab3a407');
insert into usersensors(id, carnumber) values(3,'00a395fe-d60c0b47');


insert into usertrajs(id, carnumber) values(1,'000f8d37-d4c09a0f');
insert into usertrajs(id, carnumber) values(2,'000f157f-dab3a407');
insert into usertrajs(id, carnumber) values(3,'00a395fe-d60c0b47');


insert into uservideos(id, name) values(1,'000f8d37-d4c09a0f');
insert into uservideos(id, name) values(2,'000f157f-dab3a407');
insert into uservideos(id, name) values(3,'00a395fe-d60c0b47');



select addmgeometrycolumn('public','usertrajs','mt',4326,'mpoint',2, 50);

select addmgeometrycolumn('public','usersensors','md_accx',4326,'mdouble',2, 3000);
select addmgeometrycolumn('public','usersensors','md_accy',4326,'mdouble',2, 3000);
select addmgeometrycolumn('public','usersensors','md_accz',4326,'mdouble',2, 3000);
select addmgeometrycolumn('public','usersensors','md_gyrox',4326,'mdouble',2, 3000);
select addmgeometrycolumn('public','usersensors','md_gyroy',4326,'mdouble',2, 3000);
select addmgeometrycolumn('public','usersensors','md_gyroz',4326,'mdouble',2, 3000);


select addmgeometrycolumn('public','uservideos','mv',4326,'mvideo',2, 3000);


	select sqlj.remove_jar('myjar', true);
	select sqlj.remove_jar('myjar2', true);
	select sqlj.install_jar('file:/tmp/proj/target/proj-0.0.1-SNAPSHOT.jar', 'myjar', true);
	select sqlj.install_jar('file:/tmp/ding/jts-core-1.15.0-SNAPSHOT.jar', 'myjar2', true);	
  	select sqlj.set_classpath('public', 'myjar:myjar2');
	select sqlj.get_classpath('public');




select m_astext(mv) from uservideos where id =1;
select m_astext(mt) from usertrajs where id =1;

select id, mv 
from uservideos 
where m_overlaps(mv , 'Period (1503828255000, 1503828256000)');

select id, mt 
from usertrajs 
where m_overlaps(mt , 'Period (1200, 30000000000000000)');

select id, m_slice(mv, 'Period (1503828255000, 1503828259000)') 
from uservideos 
where m_overlaps(mv , 'Period (1200, 30000000000000000)') ;


select id, m_slice(mt, 'Period (1503828255000, 1503828259000)') 
from usertrajs 
where  m_overlaps(mt , 'Period (1200, 30000000000000000)') ;


select id, mt 
from usertrajs 
where m_contains(mt , 'Period (1503828255000, 1503828259000)');


select id, mv 
from uservideos 
where m_contains(mv , 'Period (1503828255000, 1503828259000)');

select id, mt 
from usertrajs 
where m_equals(mt , 'Period (1503828255000, 1503828295000)');


select id, mv 
from uservideos 
where m_equals(mv , 'Period (1503828255000, 1503828295000)');
 
select id, mt 
from usertrajs 
where m_precedes(mt , 'Period (1503828295000, 1503828299000)');

select id, mv 
from uservideos 
where m_precedes(mv , 'Period (1503828295000, 1503828299000)');


select id, mt 
from usertrajs 
where m_succeeds(mt , 'Period (1503828255000, 1503828295000)');


select id, mv 
from uservideos 
where m_succeeds(mv , 'Period (1503828255000, 1503828295000)');


select id, mt 
from usertrajs 
where m_immprecedes(mt , 'Period (1503828295000, 1503828299000)');


select id, mv 
from uservideos 
where m_immsucceeds(mv , 'Period (1503828255000, 1503828295000)');

select id, mt 
from usertrajs 
where m_immsucceeds(mt , 'Period (1503828255000, 1503828295000)');



select id,  m_at(mt , 5) 
from usertrajs;

select id,  m_at(mv , 5) 
from uservideos;

select id,  m_numof(mv) 
from uservideos;

select id,  m_numof(mt) 
from usertrajs;



select id,  m_time(mt) 
from usertrajs;


select id,  m_time(mv) 
from uservideos;

select id,  m_starttime(mt) 
from usertrajs;

select id,  m_starttime(mv) 
from uservideos;

select id,  m_endtime(mt) 
from usertrajs;

select id,  m_endtime(mv) 
from uservideos;


select id,  m_spatial(mt) 
from usertrajs;

select id,  m_spatial(mv) 
from uservideos;

select id,  m_snapshot(mt, 1503828255000 ) 
from usertrajs;

select id,  m_snapshot(mv, 1503828255000 ) 
from uservideos;


-----------------------------------------------------------------------------------------

select m_astext(mt) from usertrajs where id =9;

select id,  M_TimeAtCummulative(mt , 50) 
from usertrajs ;


select id,  M_SnapToGrid(mt , 5) 
from usertrajs ;

select  m_intersects(a.mt, b.mt) 
from usertrajs a, usertrajs b;

select  m_enters(a.mt, 'POLYGON ((39 -74, 39 -72, 43 -72, 43 -74, 39 -74))') 
from usertrajs a;


select  m_bypasses(a.mt, 'POLYGON ((39 -74, 39 -72, 43 -72, 43 -74, 39 -74))') 
from usertrajs a;


select  m_leaves(a.mt, 'POLYGON ((39 -74, 39 -72, 43 -72, 43 -74, 39 -74))') 
from usertrajs a;

select  m_crosses(a.mt, 'POLYGON ((39 -74, 39 -72, 43 -72, 43 -74, 39 -74))') 
from usertrajs a;


select  m_eventtime(a.mt, b.mt) 
from usertrajs a, usertrajs b;


select  m_relationship(a.mt, b.mt) 
from usertrajs a, usertrajs b;


select  m_distance(a.mt, b.mt) 
from usertrajs a, usertrajs b;


select  m_area(a.mv) 
from uservideos a;

select  m_direction(a.mt) 
from usertrajs a;


select  m_velocityattime(a.mt, 1503914658000) 
from usertrajs a;

select  m_accelerationattime(a.mt, 1503914658000) 
from usertrajs a;


select  m_hausdorff(a.mt, b.mt) 
from usertrajs a, usertrajs b;

select  m_lcss(a.mt, b.mt, 0.001, 40) 
from usertrajs a, usertrajs b;

select  m_traclus(a.mt, b.mt) 
from usertrajs a, usertrajs b;



select  m_lcvs(a.mv, b.mv, 40) 
from uservideos a, uservideos b;



                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
select id,  M_TimeAtCummulative(mv , 50) 
from uservideos ;

select id,  M_SnapToGrid(mv , 5) 
from uservideos ;

select  m_intersects(a.mv, b.mv) 
from uservideos a, uservideos b;

select  m_enters(a.mv, 'POLYGON ((39 -74, 39 -72, 43 -72, 43 -74, 39 -74))') 
from uservideos a;

select  m_bypasses(a.mv, 'POLYGON ((39 -74, 39 -72, 43 -72, 43 -74, 39 -74))') 
from uservideos a;

select  m_leaves(a.mv, 'POLYGON ((39 -74, 39 -72, 43 -72, 43 -74, 39 -74))') 
from uservideos a;

select  m_crosses(a.mv, 'POLYGON ((39 -74, 39 -72, 43 -72, 43 -74, 39 -74))') 
from uservideos a;

select  m_eventtime(a.mv, b.mv) 
from uservideos a, uservideos b;


select  m_relationship(a.mv, b.mv) 
from uservideos a, uservideos b;


select  m_distance(a.mv, b.mv) 
from uservideos a, uservideos b;


select  m_direction(a.mv) 
from uservideos a;


select  m_velocityattime(a.mv, 1503914658000) 
from uservideos a;

select  m_accelerationattime(a.mv, 1503914658000) 
from uservideos a;


select  m_hausdorff(a.mv, b.mv) 
from uservideos a, uservideos b;

select  m_lcss(a.mv, b.mv, 0.001, 40) 
from uservideos a, uservideos b;

select  m_traclus(a.mv, b.mv) 
from uservideos a, uservideos b;



select m_astext(m_slice(mv, 'Period (1503828255000, 1503828259000)')) from uservideos;

select a.id, b.id
from uservideos a, uservideos b
where m_any(m_intersects (a.mv, b.mv))= true;

select m_intersects (a.mv, b.mv) from  uservideos a, uservideos b;

SELECT id, carnumber
FROM userTrajs a 
WHERE  M_Max( M_Distance( a.mt, 'POLYGON ((40.74 -73.99, 40.74 -73.98, 40.73 -73.98, 40.73 -73.99, 40.74 -73.99 ))' ))  < 500.0


select m_lattice (a.mv, 10000) from  uservideos a;
