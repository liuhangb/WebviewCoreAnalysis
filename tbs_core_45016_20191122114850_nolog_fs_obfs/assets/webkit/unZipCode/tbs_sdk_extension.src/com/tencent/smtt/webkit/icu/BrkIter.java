package com.tencent.smtt.webkit.icu;

public abstract class BrkIter
{
  public abstract int current();
  
  public abstract int first();
  
  public abstract int following(int paramInt);
  
  public abstract int getRuleStatus();
  
  public abstract boolean isBoundary(int paramInt);
  
  public abstract boolean isWord();
  
  public abstract int last();
  
  public abstract int next();
  
  public abstract int preceding(int paramInt);
  
  public abstract int previous();
  
  public abstract void setText(String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\BrkIter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */