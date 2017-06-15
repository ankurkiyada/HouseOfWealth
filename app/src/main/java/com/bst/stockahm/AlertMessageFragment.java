package com.bst.stockahm;

import com.bst.stockahm.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AlertMessageFragment extends Fragment {
	
	public AlertMessageFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_alert, container, false);
         
        return rootView;
    }
}
