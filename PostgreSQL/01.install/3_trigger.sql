---Delete or Do not use
/*
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
    sql_text := 'select f_segtableoid, f_mgeometry_column, f_sequence_name from mgeometry_columns where f_table_name = ' || quote_literal(TG_RELNAME) || ' and type = '||quote_literal('mpoint');
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
    NEW.mt = (moid,segtable_oid,null,null);            
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
    execute 'select f_mgeometry_segtable_name, f_mgeometry_column from mgeometry_columns where f_table_name = ' || quote_literal(TG_RELNAME) || ' and type = '||quote_literal('mpoint')
    into records;
    delete_record := OLD;
    delete_mpoint := OLD.mt;
    delete_id := delete_mpoint.moid;    
    execute 'DELETE FROM ' || quote_ident(records.f_mgeometry_segtable_name) || ' WHERE mpid = ' || delete_id;
    return NULL;
    END;
$BODY$;
ALTER FUNCTION public.delete_mpoint()
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
    sql_text := 'select f_segtableoid, f_mgeometry_column, f_sequence_name from mgeometry_columns where f_table_name = ' || quote_literal(TG_RELNAME) || ' and type = '||quote_literal('mvideo');
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
    NEW.mv = (moid,segtable_oid,null,null,null,null,null,null);          
    return NEW;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.insert_mvideo()
  OWNER TO postgres;


select * from mgeometry_columns;
 
CREATE OR REPLACE FUNCTION public.insert_stphoto()
  RETURNS trigger AS
$BODY$
DECLARE    
    segcolumn_name        text;
    sequence_name        text;
    moid            text;    
    sql_text        text;
    records            record;        
 BEGIN    
     sql_text := 'select f_mgeometry_column, f_sequence_name from mgeometry_columns where f_table_name = ' || quote_literal(TG_RELNAME) || ' and type = '||quote_literal('stphoto');
    execute sql_text into records;    
	 RAISE NOTICE 'moid : %', records;
	 segcolumn_name := records.f_mgeometry_column;
    sequence_name := records.f_sequence_name;
    sql_text := 'select nextval(' || quote_literal(sequence_name) || ')';        
    execute sql_text into moid;    
    RAISE NOTICE 'sql_text : %', sql_text;
    RAISE NOTICE 'segcolumn_name : %', segcolumn_name;
    RAISE NOTICE 'sequence_name : %', sequence_name;
    RAISE NOTICE 'moid : %', moid;
    NEW.st = (moid,null,null,null,null,null,null,null);          
    return NEW;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.insert_stphoto()
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
    execute 'select f_mgeometry_segtable_name, f_mgeometry_column from mgeometry_columns where f_table_name = ' || quote_literal(TG_RELNAME) || ' and type = '||quote_literal('mvideo')
    into records;
    delete_record := OLD;
    delete_mpoint := OLD.mv;
    delete_id := delete_mpoint.moid;    
    execute 'DELETE FROM ' || quote_ident(records.f_mgeometry_segtable_name) || ' WHERE mpid = ' || delete_id;
    return NULL;
    END;
$BODY$;
ALTER FUNCTION public.delete_mvideo()
    OWNER TO postgres;
	
	
CREATE OR REPLACE FUNCTION public.insert_mdouble()
  RETURNS trigger AS
$BODY$
DECLARE    
    segtable_oid        text;
    segcolumn_name      text;
    sequence_name       text;
    moid            	text;
    sql_text        	text;
    records1            record;
	records2            record;
	records3            record;
	records4            record;
	records5            record;
	records6            record;
	accx				text;
	accy				text;
	accz				text;
	gyrox			text;
	gyroy			text;
	gyroz			text;
 BEGIN  
 	accx := 'accx';
	accy := 'accy';
	accz := 'accz';
	gyrox := 'gyrox';
	gyroy := 'gyroy';
	gyroz := 'gyroz';
    sql_text := 'select f_segtableoid, f_mgeometry_column, f_sequence_name from mgeometry_columns where f_mgeometry_column = '||quote_literal(accx)||' and f_table_name = ' || quote_literal(TG_RELNAME) || ' and type = '||quote_literal('mdouble');
    execute sql_text into records1;
    RAISE INFO 'records : %', records1;
    segtable_oid := records1.f_segtableoid;
    segcolumn_name := records1.f_mgeometry_column;
    sequence_name := records1.f_sequence_name;
    sql_text := 'select nextval(' || quote_literal(sequence_name) || ')';       
    execute sql_text into moid;    
   
    NEW.accx = (moid,segtable_oid,null,null);  
		
	sql_text := 'select f_segtableoid, f_mgeometry_column, f_sequence_name from mgeometry_columns where f_mgeometry_column = '||quote_literal(accy)||' and f_table_name = ' || quote_literal(TG_RELNAME)|| ' and type = '||quote_literal('mdouble');
    execute sql_text into records2;   
    segtable_oid := records2.f_segtableoid;
	segcolumn_name := records2.f_mgeometry_column;
    sequence_name := records2.f_sequence_name;
    sql_text := 'select nextval(' || quote_literal(sequence_name) || ')';       
    execute sql_text into moid;  
    NEW.accy = (moid,segtable_oid,null,null); 
	
	sql_text := 'select f_segtableoid, f_mgeometry_column, f_sequence_name from mgeometry_columns where f_mgeometry_column = '||quote_literal(accz)||' and f_table_name = ' || quote_literal(TG_RELNAME)|| ' and type = '||quote_literal('mdouble');
    execute sql_text into records3;    
    segtable_oid := records3.f_segtableoid;
	segcolumn_name := records3.f_mgeometry_column;
    sequence_name := records3.f_sequence_name;
    sql_text := 'select nextval(' || quote_literal(sequence_name) || ')';       
    execute sql_text into moid;  
    NEW.accz = (moid,segtable_oid,null,null);   
	
	sql_text := 'select f_segtableoid, f_mgeometry_column, f_sequence_name from mgeometry_columns where f_mgeometry_column = '||quote_literal(gyrox)||' and f_table_name = ' || quote_literal(TG_RELNAME)|| ' and type = '||quote_literal('mdouble');
    execute sql_text into records4;     
    segtable_oid := records4.f_segtableoid; 
	segcolumn_name := records4.f_mgeometry_column;
    sequence_name := records4.f_sequence_name;
    sql_text := 'select nextval(' || quote_literal(sequence_name) || ')';       
    execute sql_text into moid;  
    NEW.gyrox = (moid,segtable_oid,null,null); 
	
	sql_text := 'select f_segtableoid, f_mgeometry_column, f_sequence_name from mgeometry_columns where f_mgeometry_column = '||quote_literal(gyroy)||' and f_table_name = ' || quote_literal(TG_RELNAME)|| ' and type = '||quote_literal('mdouble');
    execute sql_text into records5;   
    segtable_oid := records5.f_segtableoid;
	segcolumn_name := records5.f_mgeometry_column;
    sequence_name := records5.f_sequence_name;
    sql_text := 'select nextval(' || quote_literal(sequence_name) || ')';       
    execute sql_text into moid;  
    NEW.gyroy = (moid,segtable_oid,null,null); 
	
	sql_text := 'select f_segtableoid, f_mgeometry_column, f_sequence_name from mgeometry_columns where f_mgeometry_column = '||quote_literal(gyroz)||' and f_table_name = ' || quote_literal(TG_RELNAME)|| ' and type = '||quote_literal('mdouble');
    execute sql_text into records6;
    segtable_oid := records6.f_segtableoid;
	segcolumn_name := records6.f_mgeometry_column;
    sequence_name := records6.f_sequence_name;
    sql_text := 'select nextval(' || quote_literal(sequence_name) || ')';       
    execute sql_text into moid;  
    NEW.gyroz = (moid,segtable_oid,null,null);     
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
    delete_mdouble     	mdouble;
    delete_id        	integer;
    records          	record;
    delete_record      	record;
	segcolumns			text;   
    BEGIN
    execute 'select f_mgeometry_segtable_name, f_mgeometry_column from mgeometry_columns where f_table_name = ' || quote_literal(TG_RELNAME)|| ' and type = '||quote_literal('mdouble')
    into records;
	RAISE INFO '%', records;
	execute 'select f_mgeometry_column from mgeometry_columns where f_table_name = ' || quote_literal(TG_RELNAME) || ' and type = '||quote_literal('mdouble')
    into segcolumns;
    delete_record := OLD;
	IF (segcolumns = 'md_accx')
	THEN
    delete_mdouble := OLD.md_accx;
	    delete_id := delete_mdouble.moid; 
	END IF;
	IF (segcolumns = 'md_accy')
	THEN
    delete_mdouble := OLD.md_accy;
	    delete_id := delete_mdouble.moid; 
	END IF;
	IF (segcolumns = 'md_accz')
	THEN
    delete_mdouble := OLD.md_accz;
	    delete_id := delete_mdouble.moid; 
	END IF;
	IF (segcolumns = 'md_gyrox')
	THEN
    delete_mdouble := OLD.md_gyrox;
	    delete_id := delete_mdouble.moid; 
	END IF;
	IF (segcolumns = 'md_gyroy')
	THEN
    delete_mdouble := OLD.md_gyroy;
	    delete_id := delete_mdouble.moid; 
	END IF;
	IF (segcolumns = 'md_gyroz')
	THEN
    delete_mdouble := OLD.md_gyroz;
	    delete_id := delete_mdouble.moid; 
	END IF;
    execute 'DELETE FROM ' || quote_ident(records.f_mgeometry_segtable_name) || ' WHERE mpid = ' || delete_id
	into records;
	RAISE INFO '%', records;
    return NULL;
    END;
$BODY$;
ALTER FUNCTION public.delete_mdouble()
    OWNER TO postgres;
    */