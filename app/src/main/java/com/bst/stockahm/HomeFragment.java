package com.bst.stockahm;


import com.bst.stockahm.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


public class HomeFragment extends Fragment {

	public HomeFragment(){}
	
	 GridView grid;
	 
     
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        
        /* Session session = new Session(container.getContext());
        TextView tv = (TextView)rootView.findViewById(R.id.schoolName);
        TextView tv2 = (TextView)rootView.findViewById(R.id.studentName);
        TextView tv3 = (TextView)rootView.findViewById(R.id.currentDate);
        
        TextView tv4 = (TextView)rootView.findViewById(R.id.lunch);
        TextView tv5 = (TextView)rootView.findViewById(R.id.notice);
        TextView tv6 = (TextView)rootView.findViewById(R.id.homework);
        
        tv.setText(" "+session.getSchoolName());
        tv2.setText(" "+session.getStudentName());*/
        
        //"Sprout mung with vegitable sandwich. Lemonade or any energy drink to protect from hit"
        //One dance performance on patriotic songs will be held on 15th August
        //Assignment of tables in Mathematics will be due on next thursday 
        
       /* tv4.setText(" "+session.getLunchDetail());
        tv5.setText(" "+session.getNoticeDesc());
        tv6.setText(" "+session.getStudentHomework());*/
        
        
        
        //Date dt = new Date();
		//DateFormat df = new DateFormat();
		//@SuppressWarnings("static-access")
		//String dtString = (String)df.format("dd-MM-yyyy", dt);
			
        //tv3.setText(" Date : "+dtString);
       
        return rootView;
    }
}
