package com.tencent.smtt.webkit;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ValueCallback;
import android.widget.Toast;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.smtt.webkit.ui.TriggerLinearout.OnSizeChangeCallback;
import com.tencent.smtt.webkit.ui.i;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class r
{
  private Handler jdField_a_of_type_AndroidOsHandler = null;
  private WebViewChromiumExtension jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension = null;
  private b jdField_a_of_type_ComTencentSmttWebkitR$b = null;
  i jdField_a_of_type_ComTencentSmttWebkitUiI;
  private TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = null;
  private String jdField_a_of_type_JavaLangString = null;
  private HashSet<String> jdField_a_of_type_JavaUtilHashSet = null;
  private short jdField_a_of_type_Short = 0;
  private boolean jdField_a_of_type_Boolean = false;
  private TencentWebViewProxy jdField_b_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = null;
  private String jdField_b_of_type_JavaLangString = null;
  private boolean jdField_b_of_type_Boolean = false;
  private String jdField_c_of_type_JavaLangString = null;
  private boolean jdField_c_of_type_Boolean = true;
  private String d = null;
  
  public r(WebViewChromiumExtension paramWebViewChromiumExtension)
  {
    a();
    this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension = paramWebViewChromiumExtension;
    this.jdField_a_of_type_JavaUtilHashSet = new HashSet();
    c();
    if (e.a().n())
    {
      this.jdField_c_of_type_JavaLangString = "110109198001010038";
      return;
    }
    this.jdField_c_of_type_JavaLangString = "110109198001010040";
  }
  
  private String a(String paramString)
  {
    paramString.split("【】");
    Object localObject1 = new ArrayList();
    int k = paramString.length();
    int j;
    for (int i = 0; i < k; i = j)
    {
      if (this.jdField_b_of_type_Boolean) {
        return a.d.a();
      }
      if (k - i <= 5000)
      {
        ((ArrayList)localObject1).add(paramString.substring(i));
        break;
      }
      j = paramString.indexOf("【】", i + 5000);
      if (j == -1)
      {
        ((ArrayList)localObject1).add(paramString.substring(i));
        break;
      }
      j += 2;
      ((ArrayList)localObject1).add(paramString.substring(i, j));
    }
    paramString = new StringBuilder();
    k = ((ArrayList)localObject1).size();
    localObject1 = ((ArrayList)localObject1).iterator();
    i = 0;
    if (((Iterator)localObject1).hasNext())
    {
      Object localObject2 = (String)((Iterator)localObject1).next();
      if (this.jdField_b_of_type_Boolean) {
        return a.d.a();
      }
      Object localObject3 = this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension;
      j = i;
      if (localObject3 != null)
      {
        ((WebViewChromiumExtension)localObject3).showTranslateBubble(2, "en", "zh", i * 100 / k);
        j = i + 1;
      }
      localObject2 = a(((String)localObject2).split("【】"));
      if ((((List)localObject2).size() == 1) && (a.a((String)((List)localObject2).get(0)) != null)) {
        return (String)((List)localObject2).get(0);
      }
      localObject2 = ((List)localObject2).iterator();
      for (;;)
      {
        i = j;
        if (!((Iterator)localObject2).hasNext()) {
          break;
        }
        localObject3 = (String)((Iterator)localObject2).next();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append((String)localObject3);
        localStringBuilder.append("【】");
        paramString.append(localStringBuilder.toString());
      }
    }
    if (paramString.length() > 0) {
      paramString.delete(paramString.length() - 2, paramString.length());
    }
    return paramString.toString();
  }
  
  private List<String> a(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    if (TextUtils.isEmpty(paramString))
    {
      localArrayList.add(a.a.a());
      return localArrayList;
    }
    try
    {
      paramString = (JSONObject)new JSONTokener(paramString).nextValue();
      int i = paramString.getInt("ret");
      if (i != 0)
      {
        if (i != 509)
        {
          switch (i)
          {
          default: 
            localArrayList.add(a.h.a());
            return localArrayList;
          case 502: 
            localArrayList.add(a.f.a());
            return localArrayList;
          }
          localArrayList.add(a.e.a());
          return localArrayList;
        }
        localArrayList.add(a.g.a());
        return localArrayList;
      }
      if (paramString != null) {}
      try
      {
        if (paramString.getJSONArray("targetText") != null)
        {
          JSONArray localJSONArray = paramString.getJSONArray("targetText");
          i = 0;
          if (localJSONArray.getString(0) != null)
          {
            paramString = paramString.getJSONArray("targetText");
            while (i < paramString.length())
            {
              localArrayList.add(paramString.getString(i));
              i += 1;
            }
          }
        }
        localArrayList.add(a.c.a());
        return localArrayList;
      }
      catch (JSONException paramString)
      {
        paramString.printStackTrace();
        localArrayList.add(a.c.a());
      }
      return localArrayList;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
      if ((paramString instanceof JSONException))
      {
        localArrayList.add(a.b.a());
        return localArrayList;
      }
      localArrayList.add(a.a.a());
    }
    return localArrayList;
  }
  
  private List<String> a(String[] paramArrayOfString)
  {
    Object localObject3 = null;
    Object localObject1 = null;
    BufferedReader localBufferedReader = null;
    try
    {
      Object localObject2 = new ArrayList(2);
      ((List)localObject2).add(new BasicNameValuePair("guid", SmttServiceProxy.getInstance().getGUID()));
      Object localObject4 = new JSONArray();
      int i = 0;
      while (i < paramArrayOfString.length)
      {
        ((JSONArray)localObject4).put(paramArrayOfString[i]);
        i += 1;
      }
      ((List)localObject2).add(new BasicNameValuePair("sourceTexts", ((JSONArray)localObject4).toString()));
      ((List)localObject2).add(new BasicNameValuePair("sourceLang", "-1"));
      ((List)localObject2).add(new BasicNameValuePair("targetLang", "-1"));
      ((List)localObject2).add(new BasicNameValuePair("key", this.jdField_c_of_type_JavaLangString));
      localObject4 = URLEncodedUtils.format((List)localObject2, "UTF-8");
      localObject2 = (HttpsURLConnection)new URL("https://mqb.translator.qq.com/translate").openConnection();
      paramArrayOfString = (String[])localObject3;
      try
      {
        ((HttpsURLConnection)localObject2).setDoInput(true);
        paramArrayOfString = (String[])localObject3;
        ((HttpsURLConnection)localObject2).setDoOutput(false);
        paramArrayOfString = (String[])localObject3;
        ((HttpsURLConnection)localObject2).setUseCaches(false);
        paramArrayOfString = (String[])localObject3;
        ((HttpsURLConnection)localObject2).setConnectTimeout(5000);
        paramArrayOfString = (String[])localObject3;
        ((HttpsURLConnection)localObject2).setReadTimeout(5000);
        paramArrayOfString = (String[])localObject3;
        ((HttpsURLConnection)localObject2).setRequestProperty("Charset", "UTF-8");
        paramArrayOfString = (String[])localObject3;
        ((HttpsURLConnection)localObject2).setRequestMethod("POST");
        paramArrayOfString = (String[])localObject3;
        ((HttpsURLConnection)localObject2).connect();
        paramArrayOfString = (String[])localObject3;
        localObject1 = new DataOutputStream(((HttpsURLConnection)localObject2).getOutputStream());
        paramArrayOfString = (String[])localObject3;
        ((DataOutputStream)localObject1).writeBytes((String)localObject4);
        paramArrayOfString = (String[])localObject3;
        ((DataOutputStream)localObject1).flush();
        paramArrayOfString = (String[])localObject3;
        ((DataOutputStream)localObject1).close();
        localObject1 = localBufferedReader;
        paramArrayOfString = (String[])localObject3;
        if (((HttpsURLConnection)localObject2).getResponseCode() == 200)
        {
          paramArrayOfString = (String[])localObject3;
          localObject1 = new StringBuffer();
          paramArrayOfString = (String[])localObject3;
          localBufferedReader = new BufferedReader(new InputStreamReader(((HttpsURLConnection)localObject2).getInputStream(), "UTF-8"));
          for (;;)
          {
            paramArrayOfString = (String[])localObject3;
            localObject4 = localBufferedReader.readLine();
            if (localObject4 == null) {
              break;
            }
            paramArrayOfString = (String[])localObject3;
            ((StringBuffer)localObject1).append((String)localObject4);
          }
          paramArrayOfString = (String[])localObject3;
          localObject1 = ((StringBuffer)localObject1).toString();
          paramArrayOfString = (String[])localObject1;
          localBufferedReader.close();
        }
        paramArrayOfString = (String[])localObject1;
        ((HttpsURLConnection)localObject2).disconnect();
        paramArrayOfString = (String[])localObject1;
      }
      catch (Exception localException2)
      {
        localObject1 = localObject2;
        localObject2 = localException2;
      }
      ((HttpsURLConnection)localObject1).disconnect();
    }
    catch (Exception localException1)
    {
      paramArrayOfString = null;
    }
    localException1.printStackTrace();
    return a(paramArrayOfString);
  }
  
  private void a(int paramInt, String paramString)
  {
    Message localMessage = this.jdField_a_of_type_AndroidOsHandler.obtainMessage();
    localMessage.what = paramInt;
    Bundle localBundle = new Bundle();
    localBundle.putString("CONTENTS", paramString);
    localMessage.setData(localBundle);
    localMessage.sendToTarget();
  }
  
  private void a(final String paramString1, final String paramString2)
  {
    i locali = this.jdField_a_of_type_ComTencentSmttWebkitUiI;
    if (locali != null) {
      locali.dismiss();
    }
    if (!a(this.jdField_b_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext())) {
      return;
    }
    this.jdField_a_of_type_ComTencentSmttWebkitUiI = new i(this.jdField_b_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext(), paramString1, paramString2, new TriggerLinearout.OnSizeChangeCallback()
    {
      public void onSizeChangeCallback()
      {
        r.a(r.this, paramString1, paramString2);
      }
    });
    paramString1 = SmttResource.getString("x5_toast_copy_translate_result", "string");
    this.jdField_a_of_type_ComTencentSmttWebkitUiI.a(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ((ClipboardManager)r.b(r.this).getContext().getApplicationContext().getSystemService("clipboard")).setText(paramString2);
        r.this.a.dismiss();
        SmttServiceProxy.getInstance().userBehaviorStatistics("CH0042");
        Toast.makeText(r.b(r.this).getContext(), paramString1, 0).show();
      }
    });
    this.jdField_a_of_type_ComTencentSmttWebkitUiI.b(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        r.this.a.dismiss();
        SmttServiceProxy.getInstance().userBehaviorStatistics("CH0043");
      }
    });
    this.jdField_a_of_type_ComTencentSmttWebkitUiI.setCanceledOnTouchOutside(false);
    this.jdField_a_of_type_ComTencentSmttWebkitUiI.show();
  }
  
  private boolean a(Context paramContext)
  {
    if (paramContext != null)
    {
      if (!(paramContext instanceof Activity)) {
        return false;
      }
      paramContext = (Activity)paramContext;
      if (paramContext.isFinishing()) {
        return false;
      }
      return (Build.VERSION.SDK_INT < 17) || (!paramContext.isDestroyed());
    }
    return false;
  }
  
  private boolean a(String paramString1, String paramString2, boolean paramBoolean)
  {
    if ((paramString1 != null) && (paramString2 != null))
    {
      paramString1 = Pattern.compile(paramString1, 2).matcher(paramString2);
      if (paramBoolean) {
        return paramString1.find();
      }
      return paramString1.matches();
    }
    return false;
  }
  
  private void b(String paramString1, String paramString2)
  {
    Message localMessage = this.jdField_a_of_type_AndroidOsHandler.obtainMessage();
    localMessage.what = 4;
    Bundle localBundle = new Bundle();
    localBundle.putString("ORIGINAL_CONTENT", paramString1);
    localBundle.putString("TRANSLATED_CONTENT", paramString2);
    localMessage.setData(localBundle);
    localMessage.sendToTarget();
  }
  
  private String c()
  {
    return "function getForeignText(){var b,c,d,e,a=document.createTreeWalker(document.getElementsByTagName('html')[0],NodeFilter.SHOW_TEXT,null,!1);for(mttTextNodes=[],c=0,d={script:1,style:1,noscript:1,embed:1,object:1};b=a.nextNode();)e=b.parentNode.nodeName.toLowerCase(),/[a-zA-Z]+/.test(b.nodeValue)&&!d[e]&&(mttTextNodes.push(b),mttOriginStr=0==c?b.nodeValue.trim():mttOriginStr.concat(mttSeparator+b.nodeValue.trim()),c++);return mttOriginStr}function injectText(a){var b,c;if(!(a.length<=0))for(b=a.split(mttSeparator),c=0;c<b.length;c++)mttTextNodes[c].nodeValue=b[c]}function switchToOriginalLan(){injectText(mttOriginStr)}function switchToTranslatedLan(){injectText(mttTranslatedStr)}var mttTextNodes,mttSeparator='【】',mttOriginStr='',mttTranslatedStr='';getForeignText();";
  }
  
  private void c()
  {
    this.jdField_a_of_type_AndroidOsHandler = new Handler(Looper.getMainLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        int i = paramAnonymousMessage.what;
        final String str = paramAnonymousMessage.getData().getString("CONTENTS");
        if (i == 1) {
          BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
          {
            public void doRun()
            {
              String str = r.a(r.this, str);
              r.a locala = r.a.a(str);
              if ((locala != null) && (r.a(r.this) != null))
              {
                r.a(r.this).showTranslateBubble(-1, "", "", locala.a());
                r.a(r.this, (short)0);
                r.a(r.this, false);
                r.b(r.this, false);
                r.a(r.this).b(locala.a());
                return;
              }
              if (!r.a(r.this)) {
                r.a(r.this, 2, str);
              }
            }
          });
        }
        if (i == 2)
        {
          r.b(r.this, str);
          r.a(r.this, false);
          r.b(r.this, false);
          Object localObject = r.a(r.this);
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("mttTranslatedStr='");
          localStringBuilder.append(str);
          localStringBuilder.append("';");
          ((TencentWebViewProxy)localObject).evaluateJavascript(localStringBuilder.toString(), null);
          localObject = r.this;
          ((r)localObject).b(r.a((r)localObject));
        }
        if (i == 3) {
          BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
          {
            public void doRun()
            {
              String str = r.a(r.this, str);
              if (r.a.a(str) != null) {
                return;
              }
              r.a(r.this, str, str);
            }
          });
        }
        if (i == 4)
        {
          str = paramAnonymousMessage.getData().getString("ORIGINAL_CONTENT");
          paramAnonymousMessage = paramAnonymousMessage.getData().getString("TRANSLATED_CONTENT");
          r.b(r.this, str, paramAnonymousMessage);
        }
      }
    };
  }
  
  private boolean e(String paramString)
  {
    if (paramString == null) {
      return false;
    }
    paramString = paramString.trim();
    StringBuffer localStringBuffer = new StringBuffer();
    Matcher localMatcher = Pattern.compile("[\\x00-\\x7E\\s]+", 2).matcher(paramString);
    while (localMatcher.find()) {
      localStringBuffer.append(localMatcher.group());
    }
    return localStringBuffer.toString().length() / paramString.length() > 0.958F;
  }
  
  private boolean f(String paramString)
  {
    return paramString.equals("utf-8");
  }
  
  public int a()
  {
    return this.jdField_a_of_type_Short;
  }
  
  public b a()
  {
    return this.jdField_a_of_type_ComTencentSmttWebkitR$b;
  }
  
  public String a()
  {
    return this.jdField_b_of_type_JavaLangString;
  }
  
  public void a()
  {
    this.jdField_b_of_type_JavaLangString = "NONE";
    this.d = "";
    this.jdField_a_of_type_Short = 0;
    this.jdField_a_of_type_JavaLangString = null;
    this.jdField_a_of_type_Boolean = false;
    this.jdField_b_of_type_Boolean = false;
    this.jdField_c_of_type_Boolean = true;
    b localb = this.jdField_a_of_type_ComTencentSmttWebkitR$b;
    if (localb == null)
    {
      this.jdField_a_of_type_ComTencentSmttWebkitR$b = new b();
      return;
    }
    localb.a();
  }
  
  public void a(TencentWebViewProxy paramTencentWebViewProxy)
  {
    if (this.jdField_a_of_type_Short == 1)
    {
      paramTencentWebViewProxy.evaluateJavascript("switchToOriginalLan()", null);
      this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension.showTranslateBubble(4, "zh", "en", 0);
      this.jdField_a_of_type_Short = 2;
      this.jdField_a_of_type_JavaUtilHashSet.remove(this.d);
      this.jdField_a_of_type_ComTencentSmttWebkitR$b.c();
    }
  }
  
  public void a(TencentWebViewProxy paramTencentWebViewProxy, String paramString)
  {
    this.jdField_b_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
    a(3, paramString);
  }
  
  public void a(String paramString)
  {
    this.jdField_b_of_type_JavaLangString = paramString;
    this.jdField_a_of_type_ComTencentSmttWebkitR$b.b(paramString.equals("en"));
  }
  
  public void a(boolean paramBoolean)
  {
    this.jdField_c_of_type_Boolean = paramBoolean;
    this.jdField_a_of_type_ComTencentSmttWebkitR$b.a(paramBoolean);
  }
  
  public boolean a()
  {
    return this.jdField_c_of_type_Boolean;
  }
  
  public boolean a(String paramString)
  {
    return (!TextUtils.isEmpty(paramString)) && (paramString.startsWith("file:///")) && (paramString.endsWith(".mht"));
  }
  
  public boolean a(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.jdField_a_of_type_ComTencentSmttWebkitR$b.h();
    this.jdField_a_of_type_ComTencentSmttWebkitR$b.g();
    if ((paramString3 != null) && (!paramString3.isEmpty()) && ((b(paramString3)) || (!d(paramString3)))) {
      return false;
    }
    this.jdField_a_of_type_ComTencentSmttWebkitR$b.g();
    if ((paramString1 != null) && (paramString1.contains("en")))
    {
      boolean bool = c(paramString4);
      if ((bool) || (!e(paramString4))) {
        this.jdField_a_of_type_ComTencentSmttWebkitR$b.c(this.d);
      }
      if (!bool) {
        return true;
      }
    }
    this.jdField_a_of_type_ComTencentSmttWebkitR$b.g();
    if (paramString2 != null) {
      paramString2.contains("en");
    }
    this.jdField_a_of_type_ComTencentSmttWebkitR$b.g();
    if ((paramString4 != null) && (d(paramString4))) {
      return true;
    }
    this.jdField_a_of_type_ComTencentSmttWebkitR$b.g();
    if ((!TextUtils.isEmpty(paramString1)) && (!f(paramString1))) {
      return false;
    }
    this.jdField_a_of_type_ComTencentSmttWebkitR$b.g();
    if ((paramString4.contains("ä")) && (paramString4.contains("ö")) && (paramString4.contains("ü"))) {
      return false;
    }
    this.jdField_a_of_type_ComTencentSmttWebkitR$b.g();
    if (e(paramString4)) {
      return true;
    }
    this.jdField_a_of_type_ComTencentSmttWebkitR$b.g();
    return false;
  }
  
  public String b()
  {
    return this.d;
  }
  
  public void b()
  {
    if (!this.jdField_a_of_type_Boolean) {
      return;
    }
    this.jdField_b_of_type_Boolean = true;
    this.jdField_a_of_type_Boolean = false;
  }
  
  public void b(TencentWebViewProxy paramTencentWebViewProxy)
  {
    paramTencentWebViewProxy.evaluateJavascript("switchToTranslatedLan()", null);
    this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension.showTranslateBubble(3, "en", "zh", 0);
    this.jdField_a_of_type_Short = 1;
    this.jdField_a_of_type_JavaUtilHashSet.add(this.d);
    this.jdField_a_of_type_ComTencentSmttWebkitR$b.b();
    this.jdField_a_of_type_ComTencentSmttWebkitR$b.i();
  }
  
  public void b(String paramString)
  {
    this.d = paramString;
    this.jdField_a_of_type_ComTencentSmttWebkitR$b.a(paramString);
  }
  
  public boolean b(String paramString)
  {
    return a("[\\u4e00-\\u9fa5]+", paramString, true);
  }
  
  public void c(TencentWebViewProxy paramTencentWebViewProxy)
  {
    if (a(b()))
    {
      this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension.showTranslateBubble(-1, "", "", -3);
      return;
    }
    if ((!a().equals("en")) && (this.jdField_a_of_type_Short == 0))
    {
      this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension.showTranslateBubble(-2, "", "", -101);
      this.jdField_a_of_type_ComTencentSmttWebkitR$b.d();
      return;
    }
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
    if (this.jdField_a_of_type_Boolean)
    {
      paramTencentWebViewProxy = this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension;
      if (paramTencentWebViewProxy != null) {
        paramTencentWebViewProxy.showTranslateBubble(-2, "", "", -102);
      }
      return;
    }
    if (this.jdField_a_of_type_Short == 1)
    {
      paramTencentWebViewProxy = this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension;
      if (paramTencentWebViewProxy != null) {
        paramTencentWebViewProxy.showTranslateBubble(-2, "", "", -103);
      }
      return;
    }
    this.jdField_b_of_type_Boolean = false;
    this.jdField_a_of_type_Boolean = true;
    paramTencentWebViewProxy.evaluateJavascript(c(), new ValueCallback()
    {
      public void a(String paramAnonymousString)
      {
        paramAnonymousString = paramAnonymousString.substring(1, paramAnonymousString.length() - 1).replace("\\u003C", "<").replace("|", " ");
        if (!r.a(r.this)) {
          r.a(r.this, 1, paramAnonymousString);
        }
      }
    });
  }
  
  public boolean c(String paramString)
  {
    if (paramString != null)
    {
      if (TextUtils.isEmpty(paramString)) {
        return false;
      }
      return a("[\\u4e00-\\u9fa5\\s|\\p{P}]+", paramString, false);
    }
    return false;
  }
  
  public boolean d(String paramString)
  {
    return a("^[\\x00-\\x7E\\s|\\p{P}|\\p{S}]+", paramString, false);
  }
  
  private static enum a
  {
    private int jdField_a_of_type_Int;
    private String jdField_a_of_type_JavaLangString;
    
    static
    {
      jdField_a_of_type_ComTencentSmttWebkitR$a = new a("NETWORK_ERROR", 0, "network_error", -1);
      b = new a("PARSE_ERROR", 1, "parse_error", -2);
      c = new a("HAS_NO_TRANS", 2, "has_no_trans", -3);
      d = new a("USER_STOP", 3, "user_stop", -4);
      e = new a("SERVER_BUSY", 4, "server_busy", 65035);
      f = new a("IDENTIFY_ERROR", 5, "identify_error", 65034);
      g = new a("SERVER_ERROR", 6, "server_error", 65027);
      h = new a("UNKNOW_ERROR", 7, "unknow_error", 65026);
    }
    
    private a(String paramString, int paramInt)
    {
      this.jdField_a_of_type_JavaLangString = paramString;
      this.jdField_a_of_type_Int = paramInt;
    }
    
    public static a a(String paramString)
    {
      a[] arrayOfa = values();
      int j = arrayOfa.length;
      int i = 0;
      while (i < j)
      {
        a locala = arrayOfa[i];
        if (locala.a().equals(paramString)) {
          return locala;
        }
        i += 1;
      }
      return null;
    }
    
    public int a()
    {
      return this.jdField_a_of_type_Int;
    }
    
    public String a()
    {
      return this.jdField_a_of_type_JavaLangString;
    }
  }
  
  public class b
  {
    private int jdField_a_of_type_Int;
    private String jdField_a_of_type_JavaLangString;
    private boolean jdField_a_of_type_Boolean;
    private int jdField_b_of_type_Int;
    private String jdField_b_of_type_JavaLangString;
    private boolean jdField_b_of_type_Boolean;
    private int jdField_c_of_type_Int;
    private String jdField_c_of_type_JavaLangString;
    private int d;
    private int e;
    private int f;
    
    public b()
    {
      a();
    }
    
    private void a(HashMap<String, String> paramHashMap)
    {
      if (e.a().n()) {
        SmttServiceProxy.getInstance().upLoadToBeacon("MTT_CORE_TRANSLATE_INFO", paramHashMap);
      }
    }
    
    public void a()
    {
      this.jdField_a_of_type_Boolean = true;
      this.jdField_a_of_type_JavaLangString = null;
      this.jdField_b_of_type_Boolean = false;
      this.jdField_b_of_type_JavaLangString = null;
      this.jdField_a_of_type_Int = 0;
      this.jdField_b_of_type_Int = 0;
      this.jdField_c_of_type_JavaLangString = null;
      this.jdField_c_of_type_Int = 0;
      this.d = 0;
      this.e = 0;
      this.f = 0;
    }
    
    public void a(String paramString)
    {
      if (this.jdField_a_of_type_JavaLangString == null)
      {
        this.jdField_a_of_type_JavaLangString = paramString;
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
      localStringBuilder.append(" |-| ");
      localStringBuilder.append(paramString);
      this.jdField_a_of_type_JavaLangString = localStringBuilder.toString();
    }
    
    public void a(boolean paramBoolean)
    {
      this.jdField_a_of_type_Boolean = paramBoolean;
    }
    
    public void b()
    {
      this.jdField_a_of_type_Int += 1;
    }
    
    public void b(String paramString)
    {
      this.jdField_c_of_type_JavaLangString = paramString;
    }
    
    public void b(boolean paramBoolean)
    {
      this.jdField_b_of_type_Boolean = paramBoolean;
    }
    
    public void c()
    {
      this.jdField_b_of_type_Int += 1;
    }
    
    public void c(String paramString)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("url", paramString);
      localHashMap.put("is_empty_page", "1");
      a(localHashMap);
    }
    
    public void d()
    {
      this.jdField_c_of_type_Int += 1;
    }
    
    public void e()
    {
      this.e += 1;
    }
    
    public void f()
    {
      this.f += 1;
    }
    
    public void g()
    {
      this.d += 1;
    }
    
    public void h()
    {
      this.d = 0;
    }
    
    public void i()
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("guid", SmttServiceProxy.getInstance().getGUID());
      localHashMap.put("translate", "1");
      a(localHashMap);
    }
    
    public void j()
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("url", this.jdField_a_of_type_JavaLangString);
      localHashMap.put("translate_in_nonEng", "1");
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("");
      localStringBuilder.append(this.d);
      localHashMap.put("language_detect", localStringBuilder.toString());
      a(localHashMap);
    }
    
    public void k()
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("is_english", "1");
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("");
      localStringBuilder.append(this.d);
      localHashMap.put("language_detect", localStringBuilder.toString());
      a(localHashMap);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\r.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */