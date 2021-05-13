

CREATE TYPE mcoordinate AS
(
	pointx double precision,
	pointy double precision,
	pointz double precision
);


CREATE TYPE period AS
(
	fromtime bigint,
	totime bigint
);


CREATE TYPE mdouble AS
(
	moid oid,
	segid  text,
	doubles double precision[],
	t timestamp without time zone[]
);

CREATE TYPE mpoint AS
(
	moid oid,
	segid  text,
	geo mcoordinate[],
	t timestamp without time zone[]
);

CREATE TYPE mbool AS
(
	moid oid,
	bools boolean[],
	t timestamp without time zone[]
);


