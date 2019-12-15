package com.tencent.turingfd.sdk.tbs;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.SparseArray;
import java.util.Map;

public class TuringDIDService
{
  public static h a(Context paramContext)
  {
    if (!n.jdField_a_of_type_Boolean)
    {
      paramContext = x.a(55535);
    }
    else if (!n.b)
    {
      paramContext = x.a(55534);
    }
    else
    {
      z localz = z.a();
      int i = localz.jdField_a_of_type_ComTencentTuringfdSdkTbsM.c();
      if (i != 1)
      {
        if (i != 2) {
          paramContext = localz.a(paramContext);
        } else {
          paramContext = localz.a(paramContext, 0, true);
        }
      }
      else
      {
        x localx = localz.a(paramContext);
        localz.jdField_a_of_type_ComTencentTuringfdSdkTbsAb.a(paramContext, localx.a);
        paramContext = localx;
      }
    }
    return new a(paramContext);
  }
  
  public static void a(j paramj)
  {
    if ((n.jdField_a_of_type_Boolean) || (paramj.a())) {}
    try
    {
      System.loadLibrary("turingtbs");
      bool = true;
    }
    catch (Throwable localThrowable)
    {
      boolean bool;
      z localz;
      Object localObject;
      for (;;) {}
    }
    bool = false;
    n.jdField_a_of_type_Boolean = bool;
    if (!n.jdField_a_of_type_Boolean)
    {
      return;
      n.jdField_a_of_type_Boolean = true;
    }
    localz = z.a();
    localz.jdField_a_of_type_ComTencentTuringfdSdkTbsM = paramj;
    if (!localz.jdField_a_of_type_Boolean)
    {
      localz.jdField_a_of_type_Boolean = true;
      localObject = new HandlerThread("TuringFdCore");
      ((HandlerThread)localObject).start();
      localz.jdField_a_of_type_AndroidOsHandler = new z.b(localz, ((HandlerThread)localObject).getLooper(), paramj.a());
      localz.jdField_a_of_type_ComTencentTuringfdSdkTbsAb = new ab(localz.jdField_a_of_type_AndroidOsHandler);
      localObject = paramj.a();
      if (c.a.containsKey(c.b)) {
        new b((c.a)c.a.get(c.b), (Context)localObject).start();
      }
      if (paramj.c() == 1) {
        new y(localz).start();
      }
    }
    n.b = true;
  }
  
  public static final class a
    implements h
  {
    public a(k paramk) {}
    
    public int a()
    {
      return ((x)this.a).a;
    }
    
    public String a()
    {
      return ((x)this.a).d;
    }
    
    public String b()
    {
      return ((x)this.a).e;
    }
  }
  
  public static class aa
  {
    public static native SparseArray<Object> a(SparseArray<Object> paramSparseArray, Context paramContext, Map<String, String> paramMap, int paramInt);
    
    public static native SparseArray<Object> b(SparseArray<Object> paramSparseArray, byte[] paramArrayOfByte);
    
    public static native SparseArray<Object> c(SparseArray<Object> paramSparseArray, Context paramContext);
    
    public static native SparseArray<Object> d(SparseArray<Object> paramSparseArray);
    
    public static class bb
      implements ServiceConnection
    {
      public native void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder);
      
      public void onServiceDisconnected(ComponentName paramComponentName) {}
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\turingfd\sdk\tbs\TuringDIDService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */