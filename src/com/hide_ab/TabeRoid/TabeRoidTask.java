package com.hide_ab.TabeRoid;

import android.os.AsyncTask;
import android.widget.TextView;
import android.app.ProgressDialog; 
import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class TabeRoidTask extends AsyncTask<Integer, Integer, Integer> {
	// 検索結果店舗データオブジェクト
	protected ShopInfos shopinfos;
	// アクティビティ
	protected TabeRoid taberoid;
	protected ProgressDialog progressdialog;
	protected int Num;

	// コンストラクタ
    public TabeRoidTask(ShopInfos shopinfos, TabeRoid taberoid) {
    	this.shopinfos = shopinfos;
    	this.taberoid = taberoid;

    	// デフォルト写真の設定
		Resources r = this.taberoid.getResources();
    	this.shopinfos.DefaultPhoto = BitmapFactory.decodeResource(r, R.drawable.icon);
    }

	@Override
	protected void onPreExecute() {
    	// バックグラウンドの処理前にUIスレッドでダイアログ表示
		progressdialog = new ProgressDialog(this.taberoid);
		progressdialog.setMessage(this.taberoid.getResources().getText(R.string.label_dataloading));
		progressdialog.setIndeterminate(true);
		progressdialog.show();
	}

	// バックグラウンドで実行する処理
    @Override
    protected Integer doInBackground(Integer... params) {
    	this.Num = this.shopinfos.ImportData();
    	return(this.Num);
    }

    // メインスレッドで実行する処理
    @Override
    protected void onPostExecute(Integer params) {
		// 処理中ダイアログをクローズ
    	progressdialog.dismiss();

    	TextView locationText = (TextView)this.taberoid.findViewById(R.id.text_location);
        locationText.setText("Click" + this.Num);

		// "ShopList"画面に移行
		this.taberoid.openShopList();
    }
}