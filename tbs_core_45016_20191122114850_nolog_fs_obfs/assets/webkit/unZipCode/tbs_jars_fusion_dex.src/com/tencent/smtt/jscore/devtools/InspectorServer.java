package com.tencent.smtt.jscore.devtools;

import android.os.Process;
import com.tencent.smtt.net.HttpServer;
import com.tencent.smtt.net.HttpServer.Delegate;
import java.util.HashMap;

public class InspectorServer
  implements HttpServer.Delegate
{
  private static final String CLAZZ_NAME = "InspectorServer";
  private static final String JSCORE_URL_PREFIX = "/devtools/x5jscore/";
  private static final String JSON_MIME_TYPE = "application/json; charset=UTF-8";
  private static final int RECEIVE_BUFFER_SIZE_FOR_DEV_TOOLS = 104857600;
  private static final int SEND_BUFFER_SIZE_FOR_DEV_TOOLS = 268435456;
  private static final String SOCKET_NAME_FORMAT = "x5jscore_devtools_remote_%d";
  private static InspectorServer sInstance;
  private HttpServer mHttpServer = new HttpServer(this, String.format("x5jscore_devtools_remote_%d", new Object[] { Integer.valueOf(Process.myPid()) }));
  private HashMap<Integer, Session> mSessions = new HashMap();
  
  private void onJsonRequest(int paramInt, String paramString1, String paramString2)
  {
    int i = paramString1.indexOf("?");
    paramString2 = paramString1;
    if (i >= 0)
    {
      paramString1.substring(i + 1);
      paramString2 = paramString1.substring(0, i);
    }
    i = paramString2.indexOf("#");
    paramString1 = paramString2;
    if (i >= 0) {
      paramString1 = paramString2.substring(0, i);
    }
    if (paramString1.isEmpty())
    {
      paramString1 = "list";
    }
    else
    {
      if (!paramString1.startsWith("/"))
      {
        paramString2 = new StringBuilder();
        paramString2.append("Malformed query: ");
        paramString2.append(paramString1);
        sendJson(paramInt, 404, paramString2.toString());
        return;
      }
      paramString2 = paramString1.substring(1);
      i = paramString2.indexOf("/");
      paramString1 = paramString2;
      if (i >= 0)
      {
        paramString2.substring(i + 1);
        paramString1 = paramString2.substring(0, i);
      }
    }
    paramString2 = Inspector.getInstance();
    if ("version".equals(paramString1))
    {
      sendJson(paramInt, 200, paramString2.getVersion());
      return;
    }
    if ("protocol".equals(paramString1)) {
      return;
    }
    if ("list".equals(paramString1))
    {
      sendJson(paramInt, 200, paramString2.getPageList());
      return;
    }
    if ("new".equals(paramString1))
    {
      sendJson(paramInt, 500, "Could not create new page");
      return;
    }
    if (("activate".equals(paramString1)) || ("close".equals(paramString1)))
    {
      if ("activate".equals(paramString1))
      {
        sendJson(paramInt, 200, "Target activated");
        return;
      }
      if ("close".equals(paramString1))
      {
        sendJson(paramInt, 200, "Target is closing");
        return;
      }
    }
    paramString2 = new StringBuilder();
    paramString2.append("Unknown command: ");
    paramString2.append(paramString1);
    sendJson(paramInt, 404, paramString2.toString());
  }
  
  private void sendJson(int paramInt1, int paramInt2, String paramString)
  {
    this.mHttpServer.a(paramInt1, paramInt2, paramString, "application/json; charset=UTF-8");
  }
  
  public static void startRemoteDebugging()
  {
    if (sInstance == null) {
      sInstance = new InspectorServer();
    }
  }
  
  public void onClose(int paramInt)
  {
    Session localSession = (Session)this.mSessions.remove(Integer.valueOf(paramInt));
    if (localSession != null) {
      localSession.disconnect();
    }
  }
  
  public void onConnect(int paramInt) {}
  
  public void onHttpRequest(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    this.mHttpServer.b(paramInt, 268435456);
    if ((paramString2 != null) && (paramString2.startsWith("/json")))
    {
      onJsonRequest(paramInt, paramString2.substring(5), paramString3);
      return;
    }
    this.mHttpServer.a(paramInt);
  }
  
  public void onWebSocketMessage(int paramInt, String paramString)
  {
    Session localSession = (Session)this.mSessions.get(Integer.valueOf(paramInt));
    if (localSession != null) {
      localSession.sendMessage(paramString);
    }
  }
  
  public boolean onWebSocketRequest(final int paramInt, String paramString1, String paramString2, String paramString3)
  {
    if (!paramString2.startsWith("/devtools/x5jscore/"))
    {
      this.mHttpServer.a(paramInt);
      return false;
    }
    long l = Long.valueOf(paramString2.substring(19)).longValue();
    paramString1 = Inspector.getInstance().connect(l, new Channel()
    {
      public void onDisconnect() {}
      
      public void onMessage(String paramAnonymousString)
      {
        InspectorServer.this.mHttpServer.a(paramInt, paramAnonymousString);
      }
    });
    if (paramString1 == null)
    {
      paramString1 = this.mHttpServer;
      paramString2 = new StringBuilder();
      paramString2.append("No such target id: ");
      paramString2.append(l);
      paramString1.b(paramInt, paramString2.toString());
      return false;
    }
    this.mSessions.put(Integer.valueOf(paramInt), paramString1);
    this.mHttpServer.b(paramInt, 268435456);
    this.mHttpServer.a(paramInt, 104857600);
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jscore\devtools\InspectorServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */