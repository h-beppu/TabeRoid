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
	// �������ʓX�܃f�[�^�I�u�W�F�N�g
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

	    // ��ʍ\����K�p
	    setContentView(R.layout.shop_list);

		// �������ʓX�܃f�[�^�I�u�W�F�N�g����
	    this.shopinfos = (ShopInfos)this.getApplication();

	    // �������ʂ̓X�܏����擾
	    int ItemNum = this.ImportData();

	    TextView text_result = (TextView)findViewById(R.id.text_result);
	    text_result.setText(ItemNum + "��������܂���");

	    // List����ShopAdapter�𐶐�
	    this.shoplistadapter = new ShopListAdapter(this, R.layout.shop_listrow, shopinfos.getList());

		// �摜�̓o�b�N�O���E���h�Ŏ擾
		ImageDrawer imagedrawer = new ImageDrawer(shopinfos, shoplistadapter);   
		imagedrawer.execute();

    	// ShopAdapter��ShopList.xml���ɂ���listview_results�ɓn���ē��e��\������
    	ListView listview_results = (ListView)findViewById(R.id.listview_results);
    	// listview_results�Ƀt�b�^�[��ǉ�
    	listview_results.addFooterView(getLayoutInflater().inflate(R.layout.shop_listfooter, null), null, true);
    	// listview_results��shoplistadapter���Z�b�g
    	listview_results.setAdapter(shoplistadapter);

    	// listview_results��OnItemClickListener��ݒ�
    	listview_results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
	    		// �t�b�^���N���b�N���ꂽ�ꍇ
	    		if(view.getId() == R.id.Footer) {
	    		    // �������ʂ̓X�܏����擾(�ǉ���)
//	    			ImportData();

	    			// �摜�̓o�b�N�O���E���h�Ŏ擾
//	    			ImageDrawer imagedrawer = new ImageDrawer(shopinfos, shoplistadapter);   
//	    			imagedrawer.execute();

	    	    	// listview_results����t�b�^�[���폜
	    	    	ListView listview_results = (ListView)findViewById(R.id.listview_results);
	    	    	listview_results.removeFooterView(getLayoutInflater().inflate(R.layout.shop_listfooter, null));

	    	    	// �ĕ`��
//	    	    	listview_results.invalidateViews();
//	    			shoplistadapter.notifyDataSetChanged();
	    		}
	    		// �t�b�^�ȊO(�X�܏��)���N���b�N���ꂽ�ꍇ
	    		else {
	    			ShopInfo shopInfo = shopinfos.getInfo(position);
	    			if(shopInfo.getRcd() != "") {
//	    				showDialog(ShopList.this, "", "�{�^����������"+shopInfo.getRcd());
	    				Intent intent = new Intent(ShopList.this, ShopDetail.class);
	    				intent.putExtra("Position", position);
	    				startActivityForResult(intent, 0);
	    			}
	    		}
	    	}
	    }); 
	}

    // �������ʂ̓X�܏����擾
	protected int ImportData() {
    	ShopInfo shopInfo;
    	int ItemNodesLen = 0;

		URL url = null;
        Document doc = null;

        // �y�[�W���C���N�������g
        this.PageNum++;

        try {
            // �Ăяo��������p�����[�^�擾
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

            // �w�肵�� URL �̍쐬
            url = new URL(XmlUrl);

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

    			// �擾�����e�f�[�^��shopInfo�Ɋi�[
    			shopInfo = new ShopInfo();
       			shopInfo.setRcd(Rcd);
       			shopInfo.setRestaurantName(RestaurantName);
       			shopInfo.setTabelogUrl(TabelogUrl);
       			shopInfo.setTotalScore(TotalScore);
       			shopInfo.setCategory(Category);
       			shopInfo.setStation(Station);

       			// ���X�g�ɒǉ�
       			shopinfos.putInfo(shopInfo);
        	}
        } catch (Exception e) {
			showDialog(this, "", "Error1."+e.getMessage());
            e.printStackTrace();
        }
        
        return(ItemNodesLen);
    }

	// �_�C�A���O�̕\��
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