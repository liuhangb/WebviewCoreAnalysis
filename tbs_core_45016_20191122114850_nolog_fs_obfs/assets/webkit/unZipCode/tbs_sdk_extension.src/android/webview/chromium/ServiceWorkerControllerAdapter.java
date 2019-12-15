package android.webview.chromium;

import android.annotation.TargetApi;
import com.tencent.tbs.core.webkit.ServiceWorkerClient;
import com.tencent.tbs.core.webkit.ServiceWorkerController;
import com.tencent.tbs.core.webkit.ServiceWorkerWebSettings;
import org.chromium.android_webview.AwServiceWorkerController;

@TargetApi(24)
public class ServiceWorkerControllerAdapter
  extends ServiceWorkerController
{
  private AwServiceWorkerController mAwServiceWorkerController;
  
  public ServiceWorkerControllerAdapter(AwServiceWorkerController paramAwServiceWorkerController)
  {
    this.mAwServiceWorkerController = paramAwServiceWorkerController;
  }
  
  public ServiceWorkerWebSettings getServiceWorkerWebSettings()
  {
    return new ServiceWorkerSettingsAdapter(this.mAwServiceWorkerController.getAwServiceWorkerSettings());
  }
  
  public void setServiceWorkerClient(ServiceWorkerClient paramServiceWorkerClient)
  {
    this.mAwServiceWorkerController.setServiceWorkerClient(new ServiceWorkerClientAdapter(paramServiceWorkerClient));
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\ServiceWorkerControllerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */