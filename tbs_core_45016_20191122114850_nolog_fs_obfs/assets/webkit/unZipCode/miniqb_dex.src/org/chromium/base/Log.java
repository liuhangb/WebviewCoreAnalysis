package org.chromium.base;

import java.util.Locale;
import org.chromium.base.annotations.RemovableInRelease;

public class Log
{
  public static final int ASSERT = 7;
  public static final int DEBUG = 3;
  public static final int ERROR = 6;
  public static final int INFO = 4;
  public static final int VERBOSE = 2;
  public static final int WARN = 5;
  
  private static String a()
  {
    StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
    Object localObject = Log.class.getName();
    int i = 0;
    int j;
    for (;;)
    {
      j = i;
      if (i >= arrayOfStackTraceElement.length) {
        break;
      }
      if (arrayOfStackTraceElement[i].getClassName().equals(localObject))
      {
        j = i + 4;
        break;
      }
      i += 1;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(arrayOfStackTraceElement[j].getFileName());
    ((StringBuilder)localObject).append(":");
    ((StringBuilder)localObject).append(arrayOfStackTraceElement[j].getLineNumber());
    return ((StringBuilder)localObject).toString();
  }
  
  private static String a(String paramString, Object... paramVarArgs)
  {
    String str = paramString;
    if (paramVarArgs != null)
    {
      str = paramString;
      if (paramVarArgs.length != 0) {
        str = String.format(Locale.US, paramString, paramVarArgs);
      }
    }
    return str;
  }
  
  private static Throwable a(Object[] paramArrayOfObject)
  {
    if (paramArrayOfObject != null)
    {
      if (paramArrayOfObject.length == 0) {
        return null;
      }
      paramArrayOfObject = paramArrayOfObject[(paramArrayOfObject.length - 1)];
      if (!(paramArrayOfObject instanceof Throwable)) {
        return null;
      }
      return (Throwable)paramArrayOfObject;
    }
    return null;
  }
  
  private static void a(String paramString1, String paramString2, Object... paramVarArgs)
  {
    b(paramString2, paramVarArgs);
    a(paramVarArgs);
  }
  
  private static String b(String paramString, Object... paramVarArgs)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[");
    localStringBuilder.append(a());
    localStringBuilder.append("] ");
    localStringBuilder.append(a(paramString, paramVarArgs));
    return localStringBuilder.toString();
  }
  
  private static void b(String paramString1, String paramString2, Object... paramVarArgs)
  {
    b(paramString2, paramVarArgs);
    a(paramVarArgs);
  }
  
  @VisibleForTesting
  @RemovableInRelease
  public static void d(String paramString1, String paramString2)
  {
    b(paramString1, paramString2, new Object[0]);
  }
  
  @VisibleForTesting
  @RemovableInRelease
  public static void d(String paramString1, String paramString2, Object paramObject)
  {
    b(paramString1, paramString2, new Object[] { paramObject });
  }
  
  @VisibleForTesting
  @RemovableInRelease
  public static void d(String paramString1, String paramString2, Object paramObject1, Object paramObject2)
  {
    b(paramString1, paramString2, new Object[] { paramObject1, paramObject2 });
  }
  
  @VisibleForTesting
  @RemovableInRelease
  public static void d(String paramString1, String paramString2, Object paramObject1, Object paramObject2, Object paramObject3)
  {
    b(paramString1, paramString2, new Object[] { paramObject1, paramObject2, paramObject3 });
  }
  
  @VisibleForTesting
  @RemovableInRelease
  public static void d(String paramString1, String paramString2, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4)
  {
    b(paramString1, paramString2, new Object[] { paramObject1, paramObject2, paramObject3, paramObject4 });
  }
  
  @VisibleForTesting
  @RemovableInRelease
  public static void d(String paramString1, String paramString2, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5)
  {
    b(paramString1, paramString2, new Object[] { paramObject1, paramObject2, paramObject3, paramObject4, paramObject5 });
  }
  
  @VisibleForTesting
  @RemovableInRelease
  public static void d(String paramString1, String paramString2, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object paramObject6)
  {
    b(paramString1, paramString2, new Object[] { paramObject1, paramObject2, paramObject3, paramObject4, paramObject5, paramObject6 });
  }
  
  @VisibleForTesting
  @RemovableInRelease
  public static void d(String paramString1, String paramString2, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object paramObject6, Object paramObject7)
  {
    b(paramString1, paramString2, new Object[] { paramObject1, paramObject2, paramObject3, paramObject4, paramObject5, paramObject6, paramObject7 });
  }
  
  @VisibleForTesting
  public static void e(String paramString1, String paramString2, Object... paramVarArgs)
  {
    paramString2 = a(paramString2, paramVarArgs);
    paramVarArgs = a(paramVarArgs);
    if (paramVarArgs != null)
    {
      android.util.Log.e(normalizeTag(paramString1), paramString2, paramVarArgs);
      return;
    }
    android.util.Log.e(normalizeTag(paramString1), paramString2);
  }
  
  public static String getStackTraceString(Throwable paramThrowable)
  {
    return android.util.Log.getStackTraceString(paramThrowable);
  }
  
  @VisibleForTesting
  public static void i(String paramString1, String paramString2, Object... paramVarArgs)
  {
    a(paramString2, paramVarArgs);
    a(paramVarArgs);
  }
  
  public static boolean isLoggable(String paramString, int paramInt)
  {
    return android.util.Log.isLoggable(paramString, paramInt);
  }
  
  public static String normalizeTag(String paramString)
  {
    if (paramString.startsWith("cr_")) {
      return paramString;
    }
    int i = 0;
    if (paramString.startsWith("cr.")) {
      i = 3;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("cr_");
    localStringBuilder.append(paramString.substring(i, paramString.length()));
    return localStringBuilder.toString();
  }
  
  @VisibleForTesting
  @RemovableInRelease
  public static void v(String paramString1, String paramString2)
  {
    a(paramString1, paramString2, new Object[0]);
  }
  
  @VisibleForTesting
  @RemovableInRelease
  public static void v(String paramString1, String paramString2, Object paramObject)
  {
    a(paramString1, paramString2, new Object[] { paramObject });
  }
  
  @VisibleForTesting
  @RemovableInRelease
  public static void v(String paramString1, String paramString2, Object paramObject1, Object paramObject2)
  {
    a(paramString1, paramString2, new Object[] { paramObject1, paramObject2 });
  }
  
  @VisibleForTesting
  @RemovableInRelease
  public static void v(String paramString1, String paramString2, Object paramObject1, Object paramObject2, Object paramObject3)
  {
    a(paramString1, paramString2, new Object[] { paramObject1, paramObject2, paramObject3 });
  }
  
  @VisibleForTesting
  @RemovableInRelease
  public static void v(String paramString1, String paramString2, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4)
  {
    a(paramString1, paramString2, new Object[] { paramObject1, paramObject2, paramObject3, paramObject4 });
  }
  
  @VisibleForTesting
  @RemovableInRelease
  public static void v(String paramString1, String paramString2, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5)
  {
    a(paramString1, paramString2, new Object[] { paramObject1, paramObject2, paramObject3, paramObject4, paramObject5 });
  }
  
  @VisibleForTesting
  @RemovableInRelease
  public static void v(String paramString1, String paramString2, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object paramObject6)
  {
    a(paramString1, paramString2, new Object[] { paramObject1, paramObject2, paramObject3, paramObject4, paramObject5, paramObject6 });
  }
  
  @VisibleForTesting
  @RemovableInRelease
  public static void v(String paramString1, String paramString2, Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, Object paramObject5, Object paramObject6, Object paramObject7)
  {
    a(paramString1, paramString2, new Object[] { paramObject1, paramObject2, paramObject3, paramObject4, paramObject5, paramObject6, paramObject7 });
  }
  
  @VisibleForTesting
  public static void w(String paramString1, String paramString2, Object... paramVarArgs)
  {
    a(paramString2, paramVarArgs);
    a(paramVarArgs);
  }
  
  @VisibleForTesting
  public static void wtf(String paramString1, String paramString2, Object... paramVarArgs)
  {
    paramString2 = a(paramString2, paramVarArgs);
    paramVarArgs = a(paramVarArgs);
    if (paramVarArgs != null)
    {
      android.util.Log.wtf(normalizeTag(paramString1), paramString2, paramVarArgs);
      return;
    }
    android.util.Log.wtf(normalizeTag(paramString1), paramString2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\Log.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */