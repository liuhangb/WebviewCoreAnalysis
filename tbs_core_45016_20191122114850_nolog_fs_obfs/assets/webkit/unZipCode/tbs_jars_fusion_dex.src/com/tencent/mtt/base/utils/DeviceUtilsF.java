package com.tencent.mtt.base.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.PowerManager;
import android.os.StatFs;
import android.os.Vibrator;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.tencent.basesupport.FLogger;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.DesUtils;
import com.tencent.common.utils.GdiMeasureImpl;
import com.tencent.mtt.ContextHolder;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceUtilsF
{
  private static boolean A = false;
  private static boolean B = false;
  public static final int DEVICEINFO_HIGH_PERF = 2;
  public static final int DEVICEINFO_LOW_PERF = 0;
  public static final int DEVICEINFO_MIDDLE_PERF = 1;
  public static final int DEVICEINFO_UNKNOWN = -1;
  public static final int MINIMUM_FORCE_TABLET_WIDTH_DP = 700;
  public static final int MINIMUM_TABLET_WIDTH_DP = 590;
  public static final int SCREEN_DENSITY_HDPI = 240;
  public static final int SCREEN_DENSITY_LDPI = 120;
  public static final int SCREEN_DENSITY_MDPI = 160;
  public static final int SCREEN_DENSITY_XHDPI = 320;
  public static final int SCREEN_DENSITY_XXHDPI = 480;
  public static int STATUSBAR_HEIGHT = 0;
  private static float jdField_a_of_type_Float = 0.0F;
  private static int jdField_a_of_type_Int = -1;
  private static long jdField_a_of_type_Long = 0L;
  private static String jdField_a_of_type_JavaLangString = "";
  static boolean jdField_a_of_type_Boolean = false;
  private static int jdField_b_of_type_Int = 0;
  private static String jdField_b_of_type_JavaLangString;
  static boolean jdField_b_of_type_Boolean = false;
  private static int jdField_c_of_type_Int = 0;
  private static String jdField_c_of_type_JavaLangString;
  private static boolean jdField_c_of_type_Boolean = false;
  private static int jdField_d_of_type_Int = -1;
  private static String jdField_d_of_type_JavaLangString;
  private static boolean jdField_d_of_type_Boolean = false;
  private static int jdField_e_of_type_Int = -1;
  private static boolean jdField_e_of_type_Boolean = false;
  private static int jdField_f_of_type_Int = -1;
  private static boolean jdField_f_of_type_Boolean = false;
  private static int jdField_g_of_type_Int = -1;
  private static boolean jdField_g_of_type_Boolean = false;
  private static int jdField_h_of_type_Int = -1;
  private static boolean jdField_h_of_type_Boolean = false;
  private static int jdField_i_of_type_Int = 0;
  private static boolean jdField_i_of_type_Boolean = false;
  public static boolean isASUS_ZenFone_6 = false;
  public static boolean isBullHead = false;
  public static boolean isC8500 = false;
  public static boolean isChaCha = false;
  public static boolean isCoolpad5892 = false;
  public static boolean isCoolpad7296 = false;
  public static boolean isCoolpadF2 = false;
  public static boolean isDoov = false;
  public static boolean isE15i = false;
  public static boolean isEMUI = false;
  public static boolean isEMUI3 = false;
  public static boolean isEMUI4 = false;
  public static boolean isEMUI5 = false;
  public static boolean isEUI = false;
  public static boolean isFind5 = false;
  public static boolean isFind7 = false;
  public static boolean isGoogleAndroid = false;
  public static boolean isGt5830 = false;
  public static boolean isHMNote = false;
  public static boolean isHTCm8T = false;
  public static boolean isHUAWEI_H60_L01 = false;
  public static boolean isHUAWEI_MT7 = false;
  public static boolean isHUAWEI_P6_U06 = false;
  public static boolean isHtcHero = false;
  public static boolean isHtcM9et = false;
  public static boolean isHtcU11 = false;
  public static boolean isHuaweiG700_t00 = false;
  public static boolean isHuaweiY511_t00 = false;
  public static boolean isI9000 = false;
  public static boolean isI9100 = false;
  public static boolean isI9108 = false;
  public static boolean isI9300 = false;
  public static boolean isI939 = false;
  public static boolean isK30 = false;
  public static boolean isK860i = false;
  public static boolean isLe = false;
  public static boolean isLetv = false;
  public static boolean isLewa = false;
  public static boolean isMI4 = false;
  public static boolean isMIPAD = false;
  public static boolean isMIUIV6 = false;
  public static boolean isMX3 = false;
  public static boolean isMX4 = false;
  public static boolean isMeizuM9 = false;
  public static boolean isMi2S = false;
  public static boolean isMiNote = false;
  public static boolean isMixSeries = false;
  public static boolean isN5 = false;
  public static boolean isN5x = false;
  public static boolean isN6 = false;
  public static boolean isN6P = false;
  public static boolean isN882E = false;
  public static boolean isNX403A = false;
  public static boolean isOnePlus = false;
  public static boolean isOppo = false;
  public static boolean isOppoA11 = false;
  public static boolean isOppoFind7 = false;
  public static boolean isOppoR7Plus = false;
  public static boolean isOppoR7s = false;
  public static boolean isOppoR8207 = false;
  public static boolean isOppoR9 = false;
  public static boolean isOppoR9Plus = false;
  public static boolean isS5360 = false;
  public static boolean isS5830 = false;
  public static boolean isS5830i = false;
  public static boolean isS6 = false;
  public static boolean isS6_Edge = false;
  public static boolean isS7562 = false;
  public static boolean isS8 = false;
  public static boolean isSamsung = false;
  public static boolean isShamu = false;
  public static boolean isU2 = false;
  public static boolean isU20I = false;
  public static boolean isU8500 = false;
  public static boolean isV880 = false;
  public static boolean isVivo = false;
  public static boolean isVivoS7 = false;
  public static boolean isW619 = false;
  public static boolean isW719 = false;
  public static boolean isW9913 = false;
  public static boolean isWildFire = false;
  public static boolean isXL39h = false;
  public static boolean isXT800 = false;
  public static boolean isZTEC2016 = false;
  public static boolean isZTE_N918St = false;
  public static boolean isZTE_N958St = false;
  public static boolean isZTE_NX505J = false;
  public static boolean isZTE_NX511J = false;
  public static boolean isZTE_NX531J = false;
  public static boolean isZTE_NX549J = false;
  public static boolean isZUI = false;
  public static boolean isZUKZ1 = false;
  public static boolean isZteV889D = false;
  private static int jdField_j_of_type_Int = 0;
  private static boolean jdField_j_of_type_Boolean = false;
  private static int jdField_k_of_type_Int = 0;
  private static boolean jdField_k_of_type_Boolean = false;
  private static int jdField_l_of_type_Int = 0;
  private static boolean jdField_l_of_type_Boolean = false;
  private static int jdField_m_of_type_Int = 0;
  private static boolean jdField_m_of_type_Boolean = false;
  public static int mBrowserActiveState = 0;
  protected static boolean mIsInSystemMultiWindow = false;
  public static int mRootStatus = -1;
  protected static int mSystemMultiWindowState = -1;
  public static WindowManager mWm;
  private static int jdField_n_of_type_Int = 0;
  private static boolean jdField_n_of_type_Boolean = false;
  private static int jdField_o_of_type_Int = 0;
  private static boolean jdField_o_of_type_Boolean = false;
  private static boolean p = false;
  private static boolean q = false;
  private static boolean r = false;
  private static boolean s = false;
  public static boolean sHaveCheckStatusBarHeight = false;
  public static final boolean sIsCupcake = false;
  public static final boolean sLessGingerbread = false;
  public static final boolean sLessHoneycomb = false;
  public static final boolean sLessIcecream = false;
  private static boolean t = false;
  private static boolean u = false;
  private static boolean v = false;
  private static boolean w = false;
  private static boolean x = false;
  private static boolean y = false;
  private static boolean z;
  
  static
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(Build.MODEL);
    ((StringBuilder)localObject).append("  BRAND:");
    ((StringBuilder)localObject).append(Build.BRAND);
    FLogger.d("DeviceUtilsF MODEL:", ((StringBuilder)localObject).toString());
    localObject = ContextHolder.getAppContext();
    if (localObject != null)
    {
      jdField_i_of_type_Int = ((Context)localObject).getResources().getConfiguration().screenWidthDp;
      jdField_j_of_type_Int = ((Context)localObject).getResources().getConfiguration().screenHeightDp;
    }
    jdField_b_of_type_JavaLangString = getDeviceBrand().trim();
    if (jdField_b_of_type_JavaLangString.contains("oppo")) {
      isOppo = true;
    } else if (jdField_b_of_type_JavaLangString.trim().contains("vivo")) {
      isVivo = true;
    }
    localObject = Build.MODEL.trim().toLowerCase();
    if (((String)localObject).contains("wildfire")) {
      isWildFire = true;
    } else if (((String)localObject).contains("hm note")) {
      isHMNote = true;
    } else if (((String)localObject).contains("mi 4")) {
      isMI4 = true;
    } else if (((String)localObject).contains("mi note")) {
      isMiNote = true;
    } else if (((String)localObject).contains("chacha")) {
      isChaCha = true;
    } else if (((String)localObject).contains("g700-t00")) {
      isHuaweiG700_t00 = true;
    } else if (((String)localObject).contains("y511-t00")) {
      isHuaweiY511_t00 = true;
    } else if (((String)localObject).contains("e15i")) {
      isE15i = true;
    } else if (((String)localObject).contains("u20i")) {
      isU20I = true;
    } else if (((String)localObject).equals("m9")) {
      isMeizuM9 = true;
    } else if (((String)localObject).equals("htc hero")) {
      isHtcHero = true;
    } else if (((String)localObject).equals("htc m8t")) {
      isHTCm8T = true;
    } else if ((!((String)localObject).equals("htc 2q4d100")) && (!((String)localObject).equals("htc 2q4d200")))
    {
      if (((String)localObject).equals("htc m9et"))
      {
        isHtcM9et = true;
      }
      else if (((String)localObject).equals("zte-u v880"))
      {
        isV880 = true;
      }
      else if (((String)localObject).contains("zte n882e"))
      {
        isN882E = true;
      }
      else if (((String)localObject).contains("gt-i9000"))
      {
        isI9000 = true;
      }
      else if (((String)localObject).contains("xt800"))
      {
        isXT800 = true;
      }
      else if (((String)localObject).contains("s5360"))
      {
        isS5360 = true;
      }
      else if (((String)localObject).contains("mi pad"))
      {
        isMIPAD = true;
      }
      else if (((String)localObject).contains("s5830"))
      {
        isS5830 = true;
        if (((String)localObject).contains("s5830i")) {
          isS5830i = true;
        }
      }
      else if (((String)localObject).contains("w9913"))
      {
        isW9913 = true;
      }
      else if (((String)localObject).equals("u2"))
      {
        isU2 = true;
      }
      else if (((String)localObject).contains("w719"))
      {
        isW719 = true;
      }
      else if (((String)localObject).equalsIgnoreCase("zte v889d"))
      {
        isZteV889D = true;
      }
      else if (((String)localObject).contains("w619"))
      {
        isW619 = true;
      }
      else if (((String)localObject).contains("c8500"))
      {
        isC8500 = true;
      }
      else if (((String)localObject).contains("u8500"))
      {
        isU8500 = true;
      }
      else if (((String)localObject).contains("gt-s5830"))
      {
        isGt5830 = true;
      }
      else if (((String)localObject).contains("sm-g925"))
      {
        isS6_Edge = true;
      }
      else if (((String)localObject).contains("sm-g920"))
      {
        isS6 = true;
      }
      else if (((String)localObject).contains("sch-i939"))
      {
        isI939 = true;
      }
      else if ((!((String)localObject).contains("sm-g9500")) && (!((String)localObject).contains("sm-g9550")))
      {
        if (((String)localObject).contains("vivo"))
        {
          isVivo = true;
          FLogger.d("DeviceUtilsF", (String)localObject);
          if (((String)localObject).contains("s7")) {
            isVivoS7 = true;
          }
        }
        else if (((String)localObject).contains("k30"))
        {
          isK30 = true;
        }
        else if (((String)localObject).contains("k860i"))
        {
          isK860i = true;
        }
        else if (((String)localObject).contains("gt-i9300"))
        {
          isI9300 = true;
        }
        else if (((String)localObject).contains("gt-i9108"))
        {
          isI9108 = true;
        }
        else if (((String)localObject).contains("gt-s7562"))
        {
          isS7562 = true;
        }
        else if (((String)localObject).contains("gt-i9100"))
        {
          isI9100 = true;
        }
        else if ((!((String)localObject).equalsIgnoreCase("m353")) && (!Build.DEVICE.equalsIgnoreCase("mx3")))
        {
          if ((!((String)localObject).equalsIgnoreCase("mx4")) && (!Build.DEVICE.equalsIgnoreCase("mx4")))
          {
            if (((String)localObject).equals("xl39h"))
            {
              isXL39h = true;
            }
            else if (((String)localObject).equals("coolpad 5892"))
            {
              isCoolpad5892 = true;
            }
            else if (((String)localObject).equals("nx403a"))
            {
              isNX403A = true;
            }
            else if (((String)localObject).contains("coolpad 8675"))
            {
              isCoolpadF2 = true;
            }
            else if (((String)localObject).equals("find 5"))
            {
              isFind5 = true;
            }
            else if (((String)localObject).equals("find 7"))
            {
              isFind7 = true;
            }
            else if (((String)localObject).equals("nexus 5"))
            {
              isN5 = true;
            }
            else if (((String)localObject).equals("nexus 6"))
            {
              isN6 = true;
            }
            else if (((String)localObject).equals("nexus 6p"))
            {
              isN6P = true;
            }
            else if (((String)localObject).equals("nexus 5x"))
            {
              isN5x = true;
            }
            else if (((String)localObject).contains("aosp on bullhead"))
            {
              isBullHead = true;
            }
            else if (((String)localObject).contains("aosp on shamu"))
            {
              isShamu = true;
            }
            else if (((String)localObject).equals("coolpad 7296"))
            {
              isCoolpad7296 = true;
            }
            else if (((String)localObject).equalsIgnoreCase("asus_t00g"))
            {
              isASUS_ZenFone_6 = true;
            }
            else if (((String)localObject).equals("huawei p6-u06"))
            {
              isHUAWEI_P6_U06 = true;
            }
            else if (((String)localObject).equals("h60-l01"))
            {
              isHUAWEI_H60_L01 = true;
            }
            else if (((String)localObject).equals("h60-l02"))
            {
              isHUAWEI_H60_L01 = true;
            }
            else if (((String)localObject).equals("h60-l03"))
            {
              isHUAWEI_H60_L01 = true;
            }
            else if (((String)localObject).contains("mt7-tl10"))
            {
              isHUAWEI_MT7 = true;
            }
            else if (((String)localObject).contains("mt7-cl00"))
            {
              isHUAWEI_MT7 = true;
            }
            else if (((String)localObject).contains("mt7-tl00"))
            {
              isHUAWEI_MT7 = true;
            }
            else if (((String)localObject).contains("mt7-ul00"))
            {
              isHUAWEI_MT7 = true;
            }
            else if (((String)localObject).equals("n958st"))
            {
              isZTE_N958St = true;
            }
            else if (((String)localObject).equals("n918st"))
            {
              isZTE_N918St = true;
            }
            else if (((String)localObject).equals("nx511j"))
            {
              isZTE_NX511J = true;
            }
            else if (((String)localObject).equals("nx505j"))
            {
              isZTE_NX505J = true;
            }
            else if (((String)localObject).equals("nx549j"))
            {
              isZTE_NX549J = true;
            }
            else if (((String)localObject).equals("nx531j"))
            {
              isZTE_NX531J = true;
            }
            else if (((String)localObject).equals("mi 2s"))
            {
              isMi2S = true;
            }
            else if (((String)localObject).startsWith("mix"))
            {
              isMixSeries = true;
            }
            else if (((String)localObject).matches(".*oppo.*"))
            {
              isOppo = true;
              if (((String)localObject).matches(".*oppo[\\s\\/\\_\\&\\|]*r7s.*")) {
                isOppoR7s = true;
              } else if (((String)localObject).replaceAll("[ |\\/|\\_|\\&|\\|]", "").contains("oppor9plus")) {
                isOppoR9Plus = true;
              } else if (((String)localObject).replaceAll("[ |\\/|\\_|\\&|\\|]", "").contains("oppor9")) {
                isOppoR9 = true;
              }
            }
            else if (((String)localObject).contains("x9007"))
            {
              if (getDeviceBrand().contains("oppo"))
              {
                isOppoFind7 = true;
                isOppo = true;
              }
            }
            else if (((String)localObject).contains("r8207"))
            {
              if (getDeviceBrand().contains("oppo"))
              {
                isOppoR8207 = true;
                isOppo = true;
              }
            }
            else if (((String)localObject).contains("a11"))
            {
              if (getDeviceBrand().contains("oppo"))
              {
                isOppoA11 = true;
                isOppo = true;
              }
            }
            else if (((String)localObject).contains("r9plus"))
            {
              if (getDeviceBrand().contains("oppo"))
              {
                isOppoR9Plus = true;
                isOppo = true;
              }
            }
            else if (((String)localObject).contains("r9plus"))
            {
              if (getDeviceBrand().contains("oppo"))
              {
                isOppoR9Plus = true;
                isOppo = true;
              }
            }
            else if (((String)localObject).contains("r7plus"))
            {
              if (getDeviceBrand().contains("oppo"))
              {
                isOppoR7Plus = true;
                isOppo = true;
              }
            }
            else if (((String)localObject).contains("le"))
            {
              if (getDeviceBrand().contains("le")) {
                isLe = true;
              }
            }
            else if (((String)localObject).contains("doov"))
            {
              isDoov = true;
            }
            else if (((String)localObject).toLowerCase().contains("zuk z1"))
            {
              isZUKZ1 = true;
            }
            else if (((String)localObject).toLowerCase().contains("zte c2016"))
            {
              isZTEC2016 = true;
            }
            else if ((((String)localObject).toLowerCase().contains("ns")) || (((String)localObject).contains("oneplus")))
            {
              isOnePlus = true;
            }
          }
          else {
            isMX4 = true;
          }
        }
        else
        {
          isMX3 = true;
        }
      }
      else
      {
        isS8 = true;
      }
    }
    else {
      isHtcU11 = true;
    }
    if (getDeviceBrand().trim().contains("letv")) {
      isLetv = true;
    }
    localObject = Build.USER;
    if (!TextUtils.isEmpty((CharSequence)localObject)) {
      isLewa = ((String)localObject).trim().toLowerCase().contains("lewa");
    }
    jdField_k_of_type_Int = -1;
    z = false;
    A = false;
    B = false;
    jdField_l_of_type_Int = -1;
    jdField_a_of_type_Long = 0L;
    jdField_m_of_type_Int = -1;
    jdField_n_of_type_Int = -1;
    jdField_o_of_type_Int = -1;
    jdField_a_of_type_Float = -1.0F;
    STATUSBAR_HEIGHT = 0;
    sHaveCheckStatusBarHeight = false;
  }
  
  public static boolean IsHideSmartBarSuccess()
  {
    return z;
  }
  
  private static int a(Context paramContext, int paramInt)
  {
    return (int)(paramContext.getResources().getDisplayMetrics().density * paramInt + 0.5F);
  }
  
  private static int a(String paramString, FileInputStream paramFileInputStream)
  {
    arrayOfByte = new byte['Ѐ'];
    for (;;)
    {
      try
      {
        i4 = paramFileInputStream.read(arrayOfByte);
        i2 = 0;
      }
      catch (IOException|NumberFormatException paramString)
      {
        int i4;
        continue;
        if (i2 >= i4) {
          continue;
        }
        if (arrayOfByte[i2] == 10) {
          continue;
        }
        int i3 = i2;
        if (i2 != 0) {
          continue;
        }
        int i1 = i2;
        if (arrayOfByte[i2] != 10) {
          continue;
        }
        i1 = i2 + 1;
        int i2 = i1;
        continue;
      }
      i3 = i1;
      if (i2 >= i4) {
        continue;
      }
      i3 = i2 - i1;
      if (arrayOfByte[i2] != paramString.charAt(i3))
      {
        i3 = i1;
        continue;
      }
      if (i3 == paramString.length() - 1)
      {
        i1 = a(arrayOfByte, i2);
        return i1;
      }
      i2 += 1;
    }
    i2 = i3 + 1;
    break label94;
    return -1;
  }
  
  private static int a(byte[] paramArrayOfByte, int paramInt)
  {
    while ((paramInt < paramArrayOfByte.length) && (paramArrayOfByte[paramInt] != 10))
    {
      if ((paramArrayOfByte[paramInt] >= 48) && (paramArrayOfByte[paramInt] <= 57))
      {
        int i1 = paramInt + 1;
        while ((i1 < paramArrayOfByte.length) && (paramArrayOfByte[i1] >= 48) && (paramArrayOfByte[i1] <= 57)) {
          i1 += 1;
        }
        return Integer.parseInt(new String(paramArrayOfByte, 0, paramInt, i1 - paramInt));
      }
      paramInt += 1;
    }
    return -1;
  }
  
  private static Class<?> a(String paramString)
  {
    try
    {
      paramString = Class.forName(paramString);
      return paramString;
    }
    catch (ClassNotFoundException paramString)
    {
      for (;;) {}
    }
    return null;
  }
  
  private static <T> T a(Method paramMethod, Object paramObject, Object... paramVarArgs)
  {
    try
    {
      paramMethod = paramMethod.invoke(paramObject, paramVarArgs);
      return paramMethod;
    }
    catch (InvocationTargetException paramMethod)
    {
      throw new RuntimeException(paramMethod);
    }
    catch (Exception paramMethod)
    {
      for (;;) {}
    }
    return null;
  }
  
  private static Method a(Class<?> paramClass, String paramString, Class<?>... paramVarArgs)
  {
    try
    {
      paramClass = paramClass.getDeclaredMethod(paramString, paramVarArgs);
      return paramClass;
    }
    catch (Exception paramClass)
    {
      for (;;) {}
    }
    return null;
  }
  
  @Deprecated
  private static void a()
  {
    WindowManager localWindowManager = (WindowManager)ContextHolder.getAppContext().getSystemService("window");
    jdField_b_of_type_Int = localWindowManager.getDefaultDisplay().getWidth();
    jdField_c_of_type_Int = localWindowManager.getDefaultDisplay().getHeight();
  }
  
  private static void a(Activity paramActivity)
  {
    if (hasSmartBar())
    {
      try
      {
        paramActivity.requestWindowFeature(1);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      ApiCompat.hideNavigation(paramActivity);
      z = true;
      if (!z) {
        try
        {
          paramActivity.requestWindowFeature(1);
          return;
        }
        catch (Exception paramActivity)
        {
          paramActivity.printStackTrace();
        }
      }
    }
  }
  
  /* Error */
  private static boolean a()
  {
    // Byte code:
    //   0: getstatic 799	android/os/Build:ID	Ljava/lang/String;
    //   3: invokevirtual 243	java/lang/String:trim	()Ljava/lang/String;
    //   6: invokevirtual 260	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   9: ldc_w 801
    //   12: invokevirtual 251	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   15: istore_0
    //   16: iload_0
    //   17: ifne +45 -> 62
    //   20: invokestatic 211	com/tencent/mtt/ContextHolder:getAppContext	()Landroid/content/Context;
    //   23: astore_1
    //   24: aload_1
    //   25: ifnull +41 -> 66
    //   28: aload_1
    //   29: invokevirtual 805	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   32: ldc_w 807
    //   35: iconst_0
    //   36: invokevirtual 813	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   39: astore_1
    //   40: aload_1
    //   41: ifnull +15 -> 56
    //   44: iconst_1
    //   45: istore_0
    //   46: goto +10 -> 56
    //   49: astore_1
    //   50: iconst_1
    //   51: putstatic 815	com/tencent/mtt/base/utils/DeviceUtilsF:jdField_m_of_type_Boolean	Z
    //   54: aload_1
    //   55: athrow
    //   56: iconst_1
    //   57: putstatic 815	com/tencent/mtt/base/utils/DeviceUtilsF:jdField_m_of_type_Boolean	Z
    //   60: iload_0
    //   61: ireturn
    //   62: iconst_1
    //   63: putstatic 815	com/tencent/mtt/base/utils/DeviceUtilsF:jdField_m_of_type_Boolean	Z
    //   66: iload_0
    //   67: ireturn
    //   68: astore_1
    //   69: goto -13 -> 56
    // Local variable table:
    //   start	length	slot	name	signature
    //   15	52	0	bool	boolean
    //   23	18	1	localObject1	Object
    //   49	6	1	localObject2	Object
    //   68	1	1	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   28	40	49	finally
    //   28	40	68	java/lang/Exception
  }
  
  private static boolean a(int paramInt)
  {
    Object localObject = getSystemProperty("ro.miui.ui.version.name");
    if (!TextUtils.isEmpty((CharSequence)localObject))
    {
      int i1 = Integer.valueOf(String.valueOf(((String)localObject).charAt(((String)localObject).length() - 1))).intValue();
      if (((String)localObject).length() > 1) {
        i1 = Integer.valueOf(((String)localObject).substring(1)).intValue();
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("ver=");
      ((StringBuilder)localObject).append(i1);
      ((StringBuilder)localObject).toString();
      return i1 >= paramInt;
    }
    return false;
  }
  
  /* Error */
  private static boolean a(String paramString)
  {
    // Byte code:
    //   0: new 842	java/io/File
    //   3: dup
    //   4: ldc_w 844
    //   7: invokespecial 847	java/io/File:<init>	(Ljava/lang/String;)V
    //   10: invokevirtual 850	java/io/File:exists	()Z
    //   13: istore_2
    //   14: iconst_0
    //   15: istore 4
    //   17: iconst_0
    //   18: istore_3
    //   19: iload_2
    //   20: ifeq +154 -> 174
    //   23: aconst_null
    //   24: astore 7
    //   26: aconst_null
    //   27: astore 5
    //   29: new 852	java/io/BufferedReader
    //   32: dup
    //   33: new 854	java/io/FileReader
    //   36: dup
    //   37: new 842	java/io/File
    //   40: dup
    //   41: ldc_w 844
    //   44: invokespecial 847	java/io/File:<init>	(Ljava/lang/String;)V
    //   47: invokespecial 857	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   50: invokespecial 860	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   53: astore 6
    //   55: aload 6
    //   57: invokevirtual 863	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   60: astore 5
    //   62: iload_3
    //   63: istore_2
    //   64: aload 5
    //   66: ifnull +20 -> 86
    //   69: aload 5
    //   71: invokevirtual 260	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   74: aload_0
    //   75: invokevirtual 866	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   78: istore_1
    //   79: iconst_m1
    //   80: iload_1
    //   81: if_icmpeq -26 -> 55
    //   84: iconst_1
    //   85: istore_2
    //   86: iload_2
    //   87: istore_3
    //   88: aload 6
    //   90: invokevirtual 869	java/io/BufferedReader:close	()V
    //   93: iload_2
    //   94: ireturn
    //   95: astore_0
    //   96: aload_0
    //   97: invokevirtual 870	java/io/IOException:printStackTrace	()V
    //   100: iload_3
    //   101: ireturn
    //   102: astore_0
    //   103: aload 6
    //   105: astore 5
    //   107: goto +45 -> 152
    //   110: astore 5
    //   112: aload 6
    //   114: astore_0
    //   115: aload 5
    //   117: astore 6
    //   119: goto +12 -> 131
    //   122: astore_0
    //   123: goto +29 -> 152
    //   126: astore 6
    //   128: aload 7
    //   130: astore_0
    //   131: aload_0
    //   132: astore 5
    //   134: aload 6
    //   136: invokevirtual 870	java/io/IOException:printStackTrace	()V
    //   139: aload_0
    //   140: ifnull +34 -> 174
    //   143: iload 4
    //   145: istore_3
    //   146: aload_0
    //   147: invokevirtual 869	java/io/BufferedReader:close	()V
    //   150: iconst_0
    //   151: ireturn
    //   152: aload 5
    //   154: ifnull +18 -> 172
    //   157: aload 5
    //   159: invokevirtual 869	java/io/BufferedReader:close	()V
    //   162: goto +10 -> 172
    //   165: astore 5
    //   167: aload 5
    //   169: invokevirtual 870	java/io/IOException:printStackTrace	()V
    //   172: aload_0
    //   173: athrow
    //   174: iconst_0
    //   175: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	176	0	paramString	String
    //   78	4	1	i1	int
    //   13	81	2	bool1	boolean
    //   18	128	3	bool2	boolean
    //   15	129	4	bool3	boolean
    //   27	79	5	localObject1	Object
    //   110	6	5	localIOException1	IOException
    //   132	26	5	str	String
    //   165	3	5	localIOException2	IOException
    //   53	65	6	localObject2	Object
    //   126	9	6	localIOException3	IOException
    //   24	105	7	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   88	93	95	java/io/IOException
    //   146	150	95	java/io/IOException
    //   55	62	102	finally
    //   69	79	102	finally
    //   55	62	110	java/io/IOException
    //   69	79	110	java/io/IOException
    //   29	55	122	finally
    //   134	139	122	finally
    //   29	55	126	java/io/IOException
    //   157	162	165	java/io/IOException
  }
  
  private static boolean b()
  {
    String str = getSystemProperty("ro.build.display.id");
    char c1;
    if (!TextUtils.isEmpty(str))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("flymeOs:");
      localStringBuilder.append(str);
      localStringBuilder.toString();
      int i1 = str.indexOf("Flyme OS");
      int i2;
      if (i1 == -1)
      {
        i1 = str.indexOf("Flyme");
        i2 = i1 + 6;
      }
      else
      {
        i2 = i1 + 9;
      }
      if ((i1 != -1) && (i2 < str.length())) {
        c1 = str.charAt(i2);
      }
    }
    try
    {
      if (Integer.valueOf(String.valueOf(c1)).intValue() >= 4)
      {
        jdField_n_of_type_Boolean = true;
        return true;
      }
      return false;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return false;
  }
  
  /* Error */
  private static boolean c()
  {
    // Byte code:
    //   0: getstatic 799	android/os/Build:ID	Ljava/lang/String;
    //   3: invokevirtual 243	java/lang/String:trim	()Ljava/lang/String;
    //   6: invokevirtual 260	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   9: ldc_w 882
    //   12: invokevirtual 251	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   15: istore_1
    //   16: iload_1
    //   17: istore_0
    //   18: iload_1
    //   19: ifne +19 -> 38
    //   22: getstatic 885	android/os/Build:MANUFACTURER	Ljava/lang/String;
    //   25: invokevirtual 243	java/lang/String:trim	()Ljava/lang/String;
    //   28: invokevirtual 260	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   31: ldc_w 887
    //   34: invokevirtual 251	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   37: istore_0
    //   38: iload_0
    //   39: istore_1
    //   40: iload_0
    //   41: ifne +19 -> 60
    //   44: getstatic 194	android/os/Build:BRAND	Ljava/lang/String;
    //   47: invokevirtual 243	java/lang/String:trim	()Ljava/lang/String;
    //   50: invokevirtual 260	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   53: ldc_w 887
    //   56: invokevirtual 251	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   59: istore_1
    //   60: iload_1
    //   61: istore_0
    //   62: iload_1
    //   63: ifne +19 -> 82
    //   66: getstatic 890	android/os/Build:HOST	Ljava/lang/String;
    //   69: invokevirtual 243	java/lang/String:trim	()Ljava/lang/String;
    //   72: invokevirtual 260	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   75: ldc_w 882
    //   78: invokevirtual 251	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   81: istore_0
    //   82: iload_0
    //   83: ifne +45 -> 128
    //   86: invokestatic 211	com/tencent/mtt/ContextHolder:getAppContext	()Landroid/content/Context;
    //   89: astore_2
    //   90: aload_2
    //   91: ifnull +41 -> 132
    //   94: aload_2
    //   95: invokevirtual 805	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   98: ldc_w 892
    //   101: iconst_0
    //   102: invokevirtual 813	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   105: astore_2
    //   106: aload_2
    //   107: ifnull +15 -> 122
    //   110: iconst_1
    //   111: istore_0
    //   112: goto +10 -> 122
    //   115: astore_2
    //   116: iconst_1
    //   117: putstatic 894	com/tencent/mtt/base/utils/DeviceUtilsF:jdField_g_of_type_Boolean	Z
    //   120: aload_2
    //   121: athrow
    //   122: iconst_1
    //   123: putstatic 894	com/tencent/mtt/base/utils/DeviceUtilsF:jdField_g_of_type_Boolean	Z
    //   126: iload_0
    //   127: ireturn
    //   128: iconst_1
    //   129: putstatic 894	com/tencent/mtt/base/utils/DeviceUtilsF:jdField_g_of_type_Boolean	Z
    //   132: iload_0
    //   133: ireturn
    //   134: astore_2
    //   135: goto -13 -> 122
    // Local variable table:
    //   start	length	slot	name	signature
    //   17	116	0	bool1	boolean
    //   15	48	1	bool2	boolean
    //   89	18	2	localObject1	Object
    //   115	6	2	localObject2	Object
    //   134	1	2	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   94	106	115	finally
    //   94	106	134	java/lang/Exception
  }
  
  public static void checkAdapter(Activity paramActivity)
  {
    checkFeature(paramActivity);
    a(paramActivity);
  }
  
  public static boolean checkFeature(Activity paramActivity)
  {
    try
    {
      boolean bool = hasSmartBar();
      if (!bool) {
        return false;
      }
    }
    catch (Exception paramActivity)
    {
      paramActivity.printStackTrace();
    }
    return true;
  }
  
  public static boolean checkOp(Context paramContext, int paramInt)
  {
    try
    {
      Object localObject1 = Class.forName("android.content.Context");
      Object localObject2 = ((Class)localObject1).getDeclaredField("APP_OPS_SERVICE");
      ((Field)localObject2).setAccessible(true);
      localObject2 = ((Field)localObject2).get(localObject1);
      if ((localObject2 instanceof String))
      {
        localObject1 = ((Class)localObject1).getMethod("getSystemService", getParamTypes((Class)localObject1, "getSystemService")).invoke(paramContext, new Object[] { localObject2 });
        localObject2 = Class.forName("android.app.AppOpsManager");
        Object localObject3 = ((Class)localObject2).getDeclaredField("MODE_ALLOWED");
        ((Field)localObject3).setAccessible(true);
        paramInt = ((Field)localObject3).getInt(localObject2);
        localObject3 = ((Class)localObject2).getDeclaredField("OP_SYSTEM_ALERT_WINDOW");
        ((Field)localObject3).setAccessible(true);
        localObject3 = (Integer)((Field)localObject3).get(localObject2);
        int i1 = Binder.getCallingUid();
        paramContext = paramContext.getPackageName();
        Class[] arrayOfClass = getParamTypes((Class)localObject2, "checkOp");
        arrayOfClass[0] = Integer.TYPE;
        paramContext = ((Class)localObject2).getMethod("checkOp", arrayOfClass).invoke(localObject1, new Object[] { localObject3, Integer.valueOf(i1), paramContext });
        if ((paramContext instanceof Integer))
        {
          i1 = ((Integer)paramContext).intValue();
          if (i1 == paramInt) {
            return true;
          }
        }
        return false;
      }
      return false;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public static void checkStatusBarHeight(Window paramWindow)
  {
    if (sHaveCheckStatusBarHeight) {
      return;
    }
    Rect localRect = new Rect();
    paramWindow.getDecorView().getWindowVisibleDisplayFrame(localRect);
    int i1 = localRect.top;
    if (i1 > 0)
    {
      STATUSBAR_HEIGHT = i1;
      sHaveCheckStatusBarHeight = true;
    }
  }
  
  public static boolean checkSupportInlineDarkIcon()
  {
    if (jdField_b_of_type_Boolean) {
      return jdField_a_of_type_Boolean;
    }
    boolean bool = false;
    try
    {
      Field localField = Activity.class.getDeclaredField("mDisableStatusBarIconTheme");
      if (localField != null) {
        bool = true;
      }
      jdField_a_of_type_Boolean = bool;
      jdField_b_of_type_Boolean = true;
      return jdField_a_of_type_Boolean;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      for (;;) {}
    }
    jdField_a_of_type_Boolean = false;
    jdField_b_of_type_Boolean = true;
    return false;
  }
  
  private static boolean d()
  {
    boolean bool2 = Build.HOST.trim().toLowerCase().contains("smartisan");
    Context localContext;
    boolean bool1;
    if (!bool2)
    {
      localContext = ContextHolder.getAppContext();
      if (localContext == null) {
        break label58;
      }
      bool1 = bool2;
    }
    try
    {
      if (localContext.getPackageManager().getPackageInfo("com.smartisanos.systemui", 0) != null) {
        bool1 = true;
      }
      bool2 = bool1;
      jdField_h_of_type_Boolean = true;
      return bool1;
    }
    catch (Exception localException) {}
    jdField_h_of_type_Boolean = true;
    label58:
    return bool2;
    return bool2;
  }
  
  public static boolean enableQBWebViewLayerTypeSoftware()
  {
    int i1 = getSdkVersion();
    return (i1 == 15) || (i1 == 16) || (i1 == 18) || (i1 == 19);
  }
  
  public static String getAndroidId(Context paramContext)
  {
    if (paramContext != null)
    {
      if (TextUtils.isEmpty(jdField_a_of_type_JavaLangString)) {
        try
        {
          jdField_a_of_type_JavaLangString = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
        }
        catch (Throwable paramContext)
        {
          paramContext.printStackTrace();
        }
      }
      return jdField_a_of_type_JavaLangString;
    }
    return "";
  }
  
  public static String getAndroidOsSystemProperties(String paramString)
  {
    try
    {
      paramString = (String)Class.forName("android.os.SystemProperties").getMethod("get", new Class[] { String.class }).invoke(null, new Object[] { paramString });
      if (paramString != null) {
        return paramString;
      }
      return paramString;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return "";
  }
  
  public static int getAppMaxMemory()
  {
    if (jdField_l_of_type_Int == -1) {
      jdField_l_of_type_Int = ((ActivityManager)ContextHolder.getAppContext().getApplicationContext().getSystemService("activity")).getMemoryClass();
    }
    return jdField_l_of_type_Int;
  }
  
  public static Point getAppUsableScreenSize(Context paramContext)
  {
    paramContext = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
    Point localPoint = new Point();
    paramContext.getSize(localPoint);
    return localPoint;
  }
  
  /* Error */
  public static int getCPUMaxFreqKHz()
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_2
    //   2: iconst_m1
    //   3: istore_0
    //   4: iload_2
    //   5: invokestatic 1036	com/tencent/mtt/base/utils/DeviceUtilsF:getNumberOfCPUCores	()I
    //   8: if_icmpge +163 -> 171
    //   11: new 177	java/lang/StringBuilder
    //   14: dup
    //   15: invokespecial 180	java/lang/StringBuilder:<init>	()V
    //   18: astore_3
    //   19: aload_3
    //   20: ldc_w 1038
    //   23: invokevirtual 189	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   26: pop
    //   27: aload_3
    //   28: iload_2
    //   29: invokevirtual 840	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   32: pop
    //   33: aload_3
    //   34: ldc_w 1040
    //   37: invokevirtual 189	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   40: pop
    //   41: new 842	java/io/File
    //   44: dup
    //   45: aload_3
    //   46: invokevirtual 200	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   49: invokespecial 847	java/io/File:<init>	(Ljava/lang/String;)V
    //   52: astore_3
    //   53: iload_0
    //   54: istore_1
    //   55: aload_3
    //   56: invokevirtual 850	java/io/File:exists	()Z
    //   59: ifeq +179 -> 238
    //   62: sipush 128
    //   65: newarray <illegal type>
    //   67: astore 4
    //   69: new 694	java/io/FileInputStream
    //   72: dup
    //   73: aload_3
    //   74: invokespecial 1041	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   77: astore_3
    //   78: aload_3
    //   79: aload 4
    //   81: invokevirtual 698	java/io/FileInputStream:read	([B)I
    //   84: pop
    //   85: iconst_0
    //   86: istore_1
    //   87: aload 4
    //   89: iload_1
    //   90: baload
    //   91: bipush 48
    //   93: if_icmplt +26 -> 119
    //   96: aload 4
    //   98: iload_1
    //   99: baload
    //   100: bipush 57
    //   102: if_icmpgt +17 -> 119
    //   105: iload_1
    //   106: aload 4
    //   108: arraylength
    //   109: if_icmpge +10 -> 119
    //   112: iload_1
    //   113: iconst_1
    //   114: iadd
    //   115: istore_1
    //   116: goto -29 -> 87
    //   119: new 240	java/lang/String
    //   122: dup
    //   123: aload 4
    //   125: iconst_0
    //   126: iload_1
    //   127: invokespecial 1044	java/lang/String:<init>	([BII)V
    //   130: invokestatic 718	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   133: invokestatic 954	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   136: astore 4
    //   138: iload_0
    //   139: istore_1
    //   140: aload 4
    //   142: invokevirtual 831	java/lang/Integer:intValue	()I
    //   145: iload_0
    //   146: if_icmple +9 -> 155
    //   149: aload 4
    //   151: invokevirtual 831	java/lang/Integer:intValue	()I
    //   154: istore_1
    //   155: aload_3
    //   156: invokevirtual 1045	java/io/FileInputStream:close	()V
    //   159: goto +79 -> 238
    //   162: astore 4
    //   164: aload_3
    //   165: invokevirtual 1045	java/io/FileInputStream:close	()V
    //   168: aload 4
    //   170: athrow
    //   171: iload_0
    //   172: iconst_m1
    //   173: if_icmpne +53 -> 226
    //   176: new 694	java/io/FileInputStream
    //   179: dup
    //   180: ldc_w 844
    //   183: invokespecial 1046	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   186: astore_3
    //   187: ldc_w 1048
    //   190: aload_3
    //   191: invokestatic 1050	com/tencent/mtt/base/utils/DeviceUtilsF:a	(Ljava/lang/String;Ljava/io/FileInputStream;)I
    //   194: istore_1
    //   195: iload_1
    //   196: sipush 1000
    //   199: imul
    //   200: istore_1
    //   201: iload_1
    //   202: iload_0
    //   203: if_icmple +8 -> 211
    //   206: iload_1
    //   207: istore_0
    //   208: goto +3 -> 211
    //   211: aload_3
    //   212: invokevirtual 1045	java/io/FileInputStream:close	()V
    //   215: iload_0
    //   216: ireturn
    //   217: astore 4
    //   219: aload_3
    //   220: invokevirtual 1045	java/io/FileInputStream:close	()V
    //   223: aload 4
    //   225: athrow
    //   226: iload_0
    //   227: ireturn
    //   228: astore_3
    //   229: iconst_m1
    //   230: ireturn
    //   231: astore 4
    //   233: iload_0
    //   234: istore_1
    //   235: goto -80 -> 155
    //   238: iload_2
    //   239: iconst_1
    //   240: iadd
    //   241: istore_2
    //   242: iload_1
    //   243: istore_0
    //   244: goto -240 -> 4
    // Local variable table:
    //   start	length	slot	name	signature
    //   3	241	0	i1	int
    //   54	189	1	i2	int
    //   1	241	2	i3	int
    //   18	202	3	localObject1	Object
    //   228	1	3	localIOException	IOException
    //   67	83	4	localObject2	Object
    //   162	7	4	localObject3	Object
    //   217	7	4	localObject4	Object
    //   231	1	4	localNumberFormatException	NumberFormatException
    // Exception table:
    //   from	to	target	type
    //   78	85	162	finally
    //   105	112	162	finally
    //   119	138	162	finally
    //   140	155	162	finally
    //   187	195	217	finally
    //   4	53	228	java/io/IOException
    //   55	78	228	java/io/IOException
    //   155	159	228	java/io/IOException
    //   164	171	228	java/io/IOException
    //   176	187	228	java/io/IOException
    //   211	215	228	java/io/IOException
    //   219	226	228	java/io/IOException
    //   78	85	231	java/lang/NumberFormatException
    //   105	112	231	java/lang/NumberFormatException
    //   119	138	231	java/lang/NumberFormatException
    //   140	155	231	java/lang/NumberFormatException
  }
  
  /* Error */
  public static String getCPUProcessor()
  {
    // Byte code:
    //   0: ldc 44
    //   2: astore_2
    //   3: aconst_null
    //   4: astore 4
    //   6: aconst_null
    //   7: astore 5
    //   9: aconst_null
    //   10: astore_0
    //   11: new 852	java/io/BufferedReader
    //   14: dup
    //   15: new 1053	java/io/InputStreamReader
    //   18: dup
    //   19: invokestatic 1059	java/lang/Runtime:getRuntime	()Ljava/lang/Runtime;
    //   22: ldc_w 1061
    //   25: invokevirtual 1065	java/lang/Runtime:exec	(Ljava/lang/String;)Ljava/lang/Process;
    //   28: invokevirtual 1071	java/lang/Process:getInputStream	()Ljava/io/InputStream;
    //   31: invokespecial 1074	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   34: invokespecial 860	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   37: astore_1
    //   38: aload_1
    //   39: invokevirtual 863	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   42: astore_3
    //   43: aload_2
    //   44: astore_0
    //   45: aload_3
    //   46: ifnull +30 -> 76
    //   49: aload_3
    //   50: ldc_w 1076
    //   53: invokevirtual 251	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   56: ifeq -18 -> 38
    //   59: aload_3
    //   60: aload_3
    //   61: ldc_w 1078
    //   64: invokevirtual 866	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   67: iconst_1
    //   68: iadd
    //   69: invokevirtual 835	java/lang/String:substring	(I)Ljava/lang/String;
    //   72: invokestatic 1083	com/tencent/common/utils/StringUtils:removeHeadSpace	(Ljava/lang/String;)Ljava/lang/String;
    //   75: astore_0
    //   76: aload_0
    //   77: astore_2
    //   78: aload_1
    //   79: invokevirtual 869	java/io/BufferedReader:close	()V
    //   82: aload_0
    //   83: areturn
    //   84: astore_0
    //   85: aload_0
    //   86: invokevirtual 870	java/io/IOException:printStackTrace	()V
    //   89: aload_2
    //   90: areturn
    //   91: astore_2
    //   92: aload_1
    //   93: astore_0
    //   94: aload_2
    //   95: astore_1
    //   96: goto +61 -> 157
    //   99: astore_0
    //   100: aload_0
    //   101: astore_3
    //   102: goto +17 -> 119
    //   105: astore_0
    //   106: aload_0
    //   107: astore_3
    //   108: goto +32 -> 140
    //   111: astore_1
    //   112: goto +45 -> 157
    //   115: astore_3
    //   116: aload 4
    //   118: astore_1
    //   119: aload_1
    //   120: astore_0
    //   121: aload_3
    //   122: invokevirtual 1008	java/lang/Throwable:printStackTrace	()V
    //   125: aload_1
    //   126: ifnull +28 -> 154
    //   129: aload_1
    //   130: invokevirtual 869	java/io/BufferedReader:close	()V
    //   133: ldc 44
    //   135: areturn
    //   136: astore_3
    //   137: aload 5
    //   139: astore_1
    //   140: aload_1
    //   141: astore_0
    //   142: aload_3
    //   143: invokevirtual 870	java/io/IOException:printStackTrace	()V
    //   146: aload_1
    //   147: ifnull +7 -> 154
    //   150: aload_1
    //   151: invokevirtual 869	java/io/BufferedReader:close	()V
    //   154: ldc 44
    //   156: areturn
    //   157: aload_0
    //   158: ifnull +15 -> 173
    //   161: aload_0
    //   162: invokevirtual 869	java/io/BufferedReader:close	()V
    //   165: goto +8 -> 173
    //   168: astore_0
    //   169: aload_0
    //   170: invokevirtual 870	java/io/IOException:printStackTrace	()V
    //   173: aload_1
    //   174: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   10	73	0	localObject1	Object
    //   84	2	0	localIOException1	IOException
    //   93	1	0	localObject2	Object
    //   99	2	0	localThrowable1	Throwable
    //   105	2	0	localIOException2	IOException
    //   120	42	0	localObject3	Object
    //   168	2	0	localIOException3	IOException
    //   37	59	1	localObject4	Object
    //   111	1	1	localObject5	Object
    //   118	56	1	localObject6	Object
    //   2	88	2	localObject7	Object
    //   91	4	2	localObject8	Object
    //   42	66	3	localObject9	Object
    //   115	7	3	localThrowable2	Throwable
    //   136	7	3	localIOException4	IOException
    //   4	113	4	localObject10	Object
    //   7	131	5	localObject11	Object
    // Exception table:
    //   from	to	target	type
    //   78	82	84	java/io/IOException
    //   129	133	84	java/io/IOException
    //   150	154	84	java/io/IOException
    //   38	43	91	finally
    //   49	76	91	finally
    //   38	43	99	java/lang/Throwable
    //   49	76	99	java/lang/Throwable
    //   38	43	105	java/io/IOException
    //   49	76	105	java/io/IOException
    //   11	38	111	finally
    //   121	125	111	finally
    //   142	146	111	finally
    //   11	38	115	java/lang/Throwable
    //   11	38	136	java/io/IOException
    //   161	165	168	java/io/IOException
  }
  
  public static int getCanvasWidth()
  {
    return getWidth();
  }
  
  public static float getDensity()
  {
    if (jdField_a_of_type_Float < 0.0F)
    {
      WindowManager localWindowManager = (WindowManager)ContextHolder.getAppContext().getSystemService("window");
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
      jdField_a_of_type_Float = localDisplayMetrics.density;
    }
    return jdField_a_of_type_Float;
  }
  
  public static int getDensityDpi()
  {
    if (jdField_o_of_type_Int == -1) {}
    try
    {
      WindowManager localWindowManager = (WindowManager)ContextHolder.getAppContext().getSystemService("window");
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
      jdField_o_of_type_Int = localDisplayMetrics.densityDpi;
      return jdField_o_of_type_Int;
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
  }
  
  public static long getDeviceAvailableMemory(Context paramContext)
  {
    paramContext = (ActivityManager)paramContext.getSystemService("activity");
    if (paramContext != null)
    {
      ActivityManager.MemoryInfo localMemoryInfo = new ActivityManager.MemoryInfo();
      paramContext.getMemoryInfo(localMemoryInfo);
      return localMemoryInfo.availMem / 1024L / 1024L;
    }
    return 0L;
  }
  
  public static String getDeviceBrand()
  {
    String str = Build.BRAND;
    if (str == null) {
      return "";
    }
    return str.toLowerCase();
  }
  
  @TargetApi(17)
  public static int getDeviceHeight()
  {
    try
    {
      if (mWm == null) {
        mWm = (WindowManager)ContextHolder.getAppContext().getSystemService("window");
      }
      int i1 = mWm.getDefaultDisplay().getHeight();
      if (getSdkVersion() >= 17)
      {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        mWm.getDefaultDisplay().getRealMetrics(localDisplayMetrics);
        int i2 = localDisplayMetrics.heightPixels;
        if (i2 > i1) {
          return i2;
        }
      }
      return i1;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return 0;
  }
  
  public static String getDeviceManufacturer()
  {
    String str = Build.MANUFACTURER;
    if (str == null) {
      return "";
    }
    return str.toLowerCase();
  }
  
  public static String getDeviceModel()
  {
    return Build.MODEL.replaceAll("[ |\\/|\\_|\\&|\\|]", "");
  }
  
  public static String getDeviceName()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" ");
    localStringBuilder.append(Build.MODEL.replaceAll("[ |\\/|\\_|\\&|\\|]", ""));
    localStringBuilder.append(" ");
    return localStringBuilder.toString();
  }
  
  public static int getDeviceYear()
  {
    if (jdField_h_of_type_Int == -1) {
      jdField_h_of_type_Int = DeviceYearClass.get();
    }
    return jdField_h_of_type_Int;
  }
  
  public static float getEMUIVersion()
  {
    if (isEMUI())
    {
      Object localObject = getSystemProperty("ro.build.version.emui");
      localObject = Pattern.compile("\\d+\\.\\d+").matcher((CharSequence)localObject);
      if (((Matcher)localObject).find())
      {
        localObject = ((Matcher)localObject).group();
        try
        {
          float f1 = Float.parseFloat((String)localObject);
          return f1;
        }
        catch (Throwable localThrowable)
        {
          localThrowable.printStackTrace();
        }
      }
    }
    return -1.0F;
  }
  
  public static int getHeight()
  {
    if (mIsInSystemMultiWindow)
    {
      if (jdField_j_of_type_Int <= 0) {
        jdField_j_of_type_Int = ContextHolder.getAppContext().getResources().getConfiguration().screenHeightDp;
      }
      return (int)(jdField_j_of_type_Int * getDensity());
    }
    return ContextHolder.getAppContext().getResources().getDisplayMetrics().heightPixels;
  }
  
  public static int getHeightHC()
  {
    Context localContext = ContextHolder.getAppContext();
    if (localContext != null)
    {
      int i1 = getStatusBarHeightFromSystem();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getStatusBarHeight()=");
      localStringBuilder.append(i1);
      localStringBuilder.toString();
      return a(localContext, localContext.getResources().getConfiguration().screenHeightDp) + i1;
    }
    return 0;
  }
  
  @Deprecated
  public static int getHeightPreHC()
  {
    try
    {
      if (mWm == null) {
        mWm = (WindowManager)ContextHolder.getAppContext().getSystemService("window");
      }
      int i1 = mWm.getDefaultDisplay().getHeight();
      return i1;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return 0;
  }
  
  public static String getIMEI()
  {
    if (jdField_c_of_type_JavaLangString == null) {}
    try
    {
      Object localObject = getTelephonyManager();
      if (localObject == null) {
        return "";
      }
      localObject = ((TelephonyManager)localObject).getDeviceId();
      if (localObject == null) {
        return "";
      }
      jdField_c_of_type_JavaLangString = (String)localObject;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return "";
    return jdField_c_of_type_JavaLangString;
  }
  
  public static String getIMSI()
  {
    if ((TextUtils.isEmpty(jdField_d_of_type_JavaLangString)) || (System.currentTimeMillis() - jdField_a_of_type_Long > 600000L)) {}
    try
    {
      localObject = getTelephonyManager();
      if (localObject == null)
      {
        jdField_d_of_type_JavaLangString = "";
      }
      else
      {
        jdField_d_of_type_JavaLangString = ((TelephonyManager)localObject).getSubscriberId();
        jdField_a_of_type_Long = System.currentTimeMillis();
      }
    }
    catch (Exception localException)
    {
      Object localObject;
      for (;;) {}
    }
    jdField_d_of_type_JavaLangString = "";
    localObject = jdField_d_of_type_JavaLangString;
    if (localObject == null) {
      return "";
    }
    return (String)localObject;
  }
  
  public static boolean getIsRoot(Context paramContext)
  {
    int i1 = mRootStatus;
    boolean bool = false;
    if (i1 != -1)
    {
      if (i1 == 1) {
        bool = true;
      }
      return bool;
    }
    bool = getIsRootByFile();
    if (bool)
    {
      mRootStatus = 1;
      return bool;
    }
    mRootStatus = 0;
    return bool;
  }
  
  public static boolean getIsRootByApk(Context paramContext)
  {
    if (paramContext == null) {
      return false;
    }
    Object localObject = new File("/data/data/root");
    boolean bool = true;
    try
    {
      ((File)localObject).createNewFile();
      if (!((File)localObject).exists()) {
        break label280;
      }
      ((File)localObject).delete();
      return true;
    }
    catch (IOException localIOException)
    {
      int i2;
      int i1;
      for (;;) {}
    }
    localObject = new String[24];
    localObject[0] = "com.root.superuser";
    localObject[1] = "com.qihoo.permmgr";
    localObject[2] = "com.zhiqupk.root";
    localObject[3] = "com.mgyun.shua.su";
    localObject[4] = "com.kingroot.RushRoot";
    localObject[5] = "hh.root";
    localObject[6] = "com.lbe.security";
    localObject[7] = "com.nb.roottool";
    localObject[8] = "eu.chainfire.supersu";
    localObject[9] = "eu.chainfire.supersu.pro";
    localObject[10] = "com.noshufou.android.su";
    localObject[11] = "com.noshufou.android.su.elite";
    localObject[12] = "com.noshufou.android.su.fixer";
    localObject[13] = "com.baidu.easyroot";
    localObject[14] = "com.root.superr.administrater";
    localObject[15] = "com.qihoo.permroot";
    localObject[16] = "com.mgyun.rootmaster";
    localObject[17] = "com.koushikdutta.superuser";
    localObject[18] = "com.bestqiang.su";
    localObject[19] = "com.ifreesoft.ifreesu";
    localObject[20] = "com.miui.uac";
    localObject[21] = "com.lbe.security.miui";
    localObject[22] = "com.kingroot.kinguser";
    localObject[23] = "com.lbe.security.su";
    i2 = localObject.length;
    i1 = 0;
    while (i1 < i2)
    {
      String str = localObject[i1];
      if (PackageUtils.getInstalledPKGInfo(str, paramContext) != null)
      {
        FLogger.d("DeviceUtilsF", str);
        return true;
      }
      i1 += 1;
    }
    bool = false;
    label280:
    return bool;
  }
  
  public static boolean getIsRootByFile()
  {
    FLogger.d("DeviceUtilsF", "getIsRootByFile");
    String[] arrayOfString = new String[5];
    arrayOfString[0] = "/system/bin/";
    arrayOfString[1] = "/system/xbin/";
    arrayOfString[2] = "/system/sbin/";
    arrayOfString[3] = "/sbin/";
    arrayOfString[4] = "/vendor/bin/";
    int i1 = 0;
    try
    {
      while (i1 < arrayOfString.length)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(arrayOfString[i1]);
        localStringBuilder.append("su");
        if (new File(localStringBuilder.toString()).exists())
        {
          FLogger.d("DeviceUtilsF", arrayOfString[i1]);
          return true;
        }
        i1 += 1;
      }
      return false;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public static Map<String, String> getIsSupportImmersiveModeData()
  {
    if (getSdkVersion() >= 19)
    {
      boolean bool3 = ViewConfiguration.get(ContextHolder.getAppContext()).hasPermanentMenuKey() ^ true;
      boolean bool4 = KeyCharacterMap.deviceHasKey(4) ^ true;
      boolean bool5 = KeyCharacterMap.deviceHasKey(3) ^ true;
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("getIsSupportImmersiveMode() menu=");
      ((StringBuilder)localObject1).append(bool3);
      ((StringBuilder)localObject1).append(" back=");
      ((StringBuilder)localObject1).append(bool4);
      ((StringBuilder)localObject1).append(" home=");
      ((StringBuilder)localObject1).append(bool5);
      ((StringBuilder)localObject1).append(" api=");
      ((StringBuilder)localObject1).append(getSdkVersion());
      FLogger.d("DeviceUtilsF", ((StringBuilder)localObject1).toString());
      boolean bool1;
      if ((bool3) && (bool4) && (bool5)) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      boolean bool2 = bool1;
      if (!bool1) {
        if ((bool3) && ((isOppoR7s) || (isOppoR7Plus) || (isHUAWEI_MT7))) {
          bool2 = true;
        } else {
          bool2 = false;
        }
      }
      Object localObject2 = getDeviceModel();
      localObject1 = new HashMap();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(getDeviceBrand());
      localStringBuilder.append("_");
      localStringBuilder.append((String)localObject2);
      ((HashMap)localObject1).put("device", localStringBuilder.toString());
      ((HashMap)localObject1).put("build_model", Build.MODEL);
      ((HashMap)localObject1).put("build_brand", Build.BRAND);
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("");
      ((StringBuilder)localObject2).append(bool3);
      ((HashMap)localObject1).put("check_menu", ((StringBuilder)localObject2).toString());
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("");
      ((StringBuilder)localObject2).append(bool4);
      ((HashMap)localObject1).put("check_back", ((StringBuilder)localObject2).toString());
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("");
      ((StringBuilder)localObject2).append(bool5);
      ((HashMap)localObject1).put("check_home", ((StringBuilder)localObject2).toString());
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("");
      ((StringBuilder)localObject2).append(getSdkVersion());
      ((HashMap)localObject1).put("api", ((StringBuilder)localObject2).toString());
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("");
      ((StringBuilder)localObject2).append(bool2);
      ((HashMap)localObject1).put("support", ((StringBuilder)localObject2).toString());
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("getIsSupportImmersiveMode() report=");
      ((StringBuilder)localObject2).append(localObject1);
      FLogger.d("DeviceUtilsF", ((StringBuilder)localObject2).toString());
      return (Map<String, String>)localObject1;
    }
    return null;
  }
  
  public static int getMIUIRealHeight()
  {
    int i2 = getHeight();
    Object localObject = (WindowManager)ContextHolder.getAppContext().getSystemService("window");
    int i1 = i2;
    if (localObject != null)
    {
      localObject = ((WindowManager)localObject).getDefaultDisplay();
      Point localPoint1 = new Point();
      Point localPoint2 = new Point();
      i1 = i2;
      if (Build.VERSION.SDK_INT >= 16)
      {
        ((Display)localObject).getCurrentSizeRange(localPoint1, localPoint2);
        i1 = Math.max(localPoint2.x, localPoint2.y);
      }
    }
    return i1;
  }
  
  public static String getMIUISystemProperty()
  {
    String str = getSystemProperty("ro.miui.ui.version.name");
    if (TextUtils.isEmpty(str)) {
      return "NUKNOW";
    }
    return str;
  }
  
  public static byte[] getMacAddress()
  {
    String str = getMacAddressString();
    if (!TextUtils.isEmpty(str)) {
      return DesUtils.DesEncrypt(DesUtils.MAC_KEY, str.getBytes(), 1);
    }
    return null;
  }
  
  public static String getMacAddressString()
  {
    Object localObject1 = ContextHolder.getAppContext();
    String str = "";
    WifiManager localWifiManager = (WifiManager)((Context)localObject1).getSystemService("wifi");
    Object localObject3 = null;
    localObject1 = localObject3;
    if (localWifiManager != null) {
      try
      {
        localObject1 = localWifiManager.getConnectionInfo();
      }
      catch (Throwable localThrowable)
      {
        FLogger.e("DeviceUtilsF", localThrowable);
        localObject2 = localObject3;
      }
    }
    if (localObject2 != null) {
      str = ((WifiInfo)localObject2).getMacAddress();
    }
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("========mac adress===========:");
    ((StringBuilder)localObject2).append(str);
    FLogger.d("DeviceUtilsF", ((StringBuilder)localObject2).toString());
    return str;
  }
  
  public static int getMaxEdge()
  {
    return Math.max(getWidth(), getHeight());
  }
  
  public static int getMinEdge()
  {
    return Math.min(getWidth(), getHeight());
  }
  
  public static int getMobilePerformance()
  {
    if (jdField_g_of_type_Int == -1)
    {
      int i1 = getDeviceYear();
      if (i1 <= 2010) {
        jdField_g_of_type_Int = 0;
      } else if (i1 <= 2013) {
        jdField_g_of_type_Int = 1;
      } else {
        jdField_g_of_type_Int = 2;
      }
    }
    return jdField_g_of_type_Int;
  }
  
  public static Point getNavigationBarSize(Context paramContext)
  {
    Point localPoint = getAppUsableScreenSize(paramContext);
    paramContext = getRealScreenSize(paramContext);
    if (localPoint.x < paramContext.x) {
      return new Point(localPoint.y, paramContext.x - localPoint.x);
    }
    if (localPoint.y < paramContext.y) {
      return new Point(localPoint.x, paramContext.y - localPoint.y);
    }
    return new Point();
  }
  
  @Deprecated
  public static int getNetworkClass()
  {
    throw new RuntimeException("改用Apn.getApnInfo()");
  }
  
  public static String getNetworkType()
  {
    String str = "GPRS";
    int i1 = getNetworkTypeInt();
    if (i1 == 1) {
      return "GPRS";
    }
    if (i1 == 2) {
      return "EDGE";
    }
    if (i1 == 3) {
      return "UMTS";
    }
    if (i1 == 8) {
      return "HSDPA";
    }
    if (i1 == 9) {
      return "HSUPA";
    }
    if (i1 == 10) {
      return "HSPA";
    }
    if (i1 == 4) {
      return "CDMA";
    }
    if (i1 == 5) {
      return "CDMAEVDO0";
    }
    if (i1 == 6) {
      return "CDMAEVDOA";
    }
    if (i1 == 7) {
      str = "CDMA1xRTT";
    }
    return str;
  }
  
  public static int getNetworkTypeInt()
  {
    TelephonyManager localTelephonyManager = getTelephonyManager();
    if (localTelephonyManager != null) {
      return localTelephonyManager.getNetworkType();
    }
    return 0;
  }
  
  public static String getNewBeeROMName()
  {
    return getAndroidOsSystemProperties("ro.build.version.newbee.display");
  }
  
  public static int getNumberOfCPUCores()
  {
    try
    {
      new File("/sys/devices/system/cpu/");
      File[] arrayOfFile = FileListJNI.fileList("/sys/devices/system/cpu/", new a());
      if (arrayOfFile == null) {
        return 1;
      }
      int i1 = arrayOfFile.length;
      return i1;
    }
    catch (Exception localException)
    {
      FLogger.d("DeviceUtilsF", "CPU Count: Failed.");
      localException.printStackTrace();
    }
    return 1;
  }
  
  public static Class[] getParamTypes(Class paramClass, String paramString)
  {
    Method[] arrayOfMethod = paramClass.getDeclaredMethods();
    paramClass = null;
    int i1 = 0;
    while (i1 < arrayOfMethod.length)
    {
      if (arrayOfMethod[i1].getName().equals(paramString)) {
        paramClass = arrayOfMethod[i1].getParameterTypes();
      }
      i1 += 1;
    }
    return paramClass;
  }
  
  public static String getPhoneNumber()
  {
    try
    {
      Object localObject = getTelephonyManager();
      if (localObject == null) {
        return "";
      }
      localObject = ((TelephonyManager)localObject).getLine1Number();
      return (String)localObject;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return "";
  }
  
  public static String getPhoneType()
  {
    TelephonyManager localTelephonyManager = getTelephonyManager();
    String str2 = "GSM";
    String str1 = str2;
    if (localTelephonyManager != null)
    {
      int i1 = localTelephonyManager.getPhoneType();
      if (i1 == 1) {
        return "GSM";
      }
      str1 = str2;
      if (i1 == 2) {
        str1 = "CDMA";
      }
    }
    return str1;
  }
  
  public static int getRAM()
  {
    return getTotalRAMMemory();
  }
  
  public static float getROMMemery()
  {
    Object localObject = new StatFs(Environment.getDataDirectory().getPath());
    long l1 = ((StatFs)localObject).getAvailableBlocks();
    float f1 = (float)(((StatFs)localObject).getBlockSize() * l1 / 1024L / 1024L);
    l1 = ((StatFs)localObject).getBlockCount();
    float f2 = (float)(((StatFs)localObject).getBlockSize() * l1 / 1024L / 1024L);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("av: ");
    ((StringBuilder)localObject).append(f1);
    ((StringBuilder)localObject).append(", total: ");
    ((StringBuilder)localObject).append(f2);
    ((StringBuilder)localObject).toString();
    return f2;
  }
  
  public static Point getRealScreenSize(Context paramContext)
  {
    Display localDisplay = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
    paramContext = new Point();
    if (Build.VERSION.SDK_INT >= 17)
    {
      localDisplay.getRealSize(paramContext);
      return paramContext;
    }
    if (Build.VERSION.SDK_INT >= 14) {}
    try
    {
      paramContext.x = ((Integer)Display.class.getMethod("getRawWidth", new Class[0]).invoke(localDisplay, new Object[0])).intValue();
      paramContext.y = ((Integer)Display.class.getMethod("getRawHeight", new Class[0]).invoke(localDisplay, new Object[0])).intValue();
      return paramContext;
    }
    catch (IllegalAccessException|InvocationTargetException|NoSuchMethodException localIllegalAccessException) {}
    return paramContext;
  }
  
  public static int getScreenDpi(Context paramContext)
  {
    try
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
      if (localDisplayMetrics.density == 1.0F) {
        return 160;
      }
      if (localDisplayMetrics.density <= 0.75D) {
        return 120;
      }
      if (localDisplayMetrics.density == 1.5D) {
        return 240;
      }
      if (localDisplayMetrics.density == 2.0D) {
        return 320;
      }
      float f1 = localDisplayMetrics.density;
      if (f1 > 2.0D) {
        return 480;
      }
    }
    catch (Throwable paramContext)
    {
      paramContext.printStackTrace();
    }
    return 160;
  }
  
  @Deprecated
  public static int getScreenHeigh()
  {
    if (jdField_c_of_type_Int <= 0) {
      a();
    }
    return jdField_c_of_type_Int;
  }
  
  public static int getScreenMinWidth()
  {
    WindowManager localWindowManager = (WindowManager)ContextHolder.getAppContext().getSystemService("window");
    if (localWindowManager != null) {}
    try
    {
      int i1 = Math.min(localWindowManager.getDefaultDisplay().getWidth(), localWindowManager.getDefaultDisplay().getHeight());
      return i1;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return -1;
  }
  
  public static int getScreenMinWidth(WindowManager paramWindowManager)
  {
    if (paramWindowManager != null) {}
    try
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      paramWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
      int i1 = Math.min(localDisplayMetrics.heightPixels, localDisplayMetrics.widthPixels);
      return i1;
    }
    catch (Exception paramWindowManager)
    {
      for (;;) {}
    }
    return -1;
  }
  
  @Deprecated
  public static int getScreenWidth()
  {
    if (jdField_b_of_type_Int <= 0) {
      a();
    }
    return jdField_b_of_type_Int;
  }
  
  public static int getSdkVersion()
  {
    if (-1 == jdField_a_of_type_Int) {
      jdField_a_of_type_Int = Integer.parseInt(Build.VERSION.SDK);
    }
    return jdField_a_of_type_Int;
  }
  
  public static int getStatusBarHeightFromSystem()
  {
    int i1 = jdField_m_of_type_Int;
    if (i1 > 0) {
      return i1;
    }
    try
    {
      Class localClass = Class.forName("com.android.internal.R$dimen");
      Object localObject = localClass.newInstance();
      i1 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
      jdField_m_of_type_Int = ContextHolder.getAppContext().getResources().getDimensionPixelSize(i1);
    }
    catch (Exception localException1)
    {
      jdField_m_of_type_Int = -1;
      localException1.printStackTrace();
    }
    if (jdField_m_of_type_Int < 1) {
      try
      {
        i1 = ContextHolder.getAppContext().getResources().getIdentifier("statebar_height", "dimen", ContextHolder.getAppContext().getPackageName());
        jdField_m_of_type_Int = Math.round(ContextHolder.getAppContext().getResources().getDimension(i1));
      }
      catch (Exception localException2)
      {
        jdField_m_of_type_Int = -1;
        localException2.printStackTrace();
      }
    }
    return jdField_m_of_type_Int;
  }
  
  public static String getSysVersion()
  {
    try
    {
      String str = Build.DISPLAY.replaceAll("[&=]", ".");
      return str;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return Build.DISPLAY;
  }
  
  public static int getSystemNaviBarHeight()
  {
    int i1 = jdField_n_of_type_Int;
    if (i1 != -1) {
      return i1;
    }
    jdField_n_of_type_Int = getNavigationBarSize(ContextHolder.getAppContext()).y;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getSystemnativebarHeight : ");
    localStringBuilder.append(jdField_n_of_type_Int);
    FLogger.d("DeviceUtilsF", localStringBuilder.toString());
    return jdField_n_of_type_Int;
  }
  
  public static String getSystemProperty(String paramString)
  {
    return (String)a(a(a("android.os.SystemProperties"), "get", new Class[] { String.class }), null, new Object[] { paramString });
  }
  
  public static TelephonyManager getTelephonyManager()
  {
    Context localContext = ContextHolder.getAppContext();
    if (localContext != null) {
      return (TelephonyManager)localContext.getSystemService("phone");
    }
    return null;
  }
  
  /* Error */
  public static int getTotalRAMMemory()
  {
    // Byte code:
    //   0: getstatic 645	com/tencent/mtt/base/utils/DeviceUtilsF:jdField_k_of_type_Int	I
    //   3: istore_0
    //   4: iload_0
    //   5: ifle +5 -> 10
    //   8: iload_0
    //   9: ireturn
    //   10: new 852	java/io/BufferedReader
    //   13: dup
    //   14: new 854	java/io/FileReader
    //   17: dup
    //   18: ldc_w 1612
    //   21: invokespecial 1613	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   24: sipush 8192
    //   27: invokespecial 1616	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   30: astore_2
    //   31: aload_2
    //   32: astore_1
    //   33: aload_2
    //   34: invokevirtual 863	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   37: astore_3
    //   38: aload_3
    //   39: ifnull +80 -> 119
    //   42: aload_2
    //   43: astore_1
    //   44: aload_3
    //   45: ldc_w 1618
    //   48: invokevirtual 866	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   51: istore_0
    //   52: iconst_m1
    //   53: iload_0
    //   54: if_icmpeq -23 -> 31
    //   57: aload_2
    //   58: astore_1
    //   59: aload_3
    //   60: iload_0
    //   61: bipush 9
    //   63: iadd
    //   64: invokevirtual 835	java/lang/String:substring	(I)Ljava/lang/String;
    //   67: invokevirtual 243	java/lang/String:trim	()Ljava/lang/String;
    //   70: astore_3
    //   71: aload_2
    //   72: astore_1
    //   73: aload_3
    //   74: invokestatic 639	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   77: ifne +42 -> 119
    //   80: aload_2
    //   81: astore_1
    //   82: aload_3
    //   83: ldc_w 1619
    //   86: invokevirtual 251	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   89: ifeq +30 -> 119
    //   92: aload_2
    //   93: astore_1
    //   94: aload_3
    //   95: iconst_0
    //   96: aload_3
    //   97: ldc_w 1619
    //   100: invokevirtual 866	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   103: invokevirtual 1622	java/lang/String:substring	(II)Ljava/lang/String;
    //   106: invokevirtual 243	java/lang/String:trim	()Ljava/lang/String;
    //   109: invokestatic 718	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   112: sipush 1024
    //   115: idiv
    //   116: putstatic 645	com/tencent/mtt/base/utils/DeviceUtilsF:jdField_k_of_type_Int	I
    //   119: aload_2
    //   120: invokevirtual 869	java/io/BufferedReader:close	()V
    //   123: goto +62 -> 185
    //   126: astore_3
    //   127: goto +16 -> 143
    //   130: astore_3
    //   131: goto +32 -> 163
    //   134: astore_2
    //   135: aconst_null
    //   136: astore_1
    //   137: goto +53 -> 190
    //   140: astore_3
    //   141: aconst_null
    //   142: astore_2
    //   143: aload_2
    //   144: astore_1
    //   145: aload_3
    //   146: invokevirtual 1008	java/lang/Throwable:printStackTrace	()V
    //   149: aload_2
    //   150: ifnull +35 -> 185
    //   153: aload_2
    //   154: invokevirtual 869	java/io/BufferedReader:close	()V
    //   157: goto +28 -> 185
    //   160: astore_3
    //   161: aconst_null
    //   162: astore_2
    //   163: aload_2
    //   164: astore_1
    //   165: aload_3
    //   166: invokevirtual 870	java/io/IOException:printStackTrace	()V
    //   169: aload_2
    //   170: ifnull +15 -> 185
    //   173: aload_2
    //   174: invokevirtual 869	java/io/BufferedReader:close	()V
    //   177: goto +8 -> 185
    //   180: astore_1
    //   181: aload_1
    //   182: invokevirtual 870	java/io/IOException:printStackTrace	()V
    //   185: getstatic 645	com/tencent/mtt/base/utils/DeviceUtilsF:jdField_k_of_type_Int	I
    //   188: ireturn
    //   189: astore_2
    //   190: aload_1
    //   191: ifnull +15 -> 206
    //   194: aload_1
    //   195: invokevirtual 869	java/io/BufferedReader:close	()V
    //   198: goto +8 -> 206
    //   201: astore_1
    //   202: aload_1
    //   203: invokevirtual 870	java/io/IOException:printStackTrace	()V
    //   206: aload_2
    //   207: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   3	61	0	i1	int
    //   32	133	1	localObject1	Object
    //   180	15	1	localIOException1	IOException
    //   201	2	1	localIOException2	IOException
    //   30	90	2	localBufferedReader	java.io.BufferedReader
    //   134	1	2	localObject2	Object
    //   142	32	2	localObject3	Object
    //   189	18	2	localObject4	Object
    //   37	60	3	str	String
    //   126	1	3	localThrowable1	Throwable
    //   130	1	3	localIOException3	IOException
    //   140	6	3	localThrowable2	Throwable
    //   160	6	3	localIOException4	IOException
    // Exception table:
    //   from	to	target	type
    //   33	38	126	java/lang/Throwable
    //   44	52	126	java/lang/Throwable
    //   59	71	126	java/lang/Throwable
    //   73	80	126	java/lang/Throwable
    //   82	92	126	java/lang/Throwable
    //   94	119	126	java/lang/Throwable
    //   33	38	130	java/io/IOException
    //   44	52	130	java/io/IOException
    //   59	71	130	java/io/IOException
    //   73	80	130	java/io/IOException
    //   82	92	130	java/io/IOException
    //   94	119	130	java/io/IOException
    //   10	31	134	finally
    //   10	31	140	java/lang/Throwable
    //   10	31	160	java/io/IOException
    //   119	123	180	java/io/IOException
    //   153	157	180	java/io/IOException
    //   173	177	180	java/io/IOException
    //   33	38	189	finally
    //   44	52	189	finally
    //   59	71	189	finally
    //   73	80	189	finally
    //   82	92	189	finally
    //   94	119	189	finally
    //   145	149	189	finally
    //   165	169	189	finally
    //   194	198	201	java/io/IOException
  }
  
  public static float getUsedMemoryUsage(Context paramContext)
  {
    long l1 = getTotalRAMMemory();
    long l2 = getDeviceAvailableMemory(paramContext);
    if ((l1 != 0L) && (l2 != 0L)) {
      return (float)(l1 - l2) / (float)l1;
    }
    return 0.0F;
  }
  
  public static int getWidth()
  {
    if (mIsInSystemMultiWindow)
    {
      if (jdField_i_of_type_Int <= 0) {
        jdField_i_of_type_Int = ContextHolder.getAppContext().getResources().getConfiguration().screenWidthDp;
      }
      return (int)(jdField_i_of_type_Int * getDensity());
    }
    return ContextHolder.getAppContext().getResources().getDisplayMetrics().widthPixels;
  }
  
  public static int getWidthHC()
  {
    Context localContext = ContextHolder.getAppContext();
    if (localContext != null) {
      return a(localContext, localContext.getResources().getConfiguration().screenWidthDp);
    }
    return 0;
  }
  
  @Deprecated
  public static int getWidthPreHC()
  {
    try
    {
      if (mWm == null) {
        mWm = (WindowManager)ContextHolder.getAppContext().getSystemService("window");
      }
      int i1 = mWm.getDefaultDisplay().getWidth();
      return i1;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return 0;
  }
  
  public static void handleViberate(long[] paramArrayOfLong, boolean paramBoolean)
  {
    for (;;)
    {
      try
      {
        Context localContext = ContextHolder.getAppContext();
        Object localObject = (AudioManager)localContext.getSystemService("audio");
        if ((localObject == null) || (((AudioManager)localObject).getVibrateSetting(0) == 0)) {
          break label81;
        }
        if (paramBoolean)
        {
          localObject = (Vibrator)localContext.getSystemService("vibrator");
          if (Settings.System.getInt(localContext.getContentResolver(), "haptic_feedback_enabled", 1) != 0)
          {
            ((Vibrator)localObject).vibrate(paramArrayOfLong, -1);
            return;
          }
        }
      }
      catch (Exception paramArrayOfLong)
      {
        FLogger.d("handleViberate", "handleViberate have an error");
        paramArrayOfLong.printStackTrace();
      }
      return;
      label81:
      paramBoolean = false;
    }
  }
  
  public static void handleViberateAsync(long[] paramArrayOfLong, final boolean paramBoolean)
  {
    BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        DeviceUtilsF.handleViberate(this.jdField_a_of_type_ArrayOfLong, paramBoolean);
      }
    });
  }
  
  public static boolean hasSmartBar()
  {
    if (B) {
      return A;
    }
    try
    {
      A = ((Boolean)Class.forName("android.os.Build").getMethod("hasSmartBar", new Class[0]).invoke(null, new Object[0])).booleanValue();
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    if (Build.DEVICE.equalsIgnoreCase("mx2")) {
      A = true;
    } else if ((Build.DEVICE.equalsIgnoreCase("mx")) || (Build.DEVICE.equalsIgnoreCase("m9"))) {
      A = false;
    }
    B = true;
    return A;
  }
  
  public static void hideSmartBarForMXView(View paramView)
  {
    ApiCompat.hideNavigation(paramView);
  }
  
  public static void initStatusBarHeight(int paramInt)
  {
    STATUSBAR_HEIGHT = paramInt;
  }
  
  public static boolean isAboveKitkat()
  {
    return Build.VERSION.SDK_INT >= 19;
  }
  
  public static boolean isActiveHardware()
  {
    return getSdkVersion() >= 16;
  }
  
  public static boolean isAlertWindowOpen(Context paramContext)
  {
    int i1 = Build.VERSION.SDK_INT;
    String str = getMIUISystemProperty();
    if ((!"V5".equals(str)) && (!"V6".equals(str)))
    {
      if (i1 > 17) {
        return checkOp(paramContext, 24);
      }
      return true;
    }
    if (i1 >= 19) {
      return checkOp(paramContext, 24);
    }
    return (paramContext.getApplicationInfo().flags & 0x8000000) != 0;
  }
  
  public static boolean isArmV5()
  {
    if (jdField_d_of_type_Int < 0) {
      if (a("cpu architecture: 5")) {
        jdField_d_of_type_Int = 1;
      } else {
        jdField_d_of_type_Int = 0;
      }
    }
    return jdField_d_of_type_Int > 0;
  }
  
  public static boolean isArmV7()
  {
    return a("armv7");
  }
  
  public static boolean isEMUI()
  {
    if (!p)
    {
      String str = getSystemProperty("ro.build.version.emui");
      if (!TextUtils.isEmpty(str)) {
        isEMUI = str.toLowerCase(Locale.ENGLISH).startsWith("emotionui");
      }
      p = true;
    }
    return isEMUI;
  }
  
  public static boolean isEMUI3()
  {
    if (!q)
    {
      String str = getSystemProperty("ro.build.version.emui");
      if (!TextUtils.isEmpty(str)) {
        isEMUI3 = str.toLowerCase(Locale.ENGLISH).startsWith("emotionui_3");
      }
      q = true;
    }
    return isEMUI3;
  }
  
  public static boolean isEMUI4()
  {
    if (!r)
    {
      String str = getSystemProperty("ro.build.version.emui");
      if (!TextUtils.isEmpty(str)) {
        isEMUI4 = str.toLowerCase(Locale.ENGLISH).startsWith("emotionui_4");
      }
      r = true;
    }
    return isEMUI4;
  }
  
  public static boolean isEMUI5()
  {
    if (!s)
    {
      String str = getSystemProperty("ro.build.version.emui");
      if (!TextUtils.isEmpty(str)) {
        isEMUI5 = str.toLowerCase(Locale.ENGLISH).startsWith("emotionui_5");
      }
      s = true;
    }
    return isEMUI5;
  }
  
  public static boolean isEUI()
  {
    if (!t)
    {
      if (!TextUtils.isEmpty(getSystemProperty("ro.letv.release.version"))) {
        isEUI = true;
      }
      t = true;
    }
    return isEUI;
  }
  
  public static boolean isEmuiHeighSystem()
  {
    Intent localIntent = new Intent();
    localIntent.setClassName("com.android.settings", "com.android.settings.Settings$PreferredSettingsActivity");
    return ContextHolder.getAppContext().getPackageManager().queryIntentActivities(localIntent, 65536).size() > 0;
  }
  
  public static boolean isFlyme()
  {
    if (!jdField_m_of_type_Boolean) {
      jdField_l_of_type_Boolean = a();
    }
    return jdField_l_of_type_Boolean;
  }
  
  public static boolean isFlymeV4()
  {
    if (!jdField_n_of_type_Boolean) {
      jdField_o_of_type_Boolean = b();
    }
    return jdField_o_of_type_Boolean;
  }
  
  public static boolean isGoogleOs()
  {
    if (!jdField_e_of_type_Boolean)
    {
      String str = getSystemProperty("ro.com.google.clientidbase");
      if ((!TextUtils.isEmpty(str)) && (str.contains("google"))) {
        isGoogleAndroid = true;
      }
      jdField_e_of_type_Boolean = true;
    }
    return isGoogleAndroid;
  }
  
  public static boolean isLandscape()
  {
    boolean bool2 = mIsInSystemMultiWindow;
    boolean bool1 = false;
    if (bool2) {
      return false;
    }
    if (ContextHolder.getAppContext().getResources().getConfiguration().orientation == 2) {
      bool1 = true;
    }
    return bool1;
  }
  
  public static boolean isLandscapeUIMode(Activity paramActivity)
  {
    if (mIsInSystemMultiWindow) {
      return false;
    }
    Object localObject = paramActivity;
    if (paramActivity == null) {
      localObject = ContextHolder.getAppContext();
    }
    if (localObject == null) {
      return false;
    }
    int i1 = GdiMeasureImpl.getScreenWidth((Context)localObject);
    int i2 = GdiMeasureImpl.getScreenHeight((Context)localObject);
    if (i1 >= i2) {
      i1 = i2;
    }
    return (((Context)localObject).getResources().getConfiguration().orientation == 2) && (i1 > 320);
  }
  
  public static boolean isLargeScreen()
  {
    if (jdField_i_of_type_Boolean) {
      return jdField_j_of_type_Boolean;
    }
    int i1 = Math.min(getWidth(), getHeight()) * 160 / getDensityDpi();
    boolean bool;
    if ((i1 >= 360) && (i1 <= 590)) {
      bool = true;
    } else {
      bool = false;
    }
    jdField_j_of_type_Boolean = bool;
    jdField_i_of_type_Boolean = true;
    return jdField_j_of_type_Boolean;
  }
  
  public static boolean isMIUI()
  {
    if (!jdField_g_of_type_Boolean) {
      jdField_c_of_type_Boolean = c();
    }
    return jdField_c_of_type_Boolean;
  }
  
  public static boolean isMIUIV6()
  {
    if ((!jdField_f_of_type_Boolean) && (isMIUI()))
    {
      isMIUIV6 = a(6);
      jdField_f_of_type_Boolean = true;
    }
    return isMIUIV6;
  }
  
  public static boolean isMIUIV8()
  {
    if ((!v) && (isMIUI()))
    {
      x = a(8);
      v = true;
    }
    return x;
  }
  
  public static boolean isMIUIV9()
  {
    if ((!w) && (isMIUI()))
    {
      y = a(9);
      w = true;
    }
    return y;
  }
  
  public static boolean isMT6573()
  {
    if (jdField_e_of_type_Int < 0) {
      if (a(": mt6573")) {
        jdField_e_of_type_Int = 1;
      } else {
        jdField_e_of_type_Int = 0;
      }
    }
    return jdField_e_of_type_Int > 0;
  }
  
  public static boolean isMT6577()
  {
    if (jdField_f_of_type_Int < 0) {
      if (a(": mt6577")) {
        jdField_f_of_type_Int = 1;
      } else {
        jdField_f_of_type_Int = 0;
      }
    }
    return jdField_f_of_type_Int > 0;
  }
  
  public static boolean isMateX2()
  {
    return ("HUAWEI".equalsIgnoreCase(Build.MANUFACTURER)) && (("RLI-AN00".equalsIgnoreCase(Build.MODEL)) || ("RLI-N29".equalsIgnoreCase(Build.MODEL)) || ("TAH-AN00".equalsIgnoreCase(Build.MODEL)) || ("TAH-N29".equalsIgnoreCase(Build.MODEL)));
  }
  
  public static boolean isMeizu()
  {
    return (!TextUtils.isEmpty(getSystemProperty("ro.meizu.product.model"))) || ("meizu".equalsIgnoreCase(Build.BRAND)) || ("22c4185e".equalsIgnoreCase(Build.BRAND));
  }
  
  public static boolean isNubiaSeries()
  {
    return (isZTE_NX505J) || (isZTE_NX511J) || (isZTE_N958St);
  }
  
  public static boolean isPhablet(Context paramContext)
  {
    int i1 = getMinEdge();
    float f1 = paramContext.getResources().getDisplayMetrics().density;
    boolean bool2 = false;
    if (f1 != 0.0F)
    {
      f1 = i1 / f1;
      boolean bool1 = bool2;
      if (f1 >= 360.0F)
      {
        bool1 = bool2;
        if (f1 <= 590.0F) {
          bool1 = true;
        }
      }
      return bool1;
    }
    return false;
  }
  
  public static boolean isQVGA()
  {
    return ((getScreenWidth() == 240) && (getScreenHeigh() == 320)) || ((getScreenWidth() == 320) && (getScreenHeigh() == 240));
  }
  
  public static boolean isSamsung()
  {
    if ((!jdField_k_of_type_Boolean) && (!TextUtils.isEmpty(Build.BRAND)) && (Build.BRAND.toLowerCase().contains("samsung")))
    {
      isSamsung = true;
      jdField_k_of_type_Boolean = true;
    }
    return isSamsung;
  }
  
  public static boolean isScreenLocked()
  {
    try
    {
      boolean bool = ((KeyguardManager)ContextHolder.getAppContext().getSystemService("keyguard")).inKeyguardRestrictedInputMode();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public static boolean isScreenOn()
  {
    try
    {
      boolean bool = ((PowerManager)ContextHolder.getAppContext().getSystemService("power")).isScreenOn();
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return true;
  }
  
  public static boolean isSmartisanOS()
  {
    if (!jdField_h_of_type_Boolean) {
      jdField_d_of_type_Boolean = d();
    }
    return jdField_d_of_type_Boolean;
  }
  
  public static boolean isStatusBarHide(Window paramWindow)
  {
    boolean bool = false;
    if (paramWindow == null) {
      return false;
    }
    if ((paramWindow.getAttributes().flags & 0x400) == 1024) {
      bool = true;
    }
    return bool;
  }
  
  public static boolean isSupportImmersiveMode()
  {
    Map localMap = getIsSupportImmersiveModeData();
    if (localMap != null) {
      return Boolean.valueOf((String)localMap.get("support")).booleanValue();
    }
    return false;
  }
  
  public static boolean isTOS()
  {
    String str = getSystemProperty("ro.qrom.build.brand");
    if (!TextUtils.isEmpty(str)) {
      return str.toLowerCase(Locale.ENGLISH).equals("tos");
    }
    return false;
  }
  
  public static boolean isVivo()
  {
    if (jdField_b_of_type_JavaLangString == null) {
      jdField_b_of_type_JavaLangString = getDeviceBrand().trim();
    }
    return (isVivo) || (jdField_b_of_type_JavaLangString.contains("vivo"));
  }
  
  public static boolean isZUI()
  {
    if (!u)
    {
      if (!TextUtils.isEmpty(getSystemProperty("ro.com.zui.version"))) {
        isZUI = true;
      }
      u = true;
    }
    return isZUI;
  }
  
  public static boolean needAdapterForMX()
  {
    return (hasSmartBar()) && (!IsHideSmartBarSuccess());
  }
  
  public static void resetScreenSize() {}
  
  public static void setBrowserActiveState(int paramInt)
  {
    mBrowserActiveState = paramInt;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("browserActiveState ");
    localStringBuilder.append(mBrowserActiveState);
    localStringBuilder.toString();
  }
  
  public static void setFrameHeightDp(int paramInt)
  {
    jdField_j_of_type_Int = paramInt;
  }
  
  public static void setFrameWidthDp(int paramInt)
  {
    jdField_i_of_type_Int = paramInt;
  }
  
  class a
    implements FileFilter
  {
    public boolean accept(File paramFile)
    {
      return Pattern.matches("cpu[0-9]", paramFile.getName());
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\DeviceUtilsF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */