package com.tencent.mtt.base.task;

public abstract interface PictureTaskListener
{
  public abstract void onImageTaskCompleted(String paramString, byte[] paramArrayOfByte);
  
  public abstract void onImageTaskFailed(String paramString);
  
  public abstract void onImageTaskProgress(String paramString, int paramInt);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\task\PictureTaskListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */