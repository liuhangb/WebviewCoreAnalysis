package com.tencent.tbs.core.partner.ad;

public class ContentInfo
{
  private String mContent;
  private String mEncoding;
  private String mType;
  
  ContentInfo(String paramString1, String paramString2, String paramString3)
  {
    this.mContent = paramString1;
    this.mType = paramString2;
    this.mEncoding = paramString3;
  }
  
  public String getContent()
  {
    return this.mContent;
  }
  
  public String getEncoding()
  {
    return this.mEncoding;
  }
  
  public String getType()
  {
    return this.mType;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\ad\ContentInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */