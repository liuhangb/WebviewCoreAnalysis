package org.chromium.net;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build.VERSION;
import java.util.ArrayList;
import java.util.Iterator;
import org.chromium.base.ContextUtils;
import org.chromium.base.ObserverList;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.NativeClassQualifiedName;
import org.chromium.tencent.SmttServiceClientProxy;

@JNINamespace("net")
public class NetworkChangeNotifier
{
  @SuppressLint({"StaticFieldLeak"})
  private static NetworkChangeNotifier jdField_a_of_type_OrgChromiumNetNetworkChangeNotifier;
  private int jdField_a_of_type_Int = 0;
  private final ConnectivityManager jdField_a_of_type_AndroidNetConnectivityManager = (ConnectivityManager)ContextUtils.getApplicationContext().getSystemService("connectivity");
  private final ArrayList<Long> jdField_a_of_type_JavaUtilArrayList = new ArrayList();
  private final ObserverList<ConnectionTypeObserver> jdField_a_of_type_OrgChromiumBaseObserverList = new ObserverList();
  private NetworkChangeNotifierAutoDetect jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect;
  
  private void a()
  {
    NetworkChangeNotifierAutoDetect localNetworkChangeNotifierAutoDetect = this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect;
    if (localNetworkChangeNotifierAutoDetect != null)
    {
      localNetworkChangeNotifierAutoDetect.a();
      this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect = null;
    }
  }
  
  private void a(int paramInt, long paramLong)
  {
    Iterator localIterator = this.jdField_a_of_type_JavaUtilArrayList.iterator();
    while (localIterator.hasNext()) {
      nativeNotifyConnectionTypeChanged(((Long)localIterator.next()).longValue(), paramInt, paramLong);
    }
    localIterator = this.jdField_a_of_type_OrgChromiumBaseObserverList.iterator();
    while (localIterator.hasNext()) {
      ((ConnectionTypeObserver)localIterator.next()).onConnectionTypeChanged(paramInt);
    }
  }
  
  private void a(ConnectionTypeObserver paramConnectionTypeObserver)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList.addObserver(paramConnectionTypeObserver);
  }
  
  private void a(boolean paramBoolean, NetworkChangeNotifierAutoDetect.RegistrationPolicy paramRegistrationPolicy)
  {
    if (paramBoolean)
    {
      if (this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect == null)
      {
        this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect = new NetworkChangeNotifierAutoDetect(new NetworkChangeNotifierAutoDetect.Observer()
        {
          public void onConnectionSubtypeChanged(int paramAnonymousInt)
          {
            NetworkChangeNotifier.this.b(paramAnonymousInt);
          }
          
          public void onConnectionTypeChanged(int paramAnonymousInt)
          {
            NetworkChangeNotifier.a(NetworkChangeNotifier.this, paramAnonymousInt);
          }
          
          public void onConnectionVPNChanged(boolean paramAnonymousBoolean)
          {
            NetworkChangeNotifier.this.a(paramAnonymousBoolean);
          }
          
          public void onNetworkConnect(long paramAnonymousLong, int paramAnonymousInt)
          {
            NetworkChangeNotifier.this.a(paramAnonymousLong, paramAnonymousInt);
          }
          
          public void onNetworkDisconnect(long paramAnonymousLong)
          {
            NetworkChangeNotifier.this.b(paramAnonymousLong);
          }
          
          public void onNetworkSoonToDisconnect(long paramAnonymousLong)
          {
            NetworkChangeNotifier.this.a(paramAnonymousLong);
          }
          
          public void purgeActiveNetworkList(long[] paramAnonymousArrayOfLong)
          {
            NetworkChangeNotifier.this.a(paramAnonymousArrayOfLong);
          }
        }, paramRegistrationPolicy);
        paramRegistrationPolicy = this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect.a();
        c(paramRegistrationPolicy.c());
        b(paramRegistrationPolicy.d());
      }
    }
    else {
      a();
    }
  }
  
  @TargetApi(23)
  private boolean a()
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = false;
    boolean bool1 = false;
    if (i < 21) {
      return false;
    }
    if (Build.VERSION.SDK_INT < 23)
    {
      if (ConnectivityManager.getProcessDefaultNetwork() != null) {
        bool1 = true;
      }
      return bool1;
    }
    bool1 = bool2;
    if (this.jdField_a_of_type_AndroidNetConnectivityManager.getBoundNetworkForProcess() != null) {
      bool1 = true;
    }
    return bool1;
  }
  
  public static void addConnectionTypeObserver(ConnectionTypeObserver paramConnectionTypeObserver)
  {
    getInstance().a(paramConnectionTypeObserver);
  }
  
  private void b(ConnectionTypeObserver paramConnectionTypeObserver)
  {
    this.jdField_a_of_type_OrgChromiumBaseObserverList.removeObserver(paramConnectionTypeObserver);
  }
  
  private void b(boolean paramBoolean)
  {
    int j = this.jdField_a_of_type_Int;
    int i = 0;
    boolean bool;
    if (j != 6) {
      bool = true;
    } else {
      bool = false;
    }
    if (bool != paramBoolean)
    {
      if (!paramBoolean) {
        i = 6;
      }
      c(i);
      b(paramBoolean ^ true);
    }
  }
  
  private void c(int paramInt)
  {
    this.jdField_a_of_type_Int = paramInt;
    a(paramInt);
  }
  
  @CalledByNative
  public static void fakeConnectionSubtypeChanged(int paramInt)
  {
    setAutoDetectConnectivityState(false);
    getInstance().b(paramInt);
  }
  
  @CalledByNative
  public static void fakeDefaultNetwork(long paramLong, int paramInt)
  {
    setAutoDetectConnectivityState(false);
    getInstance().a(paramInt, paramLong);
  }
  
  @CalledByNative
  public static void fakeNetworkConnected(long paramLong, int paramInt)
  {
    setAutoDetectConnectivityState(false);
    getInstance().a(paramLong, paramInt);
  }
  
  @CalledByNative
  public static void fakeNetworkDisconnected(long paramLong)
  {
    setAutoDetectConnectivityState(false);
    getInstance().b(paramLong);
  }
  
  @CalledByNative
  public static void fakeNetworkSoonToBeDisconnected(long paramLong)
  {
    setAutoDetectConnectivityState(false);
    getInstance().a(paramLong);
  }
  
  @CalledByNative
  public static void fakePurgeActiveNetworkList(long[] paramArrayOfLong)
  {
    setAutoDetectConnectivityState(false);
    getInstance().a(paramArrayOfLong);
  }
  
  @CalledByNative
  public static void forceConnectivityState(boolean paramBoolean)
  {
    setAutoDetectConnectivityState(false);
    getInstance().b(paramBoolean);
  }
  
  public static NetworkChangeNotifierAutoDetect getAutoDetectorForTest()
  {
    return getInstance().jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect;
  }
  
  public static NetworkChangeNotifier getInstance()
  {
    if ((!jdField_a_of_type_Boolean) && (jdField_a_of_type_OrgChromiumNetNetworkChangeNotifier == null)) {
      throw new AssertionError();
    }
    return jdField_a_of_type_OrgChromiumNetNetworkChangeNotifier;
  }
  
  @CalledByNative
  public static NetworkChangeNotifier init()
  {
    if (jdField_a_of_type_OrgChromiumNetNetworkChangeNotifier == null) {
      jdField_a_of_type_OrgChromiumNetNetworkChangeNotifier = new NetworkChangeNotifier();
    }
    return jdField_a_of_type_OrgChromiumNetNetworkChangeNotifier;
  }
  
  public static boolean isInitialized()
  {
    return jdField_a_of_type_OrgChromiumNetNetworkChangeNotifier != null;
  }
  
  public static boolean isOnline()
  {
    return getInstance().getCurrentConnectionType() != 6;
  }
  
  @CalledByNative
  public static boolean isProcessBoundToNetwork()
  {
    return getInstance().a();
  }
  
  @NativeClassQualifiedName("NetworkChangeNotifierDelegateAndroid")
  private native void nativeNotifyConnectionTypeChanged(long paramLong1, int paramInt, long paramLong2);
  
  @NativeClassQualifiedName("NetworkChangeNotifierDelegateAndroid")
  private native void nativeNotifyMaxBandwidthChanged(long paramLong, int paramInt);
  
  @NativeClassQualifiedName("NetworkChangeNotifierDelegateAndroid")
  private native void nativeNotifyOfNetworkConnect(long paramLong1, long paramLong2, int paramInt);
  
  @NativeClassQualifiedName("NetworkChangeNotifierDelegateAndroid")
  private native void nativeNotifyOfNetworkDisconnect(long paramLong1, long paramLong2);
  
  @NativeClassQualifiedName("NetworkChangeNotifierDelegateAndroid")
  private native void nativeNotifyOfNetworkSoonToDisconnect(long paramLong1, long paramLong2);
  
  @NativeClassQualifiedName("NetworkChangeNotifierDelegateAndroid")
  private native void nativeNotifyPurgeActiveNetworkList(long paramLong, long[] paramArrayOfLong);
  
  public static void registerToReceiveNotificationsAlways()
  {
    getInstance().a(true, new RegistrationPolicyAlwaysRegister());
  }
  
  public static void removeConnectionTypeObserver(ConnectionTypeObserver paramConnectionTypeObserver)
  {
    getInstance().b(paramConnectionTypeObserver);
  }
  
  public static void setAutoDetectConnectivityState(NetworkChangeNotifierAutoDetect.RegistrationPolicy paramRegistrationPolicy)
  {
    getInstance().a(true, paramRegistrationPolicy);
  }
  
  public static void setAutoDetectConnectivityState(boolean paramBoolean)
  {
    getInstance().a(paramBoolean, new RegistrationPolicyApplicationStatus());
  }
  
  void a(int paramInt)
  {
    a(paramInt, getCurrentDefaultNetId());
  }
  
  void a(long paramLong)
  {
    Iterator localIterator = this.jdField_a_of_type_JavaUtilArrayList.iterator();
    while (localIterator.hasNext()) {
      nativeNotifyOfNetworkSoonToDisconnect(((Long)localIterator.next()).longValue(), paramLong);
    }
  }
  
  void a(long paramLong, int paramInt)
  {
    Iterator localIterator = this.jdField_a_of_type_JavaUtilArrayList.iterator();
    while (localIterator.hasNext()) {
      nativeNotifyOfNetworkConnect(((Long)localIterator.next()).longValue(), paramLong, paramInt);
    }
  }
  
  void a(boolean paramBoolean)
  {
    SmttServiceClientProxy.getInstance().notifyVPNChanged(paramBoolean);
  }
  
  void a(long[] paramArrayOfLong)
  {
    Iterator localIterator = this.jdField_a_of_type_JavaUtilArrayList.iterator();
    while (localIterator.hasNext()) {
      nativeNotifyPurgeActiveNetworkList(((Long)localIterator.next()).longValue(), paramArrayOfLong);
    }
  }
  
  @CalledByNative
  public void addNativeObserver(long paramLong)
  {
    this.jdField_a_of_type_JavaUtilArrayList.add(Long.valueOf(paramLong));
  }
  
  void b(int paramInt)
  {
    Iterator localIterator = this.jdField_a_of_type_JavaUtilArrayList.iterator();
    while (localIterator.hasNext()) {
      nativeNotifyMaxBandwidthChanged(((Long)localIterator.next()).longValue(), paramInt);
    }
  }
  
  void b(long paramLong)
  {
    Iterator localIterator = this.jdField_a_of_type_JavaUtilArrayList.iterator();
    while (localIterator.hasNext()) {
      nativeNotifyOfNetworkDisconnect(((Long)localIterator.next()).longValue(), paramLong);
    }
  }
  
  @CalledByNative
  public int getCurrentConnectionSubtype()
  {
    NetworkChangeNotifierAutoDetect localNetworkChangeNotifierAutoDetect = this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect;
    if (localNetworkChangeNotifierAutoDetect == null) {
      return 0;
    }
    return localNetworkChangeNotifierAutoDetect.a().d();
  }
  
  @CalledByNative
  public int getCurrentConnectionType()
  {
    return this.jdField_a_of_type_Int;
  }
  
  @CalledByNative
  public long getCurrentDefaultNetId()
  {
    NetworkChangeNotifierAutoDetect localNetworkChangeNotifierAutoDetect = this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect;
    if (localNetworkChangeNotifierAutoDetect == null) {
      return -1L;
    }
    return localNetworkChangeNotifierAutoDetect.a();
  }
  
  @CalledByNative
  public long[] getCurrentNetworksAndTypes()
  {
    NetworkChangeNotifierAutoDetect localNetworkChangeNotifierAutoDetect = this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect;
    if (localNetworkChangeNotifierAutoDetect == null) {
      return new long[0];
    }
    return localNetworkChangeNotifierAutoDetect.a();
  }
  
  @CalledByNative
  public boolean registerNetworkCallbackFailed()
  {
    NetworkChangeNotifierAutoDetect localNetworkChangeNotifierAutoDetect = this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect;
    if (localNetworkChangeNotifierAutoDetect == null) {
      return false;
    }
    return localNetworkChangeNotifierAutoDetect.a();
  }
  
  @CalledByNative
  public void removeNativeObserver(long paramLong)
  {
    this.jdField_a_of_type_JavaUtilArrayList.remove(Long.valueOf(paramLong));
  }
  
  public static abstract interface ConnectionTypeObserver
  {
    public abstract void onConnectionTypeChanged(int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\net\NetworkChangeNotifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */