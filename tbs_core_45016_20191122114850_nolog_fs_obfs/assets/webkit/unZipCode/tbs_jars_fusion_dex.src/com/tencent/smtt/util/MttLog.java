package com.tencent.smtt.util;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.tencent.common.utils.FileUtils;
import com.tencent.smtt.livelog.LiveLog;
import com.tencent.smtt.net.NetLogLoggerService;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.q;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import org.chromium.base.CommandLine;

public class MttLog
{
  public static final String AD_FILTERING_USING_REGEX_TAG = "adfilteringusingregex";
  public static boolean ENABLE_INPUT_DEBUG = false;
  public static boolean ENABLE_SCROLL_DEBUG = false;
  public static final String INPUTDISPATCH_TAG = "InputDispatchDebug";
  public static final byte LOG_BOTH = 3;
  public static final byte LOG_CONSOLE = 1;
  public static final byte LOG_FILE = 2;
  public static final byte LOG_LEVEL_D = 1;
  public static final byte LOG_LEVEL_E = 4;
  public static final byte LOG_LEVEL_I = 2;
  public static final byte LOG_LEVEL_V = 0;
  public static final byte LOG_LEVEL_W = 3;
  private static int LOG_MAX_SIZE = 5242880;
  public static final byte LOG_NULL = 0;
  public static final int MAX_BUF_LEN = 16384;
  public static final int MSG_PACK_META_INFO = 1;
  public static final int MSG_UPLOAD = 0;
  private static int MTTLOG_FLAG = 0;
  public static final String NATIVE_AD_TAG = "NativeAdDebug";
  private static int NETLOG_FLAG = 1;
  private static String NET_LOG_FILENAME_PREFIX = "netlog";
  public static final String PERFORMANCE_STAT_TAG = "performancestat";
  public static final String SCROLL_TAG = "ScrollDebug";
  protected static final String TAG = "QQBrowser";
  private static boolean enableEncryptLogFile = true;
  public static byte logDevice = 0;
  private static String logStoreDir;
  private static HandlerThread mLogAffairThread;
  private static boolean sEnableTraceLog = false;
  public static c sEncription;
  private static boolean sIsUploadingLog = false;
  private static Handler sLogUploadeHandler;
  private static a sNetLogLogger;
  
  static {}
  
  public static void closeNetLogger()
  {
    if (sNetLogLogger != null)
    {
      NetLogLoggerService.c();
      sNetLogLogger.c();
      sNetLogLogger = null;
    }
  }
  
  private static void createLogUploadHandler()
  {
    sLogUploadeHandler = new Handler(mLogAffairThread.getLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        File localFile = MttLog.getLocalLogDir();
        if (localFile == null) {
          return;
        }
        switch (paramAnonymousMessage.what)
        {
        default: 
          return;
        case 1: 
          MttLog.access$002(true);
          MttTimingLog.packDetectLog(localFile.getAbsolutePath());
          MttLog.access$002(false);
          return;
        }
        MttLog.access$002(true);
        MttTimingLog.packAndUpload(localFile.getAbsolutePath());
        MttLog.access$002(false);
      }
    };
  }
  
  public static void d(String paramString) {}
  
  public static void d(String paramString1, String paramString2) {}
  
  public static void d(String paramString1, String paramString2, Throwable paramThrowable) {}
  
  public static void d(String paramString, Throwable paramThrowable) {}
  
  public static void d(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2) {}
  
  public static void e(String paramString) {}
  
  public static void e(String paramString1, String paramString2) {}
  
  public static void e(String paramString1, String paramString2, Throwable paramThrowable) {}
  
  public static void e(String paramString, Throwable paramThrowable) {}
  
  public static void flushLogs()
  {
    a locala = sNetLogLogger;
    if (locala != null) {
      locala.b();
    }
  }
  
  public static String getLocalDumpDir()
  {
    File localFile = getLogStoreDir();
    if (localFile == null) {
      return null;
    }
    localFile = new File(localFile, ".dump");
    if (!localFile.exists()) {
      localFile.mkdirs();
    }
    return localFile.getPath();
  }
  
  public static String getLocalDumpDirTbs()
  {
    if (CommandLine.getInstance().hasSwitch("deadcode_dump_dir"))
    {
      Object localObject = CommandLine.getInstance().getSwitchValue("deadcode_dump_dir", null);
      if (localObject == null)
      {
        Log.e("QQBrowser", "Mttlog dump destDir:null");
        return null;
      }
      localObject = new File((String)localObject);
      if (!((File)localObject).exists())
      {
        Log.e("QQBrowser", "Mttlog dump dir not exist");
        return null;
      }
      localObject = new File((File)localObject, ".dump");
      if (!((File)localObject).exists()) {
        ((File)localObject).mkdirs();
      }
      return ((File)localObject).getPath();
    }
    return null;
  }
  
  public static String getLocalDumpDirTbsQQ()
  {
    File localFile = getLogStoreDir();
    if (localFile == null) {
      return null;
    }
    localFile = new File(localFile, ".dump_tbs_qq");
    if (!localFile.exists()) {
      localFile.mkdirs();
    }
    return localFile.getPath();
  }
  
  public static String getLocalDumpDirTbsWX()
  {
    File localFile = getLogStoreDir();
    if (localFile == null) {
      return null;
    }
    localFile = new File(localFile, ".dump_tbs_wx");
    if (!localFile.exists()) {
      localFile.mkdirs();
    }
    return localFile.getPath();
  }
  
  public static File getLocalLogDir()
  {
    File localFile = getLogStoreDir();
    if (localFile == null) {
      return null;
    }
    localFile = new File(localFile, ".logTmp");
    if (!localFile.exists()) {
      localFile.mkdirs();
    }
    return localFile;
  }
  
  public static String getLocalRenderInfoDir()
  {
    File localFile = getLogStoreDir();
    if (localFile == null) {
      return null;
    }
    localFile = new File(localFile, ".renderinfo");
    if (!localFile.exists()) {
      localFile.mkdirs();
    }
    return localFile.getPath();
  }
  
  private static File getLogStoreDir()
  {
    Object localObject = logStoreDir;
    if ((localObject == null) && (localObject == null))
    {
      localObject = FileUtils.getSDcardDir(ContextHolder.getInstance().getContext());
      if (localObject == null)
      {
        q.a("getLogStoreDir", "FileUtils.getSDcardDir is null");
        return null;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(((File)localObject).getAbsolutePath());
      localStringBuilder.append("/QQBrowser");
      logStoreDir = localStringBuilder.toString();
    }
    localObject = new File(logStoreDir);
    if (!((File)localObject).exists()) {
      ((File)localObject).mkdirs();
    }
    return (File)localObject;
  }
  
  public static File getSDCardOrPackageDir()
  {
    File localFile = FileUtils.getSDcardDir(ContextHolder.getInstance().getContext());
    Object localObject = localFile;
    if (localFile == null)
    {
      localObject = ContextHolder.getInstance().getContext();
      if (localObject != null) {
        return ((Context)localObject).getFilesDir();
      }
      localObject = new File("/sdcard");
    }
    return (File)localObject;
  }
  
  public static void i(String paramString) {}
  
  public static void i(String paramString1, String paramString2) {}
  
  public static void i(String paramString1, String paramString2, Throwable paramThrowable) {}
  
  public static void i(String paramString, Throwable paramThrowable) {}
  
  @Deprecated
  public static boolean isEnableLog()
  {
    return false;
  }
  
  public static boolean isLogWritten2File()
  {
    return logDevice == 2;
  }
  
  public static boolean isUploadingLog()
  {
    return sIsUploadingLog;
  }
  
  public static void netlog(String paramString)
  {
    a locala = sNetLogLogger;
    if (locala != null) {
      locala.a(paramString);
    }
  }
  
  public static void openNetLogger()
  {
    Object localObject = getLocalLogDir();
    if (localObject == null)
    {
      Log.e("QQBrowser", "setLogWrite2FileFlag gegetLocalLogDir is null");
      return;
    }
    if (!o.a().a()) {
      MttTimingLog.delAllFiles(((File)localObject).getAbsolutePath());
    }
    if (sNetLogLogger == null)
    {
      localObject = ((File)localObject).getAbsolutePath();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(LiveLog.getInstance().getNamePrefix());
      localStringBuilder.append("_");
      localStringBuilder.append(NET_LOG_FILENAME_PREFIX);
      sNetLogLogger = new a((String)localObject, localStringBuilder.toString(), LOG_MAX_SIZE, NETLOG_FLAG);
      NetLogLoggerService.a();
    }
  }
  
  public static void packPageErrorLogInfo()
  {
    if (sLogUploadeHandler == null) {
      createLogUploadHandler();
    }
    Object localObject = sNetLogLogger;
    if (localObject != null) {
      ((a)localObject).c();
    }
    localObject = sLogUploadeHandler.obtainMessage(1);
    sLogUploadeHandler.sendMessage((Message)localObject);
  }
  
  public static void setDebugx5OpenFlag()
  {
    g.a("debugx5open");
  }
  
  public static void setDumpUploadFlag()
  {
    g.a("dumpupload");
  }
  
  public static void setQBIconClickFlag()
  {
    g.a("qbiconclick");
  }
  
  public static void v(String paramString) {}
  
  public static void v(String paramString1, String paramString2) {}
  
  public static void v(String paramString1, String paramString2, Throwable paramThrowable) {}
  
  public static void v(String paramString, Throwable paramThrowable) {}
  
  public static void w(String paramString) {}
  
  public static void w(String paramString1, String paramString2) {}
  
  public static void w(String paramString1, String paramString2, Throwable paramThrowable) {}
  
  public static void w(String paramString, Throwable paramThrowable) {}
  
  public static void w(Throwable paramThrowable) {}
  
  private static class a
  {
    private static Handler jdField_a_of_type_AndroidOsHandler;
    private int jdField_a_of_type_Int = 0;
    private FileOutputStream jdField_a_of_type_JavaIoFileOutputStream = null;
    private String jdField_a_of_type_JavaLangString = null;
    private boolean jdField_a_of_type_Boolean = false;
    private byte[] jdField_a_of_type_ArrayOfByte = new byte['䀀'];
    private int jdField_b_of_type_Int = 0;
    private String jdField_b_of_type_JavaLangString = null;
    private boolean jdField_b_of_type_Boolean = false;
    private int jdField_c_of_type_Int = 0;
    private String jdField_c_of_type_JavaLangString = null;
    private boolean jdField_c_of_type_Boolean = false;
    private int jdField_d_of_type_Int = 0;
    private boolean jdField_d_of_type_Boolean = false;
    
    public a(String paramString1, String paramString2, int paramInt1, int paramInt2)
    {
      Handler localHandler = jdField_a_of_type_AndroidOsHandler;
      if (localHandler == null) {
        return;
      }
      this.jdField_d_of_type_Int = paramInt2;
      this.jdField_c_of_type_JavaLangString = paramString1;
      this.jdField_b_of_type_JavaLangString = paramString2;
      this.jdField_c_of_type_Int = paramInt1;
      localHandler.sendMessage(Message.obtain(localHandler, 0, this));
    }
    
    private String a()
    {
      String str = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
      if (a())
      {
        if (!this.jdField_c_of_type_Boolean)
        {
          this.jdField_c_of_type_Boolean = true;
          localStringBuilder = new StringBuilder();
          localStringBuilder.append(this.jdField_c_of_type_JavaLangString);
          localStringBuilder.append("/");
          localStringBuilder.append(this.jdField_b_of_type_JavaLangString);
          localStringBuilder.append("-");
          localStringBuilder.append(str);
          localStringBuilder.append(".constants.txt");
          return localStringBuilder.toString();
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(this.jdField_c_of_type_JavaLangString);
        localStringBuilder.append("/");
        localStringBuilder.append(this.jdField_b_of_type_JavaLangString);
        localStringBuilder.append("-");
        localStringBuilder.append(str);
        localStringBuilder.append(".txt");
        return localStringBuilder.toString();
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.jdField_c_of_type_JavaLangString);
      localStringBuilder.append("/");
      localStringBuilder.append(this.jdField_b_of_type_JavaLangString);
      localStringBuilder.append("-");
      localStringBuilder.append(str);
      localStringBuilder.append(".txt");
      return localStringBuilder.toString();
    }
    
    public static void a()
    {
      MttLog.access$102(new HandlerThread("log-handler"));
      MttLog.mLogAffairThread.start();
      jdField_a_of_type_AndroidOsHandler = new Handler(MttLog.mLogAffairThread.getLooper())
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          Object localObject;
          switch (paramAnonymousMessage.what)
          {
          default: 
            
          case 2: 
            paramAnonymousMessage = (MttLog.a)paramAnonymousMessage.obj;
            if (MttLog.a.a(paramAnonymousMessage) != null)
            {
              try
              {
                MttLog.a.a(paramAnonymousMessage).close();
              }
              catch (IOException localIOException)
              {
                localIOException.printStackTrace();
              }
              MttLog.a.a(paramAnonymousMessage, null);
              return;
            }
            break;
          case 1: 
            paramAnonymousMessage = (MttLog.a.a)paramAnonymousMessage.obj;
            if (paramAnonymousMessage != null)
            {
              if (paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a == null) {
                return;
              }
              try
              {
                if (MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a) != null)
                {
                  if (!new File(MttLog.a.c(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a)).exists())
                  {
                    MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a).close();
                    MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a, new FileOutputStream(MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a, MttLog.a.b(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a))));
                    MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a, 0);
                  }
                }
                else
                {
                  MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a, MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a), MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a));
                  MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a, new FileOutputStream(MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a, MttLog.a.b(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a))));
                  MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a, 0);
                }
                MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a).write(paramAnonymousMessage.jdField_a_of_type_ArrayOfByte, 0, paramAnonymousMessage.jdField_a_of_type_Int);
                MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a).flush();
                localObject = paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a;
                MttLog.a.a((MttLog.a)localObject, MttLog.a.b((MttLog.a)localObject) + paramAnonymousMessage.jdField_a_of_type_Int);
                if ((MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a)) && (!MttLog.a.b(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a)))
                {
                  MttLog.a.b(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a, true);
                  MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a, new FileOutputStream(MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a, MttLog.a.b(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a))));
                  MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a, 0);
                }
                if (MttLog.a.b(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a) <= 1024000) {
                  break;
                }
                if (MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a) != null) {
                  MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a).close();
                }
                MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a, MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a), MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a));
                MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a, new FileOutputStream(MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a, MttLog.a.b(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a))));
                MttLog.a.a(paramAnonymousMessage.jdField_a_of_type_ComTencentSmttUtilMttLog$a, 0);
                return;
              }
              catch (Exception paramAnonymousMessage)
              {
                localObject = new StringBuilder();
                ((StringBuilder)localObject).append("there are some exceptions when writing log into file: ");
                ((StringBuilder)localObject).append(paramAnonymousMessage.getMessage());
                Log.e("uploadlog", ((StringBuilder)localObject).toString());
                paramAnonymousMessage.printStackTrace();
                return;
              }
            }
            return;
          case 0: 
            paramAnonymousMessage = (MttLog.a)paramAnonymousMessage.obj;
            localObject = new File(MttLog.a.a(paramAnonymousMessage));
            if ((((File)localObject).exists()) && (((File)localObject).isDirectory()))
            {
              MttLog.a.a(paramAnonymousMessage, true);
            }
            else
            {
              if (!((File)localObject).isDirectory()) {
                ((File)localObject).delete();
              }
              if (!((File)localObject).mkdirs()) {
                return;
              }
              MttLog.a.a(paramAnonymousMessage, true);
            }
            try
            {
              MttLog.a.a(paramAnonymousMessage, MttLog.a.a(paramAnonymousMessage), MttLog.a.a(paramAnonymousMessage));
              MttLog.a.a(paramAnonymousMessage, new FileOutputStream(MttLog.a.a(paramAnonymousMessage, MttLog.a.b(paramAnonymousMessage))));
              return;
            }
            catch (FileNotFoundException localFileNotFoundException)
            {
              localFileNotFoundException.printStackTrace();
              MttLog.a.a(paramAnonymousMessage, false);
            }
          }
        }
      };
    }
    
    private void a(String paramString, long paramLong)
    {
      paramString = new File(paramString);
      if (paramString.exists())
      {
        if (!paramString.isDirectory()) {
          return;
        }
        paramString = paramString.listFiles(new b(null));
        if (paramString != null)
        {
          if (paramString.length == 0) {
            return;
          }
          Arrays.sort(paramString);
          int i = paramString.length - 1;
          while ((i >= 0) && (paramString[i].isFile()))
          {
            if (paramString[i].length() <= paramLong) {
              paramLong -= paramString[i].length();
            } else {
              paramString[i].delete();
            }
            i -= 1;
          }
          return;
        }
        return;
      }
    }
    
    private boolean a()
    {
      return this.jdField_d_of_type_Int == 1;
    }
    
    public void a(String paramString)
    {
      Object localObject1;
      Object localObject2;
      if (this.jdField_a_of_type_Boolean)
      {
        if (jdField_a_of_type_AndroidOsHandler == null) {
          return;
        }
        localObject1 = paramString;
        if (!a())
        {
          localObject1 = new Date();
          localObject2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        }
      }
      try
      {
        localObject1 = ((SimpleDateFormat)localObject2).format((Date)localObject1);
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append(" ");
        ((StringBuilder)localObject2).append(paramString);
        ((StringBuilder)localObject2).append("\r\n");
        localObject1 = ((StringBuilder)localObject2).toString();
        if (((String)localObject1).length() >= 51200) {
          return;
        }
        try
        {
          paramString = ((String)localObject1).getBytes("UTF-8");
          if (a())
          {
            if (!this.jdField_b_of_type_Boolean)
            {
              this.jdField_b_of_type_Boolean = true;
              this.jdField_a_of_type_Int += paramString.length;
              a(paramString);
              return;
            }
            if (this.jdField_a_of_type_Int + paramString.length >= 16384) {
              b();
            }
            System.arraycopy(paramString, 0, this.jdField_a_of_type_ArrayOfByte, this.jdField_a_of_type_Int, paramString.length);
            this.jdField_a_of_type_Int += paramString.length;
            return;
          }
          if (this.jdField_a_of_type_Int + paramString.length >= 16384) {
            b();
          }
          System.arraycopy(paramString, 0, this.jdField_a_of_type_ArrayOfByte, this.jdField_a_of_type_Int, paramString.length);
          this.jdField_a_of_type_Int += paramString.length;
          return;
        }
        catch (Exception paramString)
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("Exception occured : ");
          ((StringBuilder)localObject1).append(paramString.toString());
          Log.e("MttLog", ((StringBuilder)localObject1).toString());
          return;
        }
        catch (ArrayIndexOutOfBoundsException paramString)
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("ArrayIndexOutOfBoundsException occured : ");
          ((StringBuilder)localObject1).append(paramString.toString());
          Log.e("MttLog", ((StringBuilder)localObject1).toString());
          return;
        }
        catch (UnsupportedEncodingException paramString)
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("UnsupportedEncodingException occured : ");
          ((StringBuilder)localObject1).append(paramString.toString());
          Log.e("MttLog", ((StringBuilder)localObject1).toString());
          return;
        }
        return;
      }
      catch (Exception localException)
      {
        for (;;)
        {
          String str = paramString;
        }
      }
    }
    
    public void a(boolean paramBoolean)
    {
      int i;
      if (this.jdField_a_of_type_Boolean)
      {
        if (jdField_a_of_type_AndroidOsHandler == null) {
          return;
        }
        i = this.jdField_a_of_type_Int;
        if ((i > 0) && (!paramBoolean)) {}
      }
      try
      {
        a locala = new a(this, this.jdField_a_of_type_ArrayOfByte, i);
        jdField_a_of_type_AndroidOsHandler.sendMessage(Message.obtain(jdField_a_of_type_AndroidOsHandler, 1, 0, 0, locala));
        this.jdField_a_of_type_ArrayOfByte = new byte['䀀'];
        this.jdField_a_of_type_Int = 0;
        return;
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        for (;;) {}
      }
      this.jdField_a_of_type_Int = 0;
      return;
    }
    
    public void a(byte[] paramArrayOfByte)
    {
      int i;
      if (this.jdField_a_of_type_Boolean)
      {
        if (jdField_a_of_type_AndroidOsHandler == null) {
          return;
        }
        i = this.jdField_a_of_type_Int;
        if (i <= 0) {}
      }
      try
      {
        paramArrayOfByte = new a(this, paramArrayOfByte, i);
        jdField_a_of_type_AndroidOsHandler.sendMessage(Message.obtain(jdField_a_of_type_AndroidOsHandler, 1, 0, 0, paramArrayOfByte));
        this.jdField_a_of_type_Int = 0;
        return;
      }
      catch (OutOfMemoryError paramArrayOfByte)
      {
        for (;;) {}
      }
      this.jdField_a_of_type_Int = 0;
      return;
    }
    
    public void b()
    {
      a(true);
    }
    
    public void c()
    {
      if (this.jdField_a_of_type_Boolean)
      {
        if (jdField_a_of_type_AndroidOsHandler == null) {
          return;
        }
        b();
        Handler localHandler = jdField_a_of_type_AndroidOsHandler;
        localHandler.sendMessage(Message.obtain(localHandler, 2, 0, 0, this));
        this.jdField_b_of_type_Int = 0;
        return;
      }
    }
    
    private static class a
    {
      public int a;
      public MttLog.a a;
      public byte[] a;
      
      public a(MttLog.a parama, byte[] paramArrayOfByte, int paramInt)
      {
        this.jdField_a_of_type_ComTencentSmttUtilMttLog$a = null;
        this.jdField_a_of_type_ArrayOfByte = null;
        this.jdField_a_of_type_Int = 0;
        if (MttLog.enableEncryptLogFile) {}
        for (;;)
        {
          byte[] arrayOfByte1;
          int i;
          try
          {
            arrayOfByte1 = new byte[paramInt];
            System.arraycopy(paramArrayOfByte, 0, arrayOfByte1, 0, paramInt);
            if (MttLog.sEncription == null) {
              MttLog.sEncription = new c();
            }
            paramArrayOfByte = MttLog.sEncription.a(arrayOfByte1);
            arrayOfByte1 = new byte[4];
            i = paramArrayOfByte.length;
            paramInt = 0;
          }
          catch (Exception parama)
          {
            byte[] arrayOfByte2;
            paramArrayOfByte = new StringBuilder();
            paramArrayOfByte.append("there are some exceptions when encrpting log data ..: ");
            paramArrayOfByte.append(parama.getMessage());
            Log.e("uploadlog", paramArrayOfByte.toString());
            return;
          }
          arrayOfByte2 = new byte[paramArrayOfByte.length + 2];
          arrayOfByte2[0] = arrayOfByte1[2];
          arrayOfByte2[1] = arrayOfByte1[3];
          System.arraycopy(paramArrayOfByte, 0, arrayOfByte2, 2, paramArrayOfByte.length);
          this.jdField_a_of_type_ComTencentSmttUtilMttLog$a = parama;
          this.jdField_a_of_type_ArrayOfByte = arrayOfByte2;
          this.jdField_a_of_type_Int = (paramArrayOfByte.length + 2);
          return;
          this.jdField_a_of_type_ComTencentSmttUtilMttLog$a = parama;
          this.jdField_a_of_type_ArrayOfByte = paramArrayOfByte;
          this.jdField_a_of_type_Int = paramInt;
          return;
          while (paramInt < 4)
          {
            arrayOfByte1[paramInt] = ((byte)(i >> (3 - paramInt) * 8 & 0xFF));
            paramInt += 1;
          }
        }
      }
    }
    
    private class b
      implements FilenameFilter
    {
      private b() {}
      
      public boolean accept(File paramFile, String paramString)
      {
        return paramString.startsWith(MttLog.a.d(MttLog.a.this));
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\MttLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */