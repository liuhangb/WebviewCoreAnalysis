package com.tencent.common.plugin;

import android.graphics.drawable.BitmapDrawable;

public class QBPluginInfo
{
  public String mActivityName = "";
  public String mContextMenuText = null;
  public BitmapDrawable mIcon = null;
  public String mIconUrl = "";
  public boolean mIsIgnoreUpdate = false;
  public String mLaunchMode = null;
  public String mLocalPath = "";
  public String mLocalZipPath = "";
  public String mName = "";
  public boolean mNeedUnzipFromBack = false;
  public String mPackageName = "";
  public int mPositionIndex = 0;
  public String mSoName = null;
  public String mSupportResType = null;
  public int mUpdateType = 0;
  public String mUrl = "";
  public int mVersionCode = 0;
  public String mVersionName = "";
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = false;
    if (paramObject != null)
    {
      paramObject = (QBPluginInfo)paramObject;
      boolean bool1 = bool2;
      if (((QBPluginInfo)paramObject).mPackageName.equals(this.mPackageName))
      {
        bool1 = bool2;
        if (((QBPluginInfo)paramObject).mName.equals(this.mName)) {
          bool1 = true;
        }
      }
      return bool1;
    }
    return false;
  }
  
  public int hashCode()
  {
    return (629 + this.mPackageName.hashCode()) * 37 + this.mName.hashCode();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\QBPluginInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */