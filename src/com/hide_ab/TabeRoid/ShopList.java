package com.hide_ab.TabeRoid;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;

public class ShopList extends BaseActivity {
	// ListAdapter
	private ShopListAdapter shoplistadapter = null;
	// フッタのView
	private View FooterView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    // 画面構成を適用
	    setContentView(R.layout.shop_list);

	    // タイトルに検索結果件数を表示
	    this.setTitle(this.getTitle() + " - (" + this.shopinfos.NumOfResult() + "件)");

    	// ShopAdapterをShopList.xml内にあるlistview_resultsに渡して内容を表示する
	    ListView listview_results = (ListView)findViewById(R.id.listview_results);
	    // ListからShopAdapterを生成
	    this.shoplistadapter = new ShopListAdapter(this, R.layout.shop_listrow, shopinfos.getList());
    	// listview_resultsにフッターを追加
    	this.FooterView = getLayoutInflater().inflate(R.layout.shop_listfooter, null);
	    listview_results.addFooterView(this.FooterView);

    	// listview_resultsにshoplistadapterをセット
    	listview_results.setAdapter(this.shoplistadapter);
    	
    	// listview_resultsにOnItemClickListenerを設定
    	listview_results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
	    		// フッタをクリックされた場合
	    		if(view.getId() == R.id.Footer) {
	    		    // 検索結果の店舗情報を取得(追加分)
	            	ShopListTask task = new ShopListTask();
	            	task.execute();
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

	// 追加読み込み完了
    public void closeMore(int Num) {
    	try {
			ListView listview_results = (ListView)findViewById(R.id.listview_results);

			if(Num <= 0) {
    			// listview_resultsからフッターを削除
    			listview_results.removeFooterView(this.FooterView);
    		}

    		// 再描画
    		listview_results.invalidateViews();
    		shoplistadapter.notifyDataSetChanged();
        } catch (Exception e) {
			showDialog(this, "", "Error1."+e.getMessage());
        }
    }

    //
    //
    //
	class ShopListAdapter extends ArrayAdapter<ShopInfo> {
		private ArrayList<ShopInfo> List;
		private LayoutInflater inflater;

		@SuppressWarnings("unchecked")
		public ShopListAdapter(Context context, int textViewResourceId, ArrayList<ShopInfo> List) {
			super(context, textViewResourceId, List);
			this.List = List;
			this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// ビューを受け取る
			View view = convertView;
			// 受け取ったビューがnullなら新しくビューを生成
			if(view == null) {
				view = inflater.inflate(R.layout.shop_listrow, null);
			}

			// 表示すべきデータの取得
			ShopInfo shopInfo = (ShopInfo)List.get(position);
			if(shopInfo != null) {
				// スクリーンネームをビューにセット
				ImageView image_photo = (ImageView)view.findViewById(R.id.image_photo);
				if(image_photo != null) {
					Bitmap Photo = shopInfo.getPhoto();
					if(Photo == null) {
						Photo = shopinfos.getDefaultPhoto();
						// 画像取得タスクが走っていなければ
						if(!shopInfo.getPhotoGetTask()) {
							// バックグラウンドで画像を取得
							PhotoGetTask task = new PhotoGetTask(shopInfo);
							task.execute("");
						}
					}
					image_photo.setImageBitmap(Photo);
				}

				TextView tvRestaurantName = (TextView)view.findViewById(R.id.RestaurantName);
				if(tvRestaurantName != null) {
					tvRestaurantName.setText(shopInfo.getRestaurantName());
				}

				ImageView ivTotalScoreStar = (ImageView)view.findViewById(R.id.TotalScoreStar);
				if(ivTotalScoreStar != null) {
					ivTotalScoreStar.setImageBitmap(shopInfo.getTotalScoreStar());
				}

				TextView tvTotalScore = (TextView)view.findViewById(R.id.TotalScore);
				if(tvTotalScore != null) {
					tvTotalScore.setText("("+shopInfo.getTotalScore()+")");
				}

				TextView tvCategory = (TextView)view.findViewById(R.id.Category);
				if(tvCategory != null) {
					tvCategory.setText(shopInfo.getCategory());
				}

				TextView tvStation = (TextView)view.findViewById(R.id.Station);
				if(tvStation != null) {
					tvStation.setText(shopInfo.getStation());
				}
			}
			return view;
		}
	}

	//
    // バックグラウンドタスク
	//
	class ShopListTask extends AsyncTask<Integer, Integer, Integer> {
		protected ProgressDialog progressdialog;
		protected int Num;

		// コンストラクタ
	    public ShopListTask() {
	    	this.Num = 0;
	    }

		@Override
		protected void onPreExecute() {
			// バックグラウンドの処理前にUIスレッドでダイアログ表示
			this.progressdialog = new ProgressDialog(ShopList.this);
			this.progressdialog.setMessage(getResources().getText(R.string.label_dataloading));
			this.progressdialog.setIndeterminate(true);
			this.progressdialog.show();
		}

		// バックグラウンドで実行する処理
	    @Override
	    protected Integer doInBackground(Integer... params) {
	    	this.Num = shopinfos.ImportData();
	    	return(this.Num);
	    }

	    // メインスレッドで実行する処理
	    @Override  
	    protected void onPostExecute(Integer params) {
			// 処理中ダイアログをクローズ
	    	this.progressdialog.dismiss();

	    	// 追加読み込み完了
			closeMore(this.Num);
	    }
	}

	//
    // バックグラウンドタスク
	//
	class PhotoGetTask extends AsyncTask<String, Void, Bitmap> {
		private ShopInfo shopinfo;

		// コンストラクタ
	    public PhotoGetTask(ShopInfo shopinfo) {
	    	this.shopinfo = shopinfo;
	    	// 写真取得タスク稼働中
	    	this.shopinfo.setPhotoGetTask(true);
	    }

	    // バックグラウンドで実行する処理
	    @Override
	    protected Bitmap doInBackground(String... params) {
	    	return(this.shopinfo.ImportPhoto());
	    }

	    // メインスレッドで実行する処理  
	    @Override  
	    protected void onPostExecute(Bitmap result) {
	        // 結果表示を再描画
	    	ShopList.this.shoplistadapter.notifyDataSetChanged();
	    	// 写真取得タスク稼働完了
	    	this.shopinfo.setPhotoGetTask(false);
	    }
	}
}