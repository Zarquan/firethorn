/* Generated By:JavaCC: Do not edit this line. ADQLParserConstants.java */
package adql.parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface ADQLParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int LEFT_PAR = 2;
  /** RegularExpression Id. */
  int RIGHT_PAR = 3;
  /** RegularExpression Id. */
  int DOT = 4;
  /** RegularExpression Id. */
  int COMMA = 5;
  /** RegularExpression Id. */
  int EOQ = 6;
  /** RegularExpression Id. */
  int CONCAT = 7;
  /** RegularExpression Id. */
  int PLUS = 8;
  /** RegularExpression Id. */
  int MINUS = 9;
  /** RegularExpression Id. */
  int ASTERISK = 10;
  /** RegularExpression Id. */
  int DIVIDE = 11;
  /** RegularExpression Id. */
  int MODULO = 12;
  /** RegularExpression Id. */
  int BIT_AND = 13;
  /** RegularExpression Id. */
  int BIT_OR = 14;
  /** RegularExpression Id. */
  int BIT_XOR = 15;
  /** RegularExpression Id. */
  int EQUAL = 16;
  /** RegularExpression Id. */
  int NOT_EQUAL = 17;
  /** RegularExpression Id. */
  int LESS_THAN = 18;
  /** RegularExpression Id. */
  int LESS_EQUAL_THAN = 19;
  /** RegularExpression Id. */
  int GREATER_THAN = 20;
  /** RegularExpression Id. */
  int GREATER_EQUAL_THAN = 21;
  /** RegularExpression Id. */
  int SELECT = 22;
  /** RegularExpression Id. */
  int QUANTIFIER = 23;
  /** RegularExpression Id. */
  int TOP = 24;
  /** RegularExpression Id. */
  int FROM = 25;
  /** RegularExpression Id. */
  int AS = 26;
  /** RegularExpression Id. */
  int NATURAL = 27;
  /** RegularExpression Id. */
  int INNER = 28;
  /** RegularExpression Id. */
  int OUTER = 29;
  /** RegularExpression Id. */
  int RIGHT = 30;
  /** RegularExpression Id. */
  int LEFT = 31;
  /** RegularExpression Id. */
  int FULL = 32;
  /** RegularExpression Id. */
  int JOIN = 33;
  /** RegularExpression Id. */
  int ON = 34;
  /** RegularExpression Id. */
  int USING = 35;
  /** RegularExpression Id. */
  int WHERE = 36;
  /** RegularExpression Id. */
  int AND = 37;
  /** RegularExpression Id. */
  int OR = 38;
  /** RegularExpression Id. */
  int NOT = 39;
  /** RegularExpression Id. */
  int IS = 40;
  /** RegularExpression Id. */
  int NULL = 41;
  /** RegularExpression Id. */
  int BETWEEN = 42;
  /** RegularExpression Id. */
  int LIKE = 43;
  /** RegularExpression Id. */
  int IN = 44;
  /** RegularExpression Id. */
  int EXISTS = 45;
  /** RegularExpression Id. */
  int GROUP_BY = 46;
  /** RegularExpression Id. */
  int HAVING = 47;
  /** RegularExpression Id. */
  int ORDER_BY = 48;
  /** RegularExpression Id. */
  int ASC = 49;
  /** RegularExpression Id. */
  int DESC = 50;
  /** RegularExpression Id. */
  int AVG = 51;
  /** RegularExpression Id. */
  int MAX = 52;
  /** RegularExpression Id. */
  int MIN = 53;
  /** RegularExpression Id. */
  int SUM = 54;
  /** RegularExpression Id. */
  int COUNT = 55;
  /** RegularExpression Id. */
  int BOX = 56;
  /** RegularExpression Id. */
  int CENTROID = 57;
  /** RegularExpression Id. */
  int CIRCLE = 58;
  /** RegularExpression Id. */
  int POINT = 59;
  /** RegularExpression Id. */
  int POLYGON = 60;
  /** RegularExpression Id. */
  int REGION = 61;
  /** RegularExpression Id. */
  int CONTAINS = 62;
  /** RegularExpression Id. */
  int INTERSECTS = 63;
  /** RegularExpression Id. */
  int AREA = 64;
  /** RegularExpression Id. */
  int COORD1 = 65;
  /** RegularExpression Id. */
  int COORD2 = 66;
  /** RegularExpression Id. */
  int COORDSYS = 67;
  /** RegularExpression Id. */
  int DISTANCE = 68;
  /** RegularExpression Id. */
  int ABS = 69;
  /** RegularExpression Id. */
  int CEILING = 70;
  /** RegularExpression Id. */
  int DEGREES = 71;
  /** RegularExpression Id. */
  int EXP = 72;
  /** RegularExpression Id. */
  int FLOOR = 73;
  /** RegularExpression Id. */
  int LOG = 74;
  /** RegularExpression Id. */
  int LOG10 = 75;
  /** RegularExpression Id. */
  int MOD = 76;
  /** RegularExpression Id. */
  int PI = 77;
  /** RegularExpression Id. */
  int POWER = 78;
  /** RegularExpression Id. */
  int SQUARE = 79;
  /** RegularExpression Id. */
  int SIGN = 80;
  /** RegularExpression Id. */
  int RADIANS = 81;
  /** RegularExpression Id. */
  int RAND = 82;
  /** RegularExpression Id. */
  int ROUND = 83;
  /** RegularExpression Id. */
  int SQRT = 84;
  /** RegularExpression Id. */
  int TRUNCATE = 85;
  /** RegularExpression Id. */
  int ACOS = 86;
  /** RegularExpression Id. */
  int ASIN = 87;
  /** RegularExpression Id. */
  int ATAN = 88;
  /** RegularExpression Id. */
  int ATAN2 = 89;
  /** RegularExpression Id. */
  int COS = 90;
  /** RegularExpression Id. */
  int COT = 91;
  /** RegularExpression Id. */
  int SIN = 92;
  /** RegularExpression Id. */
  int TAN = 93;
  /** RegularExpression Id. */
  int CAST = 94;
  /** RegularExpression Id. */
  int CAST_TYPE = 95;
  /** RegularExpression Id. */
  int STRING_LITERAL = 101;
  /** RegularExpression Id. */
  int DELIMITED_IDENTIFIER = 104;
  /** RegularExpression Id. */
  int REGULAR_IDENTIFIER = 105;
  /** RegularExpression Id. */
  int Letter = 106;
  /** RegularExpression Id. */
  int SCIENTIFIC_NUMBER = 107;
  /** RegularExpression Id. */
  int UNSIGNED_FLOAT = 108;
  /** RegularExpression Id. */
  int UNSIGNED_INTEGER = 109;
  /** RegularExpression Id. */
  int DIGIT = 110;
  /** RegularExpression Id. */
  int HEX_PREFIX = 111;
  /** RegularExpression Id. */
  int HEX_INTEGER = 112;
  /** RegularExpression Id. */
  int HEX_DIGIT = 113;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int WithinComment = 1;
  /** Lexical state. */
  int WithinString = 2;
  /** Lexical state. */
  int WithinDelimitedId = 3;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "<token of kind 1>",
    "\"(\"",
    "\")\"",
    "\".\"",
    "\",\"",
    "\";\"",
    "\"||\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"%\"",
    "\"&\"",
    "\"|\"",
    "\"^\"",
    "\"=\"",
    "<NOT_EQUAL>",
    "\"<\"",
    "\"<=\"",
    "\">\"",
    "\">=\"",
    "\"SELECT\"",
    "<QUANTIFIER>",
    "\"TOP\"",
    "\"FROM\"",
    "\"AS\"",
    "\"NATURAL\"",
    "\"INNER\"",
    "\"OUTER\"",
    "\"RIGHT\"",
    "\"LEFT\"",
    "\"FULL\"",
    "\"JOIN\"",
    "\"ON\"",
    "\"USING\"",
    "\"WHERE\"",
    "\"AND\"",
    "\"OR\"",
    "\"NOT\"",
    "\"IS\"",
    "\"NULL\"",
    "\"BETWEEN\"",
    "\"LIKE\"",
    "\"IN\"",
    "\"EXISTS\"",
    "\"GROUP BY\"",
    "\"HAVING\"",
    "\"ORDER BY\"",
    "\"ASC\"",
    "\"DESC\"",
    "\"AVG\"",
    "\"MAX\"",
    "\"MIN\"",
    "\"SUM\"",
    "\"COUNT\"",
    "\"BOX\"",
    "\"CENTROID\"",
    "\"CIRCLE\"",
    "\"POINT\"",
    "\"POLYGON\"",
    "\"REGION\"",
    "\"CONTAINS\"",
    "\"INTERSECTS\"",
    "\"AREA\"",
    "\"COORD1\"",
    "\"COORD2\"",
    "\"COORDSYS\"",
    "\"DISTANCE\"",
    "\"ABS\"",
    "\"CEILING\"",
    "\"DEGREES\"",
    "\"EXP\"",
    "\"FLOOR\"",
    "\"LOG\"",
    "\"LOG10\"",
    "\"MOD\"",
    "\"PI\"",
    "\"POWER\"",
    "\"SQUARE\"",
    "\"SIGN\"",
    "\"RADIANS\"",
    "\"RAND\"",
    "\"ROUND\"",
    "\"SQRT\"",
    "\"TRUNCATE\"",
    "\"ACOS\"",
    "\"ASIN\"",
    "\"ATAN\"",
    "\"ATAN2\"",
    "\"COS\"",
    "\"COT\"",
    "\"SIN\"",
    "\"TAN\"",
    "\"CAST\"",
    "<CAST_TYPE>",
    "<token of kind 96>",
    "<token of kind 97>",
    "<token of kind 98>",
    "\"\\\'\"",
    "<token of kind 100>",
    "\"\\\'\"",
    "\"\\\"\"",
    "<token of kind 103>",
    "\"\\\"\"",
    "<REGULAR_IDENTIFIER>",
    "<Letter>",
    "<SCIENTIFIC_NUMBER>",
    "<UNSIGNED_FLOAT>",
    "<UNSIGNED_INTEGER>",
    "<DIGIT>",
    "\"0x\"",
    "<HEX_INTEGER>",
    "<HEX_DIGIT>",
  };

}
