package com.edu.thss.smartdental;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ClassFragment extends Fragment {
	
	Button ok;
   
	private static final int REQUEST_CODE = 0; //请求码
	
	public ClassFragment(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_class, container,false);
		this.ok = (Button) rootView.findViewById(R.id.class_ok);
		
		ok.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(getActivity(), LineChartActivity.class);
				intent.putExtra("data", "{\"bills\":[{\"id\":\"2\",\"patient\":\"金小胖\",\"time\":\"2014-12-04 10:50:23\",\"hospital\":\"北京市宣武门医院\",\"medicines\":[{\"mid\":\"1\",\"mcount\":\"15\",\"mname\":\"青霉素\",\"mprice\":\"10\",\"mreimbusement\":\"0\",\"mratio\":\"0\",\"munit\":\"瓶\"}]}],\"success\":1}");
//				intent.putExtra("data", "{\"bills\":[{\"id\":\"2\",\"patient\":\"金小胖\",\"time\":\"2013-12-04 10:50:23\",\"hospital\":\"北京市宣武门医院\",\"medicines\":[{\"mid\":\"1\",\"mcount\":\"15\",\"mname\":\"青霉素\",\"mprice\":\"10\",\"mreimbusement\":\"2\",\"mratio\":\"0.2\",\"munit\":\"瓶\"}]},{\"id\":\"2\",\"patient\":\"金小胖\",\"time\":\"2013-12-05 10:50:23\",\"hospital\":\"北京市宣武门医院\",\"medicines\":[{\"mid\":\"1\",\"mcount\":\"15\",\"mname\":\"青霉素\",\"mprice\":\"9.9\",\"mreimbusement\":\"2\",\"mratio\":\"0.2\",\"munit\":\"瓶\"}]},{\"id\":\"2\",\"patient\":\"金小胖\",\"time\":\"2014-12-04 10:50:23\",\"hospital\":\"北京市宣武门医院\",\"medicines\":[{\"mid\":\"1\",\"mcount\":\"15\",\"mname\":\"青霉素\",\"mprice\":\"10\",\"mreimbusement\":\"2\",\"mratio\":\"0.2\",\"munit\":\"瓶\"}]},{\"id\":\"2\",\"patient\":\"金小胖\",\"time\":\"2014-12-05 10:50:23\",\"hospital\":\"北京市宣武门医院\",\"medicines\":[{\"mid\":\"1\",\"mcount\":\"15\",\"mname\":\"青霉素\",\"mprice\":\"9.9\",\"mreimbusement\":\"2\",\"mratio\":\"0.2\",\"munit\":\"瓶\"}]}],\"success\":1}");
				startActivityForResult(intent,REQUEST_CODE);
			}});
		
		//intent.putExtra("data",ok.getText() );
		return rootView;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE&&resultCode == MyTestActivity.RESULT_CODE){
			String result = data.getExtras().getString("result-data");
		}
	}
}
