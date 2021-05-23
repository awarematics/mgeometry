
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
--mpid    segid    mbr    timerange(int8range)    trajectory   mpoint

 create index IF NOT EXISTS bl_index_mpid on public."mpoint_120324" using btree (mpid) tablespace pg_default ;  
 create index IF NOT EXISTS bl_index_segid on public."mpoint_120324" using btree (segid) tablespace pg_default ;  
 create index IF NOT EXISTS bl_index_mbr on public."mpoint_120324" using gist (st_geomfromtext(st_astext(mbr))) tablespace pg_default ;  

 create index IF NOT EXISTS bl_index_mbr2 on public."mpoint_120324" using gist (mbr) tablespace pg_default ;  
 create index IF NOT EXISTS bl_index_timerange on public."mpoint_120324" using gist (timerange) tablespace pg_default ;  
 create index IF NOT EXISTS bl_index_trajectory on public."mpoint_120324" using gist (trajectory) tablespace pg_default;
 
----------------------------------------------------------------------------------------------------------------------------------------------------------------
---Q3: m_snapshot()   



CREATE OR REPLACE FUNCTION public.m_snapshots(
	mpoint, double precision)
    RETURNS setof geometry
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_double			alias for $2;
	sql					text;
	mpid                integer;
	results				text;
BEGIN
    mpid := f_mgeometry.moid;	
		sql := 'select m_snapshot(wkttraj, ' || (f_double)::bigint ||') from mpoint_120324 where mpid =' ||(mpid)|| ' AND timerange @>'|| (f_double)::bigint;
		EXECUTE sql into results;
    	RETURN QUERY SELECT st_geomfromtext(results);
END
$BODY$;
ALTER FUNCTION public.m_snapshots(mpoint, double precision)
    OWNER TO postgres;	
    
----------------------------------------------------------------------------------------------------------------------------------------------------------------
---Q4: m_sintersects()   



CREATE OR REPLACE FUNCTION public.m_sintersects(
	mpoint, geometry)
    RETURNS setof boolean
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_geometry		alias for $2;
	sql					text;
	mpid                integer;
	res					geometry;
	mbr					geometry;
BEGIN
    mpid := f_mgeometry.moid;		
	sql := 'select mbr from mpoint_120324 where mpid = ' ||(mpid);
	execute sql into mbr;
	IF(ST_Intersects(mbr, f_geometry)) THEN
		sql := 'select trajectory from mpoint_120324 where mpid = ' ||(mpid);
		execute sql into res;
    	RETURN QUERY EXECUTE 'SELECT ' ||ST_Intersects(res, f_geometry);
	END IF;
END
$BODY$;
ALTER FUNCTION public.m_sintersects(mpoint, geometry)
    OWNER TO postgres;	

----------------------------------------------------------------------------------------------------------------------------------------------------------------
---Q5: m_mindistance()   

	
CREATE OR REPLACE FUNCTION public.m_mindistance(
	mpoint,
	mpoint)
    RETURNS setof double precision
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	f_mgeometry_segtable_name	char(200);
	f_mgeometry_segtable_name2	char(200);
	sql					text;
	mpid                integer;
	mpid2                integer;
	mpoint1				geometry;
	mpoint2				geometry;
BEGIN
    mpid := f_mgeometry1.moid;	
    mpid2 := f_mgeometry2.moid;	
			sql := 'select trajectory from mpoint_120324 where mpid = ' ||(mpid2);
   			EXECUTE sql INTO mpoint2;
			sql := 'select trajectory from mpoint_120324 where mpid = ' ||(mpid);
    		EXECUTE sql INTO mpoint1;
    		RETURN QUERY select MIN(st_distance(mpoint1, mpoint2));
END
$BODY$;
ALTER FUNCTION public.m_mindistance(mpoint, mpoint)
    OWNER TO postgres;
	
----------------------------------------------------------------------------------------------------------------------------------------------------------------
---Q6: m_dwithin() 	



CREATE OR REPLACE FUNCTION public.m_dwithin(mpoint, mpoint, double precision)
RETURNS setof boolean AS 
$BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	f_mgeometry3			alias for $3;
	sql					text;
	mpid                integer;
	mpid2                integer;
	mpoint1				geometry;
	mpoint2				geometry;
	traj1				geometry;
	traj2				geometry;
BEGIN
    mpid := f_mgeometry1.moid;	
    mpid2 := f_mgeometry2.moid;	
	sql := 'select mbr from mpoint_120324 where mpid = ' ||(mpid2);
   	EXECUTE sql INTO mpoint2;
	sql := 'select mbr from mpoint_120324 where mpid = ' ||(mpid);
    EXECUTE sql INTO mpoint1;
	
	IF(mpoint1 && ST_expand(mpoint2, 10)) THEN	
		sql := 'select trajectory from mpoint_120324 where mpid = ' ||(mpid2);
   		EXECUTE sql INTO traj1;
		sql := 'select trajectory from mpoint_120324 where mpid = ' ||(mpid);
    	EXECUTE sql INTO traj2;
    	RETURN QUERY select traj1 && ST_expand(traj2, 10);
	END IF;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;

----------------------------------------------------------------------------------------------------------------------------------------------------------------
---Q7: m_eventtime() 	


CREATE OR REPLACE FUNCTION public.m_eventtime(
	mpoint,
	geometry)
    RETURNS setof double precision
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	sql					text;
	mpid                integer;
	res					text;
BEGIN
    mpid := f_mgeometry1.moid;
	res := st_astext(f_mgeometry2);
	sql := 'select m_eventtime(wkttraj, '||quote_literal(res)||') from mpoint_120324 where mpid = ' ||(mpid);
    RETURN QUERY EXECUTE sql;
END
$BODY$;
ALTER FUNCTION public.m_eventtime(mpoint, geometry)
    OWNER TO postgres;




----------------------------------------------------------------------------------------------------------------------------------------------------------------
---Q8: m_timeatcummulative()  	m_slice()	



CREATE OR REPLACE FUNCTION public.m_timeatcummulative(text)
RETURNS double precision AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	sql			text;
	res		double precision;
BEGIN
  sql:= 'SELECT ' || timeAtCummulativeDistance(f_mgeometry);
   execute sql into res;
   return res;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	

CREATE OR REPLACE FUNCTION public.m_slice(
	mpoint,
	int8range)
    RETURNS setof text
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	sql					text;
	mpid                integer;
	res					text;
	queryrange			int8range;
BEGIN
    mpid := f_mgeometry.moid;	
	sql := 'select timerange from mpoint_120324 where mpid = ' ||(mpid);
	EXECUTE sql into queryrange;
	IF(queryrange && f_period ) THEN
		IF (queryrange <@ f_period) THEN
			sql := 'select wkttraj from mpoint_120324 where mpid = ' ||(mpid) || ' AND timerange <@ '|| quote_literal(f_period); 
				RETURN QUERY EXECUTE sql;
	--	ELSE 
		--	sql := 'select m_slice(wkttraj, '||quote_literal(f_period)||')  from mpoint_120324 where mpid = ' ||(mpid) || 'AND timerange && '|| quote_literal(f_period);
			-- RETURN QUERY EXECUTE sql;
		END IF;
	END IF;
END
$BODY$;
ALTER FUNCTION public.m_slice(mpoint, int8range)
    OWNER TO postgres;
    
----------------------------------------------------------------------------------------------------------------------------------------------------------------
---Q9: m_timeatcummulative()  	m_slice()	

----------------------------------------------------------------------------------------------------------------------------------------------------------------
---Q10: ()  	()	
















	