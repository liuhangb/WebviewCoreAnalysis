package com.tencent.tbs.core.webkit.tencent;

import android.content.Context;
import com.tencent.smtt.net.AwNetworkUtils;
import com.tencent.smtt.os.FileUtils;
import com.tencent.smtt.util.MttLog;
import com.tencent.smtt.util.X5Log;
import com.tencent.smtt.webkit.e;
import com.tencent.tbs.core.webkit.CacheManager;
import com.tencent.tbs.core.webkit.CacheManager.CacheResult;
import com.tencent.tbs.core.webkit.WebStorage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import org.apache.http.util.ByteArrayBuffer;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.UsedByReflection;
import org.chromium.tencent.base.TencentPathUtils;

@Deprecated
public final class TencentCacheManager
  extends CacheManager
{
  static final int CACHE_CONTENT_ENCODING_TYPE_DEFLATE = 2;
  static final int CACHE_CONTENT_ENCODING_TYPE_GZIP = 1;
  static final String HEADER_KEY_IFMODIFIEDSINCE = "if-modified-since";
  static final String HEADER_KEY_IFNONEMATCH = "if-none-match";
  private static final String LOGTAG = "cache";
  private static long MEMORY_CACHE_MAX_RESOURCE_SIZE = 51200L;
  private static File mBaseDir;
  private static boolean mClearCacheOnInit = false;
  private static boolean mDisabled;
  private static File mTmpCacheDir;
  
  @Deprecated
  @UsedByReflection("WebCoreProxy.java")
  public static boolean cacheDisabled()
  {
    return false;
  }
  
  @UsedByReflection("X5CoreWizard.java")
  public static void clearLocalStorage()
  {
    WebStorage.getInstance().deleteAllData();
  }
  
  private static boolean createCacheDirectory()
  {
    if (!mBaseDir.exists()) {
      return mBaseDir.mkdirs();
    }
    return false;
  }
  
  private static boolean createTmpCacheDirectory()
  {
    if (!mTmpCacheDir.exists()) {
      return mTmpCacheDir.mkdirs();
    }
    return false;
  }
  
  private static InputStream decodeCache(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    if (paramInt == 1) {
      paramInputStream = new GZIPInputStream(paramInputStream);
    } else {
      paramInputStream = new InflaterInputStream(paramInputStream);
    }
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte = new byte['Ѐ'];
    for (;;)
    {
      paramInt = paramInputStream.read(arrayOfByte, 0, 1024);
      if (paramInt == -1) {
        break;
      }
      localByteArrayOutputStream.write(arrayOfByte, 0, paramInt);
    }
    return new ByteArrayInputStream(localByteArrayOutputStream.toByteArray());
  }
  
  @Deprecated
  public static boolean endCacheTransaction()
  {
    return false;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  static CacheResult getCacheFile(String paramString, long paramLong, Map<String, String> paramMap)
  {
    if (mDisabled) {
      return null;
    }
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("getCacheFile ");
    ((StringBuilder)localObject1).append(paramString);
    X5Log.d("cache", ((StringBuilder)localObject1).toString());
    CacheResult localCacheResult = new CacheResult();
    localCacheResult.mUrl = paramString;
    localObject1 = "";
    boolean bool1 = false;
    int k = 0;
    Object localObject4;
    int j;
    int i;
    int m;
    try
    {
      Object localObject2 = MessageDigest.getInstance("MD5");
      ((MessageDigest)localObject2).update(paramString.getBytes());
      localObject2 = ((MessageDigest)localObject2).digest();
      localObject4 = new StringBuffer(localObject2.length * 2);
      j = localObject2.length;
      i = 0;
      while (i < j)
      {
        m = localObject2[i] & 0xFF;
        if (m < 16) {
          ((StringBuffer)localObject4).append("0");
        }
        ((StringBuffer)localObject4).append(Long.toString(m, 16));
        i += 1;
      }
      localObject2 = ((StringBuffer)localObject4).toString();
      localObject1 = localObject2;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      localNoSuchAlgorithmException.printStackTrace();
    }
    if (!mTmpCacheDir.exists()) {
      createTmpCacheDirectory();
    }
    localObject1 = new File(mTmpCacheDir, new String((String)localObject1));
    localCacheResult.localPath = ((File)localObject1).getAbsolutePath();
    Object localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append(" localPath: ");
    ((StringBuilder)localObject3).append(((File)localObject1).getAbsolutePath());
    X5Log.d("cache", ((StringBuilder)localObject3).toString());
    localObject3 = AwNetworkUtils.getCacheEntry(paramString, localCacheResult.localPath);
    if ((localObject3 != null) && (localObject3.length % 2 == 0))
    {
      bool1 = false;
      for (i = 0; k < localObject3.length; i = j)
      {
        m = k + 1;
        Object localObject5 = localObject3[k];
        localObject4 = localObject3[m];
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(" # ");
        localStringBuilder.append((String)localObject5);
        localStringBuilder.append(" - ");
        localStringBuilder.append((String)localObject4);
        X5Log.d("cache", localStringBuilder.toString());
        boolean bool2;
        if (((String)localObject5).equals("httpStatusCode"))
        {
          localCacheResult.httpStatusCode = Integer.valueOf((String)localObject4).intValue();
          bool2 = bool1;
          j = i;
        }
        else if (((String)localObject5).equals("contentLength"))
        {
          localCacheResult.contentLength = Long.valueOf((String)localObject4).longValue();
          bool2 = bool1;
          j = i;
        }
        else if (((String)localObject5).equals("expires"))
        {
          localCacheResult.expiresString = ((String)localObject4);
          localCacheResult.expires = Long.valueOf((String)localObject4).longValue();
          bool2 = bool1;
          j = i;
        }
        else if (((String)localObject5).equals("lastModified"))
        {
          localCacheResult.lastModified = ((String)localObject4);
          bool2 = bool1;
          j = i;
        }
        else if (((String)localObject5).equals("etag"))
        {
          localCacheResult.etag = ((String)localObject4);
          bool2 = bool1;
          j = i;
        }
        else if (((String)localObject5).equals("mimeType"))
        {
          localCacheResult.mimeType = ((String)localObject4);
          bool2 = bool1;
          j = i;
        }
        else if (((String)localObject5).equals("contentdisposition"))
        {
          localCacheResult.contentdisposition = ((String)localObject4);
          bool2 = bool1;
          j = i;
        }
        else if (((String)localObject5).equals("q-proxy-flag"))
        {
          bool2 = ((String)localObject4).equals("1");
          localObject4 = new StringBuilder();
          ((StringBuilder)localObject4).append("  --> isFetchedByProxy ");
          ((StringBuilder)localObject4).append(bool2);
          X5Log.d("cache", ((StringBuilder)localObject4).toString());
          j = i;
        }
        else if (((String)localObject5).equals("contentEncoding"))
        {
          localObject5 = new StringBuilder();
          ((StringBuilder)localObject5).append("  --> ContentEncoding :");
          ((StringBuilder)localObject5).append((String)localObject4);
          X5Log.d("cache", ((StringBuilder)localObject5).toString());
          if (((String)localObject4).equals("gzip"))
          {
            j = 1;
            bool2 = bool1;
          }
          else
          {
            bool2 = bool1;
            j = i;
            if (((String)localObject4).equals("deflate"))
            {
              j = 2;
              bool2 = bool1;
            }
          }
        }
        else
        {
          bool2 = bool1;
          j = i;
          if (((String)localObject5).equals("location"))
          {
            localCacheResult.location = ((String)localObject4);
            j = i;
            bool2 = bool1;
          }
        }
        k = m + 1;
        bool1 = bool2;
      }
    }
    else
    {
      i = 0;
    }
    try
    {
      if ((!e.a().e()) || (!bool1)) {
        if (i > 0) {
          localCacheResult.inStream = decodeCache(new FileInputStream((File)localObject1), i);
        } else {
          localCacheResult.inStream = new FileInputStream((File)localObject1);
        }
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
    }
    if ((paramMap != null) && (localCacheResult.isExpired()))
    {
      if ((localCacheResult.lastModified == null) && (localCacheResult.etag == null)) {
        return null;
      }
      if (localCacheResult.etag != null) {
        paramMap.put("if-none-match", localCacheResult.etag);
      }
      if (localCacheResult.lastModified != null) {
        paramMap.put("if-modified-since", localCacheResult.lastModified);
      }
    }
    paramMap = new StringBuilder();
    paramMap.append("getCacheFile return ");
    paramMap.append(paramString);
    X5Log.d("cache", paramMap.toString());
    return localCacheResult;
  }
  
  @Deprecated
  @UsedByReflection("WebCoreProxy.java")
  public static CacheResult getCacheFile(String paramString, Map<String, String> paramMap)
  {
    return getCacheFile(paramString, 0L, paramMap);
  }
  
  @Deprecated
  @UsedByReflection("WebCoreProxy.java")
  public static File getCacheFileBaseDir()
  {
    return mBaseDir;
  }
  
  public static CacheResult getCacheResult(String paramString)
  {
    return getCacheFile(paramString, null);
  }
  
  @UsedByReflection("X5CoreInit.java")
  public static void init(Context paramContext)
  {
    mBaseDir = new File(TencentPathUtils.getDataDirectory(paramContext), "Cache");
    mTmpCacheDir = new File(mBaseDir, "tmp");
    paramContext = new StringBuilder();
    paramContext.append(" tmpCacheDir:");
    paramContext.append(mTmpCacheDir.getAbsolutePath());
    X5Log.d("cache", paramContext.toString());
    if ((createCacheDirectory()) && (mClearCacheOnInit))
    {
      removeAllCacheFiles();
      mClearCacheOnInit = false;
    }
    createTmpCacheDirectory();
  }
  
  @UsedByReflection("X5CoreWizard.java")
  public static boolean removeAllCacheFiles()
  {
    if (ThreadUtils.runningOnUiThread()) {
      AwNetworkUtils.clearCache();
    } else {
      ThreadUtils.postOnUiThread(new Runnable()
      {
        public void run() {}
      });
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" delete tmppath: ");
    localStringBuilder.append(mTmpCacheDir.getAbsolutePath());
    MttLog.d("cache", localStringBuilder.toString());
    FileUtils.b(mTmpCacheDir.getAbsolutePath());
    return true;
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
  
  static void setCacheDisabled(boolean paramBoolean)
  {
    if (paramBoolean == mDisabled) {
      return;
    }
    mDisabled = paramBoolean;
    if (mDisabled) {
      removeAllCacheFiles();
    }
  }
  
  @Deprecated
  public static boolean startCacheTransaction()
  {
    return false;
  }
  
  @Deprecated
  @UsedByReflection("WebCoreProxy.java")
  public static class CacheResult
    extends CacheManager.CacheResult
  {
    byte[] dataBuf;
    String mUrl;
    ByteArrayBuffer tmpDataBuf;
    
    public boolean Flush()
    {
      if (this.outFile == null) {
        return false;
      }
      if (this.tmpDataBuf == null) {
        return true;
      }
      try
      {
        this.outStream.write(this.tmpDataBuf.toByteArray(), 0, this.tmpDataBuf.length());
        buildDataBuf();
        this.tmpDataBuf.clear();
        this.tmpDataBuf = null;
        return true;
      }
      catch (Throwable localThrowable) {}
      return true;
    }
    
    public void buildDataBuf()
    {
      if ((int)this.outFile.length() <= TencentCacheManager.MEMORY_CACHE_MAX_RESOURCE_SIZE) {
        try
        {
          this.dataBuf = this.tmpDataBuf.toByteArray();
          return;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          this.dataBuf = null;
          return;
        }
      }
      this.dataBuf = null;
    }
    
    @UsedByReflection("WebCoreProxy.java")
    public boolean isExpired()
    {
      return (getExpires() >= 0L) && (getExpires() <= System.currentTimeMillis());
    }
    
    @UsedByReflection("WebCoreProxy.java")
    public boolean isNeedToRedirect()
    {
      return ((getHttpStatusCode() == 301) || (getHttpStatusCode() == 302) || (getHttpStatusCode() == 303) || (getHttpStatusCode() == 307) || (getHttpStatusCode() == 308)) && (getLocation() != null);
    }
    
    public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      try
      {
        if (this.tmpDataBuf == null) {
          this.tmpDataBuf = new ByteArrayBuffer(paramInt2);
        }
        if (this.tmpDataBuf == null) {
          break label69;
        }
        this.tmpDataBuf.append(paramArrayOfByte, paramInt1, paramInt2);
        if (this.tmpDataBuf.length() <= TencentCacheManager.MEMORY_CACHE_MAX_RESOURCE_SIZE) {
          break label69;
        }
        Flush();
        return;
      }
      catch (Throwable paramArrayOfByte)
      {
        label69:
        for (;;) {}
      }
      if (this.tmpDataBuf != null) {
        Flush();
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\tencent\TencentCacheManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */