package org.chromium.content.browser;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.text.TextUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;
import org.chromium.ui.widget.Toast;

@JNINamespace("content")
public class TracingControllerAndroid
{
  private static final String ACTION_LIST_CATEGORIES = "GPU_PROFILER_LIST_CATEGORIES";
  private static final String ACTION_START = "GPU_PROFILER_START";
  private static final String ACTION_STOP = "GPU_PROFILER_STOP";
  private static final String CATEGORIES_EXTRA = "categories";
  private static final String DEFAULT_CHROME_CATEGORIES_PLACE_HOLDER = "_DEFAULT_CHROME_CATEGORIES";
  private static final String FILE_EXTRA = "file";
  private static final String PROFILER_FINISHED_FMT = "Profiler finished. Results are in %s.";
  private static final String PROFILER_STARTED_FMT = "Profiler started: %s";
  private static final String RECORD_CONTINUOUSLY_EXTRA = "continuous";
  private static final String TAG = "cr.TracingController";
  private final TracingBroadcastReceiver mBroadcastReceiver;
  private final Context mContext;
  private String mFilename;
  private final TracingIntentFilter mIntentFilter;
  private boolean mIsTracing;
  protected long mNativeTracingControllerAndroid;
  private boolean mShowToasts = true;
  
  public TracingControllerAndroid(Context paramContext)
  {
    this.mContext = paramContext;
    this.mBroadcastReceiver = new TracingBroadcastReceiver();
    this.mIntentFilter = new TracingIntentFilter(paramContext);
  }
  
  @CalledByNative
  private static String generateTracingFilePath()
  {
    if (!"mounted".equals(Environment.getExternalStorageState())) {
      return null;
    }
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HHmmss", Locale.US);
    localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    File localFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("chrome-profile-results-");
    localStringBuilder.append(localSimpleDateFormat.format(new Date()));
    return new File(localFile, localStringBuilder.toString()).getPath();
  }
  
  private void logAndToastError(String paramString)
  {
    Log.e("cr.TracingController", paramString, new Object[0]);
    if (this.mShowToasts) {
      Toast.a(this.mContext, paramString, 0).a();
    }
  }
  
  private void logForProfiler(String paramString) {}
  
  private native void nativeDestroy(long paramLong);
  
  private native String nativeGetDefaultCategories();
  
  private native boolean nativeGetKnownCategoryGroupsAsync(long paramLong);
  
  private native long nativeInit();
  
  private native boolean nativeStartTracing(long paramLong, String paramString1, String paramString2);
  
  private native void nativeStopTracing(long paramLong, String paramString);
  
  private void showToast(String paramString)
  {
    if (this.mShowToasts) {
      Toast.a(this.mContext, paramString, 0).a();
    }
  }
  
  public void destroy()
  {
    long l = this.mNativeTracingControllerAndroid;
    if (l != 0L)
    {
      nativeDestroy(l);
      this.mNativeTracingControllerAndroid = 0L;
    }
  }
  
  public BroadcastReceiver getBroadcastReceiver()
  {
    return this.mBroadcastReceiver;
  }
  
  public void getCategoryGroups()
  {
    initializeNativeControllerIfNeeded();
    if (!nativeGetKnownCategoryGroupsAsync(this.mNativeTracingControllerAndroid)) {
      Log.e("cr.TracingController", "Unable to fetch tracing record groups list.", new Object[0]);
    }
  }
  
  public IntentFilter getIntentFilter()
  {
    return this.mIntentFilter;
  }
  
  public String getOutputPath()
  {
    return this.mFilename;
  }
  
  protected void initializeNativeControllerIfNeeded()
  {
    if (this.mNativeTracingControllerAndroid == 0L) {
      this.mNativeTracingControllerAndroid = nativeInit();
    }
  }
  
  public boolean isTracing()
  {
    return this.mIsTracing;
  }
  
  @CalledByNative
  protected void onTracingStopped()
  {
    if (!isTracing())
    {
      Log.e("cr.TracingController", "Received onTracingStopped, but we aren't tracing", new Object[0]);
      return;
    }
    logForProfiler(String.format("Profiler finished. Results are in %s.", new Object[] { this.mFilename }));
    this.mIsTracing = false;
    this.mFilename = null;
  }
  
  public void registerReceiver(Context paramContext)
  {
    X5ApiCompatibilityUtils.x5RegisterReceiver(paramContext, getBroadcastReceiver(), getIntentFilter());
  }
  
  public boolean startTracing(String paramString1, boolean paramBoolean, String paramString2, String paramString3)
  {
    this.mShowToasts = paramBoolean;
    if (isTracing())
    {
      Log.e("cr.TracingController", "Received startTracing, but we're already tracing", new Object[0]);
      return false;
    }
    initializeNativeControllerIfNeeded();
    if (!nativeStartTracing(this.mNativeTracingControllerAndroid, paramString2, paramString3.toString())) {
      return false;
    }
    logForProfiler(String.format("Profiler started: %s", new Object[] { paramString2 }));
    this.mFilename = paramString1;
    this.mIsTracing = true;
    return true;
  }
  
  public boolean startTracing(boolean paramBoolean, String paramString1, String paramString2)
  {
    this.mShowToasts = paramBoolean;
    return startTracing(generateTracingFilePath(), paramBoolean, paramString1, paramString2);
  }
  
  public void stopTracing()
  {
    if (isTracing()) {
      nativeStopTracing(this.mNativeTracingControllerAndroid, this.mFilename);
    }
  }
  
  public void unregisterReceiver(Context paramContext)
  {
    X5ApiCompatibilityUtils.x5UnRegisterReceiver(paramContext, getBroadcastReceiver());
  }
  
  class TracingBroadcastReceiver
    extends BroadcastReceiver
  {
    TracingBroadcastReceiver() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if (paramIntent.getAction().endsWith("GPU_PROFILER_START"))
      {
        paramContext = paramIntent.getStringExtra("categories");
        if (TextUtils.isEmpty(paramContext)) {
          paramContext = TracingControllerAndroid.this.nativeGetDefaultCategories();
        } else {
          paramContext = paramContext.replaceFirst("_DEFAULT_CHROME_CATEGORIES", TracingControllerAndroid.this.nativeGetDefaultCategories());
        }
        String str;
        if (paramIntent.getStringExtra("continuous") == null) {
          str = "record-until-full";
        } else {
          str = "record-continuously";
        }
        paramIntent = paramIntent.getStringExtra("file");
        if (paramIntent != null)
        {
          TracingControllerAndroid.this.startTracing(paramIntent, true, paramContext, str);
          return;
        }
        TracingControllerAndroid.this.startTracing(true, paramContext, str);
        return;
      }
      if (paramIntent.getAction().endsWith("GPU_PROFILER_STOP"))
      {
        TracingControllerAndroid.this.stopTracing();
        return;
      }
      if (paramIntent.getAction().endsWith("GPU_PROFILER_LIST_CATEGORIES"))
      {
        TracingControllerAndroid.this.getCategoryGroups();
        return;
      }
      Log.e("cr.TracingController", "Unexpected intent: %s", new Object[] { paramIntent });
    }
  }
  
  @SuppressLint({"ParcelCreator"})
  private static class TracingIntentFilter
    extends IntentFilter
  {
    TracingIntentFilter(Context paramContext)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramContext.getPackageName());
      localStringBuilder.append(".");
      localStringBuilder.append("GPU_PROFILER_START");
      addAction(localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramContext.getPackageName());
      localStringBuilder.append(".");
      localStringBuilder.append("GPU_PROFILER_STOP");
      addAction(localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramContext.getPackageName());
      localStringBuilder.append(".");
      localStringBuilder.append("GPU_PROFILER_LIST_CATEGORIES");
      addAction(localStringBuilder.toString());
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\TracingControllerAndroid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */