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
import android.graphics.Bitmap;

public class ShopInfos extends Application {
	// 表示するデータのリスト
	protected ArrayList<ShopInfo> List = null;
	// ページ位置
	protected int PageNum;
    // 検索結果件数
	protected int NumOfResult;
	// ベースURL
	protected String XmlUrlBase;
	// API URL
	protected static final String XML_URL = "http://api.tabelog.com/Ver2.1/RestaurantSearch/?Key=96d24714e814675c7a8cd129c18608151cf2bf9b&ResultSet=large&";
//	protected static final String XML_URL_D = "http://api.tabelog.com/Ver2.1/RestaurantSearch/?Key=96d24714e814675c7a8cd129c18608151cf2bf9b&Datum=world&Latitude=35.726&Longitude=139.988";

//	protected TabeRoid taberoid;
	protected String SearchKey;
	protected String Lat;
	protected String Lon;
	protected String Station;

	public Bitmap DefaultPhoto;
	public Bitmap StarBack;
	public Bitmap StarFront;

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

	public int NumOfResult() {
		return(this.NumOfResult);
	}

	public void preSearch(String SearchKey, String Lat, String Lon, String Station) {
		this.SearchKey = SearchKey;
		this.Lat = Lat;
		this.Lon = Lon;
		this.Station = Station;

		// リスト初期化
		this.List.clear();
	
		// ページ位置を初期化
		this.PageNum = 0;

		// ベースURLを初期化
        if(this.SearchKey.equals("gps")) {
           	this.XmlUrlBase = XML_URL + "Datum=world&Latitude=" + this.Lat + "&Longitude=" + this.Lon;
        } else {
           	this.XmlUrlBase = XML_URL + "Station=" + URLEncoder.encode(this.Station);
        }
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

            // 検索結果件数
            NodeList NumNodes = root.getElementsByTagName("NumOfResult");
            int NumNodesLen = NumNodes.getLength();
        	if(NumNodesLen > 0) {
        		TmpNode2 = (Element)NumNodes.item(0);
        		Node ell = (Node)TmpNode2.getFirstChild();
        		if(ell != null) {
        			this.NumOfResult = Integer.parseInt(ell.getNodeValue());
        		}
        	}

            // リスト作成
            NodeList ItemNodes = root.getElementsByTagName("Item");
            ItemNodesLen = ItemNodes.getLength();

            for(int i = 0; i < ItemNodesLen; i++) {
            	String Rcd = "";
            	String RestaurantName = "";
            	String TabelogUrl = "";
            	String TabelogMobileUrl = "";
            	String TotalScore = "";
            	String TasteScore = "";
            	String ServiceScore = "";
            	String MoodScore = "";
            	String Situation = "";
            	String DinnerPrice = "";
            	String LunchPrice = "";
            	String Category = "";
            	String Station = "";
            	String Address = "";
            	String Tel = "";
            	String BusinessHours = "";
            	String Holiday = "";
            	String Lat = "";
            	String Lon = "";
            	Bitmap Photo = null;

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

            	TmpNodes = TmpNode.getElementsByTagName("TabelogMobileUrl");
            	TmpNodesLen = TmpNodes.getLength();
            	if(TmpNodesLen > 0) {
            		TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			TabelogMobileUrl = ell.getNodeValue();
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

            	TmpNodes = TmpNode.getElementsByTagName("TasteScore");
            	TmpNodesLen = TmpNodes.getLength();
            	if(TmpNodesLen > 0) {
            		TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			TasteScore = ell.getNodeValue();
            		}
            	}

            	TmpNodes = TmpNode.getElementsByTagName("ServiceScore");
            	TmpNodesLen = TmpNodes.getLength();
            	if(TmpNodesLen > 0) {
            		TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			ServiceScore = ell.getNodeValue();
            		}
            	}

            	TmpNodes = TmpNode.getElementsByTagName("MoodScore");
            	TmpNodesLen = TmpNodes.getLength();
            	if(TmpNodesLen > 0) {
            		TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			MoodScore = ell.getNodeValue();
            		}
            	}

            	TmpNodes = TmpNode.getElementsByTagName("Situation");
            	TmpNodesLen = TmpNodes.getLength();
            	if(TmpNodesLen > 0) {
            		TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			Situation = ell.getNodeValue();
            		}
            	}

            	TmpNodes = TmpNode.getElementsByTagName("DinnerPrice");
            	TmpNodesLen = TmpNodes.getLength();
            	if(TmpNodesLen > 0) {
            		TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			DinnerPrice = ell.getNodeValue();
            		}
            	}

            	TmpNodes = TmpNode.getElementsByTagName("LunchPrice");
            	TmpNodesLen = TmpNodes.getLength();
            	if(TmpNodesLen > 0) {
            		TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			LunchPrice = ell.getNodeValue();
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

            	TmpNodes = TmpNode.getElementsByTagName("Address");
            	TmpNodesLen = TmpNodes.getLength();
            	if(TmpNodesLen > 0) {
            		TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			Address = ell.getNodeValue();
            		}
            	}

            	TmpNodes = TmpNode.getElementsByTagName("Tel");
            	TmpNodesLen = TmpNodes.getLength();
            	if(TmpNodesLen > 0) {
            		TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			Tel = ell.getNodeValue();
            		}
            	}

            	TmpNodes = TmpNode.getElementsByTagName("BusinessHours");
            	TmpNodesLen = TmpNodes.getLength();
            	if(TmpNodesLen > 0) {
            		TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			BusinessHours = ell.getNodeValue();
            		}
            	}

            	TmpNodes = TmpNode.getElementsByTagName("Holiday");
            	TmpNodesLen = TmpNodes.getLength();
            	if(TmpNodesLen > 0) {
            		TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			Holiday = ell.getNodeValue();
            		}
            	}

            	TmpNodes = TmpNode.getElementsByTagName("Latitude");
            	TmpNodesLen = TmpNodes.getLength();
            	if(TmpNodesLen > 0) {
            		TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			Lat = ell.getNodeValue();
            		}
            	}

            	TmpNodes = TmpNode.getElementsByTagName("Longitude");
            	TmpNodesLen = TmpNodes.getLength();
            	if(TmpNodesLen > 0) {
            		TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			Lon = ell.getNodeValue();
            		}
            	}

            	// 取得した各データをshopInfoに格納
            	ShopInfo shopInfo = new ShopInfo(this.StarBack, this.StarFront);
            	shopInfo.setRcd(Rcd);
            	shopInfo.setRestaurantName(RestaurantName);
            	shopInfo.setTabelogUrl(TabelogUrl);
            	shopInfo.setTabelogMobileUrl(TabelogMobileUrl);
            	shopInfo.setTotalScore(TotalScore);
            	shopInfo.setTasteScore(TasteScore);
            	shopInfo.setServiceScore(ServiceScore);
            	shopInfo.setMoodScore(MoodScore);
            	shopInfo.setSituation(Situation);
            	shopInfo.setDinnerPrice(DinnerPrice);
            	shopInfo.setLunchPrice(LunchPrice);
            	shopInfo.setCategory(Category);
            	shopInfo.setStation(Station);
            	shopInfo.setAddress(Address);
            	shopInfo.setTel(Tel);
            	shopInfo.setBusinessHours(BusinessHours);
            	shopInfo.setHoliday(Holiday);
            	shopInfo.setLat(Lat);
            	shopInfo.setLon(Lon);
        		shopInfo.setDefaultPhoto(this.DefaultPhoto);

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