'''
Created on May 3, 2013

@author: stelios
'''
import os

#------------------------- General Configurations -----------------------#


### Directory and URL Information ###
firethorn_host = "localhost"
firethorn_port = "8080"
full_firethorn_host = firethorn_host if firethorn_port=='' else firethorn_host + ':' + firethorn_port
base_location = os.getcwd()

### Email ###
test_email = ""

### Queries ###
sample_query=""
sample_query_expected_rows=0
limit_query = None

#------------------- Test Configurations ----------------------------------#

### SQL Database Configuration ###

test_dbserver= ""
test_dbserver_username = ""
test_dbserver_password = ""
test_dbserver_port = ""
test_database = ""

### Reporting Database Configuration ###

reporting_dbserver= ""
reporting_dbserver_username = ""
reporting_dbserver_password = ""
reporting_dbserver_port = ""
reporting_database = "pyrothorn_testing"


### Logged Queries Configuration ###

stored_queries_dbserver= ""
stored_queries_dbserver_username = ""
stored_queries_dbserver_password = ""
stored_queries_dbserver_port = ""
stored_queries_database = ""
stored_queries_query = "select top 10 * from [table] where [dbname] like 'atlas%'"
logged_queries_txt_file = "query_logs/atlas-logged-queries-short.txt"


### Firethorn Live test Configuration ###

adql_copy_depth = "THIN"
resourcename = 'Atlas JDBC conection' 
resourceuri = 'spring:RoeATLAS'
adqlspacename = 'Atlas Workspace' 
catalogname = '*'
ogsadainame = 'atlas'
jdbccatalogname = 'ATLASDR1'
jdbcschemaname = 'dbo'
metadocfile = "/var/www/atlas/testing/metadocs/ATLASDR1_TablesSchema.xml"


### Firethorn Predefined test Configuration ###

jdbcspace = ""
adqlspace = ""
adqlschema = ""
starting_catalogue_id = ""
schema_name = ""
schema_alias = ""
