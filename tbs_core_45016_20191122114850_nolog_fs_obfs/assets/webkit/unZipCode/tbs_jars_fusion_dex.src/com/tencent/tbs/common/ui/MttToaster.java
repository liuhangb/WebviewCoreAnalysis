package com.tencent.tbs.common.ui;

import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import com.tencent.mtt.browser.toast.ToastCompat;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;

public class MttToaster
{
  public static final int MSG_TOAST_RES = 1;
  public static final int MSG_TOAST_RES_CENTER = 3;
  public static final int MSG_TOAST_TEXT = 2;
  private ToasterHandler mHandler = new ToasterHandler();
  
  public static MttToaster show(int paramInt1, int paramInt2)
  {
    MttToaster localMttToaster = new MttToaster();
    Message localMessage = localMttToaster.mHandler.obtainMessage(1);
    localMessage.arg1 = paramInt1;
    localMessage.arg2 = paramInt2;
    localMessage.sendToTarget();
    return localMttToaster;
  }
  
  public static MttToaster show(String paramString, int paramInt)
  {
    MttToaster localMttToaster = new MttToaster();
    Message localMessage = localMttToaster.mHandler.obtainMessage(2);
    localMessage.obj = paramString;
    localMessage.arg2 = paramInt;
    localMessage.sendToTarget();
    return localMttToaster;
  }
  
  public static MttToaster showInCenter(int paramInt1, int paramInt2)
  {
    MttToaster localMttToaster = new MttToaster();
    Message localMessage = localMttToaster.mHandler.obtainMessage(3);
    localMessage.arg1 = paramInt1;
    localMessage.arg2 = paramInt2;
    localMessage.sendToTarget();
    return localMttToaster;
  }
  
  void toast(int paramInt1, int paramInt2, boolean paramBoolean) {}
  
  void toast(String paramString, int paramInt, boolean paramBoolean)
  {
    if ((Build.VERSION.SDK_INT >= 23) && (Build.VERSION.SDK_INT <= 25))
    {
      ToastCompat.makeText(TbsBaseModuleShell.getCallerContext(), paramString, paramInt).show();
      return;
    }
    Toast.makeText(TbsBaseModuleShell.getCallerContext(), paramString, paramInt).show();
  }
  
  public class ToasterHandler
    extends Handler
  {
    public ToasterHandler()
    {
      super();
    }
    
    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default: 
        return;
      case 3: 
        i = paramMessage.arg1;
        j = paramMessage.arg2;
        MttToaster.this.toast(i, j, true);
        return;
      case 2: 
        String str = (String)paramMessage.obj;
        i = paramMessage.arg2;
        MttToaster.this.toast(str, i, false);
        return;
      }
      int i = paramMessage.arg1;
      int j = paramMessage.arg2;
      MttToaster.this.toast(i, j, false);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\MttToaster.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */