package com.example.gps;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tv_location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tv_location = (TextView) findViewById(R.id.tv_location);
		// ��ȡ��γ������(LocationManager)
		// 1.��ȡλ�õĹ����߶���lm
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 2.ͨ��lm��ȡ��γ������(��λ��ʽ��minTime��ȡ��γ�ȵ���С���ʱ�䣬minDistance�ƶ���С���)
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				new LocationListener() {

					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
						// GPS״̬�����л����¼�����

					}

					@Override
					public void onProviderEnabled(String provider) {
						// GPS����ʱ����¼�����

					}

					@Override
					public void onProviderDisabled(String provider) {
						// GPS�ر�ʱ����¼�����

					}

					@Override
					public void onLocationChanged(Location location) {
						// ����
						double longitude = location.getLongitude();
						// γ��
						double latitude = location.getLatitude();
						// ��Ӿ�γ����Ҫ��Ȩ��
						tv_location.setText("����:" + longitude + "\nγ��:" + latitude);
					}
				});
	}

}
