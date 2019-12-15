package com.tencent.mtt.external.reader.base;

import android.widget.FrameLayout;
import com.tencent.mtt.external.reader.internal.MttFileLoadingView.FileLoadingCallback;

public class ReaderLoadingView
  extends ReaderBaseView
{
  public int create()
  {
    return 0;
  }
  
  public void destroy() {}
  
  public FrameLayout getFrameLayout()
  {
    return null;
  }
  
  public void setCallbackListener(MttFileLoadingView.FileLoadingCallback paramFileLoadingCallback) {}
  
  public void setCancelable(boolean paramBoolean) {}
  
  public void setDetailProgress(int paramInt) {}
  
  public void setProgress(int paramInt) {}
  
  public void setText(String paramString) {}
  
  public void setTotalSize(int paramInt) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderLoadingView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */