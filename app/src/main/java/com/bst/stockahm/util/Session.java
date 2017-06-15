package com.bst.stockahm.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;


import com.bst.stockahm.MainActivity;

public class Session {
	 
	
	 Context context;
	 Editor editor;
	 private SharedPreferences prefs;
	 private static final String PREF_NAME = "SchoolServicePref";
	 private static final String IS_LOGIN = "IsLoggedIn";
	 int PRIVATE_MODE = 0;

	 public Session(Context cntx) {
	        // TODO Auto-generated constructor stub
		 Log.d("DEBUG","In Session Constructor");
		 this.context = cntx;
	        prefs = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
	        editor = prefs.edit();
	        
	    }

	    public void setuserDetail(String usename) {
	    	Log.d("DEBUG","User Name is "+usename);
	        editor.putString("username", usename);
	        editor.commit();
	        
	    }

	    public String getuserDetail() {
	        String usename = prefs.getString("username","");
	        Log.d("DEBUG", "getUserName is "+usename);
	        return usename;
	    }
	    
	    public void setCapital(String capital){
	    	Log.d("DEBUG","capital is "+capital);
	    	  editor.putString("capital", capital);
		      editor.commit();
	    }
	    public String getCapital(){
	    	String capital = prefs.getString("capital","");
	    	Log.d("DEBUG","capital is "+capital);
	    	return capital;
	    }
	    
	    public void setId(String id){
	    	Log.d("DEBUG","contactNo is "+id);
	    	  editor.putString("id", id);
		      editor.commit();
	    }
	    public String getId(){
	    	String id = prefs.getString("id","");
	    	Log.d("DEBUG","getContactNo is "+id);
	    	return id;
	    }
	    
	    public void setMobileNo(String mobile_no){
	    	Log.d("DEBUG","mobile_no is "+mobile_no);
	    	  editor.putString("mobile_no", mobile_no);
		      editor.commit();
	    }
	    public String getMobileNo(){
	    	String mobile_no = prefs.getString("mobile_no","");
	    	Log.d("DEBUG","getStudentStandard is "+mobile_no);
	    	return mobile_no;
	    }
	    
	    public void setUserName(String user_name){
	    	Log.d("DEBUG","user_name is "+user_name);
	    	  editor.putString("user_name", user_name);
		      editor.commit();
	    }
	    public String getUserName(){
	    	String user_name = prefs.getString("user_name","");
	    	Log.d("DEBUG","user_name is "+user_name);
	    	return user_name;
	    }
	    
	    public void setReconsileFlage(String reconsile_flage){
	    	Log.d("DEBUG","reconsile_flage is "+reconsile_flage);
	    	  editor.putString("reconsile_flage", reconsile_flage);
		      editor.commit();
	    }
	    public String getReconsileFlage(){
	    	String reconsile_flage = prefs.getString("reconsile_flage","");
	    	Log.d("DEBUG","reconsile_flage is "+reconsile_flage);
	    	return reconsile_flage;
	    }
	    
	    public void setSchoolName(String schoolName){
	    	Log.d("DEBUG","schoolName is "+schoolName);
	    	  editor.putString("schoolName", schoolName);
		      editor.commit();
	    }
	    public String getSchoolName(){
	    	String schoolName = prefs.getString("schoolName","");
	    	Log.d("DEBUG","schoolName is "+schoolName);
	    	return schoolName;
	    }
	    
	    
	    
	    public String getStudentHomework() {
	    	String homework = prefs.getString("homework","");
	    	Log.d("DEBUG","homework is "+homework);
	    	return homework;
			
		}
		public void setStudentHomework(String homework) {
			Log.d("DEBUG","homework is "+homework);
	    	  editor.putString("homework", homework);
		      editor.commit();
		}
		
		public String getNoticeDesc() {
	    	String notice = prefs.getString("notice","");
	    	Log.d("DEBUG","notice is "+notice);
	    	return notice;
			
		}
		public void setNoticeDesc(String notice) {
			Log.d("DEBUG","notice is "+notice);
	    	  editor.putString("notice", notice);
		      editor.commit();
		}
		
		public String getLunchDetail() {
	    	String lunch = prefs.getString("lunch","");
	    	Log.d("DEBUG","notice is "+lunch);
	    	return lunch;
			
		}
		public void setLunchDetail(String lunch) {
			Log.d("DEBUG","lunch is "+lunch);
	    	  editor.putString("lunch", lunch);
		      editor.commit();
		}

		public void setEmail(String email) {
			Log.d("DEBUG","email is "+email);
	    	  editor.putString("email", email);
		      editor.commit();
		}
		public String getEmail() {
	    	String email = prefs.getString("email","");
	    	Log.d("DEBUG","email is "+email);
	    	return email;
			
		}
		
		public void setMainTableList(String stockList) {
			
			Log.d("DEBUG","stockList is "+stockList);
			editor.putString("stockList", stockList);
		    editor.commit();
		}
		public String getMainTableList() {
	    	String stockList = prefs.getString("stockList","");
	    	Log.d("DEBUG","stockList is "+stockList);
	    	return stockList;
			
		}
		
		public void setRowId(String rowId) {
			Log.d("DEBUG","rowId is "+rowId);
			editor.putString("rowId", rowId);
		    editor.commit();
			
		}
		public String getRowId() {
	    	String rowId = prefs.getString("rowId","");
	    	Log.d("DEBUG","stockList is "+rowId);
	    	return rowId;
		}
		
		public void setIsAdmin(String isAdmin) {
			Log.d("DEBUG","isAdmin is "+isAdmin);
			editor.putString("isAdmin", isAdmin);
		    editor.commit();
			
		}
		public String getIsAdmin() {
	    	String isAdmin = prefs.getString("isAdmin","");
	    	Log.d("DEBUG","isAdmin is "+isAdmin);
	    	return isAdmin;
		}
		public void setToken(String token) {
			Log.d("DEBUG","token is "+token);
			editor.putString("regId", token);
			editor.commit();
		}
		public String getToken() {
			String token = prefs.getString("regId","");
			Log.d("DEBUG","token is "+token);
			return token;
		}

	    /**
	     * Clear session details
	     * */
	    public void logoutUser(){
	        // Clearing all data from Shared Preferences
	    	try{
	        	editor.clear();
		 	    editor.commit();
	    		//setCustomerId("");

	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        // After logout redirect user to Loing Activity
	       
	    }
	    
	    public void sessionClear()
        {
        	editor.clear();
	 	    editor.commit();
        }
	    /**
	     * Quick check for login
	     * **/
	    
	    
	    public String getPassword() {
	    	String password = prefs.getString("password","");
	    	Log.d("DEBUG","password is "+password);
	    	return password;
			
		}
		public void setPassword(String password) {
			Log.d("DEBUG","password is "+password);
	    	  editor.putString("password", password);
		      editor.commit();
		}
	    
	    // Get Login State
	    
	    /**
	     * Check login method wil check user login status
	     * If false it will redirect user to login page
	     * Else won't do anything
	     * */
	    public void checkLogin(){
	        // Check login status
	        if(!this.isLoggedIn()){
	            // user is not logged in redirect him to Login Activity
	            Intent i = new Intent(context, MainActivity.class);
	            // Closing all the Activities
	            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	             
	            // Add new Flag to start new Activity
	            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	             
	            // Staring Login Activity
	            context.startActivity(i);
	        }
	         
	    }
	    
	    public boolean isLoggedIn(){
	        return prefs.getBoolean(IS_LOGIN, false);
	    }


}
