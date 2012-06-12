package com.hide_ab.TabeRoid;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Application;

public class ShopInfos extends Application {
	// 表示するデータのリスト
	protected ArrayList<ShopInfo> List = null;
	// ページ位置
	protected int PageNum;
    // ベースURL
	protected String XmlUrlBase;
	// API URL
	protected static final String XML_URL = "http://api.tabelog.com/Ver2.1/RestaurantSearch/?Key=96d24714e814675c7a8cd129c18608151cf2bf9b&";
//	protected static final String XML_URL_D = "http://api.tabelog.com/Ver2.1/RestaurantSearch/?Key=96d24714e814675c7a8cd129c18608151cf2bf9b&Datum=world&Latitude=35.726&Longitude=139.988";

	@Override
	public void onCreate() {
		super.onCreate();
		this.List = new ArrayList<ShopInfo>();
	}

	public ArrayList<ShopInfo> getList() {
		return(this.List);
	}

	public ShopInfo getInfo(int position) {
		return((ShopInfo)this.List.get(position));
	}

	public void putInfo(ShopInfo shopInfo) {
		this.List.add(shopInfo);
	}

    // 検索結果の店舗情報を取得
	protected int ImportData(String SearchKey, String Lat, String Lon, String Stations) {
		// リスト初期化
		this.List.clear();
	
		// ページ位置を初期化
		this.PageNum = 0;

		// ベースURLを初期化
        if(SearchKey.equals("gps")) {
           	this.XmlUrlBase = XML_URL + "Datum=world&Latitude=" + Lat + "&Longitude=" + Lon;
        } else {
           	this.XmlUrlBase = XML_URL + "Station=" + URLEncoder.encode(Stations);
        }

    	// 検索結果の店舗情報を取得
        return(this.ImportData());
	}

	// 検索結果の店舗情報を取得
	protected int ImportData() {
    	int ItemNodesLen = 0;

        // ページをインクリメント
        this.PageNum++;

        try {
            // 指定した URL の作成
        	URL url = new URL(this.XmlUrlBase + "&PageNum=" + this.PageNum);

            // ドキュメントビルダーの生成
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            // URLからドキュメントオブジェクトを取得
            Document doc = db.parse(url.openStream());
            Element root = doc.getDocumentElement();

            Element TmpNode, TmpNode2;
            NodeList TmpNodes;
            int TmpNodesLen;

            // リスト作成
            NodeList ItemNodes = root.getElementsByTagName("Item");
            ItemNodesLen = ItemNodes.getLength();

            for(int i = 0; i < ItemNodesLen; i++) {
            	String Rcd = "";
            	String RestaurantName = "";
            	String TabelogUrl = "";
            	String TotalScore = "";
            	String Category = "";
            	String Station = "";

            	TmpNode = (Element)ItemNodes.item(i);

            	TmpNodes = TmpNode.getElementsByTagName("Rcd");
            	TmpNodesLen = TmpNodes.getLength();
            	if(TmpNodesLen > 0) {
            		TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			Rcd = ell.getNodeValue();
            		}
            	}

            	TmpNodes = TmpNode.getElementsByTagName("RestaurantName");
            	TmpNodesLen = TmpNodes.getLength();
            	if(TmpNodesLen > 0) {
            		TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			RestaurantName = ell.getNodeValue();
            		}
            	}

            	TmpNodes = TmpNode.getElementsByTagName("TabelogUrl");
            	TmpNodesLen = TmpNodes.getLength();
            	if(TmpNodesLen > 0) {
            		TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			TabelogUrl = ell.getNodeValue();
            		}
            	}

            	TmpNodes = TmpNode.getElementsByTagName("TotalScore");
            	TmpNodesLen = TmpNodes.getLength();
            	if(TmpNodesLen > 0) {
            		TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			TotalScore = ell.getNodeValue();
            		}
            	}

            	TmpNodes = TmpNode.getElementsByTagName("Category");
            	TmpNodesLen = TmpNodes.getLength();
            	if(TmpNodesLen > 0) {
            		TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			Category = ell.getNodeValue();
            		}
            	}

            	TmpNodes = TmpNode.getElementsByTagName("Station");
            	TmpNodesLen = TmpNodes.getLength();
            	if(TmpNodesLen > 0) {
            		TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			Station = ell.getNodeValue();
            		}
            	}

            	// 取得した各データをshopInfoに格納
            	ShopInfo shopInfo = new ShopInfo();
            	shopInfo.setRcd(Rcd);
            	shopInfo.setRestaurantName(RestaurantName);
            	shopInfo.setTabelogUrl(TabelogUrl);
            	shopInfo.setTotalScore(TotalScore);
            	shopInfo.setCategory(Category);
            	shopInfo.setStation(Station);

            	// リストに追加
            	this.putInfo(shopInfo);
            }
        } catch (Exception e) {
//			showDialog(this, "", "Error1."+e.getMessage());
        	e.printStackTrace();
        	return(-1);
        }

        return(ItemNodesLen);
	}
}