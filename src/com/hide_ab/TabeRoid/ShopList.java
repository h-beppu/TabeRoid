package com.hide_ab.TabeRoid;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
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
	// �t�b�^��View
	private View FooterView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    // ��ʍ\����K�p
	    setContentView(R.layout.shop_list);

	    // �^�C�g���Ɍ������ʌ�����\��
	    this.setTitle(this.getTitle() + " - (" + this.shopinfos.NumOfResult() + "��)");

    	// ShopAdapter��ShopList.xml���ɂ���listview_results�ɓn���ē��e��\������
	    ListView listview_results = (ListView)findViewById(R.id.listview_results);
	    // List����ShopAdapter�𐶐�
	    this.shoplistadapter = new ShopListAdapter(this, R.layout.shop_listrow, shopinfos.getList());
    	// listview_results�Ƀt�b�^�[��ǉ�
    	this.FooterView = getLayoutInflater().inflate(R.layout.shop_listfooter, null);
	    listview_results.addFooterView(this.FooterView);

    	// listview_results��shoplistadapter���Z�b�g
    	listview_results.setAdapter(this.shoplistadapter);
    	
    	// listview_results��OnItemClickListener��ݒ�
    	listview_results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
	    		// �t�b�^���N���b�N���ꂽ�ꍇ
	    		if(view.getId() == R.id.Footer) {
	    		    // �������ʂ̓X�܏����擾(�ǉ���)
	            	ShopListTask task = new ShopListTask();
	            	task.execute();
	    		}
	    		// �t�b�^�ȊO(�X�܏��)���N���b�N���ꂽ�ꍇ
	    		else {
	    			ShopInfo shopInfo = shopinfos.getInfo(position);
	    			if(shopInfo.getRcd() != "") {
//	    				showDialog(ShopList.this, "", "�{�^����������"+shopInfo.getRcd());
	    				Intent intent = new Intent(ShopList.this, ShopDetail.class);
	    				intent.putExtra("Position", position);
	    				startActivityForResult(intent, 0);
	    			}
	    		}
	    	}
	    }); 
	}

	// �ǉ��ǂݍ��݊���
    public void closeShopListTask(int Result) {
    	try {
			ListView listview_results = (ListView)findViewById(R.id.listview_results);

			if(Result <= 0) {
    			// listview_results����t�b�^�[���폜
    			listview_results.removeFooterView(this.FooterView);
    		}

    		// �ĕ`��
    		listview_results.invalidateViews();
    		this.shoplistadapter.notifyDataSetChanged();
        } catch (Exception e) {
			showDialog(this, "", "Error1." + e.getMessage());
        }
    }

	// �ʐ^�擾����
    public void closePhotoGetTask() {
    	// ���ʕ\�����ĕ`��
    	this.shoplistadapter.notifyDataSetChanged();
    }

	//
    // ���X�g�A�_�v�^
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
			// �r���[���󂯎��
			View view = convertView;
			// �󂯎�����r���[��null�Ȃ�V�����r���[�𐶐�
			if(view == null) {
				view = inflater.inflate(R.layout.shop_listrow, null);
			}

			// �\�����ׂ��f�[�^�̎擾
			ShopInfo shopInfo = (ShopInfo)List.get(position);
			if(shopInfo != null) {
				// �X�N���[���l�[�����r���[�ɃZ�b�g
				ImageView image_photo = (ImageView)view.findViewById(R.id.image_photo);
				if(image_photo != null) {
					Bitmap Photo = shopInfo.getPhoto();
					if(Photo == null) {
						Photo = shopinfos.getDefaultPhoto();
						// �摜�擾�^�X�N�������Ă��Ȃ����
						if(!shopInfo.getPhotoGetTask()) {
							// �o�b�N�O���E���h�ŉ摜���擾
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
    // �o�b�N�O���E���h�^�X�N
	//
	class ShopListTask extends AsyncTask<Integer, Void, Integer> {
		protected ProgressDialog progressdialog;

		@Override
		protected void onPreExecute() {
			// �o�b�N�O���E���h�̏����O��UI�X���b�h�Ń_�C�A���O�\��
			this.progressdialog = new ProgressDialog(ShopList.this);
			this.progressdialog.setMessage(getResources().getText(R.string.label_dataloading));
			this.progressdialog.setIndeterminate(true);
			this.progressdialog.show();
		}

		// �o�b�N�O���E���h�Ŏ��s���鏈��
	    @Override
	    protected Integer doInBackground(Integer... params) {
	    	int Result = shopinfos.ImportData();
	    	return(Result);
	    }

	    // ���C���X���b�h�Ŏ��s���鏈��
	    @Override  
	    protected void onPostExecute(Integer Result) {
			// �������_�C�A���O���N���[�Y
	    	this.progressdialog.dismiss();

	    	// �^�X�N����
			closeShopListTask(Result);
	    }
	}

	//
    // �o�b�N�O���E���h�^�X�N
	//
	class PhotoGetTask extends AsyncTask<String, Void, Integer> {
		private ShopInfo shopinfo;

		// �R���X�g���N�^
	    public PhotoGetTask(ShopInfo shopinfo) {
	    	this.shopinfo = shopinfo;
	    }

	    // �o�b�N�O���E���h�Ŏ��s���鏈��
	    @Override
	    protected Integer doInBackground(String... params) {
	    	// �ʐ^�擾�^�X�N�ғ���
	    	this.shopinfo.setPhotoGetTask(true);
	    	// �摜�擾
	    	this.shopinfo.ImportPhoto();
	    	return(1);
	    }

	    // ���C���X���b�h�Ŏ��s���鏈��  
	    @Override
	    protected void onPostExecute(Integer Result) {
	    	// �ʐ^�擾�^�X�N�ғ�����
	    	this.shopinfo.setPhotoGetTask(false);
	    	// �^�X�N����
			closePhotoGetTask();
	    }
	}
}