package com.hide_ab.TabeRoid;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class ShopListAdapter extends ArrayAdapter<ShopInfo> {
	private ArrayList<ShopInfo> List;
	private LayoutInflater inflater;

	@SuppressWarnings("unchecked")
	public ShopListAdapter(Context context, int textViewResourceId, ArrayList<ShopInfo> List) {
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
			view = inflater.inflate(R.layout.shop_listrow, null);
		}

		// 表示すべきデータの取得
		ShopInfo shopInfo = (ShopInfo)List.get(position);
		if(shopInfo != null) {
			// スクリーンネームをビューにセット
			TextView tvRestaurantName = (TextView)view.findViewById(R.id.RestaurantName);
			if(tvRestaurantName != null) {
				tvRestaurantName.setText(shopInfo.getRestaurantName());
			}

			TextView tvTotalScore = (TextView)view.findViewById(R.id.TotalScore);
			if(tvTotalScore != null) {
				tvTotalScore.setText(shopInfo.getTotalScore());
			}

			TextView tvCategory = (TextView)view.findViewById(R.id.Category);
			if(tvCategory != null) {
				tvCategory.setText(shopInfo.getCategory());
			}

			TextView tvStation = (TextView)view.findViewById(R.id.Station);
			if(tvStation != null) {
				tvStation.setText(shopInfo.getStation());
			}

			ImageView image_photo = (ImageView)view.findViewById(R.id.image_photo);
			if(image_photo != null) {
				image_photo.setImageBitmap(shopInfo.getPhoto());
			}
		}
		return view;
	}
}