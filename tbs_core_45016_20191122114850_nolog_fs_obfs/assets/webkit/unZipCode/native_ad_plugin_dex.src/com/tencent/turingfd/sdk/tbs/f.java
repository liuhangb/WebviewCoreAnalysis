package com.tencent.turingfd.sdk.tbs;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.text.TextUtils;
import java.util.concurrent.atomic.AtomicReference;

public class f
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
    Intent localIntent = new Intent();
    localIntent.setComponent(new ComponentName(o.a(o.r), o.a(o.s)));
    if (!paramContext.bindService(localIntent, new a(l, paramAtomicReference, paramAtomicReference1, localObject), 1)) {
      return 65136;
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
        break label104;
      }
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
    break label109;
    label104:
    throw paramContext;
    label109:
    if (paramAtomicReference.get() == null) {
      return 65131;
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
    jdField_a_of_type_Int = 65130;
    return jdField_a_of_type_JavaLangString;
  }
  
  public String a(Context paramContext)
  {
    int i = 0;
    try
    {
      Object localObject = o.a(o.r);
      localObject = paramContext.getPackageManager().getPackageInfo((String)localObject, 0);
      if (localObject != null)
      {
        long l;
        if (Build.VERSION.SDK_INT >= 28) {
          l = ((PackageInfo)localObject).getLongVersionCode();
        } else {
          l = ((PackageInfo)localObject).versionCode;
        }
        if (l >= 1L) {
          i = 1;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    if (i == 0)
    {
      jdField_a_of_type_Int = 65129;
      return jdField_a_of_type_JavaLangString;
    }
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
      //   2: putfield 19	com/tencent/turingfd/sdk/tbs/f$a:jdField_a_of_type_Long	J
      //   5: aload_0
      //   6: aload_3
      //   7: putfield 21	com/tencent/turingfd/sdk/tbs/f$a:jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference	Ljava/util/concurrent/atomic/AtomicReference;
      //   10: aload_0
      //   11: aload 4
      //   13: putfield 23	com/tencent/turingfd/sdk/tbs/f$a:b	Ljava/util/concurrent/atomic/AtomicReference;
      //   16: aload_0
      //   17: aload 5
      //   19: putfield 25	com/tencent/turingfd/sdk/tbs/f$a:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
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
      f.jdField_a_of_type_Long = System.currentTimeMillis() - this.jdField_a_of_type_Long;
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
      //   2: putfield 18	com/tencent/turingfd/sdk/tbs/f$b:jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference	Ljava/util/concurrent/atomic/AtomicReference;
      //   5: aload_0
      //   6: aload_2
      //   7: putfield 20	com/tencent/turingfd/sdk/tbs/f$b:b	Ljava/util/concurrent/atomic/AtomicReference;
      //   10: aload_0
      //   11: aload_3
      //   12: putfield 22	com/tencent/turingfd/sdk/tbs/f$b:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
      //   15: aload_0
      //   16: aload 4
      //   18: putfield 24	com/tencent/turingfd/sdk/tbs/f$b:c	Ljava/util/concurrent/atomic/AtomicReference;
      //   21: aload_0
      //   22: aload 5
      //   24: putfield 26	com/tencent/turingfd/sdk/tbs/f$b:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
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
    
    public void run()
    {
      try
      {
        ??? = i.a.a((IBinder)this.jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference.get());
      }
      catch (Throwable localThrowable1)
      {
        for (;;) {}
      }
      localAtomicReference.set(Integer.valueOf(65135));
      ??? = null;
      if (??? != null) {}
      try
      {
        String str1 = this.jdField_a_of_type_AndroidContentContext.getPackageName();
        Object localObject3 = this.jdField_a_of_type_AndroidContentContext;
        localObject3 = l.a((Context)localObject3, str1, "SHA1");
        String str2 = o.a(o.u);
        ??? = (i.a.a)???;
        f.a = ((i.a.a)???).a(str1, (String)localObject3, str2);
      }
      catch (Throwable localThrowable2)
      {
        for (;;) {}
      }
      localAtomicReference.set(Integer.valueOf(65134));
      try
      {
        this.jdField_a_of_type_AndroidContentContext.unbindService((ServiceConnection)this.c.get());
      }
      catch (Throwable localThrowable3)
      {
        for (;;) {}
      }
      localAtomicReference.set(Integer.valueOf(65133));
      try
      {
        synchronized (this.jdField_a_of_type_JavaLangObject)
        {
          this.jdField_a_of_type_JavaLangObject.notifyAll();
        }
      }
      catch (Throwable localThrowable4)
      {
        for (;;) {}
      }
      return;
      throw ((Throwable)localObject2);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\turingfd\sdk\tbs\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */