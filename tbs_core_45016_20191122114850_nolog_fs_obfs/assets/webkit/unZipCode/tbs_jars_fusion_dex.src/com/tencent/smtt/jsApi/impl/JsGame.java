package com.tencent.smtt.jsApi.impl;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.ValueCallback;
import com.tencent.smtt.jsApi.export.IOpenJsApis;
import java.lang.reflect.Method;
import org.json.JSONException;
import org.json.JSONObject;

public class JsGame
  implements IOpenJsApis
{
  public static final String TAG = "H5GamePlayerJsApi";
  private Handler jdField_a_of_type_AndroidOsHandler = null;
  OpenJsHelper jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper = null;
  Object jdField_a_of_type_JavaLangObject = null;
  
  public JsGame(OpenJsHelper paramOpenJsHelper)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper = paramOpenJsHelper;
    this.jdField_a_of_type_AndroidOsHandler = new Handler(Looper.getMainLooper());
  }
  
  private String a()
  {
    if (!a("gameplayer.version")) {
      return "";
    }
    try
    {
      Object localObject = b(this.jdField_a_of_type_JavaLangObject, "getValue", new Class[] { String.class }, new Object[] { "version" });
      if (localObject != null)
      {
        localObject = localObject.toString();
        return (String)localObject;
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    return "";
  }
  
  private JSONObject a(Throwable paramThrowable)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("result", -2);
      localJSONObject.put("msg", paramThrowable.getMessage());
      return localJSONObject;
    }
    catch (JSONException paramThrowable)
    {
      paramThrowable.printStackTrace();
    }
    return localJSONObject;
  }
  
  private boolean a(String paramString)
  {
    OpenJsHelper localOpenJsHelper = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
    if (localOpenJsHelper == null) {
      return false;
    }
    int i = localOpenJsHelper.checkJsApiDomain(paramString);
    boolean bool = true;
    if (i != 1)
    {
      if (this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.checkQQDomain()) {
        return true;
      }
      bool = false;
    }
    return bool;
  }
  
  private static Object b(Object paramObject, String paramString, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
    throws Throwable
  {
    try
    {
      Class localClass = paramObject.getClass();
      if (Build.VERSION.SDK_INT > 10) {
        paramString = localClass.getMethod(paramString, paramArrayOfClass);
      } else {
        paramString = localClass.getDeclaredMethod(paramString, paramArrayOfClass);
      }
      paramString.setAccessible(true);
      paramArrayOfClass = paramVarArgs;
      if (paramVarArgs.length == 0) {
        paramArrayOfClass = null;
      }
      paramObject = paramString.invoke(paramObject, paramArrayOfClass);
      return paramObject;
    }
    catch (Throwable paramObject)
    {
      ((Throwable)paramObject).printStackTrace();
      throw ((Throwable)paramObject);
    }
  }
  
  public String cacheExist(final JSONObject paramJSONObject, final String paramString)
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("H5GamePlayerJsApi.cacheExist:");
        ((StringBuilder)localObject).append(paramJSONObject.toString());
        ((StringBuilder)localObject).toString();
        localObject = JsGame.this.a;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("javascript:var exit = GameEngine.cacheExist('");
        localStringBuilder.append(paramJSONObject.optString("engineName"));
        localStringBuilder.append("','");
        localStringBuilder.append(paramJSONObject.optString("dir"));
        localStringBuilder.append("');var rsp = {\"exit\":exit};tbs_bridge.nativeExec('gameplayer','cacheExistCallBack','");
        localStringBuilder.append(paramString);
        localStringBuilder.append("',JSON.stringify(rsp))");
        ((OpenJsHelper)localObject).loadUrl(localStringBuilder.toString());
      }
    });
    return "";
  }
  
  public String delCache(final JSONObject paramJSONObject)
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("H5GamePlayerJsApi.delCache:");
        ((StringBuilder)localObject).append(paramJSONObject.toString());
        ((StringBuilder)localObject).toString();
        localObject = JsGame.this.a;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("javascript:GameEngine.deleteCache('");
        localStringBuilder.append(paramJSONObject.optString("engineName"));
        localStringBuilder.append("','");
        localStringBuilder.append(paramJSONObject.optString("dir"));
        localStringBuilder.append("')");
        ((OpenJsHelper)localObject).loadUrl(localStringBuilder.toString());
      }
    });
    return "";
  }
  
  public void destroy() {}
  
  public String exec(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.setOriginUrl(paramString3);
    paramString3 = new StringBuilder();
    paramString3.append("-H5GamePlayerJsApi.exec-");
    paramString3.append(paramString1);
    paramString3.append(" ");
    paramString3.append(paramString2);
    paramString3.append(" ");
    paramString3.append(paramJSONObject);
    paramString3.toString();
    if (this.jdField_a_of_type_JavaLangObject == null) {
      return null;
    }
    if ("delCache".equals(paramString1)) {
      return delCache(paramJSONObject);
    }
    if ("cacheExist".equals(paramString1)) {
      return cacheExist(paramJSONObject, paramString2);
    }
    if ("login".equals(paramString1)) {
      return login(paramJSONObject, paramString2);
    }
    if ("logout".equals(paramString1)) {
      return logout(paramJSONObject, paramString2);
    }
    if ("refreshToken".equals(paramString1)) {
      return refreshToken(paramJSONObject, paramString2);
    }
    if ("pay".equals(paramString1)) {
      return pay(paramJSONObject, paramString2);
    }
    if ("getGameFriends".equals(paramString1)) {
      return getGameFriends(paramJSONObject, paramString2);
    }
    if ("share".equals(paramString1)) {
      return share(paramJSONObject, paramString2);
    }
    if ("sendToDesktop".equals(paramString1)) {
      return sendToDesktop(paramJSONObject, paramString2);
    }
    if ("getAvailableLoginType".equals(paramString1)) {
      return getAvailableLoginType(paramJSONObject, paramString2);
    }
    if ("getUserInfo".equals(paramString1)) {
      return getUserInfo(paramJSONObject, paramString2);
    }
    if ("version".equals(paramString1)) {
      return a();
    }
    return null;
  }
  
  public String getAvailableLoginType(final JSONObject paramJSONObject, final String paramString)
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        if (!JsGame.a(JsGame.this, "gameplayer.getAvailableLoginType")) {
          return;
        }
        Object localObject1 = new Bundle();
        try
        {
          paramJSONObject.put("callback", "getAvailableLoginTypeCallBack");
          ((Bundle)localObject1).putString("jsonStr", paramJSONObject.toString());
          localObject1 = new ValueCallback()
          {
            public void onReceiveValue(JSONObject paramAnonymous2JSONObject)
            {
              JsGame.5.this.jdField_a_of_type_ComTencentSmttJsApiImplJsGame.jsCallBack(paramAnonymous2JSONObject, JsGame.5.this.jdField_a_of_type_JavaLangString);
            }
          };
          localObject2 = JsGame.this.a;
          JSONObject localJSONObject = paramJSONObject;
          JsGame.a(localObject2, "doGetAvailableLoginType", new Class[] { JSONObject.class, ValueCallback.class }, new Object[] { localJSONObject, localObject1 });
          return;
        }
        catch (Throwable localThrowable)
        {
          Object localObject2 = JsGame.this;
          ((JsGame)localObject2).jsCallBack(JsGame.a((JsGame)localObject2, localThrowable), paramString);
          localThrowable.printStackTrace();
        }
      }
    });
    return "";
  }
  
  public String getGameFriends(final JSONObject paramJSONObject, final String paramString)
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        if (!JsGame.a(JsGame.this, "gameplayer.getGameFriends")) {
          return;
        }
        try
        {
          paramJSONObject.put("callback", "getGameFriendsCallBack");
          ValueCallback local1 = new ValueCallback()
          {
            public void onReceiveValue(JSONObject paramAnonymous2JSONObject)
            {
              JsGame.8.this.jdField_a_of_type_ComTencentSmttJsApiImplJsGame.jsCallBack(paramAnonymous2JSONObject, JsGame.8.this.jdField_a_of_type_JavaLangString);
            }
          };
          localObject = JsGame.this.a;
          JSONObject localJSONObject = paramJSONObject;
          JsGame.a(localObject, "doGetFriendIds", new Class[] { JSONObject.class, ValueCallback.class }, new Object[] { localJSONObject, local1 });
          return;
        }
        catch (Throwable localThrowable)
        {
          Object localObject = JsGame.this;
          ((JsGame)localObject).jsCallBack(JsGame.a((JsGame)localObject, localThrowable), paramString);
          localThrowable.printStackTrace();
        }
      }
    });
    return "";
  }
  
  public String getUserInfo(final JSONObject paramJSONObject, final String paramString)
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        if (!JsGame.a(JsGame.this, "gameplayer.getUserInfo")) {
          return;
        }
        Object localObject1 = new Bundle();
        try
        {
          paramJSONObject.put("callback", "getUserInfoCallBack");
          ((Bundle)localObject1).putString("jsonStr", paramJSONObject.toString());
          localObject1 = new ValueCallback()
          {
            public void onReceiveValue(JSONObject paramAnonymous2JSONObject)
            {
              JsGame.9.this.jdField_a_of_type_ComTencentSmttJsApiImplJsGame.jsCallBack(paramAnonymous2JSONObject, JsGame.9.this.jdField_a_of_type_JavaLangString);
            }
          };
          localObject2 = JsGame.this.a;
          JSONObject localJSONObject = paramJSONObject;
          JsGame.a(localObject2, "doGetUserInfo", new Class[] { JSONObject.class, ValueCallback.class }, new Object[] { localJSONObject, localObject1 });
          return;
        }
        catch (Throwable localThrowable)
        {
          Object localObject2 = JsGame.this;
          ((JsGame)localObject2).jsCallBack(JsGame.a((JsGame)localObject2, localThrowable), paramString);
          localThrowable.printStackTrace();
        }
      }
    });
    return "";
  }
  
  public String jsCallBack(JSONObject paramJSONObject, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("JsGame.jsCallBack:");
    localStringBuilder.append(paramJSONObject.toString());
    localStringBuilder.toString();
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.sendSuccJsCallback(paramString, paramJSONObject);
    return null;
  }
  
  public String login(final JSONObject paramJSONObject, final String paramString)
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        if (!JsGame.a(JsGame.this, "gameplayer.login")) {
          return;
        }
        Object localObject1 = new Bundle();
        try
        {
          paramJSONObject.put("callback", "loginCallBack");
          ((Bundle)localObject1).putString("jsonStr", paramJSONObject.toString());
          localObject1 = new ValueCallback()
          {
            public void onReceiveValue(JSONObject paramAnonymous2JSONObject)
            {
              JsGame.3.this.jdField_a_of_type_ComTencentSmttJsApiImplJsGame.jsCallBack(paramAnonymous2JSONObject, JsGame.3.this.jdField_a_of_type_JavaLangString);
            }
          };
          localObject2 = JsGame.this.a;
          JSONObject localJSONObject = paramJSONObject;
          JsGame.a(localObject2, "doLogin", new Class[] { JSONObject.class, ValueCallback.class }, new Object[] { localJSONObject, localObject1 });
          return;
        }
        catch (Throwable localThrowable)
        {
          Object localObject2 = JsGame.this;
          ((JsGame)localObject2).jsCallBack(JsGame.a((JsGame)localObject2, localThrowable), paramString);
          localThrowable.printStackTrace();
        }
      }
    });
    return "";
  }
  
  public String logout(final JSONObject paramJSONObject, final String paramString)
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        if (!JsGame.a(JsGame.this, "gameplayer.logout")) {
          return;
        }
        Object localObject1 = new Bundle();
        try
        {
          paramJSONObject.put("callback", "logoutCallBack");
          ((Bundle)localObject1).putString("jsonStr", paramJSONObject.toString());
          localObject1 = new ValueCallback()
          {
            public void onReceiveValue(JSONObject paramAnonymous2JSONObject)
            {
              JsGame.4.this.jdField_a_of_type_ComTencentSmttJsApiImplJsGame.jsCallBack(paramAnonymous2JSONObject, JsGame.4.this.jdField_a_of_type_JavaLangString);
            }
          };
          localObject2 = JsGame.this.a;
          JSONObject localJSONObject = paramJSONObject;
          JsGame.a(localObject2, "doLogout", new Class[] { JSONObject.class, ValueCallback.class }, new Object[] { localJSONObject, localObject1 });
          return;
        }
        catch (Throwable localThrowable)
        {
          Object localObject2 = JsGame.this;
          ((JsGame)localObject2).jsCallBack(JsGame.a((JsGame)localObject2, localThrowable), paramString);
          localThrowable.printStackTrace();
        }
      }
    });
    return "";
  }
  
  public String pay(final JSONObject paramJSONObject, final String paramString)
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        if (!JsGame.a(JsGame.this, "gameplayer.pay")) {
          return;
        }
        Object localObject1 = new Bundle();
        try
        {
          paramJSONObject.put("callback", "payCallBack");
          ((Bundle)localObject1).putString("jsonStr", paramJSONObject.toString());
          localObject1 = new ValueCallback()
          {
            public void onReceiveValue(JSONObject paramAnonymous2JSONObject)
            {
              JsGame.7.this.jdField_a_of_type_ComTencentSmttJsApiImplJsGame.jsCallBack(paramAnonymous2JSONObject, JsGame.7.this.jdField_a_of_type_JavaLangString);
            }
          };
          localObject2 = JsGame.this.a;
          JSONObject localJSONObject = paramJSONObject;
          JsGame.a(localObject2, "doPay", new Class[] { JSONObject.class, ValueCallback.class }, new Object[] { localJSONObject, localObject1 });
          return;
        }
        catch (Throwable localThrowable)
        {
          Object localObject2 = JsGame.this;
          ((JsGame)localObject2).jsCallBack(JsGame.a((JsGame)localObject2, localThrowable), paramString);
          localThrowable.printStackTrace();
        }
      }
    });
    return "";
  }
  
  public String refreshToken(final JSONObject paramJSONObject, final String paramString)
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        if (!JsGame.a(JsGame.this, "gameplayer.refreshToken")) {
          return;
        }
        Object localObject1 = new Bundle();
        try
        {
          paramJSONObject.put("callback", "refreshTokenCallBack");
          ((Bundle)localObject1).putString("jsonStr", paramJSONObject.toString());
          localObject1 = new ValueCallback()
          {
            public void onReceiveValue(JSONObject paramAnonymous2JSONObject)
            {
              JsGame.6.this.jdField_a_of_type_ComTencentSmttJsApiImplJsGame.jsCallBack(paramAnonymous2JSONObject, JsGame.6.this.jdField_a_of_type_JavaLangString);
            }
          };
          localObject2 = JsGame.this.a;
          JSONObject localJSONObject = paramJSONObject;
          JsGame.a(localObject2, "doRefreshToken", new Class[] { JSONObject.class, ValueCallback.class }, new Object[] { localJSONObject, localObject1 });
          return;
        }
        catch (Throwable localThrowable)
        {
          Object localObject2 = JsGame.this;
          ((JsGame)localObject2).jsCallBack(JsGame.a((JsGame)localObject2, localThrowable), paramString);
          localThrowable.printStackTrace();
        }
      }
    });
    return "";
  }
  
  public String sendToDesktop(final JSONObject paramJSONObject, final String paramString)
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        Object localObject1 = new Bundle();
        try
        {
          paramJSONObject.put("callback", "sendToDesktopCallBack");
          ((Bundle)localObject1).putString("jsonStr", paramJSONObject.toString());
          localObject1 = new ValueCallback()
          {
            public void onReceiveValue(JSONObject paramAnonymous2JSONObject)
            {
              JsGame.11.this.jdField_a_of_type_ComTencentSmttJsApiImplJsGame.jsCallBack(paramAnonymous2JSONObject, JsGame.11.this.jdField_a_of_type_JavaLangString);
            }
          };
          localObject2 = JsGame.this.a;
          JSONObject localJSONObject = paramJSONObject;
          JsGame.a(localObject2, "doCreateShortCut", new Class[] { JSONObject.class, ValueCallback.class }, new Object[] { localJSONObject, localObject1 });
          return;
        }
        catch (Throwable localThrowable)
        {
          Object localObject2 = JsGame.this;
          ((JsGame)localObject2).jsCallBack(JsGame.a((JsGame)localObject2, localThrowable), paramString);
          localThrowable.printStackTrace();
        }
      }
    });
    return "";
  }
  
  public void setJsApiImpl(Object paramObject)
  {
    this.jdField_a_of_type_JavaLangObject = paramObject;
  }
  
  public String share(final JSONObject paramJSONObject, final String paramString)
  {
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        Object localObject1 = new Bundle();
        try
        {
          paramJSONObject.put("callback", "shareCallBack");
          ((Bundle)localObject1).putString("jsonStr", paramJSONObject.toString());
          localObject1 = new ValueCallback()
          {
            public void onReceiveValue(JSONObject paramAnonymous2JSONObject)
            {
              JsGame.10.this.jdField_a_of_type_ComTencentSmttJsApiImplJsGame.jsCallBack(paramAnonymous2JSONObject, JsGame.10.this.jdField_a_of_type_JavaLangString);
            }
          };
          localObject2 = JsGame.this.a;
          JSONObject localJSONObject = paramJSONObject;
          JsGame.a(localObject2, "doShare", new Class[] { JSONObject.class, ValueCallback.class }, new Object[] { localJSONObject, localObject1 });
          return;
        }
        catch (Throwable localThrowable)
        {
          Object localObject2 = JsGame.this;
          ((JsGame)localObject2).jsCallBack(JsGame.a((JsGame)localObject2, localThrowable), paramString);
          localThrowable.printStackTrace();
        }
      }
    });
    return "";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\JsGame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */