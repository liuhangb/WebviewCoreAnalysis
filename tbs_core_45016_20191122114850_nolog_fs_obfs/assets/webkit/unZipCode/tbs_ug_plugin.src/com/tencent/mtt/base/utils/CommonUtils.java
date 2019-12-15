package com.tencent.mtt.base.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.tencent.basesupport.FLogger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CommonUtils
{
  public static final int DATE_TYPE_LONGAGO = 4;
  public static final int DATE_TYPE_TODAY = 1;
  public static final int DATE_TYPE_YESTERDAY = 2;
  public static final int DATE_TYPE_YESTERDAYBEFORE = 3;
  private static Timestamp jdField_a_of_type_JavaSqlTimestamp;
  private static SimpleDateFormat jdField_a_of_type_JavaTextSimpleDateFormat;
  
  public static int GetLightness(Activity paramActivity)
  {
    try
    {
      paramActivity = paramActivity.getWindow().getAttributes();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("读取到亮度值为");
      localStringBuilder.append((int)(paramActivity.screenBrightness * 255.0F));
      FLogger.d("CommonUtils", localStringBuilder.toString());
      float f = paramActivity.screenBrightness;
      return (int)(f * 255.0F);
    }
    catch (Exception paramActivity)
    {
      paramActivity.printStackTrace();
      FLogger.e("CommonUtils", paramActivity);
      FLogger.d("CommonUtils", "读取到亮度值失败，默认返回-255");
    }
    return 65281;
  }
  
  public static void SetLightness(Window paramWindow, int paramInt)
  {
    for (;;)
    {
      int i;
      try
      {
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("SetLightness value=");
        ((StringBuilder)localObject).append(paramInt);
        ((StringBuilder)localObject).append(" window=");
        ((StringBuilder)localObject).append(paramWindow);
        FLogger.d("CommonUtils", ((StringBuilder)localObject).toString());
        float f = paramInt;
        i = 255;
        if (f < 0.0F)
        {
          paramInt = 65281;
          continue;
          paramInt = Math.max(40, paramInt);
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("SetLightness亮度被矫正为");
          ((StringBuilder)localObject).append(paramInt);
          FLogger.d("CommonUtils", ((StringBuilder)localObject).toString());
          localObject = paramWindow.getAttributes();
          ((WindowManager.LayoutParams)localObject).screenBrightness = (paramInt / 255.0F);
          paramWindow.setAttributes((WindowManager.LayoutParams)localObject);
          return;
        }
      }
      catch (Exception paramWindow)
      {
        paramWindow.printStackTrace();
        FLogger.e("CommonUtils", paramWindow);
        return;
      }
      if (paramInt > 255) {
        paramInt = i;
      }
    }
  }
  
  public static void checkIntent(Intent paramIntent)
  {
    if (paramIntent == null) {
      return;
    }
    try
    {
      paramIntent.getBooleanExtra("check", false);
    }
    catch (Exception localException2)
    {
      for (;;) {}
    }
    FLogger.d("CommonUtils", "invalid intent");
    try
    {
      Bundle localBundle = paramIntent.getExtras();
      if (localBundle != null)
      {
        localBundle.getBoolean("check", false);
        return;
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      paramIntent.replaceExtras(new Bundle());
    }
  }
  
  public static String dateToString(long paramLong, String paramString)
  {
    try
    {
      if (jdField_a_of_type_JavaSqlTimestamp == null) {
        jdField_a_of_type_JavaSqlTimestamp = new Timestamp(paramLong);
      } else {
        jdField_a_of_type_JavaSqlTimestamp.setTime(paramLong);
      }
      if (jdField_a_of_type_JavaTextSimpleDateFormat == null) {
        jdField_a_of_type_JavaTextSimpleDateFormat = new SimpleDateFormat(paramString);
      } else {
        jdField_a_of_type_JavaTextSimpleDateFormat.applyPattern(paramString);
      }
      paramString = jdField_a_of_type_JavaTextSimpleDateFormat.format(jdField_a_of_type_JavaSqlTimestamp);
      return paramString;
    }
    finally {}
  }
  
  public static String findFullHost(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      int j = paramString.indexOf("//");
      int i = 0;
      if (j < 0)
      {
        if (paramString.charAt(0) == '.') {
          i = 1;
        }
      }
      else {
        i = j + 2;
      }
      j = paramString.indexOf('/', i);
      int k = j;
      if (j < 0) {
        k = paramString.length();
      }
      j = paramString.indexOf('?', i);
      int m = j;
      if (j < 0) {
        m = paramString.length();
      }
      j = k;
      if (m < k) {
        j = m;
      }
      m = paramString.indexOf(':', i);
      k = m;
      if (m < 0) {
        k = paramString.length();
      }
      m = j;
      if (k < j) {
        m = k;
      }
      return paramString.substring(i, m);
    }
    return null;
  }
  
  public static String formatShortFileSize(long paramLong)
  {
    float f2 = (float)paramLong;
    String str1 = "B";
    float f1 = f2;
    if (f2 > 900.0F)
    {
      str1 = "KB";
      f1 = f2 / 1024.0F;
    }
    f2 = f1;
    if (f1 > 900.0F)
    {
      str1 = "MB";
      f2 = f1 / 1024.0F;
    }
    f1 = f2;
    if (f2 > 900.0F)
    {
      str1 = "GB";
      f1 = f2 / 1024.0F;
    }
    f2 = f1;
    if (f1 > 900.0F)
    {
      str1 = "TB";
      f2 = f1 / 1024.0F;
    }
    f1 = f2;
    String str2 = str1;
    if (f2 > 900.0F)
    {
      str2 = "PB";
      f1 = f2 / 1024.0F;
    }
    if (f1 < 1.0F) {
      str1 = String.format("%.2f", new Object[] { Float.valueOf(f1) });
    } else if (f1 < 10.0F) {
      str1 = String.format("%.2f", new Object[] { Float.valueOf(f1) });
    } else if (f1 < 100.0F) {
      str1 = String.format("%.1f", new Object[] { Float.valueOf(f1) });
    } else {
      str1 = String.format("%.0f", new Object[] { Float.valueOf(f1) });
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(str1);
    localStringBuilder.append(str2);
    return localStringBuilder.toString();
  }
  
  public static String formatStringDate(String paramString1, String paramString2)
  {
    Object localObject;
    try
    {
      Date localDate = getDateByString(paramString1);
    }
    catch (ParseException localParseException)
    {
      localParseException.printStackTrace();
      localObject = null;
    }
    if (localObject == null) {
      return paramString1;
    }
    paramString1 = new Timestamp(((Date)localObject).getTime());
    return new SimpleDateFormat(paramString2).format(paramString1);
  }
  
  public static Calendar getCalendar(int paramInt)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.set(11, 0);
    localCalendar.set(12, 0);
    localCalendar.set(13, 0);
    localCalendar.set(14, 0);
    if (paramInt != 0) {
      localCalendar.add(5, paramInt);
    }
    return localCalendar;
  }
  
  public static String getDate()
  {
    try
    {
      String str = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }
  
  public static String getDate(long paramLong)
  {
    try
    {
      Object localObject = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      if (paramLong == 0L) {
        return ((SimpleDateFormat)localObject).format(new Date(System.currentTimeMillis()));
      }
      localObject = ((SimpleDateFormat)localObject).format(new Date(paramLong));
      return (String)localObject;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }
  
  public static String getDate(long paramLong, String paramString)
  {
    try
    {
      paramString = new SimpleDateFormat(paramString);
      if (paramLong == 0L) {
        return paramString.format(new Date(System.currentTimeMillis()));
      }
      paramString = paramString.format(new Date(paramLong));
      return paramString;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return "";
  }
  
  public static Date getDateByString(String paramString)
    throws ParseException
  {
    if (paramString == null) {
      return null;
    }
    return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(paramString);
  }
  
  public static Calendar getDateCalender(long paramLong)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramLong);
    return localCalendar;
  }
  
  public static String getDateString(Calendar paramCalendar)
  {
    long l = paramCalendar.getTimeInMillis();
    return new SimpleDateFormat("M月d日", Locale.CHINESE).format(new Date(l));
  }
  
  public static int getDateType(Calendar paramCalendar)
  {
    long l = paramCalendar.getTimeInMillis();
    l = getTodayLastTimeMillis() - l;
    if ((0L <= l) && (l < 86400000L)) {
      return 1;
    }
    if ((86400000L <= l) && (l < 172800000L)) {
      return 2;
    }
    if ((172800000L <= l) && (l < 259200000L)) {
      return 3;
    }
    return 4;
  }
  
  public static String getGMTData(long paramLong)
  {
    try
    {
      Object localObject = new Date(paramLong);
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("EEE, d-MMM-yy HH:mm:ss", Locale.ENGLISH);
      localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(localSimpleDateFormat.format((Date)localObject));
      localStringBuilder.append(" GMT");
      localObject = localStringBuilder.toString();
      return (String)localObject;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }
  
  public static long getTodayLastTimeMillis()
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(System.currentTimeMillis());
    localCalendar.set(11, 23);
    localCalendar.set(12, 59);
    localCalendar.set(13, 59);
    localCalendar.set(14, 999);
    return localCalendar.getTimeInMillis();
  }
  
  public static boolean isTheSameDay(long paramLong1, long paramLong2)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramLong1);
    int i = localCalendar.get(1);
    int j = localCalendar.get(2);
    int k = localCalendar.get(5);
    localCalendar = Calendar.getInstance();
    localCalendar.setTimeInMillis(paramLong2);
    int m = localCalendar.get(1);
    int n = localCalendar.get(2);
    int i1 = localCalendar.get(5);
    return (i == m) && (j == n) && (k == i1);
  }
  
  public static String jsonEncode(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return paramString;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 0;
    while (i < paramString.length())
    {
      char c = paramString.charAt(i);
      if (c != '\n')
      {
        if (c != '\r')
        {
          if (c != '"')
          {
            if (c != '<')
            {
              if (c != '>') {
                switch (c)
                {
                default: 
                  localStringBuilder.append(c);
                  break;
                case '\'': 
                  localStringBuilder.append("&#39;");
                  break;
                case '&': 
                  localStringBuilder.append("&amp;");
                  break;
                }
              } else {
                localStringBuilder.append("&gt;");
              }
            }
            else {
              localStringBuilder.append("&lt;");
            }
          }
          else {
            localStringBuilder.append("&quot;");
          }
        }
        else {
          localStringBuilder.append("\\r");
        }
      }
      else {
        localStringBuilder.append("\\n");
      }
      i += 1;
    }
    return localStringBuilder.toString();
  }
  
  public static void killProcess()
  {
    FLogger.d("CommonUtils", "killProcess...");
    Process.killProcess(Process.myPid());
  }
  
  public static String longToDate(Long paramLong)
  {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss").format(paramLong);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\CommonUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */