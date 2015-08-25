/**
 * ZeroService.java
 * @author xuie0000@163.com create on 2015-7-20
 */
package com.xuie.zero.aidl_server.service;

import com.xuie.zero.aidl_server.aidl.IClientInterface;
import com.xuie.zero.aidl_server.aidl.User;
import com.xuie.zero.aidl_server.aidl.IServiceInterface;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class ZeroService extends Service {

	private ServiceBinder binder;

	public ZeroService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		if (binder == null)
			binder = new ServiceBinder();
		return binder;
	}

	private IClientInterface listener;

	private class ServiceBinder extends IServiceInterface.Stub {
		private User user;

		public ServiceBinder() {
			user = new User(21, "XiaoMing");
		}

		@Override
		public void addAge() throws RemoteException {
			user.setAge(user.getAge() + 1);
		}

		@Override
		public void setName(String name) throws RemoteException {
			user.setName(name);
		}

		@Override
		public User getUser() throws RemoteException {
			// server call client
			if (listener != null) {
				listener.update();
			}
			
			return user;
		}

		@Override
		public void loadAttach(IClientInterface listener) throws RemoteException {
			ZeroService.this.listener = listener;
		}
	}

}
