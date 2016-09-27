// ICallback.aidl
package com.xuie.androidstudioaidl;

// Declare any non-default types here with import statements
import com.xuie.androidstudioaidl.User;

interface ICallback {
    void update(in User user);
}
