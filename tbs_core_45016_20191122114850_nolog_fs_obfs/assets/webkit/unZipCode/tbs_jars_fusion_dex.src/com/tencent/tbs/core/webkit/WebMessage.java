package com.tencent.tbs.core.webkit;

public class WebMessage
{
  private String mData;
  private WebMessagePort[] mPorts;
  
  public WebMessage(String paramString)
  {
    this.mData = paramString;
  }
  
  public WebMessage(String paramString, WebMessagePort[] paramArrayOfWebMessagePort)
  {
    this.mData = paramString;
    this.mPorts = paramArrayOfWebMessagePort;
  }
  
  public String getData()
  {
    return this.mData;
  }
  
  public WebMessagePort[] getPorts()
  {
    return this.mPorts;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\WebMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */