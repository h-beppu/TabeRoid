package com.hide_ab.TabeRoid;

import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
//import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.ListView;

public class ShopList extends Activity {
	// 検索結果店舗データオブジェクト
	protected ShopInfos shopinfos;
	// ListAdapter
	private ShopListAdapter shoplistadapter = null;
	protected static final String XML_URL = "http://api.tabelog.com/Ver2.1/RestaurantSearch/?Key=96d24714e814675c7a8cd129c18608151cf2bf9b&";
//	protected static final String XML_URL_D = "http://api.tabelog.com/Ver2.1/RestaurantSearch/?Key=96d24714e814675c7a8cd129c18608151cf2bf9b&Datum=world&Latitude=35.726&Longitude=139.988";
	protected int PageNum = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    // 画面構成を適用
	    setContentView(R.layout.shop_list);

		// 検索結果店舗データオブジェクト生成
	    this.shopinfos = (ShopInfos)this.getApplication();

	    // 検索結果の店舗情報を取得
	    int ItemNum = this.ImportData();

	    TextView text_result = (TextView)findViewById(R.id.text_result);
	    text_result.setText(ItemNum + "件見つかりました");

	    // ListからShopAdapterを生成
	    this.shoplistadapter = new ShopListAdapter(this, R.layout.shop_listrow, shopinfos.getList());

		// 画像はバックグラウンドで取得
		ImageDrawer imagedrawer = new ImageDrawer(shopinfos, shoplistadapter);   
		imagedrawer.execute();

    	// ShopAdapterをShopList.xml内にあるlistview_resultsに渡して内容を表示する
    	ListView listview_results = (ListView)findViewById(R.id.listview_results);
    	// listview_resultsにフッターを追加
    	listview_results.addFooterView(getLayoutInflater().inflate(R.layout.shop_listfooter, null), null, true);
    	// listview_resultsにshoplistadapterをセット
    	listview_results.setAdapter(shoplistadapter);

    	// listview_resultsにOnItemClickListenerを設定
    	listview_results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
	    		// フッタをクリックされた場合
	    		if(view.getId() == R.id.Footer) {
	    		    // 検索結果の店舗情報を取得(追加分)
//	    			ImportData();

	    			// 画像はバックグラウンドで取得
//	    			ImageDrawer imagedrawer = new ImageDrawer(shopinfos, shoplistadapter);   
//	    			imagedrawer.execute();

	    	    	// listview_resultsからフッターを削除
	    	    	ListView listview_results = (ListView)findViewById(R.id.listview_results);
	    	    	listview_results.removeFooterView(getLayoutInflater().inflate(R.layout.shop_listfooter, null));

	    	    	// 再描画
//	    	    	listview_results.invalidateViews();
//	    			shoplistadapter.notifyDataSetChanged();
	    		}
	    		// フッタ以外(店舗情報)をクリックされた場合
	    		else {
	    			ShopInfo shopInfo = shopinfos.getInfo(position);
	    			if(shopInfo.getRcd() != "") {
//	    				showDialog(ShopList.this, "", "ボタンを押した"+shopInfo.getRcd());
	    				Intent intent = new Intent(ShopList.this, ShopDetail.class);
	    				intent.putExtra("Position", position);
	    				startActivityForResult(intent, 0);
	    			}
	    		}
	    	}
	    }); 
	}

    // 検索結果の店舗情報を取得
	protected int ImportData() {
    	ShopInfo shopInfo;
    	int ItemNodesLen = 0;

		URL url = null;
        Document doc = null;

        // ページをインクリメント
        this.PageNum++;

        try {
            // 呼び出し元からパラメータ取得
            Intent intent = getIntent();
            String SearchKey = intent.getStringExtra("Key");
            String XmlUrl;

            if(SearchKey.equals("gps")) {
                String Lat = intent.getStringExtra("Lat");
                String Lon = intent.getStringExtra("Lon");
            	XmlUrl = XML_URL + "Datum=world&Latitude=" + Lat + "&Longitude=" + Lon;
            } else {
                String Stations = intent.getStringExtra("Stations");
            	XmlUrl = XML_URL + "Station=" + URLEncoder.encode(Stations);
            }
//          XmlUrl = XML_URL_D;
            XmlUrl += "&PageNum=" + this.PageNum;

            // 指定した URL の作成
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

			for(int i=0; i < ItemNodesLen; i++) {
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
    			shopInfo = new ShopInfo();
       			shopInfo.setRcd(Rcd);
       			shopInfo.setRestaurantName(RestaurantName);
       			shopInfo.setTabelogUrl(TabelogUrl);
       			shopInfo.setTotalScore(TotalScore);
       			shopInfo.setCategory(Category);
       			shopInfo.setStation(Station);

       			// リストに追加
       			shopinfos.putInfo(shopInfo);
        	}
        } catch (Exception e) {
			showDialog(this, "", "Error1."+e.getMessage());
            e.printStackTrace();
        }
        
        return(ItemNodesLen);
    }

	// ダイアログの表示
    protected void showDialog(final Activity activity, String title, String text) {
    	AlertDialog.Builder ad = new AlertDialog.Builder(activity);
    	ad.setTitle(title);
    	ad.setMessage(text);
    	ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int wichiButton) {
    			activity.setResult(Activity.RESULT_OK);
    		}
    	});
    	ad.create();
    	ad.show();
    }
}