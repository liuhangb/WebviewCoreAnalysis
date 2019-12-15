package android.webview.chromium;

import android.graphics.Bitmap;
import com.tencent.tbs.core.webkit.WebHistoryItem;
import org.chromium.content_public.browser.NavigationEntry;

public class WebHistoryItemChromium
  extends WebHistoryItem
{
  protected Bitmap mFavicon;
  protected String mOriginalUrl;
  protected String mTitle;
  protected String mUrl;
  
  public WebHistoryItemChromium(String paramString1, String paramString2, String paramString3, Bitmap paramBitmap)
  {
    this.mUrl = paramString1;
    this.mOriginalUrl = paramString2;
    this.mTitle = paramString3;
    this.mFavicon = paramBitmap;
  }
  
  public WebHistoryItemChromium(NavigationEntry paramNavigationEntry)
  {
    this.mUrl = paramNavigationEntry.getUrl();
    this.mOriginalUrl = paramNavigationEntry.getOriginalUrl();
    this.mTitle = paramNavigationEntry.getTitle();
    this.mFavicon = paramNavigationEntry.getFavicon();
  }
  
  public WebHistoryItemChromium clone()
  {
    try
    {
      WebHistoryItemChromium localWebHistoryItemChromium = new WebHistoryItemChromium(this.mUrl, this.mOriginalUrl, this.mTitle, this.mFavicon);
      return localWebHistoryItemChromium;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public Bitmap getFavicon()
  {
    return this.mFavicon;
  }
  
  public int getId()
  {
    return -1;
  }
  
  public String getOriginalUrl()
  {
    return this.mOriginalUrl;
  }
  
  public String getTitle()
  {
    return this.mTitle;
  }
  
  public String getUrl()
  {
    return this.mUrl;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\WebHistoryItemChromium.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */