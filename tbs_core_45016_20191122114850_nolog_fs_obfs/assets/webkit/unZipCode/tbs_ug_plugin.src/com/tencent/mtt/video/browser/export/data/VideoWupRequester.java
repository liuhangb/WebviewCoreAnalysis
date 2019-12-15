package com.tencent.mtt.video.browser.export.data;

public abstract class VideoWupRequester
{
  public static final int REQ_METHOD_GET_CP_INFO = 1;
  public int mReqMethodType = 0;
  public Object mReqObj = null;
  public Object mRspObj = null;
  
  public abstract void onWupReqCompleted(int paramInt);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\data\VideoWupRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */