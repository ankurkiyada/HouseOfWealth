package com.bst.stockahm;


import java.lang.reflect.Type;
import java.util.List;

import com.bst.stockahm.model.MainTable;
import com.bst.stockahm.util.Session;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class StockDetails extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock_details);
		
		Session session = new Session(StockDetails.this);
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
				TextView creationDate = (TextView) findViewById(R.id.textView2);
				TextView companyName = (TextView) findViewById(R.id.textView4);
				TextView buySell = (TextView) findViewById(R.id.textView6);
				TextView sl = (TextView) findViewById(R.id.textView8);
				TextView exitPrice = (TextView) findViewById(R.id.textView10);
				TextView exitDate = (TextView) findViewById(R.id.textView12);
				TextView percentageGain = (TextView) findViewById(R.id.textView14);
				TextView status = (TextView) findViewById(R.id.textView16);
				TextView tredingSL = (TextView) findViewById(R.id.textView18);
				TextView currentPrice = (TextView) findViewById(R.id.TextView02);
				TextView updatedDate = (TextView) findViewById(R.id.TextView04);
				
				creationDate.setText(mtBean.getCreation_date());
				companyName.setText(mtBean.getCompany_name());
				buySell.setText(mtBean.getBuy_sell());
				sl.setText(mtBean.getSl());
				exitPrice.setText(("null".equalsIgnoreCase(mtBean.getExit_price())) ? "":mtBean.getExit_price());
				exitDate.setText(("null".equalsIgnoreCase(mtBean.getExit_date())) ? "":mtBean.getExit_date());
				percentageGain.setText(("null".equalsIgnoreCase(mtBean.getPercent_gain())) ? "":mtBean.getPercent_gain());
				status.setText("0".equalsIgnoreCase(mtBean.getStatus())?"Open":"Close");
				tredingSL.setText(("null".equalsIgnoreCase(mtBean.getTreding_sl())) ? "":mtBean.getTreding_sl());
				currentPrice.setText(("null".equalsIgnoreCase(mtBean.getCurrent_price())) ? "":mtBean.getCurrent_price());
				updatedDate.setText(("null".equalsIgnoreCase(mtBean.getUpdated_date())) ? "":mtBean.getUpdated_date());
				
			}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stock_details, menu);
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
	
	public void onclickStockDtlBackBtn(View view){
		Intent intent = new Intent(this, HomePageActivity.class);
		startActivity(intent);
	}
}
