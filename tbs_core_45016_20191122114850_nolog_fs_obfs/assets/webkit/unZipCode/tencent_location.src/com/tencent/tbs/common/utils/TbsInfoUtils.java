package com.tencent.tbs.common.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.common.utils.GpuInfoUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.baseinfo.ICoreInfoFetcher;
import com.tencent.tbs.common.baseinfo.QUAUtils;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import java.util.HashMap;

public class TbsInfoUtils
{
  public static final String APP_BUILD = "2170";
  public static final String APP_NAME_VERSION_UA = "MQQBrowser/6.5";
  public static final String APP_VERSION_QUA = "65";
  public static final String APP_VERSION_UA = "6.5";
  public static final String CHANNEL_NAME = ".channel";
  public static final byte CORE_TYPE_BLINK = 2;
  public static final byte CORE_TYPE_SYS = 0;
  public static final byte CORE_TYPE_X5 = 1;
  public static final int DEFAULT_FONT_SIZE = 14;
  public static final String KEY_LC = "LC";
  public static final String KEY_LCID = "LCID";
  public static final String LC_CONFIG_FILE = "lc.conf";
  public static final String LC_PAD_CONFIG_FILE = "lc_pad.conf";
  private static final String MM_PACKAGE_NAME = "com.tencent.mm";
  private static final String TAG = "TbsInfoUtils";
  private static final String TBS_METADATA = "com.tencent.mm.BuildInfo.CLIENT_VERSION";
  public static String TES_CHLID;
  private static String mGpuInfoWithSR;
  private static String mMode = "B1";
  private static String mMttQua;
  private static String mParentPackageName;
  private static String mParentPackageVersion;
  private static String mQua1;
  private static HashMap<String, String> mQuaMap = new HashMap();
  private static String mTesQua;
  public static String sLC;
  public static String sLCID;
  
  static
  {
    mMttQua = null;
    mQua1 = null;
    sLC = "D70A3465D4EE4E9";
    sLCID = "9422";
    TES_CHLID = "0";
    mTesQua = "";
    mGpuInfoWithSR = "";
    mParentPackageName = "PP";
    mParentPackageVersion = "PPVC";
  }
  
  private static void appendInfo(StringBuilder paramStringBuilder, String paramString1, String paramString2)
  {
    paramStringBuilder.append("&");
    paramStringBuilder.append(paramString1);
    paramStringBuilder.append("=");
    paramStringBuilder.append(paramString2);
  }
  
  private static String generateQUA2(byte paramByte, String paramString1, boolean paramBoolean, String paramString2, String paramString3, String paramString4)
  {
    String str1 = "x5Version";
    String str2 = "blinkVersion";
    byte b = 1;
    int i = 0;
    switch (paramByte)
    {
    default: 
      paramByte = 0;
      break;
    case 2: 
      str2 = paramString1;
      paramByte = b;
      break;
    case 1: 
      str1 = paramString1;
      paramByte = 0;
      i = 1;
    }
    String str3 = TES_CHLID;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("QV");
    localStringBuilder.append("=");
    localStringBuilder.append("2");
    appendInfo(localStringBuilder, "PL", "ADR");
    if (paramBoolean) {
      appendInfo(localStringBuilder, "PR", "TBS");
    } else {
      appendInfo(localStringBuilder, "PR", "QB");
    }
    appendInfo(localStringBuilder, "PB", "GE");
    appendInfo(localStringBuilder, "VE", mMode);
    if (paramBoolean)
    {
      if (!TextUtils.isEmpty(paramString2)) {
        appendInfo(localStringBuilder, "VN", paramString2);
      }
    }
    else if (!TextUtils.isEmpty("QBVersion")) {
      appendInfo(localStringBuilder, "VN", "QBVersion");
    }
    if (i != 0)
    {
      appendInfo(localStringBuilder, "CO", "X5");
      if (!TextUtils.isEmpty(str1)) {
        appendInfo(localStringBuilder, "COVN", str1);
      }
    }
    else if (paramByte != 0)
    {
      appendInfo(localStringBuilder, "CO", "Blink");
      if (!TextUtils.isEmpty(str2)) {
        appendInfo(localStringBuilder, "COVN", str2);
      }
    }
    else
    {
      appendInfo(localStringBuilder, "CO", "AMTT");
      if (!TextUtils.isEmpty(paramString1)) {
        appendInfo(localStringBuilder, "COVN", paramString1);
      }
    }
    appendInfo(localStringBuilder, "RF", "PRI");
    if (paramBoolean)
    {
      if (!TextUtils.isEmpty(paramString3)) {
        appendInfo(localStringBuilder, "PP", paramString3);
      }
      if (!TextUtils.isEmpty(paramString4)) {
        appendInfo(localStringBuilder, "PPVC", paramString4);
      }
    }
    paramString1 = new StringBuilder();
    paramString1.append(DeviceUtils.getWidth(TbsBaseModuleShell.getContext()));
    paramString1.append("*");
    paramString1.append(DeviceUtils.getHeight(TbsBaseModuleShell.getContext()));
    appendInfo(localStringBuilder, "RL", paramString1.toString());
    paramString1 = DeviceUtils.getDeviceName();
    try
    {
      paramString2 = new String(paramString1.getBytes("UTF-8"), "ISO8859-1");
      paramString1 = paramString2;
    }
    catch (Exception paramString2)
    {
      for (;;) {}
    }
    if (!TextUtils.isEmpty(paramString1)) {
      appendInfo(localStringBuilder, "MO", paramString1);
    }
    if (DeviceUtils.isRealPad(TbsBaseModuleShell.getContext())) {
      appendInfo(localStringBuilder, "DE", "PAD");
    } else {
      appendInfo(localStringBuilder, "DE", "PHONE");
    }
    paramString1 = Build.VERSION.RELEASE;
    try
    {
      paramString2 = new String(paramString1.getBytes("UTF-8"), "ISO8859-1");
      paramString1 = paramString2;
    }
    catch (Exception paramString2)
    {
      for (;;) {}
    }
    if (!TextUtils.isEmpty(paramString1)) {
      appendInfo(localStringBuilder, "OS", paramString1);
    }
    paramString1 = new StringBuilder();
    paramString1.append(Build.VERSION.SDK_INT);
    paramString1.append("");
    appendInfo(localStringBuilder, "API", paramString1.toString());
    paramString1 = str3;
    if (TextUtils.isEmpty(str3)) {
      paramString1 = "0";
    }
    appendInfo(localStringBuilder, "CHID", paramString1);
    appendInfo(localStringBuilder, "LCID", getLCID());
    return localStringBuilder.toString();
  }
  
  private static String generateQua(Context paramContext, boolean paramBoolean1, String paramString1, boolean paramBoolean2, String paramString2, String paramString3)
  {
    if (paramBoolean1) {
      localObject = "ADRQBX";
    } else {
      localObject = "ADRQB";
    }
    Object localObject = new StringBuilder((String)localObject);
    ((StringBuilder)localObject).append("65");
    ((StringBuilder)localObject).append("_");
    ((StringBuilder)localObject).append("GA");
    if (paramBoolean2)
    {
      ((StringBuilder)localObject).append("_SDK");
      ((StringBuilder)localObject).append("_");
      ((StringBuilder)localObject).append(paramString2);
      ((StringBuilder)localObject).append("_");
      ((StringBuilder)localObject).append(paramString3);
    }
    ((StringBuilder)localObject).append("/");
    ((StringBuilder)localObject).append("65");
    ((StringBuilder)localObject).append("2170");
    ((StringBuilder)localObject).append("&");
    if (paramBoolean1)
    {
      ((StringBuilder)localObject).append("X5");
      ((StringBuilder)localObject).append("/");
    }
    else
    {
      ((StringBuilder)localObject).append("AMTT");
      ((StringBuilder)localObject).append("/");
    }
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append("&");
    if (DeviceUtils.isRealPad(paramContext))
    {
      ((StringBuilder)localObject).append("AP");
      ((StringBuilder)localObject).append("&");
    }
    else
    {
      ((StringBuilder)localObject).append("ADR");
      ((StringBuilder)localObject).append("&");
    }
    int i = DeviceUtils.getWidth(paramContext);
    int j = DeviceUtils.getHeight(paramContext);
    paramContext = new Paint();
    paramContext.setTextSize(14.0F);
    paramContext.setTypeface(Typeface.create("", 0));
    int k = (int)paramContext.measureText("国");
    double d = i;
    Double.isNaN(d);
    ((StringBuilder)localObject).append(Math.round(d / 16.0D));
    d = j;
    Double.isNaN(d);
    ((StringBuilder)localObject).append(Math.round(d / 16.0D));
    ((StringBuilder)localObject).append(k);
    ((StringBuilder)localObject).append("&");
    paramContext = DeviceUtils.getDeviceName();
    try
    {
      paramString1 = new String(paramContext.getBytes("UTF-8"), "ISO8859-1");
      paramContext = paramString1;
    }
    catch (Exception paramString1)
    {
      for (;;) {}
    }
    ((StringBuilder)localObject).append(paramContext);
    ((StringBuilder)localObject).append("&");
    paramString1 = TES_CHLID;
    paramContext = paramString1;
    if (TextUtils.isEmpty(paramString1)) {
      paramContext = "0";
    }
    ((StringBuilder)localObject).append(paramContext);
    ((StringBuilder)localObject).append("&");
    paramString2 = sLCID;
    paramContext = Build.VERSION.RELEASE;
    try
    {
      paramString1 = new String(paramContext.getBytes("UTF-8"), "ISO8859-1");
      paramContext = paramString1;
    }
    catch (Exception paramString1)
    {
      for (;;) {}
    }
    ((StringBuilder)localObject).append(paramString2);
    paramString1 = new StringBuilder();
    paramString1.append("&Android");
    paramString1.append(paramContext);
    paramString1.append(" ");
    ((StringBuilder)localObject).append(paramString1.toString());
    ((StringBuilder)localObject).append("&V3");
    paramContext = new StringBuilder();
    paramContext.append("QUA = ");
    paramContext.append(((StringBuilder)localObject).toString());
    LogUtils.d("TbsInfoUtils", paramContext.toString());
    return ((StringBuilder)localObject).toString();
  }
  
  public static String getAppMetadata(Context paramContext, String paramString)
  {
    try
    {
      String str = paramContext.getPackageName();
      paramContext = String.valueOf(paramContext.getPackageManager().getApplicationInfo(str, 128).metaData.get(paramString));
    }
    catch (Exception paramContext)
    {
      for (;;) {}
    }
    try
    {
      paramString = String.valueOf(Integer.toHexString(Integer.parseInt(paramContext)));
      return paramString;
    }
    catch (Exception paramString) {}
    return null;
    return paramContext;
  }
  
  public static String getGpuInfoWithSR()
  {
    if (TextUtils.isEmpty(mGpuInfoWithSR))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("SR");
      localStringBuilder.append("=");
      localStringBuilder.append("TBS");
      String str1 = GpuInfoUtils.getGLRenderer().trim();
      String str2 = GpuInfoUtils.getGLVendor().trim();
      String str3 = GpuInfoUtils.getGLVersion().trim();
      if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2)) && (!TextUtils.isEmpty(str3)))
      {
        appendInfo(localStringBuilder, "GLRD", GpuInfoUtils.getGLRenderer().trim());
        appendInfo(localStringBuilder, "GLVD", GpuInfoUtils.getGLVendor().trim());
        appendInfo(localStringBuilder, "GLVS", GpuInfoUtils.getGLVersion().trim());
        mGpuInfoWithSR = localStringBuilder.toString();
      }
      else
      {
        return "";
      }
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("GPU Info: ");
    localStringBuilder.append(mGpuInfoWithSR);
    LogUtils.d("TbsInfoUtils", localStringBuilder.toString());
    return mGpuInfoWithSR;
  }
  
  public static String getLC()
  {
    return sLC;
  }
  
  public static String getLCID()
  {
    return sLCID;
  }
  
  public static String getMode()
  {
    String str = mMode;
    if (str != null)
    {
      if (str.equalsIgnoreCase("A")) {
        return "Alpha";
      }
      if (mMode.startsWith("B")) {
        return "Beta";
      }
      if (mMode.equalsIgnoreCase("P")) {
        return "Preview";
      }
      if (mMode.equalsIgnoreCase("RC")) {
        return "Release";
      }
      return mMode;
    }
    return str;
  }
  
  public static String getQUA()
  {
    if (!TbsBaseModuleShell.getEnableQua1()) {
      return "";
    }
    Object localObject = mQua1;
    if (localObject != null) {
      return (String)localObject;
    }
    localObject = TbsBaseModuleShell.getCoreInfoFetcher();
    if (localObject != null) {
      mQua1 = ((ICoreInfoFetcher)localObject).getQua1FromQbUi();
    }
    return mQua1;
  }
  
  public static String getQUA(Context paramContext, boolean paramBoolean, String paramString1, String paramString2)
  {
    return "";
  }
  
  public static String getQUA2()
  {
    return QUAUtils.getQUA2_V3();
  }
  
  public static void setLCInfo(String paramString1, String paramString2)
  {
    sLC = paramString1;
    sLCID = paramString2;
  }
  
  public static void setMttQua(String paramString)
  {
    mMttQua = paramString;
  }
  
  public static void setMttQua2(String paramString)
  {
    mMttQua = paramString;
  }
  
  public static void setParentPackageInfo(String paramString1, String paramString2)
  {
    mParentPackageName = paramString1;
    mParentPackageVersion = paramString2;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\TbsInfoUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */