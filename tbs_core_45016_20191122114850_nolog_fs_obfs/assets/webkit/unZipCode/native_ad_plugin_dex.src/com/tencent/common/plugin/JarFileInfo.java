package com.tencent.common.plugin;

import android.content.pm.Signature;
import android.graphics.drawable.BitmapDrawable;

public class JarFileInfo
{
  public BitmapDrawable mAppIcon;
  public String mAppName = null;
  public boolean mBrokenJarFile = false;
  public String mCanonicalPath;
  public String mContextMenuText = null;
  public String mFileName;
  public long mFileSize;
  public boolean mHasParsed = false;
  public long mLastModified;
  public String mLaunchMode = null;
  public String mPackageName;
  public Signature mSignature;
  public String mSoName = null;
  public String mSortKey = null;
  public int mState = -1;
  public String mSupportResType = null;
  public int mVersionCode;
  public String mVersionName;
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\JarFileInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */