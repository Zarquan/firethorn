package uk.ac.roe.wfau.firethorn.liquibase;

import liquibase.change.ChangeMetaData;
import liquibase.change.ColumnConfig;
import liquibase.change.DatabaseChange;
import liquibase.change.core.CreateTableChange;
import lombok.extern.slf4j.Slf4j;
import uk.ac.roe.wfau.firethorn.access.ProtectionException;
import uk.ac.roe.wfau.firethorn.meta.adql.AdqlColumn;
import uk.ac.roe.wfau.firethorn.meta.jdbc.JdbcColumn;
import uk.ac.roe.wfau.firethorn.meta.jdbc.JdbcTable;

@Slf4j
@DatabaseChange(name="createTable", description = "Create table", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class CreateJdbcTableChange
extends CreateTableChange
    {
    public CreateJdbcTableChange(final JdbcTable table)
    throws ProtectionException
        {
        log.debug("CreateJdbcTableChange(JdbcTable)");
        log.debug("  Table [{}][{}][{}][{}]", table.ident(), table.schema().catalog(), table.schema().schema(), table.name());
        this.table = table ;
        this.setTableName(
            this.table.name()
            );
        this.setCatalogName(
            this.table.schema().catalog()
            );
        this.setSchemaName(
            this.table.schema().schema()
            );

        for (final JdbcColumn column : table.columns().select())
            {
            log.debug("  Column [{}][{}][{}][{}]", column.ident(), column.name(), column.meta().jdbc().jdbctype(), column.meta().jdbc().arraysize());
            final String colname = column.meta().jdbc().name();
            final String coltype = sqltype(column.meta().jdbc());

            log.debug("  Name [{}]", colname);
            log.debug("  Type [{}]", coltype);

            this.addColumn(
                new ColumnConfig().setName(
                    colname
                    ).setType(
                        coltype
                        )
                );
            }
        }

    private final JdbcTable table ;
    public  JdbcTable table()
        {
        return this.table;
        }

    /**
     * SQLServer specific code imported from JdbcColumnEntity.CreateSql.
     * TODO This is just a temp fix until we get rid of Liquibase.
     * @throws ProtectionException 
     *   
     */
    private String sqltype(final JdbcColumn.Metadata.Jdbc meta)
    throws ProtectionException
    	{
    	final StringBuilder builder = new StringBuilder();
    	switch(meta.jdbctype())
	        {
	        case DATE :
	        case TIME :
	        case TIMESTAMP :
	            builder.append(
	                "DATETIME"
	                );
	            break ;
	        case CHAR:
	        case NCHAR:	 
	        case VARCHAR:	 
	        case NVARCHAR: 
	        	if (meta.arraysize()==null){
        	  		builder.append(
        	  			"VARCHAR"
	        		);
    	    		builder.append("(MAX)");
    	    	} else {
	    		    builder.append(
	 	                meta.jdbctype().name()
	 	            );
	    	        builder.append("(");
	    	        builder.append(
	    	            meta.arraysize()
	    	            );
	    	        builder.append(")");
	    	        
	    	        }
	        	break;
	        default :
	        	builder.append(
	        	    meta.jdbctype().name()
	        	);
	            break ;
	        }

	// Handle Cases that have not be visited by the switch statement
    	// TODO This should check for char() rather than array()
    	if (meta.jdbctype().isarray()  
    			&& !meta.jdbctype().equals(JdbcColumn.JdbcType.CHAR)
    			&& !meta.jdbctype().equals(JdbcColumn.JdbcType.VARCHAR)
    			&& !meta.jdbctype().equals(JdbcColumn.JdbcType.NCHAR)
    			&& !meta.jdbctype().equals(JdbcColumn.JdbcType.NVARCHAR))
    	    {
    	    if (meta.arraysize() == AdqlColumn.VAR_ARRAY_SIZE)
    	        {
    	        builder.append("(*)");
    	        }
    	    else {
    	        builder.append("(");
    	        builder.append(
    	            meta.arraysize()
    	            );
    	        builder.append(")");
    	        }
    	    }
	
    	return builder.toString();
    	}
    }
