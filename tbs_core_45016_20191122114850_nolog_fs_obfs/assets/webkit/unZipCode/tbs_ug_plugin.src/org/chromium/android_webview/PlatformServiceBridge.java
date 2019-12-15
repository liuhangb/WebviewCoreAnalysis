package org.chromium.android_webview;

import android.content.Context;
import android.support.annotation.NonNull;
import org.chromium.base.Callback;
import org.chromium.base.ThreadUtils;

public class PlatformServiceBridge
{
  private static final Object jdField_a_of_type_JavaLangObject = new Object();
  private static PlatformServiceBridge jdField_a_of_type_OrgChromiumAndroid_webviewPlatformServiceBridge;
  
  /* Error */
  public static PlatformServiceBridge getInstance()
  {
    // Byte code:
    //   0: getstatic 14	org/chromium/android_webview/PlatformServiceBridge:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
    //   3: astore_2
    //   4: aload_2
    //   5: monitorenter
    //   6: getstatic 33	org/chromium/android_webview/PlatformServiceBridge:jdField_a_of_type_OrgChromiumAndroid_webviewPlatformServiceBridge	Lorg/chromium/android_webview/PlatformServiceBridge;
    //   9: ifnull +11 -> 20
    //   12: getstatic 33	org/chromium/android_webview/PlatformServiceBridge:jdField_a_of_type_OrgChromiumAndroid_webviewPlatformServiceBridge	Lorg/chromium/android_webview/PlatformServiceBridge;
    //   15: astore_0
    //   16: aload_2
    //   17: monitorexit
    //   18: aload_0
    //   19: areturn
    //   20: invokestatic 39	org/chromium/base/StrictModeContext:allowDiskReads	()Lorg/chromium/base/StrictModeContext;
    //   23: astore_3
    //   24: aconst_null
    //   25: astore_1
    //   26: aload_1
    //   27: astore_0
    //   28: ldc 41
    //   30: invokestatic 47	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   33: iconst_0
    //   34: anewarray 43	java/lang/Class
    //   37: invokevirtual 51	java/lang/Class:getDeclaredConstructor	([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
    //   40: iconst_0
    //   41: anewarray 4	java/lang/Object
    //   44: invokevirtual 57	java/lang/reflect/Constructor:newInstance	([Ljava/lang/Object;)Ljava/lang/Object;
    //   47: checkcast 2	org/chromium/android_webview/PlatformServiceBridge
    //   50: putstatic 33	org/chromium/android_webview/PlatformServiceBridge:jdField_a_of_type_OrgChromiumAndroid_webviewPlatformServiceBridge	Lorg/chromium/android_webview/PlatformServiceBridge;
    //   53: aload_1
    //   54: astore_0
    //   55: getstatic 33	org/chromium/android_webview/PlatformServiceBridge:jdField_a_of_type_OrgChromiumAndroid_webviewPlatformServiceBridge	Lorg/chromium/android_webview/PlatformServiceBridge;
    //   58: astore_1
    //   59: aload_3
    //   60: ifnull +7 -> 67
    //   63: aload_3
    //   64: invokevirtual 60	org/chromium/base/StrictModeContext:close	()V
    //   67: aload_2
    //   68: monitorexit
    //   69: aload_1
    //   70: areturn
    //   71: astore_1
    //   72: goto +8 -> 80
    //   75: astore_1
    //   76: aload_1
    //   77: astore_0
    //   78: aload_1
    //   79: athrow
    //   80: aload_3
    //   81: ifnull +27 -> 108
    //   84: aload_0
    //   85: ifnull +19 -> 104
    //   88: aload_3
    //   89: invokevirtual 60	org/chromium/base/StrictModeContext:close	()V
    //   92: goto +16 -> 108
    //   95: astore_3
    //   96: aload_0
    //   97: aload_3
    //   98: invokevirtual 64	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   101: goto +7 -> 108
    //   104: aload_3
    //   105: invokevirtual 60	org/chromium/base/StrictModeContext:close	()V
    //   108: aload_1
    //   109: athrow
    //   110: astore_0
    //   111: ldc 66
    //   113: ldc 68
    //   115: iconst_1
    //   116: anewarray 4	java/lang/Object
    //   119: dup
    //   120: iconst_0
    //   121: aload_0
    //   122: invokevirtual 72	java/lang/reflect/InvocationTargetException:getCause	()Ljava/lang/Throwable;
    //   125: aastore
    //   126: invokestatic 78	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   129: goto +37 -> 166
    //   132: new 80	java/lang/StringBuilder
    //   135: dup
    //   136: invokespecial 81	java/lang/StringBuilder:<init>	()V
    //   139: astore_1
    //   140: aload_1
    //   141: ldc 83
    //   143: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   146: pop
    //   147: aload_1
    //   148: aload_0
    //   149: invokevirtual 90	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   152: pop
    //   153: ldc 66
    //   155: aload_1
    //   156: invokevirtual 94	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   159: iconst_0
    //   160: anewarray 4	java/lang/Object
    //   163: invokestatic 78	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   166: new 2	org/chromium/android_webview/PlatformServiceBridge
    //   169: dup
    //   170: invokespecial 95	org/chromium/android_webview/PlatformServiceBridge:<init>	()V
    //   173: putstatic 33	org/chromium/android_webview/PlatformServiceBridge:jdField_a_of_type_OrgChromiumAndroid_webviewPlatformServiceBridge	Lorg/chromium/android_webview/PlatformServiceBridge;
    //   176: getstatic 33	org/chromium/android_webview/PlatformServiceBridge:jdField_a_of_type_OrgChromiumAndroid_webviewPlatformServiceBridge	Lorg/chromium/android_webview/PlatformServiceBridge;
    //   179: astore_0
    //   180: aload_2
    //   181: monitorexit
    //   182: aload_0
    //   183: areturn
    //   184: astore_0
    //   185: aload_2
    //   186: monitorexit
    //   187: aload_0
    //   188: athrow
    //   189: astore_0
    //   190: goto -24 -> 166
    //   193: astore_0
    //   194: goto -62 -> 132
    //   197: astore_0
    //   198: goto -66 -> 132
    //   201: astore_0
    //   202: goto -70 -> 132
    //   205: astore_0
    //   206: goto -74 -> 132
    // Local variable table:
    //   start	length	slot	name	signature
    //   15	82	0	localObject1	Object
    //   110	39	0	localInvocationTargetException	java.lang.reflect.InvocationTargetException
    //   179	4	0	localPlatformServiceBridge1	PlatformServiceBridge
    //   184	4	0	localObject2	Object
    //   189	1	0	localClassNotFoundException	ClassNotFoundException
    //   193	1	0	localNoSuchMethodException	NoSuchMethodException
    //   197	1	0	localInstantiationException	InstantiationException
    //   201	1	0	localIllegalArgumentException	IllegalArgumentException
    //   205	1	0	localIllegalAccessException	IllegalAccessException
    //   25	45	1	localPlatformServiceBridge2	PlatformServiceBridge
    //   71	1	1	localObject3	Object
    //   75	34	1	localThrowable1	Throwable
    //   139	17	1	localStringBuilder	StringBuilder
    //   3	183	2	localObject4	Object
    //   23	66	3	localStrictModeContext	org.chromium.base.StrictModeContext
    //   95	10	3	localThrowable2	Throwable
    // Exception table:
    //   from	to	target	type
    //   28	53	71	finally
    //   55	59	71	finally
    //   78	80	71	finally
    //   28	53	75	java/lang/Throwable
    //   55	59	75	java/lang/Throwable
    //   88	92	95	java/lang/Throwable
    //   20	24	110	java/lang/reflect/InvocationTargetException
    //   63	67	110	java/lang/reflect/InvocationTargetException
    //   88	92	110	java/lang/reflect/InvocationTargetException
    //   96	101	110	java/lang/reflect/InvocationTargetException
    //   104	108	110	java/lang/reflect/InvocationTargetException
    //   108	110	110	java/lang/reflect/InvocationTargetException
    //   6	18	184	finally
    //   20	24	184	finally
    //   63	67	184	finally
    //   67	69	184	finally
    //   88	92	184	finally
    //   96	101	184	finally
    //   104	108	184	finally
    //   108	110	184	finally
    //   111	129	184	finally
    //   132	166	184	finally
    //   166	182	184	finally
    //   185	187	184	finally
    //   20	24	189	java/lang/ClassNotFoundException
    //   63	67	189	java/lang/ClassNotFoundException
    //   88	92	189	java/lang/ClassNotFoundException
    //   96	101	189	java/lang/ClassNotFoundException
    //   104	108	189	java/lang/ClassNotFoundException
    //   108	110	189	java/lang/ClassNotFoundException
    //   20	24	193	java/lang/NoSuchMethodException
    //   63	67	193	java/lang/NoSuchMethodException
    //   88	92	193	java/lang/NoSuchMethodException
    //   96	101	193	java/lang/NoSuchMethodException
    //   104	108	193	java/lang/NoSuchMethodException
    //   108	110	193	java/lang/NoSuchMethodException
    //   20	24	197	java/lang/InstantiationException
    //   63	67	197	java/lang/InstantiationException
    //   88	92	197	java/lang/InstantiationException
    //   96	101	197	java/lang/InstantiationException
    //   104	108	197	java/lang/InstantiationException
    //   108	110	197	java/lang/InstantiationException
    //   20	24	201	java/lang/IllegalArgumentException
    //   63	67	201	java/lang/IllegalArgumentException
    //   88	92	201	java/lang/IllegalArgumentException
    //   96	101	201	java/lang/IllegalArgumentException
    //   104	108	201	java/lang/IllegalArgumentException
    //   108	110	201	java/lang/IllegalArgumentException
    //   20	24	205	java/lang/IllegalAccessException
    //   63	67	205	java/lang/IllegalAccessException
    //   88	92	205	java/lang/IllegalAccessException
    //   96	101	205	java/lang/IllegalAccessException
    //   104	108	205	java/lang/IllegalAccessException
    //   108	110	205	java/lang/IllegalAccessException
  }
  
  public static void injectInstance(PlatformServiceBridge paramPlatformServiceBridge)
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      jdField_a_of_type_OrgChromiumAndroid_webviewPlatformServiceBridge = paramPlatformServiceBridge;
      return;
    }
  }
  
  public boolean canUseGms()
  {
    return false;
  }
  
  public void logMetrics(byte[] paramArrayOfByte) {}
  
  public void queryMetricsSetting(Callback<Boolean> paramCallback)
  {
    ThreadUtils.assertOnUiThread();
    paramCallback.onResult(Boolean.valueOf(false));
  }
  
  public void querySafeBrowsingUserConsent(Context paramContext, @NonNull Callback<Boolean> paramCallback) {}
  
  public void setSafeBrowsingHandler() {}
  
  public void warmUpSafeBrowsing(Context paramContext, @NonNull Callback<Boolean> paramCallback)
  {
    paramCallback.onResult(Boolean.valueOf(false));
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\PlatformServiceBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */