package com.tencent.smtt.jscore.devtools;

import com.tencent.smtt.jscore.X5JsContextImpl;
import com.tencent.smtt.jscore.X5JsVirtualMachineImpl;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Inspector
{
  private static final String DEVTOOLS_FRONTEND_URL_PREFIX = "chrome-devtools://devtools/bundled/inspector.html?experiments=true&v8only=true&ws=/devtools/x5jscore/";
  private static final String WEB_SOCKET_DEBUGGER_URL_PREFIX = "ws:///devtools/x5jscore/";
  private static Inspector sInstance;
  private static final Object sLock = new Object();
  private Map<Long, WeakReference<X5JsContextImpl>> mJsContextMap = new ConcurrentHashMap();
  private String mPackageName;
  
  public static Inspector getInstance()
  {
    synchronized (sLock)
    {
      if (sInstance == null) {
        sInstance = new Inspector();
      }
      Inspector localInspector = sInstance;
      return localInspector;
    }
  }
  
  public boolean activate(long paramLong)
  {
    return true;
  }
  
  public boolean close(long paramLong)
  {
    return true;
  }
  
  public Session connect(long paramLong, Channel paramChannel)
  {
    WeakReference localWeakReference = (WeakReference)this.mJsContextMap.get(Long.valueOf(paramLong));
    if ((localWeakReference.get() != null) && (!((X5JsContextImpl)localWeakReference.get()).isDestroyed())) {
      return new Session(paramLong, ((X5JsContextImpl)localWeakReference.get()).virtualMachine().getLooper(), paramChannel);
    }
    if (paramChannel != null) {
      paramChannel.onDisconnect();
    }
    return null;
  }
  
  public String getPageList()
  {
    JSONArray localJSONArray = new JSONArray();
    Iterator localIterator = this.mJsContextMap.entrySet().iterator();
    Object localObject2;
    long l;
    while (localIterator.hasNext())
    {
      localObject1 = (Map.Entry)localIterator.next();
      localObject2 = (X5JsContextImpl)((WeakReference)((Map.Entry)localObject1).getValue()).get();
      if (localObject2 != null) {
        l = ((Long)((Map.Entry)localObject1).getKey()).longValue();
      }
    }
    String str;
    for (Object localObject1 = ((X5JsContextImpl)localObject2).name();; str = "X5JsCore")
    {
      try
      {
        localObject2 = new JSONObject().put("id", l).put("type", "node");
        if (localObject1 == null) {
          continue;
        }
        localObject1 = ((JSONObject)localObject2).put("title", localObject1).put("description", "").put("url", "").put("faviconUrl", "");
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("ws:///devtools/x5jscore/");
        ((StringBuilder)localObject2).append(l);
        localObject1 = ((JSONObject)localObject1).put("webSocketDebuggerUrl", ((StringBuilder)localObject2).toString());
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("chrome-devtools://devtools/bundled/inspector.html?experiments=true&v8only=true&ws=/devtools/x5jscore/");
        ((StringBuilder)localObject2).append(l);
        localJSONArray.put(((JSONObject)localObject1).put("devtoolsFrontendUrl", ((StringBuilder)localObject2).toString()));
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
      break;
      return localJSONArray.toString();
    }
  }
  
  public String getVersion()
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("Protocol-Version", "1.2").put("Browser", "X5JsCore/1.0").put("V8-Version", "5.7.492.71").put("Android-Package", this.mPackageName).toString();
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return localJSONObject.toString();
  }
  
  public void registerJsContext(long paramLong, X5JsContextImpl paramX5JsContextImpl)
  {
    this.mJsContextMap.put(Long.valueOf(paramLong), new WeakReference(paramX5JsContextImpl));
  }
  
  public void setPackageName(String paramString)
  {
    this.mPackageName = paramString;
  }
  
  public void unregisterJsContext(long paramLong)
  {
    this.mJsContextMap.remove(Long.valueOf(paramLong));
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jscore\devtools\Inspector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */