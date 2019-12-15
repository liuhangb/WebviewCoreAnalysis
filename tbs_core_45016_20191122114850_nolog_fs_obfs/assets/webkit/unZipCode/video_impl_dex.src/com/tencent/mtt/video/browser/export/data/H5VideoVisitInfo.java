package com.tencent.mtt.video.browser.export.data;

public class H5VideoVisitInfo
{
  public static final int FAVORITE_ADDED = 1;
  public static final int FAVORITE_ADDING = 2;
  public static final int FAVORITE_DELETED = 0;
  public static final int FAVORITE_DELETING = -2;
  public static final int VIDEO_SRC_FROM_QVOD = 14;
  public static final int VIDEO_TYPE_DIANSHIJU = 2;
  public static final int VIDEO_TYPE_DONGMAN = 4;
  public static final int VIDEO_TYPE_MOVE = 1;
  public static final int VIDEO_TYPE_ZONGYI = 3;
  public long mAddToFavoriteTime = -1L;
  public String mCurrentDramaId = null;
  public H5VideoDramaInfo mCurrentDramaInfo = new H5VideoDramaInfo();
  public String mDramaCoverUrl = null;
  public int mDramaHD = -1;
  public int mFavorite = -1;
  public int mMaxVideoSubId;
  public String mUpdatedDramaId = null;
  public String mVideoId = null;
  public long mVisitTime = 0L;
  
  public boolean isFavorite()
  {
    return this.mFavorite >= 1;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\data\H5VideoVisitInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */