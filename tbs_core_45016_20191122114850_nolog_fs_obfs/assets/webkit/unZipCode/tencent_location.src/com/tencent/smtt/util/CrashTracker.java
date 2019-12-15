package com.tencent.smtt.util;

import android.os.Build.VERSION;
import com.tencent.smtt.webkit.e;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.chromium.base.annotations.UsedByReflection;

public class CrashTracker
{
  private static long MAX_LOGCAT_TIME = 50L;
  private static int MAX_LOG_BUFFER_SIZE = 2048;
  
  @UsedByReflection("TbsWizard.java")
  public static String getCrashExtraInfo()
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(SmttServiceProxy.getInstance().getCrashExtraInfo());
    ((StringBuilder)localObject1).append("\n");
    String str = ((StringBuilder)localObject1).toString();
    Object localObject2 = CrashExtraMessage.getCrashExtraMessage();
    localObject1 = localObject2;
    if (localObject2 == null) {
      localObject1 = "Native crash info has exception, so losted.";
    }
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append(str);
    ((StringBuilder)localObject2).append(MttTimingLog.getUserActionsSummary());
    ((StringBuilder)localObject2).append((String)localObject1);
    str = ((StringBuilder)localObject2).toString();
    localObject2 = str;
    if (e.a().n())
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append(str);
      ((StringBuilder)localObject2).append(getGlErrLog((String)localObject1));
      localObject2 = ((StringBuilder)localObject2).toString();
    }
    ((String)localObject2).replaceAll("((?i)sid|uin|sec_sig|MOBINFO)=[^&#]+", "$1=****");
    if ((!e.a().n()) && (((String)localObject2).length() > 2800)) {
      return ((String)localObject2).substring(0, 2800);
    }
    return (String)localObject2;
  }
  
  public static String getGlErrLog(String paramString)
  {
    if (Build.VERSION.SDK_INT < 23) {
      return "skip low version\r\n";
    }
    Object localObject1 = a.d;
    if (paramString.contains("Adreno "))
    {
      paramString = a.a;
      localObject1 = new StringBuilder();
      try
      {
        long l = System.currentTimeMillis();
        Object localObject2 = Runtime.getRuntime().exec(new String[] { "logcat", "-t", "1300", "-v", "time", "-b", "main" });
        l = System.currentTimeMillis() - l;
        if (l > MAX_LOGCAT_TIME)
        {
          paramString = new StringBuilder();
          paramString.append("logcat need too long time:");
          paramString.append(l);
          paramString.append("\r\n");
          return paramString.toString();
        }
        localObject2 = new BufferedReader(new InputStreamReader(((Process)localObject2).getInputStream()));
        for (;;)
        {
          String str = ((BufferedReader)localObject2).readLine();
          if (str == null) {
            break;
          }
          if ((paramString == a.a) && ((str.contains("E/Adreno")) || (str.contains("W/Adreno")) || (str.contains("E/libEGL"))))
          {
            ((StringBuilder)localObject1).append(str);
            ((StringBuilder)localObject1).append("\r\n");
            int i = ((StringBuilder)localObject1).length();
            if (i > MAX_LOG_BUFFER_SIZE) {
              ((StringBuilder)localObject1).delete(0, i - MAX_LOG_BUFFER_SIZE);
            }
          }
        }
        if (((StringBuilder)localObject1).length() <= 0) {
          ((StringBuilder)localObject1).append("get driver info null\r\n");
        }
        return ((StringBuilder)localObject1).toString();
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
        return "logcat exception\r\n";
      }
    }
    return "only get Qualcomm info\r\n";
  }
  
  private static enum a
  {
    static
    {
      jdField_a_of_type_ComTencentSmttUtilCrashTracker$a = new a("TYPE_ADRENO", 0);
      b = new a("TYPE_MALI", 1);
      c = new a("TYPE_POWWEVR", 2);
      d = new a("TYPE_UNKNOWN", 3);
      jdField_a_of_type_ArrayOfComTencentSmttUtilCrashTracker$a = new a[] { jdField_a_of_type_ComTencentSmttUtilCrashTracker$a, b, c, d };
    }
    
    private a() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\CrashTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */