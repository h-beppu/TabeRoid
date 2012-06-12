package com.hide_ab.TabeRoid;

import android.os.AsyncTask;
import android.app.ProgressDialog; 

public class ShopDetailTask extends AsyncTask<Integer, Integer, Integer> {
	// �X�܃f�[�^�I�u�W�F�N�g
	protected ShopInfo shopinfo;
	// �A�N�e�B�r�e�B
	protected ShopDetail shopdetail;
	protected ProgressDialog progressdialog;
	protected int Num;

	// �R���X�g���N�^
    public ShopDetailTask(ShopInfo shopinfoP, ShopDetail shopdetailP) {
    	this.shopinfo = shopinfoP;
    	this.shopdetail = shopdetailP;
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
//    	progressdialog.dismiss();

		// "ShopList"��ʂɈڍs
		this.shopdetail.closeComment(this.shopinfo);
    }
}