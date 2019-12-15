package org.chromium.android_webview;

import java.util.HashMap;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
public abstract class AwContentsBackgroundThreadClient
{
  @CalledByNative
  private AwWebResourceResponse shouldInterceptRequestFromNative(String paramString1, boolean paramBoolean1, boolean paramBoolean2, int paramInt, String paramString2, String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    AwContentsClient.AwWebResourceRequest localAwWebResourceRequest = new AwContentsClient.AwWebResourceRequest();
    localAwWebResourceRequest.url = paramString1;
    localAwWebResourceRequest.isMainFrame = paramBoolean1;
    localAwWebResourceRequest.hasUserGesture = paramBoolean2;
    localAwWebResourceRequest.resourceType = paramInt;
    localAwWebResourceRequest.method = paramString2;
    localAwWebResourceRequest.requestHeaders = new HashMap(paramArrayOfString1.length);
    paramInt = 0;
    while (paramInt < paramArrayOfString1.length)
    {
      localAwWebResourceRequest.requestHeaders.put(paramArrayOfString1[paramInt], paramArrayOfString2[paramInt]);
      paramInt += 1;
    }
    return shouldInterceptRequest(localAwWebResourceRequest);
  }
  
  public abstract AwWebResourceResponse shouldInterceptRequest(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwContentsBackgroundThreadClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */