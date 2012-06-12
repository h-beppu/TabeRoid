package com.hide_ab.TabeRoid;

import android.os.AsyncTask;
import android.widget.TextView;
import android.app.ProgressDialog; 
import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class TabeRoidTask extends AsyncTask<Integer, Integer, Integer> {
	// �������ʓX�܃f�[�^�I�u�W�F�N�g
	protected ShopInfos shopinfos;
	// �A�N�e�B�r�e�B
	protected TabeRoid taberoid;
	protected ProgressDialog progressdialog;
	protected int Num;

	// �R���X�g���N�^
    public TabeRoidTask(ShopInfos shopinfos, TabeRoid taberoid) {
    	this.shopinfos = shopinfos;
    	this.taberoid = taberoid;

    	// �f�t�H���g�ʐ^�̐ݒ�
		Resources r = this.taberoid.getResources();
    	this.shopinfos.DefaultPhoto = BitmapFactory.decodeResource(r, R.drawable.icon);
    }

	@Override
	protected void onPreExecute() {
    	// �o�b�N�O���E���h�̏����O��UI�X���b�h�Ń_�C�A���O�\��
		progressdialog = new ProgressDialog(this.taberoid);
		progressdialog.setMessage(this.taberoid.getResources().getText(R.string.label_dataloading));
		progressdialog.setIndeterminate(true);
		progressdialog.show();
	}

	// �o�b�N�O���E���h�Ŏ��s���鏈��
    @Override
    protected Integer doInBackground(Integer... params) {
    	this.Num = this.shopinfos.ImportData();
    	return(this.Num);
    }

    // ���C���X���b�h�Ŏ��s���鏈��
    @Override
    protected void onPostExecute(Integer params) {
		// �������_�C�A���O���N���[�Y
    	progressdialog.dismiss();

    	TextView locationText = (TextView)this.taberoid.findViewById(R.id.text_location);
        locationText.setText("Click" + this.Num);

		// "ShopList"��ʂɈڍs
		this.taberoid.openShopList();
    }
}