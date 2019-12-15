package com.tencent.smtt.export.internal.interfaces;

public abstract interface DownloadListenerExtension
{
  public abstract void onDownloadListenerStart(String paramString1, byte[] paramArrayOfByte, String paramString2, String paramString3);
  
  public abstract void onDownloadVideo(String paramString, long paramLong, int paramInt);
  
  public abstract void onSafeDownload(String paramString1, int paramInt1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, int paramInt2, int paramInt3, String paramString8, String paramString9, long paramLong);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\export\internal\interfaces\DownloadListenerExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */