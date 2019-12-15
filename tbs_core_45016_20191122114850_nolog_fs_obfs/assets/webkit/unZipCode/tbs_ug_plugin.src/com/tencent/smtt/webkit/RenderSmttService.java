package com.tencent.smtt.webkit;

import com.tencent.mtt.AppInfoHolder;
import com.tencent.mtt.AppInfoHolder.AppInfoID;
import com.tencent.smtt.util.X5Log;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.json.JSONArray;
import org.json.JSONObject;

@JNINamespace("tencent")
public class RenderSmttService
{
  private static int jdField_a_of_type_Int = 10;
  private static String jdField_a_of_type_JavaLangString = "RenderSmttService";
  private static ArrayList<String> jdField_a_of_type_JavaUtilArrayList;
  private static HashMap<String, String> jdField_a_of_type_JavaUtilHashMap;
  private static boolean jdField_a_of_type_Boolean = false;
  private static int jdField_b_of_type_Int = 0;
  private static String jdField_b_of_type_JavaLangString = "";
  private static HashMap<String, String> jdField_b_of_type_JavaUtilHashMap;
  private static int jdField_c_of_type_Int = 0;
  private static String jdField_c_of_type_JavaLangString = "";
  
  public static void a()
  {
    if (jdField_a_of_type_JavaUtilHashMap == null) {
      jdField_a_of_type_JavaUtilHashMap = new HashMap();
    }
    if (jdField_a_of_type_JavaUtilArrayList == null)
    {
      jdField_a_of_type_JavaUtilArrayList = new ArrayList();
      jdField_a_of_type_JavaUtilArrayList.add("MTT_CORE_CONFIG_RENDER_REPORT_COMMON");
      jdField_a_of_type_JavaUtilArrayList.add("MTT_CORE_CONFIG_COMPOSITOR_TIMING_INFO");
      jdField_a_of_type_JavaUtilArrayList.add("MTT_CORE_CONFIG_THREAD_JS_TIMEOUT");
      jdField_a_of_type_JavaUtilArrayList.add("MTT_CORE_CONFIG_BUSINESS_RESPONSE_TIMING");
      jdField_a_of_type_JavaUtilArrayList.add("MTT_CORE_CONFIG_EXPERIENCE_FIXED_AD");
      jdField_a_of_type_JavaUtilArrayList.add("MTT_CORE_CONFIG_EXPERIENCE_CLICK_HIJACK");
      jdField_a_of_type_JavaUtilArrayList.add("MTT_CORE_CONFIG_RENDER_MEM_INFO");
      jdField_a_of_type_JavaUtilArrayList.add("WEBPAGE_READ_MODE_CONFIG_ANDROID");
      jdField_a_of_type_JavaUtilArrayList.add("WEBPAGE_READ_MODE_PRECHECK_ANDROID");
      jdField_a_of_type_JavaUtilArrayList.add("WEBPAGE_READ_MODE_BOTTOM_OPERATION_ANDROID");
    }
    int i = 0;
    while (i < jdField_a_of_type_JavaUtilArrayList.size())
    {
      try
      {
        localObject3 = (String)jdField_a_of_type_JavaUtilArrayList.get(i);
        localObject2 = SmttServiceProxy.getInstance().getCloudStringSwitch((String)localObject3, null);
        if (localObject2 == null) {
          break label420;
        }
        localObject1 = localObject2;
        if (localObject2 != "") {}
      }
      catch (Exception localException)
      {
        for (;;)
        {
          Object localObject3;
          Object localObject2;
          Object localObject1;
          int j;
          continue;
          String str1 = "{}";
        }
      }
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("RenderSmttService.initReportConfigMap cloudKey=");
      ((StringBuilder)localObject2).append((String)localObject3);
      ((StringBuilder)localObject2).append(", cloudJson=");
      ((StringBuilder)localObject2).append((String)localObject1);
      X5Log.i("RENDER", ((StringBuilder)localObject2).toString());
      localObject1 = new JSONObject((String)localObject1).optJSONArray("list");
      if (localObject1 != null)
      {
        j = 0;
        while (j < ((JSONArray)localObject1).length())
        {
          localObject3 = ((JSONArray)localObject1).optJSONObject(j);
          if (localObject3 != null)
          {
            localObject2 = ((JSONObject)localObject3).optString("name", null);
            if (localObject2 != null)
            {
              int k = ((JSONObject)localObject3).optInt("enable", -1);
              localObject3 = ((JSONObject)localObject3).optJSONObject("config");
              if (localObject3 != null)
              {
                Iterator localIterator = ((JSONObject)localObject3).keys();
                while (localIterator.hasNext())
                {
                  String str2 = (String)localIterator.next();
                  a((String)localObject2, str2, ((JSONObject)localObject3).optString(str2, null));
                }
              }
              a((String)localObject2, "enable", String.valueOf(k));
            }
          }
          j += 1;
        }
      }
      i += 1;
    }
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("RenderSmttService.initReportConfigMap reportConfigMap=");
    ((StringBuilder)localObject1).append(jdField_a_of_type_JavaUtilHashMap.toString());
    X5Log.i("RENDER", ((StringBuilder)localObject1).toString());
  }
  
  public static void a(String paramString, int paramInt1, int paramInt2, int paramInt3, float paramFloat)
  {
    if (paramString != null)
    {
      if (paramString.length() <= 0) {
        return;
      }
      HashMap localHashMap = new HashMap();
      localHashMap.put("url", paramString);
      localHashMap.put("reason", String.valueOf(paramInt1));
      localHashMap.put("pageTextCount", String.valueOf(paramInt2));
      localHashMap.put("contentTextCount", String.valueOf(paramInt3));
      localHashMap.put("textRate", String.valueOf(paramFloat));
      a("MTT_CORE_WEBPAGE_READ_MODE_FAIL", localHashMap);
      return;
    }
  }
  
  public static void a(String paramString, HashMap<String, String> paramHashMap)
  {
    if (paramHashMap != null)
    {
      if (paramHashMap.size() == 0) {
        return;
      }
      HashMap localHashMap = new HashMap();
      Object localObject2 = AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_LC);
      String str = AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_BUILD_NO);
      Object localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = "no_lc";
      }
      localObject2 = str;
      if (str == null) {
        localObject2 = "no_buildNO";
      }
      localHashMap.put("guid", SmttServiceProxy.getInstance().getGUID());
      localHashMap.put("qua2", SmttServiceProxy.getInstance().getQUA2());
      localHashMap.put("lc", localObject1);
      localHashMap.put("buildNo", localObject2);
      localObject1 = paramHashMap.entrySet().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (Map.Entry)((Iterator)localObject1).next();
        localHashMap.put(((Map.Entry)localObject2).getKey(), ((Map.Entry)localObject2).getValue());
      }
      SmttServiceProxy.getInstance().upLoadToBeacon(paramString, localHashMap);
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(paramString);
      ((StringBuilder)localObject1).append("=");
      ((StringBuilder)localObject1).append(paramHashMap.toString());
      X5Log.i("RENDER", ((StringBuilder)localObject1).toString());
      return;
    }
  }
  
  public static void a(String paramString, boolean paramBoolean, long paramLong)
  {
    if (paramString != null)
    {
      if (paramString.length() <= 0) {
        return;
      }
      HashMap localHashMap = new HashMap();
      localHashMap.put("url", paramString);
      if (paramBoolean) {
        paramString = "1";
      } else {
        paramString = "0";
      }
      localHashMap.put("predistill", paramString);
      localHashMap.put("distilltime", String.valueOf(paramLong));
      a("MTT_CORE_WEBPAGE_READ_MODE", localHashMap);
      return;
    }
  }
  
  public static boolean a(String paramString)
  {
    return SmttServiceProxy.getInstance().isInWebpageReadModeWhiteList(paramString);
  }
  
  public static boolean a(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1 != null) && (paramString2 != null))
    {
      if (jdField_a_of_type_JavaUtilHashMap == null) {
        jdField_a_of_type_JavaUtilHashMap = new HashMap();
      }
      HashMap localHashMap = jdField_a_of_type_JavaUtilHashMap;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString1);
      localStringBuilder.append("-");
      localStringBuilder.append(paramString2);
      localHashMap.put(localStringBuilder.toString(), paramString3);
      return true;
    }
    return false;
  }
  
  @CalledByNative
  public static void addFixedADFilteredNum(int paramInt)
  {
    jdField_c_of_type_Int += paramInt;
    SmttServiceProxy.getInstance().setFixedADFilteredNum(jdField_c_of_type_Int);
  }
  
  public static void b()
  {
    HashMap localHashMap = new HashMap();
    Object localObject2 = AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_LC);
    String str = AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_BUILD_NO);
    Object localObject1 = localObject2;
    if (localObject2 == null) {
      localObject1 = "no_lc";
    }
    localObject2 = str;
    if (str == null) {
      localObject2 = "no_buildNO";
    }
    localHashMap.put("guid", SmttServiceProxy.getInstance().getGUID());
    localHashMap.put("qua2", SmttServiceProxy.getInstance().getQUA2());
    localHashMap.put("lc", localObject1);
    localHashMap.put("buildNo", localObject2);
    SmttServiceProxy.getInstance().uploadFixedADFilteredNumIfNeeded("MTT_CORE_EXPERIENCE_FIXED_AD_FILTER", localHashMap);
  }
  
  public static boolean b(String paramString)
  {
    return SmttServiceProxy.getInstance().isInWebpageReadModeBlackList(paramString);
  }
  
  @CalledByNative
  public static boolean getInRenderFixedADFilterDomainWhiteList(String paramString)
  {
    return SmttServiceProxy.getInstance().isInRenderFixedADFilterDomainWhiteList(paramString);
  }
  
  @CalledByNative
  public static boolean getInRenderHijackDomainWhiteList(String paramString)
  {
    return SmttServiceProxy.getInstance().isInRenderHijackDomainWhiteList(paramString);
  }
  
  @CalledByNative
  public static boolean getInReportDomainWhiteList(String paramString)
  {
    return SmttServiceProxy.getInstance().isInRenderReportDomainWhiteList(paramString);
  }
  
  @CalledByNative
  public static boolean getReportConfigB(String paramString1, String paramString2, boolean paramBoolean)
  {
    HashMap localHashMap = jdField_a_of_type_JavaUtilHashMap;
    if ((localHashMap != null) && (paramString1 != null)) {
      if (paramString2 == null) {
        return paramBoolean;
      }
    }
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString1);
      localStringBuilder.append("-");
      localStringBuilder.append(paramString2);
      paramString1 = (String)localHashMap.get(localStringBuilder.toString());
      if (paramString1 == null) {
        return paramBoolean;
      }
      int i = Integer.parseInt(paramString1);
      return i == 1;
    }
    catch (Exception paramString1) {}
    return paramBoolean;
    return paramBoolean;
  }
  
  @CalledByNative
  public static float getReportConfigF(String paramString1, String paramString2, float paramFloat)
  {
    HashMap localHashMap = jdField_a_of_type_JavaUtilHashMap;
    if ((localHashMap != null) && (paramString1 != null)) {
      if (paramString2 == null) {
        return paramFloat;
      }
    }
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString1);
      localStringBuilder.append("-");
      localStringBuilder.append(paramString2);
      float f = Float.parseFloat((String)localHashMap.get(localStringBuilder.toString()));
      return f;
    }
    catch (Exception paramString1) {}
    return paramFloat;
    return paramFloat;
  }
  
  @CalledByNative
  public static int getReportConfigI(String paramString1, String paramString2, int paramInt)
  {
    HashMap localHashMap = jdField_a_of_type_JavaUtilHashMap;
    if ((localHashMap != null) && (paramString1 != null)) {
      if (paramString2 == null) {
        return paramInt;
      }
    }
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString1);
      localStringBuilder.append("-");
      localStringBuilder.append(paramString2);
      int i = Integer.parseInt((String)localHashMap.get(localStringBuilder.toString()));
      return i;
    }
    catch (Exception paramString1) {}
    return paramInt;
    return paramInt;
  }
  
  @CalledByNative
  public static String getReportConfigS(String paramString1, String paramString2, String paramString3)
  {
    HashMap localHashMap = jdField_a_of_type_JavaUtilHashMap;
    if ((localHashMap != null) && (paramString1 != null))
    {
      if (paramString2 == null) {
        return paramString3;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString1);
      localStringBuilder.append("-");
      localStringBuilder.append(paramString2);
      paramString2 = (String)localHashMap.get(localStringBuilder.toString());
      paramString1 = paramString2;
      if (paramString2 == null) {
        paramString1 = paramString3;
      }
      return paramString1;
    }
    return paramString3;
  }
  
  @CalledByNative
  public static boolean getReportEnable(String paramString, boolean paramBoolean)
  {
    HashMap localHashMap = jdField_a_of_type_JavaUtilHashMap;
    if (localHashMap != null) {
      if (paramString == null) {
        return paramBoolean;
      }
    }
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString);
      localStringBuilder.append("-enable");
      int i = Integer.parseInt((String)localHashMap.get(localStringBuilder.toString()));
      if (i == 1) {
        return true;
      }
      if (i == 0) {
        return false;
      }
      return paramBoolean;
    }
    catch (Exception paramString) {}
    return paramBoolean;
    return paramBoolean;
  }
  
  @CalledByNative
  public static void init(String paramString)
  {
    jdField_b_of_type_JavaLangString = paramString;
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        RenderSmttService.a(SmttServiceProxy.getInstance().getQUA2());
      }
    });
  }
  
  @CalledByNative
  public static boolean isEnabled()
  {
    jdField_a_of_type_Boolean = e.a().q();
    return jdField_a_of_type_Boolean;
  }
  
  @CalledByNative
  public static boolean isEnabledErrState()
  {
    jdField_a_of_type_Boolean = SmttServiceProxy.getInstance().isEnableRenderInfoUploadErrState();
    return jdField_a_of_type_Boolean;
  }
  
  @CalledByNative
  public static boolean isEnabledRenderPath()
  {
    jdField_a_of_type_Boolean = SmttServiceProxy.getInstance().isEnabledRenderPathUpload();
    return jdField_a_of_type_Boolean;
  }
  
  @CalledByNative
  public static void report(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    if ((paramString2 != null) && (paramString2.equals("GLErrorState")))
    {
      int i = jdField_b_of_type_Int;
      if (i > jdField_a_of_type_Int) {
        return;
      }
      jdField_b_of_type_Int = i + 1;
    }
    HashMap localHashMap = new HashMap();
    localHashMap.put("url", paramString1);
    localHashMap.put("type", paramString2);
    localHashMap.put("msg", paramString3);
    localHashMap.put("gpu", jdField_b_of_type_JavaLangString);
    localHashMap.put("qua2", jdField_c_of_type_JavaLangString);
    if (paramInt == 2)
    {
      SmttServiceProxy.getInstance().upLoadToBeacon("MTT_CORE_WEBAR_ERROR", localHashMap);
      return;
    }
    if ((paramString2 != null) && (paramString2.equals("RENDERPATH")))
    {
      SmttServiceProxy.getInstance().upLoadToBeacon("MTT_CORE_RENDER_PATH", localHashMap);
      return;
    }
    if ((paramString2 != null) && (paramString2.equals("URL")))
    {
      SmttServiceProxy.getInstance().setVisitorUrl(paramString2, paramString3, paramString1, jdField_b_of_type_JavaLangString, jdField_c_of_type_JavaLangString);
      return;
    }
    SmttServiceProxy.getInstance().upLoadToBeacon("MTT_CORE_RENDER_ERROR", localHashMap);
  }
  
  @CalledByNative
  public static void reportClickHijack(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt)
  {
    if (paramString1 != null)
    {
      if (paramString1.length() <= 0) {
        return;
      }
      HashMap localHashMap = new HashMap();
      localHashMap.put("node_msg", paramString2);
      localHashMap.put("url", paramString1);
      localHashMap.put("image_list", paramString3);
      localHashMap.put("anchor_list", paramString4);
      localHashMap.put("create_stack", paramString5);
      localHashMap.put("node_count", String.valueOf(paramInt));
      a("MTT_CORE_EXPERIENCE_CLICK_HIJACK", localHashMap);
      return;
    }
  }
  
  @CalledByNative
  public static void reportCompositorTimingInfo(String paramString1, boolean paramBoolean, int paramInt1, int paramInt2, long paramLong1, long paramLong2, String paramString2, int paramInt3, int paramInt4, long paramLong3, long paramLong4, String paramString3, int paramInt5, int paramInt6, long paramLong5, long paramLong6, String paramString4)
  {
    if (paramString1 != null)
    {
      if (paramString1.length() <= 0) {
        return;
      }
      HashMap localHashMap = new HashMap();
      localHashMap.put("scroll_fps", String.valueOf(paramInt1));
      localHashMap.put("scroll_avg_cptime", String.valueOf(paramInt2));
      localHashMap.put("scroll_total_count", String.valueOf(paramLong1));
      localHashMap.put("scroll_sw_count", String.valueOf(paramLong2));
      localHashMap.put("scroll_dropped_frame", paramString2);
      localHashMap.put("scale_fps", String.valueOf(paramInt3));
      localHashMap.put("scale_avg_cptime", String.valueOf(paramInt4));
      localHashMap.put("scale_total_count", String.valueOf(paramLong3));
      localHashMap.put("scale_sw_count", String.valueOf(paramLong4));
      localHashMap.put("scale_dropped_frame", paramString3);
      localHashMap.put("others_fps", String.valueOf(paramInt5));
      localHashMap.put("others_avg_cptime", String.valueOf(paramInt6));
      localHashMap.put("others_total_count", String.valueOf(paramLong5));
      localHashMap.put("others_sw_count", String.valueOf(paramLong6));
      localHashMap.put("other_dropped_frame", paramString4);
      localHashMap.put("url", paramString1);
      localHashMap.put("x5pro", String.valueOf(paramBoolean));
      a("MTT_CORE_COMPOSITOR_TIMING_INFO", localHashMap);
      return;
    }
  }
  
  @CalledByNative
  public static void reportFixedAD(String paramString1, float paramFloat1, float paramFloat2, int paramInt1, int paramInt2, String paramString2, int paramInt3)
  {
    if (paramString1 != null)
    {
      if (paramString1.length() <= 0) {
        return;
      }
      HashMap localHashMap = new HashMap();
      localHashMap.put("cover_percent", String.valueOf(paramFloat1));
      localHashMap.put("flash_percent", String.valueOf(paramFloat2));
      localHashMap.put("fix_layer_count", String.valueOf(paramInt1));
      localHashMap.put("ad_layer_count", String.valueOf(paramInt2));
      localHashMap.put("layer_msg", paramString2);
      localHashMap.put("can_filter", String.valueOf(paramInt3));
      localHashMap.put("url", paramString1);
      a("MTT_CORE_EXPERIENCE_FIXED_AD", localHashMap);
      return;
    }
  }
  
  @CalledByNative
  public static void reportJsTimeOutIfNeeded(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.length() <= 0) {
        return;
      }
      Object localObject = jdField_b_of_type_JavaUtilHashMap;
      if (localObject != null)
      {
        if (((HashMap)localObject).size() == 0) {
          return;
        }
        localObject = (String)jdField_b_of_type_JavaUtilHashMap.remove(paramString);
        if (localObject != null)
        {
          HashMap localHashMap = new HashMap();
          localHashMap.put("jsinfo", localObject);
          localHashMap.put("url", paramString);
          a("MTT_CORE_THREAD_JS_TIMEOUT", localHashMap);
        }
        return;
      }
      return;
    }
  }
  
  @CalledByNative
  public static void reportReadModePageType(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, int paramInt5, float paramFloat2)
  {
    if (paramString != null)
    {
      if (paramString.length() <= 0) {
        return;
      }
      HashMap localHashMap = new HashMap();
      localHashMap.put("page_type", String.valueOf(paramInt1));
      localHashMap.put("width", String.valueOf(paramInt2));
      localHashMap.put("height", String.valueOf(paramInt3));
      localHashMap.put("text_count", String.valueOf(paramInt4));
      localHashMap.put("text_percent", String.valueOf(paramFloat1));
      localHashMap.put("image_count", String.valueOf(paramInt5));
      localHashMap.put("image_percent", String.valueOf(paramFloat2));
      localHashMap.put("url", paramString);
      a("MTT_CORE_WEBPAGE_READ_MODE_PAGE_TYPE", localHashMap);
      return;
    }
  }
  
  @CalledByNative
  public static void reportRenderMemInfo(String paramString, long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, long paramLong3)
  {
    if (paramString != null)
    {
      if (paramString.length() <= 0) {
        return;
      }
      HashMap localHashMap = new HashMap();
      localHashMap.put("webgl_size", String.valueOf(paramLong1));
      localHashMap.put("gles_size", String.valueOf(paramLong2));
      localHashMap.put("gr_cache_size", String.valueOf(paramInt1));
      localHashMap.put("gr_cache_count", String.valueOf(paramInt2));
      localHashMap.put("canvas_size", String.valueOf(paramInt3));
      localHashMap.put("canvas_count", String.valueOf(paramInt4));
      localHashMap.put("v8_size", String.valueOf(paramInt5));
      localHashMap.put("v8_count", String.valueOf(paramInt6));
      localHashMap.put("offscreen_size", String.valueOf(paramLong3));
      localHashMap.put("url", paramString);
      a("MTT_CORE_RENDER_MEM_INFO", localHashMap);
      return;
    }
  }
  
  @CalledByNative
  public static void reportResponseTiming(String paramString1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, String paramString2)
  {
    if (paramString1 != null)
    {
      if (paramString1.length() <= 0) {
        return;
      }
      HashMap localHashMap = new HashMap();
      localHashMap.put("event_count", String.valueOf(paramInt1));
      localHashMap.put("handle_time", String.valueOf(paramInt2));
      localHashMap.put("last_finish_time", String.valueOf(paramInt3));
      localHashMap.put("layout_time", String.valueOf(paramInt4));
      localHashMap.put("commit_send_time", String.valueOf(paramInt5));
      localHashMap.put("commit_achieve_time", String.valueOf(paramInt6));
      localHashMap.put("active_time", String.valueOf(paramInt7));
      localHashMap.put("node_msg", paramString2);
      localHashMap.put("url", paramString1);
      a("MTT_CORE_BUSINESS_RESPONSE_TIMING", localHashMap);
      return;
    }
  }
  
  @CalledByNative
  public static void setJsTimeOutInfo(String paramString1, String paramString2)
  {
    if (paramString1 != null)
    {
      if (paramString1.length() <= 0) {
        return;
      }
      if (jdField_b_of_type_JavaUtilHashMap == null) {
        jdField_b_of_type_JavaUtilHashMap = new HashMap();
      }
      jdField_b_of_type_JavaUtilHashMap.put(paramString1, paramString2);
      return;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\RenderSmttService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */