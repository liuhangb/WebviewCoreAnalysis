package com.tencent.smtt.livelog;

import android.content.Context;
import android.os.Process;
import android.util.Base64;
import android.util.Log;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.ThreadUtils;
import com.tencent.smtt.util.o;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;
import javax.crypto.Cipher;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.UsedByReflection;

@JNINamespace("tencent")
public class LiveLog
{
  private static final String ALGORITHM = "RSA";
  public static final int AppednerModeAsync = 0;
  public static final int AppednerModeSync = 1;
  public static final byte LEVEL_ALL = 0;
  public static final byte LEVEL_DEBUG = 1;
  public static final byte LEVEL_ERROR = 4;
  public static final byte LEVEL_FATAL = 5;
  public static final byte LEVEL_INFO = 2;
  public static final byte LEVEL_NONE = 6;
  public static final byte LEVEL_VERBOSE = 0;
  public static final byte LEVEL_WARNING = 3;
  private static final String PUBLIC_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALANdLe4BTKZtQ/RSswm3mlC3pVgTCawUYac2jHl9MEYFi+3YQzeGckXmvvMt9iVCPihJHQIOFVhzGNMhSa2qlsCAwEAAQ==";
  private static volatile boolean initFlag = false;
  private static boolean mForceInit = false;
  private String mCacheDir = null;
  private String mLogDir = null;
  private String mNamePrefix = "";
  
  public static void d(String paramString1, String paramString2)
  {
    if (initFlag)
    {
      if (paramString2 == null) {
        paramString2 = "";
      }
      nativeLogWrite(1, paramString1, "", "", 0, Process.myPid(), Thread.currentThread().getId(), paramString2);
    }
  }
  
  public static void e(String paramString1, String paramString2)
  {
    if (initFlag)
    {
      if (paramString2 == null) {
        paramString2 = "";
      }
      nativeLogWrite(4, paramString1, "", "", 0, Process.myPid(), Thread.currentThread().getId(), paramString2);
    }
  }
  
  private String encrypt(String paramString)
    throws Exception
  {
    PublicKey localPublicKey = getPublicKey("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALANdLe4BTKZtQ/RSswm3mlC3pVgTCawUYac2jHl9MEYFi+3YQzeGckXmvvMt9iVCPihJHQIOFVhzGNMhSa2qlsCAwEAAQ==");
    Cipher localCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    localCipher.init(1, localPublicKey);
    return Base64.encodeToString(localCipher.doFinal(paramString.getBytes()), 2);
  }
  
  public static void f(String paramString1, String paramString2)
  {
    if (initFlag)
    {
      if (paramString2 == null) {
        paramString2 = "";
      }
      nativeLogWrite(5, paramString1, "", "", 0, Process.myPid(), Thread.currentThread().getId(), paramString2);
    }
  }
  
  private int[] generateKey()
  {
    int[] arrayOfInt = new int[4];
    int i = 0;
    while (i < arrayOfInt.length)
    {
      int k = new Random().nextInt();
      int j = k;
      if (k < 0) {
        j = -k;
      }
      arrayOfInt[i] = j;
      i += 1;
    }
    return arrayOfInt;
  }
  
  @UsedByReflection("X5CoreInit.java")
  public static LiveLog getInstance()
  {
    return a.a();
  }
  
  private PublicKey getPublicKey(String paramString)
    throws Exception
  {
    paramString = new X509EncodedKeySpec(Base64.decode(paramString, 0));
    return KeyFactory.getInstance("RSA").generatePublic(paramString);
  }
  
  public static void i(String paramString1, String paramString2)
  {
    if (initFlag)
    {
      if (paramString2 == null) {
        paramString2 = "";
      }
      nativeLogWrite(2, paramString1, "", "", 0, Process.myPid(), Thread.currentThread().getId(), paramString2);
    }
  }
  
  @CalledByNative
  public static String initLiveLogSucceed()
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    StringBuilder localStringBuilder2 = new StringBuilder();
    localStringBuilder2.append("qua=");
    localStringBuilder2.append(SmttServiceProxy.getInstance().getQUA2());
    localStringBuilder1.append(localStringBuilder2.toString());
    localStringBuilder1.append("\n");
    return localStringBuilder1.toString();
  }
  
  private String intListToString(int[] paramArrayOfInt)
  {
    int k = 0;
    if (paramArrayOfInt == null) {
      i = 1;
    } else {
      i = 0;
    }
    if (paramArrayOfInt.length != 4) {
      j = 1;
    } else {
      j = 0;
    }
    if ((i | j) != 0) {
      return "";
    }
    StringBuilder localStringBuilder = new StringBuilder();
    int j = paramArrayOfInt.length;
    int i = k;
    while (i < j)
    {
      localStringBuilder.append(paramArrayOfInt[i]);
      localStringBuilder.append(",");
      i += 1;
    }
    localStringBuilder.replace(localStringBuilder.length() - 1, localStringBuilder.length(), "");
    return localStringBuilder.toString();
  }
  
  private static native void nativeCloseLiveLog();
  
  private static native void nativeCloseWriteAllLog();
  
  private static native void nativeFlushLivelog();
  
  private static native void nativeFlushLivelogSync();
  
  private static native boolean nativeGetLiveLogStatus();
  
  private static native void nativeInitLiveLog(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, int[] paramArrayOfInt);
  
  private static native void nativeLogWrite(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, int paramInt3, long paramLong, String paramString4);
  
  private static native void nativeOpenWriteAllLog();
  
  public static void v(String paramString1, String paramString2)
  {
    if (initFlag)
    {
      if (paramString2 == null) {
        paramString2 = "";
      }
      nativeLogWrite(0, paramString1, "", "", 0, Process.myPid(), Thread.currentThread().getId(), paramString2);
    }
  }
  
  public static void w(String paramString1, String paramString2)
  {
    if (initFlag)
    {
      if (paramString2 == null) {
        paramString2 = "";
      }
      nativeLogWrite(3, paramString1, "", "", 0, Process.myPid(), Thread.currentThread().getId(), paramString2);
    }
  }
  
  public void close()
  {
    if (!initFlag) {
      return;
    }
    initFlag = false;
    nativeCloseLiveLog();
  }
  
  public void closeWriteAllLog() {}
  
  public void flush() {}
  
  public void flushSync() {}
  
  public String getCacheDir()
  {
    return this.mCacheDir;
  }
  
  public boolean getLiveLogStatus()
  {
    return nativeGetLiveLogStatus();
  }
  
  public String getLogDir()
  {
    return this.mLogDir;
  }
  
  public String getNamePrefix()
  {
    return this.mNamePrefix;
  }
  
  @UsedByReflection("X5CoreInit.java")
  public void init(Context paramContext)
  {
    if ((paramContext != null) && (!initFlag))
    {
      if ((!mForceInit) && (!SmttServiceProxy.getInstance().isEnableLiveLog())) {
        return;
      }
      initFlag = true;
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(FileUtils.getSDcardDir(paramContext));
      ((StringBuilder)localObject).append("/Tencent/tbs_live_log/");
      ((StringBuilder)localObject).append(paramContext.getPackageName());
      this.mLogDir = ((StringBuilder)localObject).toString();
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(FileUtils.getFilesDir(paramContext));
      ((StringBuilder)localObject).append("/live_log");
      this.mCacheDir = ((StringBuilder)localObject).toString();
      this.mNamePrefix = ThreadUtils.getCurrentProcessNameIngoreColon(paramContext);
      localObject = generateKey();
      if (localObject != null)
      {
        if (localObject.length != 4) {
          return;
        }
        String str = intListToString((int[])localObject);
        try
        {
          str = encrypt(str);
          if (str != null)
          {
            if (str.length() < 1) {
              return;
            }
            nativeInitLiveLog(2, 0, this.mCacheDir, this.mLogDir, this.mNamePrefix, str, (int[])localObject);
            o.a().a(paramContext);
            return;
          }
          return;
        }
        catch (Exception paramContext)
        {
          paramContext.printStackTrace();
          return;
        }
      }
      return;
    }
    Log.e("LIVE_LOG", " LIVE LOG INIT ERROR ");
  }
  
  public void openWriteAllLog() {}
  
  public void resetInitFlag()
  {
    initFlag = false;
  }
  
  public void setForceInit()
  {
    mForceInit = true;
  }
  
  private static class a
  {
    private static final LiveLog a = new LiveLog();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\livelog\LiveLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */