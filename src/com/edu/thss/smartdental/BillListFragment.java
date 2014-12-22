package com.edu.thss.smartdental;

import java.security.PublicKey;
import java.util.ArrayList;

import com.edu.thss.smartdental.model.BillListElement;
import com.edu.thss.smartdental.ui.calendar.SlideView;
import com.edu.thss.smartdental.util.Tools;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout; 
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.os.Handler;
import android.os.Message;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import com.edu.thss.smartdental.model.general.SDAccount;

public class BillListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
	private static final int REFRESH_COMPLETE = 0X110;
	private ListView mListView;
	private SwipeRefreshLayout mySRLayout;
	private ArrayList<BillListItem> mDatas; 
	private BillListAdapter adapter;
	private SDAccount [] accounts;
	private ViewGroup handler;
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle state) {
		View rootView = inflater.inflate(R.layout.bill_list, container, false);
		mListView = (ListView)rootView.findViewById(R.id.bill_listView);
		mySRLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_layout);
		handler = container;
		accounts = Tools.getAccountInfoFromLocal(container.getContext());
		initData(accounts);
		
	    return rootView;
	}
	
	private void initData(final SDAccount[] accounts) {
		mDatas = new ArrayList<BillListItem>();
		BillListItem item;
		item = new BillListItem();
		if(accounts != null){
			for(int i = 0; i < accounts.length; i++){
				BillListElement billList = new BillListElement(accounts[i].hospital, accounts[i].time, Double.toString(accounts[i].finalTotal));
				item.billList = billList;
				mDatas.add(item);
			}
		}
		mySRLayout.setOnRefreshListener(this);
		mySRLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,  
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
		adapter = new BillListAdapter();
        mListView.setAdapter(adapter); 
		
        //对list添加点击事件
        mListView.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		Intent intent = new Intent(getActivity(), BillDetailActivity.class);
        		intent.putExtra("id", Integer.toString(accounts[(int) id].id));
        		startActivity(intent);
        	}
		});
        
        this.registerForContextMenu(mListView);
        
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // 设置上下文菜单的选项"查看详情"  
        menu.add(Menu.NONE, 0, 0, "标记已读");  
        // 设置上下文菜单的选项"删除信息"  
        menu.add(Menu.NONE, 1, 0, "删除账单");
	}
	
	@Override 
    public boolean onContextItemSelected(MenuItem item) {  
		switch (item.getItemId()) {
		case 0:
			
			break;
		case 1:
			
			break;
		}
		return true;
	}
	
	private class BillListAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
    	
		BillListAdapter(){
    		super();
    		mInflater = getActivity().getLayoutInflater();
    	}
		@Override
		public int getCount() {
			return mDatas.size();
		}

		@Override
		public Object getItem(int position) {
			return mDatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			BillListViewHolder holder;
			View itemView = (View) convertView;
			if (itemView == null) {
                itemView = mInflater.inflate(R.layout.bill_list_item, null);

                holder = new BillListViewHolder(itemView);
                itemView.setTag(holder);
            } else {
                holder = (BillListViewHolder) itemView.getTag();
            }            
			BillListItem item = mDatas.get(position);
			
			holder.name.setText(item.billList.name);
			holder.date.setText(item.billList.date);
			holder.cost.setText(item.billList.cost);
			
			return itemView;
		}
	}
	
	private static class BillListViewHolder{
    	public TextView name;
    	public TextView date;
    	public TextView cost;
    	
    	BillListViewHolder(View view){
    		name = (TextView) view.findViewById(R.id.bill_list_item_name);
    		date = (TextView) view.findViewById(R.id.bill_list_item_date);
    		cost = (TextView) view.findViewById(R.id.bill_list_item_cost);
    	}
    }
	
	public class BillListItem{
    	public BillListElement billList;
    	public SlideView slideView;
    	
    }
	
	private Handler mHandler = new Handler()  
    {  
        public void handleMessage(Message msg)  
        {  
            switch (msg.what)  
            {  
            case REFRESH_COMPLETE:  
            	accounts = Tools.getAccountInfoFromLocal(handler.getContext());
        		initData(accounts);
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