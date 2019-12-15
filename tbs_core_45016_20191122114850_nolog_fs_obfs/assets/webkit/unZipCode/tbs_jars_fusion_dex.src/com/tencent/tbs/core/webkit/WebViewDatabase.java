package com.tencent.tbs.core.webkit;

import android.content.Context;

public abstract class WebViewDatabase
{
  protected static final String LOGTAG = "webviewdatabase";
  
  public static WebViewDatabase getInstance(Context paramContext)
  {
    return WebViewFactory.getProvider().getWebViewDatabase(paramContext);
  }
  
  public abstract void clearFormData();
  
  public abstract void clearHttpAuthUsernamePassword();
  
  @Deprecated
  public abstract void clearUsernamePassword();
  
  public abstract boolean hasFormData();
  
  public abstract boolean hasHttpAuthUsernamePassword();
  
  @Deprecated
  public abstract boolean hasUsernamePassword();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\WebViewDatabase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */