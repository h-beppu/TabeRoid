package com.hide_ab.TabeRoid;

import java.net.URL;
import java.io.Serializable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ShopInfo {
	private String Rcd;
	private String RestaurantName;
	private String TabelogUrl;
	private String TotalScore;
	private String TasteScore;
	private String ServiceScore;
	private String MoodScore;
	private String Situation;
	private String Category;
	private String Station;
	private Bitmap Photo;

	public ShopInfo() {
		Rcd = "";
		RestaurantName = "";
		TabelogUrl = "";
		TotalScore = "";
		TasteScore = "";
		ServiceScore = "";
		MoodScore = "";
		Situation = "";
		Category = "";
		Station = "";
		Photo = null;
	}

	public String getRcd() {
		return this.Rcd;
	}

	public void setRcd(String Rcd) {
		this.Rcd = Rcd;
	}

	public String getRestaurantName() {
		return this.RestaurantName;
	}

	public void setRestaurantName(String RestaurantName) {
		this.RestaurantName = RestaurantName;
	}

	public String getTabelogUrl() {
		return this.TabelogUrl;
	}

	public void setTabelogUrl(String TabelogUrl) {
		this.TabelogUrl = TabelogUrl;
	}

	public String getTotalScore() {
		return this.TotalScore;
	}

	public void setTotalScore(String TotalScore) {
		this.TotalScore = TotalScore;
	}

	public String getTasteScore() {
		return this.TasteScore;
	}

	public void setTasteScore(String TasteScore) {
		this.TasteScore = TasteScore;
	}

	public String getServiceScore() {
		return this.ServiceScore;
	}

	public void setServiceScore(String ServiceScore) {
		this.ServiceScore = ServiceScore;
	}

	public String getMoodScore() {
		return this.MoodScore;
	}

	public void setMoodScore(String MoodScore) {
		this.MoodScore = MoodScore;
	}

	public String getSituation() {
		return this.Situation;
	}

	public void setSituation(String Situation) {
		this.Situation = Situation;
	}

	public String getCategory() {
		return this.Category;
	}

	public void setCategory(String Category) {
		this.Category = Category;
	}

	public void setStation(String Station) {
		this.Station = Station;
	}

	public String getStation() {
		return this.Station;
	}

	public void setPhoto(Bitmap Photo) {
		this.Photo = Photo;
	}

	public Bitmap getPhoto() {
/*
        if(this.Photo == null) {
        	this.Photo = this.ImportPhoto(this.Rcd);
        }
*/
		return this.Photo;
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