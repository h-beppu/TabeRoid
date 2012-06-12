package com.hide_ab.TabeRoid;

import java.util.Date;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.EditText;

public class TabeRoid extends Activity implements LocationListener {
	// �������ʓX�܃f�[�^�I�u�W�F�N�g
	protected ShopInfos shopinfos;
	// ��������
	private LocationManager mLm;
    private String Lat = "35.70209";
    private String Lon = "139.73744";
    private String Station = "���z";
    private EditText edittext_station;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ��ʍ\����K�p
        setContentView(R.layout.taberoid);

		// �������ʓX�܃f�[�^�I�u�W�F�N�g����
	    this.shopinfos = (ShopInfos)this.getApplication();
	    
	    // GPS������
        mLm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        mLm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
//        mLm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);

        // �w�Ŋ�w�x�G�f�B�b�g�e�L�X�g�擾
        edittext_station = (EditText)findViewById(R.id.edittext_station);
        // �w�����x�{�^���擾
        Button button_search_gps = (Button)findViewById(R.id.button_search_gps);
        Button button_search_station = (Button)findViewById(R.id.button_search_station);

//		this.shopinfos.DefaultPhoto = BitmapFactory.decodeResource(r, R.drawable.icon);

		// �w�����x�{�^���N���b�N�n���h��
        button_search_gps.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		// GPS�̍X�V���~
        		mLm.removeUpdates(TabeRoid.this);

        		// ���̓o�b�N�O���E���h�Ŏ擾
        		shopinfos.preSearch("gps", Lat, Lon, "");
            	TabeRoidTask task = new TabeRoidTask(shopinfos, TabeRoid.this);
            	task.execute();

            	// �������ʂ̓X�܏����擾
//        	    int ItemNum = shopinfos.ImportData("gps", Lat, Lon, "", TabeRoid.this);
/*
        	    // "ShopList"��ʂɈڍs
        		Intent intent = new Intent(TabeRoid.this, ShopList.class);
        		intent.putExtra("Key", "gps");
    			intent.putExtra("Lat", Lat);
    			intent.putExtra("Lon", Lon);
        		TextView text_location = (TextView)findViewById(R.id.text_location);
        		text_location.setText("Click");
    			startActivityForResult(intent, 0);
*/
        	}
        });

        // �w�����x�{�^���N���b�N�n���h��
        button_search_station.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		// GPS�̍X�V���~
        		mLm.removeUpdates(TabeRoid.this);

        		// ���͂��ꂽ�w�Ŋ�w�x���擾
        		Station = edittext_station.getText().toString();

        		// ���̓o�b�N�O���E���h�Ŏ擾
        		shopinfos.preSearch("station", "", "", Station);
            	TabeRoidTask task = new TabeRoidTask(shopinfos, TabeRoid.this);
            	task.execute();
        	}
        });
    }

	// "ShopList"��ʂɈڍs
    public void openShopList() {
		Intent intent = new Intent(TabeRoid.this, ShopList.class);
//		intent.putExtra("Key", "station");
//		intent.putExtra("Stations", Stations);
//		TextView locationText = (TextView)findViewById(R.id.text_location);
//        locationText.setText("Click");
		startActivityForResult(intent, 0);
    }

	//    @Override
    public void onLocationChanged(Location location) {
        TextView text_location = (TextView)findViewById(R.id.text_location);
        String str;
        Date d ;

        //Toast.makeText(this, "onLocationChanged()", Toast.LENGTH_SHORT).show();
        d = new Date(location.getTime());
        str  = "Time:" + d.toString() + "\n";
        str += "Latitude:" + String.valueOf(location.getLatitude()) + "\n";
        str += "Longitude:" + String.valueOf(location.getLongitude()) + "\n";
        str += "Accuracy:" + String.valueOf(location.getAccuracy()) + "\n";
        str += "Altitude:" + String.valueOf(location.getAltitude()) + "\n";
        str += "Bearing:" + String.valueOf(location.getBearing()) + "\n";
        str += "Speed:" + String.valueOf(location.getSpeed()) + "\n";

        Lat = String.valueOf(location.getLatitude());
        Lon = String.valueOf(location.getLongitude());

        text_location.setText(str);
    }

//    @Override
    public void onProviderDisabled(String provider) {
    	TextView text_provider = (TextView)findViewById(R.id.text_provider);

        Toast.makeText(this, "onProviderDisabled()", Toast.LENGTH_SHORT).show();
        text_provider.setText("provider:"+provider);
    }

//    @Override
    public void onProviderEnabled(String provider) {
        TextView text_provider = (TextView)findViewById(R.id.text_provider);

        Toast.makeText(this, "onProviderEnabled()", Toast.LENGTH_SHORT).show();
        text_provider.setText("provider:"+provider);
    }

//    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
//        TextView text_provider = (TextView)findViewById(R.id.text_provider);
        TextView text_status = (TextView)findViewById(R.id.text_status);

        //Toast.makeText(this, "onStatusChanged()", Toast.LENGTH_SHORT).show();

        text_status.setText("provider:"+provider);
        switch (status) {
            case LocationProvider.AVAILABLE:
            	text_status.setText("status:AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
            	text_status.setText("status:OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
            	text_status.setText("status:TEMPORARILY_UNAVAILABLE");
                break;
        }
    }

    //
    //
    //
	class TabeRoidTask extends AsyncTask<Integer, Integer, Integer> {
		// �������ʓX�܃f�[�^�I�u�W�F�N�g
		protected ShopInfos shopinfos;
		// �A�N�e�B�r�e�B
		protected TabeRoid taberoid;
		protected ProgressDialog progressdialog;
		protected int Num;

		// �R���X�g���N�^
	    public TabeRoidTask(ShopInfos shopinfos, TabeRoid taberoid) {
	    	this.shopinfos = shopinfos;
	    	this.taberoid = taberoid;

	    	// �f�t�H���g�ʐ^�̐ݒ�
			Resources r = this.taberoid.getResources();
	    	this.shopinfos.DefaultPhoto = BitmapFactory.decodeResource(r, R.drawable.icon);
	    	// �]���}�[�N�f��
	    	this.shopinfos.StarBack  = BitmapFactory.decodeResource(r, R.drawable.star_back);
	    	this.shopinfos.StarFront = BitmapFactory.decodeResource(r, R.drawable.star_front);
	    }

		@Override
		protected void onPreExecute() {
	    	// �o�b�N�O���E���h�̏����O��UI�X���b�h�Ń_�C�A���O�\��
			progressdialog = new ProgressDialog(this.taberoid);
			progressdialog.setMessage(this.taberoid.getResources().getText(R.string.label_dataloading));
			progressdialog.setIndeterminate(true);
			progressdialog.show();
		}

		// �o�b�N�O���E���h�Ŏ��s���鏈��
	    @Override
	    protected Integer doInBackground(Integer... params) {
	    	this.Num = this.shopinfos.ImportData();
	    	return(this.Num);
	    }

	    // ���C���X���b�h�Ŏ��s���鏈��
	    @Override
	    protected void onPostExecute(Integer params) {
			// �������_�C�A���O���N���[�Y
	    	progressdialog.dismiss();

	    	TextView locationText = (TextView)this.taberoid.findViewById(R.id.text_location);
	        locationText.setText("Click" + this.Num);

			// "ShopList"��ʂɈڍs
			this.taberoid.openShopList();
	    }
	}
}