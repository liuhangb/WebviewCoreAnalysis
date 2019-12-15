package com.tencent.tbs.core.webkit;

import android.content.ContentResolver;
import android.graphics.Bitmap;

@Deprecated
public abstract class WebIconDatabase
{
  public static WebIconDatabase getInstance()
  {
    return WebViewFactory.getProvider().getWebIconDatabase();
  }
  
  public abstract void bulkRequestIconForPageUrl(ContentResolver paramContentResolver, String paramString, IconListener paramIconListener);
  
  public abstract void close();
  
  public abstract void open(String paramString);
  
  public abstract void releaseIconForPageUrl(String paramString);
  
  public abstract void removeAllIcons();
  
  public abstract void requestIconForPageUrl(String paramString, IconListener paramIconListener);
  
  public abstract void retainIconForPageUrl(String paramString);
  
  @Deprecated
  public static abstract interface IconListener
  {
    public abstract void onReceivedIcon(String paramString, Bitmap paramBitmap);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\WebIconDatabase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */