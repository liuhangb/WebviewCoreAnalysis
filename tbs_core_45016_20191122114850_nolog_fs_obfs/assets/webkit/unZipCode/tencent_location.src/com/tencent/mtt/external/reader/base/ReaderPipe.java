package com.tencent.mtt.external.reader.base;

public class ReaderPipe
{
  public static final int READER_PIPE_ERROR_CORE = 1;
  public static final int READER_PIPE_ERROR_SO = 7;
  public static final int READER_PIPE_FUNC_MENU_HIDE = 10;
  public static final int READER_PIPE_FUNC_MENU_SHOW = 9;
  public static final int READER_PIPE_LOADING_FOCUS = 5;
  public static final int READER_PIPE_LOADING_HIDE = 4;
  public static final int READER_PIPE_LOADING_SHOW = 6;
  public static final int READER_PIPE_LOAD_MENU_CHECK = 11;
  public static final int READER_PIPE_OUTLINE_SHOW = 8;
  public static final int READER_PIPE_PROGRESS = 3;
  public static final int READER_PIPE_SCROLLED_TO_END = 12;
  public static final int READER_PIPE_SWITCH = 2;
  IReaderEvent a = null;
  
  public void destroy()
  {
    this.a = null;
  }
  
  public void send(int paramInt, Object paramObject)
  {
    IReaderEvent localIReaderEvent = this.a;
    if (localIReaderEvent != null) {
      localIReaderEvent.onUiEvent(paramInt, paramObject, null);
    }
  }
  
  public void setReceiveEvent(IReaderEvent paramIReaderEvent)
  {
    this.a = paramIReaderEvent;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */