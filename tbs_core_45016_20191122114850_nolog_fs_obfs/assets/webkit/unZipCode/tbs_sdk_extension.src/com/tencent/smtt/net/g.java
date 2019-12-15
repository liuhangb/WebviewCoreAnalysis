package com.tencent.smtt.net;

import android.os.SystemClock;
import android.util.SparseArray;
import android.webview.chromium.WebViewContentsClientAdapter;
import android.webview.chromium.tencent.TencentWebViewContentsClientAdapter;
import android.widget.Toast;
import com.tencent.common.http.Apn;
import com.tencent.smtt.b.a;
import com.tencent.smtt.b.b;
import com.tencent.smtt.util.j;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.tbsshell.common.QProxyPolicies;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.chromium.android_webview.AwContentsClient.AwWebResourceRequest;
import org.chromium.android_webview.AwSettings;
import org.chromium.android_webview.AwWebResourceResponse;
import org.chromium.tencent.AwContentsClient.AwWebRequestInfo;
import org.chromium.tencent.AwContentsClient.AwWebResourceError;
import org.chromium.tencent.AwContentsClient.AwWebResourcePerformanceInfo;
import org.chromium.tencent.TencentAwSettings;
import org.json.JSONException;
import org.json.JSONObject;

public class g
{
  private static g jdField_a_of_type_ComTencentSmttNetG;
  private static String jdField_a_of_type_JavaLangString = "PagePerformanceRecorder";
  public static boolean a = true;
  private static boolean b = false;
  private static boolean c = true;
  private SparseArray<a> jdField_a_of_type_AndroidUtilSparseArray = null;
  
  g()
  {
    jdField_c_of_type_Boolean = SmttServiceProxy.getInstance().getSubResourcePerformanceEnabled();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("shouldReportSubResourcePerformance=");
    localStringBuilder.append(jdField_c_of_type_Boolean);
    a(localStringBuilder.toString());
  }
  
  public static g a()
  {
    if (jdField_a_of_type_ComTencentSmttNetG == null) {
      jdField_a_of_type_ComTencentSmttNetG = new g();
    }
    return jdField_a_of_type_ComTencentSmttNetG;
  }
  
  private static void a(String paramString)
  {
    boolean bool = jdField_b_of_type_Boolean;
  }
  
  public static boolean a()
  {
    return jdField_c_of_type_Boolean;
  }
  
  private void b(TencentWebViewContentsClientAdapter paramTencentWebViewContentsClientAdapter, AwSettings paramAwSettings)
  {
    if (!jdField_a_of_type_Boolean) {
      return;
    }
    a locala = (a)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramTencentWebViewContentsClientAdapter.hashCode());
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("handlePagePerformance client=");
    ((StringBuilder)localObject2).append(paramTencentWebViewContentsClientAdapter.hashCode());
    ((StringBuilder)localObject2).append(" url=");
    Object localObject1;
    if (locala != null) {
      localObject1 = locala.jdField_a_of_type_JavaLangString;
    } else {
      localObject1 = "null";
    }
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(" thread=");
    ((StringBuilder)localObject2).append(Thread.currentThread().getName());
    a(((StringBuilder)localObject2).toString());
    this.jdField_a_of_type_AndroidUtilSparseArray.remove(paramTencentWebViewContentsClientAdapter.hashCode());
    if (locala != null)
    {
      boolean bool = SmttServiceProxy.getInstance().isPerformanceLogRecordOpen();
      int n = 0;
      if ((bool) && (locala.jdField_a_of_type_JavaLangString != null) && (locala.jdField_a_of_type_JavaLangString.startsWith("http")) && (!j.c(locala.jdField_a_of_type_JavaLangString).equals("debugx5.qq.com")) && (!j.c(locala.jdField_a_of_type_JavaLangString).equals("res.imtt.qq.com")))
      {
        a.a();
        a.a = locala;
        Toast.makeText(ContextHolder.getInstance().getApplicationContext(), "性能信息已上报", 0).show();
        b.a(ContextHolder.getInstance().getApplicationContext()).b();
      }
      else
      {
        b.a(ContextHolder.getInstance().getApplicationContext()).b();
      }
      if ((locala.jdField_a_of_type_ComTencentSmttNetG$b != null) && (locala.jdField_a_of_type_Long > 0L) && (locala.jdField_a_of_type_ComTencentSmttNetG$b.d))
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("handlePagePerformance url=");
        if (locala != null) {
          localObject1 = locala.jdField_a_of_type_JavaLangString;
        } else {
          localObject1 = "null";
        }
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append(" size=");
        if ((locala != null) && (locala.a() != null)) {
          localObject1 = Integer.valueOf(locala.a().size());
        } else {
          localObject1 = "0";
        }
        ((StringBuilder)localObject2).append(localObject1);
        a(((StringBuilder)localObject2).toString());
        localObject1 = new StringBuilder();
        StringBuilder localStringBuilder1 = new StringBuilder();
        StringBuilder localStringBuilder2 = new StringBuilder();
        StringBuilder localStringBuilder3 = new StringBuilder();
        StringBuilder localStringBuilder4 = new StringBuilder();
        int m;
        if (locala.a() != null) {
          m = locala.a().size();
        } else {
          m = 0;
        }
        int i = 0;
        while (i < 3)
        {
          if ((i < m) && (locala.a().get(i) != null) && (((b)locala.a().get(i)).jdField_c_of_type_Boolean != true))
          {
            ((StringBuilder)localObject1).append("re:");
            ((StringBuilder)localObject1).append(((b)locala.a().get(i)).a(false));
          }
          j = i + 3;
          if ((j < m) && (locala.a().get(j) != null) && (((b)locala.a().get(j)).jdField_c_of_type_Boolean != true))
          {
            localStringBuilder1.append("re:");
            localStringBuilder1.append(((b)locala.a().get(j)).a(false));
          }
          j = i + 6;
          if ((j < m) && (locala.a().get(j) != null) && (((b)locala.a().get(j)).jdField_c_of_type_Boolean != true))
          {
            localStringBuilder2.append("re:");
            localStringBuilder2.append(((b)locala.a().get(j)).a(false));
          }
          j = i + 9;
          if ((j < m) && (locala.a().get(j) != null) && (((b)locala.a().get(j)).jdField_c_of_type_Boolean != true))
          {
            localStringBuilder3.append("re:");
            localStringBuilder3.append(((b)locala.a().get(j)).a(false));
          }
          j = i + 12;
          if ((j < m) && (locala.a().get(j) != null) && (((b)locala.a().get(j)).jdField_c_of_type_Boolean != true))
          {
            localStringBuilder4.append("re:");
            localStringBuilder4.append(((b)locala.a().get(j)).a(false));
          }
          i += 1;
        }
        localObject2 = new HashMap();
        int k = 0;
        int j = 0;
        int i1;
        for (i = 0; n < m; i = i1)
        {
          b localb = (b)locala.a().get(n);
          int i3 = k;
          int i2 = j;
          i1 = i;
          if (localb != null)
          {
            i3 = k;
            i2 = j;
            i1 = i;
            if (!localb.jdField_b_of_type_Boolean)
            {
              i3 = k + 1;
              k = j;
              if (localb.h >= 7340032) {
                k = j + 1;
              }
              j = i;
              if (localb.jdField_a_of_type_Boolean) {
                j = i + 1;
              }
              ((HashMap)localObject2).put(localb.jdField_c_of_type_JavaLangString, Integer.valueOf(1));
              i1 = j;
              i2 = k;
            }
          }
          n += 1;
          k = i3;
          j = i2;
        }
        if ((locala.jdField_a_of_type_ComTencentSmttNetG$b != null) && (!locala.jdField_a_of_type_ComTencentSmttNetG$b.jdField_b_of_type_Boolean))
        {
          m = k + 1;
          k = j;
          if (locala.jdField_a_of_type_ComTencentSmttNetG$b.h >= 7340032) {
            k = j + 1;
          }
          j = i;
          if (locala.jdField_a_of_type_ComTencentSmttNetG$b.jdField_a_of_type_Boolean) {
            j = i + 1;
          }
          i = j;
          j = m;
          m = k;
        }
        else
        {
          m = j;
          j = k;
        }
        float f2 = i;
        float f1 = j;
        f2 /= f1;
        f1 = m / f1;
        i = ((HashMap)localObject2).size();
        localObject2 = new HashMap();
        ((HashMap)localObject2).put("url", locala.jdField_a_of_type_ComTencentSmttNetG$b.jdField_a_of_type_JavaLangString);
        ((HashMap)localObject2).put("referer", locala.jdField_a_of_type_ComTencentSmttNetG$b.jdField_b_of_type_JavaLangString);
        ((HashMap)localObject2).put("apn_type", Integer.toString(locala.jdField_a_of_type_Int));
        ((HashMap)localObject2).put("proxy_type", Integer.toString(locala.jdField_a_of_type_ComTencentSmttNetG$b.jdField_a_of_type_Int));
        ((HashMap)localObject2).put("qproxy_strategy", Integer.toString(locala.jdField_a_of_type_ComTencentSmttNetG$b.jdField_b_of_type_Int));
        ((HashMap)localObject2).put("first_screen", Long.toString(locala.d - locala.jdField_a_of_type_Long));
        ((HashMap)localObject2).put("page_finish", Long.toString(locala.j - locala.jdField_a_of_type_Long));
        ((HashMap)localObject2).put("parser_elapsed", Long.toString(locala.g));
        ((HashMap)localObject2).put("layout_elapsed", Long.toString(locala.h));
        ((HashMap)localObject2).put("render_elapsed", Long.toString(locala.i));
        ((HashMap)localObject2).put("dom_loading", Long.toString(locala.l));
        ((HashMap)localObject2).put("dom_interactive", Long.toString(locala.m));
        ((HashMap)localObject2).put("dom_complete", Long.toString(locala.p));
        ((HashMap)localObject2).put("socket_reuse_pro", Float.toString(f2));
        ((HashMap)localObject2).put("quic_pro", Float.toString(f1));
        ((HashMap)localObject2).put("hostcount", Integer.toString(i));
        ((HashMap)localObject2).put("main_resource", locala.jdField_a_of_type_ComTencentSmttNetG$b.a(true));
        ((HashMap)localObject2).put("sub_resource_1", ((StringBuilder)localObject1).toString());
        ((HashMap)localObject2).put("sub_resource_2", localStringBuilder1.toString());
        ((HashMap)localObject2).put("sub_resource_3", localStringBuilder2.toString());
        ((HashMap)localObject2).put("sub_resource_4", localStringBuilder3.toString());
        ((HashMap)localObject2).put("sub_resource_5", localStringBuilder4.toString());
        ((HashMap)localObject2).put("version", "V1");
        localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("handlePagePerformance PageInfo client=");
        localStringBuilder1.append(paramTencentWebViewContentsClientAdapter.hashCode());
        localStringBuilder1.append(" url=");
        if (locala != null) {
          localObject1 = locala.jdField_a_of_type_JavaLangString;
        } else {
          localObject1 = "null";
        }
        localStringBuilder1.append((String)localObject1);
        localStringBuilder1.append(" firstScreen=");
        localStringBuilder1.append(locala.d - locala.jdField_a_of_type_Long);
        localStringBuilder1.append(" pageFinished=");
        localStringBuilder1.append(locala.j - locala.jdField_a_of_type_Long);
        a(localStringBuilder1.toString());
        SmttServiceProxy.getInstance().onReportPerformanceV4((HashMap)localObject2);
      }
      if ((paramAwSettings != null) && ((((TencentAwSettings)paramAwSettings).getDataFilterForRequestInfo() & 0x1) == 1))
      {
        paramAwSettings = new JSONObject();
        try
        {
          paramAwSettings.put("infotype", "2");
          paramAwSettings.put("url", locala.jdField_a_of_type_JavaLangString);
          paramAwSettings.put("apn_type", locala.jdField_a_of_type_Int);
          if ((locala.b == 0L) || (locala.b > locala.d)) {
            locala.b = locala.d;
          }
          paramAwSettings.put("first_word", locala.b);
          paramAwSettings.put("first_screen", locala.d);
          paramAwSettings.put("page_finish", locala.j);
          paramAwSettings.put("dom_loading", locala.l);
          paramAwSettings.put("dom_interactive", locala.m);
          paramAwSettings.put("dom_content_loaded_event_start", locala.n);
          paramAwSettings.put("dom_content_loaded_event_end", locala.o);
          paramAwSettings.put("dom_complete", locala.p);
          try
          {
            paramTencentWebViewContentsClientAdapter.onReportResourceAllInfoToUI(paramAwSettings, null, null, null);
          }
          catch (JSONException paramAwSettings) {}
          localObject1 = new StringBuilder();
        }
        catch (JSONException paramAwSettings) {}
        ((StringBuilder)localObject1).append("JSONException: ");
        ((StringBuilder)localObject1).append(paramAwSettings.toString());
        a(((StringBuilder)localObject1).toString());
      }
      if ((locala.c == 0L) || (locala.c > locala.e)) {
        locala.c = locala.e;
      }
      paramTencentWebViewContentsClientAdapter.reportFirstByteFScreenTime(locala.k, locala.e, locala.c);
    }
  }
  
  public void a(WebViewContentsClientAdapter paramWebViewContentsClientAdapter, long paramLong1, long paramLong2)
  {
    if (!jdField_a_of_type_Boolean) {
      return;
    }
    a locala = (a)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramWebViewContentsClientAdapter.hashCode());
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setFirstWordTime client=");
    localStringBuilder.append(paramWebViewContentsClientAdapter.hashCode());
    localStringBuilder.append(" time=");
    localStringBuilder.append(paramLong1);
    localStringBuilder.append(" url=");
    if (locala != null) {
      paramWebViewContentsClientAdapter = locala.jdField_a_of_type_JavaLangString;
    } else {
      paramWebViewContentsClientAdapter = "null";
    }
    localStringBuilder.append(paramWebViewContentsClientAdapter);
    localStringBuilder.append(" thread=");
    localStringBuilder.append(Thread.currentThread().getName());
    a(localStringBuilder.toString());
    if ((locala != null) && (locala.d == 0L) && (locala.b == 0L))
    {
      locala.a(paramLong1);
      locala.c(paramLong2);
    }
  }
  
  public void a(WebViewContentsClientAdapter paramWebViewContentsClientAdapter, String paramString)
  {
    if (!jdField_a_of_type_Boolean) {
      return;
    }
    long l = SystemClock.uptimeMillis();
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("init New PagePerformance client=");
    ((StringBuilder)localObject).append(paramWebViewContentsClientAdapter.hashCode());
    ((StringBuilder)localObject).append(" pageUrl=");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append(" thread=");
    ((StringBuilder)localObject).append(Thread.currentThread().getName());
    ((StringBuilder)localObject).append(" time=");
    ((StringBuilder)localObject).append(l);
    a(((StringBuilder)localObject).toString());
    localObject = new a(paramString, Apn.sApnTypeS, l);
    this.jdField_a_of_type_AndroidUtilSparseArray.put(paramWebViewContentsClientAdapter.hashCode(), localObject);
    if ((SmttServiceProxy.getInstance().isPerformanceLogRecordOpen()) && (((a)localObject).jdField_a_of_type_JavaLangString != null) && (paramString.startsWith("http")) && (!j.c(paramString).equals("debugx5.qq.com"))) {
      b.a(ContextHolder.getInstance().getContext()).a();
    }
  }
  
  public void a(TencentWebViewContentsClientAdapter paramTencentWebViewContentsClientAdapter, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, AwSettings paramAwSettings)
  {
    if (!jdField_a_of_type_Boolean) {
      return;
    }
    a locala = (a)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramTencentWebViewContentsClientAdapter.hashCode());
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setDocumentTimingInfo client=");
    localStringBuilder.append(paramTencentWebViewContentsClientAdapter.hashCode());
    localStringBuilder.append(" domLoading=");
    localStringBuilder.append(paramDouble1);
    localStringBuilder.append(" domInteractive=");
    localStringBuilder.append(paramDouble2);
    localStringBuilder.append(" domContentLoadedEventStart=");
    localStringBuilder.append(paramDouble3);
    localStringBuilder.append(" domContentLoadedEventEnd=");
    localStringBuilder.append(paramDouble4);
    localStringBuilder.append(" domComplete=");
    localStringBuilder.append(paramDouble5);
    a(localStringBuilder.toString());
    if (locala != null)
    {
      locala.a(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5);
      if ((locala.j > 0L) && (locala.d > 0L)) {
        b(paramTencentWebViewContentsClientAdapter, paramAwSettings);
      }
    }
  }
  
  public void a(TencentWebViewContentsClientAdapter paramTencentWebViewContentsClientAdapter, long paramLong)
  {
    if (!jdField_a_of_type_Boolean) {
      return;
    }
    a locala = (a)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramTencentWebViewContentsClientAdapter.hashCode());
    if (locala != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("setFirstByteTime client=");
      localStringBuilder.append(paramTencentWebViewContentsClientAdapter.hashCode());
      localStringBuilder.append(" FirstByteTime=");
      localStringBuilder.append(paramLong);
      localStringBuilder.append(" url=");
      localStringBuilder.append(locala.jdField_a_of_type_JavaLangString);
      localStringBuilder.append(" thread=");
      localStringBuilder.append(Thread.currentThread().getName());
      a(localStringBuilder.toString());
      locala.e(paramLong);
    }
  }
  
  public void a(TencentWebViewContentsClientAdapter paramTencentWebViewContentsClientAdapter, long paramLong1, long paramLong2, long paramLong3, long paramLong4)
  {
    if (!jdField_a_of_type_Boolean) {
      return;
    }
    paramTencentWebViewContentsClientAdapter = (a)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramTencentWebViewContentsClientAdapter.hashCode());
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setFirstScreenParameter PageInfo p=");
    localStringBuilder.append(paramTencentWebViewContentsClientAdapter);
    localStringBuilder.append(" jstime=");
    localStringBuilder.append(paramLong1);
    localStringBuilder.append(" parsetime=");
    localStringBuilder.append(paramLong2);
    localStringBuilder.append(" layouttime=");
    localStringBuilder.append(paramLong3);
    localStringBuilder.append(" painttime=");
    localStringBuilder.append(paramLong4);
    localStringBuilder.append(" thread=");
    localStringBuilder.append(Thread.currentThread().getName());
    a(localStringBuilder.toString());
    if (paramTencentWebViewContentsClientAdapter != null) {
      paramTencentWebViewContentsClientAdapter.a(paramLong1, paramLong2, paramLong3, paramLong4);
    }
  }
  
  public void a(TencentWebViewContentsClientAdapter paramTencentWebViewContentsClientAdapter, long paramLong1, long paramLong2, AwSettings paramAwSettings)
  {
    if (!jdField_a_of_type_Boolean) {
      return;
    }
    a locala = (a)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramTencentWebViewContentsClientAdapter.hashCode());
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setFirstScreenTime client=");
    localStringBuilder.append(paramTencentWebViewContentsClientAdapter.hashCode());
    localStringBuilder.append(" time=");
    localStringBuilder.append(paramLong1);
    localStringBuilder.append(" url=");
    Object localObject;
    if (locala != null) {
      localObject = locala.jdField_a_of_type_JavaLangString;
    } else {
      localObject = "null";
    }
    localStringBuilder.append((String)localObject);
    localStringBuilder.append(" thread=");
    localStringBuilder.append(Thread.currentThread().getName());
    a(localStringBuilder.toString());
    if (locala != null)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("setFirstScreenTime =");
      ((StringBuilder)localObject).append(paramLong2);
      a(((StringBuilder)localObject).toString());
      locala.b(paramLong1);
      locala.d(paramLong2);
      if (locala.j > 0L) {
        b(paramTencentWebViewContentsClientAdapter, paramAwSettings);
      }
    }
  }
  
  public void a(TencentWebViewContentsClientAdapter paramTencentWebViewContentsClientAdapter, AwSettings paramAwSettings)
  {
    if (!jdField_a_of_type_Boolean) {
      return;
    }
    a locala = (a)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramTencentWebViewContentsClientAdapter.hashCode());
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("markPageLoadFinished client=");
    localStringBuilder.append(paramTencentWebViewContentsClientAdapter.hashCode());
    localStringBuilder.append(" url=");
    String str;
    if (locala != null) {
      str = locala.jdField_a_of_type_JavaLangString;
    } else {
      str = "null";
    }
    localStringBuilder.append(str);
    localStringBuilder.append(" thread=");
    localStringBuilder.append(Thread.currentThread().getName());
    a(localStringBuilder.toString());
    if ((locala != null) && (locala.j == 0L)) {
      locala.j = SystemClock.uptimeMillis();
    }
    if ((locala != null) && (locala.d > 0L)) {
      b(paramTencentWebViewContentsClientAdapter, paramAwSettings);
    }
  }
  
  public void a(TencentWebViewContentsClientAdapter paramTencentWebViewContentsClientAdapter, AwContentsClient.AwWebRequestInfo paramAwWebRequestInfo)
  {
    if (!jdField_a_of_type_Boolean) {
      return;
    }
    Object localObject3 = (a)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramTencentWebViewContentsClientAdapter.hashCode());
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("recordResourcePerformance client=");
    ((StringBuilder)localObject2).append(paramTencentWebViewContentsClientAdapter.hashCode());
    ((StringBuilder)localObject2).append(" pageurl=");
    Object localObject1;
    if (localObject3 != null) {
      localObject1 = ((a)localObject3).jdField_a_of_type_JavaLangString;
    } else {
      localObject1 = "null";
    }
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(" url=");
    if (paramAwWebRequestInfo.performance != null) {
      localObject1 = paramAwWebRequestInfo.performance.url;
    } else {
      localObject1 = "null";
    }
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(" thread=");
    ((StringBuilder)localObject2).append(Thread.currentThread().getName());
    a(((StringBuilder)localObject2).toString());
    boolean bool2 = false;
    boolean bool1;
    if ((localObject3 != null) && (((a)localObject3).d > 0L)) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    if ((localObject3 != null) && (paramAwWebRequestInfo.performance != null) && (((a)localObject3).j == 0L))
    {
      if (((paramAwWebRequestInfo.performance.proxy_type & 0x1) != 1) && (paramAwWebRequestInfo.performance.was_cached))
      {
        localObject1 = paramAwWebRequestInfo.performance;
        ((AwContentsClient.AwWebResourcePerformanceInfo)localObject1).proxy_type |= 0x2000000;
      }
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("performance proxy_type=");
      ((StringBuilder)localObject1).append(paramAwWebRequestInfo.performance.proxy_type);
      ((StringBuilder)localObject1).append(" ");
      ((StringBuilder)localObject1).append(QProxyPolicies.proxyTypeString(paramAwWebRequestInfo.performance.proxy_type));
      ((StringBuilder)localObject1).append(" url=");
      ((StringBuilder)localObject1).append(paramAwWebRequestInfo.performance.url);
      a(((StringBuilder)localObject1).toString());
      if (paramAwWebRequestInfo.performance.resource_type == 0)
      {
        localObject1 = new HashMap();
        if ((paramAwWebRequestInfo.request != null) && (paramAwWebRequestInfo.request.requestHeaders != null)) {
          localObject1 = paramAwWebRequestInfo.request.requestHeaders;
        }
        localObject2 = new HashMap();
        if ((paramAwWebRequestInfo.response != null) && (paramAwWebRequestInfo.response.getResponseHeaders() != null)) {
          localObject2 = paramAwWebRequestInfo.response.getResponseHeaders();
        }
        ((a)localObject3).jdField_a_of_type_ComTencentSmttNetG$b = new b(paramAwWebRequestInfo.performance, false, (Map)localObject1, (Map)localObject2);
      }
      else
      {
        localObject1 = new HashMap();
        if ((paramAwWebRequestInfo.request != null) && (paramAwWebRequestInfo.request.requestHeaders != null)) {
          localObject1 = paramAwWebRequestInfo.request.requestHeaders;
        }
        localObject2 = new HashMap();
        if ((paramAwWebRequestInfo.response != null) && (paramAwWebRequestInfo.response.getResponseHeaders() != null)) {
          localObject2 = paramAwWebRequestInfo.response.getResponseHeaders();
        }
        if (paramAwWebRequestInfo.performance.url.startsWith("http")) {
          ((a)localObject3).a(new b(paramAwWebRequestInfo.performance, bool1, (Map)localObject1, (Map)localObject2));
        }
      }
    }
    if ((paramAwWebRequestInfo.performance != null) && ((paramAwWebRequestInfo.filter & 0x1) == 1) && (((paramAwWebRequestInfo.filter & 0x4) != 4) || ((paramAwWebRequestInfo.performance.error_id != 0) && (paramAwWebRequestInfo.performance.error_id != -3))))
    {
      try
      {
        localObject1 = new JSONObject();
        try
        {
          ((JSONObject)localObject1).put("infotype", "1");
          ((JSONObject)localObject1).put("url", paramAwWebRequestInfo.performance.url);
          ((JSONObject)localObject1).put("resource_trigger", paramAwWebRequestInfo.performance.resourceRequestTriggerTime);
          ((JSONObject)localObject1).put("resource_handle", paramAwWebRequestInfo.performance.resourceRequestHandleTime);
          ((JSONObject)localObject1).put("redirect_start", paramAwWebRequestInfo.performance.redirect_start);
          ((JSONObject)localObject1).put("redirect_end", paramAwWebRequestInfo.performance.redirect_end);
          ((JSONObject)localObject1).put("redirect_times", paramAwWebRequestInfo.performance.redirect_times);
          ((JSONObject)localObject1).put("dns_start", paramAwWebRequestInfo.performance.dns_start);
          ((JSONObject)localObject1).put("dns_end", paramAwWebRequestInfo.performance.dns_end);
          ((JSONObject)localObject1).put("connect_start", paramAwWebRequestInfo.performance.connect_start);
          ((JSONObject)localObject1).put("connect_end", paramAwWebRequestInfo.performance.connect_end);
          ((JSONObject)localObject1).put("ssl_handshake_start", paramAwWebRequestInfo.performance.ssl_handshake_start);
          ((JSONObject)localObject1).put("ssl_handshake_end", paramAwWebRequestInfo.performance.ssl_handshake_end);
          ((JSONObject)localObject1).put("send_start", paramAwWebRequestInfo.performance.send_start);
          ((JSONObject)localObject1).put("send_end", paramAwWebRequestInfo.performance.send_end);
          ((JSONObject)localObject1).put("recv_start", paramAwWebRequestInfo.performance.recv_start);
          ((JSONObject)localObject1).put("recv_end", paramAwWebRequestInfo.performance.recv_end);
          ((JSONObject)localObject1).put("session_resumption", paramAwWebRequestInfo.performance.session_resumption);
          ((JSONObject)localObject1).put("socket_reused", paramAwWebRequestInfo.performance.socket_reused);
          ((JSONObject)localObject1).put("cipher_suit", paramAwWebRequestInfo.performance.cipher_suit);
          ((JSONObject)localObject1).put("cached", paramAwWebRequestInfo.performance.was_cached);
          ((JSONObject)localObject1).put("expired_time", paramAwWebRequestInfo.performance.expired_time);
          ((JSONObject)localObject1).put("type", paramAwWebRequestInfo.performance.resource_type);
          ((JSONObject)localObject1).put("errorcode", paramAwWebRequestInfo.performance.error_id);
          ((JSONObject)localObject1).put("website_address", paramAwWebRequestInfo.performance.website_address);
          bool1 = bool2;
          if (localObject3 != null)
          {
            bool1 = bool2;
            if (((a)localObject3).d == 0L) {
              bool1 = true;
            }
          }
          ((JSONObject)localObject1).put("first_screen_resource", bool1);
          ((JSONObject)localObject1).put("recv_bytes", paramAwWebRequestInfo.performance.recv_bytes);
        }
        catch (JSONException localJSONException1) {}
        localObject3 = new StringBuilder();
      }
      catch (JSONException localJSONException2)
      {
        localObject1 = null;
      }
      ((StringBuilder)localObject3).append("JSONException: ");
      ((StringBuilder)localObject3).append(localJSONException2.toString());
      a(((StringBuilder)localObject3).toString());
      localJSONException2.printStackTrace();
    }
    else
    {
      localObject1 = null;
    }
    if ((((paramAwWebRequestInfo.filter & 0x1) == 1) || ((paramAwWebRequestInfo.filter & 0x2) == 2)) && (((paramAwWebRequestInfo.filter & 0x4) != 4) || ((paramAwWebRequestInfo.error.errorCode != 0) && (paramAwWebRequestInfo.error.errorCode != -3))))
    {
      if (paramAwWebRequestInfo.request == null)
      {
        paramAwWebRequestInfo.response = null;
        paramAwWebRequestInfo.error = null;
      }
      paramTencentWebViewContentsClientAdapter.onReportResourceAllInfoToUI((JSONObject)localObject1, paramAwWebRequestInfo.request, paramAwWebRequestInfo.response, paramAwWebRequestInfo.error);
    }
    if ((paramAwWebRequestInfo.request != null) && (paramAwWebRequestInfo.response != null)) {
      paramTencentWebViewContentsClientAdapter.onReportMainResourceResponse(paramAwWebRequestInfo.request.url, paramAwWebRequestInfo.response);
    }
  }
  
  public boolean a(String paramString)
  {
    boolean bool = SmttServiceProxy.getInstance().isStatReportPage(paramString);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("shouldReportPerformanceToServer return ");
    localStringBuilder.append(bool);
    localStringBuilder.append(" url=");
    localStringBuilder.append(paramString);
    a(localStringBuilder.toString());
    return bool;
  }
  
  public class a
  {
    public int a;
    public long a;
    public g.b a;
    public String a;
    public ArrayList<g.b> a;
    public long b = 0L;
    public long c = 0L;
    public long d = 0L;
    public long e = 0L;
    public long f = 0L;
    public long g = 0L;
    public long h = 0L;
    public long i = 0L;
    public long j = 0L;
    public long k = 0L;
    public long l = 0L;
    public long m = 0L;
    public long n = 0L;
    public long o = 0L;
    public long p = 0L;
    
    a(String paramString, int paramInt, long paramLong)
    {
      this.jdField_a_of_type_Long = 0L;
      this.jdField_a_of_type_ComTencentSmttNetG$b = null;
      this.jdField_a_of_type_JavaUtilArrayList = null;
      this.jdField_a_of_type_JavaLangString = paramString;
      this.jdField_a_of_type_Int = paramInt;
      this.jdField_a_of_type_Long = paramLong;
    }
    
    public ArrayList<g.b> a()
    {
      return this.jdField_a_of_type_JavaUtilArrayList;
    }
    
    public void a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5)
    {
      this.l = Double.valueOf(paramDouble1 * 1000.0D).longValue();
      this.m = Double.valueOf(paramDouble2 * 1000.0D).longValue();
      this.n = Double.valueOf(paramDouble3 * 1000.0D).longValue();
      this.o = Double.valueOf(paramDouble4 * 1000.0D).longValue();
      this.p = Double.valueOf(paramDouble5 * 1000.0D).longValue();
    }
    
    public void a(long paramLong)
    {
      this.b = paramLong;
    }
    
    public void a(long paramLong1, long paramLong2, long paramLong3, long paramLong4)
    {
      this.f = paramLong1;
      this.g = paramLong2;
      this.h = paramLong3;
      this.i = paramLong4;
    }
    
    void a(g.b paramb)
    {
      if (this.jdField_a_of_type_JavaUtilArrayList == null) {
        this.jdField_a_of_type_JavaUtilArrayList = new ArrayList();
      }
      this.jdField_a_of_type_JavaUtilArrayList.add(paramb);
    }
    
    public void b(long paramLong)
    {
      this.d = paramLong;
    }
    
    public void c(long paramLong)
    {
      this.c = paramLong;
    }
    
    public void d(long paramLong)
    {
      this.e = paramLong;
    }
    
    public void e(long paramLong)
    {
      this.k = paramLong;
    }
  }
  
  public class b
  {
    public int a;
    public long a;
    public String a;
    public Map<String, String> a;
    public boolean a;
    public int b;
    public long b;
    public String b;
    public Map<String, String> b;
    public boolean b;
    public int c;
    public long c;
    public String c;
    public boolean c;
    public int d;
    public long d;
    public String d;
    public boolean d;
    public int e;
    public long e;
    public int f;
    public long f;
    public int g;
    public long g;
    public int h;
    public long h;
    public int i;
    public long i;
    public int j;
    public long j;
    public long k;
    public long l;
    public long m;
    public long n;
    public long o;
    public long p;
    public long q;
    
    b(boolean paramBoolean, Map<String, String> paramMap1, Map<String, String> paramMap2)
    {
      this.jdField_a_of_type_JavaUtilMap = new HashMap();
      this.jdField_b_of_type_JavaUtilMap = new HashMap();
      if (paramMap2 != null)
      {
        this$1 = paramMap2.keySet().iterator();
        while (g.this.hasNext())
        {
          String str = (String)g.this.next();
          this.jdField_a_of_type_JavaUtilMap.put(str, paramMap2.get(str));
        }
      }
      Object localObject;
      if (localObject != null)
      {
        this$1 = ((Map)localObject).keySet().iterator();
        while (g.this.hasNext())
        {
          paramMap2 = (String)g.this.next();
          this.jdField_b_of_type_JavaUtilMap.put(paramMap2, ((Map)localObject).get(paramMap2));
        }
      }
      this.jdField_a_of_type_JavaLangString = paramBoolean.url;
      this.jdField_b_of_type_JavaLangString = paramBoolean.referer;
      this.jdField_a_of_type_Int = paramBoolean.proxy_type;
      this.jdField_c_of_type_JavaLangString = paramBoolean.website_address;
      this.jdField_a_of_type_Long = paramBoolean.request_begin;
      this.jdField_b_of_type_Long = paramBoolean.dns_start;
      this.jdField_c_of_type_Long = paramBoolean.dns_end;
      this.jdField_d_of_type_Long = paramBoolean.connect_start;
      this.jdField_e_of_type_Long = paramBoolean.connect_end;
      this.jdField_f_of_type_Long = paramBoolean.ssl_handshake_start;
      this.jdField_g_of_type_Long = paramBoolean.ssl_handshake_end;
      this.jdField_h_of_type_Long = paramBoolean.send_start;
      this.jdField_i_of_type_Long = paramBoolean.send_end;
      this.jdField_j_of_type_Long = paramBoolean.recv_start;
      this.k = paramBoolean.recv_end;
      this.l = paramBoolean.recv_bytes;
      this.jdField_c_of_type_Int = paramBoolean.status_code;
      this.jdField_d_of_type_Int = paramBoolean.session_resumption;
      this.jdField_e_of_type_Int = paramBoolean.cipher_suit;
      this.jdField_d_of_type_JavaLangString = paramBoolean.proxy_data;
      this.jdField_a_of_type_Boolean = paramBoolean.socket_reused;
      this.jdField_b_of_type_Boolean = paramBoolean.was_cached;
      this.m = paramBoolean.expired_time;
      this.jdField_f_of_type_Int = paramBoolean.resource_type;
      this.jdField_g_of_type_Int = paramBoolean.error_id;
      this.jdField_c_of_type_Boolean = paramMap1;
      this.jdField_d_of_type_Boolean = paramBoolean.report_server;
      this.n = paramBoolean.resourceRequestTriggerTime;
      this.o = paramBoolean.resourceRequestHandleTime;
      this.jdField_b_of_type_Int = paramBoolean.qProxyStrategy;
      this.jdField_h_of_type_Int = paramBoolean.connectInfo;
      this.jdField_i_of_type_Int = paramBoolean.rtt;
      this.p = paramBoolean.redirect_start;
      this.q = paramBoolean.redirect_end;
      this.jdField_j_of_type_Int = paramBoolean.redirect_times;
      if (this.jdField_b_of_type_Long == 0L) {
        this.jdField_b_of_type_Long = this.jdField_a_of_type_Long;
      }
      if (this.jdField_c_of_type_Long == 0L) {
        this.jdField_c_of_type_Long = this.jdField_a_of_type_Long;
      }
      if (this.jdField_d_of_type_Long == 0L) {
        this.jdField_d_of_type_Long = this.jdField_a_of_type_Long;
      }
      if (this.jdField_e_of_type_Long == 0L) {
        this.jdField_e_of_type_Long = this.jdField_a_of_type_Long;
      }
    }
    
    public String a(boolean paramBoolean)
    {
      String str = "mainresource";
      if (!paramBoolean) {
        if (this.jdField_a_of_type_JavaLangString.length() > 256) {
          str = this.jdField_a_of_type_JavaLangString.substring(0, 255);
        } else {
          str = this.jdField_a_of_type_JavaLangString;
        }
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(str);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_f_of_type_Int);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_a_of_type_Int);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_b_of_type_Int);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_c_of_type_JavaLangString);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_c_of_type_Long - this.jdField_b_of_type_Long);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_e_of_type_Long - this.jdField_d_of_type_Long);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_g_of_type_Long - this.jdField_f_of_type_Long);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_j_of_type_Long - this.jdField_i_of_type_Long);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_i_of_type_Long - this.jdField_h_of_type_Long);
      localStringBuilder.append("||");
      localStringBuilder.append(this.k - this.jdField_j_of_type_Long);
      localStringBuilder.append("||");
      localStringBuilder.append(0);
      localStringBuilder.append("||");
      localStringBuilder.append(this.l);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_d_of_type_Int);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_e_of_type_Int);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_d_of_type_JavaLangString);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_a_of_type_Boolean);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_b_of_type_Boolean);
      localStringBuilder.append("||");
      localStringBuilder.append(this.m);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_g_of_type_Int);
      localStringBuilder.append("||");
      localStringBuilder.append(this.n);
      localStringBuilder.append("||");
      localStringBuilder.append(this.o);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_c_of_type_Int);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_h_of_type_Int);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_h_of_type_Long - this.jdField_d_of_type_Long);
      return localStringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */