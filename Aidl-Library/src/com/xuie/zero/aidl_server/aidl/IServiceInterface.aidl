// IServiceInterface.aidl
package com.xuie.zero.aidl_server.aidl;
import com.xuie.zero.aidl_server.aidl.User;
import com.xuie.zero.aidl_server.aidl.IClientInterface;

// Declare any non-default types here with import statements

interface IServiceInterface {
    void addAge();
    void setName(String name);
    User getUser();
    void loadAttach(IClientInterface listener);
}