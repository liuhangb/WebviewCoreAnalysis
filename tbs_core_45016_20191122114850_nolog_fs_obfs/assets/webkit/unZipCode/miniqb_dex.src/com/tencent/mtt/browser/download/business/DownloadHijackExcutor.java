package com.tencent.mtt.browser.download.business;

import android.content.ContentValues;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.utils.StringUtils;
import com.tencent.downloadprovider.DownloadproviderHelper;
import com.tencent.mtt.browser.download.engine.DownloadTask;
import com.tencent.tbs.common.download.BaseDownloadManager;

public class DownloadHijackExcutor
{
  public static final String SPLITOR = ";";
  public static final String TAG = "ZHTAG";
  private final int jdField_a_of_type_Int = 0;
  private BaseDownloadManager jdField_a_of_type_ComTencentTbsCommonDownloadBaseDownloadManager = null;
  private final int b = 1;
  
  public DownloadHijackExcutor(BaseDownloadManager paramBaseDownloadManager)
  {
    this.jdField_a_of_type_ComTencentTbsCommonDownloadBaseDownloadManager = paramBaseDownloadManager;
  }
  
  public boolean doHijack(DownloadTask paramDownloadTask, long paramLong1, long paramLong2)
  {
    if (paramDownloadTask == null) {
      return false;
    }
    FLogger.d("ZHTAG", "notifyTaskLength");
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("oldUrl : ");
    ((StringBuilder)localObject).append(paramDownloadTask.getTaskUrl());
    FLogger.d("ZHTAG", ((StringBuilder)localObject).toString());
    localObject = paramDownloadTask.getDownloaderDownloadDataUrl();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("newUrl : ");
    localStringBuilder.append((String)localObject);
    FLogger.d("ZHTAG", localStringBuilder.toString());
    if (paramLong1 > 0L)
    {
      if (!paramDownloadTask.canRetry()) {
        return false;
      }
      if (!TextUtils.isEmpty(paramDownloadTask.mHijackInfo))
      {
        localObject = paramDownloadTask.mHijackInfo.split(";");
        if ((localObject != null) && (localObject.length >= 2))
        {
          j = StringUtils.parseInt(localObject[0], -1);
          i = StringUtils.parseInt(localObject[1], -1);
          break label168;
        }
      }
      int i = -1;
      int j = -1;
      label168:
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("orginLength : ");
      ((StringBuilder)localObject).append(paramLong1);
      FLogger.d("ZHTAG", ((StringBuilder)localObject).toString());
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("newLength : ");
      ((StringBuilder)localObject).append(paramLong2);
      FLogger.d("ZHTAG", ((StringBuilder)localObject).toString());
      if (paramLong1 == paramLong2)
      {
        FLogger.d("ZHTAG", "size is Correct");
        if ((j != -1) && (i == -1))
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append(j);
          ((StringBuilder)localObject).append(";");
          ((StringBuilder)localObject).append(0);
          paramDownloadTask.mHijackInfo = ((StringBuilder)localObject).toString();
          localObject = new ContentValues();
          ((ContentValues)localObject).put("id", Integer.valueOf(paramDownloadTask.getDownloadTaskId()));
          ((ContentValues)localObject).put("extend_7", paramDownloadTask.mHijackInfo);
          DownloadproviderHelper.a((ContentValues)localObject);
        }
        return false;
      }
      FLogger.d("ZHTAG", "size is Uncorrect! Hijacked !");
      if ((j != -1) && (i == -1))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(j);
        ((StringBuilder)localObject).append(";");
        ((StringBuilder)localObject).append(1);
        paramDownloadTask.mHijackInfo = ((StringBuilder)localObject).toString();
      }
      i = j + 1;
      if (i != 0)
      {
        this.jdField_a_of_type_ComTencentTbsCommonDownloadBaseDownloadManager.retryUrl(paramDownloadTask);
        localObject = new ContentValues();
        ((ContentValues)localObject).put("id", Integer.valueOf(paramDownloadTask.getDownloadTaskId()));
        paramDownloadTask = new StringBuilder();
        paramDownloadTask.append(i + 1000);
        paramDownloadTask.append(";");
        paramDownloadTask.append(-1);
        ((ContentValues)localObject).put("extend_7", paramDownloadTask.toString());
        DownloadproviderHelper.a((ContentValues)localObject);
        return true;
      }
      paramDownloadTask.mHijackInfo = "0;-1";
      localObject = new ContentValues();
      ((ContentValues)localObject).put("id", Integer.valueOf(paramDownloadTask.getDownloadTaskId()));
      ((ContentValues)localObject).put("extend_7", paramDownloadTask.mHijackInfo);
      DownloadproviderHelper.a((ContentValues)localObject);
      this.jdField_a_of_type_ComTencentTbsCommonDownloadBaseDownloadManager.restartTask(paramDownloadTask.getDownloadTaskId(), null);
      return true;
    }
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\download\business\DownloadHijackExcutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */