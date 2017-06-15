package com.bst.stockahm;

import java.util.HashMap;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.bst.stockahm.R;
import com.bst.stockahm.app.Config;
import com.bst.stockahm.util.AssetsPropertyReader;
import com.bst.stockahm.util.NotificationUtils;
import com.bst.stockahm.util.Session;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	 ProgressDialog prgDialog;
	 
	 private AssetsPropertyReader assPrpRdr;
	 private Context context2;
	 private Properties p;
	 private BroadcastReceiver mRegistrationBroadcastReceiver;
	 //private TextView txtRegId, txtMessage;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mRegistrationBroadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {

				// checking for type intent filter
				if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
					// gcm successfully registered
					// now subscribe to `global` topic to receive app wide notifications
					FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

					displayFirebaseRegId();

				} else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
					// new push notification is received

					String message = intent.getStringExtra("message");

					Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

					//txtMessage.setText(message);
				}
			}
		};
		displayFirebaseRegId();

		prgDialog = new ProgressDialog(this);
	    prgDialog.setMessage("Validating Login. Please wait...");
	    prgDialog.setCancelable(false);
	    
	    // for registration
	    TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
	    registerScreen.setOnClickListener(new View.OnClickListener() {
	    	 
            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), Signup.class);
                startActivity(i);
            }
        });
	    
	    //check session and direct login
	    Session session = new Session(MainActivity.this);
	    if(session.getUserName().length() > 0 && session.getPassword().length() >0){
	    	context2 = this;
			assPrpRdr = new AssetsPropertyReader(context2);
			p = assPrpRdr.getProperties("eServiceConstants.properties");
			String link = p.getProperty("backOffice.link") + "/" + p.getProperty("backoffice.loginFile");
			
			// validate username password on internet master database and fetch data
			validateLogin(session.getUserName(),session.getPassword(),link, MainActivity.this);
	    }
	}

	private void displayFirebaseRegId() {
		SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
		String regId = pref.getString("regId", null);

		Log.e("DEBUG", "Firebase reg id: " + regId);

		if (!TextUtils.isEmpty(regId)){
			Toast.makeText(MainActivity.this,
					regId,
					Toast.LENGTH_SHORT).show();
		}//txtRegId.setText("Firebase Reg Id: " + regId);
		else{
			//regId = FirebaseInstanceId.getInstance().getToken();

			Toast.makeText(MainActivity.this,
					regId,
					Toast.LENGTH_LONG).show();
		}
			//txtRegId.setText("Firebase Reg Id is not received yet!");
	}
	@Override
	protected void onResume() {
		super.onResume();

		// register GCM registration complete receiver
		LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
				new IntentFilter(Config.REGISTRATION_COMPLETE));

		// register new push message receiver
		// by doing this, the activity will be notified each time a new message arrives
		LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
				new IntentFilter(Config.PUSH_NOTIFICATION));

		// clear the notification area when the app is opened
		NotificationUtils.clearNotifications(getApplicationContext());
	}

	@Override
	protected void onPause() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
		super.onPause();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
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
	
	public void onClickLoginButton(View view){
		EditText etUserName = (EditText) findViewById(R.id.etUserName);
		EditText etPassword = (EditText) findViewById(R.id.etPassword);
	//	Session session = new Session(MainActivity.this);
		
		
		if("".equals(etUserName.getText().toString()) && "".equals(etPassword.getText().toString())){
			Toast.makeText(getApplicationContext(), "Please enter User Name and Password !", Toast.LENGTH_LONG).show();
		}else if(!"".equals(etUserName.getText().toString()) && "".equals(etPassword.getText().toString())){
			Toast.makeText(getApplicationContext(), "Please enter User Password !", Toast.LENGTH_LONG).show();
		}else if("".equals(etUserName.getText().toString()) && !"".equals(etPassword.getText().toString())){
				Toast.makeText(getApplicationContext(), "Please enter User Name !", Toast.LENGTH_LONG).show();
		}else{
			// get properties value from asset property file
			context2 = this;
			assPrpRdr = new AssetsPropertyReader(context2);
			p = assPrpRdr.getProperties("eServiceConstants.properties");
			String link = p.getProperty("backOffice.link") + "/" + p.getProperty("backoffice.loginFile");
			
			/* validate username password on internet master database*/
			validateLogin(etUserName.getText().toString(),etPassword.getText().toString(),link, MainActivity.this);
		}
	}
	
	public void validateLogin(String userName, String password, String lnk, final Context context)
	{
		Log.d("DEBUG",lnk);
		Log.d("DEBUG","In validateLogin(String userName, String password) of MainActivity");
		 AsyncHttpClient client = new AsyncHttpClient();
		 RequestParams params = new RequestParams();
		 HashMap<String, String> loginDetails = new HashMap<String, String>();
		 loginDetails.put("uname", userName.trim());
		 loginDetails.put("pwd", password.trim());
		 Gson gson = new GsonBuilder().create();
		 prgDialog.show();
		 params.put("usersJSON", gson.toJson(loginDetails));
		 client.post(lnk,params ,new AsyncHttpResponseHandler() {
             @Override
             public void onSuccess(String response) {
                 System.out.println(response);
                
                 try {
                	 
                	 JSONObject jobj = new JSONObject(response);
                     int succ = jobj.getInt("success");
                     Session session = new Session(context);
                	 	if(succ == 1){
                	 		
                         JSONArray arr1 = jobj.getJSONArray("common_data");
	                    
                         for(int i=0; i<arr1.length();i++){
                         		JSONObject obj = arr1.getJSONObject(i);
                        	 
                             session.setId(obj.get("id").toString());
                        	 session.setUserName(obj.get("user_name").toString());
                        	 session.setMobileNo(obj.get("mobile_no").toString());
                        	 session.setCapital(obj.get("capital").toString());
                        	 session.setReconsileFlage(obj.get("reconsile_flage").toString());
                        	 session.setEmail(obj.get("email").toString());
                        	 session.setPassword(obj.get("password").toString());
                        	 session.setIsAdmin(obj.get("is_admin").toString());
                         }
                      
                        	 success();
                         }else{
                        	 session.setUserName("");
	            			 Toast.makeText(MainActivity.this,
	            				       "Wrong UserName and/or Password. Please Try again!",
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
		 
		Log.d("DEBUG","Out validateLogin(String userName, String password) of MainActivity");
	}
	
	public void success()
	{
		Intent loginIntent = new Intent(this,HomePageActivity.class);
		startActivity(loginIntent);
	}
	
}
