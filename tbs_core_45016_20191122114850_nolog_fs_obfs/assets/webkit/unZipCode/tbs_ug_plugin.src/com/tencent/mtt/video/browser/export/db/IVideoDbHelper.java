package com.tencent.mtt.video.browser.export.db;

import com.tencent.mtt.video.browser.export.data.H5VideoDramaInfo;
import com.tencent.mtt.video.browser.export.data.H5VideoEpisodeInfo;
import com.tencent.mtt.video.browser.export.data.H5VideoHistoryInfo;
import com.tencent.mtt.video.browser.export.data.H5VideoVisitInfo;
import java.util.ArrayList;

public abstract interface IVideoDbHelper
{
  public static final String COLUMN_BASE_ID = "_id";
  public static final String COLUMN_CURRENT_DRAMA_ID = "current_drama_id";
  public static final String COLUMN_CURRENT_SUB_ID = "current_sub_id";
  public static final String COLUMN_DESCRIPTION = "video_description";
  public static final String COLUMN_DOWNLOAD_TASK_ID = "task_id";
  public static final String COLUMN_DRAMA_COVER_URL = "drama_cover_id";
  public static final String COLUMN_DRAMA_FAVORITES = "drama_favorites";
  public static final String COLUMN_DRAMA_HD = "drama_hd";
  public static final String COLUMN_DRAMA_ID = "drama_id";
  public static final String COLUMN_DRAMA_NAME = "drama_name";
  public static final String COLUMN_DRAMA_TYPE = "drama_type";
  public static final String COLUMN_EPISODE_ID = "video_episode_id";
  public static final String COLUMN_EPISODE_PAGE_NO = "video_episode_page_no";
  public static final String COLUMN_FAVORITE_TIME = "favorite_time";
  public static final String COLUMN_HAS_PHONE_URL = "has_phone_url";
  public static final String COLUMN_LIST_ITEM_TYPE = "list_item_type";
  public static final String COLUMN_MAX_SET_NUM = "max_sub_set_num";
  public static final String COLUMN_MAX_SUB_ID = "max_sub_id";
  public static final String COLUMN_PLAYED_TIME = "played_time";
  public static final String COLUMN_SRC_URL = "video_src_url";
  public static final String COLUMN_TITLE = "video_title";
  public static final String COLUMN_TOTAL_TIME = "total_time";
  public static final String COLUMN_UPDATED_DRAMA_ID = "updated_drama_id";
  public static final String COLUMN_VIDEO_FROM = "video_from";
  public static final String COLUMN_VIDEO_ID = "video_id";
  public static final String COLUMN_VIDEO_SUB_ID = "video_sub_id";
  public static final String COLUMN_VIDEO_TOTAL_COUNT = "total_count";
  public static final String COLUMN_VISIT_TIME = "visit_time";
  public static final String COLUMN_WEB_URL = "video_web_url";
  public static final int MAX_EPISODE_COUNT = 100;
  public static final String STATUS = "status";
  public static final String TABLE_DRAMA_NAME = "video_drama";
  public static final String TABLE_DRAMA_VISIT = "video_drama_visit";
  public static final String TABLE_EPISODE_NAME = "video_episode";
  public static final String TMP_COLUMN_VIDEO_MAX_ID = "video_max_id";
  public static final String VIDEO_CENTER_SEARCH_URL = "http://v.html5.qq.com/#p=innerSearch";
  public static final String VIDEO_CENTER_URL = "http://v.html5.qq.com/?ch=003001#p=index&g=1";
  public static final String VIDEO_CENTER_URL_FAVORITE = "http://v.html5.qq.com/?ch=003003";
  public static final String VIDEO_CENTER_URL_HISTORY = "http://v.html5.qq.com/?ch=003002";
  
  public abstract void beginTransaction(String paramString)
    throws Exception;
  
  public abstract void clearVideoUpdatedHint(String paramString);
  
  public abstract void deleteDramaInfo(String paramString);
  
  public abstract void deletePageData(int paramInt, String paramString);
  
  public abstract void endTransaction(String paramString)
    throws Exception;
  
  public abstract void endTransactionOnly();
  
  public abstract H5VideoDramaInfo getDramaInfo(String paramString);
  
  public abstract ArrayList<H5VideoHistoryInfo> getUnSyncFavoriteVideoes();
  
  public abstract H5VideoVisitInfo getVideoVisitInfo(String paramString);
  
  public abstract boolean insertDramInfo(H5VideoDramaInfo paramH5VideoDramaInfo);
  
  public abstract void insertEpisodeInfo(H5VideoEpisodeInfo paramH5VideoEpisodeInfo);
  
  public abstract boolean insertVideoVisitInfo(H5VideoVisitInfo paramH5VideoVisitInfo);
  
  public abstract boolean isDramaExists(String paramString);
  
  public abstract void shrinkOldEpisdoes();
  
  public abstract void updateDramaId(String paramString1, String paramString2);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\db\IVideoDbHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */