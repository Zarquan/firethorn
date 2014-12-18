/*
 *  Copyright (C) 2013 Royal Observatory, University of Edinburgh, UK
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package uk.ac.roe.wfau.firethorn.adql.parser;

import java.util.ArrayList;
import java.util.HashMap;

import uk.ac.roe.wfau.firethorn.adql.parser.AdqlParserTable.AdqlDBColumn;
import uk.ac.roe.wfau.firethorn.meta.adql.AdqlColumn;
import lombok.extern.slf4j.Slf4j;
import adql.db.DBColumn;
import adql.db.DBTable;
import adql.db.DBType;
import adql.db.STCS.Region;
import adql.db.exception.UnresolvedJoin;
import adql.parser.ParseException;
import adql.query.ADQLList;
import adql.query.ADQLObject;
import adql.query.ADQLOrder;
import adql.query.ADQLQuery;
import adql.query.ClauseConstraints;
import adql.query.ClauseSelect;
import adql.query.ColumnReference;
import adql.query.IdentifierField;
import adql.query.SelectAllColumns;
import adql.query.SelectItem;
import adql.query.constraint.ConstraintsGroup;
import adql.query.from.ADQLTable;
import adql.query.operand.ADQLColumn;
import adql.query.operand.WrappedOperand;
import adql.query.operand.function.ADQLFunction;
import adql.query.operand.function.CastFunction;
//import adql.query.operand.function.CastFunction;
import adql.query.operand.function.MathFunction;
import adql.query.operand.function.UserDefinedFunction;
import adql.query.operand.function.geometry.AreaFunction;
import adql.query.operand.function.geometry.BoxFunction;
import adql.query.operand.function.geometry.CentroidFunction;
import adql.query.operand.function.geometry.CircleFunction;
import adql.query.operand.function.geometry.ContainsFunction;
import adql.query.operand.function.geometry.DistanceFunction;
import adql.query.operand.function.geometry.ExtractCoord;
import adql.query.operand.function.geometry.ExtractCoordSys;
import adql.query.operand.function.geometry.IntersectsFunction;
import adql.query.operand.function.geometry.PointFunction;
import adql.query.operand.function.geometry.PolygonFunction;
import adql.query.operand.function.geometry.RegionFunction;
import adql.translator.ADQLTranslator;
import adql.translator.PostgreSQLTranslator;
import adql.translator.TranslationException;



/*
 * This file is part of ADQLLibrary.
 * 
 * ADQLLibrary is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ADQLLibrary is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with ADQLLibrary.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2014 - Astronomisches Rechen Institut (ARI)
 */



import java.util.Iterator;

import adql.query.constraint.ADQLConstraint;
import adql.query.constraint.Between;
import adql.query.constraint.Comparison;
import adql.query.constraint.Exists;
import adql.query.constraint.In;
import adql.query.constraint.IsNull;
import adql.query.constraint.NotConstraint;
import adql.query.from.ADQLJoin;
import adql.query.from.FromContent;
import adql.query.operand.ADQLOperand;
import adql.query.operand.Concatenation;
import adql.query.operand.NegativeOperand;
import adql.query.operand.NumericConstant;
import adql.query.operand.Operation;
import adql.query.operand.StringConstant;
import adql.query.operand.function.SQLFunction;
import adql.query.operand.function.SQLFunctionType;
import adql.query.operand.function.geometry.GeometryFunction;
import adql.query.operand.function.geometry.GeometryFunction.GeometryValue;


/**
 * SQLServer SQL translator.
 * @todo Remove dependency on PostgreSQLTranslator
 *
 */
@Slf4j
public class SQLServerTranslator
    extends PostgreSQLTranslator
    implements ADQLTranslator
    {

	private final String schemaName="dbo";

    /**
     *
     *
     */
    public SQLServerTranslator()
        {
        super(
            false
            );

        }

    /**
     *
     *
     */
    public SQLServerTranslator(final boolean column)
        {
        super(
            column
            );

        }

    /**
     *
     *
     */
    public SQLServerTranslator(final boolean catalog, final boolean schema, final boolean table, final boolean column)
        {
        super(
            catalog,
            schema,
            table,
            column
            );

        }

    /**
     * Get the schema name
     *
     * @return String schemaName
    public String getSchemaName(){
    	return schemaName;
    }
     */

    /**
     * Replaces the PostgreSQLTranslator method to not put LIMIT at the end.
     *
     */
    @Override
	public String translate(final ADQLQuery query)
        throws TranslationException
        {
        log.debug("translate(ADQLQuery)");
        final StringBuilder builder = new StringBuilder();
        builder.append(
            translate(
                query.getSelect()
                )
            );

        builder.append("\nFROM ").append(
            translate(
                query.getFrom()
                )
            );

        if (!query.getWhere().isEmpty())
            {
            builder.append('\n').append(
                translate(
                    query.getWhere()
                    )
                );
            }

        if (!query.getGroupBy().isEmpty())
            {
            builder.append('\n').append(
                translate(
                    query.getGroupBy()
                    )
                );
            }

        if (!query.getHaving().isEmpty())
            {
            builder.append('\n').append(
                translate(
                    query.getHaving()
                    )
                );
            }

        if (!query.getOrderBy().isEmpty())
            {
            builder.append('\n').append(
                translate(
                    query.getOrderBy()
                    )
                );
            }
        return builder.toString();
        }


    /**
     * Replaces the PostgreSQLTranslator method to put TOP at the beginning.
     *
     */
    @Override
	public String translate(final ClauseSelect clause)
    throws TranslationException
        {
        log.debug("translate(ClauseSelect)");

        final StringBuilder builder = new StringBuilder();
        for(int i=0; (i < clause.size()); i++)
            {
            if (i == 0)
                {
                builder.append(
                    clause.getName()
                    );
                if (clause.hasLimit())
                    {
                    builder.append(" TOP ").append(
                        clause.getLimit()
                        );
                    }
                if (clause.distinctColumns())
                    {
                    builder.append(
                        " DISTINCT "
                        );
                    }
                }
            else {
                builder.append(" ").append(
                    clause.getSeparator(i)
                    );
                }
            builder.append(" ").append(
                translate(clause.get(i)
                    )
                );
            }
        return builder.toString();
        }

    /**
     * Copy of the PostgreSQLTranslator method ...
     * @todo Need to catch date fields and format them as strings.
     *
   
    @Override
	public String translate(final SelectItem item)
    throws TranslationException
        {
        log.debug("translate(SelectItem)");
        log.debug("  item [{}][{}]", item.getName(), item.getClass().getName());
        if (item instanceof SelectAllColumns)
            {
            return translate((SelectAllColumns)item);
            }

        final StringBuffer translation = new StringBuffer(
            translate(
                item.getOperand()
                )
            );
        if (item.hasAlias())
            {
            translation.append(" AS ");
            appendIdentifier(translation, item.getAlias(), item.isCaseSensitive());
            }
        else {
            translation.append(" AS ").append(item.getName());
            }

        return translation.toString();
        }
  */
    @Override
	public String translate(final UserDefinedFunction function)
    throws TranslationException
        {
        log.debug("translate(UserDefinedFunction)");
		return getDefaultADQLFunction(
		    function
		    );
        }

	/**
	 * Gets the default SQL output for the given ADQL function.
	 *
	 * @param function The ADQL function to format into SQL.
	 * @return The corresponding SQL.
	 * @throws TranslationException	If there is an error during the translation.
	 *
	 */
    @Override
	protected String getDefaultADQLFunction(final ADQLFunction function)
    throws TranslationException
        {
        final StringBuilder builder = new StringBuilder();
        //
        // If the function is user defined.
//ZRQ-UDF
        if (function instanceof UserDefinedFunction)
            {
            //
            // TODO Check the function schema.
            if (true)
                {
                builder.append(this.schemaName);
                builder.append(".");
                }
            }

        builder.append(function.getName());
        builder.append("(");

        for(int param = 0; param < function.getNbParameters(); param++)
            {
            if (param > 0)
                {
                builder.append(", ");
                }
            builder.append(
                translate(
                    function.getParameter(
                        param
                        )
                    )
                );
            }
        builder.append(")");
		return builder.toString();
        }

    @Override
    public String translate(final ADQLFunction function)
    throws TranslationException
		{
    	if (function instanceof CastFunction)
    		{
    		return translate(
				(CastFunction) function
				);
    		}
    	else {
    		return super.translate(
				function
				);
    		}
		}

    public String translate(final CastFunction function)
    throws TranslationException
    	{
        final StringBuilder builder = new StringBuilder();

        builder.append("CAST");
        builder.append("(");
        builder.append(
    		translate(
				function.oper()
				)
    		);
        builder.append(" AS ");
        builder.append(
    		function.type().name()
    		);
        builder.append(")");

        return builder.toString();
    	}
   
    /**
     * Copy of the PostgreSQLTranslator method ...
     *
     */
    @Override
	public String translate(final ADQLColumn column)
        throws TranslationException
        {
    	// Use its DB name if known:
		if (column.getDBLink() != null){
			DBColumn dbCol = column.getDBLink();
			StringBuffer colName = new StringBuffer();
			// Use the table alias if any:
		
			if (column.getAdqlTable() != null && column.getAdqlTable().hasAlias())
				appendIdentifier(colName, column.getAdqlTable().getAlias(), column.getAdqlTable().isCaseSensitive(IdentifierField.ALIAS)).append('.');

			// Use the DBTable if any:
			else if (dbCol.getTable() != null && dbCol.getTable().getDBName() != null) {

				System.out.println(dbCol.getTable().getDBName());
				System.out.println(dbCol.getTable().getADQLName());
				System.out.println(dbCol.getTable().getDBCatalogName());
				System.out.println(column.getAdqlTable());
				System.out.println(column.getFullColumnPrefix());
				
				if (column.getAdqlTable() != null){
					colName.append(getQualifiedTableName(dbCol.getTable())).append('.');
				} else {
					colName.append(dbCol.getTable().getADQLName());
					colName.append('.');
				}
			}
			// Otherwise, use the prefix of the column given in the ADQL query:
			else if (column.getTableName() != null)
				colName = column.getFullColumnPrefix().append('.');

		
			appendIdentifier(colName, dbCol.getADQLName(), IdentifierField.COLUMN);
		
			return colName.toString();
		}
		// Otherwise, use the whole name given in the ADQL query:
		else
			return column.getFullColumnName();
        }
 
    public String translate(AdqlColumn column)
    throws TranslationException
        {
        log.debug("translate(AdqlColumn)");
        log.debug("  adql [{}][{}]", column.name(), column.getClass().getName());
        log.debug("  fullname [{}]", column.namebuilder().toString());
        log.debug("  basename [{}]", column.base().namebuilder().toString());
        log.debug("  rootname [{}]", column.root().namebuilder().toString());

        return column.root().namebuilder().toString();

        }

    /**
     * Copy of the PostgreSQLTranslator method ...
     * @todo Need to use Firethorn metadata to iterate the AdqlTable columns.
     *
     *
     
    @Override
	public String translate(final SelectAllColumns all)
    throws TranslationException
        {
        log.debug("translate(SelectAllColumns)");

// TODO Replace this with code that uses the Firethorn metadata.
// Need to make sure this ends up with the same list of fields as the JDBC table in user data.

// Check if ADQLTable is a AdqlParserTable
// cast into AdqlParserTable and call table.table() to get a AdqlTable
// process the column list from the AdqlTable.

        final HashMap<String, String> mapAlias = new HashMap<String, String>();

        // Fetch the full list of columns to display:
        Iterable<DBColumn> dbCols = null;
        if (all.getAdqlTable() != null)
            {
            final ADQLTable table = all.getAdqlTable();
            log.debug("Table [{}][{}]", table.getName(), table.getClass().getName());
            if (table.getDBLink() != null)
                {
                dbCols = table.getDBLink();
                if (table.hasAlias())
                    {
                    final String key = appendFullDBName(new StringBuffer(), table.getDBLink()).toString();
                    mapAlias.put(key, table.isCaseSensitive(IdentifierField.ALIAS) ? ("\""+table.getAlias()+"\"") : table.getAlias());
                    }
                }
            else {
// getDBLink is null - need to fix this
// Need to find where the ADQLTable is created
// and add a reference to the BaseTable that this refers to.
                log.warn("ADQLTable with no link to the BaseTable [{}]", table.getName());
                }
            }
        else if (all.getQuery() != null)
            {
            try {
				dbCols = all.getQuery().getFrom().getDBColumns();
			} catch (UnresolvedJoin e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            final ArrayList<ADQLTable> tables = all.getQuery().getFrom().getTables();
            for(final ADQLTable table : tables)
                {
                if (table.hasAlias())
                    {
                    final String key = appendFullDBName(new StringBuffer(), table.getDBLink()).toString();
                    mapAlias.put(key, table.isCaseSensitive(IdentifierField.ALIAS) ? ("\""+table.getAlias()+"\"") : table.getAlias());
                    }
                }
            }

        // Write the DB name of all these columns:
        if (dbCols != null)
            {
            final StringBuffer cols = new StringBuffer();
            for(final DBColumn col : dbCols)
                {
                if (cols.length() > 0)
                    {
                        cols.append(',');
                        }
                if (col.getTable() != null)
                    {
                    final String fullDbName = appendFullDBName(new StringBuffer(), col.getTable()).toString();
                    if (mapAlias.containsKey(fullDbName))
                        {
                            appendIdentifier(cols, mapAlias.get(fullDbName), false).append('.');
                            }
                    else
                        {
                            cols.append(fullDbName).append('.');
                            }
                    }
                appendIdentifier(cols, col.getDBName(), IdentifierField.COLUMN);
                cols.append(" AS ").append(col.getADQLName());
                }
            return cols.toString();
            }
        else{
            return all.toADQL();
            }
        }

  */

	/**
     * Replacement for the PostgreSQLTranslator method.
     *
     *
     */
    @Override
    public String translate(final MathFunction funct) throws TranslationException
        {
        switch(funct.getType())
            {
            case LOG:
                return "log(" + translate(funct.getParameter(0)) + ")";

            case LOG10:
                return "log10(" + translate(funct.getParameter(0)) + ")";

            case RAND:
            	if (funct.getNbParameters()>0){
                    return "rand(" + translate(funct.getParameter(0)) + ")";
            	} else {
                    return "rand()";
            	}

            // Extra param to choose the rounding method.
            // http://technet.microsoft.com/en-us/library/ms175003.aspx
            case ROUND:
            	if (funct.getNbParameters()==1){
                    return "round(" + translate(funct.getParameter(0)) + ", 0)";

            	} else if (funct.getNbParameters()>1){
                    return "round(" + translate(funct.getParameter(0)) + ", " + translate(funct.getParameter(1)) + ", 0)";

            	}

            // Extra param to choose the rounding method.
            // http://technet.microsoft.com/en-us/library/ms175003.aspx
            case TRUNCATE:
            	if (funct.getNbParameters()==1){
                    return "round(" + translate(funct.getParameter(0)) + ", 1)";
            	} else if (funct.getNbParameters()>1){
                    return "round(" + translate(funct.getParameter(0)) + ", " + translate(funct.getParameter(1)) + ", 1)";
            	}

            default:
                return getDefaultADQLFunction(
                    funct
                    );
            }
        }

    /**
     * Override the PostgreSQLTranslator method to add '()' brackets for a ConstraintsGroup.
     * @see RedmineBug450TestCase
     * @see  
     *   
     */
	@Override
    protected String getDefaultADQLList(ADQLList<? extends ADQLObject> list)
	throws TranslationException
	    {
        StringBuilder builder = new StringBuilder();

        log.debug("translate(ADQLList<>)");
        log.debug("  list [{}][{}]", list.getName(), list.getClass().getName());

        if (list instanceof ConstraintsGroup)
            {
          
            builder.append(
                super.getDefaultADQLList(
                    list
                    )
                );
            
            }
        else {
            builder.append(
                super.getDefaultADQLList(
                    list
                    )
                );
            }

        String result = builder.toString();
        log.debug("  result [{}]", result);
        return result;
	    }
	

	
	/**
	 * Appends the full name of the given table to the given StringBuffer.
	 * 
	 * @param str		The string buffer.
	 * @param dbTable	The table whose the full name must be appended.
	 * 
	 * @return			The string buffer + full table name.
	
	public final StringBuffer appendFullDBName(final StringBuffer str, final DBTable dbTable){
		if (dbTable != null){
			if (dbTable.getDBCatalogName() != null)
				appendIdentifier(str, dbTable.getDBCatalogName(), IdentifierField.CATALOG).append('.');

			if (dbTable.getDBSchemaName() != null)
				appendIdentifier(str, dbTable.getDBSchemaName(), IdentifierField.SCHEMA).append('.');

			appendIdentifier(str, dbTable.getDBName(), IdentifierField.TABLE);
		}
		return str;
	} */

	/**
	 * <p>Get the qualified DB name of the schema containing the given table.</p>
	 * 
	 * <p><i>Note:
	 * 	This function will, by default, add double quotes if the schema name must be case sensitive in the SQL query.
	 * 	This information is provided by {@link #isCaseSensitive(IdentifierField)}.
	 * </i></p>
	 * 
	 * @param table	A table of the schema whose the qualified DB name is asked.
	 * 
	 * @return	The qualified (with DB catalog name prefix if any, and with double quotes if needed) DB schema name,
	 *        	or an empty string if there is no schema or no DB name.
	 */
	public String getQualifiedSchemaName(final DBTable table){
		if (table == null || table.getDBSchemaName() == null)
			return "";

		StringBuffer buf = new StringBuffer();

		if (table.getDBCatalogName() != null)
			appendIdentifier(buf, table.getDBCatalogName(), IdentifierField.CATALOG).append('.');

		appendIdentifier(buf, table.getDBSchemaName(), IdentifierField.SCHEMA);

		return buf.toString();
	}


	/**
	 * <p>Get the qualified DB name of the given table.</p>
	 * 
	 * <p><i>Note:
	 * 	This function will, by default, add double quotes if the table name must be case sensitive in the SQL query.
	 * 	This information is provided by {@link #isCaseSensitive(IdentifierField)}.
	 * </i></p>
	 * 
	 * @param table	The table whose the qualified DB name is asked.
	 * 
	 * @return	The qualified (with DB catalog and schema prefix if any, and with double quotes if needed) DB table name,
	 *        	or an empty string if the given table is NULL or if there is no DB name.
	 */
	public String getQualifiedTableName(final DBTable table){
		if (table == null)
			return "";

		StringBuffer buf = new StringBuffer(getQualifiedSchemaName(table));
		if (buf.length() > 0)
			buf.append('.');

		appendIdentifier(buf, table.getDBName(), IdentifierField.TABLE);

		return buf.toString();
	}

	/**
	 * <p>Get the DB name of the given column</p>
	 *  
	 * <p><i>Note:
	 * 	This function will, by default, add double quotes if the column name must be case sensitive in the SQL query.
	 * 	This information is provided by {@link #isCaseSensitive(IdentifierField)}.
	 * </i></p>
	 * 
	 * <p><b>Caution:
	 * 	The given column may be NULL and in this case an empty string will be returned.
	 * 	But if the given column is not NULL, its DB name MUST NOT BE NULL!
	 * </b></p>
	 * 
	 * @param column	The column whose the DB name is asked.
	 * 
	 * @return	The DB column name (with double quotes if needed),
	 *        	or an empty string if the given column is NULL.
	 */
	public String getColumnName(final DBColumn column){
		return (column == null) ? "" : appendIdentifier(new StringBuffer(), column.getDBName(), IdentifierField.COLUMN).toString();
	}

	/**
	 * Appends the given identifier in the given StringBuffer.
	 * 
	 * @param str		The string buffer.
	 * @param id		The identifier to append.
	 * @param field		The type of identifier (column, table, schema, catalog or alias ?).
	 * 
	 * @return			The string buffer + identifier.
	
	public final StringBuffer appendIdentifier(final StringBuffer str, final String id, final IdentifierField field){
		return appendIdentifier(str, id, isCaseSensitive(field));
	} */

	/**
	 * Appends the given identifier to the given StringBuffer.
	 * 
	 * @param str				The string buffer.
	 * @param id				The identifier to append.
	 * @param caseSensitive		<i>true</i> to format the identifier so that preserving the case sensitivity, <i>false</i> otherwise.
	 * 
	 * @return					The string buffer + identifier.
	
	public static final StringBuffer appendIdentifier(final StringBuffer str, final String id, final boolean caseSensitive){
		if (caseSensitive)
			return str.append('"').append(id).append('"');
		else
			return str.append(id);
	} */

	@Override
	@SuppressWarnings({"unchecked","rawtypes"})
	public String translate(ADQLObject obj) throws TranslationException{
		if (obj instanceof ADQLQuery)
			return translate((ADQLQuery)obj);
		else if (obj instanceof ADQLList)
			return translate((ADQLList)obj);
		else if (obj instanceof SelectItem)
			return translate((SelectItem)obj);
		else if (obj instanceof ColumnReference)
			return translate((ColumnReference)obj);
		else if (obj instanceof ADQLTable)
			return translate((ADQLTable)obj);
		else if (obj instanceof ADQLJoin)
			return translate((ADQLJoin)obj);
		else if (obj instanceof ADQLOperand)
			return translate((ADQLOperand)obj);
		else if (obj instanceof ADQLConstraint)
			return translate((ADQLConstraint)obj);
		else
			return obj.toADQL();
	}


	/* *************************** */
	/* ****** LIST & CLAUSE ****** */
	/* *************************** */
	@Override
	public String translate(ADQLList<? extends ADQLObject> list) throws TranslationException{
		if (list instanceof ClauseSelect)
			return translate((ClauseSelect)list);
		else if (list instanceof ClauseConstraints)
			return translate((ClauseConstraints)list);
		else
			return getDefaultADQLList(list);
	}

	/**
	 * Gets the default SQL output for a list of ADQL objects.
	 * 
	 * @param list	List to format into SQL.
	 * 
	 * @return		The corresponding SQL.
	 * 
	 * @throws TranslationException If there is an error during the translation.
	
	protected String getDefaultADQLList(ADQLList<? extends ADQLObject> list) throws TranslationException{
		String sql = (list.getName() == null) ? "" : (list.getName() + " ");

		for(int i = 0; i < list.size(); i++)
			sql += ((i == 0) ? "" : (" " + list.getSeparator(i) + " ")) + translate(list.get(i));

		return sql;
	} */
/*
	@Override
	public String translate(ClauseSelect clause) throws TranslationException{
		String sql = null;

		for(int i = 0; i < clause.size(); i++){
			if (i == 0){
				sql = clause.getName() + (clause.distinctColumns() ? " DISTINCT" : "");
			}else
				sql += " " + clause.getSeparator(i);

			sql += " " + translate(clause.get(i));
		}

		return sql;
	}*/

	@Override
	public String translate(ClauseConstraints clause) throws TranslationException{
		if (clause instanceof ConstraintsGroup)
			return "(" + getDefaultADQLList(clause) + ")";
		else
			return getDefaultADQLList(clause);
	}

	@Override
	public String translate(SelectItem item) throws TranslationException{
		if (item instanceof SelectAllColumns)
			return translate((SelectAllColumns)item);

		StringBuffer translation = new StringBuffer(translate(item.getOperand()));
		if (item.hasAlias()){
			translation.append(" AS ");
			appendIdentifier(translation, item.getAlias(), false);
		}else{
			translation.append(" AS ");
			appendIdentifier(translation, item.getName(), false);
		}

		return translation.toString();
	}

	@Override
	public String translate(SelectAllColumns item) throws TranslationException{
		HashMap<String,String> mapAlias = new HashMap<String,String>();

		// Fetch the full list of columns to display:
		Iterable<DBColumn> dbCols = null;
		if (item.getAdqlTable() != null && item.getAdqlTable().getDBLink() != null){
			ADQLTable table = item.getAdqlTable();
			dbCols = table.getDBLink();
			if (table.hasAlias()){
				String key = getQualifiedTableName(table.getDBLink());
				mapAlias.put(key, table.isCaseSensitive(IdentifierField.ALIAS) ? (table.getAlias()) : table.getAlias());
			}
		}else if (item.getQuery() != null){
			try{
				dbCols = item.getQuery().getFrom().getDBColumns();
			}catch(UnresolvedJoin pe){
				throw new TranslationException("Due to a join problem, the ADQL to SQL translation can not be completed!", pe);
			}
			ArrayList<ADQLTable> tables = item.getQuery().getFrom().getTables();
			for(ADQLTable table : tables){
				if (table.hasAlias()){
					String key = getQualifiedTableName(table.getDBLink());
					mapAlias.put(key, table.isCaseSensitive(IdentifierField.ALIAS) ? (table.getAlias()) : table.getAlias());
				}
			}
		}

		// Write the DB name of all these columns:
		if (dbCols != null){
			StringBuffer cols = new StringBuffer();
			for(DBColumn col : dbCols){
				if (cols.length() > 0)
					cols.append(',');
				if (col.getTable() != null){
					String fullDbName = getQualifiedTableName(col.getTable());
					if (mapAlias.containsKey(fullDbName))
						appendIdentifier(cols, mapAlias.get(fullDbName), false).append('.');
					else
						cols.append(fullDbName).append('.');
				}
				appendIdentifier(cols, col.getDBName(), IdentifierField.COLUMN);
				cols.append(" AS ").append(col.getADQLName());
			}
			return (cols.length() > 0) ? cols.toString() : item.toADQL();
		}else{
			return item.toADQL();
		}
	}

	@Override
	public String translate(ColumnReference ref) throws TranslationException{
		if (ref instanceof ADQLOrder)
			return translate((ADQLOrder)ref);
		else
			return getDefaultColumnReference(ref);
	}

	/**
	 * Gets the default SQL output for a column reference.
	 * 
	 * @param ref	The column reference to format into SQL.
	 * 
	 * @return		The corresponding SQL.
	 * 
	 * @throws TranslationException If there is an error during the translation.
	 */
	protected String getDefaultColumnReference(ColumnReference ref) throws TranslationException{
		if (ref.isIndex()){
			return "" + ref.getColumnIndex();
		}else{
			if (ref.getDBLink() == null){
				return (ref.isCaseSensitive() ? ("\"" + ref.getColumnName() + "\"") : ref.getColumnName());
			}else{
				DBColumn dbCol = ref.getDBLink();
				StringBuffer colName = new StringBuffer();
				// Use the table alias if any:
				if (ref.getAdqlTable() != null && ref.getAdqlTable().hasAlias())
					appendIdentifier(colName, ref.getAdqlTable().getAlias(), ref.getAdqlTable().isCaseSensitive(IdentifierField.ALIAS)).append('.');

				// Use the DBTable if any:
				else if (dbCol.getTable() != null)
					colName.append(getQualifiedTableName(dbCol.getTable())).append('.');

				appendIdentifier(colName, dbCol.getDBName(), IdentifierField.COLUMN);

				return colName.toString();
			}
		}
	}

	@Override
	public String translate(ADQLOrder order) throws TranslationException{
		return getDefaultColumnReference(order) + (order.isDescSorting() ? " DESC" : " ASC");
	}

	/* ************************** */
	/* ****** TABLE & JOIN ****** */
	/* ************************** */
	@Override
	public String translate(FromContent content) throws TranslationException{
		if (content instanceof ADQLTable)
			return translate((ADQLTable)content);
		else if (content instanceof ADQLJoin)
			return translate((ADQLJoin)content);
		else
			return content.toADQL();
	}

	@Override
	public String translate(ADQLTable table) throws TranslationException{
		StringBuffer sql = new StringBuffer();

		// CASE: SUB-QUERY:
		if (table.isSubQuery())
			sql.append('(').append(translate(table.getSubQuery())).append(')');

		// CASE: TABLE REFERENCE:
		else{
			// Use the corresponding DB table, if known:
			if (table.getDBLink() != null)
				sql.append(getQualifiedTableName(table.getDBLink()));
			// Otherwise, use the whole table name given in the ADQL query:
			else
				sql.append(table.getFullTableName());
		}

		// Add the table alias, if any:
		if (table.hasAlias()){
			sql.append(" AS ");
			appendIdentifier(sql, table.getAlias(), table.isCaseSensitive(IdentifierField.ALIAS));
		}

		return sql.toString();
	}

	@Override
	public String translate(ADQLJoin join) throws TranslationException{
		StringBuffer sql = new StringBuffer(translate(join.getLeftTable()));

		if (join.isNatural())
			sql.append(" NATURAL");

		sql.append(' ').append(join.getJoinType()).append(' ').append(translate(join.getRightTable())).append(' ');

		if (!join.isNatural()){
			if (join.getJoinCondition() != null)
				sql.append(translate(join.getJoinCondition()));
			else if (join.hasJoinedColumns()){
				StringBuffer cols = new StringBuffer();
				Iterator<ADQLColumn> it = join.getJoinedColumns();
				while(it.hasNext()){
					ADQLColumn item = it.next();
					if (cols.length() > 0)
						cols.append(", ");
					if (item.getDBLink() == null)
						appendIdentifier(cols, item.getColumnName(), item.isCaseSensitive(IdentifierField.COLUMN));
					else
						appendIdentifier(cols, item.getDBLink().getDBName(), IdentifierField.COLUMN);
				}
				sql.append("USING (").append(cols).append(')');
			}
		}

		return sql.toString();
	}

	/* ********************* */
	/* ****** OPERAND ****** */
	/* ********************* */
	@Override
	public String translate(ADQLOperand op) throws TranslationException{
		if (op instanceof ADQLColumn)
			return translate((ADQLColumn)op);
		else if (op instanceof Concatenation)
			return translate((Concatenation)op);
		else if (op instanceof NegativeOperand)
			return translate((NegativeOperand)op);
		else if (op instanceof NumericConstant)
			return translate((NumericConstant)op);
		else if (op instanceof StringConstant)
			return translate((StringConstant)op);
		else if (op instanceof WrappedOperand)
			return translate((WrappedOperand)op);
		else if (op instanceof Operation)
			return translate((Operation)op);
		else if (op instanceof ADQLFunction)
			return translate((ADQLFunction)op);
		else
			return op.toADQL();
	}


	@Override
	public String translate(Concatenation concat) throws TranslationException{
		return translate((ADQLList<ADQLOperand>)concat);
	}

	@Override
	public String translate(NegativeOperand negOp) throws TranslationException{
		return "-" + translate(negOp.getOperand());
	}

	@Override
	public String translate(NumericConstant numConst) throws TranslationException{
		return numConst.getValue();
	}

	@Override
	public String translate(StringConstant strConst) throws TranslationException{
		return "'" + strConst.getValue() + "'";
	}

	@Override
	public String translate(WrappedOperand op) throws TranslationException{
		return "(" + translate(op.getOperand()) + ")";
	}

	@Override
	public String translate(Operation op) throws TranslationException{
		return translate(op.getLeftOperand()) + op.getOperation().toADQL() + translate(op.getRightOperand());
	}

	/* ************************ */
	/* ****** CONSTRAINT ****** */
	/* ************************ */
	@Override
	public String translate(ADQLConstraint cons) throws TranslationException{
		if (cons instanceof Comparison)
			return translate((Comparison)cons);
		else if (cons instanceof Between)
			return translate((Between)cons);
		else if (cons instanceof Exists)
			return translate((Exists)cons);
		else if (cons instanceof In)
			return translate((In)cons);
		else if (cons instanceof IsNull)
			return translate((IsNull)cons);
		else if (cons instanceof NotConstraint)
			return translate((NotConstraint)cons);
		else
			return cons.toADQL();
	}

	@Override
	public String translate(Comparison comp) throws TranslationException{
		return translate(comp.getLeftOperand()) + " " + comp.getOperator().toADQL() + " " + translate(comp.getRightOperand());
	}

	@Override
	public String translate(Between comp) throws TranslationException{
		return translate(comp.getLeftOperand()) + " " + comp.getName() + " " + translate(comp.getMinOperand()) + " AND " + translate(comp.getMaxOperand());
	}

	@Override
	public String translate(Exists exists) throws TranslationException{
		return "EXISTS(" + translate(exists.getSubQuery()) + ")";
	}

	@Override
	public String translate(In in) throws TranslationException{
		return translate(in.getOperand()) + " " + in.getName() + " (" + (in.hasSubQuery() ? translate(in.getSubQuery()) : translate(in.getValuesList())) + ")";
	}

	@Override
	public String translate(IsNull isNull) throws TranslationException{
		return translate(isNull.getColumn()) + " " + isNull.getName();
	}

	@Override
	public String translate(NotConstraint notCons) throws TranslationException{
		return "NOT " + translate(notCons.getConstraint());
	}

	/**
	 * Gets the default SQL output for the given ADQL function.
	 * 
	 * @param fct	The ADQL function to format into SQL.
	 * 
	 * @return		The corresponding SQL.
	 * 
	 * @throws TranslationException	If there is an error during the translation.
	
	protected String getDefaultADQLFunction(ADQLFunction fct) throws TranslationException{
		String sql = fct.getName() + "(";

		for(int i = 0; i < fct.getNbParameters(); i++)
			sql += ((i == 0) ? "" : ", ") + translate(fct.getParameter(i));

		return sql + ")";
	} */

	@Override
	public String translate(SQLFunction fct) throws TranslationException{
		if (fct.getType() == SQLFunctionType.COUNT_ALL)
			return "COUNT(" + (fct.isDistinct() ? "DISTINCT " : "") + "*)";
		else
			return fct.getName() + "(" + (fct.isDistinct() ? "DISTINCT " : "") + translate(fct.getParameter(0)) + ")";
	}
	/*
	@Override
	public String translate(MathFunction fct) throws TranslationException{
		return getDefaultADQLFunction(fct);
	}

	@Override
	public String translate(UserDefinedFunction fct) throws TranslationException{
		return getDefaultADQLFunction(fct);
	}*/

	/* *********************************** */
	/* ****** GEOMETRICAL FUNCTIONS ****** */
	/* *********************************** */
	@Override
	public String translate(GeometryFunction fct) throws TranslationException{
		if (fct instanceof AreaFunction)
			return translate((AreaFunction)fct);
		else if (fct instanceof BoxFunction)
			return translate((BoxFunction)fct);
		else if (fct instanceof CentroidFunction)
			return translate((CentroidFunction)fct);
		else if (fct instanceof CircleFunction)
			return translate((CircleFunction)fct);
		else if (fct instanceof ContainsFunction)
			return translate((ContainsFunction)fct);
		else if (fct instanceof DistanceFunction)
			return translate((DistanceFunction)fct);
		else if (fct instanceof ExtractCoord)
			return translate((ExtractCoord)fct);
		else if (fct instanceof ExtractCoordSys)
			return translate((ExtractCoordSys)fct);
		else if (fct instanceof IntersectsFunction)
			return translate((IntersectsFunction)fct);
		else if (fct instanceof PointFunction)
			return translate((PointFunction)fct);
		else if (fct instanceof PolygonFunction)
			return translate((PolygonFunction)fct);
		else if (fct instanceof RegionFunction)
			return translate((RegionFunction)fct);
		else
			return getDefaultADQLFunction(fct);
	}

	@Override
	public String translate(GeometryValue<? extends GeometryFunction> geomValue) throws TranslationException{
		return translate(geomValue.getValue());
	}
	

	
	@Override
	public String translate(ExtractCoord extractCoord) throws TranslationException{
		return getDefaultADQLFunction(extractCoord);
	}

	@Override
	public String translate(ExtractCoordSys extractCoordSys) throws TranslationException{
		return getDefaultADQLFunction(extractCoordSys);
	}

	@Override
	public String translate(AreaFunction areaFunction) throws TranslationException{
		return getDefaultADQLFunction(areaFunction);
	}

	@Override
	public String translate(CentroidFunction centroidFunction) throws TranslationException{
		return getDefaultADQLFunction(centroidFunction);
	}

	@Override
	public String translate(DistanceFunction fct) throws TranslationException{
		return getDefaultADQLFunction(fct);
	}

	@Override
	public String translate(ContainsFunction fct) throws TranslationException{
		return getDefaultADQLFunction(fct);
	}

	@Override
	public String translate(IntersectsFunction fct) throws TranslationException{
		return getDefaultADQLFunction(fct);
	}

	@Override
	public String translate(BoxFunction box) throws TranslationException{
		return getDefaultADQLFunction(box);
	}

	@Override
	public String translate(CircleFunction circle) throws TranslationException{
		return getDefaultADQLFunction(circle);
	}

	@Override
	public String translate(PointFunction point) throws TranslationException{
		return getDefaultADQLFunction(point);
	}

	@Override
	public String translate(PolygonFunction polygon) throws TranslationException{
		return getDefaultADQLFunction(polygon);
	}

	@Override
	public String translate(RegionFunction region) throws TranslationException{
		return getDefaultADQLFunction(region);
	}


    }
