package com.hide_ab.TabeRoid;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopDetail extends Activity {
	// �������ʓX�܃f�[�^�I�u�W�F�N�g
	protected ShopInfos shopinfos;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    // ��ʍ\����K�p
	    setContentView(R.layout.shop_detail);

		// �������ʓX�܃f�[�^�I�u�W�F�N�g����
	    shopinfos = (ShopInfos)this.getApplication();

	    // �Ăяo��������p�����[�^�擾
        Intent intent = getIntent();
        int position = intent.getIntExtra("Position", 0);

        ShopInfo shopInfo = shopinfos.getInfo(position);

        TextView tvShopName = (TextView)findViewById(R.id.ShopName);
        tvShopName.setText(shopInfo.getRestaurantName());

        TextView tvTotalScore = (TextView)findViewById(R.id.TotalScore);
        tvTotalScore.setText(shopInfo.getTotalScore());

        TextView tvTasteScore = (TextView)findViewById(R.id.TasteScore);
        tvTasteScore.setText(shopInfo.getTasteScore());

        TextView tvServiceScore = (TextView)findViewById(R.id.ServiceScore);
        tvServiceScore.setText(shopInfo.getServiceScore());

        TextView tvMoodScore = (TextView)findViewById(R.id.MoodScore);
        tvMoodScore.setText(shopInfo.getMoodScore());

        TextView tvSituation = (TextView)findViewById(R.id.Situation);
        tvSituation.setText(shopInfo.getSituation());

        TextView tvCategory = (TextView)findViewById(R.id.Category);
        tvCategory.setText(shopInfo.getCategory());

        TextView tvStation = (TextView)findViewById(R.id.Station);
        tvStation.setText(shopInfo.getStation());

        if(shopInfo.getPhoto() != null) {
			ImageView ivPhoto = (ImageView)findViewById(R.id.Photo);
			ivPhoto.setImageBitmap(shopInfo.getPhoto());
		}
	}
}