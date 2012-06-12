package com.hide_ab.TabeRoid;

import android.app.TabActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

public class ShopDetail extends TabActivity {
	// 検索結果店舗データオブジェクト
	protected ShopInfos shopinfos;
	// ListAdapter
	private ShopDetailAdapter shopdetailadapter = null;
	protected String Lat;
	protected String Lon;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    // 画面構成を適用
	    TabHost tabHost = getTabHost();
	    LayoutInflater.from(this).inflate(R.layout.shop_detail, tabHost.getTabContentView(), true);
	    tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(getResources().getString(R.string.label_tab1)).setContent(R.id.view1));
	    tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(getResources().getString(R.string.label_tab2)).setContent(R.id.listview_reviews));
	    tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(getResources().getString(R.string.label_tab3)).setContent(R.id.view3));

		// 検索結果店舗データオブジェクト生成
	    shopinfos = (ShopInfos)this.getApplication();

	    // 呼び出し元からパラメータ取得
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

        // コメントを取得
    	ShopDetailTask task = new ShopDetailTask(shopInfo, ShopDetail.this);
    	task.execute();

        this.Lat = shopInfo.getLat();
        this.Lon = shopInfo.getLon();

        // 『地図で見る』ボタン取得
        Button button_share_map = (Button)findViewById(R.id.button_share_map);

        // 『地図で見る』ボタンクリックハンドラ
        button_share_map.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + Lat + "," + Lon + "?z=18"));
        		startActivity(intent);
        	}
        });
	}

    // コメント取得完了
    public void closeReview(ShopInfo shopinfo) {
    	// ListからShopDetailAdapterを生成
	    this.shopdetailadapter = new ShopDetailAdapter(this, R.layout.shop_detailrow, shopinfo.getReviews());
    	// ShopDetailAdapterをShopDetail.xml内にあるlistview_reviewsに渡して内容を表示する
    	ListView listview_reviews = (ListView)findViewById(R.id.listview_reviews);
    	// listview_reviewsにshopdetailadapterをセット
    	listview_reviews.setAdapter(shopdetailadapter);
    }
/*
	// ダイアログの表示
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
*/
}