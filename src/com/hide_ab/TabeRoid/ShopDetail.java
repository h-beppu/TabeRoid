package com.hide_ab.TabeRoid;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

public class ShopDetail extends MapActivity {
	// 検索結果店舗データオブジェクト
	protected ShopInfos shopinfos;

	// ListAdapter
	private ShopDetailAdapter shopdetailadapter = null;
	protected int position;
	protected String Lat;
	protected String Lon;
	protected Resources r;
    private MapView map;
    private Bitmap Marker;
    private MapController controller;
    private List<Overlay> overlays;
    private IconOverlay overlay;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
    	// 検索結果店舗データオブジェクトの取得
	    this.shopinfos = (ShopInfos)this.getApplication();

		// 画面構成を適用
	    setContentView(R.layout.shop_detail);

		this.r = getResources();
        this.Marker = BitmapFactory.decodeResource(this.r, R.drawable.icon);

        // TabHostオブジェクト取得   
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);       
        tabHost.setup();   
        // タブ設定
	    tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(getResources().getString(R.string.label_tab1)).setContent(R.id.view1));
	    tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(getResources().getString(R.string.label_tab2)).setContent(R.id.view2));
	    tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(getResources().getString(R.string.label_tab3)).setContent(R.id.view3));

	    // 地図初期化
	    this.map = (MapView)findViewById(R.id.map);
	    this.map.setEnabled(true);
	    this.map.setBuiltInZoomControls(true);
	    this.controller = this.map.getController();
        this.overlays = this.map.getOverlays();
        this.overlay = new IconOverlay(this.Marker);

	    // 初期表示設定
        tabHost.setCurrentTab(0);

        // 『ナビ』ボタンクリックハンドラ
        ImageView image_navi1 = (ImageView)findViewById(R.id.image_navi1);
        image_navi1.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
			    // 前のデータの表示
			    MakeView(-1);
        	}
        });
        ImageView image_navi2 = (ImageView)findViewById(R.id.image_navi2);
        image_navi2.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
				// 次のデータの表示
			    MakeView(1);
        	}
        });

        // 呼び出し元からパラメータ取得
        Intent intent = getIntent();
        this.position = intent.getIntExtra("Position", 0);

        // データの表示
        this.MakeView(0);
	}

	// 指定データの表示
	private void MakeView(int vector) {
		// データポインタを前に
		if(vector < 0) {
			this.position--;
			if(this.position < 0) {
				this.position = 0;
				return;
			}
		}
		// データポインタを次に
		else if(vector > 0) {
			this.position++;
			if(this.position > shopinfos.getNumOfItem() - 1) {
				this.position = shopinfos.getNumOfItem() - 1;
				return;
			}
		}

		ShopInfo shopInfo = this.shopinfos.getInfo(this.position);

        TextView tvRestaurantName = (TextView)findViewById(R.id.RestaurantName);
        tvRestaurantName.setText(shopInfo.getRestaurantName());
        TextView tvRestaurantName2 = (TextView)findViewById(R.id.RestaurantName2);
        tvRestaurantName2.setText(shopInfo.getRestaurantName());
        TextView tvRestaurantName3 = (TextView)findViewById(R.id.RestaurantName3);
        tvRestaurantName3.setText(shopInfo.getRestaurantName());
/*
        TextView tvTabelogUrl = (TextView)findViewById(R.id.TabelogUrl);
        tvTabelogUrl.setText(shopInfo.getTabelogUrl());

        TextView tvTabelogMobileUrl = (TextView)findViewById(R.id.TabelogMobileUrl);
        tvTabelogMobileUrl.setText(shopInfo.getTabelogMobileUrl());
*/
        TextView tvTotalScore = (TextView)findViewById(R.id.TotalScore);
        tvTotalScore.setText(shopInfo.getTotalScore());

		ImageView ivTotalScoreStar = (ImageView)findViewById(R.id.TotalScoreStar);
		if(ivTotalScoreStar != null) {
			ivTotalScoreStar.setImageBitmap(shopInfo.getTotalScoreStar());
		}

		TextView tvTasteScore = (TextView)findViewById(R.id.TasteScore);
        tvTasteScore.setText(shopInfo.getTasteScore());

		ImageView ivTasteScoreStar = (ImageView)findViewById(R.id.TasteScoreStar);
		if(ivTasteScoreStar != null) {
			ivTasteScoreStar.setImageBitmap(shopInfo.getTasteScoreStar());
		}

        TextView tvServiceScore = (TextView)findViewById(R.id.ServiceScore);
        tvServiceScore.setText(shopInfo.getServiceScore());

		ImageView ivServiceScoreStar = (ImageView)findViewById(R.id.ServiceScoreStar);
		if(ivServiceScoreStar != null) {
			ivServiceScoreStar.setImageBitmap(shopInfo.getServiceScoreStar());
		}

		TextView tvMoodScore = (TextView)findViewById(R.id.MoodScore);
        tvMoodScore.setText(shopInfo.getMoodScore());

		ImageView ivMoodScoreStar = (ImageView)findViewById(R.id.MoodScoreStar);
		if(ivMoodScoreStar != null) {
			ivMoodScoreStar.setImageBitmap(shopInfo.getTasteScoreStar());
		}

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

		ImageView ivPhoto1 = (ImageView)findViewById(R.id.Photo);
		ImageView ivPhoto2 = (ImageView)findViewById(R.id.Photo2);
		ImageView ivPhoto3 = (ImageView)findViewById(R.id.Photo3);
        if(shopInfo.getPhoto() != null) {
			ivPhoto1.setImageBitmap(shopInfo.getPhoto());
			ivPhoto2.setImageBitmap(shopInfo.getPhoto());
			ivPhoto3.setImageBitmap(shopInfo.getPhoto());
		}
        else {
			ivPhoto1.setImageBitmap(BitmapFactory.decodeResource(this.r, R.drawable.icon));
			ivPhoto2.setImageBitmap(BitmapFactory.decodeResource(this.r, R.drawable.icon));
			ivPhoto3.setImageBitmap(BitmapFactory.decodeResource(this.r, R.drawable.icon));
        }

        // 口コミを一旦クリア
        closeReviewGetTask(null);

    	// 口コミ取得タスクが走っていなければ
		if(!shopInfo.getReviewGetTask()) {
			// バックグラウンドで口コミを取得
			ReviewGetTask task = new ReviewGetTask(shopInfo);
			task.execute();
		}

        this.Lat = shopInfo.getLat();
        this.Lon = shopInfo.getLon();

        // 『地図で見る』ボタン取得
        Button button_share_map = (Button)findViewById(R.id.button_share_map);

        // 『地図で見る』ボタンクリックハンドラ
        button_share_map.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + Lat + "," + Lon + "&zoom=18"));
        		startActivity(intent);
        	}
        });

        // 地図の制御
        double dLat = new Double(this.Lat);
        double dLon = new Double(this.Lon);
        GeoPoint point = new GeoPoint(new Double(dLat * 1E6).intValue(), new Double(dLon * 1E6).intValue());
        this.controller.setCenter(point);
        this.controller.setZoom(16);

        // 生成したOverlayクラスを追加する
        this.overlay.setPoint(point);
        this.overlays.clear();
        this.overlays.add(this.overlay);
	}
	
    // 口コミ取得完了
    public void closeReviewGetTask(ShopInfo shopinfo) {
    	if(shopinfo != null) {
    		ShopInfo shopInfo = this.shopinfos.getInfo(this.position);
    		// 表示中のデータでなければ無処理
    		if(shopInfo.getRcd() != shopinfo.getRcd()) {
    			return;
    		}
    		// ListからShopDetailAdapterを生成
    		this.shopdetailadapter = new ShopDetailAdapter(this, R.layout.shop_detailrow, shopinfo.getReviews());
    	}
    	else {
    		// クリア
    	    this.shopdetailadapter = new ShopDetailAdapter(this, R.layout.shop_detailrow, new ArrayList<ReviewInfo>());
    	}
    	// ShopDetailAdapterをShopDetail.xml内にあるlistview_reviewsに渡して内容を表示する
    	ListView listview_reviews = (ListView)findViewById(R.id.listview_reviews);
    	// listview_reviewsにshopdetailadapterをセット
    	listview_reviews.setAdapter(shopdetailadapter);
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	// 地図上に表示されるオーバーレイ
	private class IconOverlay extends Overlay {
		// 描画するアイコン
		private Bitmap Marker;
		private int mOffsetX;
		private int mOffsetY;
		// アイコンを表示する位置
		private GeoPoint mPoint;

		// コンストラクタ
		public IconOverlay(Bitmap Marker) {
			// アイコンと、アイコンの中心のオフセット
			this.Marker = Marker;
			this.mOffsetX = 0 - Marker.getWidth() / 2;
			this.mOffsetY = 0 - Marker.getHeight() / 2;
		}

		// 地図の描画時に、shadow=true, shadow=falseと2回呼び出される
		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, shadow);
			if(!shadow) {
				// 地図上の場所と、描画用のCanvasの座標の変換
				Projection projection = mapView.getProjection();
				Point point = new Point();
				projection.toPixels(this.mPoint, point);
				point.offset(this.mOffsetX, this.mOffsetY);
				// アイコンを描画
				canvas.drawBitmap(this.Marker, point.x, point.y, null);
			}
		}

		public void setPoint(GeoPoint Point) {
			this.mPoint = Point;
		}
	};

	//
    // リストアダプタ
    //
	class ShopDetailAdapter extends ArrayAdapter<ReviewInfo> {
		private ArrayList<ReviewInfo> List;
		private LayoutInflater inflater;
		private TextView text_review_more;

		@SuppressWarnings("unchecked")
		public ShopDetailAdapter(Context context, int textViewResourceId, ArrayList<ReviewInfo> List) {
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
				view = inflater.inflate(R.layout.shop_detailrow, null);
			}

			// 表示すべきデータの取得
			final ReviewInfo reviewInfo = (ReviewInfo)List.get(position);
			if(reviewInfo != null) {
				// スクリーンネームをビューにセット
				TextView tvNickName = (TextView)view.findViewById(R.id.NickName);
				tvNickName.setTypeface(Typeface.DEFAULT_BOLD);
				if(tvNickName != null) {
					tvNickName.setText(reviewInfo.getNickName());
				}
				TextView tvVisitDate = (TextView)view.findViewById(R.id.VisitDate);
				tvVisitDate.setTypeface(Typeface.DEFAULT_BOLD);
				if(tvVisitDate != null) {
					tvVisitDate.setText(reviewInfo.getVisitDate());
				}

				TextView tvReviewDate = (TextView)view.findViewById(R.id.ReviewDate);
				tvReviewDate.setTypeface(Typeface.DEFAULT_BOLD);
				if(tvReviewDate != null) {
					tvReviewDate.setText(reviewInfo.getReviewDate());
				}

				TextView tvUseType = (TextView)view.findViewById(R.id.UseType);
				tvUseType.setTypeface(Typeface.DEFAULT_BOLD);
				if(tvUseType != null) {
					tvUseType.setText(reviewInfo.getUseType());
				}

				TextView tvSituations = (TextView)view.findViewById(R.id.Situations);
				tvSituations.setTypeface(Typeface.DEFAULT_BOLD);
				if(tvSituations != null) {
					tvSituations.setText(reviewInfo.getSituations());
				}
				TextView tvTotalScore = (TextView)view.findViewById(R.id.TotalScore);
				tvTotalScore.setTypeface(Typeface.DEFAULT_BOLD);
				if(tvTotalScore != null) {
					tvTotalScore.setText(reviewInfo.getTotalScore());
				}
				ImageView ivTotalScoreStar = (ImageView)view.findViewById(R.id.TotalScoreStar);
				if(ivTotalScoreStar != null) {
					ivTotalScoreStar.setImageBitmap(reviewInfo.getTotalScoreStar());
				}

				TextView tvTasteScore = (TextView)view.findViewById(R.id.TasteScore);
				tvTasteScore.setTypeface(Typeface.DEFAULT_BOLD);
				if(tvTasteScore != null) {
					tvTasteScore.setText(reviewInfo.getTasteScore());
				}
				ImageView ivTasteScoreStar = (ImageView)view.findViewById(R.id.TasteScoreStar);
				if(ivTasteScoreStar != null) {
					ivTasteScoreStar.setImageBitmap(reviewInfo.getTasteScoreStar());
				}

				TextView tvServiceScore = (TextView)view.findViewById(R.id.ServiceScore);
				tvServiceScore.setTypeface(Typeface.DEFAULT_BOLD);
				if(tvServiceScore != null) {
					tvServiceScore.setText(reviewInfo.getServiceScore());
				}
				ImageView ivServiceScoreStar = (ImageView)view.findViewById(R.id.ServiceScoreStar);
				if(ivServiceScoreStar != null) {
					ivServiceScoreStar.setImageBitmap(reviewInfo.getServiceScoreStar());
				}

				TextView tvMoodScore = (TextView)view.findViewById(R.id.MoodScore);
				tvMoodScore.setTypeface(Typeface.DEFAULT_BOLD);
				if(tvMoodScore != null) {
					tvMoodScore.setText(reviewInfo.getMoodScore());
				}
				ImageView ivMoodScoreStar = (ImageView)view.findViewById(R.id.MoodScoreStar);
				if(ivMoodScoreStar != null) {
					ivMoodScoreStar.setImageBitmap(reviewInfo.getMoodScoreStar());
				}

				TextView tvDinnerPrice = (TextView)view.findViewById(R.id.DinnerPrice);
				tvDinnerPrice.setTypeface(Typeface.DEFAULT_BOLD);
				if(tvDinnerPrice != null) {
					tvDinnerPrice.setText(reviewInfo.getDinnerPrice());
				}

				TextView tvLunchPrice = (TextView)view.findViewById(R.id.LunchPrice);
				tvLunchPrice.setTypeface(Typeface.DEFAULT_BOLD);
				if(tvLunchPrice != null) {
					tvLunchPrice.setText(reviewInfo.getLunchPrice());
				}

				TextView tvTitle = (TextView)view.findViewById(R.id.Title);
				tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
				if(tvTitle != null) {
					tvTitle.setText(reviewInfo.getTitle());
				}

				TextView tvComment = (TextView)view.findViewById(R.id.Comment);
				tvComment.setTypeface(Typeface.DEFAULT_BOLD);
				if(tvComment != null) {
					tvComment.setText(reviewInfo.getComment());
				}
/*
				TextView tvPcSiteUrl = (TextView)view.findViewById(R.id.PcSiteUrl);
				tvPcSiteUrl.setTypeface(Typeface.DEFAULT_BOLD);
				if(tvPcSiteUrl != null) {
					tvPcSiteUrl.setText(reviewInfo.getPcSiteUrl());
				}

				TextView tvMobileSiteUrl = (TextView)view.findViewById(R.id.MobileSiteUrl);
				tvMobileSiteUrl.setTypeface(Typeface.DEFAULT_BOLD);
				if(tvMobileSiteUrl != null) {
					tvMobileSiteUrl.setText(reviewInfo.getMobileSiteUrl());
				}
*/

		        // 『もっと読む』ボタン取得
		        text_review_more = (TextView)view.findViewById(R.id.text_review_more);

		        // 『もっと読む』ボタンクリックハンドラ
		        text_review_more.setOnClickListener(new View.OnClickListener() {
		        	public void onClick(View view) {
		        		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewInfo.getPcSiteUrl()));
		        		startActivity(intent);
		        	}
		        });
			}
			return view;
		}
	}

	//
    // バックグラウンドタスク
	//
	class ReviewGetTask extends AsyncTask<Integer, Void, Integer> {
		private ShopInfo shopinfo;

		// コンストラクタ
	    public ReviewGetTask(ShopInfo shopinfo) {
	    	this.shopinfo = shopinfo;
	    }

		// バックグラウンドで実行する処理
	    @Override
	    protected Integer doInBackground(Integer... params) {
	    	// 口コミ取得タスク稼働中
	    	this.shopinfo.setReviewGetTask(true);
	    	int Result = this.shopinfo.ImportReview();
	    	return(Result);
	    }

	    // メインスレッドで実行する処理
	    @Override
	    protected void onPostExecute(Integer Result) {
	    	// 写真取得タスク稼働完了
	    	this.shopinfo.setReviewGetTask(false);
	    	// タスク完了
			closeReviewGetTask(this.shopinfo);
	    }
	}
}