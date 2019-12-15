package com.tencent.smtt.webkit;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tencent.common.http.Apn;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.net.NetworkSmttService;
import com.tencent.smtt.net.e;
import com.tencent.smtt.net.f;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.tbsshell.common.QProxyPolicies;
import java.util.HashMap;
import org.chromium.tencent.AwContentsClient.AwResourceMetrics;

public class u
{
  public static byte a = 0;
  private static boolean jdField_a_of_type_Boolean = true;
  public static byte b = 1;
  public static byte c = 2;
  public static byte d = 0;
  public static byte e = 12;
  private Handler jdField_a_of_type_AndroidOsHandler = new Handler(Looper.getMainLooper())
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return;
      case 5: 
        paramAnonymousMessage = (String)paramAnonymousMessage.obj;
        HashMap localHashMap = new HashMap();
        localHashMap.put("uin", paramAnonymousMessage);
        SmttServiceProxy.getInstance().upLoadToBeacon("MTT_CORE_GUID_INFO", localHashMap);
        return;
      case 4: 
        paramAnonymousMessage = (u.b)paramAnonymousMessage.obj;
        f.a().a(paramAnonymousMessage);
        return;
      case 3: 
        paramAnonymousMessage = (u.d)paramAnonymousMessage.obj;
        e.a().a(paramAnonymousMessage);
        return;
      case 2: 
        u.b(u.this, (u.c)paramAnonymousMessage.obj);
        return;
      }
      u.a(u.this, (u.c)paramAnonymousMessage.obj);
    }
  };
  private AwContentsClient.AwResourceMetrics jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics;
  
  private b a()
  {
    Object localObject = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics;
    if ((localObject != null) && (((AwContentsClient.AwResourceMetrics)localObject).isSuccess) && (this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.resourceType == d))
    {
      localObject = new b();
      ((b)localObject).jdField_c_of_type_Int = ((int)this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.contentLength);
      boolean bool;
      if (this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.proxyStrategy != jdField_a_of_type_Byte) {
        bool = true;
      } else {
        bool = false;
      }
      ((b)localObject).jdField_a_of_type_Boolean = bool;
      ((b)localObject).jdField_a_of_type_JavaLangString = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.refer;
      ((b)localObject).jdField_b_of_type_Int = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.statusCode;
      ((b)localObject).jdField_b_of_type_Boolean = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.wasCached;
      return (b)localObject;
    }
    return null;
  }
  
  private c a()
  {
    Object localObject = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics;
    if ((localObject != null) && (((AwContentsClient.AwResourceMetrics)localObject).isSuccess))
    {
      localObject = new c();
      ((c)localObject).jdField_a_of_type_JavaLangString = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.url;
      boolean bool;
      if (this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.proxyStrategy != jdField_a_of_type_Byte) {
        bool = true;
      } else {
        bool = false;
      }
      ((c)localObject).jdField_a_of_type_Boolean = bool;
      ((c)localObject).jdField_a_of_type_Long = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.sentBytes;
      ((c)localObject).jdField_b_of_type_Long = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.receivedBytes;
      ((c)localObject).jdField_b_of_type_JavaLangString = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.metricsSaved;
      ((c)localObject).jdField_a_of_type_Int = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.statusCode;
      ((c)localObject).jdField_c_of_type_JavaLangString = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.mimeType;
      ((c)localObject).jdField_b_of_type_Int = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.proxyType;
      if (this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.resourceType == d) {
        bool = true;
      } else {
        bool = false;
      }
      ((c)localObject).jdField_b_of_type_Boolean = bool;
      ((c)localObject).jdField_c_of_type_Boolean = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.isMiniQB;
      ((c)localObject).jdField_d_of_type_Boolean = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.wasCached;
      ((c)localObject).jdField_d_of_type_JavaLangString = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.domain;
      if ((this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.serverIP != null) && (this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.serverIP.length() > 0) && (this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.serverIP.endsWith("8091"))) {
        ((c)localObject).jdField_e_of_type_Boolean = true;
      } else {
        ((c)localObject).jdField_e_of_type_Boolean = false;
      }
      ((c)localObject).jdField_c_of_type_Int = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.proxyErrorCode;
      ((c)localObject).jdField_d_of_type_Int = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.requestErrorCode;
      ((c)localObject).f = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.isProxyFailed;
      ((c)localObject).g = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.isHttpsTunnelFailed;
      ((c)localObject).jdField_e_of_type_JavaLangString = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.refer;
      ((c)localObject).h = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.isSuccess;
      ((c)localObject).jdField_e_of_type_Int = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.connectInfo;
      return (c)localObject;
    }
    return null;
  }
  
  private d a()
  {
    Object localObject = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics;
    if ((localObject != null) && (((AwContentsClient.AwResourceMetrics)localObject).resourceType != e) && ((this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.requestErrorCode != 0) || ((this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.statusCode >= 400) && (this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.statusCode < 800))) && (this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.requestErrorCode != -3))
    {
      localObject = new d();
      ((d)localObject).jdField_a_of_type_JavaLangString = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.url;
      ((d)localObject).jdField_b_of_type_JavaLangString = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.domain;
      ((d)localObject).jdField_a_of_type_Int = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.requestErrorCode;
      ((d)localObject).jdField_b_of_type_Int = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.statusCode;
      ((d)localObject).jdField_c_of_type_Int = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.proxyStrategy;
      ((d)localObject).jdField_d_of_type_Int = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.resourceType;
      ((d)localObject).jdField_d_of_type_JavaLangString = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.serverIP;
      ((d)localObject).jdField_a_of_type_Long = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.contentLength;
      ((d)localObject).f = this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics.connectInfo;
      ((d)localObject).jdField_e_of_type_Int = 0;
      return (d)localObject;
    }
    return null;
  }
  
  private void a(c paramc)
  {
    SmttServiceProxy.getInstance().onReportMetrics(paramc.jdField_a_of_type_JavaLangString, paramc.jdField_d_of_type_JavaLangString, paramc.jdField_a_of_type_Long, paramc.jdField_b_of_type_Long, paramc.jdField_a_of_type_Int, paramc.jdField_c_of_type_JavaLangString, QProxyPolicies.shouldUseQProxyAccordingToFlag(paramc.jdField_b_of_type_Int), Apn.sApnTypeS, paramc.jdField_b_of_type_JavaLangString, "", paramc.jdField_b_of_type_Boolean, paramc.jdField_d_of_type_Boolean, paramc.jdField_c_of_type_Boolean, paramc.jdField_b_of_type_Int, paramc.jdField_e_of_type_Boolean, paramc.jdField_e_of_type_Int);
  }
  
  private void b(c paramc)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("url", paramc.jdField_a_of_type_JavaLangString);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramc.jdField_a_of_type_Boolean);
    localStringBuilder.append("");
    localHashMap.put("isProxy", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramc.jdField_a_of_type_Long);
    localStringBuilder.append("");
    localHashMap.put("sentBytes", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramc.jdField_b_of_type_Long);
    localStringBuilder.append("");
    localHashMap.put("receivedBytes", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramc.jdField_a_of_type_Int);
    localStringBuilder.append("");
    localHashMap.put("stateCode", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramc.jdField_b_of_type_Boolean);
    localStringBuilder.append("");
    localHashMap.put("isMainResource", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramc.jdField_c_of_type_Int);
    localStringBuilder.append("");
    localHashMap.put("qproxyErrorCode", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramc.jdField_d_of_type_Int);
    localStringBuilder.append("");
    localHashMap.put("requestErrorCode", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramc.f);
    localStringBuilder.append("");
    localHashMap.put("isProxyFailed", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramc.g);
    localStringBuilder.append("");
    localHashMap.put("isHttpsTunnelFailed", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramc.jdField_e_of_type_JavaLangString);
    localStringBuilder.append("");
    localHashMap.put("referer", localStringBuilder.toString());
    localHashMap.put("freeflow", "false");
    SmttServiceProxy.getInstance().reportBigKingStatus(localHashMap);
  }
  
  public void a(int paramInt)
  {
    b localb = a();
    if (localb == null) {
      return;
    }
    localb.jdField_a_of_type_Int = paramInt;
    Handler localHandler = this.jdField_a_of_type_AndroidOsHandler;
    localHandler.sendMessage(localHandler.obtainMessage(4, localb));
  }
  
  public void a(IX5WebViewClientExtension paramIX5WebViewClientExtension)
  {
    c localc = a();
    if (localc == null) {
      return;
    }
    if (jdField_a_of_type_Boolean) {}
    try
    {
      paramIX5WebViewClientExtension.onMetricsSavedCountReceived(localc.jdField_a_of_type_JavaLangString, localc.jdField_a_of_type_Boolean, localc.jdField_a_of_type_Long + localc.jdField_b_of_type_Long, localc.jdField_b_of_type_JavaLangString, NetworkSmttService.a());
    }
    catch (NoSuchMethodError paramIX5WebViewClientExtension)
    {
      boolean bool1;
      boolean bool2;
      int i;
      for (;;) {}
    }
    catch (Exception paramIX5WebViewClientExtension)
    {
      for (;;) {}
    }
    jdField_a_of_type_Boolean = false;
    break label67;
    jdField_a_of_type_Boolean = false;
    label67:
    bool1 = localc.jdField_e_of_type_Boolean;
    bool2 = SmttServiceProxy.getInstance().isKingCardUser();
    if ((!bool1) && (bool2) && (localc.jdField_a_of_type_JavaLangString.startsWith("http")))
    {
      if (QProxyPolicies.shouldUseQProxyAccordingToFlag(localc.jdField_b_of_type_Int))
      {
        i = 1;
      }
      else
      {
        i = bool1;
        if (localc.jdField_d_of_type_JavaLangString != null)
        {
          i = bool1;
          if (localc.jdField_d_of_type_JavaLangString.length() != 0)
          {
            i = bool1;
            if (localc.jdField_d_of_type_JavaLangString.endsWith(".qq.com")) {
              i = 1;
            }
          }
        }
      }
    }
    else
    {
      i = bool1;
      if (!bool2) {
        i = 0;
      }
    }
    if ((bool2) && (i == 0) && (!localc.jdField_d_of_type_Boolean) && (localc.jdField_d_of_type_Int != -3))
    {
      paramIX5WebViewClientExtension = this.jdField_a_of_type_AndroidOsHandler;
      paramIX5WebViewClientExtension.sendMessage(paramIX5WebViewClientExtension.obtainMessage(2, localc));
    }
    paramIX5WebViewClientExtension = this.jdField_a_of_type_AndroidOsHandler;
    paramIX5WebViewClientExtension.sendMessage(paramIX5WebViewClientExtension.obtainMessage(1, localc));
  }
  
  public void a(String paramString)
  {
    Handler localHandler = this.jdField_a_of_type_AndroidOsHandler;
    localHandler.sendMessage(localHandler.obtainMessage(5, paramString));
  }
  
  public void a(AwContentsClient.AwResourceMetrics paramAwResourceMetrics)
  {
    this.jdField_a_of_type_OrgChromiumTencentAwContentsClient$AwResourceMetrics = paramAwResourceMetrics;
  }
  
  public void b(int paramInt)
  {
    d locald = a();
    if (locald == null) {
      return;
    }
    locald.g = paramInt;
    Handler localHandler = this.jdField_a_of_type_AndroidOsHandler;
    localHandler.sendMessage(localHandler.obtainMessage(3, locald));
  }
  
  public static class a
  {
    public int a;
    public String a;
    public String b;
    public String c;
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("BadJsMsg [bid=");
      localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
      localStringBuilder.append(", url=");
      localStringBuilder.append(this.b);
      localStringBuilder.append(", msg=");
      localStringBuilder.append(this.c);
      localStringBuilder.append(", clientAdapterHash=");
      localStringBuilder.append(this.jdField_a_of_type_Int);
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
  
  public static class b
  {
    public int a;
    public String a;
    public boolean a;
    public int b;
    public boolean b;
    public int c;
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("PageLoadInfoMetrics [clientHashCode=");
      localStringBuilder.append(this.jdField_a_of_type_Int);
      localStringBuilder.append(", isProxy=");
      localStringBuilder.append(this.jdField_a_of_type_Boolean);
      localStringBuilder.append(", refer=");
      localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
      localStringBuilder.append(", wasCached=");
      localStringBuilder.append(this.jdField_b_of_type_Boolean);
      localStringBuilder.append(", statusCode=");
      localStringBuilder.append(this.jdField_b_of_type_Int);
      localStringBuilder.append(", contentLength=");
      localStringBuilder.append(this.c);
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
  
  public static class c
  {
    public int a;
    public long a;
    public String a;
    public boolean a;
    public int b;
    public long b;
    public String b;
    public boolean b;
    public int c;
    public String c;
    public boolean c;
    public int d;
    public String d;
    public boolean d;
    public int e;
    public String e;
    public boolean e;
    public boolean f;
    public boolean g;
    public boolean h;
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("PvMetrics [url=");
      localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
      localStringBuilder.append(", isProxy=");
      localStringBuilder.append(this.jdField_a_of_type_Boolean);
      localStringBuilder.append(", sentBytes=");
      localStringBuilder.append(this.jdField_a_of_type_Long);
      localStringBuilder.append(", receivedBytes=");
      localStringBuilder.append(this.jdField_b_of_type_Long);
      localStringBuilder.append(", metricsSaved=");
      localStringBuilder.append(this.jdField_b_of_type_JavaLangString);
      localStringBuilder.append(", stateCode=");
      localStringBuilder.append(this.jdField_a_of_type_Int);
      localStringBuilder.append(", mimeType=");
      localStringBuilder.append(this.jdField_c_of_type_JavaLangString);
      localStringBuilder.append(", proxyType=");
      localStringBuilder.append(this.jdField_b_of_type_Int);
      localStringBuilder.append(", isMainResource=");
      localStringBuilder.append(this.jdField_b_of_type_Boolean);
      localStringBuilder.append(", isMiniQB=");
      localStringBuilder.append(this.jdField_c_of_type_Boolean);
      localStringBuilder.append(", wasCached=");
      localStringBuilder.append(this.jdField_d_of_type_Boolean);
      localStringBuilder.append(", domain=");
      localStringBuilder.append(this.jdField_d_of_type_JavaLangString);
      localStringBuilder.append(", isGreatCardFlow=");
      localStringBuilder.append(this.jdField_e_of_type_Boolean);
      localStringBuilder.append(", qproxyErrorCode=");
      localStringBuilder.append(this.jdField_c_of_type_Int);
      localStringBuilder.append(", requestErrorCode=");
      localStringBuilder.append(this.jdField_d_of_type_Int);
      localStringBuilder.append(", isProxyFailed=");
      localStringBuilder.append(this.f);
      localStringBuilder.append(", isHttpsTunnelFailed=");
      localStringBuilder.append(this.g);
      localStringBuilder.append(", referer=");
      localStringBuilder.append(this.jdField_e_of_type_JavaLangString);
      localStringBuilder.append(", isSuccess=");
      localStringBuilder.append(this.h);
      localStringBuilder.append(", connectInfo=");
      localStringBuilder.append(this.jdField_e_of_type_Int);
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
  
  public static class d
  {
    public int a;
    public long a;
    public String a;
    public int b;
    public String b;
    public int c;
    public String c;
    public int d;
    public String d;
    public int e;
    public int f;
    public int g;
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("ResourceErrorInfo [url=");
      localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
      localStringBuilder.append(", domain=");
      localStringBuilder.append(this.jdField_b_of_type_JavaLangString);
      localStringBuilder.append(", errorMessage=");
      localStringBuilder.append(this.jdField_c_of_type_JavaLangString);
      localStringBuilder.append(", errorId=");
      localStringBuilder.append(this.jdField_a_of_type_Int);
      localStringBuilder.append(", statusCode=");
      localStringBuilder.append(this.jdField_b_of_type_Int);
      localStringBuilder.append(", proxyStrategy=");
      localStringBuilder.append(this.jdField_c_of_type_Int);
      localStringBuilder.append(", resourceType=");
      localStringBuilder.append(this.jdField_d_of_type_Int);
      localStringBuilder.append(", serverIP=");
      localStringBuilder.append(this.jdField_d_of_type_JavaLangString);
      localStringBuilder.append(", contentLength=");
      localStringBuilder.append(this.jdField_a_of_type_Long);
      localStringBuilder.append(", errType=");
      localStringBuilder.append(this.e);
      localStringBuilder.append(", connectInfo=");
      localStringBuilder.append(this.f);
      localStringBuilder.append(", clientAdapterHash=");
      localStringBuilder.append(this.g);
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\u.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */