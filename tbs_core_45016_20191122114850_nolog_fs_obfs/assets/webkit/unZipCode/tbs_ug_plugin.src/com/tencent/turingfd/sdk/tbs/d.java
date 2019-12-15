package com.tencent.turingfd.sdk.tbs;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public abstract interface d
  extends IInterface
{
  public static abstract class a
    extends Binder
  {
    public static final String a = o.a(o.c);
    
    public static d a(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface(a);
      if ((localIInterface != null) && ((localIInterface instanceof d))) {
        return (d)localIInterface;
      }
      return new a(paramIBinder);
    }
    
    private static class a
      implements d
    {
      public IBinder a;
      
      public a(IBinder paramIBinder)
      {
        this.a = paramIBinder;
      }
      
      public IBinder asBinder()
      {
        return this.a;
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\turingfd\sdk\tbs\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */