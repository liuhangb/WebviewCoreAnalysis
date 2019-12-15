package com.tencent.mtt.browser.download.engine;

import android.text.TextUtils;
import com.tencent.basesupport.FLogger;

public class DownloadOperation
{
  public static final int DOWNLOAD_OPERATION_NONE = 0;
  public static final int DOWNLOAD_OPERATION_RENAME_DOWNLOAD_TASK_NAME = 1;
  public static final int DOWNLOAD_OPERATION_RETRY_URL = 2;
  public static final String TAG = "DownloadOperation";
  public int mOperationCmd;
  public String mValue;
  
  public DownloadOperation()
  {
    this.mOperationCmd = 0;
    this.mValue = "";
  }
  
  public DownloadOperation(int paramInt, String paramString)
  {
    this.mOperationCmd = paramInt;
    this.mValue = paramString;
  }
  
  public void clear()
  {
    this.mOperationCmd = 0;
    this.mValue = null;
  }
  
  public boolean databaseToObject(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DownloadOperation=strFormat[");
    localStringBuilder.append(paramString);
    localStringBuilder.append("]");
    FLogger.d("DownloadOperation", localStringBuilder.toString());
    int i = paramString.indexOf(';', 0);
    if (i != -1)
    {
      this.mOperationCmd = Integer.parseInt((String)paramString.subSequence(0, i));
      this.mValue = paramString.substring(i + 1);
      return false;
    }
    return true;
  }
  
  public void init(int paramInt, String paramString)
  {
    this.mOperationCmd = paramInt;
    this.mValue = paramString;
  }
  
  public String objectToDatabase()
  {
    if (this.mOperationCmd == 0) {
      return null;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.mOperationCmd);
    localStringBuilder.append(";");
    localStringBuilder.append(this.mValue);
    return localStringBuilder.toString();
  }
  
  public boolean validOps()
  {
    return this.mOperationCmd != 0;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\download\engine\DownloadOperation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */