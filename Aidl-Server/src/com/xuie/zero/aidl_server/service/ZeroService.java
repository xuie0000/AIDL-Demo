/**
 * ZeroService.java
 * @author xuie0000@163.com create on 2015-7-20
 */
package com.xuie.zero.aidl_server.service;

import com.xuie.zero.aidl_server.aidl.User;
import com.xuie.zero.aidl_server.aidl.IYFServiceInterface;

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

	private class ServiceBinder extends IYFServiceInterface.Stub {
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
			return user;
		}
	}

}
