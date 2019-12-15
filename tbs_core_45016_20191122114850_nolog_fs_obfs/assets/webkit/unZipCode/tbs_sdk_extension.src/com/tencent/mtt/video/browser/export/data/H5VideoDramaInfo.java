package com.tencent.mtt.video.browser.export.data;

public class H5VideoDramaInfo
{
  public int mCurrentSubId = 0;
  public String mDramaId = null;
  public String mDramaName = null;
  public int mDramaType = 0;
  public int mFavorite = -1;
  public int mHasPhoneUrl = -1;
  public int mListItemShowType = -1;
  public int mMaxSetNum;
  public int mMaxSubId = 0;
  public int mTotalCount;
  public int mUpdated = -1;
  public String mVideoFrom = null;
  public String mVideoId = null;
  public long mVisitTime = 0L;
  
  public boolean hasEpisodes()
  {
    return (this.mDramaType == 1) && (this.mListItemShowType != 0);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\data\H5VideoDramaInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */