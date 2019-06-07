
CREATE OR REPLACE FUNCTION public.insert_mpoint()
  RETURNS trigger AS
$BODY$
DECLARE    
    segtable_oid        text;
    segcolumn_name        text;
    sequence_name        text;
    moid            text;
    
    sql_text        text;
    records            record;
    
 BEGIN    
    sql_text := 'select f_segtableoid, f_mgeometry_column, f_sequence_name from mgeometry_columns where f_table_name = ' || quote_literal(TG_RELNAME);
    execute sql_text into records;
    
    segtable_oid := records.f_segtableoid;
    segcolumn_name := records.f_mgeometry_column;
    sequence_name := records.f_sequence_name;

    sql_text := 'select nextval(' || quote_literal(sequence_name) || ')';
        
    execute sql_text into moid;

    
    RAISE NOTICE 'sql_text : %', sql_text;
    RAISE NOTICE 'segtable_oid : %', segtable_oid;
    RAISE NOTICE 'segcolumn_name : %', segcolumn_name;
    RAISE NOTICE 'sequence_name : %', sequence_name;
    RAISE NOTICE 'moid : %', moid;
    
    NEW.mpoint = (moid);    
        
    return NEW;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.insert_mpoint()
  OWNER TO postgres;



CREATE or replace FUNCTION public.delete_mpoint()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF 
AS $BODY$
DECLARE        
    delete_mpoint        mpoint;
    delete_id        integer;
    
    records            record;
    delete_record        record;
    
    BEGIN
    execute 'select f_mgeometry_segtable_name, f_mgeometry_column from mgeometry_columns where f_table_name = ' || quote_literal(TG_RELNAME)
    into records;

    delete_record := OLD;

    /*
    execute 'select ' || column_name || ' from ' || delete_record
    into delete_trajectory;
    */
    
    delete_mpoint := OLD.mpoint;
    delete_id := delete_mpoint.id;
    
    execute 'DELETE FROM ' || quote_ident(records.f_mgeometry_segtable_name) || ' WHERE mpid = ' || delete_id;

    return NULL;

    END;
$BODY$;

ALTER FUNCTION public.delete_mpoint()
    OWNER TO postgres;





























CREATE OR REPLACE FUNCTION public.insert_mdouble()
  RETURNS trigger AS
$BODY$
DECLARE    
    segtable_oid        text;
    segcolumn_name        text;
    sequence_name        text;
    moid            text;
    
    sql_text        text;
    records            record;
    
 BEGIN    
    sql_text := 'select f_segtableoid, f_mgeometry_column, f_sequence_name from mgeometry_columns where f_table_name = ' || quote_literal(TG_RELNAME);
    execute sql_text into records;
    
    segtable_oid := records.f_segtableoid;
    segcolumn_name := records.f_mgeometry_column;
    sequence_name := records.f_sequence_name;

    sql_text := 'select nextval(' || quote_literal(sequence_name) || ')';
        
    execute sql_text into moid;

    
    RAISE NOTICE 'sql_text : %', sql_text;
    RAISE NOTICE 'segtable_oid : %', segtable_oid;
    RAISE NOTICE 'segcolumn_name : %', segcolumn_name;
    RAISE NOTICE 'sequence_name : %', sequence_name;
    RAISE NOTICE 'moid : %', moid;
    
    NEW.mdouble = (moid);    
        
    return NEW;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.insert_mdouble()
  OWNER TO postgres;



CREATE or replace FUNCTION public.delete_mdouble()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF 
AS $BODY$
DECLARE        
    delete_mdouble        mdouble;
    delete_id        integer;
    
    records            record;
    delete_record        record;
    
    BEGIN
    execute 'select f_mgeometry_segtable_name, f_mgeometry_column from mgeometry_columns where f_table_name = ' || quote_literal(TG_RELNAME)
    into records;
	RAISE INFO '%', records;
	
    delete_record := OLD;
    delete_mdouble := OLD.mdouble;
    delete_id := delete_mdouble.id;
    
    execute 'DELETE FROM ' || quote_ident(records.f_mgeometry_segtable_name) || ' WHERE mpid = ' || delete_id
	into records;
	RAISE INFO '%', records;
    return NULL;

    END;
$BODY$;

ALTER FUNCTION public.delete_mdouble()
    OWNER TO postgres;
	
	
	
	
	
	
	
	
	
	

	
CREATE OR REPLACE FUNCTION public.insert_mvideo()
  RETURNS trigger AS
$BODY$
DECLARE    
    segtable_oid        text;
    segcolumn_name        text;
    sequence_name        text;
    moid            text;
    
    sql_text        text;
    records            record;
    
 BEGIN    
    sql_text := 'select f_segtableoid, f_mgeometry_column, f_sequence_name from mgeometry_columns where f_table_name = ' || quote_literal(TG_RELNAME);
    execute sql_text into records;
    
    segtable_oid := records.f_segtableoid;
    segcolumn_name := records.f_mgeometry_column;
    sequence_name := records.f_sequence_name;

    sql_text := 'select nextval(' || quote_literal(sequence_name) || ')';
        
    execute sql_text into moid;

    
    RAISE NOTICE 'sql_text : %', sql_text;
    RAISE NOTICE 'segtable_oid : %', segtable_oid;
    RAISE NOTICE 'segcolumn_name : %', segcolumn_name;
    RAISE NOTICE 'sequence_name : %', sequence_name;
    RAISE NOTICE 'moid : %', moid;
    
    NEW.mvideo = (moid);    
        
    return NEW;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.insert_mvideo()
  OWNER TO postgres;



CREATE or replace FUNCTION public.delete_mvideo()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF 
AS $BODY$
DECLARE        
    delete_mpoint        mpoint;
    delete_id        integer;
    
    records            record;
    delete_record        record;
    
    BEGIN
    execute 'select f_mgeometry_segtable_name, f_mgeometry_column from mgeometry_columns where f_table_name = ' || quote_literal(TG_RELNAME)
    into records;

    delete_record := OLD;

    /*
    execute 'select ' || column_name || ' from ' || delete_record
    into delete_trajectory;
    */
    
    delete_mpoint := OLD.mvideo;
    delete_id := delete_mpoint.id;
    
    execute 'DELETE FROM ' || quote_ident(records.f_mgeometry_segtable_name) || ' WHERE mpid = ' || delete_id;

    return NULL;

    END;
$BODY$;

ALTER FUNCTION public.delete_mvideo()
    OWNER TO postgres;
	
	
	
	
	CREATE OR REPLACE FUNCTION public.m_astext(mpoint)
RETURNS text AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	mgeometry_prefix	char(50);
	mgeometry_suffix	char(50);
	f_mgeometry_segtable_name	char(200);
	sql					text;
	trajid				integer;
	mpid                integer;
	points				text;
	times				text;
	results				text;
	mpoints				text;
BEGIN
	mgeometry_prefix := 'mpseq_' ;  
	mgeometry_suffix := 'mpoint';
	if(f_mgeometry.timeline IS NULL) then
	sql := 'select f_segtableoid from mgeometry_columns where type = ' || quote_literal(mgeometry_suffix);
    RAISE DEBUG '%', sql;
    EXECUTE sql INTO trajid;
	
    f_mgeometry_segtable_name := mgeometry_prefix || trajid ;
    mpid := f_mgeometry.id;
	
	sql := 'select tpseg from ' || (f_mgeometry_segtable_name) ||' where mpid = ' ||(mpid);
    RAISE DEBUG '%', sql;
    EXECUTE sql INTO points;
	sql := 'select time from ' || (f_mgeometry_segtable_name) ||' where mpid = ' ||(mpid);
    RAISE DEBUG '%', sql;
    EXECUTE sql INTO times;
	results := 'mpoint '|| points||';'||times;
	else 
	results := 'mpoint '|| quote_literal(f_mgeometry.coordinates)||';'||quote_literal(f_mgeometry.timeline);
	end if;
	mpoints := m_astext(results);	
	return mpoints;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;	


	
	
CREATE OR REPLACE FUNCTION public.M_Overlaps(mpoint, character varying)
RETURNS boolean AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mvideos				boolean;
BEGIN
	results := m_astext(f_mgeometry);
	mvideos := m_overlaps(results, f_period);
	RAISE INFO '%', mvideos;
	return mvideos;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;




CREATE OR REPLACE FUNCTION public.M_Slice(mpoint, character varying)
RETURNS mpoint AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mpoints				text;
	res					mpoint;
BEGIN
	results := m_astext(f_mgeometry);
	mpoints := m_slice(results, f_period);
	IF (mpoints	 != 'MPOINT ()') THEN
	res.id :=  f_mgeometry.id;
	res.segid := f_mgeometry.segid;
	res.timeline := regexp_split_to_array(mpointtime(mpoints),',') ::timestamp without time zone[];
	res.coordinates := regexp_split_to_array(mpointpoint(mpoints),';') ::point[];		
	END IF;
	return res;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;




	
CREATE OR REPLACE FUNCTION public.M_Contains(mpoint, character varying)
RETURNS boolean AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mpoints				boolean;
BEGIN
	results := m_astext(f_mgeometry);
	mpoints := m_contains(results, f_period);
	RAISE INFO '%', mpoints;
	return mpoints;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;



	
CREATE OR REPLACE FUNCTION public.M_Equals(mpoint, character varying)
RETURNS boolean AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mpoints				boolean;
BEGIN
	results := m_astext(f_mgeometry);
	mpoints := m_equals(results, f_period);
	RAISE INFO '%', mpoints;
	return mpoints;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
CREATE OR REPLACE FUNCTION public.M_Precedes(mpoint, character varying)
RETURNS boolean AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mpoints				boolean;
BEGIN
	results := m_astext(f_mgeometry);
	mpoints := m_precedes(results, f_period);
	RAISE INFO '%', mpoints;
	return mpoints;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
CREATE OR REPLACE FUNCTION public.M_Succeeds(mpoint, character varying)
RETURNS boolean AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mpoints				boolean;
BEGIN
	results := m_astext(f_mgeometry);
	mpoints := m_succeeds(results, f_period);
	RAISE INFO '%', mpoints;
	return mpoints;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
	
CREATE OR REPLACE FUNCTION public.M_ImmPrecedes(mpoint, character varying)
RETURNS boolean AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mpoints				boolean;
BEGIN
	results := m_astext(f_mgeometry);
	mpoints := m_immprecedes(results, f_period);
	RAISE INFO '%', mpoints;
	return mpoints;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
CREATE OR REPLACE FUNCTION public.M_ImmSucceeds(mpoint, character varying)
RETURNS boolean AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mpoints				boolean;
BEGIN
	results := m_astext(f_mgeometry);
	mpoints := m_immsucceeds(results, f_period);
	RAISE INFO '%', mpoints;
	return mpoints;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
	
CREATE OR REPLACE FUNCTION public.M_ImmSucceeds(mpoint, character varying)
RETURNS boolean AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mpoints				boolean;
BEGIN
	results := m_astext(f_mgeometry);
	mpoints := m_immsucceeds(results, f_period);
	RAISE INFO '%', mpoints;
	return mpoints;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
CREATE OR REPLACE FUNCTION public.M_At(mpoint, integer )
RETURNS mpoint AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_num				alias for $2;
	results				text;
	mpoints				text;
	res					mpoint;
BEGIN
	results := m_astext(f_mgeometry);
	mpoints := m_at(results, f_num);
	IF (mpoints	 != 'MPOINT ()') THEN
	res.id :=  f_mgeometry.id;
	res.segid := f_mgeometry.segid;
	res.timeline := regexp_split_to_array(mpointtime(mpoints),',') ::timestamp without time zone[];
	res.coordinates := regexp_split_to_array(mpointpoint(mpoints),';') ::point[];	
	END IF;
	return res;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
CREATE OR REPLACE FUNCTION public.M_NumOf(mpoint)
RETURNS integer AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	results				text;
	res					integer;
BEGIN
	results := m_astext(f_mgeometry);
	res := m_numof(results);
	return res;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
CREATE OR REPLACE FUNCTION public.M_Time(mpoint)
RETURNS period AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	results				text;
	res					text;
	per					period;
	Longs				bigint[];
BEGIN
	results := m_astext(f_mgeometry);
	res := m_time(results);
	RAISE INFO '%', res;
	res := regexp_replace(res, '\)','');
	res :=regexp_replace(res, '\(','');
	Longs := regexp_split_to_array(res,' ') ::bigint[];	
	per.fromtime :=  Longs[1];
	per.totime :=  Longs[2];
	return per;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
CREATE OR REPLACE FUNCTION public.M_StartTime(mpoint)
RETURNS bigint AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	results				text;
	res					bigint;
BEGIN
	results := m_astext(f_mgeometry);
	res := m_starttime(results);
	return res;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
CREATE OR REPLACE FUNCTION public.M_EndTime(mpoint)
RETURNS bigint AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	results				text;
	res					bigint;
BEGIN
	results := m_astext(f_mgeometry);
	res := m_endtime(results);
	return res;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
CREATE OR REPLACE FUNCTION public.M_Spatial(mpoint)
RETURNS geometry AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	results				text;
	res					geometry;
BEGIN
	results := m_astext(f_mgeometry);
	res := ST_GeomFromText(m_spatial(results));
	return res;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
CREATE OR REPLACE FUNCTION public.M_Snapshot(mpoint, bigint)
RETURNS geometry AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_instant			alias for $2;
	results				text;
	mpoints				text;
	res					geometry;
BEGIN
	results := m_astext(f_mgeometry);
	mpoints := m_snapshot(results, f_instant);
	IF (mpoints	 != 'MPOINT ()') THEN
	res := ST_GeomFromText(mpoints);	
	END IF;
	return res;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
	
CREATE OR REPLACE FUNCTION public.m_astext(mvideo)
RETURNS text AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	mgeometry_prefix	char(50);
	mgeometry_suffix	char(50);
	f_mgeometry_segtable_name	char(200);
	sql					text;
	trajid				integer;
	mpid                integer;
	uris				text;
	points				text;
	angles				text;
	directions			text;
	distances			text;
	times				text;
	results				text;
	mvideos				text;
BEGIN
	mgeometry_prefix := 'mvseq_' ;  
	mgeometry_suffix := 'mvideo';
	if(f_mgeometry.times IS NULL) then
	sql := 'select f_segtableoid from mgeometry_columns where type = ' || quote_literal(mgeometry_suffix);
    RAISE DEBUG '%', sql;
    EXECUTE sql INTO trajid;
	
    f_mgeometry_segtable_name := mgeometry_prefix || trajid ;
    mpid := f_mgeometry.id;
	
	sql := 'select uri from ' || (f_mgeometry_segtable_name) ||' where mpid = ' ||(mpid);
    RAISE DEBUG '%', sql;
    EXECUTE sql INTO uris;
	sql := 'select tpseg from ' || (f_mgeometry_segtable_name) ||' where mpid = ' ||(mpid);
    RAISE DEBUG '%', sql;
    EXECUTE sql INTO points;
	sql := 'select angle from ' || (f_mgeometry_segtable_name) ||' where mpid = ' ||(mpid);
    RAISE DEBUG '%', sql;
    EXECUTE sql INTO angles;
	sql := 'select distance from ' || (f_mgeometry_segtable_name) ||' where mpid = ' ||(mpid);
    RAISE DEBUG '%', sql;
    EXECUTE sql INTO distances;
	sql := 'select direction from ' || (f_mgeometry_segtable_name) ||' where mpid = ' ||(mpid);
    RAISE DEBUG '%', sql;
    EXECUTE sql INTO directions;
	sql := 'select time from ' || (f_mgeometry_segtable_name) ||' where mpid = ' ||(mpid);
    RAISE DEBUG '%', sql;
    EXECUTE sql INTO times;
	results := 'mvideo '||uris ||';'|| points||';'||angles||';'||distances||';'||directions||';'||times;
	else 
	results := 'mvideo '|| quote_literal(f_mgeometry.uri)||';'||quote_literal(f_mgeometry.points)||';'||quote_literal(f_mgeometry.viewAngle )||';'||quote_literal(f_mgeometry.distance )||';'||quote_literal(f_mgeometry.direction )||';'||quote_literal(f_mgeometry.times );
	end if;
	mvideos := m_astext(results);
	return mvideos;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;


		
CREATE OR REPLACE FUNCTION public.M_Overlaps(mvideo, character varying)
RETURNS boolean AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mvideos				boolean;
BEGIN
	results := m_astext(f_mgeometry);
	mvideos := m_overlaps(results, f_period);
	RAISE INFO '%', mvideos;
	return mvideos;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	



CREATE OR REPLACE FUNCTION public.M_Slice(mvideo, character varying)
RETURNS mvideo AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mvideos				text;
	res					mvideo;
BEGIN
	results := m_astext(f_mgeometry);
	mvideos := m_slice(results, f_period);
	IF (mvideos != 'MVIDEO ()') THEN
	res.id :=  f_mgeometry.id;
	res.segid := f_mgeometry.segid;
	res.uri := regexp_split_to_array(mvideouri(mvideos),';') ::character varying[];
	res.times := regexp_split_to_array(mvideotime(mvideos),',') ::timestamp without time zone[];
	res.points := regexp_split_to_array(mvideopoint(mvideos),';') ::point[];		
	res.viewAngle :=regexp_split_to_array(mvideoangle(mvideos),';') ::double precision[];
	res.direction := regexp_split_to_array(mvideodirection(mvideos),';') ::double precision[];
	res.distance := regexp_split_to_array(mvideodistance(mvideos),';') ::double precision[];
	END IF;
	return res;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;





	
	
CREATE OR REPLACE FUNCTION public.M_Contains(mvideo, character varying)
RETURNS boolean AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mvideos				boolean;
BEGIN
	results := m_astext(f_mgeometry);
	mvideos := m_contains(results, f_period);
	RAISE INFO '%', mvideos;
	return mvideos;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;



	
CREATE OR REPLACE FUNCTION public.M_Equals(mvideo, character varying)
RETURNS boolean AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mvideos				boolean;
BEGIN
	results := m_astext(f_mgeometry);
	mvideos := m_equals(results, f_period);
	RAISE INFO '%', mvideos;
	return mvideos;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
CREATE OR REPLACE FUNCTION public.M_Precedes(mvideo, character varying)
RETURNS boolean AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mvideos				boolean;
BEGIN
	results := m_astext(f_mgeometry);
	mvideos := m_precedes(results, f_period);
	RAISE INFO '%', mvideos;
	return mvideos;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
CREATE OR REPLACE FUNCTION public.M_Succeeds(mvideo, character varying)
RETURNS boolean AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mvideos				boolean;
BEGIN
	results := m_astext(f_mgeometry);
	mvideos := m_succeeds(results, f_period);
	RAISE INFO '%', mvideos;
	return mvideos;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
	
CREATE OR REPLACE FUNCTION public.M_ImmPrecedes(mvideo, character varying)
RETURNS boolean AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mvideos				boolean;
BEGIN
	results := m_astext(f_mgeometry);
	mvideos := m_immprecedes(results, f_period);
	RAISE INFO '%', mvideos;
	return mvideos;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
CREATE OR REPLACE FUNCTION public.M_ImmSucceeds(mvideo, character varying)
RETURNS boolean AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mvideos				boolean;
BEGIN
	results := m_astext(f_mgeometry);
	mvideos := m_immsucceeds(results, f_period);
	RAISE INFO '%', mvideos;
	return mvideos;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
	
CREATE OR REPLACE FUNCTION public.M_ImmSucceeds(mvideo, character varying)
RETURNS boolean AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mvideos				boolean;
BEGIN
	results := m_astext(f_mgeometry);
	mvideos := m_immsucceeds(results, f_period);
	RAISE INFO '%', mvideos;
	return mvideos;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
CREATE OR REPLACE FUNCTION public.M_At(mvideo, integer )
RETURNS mvideo AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_num				alias for $2;
	results				text;
	mvideos				text;
	res					mvideo;
BEGIN
	results := m_astext(f_mgeometry);
	mvideos := m_at(results, f_num);
	IF (mvideos	 != 'MVIDEO ()') THEN
	res.id :=  f_mgeometry.id;
	res.segid := f_mgeometry.segid;
	res.uri := regexp_split_to_array(mvideouri(mvideos),';') ::character varying[];
	res.times := regexp_split_to_array(mvideotime(mvideos),',') ::timestamp without time zone[];
	res.points := regexp_split_to_array(mvideopoint(mvideos),';') ::point[];		
	res.viewAngle :=regexp_split_to_array(mvideoangle(mvideos),';') ::double precision[];
	res.direction := regexp_split_to_array(mvideodirection(mvideos),';') ::double precision[];
	res.distance := regexp_split_to_array(mvideodistance(mvideos),';') ::double precision[];
	END IF;
	return res;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
CREATE OR REPLACE FUNCTION public.M_NumOf(mvideo)
RETURNS integer AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	results				text;
	res					integer;
BEGIN
	results := m_astext(f_mgeometry);
	res := m_numof(results);
	return res;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
CREATE OR REPLACE FUNCTION public.M_Time(mvideo)
RETURNS period AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	results				text;
	res					text;
	per					period;
	Longs				bigint[];
BEGIN
	results := m_astext(f_mgeometry);
	res := m_time(results);
	RAISE INFO '%', res;
	res := regexp_replace(res, '\)','');
	res :=regexp_replace(res, '\(','');
	Longs := regexp_split_to_array(res,' ') ::bigint[];	
	per.fromtime :=  Longs[1];
	per.totime :=  Longs[2];
	return per;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
CREATE OR REPLACE FUNCTION public.M_StartTime(mvideo)
RETURNS bigint AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	results				text;
	res					bigint;
BEGIN
	results := m_astext(f_mgeometry);
	res := m_starttime(results);
	return res;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
CREATE OR REPLACE FUNCTION public.M_EndTime(mvideo)
RETURNS bigint AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	results				text;
	res					bigint;
BEGIN
	results := m_astext(f_mgeometry);
	res := m_endtime(results);
	return res;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
CREATE OR REPLACE FUNCTION public.M_Spatial(mvideo)
RETURNS geometry AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	results				text;
	res					geometry;
BEGIN
	results := m_astext(f_mgeometry);
	res := ST_GeomFromText(m_spatial(results));
	return res;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
CREATE OR REPLACE FUNCTION public.M_Snapshot(mvideo, bigint)
RETURNS geometry AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_instant			alias for $2;
	results				text;
	mvideos				text;
	res					geometry;
BEGIN
	results := m_astext(f_mgeometry);
	mvideos := m_snapshot(results, f_instant);
	IF (mvideos	 != 'MVIDEO ()') THEN
	res := ST_GeomFromText(mvideos);	
	END IF;
	return res;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
	
	
	
	
	-- FUNCTION: public.m_timeatcummulative(mpoint, double precision)

-- DROP FUNCTION public.m_timeatcummulative(mpoint, double precision);

CREATE OR REPLACE FUNCTION public.m_timeatcummulative(
	mpoint,
	double precision)
    RETURNS bigint
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mpoints				bigint;
BEGIN
	results := m_astext(f_mgeometry);
	mpoints := m_timeatcummulative(results, f_period);
	RAISE INFO '%', mpoints;
	return mpoints;
END
$BODY$;

ALTER FUNCTION public.m_timeatcummulative(mpoint, double precision)
    OWNER TO postgres;



-- FUNCTION: public.m_timeatcummulative(mvideo, double precision)

-- DROP FUNCTION public.m_timeatcummulative(mvideo, double precision);

CREATE OR REPLACE FUNCTION public.m_timeatcummulative(
	mvideo,
	double precision)
    RETURNS bigint
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mvideos				bigint;
BEGIN
	results := m_astext(f_mgeometry);
	mvideos := m_timeatcummulative(results, f_period);
	RAISE INFO '%', mvideos;
	return mvideos;
END
$BODY$;

ALTER FUNCTION public.m_timeatcummulative(mvideo, double precision)
    OWNER TO postgres;


-- FUNCTION: public.m_snaptogrid(mpoint, integer)

-- DROP FUNCTION public.m_snaptogrid(mpoint, integer);

CREATE OR REPLACE FUNCTION public.m_snaptogrid(
	mpoint,
	integer)
    RETURNS mpoint
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mpoints				text;
	res					mpoint;
BEGIN
	results := m_astext(f_mgeometry);
	mpoints := m_snaptogrid(results, f_period);
	res.id :=  f_mgeometry.id;
	res.segid := f_mgeometry.segid;
	res.timeline := regexp_split_to_array(mpointtime(mpoints),',') ::timestamp without time zone[];
	res.coordinates := regexp_split_to_array(mpointpoint(mpoints),';') ::point[];	
	return res;
END
$BODY$;

ALTER FUNCTION public.m_snaptogrid(mpoint, integer)
    OWNER TO postgres;

-- FUNCTION: public.m_snaptogrid(mvideo, integer)

-- DROP FUNCTION public.m_snaptogrid(mvideo, integer);

CREATE OR REPLACE FUNCTION public.m_snaptogrid(
	mvideo,
	integer)
    RETURNS mvideo
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mvideos				text;
	res					mvideo;
BEGIN
	results := m_astext(f_mgeometry);
	mvideos := m_snaptogrid(results, f_period);
	res.id :=  f_mgeometry.id;
	res.segid := f_mgeometry.segid;
	res.uri := regexp_split_to_array(mvideouri(mvideos),';') ::character varying[];
	res.times := regexp_split_to_array(mvideotime(mvideos),',') ::timestamp without time zone[];
	res.points := regexp_split_to_array(mvideopoint(mvideos),';') ::point[];		
	res.viewAngle :=regexp_split_to_array(mvideoangle(mvideos),';') ::double precision[];
	res.direction := regexp_split_to_array(mvideodirection(mvideos),';') ::double precision[];
	res.distance := regexp_split_to_array(mvideodistance(mvideos),';') ::double precision[];
	return res;
END
$BODY$;

ALTER FUNCTION public.m_snaptogrid(mvideo, integer)
    OWNER TO postgres;


-- FUNCTION: public.m_intersects(mpoint, mpoint)

-- DROP FUNCTION public.m_intersects(mpoint, mpoint);

CREATE OR REPLACE FUNCTION public.m_intersects(
	mpoint,
	mpoint)
    RETURNS mbool
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	mbools				text;
	res					mbool;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := m_astext(f_mgeometry2);
	mbools := m_intersects(results, results2);
	IF (mbools	 != 'MBOOL ()') THEN
	res.id :=  f_mgeometry1.id;
	res.segid := f_mgeometry1.segid;
	res.timeline := regexp_split_to_array(mbooltime(mbools),',') ::timestamp without time zone[];
	res.bools := regexp_split_to_array(mboolbool(mbools),';') ::boolean[];	
	END IF;
	return res;
END
$BODY$;

ALTER FUNCTION public.m_intersects(mpoint, mpoint)
    OWNER TO postgres;


-- FUNCTION: public.m_intersects(mvideo, mvideo)

-- DROP FUNCTION public.m_intersects(mvideo, mvideo);

CREATE OR REPLACE FUNCTION public.m_intersects(
	mvideo,
	mvideo)
    RETURNS mbool
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	mbools				text;
	res					mbool;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := m_astext(f_mgeometry2);
	mbools := m_intersects(results, results2);
	IF (mbools	 != 'MBOOL ()') THEN
	res.id :=  f_mgeometry1.id;
	res.segid := f_mgeometry1.segid;
	res.timeline := regexp_split_to_array(mbooltime(mbools),',') ::timestamp without time zone[];
	res.bools := regexp_split_to_array(mboolbool(mbools),';') ::boolean[];	
	END IF;
	return res;
END
$BODY$;

ALTER FUNCTION public.m_intersects(mvideo, mvideo)
    OWNER TO postgres;



-- FUNCTION: public.m_enters(mpoint, character varying)

-- DROP FUNCTION public.m_enters(mpoint, character varying);

CREATE OR REPLACE FUNCTION public.m_enters(
	mpoint,
	character varying)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	res					boolean;
BEGIN
	results := m_astext(f_mgeometry1);
	res := m_enters(results, f_mgeometry2);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_enters(mpoint, character varying)
    OWNER TO postgres;


-- FUNCTION: public.m_enters(mpoint, geometry)

-- DROP FUNCTION public.m_enters(mpoint, geometry);

CREATE OR REPLACE FUNCTION public.m_enters(
	mpoint,
	geometry)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	res					boolean;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := ST_AsText(f_mgeometry2);
	res := m_enters(results, results2);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_enters(mpoint, geometry)
    OWNER TO postgres;


-- FUNCTION: public.m_enters(mvideo, character varying)

-- DROP FUNCTION public.m_enters(mvideo, character varying);

CREATE OR REPLACE FUNCTION public.m_enters(
	mvideo,
	character varying)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	res					boolean;
BEGIN
	results := m_astext(f_mgeometry1);
	res := m_enters(results, f_mgeometry2);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_enters(mvideo, character varying)
    OWNER TO postgres;


-- FUNCTION: public.m_enters(mvideo, geometry)

-- DROP FUNCTION public.m_enters(mvideo, geometry);

CREATE OR REPLACE FUNCTION public.m_enters(
	mvideo,
	geometry)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	res					boolean;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := ST_AsText(f_mgeometry2);
	res := m_enters(results, results2);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_enters(mvideo, geometry)
    OWNER TO postgres;


-- FUNCTION: public.m_bypasses(mpoint, character varying)

-- DROP FUNCTION public.m_bypasses(mpoint, character varying);

CREATE OR REPLACE FUNCTION public.m_bypasses(
	mpoint,
	character varying)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	res					boolean;
BEGIN
	results := m_astext(f_mgeometry1);
	res := m_bypasses(results, f_mgeometry2);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_bypasses(mpoint, character varying)
    OWNER TO postgres;


-- FUNCTION: public.m_bypasses(mpoint, geometry)

-- DROP FUNCTION public.m_bypasses(mpoint, geometry);

CREATE OR REPLACE FUNCTION public.m_bypasses(
	mpoint,
	geometry)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	res					boolean;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := ST_AsText(f_mgeometry2);
	res := m_bypasses(results, results2);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_bypasses(mpoint, geometry)
    OWNER TO postgres;


-- FUNCTION: public.m_bypasses(mvideo, character varying)

-- DROP FUNCTION public.m_bypasses(mvideo, character varying);

CREATE OR REPLACE FUNCTION public.m_bypasses(
	mvideo,
	character varying)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	res					boolean;
BEGIN
	results := m_astext(f_mgeometry1);
	res := m_bypasses(results, f_mgeometry2);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_bypasses(mvideo, character varying)
    OWNER TO postgres;



-- FUNCTION: public.m_bypasses(mvideo, geometry)

-- DROP FUNCTION public.m_bypasses(mvideo, geometry);

CREATE OR REPLACE FUNCTION public.m_bypasses(
	mvideo,
	geometry)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	res					boolean;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := ST_AsText(f_mgeometry2);
	res := m_bypasses(results, results2);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_bypasses(mvideo, geometry)
    OWNER TO postgres;



-- FUNCTION: public.m_leaves(mpoint, character varying)

-- DROP FUNCTION public.m_leaves(mpoint, character varying);

CREATE OR REPLACE FUNCTION public.m_leaves(
	mpoint,
	character varying)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	res					boolean;
BEGIN
	results := m_astext(f_mgeometry1);
	res := m_leaves(results, f_mgeometry2);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_leaves(mpoint, character varying)
    OWNER TO postgres;


-- FUNCTION: public.m_leaves(mpoint, geometry)

-- DROP FUNCTION public.m_leaves(mpoint, geometry);

CREATE OR REPLACE FUNCTION public.m_leaves(
	mpoint,
	geometry)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	res					boolean;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := ST_AsText(f_mgeometry2);
	res := m_leaves(results, results2);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_leaves(mpoint, geometry)
    OWNER TO postgres;




-- FUNCTION: public.m_leaves(mvideo, character varying)

-- DROP FUNCTION public.m_leaves(mvideo, character varying);

CREATE OR REPLACE FUNCTION public.m_leaves(
	mvideo,
	character varying)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	res					boolean;
BEGIN
	results := m_astext(f_mgeometry1);
	res := m_leaves(results, f_mgeometry2);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_leaves(mvideo, character varying)
    OWNER TO postgres;




-- FUNCTION: public.m_leaves(mvideo, geometry)

-- DROP FUNCTION public.m_leaves(mvideo, geometry);

CREATE OR REPLACE FUNCTION public.m_leaves(
	mvideo,
	geometry)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	res					boolean;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := ST_AsText(f_mgeometry2);
	res := m_leaves(results, results2);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_leaves(mvideo, geometry)
    OWNER TO postgres;


-- FUNCTION: public.m_crosses(mpoint, character varying)

-- DROP FUNCTION public.m_crosses(mpoint, character varying);

CREATE OR REPLACE FUNCTION public.m_crosses(
	mpoint,
	character varying)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	res					boolean;
BEGIN
	results := m_astext(f_mgeometry1);
	res := m_crosses(results, f_mgeometry2);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_crosses(mpoint, character varying)
    OWNER TO postgres;


-- FUNCTION: public.m_crosses(mpoint, geometry)

-- DROP FUNCTION public.m_crosses(mpoint, geometry);

CREATE OR REPLACE FUNCTION public.m_crosses(
	mpoint,
	geometry)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	res					boolean;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := ST_AsText(f_mgeometry2);
	res := m_crosses(results, results2);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_crosses(mpoint, geometry)
    OWNER TO postgres;


-- FUNCTION: public.m_crosses(mvideo, character varying)

-- DROP FUNCTION public.m_crosses(mvideo, character varying);

CREATE OR REPLACE FUNCTION public.m_crosses(
	mvideo,
	character varying)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	res					boolean;
BEGIN
	results := m_astext(f_mgeometry1);
	res := m_crosses(results, f_mgeometry2);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_crosses(mvideo, character varying)
    OWNER TO postgres;


-- FUNCTION: public.m_crosses(mvideo, geometry)

-- DROP FUNCTION public.m_crosses(mvideo, geometry);

CREATE OR REPLACE FUNCTION public.m_crosses(
	mvideo,
	geometry)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	res					boolean;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := ST_AsText(f_mgeometry2);
	res := m_crosses(results, results2);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_crosses(mvideo, geometry)
    OWNER TO postgres;



-- FUNCTION: public.m_eventtime(mpoint, mpoint)

-- DROP FUNCTION public.m_eventtime(mpoint, mpoint);

CREATE OR REPLACE FUNCTION public.m_eventtime(
	mpoint,
	mpoint)
    RETURNS minstant
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	minstant			text;
	res					minstant;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := m_astext(f_mgeometry2);
	minstant := m_eventtime(results, results2);
	IF (minstant != 'NULL') THEN
	res.id :=  f_mgeometry1.id;
	res.segid := f_mgeometry1.segid;
	res.instant := regexp_split_to_array(minstanttime(minstant),',') ::timestamp without time zone[];	
	END IF;
	return res;
END
$BODY$;

ALTER FUNCTION public.m_eventtime(mpoint, mpoint)
    OWNER TO postgres;


-- FUNCTION: public.m_eventtime(mvideo, mvideo)

-- DROP FUNCTION public.m_eventtime(mvideo, mvideo);

CREATE OR REPLACE FUNCTION public.m_eventtime(
	mvideo,
	mvideo)
    RETURNS minstant
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	minstant			text;
	res					minstant;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := m_astext(f_mgeometry2);
	minstant := m_eventtime(results, results2);
	IF (minstant != 'NULL') THEN
	res.id :=  f_mgeometry1.id;
	res.segid := f_mgeometry1.segid;
	res.instant := regexp_split_to_array(minstanttime(minstant),',') ::timestamp without time zone[];	
	END IF;
	return res;
END
$BODY$;

ALTER FUNCTION public.m_eventtime(mvideo, mvideo)
    OWNER TO postgres;




-- FUNCTION: public.m_relationship(mpoint, mpoint)

-- DROP FUNCTION public.m_relationship(mpoint, mpoint);

CREATE OR REPLACE FUNCTION public.m_relationship(
	mpoint,
	mpoint)
    RETURNS mstring
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	mstring			text;
	res					mstring;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := m_astext(f_mgeometry2);
	mstring := m_relationship(results, results2);
	res.id :=  f_mgeometry1.id;
	res.segid := f_mgeometry1.segid;
	res.times := regexp_split_to_array(mstringtime(mstring),',') ::timestamp without time zone[];	
	res.mstrings := regexp_split_to_array(mstringstring(mstring),';') ::character varying[];	
	return res;
END
$BODY$;

ALTER FUNCTION public.m_relationship(mpoint, mpoint)
    OWNER TO postgres;



-- FUNCTION: public.m_relationship(mvideo, mvideo)

-- DROP FUNCTION public.m_relationship(mvideo, mvideo);

CREATE OR REPLACE FUNCTION public.m_relationship(
	mvideo,
	mvideo)
    RETURNS mstring
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	mstring			text;
	res					mstring;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := m_astext(f_mgeometry2);
	mstring := m_relationship(results, results2);
	res.id :=  f_mgeometry1.id;
	res.segid := f_mgeometry1.segid;
	res.times := regexp_split_to_array(mstringtime(mstring),',') ::timestamp without time zone[];	
	res.mstrings := regexp_split_to_array(mstringstring(mstring),';') ::character varying[];	
	return res;
END
$BODY$;

ALTER FUNCTION public.m_relationship(mvideo, mvideo)
    OWNER TO postgres;




-- FUNCTION: public.m_distance(mpoint, mpoint)

-- DROP FUNCTION public.m_distance(mpoint, mpoint);

CREATE OR REPLACE FUNCTION public.m_distance(
	mpoint,
	mpoint)
    RETURNS mdouble
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	mdouble			text;
	res					mdouble;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := m_astext(f_mgeometry2);
	mdouble := m_distance(results, results2);
	IF (mdouble != 'NULL') THEN
	res.id :=  f_mgeometry1.id;
	res.segid := f_mgeometry1.segid;
	res.timeline := regexp_split_to_array(mdoubletime(mdouble),',') ::timestamp without time zone[];	
	res.doubles := regexp_split_to_array(mdoubledouble(mdouble),';') ::double precision[];	
	END IF;
	return res;
END
$BODY$;

ALTER FUNCTION public.m_distance(mpoint, mpoint)
    OWNER TO postgres;



-- FUNCTION: public.m_distance(mvideo, mvideo)

-- DROP FUNCTION public.m_distance(mvideo, mvideo);

CREATE OR REPLACE FUNCTION public.m_distance(
	mvideo,
	mvideo)
    RETURNS mdouble
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	mdouble			text;
	res					mdouble;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := m_astext(f_mgeometry2);
	mdouble := m_distance(results, results2);
	IF (mdouble != 'NULL') THEN
	res.id :=  f_mgeometry1.id;
	res.segid := f_mgeometry1.segid;
	res.timeline := regexp_split_to_array(mdoubletime(mdouble),',') ::timestamp without time zone[];	
	res.doubles := regexp_split_to_array(mdoubledouble(mdouble),';') ::double precision[];	
	END IF;
	return res;
END
$BODY$;

ALTER FUNCTION public.m_distance(mvideo, mvideo)
    OWNER TO postgres;



-- FUNCTION: public.m_area(mpoint)

-- DROP FUNCTION public.m_area(mpoint);

CREATE OR REPLACE FUNCTION public.m_area(
	mpoint)
    RETURNS mdouble
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	results				text;
	results2				text;
	mdouble			text;
	res					mdouble;
BEGIN
	results := m_astext(f_mgeometry1);
	mdouble := m_area(results);
	res.id :=  f_mgeometry1.id;
	res.segid := f_mgeometry1.segid;
	res.timeline := regexp_split_to_array(mdoubletime(mdouble),',') ::timestamp without time zone[];	
	res.doubles := regexp_split_to_array(mdoubledouble(mdouble),';') ::double precision[];	
	return res;
END
$BODY$;

ALTER FUNCTION public.m_area(mpoint)
    OWNER TO postgres;


-- FUNCTION: public.m_area(mvideo)

-- DROP FUNCTION public.m_area(mvideo);

CREATE OR REPLACE FUNCTION public.m_area(
	mvideo)
    RETURNS mdouble
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	results				text;
	results2				text;
	mdouble			text;
	res					mdouble;
BEGIN
	results := m_astext(f_mgeometry1);
	mdouble := m_area(results);
	res.id :=  f_mgeometry1.id;
	res.segid := f_mgeometry1.segid;
	res.timeline := regexp_split_to_array(mdoubletime(mdouble),',') ::timestamp without time zone[];	
	res.doubles := regexp_split_to_array(mdoubledouble(mdouble),';') ::double precision[];	
	return res;
END
$BODY$;

ALTER FUNCTION public.m_area(mvideo)
    OWNER TO postgres;


-- FUNCTION: public.m_direction(mpoint)

-- DROP FUNCTION public.m_direction(mpoint);

CREATE OR REPLACE FUNCTION public.m_direction(
	mpoint)
    RETURNS mdouble
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	results				text;
	results2				text;
	mdouble			text;
	res					mdouble;
BEGIN
	results := m_astext(f_mgeometry1);
	mdouble := m_direction(results);
	res.id :=  f_mgeometry1.id;
	res.segid := f_mgeometry1.segid;
	res.timeline := regexp_split_to_array(mdoubletime(mdouble),',') ::timestamp without time zone[];	
	res.doubles := regexp_split_to_array(mdoubledouble(mdouble),';') ::double precision[];	
	return res;
END
$BODY$;

ALTER FUNCTION public.m_direction(mpoint)
    OWNER TO postgres;


-- FUNCTION: public.m_direction(mvideo)

-- DROP FUNCTION public.m_direction(mvideo);

CREATE OR REPLACE FUNCTION public.m_direction(
	mvideo)
    RETURNS mdouble
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	results				text;
	results2				text;
	mdouble			text;
	res					mdouble;
BEGIN
	results := m_astext(f_mgeometry1);
	mdouble := m_direction(results);
	res.id :=  f_mgeometry1.id;
	res.segid := f_mgeometry1.segid;
	res.timeline := regexp_split_to_array(mdoubletime(mdouble),',') ::timestamp without time zone[];	
	res.doubles := regexp_split_to_array(mdoubledouble(mdouble),';') ::double precision[];	
	return res;
END
$BODY$;

ALTER FUNCTION public.m_direction(mvideo)
    OWNER TO postgres;

-- FUNCTION: public.m_velocityattime(mpoint, bigint)

-- DROP FUNCTION public.m_velocityattime(mpoint, bigint);

CREATE OR REPLACE FUNCTION public.m_velocityattime(
	mpoint,
	bigint)
    RETURNS double precision
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_instant			alias for $2;
	results				text;
	results2				text;
	res					double precision;
BEGIN
	results := m_astext(f_mgeometry1);
	res := m_velocityattime(results, f_instant);
	if (res + 1 = 0.0)then res = null; end if;
	return res;
END
$BODY$;

ALTER FUNCTION public.m_velocityattime(mpoint, bigint)
    OWNER TO postgres;


-- FUNCTION: public.m_velocityattime(mvideo, bigint)

-- DROP FUNCTION public.m_velocityattime(mvideo, bigint);

CREATE OR REPLACE FUNCTION public.m_velocityattime(
	mvideo,
	bigint)
    RETURNS double precision
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_instant			alias for $2;
	results				text;
	results2				text;
	res					double precision;
BEGIN
	results := m_astext(f_mgeometry1);
	res := m_velocityattime(results, f_instant);
	if (res + 1 = 0.0)then res = null; end if;
	return res;
END
$BODY$;

ALTER FUNCTION public.m_velocityattime(mvideo, bigint)
    OWNER TO postgres;


-- FUNCTION: public.m_accelerationattime(mpoint, bigint)

-- DROP FUNCTION public.m_accelerationattime(mpoint, bigint);

CREATE OR REPLACE FUNCTION public.m_accelerationattime(
	mpoint,
	bigint)
    RETURNS double precision
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_instant			alias for $2;
	results				text;
	results2				text;
	res					double precision;
BEGIN
	results := m_astext(f_mgeometry1);
	res := m_accelerationattimetime(results, f_instant);
	if (res + 1 = 0.0)then res = null; end if;
	return res;
END
$BODY$;

ALTER FUNCTION public.m_accelerationattime(mpoint, bigint)
    OWNER TO postgres;


-- FUNCTION: public.m_accelerationattime(mvideo, bigint)

-- DROP FUNCTION public.m_accelerationattime(mvideo, bigint);

CREATE OR REPLACE FUNCTION public.m_accelerationattime(
	mvideo,
	bigint)
    RETURNS double precision
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_instant			alias for $2;
	results				text;
	results2				text;
	res					double precision;
BEGIN
	results := m_astext(f_mgeometry1);
	res := m_accelerationattimetime(results, f_instant);
	if (res + 1 = 0.0)then res = null; end if;
	return res;
END
$BODY$;

ALTER FUNCTION public.m_accelerationattime(mvideo, bigint)
    OWNER TO postgres;



-- FUNCTION: public.m_hausdorff(mpoint, mpoint)

-- DROP FUNCTION public.m_hausdorff(mpoint, mpoint);

CREATE OR REPLACE FUNCTION public.m_hausdorff(
	mpoint,
	mpoint)
    RETURNS double precision
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	res					double precision;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := m_astext(f_mgeometry2);
	res := m_hausdorff(results, results2);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_hausdorff(mpoint, mpoint)
    OWNER TO postgres;



-- FUNCTION: public.m_hausdorff(mvideo, mvideo)

-- DROP FUNCTION public.m_hausdorff(mvideo, mvideo);

CREATE OR REPLACE FUNCTION public.m_hausdorff(
	mvideo,
	mvideo)
    RETURNS double precision
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	res					double precision;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := m_astext(f_mgeometry2);
	res := m_hausdorff(results, results2);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_hausdorff(mvideo, mvideo)
    OWNER TO postgres;

-- FUNCTION: public.m_lcss(mpoint, mpoint, double precision, double precision)

-- DROP FUNCTION public.m_lcss(mpoint, mpoint, double precision, double precision);

CREATE OR REPLACE FUNCTION public.m_lcss(
	mpoint,
	mpoint,
	double precision,
	double precision)
    RETURNS double precision
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	f_theta			alias for $3;
	f_epsilon			alias for $4;
	results				text;
	results2				text;
	res					double precision;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := m_astext(f_mgeometry2);
	res := m_lcss(results, results2, f_theta, f_epsilon);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_lcss(mpoint, mpoint, double precision, double precision)
    OWNER TO postgres;



-- FUNCTION: public.m_lcss(mvideo, mvideo, double precision, double precision)

-- DROP FUNCTION public.m_lcss(mvideo, mvideo, double precision, double precision);

CREATE OR REPLACE FUNCTION public.m_lcss(
	mvideo,
	mvideo,
	double precision,
	double precision)
    RETURNS double precision
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	f_theta			alias for $3;
	f_epsilon			alias for $4;
	results				text;
	results2				text;
	res					double precision;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := m_astext(f_mgeometry2);
	res := m_lcss(results, results2, f_theta, f_epsilon);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_lcss(mvideo, mvideo, double precision, double precision)
    OWNER TO postgres;


-- FUNCTION: public.m_lcvs(mvideo, mvideo, double precision)

-- DROP FUNCTION public.m_lcvs(mvideo, mvideo, double precision);

CREATE OR REPLACE FUNCTION public.m_lcvs(
	mvideo,
	mvideo,
	double precision)
    RETURNS double precision
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	f_theta			alias for $3;
	results				text;
	results2				text;
	res					double precision;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := m_astext(f_mgeometry2);
	res := m_lcvs(results, results2, f_theta);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_lcvs(mvideo, mvideo, double precision)
    OWNER TO postgres;


-- FUNCTION: public.m_traclus(mpoint, mpoint)

-- DROP FUNCTION public.m_traclus(mpoint, mpoint);

CREATE OR REPLACE FUNCTION public.m_traclus(
	mpoint,
	mpoint)
    RETURNS double precision
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	res					double precision;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := m_astext(f_mgeometry2);
	res := m_traclus(results, results2);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_traclus(mpoint, mpoint)
    OWNER TO postgres;

-- FUNCTION: public.m_traclus(mvideo, mvideo)

-- DROP FUNCTION public.m_traclus(mvideo, mvideo);

CREATE OR REPLACE FUNCTION public.m_traclus(
	mvideo,
	mvideo)
    RETURNS double precision
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	res					double precision;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := m_astext(f_mgeometry2);
	res := m_traclus(results, results2);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_traclus(mvideo, mvideo)
    OWNER TO postgres;










CREATE OR REPLACE FUNCTION public.M_ANY(
	mbool)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry			alias for $1;
	mbools				text;
	results				text;
	bools				boolean;
BEGIN
	mbools := 'mbool '|| quote_literal(f_mgeometry.bools)||';'||quote_literal(f_mgeometry.timeline);
	results := m_astext(mbools);
	bools := m_any(results);
	RAISE INFO '%', bools;
	return bools;
END
$BODY$;

ALTER FUNCTION public.M_ANY(mbool)
    OWNER TO postgres;




CREATE OR REPLACE FUNCTION public.M_Max(
	mdouble)
    RETURNS double precision
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry			alias for $1;
	mdouble				text;
	res				double precision;
BEGIN
	mdouble := f_mgeometry.doubles;
	res := m_max(mdouble);
	return res;
END
$BODY$;
ALTER FUNCTION public.M_Max(mdouble)
    OWNER TO postgres;


CREATE OR REPLACE FUNCTION public.M_Max(
	mint)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry			alias for $1;
	mint				text;
	res				integer;
BEGIN
	mint := f_mgeometry.counts ;
	res := m_max(mint);
	return res;
END
$BODY$;
ALTER FUNCTION public.M_Max(mint)
    OWNER TO postgres;


CREATE OR REPLACE FUNCTION public.M_Min(
	mdouble)
    RETURNS double precision
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry			alias for $1;
	mdouble				text;
	res				double precision;
BEGIN
	mdouble := f_mgeometry.doubles;
	res := m_min(mdouble);
	return res;
END
$BODY$;
ALTER FUNCTION public.M_Min(mdouble)
    OWNER TO postgres;


CREATE OR REPLACE FUNCTION public.M_Min(
	mint)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry			alias for $1;
	mint				text;
	res				integer;
BEGIN
	mint := f_mgeometry.counts;
	res := m_min(mint);
	return res;
END
$BODY$;
ALTER FUNCTION public.M_Min(mint)
    OWNER TO postgres;
	
	
---- not finished   pljava

SELECT a.id, a.carnumber
FROM usertrajs a, usertrajs b 
WHERE  M_Max( M_Distance( a.mt, b.mt ) ) < 500.0;


CREATE OR REPLACE FUNCTION public.M_Avg(
	mdouble)
    RETURNS double precision
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry			alias for $1;
	mdouble				text;
	res				double precision;
BEGIN
	mdouble := f_mgeometry.doubles;
	res := m_avg(mdouble);
	return res;
END
$BODY$;
ALTER FUNCTION public.M_Avg(mdouble)
    OWNER TO postgres;


CREATE OR REPLACE FUNCTION public.M_Avg(
	mint)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry			alias for $1;
	mint				text;
	res				integer;
BEGIN
	mint := f_mgeometry.counts;
	res := m_avg(mint);
	return res;
END
$BODY$;
ALTER FUNCTION public.M_Avg(mint)
    OWNER TO postgres;
	
	
	
	
	
	------------
	
CREATE OR REPLACE FUNCTION public.M_Avg(
	mdouble, character varying)
    RETURNS double precision
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_duration		alias for $2;
	mdouble				text;
	f_slice			mdouble;
	res				double precision;
BEGIN
	f_slice := m_slice(f_mgeometry, f_duration);
	mdouble := f_slice.doubles;
	res := m_avg(mdouble);
	return res;
END
$BODY$;
ALTER FUNCTION public.M_Avg(mdouble, character varying)
    OWNER TO postgres;

CREATE OR REPLACE FUNCTION public.M_Avg(
	mint, character varying)
    RETURNS double precision
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry			alias for $1;
	mint				text;
	f_duration			alias for $2;
	mdouble				text;
	f_slice				mdouble;
	res					double precision;
BEGIN
	f_slice := m_slice(f_mgeometry, f_duration);
	mint := f_slice.doubles;
	res := m_avg(mint);
	return res;
END
$BODY$;
ALTER FUNCTION public.M_Avg(mint, character varying)
    OWNER TO postgres;
	
	---------------------------
		---------------------------
			---------------------------
				---------------------------
					---------------------------
						---------------------------
							---------------------------
								---------------------------
									---------------------------
										---------------------------
	
CREATE OR REPLACE FUNCTION public.M_DWithin(
	mvideo, mvideo, double precision)
    RETURNS boolean
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_mgeometry2		alias for $2;
	f_within			alias for $3;
	bools				text;
	bools2				text;
	res				double precision;
BEGIN
	bools := m_astext(f_mgeometry);
	bools2 := m_astext(f_mgeometry2);
	res := m_dwithin(bools, bools2, f_within);
	return res;
END
$BODY$;
ALTER FUNCTION public.M_DWithin(mvideo, mvideo, double precision)
    OWNER TO postgres;


CREATE OR REPLACE FUNCTION public.m_dwithin(
	mvideo,
	mvideo,
	double precision)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_mgeometry2		alias for $2;
	f_within			alias for $3;
	bools				text;
	bools2				text;
	res				boolean;
BEGIN
	bools := m_astext(f_mgeometry);
	bools2 := m_astext(f_mgeometry2);
	res := m_dwithin(bools, bools2, f_within);
	return res;
END
$BODY$;

ALTER FUNCTION public.m_dwithin(mvideo, mvideo, double precision)
    OWNER TO postgres;

-----------------------------------

----m_lattice(); pljava

CREATE OR REPLACE FUNCTION public.M_Lattice(mpoint, bigint)
RETURNS mpoint AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mpoints				text;
	res					mpoint;
BEGIN
	results := m_astext(f_mgeometry);
	mpoints := m_lattice(results, f_period);
	IF (mpoints	 != 'MPOINT ()') THEN
	res.id :=  f_mgeometry.id;
	res.segid := f_mgeometry.segid;
	res.timeline := regexp_split_to_array(mpointtime(mpoints),',') ::timestamp without time zone[];
	res.coordinates := regexp_split_to_array(mpointpoint(mpoints),';') ::point[];		
	END IF;
	return res;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;




CREATE OR REPLACE FUNCTION public.M_Lattice(mvideo, bigint)
RETURNS mvideo AS 
$BODY$
DECLARE
	f_mgeometry			alias for $1;
	f_period			alias for $2;
	results				text;
	mvideos				text;
	res					mvideo;
BEGIN
	results := m_astext(f_mgeometry);
	mvideos := m_lattice(results, f_period);
	raise info '%', mvideos;
	IF (mvideos != 'MVIDEO ()') THEN
	res.id :=  f_mgeometry.id;
	res.segid := f_mgeometry.segid;
	res.uri := regexp_split_to_array(mvideouri(mvideos),';') ::character varying[];
	res.times := regexp_split_to_array(mvideotime(mvideos),',') ::timestamp without time zone[];
	res.points := regexp_split_to_array(mvideopoint(mvideos),';') ::point[];		
	res.viewAngle :=regexp_split_to_array(mvideoangle(mvideos),';') ::double precision[];
	res.direction := regexp_split_to_array(mvideodirection(mvideos),';') ::double precision[];
	res.distance := regexp_split_to_array(mvideodistance(mvideos),';') ::double precision[];
	END IF;
	return res;
END
$BODY$
	LANGUAGE plpgsql VOLATILE STRICT
	COST 100;
-----m_intersects

CREATE OR REPLACE FUNCTION public.m_intersects(
	mpoint,
	character varying)
    RETURNS mbool
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	mbools				text;
	res					mbool;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := f_mgeometry2;
	mbools := m_intersects(results, results2);
	IF (mbools	 != 'MBOOL ()') THEN
	res.id :=  f_mgeometry1.id;
	res.segid := f_mgeometry1.segid;
	res.timeline := regexp_split_to_array(mbooltime(mbools),',') ::timestamp without time zone[];
	res.bools := regexp_split_to_array(mboolbool(mbools),';') ::boolean[];	
	END IF;
	return res;
END
$BODY$;

ALTER FUNCTION public.m_intersects(mpoint, character varying)
    OWNER TO postgres;



CREATE OR REPLACE FUNCTION public.m_intersects(
	mvideo,
	character varying)
    RETURNS mbool
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	mbools				text;
	res					mbool;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := f_mgeometry2;
	mbools := m_intersects(results, results2);
	IF (mbools	 != 'MBOOL ()') THEN
	res.id :=  f_mgeometry1.id;
	res.segid := f_mgeometry1.segid;
	res.timeline := regexp_split_to_array(mbooltime(mbools),',') ::timestamp without time zone[];
	res.bools := regexp_split_to_array(mboolbool(mbools),';') ::boolean[];	
	END IF;
	return res;
END
$BODY$;

ALTER FUNCTION public.m_intersects(mvideo, character varying)
    OWNER TO postgres;
	
	
	

CREATE OR REPLACE FUNCTION public.m_distance(
	mvideo,
	character varying)
    RETURNS mdouble
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	mdoubles				text;
	res					mdouble;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := f_mgeometry2;
	mdoubles := m_distance(results, results2);
	IF (mdoubles	 != 'NULL') THEN
	res.id :=  f_mgeometry1.id;
	res.segid := f_mgeometry1.segid;
	res.timeline := regexp_split_to_array(mdoubletime(mdoubles),',') ::timestamp without time zone[];
	res.doubles := regexp_split_to_array(mdoubledouble(mdoubles),';') ::double precision[];	
	END IF;
	return res;
END
$BODY$;

ALTER FUNCTION public.m_distance(mvideo, character varying)
    OWNER TO postgres;
	
	
	
	

CREATE OR REPLACE FUNCTION public.m_distance(
	mpoint,
	character varying)
    RETURNS mdouble
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE STRICT 
AS $BODY$
DECLARE
	f_mgeometry1			alias for $1;
	f_mgeometry2			alias for $2;
	results				text;
	results2				text;
	mdoubles				text;
	res					mdouble;
BEGIN
	results := m_astext(f_mgeometry1);
	results2 := f_mgeometry2;
	mdoubles := m_distance(results, results2);
	IF (mdoubles	 != 'NULL') THEN
	res.id :=  f_mgeometry1.id;
	res.segid := f_mgeometry1.segid;
	res.timeline := regexp_split_to_array(mdoubletime(mdoubles),',') ::timestamp without time zone[];
	res.doubles := regexp_split_to_array(mdoubledouble(mdoubles),';') ::double precision[];	
	END IF;
	return res;
END
$BODY$;

ALTER FUNCTION public.m_distance(mpoint, character varying)
    OWNER TO postgres;









	