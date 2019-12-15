package com.tencent.tbs.core;

import android.content.Context;
import android.webview.chromium.tencent.TencentWebViewDatabaseAdapter;
import com.tencent.smtt.export.external.interfaces.IX5CoreWebViewDB;

public class X5CoreWebViewDB
  implements IX5CoreWebViewDB
{
  private static X5CoreWebViewDB sInstance;
  
  public static X5CoreWebViewDB getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new X5CoreWebViewDB();
      }
      X5CoreWebViewDB localX5CoreWebViewDB = sInstance;
      return localX5CoreWebViewDB;
    }
    finally {}
  }
  
  public void clearFormData(Context paramContext)
  {
    TencentWebViewDatabaseAdapter.getInstance(paramContext).clearFormData();
  }
  
  public void clearHttpAuthUsernamePassword(Context paramContext)
  {
    TencentWebViewDatabaseAdapter.getInstance(paramContext).clearHttpAuthUsernamePassword();
  }
  
  public void clearUsernamePassword(Context paramContext)
  {
    TencentWebViewDatabaseAdapter.getInstance(paramContext).clearUsernamePassword();
  }
  
  public boolean hasFormData(Context paramContext)
  {
    return TencentWebViewDatabaseAdapter.getInstance(paramContext).hasFormData();
  }
  
  public boolean hasHttpAuthUsernamePassword(Context paramContext)
  {
    return TencentWebViewDatabaseAdapter.getInstance(paramContext).hasHttpAuthUsernamePassword();
  }
  
  public boolean hasUsernamePassword(Context paramContext)
  {
    return TencentWebViewDatabaseAdapter.getInstance(paramContext).hasUsernamePassword();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\X5CoreWebViewDB.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */