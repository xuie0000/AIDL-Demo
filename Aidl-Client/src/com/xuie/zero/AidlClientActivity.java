package com.xuie.zero;

import com.xuie.zero.aidl_client.R;
import com.xuie.zero.aidl_server.aidl.IClientInterface;
import com.xuie.zero.aidl_server.aidl.IServiceInterface;
import com.xuie.zero.aidl_server.aidl.User;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class AidlClientActivity extends Activity {
	private static final String TAG = AidlClientActivity.class.getSimpleName();
	public static final String ACTION_BIND_SERVICE = "com.xuie.zero.aidl_server.service.ZeroService";

	private TextView hello;

	private IServiceInterface mService;
	private CallBack mCallBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aidl_client);

		Log.i(TAG, "onCreate");

		// Intent intent=new Intent(ACTION_BIND_SERVICE);
		// startService(intent);

		init();
	}

	private void init() {
		hello = (TextView) findViewById(R.id.hello);
		hello.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showToast("hello!");
				try {
					if (mService != null) {
						User user;
						user = mService.getUser();
						Log.i(TAG, "user:" + user.toString());

						mService.addAge();
						user = mService.getUser();
						Log.i(TAG, "user:" + user.toString());

						mService.setName("FangFang" + Math.random() * 10);
						user = mService.getUser();
						Log.i(TAG, "user:" + user.toString());
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
	}

	// client callback
	private class CallBack extends IClientInterface.Stub {

		@Override
		public void update() throws RemoteException {
			Log.i(TAG, "call back update...");
		}
	}

	// service

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.i(TAG, "onServiceDisconnected");
			mService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i(TAG, "onServiceConnected");
			mService = IServiceInterface.Stub.asInterface(service);
			if (mService == null) {
				showToast("connected interface fail!");
				Log.e(TAG, "connected interface fail!");
				return;
			}

			// set call back
			mCallBack = new CallBack();
			try {
				mService.loadAttach(mCallBack);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	protected void onStart() {
		super.onStart();
		Log.i(TAG, "onStart");
		Intent intent = new Intent(ACTION_BIND_SERVICE);
		// Intent intent = new Intent();
		// intent.setClassName("com.xuie.zero.aidl_server", ACTION_BIND_SERVICE);
		bindService(intent, serviceConnection, BIND_AUTO_CREATE);

	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i(TAG, "onStop");
		unbindService(serviceConnection);
	}

	private Toast toast;

	private void showToast(String tip) {
		if (toast != null) {
			toast.setText(tip);
		} else {
			toast = Toast.makeText(this, tip, Toast.LENGTH_SHORT);
		}
		toast.show();
	}

}
