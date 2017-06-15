package com.bst.stockahm;

import java.util.HashMap;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;

import com.bst.stockahm.util.AssetsPropertyReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends Activity {

	 ProgressDialog prgDialog;
	 private AssetsPropertyReader assPrpRdr;
	 private Properties p;
	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onClickSignupButton(View view){
		prgDialog = new ProgressDialog(this);
	    prgDialog.setMessage("Signing up... Please wait...");
	    prgDialog.setCancelable(false);
		
		assPrpRdr = new AssetsPropertyReader(Signup.this);
		p = assPrpRdr.getProperties("eServiceConstants.properties");
		String link = p.getProperty("backOffice.link") + "/" + p.getProperty("backoffice.signUp");
		saveSignupData(link,view);
		/*if(isValide){
			Intent loginIntent = new Intent(this,MainActivity.class);
			startActivity(loginIntent);
		}*/
		
	}
	
	public void saveSignupData(String link, View view){

		Log.d("DEBUG",link);
		Log.d("DEBUG","In saveLeaveNote(String leaveNote) of HomePageActivity");
		
		EditText et0 = (EditText)findViewById(R.id.signUpUserName);
		EditText et1 = (EditText)findViewById(R.id.signUpPassword);
		EditText et2 = (EditText)findViewById(R.id.etMobileNo);
		EditText et3 = (EditText)findViewById(R.id.etemail);
		EditText et4 = (EditText)findViewById(R.id.etcapital);
		
		boolean isValide = true;
		
		if(et0.getText() == null || et0.getText().equals("")){
			 Toast.makeText(getApplicationContext(), "Please enter username", Toast.LENGTH_LONG).show();
			 isValide = false;
		}
		if(isValide && (et1.getText() == null || et1.getText().equals(""))){
			 Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_LONG).show();
			 isValide = false;
		}
		if(isValide && (et2.getText() == null || et2.getText().equals(""))){
			 Toast.makeText(getApplicationContext(), "Please enter mobile no", Toast.LENGTH_LONG).show();
			 isValide = false;
		}
		if(isValide && (et3.getText() == null || et3.getText().equals(""))){
			 Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_LONG).show();
			 isValide = false;
		}
		if(isValide && (et4.getText() == null || et4.getText().equals(""))){
			 Toast.makeText(getApplicationContext(), "Please enter capital", Toast.LENGTH_LONG).show();
			 isValide = false;
		}
		
		if(isValide){
			 AsyncHttpClient client = new AsyncHttpClient();
			 RequestParams params = new RequestParams();
			 HashMap<String, String> registerData = new HashMap<String, String>();
			 registerData.put("uname", et0.getText().toString());
			 registerData.put("password", et1.getText().toString());
			 registerData.put("mobile", et2.getText().toString());
			 registerData.put("email", et3.getText().toString());
			 registerData.put("capital", et4.getText().toString());
			 Gson gson = new GsonBuilder().create();
			 prgDialog.show();
			 Log.d("DEBUG", "Json register date: "+gson.toJson(registerData));
			 params.put("registerJSON", gson.toJson(registerData));
			 client.post(link,params ,new AsyncHttpResponseHandler() {
	             @Override
	             public void onSuccess(String response) {
	                 System.out.println(response);
	                
	                 try {
	                	 
	                	 JSONObject jobj = new JSONObject(response);
	                     int succ = jobj.getInt("success");
	                     
	                	 	if(succ == 1){
	                	 		
	                	 		Toast.makeText(getApplicationContext(),
		            				       "Signup Successfully",
		            			 Toast.LENGTH_LONG).show();
	                	 		 sucessDone();
	                	 	}else if(succ == 2){
	                	 		
	                	 		Toast.makeText(getApplicationContext(),
		            				       "This user is already registe! Please try with other UserName",
		            			 Toast.LENGTH_LONG).show();
	                	 		
	                	 	}else {
		            			 Toast.makeText(getApplicationContext(),
		            				       "Some Error Occurred!",
		            			 Toast.LENGTH_SHORT).show();
	                         }
	                	 	
	                    
	                 } catch (JSONException e) {
	                     Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
	                     e.printStackTrace();
	                 }finally{
	                	 prgDialog.hide();
	                 }
	             }

	             @Override
	             public void onFailure(int statusCode, Throwable error,
	                 String content) {
	                 prgDialog.hide();
	                 if(statusCode == 404){
	                     Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
	                 }else if(statusCode == 500){
	                     Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
	                 }else{
	                     Toast.makeText(getApplicationContext(), "Device might not be connected to Internet", Toast.LENGTH_LONG).show();
	                 }
	             }
	         });
			 
			Log.d("DEBUG","Out signup of SignupActivity");
		}else{
			//Do nothing
		}
		
	}
	
	public void sucessDone()
	{
		Intent loginIntent = new Intent(this,MainActivity.class);
		startActivity(loginIntent);
	}
	
}
