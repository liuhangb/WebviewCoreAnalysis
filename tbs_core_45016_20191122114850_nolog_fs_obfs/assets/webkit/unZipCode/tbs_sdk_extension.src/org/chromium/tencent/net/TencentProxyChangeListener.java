package org.chromium.tencent.net;

import org.chromium.base.annotations.CalledByNative;
import org.chromium.net.ProxyChangeListener;
import org.chromium.net.ProxyChangeListener.Delegate;
import org.chromium.net.ProxyChangeListener.ProxyConfig;

public class TencentProxyChangeListener
  extends ProxyChangeListener
{
  private static final String TAG = "TencentProxyChangeListener";
  private static TencentProxyChangeListener mInstance;
  private static boolean proxySetBySystem = false;
  private String x5settingproxy = "";
  
  public static TencentProxyChangeListener getInstance()
  {
    if (mInstance == null) {
      mInstance = new TencentProxyChangeListener();
    }
    return mInstance;
  }
  
  @CalledByNative
  public void InitProxySettingsForHttpProxy()
  {
    if (!sEnabled) {
      return;
    }
    if (this.mDelegate != null) {
      this.mDelegate.proxySettingsChanged();
    }
    if (this.mNativePtr == 0L) {
      return;
    }
    Object localObject3 = this.x5settingproxy;
    Object localObject2 = null;
    Object localObject1 = localObject2;
    if (localObject3 != null)
    {
      localObject1 = localObject2;
      if (((String)localObject3).length() > 0) {
        localObject3 = this.x5settingproxy.split(":");
      }
    }
    try
    {
      i = Integer.parseInt(localObject3[1]);
    }
    catch (Exception localException)
    {
      int i;
      for (;;) {}
    }
    i = 0;
    localObject1 = localObject2;
    if (localObject3[0] != null)
    {
      localObject1 = localObject2;
      if (localObject3[0].length() > 0)
      {
        localObject1 = localObject2;
        if (i > 0) {
          localObject1 = new ProxyChangeListener.ProxyConfig(localObject3[0], i, "", null);
        }
      }
    }
    if (localObject1 != null)
    {
      nativeProxySettingsChangedTo(this.mNativePtr, ((ProxyChangeListener.ProxyConfig)localObject1).jdField_a_of_type_JavaLangString, ((ProxyChangeListener.ProxyConfig)localObject1).jdField_a_of_type_Int, ((ProxyChangeListener.ProxyConfig)localObject1).b, ((ProxyChangeListener.ProxyConfig)localObject1).jdField_a_of_type_ArrayOfJavaLangString);
      return;
    }
    nativeProxySettingsChanged(this.mNativePtr);
  }
  
  protected void proxySettingsChanged(ProxyChangeListener.ProxyConfig paramProxyConfig)
  {
    if (!sEnabled) {
      return;
    }
    if (this.mDelegate != null) {
      this.mDelegate.proxySettingsChanged();
    }
    if (this.mNativePtr == 0L) {
      return;
    }
    Object localObject;
    if ((paramProxyConfig == null) || ((paramProxyConfig.jdField_a_of_type_JavaLangString != null) && (paramProxyConfig.jdField_a_of_type_Int > 0)))
    {
      localProxyConfig = paramProxyConfig;
      if (paramProxyConfig != null) {}
    }
    else
    {
      localObject = this.x5settingproxy;
      localProxyConfig = paramProxyConfig;
      if (localObject != null)
      {
        localProxyConfig = paramProxyConfig;
        if (((String)localObject).length() > 0) {
          localObject = this.x5settingproxy.split(":");
        }
      }
    }
    try
    {
      i = Integer.parseInt(localObject[1]);
    }
    catch (Exception localException)
    {
      int i;
      for (;;) {}
    }
    i = 0;
    ProxyChangeListener.ProxyConfig localProxyConfig = paramProxyConfig;
    if (localObject[0] != null)
    {
      localProxyConfig = paramProxyConfig;
      if (localObject[0].length() > 0)
      {
        localProxyConfig = paramProxyConfig;
        if (i > 0) {
          localProxyConfig = new ProxyChangeListener.ProxyConfig(localObject[0], i, "", null);
        }
      }
    }
    if (localProxyConfig != null)
    {
      if ((localProxyConfig.jdField_a_of_type_JavaLangString != null) && (localProxyConfig.jdField_a_of_type_Int > 0)) {
        proxySetBySystem = true;
      } else {
        proxySetBySystem = false;
      }
      nativeProxySettingsChangedTo(this.mNativePtr, localProxyConfig.jdField_a_of_type_JavaLangString, localProxyConfig.jdField_a_of_type_Int, localProxyConfig.b, localProxyConfig.jdField_a_of_type_ArrayOfJavaLangString);
      return;
    }
    proxySetBySystem = false;
    nativeProxySettingsChanged(this.mNativePtr);
  }
  
  public void setHttpSystemProxy(String paramString)
  {
    if (proxySetBySystem) {
      return;
    }
    if (!sEnabled) {
      return;
    }
    if (this.mDelegate != null) {
      this.mDelegate.proxySettingsChanged();
    }
    if (this.mNativePtr == 0L) {
      return;
    }
    Object localObject2 = null;
    Object localObject1 = localObject2;
    if (paramString != null)
    {
      localObject1 = localObject2;
      if (paramString.length() > 0) {
        paramString = paramString.split(":");
      }
    }
    try
    {
      i = Integer.parseInt(paramString[1]);
    }
    catch (Exception localException)
    {
      for (;;)
      {
        try
        {
          int i;
          nativeProxySettingsChanged(this.mNativePtr);
          return;
        }
        catch (UnsatisfiedLinkError paramString) {}
        localException = localException;
      }
    }
    i = 0;
    localObject1 = localObject2;
    if (paramString[0] != null)
    {
      localObject1 = localObject2;
      if (paramString[0].length() > 0)
      {
        localObject1 = localObject2;
        if (i > 0) {
          localObject1 = new ProxyChangeListener.ProxyConfig(paramString[0], i, "", null);
        }
      }
    }
    if (localObject1 != null)
    {
      nativeProxySettingsChangedTo(this.mNativePtr, ((ProxyChangeListener.ProxyConfig)localObject1).jdField_a_of_type_JavaLangString, ((ProxyChangeListener.ProxyConfig)localObject1).jdField_a_of_type_Int, ((ProxyChangeListener.ProxyConfig)localObject1).b, ((ProxyChangeListener.ProxyConfig)localObject1).jdField_a_of_type_ArrayOfJavaLangString);
      return;
    }
  }
  
  public void setProxy(String paramString)
  {
    this.x5settingproxy = paramString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\net\TencentProxyChangeListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */