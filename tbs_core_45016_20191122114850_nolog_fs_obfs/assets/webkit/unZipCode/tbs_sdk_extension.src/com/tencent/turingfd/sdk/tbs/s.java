package com.tencent.turingfd.sdk.tbs;

import android.os.IBinder;
import java.lang.reflect.Method;

public class s
{
  public static final String a = o.a(o.j);
  public static final String b = o.a(o.m);
  public static final String c = o.a(o.n);
  public static final String d = o.a(o.q);
  
  static
  {
    o.a(o.i);
    o.a(o.k);
    o.a(o.l);
  }
  
  public static Object a()
  {
    try
    {
      Object localObject = t.a(c);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(a);
      localStringBuilder.append("$Stub");
      localObject = r.a(localStringBuilder.toString(), d, new Class[] { IBinder.class }, null, new Object[] { localObject });
      return localObject;
    }
    catch (Throwable localThrowable) {}
    return null;
  }
  
  public static String[] a(int paramInt)
  {
    try
    {
      localObject1 = a();
      localObject2 = Class.forName(a).getMethod(b, new Class[] { Integer.TYPE });
      ((Method)localObject2).setAccessible(true);
      localObject1 = (String[])((Method)localObject2).invoke(localObject1, new Object[] { Integer.valueOf(paramInt) });
    }
    catch (Throwable localThrowable)
    {
      Object localObject1;
      Object localObject2;
      for (;;) {}
    }
    localObject1 = null;
    localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = new String[0];
    }
    return (String[])localObject2;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\turingfd\sdk\tbs\s.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */