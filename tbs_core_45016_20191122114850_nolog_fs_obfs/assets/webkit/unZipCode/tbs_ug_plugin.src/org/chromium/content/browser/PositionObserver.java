package org.chromium.content.browser;

public abstract interface PositionObserver
{
  public abstract void addListener(Listener paramListener);
  
  public abstract void clearListener();
  
  public abstract int getPositionX();
  
  public abstract int getPositionY();
  
  public abstract void removeListener(Listener paramListener);
  
  public static abstract interface Listener
  {
    public abstract void onPositionChanged(int paramInt1, int paramInt2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\PositionObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */