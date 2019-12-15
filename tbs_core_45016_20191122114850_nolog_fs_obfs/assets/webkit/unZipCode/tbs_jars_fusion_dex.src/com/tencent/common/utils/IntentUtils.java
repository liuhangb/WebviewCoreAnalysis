package com.tencent.common.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Process;
import android.text.TextUtils;
import android.util.SparseArray;
import com.tencent.basesupport.FLogger;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IntentUtils
{
  public static final String MTT_ACTION = "com.tencent.QQBrowser.action.VIEW";
  public static final String QQBROWSER_PARAMS_DOWNLOADCOOKIE = ",downloadcookie=";
  public static final String QQBROWSER_PARAMS_DOWNLOADFILENAME = ",downloadfilename=";
  public static final String QQBROWSER_PARAMS_DOWNLOADMINE = ",downloadmimetype=";
  public static final String QQBROWSER_PARAMS_DOWNLOADURL = ",downloadurl=";
  public static final String QQBROWSER_PARAMS_ENCODE = ",encoded=1";
  public static final String QQBROWSER_PARAMS_FORCE_DOWNLOAD = ",forcedownload=";
  public static final String QQBROWSER_PARAMS_FROME = ",from=";
  public static final String QQBROWSER_PARAMS_PACKAGENAME = ",packagename=";
  public static final String QQBROWSER_PARAMS_PD = ",product=";
  public static final String QQBROWSER_PARAMS_VERSION = ",version=";
  public static final String QQBROWSER_SCHEME = "mttbrowser://url=";
  private static String a = "";
  
  private static int a(Context paramContext)
  {
    paramContext = getCurProcessName(paramContext);
    if (!TextUtils.isEmpty(paramContext))
    {
      if (paramContext.contains("com.tencent.mobileqq")) {
        return 2;
      }
      if (paramContext.contains("com.tencent.mm")) {
        return 24;
      }
      if (paramContext.contains("com.tencent.wblog")) {
        return 4;
      }
      if (paramContext.contains("com.tencent.qzone")) {
        return 3;
      }
    }
    return 12;
  }
  
  private static a a(Context paramContext)
  {
    Object localObject = new Intent("com.tencent.QQBrowser.action.VIEW");
    ((Intent)localObject).setData(Uri.parse("http://"));
    localObject = paramContext.getPackageManager().queryIntentActivities((Intent)localObject, 0);
    if (((List)localObject).size() <= 0) {
      return null;
    }
    paramContext = new a(null);
    localObject = ((List)localObject).iterator();
    while (((Iterator)localObject).hasNext())
    {
      ResolveInfo localResolveInfo = (ResolveInfo)((Iterator)localObject).next();
      if (localResolveInfo.activityInfo.packageName.contains("com.tencent.mtt"))
      {
        paramContext.a = localResolveInfo.activityInfo.name;
        paramContext.b = localResolveInfo.activityInfo.packageName;
        return paramContext;
      }
    }
    return paramContext;
  }
  
  public static void fillPackageName(Intent paramIntent)
  {
    if (paramIntent == null) {
      return;
    }
    if (Integer.parseInt(Build.VERSION.SDK) >= 21) {
      paramIntent.setPackage("com.tencent.mtt");
    }
  }
  
  public static boolean getBoolean(Bundle paramBundle, String paramString, boolean paramBoolean)
  {
    if (paramBundle == null) {
      return paramBoolean;
    }
    try
    {
      boolean bool = paramBundle.getBoolean(paramString, paramBoolean);
      return bool;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getBoolean Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return paramBoolean;
  }
  
  public static boolean[] getBooleanArray(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return null;
    }
    try
    {
      paramBundle = paramBundle.getBooleanArray(paramString);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getBooleanArray Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static boolean[] getBooleanArrayExtra(Intent paramIntent, String paramString)
  {
    try
    {
      paramIntent = paramIntent.getBooleanArrayExtra(paramString);
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getBooleanArrayExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static boolean getBooleanExtra(Intent paramIntent, String paramString, boolean paramBoolean)
  {
    try
    {
      boolean bool = paramIntent.getBooleanExtra(paramString, paramBoolean);
      return bool;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getBooleanExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return paramBoolean;
  }
  
  public static Bundle getBundleExtra(Intent paramIntent, String paramString)
  {
    try
    {
      paramIntent = paramIntent.getBundleExtra(paramString);
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getBundleExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static byte getByte(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return 0;
    }
    try
    {
      byte b = paramBundle.getByte(paramString);
      return b;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getByte Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return 0;
  }
  
  public static Byte getByte(Bundle paramBundle, String paramString, byte paramByte)
  {
    if (paramBundle == null) {
      return Byte.valueOf(paramByte);
    }
    try
    {
      paramBundle = paramBundle.getByte(paramString, paramByte);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getByte Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return Byte.valueOf(paramByte);
  }
  
  public static byte[] getByteArray(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return null;
    }
    try
    {
      paramBundle = paramBundle.getByteArray(paramString);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getByteArray Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static byte[] getByteArrayExtra(Intent paramIntent, String paramString)
  {
    try
    {
      paramIntent = paramIntent.getByteArrayExtra(paramString);
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getByteArrayExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static byte getByteExtra(Intent paramIntent, String paramString, byte paramByte)
  {
    try
    {
      byte b = paramIntent.getByteExtra(paramString, paramByte);
      return b;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getByteExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return paramByte;
  }
  
  public static char getChar(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return '\000';
    }
    try
    {
      char c = paramBundle.getChar(paramString);
      return c;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getChar Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return '\000';
  }
  
  public static char getChar(Bundle paramBundle, String paramString, char paramChar)
  {
    if (paramBundle == null) {
      return paramChar;
    }
    try
    {
      char c = paramBundle.getChar(paramString, paramChar);
      return c;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getChar Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return paramChar;
  }
  
  public static char[] getCharArray(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return null;
    }
    try
    {
      paramBundle = paramBundle.getCharArray(paramString);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getCharArray Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static char[] getCharArrayExtra(Intent paramIntent, String paramString)
  {
    try
    {
      paramIntent = paramIntent.getCharArrayExtra(paramString);
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getCharArrayExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static char getCharExtra(Intent paramIntent, String paramString, char paramChar)
  {
    try
    {
      char c = paramIntent.getCharExtra(paramString, paramChar);
      return c;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getCharExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return paramChar;
  }
  
  public static CharSequence[] getCharSequenceArray(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return null;
    }
    try
    {
      paramBundle = paramBundle.getCharSequenceArray(paramString);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getCharSequenceArray Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static CharSequence[] getCharSequenceArrayExtra(Intent paramIntent, String paramString)
  {
    try
    {
      paramIntent = paramIntent.getCharSequenceArrayExtra(paramString);
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getCharSequenceArrayExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static ArrayList<CharSequence> getCharSequenceArrayList(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return null;
    }
    try
    {
      paramBundle = paramBundle.getCharSequenceArrayList(paramString);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getCharSequenceArrayList Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static ArrayList<CharSequence> getCharSequenceArrayListExtra(Intent paramIntent, String paramString)
  {
    try
    {
      paramIntent = paramIntent.getCharSequenceArrayListExtra(paramString);
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getCharSequenceArrayListExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static CharSequence getCharSequenceExtra(Intent paramIntent, String paramString)
  {
    try
    {
      paramIntent = paramIntent.getCharSequenceExtra(paramString);
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getCharSequenceExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static String getCurProcessName(Context paramContext)
  {
    int i = Process.myPid();
    paramContext = getRunningAppProcess(paramContext);
    if (paramContext != null)
    {
      paramContext = paramContext.iterator();
      while (paramContext.hasNext())
      {
        ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)paramContext.next();
        if ((localRunningAppProcessInfo != null) && (localRunningAppProcessInfo.pid == i)) {
          return localRunningAppProcessInfo.processName;
        }
      }
    }
    return "";
  }
  
  public static double getDouble(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return 0.0D;
    }
    try
    {
      double d = paramBundle.getDouble(paramString);
      return d;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getDouble Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return 0.0D;
  }
  
  public static double getDouble(Bundle paramBundle, String paramString, double paramDouble)
  {
    if (paramBundle == null) {
      return paramDouble;
    }
    try
    {
      double d = paramBundle.getDouble(paramString, paramDouble);
      return d;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getFloat Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return paramDouble;
  }
  
  public static double[] getDoubleArray(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return null;
    }
    try
    {
      paramBundle = paramBundle.getDoubleArray(paramString);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getDoubleArray Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static double[] getDoubleArrayExtra(Intent paramIntent, String paramString)
  {
    try
    {
      paramIntent = paramIntent.getDoubleArrayExtra(paramString);
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getDoubleArrayExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static double getDoubleExtra(Intent paramIntent, String paramString, double paramDouble)
  {
    try
    {
      double d = paramIntent.getDoubleExtra(paramString, paramDouble);
      return d;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getDoubleExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return paramDouble;
  }
  
  public static Bundle getExtras(Intent paramIntent)
  {
    try
    {
      paramIntent = paramIntent.getExtras();
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getExtras Throwable");
      localStringBuilder.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", localStringBuilder.toString());
    }
    return null;
  }
  
  public static float getFloat(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return 0.0F;
    }
    try
    {
      float f = paramBundle.getFloat(paramString);
      return f;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getFloat Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return 0.0F;
  }
  
  public static float getFloat(Bundle paramBundle, String paramString, float paramFloat)
  {
    if (paramBundle == null) {
      return paramFloat;
    }
    try
    {
      float f = paramBundle.getFloat(paramString, paramFloat);
      return f;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getFloat Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return paramFloat;
  }
  
  public static float[] getFloatArray(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return null;
    }
    try
    {
      paramBundle = paramBundle.getFloatArray(paramString);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getFloatArray Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static float[] getFloatArrayExtra(Intent paramIntent, String paramString)
  {
    try
    {
      paramIntent = paramIntent.getFloatArrayExtra(paramString);
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getFloatArrayExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static float getFloatExtra(Intent paramIntent, String paramString, float paramFloat)
  {
    try
    {
      float f = paramIntent.getFloatExtra(paramString, paramFloat);
      return f;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getFloatExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return paramFloat;
  }
  
  public static int getInt(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return 0;
    }
    try
    {
      int i = paramBundle.getInt(paramString);
      return i;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getInt Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return 0;
  }
  
  public static int getInt(Bundle paramBundle, String paramString, int paramInt)
  {
    if (paramBundle == null) {
      return paramInt;
    }
    try
    {
      int i = paramBundle.getInt(paramString, paramInt);
      return i;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getInt Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return paramInt;
  }
  
  public static int[] getIntArray(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return null;
    }
    try
    {
      paramBundle = paramBundle.getIntArray(paramString);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getIntArray Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static int[] getIntArrayExtra(Intent paramIntent, String paramString)
  {
    try
    {
      paramIntent = paramIntent.getIntArrayExtra(paramString);
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getIntArrayExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static int getIntExtra(Intent paramIntent, String paramString, int paramInt)
  {
    try
    {
      int i = paramIntent.getIntExtra(paramString, paramInt);
      return i;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getIntExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return paramInt;
  }
  
  public static ArrayList<Integer> getIntegerArrayList(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return null;
    }
    try
    {
      paramBundle = paramBundle.getIntegerArrayList(paramString);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getIntegerArrayList Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static ArrayList<Integer> getIntegerArrayListExtra(Intent paramIntent, String paramString)
  {
    try
    {
      paramIntent = paramIntent.getIntegerArrayListExtra(paramString);
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getIntegerArrayListExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static long getLong(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return 0L;
    }
    try
    {
      long l = paramBundle.getLong(paramString);
      return l;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getLong Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return 0L;
  }
  
  public static long getLong(Bundle paramBundle, String paramString, long paramLong)
  {
    if (paramBundle == null) {
      return paramLong;
    }
    try
    {
      long l = paramBundle.getLong(paramString, paramLong);
      return l;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getLong Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return paramLong;
  }
  
  public static long[] getLongArray(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return null;
    }
    try
    {
      paramBundle = paramBundle.getLongArray(paramString);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getLongArray Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static long[] getLongArrayExtra(Intent paramIntent, String paramString)
  {
    try
    {
      paramIntent = paramIntent.getLongArrayExtra(paramString);
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getLongArrayExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static long getLongExtra(Intent paramIntent, String paramString, long paramLong)
  {
    try
    {
      long l = paramIntent.getLongExtra(paramString, paramLong);
      return l;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getLongExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return paramLong;
  }
  
  public static Intent getOpenUrlAndDownloadInQQBrowserWithReport(Context paramContext, boolean paramBoolean, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    boolean bool;
    int i;
    try
    {
      bool = isEncode(paramContext);
      str = paramString1;
      if (!TextUtils.isEmpty(paramString1))
      {
        str = paramString1;
        if (bool) {
          str = URLEncoder.encode(paramString1, "UTF-8");
        }
      }
      paramString1 = paramString2;
      if (!TextUtils.isEmpty(paramString2))
      {
        paramString1 = paramString2;
        if (bool) {
          paramString1 = URLEncoder.encode(paramString2, "UTF-8");
        }
      }
      paramString2 = paramString3;
      if (!TextUtils.isEmpty(paramString3))
      {
        paramString2 = paramString3;
        if (bool) {
          paramString2 = URLEncoder.encode(paramString3, "UTF-8");
        }
      }
      paramString3 = paramString4;
      if (!TextUtils.isEmpty(paramString4))
      {
        paramString3 = paramString4;
        if (bool) {
          paramString3 = URLEncoder.encode(paramString4, "UTF-8");
        }
      }
      paramString4 = paramString5;
      if (TextUtils.isEmpty(paramString5)) {
        break label532;
      }
      paramString4 = paramString5;
      if (!bool) {
        break label532;
      }
      paramString4 = URLEncoder.encode(paramString5, "UTF-8");
    }
    catch (Exception paramContext)
    {
      String str;
      StringBuilder localStringBuilder;
      paramContext.printStackTrace();
      return null;
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("mttbrowser://url=");
    localStringBuilder.append(str);
    localStringBuilder.append(",downloadfilename=");
    localStringBuilder.append(paramString3);
    localStringBuilder.append(",forcedownload=");
    if (paramBoolean) {
      i = 1;
    }
    for (;;)
    {
      localStringBuilder.append(i);
      localStringBuilder.append(",downloadcookie=");
      localStringBuilder.append(paramString2);
      localStringBuilder.append(",downloadmimetype=");
      localStringBuilder.append(paramString4);
      localStringBuilder.append(",downloadurl=");
      localStringBuilder.append(paramString1);
      localStringBuilder.append(",product=");
      localStringBuilder.append("TBS");
      localStringBuilder.append(",packagename=");
      localStringBuilder.append(paramContext.getApplicationContext().getPackageName());
      localStringBuilder.append(",from=");
      localStringBuilder.append(paramString6);
      localStringBuilder.append(",version=");
      localStringBuilder.append(a);
      localStringBuilder.append(paramString5);
      paramContext = new StringBuilder();
      paramContext.append("openUrlInQQBrowserWithReport--StringBuilder = ");
      paramContext.append(localStringBuilder.toString());
      FLogger.d("IntentUtils", paramContext.toString());
      paramString1 = new Intent("android.intent.action.VIEW");
      try
      {
        paramContext = Uri.parse(localStringBuilder.toString());
      }
      catch (Exception paramContext)
      {
        for (;;) {}
      }
      paramContext = null;
      paramString1.setData(paramContext);
      if (!TextUtils.isEmpty(paramString7))
      {
        paramContext = new StringBuilder();
        paramContext.append("ChannelID:");
        paramContext.append(paramString7);
        FLogger.d("IntentUtils", paramContext.toString());
        paramString1.putExtra("ChannelID", paramString7);
      }
      if (!TextUtils.isEmpty(paramString8))
      {
        paramContext = new StringBuilder();
        paramContext.append("PosID:");
        paramContext.append(paramString8);
        FLogger.d("IntentUtils", paramContext.toString());
        paramString1.putExtra("PosID", paramString8);
      }
      paramString1.putExtra("FromDownloadID", "download");
      return paramString1;
      label532:
      if (bool)
      {
        paramString5 = ",encoded=1";
        break;
      }
      paramString5 = "";
      break;
      i = 0;
    }
  }
  
  public static <T extends Parcelable> T getParcelable(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return null;
    }
    try
    {
      paramBundle = paramBundle.getParcelable(paramString);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getFloat Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static Parcelable[] getParcelableArray(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return null;
    }
    try
    {
      paramBundle = paramBundle.getParcelableArray(paramString);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getFloat Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static Parcelable[] getParcelableArrayExtra(Intent paramIntent, String paramString)
  {
    try
    {
      paramIntent = paramIntent.getParcelableArrayExtra(paramString);
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getParcelableArrayExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static <T extends Parcelable> ArrayList<T> getParcelableArrayList(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return null;
    }
    try
    {
      paramBundle = paramBundle.getParcelableArrayList(paramString);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getFloat Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static <T extends Parcelable> ArrayList<T> getParcelableArrayListExtra(Intent paramIntent, String paramString)
  {
    try
    {
      paramIntent = paramIntent.getParcelableArrayListExtra(paramString);
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getParcelableArrayListExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static <T extends Parcelable> T getParcelableExtra(Intent paramIntent, String paramString)
  {
    try
    {
      paramIntent = paramIntent.getParcelableExtra(paramString);
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getParcelableExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static List<ActivityManager.RunningAppProcessInfo> getRunningAppProcess(Context paramContext)
  {
    try
    {
      paramContext = paramContext.getSystemService("activity");
      if (paramContext != null)
      {
        paramContext = ((ActivityManager)paramContext).getRunningAppProcesses();
        return paramContext;
      }
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    return null;
  }
  
  public static Serializable getSerializable(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return null;
    }
    try
    {
      paramBundle = paramBundle.getSerializable(paramString);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getSerializable Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static Serializable getSerializableExtra(Intent paramIntent, String paramString)
  {
    try
    {
      paramIntent = paramIntent.getSerializableExtra(paramString);
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getSerializableExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static short getShort(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return 0;
    }
    try
    {
      short s = paramBundle.getShort(paramString);
      return s;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getShort Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return 0;
  }
  
  public static short getShort(Bundle paramBundle, String paramString, short paramShort)
  {
    if (paramBundle == null) {
      return paramShort;
    }
    try
    {
      short s = paramBundle.getShort(paramString, paramShort);
      return s;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getShort Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return paramShort;
  }
  
  public static short[] getShortArray(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return null;
    }
    try
    {
      paramBundle = paramBundle.getShortArray(paramString);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getShortArray Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static short[] getShortArrayExtra(Intent paramIntent, String paramString)
  {
    try
    {
      paramIntent = paramIntent.getShortArrayExtra(paramString);
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getShortArrayExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static short getShortExtra(Intent paramIntent, String paramString, short paramShort)
  {
    try
    {
      short s = paramIntent.getShortExtra(paramString, paramShort);
      return s;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getShortExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return paramShort;
  }
  
  public static <T extends Parcelable> SparseArray<T> getSparseParcelableArray(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return null;
    }
    try
    {
      paramBundle = paramBundle.getSparseParcelableArray(paramString);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getSparseParcelableArray Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static String getString(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return null;
    }
    try
    {
      paramBundle = paramBundle.getString(paramString);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getDouble Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  @SuppressLint({"NewApi"})
  public static String getString(Bundle paramBundle, String paramString1, String paramString2)
  {
    if (paramBundle == null) {
      return paramString2;
    }
    try
    {
      paramBundle = paramBundle.getString(paramString1, paramString2);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString1 = new StringBuilder();
      paramString1.append("getFloat Throwable");
      paramString1.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString1.toString());
    }
    return paramString2;
  }
  
  public static String[] getStringArray(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return null;
    }
    try
    {
      paramBundle = paramBundle.getStringArray(paramString);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getStringArray Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static String[] getStringArrayExtra(Intent paramIntent, String paramString)
  {
    try
    {
      paramIntent = paramIntent.getStringArrayExtra(paramString);
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getStringArrayExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static ArrayList<String> getStringArrayList(Bundle paramBundle, String paramString)
  {
    if (paramBundle == null) {
      return null;
    }
    try
    {
      paramBundle = paramBundle.getStringArrayList(paramString);
      return paramBundle;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getStringArrayList Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static ArrayList<String> getStringArrayListExtra(Intent paramIntent, String paramString)
  {
    try
    {
      paramIntent = paramIntent.getStringArrayListExtra(paramString);
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getStringArrayListExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static String getStringExtra(Intent paramIntent, String paramString)
  {
    try
    {
      paramIntent = paramIntent.getStringExtra(paramString);
      return paramIntent;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getStringExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtils", paramString.toString());
    }
    return null;
  }
  
  public static boolean isEncode(Context paramContext)
  {
    paramContext = paramContext.getPackageManager();
    if (paramContext != null) {
      try
      {
        PackageInfo localPackageInfo = paramContext.getPackageInfo("com.tencent.mtt", 0);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("pi.versionCode:");
        if (localPackageInfo == null) {
          paramContext = "";
        } else {
          paramContext = Integer.valueOf(localPackageInfo.versionCode);
        }
        localStringBuilder.append(paramContext);
        FLogger.d("IntentUtils", localStringBuilder.toString());
        if (localPackageInfo != null)
        {
          int i = localPackageInfo.versionCode;
          if (i > 601000) {
            return true;
          }
        }
      }
      catch (Throwable paramContext)
      {
        paramContext.printStackTrace();
      }
    }
    return false;
  }
  
  public static boolean openUrlAndDownloadInQQBrowserWithReport(Context paramContext, boolean paramBoolean, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    paramString1 = getOpenUrlAndDownloadInQQBrowserWithReport(paramContext, paramBoolean, paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8);
    if (paramString1 != null)
    {
      paramContext.startActivity(paramString1);
      return true;
    }
    return false;
  }
  
  public static boolean openUrlInQQBrowser(Context paramContext, String paramString1, String paramString2)
  {
    return openUrlInQQBrowser(paramContext, paramString1, paramString2, null);
  }
  
  public static boolean openUrlInQQBrowser(Context paramContext, String paramString1, String paramString2, Bundle paramBundle)
  {
    for (;;)
    {
      boolean bool;
      try
      {
        bool = isEncode(paramContext);
        str = paramString1;
        if (!bool) {
          break label176;
        }
        str = URLEncoder.encode(paramString1, "UTF-8");
      }
      catch (Exception paramContext)
      {
        String str;
        StringBuilder localStringBuilder;
        paramContext.printStackTrace();
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("mttbrowser://url=");
      localStringBuilder.append(str);
      localStringBuilder.append(",product=");
      localStringBuilder.append("TBS");
      localStringBuilder.append(",packagename=");
      localStringBuilder.append(paramContext.getApplicationContext().getPackageName());
      localStringBuilder.append(",from=");
      localStringBuilder.append(paramString2);
      localStringBuilder.append(",version=");
      localStringBuilder.append(a);
      localStringBuilder.append(paramString1);
      FLogger.d("IntentUtils", localStringBuilder.toString());
      paramString1 = MttLoader.getIntent(paramContext, localStringBuilder.toString());
      if (paramString1 != null)
      {
        if (paramBundle != null) {
          paramString1.putExtras(paramBundle);
        }
        paramContext.startActivity(paramString1);
        return true;
      }
      return false;
      label176:
      if (bool) {
        paramString1 = ",encoded=1";
      } else {
        paramString1 = "";
      }
    }
  }
  
  public static boolean openUrlInQQBrowserWithReport(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    for (;;)
    {
      boolean bool;
      try
      {
        bool = isEncode(paramContext);
        str = paramString1;
        if (!bool) {
          break label225;
        }
        str = URLEncoder.encode(paramString1, "UTF-8");
      }
      catch (Exception paramContext)
      {
        String str;
        StringBuilder localStringBuilder;
        paramContext.printStackTrace();
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("mttbrowser://url=");
      localStringBuilder.append(str);
      localStringBuilder.append(",product=");
      localStringBuilder.append("TBS");
      localStringBuilder.append(",packagename=");
      localStringBuilder.append(paramContext.getApplicationContext().getPackageName());
      localStringBuilder.append(",from=");
      localStringBuilder.append(paramString2);
      localStringBuilder.append(",version=");
      localStringBuilder.append(a);
      localStringBuilder.append(paramString1);
      paramString1 = new StringBuilder();
      paramString1.append("openUrlInQQBrowserWithReport--StringBuilder = ");
      paramString1.append(localStringBuilder.toString());
      FLogger.d("IntentUtils", paramString1.toString());
      paramString1 = MttLoader.getIntent(paramContext, localStringBuilder.toString());
      if (paramString1 != null)
      {
        if (!TextUtils.isEmpty(paramString3)) {
          paramString1.putExtra("ChannelID", paramString3);
        }
        if (!TextUtils.isEmpty(paramString4)) {
          paramString1.putExtra("PosID", paramString4);
        }
        paramContext.startActivity(paramString1);
        return true;
      }
      return false;
      label225:
      if (bool) {
        paramString1 = ",encoded=1";
      } else {
        paramString1 = "";
      }
    }
  }
  
  public static boolean openUrlInQQBrowserWithReport(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    for (;;)
    {
      boolean bool;
      try
      {
        bool = isEncode(paramContext);
        str = paramString1;
        if (!bool) {
          break label285;
        }
        str = URLEncoder.encode(paramString1, "UTF-8");
      }
      catch (Exception paramContext)
      {
        String str;
        StringBuilder localStringBuilder;
        paramContext.printStackTrace();
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("mttbrowser://url=");
      localStringBuilder.append(str);
      localStringBuilder.append(",product=");
      localStringBuilder.append("TBS");
      localStringBuilder.append(",packagename=");
      localStringBuilder.append(paramContext.getApplicationContext().getPackageName());
      localStringBuilder.append(",from=");
      localStringBuilder.append(paramString2);
      localStringBuilder.append(",version=");
      localStringBuilder.append(a);
      localStringBuilder.append(paramString1);
      if (paramString5 != null) {
        if (paramString5.startsWith(","))
        {
          localStringBuilder.append(paramString5);
        }
        else
        {
          paramString1 = new StringBuilder();
          paramString1.append(",");
          paramString1.append(paramString5);
          localStringBuilder.append(paramString1.toString());
        }
      }
      paramString1 = new StringBuilder();
      paramString1.append("openUrlInQQBrowserWithReport--StringBuilder = ");
      paramString1.append(localStringBuilder.toString());
      FLogger.d("IntentUtils", paramString1.toString());
      paramString1 = MttLoader.getIntent(paramContext, localStringBuilder.toString());
      if (paramString1 != null)
      {
        if (!TextUtils.isEmpty(paramString3)) {
          paramString1.putExtra("ChannelID", paramString3);
        }
        if (!TextUtils.isEmpty(paramString4)) {
          paramString1.putExtra("PosID", paramString4);
        }
        paramContext.startActivity(paramString1);
        return true;
      }
      return false;
      label285:
      if (bool) {
        paramString1 = ",encoded=1";
      } else {
        paramString1 = "";
      }
    }
  }
  
  public static void setTbsVersionName(String paramString)
  {
    a = paramString;
  }
  
  public static void startQQBrowser(Context paramContext)
  {
    try
    {
      Intent localIntent = new Intent("com.tencent.QQBrowser.action.VIEW");
      a locala = a(paramContext);
      if ((locala != null) && (!TextUtils.isEmpty(locala.a))) {
        localIntent.setClassName(locala.b, locala.a);
      }
      localIntent.setFlags(268435456);
      localIntent.putExtra("loginType", a(paramContext));
      paramContext.startActivity(localIntent);
      return;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  private static class a
  {
    public String a = "";
    public String b = "";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\IntentUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */