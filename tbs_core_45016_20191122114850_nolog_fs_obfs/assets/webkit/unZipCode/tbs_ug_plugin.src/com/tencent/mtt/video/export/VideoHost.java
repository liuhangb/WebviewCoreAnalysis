package com.tencent.mtt.video.export;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.tencent.common.utils.IntentUtils;
import com.tencent.mtt.video.browser.export.data.H5VideoEpisodeInfo;
import com.tencent.tbs.common.sniffer.SniffObserver;
import com.tencent.tbs.common.utils.DBHelper;
import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class VideoHost
{
  public static final int VIDEO_HOST_BROWSER = 1;
  public static final int VIDEO_HOST_DEFAULT = 0;
  public static final int VIDEO_HOST_TBS_MTT_CALL = 4;
  public static final int VIDEO_HOST_THIRD_CALL = 2;
  public static final int VIDEO_HOST_X5_SDK = 3;
  public static String mSubProcessName;
  protected Context mAppContext;
  
  public VideoHost(Context paramContext)
  {
    this.mAppContext = paramContext.getApplicationContext();
  }
  
  public static String getCurrentProcessName(Context paramContext)
  {
    paramContext = IntentUtils.getRunningAppProcess(paramContext);
    Object localObject = null;
    if (paramContext != null)
    {
      if (paramContext.size() == 0) {
        return null;
      }
      Iterator localIterator = paramContext.iterator();
      do
      {
        paramContext = (Context)localObject;
        if (!localIterator.hasNext()) {
          break;
        }
        paramContext = (ActivityManager.RunningAppProcessInfo)localIterator.next();
      } while (paramContext.pid != Process.myPid());
      paramContext = paramContext.processName;
      return paramContext;
    }
    return null;
  }
  
  public static String getSubProcessName(Context paramContext)
  {
    String str = mSubProcessName;
    if (str != null) {
      return str;
    }
    str = paramContext.getPackageName();
    paramContext = getCurrentProcessName(paramContext);
    if (!TextUtils.isEmpty(paramContext)) {
      if (paramContext.length() > str.length()) {
        mSubProcessName = paramContext.substring(str.length() + 1);
      } else {
        mSubProcessName = "main";
      }
    }
    return mSubProcessName;
  }
  
  public Bundle callMiscMethod(String paramString, Bundle paramBundle, Context paramContext)
  {
    return null;
  }
  
  public boolean checkCanSwitchScreen()
  {
    return true;
  }
  
  public String getAccountInfo()
  {
    return null;
  }
  
  public String getCacheDirBySubProcess(String paramString)
  {
    StringBuilder localStringBuilder;
    if (Build.VERSION.SDK_INT >= 23)
    {
      localObject = this.mAppContext.getExternalFilesDir(null);
      if (localObject != null)
      {
        if (TextUtils.isEmpty(paramString))
        {
          paramString = "/VideoCache/";
        }
        else
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("/VideoCache/");
          localStringBuilder.append(paramString);
          localStringBuilder.append("/");
          paramString = localStringBuilder.toString();
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(((File)localObject).getAbsolutePath());
        localStringBuilder.append(paramString);
        return localStringBuilder.toString();
      }
      return null;
    }
    Object localObject = this.mAppContext.getPackageName();
    if (TextUtils.isEmpty(paramString))
    {
      paramString = (String)localObject;
    }
    else
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append((String)localObject);
      localStringBuilder.append("/");
      localStringBuilder.append(paramString);
      localStringBuilder.append("/");
      paramString = localStringBuilder.toString();
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(Environment.getExternalStorageDirectory().getAbsolutePath());
    ((StringBuilder)localObject).append("/VideoCache/");
    ((StringBuilder)localObject).append(paramString);
    return ((StringBuilder)localObject).toString();
  }
  
  public String getCookie(String paramString, boolean paramBoolean)
  {
    return null;
  }
  
  public String getCurrentUserName(String paramString)
  {
    return null;
  }
  
  public DBHelper getDbHelper()
  {
    return null;
  }
  
  public int getDefaultFullscreenMode()
  {
    return 102;
  }
  
  public String getLocalIp()
  {
    return null;
  }
  
  public float getStatusBarHeight()
  {
    return 0.0F;
  }
  
  public String getUa()
  {
    return null;
  }
  
  public int getVideoBuffMaxSize()
  {
    return 0;
  }
  
  public int getVideoBuffMinSize()
  {
    return 0;
  }
  
  public String getVideoCacheDir()
  {
    try
    {
      if (("mounted".equals(Environment.getExternalStorageState())) && (this.mAppContext != null))
      {
        String str = getCacheDirBySubProcess(getSubProcessName(this.mAppContext));
        return str;
      }
      return null;
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
    }
    return "";
  }
  
  public int getVideoHostType()
  {
    return 0;
  }
  
  public void goToMyVideo(boolean paramBoolean, int paramInt) {}
  
  public boolean isCacheUsing(String paramString)
  {
    return false;
  }
  
  public boolean isCanAttachVideoToWebView(VideoProxyDefault paramVideoProxyDefault)
  {
    return true;
  }
  
  public boolean isEnableLoadImage()
  {
    return true;
  }
  
  public boolean isEnablePreloadEnable()
  {
    return true;
  }
  
  public boolean isIncognito()
  {
    return false;
  }
  
  public boolean isNightMode()
  {
    return false;
  }
  
  public boolean isPlayerInMyVideo(View paramView)
  {
    return false;
  }
  
  public boolean isUrlInBackList(String paramString1, String paramString2)
  {
    return true;
  }
  
  public void jumpToFeedBack(String paramString, long paramLong) {}
  
  public void matchEpisodeInfo(H5VideoInfo paramH5VideoInfo) {}
  
  public void onNoSpace(boolean paramBoolean) {}
  
  public void onPlayerCountChanged(int paramInt) {}
  
  public void onPlayerFullScreen() {}
  
  public void onSniffPlayFailed(String paramString) {}
  
  public void onVideoCanNotSupport(String paramString) {}
  
  public void openUrl(String paramString, boolean paramBoolean) {}
  
  public void play(H5VideoInfo paramH5VideoInfo) {}
  
  public boolean reqSwitchProxy(IMTTVideoPlayer paramIMTTVideoPlayer, H5VideoInfo paramH5VideoInfo)
  {
    return false;
  }
  
  public void setCookie(URL paramURL, Map<String, List<String>> paramMap, boolean paramBoolean) {}
  
  public void showToast(String paramString, int paramInt)
  {
    try
    {
      if (this.mAppContext != null) {
        Toast.makeText(this.mAppContext, paramString, paramInt).show();
      }
      return;
    }
    catch (Exception paramString) {}
  }
  
  public void sniffVideo(String paramString, int paramInt, SniffObserver paramSniffObserver) {}
  
  public boolean supportGoToMyVideo()
  {
    return false;
  }
  
  public boolean supportSniff(String paramString)
  {
    return false;
  }
  
  public void switchMttProxy(H5VideoEpisodeInfo paramH5VideoEpisodeInfo) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\export\VideoHost.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */