package com.hide_ab.TabeRoid;

import java.util.Date;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.EditText;

public class TabeRoid extends BaseActivity implements LocationListener {
	// 検索条件
	private LocationManager mLm;
    private String Lat = "35.70209";
    private String Lon = "139.73744";

    private EditText edittext_station;
    private Button button_search_gps_large;
    private Button button_search_gps_medium;
    private Button button_search_gps_small;
    private Button button_search_station;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 画面構成を適用
        setContentView(R.layout.taberoid);

		// 画像素材の設定
		Resources r = getResources();
		this.shopinfos.setDefaultPhoto(BitmapFactory.decodeResource(r, R.drawable.icon));
		this.shopinfos.setStar(BitmapFactory.decodeResource(r, R.drawable.star_back),
								BitmapFactory.decodeResource(r, R.drawable.star_front));

		// GPS初期化
		this.mLm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        // 『最寄駅』エディットテキスト取得
		this.edittext_station = (EditText)findViewById(R.id.edittext_station);
        // 『検索』ボタン取得
		this.button_search_gps_large  = (Button)findViewById(R.id.button_search_gps_large);
		this.button_search_gps_medium = (Button)findViewById(R.id.button_search_gps_medium);
		this.button_search_gps_small  = (Button)findViewById(R.id.button_search_gps_small);
		this.button_search_station    = (Button)findViewById(R.id.button_search_station);

		// 『検索』ボタンクリックハンドラ
		this.button_search_gps_large.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		// 検索実行
        		openTabeRoidTask("gps", Lat, Lon, "large", "");
        	}
        });
		this.button_search_gps_medium.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		// 検索実行
        		openTabeRoidTask("gps", Lat, Lon, "medium", "");
        	}
        });
		this.button_search_gps_small.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		// 検索実行
        		openTabeRoidTask("gps", Lat, Lon, "small", "");
        	}
        });
		this.button_search_station.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		// 入力された『最寄駅』を取得
        		String Station = edittext_station.getText().toString();
        		// 検索実行
        		openTabeRoidTask("station", "", "", "", Station);
        	}
        });
    }

    @Override
    public void onResume() {
        super.onResume();

		// GPS開始
		this.mLm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
//      this.mLm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);

		// GPS検索ボタンを無効化
		this.button_search_gps_large.setEnabled(false);
		this.button_search_gps_medium.setEnabled(false);
		this.button_search_gps_small.setEnabled(false);
    }

	// 検索タスク実行
    public void openTabeRoidTask(String SearchKey, String Lat, String Lon, String SearchRange, String Station) {
		// 検索条件設定
		shopinfos.preSearch(SearchKey, Lat, Lon, SearchRange, Station);
		// 情報はバックグラウンドで取得
    	TabeRoidTask task = new TabeRoidTask();
    	task.execute();
    }

	// 検索タスク完了
    public void closeTabeRoidTask(int Result) {
        // 発見時
    	if(Result > 0) {
    		// GPSの更新を停止
    		mLm.removeUpdates(TabeRoid.this);

    		// "ShopList"画面に移行
    		Intent intent = new Intent(TabeRoid.this, ShopList.class);
    		startActivityForResult(intent, 0);
    	}
        // 非発見時
    	else {
            Toast.makeText(this, getResources().getText(R.string.msg_notfound), Toast.LENGTH_SHORT).show();
    	}
    }

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

        // GPS検索ボタンを有効化
        button_search_gps_large.setEnabled(true);
        button_search_gps_medium.setEnabled(true);
        button_search_gps_small.setEnabled(true);

        text_location.setText(str);
    }

    public void onProviderDisabled(String provider) {
    	TextView text_provider = (TextView)findViewById(R.id.text_provider);

        Toast.makeText(this, "onProviderDisabled()", Toast.LENGTH_SHORT).show();
        text_provider.setText("provider:"+provider);
    }

    public void onProviderEnabled(String provider) {
        TextView text_provider = (TextView)findViewById(R.id.text_provider);

        Toast.makeText(this, "onProviderEnabled()", Toast.LENGTH_SHORT).show();
        text_provider.setText("provider:"+provider);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
//      TextView text_provider = (TextView)findViewById(R.id.text_provider);
        TextView text_status = (TextView)findViewById(R.id.text_status);

//		Toast.makeText(this, "onStatusChanged()", Toast.LENGTH_SHORT).show();

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

    //
    // バックグラウンドタスク
    //
	class TabeRoidTask extends AsyncTask<Integer, Void, Integer> {
		protected ProgressDialog progressdialog;

		@Override
		protected void onPreExecute() {
	    	// バックグラウンドの処理前にUIスレッドでダイアログ表示
			this.progressdialog = new ProgressDialog(TabeRoid.this);
			this.progressdialog.setMessage(getResources().getText(R.string.label_dataloading));
			this.progressdialog.setIndeterminate(true);
			this.progressdialog.show();
		}

		// バックグラウンドで実行する処理
	    @Override
	    protected Integer doInBackground(Integer... params) {
	    	int Result = shopinfos.ImportData();
	    	return(Result);
	    }

	    // メインスレッドで実行する処理
	    @Override
	    protected void onPostExecute(Integer Result) {
			// 処理中ダイアログをクローズ
	    	this.progressdialog.dismiss();

	    	TextView text_location = (TextView)findViewById(R.id.text_location);
	    	text_location.setText("Click" + Result);

			// タスク完了
	    	closeTabeRoidTask(Result);
	    }
	}
}