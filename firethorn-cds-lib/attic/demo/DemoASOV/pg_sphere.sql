BEGIN;

-- Creates a type 'spherical point'


CREATE FUNCTION spoint_in(CSTRING)
   RETURNS spoint
   AS '$libdir/pg_sphere' , 'spherepoint_in'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);


CREATE FUNCTION spoint_out(spoint)
   RETURNS CSTRING
   AS '$libdir/pg_sphere' , 'spherepoint_out'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);


CREATE TYPE spoint (
   internallength = 16,
   input   = spoint_in,
   output  = spoint_out
);

-- Creates a type 'spherical transformation'


CREATE FUNCTION strans_in(CSTRING)
   RETURNS strans
   AS '$libdir/pg_sphere', 'spheretrans_in'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);


CREATE FUNCTION strans_out(strans)
   RETURNS CSTRING
   AS '$libdir/pg_sphere', 'spheretrans_out'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);


CREATE TYPE strans (
   internallength = 32,
   input   = strans_in,
   output  = strans_out
);

-- Creates a type 'spherical circle'


CREATE FUNCTION scircle_in(CSTRING)
   RETURNS scircle
   AS '$libdir/pg_sphere', 'spherecircle_in'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);


CREATE FUNCTION scircle_out(scircle)
   RETURNS CSTRING
   AS '$libdir/pg_sphere', 'spherecircle_out'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);


CREATE TYPE scircle (
   internallength = 24,
   input   = scircle_in,
   output  = scircle_out
);

-- Creates a type 'spherical line'


CREATE FUNCTION sline_in(CSTRING)
   RETURNS sline
   AS '$libdir/pg_sphere', 'sphereline_in'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);


CREATE FUNCTION sline_out(sline)
   RETURNS CSTRING
   AS '$libdir/pg_sphere', 'sphereline_out'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);


CREATE TYPE sline (
   internallength = 32,
   input   = sline_in,
   output  = sline_out
);


-- Creates a type 'spherical ellipse'

CREATE FUNCTION sellipse_in(CSTRING)
   RETURNS sellipse
   AS '$libdir/pg_sphere', 'sphereellipse_in'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);


CREATE FUNCTION sellipse_out(sellipse)
   RETURNS CSTRING
   AS '$libdir/pg_sphere', 'sphereellipse_out'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);


CREATE TYPE sellipse (
   internallength = 40,
   input   = sellipse_in,
   output  = sellipse_out
);



-- Creates a type 'spherical polygon'


CREATE FUNCTION spoly_in(CSTRING)
   RETURNS spoly
   AS '$libdir/pg_sphere', 'spherepoly_in'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);


CREATE FUNCTION spoly_out(spoly)
   RETURNS CSTRING
   AS '$libdir/pg_sphere', 'spherepoly_out'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);


CREATE TYPE spoly (
   internallength = VARIABLE,
   input   = spoly_in,
   output  = spoly_out,
   storage = external
);



-- Creates a type 'spherical path'


CREATE FUNCTION spath_in(CSTRING)
   RETURNS spath
   AS '$libdir/pg_sphere', 'spherepath_in'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);


CREATE FUNCTION spath_out(spath)
   RETURNS CSTRING
   AS '$libdir/pg_sphere', 'spherepath_out'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);


CREATE TYPE spath (
   internallength = VARIABLE,
   input   = spath_in,
   output  = spath_out,
   storage = external
);



-- Creates a type 'spherical box'


CREATE FUNCTION sbox_in(CSTRING)
   RETURNS sbox
   AS '$libdir/pg_sphere' , 'spherebox_in'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);


CREATE FUNCTION sbox_out(sbox)
   RETURNS CSTRING
   AS '$libdir/pg_sphere' , 'spherebox_out'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);


CREATE TYPE sbox (
   internallength = 32,
   input   = sbox_in,
   output  = sbox_out
);

-- **************************
--
-- spherical point functions
--
-- **************************

CREATE FUNCTION pg_sphere_version( )
   RETURNS CSTRING
   AS '$libdir/pg_sphere' , 'pg_sphere_version'
   LANGUAGE 'c';

CREATE FUNCTION spoint(FLOAT8, FLOAT8)
   RETURNS spoint
   AS '$libdir/pg_sphere' , 'spherepoint_from_long_lat'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

CREATE FUNCTION set_sphere_output_precision( INT4 )
   RETURNS CSTRING
   AS '$libdir/pg_sphere' , 'set_sphere_output_precision'
   LANGUAGE 'c';

CREATE FUNCTION set_sphere_output( CSTRING )
   RETURNS CSTRING
   AS '$libdir/pg_sphere' , 'set_sphere_output'
   LANGUAGE 'c';

COMMENT ON FUNCTION spoint(FLOAT8, FLOAT8) IS
  'returns a spherical point from longitude ( arg1 ) , latitude ( arg2 )'; 

CREATE FUNCTION long(spoint)
   RETURNS FLOAT8
   AS '$libdir/pg_sphere' , 'spherepoint_long'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION long(spoint) IS
  'longitude of spherical point'; 

CREATE FUNCTION lat(spoint)
   RETURNS FLOAT8
   AS '$libdir/pg_sphere' , 'spherepoint_lat'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;
   
COMMENT ON FUNCTION lat(spoint) IS
  'latitude of spherical point'; 

CREATE FUNCTION x(spoint)
   RETURNS FLOAT8
   AS '$libdir/pg_sphere' , 'spherepoint_x'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;
   
COMMENT ON FUNCTION x(spoint) IS
  'cartesian x value of spherical point'; 


CREATE FUNCTION y(spoint)
   RETURNS FLOAT8
   AS '$libdir/pg_sphere' , 'spherepoint_y'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;
   
COMMENT ON FUNCTION y(spoint) IS
  'cartesian y value of spherical point'; 

CREATE FUNCTION xyz(spoint)
   RETURNS FLOAT8[]
   AS '$libdir/pg_sphere' , 'spherepoint_xyz'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION xyz(spoint) IS
  'cartesian values of spherical point'; 


CREATE FUNCTION z(spoint)
   RETURNS FLOAT8
   AS '$libdir/pg_sphere' , 'spherepoint_z'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;
   
COMMENT ON FUNCTION z(spoint) IS
  'cartesian z value of spherical point'; 


-- ***************************
--
-- spherical point operators
--
-- ***************************

--
-- equal
--

CREATE FUNCTION spoint_equal(spoint,spoint)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoint_equal'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION spoint_equal(spoint,spoint) IS
  'returns true, if spherical points are equal'; 

CREATE OPERATOR  = (
   LEFTARG    = spoint,
   RIGHTARG   = spoint,
   COMMUTATOR = =,
   NEGATOR    = <>,
   PROCEDURE  = spoint_equal,
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR = ( spoint, spoint ) IS
  'true, if spherical points are equal'; 

--
-- not equal
--

CREATE FUNCTION spoint_equal_neg (spoint,spoint)
   RETURNS BOOL
   AS 'SELECT NOT spoint_equal($1,$2);'
   LANGUAGE 'SQL'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION spoint_equal_neg (spoint,spoint) IS
  'returns true, if spherical points are not equal'; 


CREATE OPERATOR  <> (
   LEFTARG    = spoint,
   RIGHTARG   = spoint,
   COMMUTATOR = <>,
   NEGATOR    = =,
   PROCEDURE  = spoint_equal_neg ,
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR <> ( spoint, spoint ) IS
  'true, if spherical points are not equal'; 

--
-- distance between points
--

CREATE FUNCTION dist(spoint,spoint)
   RETURNS FLOAT8
   AS '$libdir/pg_sphere' , 'spherepoint_distance'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION dist(spoint,spoint) IS
  'distance between spherical points'; 

CREATE OPERATOR  <-> (
   LEFTARG    = spoint,
   RIGHTARG   = spoint,
   COMMUTATOR = '<->',
   PROCEDURE  = dist
);

COMMENT ON OPERATOR <-> ( spoint, spoint ) IS
  'distance between spherical points'; 



-- spherical transformation functions

CREATE FUNCTION strans_zxz(strans)
   RETURNS strans
   AS '$libdir/pg_sphere' , 'spheretrans_zxz'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans_zxz(strans) IS
  'returns Euler transformation as ZXZ transformation'; 

CREATE FUNCTION strans( FLOAT8, FLOAT8, FLOAT8 )
   RETURNS strans
   AS '$libdir/pg_sphere' , 'spheretrans_from_float8'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans( FLOAT8, FLOAT8, FLOAT8 ) IS
  'returns an transformation object using Euler angles (ZXZ)'; 

CREATE FUNCTION strans( FLOAT8, FLOAT8, FLOAT8, CSTRING )
   RETURNS strans
   AS '$libdir/pg_sphere' , 'spheretrans_from_float8_and_type'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans( FLOAT8, FLOAT8, FLOAT8, CSTRING ) IS
  'returns an transformation object using Euler angles and axis'; 

CREATE FUNCTION phi( strans )
   RETURNS FLOAT8
   AS '$libdir/pg_sphere' , 'spheretrans_phi'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION phi( strans ) IS
  'returns the first angle of Euler angles of a transformation object'; 

CREATE FUNCTION theta( strans )
   RETURNS FLOAT8
   AS '$libdir/pg_sphere' , 'spheretrans_theta'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION theta( strans ) IS
  'returns the second angle of Euler angles of a transformation object'; 

CREATE FUNCTION psi( strans )
   RETURNS FLOAT8
   AS '$libdir/pg_sphere' , 'spheretrans_psi'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION psi( strans ) IS
  'returns the third angle of Euler angles of a transformation object'; 

CREATE FUNCTION axes( strans )
   RETURNS CHARACTER(3)
   AS '$libdir/pg_sphere' , 'spheretrans_type'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION axes ( strans ) IS
  'returns the axis of Euler angles of a transformation object'; 


-- spherical transformation operators

CREATE FUNCTION strans_equal(strans,strans)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spheretrans_equal'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans_equal(strans,strans) IS
  'returns true, if Euler transformations are equal'; 


CREATE OPERATOR  = (
   LEFTARG    = strans,
   RIGHTARG   = strans,
   COMMUTATOR = = ,
   NEGATOR    = <>,
   PROCEDURE  = strans_equal,
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR = ( strans, strans ) IS
  'true, if Euler transformations are equal'; 


CREATE FUNCTION strans_not_equal(strans,strans)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spheretrans_not_equal'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans_not_equal(strans,strans) IS
  'returns true, if Euler transformations are not equal'; 

CREATE OPERATOR  <> (
   LEFTARG    = strans,
   RIGHTARG   = strans,
   COMMUTATOR = <>,
   NEGATOR    = = ,
   PROCEDURE  = strans_not_equal,
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR <> ( strans, strans ) IS
  'true, if spherical Euler transformations are not equal'; 


CREATE FUNCTION strans(strans)
   RETURNS strans
   AS '$libdir/pg_sphere' , 'spheretrans'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans(strans) IS
  'returns Euler transformation'; 

CREATE OPERATOR + (
   RIGHTARG   = strans,
   PROCEDURE  = strans
);

COMMENT ON OPERATOR + ( NONE , strans ) IS
  'returns Euler transformation';

CREATE FUNCTION strans_invert(strans)
   RETURNS strans
   AS '$libdir/pg_sphere' , 'spheretrans_invert'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans_invert(strans) IS
  'returns inverse Euler transformation'; 

CREATE OPERATOR - (
   RIGHTARG   = strans,
   PROCEDURE  = strans_invert
);

COMMENT ON OPERATOR - ( NONE , strans ) IS
  'inverts Euler transformation';

CREATE FUNCTION strans_point( spoint, strans )
   RETURNS spoint
   AS '$libdir/pg_sphere' , 'spheretrans_point'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans_point ( spoint, strans ) IS
  'returns a transformated spherical point'; 

CREATE OPERATOR + (
   LEFTARG    = spoint,
   RIGHTARG   = strans,
   PROCEDURE  = strans_point
);

COMMENT ON OPERATOR + ( spoint, strans ) IS
  'transforms a spherical point '; 

CREATE FUNCTION strans_point_inverse( spoint, strans )
   RETURNS spoint
   AS '$libdir/pg_sphere' , 'spheretrans_point_inverse'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans_point_inverse ( spoint, strans ) IS
  'returns a inverse transformated spherical point'; 

CREATE OPERATOR - (
   LEFTARG    = spoint,
   RIGHTARG   = strans,
   PROCEDURE  = strans_point_inverse
);

COMMENT ON OPERATOR - ( spoint, strans ) IS
  'transforms inverse a spherical point ';

CREATE FUNCTION strans_trans( strans, strans )
   RETURNS strans
   AS '$libdir/pg_sphere' , 'spheretrans_trans'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans_trans ( strans, strans ) IS
  'returns a transformated Euler transformation'; 

CREATE OPERATOR + (
   LEFTARG    = strans,
   RIGHTARG   = strans,
   PROCEDURE  = strans_trans
);

COMMENT ON OPERATOR + ( strans, strans ) IS
  'transforms a Euler transformation '; 

CREATE FUNCTION strans_trans_inv( strans, strans )
   RETURNS strans
   AS '$libdir/pg_sphere' , 'spheretrans_trans_inv'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans_trans_inv ( strans, strans ) IS
  'returns a inverse transformated Euler transformation'; 

CREATE OPERATOR - (
   LEFTARG    = strans,
   RIGHTARG   = strans,
   PROCEDURE  = strans_trans_inv
);

COMMENT ON OPERATOR - ( strans, strans ) IS
  'transforms inverse a Euler transformation '; 
-- ****************************
--
-- spherical circle functions
--
-- ****************************


CREATE FUNCTION area(scircle)
   RETURNS FLOAT8
   AS '$libdir/pg_sphere', 'spherecircle_area'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;
   
COMMENT ON FUNCTION area(scircle) IS
  'area of spherical circle'; 

CREATE FUNCTION radius(scircle)
   RETURNS FLOAT8
   AS '$libdir/pg_sphere', 'spherecircle_radius'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;
   
COMMENT ON FUNCTION radius(scircle) IS
  'radius of spherical circle'; 

CREATE FUNCTION scircle(spoint, float8 )
   RETURNS scircle
   AS '$libdir/pg_sphere' , 'spherecircle_by_center'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION scircle(spoint,float8 ) IS
  'spherical circle with spherical point as center and float8 as radius in radians'; 


--
-- Casting point as circle
--

CREATE FUNCTION scircle(spoint)
   RETURNS scircle
   AS '$libdir/pg_sphere' , 'spherepoint_to_circle'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION scircle(spoint) IS
  'spherical circle with radius 0 and spherical point as center'; 

CREATE CAST (spoint AS scircle)
    WITH FUNCTION scircle(spoint)
    AS IMPLICIT;


-- **************************
--
-- spherical circle operators
--
-- **************************


--
-- equal
--

CREATE FUNCTION scircle_equal(scircle,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_equal'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION scircle_equal(scircle,scircle) IS
  'returns true, if spherical circles are equal'; 

CREATE OPERATOR  = (
   LEFTARG    = scircle,
   RIGHTARG   = scircle,
   COMMUTATOR = = ,
   NEGATOR    = <>,
   PROCEDURE  = scircle_equal,
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR = ( scircle, scircle ) IS
  'true, if spherical circles are equal'; 


--
-- not equal
--

CREATE FUNCTION scircle_equal_neg(scircle,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_equal_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION scircle_equal_neg(scircle,scircle) IS
  'returns true, if spherical circles are not equal'; 

CREATE OPERATOR  <> (
   LEFTARG    = scircle,
   RIGHTARG   = scircle,
   COMMUTATOR = <>,
   NEGATOR    = = ,
   PROCEDURE  = scircle_equal_neg,
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR <> ( scircle, scircle ) IS
  'true, if spherical circles are not equal'; 

--
-- overlap
--

CREATE FUNCTION scircle_overlap(scircle,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_overlap'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION scircle_overlap(scircle,scircle) IS
  'true if spherical circles overlap'; 

CREATE OPERATOR && (
   LEFTARG    = scircle,
   RIGHTARG   = scircle,
   PROCEDURE  = scircle_overlap,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);

COMMENT ON OPERATOR && ( scircle, scircle ) IS
  'true if spherical circles overlap'; 

--
-- not overlap
--

CREATE FUNCTION scircle_overlap_neg(scircle,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_overlap_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION scircle_overlap_neg(scircle,scircle) IS
  'true if spherical circles do not overlap'; 

CREATE OPERATOR !&& (
   LEFTARG    = scircle,
   RIGHTARG   = scircle,
   PROCEDURE  = scircle_overlap_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);

COMMENT ON OPERATOR !&& ( scircle, scircle ) IS
  'true if spherical circles do not overlap'; 

--
-- center of circle
--


CREATE FUNCTION center(scircle)
   RETURNS spoint
   AS '$libdir/pg_sphere' , 'spherecircle_center'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION center(scircle) IS
  'center of spherical circle'; 

CREATE OPERATOR @@ (
   RIGHTARG   = scircle,
   PROCEDURE  = center
);

COMMENT ON OPERATOR @@ ( NONE , scircle ) IS
  'center of spherical circle';


--
-- circumference
--

CREATE FUNCTION circum(scircle)
   RETURNS FLOAT8
   AS '$libdir/pg_sphere', 'spherecircle_circ'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION circum(scircle) IS
  'circumference of spherical circle'; 

CREATE OPERATOR @-@ (
   RIGHTARG   = scircle,
   PROCEDURE  = circum
);

COMMENT ON OPERATOR @-@ ( NONE , scircle ) IS
  'circumference of spherical circle';


--
-- circle is contained by circle
--

CREATE FUNCTION scircle_contained_by_circle(scircle,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_in_circle'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION scircle_contained_by_circle(scircle,scircle) IS
  'true if spherical circle is contained by spherical circle'; 

CREATE OPERATOR @ (
   LEFTARG    = scircle,
   RIGHTARG   = scircle,
   PROCEDURE  = scircle_contained_by_circle,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( scircle, scircle ) IS
  'true if spherical circle is contained by spherical circle'; 


--
-- circle is not contained by circle
--

CREATE FUNCTION scircle_contained_by_circle_neg (scircle,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_in_circle_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION scircle_contained_by_circle_neg (scircle,scircle) IS
  'true if spherical circle is not contained by spherical circle'; 

CREATE OPERATOR !@ (
   LEFTARG    = scircle,
   RIGHTARG   = scircle,
   PROCEDURE  = scircle_contained_by_circle_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( scircle, scircle ) IS
  'true if spherical circle is not contained by spherical circle'; 


--
-- circle contains circle
--

CREATE FUNCTION scircle_contains_circle (scircle,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_in_circle_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION scircle_contains_circle (scircle,scircle) IS
  'true if spherical circle contains spherical circle'; 

CREATE OPERATOR ~ (
   LEFTARG    = scircle,
   RIGHTARG   = scircle,
   PROCEDURE  = scircle_contains_circle,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( scircle, scircle ) IS
  'true if spherical circle contains spherical circle'; 

--
-- circle does not contain circle
--

CREATE FUNCTION scircle_contains_circle_neg (scircle,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_in_circle_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION scircle_contains_circle_neg (scircle,scircle) IS
  'true if spherical circle does not contain spherical circle'; 

CREATE OPERATOR !~ (
   LEFTARG    = scircle,
   RIGHTARG   = scircle,
   PROCEDURE  = scircle_contains_circle_neg,
   COMMUTATOR = '!@',
NEGATOR       = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( scircle, scircle ) IS
  'true if spherical circle does not contain spherical circle'; 


--
-- point is contained by circle
--

CREATE FUNCTION spoint_contained_by_circle(spoint,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoint_in_circle'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;
   
COMMENT ON FUNCTION spoint_contained_by_circle(spoint,scircle) IS
  'true if spherical point is contained by spherical circle'; 


CREATE OPERATOR @ (
   LEFTARG    = spoint,
   RIGHTARG   = scircle,
   PROCEDURE  = spoint_contained_by_circle,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( spoint, scircle ) IS
  'true if spherical point is contained by spherical circle'; 


--
-- point is not contained by circle
--

CREATE FUNCTION spoint_contained_by_circle_neg(spoint,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoint_in_circle_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION spoint_contained_by_circle_neg (spoint,scircle) IS
  'true if spherical point is not contained by spherical circle '; 


CREATE OPERATOR !@ (
   LEFTARG    = spoint,
   RIGHTARG   = scircle,
   PROCEDURE  = spoint_contained_by_circle_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( spoint, scircle ) IS
  'true if spherical point is not contained by spherical circle'; 


--
-- circle contains point
--

CREATE FUNCTION spoint_contained_by_circle_com(scircle,spoint)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoint_in_circle_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION spoint_contained_by_circle_com (scircle,spoint) IS
  'true if spherical circle contains spherical point '; 


CREATE OPERATOR ~ (
   LEFTARG    = scircle,
   RIGHTARG   = spoint,
   PROCEDURE  = spoint_contained_by_circle_com,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( scircle, spoint ) IS
  'true if spherical circle contains spherical point'; 


--
-- circle does not contain point
--

CREATE FUNCTION spoint_contained_by_circle_com_neg(scircle,spoint)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoint_in_circle_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION spoint_contained_by_circle_com_neg (scircle,spoint) IS
  'true if spherical circle does not contain spherical point '; 


CREATE OPERATOR !~ (
   LEFTARG    = scircle,
   RIGHTARG   = spoint,
   PROCEDURE  = spoint_contained_by_circle_com_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( scircle, spoint ) IS
  'true if spherical circle does not contain spherical point'; 


--
-- distance between circles
--

CREATE FUNCTION dist(scircle,scircle)
   RETURNS FLOAT8
   AS '$libdir/pg_sphere', 'spherecircle_distance'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;
   
COMMENT ON FUNCTION dist(scircle,scircle) IS
  'distance between two spherical circles'; 


CREATE OPERATOR  <-> (
   LEFTARG    = scircle,
   RIGHTARG   = scircle,
   COMMUTATOR = '<->',
   PROCEDURE  = dist
);

COMMENT ON OPERATOR <-> ( scircle, scircle ) IS
  'distance between two spherical circles'; 


--
-- distance between circle and point
--

CREATE FUNCTION dist(scircle,spoint)
   RETURNS FLOAT8
   AS '$libdir/pg_sphere', 'spherecircle_point_distance'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION dist(scircle,spoint) IS
  'distance between spherical circle and spherical point'; 


CREATE OPERATOR  <-> (
   LEFTARG    = scircle,
   RIGHTARG   = spoint,
   COMMUTATOR = '<->',
   PROCEDURE  = dist
);

COMMENT ON OPERATOR <-> ( scircle, spoint ) IS
  'distance between spherical circle and spherical point'; 



--
-- distance between point and circle
--

CREATE FUNCTION dist(spoint,scircle)
   RETURNS FLOAT8
   AS '$libdir/pg_sphere', 'spherecircle_point_distance_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION dist(spoint,scircle) IS
  'distance between spherical circle and spherical point'; 


CREATE OPERATOR  <-> (
   LEFTARG    = spoint,
   RIGHTARG   = scircle,
   COMMUTATOR = '<->',
   PROCEDURE  = dist
);

COMMENT ON OPERATOR <-> ( spoint, scircle ) IS
  'distance between spherical circle and spherical point'; 


--
--  Transformation of circle
--


CREATE FUNCTION strans_circle( scircle, strans )
   RETURNS scircle
   AS '$libdir/pg_sphere' , 'spheretrans_circle'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans_circle ( scircle, strans ) IS
  'returns a transformated spherical circle'; 


CREATE OPERATOR + (
   LEFTARG    = scircle,
   RIGHTARG   = strans,
   PROCEDURE  = strans_circle
);

COMMENT ON OPERATOR + ( scircle, strans ) IS
  'transforms a spherical circle '; 

CREATE FUNCTION strans_circle_inverse( scircle, strans )
   RETURNS scircle
   AS '$libdir/pg_sphere' , 'spheretrans_circle_inverse'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans_circle_inverse ( scircle, strans ) IS
  'returns a inverse transformated spherical circle'; 

CREATE OPERATOR - (
   LEFTARG    = scircle,
   RIGHTARG   = strans,
   PROCEDURE  = strans_circle_inverse
);

COMMENT ON OPERATOR - ( scircle, strans ) IS
  'transforms inverse a spherical circle ';
-- ****************************
--
-- spherical line functions
--
-- ****************************

CREATE FUNCTION sline( spoint, spoint )
   RETURNS sline
   AS '$libdir/pg_sphere' , 'sphereline_from_points'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sline( spoint, spoint ) IS
  'returns a spherical line using begin ( arg1 ) and end ( arg2 )'; 

CREATE FUNCTION sline( strans, float8 )
   RETURNS sline
   AS '$libdir/pg_sphere' , 'sphereline_from_trans'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sline( strans, float8 ) IS
  'returns a spherical line using Euler transformation ( arg1 ) and length ( arg2 )'; 

CREATE FUNCTION meridian( float8 )
   RETURNS sline
   AS '$libdir/pg_sphere' , 'sphereline_meridian'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION meridian( float8 ) IS
  'returns a spherical line as a meridian along longitude arg'; 


CREATE FUNCTION sl_beg( sline )
   RETURNS spoint
   AS '$libdir/pg_sphere' , 'sphereline_begin'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sl_beg( sline ) IS
  'returns the begin of a spherical line'; 

CREATE FUNCTION sl_end( sline )
   RETURNS spoint
   AS '$libdir/pg_sphere' , 'sphereline_end'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sl_end( sline ) IS
  'returns the end of a spherical line'; 


--
-- Cast line as Euler transformation
--

CREATE FUNCTION strans( sline )
   RETURNS strans
   AS '$libdir/pg_sphere' , 'spheretrans_from_line'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans( sline ) IS
  'converts line to a transformation (ZXZ)'; 

CREATE CAST (sline AS strans)
    WITH FUNCTION strans( sline )
    AS IMPLICIT;


--
-- Cast point as line
--

CREATE FUNCTION sline( spoint )
   RETURNS sline
   AS '$libdir/pg_sphere' , 'sphereline_from_point'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION sline( spoint ) IS
  'casts a spherical point to a spherical line'; 

CREATE CAST (spoint AS sline)
    WITH FUNCTION sline( spoint )
    AS IMPLICIT;





-- ***************************
--
-- spherical line operators
--
-- ***************************


--
-- equal
--

CREATE FUNCTION sline_equal(sline,sline)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereline_equal'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sline_equal(sline,sline) IS
  'returns true, if spherical lines are equal'; 

CREATE OPERATOR  = (
   LEFTARG    = sline,
   RIGHTARG   = sline,
   COMMUTATOR = = ,
   NEGATOR    = <>,
   PROCEDURE  = sline_equal,
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR = ( sline, sline ) IS
  'true, if spherical lines are equal'; 


--
-- not equal
--

CREATE FUNCTION sline_equal_neg(sline,sline)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereline_equal_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sline_equal_neg(sline,sline) IS
  'returns true, if spherical lines are not equal'; 

CREATE OPERATOR  <> (
   LEFTARG    = sline,
   RIGHTARG   = sline,
   COMMUTATOR = <>,
   NEGATOR    = = ,
   PROCEDURE  = sline_equal_neg,
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR <> ( sline, sline ) IS
  'true, if spherical lines are not equal'; 


--
-- length
--

CREATE FUNCTION length( sline )
   RETURNS FLOAT8
   AS '$libdir/pg_sphere' , 'sphereline_length'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION length( sline ) IS
  'returns the length of a spherical line ( in radians )'; 


CREATE OPERATOR @-@ (
   RIGHTARG   = sline,
   PROCEDURE  = length
);

COMMENT ON OPERATOR @-@ ( NONE , sline ) IS
  'length of spherical line';

--
-- swap begin and end
--

CREATE FUNCTION swap( sline )
   RETURNS sline
   AS '$libdir/pg_sphere' , 'sphereline_swap_beg_end'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION swap( sline ) IS
  'returns a spherical line with swapped begin and end';

CREATE OPERATOR - (
   RIGHTARG   = sline,
   PROCEDURE  = swap
);

COMMENT ON OPERATOR - ( NONE , sline ) IS
  'swaps begin and and of a spherical line';

--
-- turns path of line
--

CREATE FUNCTION turn( sline )
   RETURNS sline
   AS '$libdir/pg_sphere' , 'sphereline_turn'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION turn( sline ) IS
  'returns a turned spherical line but keeps begin and end';

CREATE OPERATOR ! (
   RIGHTARG   = sline,
   PROCEDURE  = turn
);

COMMENT ON OPERATOR ! ( NONE , sline ) IS
  'turns a spherical line, but keep begin and end,';



--
-- line crossing
--

CREATE FUNCTION sline_crosses( sline , sline )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereline_crosses'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sline_crosses( sline , sline ) IS
  'returns true if spherical lines cross'; 


CREATE OPERATOR  # (
   LEFTARG    = sline,
   RIGHTARG   = sline,
   COMMUTATOR =  #,
   NEGATOR    = !#,
   PROCEDURE  = sline_crosses,
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR # ( sline, sline ) IS
  'true, if spherical lines cross'; 


--
-- lines do not cross
--

CREATE FUNCTION sline_crosses_neg( sline , sline )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereline_crosses_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sline_crosses_neg( sline , sline ) IS
  'returns true if spherical lines do not cross'; 


CREATE OPERATOR  !# (
   LEFTARG    = sline,
   RIGHTARG   = sline,
   COMMUTATOR =  !#,
   NEGATOR    =  #,
   PROCEDURE  = sline_crosses_neg,
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !# ( sline, sline ) IS
  'true, if spherical lines do not cross'; 


--
-- lines overlap
--

CREATE FUNCTION sline_overlap( sline , sline )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereline_overlap'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sline_overlap( sline , sline ) IS
  'returns true if spherical lines overlap or cross'; 


CREATE OPERATOR && (
   LEFTARG    = sline,
   RIGHTARG   = sline,
   PROCEDURE  = sline_overlap,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);

COMMENT ON OPERATOR && ( sline, sline ) IS
  'true if spherical line overlap or cross'; 

--
-- lines do not overlap
--

CREATE FUNCTION sline_overlap_neg( sline , sline )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereline_overlap_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sline_overlap_neg( sline , sline ) IS
  'returns true if spherical lines do not overlap or cross'; 

CREATE OPERATOR !&& (
   LEFTARG    = sline,
   RIGHTARG   = sline,
   PROCEDURE  = sline_overlap,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);

COMMENT ON OPERATOR !&& ( sline, sline ) IS
  'true if spherical lines do not overlap or cross'; 



--
-- line contains point
--


CREATE FUNCTION sline_contains_point ( sline , spoint )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereline_cont_point'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sline_contains_point ( sline , spoint ) IS
  'returns true if spherical line contains spherical point'; 


CREATE OPERATOR ~ (
   LEFTARG    = sline,
   RIGHTARG   = spoint,
   PROCEDURE  = sline_contains_point,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( sline, spoint ) IS
  'true if spherical line contains spherical point'; 


--
-- point is contained by line
--


CREATE FUNCTION sline_contains_point_com ( spoint , sline )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereline_cont_point_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sline_contains_point_com ( spoint , sline ) IS
  'returns true if spherical line contains spherical point'; 


CREATE OPERATOR @ (
   LEFTARG    = spoint,
   RIGHTARG   = sline,
   PROCEDURE  = sline_contains_point_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( spoint, sline ) IS
  'true if spherical line contains spherical point'; 


--
-- line does not contain point
--

CREATE FUNCTION sline_contains_point_neg ( sline , spoint )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereline_cont_point_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sline_contains_point_neg ( sline , spoint ) IS
  'returns true if spherical line does not contain spherical point'; 

CREATE OPERATOR !~ (
   LEFTARG    = sline,
   RIGHTARG   = spoint,
   PROCEDURE  = sline_contains_point,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( sline, spoint ) IS
  'true if spherical line does not contain spherical point'; 


--
-- point is not contained by line
--


CREATE FUNCTION sline_contains_point_com_neg ( spoint , sline )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereline_cont_point_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sline_contains_point_com_neg ( spoint , sline ) IS
  'returns true if spherical line does not contain spherical point'; 

CREATE OPERATOR !@ (
   LEFTARG    = spoint,
   RIGHTARG   = sline,
   PROCEDURE  = sline_contains_point_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( spoint, sline ) IS
  'true if spherical line does not contain spherical point'; 


--
--  Transformation of line
--

CREATE FUNCTION strans_line( sline, strans )
   RETURNS sline
   AS '$libdir/pg_sphere' , 'spheretrans_line'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans_line ( sline, strans ) IS
  'returns a transformated spherical line'; 

CREATE OPERATOR + (
   LEFTARG    = sline,
   RIGHTARG   = strans,
   PROCEDURE  = strans_line
);

COMMENT ON OPERATOR + ( sline, strans ) IS
  'transforms a spherical line '; 


CREATE FUNCTION strans_line_inverse( sline, strans )
   RETURNS sline
   AS '$libdir/pg_sphere' , 'spheretrans_line_inverse'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans_line_inverse ( sline, strans ) IS
  'returns a inverse transformated spherical line'; 


CREATE OPERATOR - (
   LEFTARG    = sline,
   RIGHTARG   = strans,
   PROCEDURE  = strans_line_inverse
);

COMMENT ON OPERATOR - ( sline, strans ) IS
  'transforms inverse a spherical line'; 

--
-- line overlaps circle
--

CREATE FUNCTION sline_overlap_circle( sline , scircle )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereline_overlap_circle'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sline_overlap_circle( sline , scircle ) IS
  'returns true if spherical line overlaps spherical circle'; 


CREATE OPERATOR && (
   LEFTARG    = sline,
   RIGHTARG   = scircle,
   PROCEDURE  = sline_overlap_circle,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&' ,
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);

COMMENT ON OPERATOR && ( sline, scircle ) IS
  'true if spherical line overlaps spherical circle'; 

--
-- circle overlaps line    
--

CREATE FUNCTION sline_overlap_circle_com( scircle , sline )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereline_overlap_circle_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sline_overlap_circle_com( scircle , sline ) IS
  'returns true if spherical line overlaps spherical circle'; 


CREATE OPERATOR && (
   LEFTARG    = scircle,
   RIGHTARG   = sline,
   PROCEDURE  = sline_overlap_circle_com,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&' ,
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);

COMMENT ON OPERATOR && ( scircle, sline ) IS
  'true if spherical line overlaps spherical circle'; 

--
-- line does not overlap circle
--

CREATE FUNCTION sline_overlap_circle_neg( sline , scircle )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereline_overlap_circle_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sline_overlap_circle_neg( sline , scircle ) IS
  'returns true if spherical line does not overlap spherical circle'; 


CREATE OPERATOR !&& (
   LEFTARG    = sline,
   RIGHTARG   = scircle,
   PROCEDURE  = sline_overlap_circle_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&' ,
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);

COMMENT ON OPERATOR !&& ( sline, scircle ) IS
  'true if spherical line does not overlap spherical circle'; 

--
-- circle does not overlap line    
--

CREATE FUNCTION sline_overlap_circle_com_neg( scircle , sline )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereline_overlap_circle_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sline_overlap_circle_com_neg( scircle , sline ) IS
  'returns true if spherical line overlaps spherical circle'; 


CREATE OPERATOR !&& (
   LEFTARG    = scircle,
   RIGHTARG   = sline,
   PROCEDURE  = sline_overlap_circle_com,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);

COMMENT ON OPERATOR !&& ( scircle, sline ) IS
  'true if spherical line does not overlap spherical circle'; 


--
-- circle contains line
--


CREATE FUNCTION scircle_contains_line( scircle , sline )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_cont_line'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION scircle_contains_line( scircle , sline ) IS
  'returns true if spherical circle contains spherical line'; 


CREATE OPERATOR ~ (
   LEFTARG    = scircle,
   RIGHTARG   = sline,
   PROCEDURE  = scircle_contains_line,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( scircle, sline ) IS
  'true if spherical circle contains spherical line'; 


--
-- line is contained by circle
--


CREATE FUNCTION scircle_contains_line_com( sline , scircle )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_cont_line_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION scircle_contains_line_com( sline , scircle ) IS
  'returns true if spherical circle contains spherical line'; 

CREATE OPERATOR @ (
   LEFTARG    = sline,
   RIGHTARG   = scircle,
   PROCEDURE  = scircle_contains_line_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( sline, scircle ) IS
  'true if spherical circle contains spherical line'; 


--
-- circle does not contain line
--

CREATE FUNCTION scircle_contains_line_neg( scircle , sline )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_cont_line_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION scircle_contains_line_neg( scircle , sline ) IS
  'returns true if spherical circle does not contain spherical line'; 


CREATE OPERATOR !~ (
   LEFTARG    = scircle,
   RIGHTARG   = sline,
   PROCEDURE  = scircle_contains_line_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( scircle, sline ) IS
  'true if spherical circle does not contain spherical line'; 


--
-- line is not contained by circle
--

CREATE FUNCTION scircle_contains_line_com_neg( sline , scircle )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_cont_line_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION scircle_contains_line_com_neg( sline , scircle ) IS
  'returns true if spherical circle does not contain spherical line'; 


CREATE OPERATOR !@ (
   LEFTARG    = sline,
   RIGHTARG   = scircle,
   PROCEDURE  = scircle_contains_line_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( sline, scircle ) IS
  'true if spherical circle does not contain spherical line'; 

-- ****************************
--
-- spherical ellipse functions
--
-- ****************************


CREATE FUNCTION sellipse(spoint,float8,float8,float8)
   RETURNS sellipse
   AS '$libdir/pg_sphere' , 'sphereellipse_infunc'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION sellipse(spoint,float8,float8,float8) IS
  'returns spherical ellipse from center, radius1, radius2 and inclination'; 

CREATE FUNCTION inc(sellipse)
   RETURNS float8
   AS '$libdir/pg_sphere' , 'sphereellipse_incl'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION inc(sellipse) IS
  'inclination of spherical ellipse'; 

CREATE FUNCTION lrad(sellipse)
   RETURNS float8
   AS '$libdir/pg_sphere' , 'sphereellipse_rad1'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION lrad(sellipse) IS
  'large radius of spherical ellipse'; 

CREATE FUNCTION srad(sellipse)
   RETURNS float8
   AS '$libdir/pg_sphere' , 'sphereellipse_rad2'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION srad(sellipse) IS
  'small radius of spherical ellipse'; 



--
-- Casting point as ellipse
--

CREATE FUNCTION sellipse(spoint)
   RETURNS sellipse
   AS '$libdir/pg_sphere' , 'spherepoint_ellipse'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION sellipse(spoint) IS
  'returns spherical point as spherical ellipse'; 

CREATE CAST (spoint AS sellipse)
    WITH FUNCTION sellipse(spoint)
    AS IMPLICIT;


--
-- Casting ellipse as circle
--

CREATE FUNCTION scircle(sellipse)
   RETURNS scircle
   AS '$libdir/pg_sphere' , 'sphereellipse_circle'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION scircle(sellipse) IS
  'spherical bounding circle of spherical ellipse'; 

CREATE CAST (sellipse AS scircle)
    WITH FUNCTION scircle(sellipse)
    AS IMPLICIT;

--
-- Casting circle as ellipse
--

CREATE FUNCTION sellipse(scircle)
   RETURNS sellipse
   AS '$libdir/pg_sphere' , 'spherecircle_ellipse'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION sellipse(scircle) IS
  'returns spherical circle as spherical ellipse'; 

CREATE CAST (scircle AS sellipse)
    WITH FUNCTION sellipse(scircle)
    AS IMPLICIT;

--
-- Casting ellipse as Euler transformation
--

CREATE FUNCTION strans(sellipse)
   RETURNS strans
   AS '$libdir/pg_sphere' , 'sphereellipse_trans'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans(sellipse) IS
  'returns Euler transformation of spherical ellipse'; 

CREATE CAST (sellipse AS strans)
    WITH FUNCTION strans(sellipse)
    AS IMPLICIT;





-- ****************************
--
-- spherical ellipse operators
--
-- ****************************


CREATE FUNCTION center(sellipse)
   RETURNS spoint
   AS '$libdir/pg_sphere' , 'sphereellipse_center'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION center(sellipse) IS
  'center of spherical ellipse'; 


CREATE OPERATOR @@ (
   RIGHTARG   = sellipse,
   PROCEDURE  = center
);

COMMENT ON OPERATOR @@ ( NONE , sellipse ) IS
  'center of spherical ellipse';

--
-- equal
--

CREATE FUNCTION sellipse_equal(sellipse,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_equal'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION sellipse_equal(sellipse,sellipse) IS
  'returns true, if spherical ellipses are equal';

CREATE OPERATOR  = (
   LEFTARG    = sellipse,
   RIGHTARG   = sellipse,
   COMMUTATOR = = ,
   NEGATOR    = <>,
   PROCEDURE  = sellipse_equal,
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);
  
COMMENT ON OPERATOR = ( sellipse, sellipse ) IS
  'true, if spherical ellipses are equal';



--
-- not equal
--
  
CREATE FUNCTION sellipse_equal_neg(sellipse,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_equal_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION sellipse_equal_neg(sellipse,sellipse) IS
  'returns true, if spherical ellipses are not equal';    

CREATE OPERATOR  <> (
   LEFTARG    = sellipse,
   RIGHTARG   = sellipse,
   COMMUTATOR = <>,
   NEGATOR    = = ,
   PROCEDURE  = sellipse_equal_neg,
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);
  
COMMENT ON OPERATOR <> ( sellipse, sellipse ) IS
  'true, if spherical ellipses are not equal'; 


--
-- ellipse contains ellipse
--

CREATE FUNCTION sellipse_contains_ellipse(sellipse,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_ellipse'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_ellipse(sellipse,sellipse) IS
  'true if spherical ellipse contains spherical ellipse '; 


CREATE OPERATOR ~ (
   LEFTARG    = sellipse,
   RIGHTARG   = sellipse,
   PROCEDURE  = sellipse_contains_ellipse,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( sellipse, sellipse ) IS
  'true if spherical ellipse contains spherical ellipse'; 


--
-- ellipse is contained by ellipse
--

CREATE FUNCTION sellipse_contains_ellipse_com(sellipse, sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_ellipse_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_ellipse_com(sellipse, sellipse) IS
  'true if spherical ellipse is contained by spherical ellipse '; 


CREATE OPERATOR @ (
   LEFTARG    = sellipse,
   RIGHTARG   = sellipse,
   PROCEDURE  = sellipse_contains_ellipse_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( sellipse, sellipse ) IS
  'true if spherical ellipse is contained by spherical ellipse'; 

--
-- ellipse does not contain ellipse
--

CREATE FUNCTION sellipse_contains_ellipse_neg(sellipse,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_ellipse_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_ellipse_neg(sellipse,sellipse) IS
  'true if spherical ellipse does not contain spherical ellipse '; 


CREATE OPERATOR !~ (
   LEFTARG    = sellipse,
   RIGHTARG   = sellipse,
   PROCEDURE  = sellipse_contains_ellipse_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( sellipse, sellipse ) IS
  'true if spherical ellipse does not contain spherical ellipse'; 


--
-- ellipse is not contained by ellipse
--

CREATE FUNCTION sellipse_contains_ellipse_com_neg(sellipse, sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_ellipse_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_ellipse_com_neg(sellipse, sellipse) IS
  'true if spherical ellipse is not contained by spherical ellipse '; 


CREATE OPERATOR !@ (
   LEFTARG    = sellipse,
   RIGHTARG   = sellipse,
   PROCEDURE  = sellipse_contains_ellipse_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( sellipse, sellipse ) IS
  'true if spherical ellipse is not contained by spherical ellipse'; 


--
-- ellipses overlap
--

CREATE FUNCTION sellipse_overlap_ellipse(sellipse,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_overlap_ellipse'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sellipse_overlap_ellipse(sellipse,sellipse) IS
  'true if spherical ellipse overlaps spherical ellipse '; 


CREATE OPERATOR && (
   LEFTARG    = sellipse,
   RIGHTARG   = sellipse,
   PROCEDURE  = sellipse_overlap_ellipse,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( sellipse, sellipse ) IS
  'true if spherical ellipses overlap '; 

--
-- ellipses do not overlap
--

CREATE FUNCTION sellipse_overlap_ellipse_neg(sellipse,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_overlap_ellipse_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sellipse_overlap_ellipse_neg(sellipse,sellipse) IS
  'true if spherical ellipse does not overlap spherical ellipse'; 


CREATE OPERATOR !&& (
   LEFTARG    = sellipse,
   RIGHTARG   = sellipse,
   PROCEDURE  = sellipse_overlap_ellipse_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( sellipse, sellipse ) IS
  'true if spherical ellipse does not overlap spherical ellipse'; 


--
-- ellipse contains point
--

CREATE FUNCTION sellipse_contains_point(sellipse,spoint)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_point'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_point(sellipse, spoint) IS
  'true if spherical ellipse contains spherical point '; 

CREATE OPERATOR ~ (
   LEFTARG    = sellipse,
   RIGHTARG   = spoint,
   PROCEDURE  = sellipse_contains_point,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( sellipse, spoint ) IS
  'true if spherical ellipse contains spherical point '; 

--
--  point is contained by ellipse
--

CREATE FUNCTION sellipse_contains_point_com(spoint,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_point_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_point_com(spoint, sellipse) IS
  'true if spherical ellipse contains spherical point '; 


CREATE OPERATOR @ (
   LEFTARG    = spoint,
   RIGHTARG   = sellipse,
   PROCEDURE  = sellipse_contains_point_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( spoint, sellipse ) IS
  'true if spherical ellipse contains spherical point '; 

--
-- ellipse does not contain point
--

CREATE FUNCTION sellipse_contains_point_neg(sellipse,spoint)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_point_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_point_neg(sellipse, spoint) IS
  'true if spherical ellipse contains spherical point '; 

CREATE OPERATOR !~ (
   LEFTARG    = sellipse,
   RIGHTARG   = spoint,
   PROCEDURE  = sellipse_contains_point_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( sellipse, spoint ) IS
  'true if spherical ellipse does not contain spherical point'; 

--
--  point is not contained by ellipse
--

CREATE FUNCTION sellipse_contains_point_com_neg(spoint,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_point_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_point_com_neg(spoint, sellipse) IS
  'true if spherical ellipse contains spherical point '; 


CREATE OPERATOR !@ (
   LEFTARG    = spoint,
   RIGHTARG   = sellipse,
   PROCEDURE  = sellipse_contains_point_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( spoint, sellipse ) IS
  'true if spherical ellipse does not contain spherical point'; 


--
-- Transformation of ellipse
--


CREATE FUNCTION strans_ellipse( sellipse, strans )
   RETURNS sellipse
   AS '$libdir/pg_sphere' , 'spheretrans_ellipse'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans_ellipse ( sellipse, strans ) IS
  'returns a transformated spherical ellipse'; 

CREATE OPERATOR + (
   LEFTARG    = sellipse,
   RIGHTARG   = strans,
   PROCEDURE  = strans_ellipse
);

COMMENT ON OPERATOR + ( sellipse, strans ) IS
  'transforms a spherical ellipse '; 

CREATE FUNCTION strans_ellipse_inverse ( sellipse, strans )
   RETURNS sellipse
   AS '$libdir/pg_sphere' , 'spheretrans_ellipse_inv'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans_ellipse_inverse ( sellipse, strans ) IS
  'returns a inverse transformated spherical ellipse'; 

CREATE OPERATOR - (
   LEFTARG    = sellipse,
   RIGHTARG   = strans,
   PROCEDURE  = strans_ellipse_inverse
);

COMMENT ON OPERATOR - ( sellipse, strans ) IS
  'transforms inverse a spherical ellipse '; 


--
-- ellipse contains circle
--

CREATE FUNCTION sellipse_contains_circle(sellipse,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_circle'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_circle(sellipse,scircle) IS
  'true if spherical ellipse contains spherical circle ';


CREATE OPERATOR ~ (    
   LEFTARG    = sellipse,  
   RIGHTARG   = scircle,
   PROCEDURE  = sellipse_contains_circle,
   COMMUTATOR = '@',       
   NEGATOR    = '!~',     
   RESTRICT   = contsel,   
   JOIN       = contjoinsel
);

COMMENT ON OPERATOR ~ ( sellipse, scircle ) IS
  'true if spherical ellipse contains spherical circle';

--
-- circle is contained by ellipse
--

CREATE FUNCTION sellipse_contains_circle_com(scircle, sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_circle_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_circle_com(scircle, sellipse) IS
  'true if spherical ellipse contains spherical circle '; 


CREATE OPERATOR @ (
   LEFTARG    = scircle,
   RIGHTARG   = sellipse,  
   PROCEDURE  = sellipse_contains_circle_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);
  
COMMENT ON OPERATOR @ ( scircle, sellipse ) IS
  'true if spherical ellipse contains spherical circle';

  
--
-- ellipse does not contain circle
--
  
CREATE FUNCTION sellipse_contains_circle_neg(sellipse,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_circle_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_circle_neg(sellipse,scircle) IS
  'true if spherical ellipse does not contain spherical circle ';


CREATE OPERATOR !~ (
   LEFTARG    = sellipse,
   RIGHTARG   = scircle,
   PROCEDURE  = sellipse_contains_circle_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);
  
COMMENT ON OPERATOR !~ ( sellipse, scircle ) IS
  'true if spherical ellipse does not contain spherical circle';

--
-- circle is not contained by ellipse
--
  
CREATE FUNCTION sellipse_contains_circle_com_neg(scircle, sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_circle_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_circle_com_neg(scircle, sellipse) IS
  'true if spherical ellipse does not contain spherical circle ';   


CREATE OPERATOR !@ (
   LEFTARG    = scircle,
   RIGHTARG   = sellipse,  
   PROCEDURE  = sellipse_contains_circle_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',  
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);
  
COMMENT ON OPERATOR !@ ( scircle, sellipse ) IS
  'true if spherical ellipse does not contain spherical circle';

--
-- circle contains ellipse
--
  
CREATE FUNCTION scircle_contains_ellipse(scircle,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_cont_ellipse'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION scircle_contains_ellipse(scircle,sellipse) IS
  'true if spherical circle contains spherical ellipse '; 


CREATE OPERATOR ~ (
   LEFTARG    = scircle,
   RIGHTARG   = sellipse,  
   PROCEDURE  = scircle_contains_ellipse,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);
  
COMMENT ON OPERATOR ~ ( scircle, sellipse ) IS
  'true if spherical circle contains spherical ellipse';


--
-- ellipse is contained by circle
--
  
CREATE FUNCTION scircle_contains_ellipse_com(sellipse, scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_cont_ellipse_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION scircle_contains_ellipse_com(sellipse, scircle) IS
  'true if spherical circle contains spherical ellipse '; 


CREATE OPERATOR @ (
   LEFTARG    = sellipse,
   RIGHTARG   = scircle,
   PROCEDURE  = scircle_contains_ellipse_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);
  
COMMENT ON OPERATOR @ ( sellipse, scircle ) IS
  'true if spherical circle contains spherical ellipse';

--
-- circle does not contain ellipse
--
  
CREATE FUNCTION scircle_contains_ellipse_neg(scircle,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_cont_ellipse_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION scircle_contains_ellipse_neg(scircle,sellipse) IS
  'true if spherical circle does not contain spherical ellipse '; 


CREATE OPERATOR !~ (
   LEFTARG    = scircle,
   RIGHTARG   = sellipse,
   PROCEDURE  = scircle_contains_ellipse_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);
  
COMMENT ON OPERATOR !~ ( scircle, sellipse ) IS
  'true if spherical circle does not contain spherical ellipse';

--
-- ellipse is not contained by circle
--
  
CREATE FUNCTION scircle_contains_ellipse_com_neg(sellipse, scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_cont_ellipse_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION scircle_contains_ellipse_com_neg(sellipse, scircle) IS
  'true if spherical circle does not contain spherical ellipse '; 


CREATE OPERATOR !@ (
   LEFTARG    = sellipse,
   RIGHTARG   = scircle,
   PROCEDURE  = scircle_contains_ellipse_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',  
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);
  
COMMENT ON OPERATOR !@ ( sellipse, scircle ) IS
  'true if spherical circle does not contain spherical ellipse';

--
-- circle overlaps ellipse
--
  
CREATE FUNCTION sellipse_overlap_circle(sellipse,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_overlap_circle'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_overlap_circle(sellipse,scircle) IS
  'true if spherical circle overlap spherical ellipse ';  


CREATE OPERATOR && (
   LEFTARG    = sellipse,
   RIGHTARG   = scircle,
   PROCEDURE  = sellipse_overlap_circle,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);

COMMENT ON OPERATOR && ( sellipse, scircle ) IS
  'true if spherical circle overlap spherical ellipse';


--
-- ellipse overlaps circle
--
  
CREATE FUNCTION sellipse_overlap_circle_com(scircle,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_overlap_circle_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sellipse_overlap_circle_com(scircle,sellipse) IS
  'true if spherical circle overlap spherical ellipse '; 


CREATE OPERATOR && (
   LEFTARG    = scircle,
   RIGHTARG   = sellipse,  
   PROCEDURE  = sellipse_overlap_circle_com,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);
  
COMMENT ON OPERATOR && ( scircle, sellipse ) IS
  'true if spherical circle overlap spherical ellipse';

--
-- circle does not overlap ellipse
--
  
CREATE FUNCTION sellipse_overlap_circle_neg(sellipse,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_overlap_circle_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sellipse_overlap_circle_neg(sellipse,scircle) IS
  'true if spherical circle does not overlap spherical ellipse ';


CREATE OPERATOR !&& (
   LEFTARG    = sellipse,
   RIGHTARG   = scircle,
   PROCEDURE  = sellipse_overlap_circle_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&', 
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);
  
COMMENT ON OPERATOR !&& ( sellipse, scircle ) IS
  'true if spherical circle does not overlap spherical ellipse';


--
-- ellipse does not overlap circle
--
  
CREATE FUNCTION sellipse_overlap_circle_com_neg(scircle,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_overlap_circle_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sellipse_overlap_circle_com_neg(scircle,sellipse) IS
  'true if spherical circle does not overlap spherical ellipse ';  


CREATE OPERATOR !&& (
   LEFTARG    = scircle,
   RIGHTARG   = sellipse,  
   PROCEDURE  = sellipse_overlap_circle_com_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&', 
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);

COMMENT ON OPERATOR !&& ( scircle, sellipse ) IS
  'true if spherical circle does not overlap spherical ellipse';




--
-- ellipse overlaps line
--

CREATE FUNCTION sellipse_overlap_line ( sellipse, sline )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_overlap_line'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_overlap_line ( sellipse, sline ) IS
  'returns true if spherical line overlaps spherical ellipse'; 


CREATE OPERATOR && (
   LEFTARG    = sellipse,
   RIGHTARG   = sline,
   PROCEDURE  = sellipse_overlap_line,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&' ,
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);

COMMENT ON OPERATOR && ( sellipse, sline ) IS
  'true if spherical line overlaps spherical ellipse'; 

--
-- line overlaps ellipse    
--

CREATE FUNCTION sellipse_overlap_line_com( sline , sellipse )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_overlap_line_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_overlap_line_com( sline , sellipse ) IS
  'returns true if spherical line overlaps spherical ellipse'; 


CREATE OPERATOR && (
   LEFTARG    = sline,
   RIGHTARG   = sellipse,
   PROCEDURE  = sellipse_overlap_line_com,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&' ,
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);

COMMENT ON OPERATOR && ( sline, sellipse ) IS
  'true if spherical line overlaps spherical ellipse'; 


--
-- ellipse does not overlap line
--

CREATE FUNCTION sellipse_overlap_line_neg ( sellipse, sline )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_overlap_line_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_overlap_line_neg ( sellipse, sline ) IS
  'returns true if spherical line overlaps spherical ellipse'; 


CREATE OPERATOR !&& (
   LEFTARG    = sellipse,
   RIGHTARG   = sline,
   PROCEDURE  = sellipse_overlap_line_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&' ,
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);

COMMENT ON OPERATOR !&& ( sellipse, sline ) IS
  'true if spherical line does not overlap spherical ellipse'; 

--
-- line does not overlap ellipse    
--

CREATE FUNCTION sellipse_overlap_line_com_neg( sline , sellipse )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_overlap_line_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_overlap_line_com_neg( sline , sellipse ) IS
  'returns true if spherical line does not overlap spherical ellipse'; 


CREATE OPERATOR !&& (
   LEFTARG    = sline,
   RIGHTARG   = sellipse,
   PROCEDURE  = sellipse_overlap_line_com_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&' ,
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);

COMMENT ON OPERATOR !&& ( sline, sellipse ) IS
  'true if spherical line does not overlap spherical ellipse'; 


--
-- ellipse contains line
--


CREATE FUNCTION sellipse_contains_line( sellipse , sline )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_line'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_line( sellipse , sline ) IS
  'returns true if spherical ellipse contains spherical line'; 


CREATE OPERATOR ~ (
   LEFTARG    = sellipse,
   RIGHTARG   = sline,
   PROCEDURE  = sellipse_contains_line,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( sellipse, sline ) IS
  'true if spherical ellipse contains spherical line'; 


--
-- line is contained by ellipse
--


CREATE FUNCTION sellipse_contains_line_com( sline , sellipse )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_line_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_line_com( sline , sellipse ) IS
  'returns true if spherical ellipse contains spherical line'; 

CREATE OPERATOR @ (
   LEFTARG    = sline,
   RIGHTARG   = sellipse,
   PROCEDURE  = sellipse_contains_line_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( sline, sellipse ) IS
  'true if spherical ellipse contains spherical line'; 


--
-- ellipse does not contain line
--

CREATE FUNCTION sellipse_contains_line_neg( sellipse , sline )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_line_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_line_neg( sellipse , sline ) IS
  'returns true if spherical ellipse does not contain spherical line'; 


CREATE OPERATOR !~ (
   LEFTARG    = sellipse,
   RIGHTARG   = sline,
   PROCEDURE  = sellipse_contains_line_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( sellipse, sline ) IS
  'true if spherical ellipse does not contain spherical line'; 


--
-- line is not contained by ellipse
--


CREATE FUNCTION sellipse_contains_line_com_neg( sline , sellipse )
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_line_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_line_com_neg( sline , sellipse ) IS
  'returns true if spherical ellipse does not contain spherical line'; 


CREATE OPERATOR !@ (
   LEFTARG    = sline,
   RIGHTARG   = sellipse,
   PROCEDURE  = sellipse_contains_line_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( sline, sellipse ) IS
  'true if spherical ellipse does not contain spherical line'; 


-- spherical polygon functions


CREATE FUNCTION npoints(spoly)
   RETURNS INT4
   AS '$libdir/pg_sphere', 'spherepoly_npts'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  npoints(spoly) IS
  'returns number of points of spherical polygon'; 

CREATE FUNCTION area(spoly)
   RETURNS FLOAT8
   AS '$libdir/pg_sphere', 'spherepoly_area'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  area(spoly) IS
  'returns area of spherical polygon'; 



-- ****************************
--
-- spherical polygon operators
--
-- ****************************


--
-- equal
--

CREATE FUNCTION spoly_equal(spoly,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_equal'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_equal(spoly,spoly) IS
  'returns true, if spherical polygons are equal'; 


CREATE OPERATOR  = (
   LEFTARG    = spoly,
   RIGHTARG   = spoly,
   COMMUTATOR = =,
   NEGATOR    = <>,
   PROCEDURE  = spoly_equal,
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR = ( spoly, spoly ) IS
  'true, if spherical polygons are equal'; 

--
-- not equal
--

CREATE FUNCTION spoly_not_equal(spoly,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_equal_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_not_equal(spoly,spoly) IS
  'returns true, if spherical polygons are not equal'; 

CREATE OPERATOR  <> (
   LEFTARG    = spoly,
   RIGHTARG   = spoly,
   COMMUTATOR = <>,
   NEGATOR    = = ,
   PROCEDURE  = spoly_not_equal,
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR <> ( spoly, spoly ) IS
  'true, if spherical polygons are not equal'; 

--
-- circumference
--

CREATE FUNCTION circum(spoly)
   RETURNS FLOAT8
   AS '$libdir/pg_sphere', 'spherepoly_circ'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  circum(spoly) IS
  'returns circumference of spherical polygon'; 

CREATE OPERATOR  @-@ (
   RIGHTARG   = spoly,
   PROCEDURE  = circum
);

COMMENT ON OPERATOR @-@ ( NONE, spoly ) IS
  'returns circumference of spherical polygon'; 


--
-- polygon contains polygon
--

CREATE FUNCTION spoly_contains_polygon(spoly,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_poly'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_polygon(spoly,spoly) IS
  'true if spherical polygon contains spherical polygon '; 


CREATE OPERATOR ~ (
   LEFTARG    = spoly,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_contains_polygon,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( spoly, spoly ) IS
  'true if spherical polygon contains spherical polygon'; 


--
-- polygon is contained by polygon
--

CREATE FUNCTION spoly_contains_polygon_com(spoly, spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_poly_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_polygon_com(spoly, spoly) IS
  'true if spherical polygon is contained by spherical polygon '; 


CREATE OPERATOR @ (
   LEFTARG    = spoly,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_contains_polygon_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( spoly, spoly ) IS
  'true if spherical polygon is contained by spherical polygon'; 

--
-- polygon does not contain polygon
--

CREATE FUNCTION spoly_contains_polygon_neg(spoly,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_poly_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_polygon_neg(spoly,spoly) IS
  'true if spherical polygon does not contain spherical polygon '; 


CREATE OPERATOR !~ (
   LEFTARG    = spoly,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_contains_polygon_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( spoly, spoly ) IS
  'true if spherical polygon does not contain spherical polygon'; 


--
-- polygon is not contained by polygon
--

CREATE FUNCTION spoly_contains_polygon_com_neg(spoly, spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_poly_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_polygon_com_neg(spoly, spoly) IS
  'true if spherical polygon is not contained by spherical polygon '; 


CREATE OPERATOR !@ (
   LEFTARG    = spoly,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_contains_polygon_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( spoly, spoly ) IS
  'true if spherical polygon is not contained by spherical polygon'; 


--
-- polygons overlap
--

CREATE FUNCTION spoly_overlap_polygon(spoly,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_overlap_poly'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spoly_overlap_polygon(spoly,spoly) IS
  'true if spherical polygon overlaps spherical polygon '; 


CREATE OPERATOR && (
   LEFTARG    = spoly,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_overlap_polygon,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( spoly, spoly ) IS
  'true if spherical polygons overlap '; 

--
-- polygons do not overlap
--

CREATE FUNCTION spoly_overlap_polygon_neg(spoly,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_overlap_poly_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spoly_overlap_polygon_neg(spoly,spoly) IS
  'true if spherical polygon does not overlap spherical polygon'; 


CREATE OPERATOR !&& (
   LEFTARG    = spoly,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_overlap_polygon,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( spoly, spoly ) IS
  'true if spherical polygon does not overlap spherical polygon'; 


--
-- polygon contains point
--

CREATE FUNCTION spoly_contains_point(spoly,spoint)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_point'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_point(spoly,spoint) IS
  'true if spherical polygon contains spherical point '; 

CREATE OPERATOR ~ (
   LEFTARG    = spoly,
   RIGHTARG   = spoint,
   PROCEDURE  = spoly_contains_point,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( spoly, spoint ) IS
  'true if spherical polygon contains spherical point'; 


--
-- point is contained by polygon
--

CREATE FUNCTION spoly_contains_point_com(spoint,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_point_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_point_com(spoint,spoly) IS
  'true if spherical polygon contains spherical point '; 

CREATE OPERATOR @ (
   LEFTARG    = spoint,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_contains_point_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( spoint, spoly ) IS
  'true if spherical polygon contains spherical point'; 


--
-- polygon does not contain point
--

CREATE FUNCTION spoly_contains_point_neg(spoly,spoint)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_point_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_point_neg(spoly,spoint) IS
  'true if spherical polygon does not contain spherical point '; 

CREATE OPERATOR !~ (
   LEFTARG    = spoly,
   RIGHTARG   = spoint,
   PROCEDURE  = spoly_contains_point,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( spoly, spoint ) IS
  'true if spherical polygon does not contain spherical point'; 


--
-- point is not contained by polygon
--

CREATE FUNCTION spoly_contains_point_com_neg(spoint,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_point_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_point_com_neg(spoint,spoly) IS
  'true if spherical polygon does not contain spherical point '; 

CREATE OPERATOR !@ (
   LEFTARG    = spoint,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_contains_point_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( spoint, spoly ) IS
  'true if spherical polygon does not contain spherical point'; 

--
-- Transformation of polygon
--

CREATE FUNCTION strans_poly( spoly, strans )
   RETURNS spoly
   AS '$libdir/pg_sphere' , 'spheretrans_poly'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans_poly ( spoly, strans ) IS
  'returns a transformated spherical polygon'; 

CREATE OPERATOR + (
   LEFTARG    = spoly,
   RIGHTARG   = strans,
   PROCEDURE  = strans_poly
);

COMMENT ON OPERATOR + ( spoly, strans ) IS
  'transforms a spherical polygon '; 

CREATE FUNCTION strans_poly_inverse( spoly, strans )
   RETURNS spoly
   AS '$libdir/pg_sphere' , 'spheretrans_poly_inverse'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans_poly_inverse ( spoly, strans ) IS
  'returns a inverse transformated spherical polygon'; 

CREATE OPERATOR - (
   LEFTARG    = spoly,
   RIGHTARG   = strans,
   PROCEDURE  = strans_poly_inverse
);

COMMENT ON OPERATOR - ( spoly, strans ) IS
  'transforms inverse a spherical polygon ';


--
-- polygon contains circle
--

CREATE FUNCTION spoly_contains_circle(spoly,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_circle'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_circle(spoly,scircle) IS
  'true if spherical polygon contains spherical circle '; 


CREATE OPERATOR ~ (
   LEFTARG    = spoly,
   RIGHTARG   = scircle,
   PROCEDURE  = spoly_contains_circle,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( spoly, scircle ) IS
  'true if spherical polygon contains spherical circle'; 


--
-- circle is contained by polygon
--

CREATE FUNCTION spoly_contains_circle_com(scircle, spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_circle_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_circle_com(scircle, spoly) IS
  'true if spherical polygon contains spherical circle '; 


CREATE OPERATOR @ (
   LEFTARG    = scircle,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_contains_circle_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( scircle, spoly ) IS
  'true if spherical polygon contains spherical circle'; 

--
-- polygon does not contain circle
--

CREATE FUNCTION spoly_contains_circle_neg(spoly,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_circle_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_circle_neg(spoly,scircle) IS
  'true if spherical polygon does not contain spherical circle '; 


CREATE OPERATOR !~ (
   LEFTARG    = spoly,
   RIGHTARG   = scircle,
   PROCEDURE  = spoly_contains_circle_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( spoly, scircle ) IS
  'true if spherical polygon does not contain spherical circle'; 


--
-- circle is not contained by polygon
--

CREATE FUNCTION spoly_contains_circle_com_neg(scircle, spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_circle_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_circle_com_neg(scircle, spoly) IS
  'true if spherical polygon does not contain spherical circle '; 


CREATE OPERATOR !@ (
   LEFTARG    = scircle,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_contains_circle_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( scircle, spoly ) IS
  'true if spherical polygon does not contain spherical circle'; 


--
-- circle contains polygon
--

CREATE FUNCTION scircle_contains_polygon(scircle,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_cont_poly'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION scircle_contains_polygon(scircle,spoly) IS
  'true if spherical circle contains spherical polygon '; 


CREATE OPERATOR ~ (
   LEFTARG    = scircle,
   RIGHTARG   = spoly,
   PROCEDURE  = scircle_contains_polygon,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( scircle, spoly ) IS
  'true if spherical circle contains spherical polygon'; 


--
-- polygon is contained by circle
--

CREATE FUNCTION scircle_contains_polygon_com(spoly, scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_cont_poly_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION scircle_contains_polygon_com(spoly, scircle) IS
  'true if spherical circle contains spherical polygon '; 


CREATE OPERATOR @ (
   LEFTARG    = spoly,
   RIGHTARG   = scircle,
   PROCEDURE  = scircle_contains_polygon_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( spoly, scircle ) IS
  'true if spherical circle contains spherical polygon'; 

--
-- circle does not contain polygon
--

CREATE FUNCTION scircle_contains_polygon_neg(scircle,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_cont_poly_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION scircle_contains_polygon_neg(scircle,spoly) IS
  'true if spherical circle does not contain spherical polygon '; 


CREATE OPERATOR !~ (
   LEFTARG    = scircle,
   RIGHTARG   = spoly,
   PROCEDURE  = scircle_contains_polygon_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( scircle, spoly ) IS
  'true if spherical circle does not contain spherical polygon'; 


--
-- polygon is not contained by circle
--

CREATE FUNCTION scircle_contains_polygon_com_neg(spoly, scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_cont_poly_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION scircle_contains_polygon_com_neg(spoly, scircle) IS
  'true if spherical circle does not contain spherical polygon '; 


CREATE OPERATOR !@ (
   LEFTARG    = spoly,
   RIGHTARG   = scircle,
   PROCEDURE  = scircle_contains_polygon_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( spoly, scircle ) IS
  'true if spherical circle does not contain spherical polygon'; 


--
-- circle overlaps polygon
--

CREATE FUNCTION spoly_overlap_circle(spoly,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_overlap_circle'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_overlap_circle(spoly,scircle) IS
  'true if spherical circle overlap spherical polygon '; 


CREATE OPERATOR && (
   LEFTARG    = spoly,
   RIGHTARG   = scircle,
   PROCEDURE  = spoly_overlap_circle,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( spoly, scircle ) IS
  'true if spherical circle overlap spherical polygon'; 

--
-- polygon overlaps circle
--

CREATE FUNCTION spoly_overlap_circle_com(scircle,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_overlap_circle_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spoly_overlap_circle_com(scircle,spoly) IS
  'true if spherical circle overlap spherical polygon '; 


CREATE OPERATOR && (
   LEFTARG    = scircle,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_overlap_circle_com,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( scircle, spoly ) IS
  'true if spherical circle overlap spherical polygon'; 


--
-- circle does not overlap polygon
--

CREATE FUNCTION spoly_overlap_circle_neg(spoly,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_overlap_circle_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spoly_overlap_circle_neg(spoly,scircle) IS
  'true if spherical circle does not overlap spherical polygon '; 


CREATE OPERATOR !&& (
   LEFTARG    = spoly,
   RIGHTARG   = scircle,
   PROCEDURE  = spoly_overlap_circle_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( spoly, scircle ) IS
  'true if spherical circle does not overlap spherical polygon'; 

--
-- polygon does not overlap circle
--

CREATE FUNCTION spoly_overlap_circle_com_neg(scircle,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_overlap_circle_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spoly_overlap_circle_com_neg(scircle,spoly) IS
  'true if spherical circle does not overlap spherical polygon '; 


CREATE OPERATOR !&& (
   LEFTARG    = scircle,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_overlap_circle_com_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( scircle, spoly ) IS
  'true if spherical circle does not overlap spherical polygon'; 


--
-- polygon contains line
--

CREATE FUNCTION spoly_contains_line(spoly,sline)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_line'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_line(spoly,sline) IS
  'true if spherical polygon contains spherical line '; 

CREATE OPERATOR ~ (
   LEFTARG    = spoly,
   RIGHTARG   = sline,
   PROCEDURE  = spoly_contains_line,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( spoly, sline ) IS
  'true if spherical polygon contains spherical line'; 

--
-- line is contained by polygon
--

CREATE FUNCTION spoly_contains_line_com(sline,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_line_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_line_com(sline,spoly) IS
  'true if spherical polygon contains spherical line '; 

CREATE OPERATOR @ (
   LEFTARG    = sline,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_contains_line_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( sline, spoly ) IS
  'true if spherical polygon contains spherical line'; 



--
-- polygon does not contain line
--

CREATE FUNCTION spoly_contains_line_neg(spoly,sline)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_line_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_line_neg(spoly,sline) IS
  'true if spherical polygon does not contain spherical line '; 

CREATE OPERATOR !~ (
   LEFTARG    = spoly,
   RIGHTARG   = sline,
   PROCEDURE  = spoly_contains_line,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( spoly, sline ) IS
  'true if spherical polygon does not contain spherical line'; 

--
-- line is not contained by polygon
--

CREATE FUNCTION spoly_contains_line_com_neg(sline,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_line_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_line_com_neg(sline,spoly) IS
  'true if spherical polygon does not contain spherical line '; 

CREATE OPERATOR !@ (
   LEFTARG    = sline,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_contains_line_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( sline, spoly ) IS
  'true if spherical polygon does not contain spherical line'; 

--
-- line overlaps polygon
--

CREATE FUNCTION spoly_overlap_line(spoly,sline)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_overlap_line'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_overlap_line(spoly,sline) IS
  'true if spherical line overlap spherical polygon '; 


CREATE OPERATOR && (
   LEFTARG    = spoly,
   RIGHTARG   = sline,
   PROCEDURE  = spoly_overlap_line,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( spoly, sline ) IS
  'true if spherical line overlap spherical polygon'; 

--
-- polygon overlaps line
--

CREATE FUNCTION spoly_overlap_line_com(sline,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_overlap_line_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spoly_overlap_line_com(sline,spoly) IS
  'true if spherical line overlap spherical polygon '; 


CREATE OPERATOR && (
   LEFTARG    = sline,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_overlap_line_com,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( sline, spoly ) IS
  'true if spherical line overlap spherical polygon'; 


--
-- line does not overlap polygon
--

CREATE FUNCTION spoly_overlap_line_neg(spoly,sline)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_overlap_line_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spoly_overlap_line_neg(spoly,sline) IS
  'true if spherical line does not overlap spherical polygon '; 


CREATE OPERATOR !&& (
   LEFTARG    = spoly,
   RIGHTARG   = sline,
   PROCEDURE  = spoly_overlap_line_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( spoly, sline ) IS
  'true if spherical line does not overlap spherical polygon'; 

--
-- polygon does not overlap line
--

CREATE FUNCTION spoly_overlap_line_com_neg(sline,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_overlap_line_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spoly_overlap_line_com_neg(sline,spoly) IS
  'true if spherical line does not overlap spherical polygon '; 


CREATE OPERATOR !&& (
   LEFTARG    = sline,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_overlap_line_com_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( sline, spoly ) IS
  'true if spherical line does not overlap spherical polygon'; 



--
-- polygon contains ellipse
--

CREATE FUNCTION spoly_contains_ellipse(spoly,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_ellipse'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_ellipse(spoly,sellipse) IS
  'true if spherical polygon contains spherical ellipse '; 


CREATE OPERATOR ~ (
   LEFTARG    = spoly,
   RIGHTARG   = sellipse,
   PROCEDURE  = spoly_contains_ellipse,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( spoly, sellipse ) IS
  'true if spherical polygon contains spherical ellipse'; 


--
-- ellipse is contained by polygon
--

CREATE FUNCTION spoly_contains_ellipse_com(sellipse, spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_ellipse_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_ellipse_com(sellipse, spoly) IS
  'true if spherical polygon contains spherical ellipse '; 


CREATE OPERATOR @ (
   LEFTARG    = sellipse,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_contains_ellipse_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( sellipse, spoly ) IS
  'true if spherical polygon contains spherical ellipse'; 

--
-- polygon does not contain ellipse
--

CREATE FUNCTION spoly_contains_ellipse_neg(spoly,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_ellipse_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_ellipse_neg(spoly,sellipse) IS
  'true if spherical polygon does not contain spherical ellipse '; 


CREATE OPERATOR !~ (
   LEFTARG    = spoly,
   RIGHTARG   = sellipse,
   PROCEDURE  = spoly_contains_ellipse_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( spoly, sellipse ) IS
  'true if spherical polygon does not contain spherical ellipse'; 


--
-- ellipse is not contained by polygon
--

CREATE FUNCTION spoly_contains_ellipse_com_neg(sellipse, spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_ellipse_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_ellipse_com_neg(sellipse, spoly) IS
  'true if spherical polygon does not contain spherical ellipse '; 


CREATE OPERATOR !@ (
   LEFTARG    = sellipse,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_contains_ellipse_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( sellipse, spoly ) IS
  'true if spherical polygon does not contain spherical ellipse'; 


--
-- ellipse contains polygon
--

CREATE FUNCTION sellipse_contains_polygon(sellipse,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_poly'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_polygon(sellipse,spoly) IS
  'true if spherical ellipse contains spherical polygon '; 


CREATE OPERATOR ~ (
   LEFTARG    = sellipse,
   RIGHTARG   = spoly,
   PROCEDURE  = sellipse_contains_polygon,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( sellipse, spoly ) IS
  'true if spherical ellipse contains spherical polygon'; 


--
-- polygon is contained by ellipse
--

CREATE FUNCTION sellipse_contains_polygon_com(spoly, sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_poly_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_polygon_com(spoly, sellipse) IS
  'true if spherical ellipse contains spherical polygon '; 


CREATE OPERATOR @ (
   LEFTARG    = spoly,
   RIGHTARG   = sellipse,
   PROCEDURE  = sellipse_contains_polygon_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( spoly, sellipse ) IS
  'true if spherical ellipse contains spherical polygon'; 

--
-- ellipse does not contain polygon
--

CREATE FUNCTION sellipse_contains_polygon_neg(sellipse,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_poly_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_polygon_neg(sellipse,spoly) IS
  'true if spherical ellipse does not contain spherical polygon '; 


CREATE OPERATOR !~ (
   LEFTARG    = sellipse,
   RIGHTARG   = spoly,
   PROCEDURE  = sellipse_contains_polygon_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( sellipse, spoly ) IS
  'true if spherical ellipse does not contain spherical polygon'; 


--
-- polygon is not contained by ellipse
--

CREATE FUNCTION sellipse_contains_polygon_com_neg(spoly, sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_poly_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_polygon_com_neg(spoly, sellipse) IS
  'true if spherical ellipse does not contain spherical polygon '; 


CREATE OPERATOR !@ (
   LEFTARG    = spoly,
   RIGHTARG   = sellipse,
   PROCEDURE  = sellipse_contains_polygon_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( spoly, sellipse ) IS
  'true if spherical ellipse does not contain spherical polygon'; 


--
-- ellipse overlaps polygon
--

CREATE FUNCTION spoly_overlap_ellipse(spoly,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_overlap_ellipse'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_overlap_ellipse(spoly,sellipse) IS
  'true if spherical ellipse overlap spherical polygon '; 


CREATE OPERATOR && (
   LEFTARG    = spoly,
   RIGHTARG   = sellipse,
   PROCEDURE  = spoly_overlap_ellipse,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( spoly, sellipse ) IS
  'true if spherical ellipse overlap spherical polygon'; 

--
-- polygon overlaps ellipse
--

CREATE FUNCTION spoly_overlap_ellipse_com(sellipse,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_overlap_ellipse_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spoly_overlap_ellipse_com(sellipse,spoly) IS
  'true if spherical ellipse overlap spherical polygon '; 


CREATE OPERATOR && (
   LEFTARG    = sellipse,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_overlap_ellipse_com,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( sellipse, spoly ) IS
  'true if spherical ellipse overlap spherical polygon'; 


--
-- ellipse does not overlap polygon
--

CREATE FUNCTION spoly_overlap_ellipse_neg(spoly,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_overlap_ellipse_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spoly_overlap_ellipse_neg(spoly,sellipse) IS
  'true if spherical ellipse does not overlap spherical polygon '; 


CREATE OPERATOR !&& (
   LEFTARG    = spoly,
   RIGHTARG   = sellipse,
   PROCEDURE  = spoly_overlap_ellipse_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( spoly, sellipse ) IS
  'true if spherical ellipse does not overlap spherical polygon'; 

--
-- polygon does not overlap ellipse
--

CREATE FUNCTION spoly_overlap_ellipse_com_neg(sellipse,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_overlap_ellipse_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spoly_overlap_ellipse_com_neg(sellipse,spoly) IS
  'true if spherical ellipse does not overlap spherical polygon '; 


CREATE OPERATOR !&& (
   LEFTARG    = sellipse,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_overlap_ellipse_com_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( sellipse, spoly ) IS
  'true if spherical ellipse does not overlap spherical polygon'; 


--
-- Aggregate functions to add points to polygon
--

CREATE FUNCTION spoly_add_point_aggr ( spoly, spoint )
  RETURNS spoly
  AS '$libdir/pg_sphere' , 'spherepoly_add_point'
  LANGUAGE 'c'
  IMMUTABLE;

COMMENT ON FUNCTION  spoly_add_point_aggr ( spoly, spoint ) IS
  'adds a spherical point to spherical polygon. Do not use it standalone!'; 

CREATE FUNCTION spoly_add_points_fin_aggr ( spoly )
  RETURNS spoly
  AS '$libdir/pg_sphere' , 'spherepoly_add_points_finalize'
  LANGUAGE 'c'
  IMMUTABLE STRICT ;

COMMENT ON FUNCTION  spoly_add_points_fin_aggr ( spoly ) IS
  'Finalize spherical point adding to spherical polygon. Do not use it standalone!'; 

CREATE AGGREGATE spoly (
    sfunc     = spoly_add_point_aggr,
    basetype  = spoint,
    stype     = spoly,
    finalfunc = spoly_add_points_fin_aggr
);


-- ******************************
--
-- spherical path functions
--
-- ******************************


CREATE FUNCTION npoints(spath)
   RETURNS INT4
   AS '$libdir/pg_sphere', 'spherepath_npts'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  npoints(spath) IS
  'returns number of points of spherical path'; 

CREATE FUNCTION spoint(spath,int4)
   RETURNS spoint
   AS '$libdir/pg_sphere', 'spherepath_get_point'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spoint(spath,int4) IS
  'returns n-th point of spherical path'; 

CREATE FUNCTION spoint(spath,float8)
   RETURNS spoint
   AS '$libdir/pg_sphere', 'spherepath_point'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spoint(spath,float8) IS
  'returns n-th point of spherical path using linear interpolation'; 


-- ******************************
--
-- spherical path operators
--
-- ******************************


--
-- equal
--

CREATE FUNCTION spath_equal(spath,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepath_equal'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spath_equal(spath,spath) IS
  'returns true, if spherical pathes are equal'; 


CREATE OPERATOR  = (
   LEFTARG    = spath,
   RIGHTARG   = spath,
   COMMUTATOR = = ,
   NEGATOR    = <>,
   PROCEDURE  = spath_equal,
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR = ( spath, spath ) IS
  'true, if spherical pathes are equal'; 


--
-- not equal
--

CREATE FUNCTION spath_equal_neg(spath,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepath_equal_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spath_equal_neg(spath,spath) IS
  'returns true, if spherical pathes are equal'; 


CREATE OPERATOR  <> (
   LEFTARG    = spath,
   RIGHTARG   = spath,
   COMMUTATOR = <>,
   NEGATOR    = = ,
   PROCEDURE  = spath_equal_neg,
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR <> ( spath, spath ) IS
  'true, if spherical pathes are not equal'; 


--
-- length
--

CREATE FUNCTION length(spath)
   RETURNS FLOAT8
   AS '$libdir/pg_sphere', 'spherepath_length'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  length(spath) IS
  'returns length of spherical path'; 


CREATE OPERATOR  @-@ (
   RIGHTARG   = spath,
   PROCEDURE  = length
);

COMMENT ON OPERATOR @-@ ( NONE, spath ) IS
  'returns length of spherical path'; 



--
-- change the direction of path
--

CREATE FUNCTION swap( spath )
   RETURNS spath
   AS '$libdir/pg_sphere' , 'spherepath_swap'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION swap( spath ) IS
  'returns a swapped spherical path (changed direction)';

CREATE OPERATOR - ( 
   RIGHTARG   = spath,
   PROCEDURE  = swap
);

COMMENT ON OPERATOR - ( NONE , spath ) IS
  'changes the direction of a spherical path';



--
-- pathes overlap
--

CREATE FUNCTION spath_overlap_path(spath,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepath_overlap_path'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spath_overlap_path(spath,spath) IS
  'true if spherical path overlaps spherical path '; 

CREATE OPERATOR && (
   LEFTARG    = spath,
   RIGHTARG   = spath,
   PROCEDURE  = spath_overlap_path,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( spath, spath ) IS
  'true if spherical pathes overlap '; 

--
-- pathes do not overlap
--

CREATE FUNCTION spath_overlap_path_neg(spath,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepath_overlap_path_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spath_overlap_path_neg(spath,spath) IS
  'true if spherical path does not overlap spherical path '; 

CREATE OPERATOR !&& (
   LEFTARG    = spath,
   RIGHTARG   = spath,
   PROCEDURE  = spath_overlap_path,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( spath, spath ) IS
  'true if spherical path does not overlap spherical path ';




--
-- path contains point
--


CREATE FUNCTION spath_contains_point(spath,spoint)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepath_cont_point'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spath_contains_point(spath,spoint) IS
  'true if spherical path contains spherical point'; 


CREATE OPERATOR ~ (
   LEFTARG    = spath,
   RIGHTARG   = spoint,
   PROCEDURE  = spath_contains_point,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( spath, spoint ) IS
  'true if spherical path contains spherical point'; 

--
-- point is contained by path
--

CREATE FUNCTION spath_contains_point_com(spoint,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepath_cont_point_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spath_contains_point_com(spoint,spath) IS
  'true if spherical path contains spherical point'; 

CREATE OPERATOR @ (
   LEFTARG    = spoint,
   RIGHTARG   = spath,
   PROCEDURE  = spath_contains_point_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( spoint, spath ) IS
  'true if spherical path contains spherical point'; 


--
-- path does not contain point
--


CREATE FUNCTION spath_contains_point_neg(spath,spoint)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepath_cont_point_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spath_contains_point_neg(spath,spoint) IS
  'true if spherical path does not contain spherical point'; 


CREATE OPERATOR !~ (
   LEFTARG    = spath,
   RIGHTARG   = spoint,
   PROCEDURE  = spath_contains_point_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( spath, spoint ) IS
  'true if spherical path does not contain spherical point'; 

--
-- point is not contained by path
--

CREATE FUNCTION spath_contains_point_com_neg(spoint,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepath_cont_point_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spath_contains_point_com_neg(spoint,spath) IS
  'true if spherical path does not contain spherical point'; 

CREATE OPERATOR !@ (
   LEFTARG    = spoint,
   RIGHTARG   = spath,
   PROCEDURE  = spath_contains_point_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( spoint, spath ) IS
  'true if spherical path does not contain spherical point'; 


--
-- Transformation of path
--

CREATE FUNCTION strans_path( spath, strans )
   RETURNS spath
   AS '$libdir/pg_sphere' , 'spheretrans_path'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans_path ( spath, strans ) IS
  'returns a transformated spherical path'; 

CREATE OPERATOR + (
   LEFTARG    = spath,
   RIGHTARG   = strans,
   PROCEDURE  = strans_path
);

COMMENT ON OPERATOR + ( spath, strans ) IS
  'transforms a spherical path '; 

CREATE FUNCTION strans_path_inverse( spath, strans )
   RETURNS spath
   AS '$libdir/pg_sphere' , 'spheretrans_path_inverse'
   LANGUAGE 'c'
   WITH (isstrict,iscachable);

COMMENT ON FUNCTION strans_path_inverse ( spath, strans ) IS
  'returns a inverse transformated spherical path'; 

CREATE OPERATOR - (
   LEFTARG    = spath,
   RIGHTARG   = strans,
   PROCEDURE  = strans_path_inverse
);

COMMENT ON OPERATOR - ( spath, strans ) IS
  'transforms inverse a spherical path ';



--
--  circle contains path
--

CREATE FUNCTION scircle_contains_path(scircle,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_cont_path'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION scircle_contains_path(scircle,spath) IS
  'true if spherical circle contains spherical path '; 


CREATE OPERATOR ~ (
   LEFTARG    = scircle,
   RIGHTARG   = spath,
   PROCEDURE  = scircle_contains_path,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( scircle, spath ) IS
  'true if spherical circle contains spherical path'; 

--
--  path is contained by circle
--

CREATE FUNCTION scircle_contains_path_com(spath,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_cont_path_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION scircle_contains_path_com(spath,scircle) IS
  'true if spherical circle contains spherical path '; 

CREATE OPERATOR @ (
   LEFTARG    = spath,
   RIGHTARG   = scircle,
   PROCEDURE  = scircle_contains_path_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( spath, scircle ) IS
  'true if spherical circle contains spherical path'; 

--
--  circle does not contains path
--

CREATE FUNCTION scircle_contains_path_neg(scircle,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_cont_path_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION scircle_contains_path_neg(scircle,spath) IS
  'true if spherical circle does not contain spherical path '; 


CREATE OPERATOR !~ (
   LEFTARG    = scircle,
   RIGHTARG   = spath,
   PROCEDURE  = scircle_contains_path_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( scircle, spath ) IS
  'true if spherical circle contains spherical path'; 

--
--  path is not contained by circle
--

CREATE FUNCTION scircle_contains_path_com_neg(spath,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_cont_path_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION scircle_contains_path_com_neg(spath,scircle) IS
  'true if spherical circle does not contain spherical path '; 

CREATE OPERATOR !@ (
   LEFTARG    = spath,
   RIGHTARG   = scircle,
   PROCEDURE  = scircle_contains_path_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( spath, scircle ) IS
  'true if spherical circle does not contain spherical path'; 


--
-- circle overlaps path
--

CREATE FUNCTION scircle_overlap_path(scircle,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_overlap_path'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  scircle_overlap_path(scircle,spath) IS
  'true if spherical circle overlap spherical path '; 


CREATE OPERATOR && (
   LEFTARG    = scircle,
   RIGHTARG   = spath,
   PROCEDURE  = scircle_overlap_path,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( scircle, spath ) IS
  'true if spherical circle overlap spherical path'; 


--
-- path overlaps circle
--

CREATE FUNCTION scircle_overlap_path_com(spath,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_overlap_path_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  scircle_overlap_path_com(spath,scircle) IS
  'true if spherical circle overlap spherical path '; 

CREATE OPERATOR && (
   LEFTARG    = spath,
   RIGHTARG   = scircle,
   PROCEDURE  = scircle_overlap_path_com,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( spath, scircle ) IS
  'true if spherical circle overlap spherical path'; 

--
-- circle does not overlap path
--

CREATE FUNCTION scircle_overlap_path_neg(scircle,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_overlap_path_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  scircle_overlap_path_neg(scircle,spath) IS
  'true if spherical circle does not overlap spherical path '; 


CREATE OPERATOR !&& (
   LEFTARG    = scircle,
   RIGHTARG   = spath,
   PROCEDURE  = scircle_overlap_path_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( scircle, spath ) IS
  'true if spherical circle does not overlap spherical path'; 


--
-- path does not overlap circle
--

CREATE FUNCTION scircle_overlap_path_com_neg(spath,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_overlap_path_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  scircle_overlap_path_com_neg(spath,scircle) IS
  'true if spherical circle overlap spherical path '; 

CREATE OPERATOR !&& (
   LEFTARG    = spath,
   RIGHTARG   = scircle,
   PROCEDURE  = scircle_overlap_path_com_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( spath, scircle ) IS
  'true if spherical circle does not overlap spherical path'; 

--
-- path overlaps line
--

CREATE FUNCTION spath_overlap_line(spath,sline)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepath_overlap_line'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spath_overlap_line(spath,sline) IS
  'true if spherical path overlaps spherical line '; 


CREATE OPERATOR && (
   LEFTARG    = spath,
   RIGHTARG   = sline,
   PROCEDURE  = spath_overlap_line,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( spath, sline ) IS
  'true if spherical line overlap spherical path'; 


--
-- line overlaps path
--

CREATE FUNCTION spath_overlap_line_com(sline,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepath_overlap_line_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spath_overlap_line_com(sline,spath) IS
  'true if spherical path overlaps spherical line '; 


CREATE OPERATOR && (
   LEFTARG    = sline,
   RIGHTARG   = spath,
   PROCEDURE  = spath_overlap_line_com,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( sline, spath ) IS
  'true if spherical line overlap spherical path'; 


--
-- path does not overlap line
--

CREATE FUNCTION spath_overlap_line_neg(spath,sline)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepath_overlap_line_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spath_overlap_line_neg(spath,sline) IS
  'true if spherical path does not overlap spherical line '; 


CREATE OPERATOR !&& (
   LEFTARG    = spath,
   RIGHTARG   = sline,
   PROCEDURE  = spath_overlap_line_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( spath, sline ) IS
  'true if spherical line does not overlap spherical path'; 


--
-- line does not overlap path
--

CREATE FUNCTION spath_overlap_line_com_neg(sline,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepath_overlap_line_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spath_overlap_line_com_neg(sline,spath) IS
  'true if spherical path does not overlap spherical line '; 


CREATE OPERATOR !&& (
   LEFTARG    = sline,
   RIGHTARG   = spath,
   PROCEDURE  = spath_overlap_line_com_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( sline, spath ) IS
  'true if spherical line does not overlap spherical path'; 


--
--  ellipse contains path
--

CREATE FUNCTION sellipse_contains_path(sellipse,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_path'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_path(sellipse,spath) IS
  'true if spherical ellipse contains spherical path '; 


CREATE OPERATOR ~ (
   LEFTARG    = sellipse,
   RIGHTARG   = spath,
   PROCEDURE  = sellipse_contains_path,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( sellipse, spath ) IS
  'true if spherical ellipse contains spherical path'; 

--
--  path is contained by ellipse
--

CREATE FUNCTION sellipse_contains_path_com(spath,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_path_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_path_com(spath,sellipse) IS
  'true if spherical ellipse contains spherical path '; 

CREATE OPERATOR @ (
   LEFTARG    = spath,
   RIGHTARG   = sellipse,
   PROCEDURE  = sellipse_contains_path_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( spath, sellipse ) IS
  'true if spherical ellipse contains spherical path'; 

--
--  ellipse does not contains path
--

CREATE FUNCTION sellipse_contains_path_neg(sellipse,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_path_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_path_neg(sellipse,spath) IS
  'true if spherical ellipse does not contain spherical path '; 


CREATE OPERATOR !~ (
   LEFTARG    = sellipse,
   RIGHTARG   = spath,
   PROCEDURE  = sellipse_contains_path_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( sellipse, spath ) IS
  'true if spherical ellipse contains spherical path'; 

--
--  path is not contained by ellipse
--

CREATE FUNCTION sellipse_contains_path_com_neg(spath,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_path_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_path_com_neg(spath,sellipse) IS
  'true if spherical ellipse does not contain spherical path '; 

CREATE OPERATOR !@ (
   LEFTARG    = spath,
   RIGHTARG   = sellipse,
   PROCEDURE  = sellipse_contains_path_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( spath, sellipse ) IS
  'true if spherical ellipse does not contain spherical path'; 


--
-- ellipse overlaps path
--

CREATE FUNCTION sellipse_overlap_path(sellipse,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_overlap_path'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sellipse_overlap_path(sellipse,spath) IS
  'true if spherical ellipse overlap spherical path '; 


CREATE OPERATOR && (
   LEFTARG    = sellipse,
   RIGHTARG   = spath,
   PROCEDURE  = sellipse_overlap_path,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( sellipse, spath ) IS
  'true if spherical ellipse overlap spherical path'; 


--
-- path overlaps ellipse
--

CREATE FUNCTION sellipse_overlap_path_com(spath,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_overlap_path_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sellipse_overlap_path_com(spath,sellipse) IS
  'true if spherical ellipse overlap spherical path '; 

CREATE OPERATOR && (
   LEFTARG    = spath,
   RIGHTARG   = sellipse,
   PROCEDURE  = sellipse_overlap_path_com,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( spath, sellipse ) IS
  'true if spherical ellipse overlap spherical path'; 

--
-- ellipse does not overlap path
--

CREATE FUNCTION sellipse_overlap_path_neg(sellipse,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_overlap_path_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sellipse_overlap_path_neg(sellipse,spath) IS
  'true if spherical ellipse does not overlap spherical path '; 


CREATE OPERATOR !&& (
   LEFTARG    = sellipse,
   RIGHTARG   = spath,
   PROCEDURE  = sellipse_overlap_path_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( sellipse, spath ) IS
  'true if spherical ellipse does not overlap spherical path'; 


--
-- path does not overlap ellipse
--

CREATE FUNCTION sellipse_overlap_path_com_neg(spath,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_overlap_path_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sellipse_overlap_path_com_neg(spath,sellipse) IS
  'true if spherical ellipse overlap spherical path '; 

CREATE OPERATOR !&& (
   LEFTARG    = spath,
   RIGHTARG   = sellipse,
   PROCEDURE  = sellipse_overlap_path_com_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( spath, sellipse ) IS
  'true if spherical ellipse does not overlap spherical path'; 


--
--  polygon contains path
--

CREATE FUNCTION spoly_contains_path(spoly,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_path'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_path(spoly,spath) IS
  'true if spherical polygon contains spherical path '; 


CREATE OPERATOR ~ (
   LEFTARG    = spoly,
   RIGHTARG   = spath,
   PROCEDURE  = spoly_contains_path,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( spoly, spath ) IS
  'true if spherical polygon contains spherical path'; 

--
--  path is contained by polygon
--

CREATE FUNCTION spoly_contains_path_com(spath,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_path_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_path_com(spath,spoly) IS
  'true if spherical polygon contains spherical path '; 

CREATE OPERATOR @ (
   LEFTARG    = spath,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_contains_path_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( spath, spoly ) IS
  'true if spherical polygon contains spherical path'; 

--
--  polygon does not contains path
--

CREATE FUNCTION spoly_contains_path_neg(spoly,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_path_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_path_neg(spoly,spath) IS
  'true if spherical polygon does not contain spherical path '; 


CREATE OPERATOR !~ (
   LEFTARG    = spoly,
   RIGHTARG   = spath,
   PROCEDURE  = spoly_contains_path_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( spoly, spath ) IS
  'true if spherical polygon contains spherical path'; 

--
--  path is not contained by polygon
--

CREATE FUNCTION spoly_contains_path_com_neg(spath,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_path_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_path_com_neg(spath,spoly) IS
  'true if spherical polygon does not contain spherical path '; 

CREATE OPERATOR !@ (
   LEFTARG    = spath,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_contains_path_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( spath, spoly ) IS
  'true if spherical polygon does not contain spherical path'; 


--
-- polygon overlaps path
--

CREATE FUNCTION spoly_overlap_path(spoly,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_overlap_path'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spoly_overlap_path(spoly,spath) IS
  'true if spherical polygon overlap spherical path '; 


CREATE OPERATOR && (
   LEFTARG    = spoly,
   RIGHTARG   = spath,
   PROCEDURE  = spoly_overlap_path,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( spoly, spath ) IS
  'true if spherical polygon overlap spherical path'; 


--
-- path overlaps polygon
--

CREATE FUNCTION spoly_overlap_path_com(spath,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_overlap_path_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spoly_overlap_path_com(spath,spoly) IS
  'true if spherical polygon overlap spherical path '; 

CREATE OPERATOR && (
   LEFTARG    = spath,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_overlap_path_com,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( spath, spoly ) IS
  'true if spherical polygon overlap spherical path'; 

--
-- polygon does not overlap path
--

CREATE FUNCTION spoly_overlap_path_neg(spoly,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_overlap_path_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spoly_overlap_path_neg(spoly,spath) IS
  'true if spherical polygon does not overlap spherical path '; 


CREATE OPERATOR !&& (
   LEFTARG    = spoly,
   RIGHTARG   = spath,
   PROCEDURE  = spoly_overlap_path_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( spoly, spath ) IS
  'true if spherical polygon does not overlap spherical path'; 


--
-- path does not overlap polygon
--

CREATE FUNCTION spoly_overlap_path_com_neg(spath,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_overlap_path_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  spoly_overlap_path_com_neg(spath,spoly) IS
  'true if spherical polygon overlap spherical path '; 

CREATE OPERATOR !&& (
   LEFTARG    = spath,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_overlap_path_com_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( spath, spoly ) IS
  'true if spherical polygon does not overlap spherical path'; 



--
-- Aggregate functions to add points to path
--

CREATE FUNCTION spath_add_point_aggr ( spath, spoint )
  RETURNS spath
  AS '$libdir/pg_sphere' , 'spherepath_add_point'
  LANGUAGE 'c'
  IMMUTABLE;

COMMENT ON FUNCTION  spath_add_point_aggr ( spath, spoint ) IS
  'adds a spherical point to spherical path. Do not use it standalone!'; 

CREATE FUNCTION spath_add_points_fin_aggr ( spath )
  RETURNS spath
  AS '$libdir/pg_sphere' , 'spherepath_add_points_finalize'
  LANGUAGE 'c'
  IMMUTABLE STRICT ;

COMMENT ON FUNCTION  spath_add_points_fin_aggr ( spath ) IS
  'Finalize spherical point adding to spherical path. Do not use it standalone!'; 

CREATE AGGREGATE spath (
    sfunc     = spath_add_point_aggr,
    basetype  = spoint,
    stype     = spath,
    finalfunc = spath_add_points_fin_aggr
);
-- **************************
--
-- spherical box functions
--
-- **************************

CREATE FUNCTION sbox(spoint, spoint)
   RETURNS sbox
   AS '$libdir/pg_sphere' , 'spherebox_in_from_points'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION sbox(spoint, spoint) IS
  'returns a spherical box from south-west corner( arg1 ) and north-east corner( arg2 )'; 

CREATE FUNCTION sw(sbox)
   RETURNS spoint
   AS '$libdir/pg_sphere' , 'spherebox_sw'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION sw(sbox) IS
  'south-west corner of spherical box'; 

CREATE FUNCTION se(sbox)
   RETURNS spoint
   AS '$libdir/pg_sphere' , 'spherebox_se'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION se(sbox) IS
  'south-east corner of spherical box'; 


CREATE FUNCTION nw(sbox)
   RETURNS spoint
   AS '$libdir/pg_sphere' , 'spherebox_nw'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION nw(sbox) IS
  'north-west corner of spherical box'; 

CREATE FUNCTION ne(sbox)
   RETURNS spoint
   AS '$libdir/pg_sphere' , 'spherebox_ne'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION ne(sbox) IS
  'north-east corner of spherical box'; 


CREATE FUNCTION area(sbox)
   RETURNS FLOAT8
   AS '$libdir/pg_sphere', 'spherebox_area'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION area(sbox) IS
  'area of a spherical box';


--
-- circumference
--

CREATE FUNCTION circum(sbox)
   RETURNS FLOAT8
   AS '$libdir/pg_sphere', 'spherebox_circ'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION circum(sbox) IS
  'circumference of spherical box'; 

CREATE OPERATOR @-@ (
   RIGHTARG   = sbox,
   PROCEDURE  = circum
);

COMMENT ON OPERATOR @-@ ( NONE , sbox ) IS
  'circumference of spherical box';


--
-- equal
--

CREATE FUNCTION sbox_equal(sbox,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_equal'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION sbox_equal(sbox,sbox) IS
  'returns true, if spherical boxes are equal';

CREATE OPERATOR  = (
   LEFTARG    = sbox,
   RIGHTARG   = sbox,
   COMMUTATOR = =,
   NEGATOR    = <>,
   PROCEDURE  = sbox_equal,
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);

COMMENT ON OPERATOR = ( sbox, sbox ) IS
  'true, if spherical boxes are equal';

--
-- not equal
--
  
CREATE FUNCTION sbox_equal_neg (sbox,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_equal_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION sbox_equal_neg (sbox,sbox) IS
  'returns true, if spherical boxes are not equal';   


CREATE OPERATOR  <> (
   LEFTARG    = sbox,
   RIGHTARG   = sbox,
   COMMUTATOR = <>,
   NEGATOR    = =, 
   PROCEDURE  = sbox_equal_neg ,
   RESTRICT   = contsel,
   JOIN       = contjoinsel
);

COMMENT ON OPERATOR <> ( sbox, sbox ) IS
  'true, if spherical boxes are not equal';

--
-- box contains box
--

CREATE FUNCTION sbox_contains_box(sbox,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_box'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_box(sbox,sbox) IS
  'true if spherical box contains spherical box '; 


CREATE OPERATOR ~ (
   LEFTARG    = sbox,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_contains_box,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( sbox, sbox ) IS
  'true if spherical box contains spherical box'; 


--
-- box is contained by box
--

CREATE FUNCTION sbox_contains_box_com(sbox, sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_box_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_box_com(sbox, sbox) IS
  'true if spherical box contains spherical box '; 


CREATE OPERATOR @ (
   LEFTARG    = sbox,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_contains_box_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( sbox, sbox ) IS
  'true if spherical box contains spherical box'; 

--
-- box does not contain box
--

CREATE FUNCTION sbox_contains_box_neg(sbox,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_box_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_box_neg(sbox,sbox) IS
  'true if spherical box does not contain spherical box '; 


CREATE OPERATOR !~ (
   LEFTARG    = sbox,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_contains_box_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( sbox, sbox ) IS
  'true if spherical box does not contain spherical box'; 


--
-- box is not contained by box
--

CREATE FUNCTION sbox_contains_box_com_neg(sbox, sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_box_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_box_com_neg(sbox, sbox) IS
  'true if spherical box does not contain spherical box '; 


CREATE OPERATOR !@ (
   LEFTARG    = sbox,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_contains_box_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( sbox, sbox ) IS
  'true if spherical box does not contain spherical box'; 

--
-- box overlaps box
--

CREATE FUNCTION sbox_overlap_box(sbox,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_box'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_overlap_box(sbox,sbox) IS
  'true if spherical box overlap spherical box '; 


CREATE OPERATOR && (
   LEFTARG    = sbox,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_overlap_box,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( sbox, sbox ) IS
  'true if spherical box overlap spherical box'; 

--
-- box does not overlap box
--

CREATE FUNCTION sbox_overlap_box_neg(sbox,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_box_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sbox_overlap_box_neg(sbox,sbox) IS
  'true if spherical box does not overlap spherical box '; 


CREATE OPERATOR !&& (
   LEFTARG    = sbox,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_overlap_box_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( sbox, sbox ) IS
  'true if spherical box does not overlap spherical box'; 

--
-- point is contained by box
--

CREATE FUNCTION sbox_cont_point_com(spoint,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_point_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;
   
COMMENT ON FUNCTION sbox_cont_point_com(spoint,sbox) IS
  'true if spherical point is contained by spherical box'; 


CREATE OPERATOR @ (
   LEFTARG    = spoint,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_cont_point_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( spoint, sbox ) IS
  'true if spherical point is contained by spherical box'; 


--
-- point is not contained by box
--

CREATE FUNCTION sbox_cont_point_com_neg(spoint,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_point_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION sbox_cont_point_com_neg (spoint,sbox) IS
  'true if spherical point is not contained by spherical box '; 


CREATE OPERATOR !@ (
   LEFTARG    = spoint,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_cont_point_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( spoint, sbox ) IS
  'true if spherical point is not contained by spherical box'; 


--
-- box contains point
--

CREATE FUNCTION sbox_cont_point(sbox,spoint)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_point'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION sbox_cont_point (sbox,spoint) IS
  'true if spherical box contains spherical point '; 


CREATE OPERATOR ~ (
   LEFTARG    = sbox,
   RIGHTARG   = spoint,
   PROCEDURE  = sbox_cont_point,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( sbox, spoint ) IS
  'true if spherical box contains spherical point'; 


--
-- box does not contain point
--

CREATE FUNCTION sbox_cont_point_neg(sbox,spoint)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_point_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT ;

COMMENT ON FUNCTION sbox_cont_point_neg (sbox,spoint) IS
  'true if spherical box does not contain spherical point '; 


CREATE OPERATOR !~ (
   LEFTARG    = sbox,
   RIGHTARG   = spoint,
   PROCEDURE  = sbox_cont_point_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( sbox, spoint ) IS
  'true if spherical box does not contain spherical point'; 

--
-- box contains circle
--

CREATE FUNCTION sbox_contains_circle(sbox,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_circle'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_circle(sbox,scircle) IS
  'true if spherical box contains spherical circle '; 


CREATE OPERATOR ~ (
   LEFTARG    = sbox,
   RIGHTARG   = scircle,
   PROCEDURE  = sbox_contains_circle,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( sbox, scircle ) IS
  'true if spherical box contains spherical circle'; 


--
-- circle is contained by box
--

CREATE FUNCTION sbox_contains_circle_com(scircle, sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_circle_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_circle_com(scircle, sbox) IS
  'true if spherical box contains spherical circle '; 


CREATE OPERATOR @ (
   LEFTARG    = scircle,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_contains_circle_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( scircle, sbox ) IS
  'true if spherical box contains spherical circle'; 

--
-- box does not contain circle
--

CREATE FUNCTION sbox_contains_circle_neg(sbox,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_circle_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_circle_neg(sbox,scircle) IS
  'true if spherical box does not contain spherical circle '; 


CREATE OPERATOR !~ (
   LEFTARG    = sbox,
   RIGHTARG   = scircle,
   PROCEDURE  = sbox_contains_circle_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( sbox, scircle ) IS
  'true if spherical box does not contain spherical circle'; 


--
-- circle is not contained by box
--

CREATE FUNCTION sbox_contains_circle_com_neg(scircle, sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_circle_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_circle_com_neg(scircle, sbox) IS
  'true if spherical box does not contain spherical circle '; 


CREATE OPERATOR !@ (
   LEFTARG    = scircle,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_contains_circle_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( scircle, sbox ) IS
  'true if spherical box does not contain spherical circle'; 


--
-- circle contains box
--

CREATE FUNCTION scircle_contains_box(scircle,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_cont_box'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION scircle_contains_box(scircle,sbox) IS
  'true if spherical circle contains spherical box '; 


CREATE OPERATOR ~ (
   LEFTARG    = scircle,
   RIGHTARG   = sbox,
   PROCEDURE  = scircle_contains_box,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( scircle, sbox ) IS
  'true if spherical circle contains spherical box'; 


--
-- box is contained by circle
--

CREATE FUNCTION scircle_contains_box_com(sbox, scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_cont_box_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION scircle_contains_box_com(sbox, scircle) IS
  'true if spherical circle contains spherical box '; 


CREATE OPERATOR @ (
   LEFTARG    = sbox,
   RIGHTARG   = scircle,
   PROCEDURE  = scircle_contains_box_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( sbox, scircle ) IS
  'true if spherical circle contains spherical box'; 

--
-- circle does not contain box
--

CREATE FUNCTION scircle_contains_box_neg(scircle,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_cont_box_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION scircle_contains_box_neg(scircle,sbox) IS
  'true if spherical circle does not contain spherical box '; 


CREATE OPERATOR !~ (
   LEFTARG    = scircle,
   RIGHTARG   = sbox,
   PROCEDURE  = scircle_contains_box_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( scircle, sbox ) IS
  'true if spherical circle does not contain spherical box'; 


--
-- box is not contained by circle
--

CREATE FUNCTION scircle_contains_box_com_neg(sbox, scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherecircle_cont_box_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION scircle_contains_box_com_neg(sbox, scircle) IS
  'true if spherical circle does not contain spherical box '; 


CREATE OPERATOR !@ (
   LEFTARG    = sbox,
   RIGHTARG   = scircle,
   PROCEDURE  = scircle_contains_box_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( sbox, scircle ) IS
  'true if spherical circle does not contain spherical box'; 


--
-- circle overlaps box
--

CREATE FUNCTION sbox_overlap_circle(sbox,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_circle'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_overlap_circle(sbox,scircle) IS
  'true if spherical circle overlap spherical box '; 


CREATE OPERATOR && (
   LEFTARG    = sbox,
   RIGHTARG   = scircle,
   PROCEDURE  = sbox_overlap_circle,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( sbox, scircle ) IS
  'true if spherical circle overlap spherical box'; 

--
-- box overlaps circle
--

CREATE FUNCTION sbox_overlap_circle_com(scircle,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_circle_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sbox_overlap_circle_com(scircle,sbox) IS
  'true if spherical circle overlap spherical box '; 


CREATE OPERATOR && (
   LEFTARG    = scircle,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_overlap_circle_com,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( scircle, sbox ) IS
  'true if spherical circle overlap spherical box'; 


--
-- circle does not overlap box
--

CREATE FUNCTION sbox_overlap_circle_neg(sbox,scircle)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_circle_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sbox_overlap_circle_neg(sbox,scircle) IS
  'true if spherical circle does not overlap spherical box '; 


CREATE OPERATOR !&& (
   LEFTARG    = sbox,
   RIGHTARG   = scircle,
   PROCEDURE  = sbox_overlap_circle_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( sbox, scircle ) IS
  'true if spherical circle does not overlap spherical box'; 

--
-- box does not overlap circle
--

CREATE FUNCTION sbox_overlap_circle_com_neg(scircle,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_circle_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sbox_overlap_circle_com_neg(scircle,sbox) IS
  'true if spherical circle does not overlap spherical box '; 


CREATE OPERATOR !&& (
   LEFTARG    = scircle,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_overlap_circle_com_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( scircle, sbox ) IS
  'true if spherical circle does not overlap spherical box'; 


--
-- box contains line
--

CREATE FUNCTION sbox_contains_line(sbox,sline)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_line'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_line(sbox,sline) IS
  'true if spherical box contains spherical line '; 


CREATE OPERATOR ~ (
   LEFTARG    = sbox,
   RIGHTARG   = sline,
   PROCEDURE  = sbox_contains_line,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( sbox, sline ) IS
  'true if spherical box contains spherical line'; 


--
-- line is contained by box
--

CREATE FUNCTION sbox_contains_line_com(sline, sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_line_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_line_com(sline, sbox) IS
  'true if spherical box contains spherical line '; 


CREATE OPERATOR @ (
   LEFTARG    = sline,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_contains_line_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( sline, sbox ) IS
  'true if spherical box contains spherical line'; 

--
-- box does not contain line
--

CREATE FUNCTION sbox_contains_line_neg(sbox,sline)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_line_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_line_neg(sbox,sline) IS
  'true if spherical box does not contain spherical line '; 


CREATE OPERATOR !~ (
   LEFTARG    = sbox,
   RIGHTARG   = sline,
   PROCEDURE  = sbox_contains_line_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( sbox, sline ) IS
  'true if spherical box does not contain spherical line'; 


--
-- line is not contained by box
--

CREATE FUNCTION sbox_contains_line_com_neg(sline, sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_line_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_line_com_neg(sline, sbox) IS
  'true if spherical box does not contain spherical line '; 


CREATE OPERATOR !@ (
   LEFTARG    = sline,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_contains_line_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( sline, sbox ) IS
  'true if spherical box does not contain spherical line'; 


--
-- line overlaps box
--

CREATE FUNCTION sbox_overlap_line(sbox,sline)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_line'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_overlap_line(sbox,sline) IS
  'true if spherical line overlap spherical box '; 


CREATE OPERATOR && (
   LEFTARG    = sbox,
   RIGHTARG   = sline,
   PROCEDURE  = sbox_overlap_line,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( sbox, sline ) IS
  'true if spherical line overlap spherical box'; 

--
-- box overlaps line
--

CREATE FUNCTION sbox_overlap_line_com(sline,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_line_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sbox_overlap_line_com(sline,sbox) IS
  'true if spherical line overlap spherical box '; 


CREATE OPERATOR && (
   LEFTARG    = sline,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_overlap_line_com,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( sline, sbox ) IS
  'true if spherical line overlap spherical box'; 


--
-- line does not overlap box
--

CREATE FUNCTION sbox_overlap_line_neg(sbox,sline)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_line_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sbox_overlap_line_neg(sbox,sline) IS
  'true if spherical line does not overlap spherical box '; 


CREATE OPERATOR !&& (
   LEFTARG    = sbox,
   RIGHTARG   = sline,
   PROCEDURE  = sbox_overlap_line_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( sbox, sline ) IS
  'true if spherical line does not overlap spherical box'; 

--
-- box does not overlap line
--

CREATE FUNCTION sbox_overlap_line_com_neg(sline,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_line_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sbox_overlap_line_com_neg(sline,sbox) IS
  'true if spherical line does not overlap spherical box '; 


CREATE OPERATOR !&& (
   LEFTARG    = sline,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_overlap_line_com_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( sline, sbox ) IS
  'true if spherical line does not overlap spherical box'; 


--
-- box contains ellipse
--

CREATE FUNCTION sbox_contains_ellipse(sbox,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_ellipse'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_ellipse(sbox,sellipse) IS
  'true if spherical box contains spherical ellipse '; 


CREATE OPERATOR ~ (
   LEFTARG    = sbox,
   RIGHTARG   = sellipse,
   PROCEDURE  = sbox_contains_ellipse,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( sbox, sellipse ) IS
  'true if spherical box contains spherical ellipse'; 


--
-- ellipse is contained by box
--

CREATE FUNCTION sbox_contains_ellipse_com(sellipse, sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_ellipse_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_ellipse_com(sellipse, sbox) IS
  'true if spherical box contains spherical ellipse '; 


CREATE OPERATOR @ (
   LEFTARG    = sellipse,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_contains_ellipse_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( sellipse, sbox ) IS
  'true if spherical box contains spherical ellipse'; 

--
-- box does not contain ellipse
--

CREATE FUNCTION sbox_contains_ellipse_neg(sbox,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_ellipse_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_ellipse_neg(sbox,sellipse) IS
  'true if spherical box does not contain spherical ellipse '; 


CREATE OPERATOR !~ (
   LEFTARG    = sbox,
   RIGHTARG   = sellipse,
   PROCEDURE  = sbox_contains_ellipse_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( sbox, sellipse ) IS
  'true if spherical box does not contain spherical ellipse'; 


--
-- ellipse is not contained by box
--

CREATE FUNCTION sbox_contains_ellipse_com_neg(sellipse, sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_ellipse_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_ellipse_com_neg(sellipse, sbox) IS
  'true if spherical box does not contain spherical ellipse '; 


CREATE OPERATOR !@ (
   LEFTARG    = sellipse,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_contains_ellipse_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( sellipse, sbox ) IS
  'true if spherical box does not contain spherical ellipse'; 


--
-- ellipse contains box
--

CREATE FUNCTION sellipse_contains_box(sellipse,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_box'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_box(sellipse,sbox) IS
  'true if spherical ellipse contains spherical box '; 


CREATE OPERATOR ~ (
   LEFTARG    = sellipse,
   RIGHTARG   = sbox,
   PROCEDURE  = sellipse_contains_box,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( sellipse, sbox ) IS
  'true if spherical ellipse contains spherical box'; 


--
-- box is contained by ellipse
--

CREATE FUNCTION sellipse_contains_box_com(sbox, sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_box_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_box_com(sbox, sellipse) IS
  'true if spherical ellipse contains spherical box '; 


CREATE OPERATOR @ (
   LEFTARG    = sbox,
   RIGHTARG   = sellipse,
   PROCEDURE  = sellipse_contains_box_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( sbox, sellipse ) IS
  'true if spherical ellipse contains spherical box'; 

--
-- ellipse does not contain box
--

CREATE FUNCTION sellipse_contains_box_neg(sellipse,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_box_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_box_neg(sellipse,sbox) IS
  'true if spherical ellipse does not contain spherical box '; 


CREATE OPERATOR !~ (
   LEFTARG    = sellipse,
   RIGHTARG   = sbox,
   PROCEDURE  = sellipse_contains_box_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( sellipse, sbox ) IS
  'true if spherical ellipse does not contain spherical box'; 


--
-- box is not contained by ellipse
--

CREATE FUNCTION sellipse_contains_box_com_neg(sbox, sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'sphereellipse_cont_box_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sellipse_contains_box_com_neg(sbox, sellipse) IS
  'true if spherical ellipse does not contain spherical box '; 


CREATE OPERATOR !@ (
   LEFTARG    = sbox,
   RIGHTARG   = sellipse,
   PROCEDURE  = sellipse_contains_box_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( sbox, sellipse ) IS
  'true if spherical ellipse does not contain spherical box'; 


--
-- ellipse overlaps box
--

CREATE FUNCTION sbox_overlap_ellipse(sbox,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_ellipse'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_overlap_ellipse(sbox,sellipse) IS
  'true if spherical ellipse overlap spherical box '; 


CREATE OPERATOR && (
   LEFTARG    = sbox,
   RIGHTARG   = sellipse,
   PROCEDURE  = sbox_overlap_ellipse,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( sbox, sellipse ) IS
  'true if spherical ellipse overlap spherical box'; 

--
-- box overlaps ellipse
--

CREATE FUNCTION sbox_overlap_ellipse_com(sellipse,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_ellipse_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sbox_overlap_ellipse_com(sellipse,sbox) IS
  'true if spherical ellipse overlap spherical box '; 


CREATE OPERATOR && (
   LEFTARG    = sellipse,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_overlap_ellipse_com,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( sellipse, sbox ) IS
  'true if spherical ellipse overlap spherical box'; 


--
-- ellipse does not overlap box
--

CREATE FUNCTION sbox_overlap_ellipse_neg(sbox,sellipse)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_ellipse_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sbox_overlap_ellipse_neg(sbox,sellipse) IS
  'true if spherical ellipse does not overlap spherical box '; 


CREATE OPERATOR !&& (
   LEFTARG    = sbox,
   RIGHTARG   = sellipse,
   PROCEDURE  = sbox_overlap_ellipse_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( sbox, sellipse ) IS
  'true if spherical ellipse does not overlap spherical box'; 

--
-- box does not overlap ellipse
--

CREATE FUNCTION sbox_overlap_ellipse_com_neg(sellipse,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_ellipse_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sbox_overlap_ellipse_com_neg(sellipse,sbox) IS
  'true if spherical ellipse does not overlap spherical box '; 


CREATE OPERATOR !&& (
   LEFTARG    = sellipse,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_overlap_ellipse_com_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( sellipse, sbox ) IS
  'true if spherical ellipse does not overlap spherical box'; 



--
-- box contains polygon
--

CREATE FUNCTION sbox_contains_poly(sbox,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_poly'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_poly(sbox,spoly) IS
  'true if spherical box contains spherical polygon '; 


CREATE OPERATOR ~ (
   LEFTARG    = sbox,
   RIGHTARG   = spoly,
   PROCEDURE  = sbox_contains_poly,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( sbox, spoly ) IS
  'true if spherical box contains spherical polygon'; 


--
-- polygon is contained by box
--

CREATE FUNCTION sbox_contains_poly_com(spoly, sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_poly_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_poly_com(spoly, sbox) IS
  'true if spherical box contains spherical polygon '; 


CREATE OPERATOR @ (
   LEFTARG    = spoly,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_contains_poly_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( spoly, sbox ) IS
  'true if spherical box contains spherical polygon'; 

--
-- box does not contain polygon
--

CREATE FUNCTION sbox_contains_poly_neg(sbox,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_poly_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_poly_neg(sbox,spoly) IS
  'true if spherical box does not contain spherical polygon '; 


CREATE OPERATOR !~ (
   LEFTARG    = sbox,
   RIGHTARG   = spoly,
   PROCEDURE  = sbox_contains_poly_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( sbox, spoly ) IS
  'true if spherical box does not contain spherical polygon'; 


--
-- polygon is not contained by box
--

CREATE FUNCTION sbox_contains_poly_com_neg(spoly, sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_poly_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_poly_com_neg(spoly, sbox) IS
  'true if spherical box does not contain spherical polygon '; 


CREATE OPERATOR !@ (
   LEFTARG    = spoly,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_contains_poly_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( spoly, sbox ) IS
  'true if spherical box does not contain spherical polygon'; 


--
-- polygon contains box
--

CREATE FUNCTION spoly_contains_box(spoly,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_box'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_box(spoly,sbox) IS
  'true if spherical polygon contains spherical box '; 


CREATE OPERATOR ~ (
   LEFTARG    = spoly,
   RIGHTARG   = sbox,
   PROCEDURE  = spoly_contains_box,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( spoly, sbox ) IS
  'true if spherical polygon contains spherical box'; 


--
-- box is contained by polygon
--

CREATE FUNCTION spoly_contains_box_com(sbox, spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_box_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_box_com(sbox, spoly) IS
  'true if spherical polygon contains spherical box '; 


CREATE OPERATOR @ (
   LEFTARG    = sbox,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_contains_box_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( sbox, spoly ) IS
  'true if spherical polygon contains spherical box'; 

--
-- polygon does not contain box
--

CREATE FUNCTION spoly_contains_box_neg(spoly,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_box_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_box_neg(spoly,sbox) IS
  'true if spherical polygon does not contain spherical box '; 


CREATE OPERATOR !~ (
   LEFTARG    = spoly,
   RIGHTARG   = sbox,
   PROCEDURE  = spoly_contains_box_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( spoly, sbox ) IS
  'true if spherical polygon does not contain spherical box'; 


--
-- box is not contained by polygon
--

CREATE FUNCTION spoly_contains_box_com_neg(sbox, spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherepoly_cont_box_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION spoly_contains_box_com_neg(sbox, spoly) IS
  'true if spherical polygon does not contain spherical box '; 


CREATE OPERATOR !@ (
   LEFTARG    = sbox,
   RIGHTARG   = spoly,
   PROCEDURE  = spoly_contains_box_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( sbox, spoly ) IS
  'true if spherical polygon does not contain spherical box'; 


--
-- polygon overlaps box
--

CREATE FUNCTION sbox_overlap_poly(sbox,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_poly'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_overlap_poly(sbox,spoly) IS
  'true if spherical polygon overlap spherical box '; 


CREATE OPERATOR && (
   LEFTARG    = sbox,
   RIGHTARG   = spoly,
   PROCEDURE  = sbox_overlap_poly,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( sbox, spoly ) IS
  'true if spherical polygon overlap spherical box'; 

--
-- box overlaps polygon
--

CREATE FUNCTION sbox_overlap_poly_com(spoly,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_poly_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sbox_overlap_poly_com(spoly,sbox) IS
  'true if spherical polygon overlap spherical box '; 


CREATE OPERATOR && (
   LEFTARG    = spoly,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_overlap_poly_com,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( spoly, sbox ) IS
  'true if spherical polygon overlap spherical box'; 


--
-- polygon does not overlap box
--

CREATE FUNCTION sbox_overlap_poly_neg(sbox,spoly)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_poly_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sbox_overlap_poly_neg(sbox,spoly) IS
  'true if spherical polygon does not overlap spherical box '; 


CREATE OPERATOR !&& (
   LEFTARG    = sbox,
   RIGHTARG   = spoly,
   PROCEDURE  = sbox_overlap_poly_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( sbox, spoly ) IS
  'true if spherical polygon does not overlap spherical box'; 

--
-- box does not overlap polygon
--

CREATE FUNCTION sbox_overlap_poly_com_neg(spoly,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_poly_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sbox_overlap_poly_com_neg(spoly,sbox) IS
  'true if spherical polygon does not overlap spherical box '; 


CREATE OPERATOR !&& (
   LEFTARG    = spoly,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_overlap_poly_com_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( spoly, sbox ) IS
  'true if spherical polygon does not overlap spherical box'; 



--
-- box contains path
--

CREATE FUNCTION sbox_contains_path(sbox,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_path'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_path(sbox,spath) IS
  'true if spherical box contains spherical path '; 


CREATE OPERATOR ~ (
   LEFTARG    = sbox,
   RIGHTARG   = spath,
   PROCEDURE  = sbox_contains_path,
   COMMUTATOR = '@',
   NEGATOR    = '!~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR ~ ( sbox, spath ) IS
  'true if spherical box contains spherical path'; 


--
-- path is contained by box
--

CREATE FUNCTION sbox_contains_path_com(spath, sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_path_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_path_com(spath, sbox) IS
  'true if spherical box contains spherical path '; 


CREATE OPERATOR @ (
   LEFTARG    = spath,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_contains_path_com,
   COMMUTATOR = '~',
   NEGATOR    = '!@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR @ ( spath, sbox ) IS
  'true if spherical box contains spherical path'; 

--
-- box does not contain path
--

CREATE FUNCTION sbox_contains_path_neg(sbox,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_path_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_path_neg(sbox,spath) IS
  'true if spherical box does not contain spherical path '; 


CREATE OPERATOR !~ (
   LEFTARG    = sbox,
   RIGHTARG   = spath,
   PROCEDURE  = sbox_contains_path_neg,
   COMMUTATOR = '!@',
   NEGATOR    = '~',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !~ ( sbox, spath ) IS
  'true if spherical box does not contain spherical path'; 


--
-- path is not contained by box
--

CREATE FUNCTION sbox_contains_path_com_neg(spath, sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_cont_path_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_contains_path_com_neg(spath, sbox) IS
  'true if spherical box does not contain spherical path '; 


CREATE OPERATOR !@ (
   LEFTARG    = spath,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_contains_path_com_neg,
   COMMUTATOR = '!~',
   NEGATOR    = '@',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !@ ( spath, sbox ) IS
  'true if spherical box does not contain spherical path'; 


--
-- path overlaps box
--

CREATE FUNCTION sbox_overlap_path(sbox,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_path'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION sbox_overlap_path(sbox,spath) IS
  'true if spherical path overlap spherical box '; 


CREATE OPERATOR && (
   LEFTARG    = sbox,
   RIGHTARG   = spath,
   PROCEDURE  = sbox_overlap_path,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( sbox, spath ) IS
  'true if spherical path overlap spherical box'; 

--
-- box overlaps path
--

CREATE FUNCTION sbox_overlap_path_com(spath,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_path_com'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sbox_overlap_path_com(spath,sbox) IS
  'true if spherical path overlap spherical box '; 


CREATE OPERATOR && (
   LEFTARG    = spath,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_overlap_path_com,
   COMMUTATOR = '&&',
   NEGATOR    = '!&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR && ( spath, sbox ) IS
  'true if spherical path overlap spherical box'; 


--
-- path does not overlap box
--

CREATE FUNCTION sbox_overlap_path_neg(sbox,spath)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_path_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sbox_overlap_path_neg(sbox,spath) IS
  'true if spherical path does not overlap spherical box '; 


CREATE OPERATOR !&& (
   LEFTARG    = sbox,
   RIGHTARG   = spath,
   PROCEDURE  = sbox_overlap_path_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( sbox, spath ) IS
  'true if spherical path does not overlap spherical box'; 

--
-- box does not overlap path
--

CREATE FUNCTION sbox_overlap_path_com_neg(spath,sbox)
   RETURNS BOOL
   AS '$libdir/pg_sphere' , 'spherebox_overlap_path_com_neg'
   LANGUAGE 'c'
   IMMUTABLE STRICT;

COMMENT ON FUNCTION  sbox_overlap_path_com_neg(spath,sbox) IS
  'true if spherical path does not overlap spherical box '; 


CREATE OPERATOR !&& (
   LEFTARG    = spath,
   RIGHTARG   = sbox,
   PROCEDURE  = sbox_overlap_path_com_neg,
   COMMUTATOR = '!&&',
   NEGATOR    = '&&',
   RESTRICT   = contsel,
   JOIN       = contjoinsel 
);

COMMENT ON OPERATOR !&& ( spath, sbox ) IS
  'true if spherical path does not overlap spherical box'; 


-- Spherical key definition'

CREATE FUNCTION spherekey_in(CSTRING)
   RETURNS spherekey
   AS '$libdir/pg_sphere'
   LANGUAGE 'c'     
   WITH (isstrict,iscachable);


CREATE FUNCTION spherekey_out(spherekey)
   RETURNS CSTRING
   AS '$libdir/pg_sphere'
   LANGUAGE 'c'   
   WITH (isstrict,iscachable);


CREATE TYPE spherekey (
   internallength = 24,
   input   = spherekey_in,
   output  = spherekey_out
);


-- GIST common

CREATE FUNCTION g_spherekey_decompress(internal)
   RETURNS internal
   AS '$libdir/pg_sphere' , 'g_spherekey_decompress'
   LANGUAGE 'C';   

CREATE FUNCTION g_spherekey_union(bytea, internal)
   RETURNS spherekey
   AS '$libdir/pg_sphere' , 'g_spherekey_union'
   LANGUAGE 'C';   

CREATE FUNCTION g_spherekey_penalty (internal,internal,internal)
   RETURNS internal
   AS '$libdir/pg_sphere' , 'g_spherekey_penalty'
   LANGUAGE 'C' with (isstrict);

CREATE FUNCTION g_spherekey_picksplit(internal, internal)
   RETURNS internal
   AS '$libdir/pg_sphere' , 'g_spherekey_picksplit'
   LANGUAGE 'C';

CREATE FUNCTION g_spherekey_same (spherekey,spherekey,internal)
   RETURNS internal
   AS '$libdir/pg_sphere' , 'g_spherekey_same'
   LANGUAGE 'C';



-- create the operator class for spherical points

CREATE FUNCTION g_spoint_compress(internal)
   RETURNS internal
   AS '$libdir/pg_sphere' , 'g_spoint_compress'
   LANGUAGE 'C';   


CREATE FUNCTION g_spoint_consistent(internal, internal, int4, oid, internal)
   RETURNS internal
   AS '$libdir/pg_sphere' , 'g_spoint_consistent'
   LANGUAGE 'C';   


CREATE OPERATOR CLASS spoint
   DEFAULT FOR TYPE spoint USING gist AS
   OPERATOR   1 = (spoint, spoint),
   OPERATOR  11 @ (spoint, scircle),
   OPERATOR  12 @ (spoint, sline),
   OPERATOR  13 @ (spoint, spath),
   OPERATOR  14 @ (spoint, spoly),
   OPERATOR  15 @ (spoint, sellipse),
   OPERATOR  16 @ (spoint, sbox),
   FUNCTION  1 g_spoint_consistent (internal, internal, int4, oid, internal),
   FUNCTION  2 g_spherekey_union (bytea, internal),
   FUNCTION  3 g_spoint_compress (internal),
   FUNCTION  4 g_spherekey_decompress (internal),
   FUNCTION  5 g_spherekey_penalty (internal, internal, internal),
   FUNCTION  6 g_spherekey_picksplit (internal, internal),
   FUNCTION  7 g_spherekey_same (spherekey, spherekey, internal),
   STORAGE   spherekey;



-- create the operator class for spherical circle

CREATE FUNCTION g_scircle_compress(internal)
   RETURNS internal
   AS '$libdir/pg_sphere' , 'g_scircle_compress'
   LANGUAGE 'C';   


CREATE FUNCTION g_scircle_consistent(internal, internal, int4, oid, internal)
   RETURNS internal
   AS '$libdir/pg_sphere' , 'g_scircle_consistent'
   LANGUAGE 'C';   

CREATE OPERATOR CLASS scircle
   DEFAULT FOR TYPE scircle USING gist AS
   OPERATOR   1 =  (scircle, scircle),
   OPERATOR  11 @  (scircle, scircle),
   OPERATOR  12 @  (scircle, spoly),
   OPERATOR  13 @  (scircle, sellipse),
   OPERATOR  14 @  (scircle, sbox),
   OPERATOR  21 ~ (scircle, spoint),
   OPERATOR  22 ~ (scircle, scircle),
   OPERATOR  23 ~ (scircle, sline),
   OPERATOR  24 ~ (scircle, spath),
   OPERATOR  25 ~ (scircle, spoly),
   OPERATOR  26 ~ (scircle, sellipse),
   OPERATOR  27 ~ (scircle, sbox),
   OPERATOR  31 && (scircle, scircle),
   OPERATOR  32 && (scircle, sline),
   OPERATOR  33 && (scircle, spath),
   OPERATOR  34 && (scircle, spoly),
   OPERATOR  35 && (scircle, sellipse),
   OPERATOR  36 && (scircle, sbox),
   FUNCTION  1 g_scircle_consistent (internal, internal, int4, oid, internal),
   FUNCTION  2 g_spherekey_union (bytea, internal),
   FUNCTION  3 g_scircle_compress (internal),
   FUNCTION  4 g_spherekey_decompress  (internal),
   FUNCTION  5 g_spherekey_penalty   (internal, internal, internal),
   FUNCTION  6 g_spherekey_picksplit (internal, internal),
   FUNCTION  7 g_spherekey_same (spherekey, spherekey, internal),
   STORAGE   spherekey;



-- create the operator class for spherical line

CREATE FUNCTION g_sline_compress(internal)
   RETURNS internal
   AS '$libdir/pg_sphere' , 'g_sline_compress'
   LANGUAGE 'C';   

CREATE FUNCTION g_sline_consistent(internal, internal, int4, oid, internal)
   RETURNS internal
   AS '$libdir/pg_sphere' , 'g_sline_consistent'
   LANGUAGE 'C';


CREATE OPERATOR CLASS sline
   DEFAULT FOR TYPE sline USING gist AS
   OPERATOR   1 =  (sline, sline),
   OPERATOR   2 #  (sline, sline),
   OPERATOR  11 @  (sline, scircle),
   OPERATOR  12 @  (sline, spoly),
   OPERATOR  13 @  (sline, sellipse),
   OPERATOR  14 @  (sline, sbox),
   OPERATOR  21 ~ (sline, spoint),
   OPERATOR  31 && (sline, scircle),
   OPERATOR  32 && (sline, sline),
   OPERATOR  33 && (sline, spath),
   OPERATOR  34 && (sline, spoly),
   OPERATOR  35 && (sline, sellipse),
   OPERATOR  36 && (sline, sbox),
   FUNCTION  1 g_sline_consistent (internal, internal, int4, oid, internal),
   FUNCTION  2 g_spherekey_union (bytea, internal),
   FUNCTION  3 g_sline_compress (internal),
   FUNCTION  4 g_spherekey_decompress  (internal),
   FUNCTION  5 g_spherekey_penalty   (internal, internal, internal),
   FUNCTION  6 g_spherekey_picksplit (internal, internal),
   FUNCTION  7 g_spherekey_same (spherekey, spherekey, internal),
   STORAGE   spherekey;

 -- create the operator class for spherical ellipse
 
CREATE FUNCTION g_sellipse_compress(internal)
   RETURNS internal
   AS '$libdir/pg_sphere' , 'g_sellipse_compress'
   LANGUAGE 'C';   
 
 CREATE FUNCTION g_sellipse_consistent(internal, internal, int4, oid, internal)
   RETURNS internal
   AS '$libdir/pg_sphere' , 'g_sellipse_consistent'
   LANGUAGE 'C';
 
CREATE OPERATOR CLASS sellipse
   DEFAULT FOR TYPE sellipse USING gist AS
   OPERATOR   1 =  (sellipse, sellipse),
   OPERATOR  11 @  (sellipse, scircle),
   OPERATOR  12 @  (sellipse, spoly),
   OPERATOR  13 @  (sellipse, sellipse),
   OPERATOR  14 @  (sellipse, sbox),
   OPERATOR  21 ~ (sellipse, spoint),
   OPERATOR  22 ~ (sellipse, scircle),
   OPERATOR  23 ~ (sellipse, sline),
   OPERATOR  24 ~ (sellipse, spath),
   OPERATOR  25 ~ (sellipse, spoly),
   OPERATOR  26 ~ (sellipse, sellipse),
   OPERATOR  27 ~ (sellipse, sbox),
   OPERATOR  31 && (sellipse, scircle),
   OPERATOR  32 && (sellipse, sline),
   OPERATOR  33 && (sellipse, spath),
   OPERATOR  34 && (sellipse, spoly),
   OPERATOR  35 && (sellipse, sellipse),
   OPERATOR  36 && (sellipse, sbox),
   FUNCTION  1 g_sellipse_consistent (internal, internal, int4, oid, internal),
   FUNCTION  2 g_spherekey_union (bytea, internal),
   FUNCTION  3 g_sellipse_compress (internal),
   FUNCTION  4 g_spherekey_decompress  (internal),
   FUNCTION  5 g_spherekey_penalty   (internal, internal, internal),
   FUNCTION  6 g_spherekey_picksplit (internal, internal),
   FUNCTION  7 g_spherekey_same (spherekey, spherekey, internal),
   STORAGE   spherekey;

 -- create the operator class for spherical polygon
 
CREATE FUNCTION g_spoly_compress(internal)
   RETURNS internal
   AS '$libdir/pg_sphere' , 'g_spoly_compress'
   LANGUAGE 'C';   
 
CREATE FUNCTION g_spoly_consistent(internal, internal, int4, oid, internal)
   RETURNS internal
   AS '$libdir/pg_sphere' , 'g_spoly_consistent'
   LANGUAGE 'C';
 
 
CREATE OPERATOR CLASS spoly
   DEFAULT FOR TYPE spoly USING gist AS
   OPERATOR   1 =  (spoly, spoly),
   OPERATOR  11 @  (spoly, scircle),
   OPERATOR  12 @  (spoly, spoly),
   OPERATOR  13 @  (spoly, sellipse),
   OPERATOR  14 @  (spoly, sbox),
   OPERATOR  21 ~ (spoly, spoint),
   OPERATOR  22 ~ (spoly, scircle),
   OPERATOR  23 ~ (spoly, sline),
   OPERATOR  24 ~ (spoly, spath),
   OPERATOR  25 ~ (spoly, spoly),
   OPERATOR  26 ~ (spoly, sellipse),
   OPERATOR  27 ~ (spoly, sbox),
   OPERATOR  31 && (spoly, scircle),
   OPERATOR  32 && (spoly, sline),
   OPERATOR  33 && (spoly, spath),
   OPERATOR  34 && (spoly, spoly),
   OPERATOR  35 && (spoly, sellipse),
   OPERATOR  36 && (spoly, sbox),
   FUNCTION  1 g_spoly_consistent (internal, internal, int4, oid, internal),
   FUNCTION  2 g_spherekey_union (bytea, internal),
   FUNCTION  3 g_spoly_compress (internal),
   FUNCTION  4 g_spherekey_decompress  (internal),
   FUNCTION  5 g_spherekey_penalty   (internal, internal, internal),
   FUNCTION  6 g_spherekey_picksplit (internal, internal),
   FUNCTION  7 g_spherekey_same (spherekey, spherekey, internal),
   STORAGE   spherekey;


 -- create the operator class for spherical path
 
CREATE FUNCTION g_spath_compress(internal)
   RETURNS internal
   AS '$libdir/pg_sphere' , 'g_spath_compress'
   LANGUAGE 'C';   
 
CREATE FUNCTION g_spath_consistent(internal, internal, int4, oid, internal)
   RETURNS internal
   AS '$libdir/pg_sphere' , 'g_spath_consistent'
   LANGUAGE 'C';
 
 
CREATE OPERATOR CLASS spath
   DEFAULT FOR TYPE spath USING gist AS
   OPERATOR   1 =  (spath, spath),
   OPERATOR  11 @  (spath, scircle),
   OPERATOR  12 @  (spath, spoly),
   OPERATOR  13 @  (spath, sellipse),
   OPERATOR  14 @  (spath, sbox),
   OPERATOR  21 ~ (spath, spoint),
   OPERATOR  31 && (spath, scircle),
   OPERATOR  32 && (spath, sline),
   OPERATOR  33 && (spath, spath),
   OPERATOR  34 && (spath, spoly),
   OPERATOR  35 && (spath, sellipse),
   OPERATOR  36 && (spath, sbox),
   FUNCTION  1 g_spath_consistent (internal, internal, int4, oid, internal),
   FUNCTION  2 g_spherekey_union (bytea, internal),
   FUNCTION  3 g_spath_compress (internal),
   FUNCTION  4 g_spherekey_decompress  (internal),
   FUNCTION  5 g_spherekey_penalty   (internal, internal, internal),
   FUNCTION  6 g_spherekey_picksplit (internal, internal),
   FUNCTION  7 g_spherekey_same (spherekey, spherekey, internal),
   STORAGE   spherekey;


 -- create the operator class for spherical box
 
CREATE FUNCTION g_sbox_compress(internal)
   RETURNS internal
   AS '$libdir/pg_sphere' , 'g_sbox_compress'
   LANGUAGE 'C';
 
CREATE FUNCTION g_sbox_consistent(internal, internal, int4, oid, internal)
   RETURNS internal
   AS '$libdir/pg_sphere' , 'g_sbox_consistent'
   LANGUAGE 'C';
 
CREATE OPERATOR CLASS sbox
   DEFAULT FOR TYPE sbox USING gist AS
   OPERATOR   1 =  (sbox, sbox),
   OPERATOR  11 @  (sbox, scircle),
   OPERATOR  12 @  (sbox, spoly),
   OPERATOR  13 @  (sbox, sellipse),
   OPERATOR  14 @  (sbox, sbox),
   OPERATOR  21 ~ (sbox, spoint),
   OPERATOR  22 ~ (sbox, scircle),
   OPERATOR  23 ~ (sbox, sline),
   OPERATOR  24 ~ (sbox, spath),
   OPERATOR  25 ~ (sbox, spoly),
   OPERATOR  26 ~ (sbox, sellipse),
   OPERATOR  27 ~ (sbox, sbox),
   OPERATOR  31 && (sbox, scircle),
   OPERATOR  32 && (sbox, sline),
   OPERATOR  33 && (sbox, spath),
   OPERATOR  34 && (sbox, spoly),
   OPERATOR  35 && (sbox, sellipse),
   OPERATOR  36 && (sbox, sbox),
   FUNCTION  1 g_sbox_consistent (internal, internal, int4, oid, internal),
   FUNCTION  2 g_spherekey_union (bytea, internal),
   FUNCTION  3 g_sbox_compress (internal),
   FUNCTION  4 g_spherekey_decompress  (internal),
   FUNCTION  5 g_spherekey_penalty   (internal, internal, internal),
   FUNCTION  6 g_spherekey_picksplit (internal, internal),
   FUNCTION  7 g_spherekey_same (spherekey, spherekey, internal),
   STORAGE   spherekey;
COMMIT;
