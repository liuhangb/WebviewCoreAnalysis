package com.tencent.smtt.webkit;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Pair;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import com.tencent.common.utils.FileUtils;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.UsedByReflection;

@JNINamespace("content")
public class X5JavaBridge
{
  static String CODE_CACHE_DIR = "/tencent/tbs/code_cache/";
  static String PACKAGE_NAME = "";
  static String TAG = "X5JavaBridge";
  private Context mCtx;
  Handler mHandler = null;
  private final Map<String, Pair<Object, Class>> mJavaScriptInterfaces = new HashMap();
  private long mNativeX5JavaBridge = 0L;
  private final HashSet<Object> mRetainedJavaScriptObjects = new HashSet();
  final HandlerThread mThread;
  private boolean startX5InspectServer = false;
  
  public X5JavaBridge(Context paramContext)
  {
    this.mCtx = paramContext;
    this.mThread = new HandlerThread("X5JavaBridge");
    this.mThread.start();
    this.mHandler = new Handler(this.mThread.getLooper());
    PACKAGE_NAME = this.mCtx.getPackageName();
    this.startX5InspectServer = SmttServiceProxy.getInstance().getIsX5jscoreInspectorEnabled();
    if (this.startX5InspectServer)
    {
      startX5InspectServerIfNeeded(true);
      ensureNativeX5JavaBridgeInit();
      setCodeCacheEnalbed(SmttServiceProxy.getInstance().isCodeCacheEnable());
      if (ThreadUtils.runningOnUiThread())
      {
        setStartdX5InspectServer();
        return;
      }
      ThreadUtils.postOnUiThread(new Runnable()
      {
        public void run()
        {
          X5JavaBridge.this.setStartdX5InspectServer();
        }
      });
      return;
    }
    ensureNativeX5JavaBridgeInit();
    setCodeCacheEnalbed(SmttServiceProxy.getInstance().isCodeCacheEnable());
  }
  
  private void ensureNativeX5JavaBridgeInit()
  {
    Handler localHandler = this.mHandler;
    if (localHandler == null) {
      return;
    }
    localHandler.post(new Runnable()
    {
      public void run()
      {
        if (X5JavaBridge.this.mNativeX5JavaBridge != 0L) {
          return;
        }
        X5JavaBridge localX5JavaBridge = X5JavaBridge.this;
        X5JavaBridge.access$002(localX5JavaBridge, localX5JavaBridge.nativeInit(localX5JavaBridge.mRetainedJavaScriptObjects));
      }
    });
  }
  
  private native void nativeAddJavascriptInterface(long paramLong, Object paramObject, String paramString, Class paramClass);
  
  private native boolean nativeDestroy(long paramLong);
  
  private native void nativeDispatchAttachMessageImpl(long paramLong, int paramInt);
  
  private native void nativeDispatchDetachMessageImpl(long paramLong);
  
  private native void nativeDispatchProtocolMessageImpl(long paramLong, String paramString);
  
  private native String nativeEvaluateJavaScript(long paramLong, String paramString, boolean paramBoolean);
  
  private native ByteBuffer nativeGetNativeBuffer(long paramLong, int paramInt);
  
  private native int nativeGetNativeBufferId(long paramLong);
  
  private native long nativeInit(HashSet<Object> paramHashSet);
  
  private native ByteBuffer nativeNewNativeBuffer(long paramLong, int paramInt1, int paramInt2);
  
  private native void nativePause(long paramLong);
  
  private native void nativePauseTimers(long paramLong);
  
  private native void nativeRemoveJavascriptInterface(long paramLong, String paramString);
  
  private native void nativeResume(long paramLong);
  
  private native void nativeResumeTimers(long paramLong);
  
  private native void nativeSetCodeCacheEnabled(long paramLong, boolean paramBoolean);
  
  private static native void nativeSetStartdX5InspectServerImpl(long paramLong);
  
  private native int nativeSetWeak(long paramLong, int paramInt);
  
  private static native void nativeStartX5InspectServerIfNeededImpl(boolean paramBoolean);
  
  private static native void nativeStopServerOnUIThreadImpl();
  
  private native void nativeTimerFired(long paramLong, int paramInt);
  
  @CalledByNative
  public static void reportExceptionInfo(String paramString1, int paramInt, String paramString2)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("app", PACKAGE_NAME);
    localHashMap.put("appid", e.a().a());
    localHashMap.put("appName", e.a().b());
    localHashMap.put("md5", paramString1);
    localHashMap.put("size", String.valueOf(paramInt));
    localHashMap.put("msg", paramString2);
    localHashMap.put("ts", String.valueOf(System.currentTimeMillis()));
    SmttServiceProxy.getInstance().upLoadToBeacon("MTT_X5JSCORE_EXCEPTION", localHashMap);
  }
  
  @CalledByNative
  public static void reportPerformanceInfo(String paramString1, int paramInt, String paramString2, double paramDouble)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("app", PACKAGE_NAME);
    localHashMap.put("appid", e.a().a());
    localHashMap.put("appName", e.a().b());
    localHashMap.put("md5", paramString1);
    localHashMap.put("size", String.valueOf(paramInt));
    localHashMap.put("phase", paramString2);
    localHashMap.put("consume", String.valueOf(paramDouble));
    localHashMap.put("ts", String.valueOf(System.currentTimeMillis()));
    SmttServiceProxy.getInstance().upLoadToBeacon("MTT_X5JSCORE_PERF", localHashMap);
  }
  
  private void setCodeCacheEnalbed(final boolean paramBoolean)
  {
    Handler localHandler = this.mHandler;
    if (localHandler == null) {
      return;
    }
    localHandler.post(new Runnable()
    {
      public void run()
      {
        if (X5JavaBridge.this.mNativeX5JavaBridge != 0L)
        {
          X5JavaBridge localX5JavaBridge = X5JavaBridge.this;
          localX5JavaBridge.nativeSetCodeCacheEnabled(localX5JavaBridge.mNativeX5JavaBridge, paramBoolean);
        }
      }
    });
  }
  
  public static void startX5InspectServerIfNeeded(boolean paramBoolean)
  {
    if (ThreadUtils.runningOnUiThread())
    {
      nativeStartX5InspectServerIfNeededImpl(paramBoolean);
      return;
    }
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        X5JavaBridge.nativeStartX5InspectServerIfNeededImpl(this.a);
      }
    });
  }
  
  @CalledByNative
  public static void stopServerOnUIThread()
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run() {}
    });
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void addJavascriptInterface(final Object paramObject, final String paramString)
  {
    Handler localHandler = this.mHandler;
    if (localHandler == null) {
      return;
    }
    localHandler.post(new Runnable()
    {
      public void run()
      {
        if (X5JavaBridge.this.mNativeX5JavaBridge == 0L) {
          return;
        }
        Class localClass;
        if ((X5JavaBridge.this.mCtx.getApplicationInfo().targetSdkVersion >= 17) && (Build.VERSION.SDK_INT >= 17)) {
          localClass = JavascriptInterface.class;
        } else {
          localClass = null;
        }
        X5JavaBridge.this.mJavaScriptInterfaces.put(paramString, new Pair(paramObject, localClass));
        X5JavaBridge localX5JavaBridge = X5JavaBridge.this;
        localX5JavaBridge.nativeAddJavascriptInterface(localX5JavaBridge.mNativeX5JavaBridge, paramObject, paramString, localClass);
      }
    });
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void destroy()
  {
    Handler localHandler = this.mHandler;
    if (localHandler == null) {
      return;
    }
    localHandler.post(new Runnable()
    {
      public void run()
      {
        boolean bool;
        if (X5JavaBridge.this.mNativeX5JavaBridge != 0L)
        {
          X5JavaBridge localX5JavaBridge = X5JavaBridge.this;
          bool = localX5JavaBridge.nativeDestroy(localX5JavaBridge.mNativeX5JavaBridge);
          X5JavaBridge.access$002(X5JavaBridge.this, 0L);
        }
        else
        {
          bool = false;
        }
        if (bool) {
          new Handler(Looper.getMainLooper()).post(new Runnable()
          {
            public void run()
            {
              X5JavaBridge.this.mHandler = null;
              X5JavaBridge.access$202(X5JavaBridge.this, null);
              X5JavaBridge.this.mRetainedJavaScriptObjects.clear();
              X5JavaBridge.this.mJavaScriptInterfaces.clear();
              X5JavaBridge.this.mThread.quit();
            }
          });
        }
      }
    });
  }
  
  @CalledByNative
  public void dispatchAttachMessage(final int paramInt)
  {
    Handler localHandler = this.mHandler;
    if (localHandler == null) {
      return;
    }
    localHandler.post(new Runnable()
    {
      public void run()
      {
        if (X5JavaBridge.this.mNativeX5JavaBridge != 0L)
        {
          X5JavaBridge localX5JavaBridge = X5JavaBridge.this;
          localX5JavaBridge.nativeDispatchAttachMessageImpl(localX5JavaBridge.mNativeX5JavaBridge, paramInt);
        }
      }
    });
  }
  
  @CalledByNative
  public void dispatchDetachMessage()
  {
    Handler localHandler = this.mHandler;
    if (localHandler == null) {
      return;
    }
    localHandler.post(new Runnable()
    {
      public void run()
      {
        if (X5JavaBridge.this.mNativeX5JavaBridge != 0L)
        {
          X5JavaBridge localX5JavaBridge = X5JavaBridge.this;
          localX5JavaBridge.nativeDispatchDetachMessageImpl(localX5JavaBridge.mNativeX5JavaBridge);
        }
      }
    });
  }
  
  @CalledByNative
  public void dispatchProtocolMessage(final String paramString)
  {
    Handler localHandler = this.mHandler;
    if (localHandler == null) {
      return;
    }
    localHandler.post(new Runnable()
    {
      public void run()
      {
        if (X5JavaBridge.this.mNativeX5JavaBridge != 0L)
        {
          X5JavaBridge localX5JavaBridge = X5JavaBridge.this;
          localX5JavaBridge.nativeDispatchProtocolMessageImpl(localX5JavaBridge.mNativeX5JavaBridge, paramString);
        }
      }
    });
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void evaluateJavaScript(final String paramString, final ValueCallback<String> paramValueCallback)
  {
    Handler localHandler = this.mHandler;
    if (localHandler == null) {
      return;
    }
    localHandler.post(new Runnable()
    {
      public void run()
      {
        if (X5JavaBridge.this.mNativeX5JavaBridge == 0L) {
          return;
        }
        boolean bool;
        if (paramValueCallback != null) {
          bool = true;
        } else {
          bool = false;
        }
        Object localObject = X5JavaBridge.this;
        localObject = ((X5JavaBridge)localObject).nativeEvaluateJavaScript(((X5JavaBridge)localObject).mNativeX5JavaBridge, paramString, bool);
        if (bool) {
          paramValueCallback.onReceiveValue(localObject);
        }
      }
    });
  }
  
  @CalledByNative
  public String getCodeCachePath()
  {
    if (Environment.getExternalStorageState().equals("mounted"))
    {
      localObject = FileUtils.getSDcardDir(ContextHolder.getInstance().getContext());
      if (localObject != null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(localObject);
        localStringBuilder.append(CODE_CACHE_DIR);
        return localStringBuilder.toString();
      }
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.mCtx.getCacheDir());
    ((StringBuilder)localObject).append(CODE_CACHE_DIR);
    return ((StringBuilder)localObject).toString();
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public ByteBuffer getNativeBuffer(final int paramInt)
  {
    Object localObject = new FutureTask(new Callable()
    {
      public ByteBuffer a()
      {
        long l = X5JavaBridge.this.mNativeX5JavaBridge;
        ByteBuffer localByteBuffer = null;
        if (l == 0L) {
          return null;
        }
        Object localObject = X5JavaBridge.this;
        localObject = ((X5JavaBridge)localObject).nativeGetNativeBuffer(((X5JavaBridge)localObject).mNativeX5JavaBridge, paramInt);
        if (localObject != null)
        {
          ((ByteBuffer)localObject).order(ByteOrder.nativeOrder());
          localByteBuffer = ByteBuffer.allocateDirect(((ByteBuffer)localObject).capacity());
          localByteBuffer.order(ByteOrder.nativeOrder());
          localByteBuffer.put((ByteBuffer)localObject);
          localObject = X5JavaBridge.this;
          ((X5JavaBridge)localObject).nativeSetWeak(((X5JavaBridge)localObject).mNativeX5JavaBridge, paramInt);
        }
        return localByteBuffer;
      }
    });
    this.mHandler.post((Runnable)localObject);
    try
    {
      localObject = (ByteBuffer)((FutureTask)localObject).get(2L, TimeUnit.SECONDS);
      return (ByteBuffer)localObject;
    }
    catch (TimeoutException localTimeoutException)
    {
      localTimeoutException.printStackTrace();
    }
    catch (ExecutionException localExecutionException)
    {
      localExecutionException.printStackTrace();
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
    }
    return null;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public int getNativeBufferId()
  {
    FutureTask localFutureTask = new FutureTask(new Callable()
    {
      public Integer a()
      {
        if (X5JavaBridge.this.mNativeX5JavaBridge == 0L) {
          return Integer.valueOf(-1);
        }
        X5JavaBridge localX5JavaBridge = X5JavaBridge.this;
        return Integer.valueOf(localX5JavaBridge.nativeGetNativeBufferId(localX5JavaBridge.mNativeX5JavaBridge));
      }
    });
    this.mHandler.post(localFutureTask);
    try
    {
      int i = ((Integer)localFutureTask.get(2L, TimeUnit.SECONDS)).intValue();
      return i;
    }
    catch (TimeoutException localTimeoutException)
    {
      localTimeoutException.printStackTrace();
    }
    catch (ExecutionException localExecutionException)
    {
      localExecutionException.printStackTrace();
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
    }
    return -1;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void pause()
  {
    long l = this.mNativeX5JavaBridge;
    if (l != 0L) {
      nativePause(l);
    }
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void pauseTimers()
  {
    Handler localHandler = this.mHandler;
    if (localHandler == null) {
      return;
    }
    localHandler.post(new Runnable()
    {
      public void run()
      {
        if (X5JavaBridge.this.mNativeX5JavaBridge != 0L)
        {
          X5JavaBridge localX5JavaBridge = X5JavaBridge.this;
          localX5JavaBridge.nativePauseTimers(localX5JavaBridge.mNativeX5JavaBridge);
        }
      }
    });
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void removeJavascriptInterface(final String paramString)
  {
    Handler localHandler = this.mHandler;
    if (localHandler == null) {
      return;
    }
    localHandler.post(new Runnable()
    {
      public void run()
      {
        X5JavaBridge.this.mJavaScriptInterfaces.remove(paramString);
        if (X5JavaBridge.this.mNativeX5JavaBridge != 0L)
        {
          X5JavaBridge localX5JavaBridge = X5JavaBridge.this;
          localX5JavaBridge.nativeRemoveJavascriptInterface(localX5JavaBridge.mNativeX5JavaBridge, paramString);
        }
      }
    });
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void resume()
  {
    long l = this.mNativeX5JavaBridge;
    if (l != 0L) {
      nativeResume(l);
    }
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void resumeTimers()
  {
    Handler localHandler = this.mHandler;
    if (localHandler == null) {
      return;
    }
    localHandler.post(new Runnable()
    {
      public void run()
      {
        if (X5JavaBridge.this.mNativeX5JavaBridge != 0L)
        {
          X5JavaBridge localX5JavaBridge = X5JavaBridge.this;
          localX5JavaBridge.nativeResumeTimers(localX5JavaBridge.mNativeX5JavaBridge);
        }
      }
    });
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public void setNativeBuffer(final int paramInt, final ByteBuffer paramByteBuffer)
  {
    if (paramInt < 0) {
      return;
    }
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        if (X5JavaBridge.this.mNativeX5JavaBridge == 0L) {
          return;
        }
        Object localObject = X5JavaBridge.this;
        localObject = ((X5JavaBridge)localObject).nativeNewNativeBuffer(((X5JavaBridge)localObject).mNativeX5JavaBridge, paramInt, paramByteBuffer.capacity());
        if (localObject == null) {
          return;
        }
        ((ByteBuffer)localObject).put(paramByteBuffer);
      }
    });
  }
  
  public void setStartdX5InspectServer()
  {
    Handler localHandler = this.mHandler;
    if (localHandler == null) {
      return;
    }
    localHandler.post(new Runnable()
    {
      public void run()
      {
        if (X5JavaBridge.this.mNativeX5JavaBridge != 0L) {
          X5JavaBridge.nativeSetStartdX5InspectServerImpl(X5JavaBridge.this.mNativeX5JavaBridge);
        }
      }
    });
  }
  
  @CalledByNative
  public void setTimeout(final int paramInt, long paramLong)
  {
    Handler localHandler = this.mHandler;
    if (localHandler == null) {
      return;
    }
    localHandler.postDelayed(new Runnable()
    {
      public void run()
      {
        if (X5JavaBridge.this.mNativeX5JavaBridge != 0L)
        {
          X5JavaBridge localX5JavaBridge = X5JavaBridge.this;
          localX5JavaBridge.nativeTimerFired(localX5JavaBridge.mNativeX5JavaBridge, paramInt);
        }
      }
    }, paramLong);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\X5JavaBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */