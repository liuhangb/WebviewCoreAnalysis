package com.tencent.mtt.video.browser.export.data;

import android.database.Cursor;
import com.tencent.mtt.video.browser.export.db.IVideoDbHelper;
import java.util.ArrayList;

public abstract interface IVideoDataManager
{
  public abstract void chaseVideo(H5VideoEpisodeInfo paramH5VideoEpisodeInfo, IChaseVideoListener paramIChaseVideoListener);
  
  public abstract boolean chaseVideoFromWeb(IChaseVideoListener paramIChaseVideoListener, String paramString1, int paramInt1, int paramInt2, String paramString2, int paramInt3);
  
  public abstract boolean chaseVideoLocal(String paramString, int paramInt, IChaseVideoListener paramIChaseVideoListener);
  
  public abstract void checkVideoPush(ICheckVideoPushListener paramICheckVideoPushListener);
  
  public abstract void clearAllUpdateHint();
  
  public abstract void clearHistroy();
  
  public abstract void clearUpdateHint(String paramString);
  
  public abstract boolean createVideoInfoByIdAndSrcIfNeed(String paramString1, int paramInt1, int paramInt2, String paramString2, int paramInt3, boolean paramBoolean);
  
  public abstract H5VideoEpisodeInfo cursorToEpisodeInfo(Cursor paramCursor);
  
  public abstract void deleteChasedVideoBatch(ArrayList<H5VideoVisitInfo> paramArrayList, boolean paramBoolean);
  
  public abstract void deleteHistory(String paramString);
  
  public abstract void deleteHistory(ArrayList<String> paramArrayList);
  
  public abstract Cursor getCurEpisodesForDonwload(String paramString);
  
  public abstract H5VideoEpisodeInfo getCurrentEpisodeInfo(String paramString);
  
  public abstract String getDramaIdByTaskId(boolean paramBoolean, int paramInt);
  
  public abstract H5VideoDramaInfo getDramaInfo(String paramString);
  
  public abstract H5VideoEpisodeInfo getEpisodeInfo(int paramInt);
  
  public abstract H5VideoEpisodeInfo getEpisodeInfo(String paramString);
  
  public abstract H5VideoEpisodeInfo getEpisodeInfo(String paramString, int paramInt);
  
  public abstract Cursor getEpisodes(String paramString, int paramInt1, int paramInt2, boolean paramBoolean);
  
  public abstract Cursor getEpisodes(String paramString, int paramInt, boolean paramBoolean);
  
  public abstract Cursor getEpisodesForDonwload(String paramString, int paramInt1, int paramInt2, boolean paramBoolean);
  
  public abstract Cursor getEpisodesForDonwload(String paramString, int paramInt, boolean paramBoolean);
  
  public abstract Cursor getFavoriteVideos();
  
  public abstract H5VideoEpisodeInfo getFirstItemInfo(Cursor paramCursor);
  
  public abstract H5VideoHistoryInfo getHistoryByVideoId(String paramString);
  
  public abstract Cursor getHistoryVideosByTime();
  
  public abstract H5VideoEpisodeInfo getNewestEpisode(String paramString, int paramInt);
  
  public abstract int getReqSubId(long paramLong, int paramInt);
  
  public abstract IVideoDbHelper getVideoDbHelper();
  
  public abstract H5VideoVisitInfo getVideoVisitInfo(String paramString);
  
  public abstract boolean hasVideoUpdated();
  
  public abstract boolean isFavoriteVideo(String paramString);
  
  public abstract void notifyVideoDataChanged(boolean paramBoolean);
  
  public abstract void onPushMsgArrived(Object paramObject);
  
  public abstract void refreshEpisodeDownloadTask();
  
  public abstract void removeVideoUpdateObserver();
  
  public abstract void requestEpisodes(IVideoDataListener paramIVideoDataListener, boolean paramBoolean);
  
  public abstract void requestPageDataByVideoAndSrc(IVideoDataListener paramIVideoDataListener, long paramLong, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void setVideoUpdateObserver(IVideoUpdateObserver paramIVideoUpdateObserver);
  
  public abstract void setWupResolver(IVideoWupResolver paramIVideoWupResolver);
  
  public abstract boolean shouldForceNetWorkReq(String paramString, H5VideoEpisodeInfo paramH5VideoEpisodeInfo);
  
  public abstract void syncDownloadTaskStatus(int paramInt1, int paramInt2);
  
  public abstract void syncDownloadTaskStatus(ArrayList<Integer> paramArrayList);
  
  public abstract void syncFavoriteVideoes();
  
  public abstract void updateEpisodeInfo(ArrayList<H5VideoEpisodeInfo> paramArrayList);
  
  public abstract void updateEpisodeToCurrent(H5VideoEpisodeInfo paramH5VideoEpisodeInfo, boolean paramBoolean);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\data\IVideoDataManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */