package com.tencent.tbs.core.partner.crashmonitor;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Process;
import android.text.TextUtils;
import com.tencent.smtt.net.AwNetworkUtils;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.io.File;
import java.util.Iterator;
import java.util.List;

public class CrashMonitor
{
  private static final String CRASH_MONITOR_ENABLE_PROCESS_NAME = "com.tencent.mm:tools";
  private static final int MIN_SUPPORT_SDK_VERSION = 43650;
  private static final String WECHAT_CRASH_MONITOR_LIBRARY_NAME = "libwechatCrashForJni.so";
  private static CrashMonitor mInstance;
  private Context mAppContext;
  private String mCurrentProcessName;
  private Tombstone mTombstone;
  private IWebViewStateChangeListener mWebViewStateChangeListener;
  
  public static CrashMonitor getInstance()
  {
    if (mInstance == null) {
      try
      {
        if (mInstance == null) {
          mInstance = new CrashMonitor();
        }
      }
      finally {}
    }
    return mInstance;
  }
  
  private String message()
  {
    return this.mTombstone.toString();
  }
  
  public IWebViewStateChangeListener getWebViewStateChangeListener()
  {
    return this.mWebViewStateChangeListener;
  }
  
  public void init(Context paramContext)
  {
    this.mAppContext = paramContext.getApplicationContext();
    if (this.mAppContext == null) {
      this.mAppContext = paramContext;
    }
    this.mTombstone = new Tombstone();
    this.mWebViewStateChangeListener = new WebViewStateRecorder(this.mTombstone);
  }
  
  public boolean isEnable()
  {
    if (TextUtils.isEmpty(this.mCurrentProcessName)) {}
    try
    {
      int i = Process.myPid();
      Iterator localIterator = ((ActivityManager)this.mAppContext.getSystemService("activity")).getRunningAppProcesses().iterator();
      while (localIterator.hasNext())
      {
        ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)localIterator.next();
        if (localRunningAppProcessInfo.pid == i) {
          this.mCurrentProcessName = localRunningAppProcessInfo.processName;
        }
      }
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return (SmttServiceProxy.getInstance().isWeChatCrashMonitorEnable()) && ("com.tencent.mm:tools".equals(this.mCurrentProcessName)) && (this.mTombstone.getSdkVersion() >= 43650);
  }
  
  public void setTbsVersion(String paramString, int paramInt)
  {
    int i;
    if (this.mTombstone != null) {
      i = 0;
    }
    try
    {
      int j = Integer.parseInt(paramString);
      i = j;
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    this.mTombstone.setCoreVersion(i);
    this.mTombstone.setSdkVersion(paramInt);
  }
  
  public void updateCrashMonitorMessage()
  {
    if (this.mAppContext == null) {
      return;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.mAppContext.getApplicationInfo().nativeLibraryDir);
    ((StringBuilder)localObject).append(File.separator);
    ((StringBuilder)localObject).append("libwechatCrashForJni.so");
    localObject = ((StringBuilder)localObject).toString();
    String str = message();
    if ((!TextUtils.isEmpty((CharSequence)localObject)) && (!TextUtils.isEmpty(str))) {
      AwNetworkUtils.nativeUpdateCrashMonitorMessage((String)localObject, str);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\crashmonitor\CrashMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */