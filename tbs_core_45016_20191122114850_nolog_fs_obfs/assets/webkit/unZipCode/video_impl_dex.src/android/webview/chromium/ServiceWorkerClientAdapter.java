package android.webview.chromium;

import android.annotation.TargetApi;
import com.tencent.tbs.core.webkit.ServiceWorkerClient;
import com.tencent.tbs.core.webkit.WebResourceResponse;
import java.util.HashMap;
import java.util.Map;
import org.chromium.android_webview.AwContentsClient.AwWebResourceRequest;
import org.chromium.android_webview.AwServiceWorkerClient;
import org.chromium.android_webview.AwWebResourceResponse;

@TargetApi(24)
public class ServiceWorkerClientAdapter
  extends AwServiceWorkerClient
{
  private ServiceWorkerClient mServiceWorkerClient;
  
  public ServiceWorkerClientAdapter(ServiceWorkerClient paramServiceWorkerClient)
  {
    this.mServiceWorkerClient = paramServiceWorkerClient;
  }
  
  public AwWebResourceResponse shouldInterceptRequest(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest)
  {
    WebResourceResponse localWebResourceResponse = this.mServiceWorkerClient.shouldInterceptRequest(new WebResourceRequestAdapter(paramAwWebResourceRequest));
    if (localWebResourceResponse == null) {
      return null;
    }
    Map localMap = localWebResourceResponse.getResponseHeaders();
    paramAwWebResourceRequest = localMap;
    if (localMap == null) {
      paramAwWebResourceRequest = new HashMap();
    }
    return new AwWebResourceResponse(localWebResourceResponse.getMimeType(), localWebResourceResponse.getEncoding(), localWebResourceResponse.getData(), localWebResourceResponse.getStatusCode(), localWebResourceResponse.getReasonPhrase(), paramAwWebResourceRequest);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\ServiceWorkerClientAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */