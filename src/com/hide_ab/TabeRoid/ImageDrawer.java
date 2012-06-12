package com.hide_ab.TabeRoid;

import java.util.ArrayList;
import android.os.AsyncTask;
import android.graphics.Bitmap;

public class ImageDrawer extends AsyncTask<Integer, Integer, Integer> {
	// 検索結果店舗データオブジェクト
	protected ShopInfos shopinfos;
	// 結果表示のListAdapter
	private ShopListAdapter shoplistadapter;

	// コンストラクタ
    public ImageDrawer(ShopInfos shopinfosP, ShopListAdapter shoplistadapterP) {
    	this.shopinfos = shopinfosP;
    	this.shoplistadapter = shoplistadapterP;
    }

    // バックグラウンドで実行する処理   
    @Override
    protected Integer doInBackground(Integer... params) {
    	ShopInfo shopinfo;
    	Bitmap Photo;
    	
    	ArrayList<ShopInfo> List = this.shopinfos.getList();
        for(int i = 0; i < List.size(); i++) {
            shopinfo = List.get(i);
            // 画像が未取得なら
            if(!shopinfo.getPhotoFlg()) {
                // 画像の取得
            	Photo = shopinfo.ImportPhoto();
            	shopinfo.setPhoto(Photo);

            	publishProgress(0);
            }
        }
        return(0);
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        // 結果表示を再描画
    	this.shoplistadapter.notifyDataSetChanged();
    }

    // メインスレッドで実行する処理   
    @Override  
    protected void onPostExecute(Integer params) {   
        // 結果表示を再描画
    	this.shoplistadapter.notifyDataSetChanged();
    }   
}