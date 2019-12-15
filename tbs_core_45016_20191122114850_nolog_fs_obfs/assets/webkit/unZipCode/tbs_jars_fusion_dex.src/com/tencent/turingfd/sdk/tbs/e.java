package com.tencent.turingfd.sdk.tbs;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;
import java.util.concurrent.atomic.AtomicReference;

public class e
  implements a
{
  public static int a = -1;
  public static long a = -1L;
  public static String a = "";
  public static long b = -1L;
  
  public static int a(Context paramContext, AtomicReference<IBinder> paramAtomicReference, AtomicReference<ServiceConnection> paramAtomicReference1)
  {
    long l = System.currentTimeMillis();
    Object localObject = new Object();
    Intent localIntent = new Intent(o.a(o.a));
    localIntent.setPackage(o.a(o.b));
    if (!paramContext.bindService(localIntent, new a(l, paramAtomicReference, paramAtomicReference1, localObject), 1)) {
      return -100;
    }
    if (paramAtomicReference.get() == null) {}
    try
    {
      try
      {
        localObject.wait(1000L);
      }
      finally
      {
        break label96;
      }
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
    break label101;
    label96:
    throw paramContext;
    label101:
    if (paramAtomicReference.get() == null) {
      return -105;
    }
    return 0;
  }
  
  public static int b(Context paramContext, AtomicReference<IBinder> paramAtomicReference, AtomicReference<ServiceConnection> paramAtomicReference1)
  {
    Object localObject = new Object();
    final AtomicReference localAtomicReference = new AtomicReference(Integer.valueOf(0));
    long l = System.currentTimeMillis();
    new b(paramAtomicReference, localAtomicReference, paramContext, paramAtomicReference1, localObject).start();
    try
    {
      try
      {
        localObject.wait(1000L);
      }
      finally
      {
        break label84;
      }
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
    b = System.currentTimeMillis() - l;
    return ((Integer)localAtomicReference.get()).intValue();
    label84:
    throw paramContext;
  }
  
  public static String c(Context paramContext)
  {
    try
    {
      jdField_a_of_type_Int = 0;
      AtomicReference localAtomicReference1 = new AtomicReference();
      AtomicReference localAtomicReference2 = new AtomicReference();
      jdField_a_of_type_Int = a(paramContext, localAtomicReference1, localAtomicReference2);
      if (jdField_a_of_type_Int == 0) {
        jdField_a_of_type_Int = b(paramContext, localAtomicReference1, localAtomicReference2);
      }
      paramContext = jdField_a_of_type_JavaLangString;
      return paramContext;
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
    jdField_a_of_type_Int = -106;
    return jdField_a_of_type_JavaLangString;
  }
  
  public String a(Context paramContext)
  {
    if (TextUtils.isEmpty(jdField_a_of_type_JavaLangString)) {
      return c(paramContext);
    }
    return jdField_a_of_type_JavaLangString;
  }
  
  public void a(Context paramContext)
  {
    c(paramContext);
  }
  
  public String b(Context paramContext)
  {
    paramContext = new StringBuilder();
    paramContext.append(jdField_a_of_type_Int);
    paramContext.append("_");
    paramContext.append(jdField_a_of_type_Long);
    paramContext.append("_");
    paramContext.append(b);
    return paramContext.toString();
  }
  
  public static final class a
    implements ServiceConnection
  {
    /* Error */
    public a(long paramLong, AtomicReference paramAtomicReference1, AtomicReference paramAtomicReference2, Object paramObject)
    {
      // Byte code:
      //   0: aload_0
      //   1: lload_1
      //   2: putfield 19	com/tencent/turingfd/sdk/tbs/e$a:jdField_a_of_type_Long	J
      //   5: aload_0
      //   6: aload_3
      //   7: putfield 21	com/tencent/turingfd/sdk/tbs/e$a:jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference	Ljava/util/concurrent/atomic/AtomicReference;
      //   10: aload_0
      //   11: aload 4
      //   13: putfield 23	com/tencent/turingfd/sdk/tbs/e$a:b	Ljava/util/concurrent/atomic/AtomicReference;
      //   16: aload_0
      //   17: aload 5
      //   19: putfield 25	com/tencent/turingfd/sdk/tbs/e$a:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
      //   22: aload_0
      //   23: invokespecial 28	java/lang/Object:<init>	()V
      //   26: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	27	0	this	a
      //   0	27	1	paramLong	long
      //   0	27	3	paramAtomicReference1	AtomicReference
      //   0	27	4	paramAtomicReference2	AtomicReference
      //   0	27	5	paramObject	Object
    }
    
    public void onServiceConnected(ComponentName arg1, IBinder paramIBinder)
    {
      e.jdField_a_of_type_Long = System.currentTimeMillis() - this.jdField_a_of_type_Long;
      this.jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference.set(paramIBinder);
      this.b.set(this);
      try
      {
        synchronized (this.jdField_a_of_type_JavaLangObject)
        {
          this.jdField_a_of_type_JavaLangObject.notifyAll();
        }
      }
      catch (Throwable paramIBinder)
      {
        for (;;) {}
      }
      return;
      throw paramIBinder;
    }
    
    public void onServiceDisconnected(ComponentName paramComponentName) {}
  }
  
  public static final class b
    extends Thread
  {
    /* Error */
    public b(AtomicReference paramAtomicReference1, AtomicReference paramAtomicReference2, Context paramContext, AtomicReference paramAtomicReference3, Object paramObject)
    {
      // Byte code:
      //   0: aload_0
      //   1: aload_1
      //   2: putfield 18	com/tencent/turingfd/sdk/tbs/e$b:jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference	Ljava/util/concurrent/atomic/AtomicReference;
      //   5: aload_0
      //   6: aload_2
      //   7: putfield 20	com/tencent/turingfd/sdk/tbs/e$b:b	Ljava/util/concurrent/atomic/AtomicReference;
      //   10: aload_0
      //   11: aload_3
      //   12: putfield 22	com/tencent/turingfd/sdk/tbs/e$b:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
      //   15: aload_0
      //   16: aload 4
      //   18: putfield 24	com/tencent/turingfd/sdk/tbs/e$b:c	Ljava/util/concurrent/atomic/AtomicReference;
      //   21: aload_0
      //   22: aload 5
      //   24: putfield 26	com/tencent/turingfd/sdk/tbs/e$b:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
      //   27: aload_0
      //   28: invokespecial 29	java/lang/Thread:<init>	()V
      //   31: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	32	0	this	b
      //   0	32	1	paramAtomicReference1	AtomicReference
      //   0	32	2	paramAtomicReference2	AtomicReference
      //   0	32	3	paramContext	Context
      //   0	32	4	paramAtomicReference3	AtomicReference
      //   0	32	5	paramObject	Object
    }
    
    /* Error */
    public void run()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 18	com/tencent/turingfd/sdk/tbs/e$b:jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference	Ljava/util/concurrent/atomic/AtomicReference;
      //   4: invokevirtual 39	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   7: checkcast 41	android/os/IBinder
      //   10: invokestatic 46	com/tencent/turingfd/sdk/tbs/d$a:a	(Landroid/os/IBinder;)Lcom/tencent/turingfd/sdk/tbs/d;
      //   13: astore_1
      //   14: goto +17 -> 31
      //   17: aload_0
      //   18: getfield 20	com/tencent/turingfd/sdk/tbs/e$b:b	Ljava/util/concurrent/atomic/AtomicReference;
      //   21: bipush -101
      //   23: invokestatic 52	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   26: invokevirtual 56	java/util/concurrent/atomic/AtomicReference:set	(Ljava/lang/Object;)V
      //   29: aconst_null
      //   30: astore_1
      //   31: aload_1
      //   32: ifnull +84 -> 116
      //   35: aload_1
      //   36: checkcast 58	com/tencent/turingfd/sdk/tbs/d$a$a
      //   39: astore_3
      //   40: invokestatic 64	android/os/Parcel:obtain	()Landroid/os/Parcel;
      //   43: astore_1
      //   44: invokestatic 64	android/os/Parcel:obtain	()Landroid/os/Parcel;
      //   47: astore_2
      //   48: aload_1
      //   49: getstatic 67	com/tencent/turingfd/sdk/tbs/d$a:a	Ljava/lang/String;
      //   52: invokevirtual 71	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
      //   55: aload_3
      //   56: getfield 74	com/tencent/turingfd/sdk/tbs/d$a$a:a	Landroid/os/IBinder;
      //   59: iconst_1
      //   60: aload_1
      //   61: aload_2
      //   62: iconst_0
      //   63: invokeinterface 78 5 0
      //   68: pop
      //   69: aload_2
      //   70: invokevirtual 81	android/os/Parcel:readException	()V
      //   73: aload_2
      //   74: invokevirtual 85	android/os/Parcel:readString	()Ljava/lang/String;
      //   77: astore_3
      //   78: aload_2
      //   79: invokevirtual 88	android/os/Parcel:recycle	()V
      //   82: aload_1
      //   83: invokevirtual 88	android/os/Parcel:recycle	()V
      //   86: aload_3
      //   87: putstatic 89	com/tencent/turingfd/sdk/tbs/e:a	Ljava/lang/String;
      //   90: goto +26 -> 116
      //   93: astore_3
      //   94: aload_2
      //   95: invokevirtual 88	android/os/Parcel:recycle	()V
      //   98: aload_1
      //   99: invokevirtual 88	android/os/Parcel:recycle	()V
      //   102: aload_3
      //   103: athrow
      //   104: aload_0
      //   105: getfield 20	com/tencent/turingfd/sdk/tbs/e$b:b	Ljava/util/concurrent/atomic/AtomicReference;
      //   108: bipush -102
      //   110: invokestatic 52	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   113: invokevirtual 56	java/util/concurrent/atomic/AtomicReference:set	(Ljava/lang/Object;)V
      //   116: aload_0
      //   117: getfield 22	com/tencent/turingfd/sdk/tbs/e$b:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
      //   120: aload_0
      //   121: getfield 24	com/tencent/turingfd/sdk/tbs/e$b:c	Ljava/util/concurrent/atomic/AtomicReference;
      //   124: invokevirtual 39	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   127: checkcast 91	android/content/ServiceConnection
      //   130: invokevirtual 97	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
      //   133: goto +15 -> 148
      //   136: aload_0
      //   137: getfield 20	com/tencent/turingfd/sdk/tbs/e$b:b	Ljava/util/concurrent/atomic/AtomicReference;
      //   140: bipush -103
      //   142: invokestatic 52	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   145: invokevirtual 56	java/util/concurrent/atomic/AtomicReference:set	(Ljava/lang/Object;)V
      //   148: aload_0
      //   149: getfield 26	com/tencent/turingfd/sdk/tbs/e$b:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
      //   152: astore_1
      //   153: aload_1
      //   154: monitorenter
      //   155: aload_0
      //   156: getfield 26	com/tencent/turingfd/sdk/tbs/e$b:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
      //   159: invokevirtual 102	java/lang/Object:notifyAll	()V
      //   162: goto +7 -> 169
      //   165: astore_2
      //   166: goto +6 -> 172
      //   169: aload_1
      //   170: monitorexit
      //   171: return
      //   172: aload_1
      //   173: monitorexit
      //   174: aload_2
      //   175: athrow
      //   176: astore_1
      //   177: goto -160 -> 17
      //   180: astore_1
      //   181: goto -77 -> 104
      //   184: astore_1
      //   185: goto -49 -> 136
      //   188: astore_2
      //   189: goto -20 -> 169
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	192	0	this	b
      //   176	1	1	localThrowable1	Throwable
      //   180	1	1	localThrowable2	Throwable
      //   184	1	1	localThrowable3	Throwable
      //   47	48	2	localParcel	android.os.Parcel
      //   165	10	2	localObject2	Object
      //   188	1	2	localThrowable4	Throwable
      //   39	48	3	localObject3	Object
      //   93	10	3	localObject4	Object
      // Exception table:
      //   from	to	target	type
      //   48	78	93	finally
      //   155	162	165	finally
      //   169	171	165	finally
      //   172	174	165	finally
      //   0	14	176	java/lang/Throwable
      //   35	48	180	java/lang/Throwable
      //   78	90	180	java/lang/Throwable
      //   94	104	180	java/lang/Throwable
      //   116	133	184	java/lang/Throwable
      //   155	162	188	java/lang/Throwable
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\turingfd\sdk\tbs\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */