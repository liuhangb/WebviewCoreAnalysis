package com.tencent.tbs.common.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.text.TextUtils;
import com.tencent.common.utils.IntentUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.MttLoader;
import com.tencent.common.utils.TbsMode;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.stat.TBSStatManager;
import java.net.URLEncoder;

public class TBSIntentUtils
{
  private static final String ANCHOR_POINT = "AnchorPoint";
  private static final String CHANNEL_ID = "ChannelID";
  private static final String CONTENT_SIZE = "ContentSize";
  public static final String DOWNLOAD_INTERCEPT = "downloadintercept";
  public static final String DOWNLOAD_VIDEO = "downloadvideo";
  private static final String FROM_DOWNLOAD_ID = "FromDownloadID";
  public static final String MTT_ACTION = "com.tencent.QQBrowser.action.VIEW";
  private static final String POS_ID = "PosID";
  public static final String QQBROWSER_PARAMS_FROME = ",from=";
  public static final String QQBROWSER_PARAMS_PACKAGENAME = ",packagename=";
  public static final String QQBROWSER_PARAMS_PD = ",product=";
  public static final String QQBROWSER_PARAMS_VERSION = ",version=";
  public static final String QQBROWSER_SCHEME = "mttbrowser://url=";
  private static final String TBS_DOWNLOAD_SCENE = "tbs_download_scene";
  public static final String TENCENT_FILE_PARAMS_DOWNLOADCOOKIE = ",downloadcookie=";
  public static final String TENCENT_FILE_PARAMS_DOWNLOADFILENAME = ",downloadfilename=";
  public static final String TENCENT_FILE_PARAMS_DOWNLOADMINE = ",downloadmimetype=";
  public static final String TENCENT_FILE_PARAMS_DOWNLOADURL = ",downloadurl=";
  public static final String TENCENT_FILE_PARAMS_ENCODE = ",encoded=1";
  public static final String TENCENT_FILE_PARAMS_FORCE_DOWNLOAD = ",forcedownload=";
  public static final String TENCENT_FILE_PARAMS_FROME = ",from=";
  public static final String TENCENT_FILE_PARAMS_PACKAGENAME = ",packagename=";
  public static final String TENCENT_FILE_PARAMS_PD = ",product=";
  public static final String TENCENT_FILE_PARAMS_VERSION = ",version=";
  public static final String TENCENT_FILE_SCHEME = "tencentfile://feature/dispatch?feature=3&,url=";
  
  public static Intent getOpenUrlAndDownloadInQQBrowserWithReport(Context paramContext, boolean paramBoolean, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
  {
    paramContext = IntentUtils.getOpenUrlAndDownloadInQQBrowserWithReport(paramContext, paramBoolean, paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8);
    if ((paramContext != null) && (paramContext.getData() != null))
    {
      paramString1 = new StringBuilder();
      paramString1.append(paramContext.getData().toString());
      paramString1.append(",tbs_download_scene=");
      paramString1.append(paramString9);
      paramContext.setData(Uri.parse(paramString1.toString()));
      paramContext.setComponent(new ComponentName(TbsMode.QB_PKGNAME(), "com.tencent.mtt.MainActivity"));
      paramContext.addFlags(268435456);
      paramString1 = TBSStatManager.getInstance();
      paramString2 = new StringBuilder();
      paramString2.append("BZQBD_");
      paramString2.append(paramString9);
      paramString1.userBehaviorStatistics(paramString2.toString());
    }
    return paramContext;
  }
  
  public static Intent getOpenUrlAndDownloadInTencentFileWithReport(Context paramContext, boolean paramBoolean, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    String str = paramString1;
    for (;;)
    {
      try
      {
        if (!TextUtils.isEmpty(paramString1)) {
          str = URLEncoder.encode(paramString1, "UTF-8");
        }
        paramString1 = paramString2;
        if (!TextUtils.isEmpty(paramString2)) {
          paramString1 = URLEncoder.encode(paramString2, "UTF-8");
        }
        paramString2 = paramString3;
        if (!TextUtils.isEmpty(paramString3)) {
          paramString2 = URLEncoder.encode(paramString3, "UTF-8");
        }
        paramString3 = paramString4;
        if (!TextUtils.isEmpty(paramString4)) {
          paramString3 = URLEncoder.encode(paramString4, "UTF-8");
        }
        paramString4 = paramString5;
        if (!TextUtils.isEmpty(paramString5)) {
          paramString4 = URLEncoder.encode(paramString5, "UTF-8");
        }
        paramString5 = new StringBuilder();
        paramString5.append("tencentfile://feature/dispatch?feature=3&,url=");
        paramString5.append(str);
        paramString5.append(",downloadfilename=");
        paramString5.append(paramString3);
        paramString5.append(",forcedownload=");
        if (!paramBoolean) {
          break label475;
        }
        i = 1;
        paramString5.append(i);
        paramString5.append(",downloadcookie=");
        paramString5.append(paramString2);
        paramString5.append(",downloadmimetype=");
        paramString5.append(paramString4);
        paramString5.append(",downloadurl=");
        paramString5.append(paramString1);
        paramString5.append(",product=");
        paramString5.append("TBS");
        paramString5.append(",packagename=");
        paramString5.append(paramContext.getApplicationContext().getPackageName());
        paramString5.append(",from=");
        paramString5.append(paramString6);
        paramString5.append(",version=");
        paramString5.append(TbsBaseModuleShell.getTesVersionName());
        paramString5.append(",encoded=1");
        paramContext = new StringBuilder();
        paramContext.append("getOpenUrlAndDownloadInTencentFileWithReport--StringBuilder = ");
        paramContext.append(paramString5.toString());
        LogUtils.d("IntentUtils", paramContext.toString());
        paramString1 = new Intent("android.intent.action.VIEW");
      }
      catch (Exception paramContext)
      {
        paramContext.printStackTrace();
        return null;
      }
      try
      {
        paramContext = Uri.parse(paramString5.toString());
      }
      catch (Exception paramContext)
      {
        continue;
      }
      paramContext = null;
      paramString1.setData(paramContext);
      if (!TextUtils.isEmpty(paramString7))
      {
        paramContext = new StringBuilder();
        paramContext.append("ChannelID:");
        paramContext.append(paramString7);
        LogUtils.d("IntentUtils", paramContext.toString());
        paramString1.putExtra("ChannelID", paramString7);
      }
      if (!TextUtils.isEmpty(paramString8))
      {
        paramContext = new StringBuilder();
        paramContext.append("PosID:");
        paramContext.append(paramString8);
        LogUtils.d("IntentUtils", paramContext.toString());
        paramString1.putExtra("PosID", paramString8);
      }
      paramString1.putExtra("FromDownloadID", "download");
      paramString1.setFlags(268435456);
      return paramString1;
      label475:
      int i = 0;
    }
  }
  
  public static boolean openUrlAndDownloadInQQBrowserWithReport(Context paramContext, boolean paramBoolean, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
  {
    paramString1 = getOpenUrlAndDownloadInQQBrowserWithReport(paramContext, paramBoolean, paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9);
    if (paramString1 != null) {
      try
      {
        paramContext.startActivity(paramString1);
        return true;
      }
      catch (Exception paramContext)
      {
        paramContext.printStackTrace();
      }
    }
    return false;
  }
  
  public static boolean openUrlInQQBrowser(Context paramContext, String paramString1, String paramString2)
  {
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("mttbrowser://url=");
      localStringBuilder.append(paramString1);
      localStringBuilder.append(",product=");
      localStringBuilder.append("TBS");
      localStringBuilder.append(",packagename=");
      localStringBuilder.append(paramContext.getApplicationContext().getPackageName());
      localStringBuilder.append(",from=");
      localStringBuilder.append(paramString2);
      localStringBuilder.append(",version=");
      localStringBuilder.append(TbsBaseModuleShell.getTesVersionName());
      LogUtils.d("IntentUtils", localStringBuilder.toString());
      paramString1 = MttLoader.getIntent(paramContext, localStringBuilder.toString());
      if (paramString1 != null)
      {
        paramContext.startActivity(paramString1);
        return true;
      }
      return true;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public static boolean openUrlInQQBrowserWithReport(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, Point paramPoint1, Point paramPoint2)
  {
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("mttbrowser://url=");
      localStringBuilder.append(paramString1);
      localStringBuilder.append(",product=");
      localStringBuilder.append("TBS");
      localStringBuilder.append(",packagename=");
      localStringBuilder.append(paramContext.getApplicationContext().getPackageName());
      localStringBuilder.append(",from=");
      localStringBuilder.append(paramString2);
      localStringBuilder.append(",version=");
      localStringBuilder.append(TbsBaseModuleShell.getTesVersionName());
      paramString1 = new StringBuilder();
      paramString1.append("openUrlInQQBrowserWithReport--StringBuilder = ");
      paramString1.append(localStringBuilder.toString());
      LogUtils.d("IntentUtils", paramString1.toString());
      paramString1 = MttLoader.getIntent(paramContext, localStringBuilder.toString());
      if (paramString1 != null)
      {
        if (!TextUtils.isEmpty(paramString3)) {
          paramString1.putExtra("ChannelID", paramString3);
        }
        if (!TextUtils.isEmpty(paramString4)) {
          paramString1.putExtra("PosID", paramString4);
        }
        if (paramPoint1 != null) {
          paramString1.putExtra("AnchorPoint", paramPoint1);
        }
        if (paramPoint2 != null) {
          paramString1.putExtra("ContentSize", paramPoint2);
        }
        paramContext.startActivity(paramString1);
        return true;
      }
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public static boolean openUrlInTencentFileWithReport(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    try
    {
      paramString1 = getOpenUrlAndDownloadInTencentFileWithReport(paramContext, true, paramString1, paramString1, "", "", "", paramString2, paramString3, paramString4);
      if (paramString1 != null)
      {
        paramContext.startActivity(paramString1);
        return true;
      }
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\TBSIntentUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */