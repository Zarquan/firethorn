'''
Created on Sep 18, 2012

@author: stelios
'''
import web
import json
from app import render
import config
import urllib2
import urllib
import traceback
from app import session
from helper_functions import login_helpers
from datetime import datetime


class jdbc_resources:
    """
<<<<<<< local
    JDBC resources class, handles requests for specific jdbc resources.
=======
    Services class, handles requests for specific services.
>>>>>>> other
    
    Accepts GET and POST requests. 
  
    """
<<<<<<< local
    
=======
    def __init__(self):
        self.type = config.types["resource"]
>>>>>>> other

    
    def __validate_type(self, type_param):
        """
        Check if the type parameter is the same as the type of this class
        """
<<<<<<< local
        
        if type_param in config.type_update_params:
=======
        if type_param == self.type:
>>>>>>> other
            return True
        else:
            return False



    def __generate_html_content(self, data):
        """
        Generate HTML response based on json data
        """
        
        return_html = ''
                            
        if data == [] or data== None:
            return_html = "<div id='sub_item'>There was an error creating your JDBC connection</div>"
        else :
<<<<<<< local
            return_html = str(render.select_service_response('<a href=' + config.local_hostname['jdbc_resources'] + '?'+ config.get_param + '='  +  urllib2.quote(data["ident"].encode("utf8")) + '>' + data["name"] + '</a>',datetime.strptime(data["created"], "%Y-%m-%dT%H:%M:%S.%f").strftime("%d %B %Y at %H:%M:%S"), datetime.strptime(data["modified"], "%Y-%m-%dT%H:%M:%S.%f").strftime("%d %B %Y at %H:%M:%S")))
=======
            return_html = str(render.select_service_response('<a href=' + config.local_hostname['jdbc_resources'] + '?'+ config.service_get_param + '='  +  urllib2.quote(data["ident"].encode("utf8")) + '>' + data["name"] + '</a>',datetime.strptime(data["created"], "%Y-%m-%dT%H:%M:%S.%f").strftime("%d %B %Y at %H:%M:%S"), datetime.strptime(data["modified"], "%Y-%m-%dT%H:%M:%S.%f").strftime("%d %B %Y at %H:%M:%S")))
>>>>>>> other
            return_html += "<a class='button' style='float:right' id='add_adql_view'>Add ADQL View</a>"
        return return_html
    
    
    def __jdbc_connection_handler(self, data, request_type):
        """
        Handle requests for a service
        """
        return_value = ''
<<<<<<< local
        _id = data.id
=======
        id = data.id
>>>>>>> other
        f= ""
<<<<<<< local
        print data
=======
        
>>>>>>> other
        try:
<<<<<<< local
            if _id!="":
=======
            if id!="":
>>>>>>> other
            
<<<<<<< local
                request = urllib2.Request( urllib2.unquote(urllib2.quote(_id.encode("utf8"))).decode("utf8"), headers={"Accept" : "application/json"})
=======
                request = urllib2.Request( urllib2.unquote(urllib2.quote(id.encode("utf8"))).decode("utf8"), headers={"Accept" : "application/json"})
>>>>>>> other
                f = urllib2.urlopen(request)
                json_data = json.loads(f.read())
                json_data = dict([(str(k), v) for k, v in json_data.items()])
                if self.__validate_type(json_data["type"]):
                    if request_type == "GET":
                        return_value = render.jdbc_connections( str(render.header(login_helpers(session).get_log_notification())), str(render.side_menu(login_helpers(session).get_menu_items_by_permissions())), str(render.footer()), str(self.__generate_html_content(json_data)))

                    else :
                        return_value = self.__generate_html_content(json_data) 
                        
                else: 
                    return_value = config.errors['INVALID_TYPE'] + ': ' + json_data["type"]
                                

            else :
                return_value = config.errors['INVALID_PARAM']
                              
                
        except Exception as e:
            print traceback.print_exc()
            return_value = config.errors['INVALID_REQUEST']
                            
        finally:
            if f!="":
                f.close()
        
        return return_value
    

    def GET(self):
        """ 
        GET function 
        
        """
        
        data = web.input(id='')
        return  self.__jdbc_connection_handler(data, 'GET')
    
    
    def POST(self):
        """ 
        POST function 
        
        """
        
        data = web.input(id='')    
        return  self.__jdbc_connection_handler(data, 'POST')