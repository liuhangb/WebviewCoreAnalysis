package com.tencent.mtt.video.browser.export.db;

import android.database.Cursor;
import android.text.TextUtils;
import com.tencent.mtt.video.browser.export.data.H5VideoDramaInfo;
import com.tencent.mtt.video.browser.export.data.H5VideoEpisodeInfo;
import com.tencent.mtt.video.browser.export.data.H5VideoHistoryInfo;
import com.tencent.mtt.video.browser.export.data.H5VideoVisitInfo;
import java.io.UnsupportedEncodingException;

public class VideoDbUtils
{
  public static String createDramaId(long paramLong, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramLong);
    localStringBuilder.append("-");
    localStringBuilder.append(paramInt);
    return localStringBuilder.toString();
  }
  
  public static String createEpisodeId(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getHashUUID(sqliteEscape(paramString)));
    localStringBuilder.append("");
    return localStringBuilder.toString();
  }
  
  public static H5VideoDramaInfo cursorToDramaInfo(Cursor paramCursor)
  {
    H5VideoDramaInfo localH5VideoDramaInfo = new H5VideoDramaInfo();
    int i = paramCursor.getColumnIndex("current_sub_id");
    if (i != -1) {
      localH5VideoDramaInfo.mCurrentSubId = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("drama_id");
    if (i != -1) {
      localH5VideoDramaInfo.mDramaId = paramCursor.getString(i);
    }
    i = paramCursor.getColumnIndex("drama_name");
    if (i != -1) {
      localH5VideoDramaInfo.mDramaName = paramCursor.getString(i);
    }
    i = paramCursor.getColumnIndex("visit_time");
    if (i != -1) {
      localH5VideoDramaInfo.mVisitTime = paramCursor.getLong(i);
    }
    i = paramCursor.getColumnIndex("list_item_type");
    if (i != -1) {
      localH5VideoDramaInfo.mListItemShowType = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("total_count");
    if (i != -1) {
      localH5VideoDramaInfo.mTotalCount = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("max_sub_id");
    if (i != -1) {
      localH5VideoDramaInfo.mMaxSubId = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("max_sub_set_num");
    if (i != -1) {
      localH5VideoDramaInfo.mMaxSetNum = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("has_phone_url");
    if (i != -1) {
      localH5VideoDramaInfo.mHasPhoneUrl = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("drama_type");
    if (i != -1) {
      localH5VideoDramaInfo.mDramaType = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("drama_favorites");
    if (i != -1) {
      localH5VideoDramaInfo.mFavorite = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("updated_drama_id");
    if (i != -1) {
      localH5VideoDramaInfo.mUpdated = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("video_id");
    if (i != -1) {
      localH5VideoDramaInfo.mVideoId = paramCursor.getString(i);
    }
    return localH5VideoDramaInfo;
  }
  
  public static H5VideoEpisodeInfo cursorToEpisodeInfo(Cursor paramCursor)
  {
    H5VideoEpisodeInfo localH5VideoEpisodeInfo = new H5VideoEpisodeInfo();
    int i = paramCursor.getColumnIndex("video_episode_id");
    if (i != -1) {
      localH5VideoEpisodeInfo.mEpisodeId = paramCursor.getString(i);
    }
    i = paramCursor.getColumnIndex("video_src_url");
    if (i != -1) {
      localH5VideoEpisodeInfo.mVideoUrl = paramCursor.getString(i);
    }
    i = paramCursor.getColumnIndex("video_web_url");
    if (i != -1) {
      localH5VideoEpisodeInfo.mWebUrl = paramCursor.getString(i);
    }
    i = paramCursor.getColumnIndex("video_title");
    if (i != -1) {
      localH5VideoEpisodeInfo.mTitle = paramCursor.getString(i);
    }
    i = paramCursor.getColumnIndex("video_description");
    if (i != -1) {
      localH5VideoEpisodeInfo.mSetNum = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("drama_id");
    if (i != -1) {
      localH5VideoEpisodeInfo.mDramaId = paramCursor.getString(i);
    }
    i = paramCursor.getColumnIndex("drama_name");
    if (i != -1) {
      localH5VideoEpisodeInfo.mDramaName = paramCursor.getString(i);
    }
    i = paramCursor.getColumnIndex("video_sub_id");
    if (i != -1) {
      localH5VideoEpisodeInfo.mSubId = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("video_episode_page_no");
    if (i != -1) {
      localH5VideoEpisodeInfo.mEpisodePageNo = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("video_from");
    if (i != -1) {
      localH5VideoEpisodeInfo.mVideoFrom = paramCursor.getString(i);
    }
    i = paramCursor.getColumnIndex("list_item_type");
    if (i != -1) {
      localH5VideoEpisodeInfo.mListItemShowType = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("total_count");
    if (i != -1) {
      localH5VideoEpisodeInfo.mTotalCount = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("max_sub_id");
    if (i != -1) {
      localH5VideoEpisodeInfo.mMaxSubId = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("max_sub_set_num");
    if (i != -1) {
      localH5VideoEpisodeInfo.mMaxSetNum = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("has_phone_url");
    if (i != -1) {
      localH5VideoEpisodeInfo.mHasPhoneUrl = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("current_sub_id");
    if (i != -1)
    {
      localH5VideoEpisodeInfo.mCurrentSubId = paramCursor.getInt(i);
      boolean bool;
      if (localH5VideoEpisodeInfo.mCurrentSubId == localH5VideoEpisodeInfo.mSubId) {
        bool = true;
      } else {
        bool = false;
      }
      localH5VideoEpisodeInfo.mIsCurrentEpisode = bool;
    }
    i = paramCursor.getColumnIndex("drama_type");
    if (i != -1) {
      localH5VideoEpisodeInfo.mDramaType = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("drama_favorites");
    if (i != -1) {
      localH5VideoEpisodeInfo.mFavorite = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("played_time");
    if (i != -1) {
      localH5VideoEpisodeInfo.mPlayedTime = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("total_time");
    if (i != -1) {
      localH5VideoEpisodeInfo.mTotalTime = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("task_id");
    if (i != -1) {
      localH5VideoEpisodeInfo.mTaskId = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("status");
    if ((localH5VideoEpisodeInfo.mTaskId != 0) && (i != -1)) {
      localH5VideoEpisodeInfo.mTaskStatus = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("video_id");
    if (i != -1) {
      localH5VideoEpisodeInfo.mVideoId = paramCursor.getString(i);
    }
    i = paramCursor.getColumnIndex("video_max_id");
    if (i != -1) {
      localH5VideoEpisodeInfo.mMaxVideoSubId = paramCursor.getInt(i);
    }
    return localH5VideoEpisodeInfo;
  }
  
  public static H5VideoHistoryInfo cursorToVideoHistoryInfo(Cursor paramCursor)
  {
    H5VideoHistoryInfo localH5VideoHistoryInfo = new H5VideoHistoryInfo();
    localH5VideoHistoryInfo.mCurrentEpisodeInfo = cursorToEpisodeInfo(paramCursor);
    localH5VideoHistoryInfo.mDramaInfo = cursorToDramaInfo(paramCursor);
    localH5VideoHistoryInfo.mVisiInfo = cursorToVideoVisitInfo(paramCursor);
    return localH5VideoHistoryInfo;
  }
  
  public static H5VideoVisitInfo cursorToVideoVisitInfo(Cursor paramCursor)
  {
    H5VideoVisitInfo localH5VideoVisitInfo = new H5VideoVisitInfo();
    localH5VideoVisitInfo.mCurrentDramaInfo = cursorToDramaInfo(paramCursor);
    int i = paramCursor.getColumnIndex("video_id");
    if (i != -1) {
      localH5VideoVisitInfo.mVideoId = paramCursor.getString(i);
    }
    i = paramCursor.getColumnIndex("visit_time");
    if (i != -1) {
      localH5VideoVisitInfo.mVisitTime = paramCursor.getLong(i);
    }
    i = paramCursor.getColumnIndex("drama_favorites");
    if (i != -1) {
      localH5VideoVisitInfo.mFavorite = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("current_drama_id");
    if (i != -1) {
      localH5VideoVisitInfo.mCurrentDramaId = paramCursor.getString(i);
    }
    i = paramCursor.getColumnIndex("updated_drama_id");
    if (i != -1) {
      localH5VideoVisitInfo.mUpdatedDramaId = paramCursor.getString(i);
    }
    i = paramCursor.getColumnIndex("video_max_id");
    if (i != -1) {
      localH5VideoVisitInfo.mMaxVideoSubId = paramCursor.getInt(i);
    }
    i = paramCursor.getColumnIndex("drama_cover_id");
    if (i != -1) {
      localH5VideoVisitInfo.mDramaCoverUrl = paramCursor.getString(i);
    }
    i = paramCursor.getColumnIndex("drama_hd");
    if (i != -1) {
      localH5VideoVisitInfo.mDramaHD = paramCursor.getInt(i);
    }
    return localH5VideoVisitInfo;
  }
  
  public static String generateCreateTabSql(String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("CREATE TABLE ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(" ( ");
    int i = 0;
    while (i < paramArrayOfString1.length)
    {
      localStringBuilder.append(paramArrayOfString1[i]);
      localStringBuilder.append(paramArrayOfString2[i]);
      i += 1;
    }
    localStringBuilder.append(");");
    return localStringBuilder.toString();
  }
  
  public static String getCreateDramaTableSql()
  {
    return generateCreateTabSql(getDramaTabelColumns(), getDramaTabelColumnsType(), "video_drama");
  }
  
  public static String getCreateDramaVisitTableSql()
  {
    return generateCreateTabSql(getDramaVisitTableColumns(), getDramaVisitTableColumnsType(), "video_drama_visit");
  }
  
  public static String getCreateEpsiodeTableSql()
  {
    return generateCreateTabSql(getEpisodeTabelColumns(), getEpisodeTabelColumnsType(), "video_episode");
  }
  
  public static String[] getDramaTabelColumns()
  {
    return new String[] { "_id", "video_id", "drama_id", "drama_name", "total_count", "max_sub_id", "current_sub_id", "video_from", "list_item_type", "drama_type", "max_sub_set_num", "has_phone_url" };
  }
  
  public static String[] getDramaTabelColumnsType()
  {
    return new String[] { " INTEGER PRIMARY KEY autoincrement, ", " TEXT ,", " TEXT, ", " TEXT, ", " INTEGER DEFAULT 0, ", " INTEGER DEFAULT 0, ", " INTEGER, ", " TEXT ,", " INTEGER, ", " INTEGER ,", " INTEGER DEFAULT 0, ", " INTEGER DEFAULT 0" };
  }
  
  public static String[] getDramaVisitTableColumns()
  {
    return new String[] { "_id", "video_id", "visit_time", "drama_favorites", "current_drama_id", "updated_drama_id", "drama_cover_id", "drama_hd", "favorite_time" };
  }
  
  public static String[] getDramaVisitTableColumnsType()
  {
    return new String[] { " INTEGER PRIMARY KEY autoincrement, ", " TEXT, ", " LONG DEFAULT 0, ", " INTEGER DEFAULT 0 ,", " TEXT ,", " TEXT DEFAULT '',", " TEXT, ", " INTEGER DEFAULT 0, ", " LONG DEFAULT 0" };
  }
  
  public static String getDropDramaTabelSql()
  {
    return "DROP TABLE video_drama";
  }
  
  public static String getDropEpisodeTabelSql()
  {
    return "DROP TABLE video_episode";
  }
  
  public static String getDropVisitTabelSql()
  {
    return "DROP TABLE video_drama_visit";
  }
  
  public static String[] getEpisodeTabelColumns()
  {
    return new String[] { "_id", "video_episode_id", "video_src_url", "video_web_url", "video_title", "video_description", "drama_id", "video_sub_id", "video_episode_page_no", "played_time", "total_time", "task_id" };
  }
  
  public static String[] getEpisodeTabelColumnsType()
  {
    return new String[] { " INTEGER PRIMARY KEY autoincrement, ", " TEXT, ", " TEXT, ", " TEXT, ", " TEXT, ", " TEXT, ", " TEXT, ", " INTEGER DEFAULT 0, ", " INTEGER DEFAULT 0, ", " INTEGER DEFAULT 0, ", " INTEGER DEFAULT 0, ", " INTEGER DEFAULT 0" };
  }
  
  public static H5VideoEpisodeInfo getFirstItemInfo(Cursor paramCursor)
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    if (paramCursor != null) {
      localObject1 = localObject2;
    }
    try
    {
      if (paramCursor.moveToFirst()) {
        localObject1 = cursorToEpisodeInfo(paramCursor);
      }
      return (H5VideoEpisodeInfo)localObject1;
    }
    catch (Exception paramCursor) {}
    return null;
  }
  
  public static int getHashUUID(String paramString)
  {
    try
    {
      byte[] arrayOfByte = paramString.getBytes("UTF-8");
      paramString = arrayOfByte;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      int k;
      int i;
      int j;
      for (;;) {}
    }
    paramString = paramString.getBytes();
    k = paramString.length;
    i = 0;
    j = 0;
    while (i < k)
    {
      j += paramString[i];
      j += (j << 10);
      j ^= j >>> 6;
      i += 1;
    }
    i = j + (j << 3);
    i = i >>> 11 ^ i;
    i += (i << 15);
    if (i == 0) {
      return 1;
    }
    return Math.abs(i);
  }
  
  public static H5VideoEpisodeInfo getLastItemInfo(Cursor paramCursor)
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    if (paramCursor != null) {
      localObject1 = localObject2;
    }
    try
    {
      if (!paramCursor.isClosed())
      {
        localObject1 = localObject2;
        if (paramCursor.moveToLast()) {
          localObject1 = cursorToEpisodeInfo(paramCursor);
        }
      }
      return (H5VideoEpisodeInfo)localObject1;
    }
    catch (Exception paramCursor) {}
    return null;
  }
  
  public static int getSrcFromDramaId(String paramString)
  {
    boolean bool = TextUtils.isEmpty(paramString);
    int i = 0;
    if (bool) {
      return 0;
    }
    int j = paramString.lastIndexOf("-");
    if (j > 0) {
      paramString = paramString.substring(j + 1, paramString.length());
    }
    try
    {
      i = Integer.parseInt(paramString);
      return i;
    }
    catch (Exception paramString) {}
    return 0;
  }
  
  public static long getVideoId(String paramString)
  {
    boolean bool = TextUtils.isEmpty(paramString);
    long l = 0L;
    if (bool) {
      return 0L;
    }
    int i = paramString.indexOf("-");
    if (i > 0) {
      paramString = paramString.substring(0, i);
    }
    try
    {
      l = parseLong(paramString, -1L);
      return l;
    }
    catch (Exception paramString) {}
    return 0L;
  }
  
  public static long parseLong(String paramString, long paramLong)
  {
    try
    {
      long l = Long.parseLong(paramString);
      return l;
    }
    catch (Exception paramString) {}
    return paramLong;
  }
  
  public static String sqliteEscape(String paramString)
  {
    String str = paramString;
    if (!TextUtils.isEmpty(paramString)) {
      str = paramString.replace("'", "''");
    }
    return str;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\db\VideoDbUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */