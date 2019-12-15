package com.tencent.smtt.webkit;

import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.webkit.ValueCallback;
import android.webview.chromium.tencent.TencentContentSettingsAdapter;
import android.webview.chromium.tencent.TencentWebViewChromium;
import android.widget.AbsoluteLayout.LayoutParams;
import com.tencent.smtt.export.external.interfaces.IX5WebHistoryItem;
import com.tencent.tbs.core.webkit.WebSettings;
import com.tencent.tbs.core.webkit.WebView;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewClient;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.chromium.android_webview.AwContents;
import org.chromium.base.ThreadUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReadModeManager
{
  private static int jdField_a_of_type_Int = -1;
  private static String jdField_a_of_type_JavaLangString;
  private static int jdField_b_of_type_Int = -1;
  private static String jdField_b_of_type_JavaLangString;
  private static int jdField_c_of_type_Int = -1;
  private static String jdField_c_of_type_JavaLangString;
  private static int jdField_d_of_type_Int = -1;
  private static String jdField_d_of_type_JavaLangString;
  private static int jdField_e_of_type_Int = -1;
  private static String jdField_e_of_type_JavaLangString = "<[^>]+>";
  private static int jdField_f_of_type_Int = -1;
  private static String jdField_f_of_type_JavaLangString = "news";
  private static int jdField_g_of_type_Int = -1;
  private static String jdField_g_of_type_JavaLangString = "img_page";
  private static String jdField_h_of_type_JavaLangString = "catalog_page";
  private static String i = "catalog_page_by_url";
  private long jdField_a_of_type_Long = 0L;
  private ValueCallback<Boolean> jdField_a_of_type_AndroidWebkitValueCallback = null;
  private a jdField_a_of_type_ComTencentSmttWebkitReadModeManager$a = null;
  private b jdField_a_of_type_ComTencentSmttWebkitReadModeManager$b = null;
  private d jdField_a_of_type_ComTencentSmttWebkitReadModeManager$d = null;
  private TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = null;
  private Runnable jdField_a_of_type_JavaLangRunnable = null;
  private WeakReference<TencentWebViewProxy> jdField_a_of_type_JavaLangRefWeakReference = null;
  private Queue<d> jdField_a_of_type_JavaUtilQueue = new LinkedList();
  private boolean jdField_a_of_type_Boolean = false;
  private int jdField_h_of_type_Int = 0;
  private String j = null;
  private String k = null;
  private String l = null;
  private String m = null;
  private String n = null;
  private String o = null;
  
  public ReadModeManager(TencentWebViewProxy paramTencentWebViewProxy)
  {
    this.jdField_a_of_type_JavaLangRefWeakReference = new WeakReference(paramTencentWebViewProxy);
  }
  
  private int a()
  {
    if (jdField_e_of_type_Int == -1) {
      jdField_e_of_type_Int = RenderSmttService.getReportConfigI("MTT_CORE_WEBPAGE_READ_MODE", "min_content", 100);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("    getMinContentTextCount=");
    localStringBuilder.append(jdField_e_of_type_Int);
    b("ReadModeManager", localStringBuilder.toString());
    return jdField_e_of_type_Int;
  }
  
  private String a(c paramc, String paramString)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("generateReadModeHtml siteType=");
    ((StringBuilder)localObject1).append(paramString);
    b("ReadModeManager", ((StringBuilder)localObject1).toString());
    if (paramc == null) {
      return "";
    }
    paramString = h();
    String str1 = i();
    String str3;
    String str2;
    Object localObject3;
    if ((!TextUtils.isEmpty(paramString)) && (!TextUtils.isEmpty(str1)))
    {
      if (jdField_h_of_type_JavaLangString.equals(paramc.i)) {
        paramc.jdField_a_of_type_JavaLangString = "目录";
      }
      if (jdField_g_of_type_JavaLangString.equals(paramc.i)) {
        localObject1 = "style=\"margin-left: 0px; margin-right: 0px\"";
      } else {
        localObject1 = null;
      }
      str1 = paramString.replace("$1", paramc.jdField_a_of_type_JavaLangString).replace("$2", str1).replace("$3", "light sans-serif").replace("<noscript>$4</noscript>", paramc.jdField_a_of_type_JavaLangString).replace("<noscript>$5</noscript>", paramc.b);
      paramString = str1;
      if (localObject1 != null)
      {
        paramString = new StringBuilder();
        paramString.append("<div id=\"content\" ");
        paramString.append((String)localObject1);
        paramString.append(">");
        paramString = str1.replace("<div id=\"content\">", paramString.toString());
      }
      str1 = paramString;
      if (e()) {
        str1 = paramString.replace("$7", "true").replace("$8", f());
      }
      localObject1 = "";
      if (((!jdField_h_of_type_JavaLangString.equals(paramc.i)) && (!i.equals(paramc.i))) || ((paramc.j == null) && (paramc.k == null) && (paramc.l == null)))
      {
        if ((paramc.c == null) && (paramc.d == null) && (paramc.jdField_e_of_type_JavaLangString == null))
        {
          paramString = (String)localObject1;
          if (this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$b == null) {}
        }
        else
        {
          paramString = (String)localObject1;
          if (paramc.c != null)
          {
            paramString = (String)localObject1;
            if (paramc.c.startsWith("http")) {
              if (paramc.jdField_g_of_type_JavaLangString == null)
              {
                paramString = new StringBuilder();
                paramString.append("");
                paramString.append("<a class='nav prev' href='");
                paramString.append(a(paramc.c, "mtt_in_readmode=1"));
                paramString.append("'>上一页</a>");
                paramString = paramString.toString();
              }
              else
              {
                paramString = new StringBuilder();
                paramString.append("");
                paramString.append("<a class='nav prev' href='");
                paramString.append(a(paramc.c, "mtt_in_readmode=1"));
                paramString.append("' ");
                paramString.append("style=\"width: 90%; position: inherit; margin-right: 0; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;\"");
                paramString.append(">");
                paramString.append(paramc.jdField_g_of_type_JavaLangString);
                paramString.append("</a> <br>");
                paramString = paramString.toString();
              }
            }
          }
          localObject1 = this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$b;
          if ((localObject1 != null) && (((b)localObject1).jdField_a_of_type_JavaLangString.startsWith("http")) && (this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$b.c != null) && (paramc.jdField_f_of_type_JavaLangString.contains(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$b.c)))
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append(paramString);
            ((StringBuilder)localObject1).append("<a class='nav catalog' href='");
            ((StringBuilder)localObject1).append(a(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$b.jdField_a_of_type_JavaLangString, "readmode_catalog_page_url=1&mtt_in_readmode=1"));
            ((StringBuilder)localObject1).append("'>目录</a>");
            localObject1 = ((StringBuilder)localObject1).toString();
          }
          else
          {
            localObject1 = this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$b;
            if ((localObject1 != null) && (((b)localObject1).jdField_a_of_type_JavaLangString.startsWith("http")))
            {
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append(paramString);
              ((StringBuilder)localObject1).append("<a class='nav catalog' href='");
              ((StringBuilder)localObject1).append(a(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$b.jdField_a_of_type_JavaLangString, "readmode_catalog_page=1&mtt_in_readmode=1"));
              ((StringBuilder)localObject1).append("'>目录</a>");
              localObject1 = ((StringBuilder)localObject1).toString();
            }
            else
            {
              localObject1 = paramString;
              if (paramc.d != null)
              {
                localObject1 = paramString;
                if (paramc.d.startsWith("http"))
                {
                  localObject1 = new StringBuilder();
                  ((StringBuilder)localObject1).append(paramString);
                  ((StringBuilder)localObject1).append("<a class='nav catalog' href='");
                  ((StringBuilder)localObject1).append(a(paramc.d, "readmode_catalog_page=1&mtt_in_readmode=1"));
                  ((StringBuilder)localObject1).append("'>目录</a>");
                  localObject1 = ((StringBuilder)localObject1).toString();
                }
              }
            }
          }
          paramString = (String)localObject1;
          if (paramc.jdField_e_of_type_JavaLangString != null)
          {
            paramString = (String)localObject1;
            if (paramc.jdField_e_of_type_JavaLangString.startsWith("http")) {
              if (paramc.jdField_h_of_type_JavaLangString == null)
              {
                paramString = new StringBuilder();
                paramString.append((String)localObject1);
                paramString.append("<a class='nav next' href='");
                paramString.append(a(paramc.jdField_e_of_type_JavaLangString, "mtt_in_readmode=1"));
                paramString.append("'>下一页</a>");
                paramString = paramString.toString();
              }
              else
              {
                paramString = new StringBuilder();
                paramString.append((String)localObject1);
                paramString.append("<a class='nav prev' href='");
                paramString.append(a(paramc.jdField_e_of_type_JavaLangString, "mtt_in_readmode=1"));
                paramString.append("' ");
                paramString.append("style=\"width: 90%; position: inherit; margin-right: 0; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;\"");
                paramString.append(">");
                paramString.append(paramc.jdField_h_of_type_JavaLangString);
                paramString.append("</a>");
                paramString = paramString.toString();
              }
            }
          }
        }
      }
      else
      {
        str3 = b(paramc.jdField_f_of_type_JavaLangString);
        str2 = "readmode_catalog_page=1&mtt_in_readmode=1";
        if (i.equals(paramc.i)) {
          str2 = "readmode_catalog_page_url=1&mtt_in_readmode=1";
        }
        if ((paramc.k != null) && (paramc.k.startsWith("http")))
        {
          paramString = new StringBuilder();
          paramString.append("");
          paramString.append("<a class='nav catalog' href='");
          paramString.append(a(paramc.k, str2));
          paramString.append("'>完整目录</a>");
          paramString = paramString.toString();
        }
        else
        {
          if ((paramc.j != null) && (paramc.j.startsWith("http")))
          {
            paramString = new StringBuilder();
            paramString.append("");
            paramString.append("<a class='nav prev' href='");
            paramString.append(a(paramc.j, str2));
            paramString.append("'>上一页</a>");
            paramString = paramString.toString();
          }
          else
          {
            paramString = new StringBuilder();
            paramString.append("");
            paramString.append("<a class='nav prev' disabled='disabled'></a>");
            paramString = paramString.toString();
          }
          localObject3 = paramString;
          if (paramc.m != null) {
            localObject1 = "";
          }
        }
      }
    }
    for (;;)
    {
      int i1;
      try
      {
        JSONArray localJSONArray = new JSONArray(paramc.m);
        i1 = 0;
        if (i1 < localJSONArray.length())
        {
          JSONObject localJSONObject = localJSONArray.getJSONObject(i1);
          Iterator localIterator = localJSONObject.keys();
          if (!localIterator.hasNext()) {
            break label1727;
          }
          String str4 = (String)localIterator.next();
          if (str4 == null) {
            localObject3 = null;
          } else {
            localObject3 = localJSONObject.optString(str4, null);
          }
          if ((str4 == null) || (localObject3 == null) || (!((String)localObject3).startsWith("http"))) {
            continue;
          }
          if (((String)localObject3).equals(str3))
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append((String)localObject1);
            localStringBuilder.append("<option value='");
            localStringBuilder.append(a((String)localObject3, str2));
            localStringBuilder.append("' selected='selected'>");
            localStringBuilder.append(str4);
            localStringBuilder.append("</option>");
            localObject1 = localStringBuilder.toString();
            continue;
          }
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append((String)localObject1);
          localStringBuilder.append("<option value='");
          localStringBuilder.append(a((String)localObject3, str2));
          localStringBuilder.append("'>");
          localStringBuilder.append(str4);
          localStringBuilder.append("</option>");
          localObject1 = localStringBuilder.toString();
          continue;
        }
        localObject3 = paramString;
        if (!TextUtils.isEmpty((CharSequence)localObject1))
        {
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append(paramString);
          ((StringBuilder)localObject3).append("<select onchange='self.location.href=options[selectedIndex].value' style='width:35%;border:1px solid #4C9AFA;'>");
          ((StringBuilder)localObject3).append((String)localObject1);
          ((StringBuilder)localObject3).append("</select>");
          localObject3 = ((StringBuilder)localObject3).toString();
        }
      }
      catch (Exception localException)
      {
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("generateReadModeHtml selectOptionString Exception=");
        ((StringBuilder)localObject3).append(localException.toString());
        b("ReadModeManager", ((StringBuilder)localObject3).toString());
        localObject3 = paramString;
      }
      if ((paramc.l != null) && (paramc.l.startsWith("http")))
      {
        paramString = new StringBuilder();
        paramString.append((String)localObject3);
        paramString.append("<a class='nav next' href='");
        paramString.append(a(paramc.l, str2));
        paramString.append("'>下一页</a>");
        paramString = paramString.toString();
      }
      else
      {
        paramString = new StringBuilder();
        paramString.append((String)localObject3);
        paramString.append("<a class='nav next' disabled='disabled'></a>");
        paramString = paramString.toString();
      }
      Object localObject2 = str1;
      if (!TextUtils.isEmpty(paramString)) {
        localObject2 = str1.replace("nav-wrapper hidden", "nav-wrapper").replace("$6", paramString);
      }
      if ((i.equals(paramc.i)) || (jdField_h_of_type_JavaLangString.equals(paramc.i))) {
        this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$b = new b(b(paramc.jdField_f_of_type_JavaLangString), (String)localObject2, paramc.n);
      }
      return (String)localObject2;
      return "";
      label1727:
      i1 += 1;
    }
  }
  
  public static String a(String paramString1, String paramString2)
  {
    if (!TextUtils.isEmpty(paramString1))
    {
      if (TextUtils.isEmpty(paramString2)) {
        return paramString1;
      }
      String str2 = "";
      int i1 = paramString1.indexOf('#');
      String str1 = paramString1;
      if (i1 > 0)
      {
        str1 = paramString1.substring(0, i1);
        str2 = paramString1.substring(i1);
      }
      paramString1 = "?";
      if (str1.contains("?")) {
        paramString1 = "&";
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(str1);
      localStringBuilder.append(paramString1);
      localStringBuilder.append(paramString2);
      localStringBuilder.append(str2);
      return localStringBuilder.toString();
    }
    return paramString1;
  }
  
  private void a(TencentWebViewProxy paramTencentWebViewProxy, String paramString, ValueCallback<String> paramValueCallback)
  {
    a(paramTencentWebViewProxy, paramString, paramValueCallback, 0L);
  }
  
  private void a(final TencentWebViewProxy paramTencentWebViewProxy, final String paramString, final ValueCallback<String> paramValueCallback, long paramLong)
  {
    if ((paramTencentWebViewProxy != null) && (paramTencentWebViewProxy.getRealWebView() != null))
    {
      paramTencentWebViewProxy.getRealWebView().postDelayed(new Runnable()
      {
        public void run()
        {
          paramTencentWebViewProxy.evaluateJavascript(paramString, paramValueCallback);
        }
      }, paramLong);
      return;
    }
    if (paramValueCallback != null) {
      paramValueCallback.onReceiveValue(null);
    }
  }
  
  /* Error */
  public static void a(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 195	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifeq +12 -> 16
    //   7: ldc -102
    //   9: ldc_w 430
    //   12: invokestatic 161	com/tencent/smtt/webkit/ReadModeManager:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   15: return
    //   16: invokestatic 432	com/tencent/smtt/webkit/ReadModeManager:c	()Z
    //   19: ifne +12 -> 31
    //   22: ldc -102
    //   24: ldc_w 434
    //   27: invokestatic 161	com/tencent/smtt/webkit/ReadModeManager:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   30: return
    //   31: getstatic 435	com/tencent/smtt/webkit/ReadModeManager:b	Ljava/lang/String;
    //   34: invokestatic 195	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   37: ifne +56 -> 93
    //   40: getstatic 436	com/tencent/smtt/webkit/ReadModeManager:c	Ljava/lang/String;
    //   43: invokestatic 195	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   46: ifne +47 -> 93
    //   49: getstatic 437	com/tencent/smtt/webkit/ReadModeManager:d	Ljava/lang/String;
    //   52: invokestatic 195	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   55: ifeq +6 -> 61
    //   58: goto +35 -> 93
    //   61: new 142	java/lang/StringBuilder
    //   64: dup
    //   65: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   68: astore_1
    //   69: aload_1
    //   70: ldc_w 439
    //   73: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   76: pop
    //   77: aload_1
    //   78: aload_0
    //   79: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   82: pop
    //   83: ldc -102
    //   85: aload_1
    //   86: invokevirtual 158	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   89: invokestatic 161	com/tencent/smtt/webkit/ReadModeManager:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   92: return
    //   93: new 142	java/lang/StringBuilder
    //   96: dup
    //   97: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   100: astore_1
    //   101: aload_1
    //   102: aload_0
    //   103: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   106: pop
    //   107: aload_1
    //   108: getstatic 444	java/io/File:separator	Ljava/lang/String;
    //   111: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   114: pop
    //   115: aload_1
    //   116: ldc_w 446
    //   119: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   122: pop
    //   123: aload_1
    //   124: getstatic 444	java/io/File:separator	Ljava/lang/String;
    //   127: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   130: pop
    //   131: aload_1
    //   132: ldc_w 448
    //   135: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   138: pop
    //   139: new 441	java/io/File
    //   142: dup
    //   143: aload_1
    //   144: invokevirtual 158	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   147: invokespecial 449	java/io/File:<init>	(Ljava/lang/String;)V
    //   150: astore_2
    //   151: new 142	java/lang/StringBuilder
    //   154: dup
    //   155: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   158: astore_1
    //   159: aload_1
    //   160: aload_0
    //   161: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   164: pop
    //   165: aload_1
    //   166: getstatic 444	java/io/File:separator	Ljava/lang/String;
    //   169: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   172: pop
    //   173: aload_1
    //   174: ldc_w 451
    //   177: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   180: pop
    //   181: aload_1
    //   182: getstatic 444	java/io/File:separator	Ljava/lang/String;
    //   185: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   188: pop
    //   189: aload_1
    //   190: ldc_w 453
    //   193: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   196: pop
    //   197: new 441	java/io/File
    //   200: dup
    //   201: aload_1
    //   202: invokevirtual 158	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   205: invokespecial 449	java/io/File:<init>	(Ljava/lang/String;)V
    //   208: astore 6
    //   210: new 142	java/lang/StringBuilder
    //   213: dup
    //   214: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   217: astore_1
    //   218: aload_1
    //   219: aload_0
    //   220: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   223: pop
    //   224: aload_1
    //   225: getstatic 444	java/io/File:separator	Ljava/lang/String;
    //   228: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   231: pop
    //   232: aload_1
    //   233: ldc_w 455
    //   236: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   239: pop
    //   240: aload_1
    //   241: getstatic 444	java/io/File:separator	Ljava/lang/String;
    //   244: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   247: pop
    //   248: aload_1
    //   249: ldc_w 457
    //   252: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   255: pop
    //   256: new 441	java/io/File
    //   259: dup
    //   260: aload_1
    //   261: invokevirtual 158	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   264: invokespecial 449	java/io/File:<init>	(Ljava/lang/String;)V
    //   267: astore 5
    //   269: aconst_null
    //   270: astore_3
    //   271: aconst_null
    //   272: astore 4
    //   274: aload 4
    //   276: astore_1
    //   277: new 142	java/lang/StringBuilder
    //   280: dup
    //   281: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   284: astore 7
    //   286: aload 4
    //   288: astore_1
    //   289: aload 7
    //   291: ldc_w 459
    //   294: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   297: pop
    //   298: aload 4
    //   300: astore_1
    //   301: aload 7
    //   303: aload_2
    //   304: invokevirtual 462	java/io/File:exists	()Z
    //   307: invokevirtual 465	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   310: pop
    //   311: aload 4
    //   313: astore_1
    //   314: aload 7
    //   316: ldc_w 467
    //   319: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   322: pop
    //   323: aload 4
    //   325: astore_1
    //   326: aload 7
    //   328: aload 6
    //   330: invokevirtual 462	java/io/File:exists	()Z
    //   333: invokevirtual 465	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   336: pop
    //   337: aload 4
    //   339: astore_1
    //   340: aload 7
    //   342: ldc_w 467
    //   345: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   348: pop
    //   349: aload 4
    //   351: astore_1
    //   352: aload 7
    //   354: aload 5
    //   356: invokevirtual 462	java/io/File:exists	()Z
    //   359: invokevirtual 465	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   362: pop
    //   363: aload 4
    //   365: astore_1
    //   366: aload 7
    //   368: ldc_w 469
    //   371: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   374: pop
    //   375: aload 4
    //   377: astore_1
    //   378: aload 7
    //   380: aload_0
    //   381: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   384: pop
    //   385: aload 4
    //   387: astore_1
    //   388: ldc -102
    //   390: aload 7
    //   392: invokevirtual 158	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   395: invokestatic 161	com/tencent/smtt/webkit/ReadModeManager:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   398: aload 4
    //   400: astore_1
    //   401: new 471	java/io/FileInputStream
    //   404: dup
    //   405: aload_2
    //   406: invokespecial 474	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   409: astore_2
    //   410: aload_2
    //   411: astore_1
    //   412: aload_2
    //   413: astore_0
    //   414: aload_2
    //   415: ldc_w 476
    //   418: invokestatic 481	com/tencent/smtt/util/d:a	(Ljava/io/InputStream;Ljava/lang/String;)Ljava/lang/String;
    //   421: putstatic 435	com/tencent/smtt/webkit/ReadModeManager:b	Ljava/lang/String;
    //   424: aload_2
    //   425: astore_1
    //   426: aload_2
    //   427: astore_0
    //   428: aload_2
    //   429: invokevirtual 486	java/io/InputStream:close	()V
    //   432: aload 4
    //   434: astore_1
    //   435: new 471	java/io/FileInputStream
    //   438: dup
    //   439: aload 6
    //   441: invokespecial 474	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   444: astore_2
    //   445: aload_2
    //   446: astore_1
    //   447: aload_2
    //   448: astore_0
    //   449: aload_2
    //   450: ldc_w 476
    //   453: invokestatic 481	com/tencent/smtt/util/d:a	(Ljava/io/InputStream;Ljava/lang/String;)Ljava/lang/String;
    //   456: putstatic 436	com/tencent/smtt/webkit/ReadModeManager:c	Ljava/lang/String;
    //   459: aload_2
    //   460: astore_1
    //   461: aload_2
    //   462: astore_0
    //   463: aload_2
    //   464: invokevirtual 486	java/io/InputStream:close	()V
    //   467: aload 4
    //   469: astore_1
    //   470: new 471	java/io/FileInputStream
    //   473: dup
    //   474: aload 5
    //   476: invokespecial 474	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   479: astore_2
    //   480: aload_2
    //   481: astore_1
    //   482: aload_2
    //   483: astore_0
    //   484: aload_2
    //   485: ldc_w 476
    //   488: invokestatic 481	com/tencent/smtt/util/d:a	(Ljava/io/InputStream;Ljava/lang/String;)Ljava/lang/String;
    //   491: putstatic 437	com/tencent/smtt/webkit/ReadModeManager:d	Ljava/lang/String;
    //   494: aload_2
    //   495: astore_1
    //   496: aload_2
    //   497: astore_0
    //   498: aload_2
    //   499: invokevirtual 486	java/io/InputStream:close	()V
    //   502: iconst_0
    //   503: ifeq +124 -> 627
    //   506: new 488	java/lang/NullPointerException
    //   509: dup
    //   510: invokespecial 489	java/lang/NullPointerException:<init>	()V
    //   513: athrow
    //   514: astore_0
    //   515: new 142	java/lang/StringBuilder
    //   518: dup
    //   519: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   522: astore_1
    //   523: goto +78 -> 601
    //   526: astore_0
    //   527: goto +101 -> 628
    //   530: astore_2
    //   531: goto +10 -> 541
    //   534: astore_0
    //   535: goto +93 -> 628
    //   538: astore_2
    //   539: aload_3
    //   540: astore_0
    //   541: aload_0
    //   542: astore_1
    //   543: new 142	java/lang/StringBuilder
    //   546: dup
    //   547: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   550: astore_3
    //   551: aload_0
    //   552: astore_1
    //   553: aload_3
    //   554: ldc_w 491
    //   557: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   560: pop
    //   561: aload_0
    //   562: astore_1
    //   563: aload_3
    //   564: aload_2
    //   565: invokevirtual 494	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   568: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   571: pop
    //   572: aload_0
    //   573: astore_1
    //   574: ldc -102
    //   576: aload_3
    //   577: invokevirtual 158	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   580: invokestatic 161	com/tencent/smtt/webkit/ReadModeManager:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   583: aload_0
    //   584: ifnull +43 -> 627
    //   587: aload_0
    //   588: invokevirtual 486	java/io/InputStream:close	()V
    //   591: return
    //   592: astore_0
    //   593: new 142	java/lang/StringBuilder
    //   596: dup
    //   597: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   600: astore_1
    //   601: aload_1
    //   602: ldc_w 496
    //   605: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   608: pop
    //   609: aload_1
    //   610: aload_0
    //   611: invokevirtual 494	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   614: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   617: pop
    //   618: ldc -102
    //   620: aload_1
    //   621: invokevirtual 158	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   624: invokestatic 161	com/tencent/smtt/webkit/ReadModeManager:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   627: return
    //   628: aload_1
    //   629: ifnull +45 -> 674
    //   632: aload_1
    //   633: invokevirtual 486	java/io/InputStream:close	()V
    //   636: goto +38 -> 674
    //   639: astore_1
    //   640: new 142	java/lang/StringBuilder
    //   643: dup
    //   644: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   647: astore_2
    //   648: aload_2
    //   649: ldc_w 496
    //   652: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   655: pop
    //   656: aload_2
    //   657: aload_1
    //   658: invokevirtual 494	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   661: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   664: pop
    //   665: ldc -102
    //   667: aload_2
    //   668: invokevirtual 158	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   671: invokestatic 161	com/tencent/smtt/webkit/ReadModeManager:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   674: aload_0
    //   675: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	676	0	paramString	String
    //   68	565	1	localObject1	Object
    //   639	19	1	localException1	Exception
    //   150	349	2	localObject2	Object
    //   530	1	2	localException2	Exception
    //   538	27	2	localException3	Exception
    //   647	21	2	localStringBuilder1	StringBuilder
    //   270	307	3	localStringBuilder2	StringBuilder
    //   272	196	4	localObject3	Object
    //   267	208	5	localFile1	java.io.File
    //   208	232	6	localFile2	java.io.File
    //   284	107	7	localStringBuilder3	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   506	514	514	java/lang/Exception
    //   414	424	526	finally
    //   428	432	526	finally
    //   449	459	526	finally
    //   463	467	526	finally
    //   484	494	526	finally
    //   498	502	526	finally
    //   414	424	530	java/lang/Exception
    //   428	432	530	java/lang/Exception
    //   449	459	530	java/lang/Exception
    //   463	467	530	java/lang/Exception
    //   484	494	530	java/lang/Exception
    //   498	502	530	java/lang/Exception
    //   277	286	534	finally
    //   289	298	534	finally
    //   301	311	534	finally
    //   314	323	534	finally
    //   326	337	534	finally
    //   340	349	534	finally
    //   352	363	534	finally
    //   366	375	534	finally
    //   378	385	534	finally
    //   388	398	534	finally
    //   401	410	534	finally
    //   435	445	534	finally
    //   470	480	534	finally
    //   543	551	534	finally
    //   553	561	534	finally
    //   563	572	534	finally
    //   574	583	534	finally
    //   277	286	538	java/lang/Exception
    //   289	298	538	java/lang/Exception
    //   301	311	538	java/lang/Exception
    //   314	323	538	java/lang/Exception
    //   326	337	538	java/lang/Exception
    //   340	349	538	java/lang/Exception
    //   352	363	538	java/lang/Exception
    //   366	375	538	java/lang/Exception
    //   378	385	538	java/lang/Exception
    //   388	398	538	java/lang/Exception
    //   401	410	538	java/lang/Exception
    //   435	445	538	java/lang/Exception
    //   470	480	538	java/lang/Exception
    //   587	591	592	java/lang/Exception
    //   632	636	639	java/lang/Exception
  }
  
  private void a(String paramString, int paramInt1, int paramInt2, int paramInt3, float paramFloat)
  {
    if (d()) {
      RenderSmttService.a(paramString, paramInt1, paramInt2, paramInt3, paramFloat);
    }
  }
  
  private void a(String paramString, boolean paramBoolean, long paramLong)
  {
    if (d()) {
      RenderSmttService.a(paramString, paramBoolean, paramLong);
    }
  }
  
  public static boolean a()
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  private int b()
  {
    if (jdField_f_of_type_Int == -1) {
      jdField_f_of_type_Int = RenderSmttService.getReportConfigI("MTT_CORE_WEBPAGE_READ_MODE_PRECHECK", "check_delay", 1000);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("    getPreCheckDelayMs=");
    localStringBuilder.append(jdField_f_of_type_Int);
    b("ReadModeManager", localStringBuilder.toString());
    return jdField_f_of_type_Int;
  }
  
  private String b(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return "";
    }
    return b(b(b(paramString, "readmode_catalog_page=1"), "mtt_in_readmode=1"), "readmode_catalog_page_url=1");
  }
  
  public static String b(String paramString1, String paramString2)
  {
    if (!TextUtils.isEmpty(paramString1))
    {
      if (TextUtils.isEmpty(paramString2)) {
        return paramString1;
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("?");
      ((StringBuilder)localObject).append(paramString2);
      ((StringBuilder)localObject).append("&");
      if (paramString1.contains(((StringBuilder)localObject).toString()))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramString2);
        ((StringBuilder)localObject).append("&");
        return paramString1.replace(((StringBuilder)localObject).toString(), "");
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("?");
      ((StringBuilder)localObject).append(paramString2);
      if (paramString1.contains(((StringBuilder)localObject).toString()))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("?");
        ((StringBuilder)localObject).append(paramString2);
        return paramString1.replace(((StringBuilder)localObject).toString(), "");
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("&");
      localStringBuilder.append(paramString2);
      localObject = paramString1;
      if (paramString1.contains(localStringBuilder.toString()))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("&");
        ((StringBuilder)localObject).append(paramString2);
        localObject = paramString1.replace(((StringBuilder)localObject).toString(), "");
      }
      return (String)localObject;
    }
    return paramString1;
  }
  
  private void b(final String paramString, final e parame)
  {
    Object localObject = this.jdField_a_of_type_JavaLangRefWeakReference;
    if ((localObject != null) && (((WeakReference)localObject).get() != null))
    {
      if (((TencentWebViewProxy)this.jdField_a_of_type_JavaLangRefWeakReference.get()).getRealWebView() == null) {
        return;
      }
      if (this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$d != null) {
        return;
      }
      if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy == null)
      {
        this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = new X5WebViewAdapter(((TencentWebViewProxy)this.jdField_a_of_type_JavaLangRefWeakReference.get()).getRealWebView().getContext(), false);
        localObject = (TencentContentSettingsAdapter)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getSettings();
        ((TencentContentSettingsAdapter)localObject).setUserAgentString(((TencentWebViewProxy)this.jdField_a_of_type_JavaLangRefWeakReference.get()).getRealWebView().getSettings().getUserAgentString());
        ((TencentContentSettingsAdapter)localObject).setJavaScriptEnabled(true);
        localObject = new AbsoluteLayout.LayoutParams(0, 0, 0, 0);
        ((TencentWebViewProxy)this.jdField_a_of_type_JavaLangRefWeakReference.get()).getRealWebView().addView(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView(), (ViewGroup.LayoutParams)localObject);
        ((TencentWebViewChromium)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getWebViewProvider()).mAwContents.onSizeChanged(((TencentWebViewProxy)this.jdField_a_of_type_JavaLangRefWeakReference.get()).getRealWebView().getWidth(), ((TencentWebViewProxy)this.jdField_a_of_type_JavaLangRefWeakReference.get()).getRealWebView().getHeight(), 0, 0);
      }
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().setWebViewClient(new TencentWebViewClient()
      {
        public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
        {
          ReadModeManager.d(ReadModeManager.this);
          if (ReadModeManager.e(ReadModeManager.this) < 0) {
            ReadModeManager.a(ReadModeManager.this, 0);
          } else if (ReadModeManager.e(ReadModeManager.this) > 0) {
            return;
          }
          paramAnonymousWebView = ReadModeManager.this;
          paramAnonymousWebView.a(parame, ReadModeManager.a(paramAnonymousWebView), new ValueCallback()
          {
            public void a(Boolean paramAnonymous2Boolean)
            {
              if ((paramAnonymous2Boolean != null) && (paramAnonymous2Boolean.booleanValue() == true))
              {
                if ((ReadModeManager.4.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$e != ReadModeManager.e.c) && (ReadModeManager.4.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$e != ReadModeManager.e.b))
                {
                  ReadModeManager.4.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager.a(ReadModeManager.a(ReadModeManager.4.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager), (TencentWebViewProxy)ReadModeManager.a(ReadModeManager.4.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager).get(), null, null);
                  return;
                }
                if (ReadModeManager.4.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$e == ReadModeManager.e.c) {
                  ((TencentWebViewProxy)ReadModeManager.a(ReadModeManager.4.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager).get()).onReadModePreReadFinished();
                }
              }
              else if ((ReadModeManager.4.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$e != ReadModeManager.e.c) && (ReadModeManager.4.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$e != ReadModeManager.e.b))
              {
                ((TencentWebViewProxy)ReadModeManager.a(ReadModeManager.4.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager).get()).loadUrl(ReadModeManager.e(ReadModeManager.4.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager, ReadModeManager.4.this.jdField_a_of_type_JavaLangString));
              }
            }
          });
        }
      });
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.stopLoading();
      this.jdField_h_of_type_Int += 1;
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.loadUrl(paramString);
      return;
    }
  }
  
  private static void b(String paramString1, String paramString2) {}
  
  private boolean b(String paramString)
  {
    return RenderSmttService.a(paramString);
  }
  
  private int c()
  {
    if (jdField_g_of_type_Int == -1) {
      jdField_g_of_type_Int = RenderSmttService.getReportConfigI("MTT_CORE_WEBPAGE_READ_MODE_PRECHECK", "pre_min_content", 400);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("    getPreCheckMinContentTextCount=");
    localStringBuilder.append(jdField_g_of_type_Int);
    b("ReadModeManager", localStringBuilder.toString());
    return jdField_g_of_type_Int;
  }
  
  private static String c(String paramString)
  {
    String str2 = g();
    String str1 = str2;
    if (!TextUtils.isEmpty(str2)) {
      str1 = str2.replace("$$SITETYPE", paramString).replace("{\"1\":true}", "{}").replace("$$STRINGIFY_OUTPUT", "false");
    }
    return str1;
  }
  
  private void c(String paramString1, String paramString2)
  {
    this.k = paramString2;
    this.jdField_a_of_type_Boolean = true;
    try
    {
      this.j = new URL(paramString1).getHost();
    }
    catch (Exception paramString1)
    {
      this.j = "";
      paramString2 = new StringBuilder();
      paramString2.append("updateCurrentInfo Exception=");
      paramString2.append(paramString1.toString());
      b("ReadModeManager", paramString2.toString());
    }
    paramString1 = new StringBuilder();
    paramString1.append("updateCurrentInfo shouldPreDistill=");
    paramString1.append(this.jdField_a_of_type_Boolean);
    paramString1.append(", currentHost=");
    paramString1.append(this.j);
    paramString1.append(", currentPageNextUrl=");
    paramString1.append(this.k);
    b("ReadModeManager", paramString1.toString());
  }
  
  private static boolean c()
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  private boolean c(String paramString)
  {
    return RenderSmttService.b(paramString);
  }
  
  private boolean d()
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  private boolean d(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() != 0))
    {
      localObject = paramString;
      if (paramString.length() > 200) {
        localObject = paramString.substring(0, 200);
      }
      int i1 = ((String)localObject).indexOf("?");
      paramString = (String)localObject;
      if (i1 != -1) {
        paramString = ((String)localObject).substring(0, i1);
      }
      if ((paramString != null) && (paramString.trim().length() != 0))
      {
        if (c(paramString))
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("isAllowedUrl FALSE_BLACK_LIST url=");
          ((StringBuilder)localObject).append(paramString);
          b("ReadModeManager", ((StringBuilder)localObject).toString());
          return false;
        }
        if (b(paramString))
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("isAllowedUrl TRUE_WHITE_LIST url=");
          ((StringBuilder)localObject).append(paramString);
          b("ReadModeManager", ((StringBuilder)localObject).toString());
          return true;
        }
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("isAllowedUrl FALSE_DEFAULT url=");
        ((StringBuilder)localObject).append(paramString);
        b("ReadModeManager", ((StringBuilder)localObject).toString());
        return false;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("isAllowedUrl FALSE_INVALID_URL_2 url=");
      ((StringBuilder)localObject).append(paramString);
      b("ReadModeManager", ((StringBuilder)localObject).toString());
      return false;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("isAllowedUrl FALSE_INVALID_URL_1 url=");
    ((StringBuilder)localObject).append(paramString);
    b("ReadModeManager", ((StringBuilder)localObject).toString());
    return false;
  }
  
  private boolean e()
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  private boolean e(String paramString)
  {
    return (paramString != null) && (paramString.equals(this.m)) && (this.o != null);
  }
  
  private String f()
  {
    if (jdField_a_of_type_JavaLangString == null) {
      jdField_a_of_type_JavaLangString = RenderSmttService.getReportConfigS("MTT_CORE_WEBPAGE_READ_MODE_OPERATION", "jsurl", "https://nearby.3g.qq.com/api/feedsJs?from=novel&ps=2");
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("    getReadModeBottomOperationJsUrl=");
    localStringBuilder.append(jdField_a_of_type_JavaLangString);
    b("ReadModeManager", localStringBuilder.toString());
    return jdField_a_of_type_JavaLangString;
  }
  
  private boolean f()
  {
    return (!TextUtils.isEmpty(h())) && (!TextUtils.isEmpty(i())) && (!TextUtils.isEmpty(g()));
  }
  
  private static String g()
  {
    return b;
  }
  
  private static String h()
  {
    return c;
  }
  
  private static String i()
  {
    return d;
  }
  
  public void a()
  {
    a(this.k, e.a);
  }
  
  public void a(final e parame, final TencentWebViewProxy paramTencentWebViewProxy, ValueCallback<Boolean> paramValueCallback)
  {
    String str;
    if ((paramTencentWebViewProxy != null) && (paramTencentWebViewProxy.getRealWebView() != null)) {
      str = paramTencentWebViewProxy.getUrl();
    } else {
      str = "webview_null";
    }
    final Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("canEnterReadMode modeType=");
    ((StringBuilder)localObject1).append(parame.toString());
    ((StringBuilder)localObject1).append(", url=");
    ((StringBuilder)localObject1).append(str);
    b("ReadModeManager", ((StringBuilder)localObject1).toString());
    if (paramValueCallback == null)
    {
      if ((parame != e.b) && (parame != e.d)) {
        a(str, 1, 0, 0, 0.0F);
      }
      return;
    }
    boolean bool = c();
    int i1 = 0;
    if (!bool)
    {
      paramValueCallback.onReceiveValue(Boolean.valueOf(false));
      if ((parame != e.b) && (parame != e.d)) {
        a(str, 2, 0, 0, 0.0F);
      }
      return;
    }
    if ((paramTencentWebViewProxy != null) && (paramTencentWebViewProxy.getRealWebView() != null))
    {
      if ((parame == e.b) && (!d(str)))
      {
        paramValueCallback.onReceiveValue(Boolean.valueOf(false));
        return;
      }
      if (e(str))
      {
        b("ReadModeManager", "    canEnterReadMode Check_PreCheck TRUE");
        paramValueCallback.onReceiveValue(Boolean.valueOf(true));
        return;
      }
      if (!f())
      {
        paramValueCallback.onReceiveValue(Boolean.valueOf(false));
        if ((parame != e.b) && (parame != e.d)) {
          a(str, 3, 0, 0, 0.0F);
        }
        return;
      }
      Object localObject2 = jdField_f_of_type_JavaLangString;
      localObject1 = localObject2;
      if (str != null)
      {
        localObject1 = localObject2;
        if (str.contains("readmode_catalog_page=1")) {
          localObject1 = jdField_h_of_type_JavaLangString;
        }
      }
      if ((str != null) && (str.contains("readmode_catalog_page_url=1"))) {
        localObject1 = i;
      }
      str = c((String)localObject1);
      if (i.equals(localObject1))
      {
        localObject2 = this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$b;
        if ((localObject2 != null) && (((b)localObject2).c != null))
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("{\\\"url\\\":\\\"\\\",\\\"pre_url\\\":\\\"\\\",\\\"next_url\\\":\\\"\\\",\\\"path\\\":\\\"");
          ((StringBuilder)localObject2).append(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$b.c);
          ((StringBuilder)localObject2).append("\\\"}");
          str = str.replace("$$EXTRADATA", ((StringBuilder)localObject2).toString());
        }
      }
      paramValueCallback = new ValueCallback()
      {
        public void a(String paramAnonymousString)
        {
          if (paramAnonymousString != null) {}
          for (;;)
          {
            int j;
            try
            {
              ReadModeManager.c localc = new ReadModeManager.c(ReadModeManager.this, paramAnonymousString);
              paramAnonymousString = localc.i;
              j = 1;
              if ((paramAnonymousString != null) && (!ReadModeManager.b().equals(localc.i)))
              {
                if (ReadModeManager.d().equals(localc.i))
                {
                  if (localc.b != null)
                  {
                    paramAnonymousString = new StringBuilder();
                    paramAnonymousString.append("    canEnterReadMode IMG siteType=");
                    paramAnonymousString.append(localc.i);
                    paramAnonymousString.append(" Check_Normal TRUE");
                    ReadModeManager.a("ReadModeManager", paramAnonymousString.toString());
                    paramAnonymousString = localc.f.substring(0, localc.f.lastIndexOf("/") + 1);
                    if ((ReadModeManager.a(ReadModeManager.this) != null) && (ReadModeManager.a(ReadModeManager.this).c != null) && (ReadModeManager.a(ReadModeManager.this).c.contains(paramAnonymousString.substring(paramAnonymousString.indexOf("/"))))) {
                      break label1271;
                    }
                    if ((ReadModeManager.a(ReadModeManager.this) != null) && (localObject1.contains(paramAnonymousString.substring(paramAnonymousString.indexOf("/")))))
                    {
                      ReadModeManager.a(ReadModeManager.this).a();
                      ReadModeManager.a(ReadModeManager.this, new ReadModeManager.a(ReadModeManager.this, localc, paramAnonymousString));
                      break label1271;
                    }
                    if (ReadModeManager.a(ReadModeManager.this) != null) {
                      break label1271;
                    }
                    ReadModeManager.a(ReadModeManager.this, new ReadModeManager.a(ReadModeManager.this, localc, paramAnonymousString));
                    break label1271;
                  }
                  paramAnonymousString = new StringBuilder();
                  paramAnonymousString.append("    canEnterReadMode IMG siteType=");
                  paramAnonymousString.append(localc.i);
                  paramAnonymousString.append(" Check_Normal FALSE_CONTENT_SHORT");
                  ReadModeManager.a("ReadModeManager", paramAnonymousString.toString());
                  if ((parame == ReadModeManager.e.b) || (parame == ReadModeManager.e.d)) {
                    break label1279;
                  }
                  ReadModeManager.a(ReadModeManager.this, paramTencentWebViewProxy.getUrl(), 4, 0, 0, 0.0F);
                  break label1279;
                }
                if (!ReadModeManager.e().equals(localc.i)) {
                  if (!ReadModeManager.a().equals(localc.i)) {
                    break label1287;
                  }
                }
                if (localc.b != null)
                {
                  paramAnonymousString = new StringBuilder();
                  paramAnonymousString.append("    canEnterReadMode CATALOG siteType=");
                  paramAnonymousString.append(localc.i);
                  paramAnonymousString.append(" Check_Normal TRUE");
                  ReadModeManager.a("ReadModeManager", paramAnonymousString.toString());
                  bool = true;
                  i = j;
                }
                else
                {
                  paramAnonymousString = new StringBuilder();
                  paramAnonymousString.append("    canEnterReadMode CATALOG siteType=");
                  paramAnonymousString.append(localc.i);
                  paramAnonymousString.append(" Check_Normal FALSE_CONTENT_SHORT");
                  ReadModeManager.a("ReadModeManager", paramAnonymousString.toString());
                  if ((parame == ReadModeManager.e.b) || (parame == ReadModeManager.e.d)) {
                    break label1279;
                  }
                  ReadModeManager.a(ReadModeManager.this, paramTencentWebViewProxy.getUrl(), 4, 0, 0, 0.0F);
                  break label1279;
                }
              }
              else
              {
                paramAnonymousString = null;
                if (localc.b != null) {
                  paramAnonymousString = Pattern.compile(ReadModeManager.c(), 2).matcher(localc.b).replaceAll("");
                }
                if (paramAnonymousString == null) {
                  break label1295;
                }
                i = paramAnonymousString.replaceAll("\\s*", "").length();
                if ((localc.b != null) && (i > ReadModeManager.b(ReadModeManager.this)))
                {
                  if ((parame == ReadModeManager.e.b) && (i < ReadModeManager.c(ReadModeManager.this)))
                  {
                    paramAnonymousString = new StringBuilder();
                    paramAnonymousString.append("    canEnterReadMode TEXT siteType=");
                    paramAnonymousString.append(localc.i);
                    paramAnonymousString.append(" Check_Normal FALSE_FOR_PRECHECK noneHtmlContentTextCount=");
                    paramAnonymousString.append(i);
                    ReadModeManager.a("ReadModeManager", paramAnonymousString.toString());
                    bool = false;
                    i = j;
                  }
                  else if ((parame == ReadModeManager.e.d) && (i < ReadModeManager.c(ReadModeManager.this)))
                  {
                    paramAnonymousString = new StringBuilder();
                    paramAnonymousString.append("    canEnterReadMode TEXT siteType=");
                    paramAnonymousString.append(localc.i);
                    paramAnonymousString.append(" Check_Normal FALSE_FOR_REENTER noneHtmlContentTextCount=");
                    paramAnonymousString.append(i);
                    ReadModeManager.a("ReadModeManager", paramAnonymousString.toString());
                    bool = false;
                    i = j;
                  }
                  else
                  {
                    paramAnonymousString = new StringBuilder();
                    paramAnonymousString.append("    canEnterReadMode TEXT siteType=");
                    paramAnonymousString.append(localc.i);
                    paramAnonymousString.append(" Check_Normal TRUE noneHtmlContentTextCount=");
                    paramAnonymousString.append(i);
                    ReadModeManager.a("ReadModeManager", paramAnonymousString.toString());
                    bool = true;
                    i = j;
                  }
                }
                else
                {
                  paramAnonymousString = new StringBuilder();
                  paramAnonymousString.append("    canEnterReadMode TEXT siteType=");
                  paramAnonymousString.append(localc.i);
                  paramAnonymousString.append(" Check_Normal FALSE_CONTENT_SHORT noneHtmlContentTextCount=");
                  paramAnonymousString.append(i);
                  ReadModeManager.a("ReadModeManager", paramAnonymousString.toString());
                  if ((parame == ReadModeManager.e.b) || (parame == ReadModeManager.e.d)) {
                    break label1300;
                  }
                  ReadModeManager.a(ReadModeManager.this, paramTencentWebViewProxy.getUrl(), 4, ReadModeManager.b(ReadModeManager.this), i, 0.0F);
                  break label1300;
                }
              }
              if (i != 0)
              {
                paramAnonymousString = new StringBuilder();
                paramAnonymousString.append("    canEnterReadMode siteType=");
                paramAnonymousString.append(localc.i);
                paramAnonymousString.append(" generateReadModeHtml");
                ReadModeManager.a("ReadModeManager", paramAnonymousString.toString());
                paramAnonymousString = ReadModeManager.a(ReadModeManager.this, localc, localObject1);
                ReadModeManager.a(ReadModeManager.this, localc.e);
                long l = System.currentTimeMillis();
                ReadModeManager.b(ReadModeManager.this, localc.f);
                ReadModeManager.c(ReadModeManager.this, localc.e);
                ReadModeManager.d(ReadModeManager.this, paramAnonymousString);
                ReadModeManager.a(ReadModeManager.this, l - this.jdField_a_of_type_Long);
                if (parame == ReadModeManager.e.b) {
                  ReadModeManager.a(ReadModeManager.this, ReadModeManager.a(ReadModeManager.this) - ReadModeManager.a(ReadModeManager.this));
                }
                paramAnonymousString = new StringBuilder();
                paramAnonymousString.append("    canEnterReadMode siteType=");
                paramAnonymousString.append(localc.i);
                paramAnonymousString.append(" preDistillTimeMs=");
                paramAnonymousString.append(ReadModeManager.a(ReadModeManager.this));
                paramAnonymousString.append(", preDistillPageUrl=");
                paramAnonymousString.append(ReadModeManager.a(ReadModeManager.this));
                ReadModeManager.a("ReadModeManager", paramAnonymousString.toString());
              }
              this.jdField_a_of_type_AndroidWebkitValueCallback.onReceiveValue(Boolean.valueOf(bool));
              return;
            }
            catch (Exception paramAnonymousString)
            {
              paramAnonymousString.printStackTrace();
              this.jdField_a_of_type_AndroidWebkitValueCallback.onReceiveValue(Boolean.valueOf(false));
              if (parame == ReadModeManager.e.b) {
                break label1270;
              }
            }
            if (parame != ReadModeManager.e.d)
            {
              ReadModeManager.a(ReadModeManager.this, paramTencentWebViewProxy.getUrl(), 9, 0, 0, 0.0F);
              return;
              this.jdField_a_of_type_AndroidWebkitValueCallback.onReceiveValue(Boolean.valueOf(false));
              if ((parame != ReadModeManager.e.b) && (parame != ReadModeManager.e.d)) {
                ReadModeManager.a(ReadModeManager.this, paramTencentWebViewProxy.getUrl(), 12, 0, 0, 0.0F);
              }
            }
            label1270:
            return;
            label1271:
            boolean bool = true;
            int i = j;
            continue;
            label1279:
            bool = false;
            i = 0;
            continue;
            label1287:
            bool = false;
            i = 0;
            continue;
            label1295:
            i = 0;
            continue;
            label1300:
            bool = false;
            i = 0;
          }
        }
      };
      if (parame == e.b) {
        i1 = b();
      }
      a(paramTencentWebViewProxy, str, paramValueCallback, i1);
      return;
    }
    paramValueCallback.onReceiveValue(Boolean.valueOf(false));
    if ((parame != e.b) && (parame != e.d)) {
      a(str, 7, 0, 0, 0.0F);
    }
  }
  
  public void a(TencentWebViewProxy paramTencentWebViewProxy)
  {
    paramTencentWebViewProxy = new StringBuilder();
    paramTencentWebViewProxy.append("onPageFinished preDistillNextUrl=");
    paramTencentWebViewProxy.append(this.l);
    b("ReadModeManager", paramTencentWebViewProxy.toString());
    if (this.jdField_a_of_type_Boolean == true)
    {
      paramTencentWebViewProxy = this.l;
      if ((paramTencentWebViewProxy != null) && (paramTencentWebViewProxy.startsWith("http")))
      {
        this.jdField_a_of_type_Boolean = false;
        b(this.l, e.c);
        this.l = null;
      }
    }
  }
  
  public void a(TencentWebViewProxy paramTencentWebViewProxy1, final TencentWebViewProxy paramTencentWebViewProxy2, final ValueCallback<Boolean> paramValueCallback, Runnable paramRunnable)
  {
    this.jdField_a_of_type_AndroidWebkitValueCallback = paramValueCallback;
    this.jdField_a_of_type_JavaLangRunnable = paramRunnable;
    b("ReadModeManager", "enterReadMode");
    if ((paramTencentWebViewProxy2 != null) && (paramTencentWebViewProxy2.getRealWebView() != null) && (paramTencentWebViewProxy1 != null) && (paramTencentWebViewProxy1.getRealWebView() != null))
    {
      if (!f())
      {
        paramTencentWebViewProxy2 = this.jdField_a_of_type_AndroidWebkitValueCallback;
        if (paramTencentWebViewProxy2 != null) {
          paramTencentWebViewProxy2.onReceiveValue(Boolean.valueOf(false));
        }
        a(paramTencentWebViewProxy1.getUrl(), 6, 0, 0, 0.0F);
        return;
      }
      paramRunnable = jdField_f_of_type_JavaLangString;
      paramValueCallback = paramRunnable;
      if (paramTencentWebViewProxy1.getUrl() != null)
      {
        paramValueCallback = paramRunnable;
        if (paramTencentWebViewProxy1.getUrl().contains("readmode_catalog_page=1")) {
          paramValueCallback = jdField_h_of_type_JavaLangString;
        }
      }
      if ((paramTencentWebViewProxy1.getUrl() != null) && (paramTencentWebViewProxy1.getUrl().contains("readmode_catalog_page_url=1"))) {
        paramValueCallback = i;
      }
      String str = c(paramValueCallback);
      paramRunnable = str;
      if (i.equals(paramValueCallback))
      {
        b localb = this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$b;
        paramRunnable = str;
        if (localb != null)
        {
          paramRunnable = str;
          if (localb.c != null)
          {
            paramRunnable = new StringBuilder();
            paramRunnable.append("{\\\"url\\\":\\\"\\\",\\\"pre_url\\\":\\\"\\\",\\\"next_url\\\":\\\"\\\",\\\"path\\\":\\\"");
            paramRunnable.append(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$b.c);
            paramRunnable.append("\\\"}");
            paramRunnable = str.replace("$$EXTRADATA", paramRunnable.toString());
          }
        }
      }
      if (e(paramTencentWebViewProxy1.getUrl()))
      {
        paramValueCallback = a(b(paramTencentWebViewProxy1.getUrl()), "mtt_in_readmode=1");
        paramTencentWebViewProxy2.getRealWebView().loadDataWithBaseURL(paramValueCallback, this.o, "text/html", "UTF-8", paramValueCallback);
        c(paramValueCallback, this.n);
        paramTencentWebViewProxy2 = new StringBuilder();
        paramTencentWebViewProxy2.append("    enterReadMode Load_PreCheck Success preDistillTimeMs=");
        paramTencentWebViewProxy2.append(this.jdField_a_of_type_Long);
        b("ReadModeManager", paramTencentWebViewProxy2.toString());
        a(paramTencentWebViewProxy1.getUrl(), true, this.jdField_a_of_type_Long);
        return;
      }
      final long l1 = System.currentTimeMillis();
      b("ReadModeManager", "    enterReadMode Start_Distill");
      a(paramTencentWebViewProxy1, paramRunnable, new ValueCallback()
      {
        public void a(String paramAnonymousString)
        {
          if (paramAnonymousString != null) {
            try
            {
              ReadModeManager.c localc = new ReadModeManager.c(ReadModeManager.this, paramAnonymousString);
              ReadModeManager.a("ReadModeManager", "    enterReadMode generateReadModeHtml");
              String str = ReadModeManager.a(ReadModeManager.this, localc, paramValueCallback);
              ReadModeManager.a(ReadModeManager.this, localc.e);
              long l = System.currentTimeMillis();
              paramAnonymousString = null;
              if (localc.f != null) {
                paramAnonymousString = ReadModeManager.a(ReadModeManager.e(ReadModeManager.this, localc.f), "mtt_in_readmode=1");
              }
              paramTencentWebViewProxy2.getRealWebView().loadDataWithBaseURL(paramAnonymousString, str, "text/html", "UTF-8", paramAnonymousString);
              ReadModeManager.a(ReadModeManager.this, paramAnonymousString, localc.e);
              paramAnonymousString = new StringBuilder();
              paramAnonymousString.append("    enterReadMode Load_Normal Success distillTimeMs=");
              paramAnonymousString.append(l - l1);
              ReadModeManager.a("ReadModeManager", paramAnonymousString.toString());
              ReadModeManager.a(ReadModeManager.this, localc.f, false, l - l1);
              if (ReadModeManager.a(ReadModeManager.this) == null) {
                return;
              }
              ReadModeManager.a(ReadModeManager.this).onReceiveValue(Boolean.valueOf(true));
              return;
            }
            catch (Exception paramAnonymousString)
            {
              paramAnonymousString.printStackTrace();
              if (ReadModeManager.a(ReadModeManager.this) != null) {
                ReadModeManager.a(ReadModeManager.this).onReceiveValue(Boolean.valueOf(false));
              }
              ReadModeManager.a(ReadModeManager.this, this.b.getUrl(), 11, 0, 0, 0.0F);
              return;
            }
          }
          if (ReadModeManager.a(ReadModeManager.this) != null) {
            ReadModeManager.a(ReadModeManager.this).onReceiveValue(Boolean.valueOf(false));
          }
          ReadModeManager.a(ReadModeManager.this, this.b.getUrl(), 14, 0, 0, 0.0F);
        }
      });
      return;
    }
    paramTencentWebViewProxy2 = this.jdField_a_of_type_AndroidWebkitValueCallback;
    if (paramTencentWebViewProxy2 != null) {
      paramTencentWebViewProxy2.onReceiveValue(Boolean.valueOf(false));
    }
    a(paramTencentWebViewProxy1.getUrl(), 8, 0, 0, 0.0F);
  }
  
  public void a(String paramString, e parame)
  {
    WeakReference localWeakReference = this.jdField_a_of_type_JavaLangRefWeakReference;
    if ((localWeakReference != null) && (localWeakReference.get() != null) && (((TencentWebViewProxy)this.jdField_a_of_type_JavaLangRefWeakReference.get()).getRealWebView() != null))
    {
      if (paramString == null) {
        return;
      }
      if (e(paramString))
      {
        parame = a(b(paramString), "mtt_in_readmode=1");
        ((TencentWebViewProxy)this.jdField_a_of_type_JavaLangRefWeakReference.get()).getRealWebView().loadDataWithBaseURL(parame, this.o, "text/html", "UTF-8", parame);
        c(parame, this.n);
        parame = new StringBuilder();
        parame.append("navigateToReadModePage Load_PreDistill Success preDistillTimeMs=");
        parame.append(this.jdField_a_of_type_Long);
        b("ReadModeManager", parame.toString());
        a(paramString, true, this.jdField_a_of_type_Long);
        return;
      }
      if ((this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$b != null) && (b(paramString).equals(b(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$b.jdField_a_of_type_JavaLangString))) && (this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$b.b != null))
      {
        parame = new StringBuilder();
        parame.append(b(paramString));
        parame.append("mtt_in_readmode=1");
        parame = parame.toString();
        ((TencentWebViewProxy)this.jdField_a_of_type_JavaLangRefWeakReference.get()).getRealWebView().loadDataWithBaseURL(parame, this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$b.b, "text/html", "UTF-8", parame);
        b("ReadModeManager", "navigateToReadModePage Load_PreDistill_Catalog Success");
        a(paramString, true, this.jdField_a_of_type_Long);
        return;
      }
      b(paramString, parame);
      return;
    }
  }
  
  public boolean a(String paramString)
  {
    try
    {
      paramString = new URL(paramString).getHost();
      if ((this.j != null) && (this.j.equals(paramString)))
      {
        b("ReadModeManager", "matchReadModeCurrentHost TRUE");
        return true;
      }
      b("ReadModeManager", "matchReadModeCurrentHost FALSE");
      return false;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("matchReadModeCurrentHost FALSE Exception=");
      localStringBuilder.append(paramString.toString());
      b("ReadModeManager", localStringBuilder.toString());
    }
    return false;
  }
  
  public void b()
  {
    b("ReadModeManager", "exitReadMode");
    Object localObject = this.jdField_a_of_type_JavaLangRefWeakReference;
    if ((localObject != null) && (((WeakReference)localObject).get() != null) && (((TencentWebViewProxy)this.jdField_a_of_type_JavaLangRefWeakReference.get()).getRealWebView() != null))
    {
      localObject = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
      if ((localObject != null) && (((TencentWebViewProxy)localObject).getRealWebView() != null))
      {
        ((TencentWebViewProxy)this.jdField_a_of_type_JavaLangRefWeakReference.get()).getRealWebView().removeView(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView());
        this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.destroy();
      }
      localObject = ((TencentWebViewProxy)this.jdField_a_of_type_JavaLangRefWeakReference.get()).getUrl();
      if ((!((TencentWebViewProxy)this.jdField_a_of_type_JavaLangRefWeakReference.get()).canGoBackOrForward(-1)) && (localObject != null) && (((String)localObject).contains("mtt_in_readmode=1"))) {
        ((TencentWebViewProxy)this.jdField_a_of_type_JavaLangRefWeakReference.get()).loadUrl(b((String)localObject));
      }
    }
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = null;
    this.jdField_a_of_type_Long = 0L;
    this.jdField_h_of_type_Int = 0;
    this.jdField_a_of_type_AndroidWebkitValueCallback = null;
    this.jdField_a_of_type_JavaLangRunnable = null;
    this.j = null;
    this.k = null;
    localObject = this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$b;
    if ((localObject != null) && (((b)localObject).c == null)) {
      this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$b = null;
    }
  }
  
  public boolean b()
  {
    return e(this.k);
  }
  
  public static abstract interface ResultCallback
  {
    public abstract void run(ReadModeManager.d paramd);
  }
  
  private class a
  {
    private int jdField_a_of_type_Int;
    public final String a;
    public boolean a;
    public String[] a;
    public final String b;
    public final String c;
    
    public a(ReadModeManager.c paramc, String paramString)
    {
      this.jdField_a_of_type_ArrayOfJavaLangString = new String[5];
      int i = 0;
      this.jdField_a_of_type_Boolean = false;
      this.jdField_a_of_type_Int = 0;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("CatalogFromHistoryPageFinder: ");
      localStringBuilder.append(paramc.f);
      Log.e("penghu_test", localStringBuilder.toString());
      if ((ReadModeManager.a(ReadModeManager.this) != null) && (ReadModeManager.a(ReadModeManager.this).get() != null) && (((TencentWebViewProxy)ReadModeManager.a(ReadModeManager.this).get()).getRealWebView() != null) && (paramc.f.startsWith("http")) && (paramc.f.length() >= 8))
      {
        this.jdField_a_of_type_JavaLangString = paramString;
        this.b = paramc.f.substring(0, paramc.f.indexOf("/", 8) + 1);
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("{\\\"url\\\":\\\"");
        localStringBuilder.append(paramc.f);
        localStringBuilder.append("\\\",\\\"pre_url\\\":\\\"");
        if (paramc.c == null) {
          paramString = "";
        } else {
          paramString = paramc.c;
        }
        localStringBuilder.append(paramString);
        localStringBuilder.append("\\\",\\\"next_url\\\":\\\"");
        if (paramc.e == null) {
          paramc = "";
        } else {
          paramc = paramc.e;
        }
        localStringBuilder.append(paramc);
        localStringBuilder.append("\\\"}");
        this.c = localStringBuilder.toString();
        int m = ((TencentWebViewProxy)ReadModeManager.a(ReadModeManager.this).get()).getCurrentHistoryItemIndex();
        int j = 0;
        for (;;)
        {
          int k = i - 1;
          if ((i <= m * -1) || (j >= 5)) {
            break;
          }
          paramc = ((TencentWebViewProxy)ReadModeManager.a(ReadModeManager.this).get()).getHistoryItem(k);
          if (paramc != null)
          {
            paramc = paramc.getUrl();
            if ((paramc != null) && (paramc.contains(this.b)) && (!paramc.contains("mtt_in_readmode")))
            {
              this.jdField_a_of_type_ArrayOfJavaLangString[j] = paramc;
              j += 1;
              i = k;
              continue;
            }
          }
          i = k;
        }
        b();
        return;
      }
      this.jdField_a_of_type_JavaLangString = null;
      this.b = null;
      this.c = null;
    }
    
    private void b()
    {
      String str = this.jdField_a_of_type_ArrayOfJavaLangString[this.jdField_a_of_type_Int];
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.jdField_a_of_type_Int);
      localStringBuilder.append(" FindCatalog: ");
      localStringBuilder.append(str);
      Log.e("penghu_test", localStringBuilder.toString());
      if ((str != null) && (!str.isEmpty()))
      {
        if (this.jdField_a_of_type_Int >= 5) {
          return;
        }
        new ReadModeManager.d(ReadModeManager.this, str, ReadModeManager.e.c, this.c, "catalog_page_by_url", new ReadModeManager.ResultCallback()
        {
          public void run(ReadModeManager.d paramAnonymousd)
          {
            Object localObject = new StringBuilder();
            ((StringBuilder)localObject).append(paramAnonymousd.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$c.f);
            ((StringBuilder)localObject).append(" \n content:");
            ((StringBuilder)localObject).append(paramAnonymousd.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$c.b);
            Log.e("penghu_test", ((StringBuilder)localObject).toString());
            ReadModeManager.a.a(ReadModeManager.a.this);
            if ((paramAnonymousd.jdField_a_of_type_Boolean) && (paramAnonymousd.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$c.b != null) && (!paramAnonymousd.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$c.b.isEmpty()))
            {
              localObject = ReadModeManager.a(ReadModeManager.a.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager, paramAnonymousd.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$c, ReadModeManager.a());
              ReadModeManager.a(ReadModeManager.a.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager, new ReadModeManager.b(ReadModeManager.a.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager, paramAnonymousd.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$c.f, (String)localObject, ReadModeManager.a.this.jdField_a_of_type_JavaLangString));
              ReadModeManager.a(ReadModeManager.a.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager).loadUrl("about:blank");
              ReadModeManager.a(ReadModeManager.a.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager, null);
              return;
            }
            if (ReadModeManager.a.this.jdField_a_of_type_Boolean) {
              return;
            }
            ReadModeManager.a.a(ReadModeManager.a.this);
          }
        }).a();
        return;
      }
    }
    
    public void a()
    {
      this.jdField_a_of_type_Boolean = true;
      if (ReadModeManager.a(ReadModeManager.this) != null) {
        ReadModeManager.a(ReadModeManager.this).stopLoading();
      }
    }
  }
  
  public class b
  {
    public final String a;
    public final String b;
    public final String c;
    
    public b(String paramString1, String paramString2, String paramString3)
    {
      this.jdField_a_of_type_JavaLangString = paramString1;
      this.b = paramString2;
      this.c = paramString3;
    }
  }
  
  private class c
  {
    public String a;
    public String b = null;
    public String c = null;
    public String d = null;
    public String e = null;
    public String f = null;
    public String g = null;
    public String h = null;
    public String i = null;
    public String j = null;
    public String k = null;
    public String l = null;
    public String m = null;
    public String n = null;
    
    public c(String paramString)
      throws Exception
    {
      this.jdField_a_of_type_JavaLangString = null;
      this$1 = new JSONObject(paramString);
      this.jdField_a_of_type_JavaLangString = ReadModeManager.this.optString("chapterTitle", "阅读模式");
      this.b = ReadModeManager.this.optString("content", null);
      this.c = ReadModeManager.this.optString("preUrl", null);
      this.d = ReadModeManager.this.optString("catalogUrl", null);
      this.e = ReadModeManager.this.optString("nextUrl", null);
      this.f = ReadModeManager.this.optString("url", null);
      this.g = ReadModeManager.this.optString("prevTitle", null);
      this.h = ReadModeManager.this.optString("nextTitle", null);
      this.i = ReadModeManager.this.optString("siteType", null);
      this.j = ReadModeManager.this.optString("preCatalogUrl", null);
      this.k = ReadModeManager.this.optString("redirectUrl", null);
      this.l = ReadModeManager.this.optString("nextCatalogUrl", null);
      this.m = ReadModeManager.this.optString("catalogArr", null);
      this.n = ReadModeManager.this.optString("path", null);
    }
  }
  
  private class d
  {
    public final ReadModeManager.ResultCallback a;
    public ReadModeManager.c a;
    public final ReadModeManager.e a;
    public final String a;
    public boolean a;
    public final String b;
    public boolean b;
    public final String c;
    
    public d(String paramString1, ReadModeManager.e parame, String paramString2, String paramString3, ReadModeManager.ResultCallback paramResultCallback)
    {
      this.jdField_a_of_type_Boolean = true;
      this.jdField_b_of_type_Boolean = false;
      this.jdField_a_of_type_JavaLangString = paramString1;
      this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$e = parame;
      this.jdField_b_of_type_JavaLangString = paramString2;
      this.c = paramString3;
      this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$ResultCallback = paramResultCallback;
    }
    
    private void a(long paramLong)
    {
      ThreadUtils.postOnUiThreadDelayed(new Runnable()
      {
        public void run()
        {
          if (ReadModeManager.d.this.b) {
            return;
          }
          ReadModeManager.d.b(ReadModeManager.d.this);
        }
      }, paramLong);
    }
    
    private void b()
    {
      ThreadUtils.postOnUiThread(new Runnable()
      {
        public void run()
        {
          if (ReadModeManager.a(ReadModeManager.this) == null) {
            return;
          }
          ReadModeManager.d locald = (ReadModeManager.d)ReadModeManager.a(ReadModeManager.this).poll();
          if (locald != null) {
            locald.a();
          }
        }
      });
    }
    
    private void c()
    {
      ValueCallback local2 = new ValueCallback()
      {
        public void a(String paramAnonymousString)
        {
          ReadModeManager.d locald1 = ReadModeManager.a(ReadModeManager.d.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager);
          ReadModeManager.d locald2 = ReadModeManager.d.this;
          if (locald1 == locald2) {
            ReadModeManager.a(locald2.jdField_a_of_type_ComTencentSmttWebkitReadModeManager, null);
          }
          ReadModeManager.d.a(ReadModeManager.d.this);
          ReadModeManager.a(ReadModeManager.d.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager).getRealWebView().setWebViewClient(null);
          if (paramAnonymousString == null)
          {
            paramAnonymousString = ReadModeManager.d.this;
            paramAnonymousString.jdField_a_of_type_Boolean = false;
            if ((paramAnonymousString.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$e != ReadModeManager.e.b) && (ReadModeManager.d.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$e != ReadModeManager.e.d)) {
              ReadModeManager.a(ReadModeManager.d.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager, ReadModeManager.d.this.jdField_a_of_type_JavaLangString, 12, 0, 0, 0.0F);
            }
            return;
          }
          try
          {
            ReadModeManager.d.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$c = new ReadModeManager.c(ReadModeManager.d.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager, paramAnonymousString);
            if (ReadModeManager.d.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$ResultCallback != null)
            {
              ReadModeManager.d.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$ResultCallback.run(ReadModeManager.d.this);
              return;
            }
          }
          catch (Exception paramAnonymousString)
          {
            paramAnonymousString.printStackTrace();
            paramAnonymousString = ReadModeManager.d.this;
            paramAnonymousString.jdField_a_of_type_Boolean = false;
            if ((paramAnonymousString.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$e != ReadModeManager.e.b) && (ReadModeManager.d.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$e != ReadModeManager.e.d)) {
              ReadModeManager.a(ReadModeManager.d.this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager, ReadModeManager.d.this.jdField_a_of_type_JavaLangString, 9, 0, 0, 0.0F);
            }
          }
        }
      };
      int i;
      if (this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager$e == ReadModeManager.e.b) {
        i = ReadModeManager.a(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager);
      } else {
        i = 0;
      }
      String str = ReadModeManager.a(this.c).replace("$$EXTRADATA", this.jdField_b_of_type_JavaLangString);
      ReadModeManager localReadModeManager = this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager;
      ReadModeManager.a(localReadModeManager, ReadModeManager.a(localReadModeManager), str, local2, i);
      this.jdField_b_of_type_Boolean = true;
    }
    
    public void a()
    {
      if ((ReadModeManager.a(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager) != null) && (ReadModeManager.a(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager).get() != null) && (((TencentWebViewProxy)ReadModeManager.a(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager).get()).getRealWebView() != null))
      {
        if (this.jdField_a_of_type_JavaLangString == null) {
          return;
        }
        if (ReadModeManager.a(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager) == null)
        {
          Object localObject = this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager;
          ReadModeManager.a((ReadModeManager)localObject, new X5WebViewAdapter(((TencentWebViewProxy)ReadModeManager.a((ReadModeManager)localObject).get()).getRealWebView().getContext(), false));
          localObject = (TencentContentSettingsAdapter)ReadModeManager.a(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager).getRealWebView().getSettings();
          ((TencentContentSettingsAdapter)localObject).setUserAgentString(((TencentWebViewProxy)ReadModeManager.a(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager).get()).getRealWebView().getSettings().getUserAgentString());
          ((TencentContentSettingsAdapter)localObject).setJavaScriptEnabled(true);
          localObject = new AbsoluteLayout.LayoutParams(0, 0, 0, 0);
          ((TencentWebViewProxy)ReadModeManager.a(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager).get()).getRealWebView().addView(ReadModeManager.a(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager).getRealWebView(), (ViewGroup.LayoutParams)localObject);
          ((TencentWebViewChromium)ReadModeManager.a(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager).getWebViewProvider()).mAwContents.onSizeChanged(((TencentWebViewProxy)ReadModeManager.a(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager).get()).getRealWebView().getWidth(), ((TencentWebViewProxy)ReadModeManager.a(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager).get()).getRealWebView().getHeight(), 0, 0);
        }
        if (ReadModeManager.a(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager) != null)
        {
          ReadModeManager.a(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager).offer(this);
          return;
        }
        ReadModeManager.a(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager, this);
        ReadModeManager.a(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager).getRealWebView().setWebViewClient(new TencentWebViewClient()
        {
          public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
          {
            paramAnonymousWebView = new StringBuilder();
            paramAnonymousWebView.append("onPageFinished url: ");
            paramAnonymousWebView.append(paramAnonymousString);
            paramAnonymousWebView.append(" mDistillUrl: ");
            paramAnonymousWebView.append(ReadModeManager.d.this.a);
            paramAnonymousWebView.append(" mHasDistilled:");
            paramAnonymousWebView.append(ReadModeManager.d.this.b);
            Log.e("penghu_test", paramAnonymousWebView.toString());
            if (((paramAnonymousString != null) && (ReadModeManager.d.this.a != null) && (!paramAnonymousString.substring(paramAnonymousString.indexOf("/")).equals(ReadModeManager.d.this.a.substring(ReadModeManager.d.this.a.indexOf("/"))))) || (ReadModeManager.d.this.b)) {
              return;
            }
            ReadModeManager.d.b(ReadModeManager.d.this);
          }
        });
        ReadModeManager.a(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager).stopLoading();
        ReadModeManager.a(this.jdField_a_of_type_ComTencentSmttWebkitReadModeManager).loadUrl(this.jdField_a_of_type_JavaLangString);
        a(3000L);
        return;
      }
    }
  }
  
  public static enum e
  {
    static
    {
      jdField_a_of_type_ComTencentSmttWebkitReadModeManager$e = new e("MODE_NORMAL", 0);
      b = new e("MODE_PRE_CHECK", 1);
      c = new e("MODE_PRE_DISTILL", 2);
      d = new e("MODE_RE_ENTER", 3);
      jdField_a_of_type_ArrayOfComTencentSmttWebkitReadModeManager$e = new e[] { jdField_a_of_type_ComTencentSmttWebkitReadModeManager$e, b, c, d };
    }
    
    private e() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ReadModeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */