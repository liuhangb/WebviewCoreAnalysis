package android.webview.chromium;

import android.annotation.TargetApi;
import com.tencent.tbs.core.webkit.ServiceWorkerWebSettings;
import org.chromium.android_webview.AwServiceWorkerSettings;

@TargetApi(24)
public class ServiceWorkerSettingsAdapter
  extends ServiceWorkerWebSettings
{
  private AwServiceWorkerSettings mAwServiceWorkerSettings;
  
  public ServiceWorkerSettingsAdapter(AwServiceWorkerSettings paramAwServiceWorkerSettings)
  {
    this.mAwServiceWorkerSettings = paramAwServiceWorkerSettings;
  }
  
  public boolean getAllowContentAccess()
  {
    return this.mAwServiceWorkerSettings.getAllowContentAccess();
  }
  
  public boolean getAllowFileAccess()
  {
    return this.mAwServiceWorkerSettings.getAllowFileAccess();
  }
  
  AwServiceWorkerSettings getAwSettings()
  {
    return this.mAwServiceWorkerSettings;
  }
  
  public boolean getBlockNetworkLoads()
  {
    try
    {
      boolean bool = this.mAwServiceWorkerSettings.getBlockNetworkLoads();
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int getCacheMode()
  {
    return this.mAwServiceWorkerSettings.getCacheMode();
  }
  
  public void setAllowContentAccess(boolean paramBoolean)
  {
    this.mAwServiceWorkerSettings.setAllowContentAccess(paramBoolean);
  }
  
  public void setAllowFileAccess(boolean paramBoolean)
  {
    this.mAwServiceWorkerSettings.setAllowFileAccess(paramBoolean);
  }
  
  public void setBlockNetworkLoads(boolean paramBoolean)
  {
    try
    {
      this.mAwServiceWorkerSettings.setBlockNetworkLoads(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setCacheMode(int paramInt)
  {
    this.mAwServiceWorkerSettings.setCacheMode(paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\ServiceWorkerSettingsAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */