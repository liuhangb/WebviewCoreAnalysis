package com.tencent.smtt.b;

import android.os.Handler;
import android.os.HandlerThread;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.FileUtils;
import com.tencent.smtt.net.g.a;
import com.tencent.smtt.net.g.b;
import com.tencent.smtt.util.MttTimingLog.c;
import com.tencent.smtt.util.X5LogUploadManager;
import com.tencent.smtt.util.X5LogUploadManager.b;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a
{
  public static float a = 0.0F;
  public static int a = 0;
  public static long a = 0L;
  private static Handler jdField_a_of_type_AndroidOsHandler;
  private static HandlerThread jdField_a_of_type_AndroidOsHandlerThread;
  public static g.a a;
  public static String a = "";
  public static ArrayList<Long> a;
  public static boolean a = true;
  public static float b = 0.0F;
  public static int b = 0;
  public static long b = 0L;
  public static String b = "";
  public static ArrayList<Integer> b;
  public static int c = 0;
  public static long c = 0L;
  public static String c = "";
  public static ArrayList<Integer> c;
  public static int d;
  public static long d;
  public static ArrayList<Long> d;
  public static int e;
  public static ArrayList<Integer> e;
  public static ArrayList<Integer> f = new ArrayList();
  public static ArrayList<Long> g = new ArrayList();
  public static ArrayList<Integer> h = new ArrayList();
  public static ArrayList<Integer> i = new ArrayList();
  public static ArrayList<Long> j = new ArrayList();
  public static ArrayList<Long> k = new ArrayList();
  public static ArrayList<Long> l = new ArrayList();
  public static ArrayList<Long> m = new ArrayList();
  public static ArrayList<Long> n = new ArrayList();
  public static ArrayList<Long> o = new ArrayList();
  
  static
  {
    jdField_a_of_type_JavaUtilArrayList = new ArrayList();
    jdField_b_of_type_JavaUtilArrayList = new ArrayList();
    jdField_c_of_type_JavaUtilArrayList = new ArrayList();
    jdField_d_of_type_JavaUtilArrayList = new ArrayList();
    jdField_e_of_type_JavaUtilArrayList = new ArrayList();
  }
  
  public a()
  {
    d();
  }
  
  public static a a()
  {
    return a.a();
  }
  
  private void a(String paramString)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("psw", SmttServiceProxy.getInstance().getPasswordKey());
      localJSONObject.put("url", jdField_a_of_type_JavaLangString);
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    paramString = new X5LogUploadManager.b(paramString, "PerformanceLog", "PerformanceLog", localJSONObject);
    X5LogUploadManager.a().a(paramString);
  }
  
  private void c()
  {
    try
    {
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(FileUtils.getCacheDir(ContextHolder.getInstance().getApplicationContext()));
      ((StringBuilder)localObject1).append("/");
      ((StringBuilder)localObject1).append("performancelog.inf");
      localObject1 = new FileWriter(((StringBuilder)localObject1).toString());
      JSONObject localJSONObject = new JSONObject();
      JSONArray localJSONArray1 = new JSONArray();
      localJSONObject.put("page_url", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaLangString);
      jdField_a_of_type_JavaLangString = jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaLangString;
      localJSONObject.put("uint32_cpu_count", jdField_a_of_type_Int);
      localJSONObject.put("uint64_cpu_hz", jdField_a_of_type_Long);
      localJSONObject.put("uint64_memory_size", jdField_b_of_type_Long);
      localJSONObject.put("str_phone_type", jdField_b_of_type_JavaLangString);
      localJSONObject.put("uint32_system_version", jdField_b_of_type_Int);
      localJSONObject.put("uint32_is_system_kernel", jdField_c_of_type_JavaLangString);
      localJSONObject.put("frame_start_time", jdField_c_of_type_Long);
      localJSONObject.put("frame_end_time", jdField_d_of_type_Long);
      localJSONObject.put("layer_num", jdField_d_of_type_Int);
      localJSONObject.put("total_layer_mem", jdField_e_of_type_Int);
      localJSONObject.put("min_layer_size", jdField_a_of_type_Float);
      localJSONObject.put("max_layer_size", jdField_b_of_type_Float);
      Object localObject5 = new JSONArray();
      Object localObject4 = new JSONArray();
      Object localObject3 = new JSONArray();
      Object localObject2 = new JSONArray();
      JSONArray localJSONArray2 = new JSONArray();
      JSONArray localJSONArray3 = new JSONArray();
      int i2 = 0;
      int i1 = 0;
      Object localObject6;
      while (i1 < jdField_a_of_type_JavaUtilArrayList.size())
      {
        localObject6 = new JSONObject();
        ((JSONObject)localObject6).put("cpuRecordTime", jdField_a_of_type_JavaUtilArrayList.get(i1));
        ((JSONObject)localObject6).put("cpuSystemUsage", jdField_b_of_type_JavaUtilArrayList.get(i1));
        ((JSONObject)localObject6).put("cpuHostProcessUsage", jdField_c_of_type_JavaUtilArrayList.get(i1));
        ((JSONArray)localObject5).put(localObject6);
        i1 += 1;
      }
      localJSONObject.put("cpuInfoJsonArr", localObject5);
      i1 = 0;
      while (i1 < jdField_d_of_type_JavaUtilArrayList.size())
      {
        localObject5 = new JSONObject();
        ((JSONObject)localObject5).put("memoryRecordTime", jdField_d_of_type_JavaUtilArrayList.get(i1));
        ((JSONObject)localObject5).put("memorySystemUsage", jdField_e_of_type_JavaUtilArrayList.get(i1));
        ((JSONObject)localObject5).put("memoryHostProcessUsage", f.get(i1));
        ((JSONArray)localObject4).put(localObject5);
        i1 += 1;
      }
      localJSONObject.put("memoryInfoJsonArr", localObject4);
      i1 = 0;
      while (i1 < j.size())
      {
        localObject4 = new JSONObject();
        ((JSONObject)localObject4).put("networkRecordTime", j.get(i1));
        ((JSONObject)localObject4).put("networkSystemTotalReceivedBytes", k.get(i1));
        ((JSONObject)localObject4).put("networkSystemTotalSentBytes", l.get(i1));
        ((JSONObject)localObject4).put("networkHostProcessReceivedBytes", m.get(i1));
        ((JSONObject)localObject4).put("networkUint64HostProcessSentBytes", n.get(i1));
        ((JSONArray)localObject3).put(localObject4);
        i1 += 1;
      }
      localJSONObject.put("networkInfoJsonArr", localObject3);
      i1 = 0;
      while (i1 < g.size())
      {
        localObject3 = new JSONObject();
        ((JSONObject)localObject3).put("gpuRecordTime", g.get(i1));
        ((JSONObject)localObject3).put("gpuHostProcessUsage", h.get(i1));
        ((JSONObject)localObject3).put("gpuHostSystemUsage", i.get(i1));
        ((JSONArray)localObject2).put(localObject3);
        i1 += 1;
      }
      localJSONObject.put("gpuInfoJsonArr", localObject2);
      localJSONObject.put("apn_type", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_Int);
      localJSONObject.put("page_start", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_Long);
      localJSONObject.put("first_word", jdField_a_of_type_ComTencentSmttNetG$a.jdField_b_of_type_Long);
      localJSONObject.put("first_word_stamp", jdField_a_of_type_ComTencentSmttNetG$a.jdField_c_of_type_Long);
      localJSONObject.put("first_screen", jdField_a_of_type_ComTencentSmttNetG$a.jdField_d_of_type_Long);
      localJSONObject.put("first_screen_stamp", jdField_a_of_type_ComTencentSmttNetG$a.jdField_e_of_type_Long);
      localJSONObject.put("js", jdField_a_of_type_ComTencentSmttNetG$a.jdField_f_of_type_Long);
      localJSONObject.put("parse", jdField_a_of_type_ComTencentSmttNetG$a.jdField_g_of_type_Long);
      localJSONObject.put("layout", jdField_a_of_type_ComTencentSmttNetG$a.jdField_h_of_type_Long);
      localJSONObject.put("paint", jdField_a_of_type_ComTencentSmttNetG$a.jdField_i_of_type_Long);
      localJSONObject.put("page_finish", jdField_a_of_type_ComTencentSmttNetG$a.jdField_j_of_type_Long);
      localJSONObject.put("first_byte_stamp", jdField_a_of_type_ComTencentSmttNetG$a.k);
      localJSONObject.put("dom_loading", jdField_a_of_type_ComTencentSmttNetG$a.l);
      localJSONObject.put("dom_interactive", jdField_a_of_type_ComTencentSmttNetG$a.m);
      localJSONObject.put("dom_contentloadedeventstart", jdField_a_of_type_ComTencentSmttNetG$a.n);
      localJSONObject.put("dom_contentloadedeventend", jdField_a_of_type_ComTencentSmttNetG$a.o);
      localJSONObject.put("dom_complete", jdField_a_of_type_ComTencentSmttNetG$a.p);
      localObject2 = new JSONObject();
      ((JSONObject)localObject2).put("url", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_a_of_type_JavaLangString);
      ((JSONObject)localObject2).put("referer", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_b_of_type_JavaLangString);
      ((JSONObject)localObject2).put("proxy_type", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_a_of_type_Int);
      ((JSONObject)localObject2).put("qProxyStrategy", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_b_of_type_Int);
      ((JSONObject)localObject2).put("website_address", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_c_of_type_JavaLangString);
      ((JSONObject)localObject2).put("request_begin", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_a_of_type_Long);
      ((JSONObject)localObject2).put("dns_start", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_b_of_type_Long);
      ((JSONObject)localObject2).put("dns_end", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_c_of_type_Long);
      ((JSONObject)localObject2).put("connect_start", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_d_of_type_Long);
      ((JSONObject)localObject2).put("connect_end", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_e_of_type_Long);
      ((JSONObject)localObject2).put("ssl_handshake_start", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_f_of_type_Long);
      ((JSONObject)localObject2).put("ssl_handshake_end", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_g_of_type_Long);
      ((JSONObject)localObject2).put("send_start", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_h_of_type_Long);
      ((JSONObject)localObject2).put("send_end", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_i_of_type_Long);
      ((JSONObject)localObject2).put("recv_start", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_j_of_type_Long);
      ((JSONObject)localObject2).put("recv_end", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.k);
      ((JSONObject)localObject2).put("recv_bytes", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.l);
      ((JSONObject)localObject2).put("status_code", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_c_of_type_Int);
      ((JSONObject)localObject2).put("session_resumption", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_d_of_type_Int);
      ((JSONObject)localObject2).put("cipher_suit", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_e_of_type_Int);
      ((JSONObject)localObject2).put("proxy_data", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_d_of_type_JavaLangString);
      ((JSONObject)localObject2).put("socket_reused", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_a_of_type_Boolean);
      ((JSONObject)localObject2).put("was_cached", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_b_of_type_Boolean);
      ((JSONObject)localObject2).put("expired_time", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.m);
      ((JSONObject)localObject2).put("resource_type", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_f_of_type_Int);
      ((JSONObject)localObject2).put("error_id", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_g_of_type_Int);
      ((JSONObject)localObject2).put("after_first_screen", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_c_of_type_Boolean);
      ((JSONObject)localObject2).put("report_server", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_d_of_type_Boolean);
      ((JSONObject)localObject2).put("resourceRequestTriggerTime", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.n);
      ((JSONObject)localObject2).put("resourceRequestHandleTime", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.o);
      ((JSONObject)localObject2).put("connectInfo", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_h_of_type_Int);
      ((JSONObject)localObject2).put("rtt_time", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_i_of_type_Int);
      ((JSONObject)localObject2).put("redirect_start", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.p);
      ((JSONObject)localObject2).put("redirect_end", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.q);
      ((JSONObject)localObject2).put("redirect_times", jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_j_of_type_Int);
      localObject3 = new JSONObject();
      localObject4 = jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_a_of_type_JavaUtilMap.keySet().iterator();
      while (((Iterator)localObject4).hasNext())
      {
        localObject5 = (String)((Iterator)localObject4).next();
        ((JSONObject)localObject3).put((String)localObject5, jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_a_of_type_JavaUtilMap.get(localObject5));
      }
      localObject4 = new JSONObject();
      localObject5 = jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_b_of_type_JavaUtilMap.keySet().iterator();
      while (((Iterator)localObject5).hasNext())
      {
        localObject6 = (String)((Iterator)localObject5).next();
        ((JSONObject)localObject4).put((String)localObject6, jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_ComTencentSmttNetG$b.jdField_b_of_type_JavaUtilMap.get(localObject6));
      }
      ((JSONObject)localObject2).put("requestHeaders", localObject3);
      ((JSONObject)localObject2).put("responseHeaders", localObject4);
      localJSONObject.put("mainResoucePerformance", localObject2);
      i1 = 0;
      while (i1 < jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.size())
      {
        localObject2 = new JSONObject();
        ((JSONObject)localObject2).put("url", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_a_of_type_JavaLangString);
        ((JSONObject)localObject2).put("referer", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_b_of_type_JavaLangString);
        ((JSONObject)localObject2).put("proxy_type", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_a_of_type_Int);
        ((JSONObject)localObject2).put("qProxyStrategy", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_b_of_type_Int);
        ((JSONObject)localObject2).put("website_address", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_c_of_type_JavaLangString);
        ((JSONObject)localObject2).put("request_begin", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_a_of_type_Long);
        ((JSONObject)localObject2).put("dns_start", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_b_of_type_Long);
        ((JSONObject)localObject2).put("dns_end", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_c_of_type_Long);
        ((JSONObject)localObject2).put("connect_start", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_d_of_type_Long);
        ((JSONObject)localObject2).put("connect_end", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_e_of_type_Long);
        ((JSONObject)localObject2).put("ssl_handshake_start", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_f_of_type_Long);
        ((JSONObject)localObject2).put("ssl_handshake_end", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_g_of_type_Long);
        ((JSONObject)localObject2).put("send_start", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_h_of_type_Long);
        ((JSONObject)localObject2).put("send_end", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_i_of_type_Long);
        ((JSONObject)localObject2).put("recv_start", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_j_of_type_Long);
        ((JSONObject)localObject2).put("recv_end", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).k);
        ((JSONObject)localObject2).put("recv_bytes", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).l);
        ((JSONObject)localObject2).put("status_code", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_c_of_type_Int);
        ((JSONObject)localObject2).put("session_resumption", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_d_of_type_Int);
        ((JSONObject)localObject2).put("cipher_suit", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_e_of_type_Int);
        ((JSONObject)localObject2).put("proxy_data", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_d_of_type_JavaLangString);
        ((JSONObject)localObject2).put("socket_reused", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_a_of_type_Boolean);
        ((JSONObject)localObject2).put("was_cached", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_b_of_type_Boolean);
        ((JSONObject)localObject2).put("expired_time", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).m);
        ((JSONObject)localObject2).put("resource_type", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_f_of_type_Int);
        ((JSONObject)localObject2).put("error_id", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_g_of_type_Int);
        ((JSONObject)localObject2).put("after_first_screen", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_c_of_type_Boolean);
        ((JSONObject)localObject2).put("report_server", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_d_of_type_Boolean);
        ((JSONObject)localObject2).put("resourceRequestTriggerTime", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).n);
        ((JSONObject)localObject2).put("resourceRequestHandleTime", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).o);
        ((JSONObject)localObject2).put("connectInfo", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_h_of_type_Int);
        ((JSONObject)localObject2).put("rtt_time", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_i_of_type_Int);
        ((JSONObject)localObject2).put("redirect_start", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).p);
        ((JSONObject)localObject2).put("redirect_end", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).q);
        ((JSONObject)localObject2).put("redirect_times", ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_j_of_type_Int);
        localObject3 = new JSONObject();
        localObject4 = ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_a_of_type_JavaUtilMap.keySet().iterator();
        while (((Iterator)localObject4).hasNext())
        {
          localObject5 = (String)((Iterator)localObject4).next();
          ((JSONObject)localObject3).put((String)localObject5, ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_a_of_type_JavaUtilMap.get(localObject5));
        }
        localObject4 = new JSONObject();
        localObject5 = ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_b_of_type_JavaUtilMap.keySet().iterator();
        while (((Iterator)localObject5).hasNext())
        {
          localObject6 = (String)((Iterator)localObject5).next();
          ((JSONObject)localObject4).put((String)localObject6, ((g.b)jdField_a_of_type_ComTencentSmttNetG$a.jdField_a_of_type_JavaUtilArrayList.get(i1)).jdField_b_of_type_JavaUtilMap.get(localObject6));
        }
        ((JSONObject)localObject2).put("requestHeaders", localObject3);
        ((JSONObject)localObject2).put("responseHeaders", localObject4);
        localJSONArray2.put(localObject2);
        i1 += 1;
      }
      localJSONObject.put("subResourceJsonArr", localJSONArray2);
      i1 = i2;
      while (i1 < o.size())
      {
        localJSONArray3.put(o.get(i1));
        i1 += 1;
      }
      localJSONObject.put("fpsJsonArr", localJSONArray3);
      localJSONArray1.put(localJSONObject);
      jdField_a_of_type_Boolean = true;
      ((FileWriter)localObject1).write(localJSONArray1.toString());
      ((FileWriter)localObject1).close();
      return;
    }
    catch (Exception localException) {}
  }
  
  private void d() {}
  
  public void a()
  {
    BrowserExecutorSupplier.postForDbTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        a.a(a.this);
        a.this.b();
      }
    });
  }
  
  public void b()
  {
    try
    {
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(FileUtils.getCacheDir(ContextHolder.getInstance().getApplicationContext()));
      ((StringBuilder)localObject1).append("/");
      ((StringBuilder)localObject1).append("performancelog.inf");
      localObject1 = ((StringBuilder)localObject1).toString();
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append(FileUtils.getCacheDir(ContextHolder.getInstance().getApplicationContext()));
      ((StringBuilder)localObject2).append("/");
      ((StringBuilder)localObject2).append("performancelog.zip");
      localObject2 = ((StringBuilder)localObject2).toString();
      new MttTimingLog.c(new String[] { localObject1 }, (String)localObject2).a();
      a((String)localObject2);
      new File((String)localObject1).delete();
      return;
    }
    catch (Exception localException) {}
  }
  
  private static class a
  {
    private static final a a = new a();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\b\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */