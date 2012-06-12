package com.hide_ab.TabeRoid;

import android.os.AsyncTask;
import android.app.ProgressDialog;

public class ShopListTask extends AsyncTask<Integer, Integer, Integer> {
	// 検索結果店舗データオブジェクト
	protected ShopInfos shopinfos;
	// アクティビティ
	protected ShopList shoplist;
	protected ProgressDialog progressdialog;
	protected int Num;

	// コンストラクタ
    public ShopListTask(ShopInfos shopinfosP, ShopList shoplistP) {
    	this.shopinfos = shopinfosP;
    	this.shoplist = shoplistP;
    }

	@Override
	protected void onPreExecute() {
/*
		// バックグラウンドの処理前にUIスレッドでダイアログ表示
		progressdialog = new ProgressDialog(this.shoplist);
		progressdialog.setMessage(this.shoplist.getResources().getText(R.string.label_dataloading));
		progressdialog.setIndeterminate(true);
		progressdialog.show();
*/
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
//    	progressdialog.dismiss();

    	// 追加読み込み完了
		this.shoplist.closeMore();
    }
}