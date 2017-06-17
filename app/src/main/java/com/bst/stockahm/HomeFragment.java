package com.bst.stockahm;
import com.bst.stockahm.R;
import com.bst.stockahm.model.Blog;
import com.bst.stockahm.util.MyRecyclerViewAdapter;

import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;

import java.util.ArrayList;
public class HomeFragment extends Fragment {

	public HomeFragment(){}
    
    MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    View rootView;
    ArrayList<Blog> blogList=new ArrayList<>();
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView= (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        recyclerView.setHasFixedSize(true);
        adapter = new MyRecyclerViewAdapter(blogList);
        preparedata();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        return rootView;
    }
    private void preparedata() {
        // Fetch the blog data from database
        Blog blog=new Blog("12-12-12","Abcnddf","100");
        blogList.add(blog);

        blog=new Blog("12-12-12","Here is an example. I limit the sizewith the maxLength attribute, limit it to a single line with maxLines attribute, ","100");
        blogList.add(blog);

        blog=new Blog("12-12-12","Abcnddf","100");
        blogList.add(blog);

        blog=new Blog("12-12-12","Abcnddf","100");
        blogList.add(blog);

        blog=new Blog("12-12-12","Abcnddf","100");
        blogList.add(blog);

        blog=new Blog("12-12-12","Abcnddf","100");
        blogList.add(blog);

        blog=new Blog("12-12-12","Abcnddf","100");
        blogList.add(blog);

        adapter.notifyDataSetChanged();
    }


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

}
