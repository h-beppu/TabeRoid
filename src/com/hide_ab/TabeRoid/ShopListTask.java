package com.hide_ab.TabeRoid;

import android.os.AsyncTask;
import android.app.ProgressDialog;

public class ShopListTask extends AsyncTask<Integer, Integer, Integer> {
	// �������ʓX�܃f�[�^�I�u�W�F�N�g
	protected ShopInfos shopinfos;
	// �A�N�e�B�r�e�B
	protected ShopList shoplist;
	protected ProgressDialog progressdialog;
	protected int Num;

	// �R���X�g���N�^
    public ShopListTask(ShopInfos shopinfosP, ShopList shoplistP) {
    	this.shopinfos = shopinfosP;
    	this.shoplist = shoplistP;
    }

	@Override
	protected void onPreExecute() {
/*
		// �o�b�N�O���E���h�̏����O��UI�X���b�h�Ń_�C�A���O�\��
		progressdialog = new ProgressDialog(this.shoplist);
		progressdialog.setMessage(this.shoplist.getResources().getText(R.string.label_dataloading));
		progressdialog.setIndeterminate(true);
		progressdialog.show();
*/
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
//    	progressdialog.dismiss();

    	// �ǉ��ǂݍ��݊���
		this.shoplist.closeMore();
    }
}