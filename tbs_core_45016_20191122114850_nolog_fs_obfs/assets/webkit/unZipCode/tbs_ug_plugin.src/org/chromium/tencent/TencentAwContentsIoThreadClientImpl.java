package org.chromium.tencent;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;
import org.chromium.android_webview.AwContentsClient;
import org.chromium.base.ThreadUtils;

public class TencentAwContentsIoThreadClientImpl
  extends TencentAwContentsIoThreadClient
{
  private static final String TAG = "TencentAwReportClientImpl";
  String mAppWebViewPath = null;
  private AwContentsClient mContentsClient;
  private AwContentsClientExtension mContentsClientExtension;
  private Context mContext;
  String mDatabasePath = null;
  private TencentAwSettings mSettings;
  
  public TencentAwContentsIoThreadClientImpl(Context paramContext, AwContentsClient paramAwContentsClient)
  {
    this.mContext = paramContext;
    this.mContentsClient = paramAwContentsClient;
  }
  
  boolean checkIsFileOrUrl(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    if (!paramString.startsWith("file://")) {
      return true;
    }
    String str = null;
    if (paramString.length() > 7) {
      str = paramString.substring(7);
    }
    try
    {
      paramString = new File(str).getCanonicalPath();
      if (TextUtils.isEmpty(this.mDatabasePath))
      {
        str = this.mContext.getApplicationContext().getDatabasePath(".").getParentFile().getParentFile().getCanonicalPath();
        if (TextUtils.isEmpty(str)) {
          return true;
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(str);
        localStringBuilder.append("/app_webview");
        this.mAppWebViewPath = localStringBuilder.toString();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(str);
        localStringBuilder.append("/databases");
        this.mDatabasePath = localStringBuilder.toString();
      }
      boolean bool;
      if (!paramString.startsWith(this.mDatabasePath)) {
        bool = paramString.startsWith(this.mAppWebViewPath);
      }
      return !bool;
    }
    catch (Exception paramString) {}
    return true;
  }
  
  public int getDataFilterForRequestInfo()
  {
    if (this.mContentsClientExtension == null) {
      return 0;
    }
    return this.mSettings.getDataFilterForRequestInfo() | this.mContentsClientExtension.shouldReportSubResourcePerformance();
  }
  
  public void notifyResponseStartTime(final long paramLong)
  {
    if (this.mContentsClientExtension == null) {
      return;
    }
    ThreadUtils.runOnUiThread(new Runnable()
    {
      public void run()
      {
        TencentAwContentsIoThreadClientImpl.this.mContentsClientExtension.notifyResponseStartTime(paramLong);
      }
    });
  }
  
  public void notifyUrlSecurityLevelResp(String paramString1, String paramString2)
  {
    AwContentsClientExtension localAwContentsClientExtension = this.mContentsClientExtension;
    if (localAwContentsClientExtension == null) {
      return;
    }
    localAwContentsClientExtension.notifyUrlSecurityLevelResp(paramString1, paramString2);
  }
  
  public void onDownloadStart(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong, String paramString5, String paramString6, String paramString7, String paramString8, byte[] paramArrayOfByte)
  {
    AwContentsClientExtension localAwContentsClientExtension = this.mContentsClientExtension;
    if (localAwContentsClientExtension == null) {
      return;
    }
    localAwContentsClientExtension.onDownloadStart(paramString1, paramString2, paramString3, paramString4, paramLong, paramString5, paramString6, paramString7, paramString8, paramArrayOfByte);
  }
  
  public void onNotifyResourceRequestURL(String paramString)
  {
    AwContentsClientExtension localAwContentsClientExtension = this.mContentsClientExtension;
    if (localAwContentsClientExtension == null) {
      return;
    }
    localAwContentsClientExtension.onNotifyResourceRequestURL(paramString);
  }
  
  public void onReportMainresourceInDirectMode(final String paramString)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        if (TencentAwContentsIoThreadClientImpl.this.mContentsClientExtension != null) {
          TencentAwContentsIoThreadClientImpl.this.mContentsClientExtension.onReportMainresourceInDirectMode(paramString);
        }
      }
    });
  }
  
  public void onReportPrivacyHeaders(String paramString)
  {
    AwContentsClientExtension localAwContentsClientExtension = this.mContentsClientExtension;
    if (localAwContentsClientExtension != null) {
      localAwContentsClientExtension.onReportPrivacyHeaders(paramString);
    }
  }
  
  public void onReportResourceInfo(AwContentsClient.AwResourceMetrics paramAwResourceMetrics)
  {
    AwContentsClientExtension localAwContentsClientExtension = this.mContentsClientExtension;
    if (localAwContentsClientExtension != null) {
      localAwContentsClientExtension.onReportResourceInfo(paramAwResourceMetrics);
    }
  }
  
  public void onReportStgwTime(AwContentsClient.AwStgwTime paramAwStgwTime)
  {
    AwContentsClientExtension localAwContentsClientExtension = this.mContentsClientExtension;
    if (localAwContentsClientExtension != null) {
      localAwContentsClientExtension.onReportStgwTime(paramAwStgwTime);
    }
  }
  
  public void onResolveProxyHeadersCmd(String paramString)
  {
    AwContentsClientExtension localAwContentsClientExtension = this.mContentsClientExtension;
    if (localAwContentsClientExtension != null) {
      localAwContentsClientExtension.onResolveProxyHeadersCmd(paramString);
    }
  }
  
  public void reportResourceAllInfo(AwContentsClient.AwWebRequestInfo paramAwWebRequestInfo)
  {
    AwContentsClientExtension localAwContentsClientExtension = this.mContentsClientExtension;
    if (localAwContentsClientExtension == null) {
      return;
    }
    localAwContentsClientExtension.reportResourceAllInfo(paramAwWebRequestInfo);
  }
  
  public void reportResponseFailedWithErrCode(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    AwContentsClientExtension localAwContentsClientExtension = this.mContentsClientExtension;
    if (localAwContentsClientExtension == null) {
      return;
    }
    localAwContentsClientExtension.reportResponseFailedWithErrCode(paramString1, paramString2, paramString3, paramInt);
  }
  
  public void reportTrafficAnomalyInfo(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, int paramInt3, String paramString4, int paramInt4)
  {
    AwContentsClientExtension localAwContentsClientExtension = this.mContentsClientExtension;
    if (localAwContentsClientExtension == null) {
      return;
    }
    localAwContentsClientExtension.reportTrafficAnomalyInfo(paramInt1, paramString1, paramString2, paramString3, paramInt2, paramInt3, paramString4, paramInt4);
  }
  
  public void setAwContentsClientExtension(AwContentsClientExtension paramAwContentsClientExtension)
  {
    this.mContentsClientExtension = paramAwContentsClientExtension;
  }
  
  public void setTencentAwSettings(TencentAwSettings paramTencentAwSettings)
  {
    this.mSettings = paramTencentAwSettings;
  }
  
  public boolean shouldBlockTheRequestCustom(String paramString1, String paramString2, int paramInt)
  {
    if (!checkIsFileOrUrl(paramString1)) {
      return true;
    }
    AwContentsClientExtension localAwContentsClientExtension = this.mContentsClientExtension;
    if (localAwContentsClientExtension == null) {
      return false;
    }
    return localAwContentsClientExtension.shouldBlockTheRequestCustom(paramString1, paramString2, paramInt);
  }
  
  public boolean shouldReportPerformanceToServer(String paramString)
  {
    AwContentsClientExtension localAwContentsClientExtension = this.mContentsClientExtension;
    if (localAwContentsClientExtension == null) {
      return false;
    }
    return localAwContentsClientExtension.shouldReportPerformanceToServer(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\TencentAwContentsIoThreadClientImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */