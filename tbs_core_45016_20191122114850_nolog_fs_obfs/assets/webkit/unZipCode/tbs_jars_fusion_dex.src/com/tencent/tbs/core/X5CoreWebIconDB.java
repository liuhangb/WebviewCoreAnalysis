package com.tencent.tbs.core;

import android.graphics.Bitmap;
import android.webview.chromium.tencent.TencentWebIconDatabaseAdapter;
import com.tencent.smtt.export.external.interfaces.IX5CoreWebIconDB;
import com.tencent.smtt.export.external.interfaces.IconListener;
import com.tencent.tbs.core.webkit.WebIconDatabase.IconListener;

public class X5CoreWebIconDB
  implements IX5CoreWebIconDB
{
  private static X5CoreWebIconDB sInstance;
  
  public static X5CoreWebIconDB getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new X5CoreWebIconDB();
      }
      X5CoreWebIconDB localX5CoreWebIconDB = sInstance;
      return localX5CoreWebIconDB;
    }
    finally {}
  }
  
  public void close()
  {
    TencentWebIconDatabaseAdapter.getInstance().close();
  }
  
  public Bitmap getIconForPageUrl(String paramString)
  {
    return TencentWebIconDatabaseAdapter.getInstance().getIconForPageUrl(paramString);
  }
  
  public void open(String paramString)
  {
    TencentWebIconDatabaseAdapter.getInstance().open(paramString);
  }
  
  public void releaseIconForPageUrl(String paramString)
  {
    TencentWebIconDatabaseAdapter.getInstance().releaseIconForPageUrl(paramString);
  }
  
  public void removeAllIcons()
  {
    TencentWebIconDatabaseAdapter.getInstance().removeAllIcons();
  }
  
  public void requestIconForPageUrl(String paramString, final IconListener paramIconListener)
  {
    TencentWebIconDatabaseAdapter.getInstance().requestIconForPageUrl(paramString, new WebIconDatabase.IconListener()
    {
      public void onReceivedIcon(String paramAnonymousString, Bitmap paramAnonymousBitmap)
      {
        paramIconListener.onReceivedIcon(paramAnonymousString, paramAnonymousBitmap);
      }
    });
  }
  
  public void retainIconForPageUrl(String paramString)
  {
    TencentWebIconDatabaseAdapter.getInstance().retainIconForPageUrl(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\X5CoreWebIconDB.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */