package com.tencent.smtt.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Process;

public class X5LogIPCBroadcastReceiver
  extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    String str = paramIntent.getAction();
    int i = paramIntent.getIntExtra("x5log_broadcast", 0);
    if ("com.tencent.x5.x5log".equals(str)) {
      switch (i)
      {
      default: 
        
      case 3: 
        if (o.a().b(paramContext))
        {
          Process.killProcess(Process.myPid());
          return;
        }
        break;
      case 2: 
        if (X5LogManager.isOpenX5log())
        {
          X5LogManager.setLogWrite2FileFlag(false);
          X5LogManager.uploadX5LogFilesToServer(3000L);
          return;
        }
        break;
      case 1: 
        if (o.a().a(paramContext)) {
          X5LogManager.setLogWrite2FileFlag(true, "broadcast_toolsmp");
        }
        break;
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\X5LogIPCBroadcastReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */