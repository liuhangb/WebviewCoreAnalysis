package com.tencent.smtt.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.webkit.ValueCallback;
import com.tencent.common.http.Apn;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import com.tencent.tbs.core.webkit.tencent.TencentURLUtil;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewClient;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.base.TencentPathUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@JNINamespace("tencent")
public class SWOfflineFramework
{
  private static SWOfflineFramework jdField_a_of_type_ComTencentSmttNetSWOfflineFramework;
  private static boolean jdField_a_of_type_Boolean = false;
  private static boolean jdField_c_of_type_Boolean = false;
  final long jdField_a_of_type_Long = 3000L;
  private Context jdField_a_of_type_AndroidContentContext = null;
  private Handler jdField_a_of_type_AndroidOsHandler = null;
  private ValueCallback<Boolean> jdField_a_of_type_AndroidWebkitValueCallback = null;
  private X5WebViewAdapter jdField_a_of_type_ComTencentTbsCoreWebkitAdapterX5WebViewAdapter = null;
  private File jdField_a_of_type_JavaIoFile = null;
  private Runnable jdField_a_of_type_JavaLangRunnable = null;
  private final String jdField_a_of_type_JavaLangString = "SERVICEWORKER";
  private List<String> jdField_a_of_type_JavaUtilList = null;
  private JSONArray jdField_a_of_type_OrgJsonJSONArray = null;
  final long jdField_b_of_type_Long = 600000L;
  private Runnable jdField_b_of_type_JavaLangRunnable = null;
  private String jdField_b_of_type_JavaLangString = null;
  private List<String> jdField_b_of_type_JavaUtilList = null;
  private boolean jdField_b_of_type_Boolean = true;
  final long jdField_c_of_type_Long = 6000L;
  private final String jdField_c_of_type_JavaLangString = "url";
  private final String d = "scriptUrl";
  private final String e = "forceInstall";
  private final String f = "skipWifiCheck";
  private final String g = "priority";
  private final String h = "updatePeriod";
  private final String i = "lastUpdateTime";
  private final String j = "id";
  private final String k = "scope";
  private final String l = "exist";
  
  /* Error */
  private String a(File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore_2
    //   5: new 141	java/io/BufferedReader
    //   8: dup
    //   9: new 143	java/io/InputStreamReader
    //   12: dup
    //   13: new 145	java/io/FileInputStream
    //   16: dup
    //   17: aload_1
    //   18: invokespecial 148	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   21: invokespecial 151	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   24: invokespecial 154	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   27: astore 4
    //   29: new 156	java/lang/StringBuffer
    //   32: dup
    //   33: invokespecial 157	java/lang/StringBuffer:<init>	()V
    //   36: astore_1
    //   37: aload 4
    //   39: invokevirtual 161	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   42: astore_2
    //   43: aload_2
    //   44: ifnull +12 -> 56
    //   47: aload_1
    //   48: aload_2
    //   49: invokevirtual 165	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   52: pop
    //   53: goto -16 -> 37
    //   56: aload 4
    //   58: invokevirtual 168	java/io/BufferedReader:close	()V
    //   61: goto +54 -> 115
    //   64: astore_2
    //   65: aload_1
    //   66: astore_3
    //   67: aload_2
    //   68: astore_1
    //   69: goto +13 -> 82
    //   72: astore_1
    //   73: aload 4
    //   75: astore_2
    //   76: goto +51 -> 127
    //   79: astore_1
    //   80: aconst_null
    //   81: astore_3
    //   82: goto +14 -> 96
    //   85: astore_1
    //   86: goto +41 -> 127
    //   89: astore_1
    //   90: aconst_null
    //   91: astore_3
    //   92: aload 5
    //   94: astore 4
    //   96: aload 4
    //   98: astore_2
    //   99: aload_1
    //   100: invokevirtual 171	java/lang/Exception:printStackTrace	()V
    //   103: aload 4
    //   105: ifnull +8 -> 113
    //   108: aload 4
    //   110: invokevirtual 168	java/io/BufferedReader:close	()V
    //   113: aload_3
    //   114: astore_1
    //   115: aload_1
    //   116: ifnull +8 -> 124
    //   119: aload_1
    //   120: invokevirtual 174	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   123: areturn
    //   124: ldc -80
    //   126: areturn
    //   127: aload_2
    //   128: ifnull +7 -> 135
    //   131: aload_2
    //   132: invokevirtual 168	java/io/BufferedReader:close	()V
    //   135: aload_1
    //   136: athrow
    //   137: astore_2
    //   138: goto -23 -> 115
    //   141: astore_1
    //   142: goto -29 -> 113
    //   145: astore_2
    //   146: goto -11 -> 135
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	149	0	this	SWOfflineFramework
    //   0	149	1	paramFile	File
    //   4	45	2	str	String
    //   64	4	2	localException1	Exception
    //   75	57	2	localObject1	Object
    //   137	1	2	localException2	Exception
    //   145	1	2	localException3	Exception
    //   66	48	3	localFile	File
    //   27	82	4	localObject2	Object
    //   1	92	5	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   37	43	64	java/lang/Exception
    //   47	53	64	java/lang/Exception
    //   29	37	72	finally
    //   37	43	72	finally
    //   47	53	72	finally
    //   29	37	79	java/lang/Exception
    //   5	29	85	finally
    //   99	103	85	finally
    //   5	29	89	java/lang/Exception
    //   56	61	137	java/lang/Exception
    //   108	113	141	java/lang/Exception
    //   131	135	145	java/lang/Exception
  }
  
  private JSONObject a(String paramString)
  {
    Object localObject = this.jdField_a_of_type_OrgJsonJSONArray;
    if ((localObject != null) && (((JSONArray)localObject).length() != 0) && (paramString != null))
    {
      if (paramString == "") {
        return null;
      }
      boolean bool = paramString.endsWith("/");
      int n = 0;
      int m = n;
      localObject = paramString;
      if (!bool)
      {
        localObject = paramString.substring(0, paramString.lastIndexOf("/") + 1);
        m = n;
      }
      while (m < this.jdField_a_of_type_OrgJsonJSONArray.length()) {
        try
        {
          if (((String)localObject).equals(this.jdField_a_of_type_OrgJsonJSONArray.getJSONObject(m).getString("scope")))
          {
            paramString = this.jdField_a_of_type_OrgJsonJSONArray.getJSONObject(m);
            return paramString;
          }
          m += 1;
        }
        catch (JSONException paramString)
        {
          paramString.printStackTrace();
          return null;
        }
      }
      return null;
    }
    return null;
  }
  
  private void a()
  {
    if (this.jdField_a_of_type_AndroidOsHandler == null) {
      this.jdField_a_of_type_AndroidOsHandler = new Handler(Looper.getMainLooper());
    }
    if ((this.jdField_a_of_type_AndroidContentContext != null) && (this.jdField_a_of_type_JavaIoFile == null)) {}
    for (;;)
    {
      int m;
      try
      {
        this.jdField_a_of_type_JavaUtilList = new ArrayList();
        this.jdField_b_of_type_JavaUtilList = new ArrayList();
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append(TencentPathUtils.getDataDirectory(this.jdField_a_of_type_AndroidContentContext));
        ((StringBuilder)localObject).append("/Service Worker/Spread/installedDetails");
        this.jdField_a_of_type_JavaIoFile = new File(((StringBuilder)localObject).toString());
        if (!this.jdField_a_of_type_JavaIoFile.exists())
        {
          this.jdField_a_of_type_JavaIoFile.getParentFile().mkdirs();
          this.jdField_a_of_type_JavaIoFile.createNewFile();
        }
        localObject = a(this.jdField_a_of_type_JavaIoFile);
        if (((String)localObject).isEmpty()) {
          this.jdField_a_of_type_OrgJsonJSONArray = new JSONArray();
        } else {
          this.jdField_a_of_type_OrgJsonJSONArray = new JSONArray((String)localObject);
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      if (m < this.jdField_a_of_type_OrgJsonJSONArray.length())
      {
        this.jdField_a_of_type_JavaUtilList.add(this.jdField_a_of_type_OrgJsonJSONArray.getJSONObject(m).optString("scope", ""));
        if (this.jdField_a_of_type_OrgJsonJSONArray.getJSONObject(m).optBoolean("exist", false)) {
          this.jdField_b_of_type_JavaUtilList.add(this.jdField_a_of_type_OrgJsonJSONArray.getJSONObject(m).optString("scope", ""));
        }
        m += 1;
      }
      else
      {
        b();
        return;
        m = 0;
      }
    }
  }
  
  private void a(final boolean paramBoolean, long paramLong)
  {
    this.jdField_a_of_type_AndroidOsHandler.postDelayed(new Runnable()
    {
      public void run()
      {
        BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
        {
          public void doRun()
          {
            if (Apn.isWifiMode()) {
              SWOfflineFramework.a(true);
            } else {
              SWOfflineFramework.a(false);
            }
            if (SWOfflineFramework.4.this.jdField_a_of_type_Boolean)
            {
              SWOfflineFramework.a(SWOfflineFramework.4.this.jdField_a_of_type_ComTencentSmttNetSWOfflineFramework).post(SWOfflineFramework.b(SWOfflineFramework.4.this.jdField_a_of_type_ComTencentSmttNetSWOfflineFramework));
              return;
            }
            SWOfflineFramework.a(SWOfflineFramework.4.this.jdField_a_of_type_ComTencentSmttNetSWOfflineFramework).post(SWOfflineFramework.a(SWOfflineFramework.4.this.jdField_a_of_type_ComTencentSmttNetSWOfflineFramework));
          }
        });
      }
    }, paramLong);
  }
  
  private boolean a(List<String> paramList, String paramString)
  {
    if ((paramList != null) && (paramList.size() != 0) && (paramString != null))
    {
      if (paramString == "") {
        return false;
      }
      String str = paramString;
      if (!paramString.endsWith("/")) {
        str = paramString.substring(0, paramString.lastIndexOf("/") + 1);
      }
      int m = 0;
      while (m < paramList.size())
      {
        if (str.equals(paramList.get(m))) {
          return true;
        }
        m += 1;
      }
      return false;
    }
    return false;
  }
  
  private void b()
  {
    if (this.jdField_a_of_type_AndroidContentContext == null) {
      return;
    }
    c();
    e();
  }
  
  private void c()
  {
    d();
  }
  
  private void d()
  {
    this.jdField_a_of_type_JavaLangRunnable = new Runnable()
    {
      public void run()
      {
        List localList = SmttServiceProxy.getInstance().getSWPresetWhiteList();
        if (localList != null)
        {
          if (localList.size() == 0) {
            return;
          }
          int i = 0;
          while (i < localList.size()) {
            try
            {
              JSONObject localJSONObject = new JSONObject((String)localList.get(i));
              String str = localJSONObject.getString("url");
              if (((localJSONObject.optBoolean("skipWifiCheck", false)) || (SWOfflineFramework.a())) && (localJSONObject.optBoolean("forceInstall", false) ? !SWOfflineFramework.this.isInstalledExitSWPage(str) : !SWOfflineFramework.this.isInstalledSWPage(str)))
              {
                SWOfflineFramework.b(SWOfflineFramework.this);
                SWOfflineFramework.a(SWOfflineFramework.this).registerServiceWorkerBackground(localJSONObject.getString("url"), localJSONObject.getString("scriptUrl"));
                return;
              }
            }
            catch (Exception localException)
            {
              localException.printStackTrace();
              i += 1;
            }
          }
          return;
        }
      }
    };
    a(true, 6000L);
  }
  
  private void e()
  {
    if (this.jdField_a_of_type_AndroidContentContext == null) {
      return;
    }
    f();
  }
  
  private void f()
  {
    this.jdField_b_of_type_JavaLangRunnable = new Runnable()
    {
      public void run()
      {
        List localList = SmttServiceProxy.getInstance().getSWUpdateWhiteList();
        if ((localList != null) && (localList.size() != 0))
        {
          int i = 0;
          while (i < localList.size())
          {
            try
            {
              JSONObject localJSONObject1 = new JSONObject((String)localList.get(i));
              String str = localJSONObject1.getString("url");
              if (((localJSONObject1.optBoolean("skipWifiCheck", false)) || (SWOfflineFramework.a())) && (SWOfflineFramework.a(SWOfflineFramework.this) == true ? localJSONObject1.optInt("priority", 0) != 0 : localJSONObject1.optInt("priority", 0) != 1))
              {
                JSONObject localJSONObject2 = SWOfflineFramework.a(SWOfflineFramework.this, str);
                if ((localJSONObject2 != null) && (localJSONObject2.getLong("lastUpdateTime") + localJSONObject1.getLong("updatePeriod") <= System.currentTimeMillis() / 1000L))
                {
                  SWOfflineFramework.b(SWOfflineFramework.this);
                  SWOfflineFramework.a(SWOfflineFramework.this).updateServiceWorkerBackground(str);
                  localJSONObject2.put("lastUpdateTime", System.currentTimeMillis() / 1000L);
                  SWOfflineFramework.this.writeFile(SWOfflineFramework.a(SWOfflineFramework.this), SWOfflineFramework.a(SWOfflineFramework.this).toString());
                }
              }
            }
            catch (Exception localException)
            {
              localException.printStackTrace();
            }
            i += 1;
          }
          SWOfflineFramework.a(SWOfflineFramework.this, false);
          SWOfflineFramework.a(SWOfflineFramework.this, false, 600000L);
          return;
        }
        SWOfflineFramework.a(SWOfflineFramework.this).postDelayed(SWOfflineFramework.a(SWOfflineFramework.this), 600000L);
      }
    };
    a(false, 3000L);
  }
  
  private void g()
  {
    if (this.jdField_a_of_type_ComTencentTbsCoreWebkitAdapterX5WebViewAdapter == null)
    {
      this.jdField_a_of_type_ComTencentTbsCoreWebkitAdapterX5WebViewAdapter = new X5WebViewAdapter(this.jdField_a_of_type_AndroidContentContext, false);
      this.jdField_a_of_type_ComTencentTbsCoreWebkitAdapterX5WebViewAdapter.setWebViewClient(new TencentWebViewClient());
      this.jdField_a_of_type_ComTencentTbsCoreWebkitAdapterX5WebViewAdapter.getSettings().setJavaScriptEnabled(true);
      this.jdField_a_of_type_ComTencentTbsCoreWebkitAdapterX5WebViewAdapter.loadUrl("about:blank");
    }
  }
  
  @CalledByNative
  public static SWOfflineFramework getInstance()
  {
    try
    {
      if (jdField_a_of_type_ComTencentSmttNetSWOfflineFramework == null) {
        jdField_a_of_type_ComTencentSmttNetSWOfflineFramework = new SWOfflineFramework();
      }
      SWOfflineFramework localSWOfflineFramework = jdField_a_of_type_ComTencentSmttNetSWOfflineFramework;
      return localSWOfflineFramework;
    }
    finally {}
  }
  
  private void h()
  {
    String str = this.jdField_b_of_type_JavaLangString;
    if ((str != null) && (this.jdField_a_of_type_AndroidWebkitValueCallback != null) && (a(this.jdField_b_of_type_JavaUtilList, str)))
    {
      this.jdField_a_of_type_AndroidWebkitValueCallback.onReceiveValue(Boolean.valueOf(true));
      this.jdField_b_of_type_JavaLangString = null;
      this.jdField_a_of_type_AndroidWebkitValueCallback = null;
    }
  }
  
  @CalledByNative
  private void writeRegisterToFileJNI(String paramString, long paramLong1, long paramLong2)
  {
    if ((this.jdField_a_of_type_JavaIoFile != null) && (this.jdField_b_of_type_JavaUtilList != null) && (this.jdField_a_of_type_JavaUtilList != null))
    {
      if (this.jdField_a_of_type_OrgJsonJSONArray == null) {
        return;
      }
      Object localObject = paramString;
      if (!paramString.endsWith("/"))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append("/");
        localObject = ((StringBuilder)localObject).toString();
      }
      boolean bool = a(this.jdField_b_of_type_JavaUtilList, (String)localObject);
      int m = 0;
      int n = 0;
      ArrayList localArrayList;
      if (!bool)
      {
        this.jdField_b_of_type_JavaUtilList.add(localObject);
        if (a(this.jdField_a_of_type_JavaUtilList, (String)localObject))
        {
          m = n;
          while (m < this.jdField_a_of_type_OrgJsonJSONArray.length()) {
            try
            {
              paramString = this.jdField_a_of_type_OrgJsonJSONArray.getJSONObject(m);
              localArrayList = new ArrayList();
              localArrayList.add(paramString.getString("scope"));
              if (a(localArrayList, (String)localObject))
              {
                paramString.put("exist", true);
                paramString.put("lastUpdateTime", paramLong2);
                paramString.put("id", paramLong1);
                writeFile(this.jdField_a_of_type_JavaIoFile, this.jdField_a_of_type_OrgJsonJSONArray.toString());
                h();
              }
            }
            catch (JSONException paramString)
            {
              paramString.printStackTrace();
              m += 1;
            }
          }
        }
        try
        {
          paramString = new JSONObject();
          paramString.put("id", paramLong1);
          paramString.put("scope", localObject);
          paramString.put("lastUpdateTime", paramLong2);
          paramString.put("exist", true);
          this.jdField_a_of_type_OrgJsonJSONArray.put(paramString);
          this.jdField_a_of_type_JavaUtilList.add(localObject);
          writeFile(this.jdField_a_of_type_JavaIoFile, this.jdField_a_of_type_OrgJsonJSONArray.toString());
          h();
        }
        catch (Exception paramString)
        {
          paramString.printStackTrace();
        }
      }
      else
      {
        while (m < this.jdField_a_of_type_OrgJsonJSONArray.length()) {
          try
          {
            paramString = this.jdField_a_of_type_OrgJsonJSONArray.getJSONObject(m);
            localArrayList = new ArrayList();
            localArrayList.add(paramString.getString("scope"));
            if (a(localArrayList, (String)localObject))
            {
              paramString.put("exist", true);
              paramString.put("lastUpdateTime", paramLong2);
              writeFile(this.jdField_a_of_type_JavaIoFile, this.jdField_a_of_type_OrgJsonJSONArray.toString());
            }
          }
          catch (JSONException paramString)
          {
            paramString.printStackTrace();
            m += 1;
          }
        }
      }
      paramString = this.jdField_a_of_type_JavaLangRunnable;
      if (paramString != null) {
        this.jdField_a_of_type_AndroidOsHandler.postDelayed(paramString, 500L);
      }
      return;
    }
  }
  
  public void clearSWCache()
  {
    if (!jdField_a_of_type_Boolean) {
      return;
    }
    List localList = this.jdField_b_of_type_JavaUtilList;
    if (localList != null)
    {
      if (this.jdField_a_of_type_OrgJsonJSONArray == null) {
        return;
      }
      localList.clear();
      int m = 0;
      while (m < this.jdField_a_of_type_OrgJsonJSONArray.length())
      {
        try
        {
          this.jdField_a_of_type_OrgJsonJSONArray.getJSONObject(m).put("exist", false);
        }
        catch (JSONException localJSONException)
        {
          localJSONException.printStackTrace();
        }
        m += 1;
      }
      writeFile(this.jdField_a_of_type_JavaIoFile, this.jdField_a_of_type_OrgJsonJSONArray.toString());
      return;
    }
  }
  
  public boolean isInstalledExitSWPage(String paramString)
  {
    return a(this.jdField_b_of_type_JavaUtilList, paramString);
  }
  
  public boolean isInstalledSWPage(String paramString)
  {
    return a(this.jdField_a_of_type_JavaUtilList, paramString);
  }
  
  public void start(Context paramContext)
  {
    if (jdField_a_of_type_Boolean != true)
    {
      if (paramContext == null) {
        return;
      }
      this.jdField_a_of_type_AndroidContentContext = paramContext.getApplicationContext();
      BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          SWOfflineFramework.a(SWOfflineFramework.this);
        }
      });
      jdField_a_of_type_Boolean = true;
      return;
    }
  }
  
  public void unRegisterSW(String paramString)
  {
    if (this.jdField_b_of_type_JavaUtilList != null)
    {
      if (this.jdField_a_of_type_OrgJsonJSONArray == null) {
        return;
      }
      Object localObject = TencentURLUtil.getPath(paramString);
      paramString = (String)localObject;
      if (!((String)localObject).endsWith("/"))
      {
        paramString = new StringBuilder();
        paramString.append((String)localObject);
        paramString.append("/");
        paramString = paramString.toString();
      }
      if (isInstalledExitSWPage(paramString))
      {
        this.jdField_b_of_type_JavaUtilList.remove(paramString);
        int m = 0;
        while (m < this.jdField_a_of_type_OrgJsonJSONArray.length()) {
          try
          {
            localObject = this.jdField_a_of_type_OrgJsonJSONArray.getJSONObject(m);
            if (((JSONObject)localObject).optString("scope", "").equals(paramString))
            {
              ((JSONObject)localObject).put("exist", false);
              writeFile(this.jdField_a_of_type_JavaIoFile, this.jdField_a_of_type_OrgJsonJSONArray.toString());
              return;
            }
          }
          catch (JSONException localJSONException)
          {
            localJSONException.printStackTrace();
            m += 1;
          }
        }
      }
      return;
    }
  }
  
  public void waitSWInstalled(String paramString, ValueCallback<Boolean> paramValueCallback)
  {
    if (isInstalledExitSWPage(TencentURLUtil.getPath(paramString)))
    {
      paramValueCallback.onReceiveValue(Boolean.valueOf(true));
      return;
    }
    this.jdField_b_of_type_JavaLangString = TencentURLUtil.getPath(paramString);
    this.jdField_a_of_type_AndroidWebkitValueCallback = paramValueCallback;
  }
  
  public void writeFile(final File paramFile, final String paramString)
  {
    BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      /* Error */
      public void doRun()
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore 4
        //   3: aconst_null
        //   4: astore_1
        //   5: new 30	java/io/BufferedWriter
        //   8: dup
        //   9: new 32	java/io/OutputStreamWriter
        //   12: dup
        //   13: new 34	java/io/FileOutputStream
        //   16: dup
        //   17: aload_0
        //   18: getfield 19	com/tencent/smtt/net/SWOfflineFramework$5:jdField_a_of_type_JavaIoFile	Ljava/io/File;
        //   21: invokespecial 37	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
        //   24: invokespecial 40	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;)V
        //   27: invokespecial 43	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
        //   30: astore_2
        //   31: aload_2
        //   32: aload_0
        //   33: getfield 21	com/tencent/smtt/net/SWOfflineFramework$5:jdField_a_of_type_JavaLangString	Ljava/lang/String;
        //   36: invokevirtual 47	java/io/BufferedWriter:write	(Ljava/lang/String;)V
        //   39: aload_2
        //   40: invokevirtual 50	java/io/BufferedWriter:flush	()V
        //   43: aload_2
        //   44: invokevirtual 53	java/io/BufferedWriter:close	()V
        //   47: return
        //   48: astore_1
        //   49: goto +36 -> 85
        //   52: astore_1
        //   53: aload_1
        //   54: astore_3
        //   55: goto +15 -> 70
        //   58: astore_3
        //   59: aload_1
        //   60: astore_2
        //   61: aload_3
        //   62: astore_1
        //   63: goto +22 -> 85
        //   66: astore_3
        //   67: aload 4
        //   69: astore_2
        //   70: aload_2
        //   71: astore_1
        //   72: aload_3
        //   73: invokevirtual 56	java/lang/Exception:printStackTrace	()V
        //   76: aload_2
        //   77: ifnull +7 -> 84
        //   80: aload_2
        //   81: invokevirtual 53	java/io/BufferedWriter:close	()V
        //   84: return
        //   85: aload_2
        //   86: ifnull +7 -> 93
        //   89: aload_2
        //   90: invokevirtual 53	java/io/BufferedWriter:close	()V
        //   93: aload_1
        //   94: athrow
        //   95: astore_1
        //   96: return
        //   97: astore_2
        //   98: goto -5 -> 93
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	101	0	this	5
        //   4	1	1	localObject1	Object
        //   48	1	1	localObject2	Object
        //   52	8	1	localException1	Exception
        //   62	32	1	localObject3	Object
        //   95	1	1	localException2	Exception
        //   30	60	2	localObject4	Object
        //   97	1	2	localException3	Exception
        //   54	1	3	localException4	Exception
        //   58	4	3	localObject5	Object
        //   66	7	3	localException5	Exception
        //   1	67	4	localObject6	Object
        // Exception table:
        //   from	to	target	type
        //   31	43	48	finally
        //   31	43	52	java/lang/Exception
        //   5	31	58	finally
        //   72	76	58	finally
        //   5	31	66	java/lang/Exception
        //   43	47	95	java/lang/Exception
        //   80	84	95	java/lang/Exception
        //   89	93	97	java/lang/Exception
      }
    });
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\SWOfflineFramework.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */