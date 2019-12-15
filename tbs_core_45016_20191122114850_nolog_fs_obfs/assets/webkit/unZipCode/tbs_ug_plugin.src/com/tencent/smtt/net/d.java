package com.tencent.smtt.net;

import com.tencent.smtt.export.external.interfaces.NetworkException;

public class d
  extends NetworkException
{
  private final int a;
  private final int b;
  
  public d(String paramString, int paramInt1, int paramInt2)
  {
    super(paramString, null);
    this.a = paramInt1;
    this.b = paramInt2;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */