package org.chromium.android_webview;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.Executor;
import org.chromium.android_webview.policy.AwPolicyProvider;
import org.chromium.base.BuildInfo;
import org.chromium.base.Callback;
import org.chromium.base.CommandLine;
import org.chromium.base.ContextUtils;
import org.chromium.base.PathUtils;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.library_loader.ProcessInitException;
import org.chromium.content.browser.BrowserStartupController;
import org.chromium.content.browser.ChildProcessCreationParams;
import org.chromium.content.browser.ChildProcessLauncherHelper;
import org.chromium.policy.CombinedPolicyProvider;

@JNINamespace("android_webview")
public final class AwBrowserProcess
{
  public static final String WEBVIEW_DIR_BASENAME = "webview";
  private static RandomAccessFile jdField_a_of_type_JavaIoRandomAccessFile;
  private static String jdField_a_of_type_JavaLangString;
  private static FileLock jdField_a_of_type_JavaNioChannelsFileLock;
  
  private static void a()
  {
    boolean bool = BuildInfo.isAtLeastP();
    int j = 0;
    int i;
    if ((bool) && (BuildInfo.targetsAtLeastP())) {
      i = 1;
    } else {
      i = 0;
    }
    localThreadPolicy = StrictMode.allowThreadDiskWrites();
    for (;;)
    {
      try
      {
        localObject1 = new File(PathUtils.getDataDirectory(), "webview_data_x5.lock");
      }
      finally
      {
        Object localObject1;
        StrictMode.setThreadPolicy(localThreadPolicy);
      }
      try
      {
        jdField_a_of_type_JavaIoRandomAccessFile = new RandomAccessFile((File)localObject1, "rw");
        jdField_a_of_type_JavaNioChannelsFileLock = jdField_a_of_type_JavaIoRandomAccessFile.getChannel().tryLock();
        localObject1 = jdField_a_of_type_JavaNioChannelsFileLock;
        if (localObject1 != null) {
          j = 1;
        }
      }
      catch (IOException|Exception localIOException) {}
    }
    if ((j == 0) && (i != 0)) {
      throw new RuntimeException("Using WebView from more than one process at once with the same data directory is not supported. https://crbug.com/558377");
    }
    StrictMode.setThreadPolicy(localThreadPolicy);
  }
  
  public static void configureChildProcessLauncher(String paramString, boolean paramBoolean)
  {
    ChildProcessCreationParams.set(new ChildProcessCreationParams(paramString, paramBoolean, 4, true, true));
  }
  
  public static String getWebViewPackageName()
  {
    String str = jdField_a_of_type_JavaLangString;
    if (str == null) {
      return "";
    }
    return str;
  }
  
  public static void handleMinidumps(String paramString, boolean paramBoolean) {}
  
  public static void handleMinidumpsAndSetMetricsConsent(final String paramString, boolean paramBoolean)
  {
    final boolean bool = CommandLine.getInstance().hasSwitch("enable-crash-reporter-for-testing");
    if (bool) {
      handleMinidumps(paramString, true);
    }
    PlatformServiceBridge.getInstance().queryMetricsSetting(new Callback()
    {
      public void onResult(Boolean paramAnonymousBoolean)
      {
        
        if (this.jdField_a_of_type_Boolean) {
          AwMetricsServiceClient.setConsentSetting(ContextUtils.getApplicationContext(), paramAnonymousBoolean.booleanValue());
        }
        if (!bool) {
          AwBrowserProcess.handleMinidumps(paramString, paramAnonymousBoolean.booleanValue());
        }
      }
    });
  }
  
  /* Error */
  public static void loadLibrary(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +12 -> 13
    //   4: ldc 19
    //   6: aconst_null
    //   7: invokestatic 150	org/chromium/base/PathUtils:setPrivateDataDirectorySuffix	(Ljava/lang/String;Ljava/lang/String;)V
    //   10: goto +34 -> 44
    //   13: new 152	java/lang/StringBuilder
    //   16: dup
    //   17: invokespecial 153	java/lang/StringBuilder:<init>	()V
    //   20: astore_1
    //   21: aload_1
    //   22: ldc -101
    //   24: invokevirtual 159	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   27: pop
    //   28: aload_1
    //   29: aload_0
    //   30: invokevirtual 159	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   33: pop
    //   34: aload_1
    //   35: invokevirtual 162	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   38: astore_0
    //   39: aload_0
    //   40: aload_0
    //   41: invokestatic 150	org/chromium/base/PathUtils:setPrivateDataDirectorySuffix	(Ljava/lang/String;Ljava/lang/String;)V
    //   44: invokestatic 165	android/os/StrictMode:allowThreadDiskReads	()Landroid/os/StrictMode$ThreadPolicy;
    //   47: astore_0
    //   48: iconst_3
    //   49: invokestatic 171	org/chromium/base/library_loader/LibraryLoader:get	(I)Lorg/chromium/base/library_loader/LibraryLoader;
    //   52: astore_1
    //   53: aload_1
    //   54: invokevirtual 174	org/chromium/base/library_loader/LibraryLoader:loadNow	()V
    //   57: aload_1
    //   58: invokevirtual 177	org/chromium/base/library_loader/LibraryLoader:switchCommandLineForWebView	()V
    //   61: aload_0
    //   62: invokestatic 100	android/os/StrictMode:setThreadPolicy	(Landroid/os/StrictMode$ThreadPolicy;)V
    //   65: return
    //   66: astore_1
    //   67: goto +15 -> 82
    //   70: astore_1
    //   71: new 91	java/lang/RuntimeException
    //   74: dup
    //   75: ldc -77
    //   77: aload_1
    //   78: invokespecial 182	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   81: athrow
    //   82: aload_0
    //   83: invokestatic 100	android/os/StrictMode:setThreadPolicy	(Landroid/os/StrictMode$ThreadPolicy;)V
    //   86: aload_1
    //   87: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	88	0	paramString	String
    //   20	38	1	localObject1	Object
    //   66	1	1	localObject2	Object
    //   70	17	1	localProcessInitException	ProcessInitException
    // Exception table:
    //   from	to	target	type
    //   48	61	66	finally
    //   71	82	66	finally
    //   48	61	70	org/chromium/base/library_loader/ProcessInitException
  }
  
  public static void setWebViewPackageName(String paramString)
  {
    if (!jdField_a_of_type_Boolean)
    {
      String str = jdField_a_of_type_JavaLangString;
      if ((str != null) && (!str.equals(paramString))) {
        throw new AssertionError();
      }
    }
    jdField_a_of_type_JavaLangString = paramString;
  }
  
  public static void start()
  {
    Context localContext = ContextUtils.getApplicationContext();
    a();
    ThreadUtils.runOnUiThreadBlocking(new Runnable()
    {
      public void run()
      {
        boolean bool = CommandLine.getInstance().hasSwitch("webview-sandboxed-renderer");
        if (bool) {
          ChildProcessLauncherHelper.warmUp(this.a);
        }
        CombinedPolicyProvider.a().a(new AwPolicyProvider(this.a));
        AwSafeBrowsingConfigHelper.maybeEnableSafeBrowsingFromManifest(this.a);
        for (;;)
        {
          try
          {
            BrowserStartupController localBrowserStartupController = BrowserStartupController.get(3);
            if (!bool)
            {
              bool = true;
              localBrowserStartupController.startBrowserProcessesSync(bool);
              return;
            }
          }
          catch (ProcessInitException localProcessInitException)
          {
            throw new RuntimeException("Cannot initialize WebView", localProcessInitException);
          }
          bool = false;
        }
      }
    });
    if (Build.VERSION.SDK_INT >= 24) {
      AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable()
      {
        public void run()
        {
          try
          {
            File localFile = new File(PathUtils.getDataDirectory(), "paks");
            if (localFile.exists())
            {
              File[] arrayOfFile = localFile.listFiles();
              int j = arrayOfFile.length;
              int i = 0;
              while (i < j)
              {
                arrayOfFile[i].delete();
                i += 1;
              }
              localFile.delete();
            }
            return;
          }
          catch (Throwable localThrowable) {}
        }
      });
    }
  }
  
  @CalledByNative
  private static void triggerMinidumpUploading()
  {
    handleMinidumpsAndSetMetricsConsent(jdField_a_of_type_JavaLangString, false);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwBrowserProcess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */