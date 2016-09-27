package com.xuie.androidstudioaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {
    private User user = new User();
    private List<ICallback> callbacks = new ArrayList<>();

    @Override public void onCreate() {
        super.onCreate();
        user.setPhoneNumber("123456");
        user.setId(1);
        user.setName("Li");
        user.setAddress("Shen Zhen");
    }

    @Override public IBinder onBind(Intent intent) {
        return new ServiceBinder();
    }

    private class ServiceBinder extends IControlInterface.Stub {

        @Override public void setName(String name) throws RemoteException {
            user.setName(name);

            for (ICallback cb : callbacks)
                cb.update(user);
        }

        @Override public User getUser() throws RemoteException {
            return user;
        }

        @Override public void setUser(User user) throws RemoteException {
            MyService.this.user = user;
            for (ICallback cb : callbacks)
                cb.update(user);
        }

        @Override public void attach(ICallback cb) throws RemoteException {
            if (!callbacks.contains(cb)) {
                callbacks.add(cb);
            }
        }

        @Override public void detach(ICallback cb) throws RemoteException {
            if (callbacks.contains(cb)) {
                callbacks.remove(cb);
            }
        }
    }
}
