package com.tencent.tbs.core.partner.qbdownload;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.smtt.webkit.service.SmttServiceProxy;

public class QbDownloaderProxy
{
  public static final String DEFALUT_QB_OPENURL = "qb://home";
  static final String KEY_QB_CURRENT_INSTALLLISTENER = "key_qb_current_installlistener";
  static final String KEY_QB_CURRENT_URL_PROVIDER = "key_qb_current_url_provider";
  static final String KEY_QB_DOWNLOAD_ISSLIENT = "key_qb_download_issilent";
  static final String KEY_QB_EXTRA_PARAMS = "key_qb_extra_params";
  static final String KEY_QB_NEED_OPENQB = "key_qb_need_openqb";
  static final String KEY_QB_OPENURL = "key_qb_openurl";
  static final String KEY_QB_STATKEY_INSTALLED = "key_qb_statkey_installed";
  static final String KEY_QB_STATKEY_INSTALLING = "key_qb_statkey_installing";
  static final String KEY_QB_TOAST = "key_qb_toast";
  public static final String KEY_SHOW_PROGRESS = "key_show_progress";
  public static final String PARAM_KEY_FEATUREID = "param_key_featureid";
  public static final String PARAM_KEY_FUNCTIONID = "param_key_functionid";
  public static final String PARAM_KEY_POSITIONID = "param_key_positionid";
  public static final String QBDOWNLOAD_URL = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=23150";
  static final String TAG = "QbDownloaderProxy";
  
  public static void handleQbDownload(Context paramContext, String paramString)
  {
    if ((paramString != null) && (paramString.startsWith("http://mdc.html5.qq.com/d/directdown.jsp?channel_id=23150")))
    {
      Uri localUri = Uri.parse(paramString);
      String str1 = localUri.getQueryParameter("param_key_positionid");
      String str2 = localUri.getQueryParameter("param_key_featureid");
      String str3 = localUri.getQueryParameter("param_key_functionid");
      if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2)) && (!TextUtils.isEmpty(str3)))
      {
        Object localObject = localUri.getQueryParameter("key_qb_openurl");
        paramString = (String)localObject;
        if (TextUtils.isEmpty((CharSequence)localObject)) {
          paramString = "qb://home";
        }
        localObject = new Bundle();
        ((Bundle)localObject).putString("key_qb_statkey_installing", localUri.getQueryParameter("key_qb_statkey_installing"));
        ((Bundle)localObject).putString("key_qb_statkey_installed", localUri.getQueryParameter("key_qb_statkey_installed"));
        ((Bundle)localObject).putString("key_qb_openurl", paramString);
        ((Bundle)localObject).putBoolean("key_qb_need_openqb", localUri.getBooleanQueryParameter("key_qb_need_openqb", false));
        ((Bundle)localObject).putString("key_qb_toast", localUri.getQueryParameter("key_qb_toast"));
        ((Bundle)localObject).putBoolean("key_qb_download_issilent", localUri.getBooleanQueryParameter("key_qb_download_issilent", false));
        ((Bundle)localObject).putBoolean("key_show_progress", localUri.getBooleanQueryParameter("key_show_progress", true));
        SmttServiceProxy.getInstance().startDownloadAndInstallQbIfNeeded(paramContext, str1, str2, str3, (Bundle)localObject);
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\qbdownload\QbDownloaderProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */