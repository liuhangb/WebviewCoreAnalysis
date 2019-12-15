package com.tencent.tbs.common.ar.WEBAR;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class WebAREngineManagerService
{
  private static final String TAG = "WebAREngineManagerService";
  private static WebAREngineManagerService sInstance;
  private Context mApp;
  private WebARCloudRecognition mCloudRecognition;
  private WebARModelManager mModelManager;
  private WebARPluginManager mPluginManager;
  
  private WebAREngineManagerService(Context paramContext)
  {
    this.mApp = paramContext.getApplicationContext();
    this.mCloudRecognition = new WebARCloudRecognition(paramContext);
    this.mPluginManager = new WebARPluginManager(paramContext);
    this.mModelManager = new WebARModelManager(paramContext);
  }
  
  public static WebAREngineManagerService getInstance(Context paramContext)
  {
    try
    {
      if (sInstance == null) {
        sInstance = new WebAREngineManagerService(paramContext);
      }
      paramContext = sInstance;
      return paramContext;
    }
    finally {}
  }
  
  public void doRecogitionOnCloud(byte[] paramArrayOfByte)
  {
    WebARCloudRecognition localWebARCloudRecognition = this.mCloudRecognition;
    if (localWebARCloudRecognition != null) {
      localWebARCloudRecognition.doRecognition(paramArrayOfByte);
    }
  }
  
  public void loadWebAREngine(int paramInt, IWebAREngineCallback paramIWebAREngineCallback)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("loadWebAREngine arType: ");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).toString();
    localObject = this.mPluginManager;
    if (localObject != null) {
      ((WebARPluginManager)localObject).loadWebAREngine(paramInt, paramIWebAREngineCallback);
    }
  }
  
  public void loadWebARModel(String paramString, IWebARModelCallback paramIWebARModelCallback)
  {
    WebARModelManager localWebARModelManager = this.mModelManager;
    if (localWebARModelManager != null) {
      localWebARModelManager.loadWebARModel(paramString, paramIWebARModelCallback);
    }
  }
  
  public void requestCloudRecognition(String paramString, IWebARCloudRecognitionCallback paramIWebARCloudRecognitionCallback)
  {
    for (;;)
    {
      int i;
      try
      {
        Object localObject2 = new JSONObject(paramString);
        if (TextUtils.isEmpty(((JSONObject)localObject2).optString("name", "empty options")))
        {
          Log.e("WebAREngineManagerService", "empty marker name");
          return;
        }
        String str1 = ((JSONObject)localObject2).optString("appid", "");
        if (TextUtils.isEmpty(str1)) {
          Log.e("WebAREngineManagerService", "empty appid");
        }
        String str2 = ((JSONObject)localObject2).optString("signature", "");
        if (TextUtils.isEmpty(str2)) {
          Log.e("WebAREngineManagerService", "empty signature");
        }
        String str3 = ((JSONObject)localObject2).optString("accessid", "");
        if (TextUtils.isEmpty(str3)) {
          Log.e("WebAREngineManagerService", "empty aceesskey");
        }
        String str4 = ((JSONObject)localObject2).optString("lTimeStamp", "0");
        if (TextUtils.isEmpty(str4)) {
          Log.e("WebAREngineManagerService", "empty time");
        }
        Object localObject1 = ((JSONObject)localObject2).optString("scene", "2");
        paramString = (String)localObject1;
        if (TextUtils.isEmpty((CharSequence)localObject1))
        {
          Log.e("WebAREngineManagerService", "empty scene");
          paramString = "2";
        }
        localObject1 = ((JSONObject)localObject2).optJSONArray("targetCategory");
        localObject2 = new ArrayList();
        if (localObject1 != null)
        {
          i = 0;
          if (i < ((JSONArray)localObject1).length())
          {
            String str5 = ((JSONArray)localObject1).optString(i, "");
            if (TextUtils.isEmpty(str5)) {
              break label286;
            }
            ((ArrayList)localObject2).add(str5);
            break label286;
          }
        }
        if (this.mCloudRecognition != null)
        {
          this.mCloudRecognition.setCategories(str3, (ArrayList)localObject2, str1, str2, Long.valueOf(str4).longValue(), Integer.valueOf(paramString).intValue());
          this.mCloudRecognition.setCloudRecognitionCallback(paramIWebARCloudRecognitionCallback);
        }
        return;
      }
      catch (Throwable paramString)
      {
        return;
      }
      label286:
      i += 1;
    }
  }
  
  public void stopCloudRecognition()
  {
    WebARCloudRecognition localWebARCloudRecognition = this.mCloudRecognition;
    if (localWebARCloudRecognition != null) {
      localWebARCloudRecognition.stopCloudRecognition();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ar\WEBAR\WebAREngineManagerService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */