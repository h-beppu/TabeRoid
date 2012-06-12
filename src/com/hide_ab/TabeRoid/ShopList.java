package com.hide_ab.TabeRoid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.ListView;

public class ShopList extends Activity {
	// 検索結果店舗データオブジェクト
	protected ShopInfos shopinfos;
	// ListAdapter
	private ShopListAdapter shoplistadapter = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    // 画面構成を適用
	    setContentView(R.layout.shop_list);

		// 検索結果店舗データオブジェクト生成
	    this.shopinfos = (ShopInfos)this.getApplication();

        // 呼び出し元からパラメータ取得
        Intent intent = getIntent();
        String SearchKey = intent.getStringExtra("Key");
        String Lat = null;
        String Lon = null;
        String Stations = null;

        if(SearchKey.equals("gps")) {
            Lat = intent.getStringExtra("Lat");
            Lon = intent.getStringExtra("Lon");
        } else {
            Stations = intent.getStringExtra("Stations");
        }

        // 検索結果の店舗情報を取得
	    int ItemNum = this.shopinfos.ImportData(SearchKey, Lat, Lon, Stations);

	    TextView text_result = (TextView)findViewById(R.id.text_result);
	    text_result.setText(ItemNum + "件見つかりました");

	    // ListからShopAdapterを生成
	    this.shoplistadapter = new ShopListAdapter(this, R.layout.shop_listrow, shopinfos.getList());

		// 画像はバックグラウンドで取得
		ImageDrawer imagedrawer = new ImageDrawer(shopinfos, shoplistadapter);
		imagedrawer.execute();

    	// ShopAdapterをShopList.xml内にあるlistview_resultsに渡して内容を表示する
    	ListView listview_results = (ListView)findViewById(R.id.listview_results);
    	// listview_resultsにフッターを追加
    	listview_results.addFooterView(getLayoutInflater().inflate(R.layout.shop_listfooter, null), null, true);
    	// listview_resultsにshoplistadapterをセット
    	listview_results.setAdapter(shoplistadapter);

    	// listview_resultsにOnItemClickListenerを設定
    	listview_results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
	    		// フッタをクリックされた場合
	    		if(view.getId() == R.id.Footer) {
	    		    // 検索結果の店舗情報を取得(追加分)
	    			shopinfos.ImportData();

	    			// 画像はバックグラウンドで取得
	    			ImageDrawer imagedrawer = new ImageDrawer(shopinfos, shoplistadapter);   
	    			imagedrawer.execute();

	    	    	// listview_resultsからフッターを削除
	    	    	ListView listview_results = (ListView)findViewById(R.id.listview_results);
	    	    	listview_results.removeFooterView(getLayoutInflater().inflate(R.layout.shop_listfooter, null));

	    	    	// 再描画
	    	    	listview_results.invalidateViews();
	    			shoplistadapter.notifyDataSetChanged();
	    		}
	    		// フッタ以外(店舗情報)をクリックされた場合
	    		else {
	    			ShopInfo shopInfo = shopinfos.getInfo(position);
	    			if(shopInfo.getRcd() != "") {
//	    				showDialog(ShopList.this, "", "ボタンを押した"+shopInfo.getRcd());
	    				Intent intent = new Intent(ShopList.this, ShopDetail.class);
	    				intent.putExtra("Position", position);
	    				startActivityForResult(intent, 0);
	    			}
	    		}
	    	}
	    }); 
	}

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
}