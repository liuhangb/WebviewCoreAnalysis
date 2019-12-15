package org.chromium.android_webview;

import android.content.Context;

public class AwServiceWorkerController
{
  private AwBrowserContext jdField_a_of_type_OrgChromiumAndroid_webviewAwBrowserContext;
  private AwContentsBackgroundThreadClient jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsBackgroundThreadClient;
  private AwContentsIoThreadClient jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsIoThreadClient;
  private AwServiceWorkerClient jdField_a_of_type_OrgChromiumAndroid_webviewAwServiceWorkerClient;
  private AwServiceWorkerSettings jdField_a_of_type_OrgChromiumAndroid_webviewAwServiceWorkerSettings;
  
  public AwServiceWorkerController(Context paramContext, AwBrowserContext paramAwBrowserContext)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwServiceWorkerSettings = new AwServiceWorkerSettings(paramContext);
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwBrowserContext = paramAwBrowserContext;
  }
  
  public AwServiceWorkerSettings getAwServiceWorkerSettings()
  {
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwServiceWorkerSettings;
  }
  
  public void setServiceWorkerClient(AwServiceWorkerClient paramAwServiceWorkerClient)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwServiceWorkerClient = paramAwServiceWorkerClient;
    if (paramAwServiceWorkerClient != null)
    {
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsBackgroundThreadClient = new ServiceWorkerBackgroundThreadClientImpl(null);
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsIoThreadClient = new ServiceWorkerIoThreadClientImpl(null);
      AwContentsStatics.setServiceWorkerIoThreadClient(this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsIoThreadClient, this.jdField_a_of_type_OrgChromiumAndroid_webviewAwBrowserContext);
      return;
    }
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsBackgroundThreadClient = null;
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsIoThreadClient = null;
    AwContentsStatics.setServiceWorkerIoThreadClient(null, this.jdField_a_of_type_OrgChromiumAndroid_webviewAwBrowserContext);
  }
  
  private class ServiceWorkerBackgroundThreadClientImpl
    extends AwContentsBackgroundThreadClient
  {
    private ServiceWorkerBackgroundThreadClientImpl() {}
    
    public AwWebResourceResponse shouldInterceptRequest(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest)
    {
      return AwServiceWorkerController.a(AwServiceWorkerController.this).shouldInterceptRequest(paramAwWebResourceRequest);
    }
  }
  
  private class ServiceWorkerIoThreadClientImpl
    extends AwContentsIoThreadClient
  {
    private ServiceWorkerIoThreadClientImpl() {}
    
    public AwContentsBackgroundThreadClient getBackgroundThreadClient()
    {
      return AwServiceWorkerController.a(AwServiceWorkerController.this);
    }
    
    public int getCacheMode()
    {
      return AwServiceWorkerController.a(AwServiceWorkerController.this).getCacheMode();
    }
    
    public boolean getSafeBrowsingEnabled()
    {
      return AwContentsStatics.getSafeBrowsingEnabledByManifest();
    }
    
    public boolean shouldAcceptThirdPartyCookies()
    {
      return false;
    }
    
    public boolean shouldBlockContentUrls()
    {
      return AwServiceWorkerController.a(AwServiceWorkerController.this).getAllowContentAccess() ^ true;
    }
    
    public boolean shouldBlockFileUrls()
    {
      return AwServiceWorkerController.a(AwServiceWorkerController.this).getAllowFileAccess() ^ true;
    }
    
    public boolean shouldBlockNetworkLoads()
    {
      return AwServiceWorkerController.a(AwServiceWorkerController.this).getBlockNetworkLoads();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwServiceWorkerController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */