package com.tencent.common.http;

public class ResponseBase
{
  protected String mByteRanges;
  protected String mCacheControl;
  protected String mCharset;
  protected String mConnection;
  protected String mContentDisposition;
  protected String mContentEncoding;
  protected String mContentRange;
  protected String mLastModify;
  protected String mServer;
  protected String mTransferEncoding;
  
  public String getAcceptRanges()
  {
    return this.mByteRanges;
  }
  
  public String getCacheControl()
  {
    return this.mCacheControl;
  }
  
  public String getCharset()
  {
    return this.mCharset;
  }
  
  public String getConnection()
  {
    return this.mConnection;
  }
  
  public String getContentDisposition()
  {
    return this.mContentDisposition;
  }
  
  public String getContentEncoding()
  {
    return this.mContentEncoding;
  }
  
  public String getContentRange()
  {
    return this.mContentRange;
  }
  
  public String getLastModify()
  {
    return this.mLastModify;
  }
  
  public String getServer()
  {
    return this.mServer;
  }
  
  public String getTransferEncoding()
  {
    return this.mTransferEncoding;
  }
  
  public void setByteRanges(String paramString)
  {
    this.mByteRanges = paramString;
  }
  
  public void setCacheControl(String paramString)
  {
    this.mCacheControl = paramString;
  }
  
  public void setCharset(String paramString)
  {
    this.mCharset = paramString;
  }
  
  public void setConnection(String paramString)
  {
    this.mConnection = paramString;
  }
  
  public void setContentDisposition(String paramString)
  {
    this.mContentDisposition = paramString;
  }
  
  public void setContentEncoding(String paramString)
  {
    this.mContentEncoding = paramString;
  }
  
  public void setContentRange(String paramString)
  {
    this.mContentRange = paramString;
  }
  
  public void setLastModify(String paramString)
  {
    this.mLastModify = paramString;
  }
  
  public void setServer(String paramString)
  {
    this.mServer = paramString;
  }
  
  public void setTransferEncoding(String paramString)
  {
    this.mTransferEncoding = paramString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\ResponseBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */