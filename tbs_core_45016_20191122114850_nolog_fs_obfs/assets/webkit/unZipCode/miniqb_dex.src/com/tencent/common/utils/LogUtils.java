package com.tencent.common.utils;

import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogUtils
{
  public static final boolean FILE_LOGABLE = true;
  public static final byte LOG_BOTH = 3;
  public static final byte LOG_CONSOLE = 1;
  public static final byte LOG_FILE = 2;
  private static byte jdField_a_of_type_Byte = 1;
  private static a jdField_a_of_type_ComTencentCommonUtilsLogUtils$a;
  private static Map<String, Long> jdField_a_of_type_JavaUtilMap = new ConcurrentHashMap();
  
  static
  {
    init();
  }
  
  public static void E(String paramString1, String paramString2)
  {
    Log.e(paramString1, paramString2);
  }
  
  private static void a(String paramString)
  {
    if (!init()) {
      return;
    }
    long l = System.currentTimeMillis();
    Message localMessage = jdField_a_of_type_ComTencentCommonUtilsLogUtils$a.obtainMessage();
    localMessage.obj = paramString;
    localMessage.arg1 = ((int)(l >> 32 & 0xFFFFFFFF));
    localMessage.arg2 = ((int)(l & 0xFFFFFFFF));
    jdField_a_of_type_ComTencentCommonUtilsLogUtils$a.sendMessage(localMessage);
  }
  
  public static void d(String paramString1, String paramString2)
  {
    d(paramString1, paramString2, jdField_a_of_type_Byte);
  }
  
  public static void d(String paramString1, String paramString2, int paramInt)
  {
    String str = paramString2;
    if (paramString2 == null) {
      str = "NULL MSG";
    }
    switch (paramInt)
    {
    default: 
      
    case 3: 
      paramString2 = new StringBuilder();
      paramString2.append(paramString1);
      paramString2.append("\t");
      paramString2.append(str);
      a(paramString2.toString());
      return;
    case 2: 
      paramString2 = new StringBuilder();
      paramString2.append(paramString1);
      paramString2.append("\t");
      paramString2.append(str);
      a(paramString2.toString());
    }
  }
  
  public static void e(String paramString1, String paramString2)
  {
    d(paramString1, paramString2, jdField_a_of_type_Byte);
  }
  
  public static void e(String paramString, Throwable paramThrowable)
  {
    if (paramThrowable == null) {
      return;
    }
    StackTraceElement[] arrayOfStackTraceElement = new Throwable().getStackTrace();
    Object localObject = null;
    if (arrayOfStackTraceElement.length > 1)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("class : ");
      ((StringBuilder)localObject).append(arrayOfStackTraceElement[1].getClassName());
      ((StringBuilder)localObject).append("; line : ");
      ((StringBuilder)localObject).append(arrayOfStackTraceElement[1].getLineNumber());
      localObject = ((StringBuilder)localObject).toString();
    }
    Log.e(paramString, (String)localObject, paramThrowable);
  }
  
  public static boolean getIsLogged()
  {
    return jdField_a_of_type_Byte != 0;
  }
  
  public static File getLogFile()
  {
    File localFile = FileUtilsF.getSDcardDir();
    if ((localFile != null) && (localFile.exists()))
    {
      localFile = new File(localFile, "QQBrowser");
      if (!localFile.exists()) {
        localFile.mkdirs();
      }
      return new File(localFile, "log.dat");
    }
    return null;
  }
  
  public static void i(String paramString1, String paramString2)
  {
    d(paramString1, paramString2, jdField_a_of_type_Byte);
  }
  
  public static boolean init()
  {
    if ((jdField_a_of_type_ComTencentCommonUtilsLogUtils$a == null) && (jdField_a_of_type_Byte > 1)) {}
    try
    {
      jdField_a_of_type_ComTencentCommonUtilsLogUtils$a = new a();
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    return jdField_a_of_type_ComTencentCommonUtilsLogUtils$a != null;
  }
  
  public static void printCostTime(String paramString1, String paramString2, String paramString3)
  {
    long l1;
    if (jdField_a_of_type_JavaUtilMap.containsKey(paramString3))
    {
      localObject = (Long)jdField_a_of_type_JavaUtilMap.get(paramString3);
      if (localObject == null) {
        return;
      }
      l1 = ((Long)localObject).longValue();
    }
    else
    {
      l1 = 0L;
    }
    long l2 = System.currentTimeMillis();
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append(", cost time:");
    ((StringBuilder)localObject).append(l2 - l1);
    d(paramString1, ((StringBuilder)localObject).toString());
    jdField_a_of_type_JavaUtilMap.put(paramString3, Long.valueOf(l2));
  }
  
  public static void setIsLogged(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      jdField_a_of_type_Byte = 1;
      return;
    }
    jdField_a_of_type_Byte = 0;
  }
  
  public static void startTiming(String paramString)
  {
    long l = System.currentTimeMillis();
    jdField_a_of_type_JavaUtilMap.put(paramString, Long.valueOf(l));
  }
  
  public static void t(String paramString1, String paramString2)
  {
    d(paramString1, paramString2, jdField_a_of_type_Byte);
  }
  
  public static void timeStamp(Exception paramException, String paramString)
  {
    paramException = paramException.getStackTrace()[0];
    String str1 = paramException.getClassName();
    String str2 = paramException.getMethodName();
    int i = paramException.getLineNumber();
    if (paramString == null)
    {
      paramException = "";
    }
    else
    {
      paramException = new StringBuilder();
      paramException.append(paramString);
      paramException.append("-");
      paramException = paramException.toString();
    }
    paramString = new StringBuilder();
    paramString.append(paramException);
    paramString.append(str1);
    paramString.append(".");
    paramString.append(str2);
    paramString.append("():");
    paramString.append(i);
    paramString.toString();
  }
  
  public static void w(String paramString1, String paramString2)
  {
    d(paramString1, paramString2, jdField_a_of_type_Byte);
  }
  
  public static void writeLog(String paramString1, String paramString2)
  {
    if (FileUtilsF.hasSDcard()) {
      try
      {
        paramString1 = new PrintWriter(new FileOutputStream(new File(paramString1), true));
        paramString1.println(paramString2);
        paramString1.flush();
        paramString1.close();
        return;
      }
      catch (FileNotFoundException paramString1)
      {
        paramString1.getMessage();
      }
    }
  }
  
  private static class a
    extends Handler
  {
    Time jdField_a_of_type_AndroidTextFormatTime = new Time();
    private File jdField_a_of_type_JavaIoFile;
    private FileOutputStream jdField_a_of_type_JavaIoFileOutputStream;
    private boolean jdField_a_of_type_Boolean = true;
    
    a()
    {
      if (this.jdField_a_of_type_Boolean) {}
      try
      {
        this.jdField_a_of_type_JavaIoFile = LogUtils.getLogFile();
        if ((this.jdField_a_of_type_JavaIoFile != null) && (!this.jdField_a_of_type_JavaIoFile.exists())) {
          this.jdField_a_of_type_JavaIoFile.createNewFile();
        }
        return;
      }
      catch (IOException localIOException) {}
    }
    
    FileOutputStream a()
      throws Exception
    {
      if (this.jdField_a_of_type_JavaIoFileOutputStream == null) {
        this.jdField_a_of_type_JavaIoFileOutputStream = new FileOutputStream(this.jdField_a_of_type_JavaIoFile, true);
      }
      return this.jdField_a_of_type_JavaIoFileOutputStream;
    }
    
    public void handleMessage(Message paramMessage)
    {
      if (!this.jdField_a_of_type_Boolean) {
        return;
      }
      try
      {
        long l1 = paramMessage.arg1;
        l1 = 0xFFFFFFFF & paramMessage.arg2 | l1 << 32 & 0xFFFFFFFF00000000;
        this.jdField_a_of_type_AndroidTextFormatTime.set(l1);
        long l2 = l1 % 1000L;
        l1 = l2;
        if (l2 < 0L) {
          l1 = l2 + 1000L;
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(this.jdField_a_of_type_AndroidTextFormatTime.format("%Y-%m-%d %H:%M:%S"));
        localStringBuilder.append(String.format(".%03d\t", new Object[] { Integer.valueOf((int)l1) }));
        localStringBuilder.append((String)paramMessage.obj);
        localStringBuilder.append("\n");
        paramMessage = localStringBuilder.toString();
        if (paramMessage != null)
        {
          paramMessage = paramMessage.getBytes();
          a().write(paramMessage, 0, paramMessage.length);
        }
        return;
      }
      catch (Exception paramMessage) {}
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\LogUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */