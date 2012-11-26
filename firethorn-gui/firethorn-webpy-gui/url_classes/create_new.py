'''
Created on Sep 18, 2012

@author: stelios
'''
from app import render, session
from helper_functions import session_helpers
import config
import json
import traceback
import urllib
import urllib2
import web
from datetime import datetime
from helper_functions.string_functions import string_functions
string_functions = string_functions()


class create_new:
    """
    Create new object class, handles requests for creating new objects.
    
    Accepts GET and POST requests. 
  
    """
    
    def __generate_resource_url(self, json_data, obj_name, obj_type):
       
        return_html = ""
        
        if obj_type == config.types['Service']:
            data = json.loads(json_data)
            if data == [] or data== None:
                return json.dumps({
                         'Code' : -1,
                         'Content' : "<div id='sub_item'>There was an error creating your service</div>"
                     })
            else :
                converted_dict = dict([(str(k), v) for k, v in data.items()])
                return_html = config.local_hostname[obj_type] + '?'+ config.get_param + '='  +  string_functions.encode(converted_dict["ident"])
                
        elif obj_type == config.types['JDBC connection']:
            data = json.loads(json_data)
            if data == [] or data== None:
                return json.dumps({
                         'Code' : -1,
                         'Content' : "<div id='sub_item'>There was an error creating your JDBC connection</div>"
                     })
            else :
            

                converted_dict = dict([(str(k), v) for k, v in data.items()])
                return_html = config.local_hostname[obj_type] + '?'+ config.get_param + '='  +  string_functions.encode(converted_dict["ident"])

        else:
            return json.dumps({
                         'Code' : -1,
                         'Content' : config.errors['INVALID_PARAM']
                     })

        return  json.dumps({
                         'Code' : 1,
                         'Content' : return_html
                     })
    
    
    def __is_length_ok(self, strng):
        """
        Check if length of string is less than 1
        """
        if len(strng)<1:
            return False
        else :
            return True
    
    
    def __input_validator(self, obj_name, obj_type, obj_url, username, password):
        """
        Validate input for create page parameters
        """
        if obj_name!='' and obj_type!='' and username!='' and password!='' and obj_url!='':
            return self.__is_length_ok(obj_name) and self.__is_length_ok(obj_url)  and self.__is_length_ok(username)  and self.__is_length_ok(password) 
        else :
            return False   
        
        
    def GET(self):
        """ 
        GET function 
        
        """
        
        data = web.input(obj_type='')
        try:
            obj_type = string_functions.decode(data.obj_type)
            return render.create_new( str(render.header(session_helpers(session).get_log_notification())), str(render.side_menu(session_helpers(session).get_menu_items_by_permissions())), str(render.create_input_area(obj_type)), str(render.footer()))
        except Exception as e:
            return config.errors["INVALID_NETWORK_REQUEST"]
    
    
    def POST(self):
        """ 
        POST function 
        
        """
        
        data = web.input(obj_type='', obj_name='', username='', password='')
        return_string = ''
        f=''
        f2 =''
        
        obj_type = config.types[string_functions.decode(data.obj_type)]
        obj_name = string_functions.decode(data.obj_name)
        obj_url = string_functions.decode(data.obj_url)
        username = data.username
        password = data.password
      
        
        try:
           
            if  self.__input_validator(obj_name, obj_type,obj_url, username, password):
                encoded_args = urllib.urlencode({config.create_params[obj_type] : obj_name})
                request = urllib2.Request(config.create_urls[obj_type], encoded_args, headers={"Accept" : "application/json"})
                f = urllib2.urlopen(request)
                result = f.read()
                return_string = self.__generate_resource_url(result, obj_name, obj_type)
            else:
                return_string = json.dumps({
                                    'Code' : -1,
                                    'Content' : config.errors['INVALID_PARAM']
                                })
            
        except Exception:
            traceback.print_exc()
            if f!="":
                f.close()
          
            
            return_string = json.dumps({
                                'Code' : -1,
                                'Content' : config.errors['INVALID_NETWORK_REQUEST']
                                })
        if f!="":
            f.close()
      
        return return_string
       
