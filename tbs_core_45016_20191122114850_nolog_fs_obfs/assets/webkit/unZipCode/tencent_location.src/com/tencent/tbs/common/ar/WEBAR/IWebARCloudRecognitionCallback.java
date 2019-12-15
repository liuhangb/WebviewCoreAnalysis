package com.tencent.tbs.common.ar.WEBAR;

public abstract interface IWebARCloudRecognitionCallback
{
  public static final int EVENT_RECOGNITION_FAILED = 1;
  public static final int EVENT_RECOGNITION_SUCCESS = 0;
  
  public abstract void onCloudRecognitionEvent(int paramInt1, int paramInt2, String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ar\WEBAR\IWebARCloudRecognitionCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */