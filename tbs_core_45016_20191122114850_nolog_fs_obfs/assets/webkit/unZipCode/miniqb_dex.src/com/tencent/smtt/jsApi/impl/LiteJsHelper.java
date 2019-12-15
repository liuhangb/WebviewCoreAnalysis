package com.tencent.smtt.jsApi.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import com.tencent.smtt.jsApi.export.IJsHelperDestroyCallback;
import com.tencent.smtt.jsApi.export.IWebviewNotifyListener;
import com.tencent.smtt.jsApi.impl.lite.BaseWebView;
import java.util.ArrayList;
import java.util.Map;
import org.json.JSONObject;

public class LiteJsHelper
  extends OpenJsHelper
  implements IJsHelperDestroyCallback
{
  private BaseWebView a;
  
  @SuppressLint({"NewApi"})
  public LiteJsHelper(Object paramObject, Context paramContext1, Context paramContext2)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplLiteBaseWebView = ((BaseWebView)paramObject);
    if (Build.VERSION.SDK_INT >= 11)
    {
      this.jdField_a_of_type_ComTencentSmttJsApiImplLiteBaseWebView.removeJavascriptInterface("searchBoxJavaBridge_");
      this.jdField_a_of_type_ComTencentSmttJsApiImplLiteBaseWebView.removeJavascriptInterface("accessibility");
      this.jdField_a_of_type_ComTencentSmttJsApiImplLiteBaseWebView.removeJavascriptInterface("accessibilityTraversal");
    }
    super.setWebView(this.jdField_a_of_type_ComTencentSmttJsApiImplLiteBaseWebView);
    super.setContext(paramContext1);
    super.setActivityContext(paramContext2);
  }
  
  public Object doCreateEmbeddedGameFramework(ArrayList<IWebviewNotifyListener> paramArrayList)
  {
    return null;
  }
  
  public void doEnterFullScreen()
  {
    super.doEnterFullScreen();
    BaseWebView localBaseWebView = this.jdField_a_of_type_ComTencentSmttJsApiImplLiteBaseWebView;
    if ((localBaseWebView instanceof BaseWebView)) {
      localBaseWebView.startLoadMetaData();
    }
  }
  
  public void doExitMidPage() {}
  
  public void doExitMiniQB(String paramString) {}
  
  public String doGetMiniqbVersion()
  {
    return "";
  }
  
  public int doHideScreenAd()
  {
    return -1;
  }
  
  protected void doLoadUrl(String paramString)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplLiteBaseWebView.loadUrl(paramString);
  }
  
  public void doReloadMeta(String paramString)
  {
    BaseWebView localBaseWebView = this.jdField_a_of_type_ComTencentSmttJsApiImplLiteBaseWebView;
    if ((localBaseWebView instanceof BaseWebView))
    {
      localBaseWebView.loadUrl(paramString);
      this.jdField_a_of_type_ComTencentSmttJsApiImplLiteBaseWebView.startLoadMetaData();
    }
  }
  
  public void doShowMidPage(String paramString1, int paramInt, String paramString2, String paramString3) {}
  
  public void doShowMiniQB(String paramString) {}
  
  public void doShowNormalDialog(String paramString1, String paramString2) {}
  
  public void doShowPermissionDialog(String paramString, OpenJsHelper.PermissionCallback paramPermissionCallback) {}
  
  public int doShowScreenAd(String paramString)
  {
    return -1;
  }
  
  public void doStartFullscreenPlayer(int paramInt, Bundle paramBundle) {}
  
  public void doStartLive(int paramInt, Bundle paramBundle) {}
  
  protected void evaluateJavascript(String paramString, ValueCallback paramValueCallback) {}
  
  @SuppressLint({"NewApi"})
  protected void evaluateJavascriptInFrame(String paramString1, String paramString2)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplLiteBaseWebView.evaluateJavascript(paramString2, new ValueCallback()
    {
      public void onReceiveValue(String paramAnonymousString) {}
    });
  }
  
  public String getAdInfo()
  {
    return null;
  }
  
  public String getDownloadFileInfo()
  {
    return null;
  }
  
  public String getHistoryUrl(int paramInt)
  {
    return null;
  }
  
  public Object getInternalWebView()
  {
    return null;
  }
  
  public String getTitle()
  {
    return this.jdField_a_of_type_ComTencentSmttJsApiImplLiteBaseWebView.getTitle();
  }
  
  public String getUrl()
  {
    return this.jdField_a_of_type_ComTencentSmttJsApiImplLiteBaseWebView.getUrlUnSafe();
  }
  
  public String getUserAgent()
  {
    return this.jdField_a_of_type_ComTencentSmttJsApiImplLiteBaseWebView.getSettings().getUserAgentString();
  }
  
  public float getWebViewScale()
  {
    return this.jdField_a_of_type_ComTencentSmttJsApiImplLiteBaseWebView.getScale();
  }
  
  public void initAR() {}
  
  public void isInstalled(String paramString1, String paramString2, OpenJsHelper.CallbackRunnable paramCallbackRunnable)
  {
    paramCallbackRunnable.setReturnValue("");
    this.jdField_a_of_type_AndroidOsHandler.post(paramCallbackRunnable);
  }
  
  public void onWebViewDestroyed() {}
  
  public int preloadScreenAd(String paramString)
  {
    return -1;
  }
  
  public void savePageToDisk(String paramString, boolean paramBoolean, int paramInt, ValueCallback<String> paramValueCallback) {}
  
  public String setAdInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    return null;
  }
  
  public void setSniffVideoInfo(String paramString1, int paramInt, String paramString2, String paramString3) {}
  
  public void setTbsARShareType(int paramInt) {}
  
  public String ugJsApiExec(String paramString1, ValueCallback<JSONObject> paramValueCallback, JSONObject paramJSONObject, String paramString2, Map<String, Object> paramMap)
  {
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\LiteJsHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */