package com.tencent.beacontbs.a;

import android.content.Context;

public final class c
{
  private static c jdField_a_of_type_ComTencentBeacontbsAC;
  private byte jdField_a_of_type_Byte = -1;
  private long jdField_a_of_type_Long = 0L;
  private Context jdField_a_of_type_AndroidContentContext = null;
  private String jdField_a_of_type_JavaLangString = "";
  private String b = "";
  private String c = "";
  private String d = "";
  private String e = "";
  private String f = "";
  private String g = "";
  private String h = "";
  private String i = "";
  private String j = "";
  
  public static c a()
  {
    try
    {
      c localc = jdField_a_of_type_ComTencentBeacontbsAC;
      return localc;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  private void a()
  {
    try
    {
      this.jdField_a_of_type_Byte = 1;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static void a(Context paramContext)
  {
    if (paramContext != null) {
      try
      {
        if (jdField_a_of_type_ComTencentBeacontbsAC == null) {
          jdField_a_of_type_ComTencentBeacontbsAC = new c();
        }
        synchronized (jdField_a_of_type_ComTencentBeacontbsAC)
        {
          com.tencent.beacontbs.c.a.e("init cominfo", new Object[0]);
          jdField_a_of_type_ComTencentBeacontbsAC.jdField_a_of_type_AndroidContentContext = paramContext;
          e.a(paramContext);
          jdField_a_of_type_ComTencentBeacontbsAC.jdField_a_of_type_JavaLangString = e.a();
          c localc2 = jdField_a_of_type_ComTencentBeacontbsAC;
          StringBuffer localStringBuffer = new StringBuffer();
          localStringBuffer.append("Android ");
          localStringBuffer.append(e.b());
          localStringBuffer.append(",level ");
          localStringBuffer.append(e.c());
          localc2.b = localStringBuffer.toString();
          jdField_a_of_type_ComTencentBeacontbsAC.a();
          jdField_a_of_type_ComTencentBeacontbsAC.d(a.b(paramContext));
          if (a.jdField_a_of_type_JavaLangString != null) {
            jdField_a_of_type_ComTencentBeacontbsAC.a(a.jdField_a_of_type_JavaLangString);
          }
          jdField_a_of_type_ComTencentBeacontbsAC.e("beacon_tbs");
          jdField_a_of_type_ComTencentBeacontbsAC.f("2.8.2");
          jdField_a_of_type_ComTencentBeacontbsAC.b("unknown");
          jdField_a_of_type_ComTencentBeacontbsAC.g(e.a(paramContext));
          if ((com.tencent.beacontbs.event.c.c() != null) && (!"".equals(com.tencent.beacontbs.event.c.c()))) {
            jdField_a_of_type_ComTencentBeacontbsAC.h(com.tencent.beacontbs.event.c.c());
          } else {
            jdField_a_of_type_ComTencentBeacontbsAC.h(jdField_a_of_type_ComTencentBeacontbsAC.j());
          }
          jdField_a_of_type_ComTencentBeacontbsAC.c(e.b(a.a(paramContext)));
          return;
        }
      }
      finally {}
    }
  }
  
  private void d(String paramString)
  {
    try
    {
      this.c = paramString;
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  private void e(String paramString)
  {
    try
    {
      this.e = paramString;
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  private void f(String paramString)
  {
    try
    {
      this.f = paramString;
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  /* Error */
  private void g(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 29
    //   4: aload_1
    //   5: invokevirtual 129	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   8: istore_2
    //   9: iload_2
    //   10: ifne +14 -> 24
    //   13: aload_0
    //   14: getfield 27	com/tencent/beacontbs/a/c:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   17: ldc -113
    //   19: aload_1
    //   20: invokestatic 146	com/tencent/beacontbs/a/a:a	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Z
    //   23: pop
    //   24: aload_0
    //   25: aload_1
    //   26: putfield 49	com/tencent/beacontbs/a/c:h	Ljava/lang/String;
    //   29: aload_0
    //   30: monitorexit
    //   31: return
    //   32: astore_1
    //   33: aload_0
    //   34: monitorexit
    //   35: aload_1
    //   36: athrow
    //   37: astore_3
    //   38: goto -14 -> 24
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	41	0	this	c
    //   0	41	1	paramString	String
    //   8	2	2	bool	boolean
    //   37	1	3	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   2	9	32	finally
    //   13	24	32	finally
    //   24	29	32	finally
    //   13	24	37	java/lang/Exception
  }
  
  private void h(String paramString)
  {
    try
    {
      this.i = paramString;
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  private String j()
  {
    try
    {
      String str = this.c;
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final byte a()
  {
    try
    {
      byte b1 = this.jdField_a_of_type_Byte;
      return b1;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final long a()
  {
    try
    {
      long l = this.jdField_a_of_type_Long;
      return l;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final Context a()
  {
    try
    {
      Context localContext = this.jdField_a_of_type_AndroidContentContext;
      return localContext;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final String a()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public final void a(long paramLong)
  {
    try
    {
      this.jdField_a_of_type_Long = paramLong;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void a(String paramString)
  {
    try
    {
      this.d = paramString;
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public final String b()
  {
    return this.b;
  }
  
  public final void b(String paramString)
  {
    try
    {
      this.g = paramString;
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public final String c()
  {
    try
    {
      String str = this.d;
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void c(String paramString)
  {
    try
    {
      this.j = paramString;
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public final String d()
  {
    try
    {
      String str = this.e;
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final String e()
  {
    try
    {
      String str = this.f;
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final String f()
  {
    try
    {
      String str = this.g;
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  /* Error */
  public final String g()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 29
    //   4: aload_0
    //   5: getfield 49	com/tencent/beacontbs/a/c:h	Ljava/lang/String;
    //   8: invokevirtual 129	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   11: istore_1
    //   12: iload_1
    //   13: ifeq +18 -> 31
    //   16: aload_0
    //   17: aload_0
    //   18: getfield 27	com/tencent/beacontbs/a/c:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   21: ldc -113
    //   23: ldc 29
    //   25: invokestatic 153	com/tencent/beacontbs/a/a:a	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   28: putfield 49	com/tencent/beacontbs/a/c:h	Ljava/lang/String;
    //   31: aload_0
    //   32: getfield 49	com/tencent/beacontbs/a/c:h	Ljava/lang/String;
    //   35: astore_2
    //   36: aload_0
    //   37: monitorexit
    //   38: aload_2
    //   39: areturn
    //   40: astore_2
    //   41: aload_0
    //   42: monitorexit
    //   43: aload_2
    //   44: athrow
    //   45: astore_2
    //   46: goto -15 -> 31
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	49	0	this	c
    //   11	2	1	bool	boolean
    //   35	4	2	str	String
    //   40	4	2	localObject	Object
    //   45	1	2	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   2	12	40	finally
    //   16	31	40	finally
    //   31	36	40	finally
    //   16	31	45	java/lang/Exception
  }
  
  public final String h()
  {
    try
    {
      String str = this.i;
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final String i()
  {
    try
    {
      String str = this.j;
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\a\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */