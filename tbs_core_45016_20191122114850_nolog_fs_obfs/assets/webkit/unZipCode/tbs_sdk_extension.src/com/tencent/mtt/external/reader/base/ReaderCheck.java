package com.tencent.mtt.external.reader.base;

public abstract class ReaderCheck
{
  public static final int CHECK_TYPE_FAILED = 1;
  public static final int CHECK_TYPE_SUCCESS = 0;
  String a = null;
  protected CheckCallback mCallback = null;
  public ReaderLoadingView mLoadingView = null;
  
  public ReaderCheck(String paramString)
  {
    this.a = paramString;
  }
  
  public abstract void cancel();
  
  public abstract void check();
  
  public abstract void close();
  
  public abstract String getReaderPath();
  
  public final void setCallback(CheckCallback paramCheckCallback)
  {
    this.mCallback = paramCheckCallback;
  }
  
  public final void setView(ReaderLoadingView paramReaderLoadingView)
  {
    this.mLoadingView = paramReaderLoadingView;
  }
  
  public static abstract interface CheckCallback
  {
    public abstract void onCheckEvent(int paramInt1, int paramInt2, Object paramObject);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderCheck.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */