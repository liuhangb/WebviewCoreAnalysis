package com.tencent.tbs.core.webkit;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import org.chromium.base.annotations.UsedByReflection;

@Deprecated
public class CacheManager
{
  @Deprecated
  public static boolean cacheDisabled()
  {
    return false;
  }
  
  @Deprecated
  public static boolean endCacheTransaction()
  {
    return false;
  }
  
  @Deprecated
  public static CacheResult getCacheFile(String paramString, Map<String, String> paramMap)
  {
    return null;
  }
  
  @Deprecated
  public static File getCacheFileBaseDir()
  {
    return null;
  }
  
  static void saveCacheFile(String paramString, long paramLong, CacheResult paramCacheResult)
  {
    try
    {
      paramCacheResult.outStream.close();
      return;
    }
    catch (IOException paramString) {}
  }
  
  @Deprecated
  public static void saveCacheFile(String paramString, CacheResult paramCacheResult)
  {
    saveCacheFile(paramString, 0L, paramCacheResult);
  }
  
  @Deprecated
  public static boolean startCacheTransaction()
  {
    return false;
  }
  
  @Deprecated
  public static class CacheResult
  {
    public long contentLength;
    public String contentdisposition;
    protected String crossDomain;
    protected String encoding;
    public String etag;
    public long expires;
    public String expiresString;
    public int httpStatusCode;
    public InputStream inStream;
    public String lastModified;
    public String localPath;
    public String location;
    public String mimeType;
    protected File outFile;
    public OutputStream outStream;
    
    public String getContentDisposition()
    {
      return this.contentdisposition;
    }
    
    public long getContentLength()
    {
      return this.contentLength;
    }
    
    public String getETag()
    {
      return this.etag;
    }
    
    public String getEncoding()
    {
      return this.encoding;
    }
    
    public long getExpires()
    {
      return this.expires;
    }
    
    public String getExpiresString()
    {
      return this.expiresString;
    }
    
    public int getHttpStatusCode()
    {
      return this.httpStatusCode;
    }
    
    @UsedByReflection("WebCoreProxy.java")
    public InputStream getInputStream()
    {
      return this.inStream;
    }
    
    public String getLastModified()
    {
      return this.lastModified;
    }
    
    @UsedByReflection("WebCoreProxy.java")
    public String getLocalPath()
    {
      return this.localPath;
    }
    
    @UsedByReflection("WebCoreProxy.java")
    public String getLocation()
    {
      return this.location;
    }
    
    public String getMimeType()
    {
      return this.mimeType;
    }
    
    public OutputStream getOutputStream()
    {
      return this.outStream;
    }
    
    public void setContentLength(long paramLong)
    {
      this.contentLength = paramLong;
    }
    
    public void setEncoding(String paramString)
    {
      this.encoding = paramString;
    }
    
    public void setInputStream(InputStream paramInputStream)
    {
      this.inStream = paramInputStream;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\CacheManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */