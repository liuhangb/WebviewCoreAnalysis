package com.tencent.mtt.video.browser.export.data;

public class VideoRequestBoby
{
  public static final int DEFAULT_PAGESIZE = 45;
  public static final int REQ_DATA_NORMAL = 0;
  public static final int REQ_NO_DATA_WHEN_CHASE = 1;
  public static final int REQ_NO_DATA_WHEN_PLAY = 3;
  public static final int REQ_PULL_USER_DATA = 2;
  public int iSrc;
  public boolean isConfirmedNoMoreDownData = false;
  public int mDefaultReqCount;
  public String mDramaId;
  public int mFavortie = 0;
  public int mMaxSubId;
  public int mMaxVideoSubId;
  public long mPlaytime = 0L;
  public int mReqMode = 0;
  public int mReqReason = -1;
  public int mRequestSubId;
  public String mVideoFrom;
  public long mVideoId;
  public String mVideoTitle = "";
  public String mWebUrl;
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\data\VideoRequestBoby.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */