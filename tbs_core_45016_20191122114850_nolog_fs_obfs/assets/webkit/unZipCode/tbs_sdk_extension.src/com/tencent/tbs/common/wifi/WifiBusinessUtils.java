package com.tencent.tbs.common.wifi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.tencent.mtt.ContextHolder;
import com.tencent.tbs.common.download.qb.QBDownloadListener;
import com.tencent.tbs.common.download.qb.QBDownloadManager;
import com.tencent.tbs.common.download.qb.QBInstallListener;
import com.tencent.tbs.common.resources.TBSResources;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.stat.TBSStatManager;
import com.tencent.tbs.common.ui.dialog.ProgressAlertDialog;
import com.tencent.tbs.common.ui.dialog.ProgressAlertDialog.ExtButtonClickListenerSerial;
import com.tencent.tbs.common.ui.dialog.ProgressAlertDialog.IProgressViewListener;
import com.tencent.tbs.common.ui.dialog.TBSDefaultDialogBuilder;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialog;

public class WifiBusinessUtils
{
  public static final String BUNDLE_KEY_CODE = "newApi";
  public static final String BUNDLE_KEY_FROM = "fromTbs";
  public static final int FROM_ICON = 1;
  public static final int FROM_INST_FIN = 2;
  public static final int FROM_OTHER = -1;
  public static final int FROM_PLAYER_AD = 4;
  public static final int FROM_PLAYER_DLG = 3;
  public static final String LOGIN_TYPE_KEY_PARTNER_CALL_POS = "PosID";
  public static final String LOGIN_TYPE_KEY_PARTNER_ID = "ChannelID";
  public static final String LOGIN_TYPE_KEY_TYPE = "loginType";
  private static final int LOGIN_TYPE_OTHER = 12;
  private static final int LOGIN_TYPE_QQ = 2;
  private static final int LOGIN_TYPE_QZONE = 3;
  private static final int LOGIN_TYPE_WB = 4;
  private static final int LOGIN_TYPE_WX = 24;
  private static final String PACKAGE_NAME_QQ = "com.tencent.mobileqq";
  private static final String PACKAGE_NAME_QZONE = "com.tencent.qzone";
  private static final String PACKAGE_NAME_WEIBO = "com.tencent.wblog";
  private static final String PACKAGE_NAME_WX = "com.tencent.mm";
  public static final String QB_PKG_NAME = "com.tencent.mtt";
  public static final String QB_URL_PLAYER_AD_CH_ID = "0003";
  public static final String QB_URL_VIDEO_CH_ID = "0004";
  public static final String QB_WIFI_URL = "qb://freewifi";
  public static final String QB_WIFI_URL_WITH_FROM = "qb://freewifi/?fromTbs=true";
  public static final String STAT_POSID_WIFI_FROM_TBS = "21001";
  private static final String TAG = "WifiBusinessUtils";
  static final int WIFI_NOQBSWITCH_DOWNLOADQB = 2;
  static final int WIFI_NOQBSWITCH_JUMPTBS = 1;
  
  public static void activateOrInst4Wifi(Context paramContext, int paramInt, FreeWifiGuideView.IWifiGuideActionListener paramIWifiGuideActionListener)
  {
    Object localObject = ContextHolder.getAppContext();
    boolean bool1;
    if (getInstalledPKGInfo("com.tencent.mtt", (Context)localObject, 128) != null)
    {
      boolean bool2 = isWifiQbInstalled((Context)localObject);
      bool1 = bool2;
      if (paramInt == 1)
      {
        TBSStatManager.getInstance().userBehaviorStatistics("AWTWF014");
        if (bool2)
        {
          TBSStatManager.getInstance().userBehaviorStatistics("AWTWF015");
          bool1 = bool2;
        }
        else
        {
          TBSStatManager.getInstance().userBehaviorStatistics("AWTWF016");
          bool1 = bool2;
        }
      }
    }
    else
    {
      if (paramInt == 1) {
        TBSStatManager.getInstance().userBehaviorStatistics("AWTWF017");
      }
      bool1 = false;
    }
    if (bool1)
    {
      jump2QbWifiHelper(getLoginType(), "21001", paramInt);
      return;
    }
    int i = PublicSettingManager.getInstance().getWifiNOQBSwitch();
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[activateOrInst4Wifi] wifiNOQBSwitch:");
    ((StringBuilder)localObject).append(i);
    ((StringBuilder)localObject).toString();
    if (i == 2)
    {
      instOrDowloadQbForWifi(paramContext, paramInt);
      return;
    }
    paramContext = PublicSettingManager.getInstance().getWifiNOQBSwitchJumpUrl();
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[activateOrInst4Wifi] strJumpUrl:");
    ((StringBuilder)localObject).append(paramContext);
    ((StringBuilder)localObject).toString();
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[activateOrInst4Wifi] listener:");
    ((StringBuilder)localObject).append(paramIWifiGuideActionListener);
    ((StringBuilder)localObject).toString();
    if (paramIWifiGuideActionListener != null) {
      paramIWifiGuideActionListener.jumpTo(paramContext);
    }
    TBSStatManager.getInstance().userBehaviorStatistics("AWTWF101");
  }
  
  public static PackageInfo getInstalledPKGInfo(String paramString, Context paramContext, int paramInt)
  {
    if (paramContext != null)
    {
      if (TextUtils.isEmpty(paramString)) {
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
  
  public static int getLoginType()
  {
    String str = ContextHolder.getAppContext().getPackageName();
    if (!TextUtils.isEmpty(str))
    {
      if (str.contains("com.tencent.mm")) {
        return 24;
      }
      if (str.contains("com.tencent.mobileqq")) {
        return 2;
      }
      if (str.contains("com.tencent.qzone")) {
        return 3;
      }
      if (str.contains("com.tencent.wblog")) {
        return 4;
      }
    }
    return 12;
  }
  
  private static int getVersionInt()
  {
    Object localObject = getInstalledPKGInfo("com.tencent.mtt", ContextHolder.getAppContext(), 128);
    int i = -1;
    if (localObject != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getVersionInt() versionName: ");
      localStringBuilder.append(((PackageInfo)localObject).versionName);
      localStringBuilder.toString();
      if ((!TextUtils.isEmpty(((PackageInfo)localObject).versionName)) && (((PackageInfo)localObject).versionName.length() > 5))
      {
        localObject = ((PackageInfo)localObject).versionName.substring(0, ((PackageInfo)localObject).versionName.length() - 5).replaceAll("\\.", "");
        try
        {
          int j = Integer.parseInt((String)localObject);
          i = j;
          localObject = new StringBuilder();
          i = j;
          ((StringBuilder)localObject).append("getVersionInt() versionInt: ");
          i = j;
          ((StringBuilder)localObject).append(j);
          i = j;
          ((StringBuilder)localObject).toString();
          return j;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          localNumberFormatException.printStackTrace();
          return i;
        }
      }
    }
    return -1;
  }
  
  public static void instOrDowloadQbForWifi(final Context paramContext, int paramInt)
  {
    TBSStatManager.getInstance().userBehaviorStatistics("AWTWF018");
    final Bundle localBundle = new Bundle();
    if (paramInt == 1)
    {
      TBSStatManager.getInstance().userBehaviorStatistics("AWTWF054");
      localBundle.putString("param_key_featureid", "WiFi");
      localBundle.putString("param_key_functionid", "2001");
    }
    else if (paramInt == 3)
    {
      TBSStatManager.getInstance().userBehaviorStatistics("AWTWF055");
      localBundle.putString("param_key_featureid", "WiFi");
      localBundle.putString("param_key_functionid", "2002");
    }
    final QBInstallListener local1 = new QBInstallListener()
    {
      public boolean onInstallFinished()
      {
        WifiBusinessUtils.jump2QbWifiHelper(WifiBusinessUtils.getLoginType(), "21001", 2);
        return true;
      }
      
      public boolean onInstalling()
      {
        return false;
      }
      
      public boolean onUninstallFinished()
      {
        return false;
      }
    };
    Object localObject4 = ContextHolder.getAppContext();
    final boolean bool1;
    if (getInstalledPKGInfo("com.tencent.mtt", paramContext, 128) != null)
    {
      bool1 = true;
    }
    else
    {
      TBSStatManager.getInstance().userBehaviorStatistics("AWTWF042");
      bool1 = false;
    }
    boolean bool2 = PublicSettingManager.getInstance().getWifiHasAuthorizeDownload();
    boolean bool3 = QBDownloadManager.getInstance().isQBDownloaded();
    Object localObject1;
    Object localObject2;
    if (bool3)
    {
      TBSStatManager.getInstance().userBehaviorStatistics("AWTWF019");
      if (!bool2)
      {
        TBSStatManager.getInstance().userBehaviorStatistics("AWTWF032");
        if (bool1)
        {
          localObject1 = TBSResources.getString("wifi_upgrade");
          localObject2 = TBSResources.getString("wifi_upgrading");
        }
        else
        {
          localObject1 = TBSResources.getString("wifi_download");
          localObject2 = TBSResources.getString("wifi_downloading");
        }
      }
      else
      {
        TBSStatManager.getInstance().userBehaviorStatistics("AWTWF031");
        localObject1 = TBSResources.getString("wifi_install");
        localObject2 = "";
      }
    }
    else
    {
      TBSStatManager.getInstance().userBehaviorStatistics("AWTWF020");
      if (bool1)
      {
        localObject1 = TBSResources.getString("wifi_upgrade");
        localObject2 = TBSResources.getString("wifi_upgrading");
      }
      else
      {
        localObject1 = TBSResources.getString("wifi_download");
        localObject2 = TBSResources.getString("wifi_downloading");
      }
    }
    Object localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append((String)localObject1);
    ((StringBuilder)localObject3).append(TBSResources.getString("wifi_inst_qb_desc"));
    localObject3 = ((StringBuilder)localObject3).toString();
    int i = PublicSettingManager.getInstance().getWifiDownloadOrInstDlgType();
    Object localObject5 = new StringBuilder();
    ((StringBuilder)localObject5).append("instOrDowloadQbForWifi() uiType: ");
    ((StringBuilder)localObject5).append(i);
    ((StringBuilder)localObject5).toString();
    if (i == 1)
    {
      localObject5 = TBSResources.getBitmap("wifi_download_or_inst_banner");
      if ((bool3) && (bool2))
      {
        localObject2 = new ProgressAlertDialog(paramContext, (String)localObject3, 1, (Bitmap)localObject5, false, null);
        ((ProgressAlertDialog)localObject2).setIsButtonTextCustomized(true);
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("instOrDowloadQbForWifi() 进度条文字自定义: ");
        ((StringBuilder)localObject3).append((String)localObject1);
        ((StringBuilder)localObject3).toString();
        ((ProgressAlertDialog)localObject2).setCustomInitButtonText((String)localObject1);
        ((ProgressAlertDialog)localObject2).setCustomDownloadSuccButtonText((String)localObject1);
        ((ProgressAlertDialog)localObject2).setOnProgressViewListener(new ProgressAlertDialog.IProgressViewListener()
        {
          public void onInstallButtonClick()
          {
            ProgressAlertDialog localProgressAlertDialog = this.val$progDlg;
            if (localProgressAlertDialog != null) {
              localProgressAlertDialog.dismiss();
            }
            TBSStatManager.getInstance().userBehaviorStatistics("AWTWF033");
            TBSStatManager.getInstance().userBehaviorStatistics("AWTWF021");
            if (!bool1) {
              TBSStatManager.getInstance().userBehaviorStatistics("AWTWF043");
            }
            PublicSettingManager.getInstance().setWifiHasAuthorizeDownload(true);
            int i = QBDownloadManager.getInstance().startDownload(paramContext, true, "AWTWF027", "AWTWF028", null, null, local1, localBundle);
            if (i == -4)
            {
              TBSStatManager.getInstance().userBehaviorStatistics("AWTWF060");
              return;
            }
            if (i == 1) {
              TBSStatManager.getInstance().userBehaviorStatistics("AWTWF059");
            }
          }
        });
        ((ProgressAlertDialog)localObject2).show();
        return;
      }
      if (paramInt == 1)
      {
        localBundle.putString("param_key_featureid", "WiFi");
        localBundle.putString("param_key_functionid", "2001");
      }
      else if (paramInt == 3)
      {
        localBundle.putString("param_key_featureid", "WiFi");
        localBundle.putString("param_key_functionid", "2002");
      }
      localBundle.putBoolean("key_show_progress", true);
      localBundle.putString("key_show_message", (String)localObject3);
      localBundle.putInt("key_show_type", 3);
      localBundle.putParcelable("key_show_bitmap", (Parcelable)localObject5);
      if (bool1) {
        localObject3 = TBSResources.getString("wifi_qb_upgrade_under_wifi");
      } else {
        localObject3 = TBSResources.getString("wifi_qb_download_under_wifi");
      }
      localBundle.putString("key_ext_btn_txt", (String)localObject3);
      localBundle.putSerializable("key_ext_btn_click_listener", new ProgressAlertDialog.ExtButtonClickListenerSerial()
      {
        private static final long serialVersionUID = -9100162817492158293L;
        
        public void onClick(View paramAnonymousView)
        {
          TBSStatManager.getInstance().userBehaviorStatistics("AWTWF024");
          PublicSettingManager.getInstance().setWifiHasAuthorizeDownload(true);
          QBDownloadManager.getInstance().setAutoSilentDownloadSwitch(this.val$appContext, true);
          Toast.makeText(paramContext, TBSResources.getString("wifi_toast_auto_download_qb_under_wifi"), 0).show();
        }
      });
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("instOrDowloadQbForWifi() 进度条文字自定义: ");
      ((StringBuilder)localObject3).append((String)localObject1);
      ((StringBuilder)localObject3).toString();
      localBundle.putBoolean("key_is_progress_text_custom", true);
      localBundle.putString("key_custom_progress_init_text", (String)localObject1);
      if (!TextUtils.isEmpty((CharSequence)localObject2)) {
        localBundle.putString("key_custom_progress_downloading_text", (String)localObject2);
      } else {
        localBundle.putString("key_custom_progress_downloading_text", (String)localObject1);
      }
      localObject1 = new QBDownloadListener()
      {
        public void onDownloadFailed(boolean paramAnonymousBoolean, Bundle paramAnonymousBundle) {}
        
        public void onDownloadPause(boolean paramAnonymousBoolean, int paramAnonymousInt) {}
        
        public void onDownloadProgress(boolean paramAnonymousBoolean, int paramAnonymousInt) {}
        
        public void onDownloadResume(boolean paramAnonymousBoolean, int paramAnonymousInt) {}
        
        public void onDownloadStart(boolean paramAnonymousBoolean)
        {
          if (!this.val$hasApk)
          {
            TBSStatManager.getInstance().userBehaviorStatistics("AWTWF033");
            if (bool1)
            {
              TBSStatManager.getInstance().userBehaviorStatistics("AWTWF023");
            }
            else
            {
              TBSStatManager.getInstance().userBehaviorStatistics("AWTWF022");
              TBSStatManager.getInstance().userBehaviorStatistics("AWTWF043");
            }
            PublicSettingManager.getInstance().setWifiHasAuthorizeDownload(true);
          }
        }
        
        public void onDownloadSucess(boolean paramAnonymousBoolean, String paramAnonymousString, Bundle paramAnonymousBundle)
        {
          if (this.val$hasApk)
          {
            TBSStatManager.getInstance().userBehaviorStatistics("AWTWF033");
            if (bool1)
            {
              TBSStatManager.getInstance().userBehaviorStatistics("AWTWF023");
            }
            else
            {
              TBSStatManager.getInstance().userBehaviorStatistics("AWTWF022");
              TBSStatManager.getInstance().userBehaviorStatistics("AWTWF043");
            }
            PublicSettingManager.getInstance().setWifiHasAuthorizeDownload(true);
          }
        }
      };
      paramInt = QBDownloadManager.getInstance().startDownload(paramContext, true, "AWTWF027", "AWTWF028", null, (QBDownloadListener)localObject1, local1, localBundle);
      if (paramInt == 1)
      {
        TBSStatManager.getInstance().userBehaviorStatistics("AWTWF059");
        return;
      }
      if (paramInt == -4) {
        TBSStatManager.getInstance().userBehaviorStatistics("AWTWF060");
      }
    }
    else
    {
      if (i == 0)
      {
        localObject2 = new TBSDefaultDialogBuilder(paramContext);
        ((TBSDefaultDialogBuilder)localObject2).setMessage((String)localObject3);
        ((TBSDefaultDialogBuilder)localObject2).setNegativeButton(TBSResources.getString("wifi_cacel"), new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            paramAnonymousDialogInterface.dismiss();
          }
        });
        ((TBSDefaultDialogBuilder)localObject2).setPositiveButton((String)localObject1, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            TBSStatManager.getInstance().userBehaviorStatistics("AWTWF033");
            if (this.val$hasApk) {
              TBSStatManager.getInstance().userBehaviorStatistics("AWTWF021");
            } else if (bool1) {
              TBSStatManager.getInstance().userBehaviorStatistics("AWTWF023");
            } else {
              TBSStatManager.getInstance().userBehaviorStatistics("AWTWF022");
            }
            if (!bool1) {
              TBSStatManager.getInstance().userBehaviorStatistics("AWTWF043");
            }
            PublicSettingManager.getInstance().setWifiHasAuthorizeDownload(true);
            paramAnonymousDialogInterface = TBSResources.getString("wifi_installing");
            paramAnonymousInt = QBDownloadManager.getInstance().startDownload(paramContext, true, "AWTWF027", "AWTWF028", paramAnonymousDialogInterface, null, local1, localBundle);
            if (paramAnonymousInt == 1)
            {
              TBSStatManager.getInstance().userBehaviorStatistics("AWTWF059");
              return;
            }
            if (paramAnonymousInt == -4)
            {
              TBSStatManager.getInstance().userBehaviorStatistics("AWTWF060");
              return;
            }
            if (paramAnonymousInt == -3) {
              Toast.makeText(paramContext, paramAnonymousDialogInterface, 0).show();
            }
          }
        });
        ((TBSDefaultDialogBuilder)localObject2).create().show();
        return;
      }
      if (i == 2)
      {
        localObject4 = TBSResources.getBitmap("wifi_download_or_inst_banner");
        if ((bool3) && (bool2))
        {
          localObject2 = new ProgressAlertDialog(paramContext, (String)localObject3, 1, (Bitmap)localObject4, false, null);
          ((ProgressAlertDialog)localObject2).setIsButtonTextCustomized(true);
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("instOrDowloadQbForWifi() 进度条文字自定义: ");
          ((StringBuilder)localObject3).append((String)localObject1);
          ((StringBuilder)localObject3).toString();
          ((ProgressAlertDialog)localObject2).setCustomInitButtonText((String)localObject1);
          ((ProgressAlertDialog)localObject2).setCustomDownloadSuccButtonText((String)localObject1);
          ((ProgressAlertDialog)localObject2).setOnProgressViewListener(new ProgressAlertDialog.IProgressViewListener()
          {
            public void onInstallButtonClick()
            {
              ProgressAlertDialog localProgressAlertDialog = this.val$progDlg;
              if (localProgressAlertDialog != null) {
                localProgressAlertDialog.dismiss();
              }
              TBSStatManager.getInstance().userBehaviorStatistics("AWTWF033");
              TBSStatManager.getInstance().userBehaviorStatistics("AWTWF021");
              if (!bool1) {
                TBSStatManager.getInstance().userBehaviorStatistics("AWTWF043");
              }
              PublicSettingManager.getInstance().setWifiHasAuthorizeDownload(true);
              int i = QBDownloadManager.getInstance().startDownload(paramContext, true, "AWTWF027", "AWTWF028", null, null, local1, localBundle);
              if (i == -4)
              {
                TBSStatManager.getInstance().userBehaviorStatistics("AWTWF060");
                return;
              }
              if (i == 1) {
                TBSStatManager.getInstance().userBehaviorStatistics("AWTWF059");
              }
            }
          });
          ((ProgressAlertDialog)localObject2).show();
          return;
        }
        localBundle.putBoolean("key_show_progress", true);
        localBundle.putString("key_show_message", (String)localObject3);
        localBundle.putInt("key_show_type", 1);
        localBundle.putParcelable("key_show_bitmap", (Parcelable)localObject4);
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("instOrDowloadQbForWifi() 进度条文字自定义: ");
        ((StringBuilder)localObject3).append((String)localObject1);
        ((StringBuilder)localObject3).toString();
        localBundle.putBoolean("key_is_progress_text_custom", true);
        localBundle.putString("key_custom_progress_init_text", (String)localObject1);
        if (!TextUtils.isEmpty((CharSequence)localObject2)) {
          localBundle.putString("key_custom_progress_downloading_text", (String)localObject2);
        } else {
          localBundle.putString("key_custom_progress_downloading_text", (String)localObject1);
        }
        localObject1 = new QBDownloadListener()
        {
          public void onDownloadFailed(boolean paramAnonymousBoolean, Bundle paramAnonymousBundle) {}
          
          public void onDownloadPause(boolean paramAnonymousBoolean, int paramAnonymousInt) {}
          
          public void onDownloadProgress(boolean paramAnonymousBoolean, int paramAnonymousInt) {}
          
          public void onDownloadResume(boolean paramAnonymousBoolean, int paramAnonymousInt) {}
          
          public void onDownloadStart(boolean paramAnonymousBoolean)
          {
            if (!this.val$hasApk)
            {
              TBSStatManager.getInstance().userBehaviorStatistics("AWTWF033");
              if (bool1)
              {
                TBSStatManager.getInstance().userBehaviorStatistics("AWTWF023");
              }
              else
              {
                TBSStatManager.getInstance().userBehaviorStatistics("AWTWF022");
                TBSStatManager.getInstance().userBehaviorStatistics("AWTWF043");
              }
              PublicSettingManager.getInstance().setWifiHasAuthorizeDownload(true);
            }
          }
          
          public void onDownloadSucess(boolean paramAnonymousBoolean, String paramAnonymousString, Bundle paramAnonymousBundle)
          {
            if (this.val$hasApk)
            {
              TBSStatManager.getInstance().userBehaviorStatistics("AWTWF033");
              if (bool1)
              {
                TBSStatManager.getInstance().userBehaviorStatistics("AWTWF023");
              }
              else
              {
                TBSStatManager.getInstance().userBehaviorStatistics("AWTWF022");
                TBSStatManager.getInstance().userBehaviorStatistics("AWTWF043");
              }
              PublicSettingManager.getInstance().setWifiHasAuthorizeDownload(true);
            }
          }
        };
        paramInt = QBDownloadManager.getInstance().startDownload(paramContext, true, "AWTWF027", "AWTWF028", null, (QBDownloadListener)localObject1, local1, localBundle);
        if (paramInt == -4)
        {
          TBSStatManager.getInstance().userBehaviorStatistics("AWTWF060");
          return;
        }
        if (paramInt == 1) {
          TBSStatManager.getInstance().userBehaviorStatistics("AWTWF059");
        }
      }
    }
  }
  
  public static boolean isNewWifiQbInstalled(Context paramContext)
  {
    return getVersionInt() >= 700;
  }
  
  public static boolean isWifiQbInstalled(Context paramContext)
  {
    return getVersionInt() >= 661;
  }
  
  public static void jump2QbWifiHelper(int paramInt1, String paramString, int paramInt2)
  {
    Context localContext = ContextHolder.getAppContext();
    if (isWifiQbInstalled(localContext))
    {
      Intent localIntent = new Intent();
      localIntent.setAction("android.intent.action.VIEW");
      localIntent.setPackage("com.tencent.mtt");
      Object localObject = "qb://freewifi/?fromTbs=true";
      if ((paramInt2 != 1) && (paramInt2 != 2) && (paramInt2 != 3))
      {
        if (paramInt2 == 4)
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("qb://freewifi/?fromTbs=true");
          ((StringBuilder)localObject).append("&ch=");
          ((StringBuilder)localObject).append("0003");
          localObject = ((StringBuilder)localObject).toString();
        }
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("qb://freewifi/?fromTbs=true");
        ((StringBuilder)localObject).append("&ch=");
        ((StringBuilder)localObject).append("0004");
        localObject = ((StringBuilder)localObject).toString();
      }
      localIntent.setData(Uri.parse((String)localObject));
      localIntent.putExtra("loginType", paramInt1);
      localIntent.putExtra("ChannelID", localContext.getPackageName());
      localIntent.putExtra("PosID", paramString);
      if (isNewWifiQbInstalled(localContext))
      {
        localIntent.putExtra("newApi", "1");
        localIntent.putExtra("fromTbs", true);
        localIntent.setClassName("com.tencent.mtt", "com.tencent.mtt.external.wifi.WifiLaunchShortcutActivity");
      }
      else
      {
        localIntent.setClassName("com.tencent.mtt", "com.tencent.mtt.external.wifi.WifiLaunchShortcutActivity");
      }
      localIntent.setFlags(268435456);
      try
      {
        localContext.startActivity(localIntent);
        if (paramInt2 != 4)
        {
          TBSStatManager.getInstance().userBehaviorStatistics("AWTWF058");
          if (paramInt2 == 3)
          {
            TBSStatManager.getInstance().userBehaviorStatistics("AWTWF056");
            return;
          }
          if (paramInt2 == 1)
          {
            TBSStatManager.getInstance().userBehaviorStatistics("AWTWF030");
            return;
          }
          if (paramInt2 == 2) {
            TBSStatManager.getInstance().userBehaviorStatistics("AWTWF029");
          }
        }
        else
        {
          TBSStatManager.getInstance().userBehaviorStatistics("AWTWF057");
          return;
        }
      }
      catch (Exception paramString)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("jump2QbWifiHelper() e.msg: ");
        ((StringBuilder)localObject).append(paramString.getMessage());
        Log.e("WifiBusinessUtils", ((StringBuilder)localObject).toString());
        paramString.printStackTrace();
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\WifiBusinessUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */