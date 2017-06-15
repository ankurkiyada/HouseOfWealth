package com.bst.stockahm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bst.stockahm.R;
import com.bst.stockahm.model.MainTable;
import com.bst.stockahm.util.AssetsPropertyReader;
import com.bst.stockahm.util.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableLayout.LayoutParams;

public class AssignmentFragment extends Fragment {
	
	 Context thiscontext;
	 ProgressDialog prgDialog;
	 private AssetsPropertyReader assPrpRdr;
	 private Properties p;
	 TableLayout table_layout;
	 
	public AssignmentFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_assignment, container, false);
   	 	
        thiscontext = container.getContext();
        prgDialog = new ProgressDialog(thiscontext);
        
        Session session = new Session(thiscontext);
        
        if("Y".equalsIgnoreCase(session.getReconsileFlage())){
      
        	assPrpRdr = new AssetsPropertyReader(thiscontext);
    		p = assPrpRdr.getProperties("eServiceConstants.properties");
    		String link = p.getProperty("backOffice.link") + "/" + p.getProperty("backoffice.stockDetails");
    		fetchListOfStockDetail(link, session);
    		
        }else{
        	Fragment fragment = new AlertMessageFragment();
        	
        	FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();


        }
      
        return rootView;
    }

	private void fetchListOfStockDetail(String link,Session session) {
		Log.d("DEBUG","In fetchListOfStockDetail(String link) of AssignmentFragment");
		
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		
		HashMap<String, String> assignmentDetail = new HashMap<String, String>();
		//Session session = new Session(thiscontext);
		assignmentDetail.put("userName", session.getUserName());

		 
		 Gson gson = new GsonBuilder().create();
		 prgDialog.show();
		 params.put("usersJSON", gson.toJson(assignmentDetail));
		 client.post(link,params ,new AsyncHttpResponseHandler() {
			 	
			 public void onSuccess(String response) {
                 System.out.println(response);
                 prgDialog.hide();
                 try {
                	 JSONObject jobj = new JSONObject(response);
                     int succ = jobj.getInt("success");
                     Session session = new Session(thiscontext);
                     if(succ == 1){
                    	  JSONArray arr1 = jobj.getJSONArray("stockData");
                    	  List<MainTable> stockList = new ArrayList<>();
                    	  for(int i=0; i<arr1.length();i++){
                    		 JSONObject obj = arr1.getJSONObject(i);
	                         MainTable mainTable = new MainTable();
	                         mainTable.setSrNo(Long.valueOf(obj.get("id").toString()));
	                         mainTable.setCreation_date(obj.get("creation_date").toString());
	                         mainTable.setCompany_name(obj.get("company_name").toString());
	                         mainTable.setBuy_sell(obj.get("buy_sell").toString());
	                         mainTable.setBuy_sell(obj.get("buy_price").toString());
	                         mainTable.setSl(obj.get("sl").toString());
	                         mainTable.setExit_price(obj.get("exit_price").toString());
	                         mainTable.setExit_date(obj.get("exit_date").toString());
	                         mainTable.setPercent_gain(obj.get("percent_gain").toString());
	                         mainTable.setStatus(obj.get("status").toString());
	                         mainTable.setTreding_sl(obj.get("treding_sl").toString());
	                         mainTable.setCurrent_price(obj.get("current_price").toString());
	                         mainTable.setUpdated_date(obj.get("updated_date").toString());
	                         stockList.add(mainTable);
	                     }
                    	  table_layout = (TableLayout) getView().findViewById(R.id.tlStockRows);
              	          table_layout.removeAllViews();
                    	  buildTable(stockList, session);
                     }else{
                    	 Toast.makeText(thiscontext,
          				       "No Record found!",
          			 Toast.LENGTH_SHORT).show();
                     }
                    
                 } catch (JSONException e) {
                     Toast.makeText(thiscontext, "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                     e.printStackTrace();
                 }
			 }
			 
			 @Override
             public void onFailure(int statusCode, Throwable error,
                 String content) {
                 prgDialog.hide();
                 if(statusCode == 404){
                     Toast.makeText(thiscontext, "Requested resource not found", Toast.LENGTH_LONG).show();
                 }else if(statusCode == 500){
                     Toast.makeText(thiscontext, "Something went wrong at server end", Toast.LENGTH_LONG).show();
                 }else{
                     Toast.makeText(thiscontext, "Device might not be connected to Internet", Toast.LENGTH_LONG).show();
                 }
             }
			 
		 });
	}
	
	private void buildTable(List<MainTable> stockList, Session session){

		Log.d("DEBUG","In BuildTable(List<PackageMaster> itemList) of AssignmentFragment"); 
		Gson gson = new GsonBuilder().create();
		session.setMainTableList(gson.toJson(stockList));
		// Session session = new Session(this);
		/* outer for loop */
		TableRow.LayoutParams tableRowLayout = new TableRow.LayoutParams();
		tableRowLayout.setMargins(1, 1, 1, 1);
		tableRowLayout.weight=1;
		 
		TableRow rowHeader = new TableRow(thiscontext);
		rowHeader.setBackgroundColor(Color.rgb(168, 207, 236));
		
		TextView tv0 = new TextView(thiscontext);
		tv0.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		tv0.setBackgroundColor(Color.rgb(60,90,153));
		tv0.setTextColor(Color.WHITE);
		tv0.setPadding(2, 2, 2, 2);
		tv0.setText("Id");
		rowHeader.addView(tv0,tableRowLayout);
		
		TextView tv = new TextView(thiscontext);
		tv.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		tv.setBackgroundColor(Color.rgb(60,90,153));
		tv.setTextColor(Color.WHITE);
		tv.setPadding(2, 2, 2, 2);
		tv.setText("Date");
		rowHeader.addView(tv,tableRowLayout);
		
		TextView tv2 = new TextView(thiscontext);
		tv2.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		tv2.setBackgroundColor(Color.rgb(60,90,153));
		tv2.setTextColor(Color.WHITE);
		tv2.setPadding(2, 2, 2, 2);
		tv2.setText("Company Name");
		rowHeader.addView(tv2,tableRowLayout);
		
		TextView tv3 = new TextView(thiscontext);
		tv3.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		tv3.setBackgroundColor(Color.rgb(60,90,153));
		tv3.setTextColor(Color.WHITE);
		tv3.setPadding(2, 2, 2, 2);
		tv3.setText("Buy Price");
		rowHeader.addView(tv3,tableRowLayout);
		
		TextView tv4 = new TextView(thiscontext);
		tv4.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		tv4.setBackgroundColor(Color.rgb(60,90,153));
		tv4.setTextColor(Color.WHITE);
		tv4.setPadding(2, 2, 2, 2);
		tv4.setText("SL");
		rowHeader.addView(tv4,tableRowLayout);
		
		TextView tv5 = new TextView(thiscontext);
		tv5.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		tv5.setBackgroundColor(Color.rgb(60,90,153));
		tv5.setTextColor(Color.WHITE);
		tv5.setPadding(2, 2, 2, 2);
		tv5.setText("Status");
		
		rowHeader.addView(tv5,tableRowLayout);
		table_layout.addView(rowHeader);
		
		// for loop
		  for (MainTable stockBean : stockList) {
			  
			  TableRow row = new TableRow(thiscontext);
			  row.setBackgroundColor(Color.rgb(168, 207, 236));
			  row.setBaselineAligned(false);
			  row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					   LayoutParams.WRAP_CONTENT));

			  	/*Column 0*/
			  	final TextView ev0 = new TextView(thiscontext);
				ev0.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
				ev0.setBackgroundColor(Color.WHITE);
			    ev0.setTextColor(Color.BLACK);
				ev0.setPadding(2, 2, 2, 2);
				ev0.setHeight(100);
				ev0.setText(String.valueOf(stockBean.getSrNo()));
				row.addView(ev0,tableRowLayout);
			  	
				/*Column 1*/
				final TextView ev = new TextView(thiscontext);
				ev.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
				ev.setBackgroundColor(Color.WHITE);
			    ev.setTextColor(Color.BLACK);
				ev.setPadding(2, 2, 2, 2);
				ev.setHeight(100);
				ev.setText(stockBean.getCreation_date().split(" ")[0]);
				row.addView(ev,tableRowLayout);
			
				/*Column 2*/
		
				final TextView ev2 = new TextView(thiscontext);
				ev2.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
				ev2.setBackgroundColor(Color.WHITE);
			    ev2.setTextColor(Color.BLACK);
				ev2.setPadding(2, 2, 2, 2);
				ev2.setHeight(100);
				ev2.setText(stockBean.getCompany_name());
				row.addView(ev2,tableRowLayout);
				
				/*Column 3*/
				final TextView ev3 = new TextView(thiscontext);
				ev3.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
				ev3.setBackgroundColor(Color.WHITE);
			    ev3.setTextColor(Color.BLUE);
				ev3.setPadding(2, 2, 2, 2);
				ev3.setHeight(100);
				ev3.setText(stockBean.getBuyPrice());
				row.addView(ev3);
				
				/*Column 4*/
				final TextView ev4 = new TextView(thiscontext);
				ev4.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
				ev4.setBackgroundColor(Color.WHITE);
			    ev4.setTextColor(Color.BLACK);
				ev4.setPadding(2, 2, 2, 2);
				ev4.setHeight(100);
				ev4.setText(stockBean.getSl());
				row.addView(ev4);
				
				/*Column 5*/
				final TextView ev5 = new TextView(thiscontext);
				ev5.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT));
				ev5.setBackgroundColor(Color.WHITE);
			    ev5.setTextColor("0".equalsIgnoreCase(stockBean.getStatus())?Color.RED:Color.GREEN);
				ev5.setPadding(2, 2, 2, 2);
				ev5.setHeight(100);
				ev5.setText("0".equalsIgnoreCase(stockBean.getStatus())?"Open":"Close");
				row.addView(ev5);
				
				
				row.setClickable(true);
				row.setOnClickListener(tablerowOnClickListener);
			    table_layout.addView(row);
			  
		  }
		  Log.d("DEBUG","Out BuildTable(List<ItemMaster> itemList) of AssignmentFragment");
	}
	
	private OnClickListener tablerowOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			TableRow tr = (TableRow)v;
			TextView td = (TextView)tr.getChildAt(0); 
			Session session = new Session(thiscontext);
			session.setRowId(td.getText().toString());
			Intent stockIntent = new Intent(thiscontext,StockDetails.class);
			startActivity(stockIntent);
		}
		
	};
}
