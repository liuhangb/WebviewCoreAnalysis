package com.tencent.common.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.basesupport.PackageInfo;
import java.util.Iterator;
import java.util.List;

public class ThreadUtils
{
  public static final String MTT_GAME_PROCESS_NAME;
  public static final String MTT_MAIN_PROCESS_NAME = ;
  public static final String SYSTEM_BROWSER_PROCESS_NAME = "com.android.browser";
  private static String jdField_a_of_type_JavaLangString = "";
  private static boolean jdField_a_of_type_Boolean = false;
  private static boolean b = false;
  
  static
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(MTT_MAIN_PROCESS_NAME);
    localStringBuilder.append(":X5Game");
    MTT_GAME_PROCESS_NAME = localStringBuilder.toString();
  }
  
  /* Error */
  public static String getCurrentProcessName(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +6 -> 7
    //   4: ldc 41
    //   6: areturn
    //   7: getstatic 43	com/tencent/common/utils/ThreadUtils:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   10: invokestatic 59	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   13: ifne +7 -> 20
    //   16: getstatic 43	com/tencent/common/utils/ThreadUtils:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   19: areturn
    //   20: getstatic 47	com/tencent/common/utils/ThreadUtils:b	Z
    //   23: ifeq +19 -> 42
    //   26: getstatic 45	com/tencent/common/utils/ThreadUtils:jdField_a_of_type_Boolean	Z
    //   29: ifeq +13 -> 42
    //   32: getstatic 23	com/tencent/common/utils/ThreadUtils:MTT_MAIN_PROCESS_NAME	Ljava/lang/String;
    //   35: astore_0
    //   36: aload_0
    //   37: putstatic 43	com/tencent/common/utils/ThreadUtils:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   40: aload_0
    //   41: areturn
    //   42: getstatic 65	com/tencent/common/utils/ICostTimeLite:PROXY	Lcom/tencent/basesupport/ModuleProxy;
    //   45: invokevirtual 71	com/tencent/basesupport/ModuleProxy:get	()Ljava/lang/Object;
    //   48: checkcast 61	com/tencent/common/utils/ICostTimeLite
    //   51: astore_2
    //   52: aload_2
    //   53: ifnull +11 -> 64
    //   56: aload_2
    //   57: ldc 73
    //   59: invokeinterface 77 2 0
    //   64: invokestatic 83	android/os/Process:myPid	()I
    //   67: istore_1
    //   68: aload_0
    //   69: ldc 85
    //   71: invokevirtual 91	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   74: checkcast 93	android/app/ActivityManager
    //   77: astore_0
    //   78: aload_0
    //   79: ifnull +92 -> 171
    //   82: aload_0
    //   83: invokevirtual 97	android/app/ActivityManager:getRunningAppProcesses	()Ljava/util/List;
    //   86: astore_3
    //   87: aload_3
    //   88: ifnonnull +6 -> 94
    //   91: goto +80 -> 171
    //   94: aload_0
    //   95: invokevirtual 97	android/app/ActivityManager:getRunningAppProcesses	()Ljava/util/List;
    //   98: invokeinterface 103 1 0
    //   103: astore_0
    //   104: aload_0
    //   105: invokeinterface 109 1 0
    //   110: ifeq +46 -> 156
    //   113: aload_0
    //   114: invokeinterface 112 1 0
    //   119: checkcast 114	android/app/ActivityManager$RunningAppProcessInfo
    //   122: astore_3
    //   123: aload_3
    //   124: getfield 118	android/app/ActivityManager$RunningAppProcessInfo:pid	I
    //   127: iload_1
    //   128: if_icmpne -24 -> 104
    //   131: aload_3
    //   132: getfield 121	android/app/ActivityManager$RunningAppProcessInfo:processName	Ljava/lang/String;
    //   135: putstatic 43	com/tencent/common/utils/ThreadUtils:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   138: getstatic 43	com/tencent/common/utils/ThreadUtils:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   141: astore_0
    //   142: aload_2
    //   143: ifnull +11 -> 154
    //   146: aload_2
    //   147: ldc 73
    //   149: invokeinterface 124 2 0
    //   154: aload_0
    //   155: areturn
    //   156: aload_2
    //   157: ifnull +11 -> 168
    //   160: aload_2
    //   161: ldc 73
    //   163: invokeinterface 124 2 0
    //   168: ldc 41
    //   170: areturn
    //   171: aload_2
    //   172: ifnull +11 -> 183
    //   175: aload_2
    //   176: ldc 73
    //   178: invokeinterface 124 2 0
    //   183: ldc 41
    //   185: areturn
    //   186: aload_2
    //   187: ifnull +11 -> 198
    //   190: aload_2
    //   191: ldc 73
    //   193: invokeinterface 124 2 0
    //   198: ldc 41
    //   200: areturn
    //   201: astore_0
    //   202: goto +18 -> 220
    //   205: aload_2
    //   206: ifnull +11 -> 217
    //   209: aload_2
    //   210: ldc 73
    //   212: invokeinterface 124 2 0
    //   217: ldc 41
    //   219: areturn
    //   220: aload_2
    //   221: ifnull +11 -> 232
    //   224: aload_2
    //   225: ldc 73
    //   227: invokeinterface 124 2 0
    //   232: aload_0
    //   233: athrow
    //   234: astore_0
    //   235: goto -30 -> 205
    //   238: astore_0
    //   239: goto -53 -> 186
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	242	0	paramContext	Context
    //   67	62	1	i	int
    //   51	174	2	localICostTimeLite	ICostTimeLite
    //   86	46	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   64	78	201	finally
    //   82	87	201	finally
    //   94	104	201	finally
    //   104	142	201	finally
    //   64	78	234	java/lang/Throwable
    //   94	104	234	java/lang/Throwable
    //   104	142	234	java/lang/Throwable
    //   82	87	238	java/lang/Throwable
  }
  
  public static String getCurrentProcessNameIngoreColon(Context paramContext)
  {
    paramContext = getCurrentProcessName(paramContext);
    if (TextUtils.isEmpty(paramContext)) {
      return "";
    }
    return paramContext.replace(":", "_");
  }
  
  public static String getMainThreadStackTrace()
  {
    Object localObject = Looper.getMainLooper().getThread();
    if (localObject != null)
    {
      localObject = ((Thread)localObject).getStackTrace();
      if (localObject != null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("-------------- stack trace start --------------\r\n");
        int j = localObject.length;
        int i = 0;
        while (i < j)
        {
          localStringBuilder.append(localObject[i].toString());
          localStringBuilder.append("\r\n");
          i += 1;
        }
        return localStringBuilder.toString();
      }
    }
    return null;
  }
  
  @Deprecated
  public static boolean isGameProcessRunning(Context paramContext)
  {
    return isProcessRunning(paramContext, MTT_GAME_PROCESS_NAME);
  }
  
  public static boolean isMainProcess(Context paramContext)
  {
    if (b) {
      return jdField_a_of_type_Boolean;
    }
    paramContext = getCurrentProcessName(paramContext);
    if (!TextUtils.isEmpty(paramContext))
    {
      boolean bool = isStringEqualsIgnoreCase(MTT_MAIN_PROCESS_NAME, paramContext);
      setIsMainProcess(bool);
      return bool;
    }
    return false;
  }
  
  public static boolean isMainThread()
  {
    Looper localLooper = Looper.myLooper();
    return (localLooper != null) && (localLooper == Looper.getMainLooper());
  }
  
  public static boolean isProcessRunning(Context paramContext, String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    try
    {
      paramContext = (ActivityManager)paramContext.getSystemService("activity");
      if (paramContext != null)
      {
        if (paramContext.getRunningAppProcesses() == null) {
          return false;
        }
        paramContext = paramContext.getRunningAppProcesses().iterator();
        while (paramContext.hasNext())
        {
          boolean bool = paramString.equalsIgnoreCase(((ActivityManager.RunningAppProcessInfo)paramContext.next()).processName);
          if (bool) {
            return true;
          }
        }
        return false;
      }
      return false;
    }
    catch (Throwable paramContext) {}
    return false;
  }
  
  @Deprecated
  public static boolean isQQBrowserProcess(Context paramContext)
  {
    return isMainProcess(paramContext);
  }
  
  public static boolean isStringEqualsIgnoreCase(String paramString1, String paramString2)
  {
    return ((TextUtils.isEmpty(paramString1)) && (TextUtils.isEmpty(paramString2))) || ((paramString1 != null) && (paramString1.equalsIgnoreCase(paramString2)));
  }
  
  public static void setIsMainProcess(boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setIsMainProcess: ");
    localStringBuilder.append(paramBoolean);
    FLogger.d("ThreadUtils", localStringBuilder.toString());
    jdField_a_of_type_Boolean = paramBoolean;
    b = true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\ThreadUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */