package com.edu.thss.smartdental;
import com.edu.thss.smartdental.model.general.SDAccount;
import com.edu.thss.smartdental.util.Tools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.Button;

public class BillFragment extends Fragment  {
	private FragmentManager fm = null; 
	private RadioGroup radioGroup;
	private Button bt;
	private ViewGroup handler;
	private static final int REQUEST_CODE = 0; //«Î«Û¬Î
	public BillFragment(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_bill, container,false);
		handler = container;
		fm = getChildFragmentManager();
		radioGroup = (RadioGroup)rootView.findViewById(R.id.bill_tab);
		radioGroup.check(R.id.bill_tab_all);
		changeFragment(0);
		this.bt = (Button) rootView.findViewById(R.id.show_chart);
		bt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(getActivity(), LineChartActivity.class);
				SDAccount [] accounts = Tools.getAccountInfoFromLocal(handler.getContext());
				String s = "{\"bills\":[";
				for(int i = 0; i < accounts.length; i++){
					s += "{\"id\": \"" + Integer.toString(accounts[i].id) + "\",";
					s += "\"patient\": \"" + accounts[i].patientName + "\",";
					s += "\"time\": \"" + accounts[i].time + "\",";
					s += "\"hospital\": \"" + accounts[i].hospital + "\",";
					s += "\"firstTotal\": \"" + accounts[i].firstTotal + "\",";
					s += "\"finalTotal\": \"" + accounts[i].finalTotal + "\"";
					s += "}";
					if(i < accounts.length-1)
						s += ",";
				}
				s += "],\"success\":1}";
				intent.putExtra("data", s);
				startActivityForResult(intent,REQUEST_CODE);
			}});
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch(checkedId){
				case R.id.bill_tab_all: changeFragment(0); break;
				case R.id.bill_tab_unread: changeFragment(1); break;
				case R.id.bill_tab_hide: changeFragment(2); break;
				case R.id.bill_tab_mark: changeFragment(3); break;
				}
               
			}} );
		
		return rootView;
	}

	private void changeFragment(int index){
		FragmentTransaction transaction = fm.beginTransaction();
		Fragment tempfragment = null;
		switch(index){
		case 0: tempfragment = new BillListFragment();break;
		case 1: tempfragment = new BillListFragment();break;
		case 2: tempfragment = new BillListFragment();break;
		default: break;
		}
		if(tempfragment != null){
        	transaction.replace(R.id.bill_tabcontent, tempfragment);
        	transaction.commit();
        }
	}
	

}
