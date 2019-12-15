package com.tencent.tbs.common.download.qb;

import android.os.Bundle;
import java.io.Serializable;

public abstract interface QBDownloadListener
  extends Serializable
{
  public static final int STATUS_INSTALL_ING = -7;
  public static final int STATUS_ISDOWNLOADING = -3;
  public static final int STATUS_NOSDCARD = -2;
  public static final int STATUS_QBDOWNLOADED = -4;
  public static final int STATUS_QBINSTALLED = -1;
  public static final int STATUS_START_DOWNLOAD_FAILED = -5;
  public static final int STATUS_START_DOWNLOAD_PARAMS_INVALID = -6;
  public static final int STATUS_WILLDOWNLOAD = 1;
  
  public abstract void onDownloadFailed(boolean paramBoolean, Bundle paramBundle);
  
  public abstract void onDownloadPause(boolean paramBoolean, int paramInt);
  
  public abstract void onDownloadProgress(boolean paramBoolean, int paramInt);
  
  public abstract void onDownloadResume(boolean paramBoolean, int paramInt);
  
  public abstract void onDownloadStart(boolean paramBoolean);
  
  public abstract void onDownloadSucess(boolean paramBoolean, String paramString, Bundle paramBundle);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\QBDownloadListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */