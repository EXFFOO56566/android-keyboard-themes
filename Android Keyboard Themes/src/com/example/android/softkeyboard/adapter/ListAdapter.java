package com.example.android.softkeyboard.adapter;

import gt.keybord.R;
import gt.module.constants.AppConstants;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ListAdapter extends BaseAdapter{
	
	
	Context context;
	LayoutInflater layoutInflater;
	private int[] themes_list;
	public ListAdapter(Context context) {
		// TODO Auto-generated constructor stub
	this.themes_list = AppConstants.THEMES_LIST;
	this.context = context;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return themes_list.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return themes_list[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	class ViewHolder
	{
		ImageView imageItem;
		TextView themes_no;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		
		if(convertView==null)
		{
			holder = new ViewHolder();
			layoutInflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 convertView = layoutInflater.inflate(R.layout.list_item, null);
			 holder.imageItem = (ImageView) convertView.findViewById(R.id.imageView1);
			 holder.themes_no = (TextView) convertView.findViewById(R.id.themes_no);
			 convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.themes_no.setText("Theme No :- "+(position+1));
		holder.imageItem.setImageResource(themes_list[position]);
		
		
		return convertView;
	}

}
