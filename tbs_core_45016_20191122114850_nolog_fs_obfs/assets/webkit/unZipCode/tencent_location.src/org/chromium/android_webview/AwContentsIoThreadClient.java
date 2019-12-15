package org.chromium.android_webview;

import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
public abstract class AwContentsIoThreadClient
{
  @CalledByNative
  public abstract AwContentsBackgroundThreadClient getBackgroundThreadClient();
  
  @CalledByNative
  public abstract int getCacheMode();
  
  @CalledByNative
  public abstract boolean getSafeBrowsingEnabled();
  
  @CalledByNative
  public abstract boolean shouldAcceptThirdPartyCookies();
  
  @CalledByNative
  public abstract boolean shouldBlockContentUrls();
  
  @CalledByNative
  public abstract boolean shouldBlockFileUrls();
  
  @CalledByNative
  public abstract boolean shouldBlockNetworkLoads();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwContentsIoThreadClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */