select sqlj.remove_jar('myjar', true);
select sqlj.remove_jar('myjar2', true);
select sqlj.install_jar('file:/tmp/proj_berlinmod/target/proj-0.0.1-SNAPSHOT.jar', 'myjar', true);
select sqlj.install_jar('file:/tmp/proj_berlinmod/jts-core-1.15.0-SNAPSHOT.jar', 'myjar2', true);	
select sqlj.set_classpath('public', 'myjar:myjar2');
select sqlj.get_classpath('public');


-----car table
 create index IF NOT EXISTS bl_index_carid on public."cars" using btree (carid) tablespace pg_default ;  
 create index IF NOT EXISTS bl_index_licence on public."cars" using btree (licence text_pattern_ops) tablespace pg_default ;  
 create index IF NOT EXISTS bl_index_type on public."cars" using btree (type text_pattern_ops) tablespace pg_default ; 
 create index IF NOT EXISTS bl_index_model on public."cars" using btree (model text_pattern_ops) tablespace pg_default ;
 
-----licence table
 create index IF NOT EXISTS bl_index_querylicence on public."licences" using btree (licence text_pattern_ops) tablespace pg_default ;  
 create index IF NOT EXISTS bl_index_querylicenceid on public."licences" using btree (licenceid) tablespace pg_default ; 
 
-----instant table 
 create index IF NOT EXISTS bl_index_queryinstant on public."queryinstants" using btree (instant) tablespace pg_default ;  
 create index IF NOT EXISTS bl_index_queryinstantid on public."queryinstants" using btree (instantid) tablespace pg_default ; 
 
-----period table 
 create index IF NOT EXISTS bl_index_queryperiod on public."queryperiods" using btree (period) tablespace pg_default ;  
 create index IF NOT EXISTS bl_index_queryperiodid on public."queryperiods" using btree (periodid) tablespace pg_default ; 
 
-----point table
 create index IF NOT EXISTS bl_index_querypoint on public."querypoints" using gist (geom) tablespace pg_default ;  
 create index IF NOT EXISTS bl_index_querypointid on public."querypoints" using btree (pointid) tablespace pg_default ;
 
-----region table
 create index IF NOT EXISTS bl_index_queryregion on public."queryregions" using gist (geom) tablespace pg_default ;  
 create index IF NOT EXISTS bl_index_queryregionid on public."queryregions" using btree (regionid) tablespace pg_default ;

-----mpoint_seg_table
--mpid    segid    mbr    timerange(int8range)    trajectory   wkttraj

 create index IF NOT EXISTS bl_index_mpid on public."mpoint_120324" using btree (mpid) tablespace pg_default ;  
 create index IF NOT EXISTS bl_index_segid on public."mpoint_120324" using btree (segid) tablespace pg_default ;  
 create index IF NOT EXISTS bl_index_mbr on public."mpoint_120324" using gist (st_geomfromtext(st_astext(mbr))) tablespace pg_default ;  

 create index IF NOT EXISTS bl_index_mbr2 on public."mpoint_120324" using gist (mbr) tablespace pg_default ;  
 create index IF NOT EXISTS bl_index_timerange on public."mpoint_120324" using gist (timerange) tablespace pg_default ;  
 create index IF NOT EXISTS bl_index_trajectory on public."mpoint_120324" using gist (trajectory) tablespace pg_default ;
 -----mgeo
 create index IF NOT EXISTS bl_index_segoid on public."mgeometry_columns" using btree (f_segtableoid text_pattern_ops) tablespace pg_default ; 
  create index IF NOT EXISTS bl_index_segtname on public."mgeometry_columns" using btree (f_mgeometry_segtable_name text_pattern_ops) tablespace pg_default ; 
	