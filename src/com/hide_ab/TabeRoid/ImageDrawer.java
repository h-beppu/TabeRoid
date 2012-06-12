package com.hide_ab.TabeRoid;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.widget.ImageView;
import android.os.AsyncTask;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageDrawer extends AsyncTask<String, Integer, Bitmap> {
	// �A�C�R����\������r���[   
	private ShopInfo shopInfo;   

	// �R���X�g���N�^
    public ImageDrawer(ShopInfo shopInfoP) {
    	this.shopInfo = shopInfoP;
    }

    // �o�b�N�O���E���h�Ŏ��s���鏈��   
    @Override
    protected Bitmap doInBackground(String... params) {
        String Rcd = params[0];
        return ImportPhoto(Rcd);
    }

    // ���C���X���b�h�Ŏ��s���鏈��   
    @Override  
    protected void onPostExecute(Bitmap result) {   
    	this.shopInfo.setPhoto(result);   
    }   

    // �摜�̎擾
	protected Bitmap ImportPhoto(String Rcd) {
        Bitmap PhotoTmp = null;
        Document doc = null;

        // �摜URL�擾API��URL
        String ImgXmlUrl = "http://api.tabelog.com/Ver1/ReviewImageSearch/?Key=96d24714e814675c7a8cd129c18608151cf2bf9b&Rcd=";
        String ImgUrl = "http://image2.tabelog.k-img.com/restaurant/images/Rvw/2875/100x100_square_2875567.jpg";

        try {
        	// �w�肵��URL�̍쐬
        	URL url = new URL(ImgXmlUrl + Rcd);

        	// �h�L�������g�r���_�[�̐���
        	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        	DocumentBuilder db = dbf.newDocumentBuilder();

        	// URL����h�L�������g�I�u�W�F�N�g���擾
        	doc = db.parse(url.openStream());
        	Element root = doc.getDocumentElement();

        	Element TmpNode, TmpNode2;
        	NodeList TmpNodes;
        	int TmpNodesLen;

        	// ���X�g�쐬
        	NodeList ItemNodes = root.getElementsByTagName("Item");
        	int ItemNodesLen = ItemNodes.getLength();
    		if(ItemNodesLen > 0) {
        		TmpNode = (Element)ItemNodes.item(0);
        		TmpNodes = TmpNode.getElementsByTagName("ImageUrlM");
        		TmpNodesLen = TmpNodes.getLength();
        		if(TmpNodesLen > 0) {
        			TmpNode2 = (Element)TmpNodes.item(0);
        			Node ell = (Node)TmpNode2.getFirstChild();
        			if(ell != null) {
        				// �T�[�o����摜�t�@�C�����擾
        				ImgUrl = ell.getNodeValue();
        				url = new URL(ImgUrl);
        				PhotoTmp = BitmapFactory.decodeStream(url.openStream());
        			}
        		}
    		}
        } catch (Exception e) {
//        	showDialog(this, "", "Error2."+e.getMessage());
        	e.printStackTrace();
        }
        return(PhotoTmp);
	}
}
