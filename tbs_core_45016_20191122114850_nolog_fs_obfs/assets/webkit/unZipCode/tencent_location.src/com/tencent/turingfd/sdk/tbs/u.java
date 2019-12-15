package com.tencent.turingfd.sdk.tbs;

import android.os.Binder;
import android.os.IBinder;

public abstract class u
  extends Binder
  implements q
{
  public static q a(IBinder paramIBinder)
  {
    if (paramIBinder == null) {
      return null;
    }
    q localq = (q)paramIBinder.queryLocalInterface("android.os.IServiceManager");
    if (localq != null) {
      return localq;
    }
    return new v(paramIBinder);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\turingfd\sdk\tbs\u.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */