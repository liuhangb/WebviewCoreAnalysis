package android.webview.chromium;

import android.net.Uri;
import com.tencent.tbs.core.webkit.WebResourceRequest;
import java.util.Map;
import org.chromium.android_webview.AwContentsClient.AwWebResourceRequest;

class WebResourceRequestAdapter
  implements WebResourceRequest
{
  private final AwContentsClient.AwWebResourceRequest mRequest;
  
  public WebResourceRequestAdapter(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest)
  {
    this.mRequest = paramAwWebResourceRequest;
  }
  
  public String getMethod()
  {
    return this.mRequest.method;
  }
  
  public Map<String, String> getRequestHeaders()
  {
    return this.mRequest.requestHeaders;
  }
  
  public Uri getUrl()
  {
    return Uri.parse(this.mRequest.url);
  }
  
  public boolean hasGesture()
  {
    return this.mRequest.hasUserGesture;
  }
  
  public boolean isForMainFrame()
  {
    return this.mRequest.isMainFrame;
  }
  
  public boolean isRedirect()
  {
    return this.mRequest.isRedirect;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\WebResourceRequestAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */