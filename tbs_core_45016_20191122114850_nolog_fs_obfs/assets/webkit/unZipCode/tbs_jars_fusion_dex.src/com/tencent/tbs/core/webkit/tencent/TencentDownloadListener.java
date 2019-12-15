package com.tencent.tbs.core.webkit.tencent;

public class TencentDownloadListener
  implements com.tencent.smtt.export.external.interfaces.DownloadListener, com.tencent.tbs.core.webkit.DownloadListener
{
  private com.tencent.smtt.export.external.interfaces.DownloadListener mDownloadListener;
  
  public TencentDownloadListener(com.tencent.smtt.export.external.interfaces.DownloadListener paramDownloadListener)
  {
    this.mDownloadListener = paramDownloadListener;
  }
  
  public void onDownloadStart(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong)
  {
    this.mDownloadListener.onDownloadStart(paramString1, paramString2, paramString3, paramString4, paramLong);
  }
  
  public void onDownloadStart(String paramString1, String paramString2, byte[] paramArrayOfByte, String paramString3, String paramString4, String paramString5, long paramLong, String paramString6, String paramString7)
  {
    this.mDownloadListener.onDownloadStart(paramString1, paramString2, paramArrayOfByte, paramString3, paramString4, paramString5, paramLong, paramString6, paramString7);
  }
  
  public void onDownloadVideo(String paramString, long paramLong, int paramInt)
  {
    this.mDownloadListener.onDownloadVideo(paramString, paramLong, paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\tencent\TencentDownloadListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */