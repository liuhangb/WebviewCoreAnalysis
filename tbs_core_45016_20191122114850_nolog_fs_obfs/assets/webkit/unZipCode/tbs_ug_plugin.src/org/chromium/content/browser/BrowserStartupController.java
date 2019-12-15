package org.chromium.content.browser;

import android.content.Context;
import android.os.Handler;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.chromium.base.BuildInfo;
import org.chromium.base.ContextUtils;
import org.chromium.base.ResourceExtractor;
import org.chromium.base.ThreadUtils;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.library_loader.LibraryLoader;
import org.chromium.base.library_loader.ProcessInitException;
import org.chromium.content.app.ContentMain;

@JNINamespace("content")
public class BrowserStartupController
{
  private static BrowserStartupController jdField_a_of_type_OrgChromiumContentBrowserBrowserStartupController;
  private static boolean b;
  private int jdField_a_of_type_Int;
  private final List<StartupCallback> jdField_a_of_type_JavaUtilList = new ArrayList();
  private TracingControllerAndroid jdField_a_of_type_OrgChromiumContentBrowserTracingControllerAndroid;
  private boolean c;
  private boolean d;
  private boolean e;
  private boolean f;
  private boolean g;
  
  BrowserStartupController(int paramInt)
  {
    this.jdField_a_of_type_Int = paramInt;
    if (BuildInfo.isDebugAndroid()) {
      ThreadUtils.postOnUiThread(new Runnable()
      {
        public void run()
        {
          BrowserStartupController.this.addStartupCompletedObserver(new BrowserStartupController.StartupCallback()
          {
            public void onFailure() {}
            
            public void onSuccess(boolean paramAnonymous2Boolean)
            {
              if ((!jdField_a_of_type_Boolean) && (BrowserStartupController.a(BrowserStartupController.this) != null)) {
                throw new AssertionError();
              }
              Context localContext = ContextUtils.getApplicationContext();
              BrowserStartupController.a(BrowserStartupController.this, new TracingControllerAndroid(localContext));
              BrowserStartupController.a(BrowserStartupController.this).registerReceiver(localContext);
            }
          });
        }
      });
    }
  }
  
  private String a()
  {
    return PepperPluginManager.getPlugins(ContextUtils.getApplicationContext());
  }
  
  private void a(int paramInt, boolean paramBoolean)
  {
    if ((!jdField_a_of_type_Boolean) && (!ThreadUtils.runningOnUiThread())) {
      throw new AssertionError("Callback from browser startup from wrong thread.");
    }
    boolean bool = true;
    this.f = true;
    if (paramInt > 0) {
      bool = false;
    }
    this.g = bool;
    Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
    while (localIterator.hasNext())
    {
      StartupCallback localStartupCallback = (StartupCallback)localIterator.next();
      if (this.g) {
        localStartupCallback.onSuccess(paramBoolean);
      } else {
        localStartupCallback.onFailure();
      }
    }
    this.jdField_a_of_type_JavaUtilList.clear();
  }
  
  private void a(final StartupCallback paramStartupCallback)
  {
    new Handler().post(new Runnable()
    {
      public void run()
      {
        if (BrowserStartupController.b(BrowserStartupController.this))
        {
          paramStartupCallback.onSuccess(true);
          return;
        }
        paramStartupCallback.onFailure();
      }
    });
  }
  
  private static void a(boolean paramBoolean)
  {
    b = paramBoolean;
  }
  
  private void b(final int paramInt, final boolean paramBoolean)
  {
    new Handler().post(new Runnable()
    {
      public void run()
      {
        BrowserStartupController.b(BrowserStartupController.this, paramInt, paramBoolean);
      }
    });
  }
  
  @VisibleForTesting
  @CalledByNative
  static void browserStartupComplete(int paramInt)
  {
    BrowserStartupController localBrowserStartupController = jdField_a_of_type_OrgChromiumContentBrowserBrowserStartupController;
    if (localBrowserStartupController != null) {
      localBrowserStartupController.a(paramInt, false);
    }
  }
  
  public static BrowserStartupController get(int paramInt)
  {
    if ((!jdField_a_of_type_Boolean) && (!ThreadUtils.runningOnUiThread())) {
      throw new AssertionError("Tried to start the browser on the wrong thread.");
    }
    ThreadUtils.assertOnUiThread();
    if (jdField_a_of_type_OrgChromiumContentBrowserBrowserStartupController == null)
    {
      if ((!jdField_a_of_type_Boolean) && (1 != paramInt) && (3 != paramInt)) {
        throw new AssertionError();
      }
      jdField_a_of_type_OrgChromiumContentBrowserBrowserStartupController = new BrowserStartupController(paramInt);
    }
    if ((!jdField_a_of_type_Boolean) && (jdField_a_of_type_OrgChromiumContentBrowserBrowserStartupController.jdField_a_of_type_Int != paramInt)) {
      throw new AssertionError("Wrong process type");
    }
    return jdField_a_of_type_OrgChromiumContentBrowserBrowserStartupController;
  }
  
  private static native void nativeFlushStartupTasks();
  
  private static native boolean nativeIsOfficialBuild();
  
  private static native boolean nativeIsPluginEnabled();
  
  private static native void nativeSetCommandLineFlags(boolean paramBoolean, String paramString);
  
  @VisibleForTesting
  public static BrowserStartupController overrideInstanceForTest(BrowserStartupController paramBrowserStartupController)
  {
    jdField_a_of_type_OrgChromiumContentBrowserBrowserStartupController = paramBrowserStartupController;
    return jdField_a_of_type_OrgChromiumContentBrowserBrowserStartupController;
  }
  
  @CalledByNative
  static boolean shouldStartGpuProcessOnBrowserStartup()
  {
    return b;
  }
  
  @VisibleForTesting
  int a()
  {
    if ((!jdField_a_of_type_Boolean) && (this.e)) {
      throw new AssertionError();
    }
    this.e = true;
    return ContentMain.start();
  }
  
  @VisibleForTesting
  void a() {}
  
  @VisibleForTesting
  void a(final boolean paramBoolean, final Runnable paramRunnable)
    throws ProcessInitException
  {
    Object localObject = StrictMode.allowThreadDiskReads();
    try
    {
      LibraryLoader.get(this.jdField_a_of_type_Int).ensureInitialized();
      StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)localObject);
      localObject = new Runnable()
      {
        public void run()
        {
          if (!BrowserStartupController.c(BrowserStartupController.this))
          {
            DeviceUtils.addDeviceSpecificUserAgentSwitch(ContextUtils.getApplicationContext());
            boolean bool = paramBoolean;
            if (BrowserStartupController.a()) {
              localObject = BrowserStartupController.a(BrowserStartupController.this);
            } else {
              localObject = null;
            }
            BrowserStartupController.a(bool, (String)localObject);
            BrowserStartupController.a(BrowserStartupController.this, true);
          }
          Object localObject = paramRunnable;
          if (localObject != null) {
            ((Runnable)localObject).run();
          }
        }
      };
      if (paramRunnable == null)
      {
        ResourceExtractor.get().waitForCompletion();
        ((Runnable)localObject).run();
        return;
      }
      ResourceExtractor.get().addCompletionCallback((Runnable)localObject);
      return;
    }
    finally
    {
      StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)localObject);
    }
  }
  
  public void addStartupCompletedObserver(StartupCallback paramStartupCallback)
  {
    
    if (this.f)
    {
      a(paramStartupCallback);
      return;
    }
    this.jdField_a_of_type_JavaUtilList.add(paramStartupCallback);
  }
  
  public void initChromiumBrowserProcessForTests()
  {
    ResourceExtractor localResourceExtractor = ResourceExtractor.get();
    localResourceExtractor.startExtractingResources();
    localResourceExtractor.waitForCompletion();
    nativeSetCommandLineFlags(false, null);
  }
  
  public boolean isStartupSuccessfullyCompleted()
  {
    ThreadUtils.assertOnUiThread();
    return (this.f) && (this.g);
  }
  
  public void startBrowserProcessesAsync(boolean paramBoolean, StartupCallback paramStartupCallback)
    throws ProcessInitException
  {
    if ((!jdField_a_of_type_Boolean) && (!ThreadUtils.runningOnUiThread())) {
      throw new AssertionError("Tried to start the browser on the wrong thread.");
    }
    if (this.f)
    {
      a(paramStartupCallback);
      return;
    }
    this.jdField_a_of_type_JavaUtilList.add(paramStartupCallback);
    if (!this.c)
    {
      this.c = true;
      a(paramBoolean);
      a(false, new Runnable()
      {
        public void run()
        {
          
          if (BrowserStartupController.a(BrowserStartupController.this)) {
            return;
          }
          if (BrowserStartupController.this.a() > 0) {
            BrowserStartupController.a(BrowserStartupController.this, 1, false);
          }
        }
      });
    }
  }
  
  public void startBrowserProcessesSync(boolean paramBoolean)
    throws ProcessInitException
  {
    if (!this.f)
    {
      if ((!this.c) || (!this.d)) {
        a(paramBoolean, null);
      }
      paramBoolean = this.e;
      int i = 0;
      if ((!paramBoolean) && (a() > 0)) {
        b(1, false);
      } else {
        i = 1;
      }
      if (i != 0) {
        a();
      }
    }
    if ((!jdField_a_of_type_Boolean) && (!this.f)) {
      throw new AssertionError();
    }
    if (this.g) {
      return;
    }
    throw new ProcessInitException(4);
  }
  
  public static abstract interface StartupCallback
  {
    public abstract void onFailure();
    
    public abstract void onSuccess(boolean paramBoolean);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\BrowserStartupController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */