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
	// �������ʓX�܃f�[�^�I�u�W�F�N�g
	protected ShopInfos shopinfos;
	// ListAdapter
	private ShopListAdapter shoplistadapter = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    // ��ʍ\����K�p
	    setContentView(R.layout.shop_list);

		// �������ʓX�܃f�[�^�I�u�W�F�N�g����
	    this.shopinfos = (ShopInfos)this.getApplication();

		TextView text_result = (TextView)findViewById(R.id.text_result);
//	    text_result.setText(ItemNum + "��������܂���");

	    // List����ShopAdapter�𐶐�
	    this.shoplistadapter = new ShopListAdapter(this, R.layout.shop_listrow, shopinfos.getList());
    	// ShopAdapter��ShopList.xml���ɂ���listview_results�ɓn���ē��e��\������
    	ListView listview_results = (ListView)findViewById(R.id.listview_results);
    	// listview_results�Ƀt�b�^�[��ǉ�
    	listview_results.addFooterView(getLayoutInflater().inflate(R.layout.shop_listfooter, null), null, true);
    	// listview_results��shoplistadapter���Z�b�g
    	listview_results.setAdapter(this.shoplistadapter);

		// �摜�̓o�b�N�O���E���h�Ŏ擾
		ImageDrawer imagedrawer = new ImageDrawer(shopinfos, shoplistadapter);
		imagedrawer.execute();

    	// listview_results��OnItemClickListener��ݒ�
    	listview_results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
	    		// �t�b�^���N���b�N���ꂽ�ꍇ
	    		if(view.getId() == R.id.Footer) {
	    		    // �������ʂ̓X�܏����擾(�ǉ���)
	            	ShopListTask task = new ShopListTask(shopinfos, ShopList.this);
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
    public void closeMore() {
		// �摜�̓o�b�N�O���E���h�Ŏ擾
		ImageDrawer imagedrawer = new ImageDrawer(shopinfos, shoplistadapter);   
		imagedrawer.execute();

    	// listview_results����t�b�^�[���폜
    	ListView listview_results = (ListView)findViewById(R.id.listview_results);
    	listview_results.removeFooterView(getLayoutInflater().inflate(R.layout.shop_listfooter, null));

    	// �ĕ`��
    	listview_results.invalidateViews();
		shoplistadapter.notifyDataSetChanged();
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
}