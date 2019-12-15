package com.tencent.mtt.external.reader.base;

import android.widget.FrameLayout;

public class ReaderTypeView
  extends ReaderBaseView
{
  public static final int READER_EVENT_BTN_CANCEL = 3009;
  public static final int READER_EVENT_BTN_COPY = 3008;
  public static final int READER_EVENT_BTN_EDIT = 3015;
  public static final int READER_EVENT_BTN_SEARCH = 3012;
  public static final int READER_EVENT_CHM = 3010;
  public static final int READER_EVENT_CHM_READER = 3011;
  public static final int READER_EVENT_CLICK = 3000;
  public static final int READER_EVENT_HIDE_MENU_WITHOUT_ANIMATION = 3013;
  public static final int READER_EVENT_OPEN_FAILED = 3007;
  public static final int READER_EVENT_SCALE_BEGIN = 3004;
  public static final int READER_EVENT_SCALE_END = 3005;
  public static final int READER_EVENT_SCROLLING = 3003;
  public static final int READER_EVENT_SCROLL_BEGIN = 3001;
  public static final int READER_EVENT_SCROLL_END = 3002;
  public static final int READER_EVENT_SCROLL_RATIO = 3006;
  public static final int READER_EVENT_STAT_REQ = 3014;
  public static final int VIEW_MODE_NORMAL = 0;
  public static final int VIEW_MODE_PDF_FITSCREEN = 1;
  IViewCommand a = null;
  protected FrameLayout mParentLayout = null;
  protected ReaderConfig mReaderConfig = null;
  protected int mViewMode = -1;
  
  public int create()
  {
    return 0;
  }
  
  public void destroy() {}
  
  public final void doCommand(int paramInt, Object paramObject1, Object paramObject2)
  {
    IViewCommand localIViewCommand = this.a;
    if (localIViewCommand != null) {
      localIViewCommand.doAction(paramInt, paramObject1, paramObject2);
    }
  }
  
  public final FrameLayout getFrameLayout()
  {
    return this.mParentLayout;
  }
  
  public void notifySkinChanged() {}
  
  public final void setConfig(ReaderConfig paramReaderConfig)
  {
    this.mReaderConfig = paramReaderConfig;
  }
  
  public final void setFrameLayout(FrameLayout paramFrameLayout)
  {
    this.mParentLayout = paramFrameLayout;
  }
  
  public final void setViewCommand(IViewCommand paramIViewCommand)
  {
    this.a = paramIViewCommand;
  }
  
  public static abstract interface IViewCommand
  {
    public abstract void doAction(int paramInt, Object paramObject1, Object paramObject2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderTypeView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */