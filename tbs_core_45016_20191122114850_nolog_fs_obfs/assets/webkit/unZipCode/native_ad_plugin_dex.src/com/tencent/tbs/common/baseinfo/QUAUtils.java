package com.tencent.tbs.common.baseinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;
import com.tencent.tbs.common.utils.DeviceUtils;

public class QUAUtils
{
  private static final String DEMO_PACKAGE_NAME = "com.tencent.browserdemo";
  public static final String DE_DEFAULT = "PHONE";
  public static final String DE_PAD = "PAD";
  public static final String DE_TV = "TV";
  public static final String PB_DEFAULT = "GE";
  public static final String PB_INT = "INT";
  public static final String PB_PAD = "PAD";
  public static final String PB_TMS = "TMS";
  public static final String PB_TV = "TV";
  public static final String PR_DEFAULT = "TRD";
  public static final String PR_QB = "QB";
  public static final String PR_QQ = "QQ";
  public static final String PR_QZ = "QZ";
  public static final String PR_TOS = "TOS";
  public static final String PR_WX = "WX";
  private static final String QB_PACKAGE_NAME = "com.tencent.mtt";
  private static final String QQ_PACKAGE_NAME = "com.tencent.mobileqq";
  private static final String QZONE_PACKAGE_NAME = "com.qzone";
  private static final String TAG = "TbsQUAUtils";
  private static final String TMS_PACKAGE_NAME = "com.android.browser";
  private static final String VIVO_PACKAGE_NAME = "com.vivo.browser";
  private static final String WX_PACKAGE_NAME = "com.tencent.mm";
  private static String mCHID = "11111";
  private static boolean mIsPad = false;
  private static String mLC = "D70A3465D4EE4E9";
  private static String mLCID = "9422";
  private static String mMttQua;
  private static String mPB = "GE";
  private static String mPPVN = "";
  private static String mVE = "GA";
  
  private static void appendInfo(StringBuilder paramStringBuilder, String paramString1, String paramString2)
  {
    paramStringBuilder.append("&");
    paramStringBuilder.append(paramString1);
    paramStringBuilder.append("=");
    paramStringBuilder.append(paramString2);
  }
  
  private static String generateQUA_v3(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, boolean paramBoolean)
  {
    String str1 = "PHONE";
    StringBuilder localStringBuilder = new StringBuilder();
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(getDeviceWidth(paramContext));
    ((StringBuilder)localObject).append("*");
    ((StringBuilder)localObject).append(getDeviceHeight(paramContext));
    String str2 = ((StringBuilder)localObject).toString();
    localObject = null;
    try
    {
      ApplicationInfo localApplicationInfo = paramContext.getApplicationInfo();
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(localApplicationInfo.packageName, 0);
      paramContext = localApplicationInfo.packageName;
      try
      {
        if (!TextUtils.isEmpty(paramString7)) {
          break label147;
        }
        paramString7 = localPackageInfo.versionName;
      }
      catch (RuntimeException paramString7) {}catch (PackageManager.NameNotFoundException paramString7) {}
      paramString7.printStackTrace();
    }
    catch (RuntimeException paramString7)
    {
      paramContext = null;
      paramString7.printStackTrace();
      paramString7 = (String)localObject;
    }
    catch (PackageManager.NameNotFoundException paramString7)
    {
      paramContext = null;
    }
    paramString7 = (String)localObject;
    label147:
    localObject = getPrFromPP(paramContext);
    if ("QB".equals(localObject))
    {
      if (paramBoolean) {
        str1 = "PAD";
      }
    }
    else if (DeviceUtils.isRealPad(TbsBaseModuleShell.getContext())) {
      str1 = "PAD";
    }
    localStringBuilder.append("QV");
    localStringBuilder.append("=");
    localStringBuilder.append("3");
    appendInfo(localStringBuilder, "PL", "ADR");
    appendInfo(localStringBuilder, "PR", (String)localObject);
    appendInfo(localStringBuilder, "PP", paramContext);
    appendInfo(localStringBuilder, "PPVN", paramString7);
    if (!TextUtils.isEmpty(paramString1)) {
      appendInfo(localStringBuilder, "TBSVC", paramString1);
    }
    if (!TextUtils.isEmpty(paramString2))
    {
      paramContext = paramString2.substring(0, 2);
      if (paramContext.equals("02")) {
        appendInfo(localStringBuilder, "CO", "WK");
      } else if ((!paramContext.equals("03")) && (!paramContext.equals("04")) && (!paramContext.equals("88"))) {
        appendInfo(localStringBuilder, "CO", "SYS");
      } else {
        appendInfo(localStringBuilder, "CO", "BK");
      }
    }
    else
    {
      appendInfo(localStringBuilder, "CO", "SYS");
    }
    if (!TextUtils.isEmpty(paramString2)) {
      appendInfo(localStringBuilder, "COVC", paramString2);
    }
    appendInfo(localStringBuilder, "PB", paramString4);
    appendInfo(localStringBuilder, "VE", paramString3);
    appendInfo(localStringBuilder, "DE", str1);
    paramContext = paramString6;
    if (TextUtils.isEmpty(paramString6)) {
      paramContext = "0";
    }
    appendInfo(localStringBuilder, "CHID", paramContext);
    appendInfo(localStringBuilder, "LCID", paramString5);
    paramContext = getDeviceName();
    try
    {
      paramString1 = new String(paramContext.getBytes("UTF-8"), "ISO8859-1");
      paramContext = paramString1;
    }
    catch (Exception paramString1)
    {
      for (;;) {}
    }
    if (!TextUtils.isEmpty(paramContext)) {
      appendInfo(localStringBuilder, "MO", paramContext);
    }
    appendInfo(localStringBuilder, "RL", str2);
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
    if (!TextUtils.isEmpty(paramContext)) {
      appendInfo(localStringBuilder, "OS", paramContext);
    }
    paramContext = new StringBuilder();
    paramContext.append(Build.VERSION.SDK_INT);
    paramContext.append("");
    appendInfo(localStringBuilder, "API", paramContext.toString());
    return localStringBuilder.toString();
  }
  
  private static int getDeviceHeight(Context paramContext)
  {
    try
    {
      int i = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getHeight();
      return i;
    }
    catch (RuntimeException paramContext)
    {
      for (;;) {}
    }
    return 0;
  }
  
  private static String getDeviceName()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" ");
    localStringBuilder.append(Build.MODEL.replaceAll("[ |\\/|\\_|\\&|\\|]", ""));
    localStringBuilder.append(" ");
    return localStringBuilder.toString();
  }
  
  private static int getDeviceWidth(Context paramContext)
  {
    try
    {
      int i = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getWidth();
      return i;
    }
    catch (RuntimeException paramContext)
    {
      for (;;) {}
    }
    return 0;
  }
  
  private static String getPrFromPP(String paramString)
  {
    if (paramString == null) {
      return "TRD";
    }
    if (paramString.equals("com.tencent.mm")) {
      return "WX";
    }
    if (paramString.equals("com.tencent.mobileqq")) {
      return "QQ";
    }
    if (paramString.equals("com.qzone")) {
      return "QZ";
    }
    if (paramString.equals("com.tencent.mtt")) {
      return "QB";
    }
    if (paramString.equals("com.android.browser")) {
      return "QB";
    }
    if ((!paramString.equals("com.vivo.browser")) && (!paramString.equals("com.tencent.browserdemo"))) {
      return "TRD";
    }
    return "QB";
  }
  
  public static String getQUA2_V3()
  {
    if (!TextUtils.isEmpty(mMttQua)) {
      return mMttQua;
    }
    Context localContext = TbsBaseModuleShell.getContext();
    int i = TbsBaseModuleShell.getTesVersionCode();
    String str1 = mVE;
    String str2 = mPB;
    String str3 = mLCID;
    String str4 = mCHID;
    String str5 = mPPVN;
    boolean bool = mIsPad;
    ICoreInfoFetcher localICoreInfoFetcher = TbsBaseModuleShell.getCoreInfoFetcher();
    Object localObject;
    if (localICoreInfoFetcher != null)
    {
      String str6 = localICoreInfoFetcher.getCoreVersion();
      localObject = str6;
      if (str6.length() == 5)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("0");
        ((StringBuilder)localObject).append(str6);
        localObject = ((StringBuilder)localObject).toString();
      }
      if (!TextUtils.isEmpty(localICoreInfoFetcher.getQbVE())) {
        str1 = localICoreInfoFetcher.getQbVE();
      }
      if (!TextUtils.isEmpty(localICoreInfoFetcher.getQbPB())) {
        str2 = localICoreInfoFetcher.getQbPB();
      }
      if (!TextUtils.isEmpty(localICoreInfoFetcher.getQbLCID())) {
        str3 = localICoreInfoFetcher.getQbLCID();
      }
      if (!TextUtils.isEmpty(localICoreInfoFetcher.getQbCHID())) {
        str4 = localICoreInfoFetcher.getQbCHID();
      }
      if (!TextUtils.isEmpty(localICoreInfoFetcher.getQbPPVN())) {
        str5 = localICoreInfoFetcher.getQbPPVN();
      }
      bool = localICoreInfoFetcher.getIsPad();
    }
    else
    {
      localObject = "";
    }
    mMttQua = generateQUA_v3(localContext, String.valueOf(i), (String)localObject, str1, str2, str3, str4, str5, bool);
    return mMttQua;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\QUAUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */