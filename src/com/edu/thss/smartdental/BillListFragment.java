package com.edu.thss.smartdental;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout; 
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.ViewGroup;
import android.os.Handler;
import android.os.Message;

public class BillListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
	private static final int REFRESH_COMPLETE = 0X110;
	private ListView mListView;
	private SwipeRefreshLayout mySRLayout;
	private ArrayAdapter<String> adapter;
	private List<String> mDatas = new ArrayList<String>(Arrays.asList("Java", "Javascript", "C++", "Ruby", "Json",  
            "HTML")); 
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle state) {
		View rootView = inflater.inflate(R.layout.bill_list, container, false);
		mListView = (ListView)rootView.findViewById(R.id.bill_listView);
		mySRLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_layout);
		
		mySRLayout.setOnRefreshListener(this);
		mySRLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,  
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
		adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, mDatas);  
        mListView.setAdapter(adapter); 
	    return rootView;
	}
	
	private Handler mHandler = new Handler()  
    {  
        public void handleMessage(Message msg)  
        {  
            switch (msg.what)  
            {  
            case REFRESH_COMPLETE:  
                mDatas.addAll(Arrays.asList("Lucene", "Canvas", "Bitmap"));  
                adapter.notifyDataSetChanged();  
                mySRLayout.setRefreshing(false);  
                break;  
            }  
        };  
    };  
	
	public void onRefresh() {
		mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000); 
	}
} 