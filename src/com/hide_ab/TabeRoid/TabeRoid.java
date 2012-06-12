package com.hide_ab.TabeRoid;

import java.util.Date;
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

public class TabeRoid extends BaseActivity implements LocationListener {
	// ��������
	private LocationManager mLm;
    private String Lat = "35.70209";
    private String Lon = "139.73744";

    private EditText edittext_station;
    private Button button_search_gps_large;
    private Button button_search_gps_medium;
    private Button button_search_gps_small;
    private Button button_search_station;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ��ʍ\����K�p
        setContentView(R.layout.taberoid);

		// �摜�f�ނ̐ݒ�
		Resources r = getResources();
		this.shopinfos.setDefaultPhoto(BitmapFactory.decodeResource(r, R.drawable.icon));
		this.shopinfos.setStar(BitmapFactory.decodeResource(r, R.drawable.star_back),
								BitmapFactory.decodeResource(r, R.drawable.star_front));

		// GPS������
		this.mLm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        // �w�Ŋ�w�x�G�f�B�b�g�e�L�X�g�擾
		this.edittext_station = (EditText)findViewById(R.id.edittext_station);
        // �w�����x�{�^���擾
		this.button_search_gps_large  = (Button)findViewById(R.id.button_search_gps_large);
		this.button_search_gps_medium = (Button)findViewById(R.id.button_search_gps_medium);
		this.button_search_gps_small  = (Button)findViewById(R.id.button_search_gps_small);
		this.button_search_station    = (Button)findViewById(R.id.button_search_station);

		// �w�����x�{�^���N���b�N�n���h��
		this.button_search_gps_large.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		// �������s
        		openTabeRoidTask("gps", Lat, Lon, "large", "");
        	}
        });
		this.button_search_gps_medium.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		// �������s
        		openTabeRoidTask("gps", Lat, Lon, "medium", "");
        	}
        });
		this.button_search_gps_small.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		// �������s
        		openTabeRoidTask("gps", Lat, Lon, "small", "");
        	}
        });
		this.button_search_station.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		// ���͂��ꂽ�w�Ŋ�w�x���擾
        		String Station = edittext_station.getText().toString();
        		// �������s
        		openTabeRoidTask("station", "", "", "", Station);
        	}
        });
    }

    @Override
    public void onResume() {
        super.onResume();

		// GPS�J�n
		this.mLm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
//      this.mLm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);

		// GPS�����{�^���𖳌���
		this.button_search_gps_large.setEnabled(false);
		this.button_search_gps_medium.setEnabled(false);
		this.button_search_gps_small.setEnabled(false);
    }

	// �����^�X�N���s
    public void openTabeRoidTask(String SearchKey, String Lat, String Lon, String SearchRange, String Station) {
		// ���������ݒ�
		shopinfos.preSearch(SearchKey, Lat, Lon, SearchRange, Station);
		// ���̓o�b�N�O���E���h�Ŏ擾
    	TabeRoidTask task = new TabeRoidTask();
    	task.execute();
    }

	// �����^�X�N����
    public void closeTabeRoidTask(int Result) {
        // ������
    	if(Result > 0) {
    		// GPS�̍X�V���~
    		mLm.removeUpdates(TabeRoid.this);

    		// "ShopList"��ʂɈڍs
    		Intent intent = new Intent(TabeRoid.this, ShopList.class);
    		startActivityForResult(intent, 0);
    	}
        // �񔭌���
    	else {
            Toast.makeText(this, getResources().getText(R.string.msg_notfound), Toast.LENGTH_SHORT).show();
    	}
    }

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

        // GPS�����{�^����L����
        button_search_gps_large.setEnabled(true);
        button_search_gps_medium.setEnabled(true);
        button_search_gps_small.setEnabled(true);

        text_location.setText(str);
    }

    public void onProviderDisabled(String provider) {
    	TextView text_provider = (TextView)findViewById(R.id.text_provider);

        Toast.makeText(this, "onProviderDisabled()", Toast.LENGTH_SHORT).show();
        text_provider.setText("provider:"+provider);
    }

    public void onProviderEnabled(String provider) {
        TextView text_provider = (TextView)findViewById(R.id.text_provider);

        Toast.makeText(this, "onProviderEnabled()", Toast.LENGTH_SHORT).show();
        text_provider.setText("provider:"+provider);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
//      TextView text_provider = (TextView)findViewById(R.id.text_provider);
        TextView text_status = (TextView)findViewById(R.id.text_status);

//		Toast.makeText(this, "onStatusChanged()", Toast.LENGTH_SHORT).show();

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
    // �o�b�N�O���E���h�^�X�N
    //
	class TabeRoidTask extends AsyncTask<Integer, Void, Integer> {
		protected ProgressDialog progressdialog;

		@Override
		protected void onPreExecute() {
	    	// �o�b�N�O���E���h�̏����O��UI�X���b�h�Ń_�C�A���O�\��
			this.progressdialog = new ProgressDialog(TabeRoid.this);
			this.progressdialog.setMessage(getResources().getText(R.string.label_dataloading));
			this.progressdialog.setIndeterminate(true);
			this.progressdialog.show();
		}

		// �o�b�N�O���E���h�Ŏ��s���鏈��
	    @Override
	    protected Integer doInBackground(Integer... params) {
	    	int Result = shopinfos.ImportData();
	    	return(Result);
	    }

	    // ���C���X���b�h�Ŏ��s���鏈��
	    @Override
	    protected void onPostExecute(Integer Result) {
			// �������_�C�A���O���N���[�Y
	    	this.progressdialog.dismiss();

	    	TextView text_location = (TextView)findViewById(R.id.text_location);
	    	text_location.setText("Click" + Result);

			// �^�X�N����
	    	closeTabeRoidTask(Result);
	    }
	}
}