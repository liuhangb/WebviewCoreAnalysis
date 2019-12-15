package com.tencent.tbs.tbsshell.common.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.tbsshell.TBSShell;
import com.tencent.tbs.tbsshell.TBSShellDelegate;
import com.tencent.tbs.tbsshell.TBSShellFactory;
import com.tencent.tbs.tbsshell.WebCoreProxy;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TbsInitPerformanceUtils
{
  public static final String IS_FIRST_INIT_TBS = "is_first_init_tbs";
  public static final String IS_FIRST_INIT_X5 = "is_first_init_x5";
  private static final String TAG = "performance";
  private static String[] X5_CORE_PERFORMANCE_KEYS = { "webview_init", "x5_webview_init", "ensureprovidercreated", "provider_init", "adinfomanager_init", "new_webviewchromiumfactoryprovider", "init_default_commandline", "init_application_context", "chromium_init_for_real", "new_aw_contents", "webviewchromium_extension", "startyourengines", "browser_content_start" };
  private static final String[] eventTypes = { "init_all", "tbslog_init", "mtt_trace", "x5_core_engine_init_sync", "tbs_rename_task", "can_load_x5", "read_core_info", "load_tbs_dex", "init_tbs", "init_x5_core", "init_x5_webview", "load_url_gap", "load_url", "miniqb_upgrademanager_init", "tbs_load_so", "x5webview_clinit", "webview_init", "x5_webview_init", "ensureprovidercreated", "provider_init", "adinfomanager_init", "new_webviewchromiumfactoryprovider", "init_default_commandline", "init_application_context", "chromium_init_for_real", "new_aw_contents", "webviewchromium_extension", "startyourengines", "browser_content_start" };
  private static final String[] final_keys = { "", "init_all_begin", "", "tbslog_init_begin", "tbslog_init_end", "", "x5_core_engine_init_sync_begin", "x5_core_engine_init_sync_end", "", "tbs_rename_task_begin", "tbs_rename_task_end", "", "can_load_x5_begin", "can_load_x5_end", "", "load_tbs_dex_begin", "load_tbs_dex_end", "", "init_tbs_begin", "", "miniqb_upgrademanager_init_begin", "miniqb_upgrademanager_init_end", "", "init_tbs_end", "", "init_x5_core_begin", "", "tbs_load_so_begin", "tbs_load_so_end", "", "new_webviewchromiumfactoryprovider_begin", "", "init_application_context_begin", "init_application_context_end", "", "init_default_commandline_begin", "init_default_commandline_end", "", "new_webviewchromiumfactoryprovider_end", "", "init_x5_core_end", "", "init_x5_webview_begin", "", "x5webview_clinit_begin", "x5webview_clinit_end", "", "webview_init_begin", "", "ensureprovidercreated_begin", "ensureprovidercreated_end", "", "provider_init_begin", "", "startyourengines_begin", "", "browser_content_start_begin", "browser_content_start_end", "", "startyourengines_end", "", "chromium_init_for_real_begin", "", "new_aw_contents_begin", "new_aw_contents_end", "", "webviewchromium_extension_begin", "webviewchromium_extension_end", "", "chromium_init_for_real_end", "", "provider_init_end", "", "adinfomanager_init_begin", "adinfomanager_init_end", "", "webview_init_end", "", "x5_webview_init_begin", "x5_webview_init_end", "", "init_x5_webview_end", "", "load_url_gap_begin", "load_url_gap_end", "", "init_all_end", "" };
  private static Context mCallerAppContext;
  private static Set<Integer> webviewsSet = new HashSet();
  
  static
  {
    mCallerAppContext = null;
  }
  
  private static void displayTbsInitPerformanceData(Map<String, String> paramMap)
  {
    Object localObject1 = new StringBuilder();
    StringBuilder localStringBuilder3 = new StringBuilder();
    Object localObject2 = new String[4];
    int j = 0;
    localObject2[0] = "is_first_init_x5";
    localObject2[1] = "is_first_init_tbs";
    localObject2[2] = "x5_webview_id";
    localObject2[3] = "current_url";
    StringBuilder localStringBuilder1 = new StringBuilder();
    StringBuilder localStringBuilder2 = new StringBuilder();
    localStringBuilder1.append("----------------------------start------------------------------");
    localStringBuilder1.append("\n");
    Object localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("tbs_core_version: ");
    ((StringBuilder)localObject3).append(TBSShell.getTbsCoreVersionCode());
    localStringBuilder1.append(((StringBuilder)localObject3).toString());
    localStringBuilder1.append("\n");
    int i = 0;
    while (i < localObject2.length)
    {
      localObject3 = localObject2[i];
      localStringBuilder1.append((String)localObject3);
      localStringBuilder1.append(": ");
      localStringBuilder1.append((String)paramMap.get(localObject3));
      localStringBuilder1.append("\n");
      ((StringBuilder)localObject1).append((String)localObject3);
      ((StringBuilder)localObject1).append(",");
      localStringBuilder3.append((String)paramMap.get(localObject3));
      localStringBuilder3.append(",");
      i += 1;
    }
    long l2 = 0L;
    long l1 = 0L;
    i = 0;
    Object localObject4;
    for (;;)
    {
      localObject2 = eventTypes;
      if (i >= localObject2.length) {
        break;
      }
      localObject2 = localObject2[i];
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append((String)localObject2);
      ((StringBuilder)localObject3).append("_end");
      localObject3 = ((StringBuilder)localObject3).toString();
      localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append((String)localObject2);
      ((StringBuilder)localObject4).append("_begin");
      localObject4 = ((StringBuilder)localObject4).toString();
      localObject3 = (String)paramMap.get(localObject3);
      localObject4 = (String)paramMap.get(localObject4);
      long l3 = l2;
      long l4 = l1;
      if (localObject3 != null)
      {
        l3 = l2;
        l4 = l1;
        if (localObject4 != null)
        {
          l3 = l2;
          l4 = l1;
          if (!((String)localObject3).isEmpty()) {
            if (((String)localObject4).isEmpty())
            {
              l3 = l2;
              l4 = l1;
            }
            else
            {
              l3 = Long.parseLong((String)localObject3) - Long.parseLong((String)localObject4);
              localObject3 = new StringBuilder();
              ((StringBuilder)localObject3).append((String)localObject2);
              ((StringBuilder)localObject3).append(" duration:\t");
              localStringBuilder1.append(((StringBuilder)localObject3).toString());
              localStringBuilder1.append(l3);
              localStringBuilder1.append("\n");
              if (i != 0)
              {
                if (i != 11)
                {
                  l3 = l2;
                  l4 = l1;
                }
                else
                {
                  l4 = l3;
                  l3 = l2;
                }
              }
              else {
                l4 = l1;
              }
            }
          }
        }
      }
      i += 1;
      l2 = l3;
      l1 = l4;
    }
    localObject2 = final_keys;
    int k = localObject2.length;
    i = j;
    while (i < k)
    {
      localObject3 = localObject2[i];
      ((StringBuilder)localObject1).append((String)localObject3);
      ((StringBuilder)localObject1).append(",");
      localStringBuilder3.append((String)paramMap.get(localObject3));
      localStringBuilder3.append(",");
      localStringBuilder2.append((String)localObject3);
      if (!TextUtils.isEmpty((CharSequence)localObject3))
      {
        localObject4 = new StringBuilder();
        ((StringBuilder)localObject4).append("\t");
        ((StringBuilder)localObject4).append((String)paramMap.get(localObject3));
        localStringBuilder2.append(((StringBuilder)localObject4).toString());
      }
      localStringBuilder2.append("\n");
      i += 1;
    }
    localStringBuilder3.append("\n");
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("titleBuilder:");
    ((StringBuilder)localObject2).append(((StringBuilder)localObject1).toString());
    Log.e("performance", ((StringBuilder)localObject2).toString());
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("speedBuilder:");
    ((StringBuilder)localObject1).append(localStringBuilder3.toString());
    Log.e("performance", ((StringBuilder)localObject1).toString());
    try
    {
      localObject1 = new FileWriter(new File(mCallerAppContext.getExternalFilesDir("tbs_init"), "init_log.txt"), true);
      ((FileWriter)localObject1).write(localStringBuilder3.toString());
      ((FileWriter)localObject1).close();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    localStringBuilder1.append("\n");
    StringBuilder localStringBuilder4 = new StringBuilder();
    localStringBuilder4.append("------->> tbs_init cost: ");
    localStringBuilder4.append(l2 - l1);
    localStringBuilder1.append(localStringBuilder4.toString());
    try
    {
      localStringBuilder1.append("\n");
      l1 = Long.parseLong((String)paramMap.get("free_ram_begin")) / 1048576L;
      l2 = Long.parseLong((String)paramMap.get("free_ram_end")) / 1048576L;
      paramMap = new StringBuilder();
      paramMap.append("------->> FREE_RAM_BEGIN: ");
      paramMap.append(l1);
      paramMap.append("M; FREE_RAM_END: ");
      paramMap.append(l2);
      paramMap.append("M;");
      localStringBuilder1.append(paramMap.toString());
    }
    catch (Throwable paramMap)
    {
      for (;;) {}
    }
    Log.e("performance", "print free-ram exception!");
    localStringBuilder1.append("-------------------------------------------------------------");
    localStringBuilder1.append("\n");
    localStringBuilder1.append(localStringBuilder2.toString());
    localStringBuilder1.append("----------------------------end------------------------------");
    localStringBuilder1.append("\n");
    paramMap = new StringBuilder();
    paramMap.append("");
    paramMap.append(localStringBuilder1.toString());
    Log.e("performance", paramMap.toString());
  }
  
  public static void doReport(int paramInt, Map<String, String> paramMap, boolean paramBoolean, Context paramContext)
  {
    mCallerAppContext = paramContext;
    paramContext = fetchInitPerformanceDataFromX5Core(paramBoolean);
    if (paramContext != null) {
      paramMap.putAll(paramContext);
    }
    paramContext = Integer.valueOf(paramInt);
    if (webviewsSet.contains(paramContext))
    {
      paramMap = new StringBuilder();
      paramMap.append("webviewId(");
      paramMap.append(paramInt);
      paramMap.append(") is reported!");
      paramMap.toString();
      return;
    }
    webviewsSet.add(paramContext);
    paramContext = new StringBuilder();
    paramContext.append("webviewId(");
    paramContext.append(paramInt);
    paramContext.append(") is to be reported!");
    paramContext.toString();
    X5CoreBeaconUploader.getInstance(mCallerAppContext).upLoadToBeacon("TBS_INIT_PERFORMANCE", paramMap);
    X5CoreBeaconUploader.getInstance(mCallerAppContext).upLoadToBeacon("TBS_INIT_PERFORMANCE_COLD", paramMap);
  }
  
  private static Map<String, String> fetchInitPerformanceDataFromX5Core(boolean paramBoolean)
  {
    Map localMap = TBSShellFactory.getTbsShellDelegate().getDataRecord();
    int i = 0;
    Object localObject1 = WebCoreProxy.invokeStaticMethod(paramBoolean, "org.chromium.tencent.SharedResource", "getPerformanceData", new Class[0], new Object[0]);
    if ((localObject1 instanceof Map))
    {
      localObject1 = (HashMap)localObject1;
      localMap.put("x5webview_clinit_end", ((Map)localObject1).get("x5webview_clinit_end"));
      String[] arrayOfString = X5_CORE_PERFORMANCE_KEYS;
      int j = arrayOfString.length;
      while (i < j)
      {
        String str = arrayOfString[i];
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(str);
        ((StringBuilder)localObject2).append("_begin");
        localObject2 = ((StringBuilder)localObject2).toString();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(str);
        localStringBuilder.append("_end");
        str = localStringBuilder.toString();
        localMap.put(localObject2, ((Map)localObject1).get(localObject2));
        localMap.put(str, ((Map)localObject1).get(str));
        i += 1;
      }
    }
    return localMap;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\utils\TbsInitPerformanceUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */