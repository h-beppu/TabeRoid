package com.hide_ab.TabeRoid;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ShopDetailAdapter extends ArrayAdapter<String> {
	private ArrayList<String> List;
	private LayoutInflater inflater;

	@SuppressWarnings("unchecked")
	public ShopDetailAdapter(Context context, int textViewResourceId, ArrayList<String> List) {
		super(context, textViewResourceId, List);
		this.List = List;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// ビューを受け取る
		View view = convertView;
		// 受け取ったビューがnullなら新しくビューを生成
		if(view == null) {
			view = inflater.inflate(R.layout.shop_detailrow, null);
		}

		// 表示すべきデータの取得
		String Comment = (String)List.get(position);
		if(Comment != null) {
			// スクリーンネームをビューにセット
			TextView tvDate = (TextView)view.findViewById(R.id.text1z);
			tvDate.setTypeface(Typeface.DEFAULT_BOLD);
			if(tvDate != null) {
				tvDate.setText(Comment);
			}
		}
		return view;
	}
}