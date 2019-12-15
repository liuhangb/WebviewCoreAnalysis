package com.tencent.beacontbs.upload;

import android.content.Context;
import com.tencent.beacontbs.a.b.d;
import com.tencent.beacontbs.a.b.d.a;
import com.tencent.beacontbs.a.c;
import com.tencent.beacontbs.a.e;
import com.tencent.beacontbs.c.a;

public abstract class b
{
  protected final int a;
  protected Context a;
  protected String a;
  protected final int b;
  protected int c;
  
  public b(Context paramContext, int paramInt1, int paramInt2)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_Int = paramInt2;
    this.b = paramInt1;
  }
  
  public static com.tencent.beacontbs.b.a.b a(int paramInt, byte[] paramArrayOfByte)
  {
    try
    {
      paramArrayOfByte = e.a(paramInt, c.a(), paramArrayOfByte, 2, 3);
      return paramArrayOfByte;
    }
    catch (Throwable paramArrayOfByte)
    {
      a.c("imposiable comStrategy error:%s", new Object[] { paramArrayOfByte.toString() });
      a.a(paramArrayOfByte);
    }
    return null;
  }
  
  public final int a()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public abstract com.tencent.beacontbs.b.a.b a();
  
  public final String a()
  {
    try
    {
      String str = this.jdField_a_of_type_JavaLangString;
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void a()
  {
    a.c("encode failed, clear db data", new Object[0]);
  }
  
  public final String b()
  {
    try
    {
      if (this.b == 0) {
        return com.tencent.beacontbs.a.b.b.a(this.jdField_a_of_type_AndroidContentContext).a().a();
      }
      String str = com.tencent.beacontbs.a.b.b.a(this.jdField_a_of_type_AndroidContentContext).a().a(this.b).a();
      return str;
    }
    catch (Throwable localThrowable)
    {
      a.c("imposiable comStrategy error:%s", new Object[] { localThrowable.toString() });
      a.a(localThrowable);
    }
    return null;
  }
  
  public abstract void b(boolean paramBoolean);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\upload\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */