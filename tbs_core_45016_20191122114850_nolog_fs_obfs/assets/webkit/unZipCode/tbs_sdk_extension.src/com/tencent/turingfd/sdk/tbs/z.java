package com.tencent.turingfd.sdk.tbs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.SparseArray;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class z
{
  public static z a;
  public Handler a;
  public ab a;
  public m a;
  public x a;
  public AtomicReference<Boolean> a;
  public boolean a;
  
  static
  {
    jdField_a_of_type_ComTencentTuringfdSdkTbsZ = new z();
  }
  
  public z()
  {
    this.jdField_a_of_type_Boolean = false;
    this.jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference = new AtomicReference(Boolean.valueOf(false));
  }
  
  public static z a()
  {
    return jdField_a_of_type_ComTencentTuringfdSdkTbsZ;
  }
  
  public final x a(Context paramContext)
  {
    for (;;)
    {
      try
      {
        if (this.jdField_a_of_type_ComTencentTuringfdSdkTbsX == null) {
          this.jdField_a_of_type_ComTencentTuringfdSdkTbsX = this.jdField_a_of_type_ComTencentTuringfdSdkTbsAb.a(paramContext);
        }
        x localx = this.jdField_a_of_type_ComTencentTuringfdSdkTbsX;
        if (localx.jdField_a_of_type_Int != 0)
        {
          i = 2;
        }
        else
        {
          if (System.currentTimeMillis() / 1000L < localx.jdField_a_of_type_Long) {
            break label145;
          }
          i = 3;
        }
        if (i == 1)
        {
          paramContext = this.jdField_a_of_type_ComTencentTuringfdSdkTbsX;
          return paramContext;
        }
        if (i == 2)
        {
          this.jdField_a_of_type_ComTencentTuringfdSdkTbsX = a(paramContext, false);
        }
        else if ((i == 3) && (!((Boolean)this.jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference.get()).booleanValue()))
        {
          this.jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference.set(Boolean.valueOf(true));
          this.jdField_a_of_type_AndroidOsHandler.sendEmptyMessage(2);
        }
        paramContext = this.jdField_a_of_type_ComTencentTuringfdSdkTbsX;
        return paramContext;
      }
      finally {}
      label145:
      int i = 1;
    }
  }
  
  public final x a(Context paramContext, int paramInt, boolean paramBoolean)
  {
    long l = System.currentTimeMillis();
    int i;
    if (paramContext.getSharedPreferences("turingfd_protect_105578_28_tbsMini", 0).getInt("301", 0) >= 10) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return x.a(55531);
    }
    Object localObject2 = new HashMap();
    Object localObject1;
    if (!TextUtils.isEmpty(c.jdField_a_of_type_JavaLangString))
    {
      localObject1 = c.jdField_a_of_type_JavaLangString;
    }
    else if (!c.jdField_a_of_type_JavaUtilMap.containsKey(c.b))
    {
      localObject1 = c.jdField_a_of_type_JavaLangString;
    }
    else
    {
      localObject1 = (c.a)c.jdField_a_of_type_JavaUtilMap.get(c.b);
      if (localObject1 != null)
      {
        i = ((c.a)localObject1).jdField_a_of_type_Int;
        if (i <= 3)
        {
          ((c.a)localObject1).jdField_a_of_type_Int = (i + 1);
          c.jdField_a_of_type_JavaLangString = ((c.a)localObject1).jdField_a_of_type_ComTencentTuringfdSdkTbsA.a(paramContext);
        }
      }
      localObject1 = c.jdField_a_of_type_JavaLangString;
    }
    ((HashMap)localObject2).put("100", localObject1);
    ((HashMap)localObject2).put("101", this.jdField_a_of_type_ComTencentTuringfdSdkTbsM.a());
    ((HashMap)localObject2).put("205", ae.a(paramContext));
    ((HashMap)localObject2).put("207", this.jdField_a_of_type_ComTencentTuringfdSdkTbsAb.a(paramContext).jdField_a_of_type_JavaLangString);
    ((HashMap)localObject2).put("209", this.jdField_a_of_type_ComTencentTuringfdSdkTbsM.b());
    ((HashMap)localObject2).put("210", ad.a(paramContext, paramBoolean));
    ((HashMap)localObject2).put("302", this.jdField_a_of_type_ComTencentTuringfdSdkTbsAb.c(paramContext));
    ((HashMap)localObject2).put("303", this.jdField_a_of_type_ComTencentTuringfdSdkTbsAb.b(paramContext));
    ((HashMap)localObject2).put("305", this.jdField_a_of_type_ComTencentTuringfdSdkTbsAb.a(paramContext));
    ((HashMap)localObject2).put("306", ae.a());
    if (paramBoolean) {
      ((HashMap)localObject2).put("1001", "1");
    }
    l.a(paramContext, "301", paramContext.getSharedPreferences("turingfd_protect_105578_28_tbsMini", 0).getInt("301", 0));
    try
    {
      localObject1 = TuringDIDService.aa.a(new SparseArray(), paramContext, (Map)localObject2, paramInt);
      l.a(paramContext, "301", 0);
      this.jdField_a_of_type_ComTencentTuringfdSdkTbsAb.c(paramContext, System.currentTimeMillis() - l);
      paramInt = l.a((SparseArray)localObject1);
      localObject2 = (byte[])l.a((SparseArray)localObject1, 1, byte[].class);
      localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = new byte[0];
      }
      this.jdField_a_of_type_ComTencentTuringfdSdkTbsAb.b(paramContext, localObject1.length);
      this.jdField_a_of_type_ComTencentTuringfdSdkTbsM.a();
      if (paramInt == 0) {
        return new x(0, (byte[])localObject1);
      }
      return x.a(paramInt);
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
    return x.a(55530);
  }
  
  /* Error */
  public final x a(Context paramContext, boolean paramBoolean)
  {
    // Byte code:
    //   0: new 4	java/lang/Object
    //   3: dup
    //   4: invokespecial 27	java/lang/Object:<init>	()V
    //   7: astore 5
    //   9: new 31	java/util/concurrent/atomic/AtomicReference
    //   12: dup
    //   13: invokespecial 249	java/util/concurrent/atomic/AtomicReference:<init>	()V
    //   16: astore 6
    //   18: new 31	java/util/concurrent/atomic/AtomicReference
    //   21: dup
    //   22: invokespecial 249	java/util/concurrent/atomic/AtomicReference:<init>	()V
    //   25: astore 7
    //   27: aload 7
    //   29: iconst_0
    //   30: invokestatic 37	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   33: invokevirtual 270	java/util/concurrent/atomic/AtomicReference:set	(Ljava/lang/Object;)V
    //   36: aload 6
    //   38: sipush 55532
    //   41: invokestatic 286	com/tencent/turingfd/sdk/tbs/x:a	(I)Lcom/tencent/turingfd/sdk/tbs/x;
    //   44: invokevirtual 270	java/util/concurrent/atomic/AtomicReference:set	(Ljava/lang/Object;)V
    //   47: aload_0
    //   48: getfield 370	com/tencent/turingfd/sdk/tbs/z:jdField_a_of_type_AndroidOsHandler	Landroid/os/Handler;
    //   51: new 6	com/tencent/turingfd/sdk/tbs/z$a
    //   54: dup
    //   55: aload_0
    //   56: aload_1
    //   57: iload_2
    //   58: aload 6
    //   60: aload 7
    //   62: aload 5
    //   64: invokespecial 500	com/tencent/turingfd/sdk/tbs/z$a:<init>	(Lcom/tencent/turingfd/sdk/tbs/z;Landroid/content/Context;ZLjava/util/concurrent/atomic/AtomicReference;Ljava/util/concurrent/atomic/AtomicReference;Ljava/lang/Object;)V
    //   67: invokevirtual 504	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   70: pop
    //   71: aload 5
    //   73: monitorenter
    //   74: aload_1
    //   75: invokestatic 291	com/tencent/turingfd/sdk/tbs/l:a	(Landroid/content/Context;)B
    //   78: istore 4
    //   80: iconst_2
    //   81: istore_3
    //   82: iload 4
    //   84: iconst_2
    //   85: if_icmpne +58 -> 143
    //   88: goto +3 -> 91
    //   91: aload 5
    //   93: aload_0
    //   94: getfield 45	com/tencent/turingfd/sdk/tbs/z:jdField_a_of_type_ComTencentTuringfdSdkTbsM	Lcom/tencent/turingfd/sdk/tbs/m;
    //   97: invokevirtual 505	com/tencent/turingfd/sdk/tbs/m:a	()I
    //   100: iload_3
    //   101: imul
    //   102: i2l
    //   103: invokevirtual 509	java/lang/Object:wait	(J)V
    //   106: goto +7 -> 113
    //   109: astore_1
    //   110: goto +24 -> 134
    //   113: aload 5
    //   115: monitorexit
    //   116: aload 7
    //   118: iconst_1
    //   119: invokestatic 37	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   122: invokevirtual 270	java/util/concurrent/atomic/AtomicReference:set	(Ljava/lang/Object;)V
    //   125: aload 6
    //   127: invokevirtual 277	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
    //   130: checkcast 75	com/tencent/turingfd/sdk/tbs/x
    //   133: areturn
    //   134: aload 5
    //   136: monitorexit
    //   137: aload_1
    //   138: athrow
    //   139: astore_1
    //   140: goto -27 -> 113
    //   143: iconst_1
    //   144: istore_3
    //   145: goto -54 -> 91
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	148	0	this	z
    //   0	148	1	paramContext	Context
    //   0	148	2	paramBoolean	boolean
    //   81	64	3	i	int
    //   78	8	4	j	int
    //   7	128	5	localObject	Object
    //   16	110	6	localAtomicReference1	AtomicReference
    //   25	92	7	localAtomicReference2	AtomicReference
    // Exception table:
    //   from	to	target	type
    //   74	80	109	finally
    //   91	106	109	finally
    //   113	116	109	finally
    //   134	137	109	finally
    //   74	80	139	java/lang/InterruptedException
    //   91	106	139	java/lang/InterruptedException
  }
  
  public class a
    implements Runnable
  {
    /* Error */
    public a(Context paramContext, boolean paramBoolean, AtomicReference paramAtomicReference1, AtomicReference paramAtomicReference2, Object paramObject)
    {
      // Byte code:
      //   0: aload_0
      //   1: aload_1
      //   2: putfield 21	com/tencent/turingfd/sdk/tbs/z$a:jdField_a_of_type_ComTencentTuringfdSdkTbsZ	Lcom/tencent/turingfd/sdk/tbs/z;
      //   5: aload_0
      //   6: aload_2
      //   7: putfield 23	com/tencent/turingfd/sdk/tbs/z$a:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
      //   10: aload_0
      //   11: iload_3
      //   12: putfield 25	com/tencent/turingfd/sdk/tbs/z$a:jdField_a_of_type_Boolean	Z
      //   15: aload_0
      //   16: aload 4
      //   18: putfield 27	com/tencent/turingfd/sdk/tbs/z$a:jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference	Ljava/util/concurrent/atomic/AtomicReference;
      //   21: aload_0
      //   22: aload 5
      //   24: putfield 29	com/tencent/turingfd/sdk/tbs/z$a:b	Ljava/util/concurrent/atomic/AtomicReference;
      //   27: aload_0
      //   28: aload 6
      //   30: putfield 31	com/tencent/turingfd/sdk/tbs/z$a:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
      //   33: aload_0
      //   34: invokespecial 34	java/lang/Object:<init>	()V
      //   37: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	38	0	this	a
      //   0	38	1	this$1	z
      //   0	38	2	paramContext	Context
      //   0	38	3	paramBoolean	boolean
      //   0	38	4	paramAtomicReference1	AtomicReference
      //   0	38	5	paramAtomicReference2	AtomicReference
      //   0	38	6	paramObject	Object
    }
    
    public void run()
    {
      int i = 0;
      while (i < z.a(this.jdField_a_of_type_ComTencentTuringfdSdkTbsZ).b())
      {
        ??? = z.a(this.jdField_a_of_type_ComTencentTuringfdSdkTbsZ, this.jdField_a_of_type_AndroidContentContext, this.jdField_a_of_type_Boolean);
        this.jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference.set(???);
        if ((((x)???).a == 0) || (((Boolean)this.b.get()).booleanValue())) {
          break;
        }
        i += 1;
      }
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        this.jdField_a_of_type_JavaLangObject.notifyAll();
        return;
      }
    }
  }
  
  private class b
    extends Handler
  {
    public Context a;
    
    public b(Looper paramLooper, Context paramContext)
    {
      super();
      this.jdField_a_of_type_AndroidContentContext = paramContext;
    }
    
    public void handleMessage(Message paramMessage)
    {
      System.currentTimeMillis();
      int i = paramMessage.what;
      if (i != 1)
      {
        if (i != 2) {
          return;
        }
        paramMessage = this.jdField_a_of_type_ComTencentTuringfdSdkTbsZ;
        z.a(paramMessage, z.a(paramMessage, this.jdField_a_of_type_AndroidContentContext, true));
        z.a(this.jdField_a_of_type_ComTencentTuringfdSdkTbsZ).set(Boolean.valueOf(false));
        return;
      }
      z.a(this.jdField_a_of_type_ComTencentTuringfdSdkTbsZ, this.jdField_a_of_type_AndroidContentContext);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\turingfd\sdk\tbs\z.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */