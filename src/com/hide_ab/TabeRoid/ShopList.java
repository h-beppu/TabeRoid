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

public class ShopList extends Activity {
	// �������ʓX�܃f�[�^�I�u�W�F�N�g
	protected ShopInfos shopinfos;
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

		// �������ʓX�܃f�[�^�I�u�W�F�N�g����
	    this.shopinfos = (ShopInfos)this.getApplication();

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
    	
		// �摜�̓o�b�N�O���E���h�Ŏ擾
		ImageDrawer imagedrawer = new ImageDrawer();
		imagedrawer.execute();

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
    public void closeMore(int Num) {
    	try {
    		// �摜�̓o�b�N�O���E���h�Ŏ擾
    		ImageDrawer imagedrawer = new ImageDrawer();   
    		imagedrawer.execute();

			ListView listview_results = (ListView)findViewById(R.id.listview_results);

			if(Num <= 0) {
    			// listview_results����t�b�^�[���폜
    			listview_results.removeFooterView(this.FooterView);
    		}

    		// �ĕ`��
    		listview_results.invalidateViews();
    		shoplistadapter.notifyDataSetChanged();
        } catch (Exception e) {
			showDialog(this, "", "Error1."+e.getMessage());
        }
    }

    // �_�C�A���O�̕\��
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
					image_photo.setImageBitmap(shopInfo.getPhoto());
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
    //
    //
	class ImageDrawer extends AsyncTask<Integer, Integer, Integer> {
		// �R���X�g���N�^
	    public ImageDrawer() {
	    	;
	    }

	    // �o�b�N�O���E���h�Ŏ��s���鏈��
	    @Override
	    protected Integer doInBackground(Integer... params) {
	    	ShopInfo shopinfo;
	    	Bitmap Photo;

	    	ArrayList<ShopInfo> List = shopinfos.getList();
	        for(int i = 0; i < List.size(); i++) {
	            shopinfo = List.get(i);
	            // �摜�����擾�Ȃ�
	            if(!shopinfo.getPhotoFlg()) {
	                // �摜�̎擾
	            	Photo = shopinfo.ImportPhoto();
	            	if(Photo != null) {
	            		shopinfo.setPhoto(Photo);
	            	}
	            	publishProgress(0);
	            }
	        }
	        return(0);
	    }

	    @Override
	    protected void onProgressUpdate(Integer... progress) {
	        // ���ʕ\�����ĕ`��
	    	shoplistadapter.notifyDataSetChanged();
	    }

	    // ���C���X���b�h�Ŏ��s���鏈��  
	    @Override  
	    protected void onPostExecute(Integer params) {
	        // ���ʕ\�����ĕ`��
	    	shoplistadapter.notifyDataSetChanged();
	    }
	}

	//
	//
	//
	class ShopListTask extends AsyncTask<Integer, Integer, Integer> {
		protected ProgressDialog progressdialog;
		protected int Num;

		// �R���X�g���N�^
	    public ShopListTask() {
	    	this.Num = 0;
	    }

		@Override
		protected void onPreExecute() {
			// �o�b�N�O���E���h�̏����O��UI�X���b�h�Ń_�C�A���O�\��
			progressdialog = new ProgressDialog(ShopList.this);
			progressdialog.setMessage(getResources().getText(R.string.label_dataloading));
			progressdialog.setIndeterminate(true);
			progressdialog.show();
		}

		// �o�b�N�O���E���h�Ŏ��s���鏈��
	    @Override
	    protected Integer doInBackground(Integer... params) {
	    	this.Num = shopinfos.ImportData();
	    	return(this.Num);
	    }

	    // ���C���X���b�h�Ŏ��s���鏈��
	    @Override  
	    protected void onPostExecute(Integer params) {
			// �������_�C�A���O���N���[�Y
	    	progressdialog.dismiss();

	    	// �ǉ��ǂݍ��݊���
			closeMore(this.Num);
	    }
	}
}