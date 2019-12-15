package com.tencent.tbs.common.stat;

import java.util.ArrayList;

public class PageLoadInfoData
{
  private final String WUP_PLI_DATA_VERSION = "000001";
  public int mId = 0;
  public ArrayList<String> mRecordInfos;
  public long mTime = 0L;
  public String version = "000001";
  
  public PageLoadInfoData()
  {
    reset();
  }
  
  private void reset()
  {
    try
    {
      this.version = "000001";
      this.mRecordInfos = new ArrayList();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public PageLoadInfoData copy()
  {
    try
    {
      PageLoadInfoData localPageLoadInfoData = new PageLoadInfoData();
      try
      {
        localPageLoadInfoData.mId = this.mId;
        localPageLoadInfoData.mTime = this.mTime;
        localPageLoadInfoData.mRecordInfos = new ArrayList(this.mRecordInfos);
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        localOutOfMemoryError.printStackTrace();
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return localPageLoadInfoData;
    }
    finally {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\stat\PageLoadInfoData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */