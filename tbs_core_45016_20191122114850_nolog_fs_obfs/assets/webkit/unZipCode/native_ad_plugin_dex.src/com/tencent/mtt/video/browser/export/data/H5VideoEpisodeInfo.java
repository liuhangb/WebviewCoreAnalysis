package com.tencent.mtt.video.browser.export.data;

import android.os.Bundle;

public class H5VideoEpisodeInfo
{
  public static final String KEY_DEFAULT_FULLSCREEN_MODE = "defaultFullscreenMode";
  public static final String KEY_FRAME_MODE = "frameMode";
  public static final String KEY_HAS_PROXY = "hasProxy";
  public static final String KEY_SCREEN_MODE = "screenMode";
  public static final String KEY_SNIFF_ID = "sniffId";
  public static final String KEY_SWITCH_HD = "switchHD";
  public static final int POSITION_FROM_BEGINING = 0;
  public static final int POSITION_INVALID = -1;
  public static final int TYPE_GROUP = 1;
  public static final int TYPE_VIDEO = 2;
  public static final int VIDEO_DOWNLOADED = 1;
  public static final int VIDEO_IS_DOWNLOADING = 2;
  public static final int VIDEO_NOT_DOWNLOADED = 0;
  public int mClarity = 0;
  public long mCreateTime;
  public int mCurrentSubId = 0;
  public int mDownloadStatus;
  public String mDramaId;
  public String mDramaName = "";
  public int mDramaType = 0;
  public String mEpisodeId;
  public int mEpisodePageNo = 0;
  public Bundle mExtraData = null;
  public int mFavorite = 0;
  public int mFileSize = 0;
  public int mHasPhoneUrl = -1;
  public boolean mIsCurrentEpisode = false;
  public int mListItemShowType;
  public int mMaxSetNum = 0;
  public int mMaxSubId = 0;
  public int mMaxVideoSubId = 0;
  public int mPlayedTime = -1;
  public String mPosterUrl;
  public int mSetNum;
  public int mSubId = 0;
  public int mTaskId = 0;
  public int mTaskStatus = -1;
  public String mTitle;
  public int mTotalCount;
  public int mTotalTime = 0;
  public int mVideoDuration = 0;
  public String mVideoFrom;
  public String mVideoId = null;
  public String mVideoUrl;
  public String mWebUrl;
  
  public boolean hasEpisodes()
  {
    return (this.mDramaType == 1) && (this.mListItemShowType != 0);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\data\H5VideoEpisodeInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */