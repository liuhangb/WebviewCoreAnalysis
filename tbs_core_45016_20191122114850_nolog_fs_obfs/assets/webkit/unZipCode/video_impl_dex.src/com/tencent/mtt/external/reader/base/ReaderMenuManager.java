package com.tencent.mtt.external.reader.base;

public abstract class ReaderMenuManager
{
  public abstract void destory();
  
  public abstract void focus();
  
  public abstract int initMenu(int paramInt);
  
  public abstract boolean isAnimating();
  
  public abstract boolean isShow();
  
  public abstract void removeAll();
  
  public abstract void setEventHandler(IReaderEvent paramIReaderEvent);
  
  public abstract int setMenu(int paramInt);
  
  public abstract void setMenuVisiable(boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void updateMenuText(int paramInt, String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderMenuManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */