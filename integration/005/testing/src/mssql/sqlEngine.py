'''
Created on Jun 4, 2014

@author: stelios
'''

import os
import urllib2
import urllib
import StringIO
import time
import xml.dom.minidom
import pyodbc
try:
    import simplejson as json
except ImportError:
    import json
import numpy
from helper_functions import html_functions
import re
import datetime
from time import gmtime,  strftime
import logging
import datetime

         
class DBHelper:
    '''
    DBHelper module
    Class to provide necessary database functionality

    University of Edinburgh, WAFU
    @author: Stelios Voutsinas
    
    '''
    
    def __init__(self, db_server, username, password, port="1433", driver="TDS"):
        '''
        Initialise DBHelper class instance
        '''
        self.db_server = db_server
        self.username = username
        self.password = password
        self.port = port
        self.driver = driver
        
    def execute_qry_single_row (self, query, db_name):
        '''
        Execute a query on a database & table, that will return a single row
        '''
        return_val = []
        params = 'DRIVER={' + self.driver + '};SERVER=' + self.db_server + ';Database=' + db_name +';UID=' + self.username + ';PWD=' + self.password +';TDS_Version=8.0;Port='  + self.port + ';'
        cnxn = pyodbc.connect(params)  
        cursor = cnxn.cursor()
        cursor.execute(query)
        row = cursor.fetchone()
        
        if row:
            return_val = row

        cnxn.close()
        
        return return_val
    
    
    def execute_query_multiple_rows(self, query, db_name, limit=None):
        '''
        Execute a query on a database & table that may return any number of rows
        '''
        return_val = []
        params = 'DRIVER={' + self.driver + '};SERVER=' + self.db_server + ';Database=' + db_name +';UID=' + self.username + ';PWD=' + self.password +';TDS_Version=8.0;Port='  + self.port + ';'
        if limit!=None:
            query = "SELECT TOP " + str(limit) + " * FROM (" + query + ") AS q"
            
        cnxn = pyodbc.connect(params)  
        cursor = cnxn.cursor()
        cursor.execute(query)
        rows = cursor.fetchall()
        
        for row in rows:
            return_val.append(row)
            
        cnxn.close()
        
        return return_val
        
        
        
    def execute_query_get_cols_rows(self, query, db_name, limit=None):
        '''
        Execute a query on a database & table that may return any number of rows
        '''
        return_val = []
       
        params = 'DRIVER={' + self.driver + '};SERVER=' + self.db_server + ';Database=' + db_name +';UID=' + self.username + ';PWD=' + self.password +';TDS_Version=8.0;Port='  + self.port + ';'
        if limit!=None:
            query = "SELECT TOP " + str(limit) + " * FROM (" + query + ") AS q"
            
        cnxn = pyodbc.connect(params)  
        cursor = cnxn.cursor()
        cursor.execute(query)
   
        columns = [column[0] for column in cursor.description]
        return_val.append(columns)
        
        rowlist=[]
        
        for row in cursor.fetchall():
            rowlist.append(dict(zip(columns, row)))
        return_val.append(rowlist)  
        cnxn.close()

        return return_val


    def execute_insert (self, insert_query, db_name, query_parameters):           
        '''
        Execute an insert on a database & table 
        '''
        return_val = []
        params = 'DRIVER={' + self.driver + '};SERVER=' + self.db_server + ';Database=' + db_name +';UID=' + self.username + ';PWD=' + self.password +';TDS_Version=8.0;Port='  + self.port + ';'
        cnxn = pyodbc.connect(params)  
        cursor = cnxn.cursor()
        cursor.execute(insert_query, query_parameters)
        cnxn.commit()
        cnxn.close()
        
    def execute_update(self, update_query, db_name):           
        '''
        Execute an insert on a database & table 
        '''
        return_val = []
        params = 'DRIVER={' + self.driver + '};SERVER=' + self.db_server + ';Database=' + db_name +';UID=' + self.username + ';PWD=' + self.password +';TDS_Version=8.0;Port='  + self.port + ';'
        cnxn = pyodbc.connect(params)  
        cursor = cnxn.cursor()
        cursor.execute(update_query)
        cnxn.commit()
        cnxn.close()        
            
            
class SQLEngine(object):
    '''
    SQLEngine
    Class that handles database interactions
    '''


    def __init__(self, dbserver, dbuser, dbpasswd, dbport='1433', driver='TDS'):
        '''
        Constructor   
           
        :param dbserver:
        :param dbuser:
        :param dbpasswd:
        :param dbport:
        :param driver:
        '''
     
        self.dbserver = dbserver
        self.dbuser = dbuser
        self.dbpasswd = dbpasswd
        self.dbport = dbport
        self.driver = driver
        
    def _getRows(self, query_result):
        '''
        Get rows from a query result array
        
        :param query_result:
        '''
        row_length = -1
        if len(query_result)>=2:
            row_length = len(query_result[1])    
        return row_length
   
    def execute_sql_query(self, query, database, limit=None):
        '''
        Execute an SQL query
        
        @param query: The SQL Query
        @param database: The Database
        '''
        return self._execute_query(query, database, limit)
    
    
    def execute_update(self, query, database):
        '''
        Execute an SQL Update
        
        @param query: The SQL Query
        @param database: The Database
        '''
        mydb = DBHelper(self.dbserver, self.dbuser, self.dbpasswd, self.dbport, self.driver)
        response = mydb.execute_update(query.encode('utf-8'), database)
        return response
        
        
    def execute_sql_query_get_rows(self, query, database, limit=None):
        '''
        Execute an SQL query
        
        @param query: The SQL Query
        @param database: The Database
        '''
        file_path=''
        now = datetime.datetime.now()
        query_results=[]
        cols = []
        rows = -1
        datatable=[]
        error_code = -1
        
        dthandler = lambda obj: (
            obj.isoformat()
            if isinstance(obj, datetime.datetime) or isinstance(obj, datetime.date) else None)
        
 
        try:
            query_results = self._execute_query_get_cols_rows(query,database, limit)
        except pyodbc.ProgrammingError, err:
            error_message = repr(err)
            logging.exception(error_message)
            return (-1, error_message)     
        except Exception as e:
            logging.exception(e) 
            return (-1, e)
        
        return (self._getRows(query_results), "")
    
    def execute_insert (self, qry, database, params):
        '''
        Execute an insert query (qry) against a db 
        
        :param qry:
        :param database:
        :param params:
        '''
        mydb = DBHelper(self.dbserver, self.dbuser, self.dbpasswd, self.dbport, self.driver)
        res = mydb.execute_insert(qry.encode('utf-8'), database, params)
        return res
    

    def _execute_query (self, qry, database, limit=None):
        '''
        Execute a query (qry) against a db and table
        
        :param qry:
        :param database:        
        '''
        mydb = DBHelper(self.dbserver, self.dbuser, self.dbpasswd, self.dbport, self.driver)
        table_data = mydb.execute_query_multiple_rows(qry.encode('utf-8'), database, limit)
        return table_data
        
        
    def _execute_query_get_cols_rows (self,qry, database, limit=None):
        '''
        Execute a query (qry) against a db and table, the information of which is stored as global variables
        
        :param qry:
        :param database:
        '''
        mydb = DBHelper(self.dbserver,self.dbuser ,self.dbpasswd, self.dbport, self.driver)
        table_data = mydb.execute_query_get_cols_rows(qry.encode('utf-8'), database, limit)
        return table_data        
          
 