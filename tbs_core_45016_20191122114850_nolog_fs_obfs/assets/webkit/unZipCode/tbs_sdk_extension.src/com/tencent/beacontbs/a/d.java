package com.tencent.beacontbs.a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import com.tencent.beacontbs.c.a;
import com.tencent.beacontbs.event.k;

public final class d
  extends BroadcastReceiver
{
  private Context jdField_a_of_type_AndroidContentContext;
  private Runnable jdField_a_of_type_JavaLangRunnable = new Runnable()
  {
    public final void run()
    {
      a.b(" db events to up on netConnectChange", new Object[0]);
      try
      {
        k.a(true);
        return;
      }
      catch (Throwable localThrowable)
      {
        a.a(localThrowable);
      }
    }
  };
  private boolean jdField_a_of_type_Boolean = false;
  
  public final void a(Context paramContext)
  {
    if (paramContext == null)
    {
      a.c(" Context is null!", new Object[0]);
      return;
    }
    if (!this.jdField_a_of_type_Boolean) {
      this.jdField_a_of_type_Boolean = true;
    }
    try
    {
      paramContext.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
      return;
    }
    catch (SecurityException paramContext)
    {
      for (;;) {}
    }
    a.d("register receiver failure by SecurityException", new Object[0]);
  }
  
  public final void onReceive(Context paramContext, Intent paramIntent)
  {
    if (paramContext == null) {}
    try
    {
      a.c(" onReceive context is null!", new Object[0]);
      return;
    }
    catch (Exception paramContext)
    {
      NetworkInfo localNetworkInfo;
      a.a(paramContext);
      return;
    }
    if (this.jdField_a_of_type_AndroidContentContext == null) {
      this.jdField_a_of_type_AndroidContentContext = paramContext;
    }
    Object localObject = (ConnectivityManager)paramContext.getSystemService("connectivity");
    if (localObject == null)
    {
      a.c(" onReceive ConnectivityManager is null!", new Object[0]);
      return;
    }
    paramIntent = ((ConnectivityManager)localObject).getNetworkInfo(1);
    localNetworkInfo = ((ConnectivityManager)localObject).getNetworkInfo(0);
    localObject = null;
    if (paramIntent != null)
    {
      paramIntent = paramIntent.getState();
      if (localNetworkInfo == null) {
        break label169;
      }
      localObject = localNetworkInfo.getState();
      break label169;
      label91:
      paramContext = com.tencent.beacontbs.a.b.b.a(paramContext);
      if ((paramContext.a()) || (k.a().a() == 0) || (paramContext.a() == 2)) {
        break label180;
      }
      b.a().a(paramContext.a());
      break label180;
    }
    for (;;)
    {
      label133:
      if ((NetworkInfo.State.CONNECTED == localObject) || (NetworkInfo.State.CONNECTED == paramIntent)) {
        b.a().a(this.jdField_a_of_type_JavaLangRunnable);
      }
      label169:
      label180:
      do
      {
        return;
        paramIntent = null;
        break;
        if ((paramIntent != null) || (localObject != null)) {
          break label91;
        }
        if (paramIntent != null) {
          break label133;
        }
      } while (localObject == null);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\a\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */