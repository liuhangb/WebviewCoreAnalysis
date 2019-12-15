package com.tencent.tbs.core.webkit;

import java.io.InputStream;
import java.util.Map;

@Deprecated
public final class PluginData
{
  private long mContentLength;
  private Map<String, String[]> mHeaders;
  private int mStatusCode;
  private InputStream mStream;
  
  @Deprecated
  public PluginData(InputStream paramInputStream, long paramLong, Map<String, String[]> paramMap, int paramInt)
  {
    this.mStream = paramInputStream;
    this.mContentLength = paramLong;
    this.mHeaders = paramMap;
    this.mStatusCode = paramInt;
  }
  
  @Deprecated
  public long getContentLength()
  {
    return this.mContentLength;
  }
  
  @Deprecated
  public Map<String, String[]> getHeaders()
  {
    return this.mHeaders;
  }
  
  @Deprecated
  public InputStream getInputStream()
  {
    return this.mStream;
  }
  
  @Deprecated
  public int getStatusCode()
  {
    return this.mStatusCode;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\PluginData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */