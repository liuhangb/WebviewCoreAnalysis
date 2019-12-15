package com.tencent.smtt.export.internal.interfaces;

public class SecurityLevelBase
{
  public int evilclass = 0;
  public int eviltype = 0;
  public int flag = 0;
  public String infoUrl = null;
  public int level = -1;
  public int securitySubLevel = 0;
  public String type = null;
  public String url = null;
  
  public SecurityLevelBase() {}
  
  public SecurityLevelBase(String paramString1, int paramInt, String paramString2)
  {
    this.url = paramString1;
    this.level = paramInt;
    this.type = paramString2;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\export\internal\interfaces\SecurityLevelBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */