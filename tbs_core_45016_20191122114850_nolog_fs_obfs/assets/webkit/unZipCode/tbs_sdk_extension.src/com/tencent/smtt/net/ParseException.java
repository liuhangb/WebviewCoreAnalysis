package com.tencent.smtt.net;

public class ParseException
  extends RuntimeException
{
  public String response;
  
  ParseException(String paramString)
  {
    this.response = paramString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\ParseException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */