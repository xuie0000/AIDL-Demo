// IYFServiceInterface.aidl
package com.xuie.zero.aidl_server.aidl;
import com.xuie.zero.aidl_server.aidl.User;


// Declare any non-default types here with import statements

interface IYFServiceInterface {
    void addAge();
    void setName(String name);
    User getUser();
}