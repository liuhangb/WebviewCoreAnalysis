package org.chromium.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.chromium.base.BuildConfig;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.NativeClassQualifiedName;
import org.chromium.tencent.net.TencentProxyChangeListener;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;

@JNINamespace("net")
public class ProxyChangeListener
{
  private static final String TAG = "ProxyChangeListener";
  protected static boolean sEnabled = true;
  protected Delegate mDelegate;
  private final Handler mHandler = new Handler(this.mLooper);
  private final Looper mLooper = Looper.myLooper();
  protected long mNativePtr;
  private ProxyReceiver mProxyReceiver;
  
  private void assertOnThread()
  {
    if (BuildConfig.DCHECK_IS_ON)
    {
      if (onThread()) {
        return;
      }
      throw new IllegalStateException("Must be called on ProxyChangeListener thread.");
    }
  }
  
  @CalledByNative
  public static ProxyChangeListener create()
  {
    return TencentProxyChangeListener.getInstance();
  }
  
  @CalledByNative
  public static String getProperty(String paramString)
  {
    return System.getProperty(paramString);
  }
  
  private boolean onThread()
  {
    return this.mLooper == Looper.myLooper();
  }
  
  private void registerReceiver()
  {
    if (this.mProxyReceiver != null) {
      return;
    }
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.PROXY_CHANGE");
    this.mProxyReceiver = new ProxyReceiver(null);
    try
    {
      X5ApiCompatibilityUtils.x5RegisterReceiver(ContextUtils.getApplicationContext(), this.mProxyReceiver, localIntentFilter);
      return;
    }
    catch (SecurityException localSecurityException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("registerReceiver due to exception:");
      localStringBuilder.append(localSecurityException);
      Log.e("ProxyChangeListener", localStringBuilder.toString());
    }
  }
  
  private void runOnThread(Runnable paramRunnable)
  {
    if (onThread())
    {
      paramRunnable.run();
      return;
    }
    this.mHandler.post(paramRunnable);
  }
  
  public static void setEnabled(boolean paramBoolean)
  {
    sEnabled = paramBoolean;
  }
  
  private void unregisterReceiver()
  {
    if (this.mProxyReceiver == null) {
      return;
    }
    X5ApiCompatibilityUtils.x5UnRegisterReceiver(ContextUtils.getApplicationContext(), this.mProxyReceiver);
    this.mProxyReceiver = null;
  }
  
  @NativeClassQualifiedName("ProxyConfigServiceAndroid::JNIDelegate")
  protected native void nativeProxySettingsChanged(long paramLong);
  
  @NativeClassQualifiedName("ProxyConfigServiceAndroid::JNIDelegate")
  protected native void nativeProxySettingsChangedTo(long paramLong, String paramString1, int paramInt, String paramString2, String[] paramArrayOfString);
  
  protected void proxySettingsChanged(ProxyReceiver paramProxyReceiver, ProxyConfig paramProxyConfig)
  {
    if (sEnabled)
    {
      if (paramProxyReceiver != this.mProxyReceiver) {
        return;
      }
      paramProxyReceiver = this.mDelegate;
      if (paramProxyReceiver != null) {
        paramProxyReceiver.proxySettingsChanged();
      }
      long l = this.mNativePtr;
      if (l == 0L) {
        return;
      }
      if (paramProxyConfig != null)
      {
        nativeProxySettingsChangedTo(l, paramProxyConfig.jdField_a_of_type_JavaLangString, paramProxyConfig.jdField_a_of_type_Int, paramProxyConfig.b, paramProxyConfig.jdField_a_of_type_ArrayOfJavaLangString);
        return;
      }
      nativeProxySettingsChanged(l);
      return;
    }
  }
  
  public void setDelegateForTesting(Delegate paramDelegate)
  {
    this.mDelegate = paramDelegate;
  }
  
  @CalledByNative
  public void start(long paramLong)
  {
    assertOnThread();
    this.mNativePtr = paramLong;
    registerReceiver();
  }
  
  @CalledByNative
  public void stop()
  {
    assertOnThread();
    this.mNativePtr = 0L;
    unregisterReceiver();
  }
  
  public static abstract interface Delegate
  {
    public abstract void proxySettingsChanged();
  }
  
  protected static class ProxyConfig
  {
    public final int a;
    public final String a;
    public final String[] a;
    public final String b;
    
    public ProxyConfig(String paramString1, int paramInt, String paramString2, String[] paramArrayOfString)
    {
      this.jdField_a_of_type_JavaLangString = paramString1;
      this.jdField_a_of_type_Int = paramInt;
      this.b = paramString2;
      this.jdField_a_of_type_ArrayOfJavaLangString = paramArrayOfString;
    }
  }
  
  private class ProxyReceiver
    extends BroadcastReceiver
  {
    private ProxyReceiver() {}
    
    private ProxyChangeListener.ProxyConfig a(Intent paramIntent)
    {
      for (;;)
      {
        try
        {
          if (Build.VERSION.SDK_INT >= 21) {
            break label480;
          }
          localObject1 = "android.net.ProxyProperties";
          localObject2 = "proxy";
          localObject2 = paramIntent.getExtras().get((String)localObject2);
          if (localObject2 == null) {
            return null;
          }
          Class localClass = Class.forName((String)localObject1);
          localObject1 = localClass.getDeclaredMethod("getHost", new Class[0]);
          paramIntent = localClass.getDeclaredMethod("getPort", new Class[0]);
          Method localMethod = localClass.getDeclaredMethod("getExclusionList", new Class[0]);
          localObject1 = (String)((Method)localObject1).invoke(localObject2, new Object[0]);
          int i = ((Integer)paramIntent.invoke(localObject2, new Object[0])).intValue();
          if (Build.VERSION.SDK_INT < 21) {
            paramIntent = ((String)localMethod.invoke(localObject2, new Object[0])).split(",");
          } else {
            paramIntent = (String[])localMethod.invoke(localObject2, new Object[0]);
          }
          if ((Build.VERSION.SDK_INT >= 19) && (Build.VERSION.SDK_INT < 21))
          {
            localObject2 = (String)localClass.getDeclaredMethod("getPacFileUrl", new Class[0]).invoke(localObject2, new Object[0]);
            if (!TextUtils.isEmpty((CharSequence)localObject2)) {
              return new ProxyChangeListener.ProxyConfig((String)localObject1, i, (String)localObject2, paramIntent);
            }
          }
          else if (Build.VERSION.SDK_INT >= 21)
          {
            localObject2 = (Uri)localClass.getDeclaredMethod("getPacFileUrl", new Class[0]).invoke(localObject2, new Object[0]);
            if (!Uri.EMPTY.equals(localObject2)) {
              return new ProxyChangeListener.ProxyConfig((String)localObject1, i, ((Uri)localObject2).toString(), paramIntent);
            }
          }
          paramIntent = new ProxyChangeListener.ProxyConfig((String)localObject1, i, null, paramIntent);
          return paramIntent;
        }
        catch (NullPointerException paramIntent) {}catch (InvocationTargetException paramIntent)
        {
          break label348;
        }
        catch (IllegalAccessException paramIntent)
        {
          break label381;
        }
        catch (NoSuchMethodException paramIntent)
        {
          break label414;
        }
        catch (ClassNotFoundException paramIntent) {}
        Object localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("Using no proxy configuration due to exception:");
        ((StringBuilder)localObject1).append(paramIntent);
        Log.e("ProxyChangeListener", ((StringBuilder)localObject1).toString());
        return null;
        label348:
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("Using no proxy configuration due to exception:");
        ((StringBuilder)localObject1).append(paramIntent);
        Log.e("ProxyChangeListener", ((StringBuilder)localObject1).toString());
        return null;
        label381:
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("Using no proxy configuration due to exception:");
        ((StringBuilder)localObject1).append(paramIntent);
        Log.e("ProxyChangeListener", ((StringBuilder)localObject1).toString());
        return null;
        label414:
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("Using no proxy configuration due to exception:");
        ((StringBuilder)localObject1).append(paramIntent);
        Log.e("ProxyChangeListener", ((StringBuilder)localObject1).toString());
        return null;
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("Using no proxy configuration due to exception:");
        ((StringBuilder)localObject1).append(paramIntent);
        Log.e("ProxyChangeListener", ((StringBuilder)localObject1).toString());
        return null;
        label480:
        localObject1 = "android.net.ProxyInfo";
        Object localObject2 = "android.intent.extra.PROXY_INFO";
      }
    }
    
    public void onReceive(Context paramContext, final Intent paramIntent)
    {
      if (paramIntent.getAction().equals("android.intent.action.PROXY_CHANGE")) {
        ProxyChangeListener.this.runOnThread(new Runnable()
        {
          public void run()
          {
            ProxyChangeListener localProxyChangeListener = ProxyChangeListener.this;
            ProxyChangeListener.ProxyReceiver localProxyReceiver = ProxyChangeListener.ProxyReceiver.this;
            localProxyChangeListener.proxySettingsChanged(localProxyReceiver, ProxyChangeListener.ProxyReceiver.a(localProxyReceiver, paramIntent));
          }
        });
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\net\ProxyChangeListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */