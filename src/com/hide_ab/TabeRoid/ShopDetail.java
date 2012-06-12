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
	// 検索結果店舗データオブジェクト
	protected ShopInfos shopinfos;
	// ListAdapter
	private ShopDetailAdapter shopdetailadapter = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    // 画面構成を適用
	    setContentView(R.layout.shop_detail);

		// 検索結果店舗データオブジェクト生成
	    shopinfos = (ShopInfos)this.getApplication();

	    // 呼び出し元からパラメータ取得
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

        // コメントを取得
        int ItemNum = shopInfo.ImportData();

	    // ListからShopDetailAdapterを生成
	    this.shopdetailadapter = new ShopDetailAdapter(this, R.layout.shop_detailrow, shopInfo.getComments());
    	// ShopDetailAdapterをShopDetail.xml内にあるlistview_commentsに渡して内容を表示する
    	ListView listview_comments = (ListView)findViewById(R.id.listview_comments);
    	// listview_commentにshopdetailadapterをセット
    	listview_comments.setAdapter(shopdetailadapter);
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