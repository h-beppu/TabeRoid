package com.hide_ab.TabeRoid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ShopDetail extends Activity {
	// �������ʓX�܃f�[�^�I�u�W�F�N�g
	protected ShopInfos shopinfos;
	// ListAdapter
	private ShopDetailAdapter shopdetailadapter = null;

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

        TextView tvRestaurantName = (TextView)findViewById(R.id.RestaurantName);
        tvRestaurantName.setText(shopInfo.getRestaurantName());
/*
        TextView tvTabelogUrl = (TextView)findViewById(R.id.TabelogUrl);
        tvTabelogUrl.setText(shopInfo.getTabelogUrl());

        TextView tvTabelogMobileUrl = (TextView)findViewById(R.id.TabelogMobileUrl);
        tvTabelogMobileUrl.setText(shopInfo.getTabelogMobileUrl());
*/
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

        TextView tvDinnerPrice = (TextView)findViewById(R.id.DinnerPrice);
        tvDinnerPrice.setText(shopInfo.getDinnerPrice());

        TextView tvLunchPrice = (TextView)findViewById(R.id.LunchPrice);
        tvLunchPrice.setText(shopInfo.getLunchPrice());

        TextView tvCategory = (TextView)findViewById(R.id.Category);
        tvCategory.setText(shopInfo.getCategory());

        TextView tvStation = (TextView)findViewById(R.id.Station);
        tvStation.setText(shopInfo.getStation());

        TextView tvAddress = (TextView)findViewById(R.id.Address);
        tvAddress.setText(shopInfo.getAddress());

        TextView tvTel = (TextView)findViewById(R.id.Tel);
        tvTel.setText(shopInfo.getTel());

        TextView tvBusinessHours = (TextView)findViewById(R.id.BusinessHours);
        tvBusinessHours.setText(shopInfo.getBusinessHours());

        TextView tvHoliday = (TextView)findViewById(R.id.Holiday);
        tvHoliday.setText(shopInfo.getHoliday());

        TextView tvLatLon = (TextView)findViewById(R.id.LatLon);
        tvLatLon.setText(shopInfo.getLat() + " " + shopInfo.getLon());

        if(shopInfo.getPhoto() != null) {
			ImageView ivPhoto = (ImageView)findViewById(R.id.Photo);
			ivPhoto.setImageBitmap(shopInfo.getPhoto());
		}

        // �R�����g���擾
    	ShopDetailTask task = new ShopDetailTask(shopInfo, ShopDetail.this);
    	task.execute();
	}

    // �R�����g�擾����
    public void closeReview(ShopInfo shopinfo) {
    	// List����ShopDetailAdapter�𐶐�
	    this.shopdetailadapter = new ShopDetailAdapter(this, R.layout.shop_detailrow, shopinfo.getReviews());
    	// ShopDetailAdapter��ShopDetail.xml���ɂ���listview_reviews�ɓn���ē��e��\������
    	ListView listview_reviews = (ListView)findViewById(R.id.listview_reviews);
    	// listview_reviews��shopdetailadapter���Z�b�g
    	listview_reviews.setAdapter(shopdetailadapter);
    }

	// �_�C�A���O�̕\��
    protected void showDialog(final Activity activity, String title, String text) {
    	AlertDialog.Builder ad = new AlertDialog.Builder(activity);
    	ad.setTitle(title);
    	ad.setMessage(text);
    	ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int wichiButton) {
    			activity.setResult(Activity.RESULT_OK);
    		}
    	});
    	ad.create();
    	ad.show();
    }
}