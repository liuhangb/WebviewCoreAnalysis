package com.tencent.smtt.webkit;

public class SmttPermissionException
  extends Exception
{
  public static final int OpenDBFailed = -1;
  public static final int PermanentPermissionNotFound = -2;
  private int a;
  
  SmttPermissionException(int paramInt)
  {
    this.a = paramInt;
  }
  
  public int getErrorCode()
  {
    return this.a;
  }
  
  public String toString()
  {
    if (this.a != -1) {
      return "Unknown Error";
    }
    return "OpenDBFailed";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\SmttPermissionException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */