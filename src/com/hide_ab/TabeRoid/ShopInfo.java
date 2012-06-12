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
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;

public class ShopInfo {
	private String Rcd;
	private String RestaurantName;
	private String TabelogUrl;
	private String TabelogMobileUrl;
	private String TotalScore;
	private String TasteScore;
	private String ServiceScore;
	private String MoodScore;
	private String Situation;
	private String DinnerPrice;
	private String LunchPrice;
	private String Category;
	private String Station;
	private String Address;
	private String Tel;
	private String BusinessHours;
	private String Holiday;
	private String Lat;
	private String Lon;
	private Bitmap TotalScoreStar;
	private Bitmap TasteScoreStar;
	private Bitmap ServiceScoreStar;
	private Bitmap MoodScoreStar;
	private ArrayList<ReviewInfo> Reviews;
	private Bitmap Photo;
	private Bitmap StarBack;
	private Bitmap StarFront;
	private boolean PhotoGetTask;

    // ベースURL
	protected String XmlUrlBase;
	// API URL
	protected static final String XML_URL = "http://api.tabelog.com/Ver1/ReviewSearch/?Key=96d24714e814675c7a8cd129c18608151cf2bf9b&";

	public ShopInfo(Bitmap DefaultPhoto, Bitmap StarBack, Bitmap StarFront) {
		this.Rcd = "";
		this.RestaurantName = "";
		this.TabelogUrl = "";
		this.TabelogMobileUrl = "";
		this.TotalScore = "";
		this.TasteScore = "";
		this.ServiceScore = "";
		this.MoodScore = "";
		this.Situation = "";
		this.DinnerPrice = "";
		this.LunchPrice = "";
		this.Category = "";
		this.Station = "";
		this.Address = "";
		this.Tel = "";
		this.BusinessHours = "";
		this.Holiday = "";
		this.Lat = "";
		this.Lon = "";
		this.TotalScoreStar = null;
		this.TasteScoreStar = null;
		this.ServiceScoreStar = null;
		this.MoodScoreStar = null;
		this.Photo = null;
		this.Reviews = new ArrayList<ReviewInfo>();
		this.StarBack  = StarBack;
		this.StarFront = StarFront;
		this.PhotoGetTask = false;
	}

	public boolean getPhotoGetTask() {
		return this.PhotoGetTask;
	}

	public void setPhotoGetTask(boolean Flag) {
		this.PhotoGetTask = Flag;
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

	public String getTabelogMobileUrl() {
		return this.TabelogMobileUrl;
	}

	public void setTabelogMobileUrl(String TabelogMobileUrl) {
		this.TabelogMobileUrl = TabelogMobileUrl;
	}

	public String getTotalScore() {
		return this.TotalScore;
	}

	public void setTotalScore(String TotalScore) {
		this.TotalScore = TotalScore;
		// 評価マーク生成
		this.TotalScoreStar = this.createMarkStar(TotalScore);
	}

	public String getTasteScore() {
		return this.TasteScore;
	}

	public void setTasteScore(String TasteScore) {
		this.TasteScore = TasteScore;
		// 評価マーク生成
		this.TasteScoreStar = this.createMarkStar(TasteScore);
	}

	public String getServiceScore() {
		return this.ServiceScore;
	}

	public void setServiceScore(String ServiceScore) {
		this.ServiceScore = ServiceScore;
		// 評価マーク生成
		this.ServiceScoreStar = this.createMarkStar(ServiceScore);
	}

	public String getMoodScore() {
		return this.MoodScore;
	}

	public void setMoodScore(String MoodScore) {
		this.MoodScore = MoodScore;
		// 評価マーク生成
		this.MoodScoreStar = this.createMarkStar(MoodScore);
	}

	public String getSituation() {
		return this.Situation;
	}

	public void setSituation(String Situation) {
		this.Situation = Situation;
	}

	public String getDinnerPrice() {
		return this.DinnerPrice;
	}

	public void setDinnerPrice(String DinnerPrice) {
		this.DinnerPrice = DinnerPrice;
	}

	public String getLunchPrice() {
		return this.LunchPrice;
	}

	public void setLunchPrice(String LunchPrice) {
		this.LunchPrice = LunchPrice;
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

	public void setAddress(String Address) {
		this.Address = Address;
	}

	public String getAddress() {
		return this.Address;
	}

	public void setTel(String Tel) {
		this.Tel = Tel;
	}

	public String getTel() {
		return this.Tel;
	}

	public void setBusinessHours(String BusinessHours) {
		this.BusinessHours = BusinessHours;
	}

	public String getBusinessHours() {
		return this.BusinessHours;
	}

	public void setHoliday(String Holiday) {
		this.Holiday = Holiday;
	}

	public String getHoliday() {
		return this.Holiday;
	}

	public void setLat(String Lat) {
		this.Lat = Lat;
	}

	public String getLat() {
		return this.Lat;
	}

	public void setLon(String Lon) {
		this.Lon = Lon;
	}

	public String getLon() {
		return this.Lon;
	}

	public Bitmap getTotalScoreStar() {
		return this.TotalScoreStar;
	}

	public Bitmap getTasteScoreStar() {
		return this.TasteScoreStar;
	}

	public Bitmap getServiceScoreStar() {
		return this.ServiceScoreStar;
	}

	public Bitmap getMoodScoreStar() {
		return this.MoodScoreStar;
	}

//	public void setDefaultPhoto(Bitmap Photo) {
//		this.Photo = Photo;
//	}

	public void setPhoto(Bitmap Photo) {
		this.Photo = Photo;
	}

	public Bitmap getPhoto() {
//		if(this.Photo == null) {
//			return this.DefaultPhoto;
//		}
		return this.Photo;
	}

	public ArrayList<ReviewInfo> getReviews() {
		return this.Reviews;
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
				String NickName = "";
				String VisitDate = "";
				String ReviewDate = "";
				String UseType = "";
				String Situations = "";
				String TotalScore = "";
				String TasteScore = "";
				String ServiceScore = "";
				String MoodScore = "";
				String DinnerPrice = "";
				String LunchPrice = "";
				String Title = "";
				String Comment = "";
				String PcSiteUrl = "";
				String MobileSiteUrl = "";

        		TmpNode = (Element)ItemNodes.item(i);

    			TmpNodes = TmpNode.getElementsByTagName("NickName");
        		TmpNodesLen = TmpNodes.getLength();
        		if(TmpNodesLen > 0) {
        			TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			NickName = ell.getNodeValue();
            		}
        		}

    			TmpNodes = TmpNode.getElementsByTagName("VisitDate");
        		TmpNodesLen = TmpNodes.getLength();
        		if(TmpNodesLen > 0) {
        			TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			VisitDate = ell.getNodeValue();
            		}
        		}

    			TmpNodes = TmpNode.getElementsByTagName("ReviewDate");
        		TmpNodesLen = TmpNodes.getLength();
        		if(TmpNodesLen > 0) {
        			TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			ReviewDate = ell.getNodeValue();
            		}
        		}

    			TmpNodes = TmpNode.getElementsByTagName("UseType");
        		TmpNodesLen = TmpNodes.getLength();
        		if(TmpNodesLen > 0) {
        			TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			UseType = ell.getNodeValue();
            		}
        		}

    			TmpNodes = TmpNode.getElementsByTagName("Situations");
        		TmpNodesLen = TmpNodes.getLength();
        		if(TmpNodesLen > 0) {
        			TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			Situations = ell.getNodeValue();
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

    			TmpNodes = TmpNode.getElementsByTagName("Title");
        		TmpNodesLen = TmpNodes.getLength();
        		if(TmpNodesLen > 0) {
        			TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			Title = ell.getNodeValue();
            		}
        		}

    			TmpNodes = TmpNode.getElementsByTagName("Comment");
        		TmpNodesLen = TmpNodes.getLength();
        		if(TmpNodesLen > 0) {
        			TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			Comment = ell.getNodeValue();
            		}
        		}

    			TmpNodes = TmpNode.getElementsByTagName("PcSiteUrl");
        		TmpNodesLen = TmpNodes.getLength();
        		if(TmpNodesLen > 0) {
        			TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			PcSiteUrl = ell.getNodeValue();
            		}
        		}

    			TmpNodes = TmpNode.getElementsByTagName("MobileSiteUrl");
        		TmpNodesLen = TmpNodes.getLength();
        		if(TmpNodesLen > 0) {
        			TmpNode2 = (Element)TmpNodes.item(0);
            		Node ell = (Node)TmpNode2.getFirstChild();
            		if(ell != null) {
            			MobileSiteUrl = ell.getNodeValue();
            		}
        		}

            	// 取得した各データをreviewInfoに格納
            	ReviewInfo reviewInfo = new ReviewInfo(this.StarBack, this.StarFront);
            	reviewInfo.setNickName(NickName);
            	reviewInfo.setNickName(NickName);
            	reviewInfo.setVisitDate(VisitDate);
            	reviewInfo.setReviewDate(ReviewDate);
            	reviewInfo.setUseType(UseType);
            	reviewInfo.setSituations(Situations);
            	reviewInfo.setTotalScore(TotalScore);
            	reviewInfo.setTasteScore(TasteScore);
            	reviewInfo.setServiceScore(ServiceScore);
            	reviewInfo.setMoodScore(MoodScore);
            	reviewInfo.setDinnerPrice(DinnerPrice);
            	reviewInfo.setLunchPrice(LunchPrice);
            	reviewInfo.setTitle(Title);
            	reviewInfo.setComment(Comment);
            	reviewInfo.setPcSiteUrl(PcSiteUrl);
            	reviewInfo.setMobileSiteUrl(MobileSiteUrl);
        		this.Reviews.add(reviewInfo);
        	}
        } catch (Exception e) {
//			showDialog(this, "", "Error1."+e.getMessage());
            e.printStackTrace();
        	return(-1);
        }

        return(ItemNodesLen);
    }

    // 画像の取得
	protected Bitmap ImportPhoto() {
        Bitmap PhotoTmp = null;
        Document doc = null;

        // 画像URL取得APIのURL
        String ImgXmlUrl = "http://api.tabelog.com/Ver1/ReviewImageSearch/?Key=96d24714e814675c7a8cd129c18608151cf2bf9b&Rcd=";
        String ImgUrl = "http://image2.tabelog.k-img.com/restaurant/images/Rvw/2875/100x100_square_2875567.jpg";

        try {
        	// 指定したURLの作成
        	URL url = new URL(ImgXmlUrl + this.Rcd);

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
        				this.Photo = PhotoTmp;
        			}
        		}
    		}
        } catch (Exception e) {
//        	showDialog(this, "", "Error2."+e.getMessage());
        	e.printStackTrace();
        }
        return(PhotoTmp);
	}

	// 評価マーク生成
	private Bitmap createMarkStar(String Score) {
		// とりあえず背景分を設定
		int width  = this.StarBack.getWidth();
		int height = this.StarBack.getHeight();
		Bitmap MarkStar = Bitmap.createBitmap(this.StarBack, 0, 0, width, height);
		// 評価分のBitmapを生成
		width  = (int)(this.StarFront.getWidth() * (Float.parseFloat(Score) / 5));
		Bitmap Tmp = Bitmap.createBitmap(this.StarFront, 0, 0, width, height);
		// 合成
		Canvas offScreen = new Canvas(MarkStar);
		offScreen.drawBitmap(Tmp, 0, 0, (Paint)null);

		return(MarkStar);
	}
}