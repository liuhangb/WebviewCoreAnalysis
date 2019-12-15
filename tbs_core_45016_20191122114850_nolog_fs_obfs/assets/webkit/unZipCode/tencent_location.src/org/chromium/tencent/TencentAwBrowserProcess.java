package org.chromium.tencent;

import android.content.Context;
import java.io.File;
import org.chromium.base.ContextUtils;

public abstract class TencentAwBrowserProcess
{
  public static final String TAG = "TencentAwBrowserProcess";
  private static final String TODO_X5_PATH_FILE = "todox5path";
  private static final String USE_X5_DATA_DIRECTORY_FILE = "use_x5_data_dir";
  public static final String WEBVIEW_DIR_BASENAME = "x5webview";
  private static final String X5_EXCLUSIVE_LOCK_FILE_1 = "webview_data_m57.lock";
  private static final String X5_EXCLUSIVE_LOCK_FILE_2 = "webview_data_x5.lock";
  private static boolean mIsUsingX5DataDir = false;
  
  private static boolean canUseX5DataDirectory()
  {
    Object localObject1 = ContextUtils.getApplicationContext();
    if (new File(((Context)localObject1).getDir("x5webview", 0).getPath(), "use_x5_data_dir").exists()) {
      return true;
    }
    Object localObject2 = ((Context)localObject1).getDir("webview", 0).getPath();
    localObject1 = new File((String)localObject2, "webview_data_m57.lock");
    File localFile = new File((String)localObject2, "webview_data_x5.lock");
    localObject2 = new File((String)localObject2, "todox5path");
    if (((((File)localObject1).exists()) || (localFile.exists())) && (!((File)localObject2).exists())) {
      return false;
    }
    prepareUseX5DataDir();
    return true;
  }
  
  public static void disableUseX5DataDir()
  {
    if (!mIsUsingX5DataDir) {
      return;
    }
    Object localObject1 = ContextUtils.getApplicationContext();
    File localFile = new File(((Context)localObject1).getDir("x5webview", 0).getPath(), "use_x5_data_dir");
    if (localFile.exists()) {
      try
      {
        localFile.delete();
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
    Object localObject2 = ((Context)localObject1).getDir("webview", 0).getPath();
    localObject1 = new File((String)localObject2, "webview_data_m57.lock");
    localObject2 = new File((String)localObject2, "webview_data_x5.lock");
    if ((!((File)localObject1).exists()) && (!((File)localObject2).exists())) {
      try
      {
        ((File)localObject1).getParentFile().mkdirs();
        ((File)localObject1).createNewFile();
        return;
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
      }
    }
  }
  
  public static boolean isUsingX5DataDir()
  {
    return mIsUsingX5DataDir;
  }
  
  /* Error */
  public static void loadLibrary(String paramString)
  {
    // Byte code:
    //   0: ldc 60
    //   2: astore_1
    //   3: iconst_0
    //   4: putstatic 68	org/chromium/tencent/TencentAwBrowserProcess:mIsUsingX5DataDir	Z
    //   7: invokestatic 91	org/chromium/tencent/TencentAwBrowserProcess:canUseX5DataDirectory	()Z
    //   10: ifeq +10 -> 20
    //   13: iconst_1
    //   14: putstatic 68	org/chromium/tencent/TencentAwBrowserProcess:mIsUsingX5DataDir	Z
    //   17: ldc 17
    //   19: astore_1
    //   20: aload_0
    //   21: ifnonnull +11 -> 32
    //   24: aload_1
    //   25: aconst_null
    //   26: invokestatic 96	org/chromium/base/PathUtils:setPrivateDataDirectorySuffix	(Ljava/lang/String;Ljava/lang/String;)V
    //   29: goto +40 -> 69
    //   32: new 98	java/lang/StringBuilder
    //   35: dup
    //   36: invokespecial 99	java/lang/StringBuilder:<init>	()V
    //   39: astore_2
    //   40: aload_2
    //   41: aload_1
    //   42: invokevirtual 103	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: pop
    //   46: aload_2
    //   47: ldc 105
    //   49: invokevirtual 103	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: pop
    //   53: aload_2
    //   54: aload_0
    //   55: invokevirtual 103	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   58: pop
    //   59: aload_2
    //   60: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   63: astore_0
    //   64: aload_0
    //   65: aload_0
    //   66: invokestatic 96	org/chromium/base/PathUtils:setPrivateDataDirectorySuffix	(Ljava/lang/String;Ljava/lang/String;)V
    //   69: invokestatic 114	android/os/StrictMode:allowThreadDiskReads	()Landroid/os/StrictMode$ThreadPolicy;
    //   72: astore_0
    //   73: iconst_3
    //   74: invokestatic 120	org/chromium/base/library_loader/LibraryLoader:get	(I)Lorg/chromium/base/library_loader/LibraryLoader;
    //   77: astore_1
    //   78: aload_1
    //   79: invokevirtual 123	org/chromium/base/library_loader/LibraryLoader:loadNow	()V
    //   82: aload_1
    //   83: invokevirtual 126	org/chromium/base/library_loader/LibraryLoader:switchCommandLineForWebView	()V
    //   86: aload_0
    //   87: invokestatic 130	android/os/StrictMode:setThreadPolicy	(Landroid/os/StrictMode$ThreadPolicy;)V
    //   90: return
    //   91: astore_1
    //   92: goto +15 -> 107
    //   95: astore_1
    //   96: new 132	java/lang/RuntimeException
    //   99: dup
    //   100: ldc -122
    //   102: aload_1
    //   103: invokespecial 137	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   106: athrow
    //   107: aload_0
    //   108: invokestatic 130	android/os/StrictMode:setThreadPolicy	(Landroid/os/StrictMode$ThreadPolicy;)V
    //   111: aload_1
    //   112: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	113	0	paramString	String
    //   2	81	1	localObject1	Object
    //   91	1	1	localObject2	Object
    //   95	17	1	localProcessInitException	org.chromium.base.library_loader.ProcessInitException
    //   39	21	2	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   73	86	91	finally
    //   96	107	91	finally
    //   73	86	95	org/chromium/base/library_loader/ProcessInitException
  }
  
  public static void prepareUseX5DataDir()
  {
    if (mIsUsingX5DataDir) {
      return;
    }
    File localFile = new File(ContextUtils.getApplicationContext().getDir("x5webview", 0).getPath(), "use_x5_data_dir");
    if (!localFile.exists()) {
      try
      {
        localFile.getParentFile().mkdirs();
        localFile.createNewFile();
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\TencentAwBrowserProcess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */