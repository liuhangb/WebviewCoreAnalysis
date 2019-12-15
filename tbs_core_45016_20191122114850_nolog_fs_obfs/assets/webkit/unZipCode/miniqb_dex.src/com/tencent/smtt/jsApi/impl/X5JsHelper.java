package com.tencent.smtt.jsApi.impl;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.ValueCallback;
import com.tencent.common.utils.StringUtils;
import com.tencent.smtt.jsApi.export.IJsHelperDestroyCallback;
import com.tencent.smtt.jsApi.export.IWebviewNotifyListener;
import com.tencent.smtt.jsApi.impl.utils.ReflectionUtils;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialog;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialogBuilder;
import dalvik.system.DexClassLoader;
import java.util.ArrayList;
import java.util.Map;
import org.json.JSONObject;

public class X5JsHelper
  extends OpenJsHelper
  implements IJsHelperDestroyCallback
{
  private DexClassLoader jdField_a_of_type_DalvikSystemDexClassLoader = null;
  Object jdField_a_of_type_JavaLangObject;
  ArrayList<TBSDialog> jdField_a_of_type_JavaUtilArrayList;
  
  public X5JsHelper(Object paramObject1, Object paramObject2, Context paramContext1, Context paramContext2)
  {
    this.jdField_a_of_type_JavaLangObject = paramObject1;
    this.jdField_a_of_type_DalvikSystemDexClassLoader = ((DexClassLoader)paramObject2);
    super.setWebView(paramObject1);
    super.setContext(paramContext1);
    super.setActivityContext(paramContext2);
  }
  
  public Object doCreateEmbeddedGameFramework(ArrayList<IWebviewNotifyListener> paramArrayList)
  {
    DexClassLoader localDexClassLoader = this.jdField_a_of_type_DalvikSystemDexClassLoader;
    if (localDexClassLoader != null)
    {
      Object localObject = this.jdField_a_of_type_JavaLangObject;
      return ReflectionUtils.invokeStaticMethod(localDexClassLoader, "com.tencent.tbs.tbsshell.partner.game.embedded.EmbeddedGameFramework", "createInstance", new Class[] { Object.class, ArrayList.class }, new Object[] { localObject, paramArrayList });
    }
    return null;
  }
  
  public void doExitMidPage()
  {
    DexClassLoader localDexClassLoader = this.jdField_a_of_type_DalvikSystemDexClassLoader;
    if (localDexClassLoader != null) {
      ReflectionUtils.invokeStaticMethod(localDexClassLoader, "com.tencent.tbs.tbsshell.partner.miniqb.TbsMiniQBProxy", "closeMiniQB", new Class[] { String.class }, new Object[] { "fromJsApiCall" });
    }
  }
  
  public void doExitMiniQB(String paramString)
  {
    paramString = this.jdField_a_of_type_DalvikSystemDexClassLoader;
    if (paramString != null) {
      ReflectionUtils.invokeStaticMethod(paramString, "com.tencent.tbs.tbsshell.partner.miniqb.TbsMiniQBProxy", "closeMiniQB", new Class[] { String.class }, new Object[] { "fromJsApiCall" });
    }
  }
  
  public String doGetMiniqbVersion()
  {
    Object localObject = this.jdField_a_of_type_DalvikSystemDexClassLoader;
    if (localObject != null)
    {
      localObject = ReflectionUtils.invokeStaticMethod((DexClassLoader)localObject, "com.tencent.tbs.tbsshell.partner.miniqb.TbsMiniQBProxy", "getMiniQbVersion", new Class[0], new Object[0]);
      if ((localObject instanceof String)) {
        return (String)localObject;
      }
    }
    return "";
  }
  
  public int doHideScreenAd()
  {
    Object localObject1 = this.jdField_a_of_type_DalvikSystemDexClassLoader;
    if (localObject1 != null)
    {
      if (this.jdField_a_of_type_JavaLangObject == null) {
        return -1;
      }
      Context localContext = getContext();
      localObject1 = ReflectionUtils.invokeStaticMethod((DexClassLoader)localObject1, "com.tencent.tbs.tbsshell.common.ad.TbsAdProxy", "getInstance", new Class[] { Context.class }, new Object[] { localContext });
      if (localObject1 != null)
      {
        localContext = getContext();
        Object localObject2 = this.jdField_a_of_type_JavaLangObject;
        localObject1 = ReflectionUtils.invokeMethod(localObject1, "com.tencent.tbs.tbsshell.common.ad.TbsAdProxy", "hideScreenAd", new Class[] { Context.class, Object.class }, new Object[] { localContext, localObject2 });
        if ((localObject1 instanceof Integer)) {
          return ((Integer)localObject1).intValue();
        }
      }
      return -1;
    }
    return -1;
  }
  
  protected void doLoadUrl(String paramString)
  {
    Object localObject = this.jdField_a_of_type_JavaLangObject;
    if (localObject != null)
    {
      if (this.jdField_a_of_type_DalvikSystemDexClassLoader == null) {
        return;
      }
      ReflectionUtils.invokeMethod(localObject, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "loadUrl", new Class[] { String.class }, new Object[] { paramString });
      return;
    }
  }
  
  public void doReloadMeta(String paramString)
  {
    Object localObject = this.jdField_a_of_type_JavaLangObject;
    if ((localObject != null) && (this.jdField_a_of_type_DalvikSystemDexClassLoader != null))
    {
      ReflectionUtils.invokeMethod(localObject, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "loadUrl", new Class[] { String.class }, new Object[] { paramString });
      ReflectionUtils.invokeMethod(this.jdField_a_of_type_JavaLangObject, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "reloadCustomMetaData", new Class[0], new Object[0]);
    }
  }
  
  public void doShowMidPage(String paramString1, int paramInt, String paramString2, String paramString3)
  {
    if (this.jdField_a_of_type_DalvikSystemDexClassLoader != null)
    {
      paramString2 = Boolean.TYPE;
      Class localClass = Integer.TYPE;
      boolean bool = StringUtils.isStringEqualsIgnoreCase(paramString3, "true");
      paramString3 = this.jdField_a_of_type_DalvikSystemDexClassLoader;
      Context localContext = getContext();
      ReflectionUtils.invokeStaticMethod(paramString3, "com.tencent.tbs.tbsshell.partner.miniqb.TbsMiniQBProxy", "handleAdsUrl", new Class[] { Context.class, Object.class, String.class, paramString2, String.class, localClass }, new Object[] { localContext, null, paramString1, Boolean.valueOf(bool), "fromJsApiCall", Integer.valueOf(paramInt) });
    }
  }
  
  public void doShowMiniQB(String paramString)
  {
    Object localObject1 = this.jdField_a_of_type_DalvikSystemDexClassLoader;
    int j;
    String str3;
    String str1;
    int i;
    if (localObject1 != null)
    {
      j = 0;
      localObject2 = getContext();
      localObject1 = ReflectionUtils.invokeStaticMethod((DexClassLoader)localObject1, "com.tencent.tbs.tbsshell.partner.miniqb.TbsMiniQBProxy", "startMiniQB", new Class[] { Context.class, String.class, String.class }, new Object[] { localObject2, "fromJsApiCall", paramString });
      if ((localObject1 == null) || (((localObject1 instanceof Integer)) && (((Integer)localObject1).intValue() < 0)))
      {
        str3 = "";
        str1 = "false";
        str2 = "true";
        localObject2 = str3;
        localObject1 = str1;
        i = j;
      }
    }
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      localObject2 = str3;
      localObject1 = str1;
      i = j;
      paramString = localJSONObject.optString("url");
      localObject2 = paramString;
      localObject1 = str1;
      i = j;
      j = localJSONObject.optInt("entryId");
      localObject2 = paramString;
      localObject1 = str1;
      i = j;
      str1 = localJSONObject.optString("fullscreen");
      localObject2 = paramString;
      localObject1 = str1;
      i = j;
      str3 = localJSONObject.optString("showNotification");
      i = j;
      localObject2 = str3;
      localObject1 = str1;
    }
    catch (Exception paramString)
    {
      for (;;)
      {
        paramString = (String)localObject2;
        localObject2 = str2;
      }
    }
    doShowMidPage(paramString, i, (String)localObject2, (String)localObject1);
  }
  
  public void doShowNormalDialog(String paramString1, final String paramString2)
  {
    if (getActivityCx() != null)
    {
      TBSDialogBuilder localTBSDialogBuilder = new TBSDialogBuilder(getActivityCx()).setOnKeyListener(new DialogInterface.OnKeyListener()
      {
        public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          if ((paramAnonymousInt == 4) && (paramAnonymousDialogInterface != null)) {
            paramAnonymousDialogInterface.dismiss();
          }
          return false;
        }
      });
      localTBSDialogBuilder.setMessage(paramString1, 2);
      localTBSDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          JSONObject localJSONObject = new JSONObject();
          try
          {
            localJSONObject.put("ret", "ok");
            X5JsHelper.this.sendSuccJsCallback(paramString2, localJSONObject);
            paramAnonymousDialogInterface.dismiss();
            return;
          }
          catch (Exception localException)
          {
            for (;;) {}
          }
        }
      });
      localTBSDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          JSONObject localJSONObject = new JSONObject();
          try
          {
            localJSONObject.put("ret", "cancel");
            X5JsHelper.this.sendSuccJsCallback(paramString2, localJSONObject);
            paramAnonymousDialogInterface.dismiss();
            return;
          }
          catch (Exception localException)
          {
            for (;;) {}
          }
        }
      });
      paramString1 = localTBSDialogBuilder.create();
      paramString1.setCanceledOnTouchOutside(false);
      paramString1.show();
    }
  }
  
  public void doShowPermissionDialog(String paramString, final OpenJsHelper.PermissionCallback paramPermissionCallback)
  {
    if (paramPermissionCallback == null) {
      return;
    }
    Object localObject = this.jdField_a_of_type_JavaUtilArrayList;
    if ((localObject != null) && (!((ArrayList)localObject).isEmpty())) {
      return;
    }
    localObject = paramPermissionCallback.getPromptMessage();
    if (TextUtils.isEmpty((CharSequence)localObject))
    {
      paramPermissionCallback = new StringBuilder();
      paramPermissionCallback.append("No message defined for permission:");
      paramPermissionCallback.append(paramString);
      paramPermissionCallback.toString();
      return;
    }
    if (getActivityCx() != null)
    {
      paramString = new TBSDialogBuilder(getActivityCx()).setOnKeyListener(new DialogInterface.OnKeyListener()
      {
        public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          if ((paramAnonymousInt == 4) && (paramAnonymousDialogInterface != null)) {
            paramAnonymousDialogInterface.dismiss();
          }
          return false;
        }
      });
      paramString.setMessage((String)localObject, 2);
      paramString.setPositiveButton("是", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          OpenJsHelper.PermissionCallback localPermissionCallback = paramPermissionCallback;
          if (localPermissionCallback != null) {
            localPermissionCallback.onResult(true);
          }
          paramAnonymousDialogInterface.dismiss();
          if (X5JsHelper.this.a != null) {
            X5JsHelper.this.a.remove(paramAnonymousDialogInterface);
          }
        }
      });
      paramString.setNegativeButton("否", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          OpenJsHelper.PermissionCallback localPermissionCallback = paramPermissionCallback;
          if (localPermissionCallback != null) {
            localPermissionCallback.onResult(false);
          }
          paramAnonymousDialogInterface.dismiss();
          if (X5JsHelper.this.a != null) {
            X5JsHelper.this.a.remove(paramAnonymousDialogInterface);
          }
        }
      });
      paramString = paramString.create();
      paramString.setCanceledOnTouchOutside(false);
      paramString.show();
      if (this.jdField_a_of_type_JavaUtilArrayList == null) {
        this.jdField_a_of_type_JavaUtilArrayList = new ArrayList();
      }
      this.jdField_a_of_type_JavaUtilArrayList.add(paramString);
    }
  }
  
  public int doShowScreenAd(String paramString)
  {
    Object localObject1 = this.jdField_a_of_type_DalvikSystemDexClassLoader;
    if (localObject1 != null)
    {
      if (this.jdField_a_of_type_JavaLangObject == null) {
        return -1;
      }
      Context localContext = getContext();
      localObject1 = ReflectionUtils.invokeStaticMethod((DexClassLoader)localObject1, "com.tencent.tbs.tbsshell.common.ad.TbsAdProxy", "getInstance", new Class[] { Context.class }, new Object[] { localContext });
      if (localObject1 != null)
      {
        localContext = getContext();
        Object localObject2 = this.jdField_a_of_type_JavaLangObject;
        paramString = ReflectionUtils.invokeMethod(localObject1, "com.tencent.tbs.tbsshell.common.ad.TbsAdProxy", "showScreenAd", new Class[] { Context.class, Object.class, String.class }, new Object[] { localContext, localObject2, paramString });
        if ((paramString instanceof Integer)) {
          return ((Integer)paramString).intValue();
        }
      }
      return -1;
    }
    return -1;
  }
  
  public void doStartFullscreenPlayer(int paramInt, Bundle paramBundle)
  {
    final String str;
    if (this.jdField_a_of_type_DalvikSystemDexClassLoader != null)
    {
      if (paramBundle == null) {
        return;
      }
      str = paramBundle.getString("callbackid");
      Object localObject1 = null;
      Object localObject2 = this.jdField_a_of_type_DalvikSystemDexClassLoader;
      Object localObject3 = getContext();
      localObject2 = ReflectionUtils.invokeStaticMethod((DexClassLoader)localObject2, "com.tencent.tbs.tbsshell.partner.fullscreenplayer.FullscreenPlayerPorxy", "getInstance", new Class[] { Context.class }, new Object[] { localObject3 });
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("doStartFullscreenPlayer target:");
      ((StringBuilder)localObject3).append(localObject2);
      ((StringBuilder)localObject3).toString();
      if (localObject2 != null)
      {
        localObject1 = Integer.TYPE;
        localObject3 = getContext();
        ValueCallback local12 = new ValueCallback()
        {
          public void onReceiveValue(Integer paramAnonymousInteger)
          {
            if (paramAnonymousInteger.intValue() == 0) {
              paramAnonymousInteger = "ok";
            } else {
              paramAnonymousInteger = "failed";
            }
            JSONObject localJSONObject = new JSONObject();
            try
            {
              localJSONObject.put("ret", paramAnonymousInteger);
              X5JsHelper.this.sendSuccJsCallback(str, localJSONObject);
              return;
            }
            catch (Exception paramAnonymousInteger)
            {
              for (;;) {}
            }
          }
        };
        localObject1 = ReflectionUtils.invokeMethod(localObject2, "com.tencent.tbs.tbsshell.partner.fullscreenplayer.FullscreenPlayerPorxy", "startFullscreenPlayer", new Class[] { Context.class, localObject1, Bundle.class, ValueCallback.class }, new Object[] { localObject3, Integer.valueOf(paramInt), paramBundle, local12 });
      }
      if (localObject1 == null) {
        paramBundle = new JSONObject();
      }
    }
    try
    {
      paramBundle.put("ret", "no such method: startFullscreenPlayer");
      sendSuccJsCallback(str, paramBundle);
      return;
      return;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
  }
  
  public void doStartLive(int paramInt, Bundle paramBundle)
  {
    final String str;
    if ((this.jdField_a_of_type_DalvikSystemDexClassLoader != null) && (paramBundle != null))
    {
      str = paramBundle.getString("callbackid");
      Class localClass = Integer.TYPE;
      DexClassLoader localDexClassLoader = this.jdField_a_of_type_DalvikSystemDexClassLoader;
      Context localContext = getContext();
      ValueCallback local11 = new ValueCallback()
      {
        public void onReceiveValue(Integer paramAnonymousInteger)
        {
          if (paramAnonymousInteger.intValue() == 0)
          {
            paramAnonymousInteger = "ok";
          }
          else
          {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("failed,code:");
            ((StringBuilder)localObject).append(paramAnonymousInteger);
            paramAnonymousInteger = ((StringBuilder)localObject).toString();
          }
          Object localObject = new JSONObject();
          try
          {
            ((JSONObject)localObject).put("ret", paramAnonymousInteger);
            X5JsHelper.this.sendSuccJsCallback(str, (JSONObject)localObject);
            return;
          }
          catch (Exception paramAnonymousInteger)
          {
            for (;;) {}
          }
        }
      };
      if (ReflectionUtils.invokeStaticMethod(localDexClassLoader, "com.tencent.tbs.tbsshell.partner.miniqb.TbsMiniQBProxy", "startMiniQBFramework", new Class[] { Context.class, String.class, localClass, Bundle.class, ValueCallback.class }, new Object[] { localContext, "fromJsApiCall", Integer.valueOf(paramInt), paramBundle, local11 }) == null) {
        paramBundle = new JSONObject();
      }
    }
    try
    {
      paramBundle.put("ret", "no such method: startMiniQBFramework");
      sendSuccJsCallback(str, paramBundle);
      return;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
  }
  
  protected void evaluateJavascript(String paramString, ValueCallback paramValueCallback)
  {
    Object localObject = this.jdField_a_of_type_JavaLangObject;
    if ((localObject != null) && (this.jdField_a_of_type_DalvikSystemDexClassLoader != null)) {
      ReflectionUtils.invokeMethod(localObject, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "evaluateJavascript", new Class[] { String.class, ValueCallback.class }, new Object[] { paramString, paramValueCallback });
    }
  }
  
  protected void evaluateJavascriptInFrame(String paramString1, String paramString2)
  {
    Object localObject = this.jdField_a_of_type_JavaLangObject;
    if (localObject != null)
    {
      if (this.jdField_a_of_type_DalvikSystemDexClassLoader == null) {
        return;
      }
      ReflectionUtils.invokeMethod(localObject, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "evaluateJavaScriptInFrame", new Class[] { String.class, String.class }, new Object[] { paramString1, paramString2 });
      return;
    }
  }
  
  public String getAdInfo()
  {
    Object localObject = this.jdField_a_of_type_DalvikSystemDexClassLoader;
    if (localObject != null)
    {
      localObject = ReflectionUtils.invokeStaticMethod((DexClassLoader)localObject, "com.tencent.tbs.core.partner.ad.AdInfoHolder", "getInstance", null, new Object[0]);
      if (localObject != null)
      {
        localObject = ReflectionUtils.invokeMethod(localObject, "com.tencent.tbs.core.partner.ad.AdInfoHolder", "getAdInfo", new Class[] { Integer.TYPE }, new Object[] { Integer.valueOf(0) });
        if ((localObject instanceof String)) {
          return (String)localObject;
        }
      }
    }
    return null;
  }
  
  public boolean getAutoPlayNextVideoFlag()
  {
    Object localObject = this.jdField_a_of_type_JavaLangObject;
    if (localObject != null)
    {
      if (this.jdField_a_of_type_DalvikSystemDexClassLoader == null) {
        return false;
      }
      localObject = ReflectionUtils.invokeMethod(localObject, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "getAutoPlayFlag", new Class[0], new Object[0]);
      if ((localObject != null) && ((localObject instanceof Boolean))) {
        return ((Boolean)localObject).booleanValue();
      }
      return false;
    }
    return false;
  }
  
  public String getDownloadFileInfo()
  {
    if (this.jdField_a_of_type_JavaLangObject == null) {
      return null;
    }
    try
    {
      if (Configuration.getInstance(getContext()).isDownloadInterceptPluginEnabled())
      {
        Object localObject1 = ReflectionUtils.invokeStaticMethod("com.tencent.tbs.tbsshell.partner.ug.TbsUgEngine", "getInstance", new Class[0], new Object[0]);
        if (localObject1 != null)
        {
          Object localObject3 = this.jdField_a_of_type_JavaLangObject;
          localObject1 = ReflectionUtils.invokeMethod(localObject1, "com.tencent.tbs.tbsshell.partner.ug.TbsUgEngine", "getDownloadFileInfo", new Class[] { Object.class }, new Object[] { localObject3 });
          if ((localObject1 instanceof String))
          {
            localObject1 = (String)localObject1;
            return (String)localObject1;
          }
          return null;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Object localObject2 = ReflectionUtils.invokeMethod(this.jdField_a_of_type_JavaLangObject, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "getDownloadFileInfo", new Class[0], new Object[0]);
      if ((localObject2 != null) && ((localObject2 instanceof String))) {
        return (String)localObject2;
      }
    }
    return null;
  }
  
  public String getHistoryUrl(int paramInt)
  {
    Object localObject = this.jdField_a_of_type_JavaLangObject;
    if (localObject != null)
    {
      if (this.jdField_a_of_type_DalvikSystemDexClassLoader == null) {
        return null;
      }
      localObject = ReflectionUtils.invokeMethod(localObject, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "getHistoryItem", new Class[] { Integer.TYPE }, new Object[] { Integer.valueOf(paramInt) });
      if (localObject != null)
      {
        localObject = ReflectionUtils.invokeMethod(localObject, "com.tencent.smtt.export.external.interfaces.IX5WebHistoryItem", "getUrl", new Class[0], new Object[0]);
        if ((localObject instanceof String)) {
          return (String)localObject;
        }
      }
      return null;
    }
    return null;
  }
  
  public Object getInternalWebView()
  {
    return this.jdField_a_of_type_JavaLangObject;
  }
  
  public String getTitle()
  {
    Object localObject = this.jdField_a_of_type_JavaLangObject;
    if (localObject != null)
    {
      if (this.jdField_a_of_type_DalvikSystemDexClassLoader == null) {
        return null;
      }
      localObject = ReflectionUtils.invokeMethod(localObject, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "getTitle", new Class[0], new Object[0]);
      if ((localObject != null) && ((localObject instanceof String))) {
        return (String)localObject;
      }
      return null;
    }
    return null;
  }
  
  /* Error */
  public String getUrl()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 42	com/tencent/smtt/jsApi/impl/X5JsHelper:jdField_a_of_type_DalvikSystemDexClassLoader	Ldalvik/system/DexClassLoader;
    //   4: ifnonnull +5 -> 9
    //   7: aconst_null
    //   8: areturn
    //   9: invokestatic 369	android/os/Looper:myLooper	()Landroid/os/Looper;
    //   12: invokestatic 372	android/os/Looper:getMainLooper	()Landroid/os/Looper;
    //   15: if_acmpne +41 -> 56
    //   18: aload_0
    //   19: getfield 44	com/tencent/smtt/jsApi/impl/X5JsHelper:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
    //   22: astore_1
    //   23: aload_1
    //   24: ifnull +24 -> 48
    //   27: aload_1
    //   28: ldc 122
    //   30: ldc_w 359
    //   33: iconst_0
    //   34: anewarray 67	java/lang/Class
    //   37: iconst_0
    //   38: anewarray 69	java/lang/Object
    //   41: invokestatic 114	com/tencent/smtt/jsApi/impl/utils/ReflectionUtils:invokeMethod	(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Class;[Ljava/lang/Object;)Ljava/lang/Object;
    //   44: astore_1
    //   45: goto +6 -> 51
    //   48: ldc 96
    //   50: astore_1
    //   51: aload_1
    //   52: checkcast 86	java/lang/String
    //   55: areturn
    //   56: aload_0
    //   57: monitorenter
    //   58: new 283	android/os/Bundle
    //   61: dup
    //   62: invokespecial 373	android/os/Bundle:<init>	()V
    //   65: astore_1
    //   66: aload_0
    //   67: getfield 376	com/tencent/smtt/jsApi/impl/X5JsHelper:jdField_a_of_type_AndroidOsHandler	Landroid/os/Handler;
    //   70: new 8	com/tencent/smtt/jsApi/impl/X5JsHelper$1
    //   73: dup
    //   74: aload_0
    //   75: aload_1
    //   76: invokespecial 379	com/tencent/smtt/jsApi/impl/X5JsHelper$1:<init>	(Lcom/tencent/smtt/jsApi/impl/X5JsHelper;Landroid/os/Bundle;)V
    //   79: invokevirtual 385	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   82: pop
    //   83: aload_0
    //   84: ldc2_w 386
    //   87: invokevirtual 391	java/lang/Object:wait	(J)V
    //   90: aload_1
    //   91: ldc -90
    //   93: invokevirtual 286	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   96: astore_1
    //   97: aload_1
    //   98: ifnull +19 -> 117
    //   101: goto +3 -> 104
    //   104: aload_0
    //   105: monitorexit
    //   106: aload_1
    //   107: areturn
    //   108: astore_1
    //   109: aload_0
    //   110: monitorexit
    //   111: aload_1
    //   112: athrow
    //   113: astore_2
    //   114: goto -24 -> 90
    //   117: ldc 96
    //   119: astore_1
    //   120: goto -16 -> 104
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	123	0	this	X5JsHelper
    //   22	85	1	localObject1	Object
    //   108	4	1	localObject2	Object
    //   119	1	1	str	String
    //   113	1	2	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   58	83	108	finally
    //   83	90	108	finally
    //   90	97	108	finally
    //   104	106	108	finally
    //   109	111	108	finally
    //   83	90	113	java/lang/Exception
  }
  
  public String getUserAgent()
  {
    Object localObject = this.jdField_a_of_type_JavaLangObject;
    if (localObject != null)
    {
      if (this.jdField_a_of_type_DalvikSystemDexClassLoader == null) {
        return null;
      }
      localObject = ReflectionUtils.invokeMethod(localObject, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "getSettings", new Class[0], new Object[0]);
      if (localObject != null)
      {
        localObject = ReflectionUtils.invokeMethod(localObject, "com.tencent.tbs.core.webkit.tencent.TencentWebSettingsProxy", "getUserAgent", new Class[0], new Object[0]);
        if ((localObject != null) && ((localObject instanceof String))) {
          return (String)localObject;
        }
        return null;
      }
      return null;
    }
    return null;
  }
  
  public float getWebViewScale()
  {
    Object localObject = this.jdField_a_of_type_JavaLangObject;
    if ((localObject != null) && (this.jdField_a_of_type_DalvikSystemDexClassLoader != null))
    {
      localObject = ReflectionUtils.invokeMethod(localObject, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "getScale", new Class[0], new Object[0]);
      if ((localObject != null) && ((localObject instanceof Float))) {
        return ((Float)localObject).floatValue();
      }
      return 1.0F;
    }
    return 1.0F;
  }
  
  public void handlePluginTag(final String paramString1, final String paramString2)
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        if ((X5JsHelper.this.a != null) && (X5JsHelper.a(X5JsHelper.this) != null))
        {
          Object localObject = ReflectionUtils.invokeMethod(X5JsHelper.this.a, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "getWebViewClientExtension", new Class[0], new Object[0]);
          if (localObject != null)
          {
            String str1 = paramString1;
            String str2 = paramString2;
            ReflectionUtils.invokeMethod(localObject, "com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension", "handlePluginTag", new Class[] { String.class, String.class, Boolean.class, String.class }, new Object[] { str1, str2, Boolean.valueOf(false), null });
          }
        }
      }
    });
  }
  
  public void initAR()
  {
    DexClassLoader localDexClassLoader = this.jdField_a_of_type_DalvikSystemDexClassLoader;
    if (localDexClassLoader != null) {
      ReflectionUtils.invokeStaticMethod(localDexClassLoader, "com.tencent.tbs.tbsshell.TBSShell", "initAR", new Class[] { Context.class, String.class }, new Object[] { null, null });
    }
  }
  
  public void isInstalled(String paramString1, String paramString2, final OpenJsHelper.CallbackRunnable paramCallbackRunnable)
  {
    paramCallbackRunnable = new Handler.Callback()
    {
      public boolean handleMessage(Message paramAnonymousMessage)
      {
        paramCallbackRunnable.setReturnValue(paramAnonymousMessage.getData().getString("msg"));
        X5JsHelper.this.a.post(paramCallbackRunnable);
        return true;
      }
    };
    ReflectionUtils.invokeMethod(this.jdField_a_of_type_JavaLangObject, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "isInstalled", new Class[] { String.class, String.class, Handler.Callback.class }, new Object[] { paramString1, paramString2, paramCallbackRunnable });
  }
  
  public void onWebViewDestroy()
  {
    this.jdField_a_of_type_JavaLangObject = null;
    super.onWebViewDestroy();
  }
  
  public void onWebViewDestroyed()
  {
    this.jdField_a_of_type_JavaLangObject = null;
    super.onWebViewDestroy();
  }
  
  public int preloadScreenAd(String paramString)
  {
    if (this.jdField_a_of_type_DalvikSystemDexClassLoader != null)
    {
      if (TextUtils.isEmpty(paramString)) {
        return -1;
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("preloadScreenAd:");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).toString();
      localObject = this.jdField_a_of_type_DalvikSystemDexClassLoader;
      Context localContext = getContext();
      localObject = ReflectionUtils.invokeStaticMethod((DexClassLoader)localObject, "com.tencent.tbs.tbsshell.common.ad.TbsAdProxy", "getInstance", new Class[] { Context.class }, new Object[] { localContext });
      if (localObject != null)
      {
        localContext = getContext();
        paramString = ReflectionUtils.invokeMethod(localObject, "com.tencent.tbs.tbsshell.common.ad.TbsAdProxy", "preLoadScreenAd", new Class[] { Context.class, String.class }, new Object[] { localContext, paramString });
        if ((paramString instanceof Integer)) {
          return ((Integer)paramString).intValue();
        }
      }
      return -1;
    }
    return -1;
  }
  
  public void savePageToDisk(String paramString, boolean paramBoolean, int paramInt, ValueCallback<String> paramValueCallback)
  {
    Object localObject = this.jdField_a_of_type_JavaLangObject;
    if ((localObject != null) && (this.jdField_a_of_type_DalvikSystemDexClassLoader != null)) {
      ReflectionUtils.invokeMethod(localObject, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "saveWebArchive", new Class[] { String.class, Boolean.TYPE, Boolean.TYPE, Integer.TYPE, ValueCallback.class }, new Object[] { paramString, Boolean.valueOf(false), Boolean.valueOf(paramBoolean), Integer.valueOf(paramInt), paramValueCallback });
    }
  }
  
  public String setAdInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    Object localObject = this.jdField_a_of_type_DalvikSystemDexClassLoader;
    if (localObject != null)
    {
      localObject = ReflectionUtils.invokeStaticMethod((DexClassLoader)localObject, "com.tencent.tbs.core.partner.ad.AdInfoHolder", "getInstance", null, new Object[0]);
      if (localObject != null)
      {
        ReflectionUtils.invokeMethod(localObject, "com.tencent.tbs.core.partner.ad.AdInfoHolder", "setAdInfoUnit", new Class[] { String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class }, new Object[] { paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8 });
        return "succeed";
      }
    }
    return "failed";
  }
  
  public void setAutoPlayNextVideo(String paramString, boolean paramBoolean)
  {
    if (this.jdField_a_of_type_DalvikSystemDexClassLoader != null) {
      ReflectionUtils.invokeMethod(this.jdField_a_of_type_JavaLangObject, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "setAutoPlayNextVideo", new Class[] { String.class, Boolean.TYPE }, new Object[] { paramString, Boolean.valueOf(paramBoolean) });
    }
  }
  
  public void setSniffVideoInfo(String paramString1, int paramInt, String paramString2, String paramString3)
  {
    Object localObject = this.jdField_a_of_type_JavaLangObject;
    if (localObject != null)
    {
      if (this.jdField_a_of_type_DalvikSystemDexClassLoader == null) {
        return;
      }
      Integer localInteger = new Integer(paramInt);
      ReflectionUtils.invokeMethod(localObject, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "setSniffVideoInfo", new Class[] { String.class, Integer.class, String.class, String.class }, new Object[] { paramString1, localInteger, paramString2, paramString3 });
      return;
    }
  }
  
  public void setTbsARShareType(final int paramInt)
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        if ((X5JsHelper.this.a != null) && (X5JsHelper.a(X5JsHelper.this) != null))
        {
          Object localObject = ReflectionUtils.invokeMethod(X5JsHelper.this.a, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "getSettingsExtension", new Class[0], new Object[0]);
          if (localObject != null)
          {
            Class localClass = Integer.TYPE;
            int i = paramInt;
            ReflectionUtils.invokeMethod(localObject, "com.tencent.smtt.export.external.extension.interfaces.IX5WebSettingsExtension", "setTbsARShareType", new Class[] { localClass }, new Object[] { Integer.valueOf(i) });
          }
        }
      }
    });
  }
  
  public String ugJsApiExec(String paramString1, ValueCallback<JSONObject> paramValueCallback, JSONObject paramJSONObject, String paramString2, Map<String, Object> paramMap)
  {
    if (this.jdField_a_of_type_JavaLangObject == null) {
      return null;
    }
    try
    {
      if (Configuration.getInstance(getContext()).isDownloadInterceptPluginEnabled())
      {
        Object localObject = ReflectionUtils.invokeStaticMethod("com.tencent.tbs.tbsshell.partner.ug.TbsUgEngine", "getInstance", new Class[0], new Object[0]);
        if (localObject != null)
        {
          paramString1 = ReflectionUtils.invokeMethod(localObject, "com.tencent.tbs.tbsshell.partner.ug.TbsUgEngine", "ugJsApiExec", new Class[] { String.class, ValueCallback.class, JSONObject.class, String.class, Map.class }, new Object[] { paramString1, paramValueCallback, paramJSONObject, paramString2, paramMap });
          if ((paramString1 instanceof String))
          {
            paramString1 = (String)paramString1;
            return paramString1;
          }
        }
      }
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\X5JsHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */