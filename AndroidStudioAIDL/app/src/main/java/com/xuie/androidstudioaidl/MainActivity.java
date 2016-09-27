package com.xuie.androidstudioaidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.xuie.androidstudioaidl.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private IControlInterface mService;
    private CallBack mCallBack = new CallBack();

    public void changeName(View view) {
        if (mService == null)
            return;

        try {
            User user = mService.getUser();
            mService.setName(user.getName() + Math.random());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void changeUser(View view) {
        if (mService == null)
            return;

        try {
            User user = mService.getUser();
            user.setName(user.getName() + Math.random());
            user.setPhoneNumber(user.getPhoneNumber() + Math.random());
            user.setAddress(user.getAddress() + Math.random());
            mService.setUser(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // client callback
    private class CallBack extends ICallback.Stub {
        @Override public void update(User user) throws RemoteException {
            Log.i(TAG, "call back update... ");
            Log.i(TAG, user.toString());
            activityMainBinding.setUser(user);
        }
    }


    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected");
            try {
                mService.detach(mCallBack);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mService = null;
        }

        @Override public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected");
            mService = IControlInterface.Stub.asInterface(service);
            if (mService == null) {
                Log.e(TAG, "connected interface fail!");
                return;
            }

            // set call back
            try {
                mService.attach(mCallBack);
                activityMainBinding.setUser(mService.getUser());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    private ActivityMainBinding activityMainBinding;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        startService(new Intent(this, MyService.class));
    }


    @Override protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
    }
}
