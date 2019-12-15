package com.tencent.tbs.core.partner.jsextension;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import com.tencent.common.utils.IntentUtils;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.security.MessageDigest;
import org.json.JSONException;
import org.json.JSONObject;

public class jsPackages
{
  private static final String KEY_COOKIE = "cookie";
  private static final String KEY_DOWNLOAD_INTERCEPT = "downloadIntercept";
  private static final String KEY_FILE_NAME = "filename";
  private static final String KEY_FROM = "from";
  private static final String KEY_INTENT_CHANNELID = "ChannelID";
  private static final String KEY_INTENT_DATA_URL = "url";
  private static final String KEY_INTENT_EXTRA = "extraInfo";
  private static final String KEY_INTENT_POSID = "PosID";
  private static final String KEY_INTENT_WINDOW_TYPE = "windowType";
  private static final String KEY_MIME_TYPE = "mimetype";
  private static final String KEY_PKG_NAME = "packagename";
  private static final String KEY_SIGN_MD5 = "signature";
  private static final String KEY_VER_CODE = "versioncode";
  private static final String KEY_VER_NAME = "versionname";
  private static final int PKG_CHECK_RSLT_INSTALLED = 1;
  public static final int PKG_CHECK_RSLT_UNINSTALLED = -1;
  private static final String QB_PACKAGE_NAME = "com.tencent.mtt";
  public static final String QQBROWSER_PARAMS_PACKAGENAME = ",packagename=";
  public static final String QQBROWSER_PARAMS_PD = ",product=";
  public static final String QQBROWSER_PARAMS_VERSION = ",version=";
  public static final String QQBROWSER_SCHEME = "mttbrowser://url=";
  private static final String SYSB_PACKAGE_NAME = "com.android.browser";
  private static final String TAG = "jsPackages";
  private static Context mContext;
  
  public jsPackages(Context paramContext)
  {
    mContext = paramContext;
  }
  
  private static String byteToHexString(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length > 0))
    {
      StringBuffer localStringBuffer = new StringBuffer(paramArrayOfByte.length * 2);
      int i = 0;
      while (i < paramArrayOfByte.length)
      {
        if ((paramArrayOfByte[i] & 0xFF) < 16) {
          localStringBuffer.append("0");
        }
        localStringBuffer.append(Long.toString(paramArrayOfByte[i] & 0xFF, 16));
        i += 1;
      }
      return localStringBuffer.toString();
    }
    return null;
  }
  
  private static String getHainaSignMd5(PackageInfo paramPackageInfo)
  {
    String str = "";
    Object localObject = str;
    if (paramPackageInfo != null)
    {
      paramPackageInfo = paramPackageInfo.signatures;
      localObject = str;
      if (paramPackageInfo != null)
      {
        localObject = str;
        if (paramPackageInfo.length > 0)
        {
          paramPackageInfo = byteToHexString(getMD5(paramPackageInfo[0].toByteArray()));
          localObject = paramPackageInfo;
          if (!TextUtils.isEmpty(paramPackageInfo)) {
            localObject = paramPackageInfo.toLowerCase();
          }
        }
      }
    }
    return (String)localObject;
  }
  
  private static PackageInfo getInstalledPKGInfo(String paramString, Context paramContext, int paramInt)
  {
    if (paramContext != null)
    {
      if (TextUtils.isEmpty(paramString)) {
        return null;
      }
      if ((SmttServiceProxy.getInstance().getQbInstallStatus()) && ("com.tencent.mtt".equals(paramString))) {
        return null;
      }
      paramContext = paramContext.getPackageManager();
    }
    try
    {
      paramString = paramContext.getPackageInfo(paramString, paramInt);
      return paramString;
    }
    catch (Exception paramString) {}
    return null;
    return null;
  }
  
  private static byte[] getMD5(byte[] paramArrayOfByte)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramArrayOfByte);
      paramArrayOfByte = localMessageDigest.digest();
      return paramArrayOfByte;
    }
    catch (Exception paramArrayOfByte)
    {
      for (;;) {}
    }
    return null;
  }
  
  private static int jsCallCheckPackageExist(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return -1;
    }
    try
    {
      paramString = getInstalledPKGInfo(new JSONObject(paramString).getString("packagename"), mContext, 0);
      if (paramString != null) {
        return 1;
      }
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return -1;
  }
  
  private static String jsCallGetAppInfo(String paramString)
  {
    String str = "";
    Object localObject = str;
    if (!TextUtils.isEmpty(paramString))
    {
      paramString = getInstalledPKGInfo(paramString, mContext, 64);
      localObject = str;
      if (paramString != null) {
        localObject = new JSONObject();
      }
    }
    try
    {
      ((JSONObject)localObject).put("packagename", paramString.packageName);
      ((JSONObject)localObject).put("versionname", paramString.versionName);
      ((JSONObject)localObject).put("versioncode", paramString.versionCode);
      ((JSONObject)localObject).put("signature", getHainaSignMd5(paramString));
      localObject = ((JSONObject)localObject).toString();
      return (String)localObject;
    }
    catch (JSONException paramString) {}
    return "";
  }
  
  private static void jsCallRunApk(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    boolean bool = false;
    String str3 = null;
    Object localObject1;
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
      localObject1 = null;
    }
    Object localObject3;
    Object localObject2;
    Object localObject4;
    if (localObject1 != null)
    {
      localObject3 = ((JSONObject)localObject1).optString("url");
      str1 = ((JSONObject)localObject1).optString("PosID");
      localObject2 = ((JSONObject)localObject1).optString("windowType");
      localObject4 = ((JSONObject)localObject1).optString("extraInfo");
      bool = ((JSONObject)localObject1).optBoolean("downloadIntercept");
      localObject1 = str1;
    }
    else
    {
      str1 = null;
      localObject1 = str1;
      localObject2 = localObject1;
      localObject3 = localObject2;
      localObject4 = localObject2;
      localObject2 = localObject1;
      localObject1 = str1;
    }
    Context localContext = mContext.getApplicationContext();
    String str1 = str3;
    String str2;
    if (localContext != null) {
      try
      {
        str1 = localContext.getApplicationInfo().packageName;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        str2 = str3;
      }
    }
    try
    {
      paramString = new JSONObject(paramString);
      str3 = paramString.getString("packagename");
      if (bool)
      {
        localObject1 = paramString.optString("cookie");
        localObject2 = paramString.optString("filename");
        localObject4 = paramString.optString("mimetype");
        paramString = paramString.optString("from");
        paramString = IntentUtils.getOpenUrlAndDownloadInQQBrowserWithReport(mContext, true, "qb://home", (String)localObject3, (String)localObject1, (String)localObject2, (String)localObject4, paramString, "", "");
      }
      else
      {
        paramString = new Intent("android.intent.action.VIEW");
        paramString.setPackage(str3);
        if (!TextUtils.isEmpty((CharSequence)localObject1)) {
          paramString.putExtra("PosID", (String)localObject1);
        }
        if (!TextUtils.isEmpty(str2)) {
          paramString.putExtra("ChannelID", str2);
        }
        if (!TextUtils.isEmpty((CharSequence)localObject2)) {
          paramString.putExtra("windowType", (String)localObject2);
        }
        if (!TextUtils.isEmpty((CharSequence)localObject4)) {
          paramString.putExtra("extraInfo", (String)localObject4);
        }
        if (!TextUtils.isEmpty((CharSequence)localObject3))
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("mttbrowser://url=");
          ((StringBuilder)localObject1).append((String)localObject3);
          ((StringBuilder)localObject1).append(",product=");
          ((StringBuilder)localObject1).append("TBS");
          ((StringBuilder)localObject1).append(",packagename=");
          ((StringBuilder)localObject1).append(mContext.getPackageName());
          paramString.setData(Uri.parse(((StringBuilder)localObject1).toString()));
        }
        else
        {
          paramString = mContext.getPackageManager().getLaunchIntentForPackage(str3);
          paramString.setComponent(new ComponentName("com.tencent.mtt", "com.tencent.mtt.MainActivity"));
        }
      }
      if (paramString != null)
      {
        new Handler(Looper.getMainLooper()).post(new Runnable()
        {
          public void run()
          {
            try
            {
              this.val$finalIntent.setFlags(268435456);
              jsPackages.mContext.startActivity(this.val$finalIntent);
              if ((jsPackages.mContext instanceof Activity)) {
                ((Activity)jsPackages.mContext).overridePendingTransition(0, 0);
              }
              return;
            }
            catch (Exception localException) {}
          }
        });
        return;
      }
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  protected boolean checkPermission(String paramString)
  {
    try
    {
      paramString = new JSONObject(paramString).getString("packagename");
      return (!TextUtils.isEmpty(paramString)) && ((paramString.equals("com.tencent.mtt")) || (paramString.equals("com.android.browser")));
    }
    catch (JSONException paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  @JavascriptInterface
  public String getApkInfo(String paramString)
  {
    if (!checkPermission(paramString)) {
      return "";
    }
    try
    {
      paramString = new JSONObject(paramString).getString("packagename");
      return jsCallGetAppInfo(paramString);
    }
    catch (JSONException paramString)
    {
      paramString.printStackTrace();
    }
    return "";
  }
  
  @JavascriptInterface
  public int isApkInstalled(String paramString)
  {
    if (checkPermission(paramString)) {
      return jsCallCheckPackageExist(paramString);
    }
    return -1;
  }
  
  @JavascriptInterface
  public void runApk(String paramString)
  {
    if (checkPermission(paramString)) {
      jsCallRunApk(paramString);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\jsextension\jsPackages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */