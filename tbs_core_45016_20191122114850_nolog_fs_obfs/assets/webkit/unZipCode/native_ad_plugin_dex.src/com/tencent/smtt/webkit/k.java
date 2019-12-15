package com.tencent.smtt.webkit;

import org.chromium.tencent.AwMediaAccessPermissions;

public class k
  extends j
{
  private AwMediaAccessPermissions a;
  
  public k(AwMediaAccessPermissions paramAwMediaAccessPermissions)
  {
    this.a = paramAwMediaAccessPermissions;
  }
  
  public void a()
  {
    this.a.clearAll();
  }
  
  public void a(String paramString, int paramInt)
  {
    this.a.allow(paramString, paramInt);
  }
  
  public boolean a(String paramString, int paramInt)
  {
    return this.a.isOriginAllowed(paramString, paramInt);
  }
  
  public void b(String paramString, int paramInt)
  {
    this.a.deny(paramString, paramInt);
  }
  
  public boolean b(String paramString, int paramInt)
  {
    return this.a.hasOrigin(paramString, paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\k.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */