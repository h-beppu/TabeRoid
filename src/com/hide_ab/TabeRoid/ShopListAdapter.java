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
public class ShopListAdapter extends ArrayAdapter {
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
		// �r���[���󂯎��
		View view = convertView;
		// �󂯎�����r���[��null�Ȃ�V�����r���[�𐶐�
		if(view == null) {
			view = inflater.inflate(R.layout.shop_listrow, null);
		}

		// �\�����ׂ��f�[�^�̎擾
		ShopInfo shopInfo = (ShopInfo)List.get(position);
		if(shopInfo != null) {
			// �X�N���[���l�[�����r���[�ɃZ�b�g
			TextView tvDate = (TextView)view.findViewById(R.id.text1);
			tvDate.setTypeface(Typeface.DEFAULT_BOLD);
			if(tvDate != null) {
				tvDate.setText(shopInfo.getRestaurantName());
			}

			TextView tvJyoName = (TextView)view.findViewById(R.id.text2);
			if(tvJyoName != null) {
				tvJyoName.setText(shopInfo.getTotalScore());
			}

			TextView tvCategory = (TextView)view.findViewById(R.id.text3);
			if(tvCategory != null) {
				tvCategory.setText(shopInfo.getCategory());
			}

			TextView tvStation = (TextView)view.findViewById(R.id.text4);
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