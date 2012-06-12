package com.hide_ab.TabeRoid;

import android.os.AsyncTask;
import android.app.ProgressDialog; 

public class ShopDetailTask extends AsyncTask<Integer, Integer, Integer> {
	// 店舗データオブジェクト
	protected ShopInfo shopinfo;
	// アクティビティ
	protected ShopDetail shopdetail;
	// 待機ダイアログ
	protected ProgressDialog progressdialog;
	protected int Num;

	// コンストラクタ
    public ShopDetailTask(ShopInfo shopinfo, ShopDetail shopdetail) {
    	this.shopinfo = shopinfo;
    	this.shopdetail = shopdetail;
    }

	@Override
	protected void onPreExecute() {
/*
		// バックグラウンドの処理前にUIスレッドでダイアログ表示
		progressdialog = new ProgressDialog(this.shopdetail);
		progressdialog.setMessage(this.shopdetail.getResources().getText(R.string.label_dataloading));
		progressdialog.setIndeterminate(true);
		progressdialog.show();
*/
	}

	// バックグラウンドで実行する処理
    @Override
    protected Integer doInBackground(Integer... params) {
    	this.Num = this.shopinfo.ImportData();
    	return(this.Num);
    }

    // メインスレッドで実行する処理
    @Override
    protected void onPostExecute(Integer params) {
		// 処理中ダイアログをクローズ
//    	progressdialog.dismiss();

		// "ShopList"画面に移行
		this.shopdetail.closeReview(this.shopinfo);
    }
}