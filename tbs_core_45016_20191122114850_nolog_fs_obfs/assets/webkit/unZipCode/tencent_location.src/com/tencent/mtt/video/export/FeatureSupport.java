package com.tencent.mtt.video.export;

public class FeatureSupport
{
  public static final long FT_FLAG_CLOSE_TO_DESTROY = 32768L;
  public static final long FT_FLAG_CONFIRM_IN_MOBILE_NET_WORK = 1024L;
  public static final long FT_FLAG_CONTENT_URL_SWTICH_SYSTEM_CODEC_PLAYER = 8192L;
  public static final long FT_FLAG_DOWNLOAD = 1L;
  public static final long FT_FLAG_EPISODE = 8L;
  public static final long FT_FLAG_FAVORITE = 2L;
  public static final long FT_FLAG_LITE_WND = 256L;
  public static final long FT_FLAG_LOCAL_FILE_LIST = 512L;
  public static final long FT_FLAG_QBLOGO = 16384L;
  public static final long FT_FLAG_RSTP_SWTICH_SYSTEM_CODEC_PLAYER = 4096L;
  public static final long FT_FLAG_SDK_STANDARD_FULL_SCREEN = 2048L;
  public static final long FT_FLAG_SHARE = 4L;
  public static final long FT_FLAG_SHOW_QB_LOG = 64L;
  public static final long FT_FLAG_SNIFF_ID = 32L;
  public static final long FT_FLAG_STAT = 16L;
  public static final long FT_FLAG_WITCH_FULLSCREEN_ONRESUME = 128L;
  protected long mFtFlags = 0L;
  
  public void addFeatureFlag(long paramLong)
  {
    this.mFtFlags = (paramLong | this.mFtFlags);
  }
  
  public void clearFeatrueFlag(long paramLong)
  {
    this.mFtFlags = ((paramLong ^ 0xFFFFFFFFFFFFFFFF) & this.mFtFlags);
  }
  
  public void setFeatureFlag(long paramLong)
  {
    this.mFtFlags = paramLong;
  }
  
  public final boolean support(long paramLong)
  {
    return (paramLong & this.mFtFlags) != 0L;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\export\FeatureSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */