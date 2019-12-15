package com.tencent.mtt.video.export;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class H5VideoInfo
  implements Parcelable
{
  public static final Parcelable.Creator<H5VideoInfo> CREATOR = new Parcelable.Creator()
  {
    public H5VideoInfo createFromParcel(Parcel paramAnonymousParcel)
    {
      return new H5VideoInfo(paramAnonymousParcel);
    }
    
    public H5VideoInfo[] newArray(int paramAnonymousInt)
    {
      return new H5VideoInfo[paramAnonymousInt];
    }
  };
  public static final int VIDEO_FROM_H5 = 1;
  public static final int VIDEO_FROM_MTT = 2;
  public static final int VIDEO_FROM_NATIVE = 3;
  public static final int VIDEO_FROM_NONE = 0;
  public boolean mAutoPlayVideo;
  public long mCacheFilePos;
  public int mCurrentClassifyId;
  public Long mCurrentLiveID;
  public int mDuration;
  public Bundle mExtraData;
  public int mFromWhere;
  public boolean mHasClicked;
  public boolean mIsForceSniff;
  public int mLiteWndHeight;
  public int mLiteWndPosX;
  public int mLiteWndPosY;
  public int mLiteWndWidth;
  public boolean mLivePlay;
  public String mMimeType;
  public int mNodePointer;
  public int mPostion;
  public boolean mPrivateBrowsingEnabled;
  public int mScreenMode;
  public String mSnifferReffer;
  public String mUA;
  public String mVideoCachePath;
  public int mVideoId;
  public String mVideoUrl;
  public String mWebTitle;
  public String mWebUrl;
  
  public H5VideoInfo()
  {
    this.mDuration = 0;
    this.mPostion = 0;
    this.mPrivateBrowsingEnabled = false;
    this.mScreenMode = 100;
    this.mVideoId = -1;
    this.mFromWhere = 0;
    this.mIsForceSniff = false;
    this.mHasClicked = false;
    this.mCurrentClassifyId = -1;
    this.mCurrentLiveID = Long.valueOf(-1L);
    this.mLivePlay = false;
    this.mAutoPlayVideo = false;
    this.mCacheFilePos = -1L;
    this.mVideoCachePath = null;
    this.mLiteWndPosX = 0;
    this.mLiteWndPosY = 0;
    this.mLiteWndWidth = 0;
    this.mLiteWndHeight = 0;
    this.mExtraData = new Bundle();
  }
  
  public H5VideoInfo(Parcel paramParcel)
  {
    boolean bool2 = false;
    this.mDuration = 0;
    this.mPostion = 0;
    this.mPrivateBrowsingEnabled = false;
    this.mScreenMode = 100;
    this.mVideoId = -1;
    this.mFromWhere = 0;
    this.mIsForceSniff = false;
    this.mHasClicked = false;
    this.mCurrentClassifyId = -1;
    this.mCurrentLiveID = Long.valueOf(-1L);
    this.mLivePlay = false;
    this.mAutoPlayVideo = false;
    this.mCacheFilePos = -1L;
    this.mVideoCachePath = null;
    this.mLiteWndPosX = 0;
    this.mLiteWndPosY = 0;
    this.mLiteWndWidth = 0;
    this.mLiteWndHeight = 0;
    this.mExtraData = new Bundle();
    this.mNodePointer = paramParcel.readInt();
    this.mDuration = paramParcel.readInt();
    this.mPostion = paramParcel.readInt();
    this.mScreenMode = paramParcel.readInt();
    this.mVideoId = paramParcel.readInt();
    this.mMimeType = paramParcel.readString();
    this.mWebUrl = paramParcel.readString();
    this.mVideoUrl = paramParcel.readString();
    this.mWebTitle = paramParcel.readString();
    this.mUA = paramParcel.readString();
    this.mSnifferReffer = paramParcel.readString();
    if (paramParcel.readInt() == 1) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    this.mPrivateBrowsingEnabled = bool1;
    this.mFromWhere = paramParcel.readInt();
    if (paramParcel.readInt() == 1) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    this.mIsForceSniff = bool1;
    if (paramParcel.readInt() == 1) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    this.mHasClicked = bool1;
    boolean bool1 = bool2;
    if (paramParcel.readInt() == 1) {
      bool1 = true;
    }
    this.mAutoPlayVideo = bool1;
    this.mCacheFilePos = paramParcel.readLong();
    this.mVideoCachePath = paramParcel.readString();
    this.mLiteWndPosX = paramParcel.readInt();
    this.mLiteWndPosY = paramParcel.readInt();
    this.mLiteWndWidth = paramParcel.readInt();
    this.mLiteWndHeight = paramParcel.readInt();
    this.mExtraData = paramParcel.readBundle();
  }
  
  public static H5VideoInfo copy(H5VideoInfo paramH5VideoInfo)
  {
    H5VideoInfo localH5VideoInfo = new H5VideoInfo();
    if (paramH5VideoInfo == null) {
      return localH5VideoInfo;
    }
    localH5VideoInfo.mNodePointer = paramH5VideoInfo.mNodePointer;
    localH5VideoInfo.mWebUrl = paramH5VideoInfo.mWebUrl;
    localH5VideoInfo.mVideoUrl = paramH5VideoInfo.mVideoUrl;
    localH5VideoInfo.mWebTitle = paramH5VideoInfo.mWebTitle;
    localH5VideoInfo.mDuration = paramH5VideoInfo.mDuration;
    localH5VideoInfo.mPostion = paramH5VideoInfo.mPostion;
    localH5VideoInfo.mUA = paramH5VideoInfo.mUA;
    localH5VideoInfo.mPrivateBrowsingEnabled = paramH5VideoInfo.mPrivateBrowsingEnabled;
    localH5VideoInfo.mScreenMode = paramH5VideoInfo.mScreenMode;
    localH5VideoInfo.mVideoId = paramH5VideoInfo.mVideoId;
    localH5VideoInfo.mSnifferReffer = paramH5VideoInfo.mSnifferReffer;
    localH5VideoInfo.mMimeType = paramH5VideoInfo.mMimeType;
    localH5VideoInfo.mFromWhere = paramH5VideoInfo.mFromWhere;
    localH5VideoInfo.mIsForceSniff = paramH5VideoInfo.mIsForceSniff;
    localH5VideoInfo.mHasClicked = paramH5VideoInfo.mHasClicked;
    localH5VideoInfo.mAutoPlayVideo = paramH5VideoInfo.mAutoPlayVideo;
    localH5VideoInfo.mCurrentClassifyId = paramH5VideoInfo.mCurrentClassifyId;
    localH5VideoInfo.mCurrentLiveID = paramH5VideoInfo.mCurrentLiveID;
    localH5VideoInfo.mLivePlay = paramH5VideoInfo.mLivePlay;
    localH5VideoInfo.mCacheFilePos = paramH5VideoInfo.mCacheFilePos;
    localH5VideoInfo.mVideoCachePath = paramH5VideoInfo.mVideoCachePath;
    localH5VideoInfo.mLiteWndPosX = paramH5VideoInfo.mLiteWndPosX;
    localH5VideoInfo.mLiteWndPosY = paramH5VideoInfo.mLiteWndPosY;
    localH5VideoInfo.mLiteWndWidth = paramH5VideoInfo.mLiteWndWidth;
    localH5VideoInfo.mLiteWndHeight = paramH5VideoInfo.mLiteWndHeight;
    localH5VideoInfo.mExtraData = paramH5VideoInfo.mExtraData;
    return localH5VideoInfo;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\export\H5VideoInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */