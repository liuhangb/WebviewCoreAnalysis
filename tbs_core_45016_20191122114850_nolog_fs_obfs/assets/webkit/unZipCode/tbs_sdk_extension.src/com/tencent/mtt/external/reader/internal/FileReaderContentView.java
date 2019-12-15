package com.tencent.mtt.external.reader.internal;

import android.content.Context;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import com.tencent.common.utils.LogUtils;

public class FileReaderContentView
  extends FrameLayout
{
  public VolumeKeyListener volumeKeyListener = null;
  
  public FileReaderContentView(Context paramContext)
  {
    super(paramContext);
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    LogUtils.d("dispatchKeyEvent", "FileReaderContentView");
    return super.dispatchKeyEvent(paramKeyEvent);
  }
  
  public void toFinish()
  {
    this.volumeKeyListener = null;
  }
  
  public static abstract interface VolumeKeyListener
  {
    public abstract boolean onVolumeDown();
    
    public abstract boolean onVolumeUp();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\FileReaderContentView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */