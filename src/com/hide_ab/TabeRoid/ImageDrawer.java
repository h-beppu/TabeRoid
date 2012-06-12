package com.hide_ab.TabeRoid;

import java.util.ArrayList;
import android.os.AsyncTask;
import android.graphics.Bitmap;

public class ImageDrawer extends AsyncTask<Integer, Integer, Integer> {
	// �������ʓX�܃f�[�^�I�u�W�F�N�g
	protected ShopInfos shopinfos;
	// ���ʕ\����ListAdapter
	private ShopListAdapter shoplistadapter;

	// �R���X�g���N�^
    public ImageDrawer(ShopInfos shopinfosP, ShopListAdapter shoplistadapterP) {
    	this.shopinfos = shopinfosP;
    	this.shoplistadapter = shoplistadapterP;
    }

    // �o�b�N�O���E���h�Ŏ��s���鏈��   
    @Override
    protected Integer doInBackground(Integer... params) {
    	ShopInfo shopinfo;
    	Bitmap Photo;
    	
    	ArrayList<ShopInfo> List = this.shopinfos.getList();
        for(int i = 0; i < List.size(); i++) {
            shopinfo = List.get(i);
            // �摜�����擾�Ȃ�
            if(!shopinfo.getPhotoFlg()) {
                // �摜�̎擾
            	Photo = shopinfo.ImportPhoto();
            	shopinfo.setPhoto(Photo);

            	publishProgress(0);
            }
        }
        return(0);
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        // ���ʕ\�����ĕ`��
    	this.shoplistadapter.notifyDataSetChanged();
    }

    // ���C���X���b�h�Ŏ��s���鏈��   
    @Override  
    protected void onPostExecute(Integer params) {   
        // ���ʕ\�����ĕ`��
    	this.shoplistadapter.notifyDataSetChanged();
    }   
}