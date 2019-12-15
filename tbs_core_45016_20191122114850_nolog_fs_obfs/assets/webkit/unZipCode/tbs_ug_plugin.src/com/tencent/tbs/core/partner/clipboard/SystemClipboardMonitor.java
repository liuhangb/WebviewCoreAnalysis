package com.tencent.tbs.core.partner.clipboard;

import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Context;
import android.os.Build.VERSION;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.service.SmttServiceProxy;

public class SystemClipboardMonitor
{
  private static SystemClipboardMonitor mInstance;
  private Context mContext = null;
  
  private SystemClipboardMonitor(Context paramContext)
  {
    try
    {
      this.mContext = paramContext.getApplicationContext();
      startMonitor();
      return;
    }
    catch (Throwable paramContext) {}
  }
  
  public static SystemClipboardMonitor getInstance(Context paramContext)
  {
    try
    {
      if ((!ContextHolder.getInstance().isThirdPartyApp(paramContext)) && (SmttServiceProxy.getInstance().isTbsClipBoardEnabled(paramContext)))
      {
        if (mInstance == null) {
          mInstance = new SystemClipboardMonitor(paramContext);
        }
        paramContext = mInstance;
        return paramContext;
      }
      return null;
    }
    finally {}
  }
  
  private void startMonitor()
  {
    if (Build.VERSION.SDK_INT >= 11)
    {
      ClipboardManager localClipboardManager = (ClipboardManager)this.mContext.getSystemService("clipboard");
      ClipboardManager.OnPrimaryClipChangedListener local1 = new ClipboardManager.OnPrimaryClipChangedListener()
      {
        public void onPrimaryClipChanged()
        {
          PartnerClipboard.setIsSystemClipBoardNewest(true);
          PartnerClipboard.getTextFromSysClipBoard(SystemClipboardMonitor.this.mContext);
        }
      };
      if (localClipboardManager == null) {
        return;
      }
      localClipboardManager.addPrimaryClipChangedListener(local1);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\clipboard\SystemClipboardMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */