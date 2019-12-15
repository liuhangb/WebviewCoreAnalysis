package com.tencent.tbs.core.webkit;

import android.content.Context;
import android.os.Handler;
import android.webkit.WebViewDatabase;

@Deprecated
abstract class WebSyncManager
  implements Runnable
{
  protected static final String LOGTAG = "websync";
  protected WebViewDatabase mDataBase;
  protected Handler mHandler;
  
  protected WebSyncManager(Context paramContext, String paramString) {}
  
  protected Object clone()
    throws CloneNotSupportedException
  {
    throw new CloneNotSupportedException("doesn't implement Cloneable");
  }
  
  protected void onSyncInit() {}
  
  public void resetSync() {}
  
  public void run() {}
  
  public void startSync() {}
  
  public void stopSync() {}
  
  public void sync() {}
  
  abstract void syncFromRamToFlash();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\WebSyncManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */