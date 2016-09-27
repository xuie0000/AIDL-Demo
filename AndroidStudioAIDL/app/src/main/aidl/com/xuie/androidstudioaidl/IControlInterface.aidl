// IControlInterface.aidl
package com.xuie.androidstudioaidl;

import com.xuie.androidstudioaidl.User;
import com.xuie.androidstudioaidl.ICallback;
// Declare any non-default types here with import statements

interface IControlInterface {
    void setName(String name);

    User getUser();

    void setUser(in User user);

    void attach(ICallback cb);

    void detach(ICallback cb);

}
