package com.tencent.common.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Process;
import android.util.SparseArray;
import com.tencent.basesupport.FLogger;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IntentUtilsF
{
  private static final String a = ;
  private static String b = "";
  
  public static void fillPackageName(Intent paramIntent)
  {
    if (paramIntent == null) {
      return;
    }
    if (Integer.parseInt(Build.VERSION.SDK) >= 21) {
      paramIntent.setPackage(a);
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      byte b1 = paramBundle.getByte(paramString);
      return b1;
    }
    catch (Throwable paramBundle)
    {
      paramString = new StringBuilder();
      paramString.append("getByte Throwable");
      paramString.append(paramBundle.getMessage());
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
    }
    return null;
  }
  
  public static byte getByteExtra(Intent paramIntent, String paramString, byte paramByte)
  {
    try
    {
      byte b1 = paramIntent.getByteExtra(paramString, paramByte);
      return b1;
    }
    catch (Throwable paramIntent)
    {
      paramString = new StringBuilder();
      paramString.append("getByteExtra Throwable");
      paramString.append(paramIntent.getMessage());
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", localStringBuilder.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
    }
    return paramLong;
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString1.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
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
      FLogger.d("IntentUtilsF", paramString.toString());
    }
    return null;
  }
  
  public static boolean isEncode(Context paramContext)
  {
    paramContext = paramContext.getPackageManager();
    if (paramContext != null) {
      try
      {
        paramContext = paramContext.getPackageInfo(a, 0);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("pi.versionCode:");
        localStringBuilder.append(paramContext.versionCode);
        FLogger.d("IntentUtilsF", localStringBuilder.toString());
        if (paramContext != null)
        {
          int i = paramContext.versionCode;
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
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\IntentUtilsF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */