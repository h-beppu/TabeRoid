package com.hide_ab.TabeRoid;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.os.AsyncTask;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageDrawer extends AsyncTask<Integer, Integer, Integer> {
	// 検索結果店舗データオブジェクト
	protected ShopInfos shopinfos;
	// 結果表示のListAdapter
	private ShopListAdapter shoplistadapter;

	// コンストラクタ
    public ImageDrawer(ShopInfos shopinfosP, ShopListAdapter shoplistadapterP) {
    	this.shopinfos = shopinfosP;
    	this.shoplistadapter = shoplistadapterP;
    }

    // バックグラウンドで実行する処理   
    @Override
    protected Integer doInBackground(Integer... params) {
    	ShopInfo shopinfo;
    	Bitmap bitmap;
    	
    	ArrayList<ShopInfo> List = this.shopinfos.getList();
        for(int i = 0; i < List.size(); i++) {
            // 画像の取得
            shopinfo = List.get(i);
            bitmap = ImportPhoto(shopinfo.getRcd());
            shopinfo.setPhoto(bitmap);

        	publishProgress(0);
        }
        return(0);
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        // 結果表示を再描画
    	this.shoplistadapter.notifyDataSetChanged();
    }

    // メインスレッドで実行する処理   
    @Override  
    protected void onPostExecute(Integer params) {   
        // 結果表示を再描画
    	this.shoplistadapter.notifyDataSetChanged();
    }   

    // 画像の取得
	protected Bitmap ImportPhoto(String Rcd) {
        Bitmap PhotoTmp = null;
        Document doc = null;

        // 画像URL取得APIのURL
        String ImgXmlUrl = "http://api.tabelog.com/Ver1/ReviewImageSearch/?Key=96d24714e814675c7a8cd129c18608151cf2bf9b&Rcd=";
        String ImgUrl = "http://image2.tabelog.k-img.com/restaurant/images/Rvw/2875/100x100_square_2875567.jpg";

        try {
        	// 指定したURLの作成
        	URL url = new URL(ImgXmlUrl + Rcd);

        	// ドキュメントビルダーの生成
        	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        	DocumentBuilder db = dbf.newDocumentBuilder();

        	// URLからドキュメントオブジェクトを取得
        	doc = db.parse(url.openStream());
        	Element root = doc.getDocumentElement();

        	Element TmpNode, TmpNode2;
        	NodeList TmpNodes;
        	int TmpNodesLen;

        	// リスト作成
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
        				// サーバから画像ファイルを取得
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