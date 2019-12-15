package com.tencent.beacontbs.upload;

import android.content.Context;
import com.tencent.beacontbs.a.e;
import com.tencent.beacontbs.c.a;

public final class c
  extends b
{
  public c(Context paramContext)
  {
    super(paramContext, 0, 100);
  }
  
  public final com.tencent.beacontbs.b.a.b a()
  {
    Object localObject = com.tencent.beacontbs.a.c.a();
    byte[] arrayOfByte = "".getBytes();
    try
    {
      localObject = e.a(a(), (com.tencent.beacontbs.a.c)localObject, arrayOfByte, -1, -1);
      if (localObject != null) {
        return (com.tencent.beacontbs.b.a.b)localObject;
      }
      return null;
    }
    catch (Throwable localThrowable)
    {
      a.a(localThrowable);
      a.d("encode2RequestPackage failed", new Object[0]);
    }
    return null;
  }
  
  public final void b(boolean paramBoolean) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\upload\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */