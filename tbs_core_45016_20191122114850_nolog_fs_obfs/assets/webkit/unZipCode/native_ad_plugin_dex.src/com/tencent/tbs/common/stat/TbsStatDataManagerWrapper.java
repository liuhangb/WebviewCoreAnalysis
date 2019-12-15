package com.tencent.tbs.common.stat;

import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.baseinfo.TbsWupManager;

public class TbsStatDataManagerWrapper
{
  private static final String TAG = "TesStatManagerWrapper";
  private static TbsStatDataManagerWrapper sInstance;
  private static byte[] sInstanceLock = new byte[0];
  private boolean mHasLoaded = false;
  private Object mLoadLock = new Object();
  
  public static TbsStatDataManagerWrapper getInstance()
  {
    if (sInstance == null) {
      synchronized (sInstanceLock)
      {
        if (sInstance == null) {
          sInstance = new TbsStatDataManagerWrapper();
        }
      }
    }
    return sInstance;
  }
  
  private void loadStatDataSync()
  {
    LogUtils.d("TesStatManagerWrapper", "start loading TBS stat files");
    int i = TbsStatDataManager.getTBSStatManager().loadDataFromFile();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("finish loading TBS stat files, ");
    localStringBuilder.append(i);
    localStringBuilder.append(" files has been loaded");
    LogUtils.d("TesStatManagerWrapper", localStringBuilder.toString());
    LogUtils.d("TesStatManagerWrapper", "start loading MiniQb stat files");
    i = TbsStatDataManager.getSimpleQBStatManager().loadDataFromFile();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("finish loading MiniQB stat files, ");
    localStringBuilder.append(i);
    localStringBuilder.append(" files has been loaded");
    LogUtils.d("TesStatManagerWrapper", localStringBuilder.toString());
    boolean bool1 = TbsStatDataManager.getTBSStatManager().checkShouldUpload();
    boolean bool2 = TbsStatDataManager.getSimpleQBStatManager().checkShouldUpload();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("check if should upload, TBS=");
    localStringBuilder.append(bool1);
    localStringBuilder.append(", MiniQB=");
    localStringBuilder.append(bool2);
    LogUtils.d("TesStatManagerWrapper", localStringBuilder.toString());
    if ((bool2) || (bool1))
    {
      LogUtils.d("TesStatManagerWrapper", "begin upload MiniQb and TBS stat datas");
      TbsStatDataUploader.getInstance().uploadAll();
    }
  }
  
  public void loadStatManagerData()
  {
    synchronized (this.mLoadLock)
    {
      if ((this.mHasLoaded) && (!TbsWupManager.getInstance().isNewDay()))
      {
        LogUtils.d("TesStatManagerWrapper", "we have load data already, ignore this request");
        return;
      }
      this.mHasLoaded = true;
      TbsStatDataManager.StatWorkerThread.getInstance().post(new Runnable()
      {
        public void run()
        {
          TbsStatDataManagerWrapper.this.loadStatDataSync();
        }
      });
      return;
    }
  }
  
  public void saveStatData()
  {
    TbsStatDataManager.getTBSStatManager().save();
    TbsStatDataManager.getSimpleQBStatManager().save();
  }
  
  public void uploadStatData()
  {
    LogUtils.d("TesStatManagerWrapper", "uploadStatData called");
    TbsStatDataUploader.getInstance().uploadAll();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\stat\TbsStatDataManagerWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */