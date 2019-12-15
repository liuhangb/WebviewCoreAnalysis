package com.tencent.tbs.common.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.tencent.common.utils.DesUtils;
import com.tencent.mtt.base.utils.Cryptor;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtils
{
  public static int GetLightness(Activity paramActivity)
  {
    try
    {
      float f = paramActivity.getWindow().getAttributes().screenBrightness;
      return (int)(f * 255.0F);
    }
    catch (Exception paramActivity)
    {
      paramActivity.printStackTrace();
    }
    return 65281;
  }
  
  public static void SetLightness(Window paramWindow, int paramInt)
  {
    float f = paramInt;
    int i = 255;
    if (f < 0.0F) {
      paramInt = 65281;
    } else if (paramInt > 255) {
      paramInt = i;
    }
    try
    {
      paramInt = Math.max(40, paramInt);
      WindowManager.LayoutParams localLayoutParams = paramWindow.getAttributes();
      localLayoutParams.screenBrightness = (paramInt / 255.0F);
      paramWindow.setAttributes(localLayoutParams);
      return;
    }
    catch (Exception paramWindow)
    {
      paramWindow.printStackTrace();
    }
  }
  
  public static long combine(short paramShort1, short paramShort2)
  {
    long l = paramShort1;
    return 0xFFFF & paramShort2 | (l & 0xFFFF) << 16;
  }
  
  public static String dateToString(Date paramDate, String paramString)
  {
    paramDate = new Timestamp(paramDate.getTime());
    return new SimpleDateFormat(paramString).format(paramDate);
  }
  
  public static byte[] decryption(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    try
    {
      paramArrayOfByte = new Cryptor().decrypt(paramArrayOfByte, DesUtils.KEY);
      return paramArrayOfByte;
    }
    catch (Exception paramArrayOfByte) {}
    return null;
  }
  
  public static short[] divide(long paramLong)
  {
    int i = (short)(int)(paramLong & 0xFFFF);
    return new short[] { (short)(int)(paramLong >> 16 & 0xFFFF), i };
  }
  
  public static byte[] encryption(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    try
    {
      paramArrayOfByte = new Cryptor().encrypt(paramArrayOfByte, DesUtils.KEY);
      return paramArrayOfByte;
    }
    catch (Exception paramArrayOfByte)
    {
      paramArrayOfByte.printStackTrace();
    }
    return null;
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
  
  public static long formatReadDateToMillis(String paramString)
  {
    try
    {
      paramString = getDateByString(paramString);
      if (paramString != null)
      {
        long l = paramString.getTime();
        return l;
      }
    }
    catch (ParseException paramString)
    {
      paramString.printStackTrace();
    }
    return 0L;
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
  
  public static Date getMBDateByString(String paramString)
    throws ParseException
  {
    if (paramString == null) {
      return null;
    }
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(paramString);
  }
  
  public static String getMBShortTime(String paramString)
  {
    try
    {
      Date localDate = getMBDateByString(paramString);
      if (localDate != null) {
        l = localDate.getTime();
      } else {
        l = 0L;
      }
    }
    catch (ParseException localParseException)
    {
      localParseException.printStackTrace();
      l = 0L;
    }
    if (l == 0L) {
      return paramString;
    }
    long l = (Calendar.getInstance().getTimeInMillis() - l) / 1000L;
    if (l > 604800L) {
      return formatStringDate(paramString, "yyyy-MM-dd");
    }
    if (l > 86400L)
    {
      paramString = new StringBuilder();
      paramString.append((int)(l / 86400L));
      paramString.append("天前");
      return paramString.toString();
    }
    if (l > 3600L)
    {
      paramString = new StringBuilder();
      paramString.append((int)(l / 3600L));
      paramString.append("小时前");
      return paramString.toString();
    }
    if (l > 60L)
    {
      paramString = new StringBuilder();
      paramString.append((int)(l / 60L));
      paramString.append("分前");
      return paramString.toString();
    }
    if (l > 1L) {
      paramString = "刚刚";
    }
    return paramString;
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
  
  public static boolean isValid(int paramInt1, int paramInt2)
  {
    int i = (int)(System.currentTimeMillis() / 1000L);
    return (i >= paramInt1) && (i <= paramInt2);
  }
  
  public static String longToDate(Long paramLong)
  {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss").format(paramLong);
  }
  
  public static int safeAbsHashCode(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return 0;
    }
    int i = paramString.hashCode();
    if (i == Integer.MIN_VALUE) {
      return Integer.MAX_VALUE;
    }
    return Math.abs(i);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\CommonUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */