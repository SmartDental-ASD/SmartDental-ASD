package com.edu.thss.smartdental;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

public class BillFragment extends Fragment  {
	private FragmentManager fm = null; 
	private RadioGroup radioGroup;
	public BillFragment(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_bill, container,false);
		fm = getChildFragmentManager();
		radioGroup = (RadioGroup)rootView.findViewById(R.id.bill_tab);
		radioGroup.check(R.id.bill_tab_all);
		changeFragment(0);
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
		case 0: tempfragment = new ToothPermanentFragment();break;
		case 1: tempfragment = new BillListFragment();break;
		case 2: tempfragment = new Tooth2DFragment();break;
		default: break;
		}
		if(tempfragment != null){
        	transaction.replace(R.id.bill_tabcontent, tempfragment);
        	transaction.commit();
        }
	}
	

}
