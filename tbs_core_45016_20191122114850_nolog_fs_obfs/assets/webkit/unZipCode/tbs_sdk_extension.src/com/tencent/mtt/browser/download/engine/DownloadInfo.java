package com.tencent.mtt.browser.download.engine;

import android.text.TextUtils;
import com.tencent.mtt.base.task.TaskObserver;
import com.tencent.tbs.common.download.BaseDownloadManager.OnDownloadFeedbackListener;
import java.io.Serializable;
import java.util.ArrayList;

public class DownloadInfo
  implements Serializable
{
  public static final String CHANNEL_ID = "ChannelID";
  public static final String DOWNLOAD_CHANNEL_COMMON_TO_QQMARKET = "004001";
  public static final String DOWNLOAD_CHANNEL_TBS = "110102";
  public static final int DOWNLOAD_FROM_DELTA_UPDATE = 3;
  public static final int DOWNLOAD_FROM_FASTLINK = 6;
  public static final int DOWNLOAD_FROM_QQMARKET = 1;
  public static final int DOWNLOAD_FROM_VIDEO = 2;
  public static final int DOWNLOAD_FROM_WEIYUN = 5;
  public static final int DOWNLOAD_FROM_X5WEBVIEW = 4;
  public static final int FROM_NORMAL = 0;
  public static final int FROM_TBS = 1;
  public static final int FROM_THIRD_QQ = 3;
  public static final int FROM_THIRD_SOGOU = 2;
  public static boolean sFromTBS = false;
  public boolean alreadyCompleted = false;
  public String annotation = "";
  public String annotationExt = "";
  public boolean autoInstall = true;
  public long createTime;
  public boolean deleteTaskIfCompleted;
  public boolean disablePreDownlod;
  public long extFlag;
  public String fileFolderPath;
  public String fileName;
  public long fileSize;
  public int flag;
  public boolean forbidRename;
  public int fromThird = 0;
  public byte fromWhere;
  public boolean hasChooserDlg;
  public boolean hasDetail = false;
  public int hasNewVersionApk = 0;
  public boolean hasThreadPool;
  public boolean hasToast = true;
  public boolean isFileSizeReal;
  public boolean isPluginTask;
  public boolean isPreDownload;
  public boolean isPreDownloadApp = false;
  public BaseDownloadManager.OnDownloadFeedbackListener listener;
  public int mApkType = 0;
  public boolean mAudioTask = false;
  public String mBackupApkUrl;
  public boolean mCanDownContorlerShowHead = false;
  public String mChannel;
  public String mChannelPkgName;
  public int mClarity = 0;
  public String mCookie;
  public String mDownloadPkgName;
  public int mDownloadSecurityLevel = 0;
  public String mFromChannel;
  public String mIconUrl = null;
  public boolean mIsPartner = false;
  public String mMarketPkgName;
  public String mNewVersion;
  public String mOrgVersion;
  public int mPkgSwitch = 0;
  public String mRealFileName;
  public String mReportString;
  public int mRetCode = 1;
  public long mSafeUrlFileSize = 0L;
  public String mWebTitle = null;
  public String mWebUrl = null;
  public String mimeType;
  public boolean needNotification;
  public DownloadTaskConfirmObserver observer;
  public String originalUrl;
  public String postData;
  public boolean qbUpdate = false;
  public String referer;
  public ArrayList<String> retryUrls;
  public String safeUrl;
  public long saveFlowSize;
  public boolean show2G2GDialog = true;
  public String skinName;
  public byte statusCache;
  public int taskId;
  public TaskObserver taskObserver;
  public String url;
  public int videoType;
  public int windowId;
  
  public DownloadInfo()
  {
    this.url = null;
    this.fileName = null;
    this.fileSize = 0L;
    this.isFileSizeReal = false;
    this.referer = null;
    this.fileFolderPath = null;
    this.hasToast = true;
    this.hasChooserDlg = true;
    this.isPluginTask = false;
    this.needNotification = true;
    this.deleteTaskIfCompleted = false;
    this.saveFlowSize = 0L;
    this.videoType = 99;
    this.fromWhere = 0;
    this.isPreDownload = false;
    this.hasThreadPool = true;
    this.disablePreDownlod = false;
    this.forbidRename = false;
    this.autoInstall = true;
  }
  
  public DownloadInfo(String paramString1, String paramString2, long paramLong, String paramString3, String paramString4)
  {
    this.url = paramString1;
    this.fileName = paramString2;
    this.fileSize = paramLong;
    this.isFileSizeReal = false;
    this.referer = paramString3;
    this.fileFolderPath = paramString4;
    this.videoType = 99;
    this.fromWhere = 0;
    this.forbidRename = false;
    this.autoInstall = true;
  }
  
  public void setRealFileName(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    Object localObject = paramString;
    if (!paramString.toLowerCase().endsWith(".apk"))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append(".apk");
      localObject = ((StringBuilder)localObject).toString();
    }
    this.mRealFileName = ((String)localObject);
  }
  
  public static abstract interface DownloadTaskConfirmObserver
  {
    public abstract void onTaskCancelled(DownloadInfo paramDownloadInfo);
    
    public abstract void onTaskCreated(DownloadTask paramDownloadTask);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\download\engine\DownloadInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */