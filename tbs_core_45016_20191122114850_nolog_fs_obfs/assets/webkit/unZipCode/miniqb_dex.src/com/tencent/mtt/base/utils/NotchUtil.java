package com.tencent.mtt.base.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.tencent.basesupport.FLogger;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NotchUtil
{
  public static final String DISPLAY_NOTCH_STATUS = "display_notch_status";
  public static final int FLAG_NOTCH_SUPPORT = 65536;
  public static final int FLAG_VIVO_NOTCH_SUPPORT = 32;
  private static int a = -1;
  private static int b = -1;
  private static int c = -1;
  private static int d = -1;
  private static int e = -1;
  private static int f = -1;
  
  public static int[] getHWNotchSize(Context paramContext)
  {
    int[] arrayOfInt = new int[2];
    int[] tmp5_4 = arrayOfInt;
    tmp5_4[0] = 0;
    int[] tmp9_5 = tmp5_4;
    tmp9_5[1] = 0;
    tmp9_5;
    try
    {
      paramContext = paramContext.getClassLoader().loadClass("com.huawei.android.util.HwNotchSizeUtil");
      paramContext = (int[])paramContext.getMethod("getNotchSize", new Class[0]).invoke(paramContext, new Object[0]);
      return paramContext;
    }
    catch (ClassNotFoundException paramContext)
    {
      for (;;)
      {
        paramContext = paramContext;
      }
    }
    catch (NoSuchMethodException paramContext)
    {
      for (;;)
      {
        paramContext = paramContext;
      }
    }
    catch (Exception paramContext)
    {
      for (;;)
      {
        paramContext = paramContext;
      }
    }
    finally {}
    FLogger.e("NotchUtil", "hw getNotchSize Exception");
    return arrayOfInt;
    FLogger.e("NotchUtil", "hw getNotchSize NoSuchMethodException");
    return arrayOfInt;
    FLogger.e("NotchUtil", "hw getNotchSize ClassNotFoundException");
    return arrayOfInt;
    return arrayOfInt;
  }
  
  public static boolean isFullScreenDisplayCutout(Window paramWindow, String paramString, int paramInt)
  {
    if (paramWindow == null) {
      return false;
    }
    if (Build.VERSION.SDK_INT < 21) {
      return false;
    }
    paramWindow = paramWindow.getAttributes();
    try
    {
      paramString = paramWindow.getClass().getField(paramString);
      if (paramString != null)
      {
        int i = paramString.getInt(paramWindow);
        if (i >= paramInt) {
          return true;
        }
      }
    }
    catch (Exception paramWindow)
    {
      paramWindow.printStackTrace();
    }
    return false;
  }
  
  public static boolean isFullScreenWindowLayoutInDisplayCutout(Context paramContext, Window paramWindow)
  {
    if (paramWindow == null) {
      return false;
    }
    if (Build.VERSION.SDK_INT < 21) {
      return false;
    }
    boolean bool = isNotchDevice(paramContext);
    if (DeviceUtilsF.isEMUI())
    {
      if (Build.VERSION.SDK_INT < 28) {
        return isFullScreenDisplayCutout(paramWindow, "hwFlags", 1);
      }
      return isFullScreenDisplayCutout(paramWindow, "layoutInDisplayCutoutMode", 1);
    }
    if (DeviceUtilsF.isMIUI())
    {
      if (Build.VERSION.SDK_INT < 28) {
        return isFullScreenDisplayCutout(paramWindow, "extraFlags", 256);
      }
      return isFullScreenDisplayCutout(paramWindow, "layoutInDisplayCutoutMode", 1);
    }
    if (Build.VERSION.SDK_INT >= 28) {
      bool = isFullScreenDisplayCutout(paramWindow, "layoutInDisplayCutoutMode", 1);
    }
    return bool;
  }
  
  public static boolean isHWNotchDevice(Context paramContext)
  {
    int j = a;
    int i = 1;
    if (j != -1) {
      return j == 1;
    }
    for (;;)
    {
      try
      {
        Class localClass = paramContext.getClassLoader().loadClass("com.huawei.android.util.HwNotchSizeUtil");
        bool = ((Boolean)localClass.getMethod("hasNotchInScreen", new Class[0]).invoke(localClass, new Object[0])).booleanValue();
        if (!bool) {}
      }
      catch (NoSuchMethodException paramContext)
      {
        boolean bool;
        continue;
      }
      catch (IllegalAccessException paramContext)
      {
        continue;
      }
      catch (InvocationTargetException paramContext)
      {
        continue;
      }
      catch (ClassNotFoundException paramContext)
      {
        continue;
      }
      try
      {
        j = Settings.Secure.getInt(paramContext.getContentResolver(), "display_notch_status", 0);
        if (j == 0) {
          bool = true;
        } else {
          bool = false;
        }
      }
      catch (NoSuchMethodException paramContext) {}catch (IllegalAccessException paramContext) {}catch (InvocationTargetException paramContext) {}catch (ClassNotFoundException paramContext) {}
    }
    break label116;
    break label127;
    break label138;
    break label149;
    if (!bool) {
      i = 0;
    }
    try
    {
      a = i;
      return bool;
    }
    catch (NoSuchMethodException paramContext)
    {
      for (;;) {}
    }
    catch (IllegalAccessException paramContext)
    {
      for (;;) {}
    }
    catch (InvocationTargetException paramContext)
    {
      for (;;) {}
    }
    catch (ClassNotFoundException paramContext)
    {
      for (;;) {}
    }
    bool = false;
    label116:
    FLogger.d("NotchUtil", "hw hasNotchInScreen ClassNotFoundException");
    return bool;
    bool = false;
    label127:
    FLogger.d("NotchUtil", "hw hasNotchInScreen InvocationTargetException");
    return bool;
    bool = false;
    label138:
    FLogger.d("NotchUtil", "hw hasNotchInScreen IllegalAccessException");
    return bool;
    bool = false;
    label149:
    FLogger.d("NotchUtil", "hw hasNotchInScreen NoSuchMethodException");
    return bool;
  }
  
  public static boolean isMIUINotchDevice(Context paramContext)
  {
    int i = c;
    boolean bool = false;
    if (i != -1)
    {
      if (i == 1) {
        bool = true;
      }
      return bool;
    }
    if (paramContext == null) {
      return false;
    }
    if (TextUtils.equals("1", DeviceUtilsF.getSystemProperty("ro.miui.notch")))
    {
      c = 1;
      return true;
    }
    c = 0;
    return false;
  }
  
  public static boolean isNotchDevice(Context paramContext)
  {
    if (isPieNotchDevice(null)) {
      return true;
    }
    if (DeviceUtilsF.isEMUI()) {
      return isHWNotchDevice(paramContext);
    }
    if (DeviceUtilsF.isOnePlus)
    {
      boolean bool = isOnePlusNotchDevice(paramContext);
      if (!bool)
      {
        int i = f;
        if (i != -1) {
          return i == 1;
        }
      }
      return bool;
    }
    if (DeviceUtilsF.isMIUI()) {
      return isMIUINotchDevice(paramContext);
    }
    if (DeviceUtilsF.isVivo) {
      return isVIVIONotchDevice(paramContext);
    }
    if (DeviceUtilsF.isOppo) {
      return isOPPOONotchDevice(paramContext);
    }
    return false;
  }
  
  public static boolean isOPPOONotchDevice(Context paramContext)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public static boolean isOnePlusNotchDevice(Context paramContext)
  {
    int j = b;
    int i = 1;
    if (j != -1) {
      return j == 1;
    }
    if (paramContext == null) {
      return false;
    }
    try
    {
      bool = paramContext.getPackageManager().hasSystemFeature("com.oneplus.screen.cameranotch");
    }
    catch (Exception paramContext)
    {
      boolean bool;
      for (;;) {}
    }
    FLogger.d("NotchUtil", "oneplus notch feature exception");
    bool = false;
    if (!bool) {
      i = 0;
    }
    b = i;
    return bool;
  }
  
  public static boolean isPieNotchDevice(View paramView)
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool = false;
    if (i < 28) {
      return false;
    }
    i = f;
    if (i != -1)
    {
      if (i == 1) {
        bool = true;
      }
      return bool;
    }
    if (paramView == null) {
      return false;
    }
    paramView = paramView.getRootWindowInsets();
    if (paramView == null) {
      return false;
    }
    try
    {
      paramView = paramView.getClass().getMethod("getDisplayCutout", new Class[0]).invoke(paramView, new Object[0]);
      if (paramView != null)
      {
        i = ((Integer)paramView.getClass().getMethod("getSafeInsetBottom", new Class[0]).invoke(paramView, new Object[0])).intValue();
        int j = ((Integer)paramView.getClass().getMethod("getSafeInsetLeft", new Class[0]).invoke(paramView, new Object[0])).intValue();
        int k = ((Integer)paramView.getClass().getMethod("getSafeInsetRight", new Class[0]).invoke(paramView, new Object[0])).intValue();
        int m = ((Integer)paramView.getClass().getMethod("getSafeInsetTop", new Class[0]).invoke(paramView, new Object[0])).intValue();
        paramView = new StringBuilder();
        paramView.append("bottom:");
        paramView.append(i);
        paramView.append("  left:");
        paramView.append(j);
        paramView.append("  right:");
        paramView.append(k);
        paramView.append(" top:");
        paramView.append(m);
        FLogger.d("NotchUtil", paramView.toString());
        if (m > 0)
        {
          f = 1;
          return true;
        }
        f = 0;
        return false;
      }
    }
    catch (InvocationTargetException paramView)
    {
      paramView.printStackTrace();
      return false;
    }
    catch (IllegalAccessException paramView)
    {
      paramView.printStackTrace();
      return false;
    }
    catch (NoSuchMethodException paramView)
    {
      paramView.printStackTrace();
    }
    return false;
  }
  
  public static boolean isVIVIONotchDevice(Context paramContext)
  {
    int j = d;
    i = 1;
    if (j != -1) {
      return j == 1;
    }
    if (paramContext == null) {
      return false;
    }
    try
    {
      paramContext = (Boolean)Class.forName("android.util.FtFeature").getMethod("isFeatureSupport", new Class[] { Integer.TYPE }).invoke(null, new Object[] { Integer.valueOf(32) });
      if (!paramContext.booleanValue()) {
        break label145;
      }
    }
    catch (NoSuchMethodException paramContext)
    {
      boolean bool;
      for (;;) {}
    }
    catch (IllegalAccessException paramContext)
    {
      for (;;) {}
    }
    catch (InvocationTargetException paramContext)
    {
      for (;;) {}
    }
    catch (ClassNotFoundException paramContext)
    {
      for (;;)
      {
        continue;
        i = 0;
      }
    }
    d = i;
    bool = paramContext.booleanValue();
    return bool;
    FLogger.d("NotchUtil", "vivo hasNotchInScreen ClassNotFoundException");
    return false;
    FLogger.d("NotchUtil", "vivo hasNotchInScreen InvocationTargetException");
    return false;
    FLogger.d("NotchUtil", "vivo hasNotchInScreen IllegalAccessException");
    return false;
    FLogger.d("NotchUtil", "vivo hasNotchInScreen NoSuchMethodException");
    return false;
  }
  
  public static void setFullScreenWindowLayoutInDisplayCutout(Window paramWindow)
  {
    setHWWindowLayoutInDisplayCutout(paramWindow);
    setMIUIInDisplayCutout(paramWindow);
    if (Build.VERSION.SDK_INT >= 28) {
      setPieLayoutInDisplayCutout(paramWindow);
    }
  }
  
  public static void setHWWindowLayoutInDisplayCutout(Window paramWindow)
  {
    if (paramWindow == null) {
      return;
    }
    if (Build.VERSION.SDK_INT < 21) {
      return;
    }
    if (!DeviceUtilsF.isEMUI()) {
      return;
    }
    Object localObject = paramWindow.getAttributes();
    try
    {
      paramWindow = Class.forName("com.huawei.android.view.LayoutParamsEx");
      localObject = paramWindow.getConstructor(new Class[] { WindowManager.LayoutParams.class }).newInstance(new Object[] { localObject });
      paramWindow.getMethod("addHwFlags", new Class[] { Integer.TYPE }).invoke(localObject, new Object[] { Integer.valueOf(65536) });
      return;
    }
    catch (ClassNotFoundException|NoSuchMethodException|IllegalAccessException|InstantiationException|InvocationTargetException paramWindow)
    {
      for (;;) {}
    }
    catch (Exception paramWindow)
    {
      for (;;) {}
    }
    FLogger.e("NotchUtil", "hw other Exception");
    return;
    FLogger.e("NotchUtil", "hw notch screen flag api error");
  }
  
  public static void setMIUIInDisplayCutout(Window paramWindow)
  {
    if (paramWindow == null) {
      return;
    }
    if (Build.VERSION.SDK_INT < 21) {
      return;
    }
    if (!DeviceUtilsF.isMIUI()) {
      return;
    }
    try
    {
      Window.class.getMethod("addExtraFlags", new Class[] { Integer.TYPE }).invoke(paramWindow, new Object[] { Integer.valueOf(1792) });
      return;
    }
    catch (Exception paramWindow) {}
  }
  
  public static void setPieLayoutInDisplayCutout(Window paramWindow)
  {
    if (Build.VERSION.SDK_INT < 28) {
      return;
    }
    paramWindow = paramWindow.getAttributes();
    try
    {
      Field localField = paramWindow.getClass().getDeclaredField("layoutInDisplayCutoutMode");
      if (localField != null)
      {
        localField.setAccessible(true);
        localField.set(paramWindow, Integer.valueOf(1));
        return;
      }
    }
    catch (NoSuchFieldException paramWindow)
    {
      paramWindow.printStackTrace();
      return;
    }
    catch (IllegalAccessException paramWindow)
    {
      paramWindow.printStackTrace();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\NotchUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */