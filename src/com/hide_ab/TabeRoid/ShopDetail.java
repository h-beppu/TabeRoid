package com.hide_ab.TabeRoid;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class ShopDetail extends TabActivity {
	// �������ʓX�܃f�[�^�I�u�W�F�N�g
	protected ShopInfos shopinfos;
	// ListAdapter
	private ShopDetailAdapter shopdetailadapter = null;
	protected String Lat;
	protected String Lon;
	protected int position;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    // ��ʍ\����K�p
	    TabHost tabHost = getTabHost();
	    LayoutInflater.from(this).inflate(R.layout.shop_detail, tabHost.getTabContentView(), true);
	    tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(getResources().getString(R.string.label_tab1)).setContent(R.id.view1));
	    tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(getResources().getString(R.string.label_tab2)).setContent(R.id.view2));
	    tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(getResources().getString(R.string.label_tab3)).setContent(R.id.view3));

		// �������ʓX�܃f�[�^�I�u�W�F�N�g����
	    shopinfos = (ShopInfos)this.getApplication();

        // �w�i�r�x�{�^���N���b�N�n���h��
        ImageView image_navi1 = (ImageView)findViewById(R.id.image_navi1);
        image_navi1.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
				position--;
				if(position < 0) {
					position = 0;
				}
				else {
			        // �w��f�[�^�̕\��
			        MakeView();
				}
        	}
        });
        ImageView image_navi2 = (ImageView)findViewById(R.id.image_navi2);
        image_navi2.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
				position++;
		        // �w��f�[�^�̕\��
		        MakeView();
        	}
        });

        // �Ăяo��������p�����[�^�擾
        Intent intent = getIntent();
        this.position = intent.getIntExtra("Position", 0);

        // �w��f�[�^�̕\��
        this.MakeView();
	}

	// �w��f�[�^�̕\��
	private void MakeView() {
        ShopInfo shopInfo = shopinfos.getInfo(this.position);

        TextView tvRestaurantName = (TextView)findViewById(R.id.RestaurantName);
        tvRestaurantName.setText(shopInfo.getRestaurantName());
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

        if(shopInfo.getPhoto() != null) {
			ImageView ivPhoto = (ImageView)findViewById(R.id.Photo);
			ivPhoto.setImageBitmap(shopInfo.getPhoto());
		}

        //
        TextView tvRestaurantName2 = (TextView)findViewById(R.id.RestaurantName2);
        tvRestaurantName2.setText(shopInfo.getRestaurantName());

        if(shopInfo.getPhoto() != null) {
			ImageView ivPhoto2 = (ImageView)findViewById(R.id.Photo2);
			ivPhoto2.setImageBitmap(shopInfo.getPhoto());
		}

        // �R�����g���擾
    	ShopDetailTask task = new ShopDetailTask(shopInfo);
    	task.execute();

        this.Lat = shopInfo.getLat();
        this.Lon = shopInfo.getLon();

        // �w�n�}�Ō���x�{�^���擾
        Button button_share_map = (Button)findViewById(R.id.button_share_map);

        // �w�n�}�Ō���x�{�^���N���b�N�n���h��
        button_share_map.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + Lat + "," + Lon + "&zoom=18"));
        		startActivity(intent);
        	}
        });
	}
	
    // �R�����g�擾����
    public void closeReview(ShopInfo shopinfo) {
    	// List����ShopDetailAdapter�𐶐�
	    this.shopdetailadapter = new ShopDetailAdapter(this, R.layout.shop_detailrow, shopinfo.getReviews());
    	// ShopDetailAdapter��ShopDetail.xml���ɂ���listview_reviews�ɓn���ē��e��\������
    	ListView listview_reviews = (ListView)findViewById(R.id.listview_reviews);
    	// listview_reviews��shopdetailadapter���Z�b�g
    	listview_reviews.setAdapter(shopdetailadapter);
    }

	// �_�C�A���O�̕\��
    protected void showDialog(final TabActivity activity, String title, String text) {
    	AlertDialog.Builder ad = new AlertDialog.Builder(activity);
    	ad.setTitle(title);
    	ad.setMessage(text);
    	ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int wichiButton) {
    			activity.setResult(TabActivity.RESULT_OK);
    		}
    	});
    	ad.create();
    	ad.show();
    }

    //
    //
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
			// �r���[���󂯎��
			View view = convertView;
			// �󂯎�����r���[��null�Ȃ�V�����r���[�𐶐�
			if(view == null) {
				view = inflater.inflate(R.layout.shop_detailrow, null);
			}

			// �\�����ׂ��f�[�^�̎擾
			final ReviewInfo reviewInfo = (ReviewInfo)List.get(position);
			if(reviewInfo != null) {
				// �X�N���[���l�[�����r���[�ɃZ�b�g
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

		        // �w�����Ɠǂށx�{�^���擾
		        text_review_more = (TextView)view.findViewById(R.id.text_review_more);

		        // �w�����Ɠǂށx�{�^���N���b�N�n���h��
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
	//
	//
	class ShopDetailTask extends AsyncTask<Integer, Integer, Integer> {
		// �X�܃f�[�^�I�u�W�F�N�g
		protected ShopInfo shopinfo;
		// �ҋ@�_�C�A���O
		protected ProgressDialog progressdialog;
		protected int Num;

		// �R���X�g���N�^
	    public ShopDetailTask(ShopInfo shopinfo) {
	    	this.shopinfo = shopinfo;
	    }

		@Override
		protected void onPreExecute() {
/*
			// �o�b�N�O���E���h�̏����O��UI�X���b�h�Ń_�C�A���O�\��
			progressdialog = new ProgressDialog(this.shopdetail);
			progressdialog.setMessage(this.shopdetail.getResources().getText(R.string.label_dataloading));
			progressdialog.setIndeterminate(true);
			progressdialog.show();
*/
		}

		// �o�b�N�O���E���h�Ŏ��s���鏈��
	    @Override
	    protected Integer doInBackground(Integer... params) {
	    	this.Num = this.shopinfo.ImportData();
	    	return(this.Num);
	    }

	    // ���C���X���b�h�Ŏ��s���鏈��
	    @Override
	    protected void onPostExecute(Integer params) {
			// �������_�C�A���O���N���[�Y
//	    	progressdialog.dismiss();

			// "ShopList"��ʂɈڍs
			closeReview(this.shopinfo);
	    }
	}
}