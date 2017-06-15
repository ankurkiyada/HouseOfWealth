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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableLayout.LayoutParams;

public class AdminFragment extends Fragment {
	
	public AdminFragment(){}
	
	 Context thiscontext;
	 ProgressDialog prgDialog;
	 private AssetsPropertyReader assPrpRdr;
	 private Properties p;
	 TableLayout table_layout;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_admin, container, false);

        thiscontext = container.getContext();
        prgDialog = new ProgressDialog(thiscontext);

        return rootView;
    }
	
}
