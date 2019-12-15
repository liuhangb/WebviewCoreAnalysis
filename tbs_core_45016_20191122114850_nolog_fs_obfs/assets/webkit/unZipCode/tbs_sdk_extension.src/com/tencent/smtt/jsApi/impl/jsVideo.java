package com.tencent.smtt.jsApi.impl;

import android.os.Bundle;
import com.tencent.smtt.jsApi.export.IOpenJsApis;
import com.tencent.tbs.common.sniffer.SniffObserver;
import com.tencent.tbs.common.sniffer.VideoSniffer;
import dalvik.system.DexClassLoader;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class jsVideo
  implements IOpenJsApis
{
  OpenJsHelper jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper = null;
  Object jdField_a_of_type_JavaLangObject = null;
  
  public jsVideo(OpenJsHelper paramOpenJsHelper, Object paramObject)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper = paramOpenJsHelper;
    this.jdField_a_of_type_JavaLangObject = paramObject;
  }
  
  protected boolean a()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("video");
    ((StringBuilder)localObject).append(".");
    ((StringBuilder)localObject).append(Thread.currentThread().getStackTrace()[3].getMethodName());
    localObject = ((StringBuilder)localObject).toString();
    if (this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.checkJsApiDomain((String)localObject) == 1) {
      return true;
    }
    return this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.checkQQDomain();
  }
  
  public String canSniff(JSONObject paramJSONObject)
  {
    if (!a()) {
      return null;
    }
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("host", new JSONArray());
      try
      {
        localObject = paramJSONObject.getJSONArray("host");
        if (localObject == null) {
          return localJSONObject.toString();
        }
        paramJSONObject = new JSONArray();
        if (!(this.jdField_a_of_type_JavaLangObject instanceof DexClassLoader)) {
          break label104;
        }
        i = 0;
      }
      catch (Exception paramJSONObject)
      {
        for (;;)
        {
          Object localObject;
          int i;
          String str;
          label104:
          continue;
          i += 1;
        }
      }
      if (i < ((JSONArray)localObject).length())
      {
        str = ((JSONArray)localObject).getString(i);
        if (VideoSniffer.isSniffSupported(str)) {
          paramJSONObject.put(str);
        }
      }
      else
      {
        if (paramJSONObject.length() > 0)
        {
          localObject = new JSONObject();
          ((JSONObject)localObject).put("host", paramJSONObject);
          paramJSONObject = ((JSONObject)localObject).toString();
          return paramJSONObject;
        }
        return localJSONObject.toString();
        return localJSONObject.toString();
      }
    }
    catch (JSONException localJSONException)
    {
      for (;;) {}
    }
  }
  
  public void destroy() {}
  
  public String exec(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.setOriginUrl(paramString3);
    if ("canSniff".equals(paramString1)) {
      return canSniff(paramJSONObject);
    }
    if ("sniffVideo".equals(paramString1))
    {
      int i = 0;
      try
      {
        paramString1 = paramJSONObject.getString("webUrl");
        try
        {
          int j = paramJSONObject.getInt("clarity");
          i = j;
        }
        catch (JSONException paramJSONObject) {}
        paramJSONObject.printStackTrace();
      }
      catch (JSONException paramJSONObject)
      {
        paramString1 = null;
      }
      if (paramString1 != null) {
        sniffVideoUrl(paramString1, i, paramString2);
      }
    }
    return null;
  }
  
  public void setJsApiImpl(Object paramObject) {}
  
  public void sniffVideoUrl(final String paramString1, final int paramInt, final String paramString2)
  {
    if (!a()) {
      return;
    }
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.runOnUiThread(new Runnable()
    {
      public void run()
      {
        new VideoSniffer().sniffVideo(paramString1, paramInt, new SniffObserver()
        {
          public void onSniffCompleted(List<String> paramAnonymous2List, Bundle paramAnonymous2Bundle)
          {
            int i;
            if ((paramAnonymous2List != null) && (paramAnonymous2List.size() > 0)) {
              i = 0;
            } else {
              i = -1;
            }
            JSONObject localJSONObject = new JSONObject();
            try
            {
              localJSONObject.put("status", i);
              localJSONObject.put("requestUrl", jsVideo.1.this.jdField_a_of_type_JavaLangString);
              if (i == 0) {
                paramAnonymous2List = new JSONArray(paramAnonymous2List);
              } else {
                paramAnonymous2List = new JSONArray();
              }
              localJSONObject.put("urls", paramAnonymous2List);
              localJSONObject.put("clarityCount", paramAnonymous2Bundle.getInt("clarityNum"));
              try
              {
                jsVideo.1.this.jdField_a_of_type_ComTencentSmttJsApiImplJsVideo.a.sendJsCallback(jsVideo.1.this.b, localJSONObject);
                return;
              }
              catch (OutOfMemoryError paramAnonymous2List)
              {
                paramAnonymous2List.printStackTrace();
                return;
              }
              return;
            }
            catch (JSONException paramAnonymous2List) {}
          }
        });
      }
    });
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\jsVideo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */