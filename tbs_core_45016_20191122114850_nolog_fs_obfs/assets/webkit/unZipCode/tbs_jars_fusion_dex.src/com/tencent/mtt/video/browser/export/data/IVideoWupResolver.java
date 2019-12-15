package com.tencent.mtt.video.browser.export.data;

public abstract interface IVideoWupResolver
{
  public abstract String getLiveName(int paramInt, Long paramLong);
  
  public abstract void requestVideoPageListNew(String paramString, IVideoDataListener paramIVideoDataListener);
  
  public abstract void syncFavoriteVideoes();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\data\IVideoWupResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */