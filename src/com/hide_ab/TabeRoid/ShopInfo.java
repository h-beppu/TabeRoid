package com.hide_ab.TabeRoid;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.graphics.Bitmap;

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
	private ArrayList<String> Comments = null;

    // ベースURL
	protected String XmlUrlBase;
	// API URL
	protected static final String XML_URL = "http://api.tabelog.com/Ver1/ReviewSearch/?Key=96d24714e814675c7a8cd129c18608151cf2bf9b&";

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
		Comments = null;
	}

	public String getRcd() {
		this.Comments = new ArrayList<String>();
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
		return this.Photo;
	}

	public ArrayList<String> getComments() {
		return this.Comments;
	}

    // コメントを取得
	protected int ImportData() {
    	int ItemNodesLen = 0;
    	
		URL url = null;
        Document doc = null;

		try {
            // 指定した URL の作成
            String XmlUrl = XML_URL + "rcd=" + this.Rcd;
            url = new URL(XmlUrl);
            
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
            ItemNodesLen = ItemNodes.getLength();

			for(int i = 0; i < ItemNodesLen; i++) {
				String Title = "";
				String RestaurantName = "";
        		String TabelogUrl = "";
        		String TotalScore = "";
        		String Category = "";
        		String Station = "";

        		TmpNode = (Element)ItemNodes.item(i);

    			TmpNodes = TmpNode.getElementsByTagName("Title");
        		TmpNodesLen = TmpNodes.getLength();
        		if(TmpNodesLen > 0) {
        			TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			Title = ell.getNodeValue();
            		}
        		}

       			// リストに追加
        		this.Comments.add(Title);
        	}
        } catch (Exception e) {
//			showDialog(this, "", "Error1."+e.getMessage());
            e.printStackTrace();
        	return(-1);
        }

        return(ItemNodesLen);
    }
}