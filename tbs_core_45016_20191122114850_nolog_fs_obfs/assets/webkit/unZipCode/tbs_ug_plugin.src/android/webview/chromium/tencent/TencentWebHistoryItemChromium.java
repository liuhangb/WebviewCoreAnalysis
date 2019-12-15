package android.webview.chromium.tencent;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.webview.chromium.WebHistoryItemChromium;
import com.tencent.smtt.export.external.interfaces.IX5WebHistoryItem;
import com.tencent.smtt.webkit.WebViewChromiumExtension;
import java.lang.ref.WeakReference;
import org.chromium.content_public.browser.NavigationEntry;

public class TencentWebHistoryItemChromium
  extends WebHistoryItemChromium
  implements IX5WebHistoryItem
{
  private Object mCustomData;
  private final int mId;
  private final int mIndex;
  private WeakReference<WebViewChromiumExtension> mWebViewChromiumExtension;
  
  public TencentWebHistoryItemChromium(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, Bitmap paramBitmap)
  {
    super(paramString1, paramString2, paramString3, paramBitmap);
    this.mIndex = paramInt1;
    this.mId = paramInt2;
  }
  
  public TencentWebHistoryItemChromium(NavigationEntry paramNavigationEntry)
  {
    super(paramNavigationEntry);
    this.mIndex = paramNavigationEntry.getIndex();
    this.mId = getEntryId(paramNavigationEntry);
    if (paramNavigationEntry.getUrl().startsWith("data:")) {
      this.mUrl = paramNavigationEntry.getVirtualUrl();
    } else {
      this.mUrl = paramNavigationEntry.getUrl();
    }
    if (paramNavigationEntry.getOriginalUrl().startsWith("data:"))
    {
      this.mOriginalUrl = paramNavigationEntry.getVirtualUrl();
      return;
    }
    this.mOriginalUrl = paramNavigationEntry.getOriginalUrl();
  }
  
  private int getEntryId(NavigationEntry paramNavigationEntry)
  {
    return paramNavigationEntry.getId();
  }
  
  public boolean canDrawBaseLayer()
  {
    Object localObject = this.mWebViewChromiumExtension;
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = (WebViewChromiumExtension)((WeakReference)localObject).get();
    }
    if (localObject != null) {
      return ((WebViewChromiumExtension)localObject).canDrawBaseLayer(this.mId);
    }
    return false;
  }
  
  public TencentWebHistoryItemChromium clone()
  {
    try
    {
      TencentWebHistoryItemChromium localTencentWebHistoryItemChromium = new TencentWebHistoryItemChromium(this.mIndex, this.mId, this.mUrl, this.mOriginalUrl, this.mTitle, this.mFavicon);
      return localTencentWebHistoryItemChromium;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean drawBaseLayer(Canvas paramCanvas, boolean paramBoolean)
  {
    Object localObject = this.mWebViewChromiumExtension;
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = (WebViewChromiumExtension)((WeakReference)localObject).get();
    }
    if (localObject != null) {
      return ((WebViewChromiumExtension)localObject).drawBaseLayer(paramCanvas, paramBoolean, this.mId);
    }
    return false;
  }
  
  public Object getCustomData()
  {
    return this.mCustomData;
  }
  
  public int getId()
  {
    return this.mId;
  }
  
  public boolean getIsSubmitForm()
  {
    return false;
  }
  
  public String getTouchIconUrl()
  {
    return null;
  }
  
  public void setCustomData(Object paramObject)
  {
    this.mCustomData = paramObject;
  }
  
  public void setFavicon(Bitmap paramBitmap)
  {
    this.mFavicon = paramBitmap;
  }
  
  public void setUrl(String paramString)
  {
    this.mUrl = paramString;
  }
  
  public void setWebViewChromiumExtension(WebViewChromiumExtension paramWebViewChromiumExtension)
  {
    this.mWebViewChromiumExtension = new WeakReference(paramWebViewChromiumExtension);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\tencent\TencentWebHistoryItemChromium.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */