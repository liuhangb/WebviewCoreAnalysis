package org.chromium.content.browser;

import android.annotation.SuppressLint;
import android.os.Process;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.chromium.base.ApiCompatibilityUtils;
import org.chromium.base.ContextUtils;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.NativeClassQualifiedName;
import org.chromium.base.metrics.RecordHistogram;
import org.chromium.net.NetworkChangeNotifierAutoDetect;
import org.chromium.net.NetworkChangeNotifierAutoDetect.NetworkState;
import org.chromium.net.NetworkChangeNotifierAutoDetect.Observer;
import org.chromium.net.RegistrationPolicyAlwaysRegister;

@JNINamespace("content")
class BackgroundSyncNetworkObserver
  implements NetworkChangeNotifierAutoDetect.Observer
{
  @SuppressLint({"StaticFieldLeak"})
  private static BackgroundSyncNetworkObserver jdField_a_of_type_OrgChromiumContentBrowserBackgroundSyncNetworkObserver;
  private int jdField_a_of_type_Int;
  private List<Long> jdField_a_of_type_JavaUtilList;
  private NetworkChangeNotifierAutoDetect jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect;
  private boolean jdField_a_of_type_Boolean;
  
  private BackgroundSyncNetworkObserver()
  {
    ThreadUtils.assertOnUiThread();
    this.jdField_a_of_type_JavaUtilList = new ArrayList();
  }
  
  private void a(int paramInt)
  {
    if ((this.jdField_a_of_type_Boolean) && (paramInt == this.jdField_a_of_type_Int)) {
      return;
    }
    this.jdField_a_of_type_Boolean = true;
    this.jdField_a_of_type_Int = paramInt;
    Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
    while (localIterator.hasNext()) {
      nativeNotifyConnectionTypeChanged(((Long)localIterator.next()).longValue(), paramInt);
    }
  }
  
  private void a(long paramLong)
  {
    
    if (!a())
    {
      RecordHistogram.recordBooleanHistogram("BackgroundSync.NetworkObserver.HasPermission", false);
      return;
    }
    if (this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect == null)
    {
      this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect = new NetworkChangeNotifierAutoDetect(this, new RegistrationPolicyAlwaysRegister());
      RecordHistogram.recordBooleanHistogram("BackgroundSync.NetworkObserver.HasPermission", true);
    }
    this.jdField_a_of_type_JavaUtilList.add(Long.valueOf(paramLong));
    nativeNotifyConnectionTypeChanged(paramLong, this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect.a().c());
  }
  
  private static boolean a()
  {
    return ApiCompatibilityUtils.checkPermission(ContextUtils.getApplicationContext(), "android.permission.ACCESS_NETWORK_STATE", Process.myPid(), Process.myUid()) == 0;
  }
  
  @CalledByNative
  private static BackgroundSyncNetworkObserver createObserver(long paramLong)
  {
    
    if (jdField_a_of_type_OrgChromiumContentBrowserBackgroundSyncNetworkObserver == null) {
      jdField_a_of_type_OrgChromiumContentBrowserBackgroundSyncNetworkObserver = new BackgroundSyncNetworkObserver();
    }
    jdField_a_of_type_OrgChromiumContentBrowserBackgroundSyncNetworkObserver.a(paramLong);
    return jdField_a_of_type_OrgChromiumContentBrowserBackgroundSyncNetworkObserver;
  }
  
  @NativeClassQualifiedName("BackgroundSyncNetworkObserverAndroid::Observer")
  private native void nativeNotifyConnectionTypeChanged(long paramLong, int paramInt);
  
  @CalledByNative
  private void removeObserver(long paramLong)
  {
    ThreadUtils.assertOnUiThread();
    this.jdField_a_of_type_JavaUtilList.remove(Long.valueOf(paramLong));
    if (this.jdField_a_of_type_JavaUtilList.size() == 0)
    {
      NetworkChangeNotifierAutoDetect localNetworkChangeNotifierAutoDetect = this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect;
      if (localNetworkChangeNotifierAutoDetect != null)
      {
        localNetworkChangeNotifierAutoDetect.a();
        this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect = null;
      }
    }
  }
  
  public void onConnectionSubtypeChanged(int paramInt) {}
  
  public void onConnectionTypeChanged(int paramInt)
  {
    ThreadUtils.assertOnUiThread();
    a(paramInt);
  }
  
  public void onConnectionVPNChanged(boolean paramBoolean) {}
  
  public void onNetworkConnect(long paramLong, int paramInt)
  {
    ThreadUtils.assertOnUiThread();
    a(this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect.a().c());
  }
  
  public void onNetworkDisconnect(long paramLong)
  {
    ThreadUtils.assertOnUiThread();
    a(this.jdField_a_of_type_OrgChromiumNetNetworkChangeNotifierAutoDetect.a().c());
  }
  
  public void onNetworkSoonToDisconnect(long paramLong) {}
  
  public void purgeActiveNetworkList(long[] paramArrayOfLong) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\BackgroundSyncNetworkObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */