package org.chromium.base;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Looper;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.MainDex;
import org.chromium.tencent.TencentAwBrowserProcess;
import org.chromium.tencent.base.TencentPathUtils;

@MainDex
public abstract class PathUtils
{
  public static final int DATA_DIRECTORY = 0;
  private static AsyncTask<Void, Void, String[]> jdField_a_of_type_AndroidOsAsyncTask;
  private static String jdField_a_of_type_JavaLangString;
  private static final AtomicBoolean jdField_a_of_type_JavaUtilConcurrentAtomicAtomicBoolean = new AtomicBoolean();
  private static String b;
  
  /* Error */
  private static String[] c()
  {
    // Byte code:
    //   0: getstatic 54	org/chromium/base/PathUtils:jdField_a_of_type_AndroidOsAsyncTask	Landroid/os/AsyncTask;
    //   3: astore_0
    //   4: aload_0
    //   5: ifnonnull +5 -> 10
    //   8: aconst_null
    //   9: areturn
    //   10: aload_0
    //   11: iconst_0
    //   12: invokevirtual 60	android/os/AsyncTask:cancel	(Z)Z
    //   15: ifeq +61 -> 76
    //   18: invokestatic 66	org/chromium/base/StrictModeContext:allowDiskWrites	()Lorg/chromium/base/StrictModeContext;
    //   21: astore_2
    //   22: invokestatic 46	org/chromium/base/PathUtils:d	()[Ljava/lang/String;
    //   25: astore_0
    //   26: aload_2
    //   27: ifnull +7 -> 34
    //   30: aload_2
    //   31: invokevirtual 69	org/chromium/base/StrictModeContext:close	()V
    //   34: aload_0
    //   35: areturn
    //   36: astore_0
    //   37: aconst_null
    //   38: astore_1
    //   39: goto +7 -> 46
    //   42: astore_1
    //   43: aload_1
    //   44: athrow
    //   45: astore_0
    //   46: aload_2
    //   47: ifnull +27 -> 74
    //   50: aload_1
    //   51: ifnull +19 -> 70
    //   54: aload_2
    //   55: invokevirtual 69	org/chromium/base/StrictModeContext:close	()V
    //   58: goto +16 -> 74
    //   61: astore_2
    //   62: aload_1
    //   63: aload_2
    //   64: invokevirtual 73	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   67: goto +7 -> 74
    //   70: aload_2
    //   71: invokevirtual 69	org/chromium/base/StrictModeContext:close	()V
    //   74: aload_0
    //   75: athrow
    //   76: getstatic 54	org/chromium/base/PathUtils:jdField_a_of_type_AndroidOsAsyncTask	Landroid/os/AsyncTask;
    //   79: invokevirtual 77	android/os/AsyncTask:get	()Ljava/lang/Object;
    //   82: checkcast 79	[Ljava/lang/String;
    //   85: astore_0
    //   86: aload_0
    //   87: areturn
    //   88: astore_0
    //   89: aconst_null
    //   90: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   3	32	0	localObject1	Object
    //   36	1	0	localObject2	Object
    //   45	30	0	localObject3	Object
    //   85	2	0	arrayOfString	String[]
    //   88	1	0	localInterruptedException	InterruptedException
    //   38	1	1	localObject4	Object
    //   42	21	1	localThrowable1	Throwable
    //   21	34	2	localStrictModeContext	StrictModeContext
    //   61	10	2	localThrowable2	Throwable
    // Exception table:
    //   from	to	target	type
    //   22	26	36	finally
    //   22	26	42	java/lang/Throwable
    //   43	45	45	finally
    //   54	58	61	java/lang/Throwable
    //   10	22	88	java/lang/InterruptedException
    //   10	22	88	java/util/concurrent/ExecutionException
    //   30	34	88	java/lang/InterruptedException
    //   30	34	88	java/util/concurrent/ExecutionException
    //   54	58	88	java/lang/InterruptedException
    //   54	58	88	java/util/concurrent/ExecutionException
    //   62	67	88	java/lang/InterruptedException
    //   62	67	88	java/util/concurrent/ExecutionException
    //   70	74	88	java/lang/InterruptedException
    //   70	74	88	java/util/concurrent/ExecutionException
    //   74	76	88	java/lang/InterruptedException
    //   74	76	88	java/util/concurrent/ExecutionException
    //   76	86	88	java/lang/InterruptedException
    //   76	86	88	java/util/concurrent/ExecutionException
  }
  
  private static String[] d()
  {
    String[] arrayOfString = new String[3];
    Object localObject = ContextUtils.getApplicationContext();
    arrayOfString[0] = ((Context)localObject).getDir(jdField_a_of_type_JavaLangString, 0).getPath();
    arrayOfString[1] = ((Context)localObject).getDir("textures", 0).getPath();
    if (TencentAwBrowserProcess.isUsingX5DataDir())
    {
      if (b == null)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(arrayOfString[0]);
        ((StringBuilder)localObject).append(File.separator);
        ((StringBuilder)localObject).append("Cache");
        arrayOfString[2] = ((StringBuilder)localObject).toString();
        return arrayOfString;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(arrayOfString[0]);
      ((StringBuilder)localObject).append(File.separator);
      ((StringBuilder)localObject).append("Cache");
      ((StringBuilder)localObject).append(File.separator);
      ((StringBuilder)localObject).append(b);
      arrayOfString[2] = ((StringBuilder)localObject).toString();
      return arrayOfString;
    }
    if (((Context)localObject).getCacheDir() != null)
    {
      if (b == null)
      {
        arrayOfString[2] = ((Context)localObject).getCacheDir().getPath();
        return arrayOfString;
      }
      arrayOfString[2] = new File(((Context)localObject).getCacheDir(), b).getPath();
    }
    return arrayOfString;
  }
  
  @CalledByNative
  public static String getCacheDirectory()
  {
    if ((!jdField_a_of_type_Boolean) && (jdField_a_of_type_AndroidOsAsyncTask == null)) {
      throw new AssertionError("setDataDirectorySuffix must be called first.");
    }
    return getDirectoryPath(2);
  }
  
  @CalledByNative
  public static String getDataDirectory()
  {
    if ((!jdField_a_of_type_Boolean) && (jdField_a_of_type_AndroidOsAsyncTask == null)) {
      throw new AssertionError("setDataDirectorySuffix must be called first.");
    }
    return getDirectoryPath(0);
  }
  
  public static String getDirectoryPath(int paramInt)
  {
    if (Holder.a() == null) {
      return "";
    }
    return Holder.a()[paramInt];
  }
  
  /* Error */
  @CalledByNative
  private static String getDownloadsDirectory()
  {
    // Byte code:
    //   0: invokestatic 155	org/chromium/base/StrictModeContext:allowDiskReads	()Lorg/chromium/base/StrictModeContext;
    //   3: astore 4
    //   5: aconst_null
    //   6: astore_3
    //   7: aload_3
    //   8: astore_2
    //   9: invokestatic 161	android/os/SystemClock:elapsedRealtime	()J
    //   12: lstore_0
    //   13: aload_3
    //   14: astore_2
    //   15: getstatic 166	android/os/Environment:DIRECTORY_DOWNLOADS	Ljava/lang/String;
    //   18: invokestatic 170	android/os/Environment:getExternalStoragePublicDirectory	(Ljava/lang/String;)Ljava/io/File;
    //   21: invokevirtual 101	java/io/File:getPath	()Ljava/lang/String;
    //   24: astore 5
    //   26: aload_3
    //   27: astore_2
    //   28: ldc -84
    //   30: invokestatic 161	android/os/SystemClock:elapsedRealtime	()J
    //   33: lload_0
    //   34: lsub
    //   35: getstatic 178	java/util/concurrent/TimeUnit:MILLISECONDS	Ljava/util/concurrent/TimeUnit;
    //   38: invokestatic 184	org/chromium/base/metrics/RecordHistogram:recordTimesHistogram	(Ljava/lang/String;JLjava/util/concurrent/TimeUnit;)V
    //   41: aload 4
    //   43: ifnull +8 -> 51
    //   46: aload 4
    //   48: invokevirtual 69	org/chromium/base/StrictModeContext:close	()V
    //   51: aload 5
    //   53: areturn
    //   54: astore_3
    //   55: goto +8 -> 63
    //   58: astore_3
    //   59: aload_3
    //   60: astore_2
    //   61: aload_3
    //   62: athrow
    //   63: aload 4
    //   65: ifnull +31 -> 96
    //   68: aload_2
    //   69: ifnull +22 -> 91
    //   72: aload 4
    //   74: invokevirtual 69	org/chromium/base/StrictModeContext:close	()V
    //   77: goto +19 -> 96
    //   80: astore 4
    //   82: aload_2
    //   83: aload 4
    //   85: invokevirtual 73	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   88: goto +8 -> 96
    //   91: aload 4
    //   93: invokevirtual 69	org/chromium/base/StrictModeContext:close	()V
    //   96: aload_3
    //   97: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   12	22	0	l	long
    //   8	75	2	localObject1	Object
    //   6	21	3	localObject2	Object
    //   54	1	3	localObject3	Object
    //   58	39	3	localThrowable1	Throwable
    //   3	70	4	localStrictModeContext	StrictModeContext
    //   80	12	4	localThrowable2	Throwable
    //   24	28	5	str	String
    // Exception table:
    //   from	to	target	type
    //   9	13	54	finally
    //   15	26	54	finally
    //   28	41	54	finally
    //   61	63	54	finally
    //   9	13	58	java/lang/Throwable
    //   15	26	58	java/lang/Throwable
    //   28	41	58	java/lang/Throwable
    //   72	77	80	java/lang/Throwable
  }
  
  @CalledByNative
  public static String getExternalStorageDirectory()
  {
    try
    {
      String str = Environment.getExternalStorageDirectory().getAbsolutePath();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  @CalledByNative
  private static String getNativeLibraryDirectory()
  {
    Object localObject = TencentPathUtils.getNativeLibraryDirectory();
    if (localObject != null) {
      return (String)localObject;
    }
    localObject = ContextUtils.getApplicationContext().getApplicationInfo();
    if (((((ApplicationInfo)localObject).flags & 0x80) == 0) && ((((ApplicationInfo)localObject).flags & 0x1) != 0)) {
      return "/system/lib/";
    }
    return ((ApplicationInfo)localObject).nativeLibraryDir;
  }
  
  @CalledByNative
  public static String getThumbnailCacheDirectory()
  {
    if ((!jdField_a_of_type_Boolean) && (jdField_a_of_type_AndroidOsAsyncTask == null)) {
      throw new AssertionError("setDataDirectorySuffix must be called first.");
    }
    return getDirectoryPath(1);
  }
  
  public static void setPrivateDataDirectorySuffix(String paramString)
  {
    setPrivateDataDirectorySuffix(paramString, null);
  }
  
  public static void setPrivateDataDirectorySuffix(String paramString1, String paramString2)
  {
    if (!jdField_a_of_type_JavaUtilConcurrentAtomicAtomicBoolean.getAndSet(true))
    {
      if ((!jdField_a_of_type_Boolean) && (ContextUtils.getApplicationContext() == null)) {
        throw new AssertionError();
      }
      jdField_a_of_type_JavaLangString = paramString1;
      b = paramString2;
      if ((Looper.myLooper() == null) && (Build.VERSION.SDK_INT <= 15)) {
        Looper.prepare();
      }
      jdField_a_of_type_AndroidOsAsyncTask = new AsyncTask()
      {
        protected String[] a(Void... paramAnonymousVarArgs)
        {
          return PathUtils.b();
        }
      }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }
  }
  
  private static class Holder
  {
    private static final String[] a = ;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\PathUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */