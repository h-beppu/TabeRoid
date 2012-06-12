package com.hide_ab.TabeRoid;

import java.util.Date;
import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.EditText;

public class TabeRoid extends Activity implements LocationListener {
	// 検索結果店舗データオブジェクト
	protected ShopInfos shopinfos;
	// 検索条件
	private LocationManager mLm;
    private String Lat = "35.70209";
    private String Lon = "139.73744";
    private String Station = "調布";
    private EditText edittext_station;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 画面構成を適用
        setContentView(R.layout.taberoid);

		// 検索結果店舗データオブジェクト生成
	    this.shopinfos = (ShopInfos)this.getApplication();

	    // GPS初期化
        mLm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        mLm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
//        mLm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);

        // 『最寄駅』エディットテキスト取得
        edittext_station = (EditText)findViewById(R.id.edittext_station);
        // 『検索』ボタン取得
        Button button_search_gps = (Button)findViewById(R.id.button_search_gps);
        Button button_search_station = (Button)findViewById(R.id.button_search_station);

        // 『検索』ボタンクリックハンドラ
        button_search_gps.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		// GPSの更新を停止
        		mLm.removeUpdates(TabeRoid.this);

        		// 情報はバックグラウンドで取得
        		shopinfos.preSearch("gps", Lat, Lon, "");
            	TabeRoidTask task = new TabeRoidTask(shopinfos, TabeRoid.this);
            	task.execute();

            	// 検索結果の店舗情報を取得
//        	    int ItemNum = shopinfos.ImportData("gps", Lat, Lon, "", TabeRoid.this);
/*
        	    // "ShopList"画面に移行
        		Intent intent = new Intent(TabeRoid.this, ShopList.class);
        		intent.putExtra("Key", "gps");
    			intent.putExtra("Lat", Lat);
    			intent.putExtra("Lon", Lon);
        		TextView text_location = (TextView)findViewById(R.id.text_location);
        		text_location.setText("Click");
    			startActivityForResult(intent, 0);
*/
        	}
        });

        // 『検索』ボタンクリックハンドラ
        button_search_station.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		// GPSの更新を停止
        		mLm.removeUpdates(TabeRoid.this);

        		// 入力された『最寄駅』を取得
        		Station = edittext_station.getText().toString();

        		// 情報はバックグラウンドで取得
        		shopinfos.preSearch("station", "", "", Station);
            	TabeRoidTask task = new TabeRoidTask(shopinfos, TabeRoid.this);
            	task.execute();
        	}
        });
    }

	// "ShopList"画面に移行
    public void openShopList() {
		Intent intent = new Intent(TabeRoid.this, ShopList.class);
//		intent.putExtra("Key", "station");
//		intent.putExtra("Stations", Stations);
//		TextView locationText = (TextView)findViewById(R.id.text_location);
//        locationText.setText("Click");
		startActivityForResult(intent, 0);
    }

	//    @Override
    public void onLocationChanged(Location location) {
        TextView text_location = (TextView)findViewById(R.id.text_location);
        String str;
        Date d ;

        //Toast.makeText(this, "onLocationChanged()", Toast.LENGTH_SHORT).show();
        d = new Date(location.getTime());
        str  = "Time:" + d.toString() + "\n";
        str += "Latitude:" + String.valueOf(location.getLatitude()) + "\n";
        str += "Longitude:" + String.valueOf(location.getLongitude()) + "\n";
        str += "Accuracy:" + String.valueOf(location.getAccuracy()) + "\n";
        str += "Altitude:" + String.valueOf(location.getAltitude()) + "\n";
        str += "Bearing:" + String.valueOf(location.getBearing()) + "\n";
        str += "Speed:" + String.valueOf(location.getSpeed()) + "\n";

        Lat = String.valueOf(location.getLatitude());
        Lon = String.valueOf(location.getLongitude());

        text_location.setText(str);
    }

//    @Override
    public void onProviderDisabled(String provider) {
    	TextView text_provider = (TextView)findViewById(R.id.text_provider);

        Toast.makeText(this, "onProviderDisabled()", Toast.LENGTH_SHORT).show();
        text_provider.setText("provider:"+provider);
    }

//    @Override
    public void onProviderEnabled(String provider) {
        TextView text_provider = (TextView)findViewById(R.id.text_provider);

        Toast.makeText(this, "onProviderEnabled()", Toast.LENGTH_SHORT).show();
        text_provider.setText("provider:"+provider);
    }

//    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
//        TextView text_provider = (TextView)findViewById(R.id.text_provider);
        TextView text_status = (TextView)findViewById(R.id.text_status);

        //Toast.makeText(this, "onStatusChanged()", Toast.LENGTH_SHORT).show();

        text_status.setText("provider:"+provider);
        switch (status) {
            case LocationProvider.AVAILABLE:
            	text_status.setText("status:AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
            	text_status.setText("status:OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
            	text_status.setText("status:TEMPORARILY_UNAVAILABLE");
                break;
        }
    }
}