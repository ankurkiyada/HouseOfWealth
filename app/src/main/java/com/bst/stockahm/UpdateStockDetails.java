package com.bst.stockahm;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;

import com.bst.stockahm.model.MainTable;
import com.bst.stockahm.util.AssetsPropertyReader;
import com.bst.stockahm.util.Session;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateStockDetails extends Activity {

	 ProgressDialog prgDialog;
	 private AssetsPropertyReader assPrpRdr;
	 private Properties p;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_stock_details);
		
		Session session = new Session(UpdateStockDetails.this);
		Gson gson = new GsonBuilder().create();
		
		String selectedRowId = session.getRowId();
		String stockListStr = session.getMainTableList();
		
		Type type = new TypeToken<List<MainTable>>(){}.getType();
		List<MainTable> stockList = gson.fromJson(stockListStr, type);
		
		this.showSelectedRowValue(stockList,selectedRowId);
	}

	private void showSelectedRowValue(List<MainTable> stockList,
			String selectedRowId) {
		
		for(MainTable mtBean: stockList){
			if(mtBean.getSrNo() == Long.valueOf(selectedRowId)){
				TextView creationDate = (TextView) findViewById(R.id.esettextView2);
				TextView companyName = (TextView) findViewById(R.id.esettextView4);
				TextView buySell = (TextView) findViewById(R.id.esettextView6);
				TextView sl = (TextView) findViewById(R.id.esettextView8);
				TextView status = (TextView) findViewById(R.id.etestextView16);
				EditText exitPrice = (EditText) findViewById(R.id.esetView10);
				EditText tredingSL = (EditText) findViewById(R.id.esetView18);
				EditText currentPrice = (EditText) findViewById(R.id.esetView02);
				Button updateStock = (Button) findViewById(R.id.btnUpdateStock);
				
				creationDate.setText(mtBean.getCreation_date());
				companyName.setText(mtBean.getCompany_name());
				buySell.setText(mtBean.getBuyPrice());
				sl.setText(mtBean.getSl());
				exitPrice.setText(("null".equalsIgnoreCase(mtBean.getExit_price())) ? "":mtBean.getExit_price());
				
				status.setText("0".equals(mtBean.getStatus())?"Open":"Close");
				tredingSL.setText(("null".equalsIgnoreCase(mtBean.getTreding_sl())) ? "":mtBean.getTreding_sl());
				currentPrice.setText(("null".equalsIgnoreCase(mtBean.getCurrent_price())) ? "":mtBean.getCurrent_price());
				
				if(mtBean.getStatus().equals("1")){
					updateStock.setClickable(false);
				}
				
			}
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_stock_details, menu);
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
	
	public void onclickUpdateStockBtn(View view){
		Session session = new Session(this);
		
		EditText exitPrice = (EditText) findViewById(R.id.esetView10);
		EditText tredingSL = (EditText) findViewById(R.id.esetView18);
		EditText currentPrice = (EditText) findViewById(R.id.esetView02);
		TextView buyPrice = (TextView) findViewById(R.id.esettextView6);

		
		prgDialog = new ProgressDialog(this);
	    prgDialog.setMessage("Updating data... Please wait...");
	    prgDialog.setCancelable(false);
	    
	    assPrpRdr = new AssetsPropertyReader(UpdateStockDetails.this);
		p = assPrpRdr.getProperties("eServiceConstants.properties");
		String link = p.getProperty("backOffice.link") + "/" + p.getProperty("backoffice.updateStockDetail");
		
		boolean isValide = true;
		
		if((exitPrice.getText() == null || exitPrice.getText().equals("")) &&
			(tredingSL.getText() == null || tredingSL.getText().equals("")) &&
			(currentPrice.getText() == null || currentPrice.getText().equals(""))){
			 Toast.makeText(getApplicationContext(), "Please enter any of the value to update", Toast.LENGTH_LONG).show();
			 isValide = false;
		}
		
		
		if(isValide){
			
			 AsyncHttpClient client = new AsyncHttpClient();
			 RequestParams params = new RequestParams();
			 HashMap<String, String> registerData = new HashMap<String, String>();
			 registerData.put("rowId", session.getRowId());
			 registerData.put("exitPrice", exitPrice.getText().toString());
			 registerData.put("tredingSL", tredingSL.getText().toString());
			 registerData.put("currentPrice", currentPrice.getText().toString());
			 registerData.put("buyPrice", buyPrice.getText().toString());

			 Gson gson = new GsonBuilder().create();
			 prgDialog.show();
			 Log.d("DEBUG", "Json Update stock: "+gson.toJson(registerData));
			 params.put("updateStockDetail", gson.toJson(registerData));
			 client.post(link,params ,new AsyncHttpResponseHandler() {
	             @Override
	             public void onSuccess(String response) {
	                 System.out.println(response);
	                
	                 try {
	                	 
	                	 JSONObject jobj = new JSONObject(response);
	                     int succ = jobj.getInt("success");
	                     
	                	 	if(succ == 1){
	                	 		
	                	 		Toast.makeText(getApplicationContext(),
		            				       "Stock Updated Successfully",
		            			 Toast.LENGTH_LONG).show();
	                	 		 sucessDone();
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
			 
			Log.d("DEBUG","Out saveLeaveNote(String leaveNote) of HomePageActivity");
		
		}else{
			//do nothing
		}
	
	}
	
	public void sucessDone()
	{
		Intent i = new Intent(this,HomePageActivity.class);
		startActivity(i);
	}
}
