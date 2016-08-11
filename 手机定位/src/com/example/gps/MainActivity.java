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
		// 获取经纬度坐标(LocationManager)
		// 1.获取位置的管理者对象lm
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 2.通过lm获取经纬度坐标(定位方式，minTime获取经纬度的最小间隔时间，minDistance移动最小间距)
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				new LocationListener() {

					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
						// GPS状态发生切换的事件监听

					}

					@Override
					public void onProviderEnabled(String provider) {
						// GPS开启时候的事件监听

					}

					@Override
					public void onProviderDisabled(String provider) {
						// GPS关闭时候的事件监听

					}

					@Override
					public void onLocationChanged(Location location) {
						// 经度
						double longitude = location.getLongitude();
						// 纬度
						double latitude = location.getLatitude();
						// 添加经纬度需要的权限
						tv_location.setText("经度:" + longitude + "\n纬度:" + latitude);
					}
				});
	}

}
