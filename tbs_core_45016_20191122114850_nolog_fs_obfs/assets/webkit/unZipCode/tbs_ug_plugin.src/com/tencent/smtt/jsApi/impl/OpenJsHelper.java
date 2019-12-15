package com.tencent.smtt.jsApi.impl;

import android.content.Context;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.ValueCallback;
import com.tencent.common.http.Apn;
import com.tencent.common.utils.UrlUtils;
import com.tencent.smtt.jsApi.export.IJsHelperDestroyCallback;
import com.tencent.smtt.jsApi.export.IWebviewNotifyListener;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.utils.QBUrlUtils;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class OpenJsHelper
{
  public static final String API_NO_PERMISSION = "this api without user authorization";
  public static final String PERMISSION_DESKTOP_SHORTCUT = "shortcut";
  public static final String PERMISSION_LOGIN = "login";
  int jdField_a_of_type_Int = -1;
  private Context jdField_a_of_type_AndroidContentContext = null;
  protected Handler a;
  private IJsHelperDestroyCallback jdField_a_of_type_ComTencentSmttJsApiExportIJsHelperDestroyCallback;
  private Object jdField_a_of_type_JavaLangObject = null;
  private String jdField_a_of_type_JavaLangString = null;
  private boolean jdField_a_of_type_Boolean;
  private Context b = null;
  
  protected OpenJsHelper()
  {
    this.jdField_a_of_type_AndroidOsHandler = new MyHandler();
  }
  
  private String a(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("javascript:var metaNodes=document.getElementsByTagName('meta');var metaNode;for(var i=0;i<metaNodes.length;i++){metaNode=metaNodes[i];if(metaNode.name=='");
    localStringBuilder.append(paramString);
    localStringBuilder.append("'){document.head.removeChild(metaNode);}}");
    return localStringBuilder.toString();
  }
  
  private String a(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("javascript:var metaNodes=document.getElementsByTagName('meta');var metaNode;for(var i=0;i<metaNodes.length;i++){metaNode=metaNodes[i];if(metaNode.name=='");
    localStringBuilder.append(paramString1);
    localStringBuilder.append("'){document.head.removeChild(metaNode);}}var metaNode = document.createElement('meta');metaNode.name='");
    localStringBuilder.append(paramString1);
    localStringBuilder.append("';metaNode.content='");
    localStringBuilder.append(paramString2);
    localStringBuilder.append("';document.head.appendChild(metaNode);");
    return localStringBuilder.toString();
  }
  
  private void a(String paramString)
  {
    paramString = a(paramString);
    this.jdField_a_of_type_AndroidOsHandler.obtainMessage(101, paramString).sendToTarget();
  }
  
  private void a(String paramString1, String paramString2)
  {
    paramString1 = a(paramString1, paramString2);
    this.jdField_a_of_type_AndroidOsHandler.obtainMessage(101, paramString1).sendToTarget();
  }
  
  private void a(String paramString, JSONObject paramJSONObject, boolean paramBoolean1, boolean paramBoolean2)
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("data", paramJSONObject);
      localJSONObject.put("keep", paramBoolean2);
      paramJSONObject = new StringBuilder();
      paramJSONObject.append("javascript:tbs_bridge.callbackFromNative('");
      paramJSONObject.append(paramString);
      paramJSONObject.append("','");
      paramJSONObject.append(localJSONObject);
      paramJSONObject.append("');");
      loadUrl(paramJSONObject.toString());
      return;
    }
    catch (JSONException paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public static boolean isSogouDomain(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    try
    {
      String str = new URL(paramString).getHost();
      paramString = str.toLowerCase();
      return (paramString.equalsIgnoreCase("sogou.com")) || (paramString.endsWith(".sogou.com"));
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("isSogouDomain url:");
      localStringBuilder.append(paramString);
      localStringBuilder.toString();
      localException.printStackTrace();
    }
    return false;
  }
  
  public void callJs(String paramString, Object... paramVarArgs)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("javascript:");
    localStringBuilder.append(paramString);
    localStringBuilder.append(".call(this");
    int j = paramVarArgs.length;
    int i = 0;
    while (i < j)
    {
      localStringBuilder.append(",");
      paramString = paramVarArgs[i];
      if ((paramString instanceof String))
      {
        localStringBuilder.append("'");
        localStringBuilder.append(((String)paramString).replaceAll("'", "'"));
        localStringBuilder.append("'");
      }
      else
      {
        localStringBuilder.append(paramString);
      }
      i += 1;
    }
    localStringBuilder.append(")");
    loadUrl(localStringBuilder.toString());
  }
  
  public void callWithPermission(String paramString, final PermissionCallback paramPermissionCallback)
  {
    if (paramPermissionCallback == null) {
      return;
    }
    if (checkPermission(paramString))
    {
      this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
      {
        public void run()
        {
          paramPermissionCallback.onResult(true);
        }
      });
      return;
    }
    this.jdField_a_of_type_AndroidOsHandler.obtainMessage(103, new Object[] { paramString, paramPermissionCallback }).sendToTarget();
  }
  
  public void cancelPageFullScreen()
  {
    this.jdField_a_of_type_AndroidOsHandler.obtainMessage(106).sendToTarget();
  }
  
  public void cancelPageNoTitle()
  {
    a("x5-page-mode", "has-title");
  }
  
  public void cancelScreenBacklight()
  {
    a("x5-screen-on");
  }
  
  public void cancelScreenOrientation()
  {
    a("x5-orientation");
  }
  
  public int checkJsApiDomain(String paramString)
  {
    String str2 = this.jdField_a_of_type_JavaLangString;
    String str1 = str2;
    if (TextUtils.isEmpty(str2)) {
      str1 = getUrl();
    }
    return QBUrlUtils.isJsApiDomain(str1, paramString);
  }
  
  public boolean checkPermission(String paramString)
  {
    if ("shortcut".equals(paramString))
    {
      String[] arrayOfString = new String[2];
      arrayOfString[0] = "app.html5.qq.com";
      arrayOfString[1] = "lightstore.html5.qq.com";
      String str = this.jdField_a_of_type_JavaLangString;
      paramString = str;
      if (TextUtils.isEmpty(str)) {
        paramString = getUrl();
      }
      paramString = UrlUtils.getHost(paramString);
      int j = arrayOfString.length;
      int i = 0;
      while (i < j)
      {
        if (arrayOfString[i].equalsIgnoreCase(paramString)) {
          return true;
        }
        i += 1;
      }
      return false;
    }
    return false;
  }
  
  public boolean checkQQDomain()
  {
    if (this.jdField_a_of_type_Boolean) {
      return true;
    }
    String str2 = this.jdField_a_of_type_JavaLangString;
    String str1 = str2;
    if (TextUtils.isEmpty(str2)) {
      str1 = getUrl();
    }
    return QBUrlUtils.isQQDomain(str1, false);
  }
  
  public void closeMidPage()
  {
    this.jdField_a_of_type_AndroidOsHandler.obtainMessage(108).sendToTarget();
  }
  
  public void closeMiniQB(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {
      return;
    }
    paramJSONObject = paramJSONObject.toString();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("closeMiniQB params : ");
    localStringBuilder.append(paramJSONObject);
    localStringBuilder.toString();
    if (TextUtils.isEmpty(paramJSONObject)) {
      return;
    }
    this.jdField_a_of_type_AndroidOsHandler.obtainMessage(110).sendToTarget();
  }
  
  public abstract Object doCreateEmbeddedGameFramework(ArrayList<IWebviewNotifyListener> paramArrayList);
  
  public void doEnterFullScreen()
  {
    doLoadUrl(a("x5-fullscreen", "true"));
  }
  
  public abstract void doExitMidPage();
  
  public abstract void doExitMiniQB(String paramString);
  
  public abstract String doGetMiniqbVersion();
  
  public abstract int doHideScreenAd();
  
  protected abstract void doLoadUrl(String paramString);
  
  public abstract void doReloadMeta(String paramString);
  
  public abstract void doShowMidPage(String paramString1, int paramInt, String paramString2, String paramString3);
  
  public abstract void doShowMiniQB(String paramString);
  
  public abstract void doShowNormalDialog(String paramString1, String paramString2);
  
  public abstract void doShowPermissionDialog(String paramString, PermissionCallback paramPermissionCallback);
  
  public abstract int doShowScreenAd(String paramString);
  
  public abstract void doStartFullscreenPlayer(int paramInt, Bundle paramBundle);
  
  public abstract void doStartLive(int paramInt, Bundle paramBundle);
  
  protected abstract void evaluateJavascript(String paramString, ValueCallback paramValueCallback);
  
  protected abstract void evaluateJavascriptInFrame(String paramString1, String paramString2);
  
  public void execJavaScriptInFrame(String paramString1, String paramString2)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("frameurl", paramString1);
    localBundle.putString("script", paramString2);
    this.jdField_a_of_type_AndroidOsHandler.obtainMessage(114, localBundle).sendToTarget();
  }
  
  public void fireEvent(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("javascript:tbs_bridge.fireEvent('");
    localStringBuilder.append(paramString1);
    localStringBuilder.append("','");
    localStringBuilder.append(paramString2);
    localStringBuilder.append("');");
    loadUrl(localStringBuilder.toString());
  }
  
  public Context getActivityCx()
  {
    return this.b;
  }
  
  public abstract String getAdInfo();
  
  public boolean getAutoPlayNextVideoFlag()
  {
    return false;
  }
  
  public Context getContext()
  {
    return this.jdField_a_of_type_AndroidContentContext;
  }
  
  public abstract String getDownloadFileInfo();
  
  public abstract String getHistoryUrl(int paramInt);
  
  public abstract Object getInternalWebView();
  
  public String getOriginUrl()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public abstract String getTitle();
  
  public abstract String getUrl();
  
  public abstract String getUserAgent();
  
  public Object getWebView()
  {
    return this.jdField_a_of_type_JavaLangObject;
  }
  
  public abstract float getWebViewScale();
  
  public void handlePluginTag(String paramString1, String paramString2) {}
  
  public int hideScreenAd(String paramString)
  {
    this.jdField_a_of_type_AndroidOsHandler.obtainMessage(116, paramString).sendToTarget();
    return 0;
  }
  
  public abstract void initAR();
  
  public int innerHeightAfterRequestPageFullScreen()
  {
    synchronized (Message.obtain(null, 102))
    {
      this.jdField_a_of_type_AndroidOsHandler.sendMessage(???);
      try
      {
        ???.wait(200L);
        return this.jdField_a_of_type_Int;
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;) {}
      }
      return -1;
    }
  }
  
  public boolean isInLightAppTemplate()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public abstract void isInstalled(String paramString1, String paramString2, CallbackRunnable paramCallbackRunnable);
  
  public boolean isSogouDomain()
  {
    String str2 = this.jdField_a_of_type_JavaLangString;
    String str1 = str2;
    if (TextUtils.isEmpty(str2)) {
      str1 = getUrl();
    }
    return isSogouDomain(str1);
  }
  
  public void loadUrl(String paramString)
  {
    this.jdField_a_of_type_AndroidOsHandler.obtainMessage(100, paramString).sendToTarget();
  }
  
  public void onWebViewDestroy()
  {
    IJsHelperDestroyCallback localIJsHelperDestroyCallback = this.jdField_a_of_type_ComTencentSmttJsApiExportIJsHelperDestroyCallback;
    if (localIJsHelperDestroyCallback != null) {
      localIJsHelperDestroyCallback.onWebViewDestroyed();
    }
  }
  
  public void openMidPage(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {
      return;
    }
    paramJSONObject = paramJSONObject.toString();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("openMidPage params : ");
    localStringBuilder.append(paramJSONObject);
    localStringBuilder.toString();
    if (TextUtils.isEmpty(paramJSONObject)) {
      return;
    }
    this.jdField_a_of_type_AndroidOsHandler.obtainMessage(109, new Object[] { paramJSONObject }).sendToTarget();
  }
  
  public void openMiniQB(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {
      return;
    }
    paramJSONObject = paramJSONObject.toString();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("openMiniQB params : ");
    localStringBuilder.append(paramJSONObject);
    localStringBuilder.toString();
    if (TextUtils.isEmpty(paramJSONObject)) {
      return;
    }
    this.jdField_a_of_type_AndroidOsHandler.obtainMessage(109, new Object[] { paramJSONObject }).sendToTarget();
  }
  
  public abstract int preloadScreenAd(String paramString);
  
  public void requestPageFullScreen(JSONObject paramJSONObject)
  {
    try
    {
      paramJSONObject = paramJSONObject.optString("hideNotification");
    }
    catch (Exception paramJSONObject)
    {
      String str;
      for (;;) {}
    }
    paramJSONObject = "false";
    str = getUrl();
    this.jdField_a_of_type_AndroidOsHandler.obtainMessage(105, new Object[] { str, Integer.valueOf(0), paramJSONObject, "true" }).sendToTarget();
  }
  
  public void requestPageNoTitle()
  {
    a("x5-page-mode", "no-title");
  }
  
  public void requestScreenBacklight()
  {
    a("x5-screen-on", "true");
  }
  
  public void requestScreenLandscape()
  {
    a("x5-orientation", "landscape");
  }
  
  public void requestScreenPortrait()
  {
    a("x5-orientation", "portrait");
  }
  
  public void runOnUiThread(Runnable paramRunnable)
  {
    this.jdField_a_of_type_AndroidOsHandler.post(paramRunnable);
  }
  
  public void runOnUiThreadDelay(Runnable paramRunnable, int paramInt)
  {
    this.jdField_a_of_type_AndroidOsHandler.postDelayed(paramRunnable, paramInt);
  }
  
  public abstract void savePageToDisk(String paramString, boolean paramBoolean, int paramInt, ValueCallback<String> paramValueCallback);
  
  public void sendFailJsCallback(String paramString, JSONObject paramJSONObject)
  {
    a(paramString, paramJSONObject, false, false);
  }
  
  public void sendJsCallback(String paramString, JSONObject paramJSONObject)
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("data", paramJSONObject);
      localJSONObject.put("keep", false);
      paramJSONObject = new StringBuilder();
      paramJSONObject.append("javascript:tbs_bridge.callbackFromNative('");
      paramJSONObject.append(paramString);
      paramJSONObject.append("','");
      paramJSONObject.append(localJSONObject);
      paramJSONObject.append("');");
      wvEvluateJavascript(paramJSONObject.toString());
      return;
    }
    catch (JSONException paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void sendJsCallbackInFrame(String paramString1, String paramString2, JSONObject paramJSONObject)
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("data", paramJSONObject);
      localJSONObject.put("keep", false);
      paramJSONObject = new StringBuilder();
      paramJSONObject.append("javascript:tbs_bridge.callbackFromNative('");
      paramJSONObject.append(paramString1);
      paramJSONObject.append("','");
      paramJSONObject.append(localJSONObject);
      paramJSONObject.append("');");
      wvEvluateJavascriptInFrame(paramString2, paramJSONObject.toString());
      return;
    }
    catch (JSONException paramString1)
    {
      paramString1.printStackTrace();
    }
  }
  
  public void sendSuccJsCallback(String paramString, JSONObject paramJSONObject)
  {
    a(paramString, paramJSONObject, true, false);
  }
  
  public void sendSuccJsCallback(String paramString, JSONObject paramJSONObject, boolean paramBoolean)
  {
    a(paramString, paramJSONObject, true, paramBoolean);
  }
  
  public void setActivityContext(Context paramContext)
  {
    this.b = paramContext;
  }
  
  public abstract String setAdInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8);
  
  public void setAutoPlayNextVideo(String paramString, boolean paramBoolean) {}
  
  public void setContext(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
  }
  
  public void setDestroyCallback(IJsHelperDestroyCallback paramIJsHelperDestroyCallback)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiExportIJsHelperDestroyCallback = paramIJsHelperDestroyCallback;
  }
  
  public void setInLightAppTemplate(boolean paramBoolean)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
  }
  
  public void setOriginUrl(String paramString)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
  }
  
  public abstract void setSniffVideoInfo(String paramString1, int paramInt, String paramString2, String paramString3);
  
  public abstract void setTbsARShareType(int paramInt);
  
  public void setWebView(Object paramObject)
  {
    this.jdField_a_of_type_JavaLangObject = paramObject;
  }
  
  public void setX5GamePlayerOrientation(String paramString) {}
  
  public void showNormalDialog(String paramString, JSONObject paramJSONObject)
  {
    paramJSONObject = paramJSONObject.optString("text");
    this.jdField_a_of_type_AndroidOsHandler.obtainMessage(104, new Object[] { paramJSONObject, paramString }).sendToTarget();
  }
  
  public int showScreenAd(String paramString)
  {
    this.jdField_a_of_type_AndroidOsHandler.obtainMessage(115, paramString).sendToTarget();
    return 0;
  }
  
  public void startFullscreenPlayer(String paramString, JSONObject paramJSONObject)
  {
    int i = paramJSONObject.optInt("type");
    if (i == 2)
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("callbackid", paramString);
      paramString = paramJSONObject.optJSONObject("style");
      if (paramString != null) {
        localBundle.putString("style", paramString.toString());
      }
      paramString = paramJSONObject.optString("url");
      if (!TextUtils.isEmpty(paramString)) {
        localBundle.putString("url", paramString);
      }
      paramString = paramJSONObject.optJSONObject("shareinfo");
      if (paramString != null) {
        localBundle.putString("shareinfo", paramString.toString());
      }
      this.jdField_a_of_type_AndroidOsHandler.obtainMessage(113, i, 0, localBundle).sendToTarget();
      return;
    }
    paramJSONObject = new JSONObject();
    try
    {
      paramJSONObject.put("ret", "failed");
      sendSuccJsCallback(paramString, paramJSONObject);
      return;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
  }
  
  public void startLive(String paramString, JSONObject paramJSONObject)
  {
    int i = paramJSONObject.optInt("type");
    Bundle localBundle = new Bundle();
    localBundle.putString("callbackid", paramString);
    if (i == 1)
    {
      paramString = paramJSONObject.optString("userId");
      Object localObject = paramJSONObject.optString("userSig");
      int j = paramJSONObject.optInt("appId", -1);
      int k = paramJSONObject.optInt("accountType", -1);
      int m = paramJSONObject.optInt("roomNum");
      boolean bool1 = paramJSONObject.optBoolean("preload", false);
      boolean bool2 = paramJSONObject.optBoolean("env", false);
      paramJSONObject = paramJSONObject.optString("role");
      localBundle.putString("userId", paramString);
      localBundle.putString("userSig", (String)localObject);
      localBundle.putInt("roomNum", m);
      localBundle.putBoolean("preload", bool1);
      localBundle.putBoolean("env", bool2);
      localBundle.putString("role", paramJSONObject);
      if (j != -1) {
        localBundle.putInt("appId", j);
      }
      if (k != -1) {
        localBundle.putInt("accountType", k);
      }
      paramJSONObject = getUrl();
      paramString = new StringBuilder();
      paramString.append("current url:");
      paramString.append(paramJSONObject);
      paramString.toString();
      if ((!TextUtils.isEmpty(paramJSONObject)) && (!bool1))
      {
        localObject = Apn.getActiveNetworkInfo(true);
        paramString = "none";
        if (localObject != null) {
          paramString = ((NetworkInfo)localObject).getTypeName();
        }
        localObject = new HashMap();
        ((HashMap)localObject).put("url", paramJSONObject);
        ((HashMap)localObject).put("network", paramString);
        paramString = new StringBuilder();
        paramString.append("upload ilive start: ");
        paramString.append(((HashMap)localObject).toString());
        paramString.toString();
        X5CoreBeaconUploader.getInstance(this.jdField_a_of_type_AndroidContentContext).upLoadToBeacon("MTT_ILIVE", (Map)localObject);
      }
      this.jdField_a_of_type_AndroidOsHandler.obtainMessage(112, i, 0, localBundle).sendToTarget();
      return;
    }
    paramJSONObject = new JSONObject();
    try
    {
      paramJSONObject.put("ret", "failed");
      sendSuccJsCallback(paramString, paramJSONObject);
      return;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
  }
  
  public abstract String ugJsApiExec(String paramString1, ValueCallback<JSONObject> paramValueCallback, JSONObject paramJSONObject, String paramString2, Map<String, Object> paramMap);
  
  public void wvEvluateJavascript(String paramString)
  {
    this.jdField_a_of_type_AndroidOsHandler.obtainMessage(111, paramString).sendToTarget();
  }
  
  public void wvEvluateJavascriptInFrame(String paramString1, String paramString2)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("frameurl", paramString1);
    localBundle.putString("script", paramString2);
    this.jdField_a_of_type_AndroidOsHandler.obtainMessage(114, localBundle).sendToTarget();
  }
  
  public static abstract interface CallbackRunnable
    extends Runnable
  {
    public abstract void setReturnValue(String paramString);
  }
  
  private class MyHandler
    extends Handler
  {
    public MyHandler()
    {
      super();
    }
    
    public void handleMessage(Message paramMessage)
    {
      Object localObject1;
      int i;
      Object localObject2;
      switch (paramMessage.what)
      {
      case 102: 
      default: 
        break;
      case 116: 
        localObject1 = (String)paramMessage.obj;
        i = OpenJsHelper.this.doHideScreenAd();
        localObject2 = new JSONObject();
      }
      try
      {
        ((JSONObject)localObject2).put("ret", i);
        OpenJsHelper.this.sendSuccJsCallback((String)localObject1, (JSONObject)localObject2);
        break label501;
        localObject1 = (String)paramMessage.obj;
        i = OpenJsHelper.this.doShowScreenAd((String)localObject1);
        localObject2 = new JSONObject();
      }
      catch (Exception localException1)
      {
        try
        {
          ((JSONObject)localObject2).put("ret", i);
          OpenJsHelper.this.sendSuccJsCallback((String)localObject1, (JSONObject)localObject2, true);
          break label501;
          localObject2 = (Bundle)paramMessage.obj;
          localObject1 = ((Bundle)localObject2).getString("frameurl");
          localObject2 = ((Bundle)localObject2).getString("script");
          if (!TextUtils.isEmpty((CharSequence)localObject2))
          {
            if (TextUtils.isEmpty((CharSequence)localObject1)) {
              return;
            }
            OpenJsHelper.this.evaluateJavascriptInFrame((String)localObject1, (String)localObject2);
          }
          else
          {
            return;
            localObject1 = (Bundle)paramMessage.obj;
            OpenJsHelper.this.doStartFullscreenPlayer(paramMessage.arg1, (Bundle)localObject1);
            break label501;
            localObject1 = (Bundle)paramMessage.obj;
            OpenJsHelper.this.doStartLive(paramMessage.arg1, (Bundle)localObject1);
            break label501;
            OpenJsHelper.this.evaluateJavascript((String)paramMessage.obj, null);
            break label501;
            localObject1 = (Object[])paramMessage.obj;
            OpenJsHelper.this.doExitMiniQB((String)localObject1[0]);
            break label501;
            localObject1 = (Object[])paramMessage.obj;
            OpenJsHelper.this.doShowMiniQB((String)localObject1[0]);
            break label501;
            OpenJsHelper.this.doExitMidPage();
            break label501;
            localObject1 = (Object[])paramMessage.obj;
            OpenJsHelper.this.doShowMidPage((String)localObject1[0], ((Integer)localObject1[1]).intValue(), (String)localObject1[2], (String)localObject1[3]);
            break label501;
            localObject1 = (Object[])paramMessage.obj;
            OpenJsHelper.this.doShowNormalDialog((String)localObject1[0], (String)localObject1[1]);
            break label501;
            localObject1 = (Object[])paramMessage.obj;
            OpenJsHelper.this.doShowPermissionDialog((String)localObject1[0], (OpenJsHelper.PermissionCallback)localObject1[1]);
            break label501;
            OpenJsHelper.this.doReloadMeta((String)paramMessage.obj);
            break label501;
            OpenJsHelper.this.doLoadUrl((String)paramMessage.obj);
          }
          label501:
          super.handleMessage(paramMessage);
          return;
          localException1 = localException1;
        }
        catch (Exception localException2)
        {
          for (;;) {}
        }
      }
    }
  }
  
  public static abstract interface PermissionCallback
  {
    public abstract String getPromptMessage();
    
    public abstract void onResult(boolean paramBoolean);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\OpenJsHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */