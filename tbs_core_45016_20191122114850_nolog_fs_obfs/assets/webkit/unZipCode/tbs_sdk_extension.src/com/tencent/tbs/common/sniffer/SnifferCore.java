package com.tencent.tbs.common.sniffer;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import com.tencent.common.utils.RSAUtils;
import com.tencent.common.utils.UrlUtils;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.proxy.ProxyWebChromeClient;
import com.tencent.tbs.modulebridge.TBSModuleBridgeFactory;
import com.tencent.tbs.modulebridge.webview.IBrigeWebViewProxy;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

class SnifferCore
{
  private static List<String> mOverrideBlackList;
  static String sBrowserVersion;
  static SniffTools sDefaultTools;
  static List<String> sDefaultVideoSniffList;
  static String sGuid;
  static List<String> sSniffSupportHostList;
  Context mContext;
  Handler mHandler = new Handler(Looper.getMainLooper());
  SnifferBase mSniffBase;
  
  public SnifferCore(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private static List<String> getSniffPlayFailUrls()
  {
    if (mOverrideBlackList == null) {
      mOverrideBlackList = new ArrayList();
    }
    return mOverrideBlackList;
  }
  
  private SniffTools getSniffTools()
  {
    SniffTools localSniffTools = sDefaultTools;
    if ((localSniffTools != null) && (!localSniffTools.mBusy)) {
      localSniffTools = sDefaultTools;
    } else {
      localSniffTools = null;
    }
    Object localObject = localSniffTools;
    if (localSniffTools == null)
    {
      localObject = localSniffTools;
      if (this.mContext != null)
      {
        localSniffTools = new SniffTools();
        localObject = TBSModuleBridgeFactory.getInstance().createWebView(this.mContext).getIX5WebViewBase();
        if ((localObject instanceof IX5WebViewBase))
        {
          localSniffTools.mWebView = ((IX5WebViewBase)localObject);
          localSniffTools.mJSExtension = new SniffExtensions(localSniffTools);
          localSniffTools.mWebView.addJavascriptInterface(localSniffTools.mJSExtension, "x5mtt");
          if (Build.VERSION.SDK_INT >= 16) {
            localSniffTools.mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
          }
          localSniffTools.mWebView.setWebChromeClient(new ProxyWebChromeClient()
          {
            public boolean onJsPrompt(IX5WebViewBase paramAnonymousIX5WebViewBase, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, JsPromptResult paramAnonymousJsPromptResult)
            {
              paramAnonymousJsPromptResult.cancel();
              return true;
            }
          });
          localSniffTools.mWebView.getSettings().setJavaScriptEnabled(true);
        }
        localObject = localSniffTools;
        if (sDefaultTools == null)
        {
          sDefaultTools = localSniffTools;
          localObject = localSniffTools;
        }
      }
    }
    return (SniffTools)localObject;
  }
  
  public static List<String> getVideoSniffList()
  {
    try
    {
      Object localObject3;
      if (sDefaultVideoSniffList == null)
      {
        sDefaultVideoSniffList = new ArrayList();
        localObject1 = new String[15];
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("youku.com|");
        ((StringBuilder)localObject3).append("http://res.imtt.qq.com/ipadvedio/js/");
        ((StringBuilder)localObject3).append("youku.htm|0");
        localObject1[0] = ((StringBuilder)localObject3).toString();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("tudou.com|");
        ((StringBuilder)localObject3).append("http://res.imtt.qq.com/ipadvedio/js/");
        ((StringBuilder)localObject3).append("tudou.htm|0");
        localObject1[1] = ((StringBuilder)localObject3).toString();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("letv.com|");
        ((StringBuilder)localObject3).append("http://res.imtt.qq.com/ipadvedio/js/");
        ((StringBuilder)localObject3).append("letv.htm|0");
        localObject1[2] = ((StringBuilder)localObject3).toString();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("sohu.com|");
        ((StringBuilder)localObject3).append("http://res.imtt.qq.com/ipadvedio/js/");
        ((StringBuilder)localObject3).append("sohu.htm|0");
        localObject1[3] = ((StringBuilder)localObject3).toString();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("ku6.com|");
        ((StringBuilder)localObject3).append("http://res.imtt.qq.com/ipadvedio/js/");
        ((StringBuilder)localObject3).append("ku6.htm|0");
        localObject1[4] = ((StringBuilder)localObject3).toString();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("v.qq.com|");
        ((StringBuilder)localObject3).append("http://res.imtt.qq.com/ipadvedio/js/");
        ((StringBuilder)localObject3).append("vqq.htm|0");
        localObject1[5] = ((StringBuilder)localObject3).toString();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("iqiyi.com|");
        ((StringBuilder)localObject3).append("http://res.imtt.qq.com/ipadvedio/js/");
        ((StringBuilder)localObject3).append("iqiyi.htm|0");
        localObject1[6] = ((StringBuilder)localObject3).toString();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("video.sina.com.cn|");
        ((StringBuilder)localObject3).append("http://res.imtt.qq.com/ipadvedio/js/");
        ((StringBuilder)localObject3).append("sina.htm|0");
        localObject1[7] = ((StringBuilder)localObject3).toString();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("video.sina.cn|");
        ((StringBuilder)localObject3).append("http://res.imtt.qq.com/ipadvedio/js/");
        ((StringBuilder)localObject3).append("sina.htm|0");
        localObject1[8] = ((StringBuilder)localObject3).toString();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("3g.qq.com|");
        ((StringBuilder)localObject3).append("http://res.imtt.qq.com/ipadvedio/js/");
        ((StringBuilder)localObject3).append("3gqq.htm|0");
        localObject1[9] = ((StringBuilder)localObject3).toString();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("56.com|");
        ((StringBuilder)localObject3).append("http://res.imtt.qq.com/ipadvedio/js/");
        ((StringBuilder)localObject3).append("56.htm|0");
        localObject1[10] = ((StringBuilder)localObject3).toString();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("pps.tv|");
        ((StringBuilder)localObject3).append("http://res.imtt.qq.com/ipadvedio/js/");
        ((StringBuilder)localObject3).append("pps.htm|0");
        localObject1[11] = ((StringBuilder)localObject3).toString();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("fengyunzhibo|");
        ((StringBuilder)localObject3).append("http://res.imtt.qq.com/ipadvedio/js/");
        ((StringBuilder)localObject3).append("fengyunzhibo.htm|0");
        localObject1[12] = ((StringBuilder)localObject3).toString();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("qvodbdhdmark|");
        ((StringBuilder)localObject3).append("http://res.imtt.qq.com/ipadvedio/js/");
        ((StringBuilder)localObject3).append("qvodbdhd.htm|0");
        localObject1[13] = ((StringBuilder)localObject3).toString();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("flash2h5videomark|");
        ((StringBuilder)localObject3).append("http://res.imtt.qq.com/ipadvedio/js/");
        ((StringBuilder)localObject3).append("flash2video.htm|0");
        localObject1[14] = ((StringBuilder)localObject3).toString();
        int i = 0;
        while (i < localObject1.length)
        {
          sDefaultVideoSniffList.add(localObject1[i]);
          i += 1;
        }
      }
      Object localObject1 = sDefaultVideoSniffList;
      if (sSniffSupportHostList == null)
      {
        sSniffSupportHostList = new ArrayList();
        if (((List)localObject1).size() > 0) {
          try
          {
            localObject3 = ((List)localObject1).iterator();
            while (((Iterator)localObject3).hasNext())
            {
              String[] arrayOfString = ((String)((Iterator)localObject3).next()).split("\\|");
              if (arrayOfString.length > 1) {
                sSniffSupportHostList.add(arrayOfString[0]);
              }
            }
          }
          finally {}
        }
      }
      return (List<String>)localObject1;
    }
    finally {}
  }
  
  public static void onSniffPlayFailed(String paramString)
  {
    getSniffPlayFailUrls();
    synchronized (mOverrideBlackList)
    {
      if (!mOverrideBlackList.contains(paramString)) {
        mOverrideBlackList.add(paramString);
      }
      return;
    }
  }
  
  public static void setVideoSniffList(List<String> paramList, String paramString1, String paramString2)
  {
    sDefaultVideoSniffList = paramList;
    sBrowserVersion = paramString1;
    sGuid = paramString2;
    sSniffSupportHostList = null;
    getVideoSniffList();
  }
  
  public static boolean shouldOverrideStandardPlay(String paramString)
  {
    ??? = getVideoSniffList();
    String str;
    if (??? != null)
    {
      if (paramString == null) {
        return false;
      }
      str = UrlUtils.getHost(paramString);
      if (str == null) {}
    }
    for (;;)
    {
      try
      {
        Iterator localIterator = ((List)???).iterator();
        if (!localIterator.hasNext()) {
          break label177;
        }
        Object localObject2 = (String)localIterator.next();
        if (localObject2 == null) {
          continue;
        }
        localObject2 = ((String)localObject2).split("\\|");
        if ((localObject2.length <= 2) || (!localObject2[2].equals("1")) || (!str.contains(localObject2[0]))) {
          continue;
        }
        bool = true;
      }
      finally {}
      boolean bool = false;
      if (bool) {
        synchronized (getSniffPlayFailUrls().iterator())
        {
          while (((Iterator)???).hasNext()) {
            if (paramString.equals((String)((Iterator)???).next())) {
              return false;
            }
          }
          return bool;
        }
      }
      return bool;
      return false;
      label177:
      bool = false;
    }
  }
  
  public static List<String> sniffSupportList()
  {
    getVideoSniffList();
    return sSniffSupportHostList;
  }
  
  static void webViewEvaluateScript(IX5WebViewBase paramIX5WebViewBase, String paramString)
  {
    for (;;)
    {
      try
      {
        localView = paramIX5WebViewBase.getView();
        localClass = localView.getClass();
        localObject1 = null;
        localObject2 = localObject1;
        if (localClass != Object.class) {
          if (localClass == null) {
            localObject2 = localObject1;
          }
        }
      }
      catch (Exception localException1)
      {
        View localView;
        Class localClass;
        Object localObject1;
        Object localObject2;
        localException1.printStackTrace();
        paramIX5WebViewBase.loadUrl(paramString);
      }
      try
      {
        localObject2 = localClass.getDeclaredMethod("evaluateJavascript", new Class[] { String.class, ValueCallback.class });
        localObject1 = localObject2;
        if (localObject2 == null) {}
      }
      catch (Exception localException2)
      {
        continue;
      }
      localClass = localClass.getSuperclass();
    }
    if (localObject2 != null)
    {
      ((Method)localObject2).setAccessible(true);
      ((Method)localObject2).invoke(localView, new Object[] { paramString, null });
      return;
    }
  }
  
  void initIfNeeded()
  {
    if (this.mSniffBase == null)
    {
      this.mSniffBase = new SnifferBase(this.mHandler, getSniffTools(), this.mContext);
      this.mSniffBase.setSniffDomainList(getVideoSniffList());
    }
  }
  
  public void sniffVideo(final String paramString, final int paramInt, final SniffObserver paramSniffObserver)
  {
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        SnifferCore.this.initIfNeeded();
        SnifferCore.this.mSniffBase.start(paramString, paramInt, paramSniffObserver);
      }
    });
  }
  
  public void sniffVideoFromFlash(final String paramString1, final String paramString2, final int paramInt, final SniffObserver paramSniffObserver)
  {
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        SnifferCore.this.initIfNeeded();
        SnifferCore.this.mSniffBase.sniffH5VideoFromFlash(paramString1, paramString2, paramInt, paramSniffObserver);
      }
    });
  }
  
  class SniffExtensions
  {
    SniffObserver.InternalObserver mObserver;
    SnifferCore.SniffTools mTools;
    
    SniffExtensions(SnifferCore.SniffTools paramSniffTools)
    {
      this.mTools = paramSniffTools;
    }
    
    private String getCharsetFromHeader(HttpURLConnection paramHttpURLConnection, String paramString)
    {
      paramHttpURLConnection = paramHttpURLConnection.getHeaderField("Content-Type");
      if (paramHttpURLConnection != null)
      {
        paramHttpURLConnection = paramHttpURLConnection.toLowerCase();
        paramHttpURLConnection = Pattern.compile("charset[\\s]*=[\\s]*([\\w-]+)").matcher(paramHttpURLConnection);
        if ((paramHttpURLConnection.find()) && (paramHttpURLConnection.groupCount() >= 1)) {
          return paramHttpURLConnection.group(1);
        }
      }
      return paramString;
    }
    
    private String getResourceFromCache(String paramString)
    {
      return null;
    }
    
    private void httpGetCallback(final String paramString)
    {
      SnifferCore.this.mHandler.post(new Runnable()
      {
        public void run()
        {
          SnifferCore.webViewEvaluateScript(SnifferCore.SniffExtensions.this.mTools.mWebView, paramString);
        }
      });
    }
    
    @JavascriptInterface
    public int browserBuilderId()
    {
      return 640000;
    }
    
    /* Error */
    void doSniffVideoHTTPGet(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    {
      // Byte code:
      //   0: aload 5
      //   2: invokestatic 111	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   5: ifne +61 -> 66
      //   8: new 113	org/json/JSONObject
      //   11: dup
      //   12: aload 5
      //   14: invokespecial 115	org/json/JSONObject:<init>	(Ljava/lang/String;)V
      //   17: astore 5
      //   19: aload 5
      //   21: ldc 117
      //   23: invokevirtual 120	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
      //   26: astore 12
      //   28: aload 12
      //   30: astore_1
      //   31: aload 5
      //   33: astore 14
      //   35: aload_1
      //   36: astore 5
      //   38: goto +34 -> 72
      //   41: astore 12
      //   43: goto +8 -> 51
      //   46: astore 12
      //   48: aconst_null
      //   49: astore 5
      //   51: aload 12
      //   53: invokevirtual 123	java/lang/Exception:printStackTrace	()V
      //   56: aload 5
      //   58: astore 14
      //   60: aload_1
      //   61: astore 5
      //   63: goto +9 -> 72
      //   66: aconst_null
      //   67: astore 14
      //   69: aload_1
      //   70: astore 5
      //   72: aload 5
      //   74: ifnonnull +4 -> 78
      //   77: return
      //   78: aload 5
      //   80: bipush 35
      //   82: invokevirtual 127	java/lang/String:indexOf	(I)I
      //   85: istore 6
      //   87: iload 6
      //   89: ifle +16 -> 105
      //   92: aload 5
      //   94: iconst_0
      //   95: iload 6
      //   97: invokevirtual 131	java/lang/String:substring	(II)Ljava/lang/String;
      //   100: astore 12
      //   102: goto +7 -> 109
      //   105: aload 5
      //   107: astore 12
      //   109: aload_0
      //   110: aload 12
      //   112: invokespecial 133	com/tencent/tbs/common/sniffer/SnifferCore$SniffExtensions:getResourceFromCache	(Ljava/lang/String;)Ljava/lang/String;
      //   115: astore_1
      //   116: new 113	org/json/JSONObject
      //   119: dup
      //   120: invokespecial 134	org/json/JSONObject:<init>	()V
      //   123: astore 13
      //   125: aload 13
      //   127: ldc -120
      //   129: sipush 404
      //   132: invokevirtual 140	org/json/JSONObject:put	(Ljava/lang/String;I)Lorg/json/JSONObject;
      //   135: pop
      //   136: new 142	java/lang/StringBuilder
      //   139: dup
      //   140: invokespecial 143	java/lang/StringBuilder:<init>	()V
      //   143: astore 15
      //   145: aload 15
      //   147: ldc -111
      //   149: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   152: pop
      //   153: aload 15
      //   155: aload 4
      //   157: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   160: pop
      //   161: aload 15
      //   163: ldc -105
      //   165: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   168: pop
      //   169: aload 15
      //   171: aload 13
      //   173: invokevirtual 154	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   176: pop
      //   177: aload 15
      //   179: ldc -100
      //   181: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   184: pop
      //   185: aload 15
      //   187: invokevirtual 159	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   190: astore 19
      //   192: aload_1
      //   193: ifnonnull +1168 -> 1361
      //   196: aload 14
      //   198: ifnull +108 -> 306
      //   201: aload 4
      //   203: astore 15
      //   205: aload 14
      //   207: ldc -95
      //   209: invokevirtual 120	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
      //   212: astore 4
      //   214: aload 4
      //   216: astore 15
      //   218: aload 14
      //   220: ldc -93
      //   222: invokevirtual 120	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
      //   225: astore 13
      //   227: aload 14
      //   229: ldc -91
      //   231: invokevirtual 120	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
      //   234: astore 15
      //   236: aload 14
      //   238: ldc -89
      //   240: invokevirtual 171	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
      //   243: astore_3
      //   244: aload_3
      //   245: invokevirtual 175	org/json/JSONObject:names	()Lorg/json/JSONArray;
      //   248: astore 16
      //   250: aload 13
      //   252: astore 14
      //   254: aload_3
      //   255: astore 17
      //   257: goto +61 -> 318
      //   260: astore_2
      //   261: goto +28 -> 289
      //   264: astore_2
      //   265: goto +22 -> 287
      //   268: astore_2
      //   269: goto +15 -> 284
      //   272: astore 14
      //   274: aload_2
      //   275: astore 13
      //   277: aload 15
      //   279: astore 4
      //   281: aload 14
      //   283: astore_2
      //   284: aload_3
      //   285: astore 15
      //   287: aconst_null
      //   288: astore_3
      //   289: aload_2
      //   290: invokevirtual 123	java/lang/Exception:printStackTrace	()V
      //   293: aconst_null
      //   294: astore 16
      //   296: aload 13
      //   298: astore 14
      //   300: aload_3
      //   301: astore 17
      //   303: goto +15 -> 318
      //   306: aconst_null
      //   307: astore 16
      //   309: aconst_null
      //   310: astore 17
      //   312: aload_3
      //   313: astore 15
      //   315: aload_2
      //   316: astore 14
      //   318: aload 12
      //   320: astore_2
      //   321: iconst_0
      //   322: istore 9
      //   324: sipush 200
      //   327: istore 6
      //   329: iconst_0
      //   330: istore 7
      //   332: iload 9
      //   334: iconst_5
      //   335: if_icmpge +1016 -> 1351
      //   338: new 177	java/net/URL
      //   341: dup
      //   342: aload_2
      //   343: invokespecial 178	java/net/URL:<init>	(Ljava/lang/String;)V
      //   346: invokevirtual 182	java/net/URL:openConnection	()Ljava/net/URLConnection;
      //   349: checkcast 37	java/net/HttpURLConnection
      //   352: astore 18
      //   354: aload_1
      //   355: astore_3
      //   356: aload_2
      //   357: astore 13
      //   359: iload 6
      //   361: istore 8
      //   363: aload 18
      //   365: astore 12
      //   367: aload 18
      //   369: ldc -72
      //   371: aload 14
      //   373: invokevirtual 188	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
      //   376: aload_1
      //   377: astore_3
      //   378: aload_2
      //   379: astore 13
      //   381: iload 6
      //   383: istore 8
      //   385: aload 18
      //   387: astore 12
      //   389: aload 18
      //   391: ldc -66
      //   393: aload 15
      //   395: invokevirtual 188	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
      //   398: aload 16
      //   400: ifnull +94 -> 494
      //   403: aload 18
      //   405: astore 12
      //   407: aload 16
      //   409: invokevirtual 195	org/json/JSONArray:length	()I
      //   412: istore 10
      //   414: iload 10
      //   416: ifle +78 -> 494
      //   419: iload 7
      //   421: istore 8
      //   423: iload 10
      //   425: istore 7
      //   427: iload 8
      //   429: iload 7
      //   431: if_icmpge +63 -> 494
      //   434: aload 18
      //   436: astore 12
      //   438: aload 16
      //   440: iload 8
      //   442: invokevirtual 199	org/json/JSONArray:get	(I)Ljava/lang/Object;
      //   445: checkcast 43	java/lang/String
      //   448: astore_3
      //   449: aload 18
      //   451: astore 12
      //   453: aload 18
      //   455: aload_3
      //   456: aload 17
      //   458: aload_3
      //   459: invokevirtual 120	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
      //   462: invokevirtual 188	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
      //   465: iload 8
      //   467: iconst_1
      //   468: iadd
      //   469: istore 8
      //   471: goto -44 -> 427
      //   474: astore 20
      //   476: aload_1
      //   477: astore_3
      //   478: aload_2
      //   479: astore 13
      //   481: iload 6
      //   483: istore 8
      //   485: aload 18
      //   487: astore 12
      //   489: aload 20
      //   491: invokevirtual 123	java/lang/Exception:printStackTrace	()V
      //   494: aload_1
      //   495: astore_3
      //   496: aload_2
      //   497: astore 13
      //   499: iload 6
      //   501: istore 8
      //   503: aload 18
      //   505: astore 12
      //   507: iconst_1
      //   508: invokestatic 203	java/net/HttpURLConnection:setFollowRedirects	(Z)V
      //   511: aload_1
      //   512: astore_3
      //   513: aload_2
      //   514: astore 13
      //   516: iload 6
      //   518: istore 8
      //   520: aload 18
      //   522: astore 12
      //   524: aload 18
      //   526: ldc -51
      //   528: invokevirtual 208	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
      //   531: aload_1
      //   532: astore_3
      //   533: aload_2
      //   534: astore 13
      //   536: iload 6
      //   538: istore 8
      //   540: aload 18
      //   542: astore 12
      //   544: aload 18
      //   546: sipush 15000
      //   549: invokevirtual 212	java/net/HttpURLConnection:setReadTimeout	(I)V
      //   552: aload_1
      //   553: astore_3
      //   554: aload_2
      //   555: astore 13
      //   557: iload 6
      //   559: istore 8
      //   561: aload 18
      //   563: astore 12
      //   565: aload 18
      //   567: sipush 15000
      //   570: invokevirtual 215	java/net/HttpURLConnection:setConnectTimeout	(I)V
      //   573: aload_1
      //   574: astore_3
      //   575: aload_2
      //   576: astore 13
      //   578: iload 6
      //   580: istore 8
      //   582: aload 18
      //   584: astore 12
      //   586: aload 18
      //   588: invokevirtual 218	java/net/HttpURLConnection:getResponseCode	()I
      //   591: istore 7
      //   593: iload 7
      //   595: sipush 301
      //   598: if_icmpeq +573 -> 1171
      //   601: iload 7
      //   603: sipush 302
      //   606: if_icmpeq +565 -> 1171
      //   609: iload 7
      //   611: sipush 303
      //   614: if_icmpeq +557 -> 1171
      //   617: iload 7
      //   619: sipush 307
      //   622: if_icmpne +6 -> 628
      //   625: goto +546 -> 1171
      //   628: aload_1
      //   629: astore_3
      //   630: iload 7
      //   632: sipush 200
      //   635: if_icmpne +875 -> 1510
      //   638: aload_1
      //   639: astore_3
      //   640: aload_2
      //   641: astore 13
      //   643: iload 7
      //   645: istore 8
      //   647: aload 18
      //   649: astore 12
      //   651: aload 18
      //   653: invokevirtual 222	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
      //   656: astore 16
      //   658: aload_1
      //   659: astore_3
      //   660: aload 16
      //   662: ifnull +848 -> 1510
      //   665: new 224	java/io/InputStreamReader
      //   668: dup
      //   669: aload 16
      //   671: aload_0
      //   672: aload 18
      //   674: ldc -30
      //   676: invokespecial 228	com/tencent/tbs/common/sniffer/SnifferCore$SniffExtensions:getCharsetFromHeader	(Ljava/net/HttpURLConnection;Ljava/lang/String;)Ljava/lang/String;
      //   679: invokespecial 231	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
      //   682: astore 14
      //   684: aload 14
      //   686: astore_3
      //   687: new 233	java/io/BufferedReader
      //   690: dup
      //   691: aload 14
      //   693: invokespecial 236	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
      //   696: astore 12
      //   698: aload 14
      //   700: astore_3
      //   701: new 238	java/lang/StringBuffer
      //   704: dup
      //   705: invokespecial 239	java/lang/StringBuffer:<init>	()V
      //   708: astore 13
      //   710: aload 14
      //   712: astore_3
      //   713: aload 12
      //   715: invokevirtual 242	java/io/BufferedReader:readLine	()Ljava/lang/String;
      //   718: astore 15
      //   720: aload 15
      //   722: ifnull +27 -> 749
      //   725: aload 14
      //   727: astore_3
      //   728: aload 13
      //   730: aload 15
      //   732: invokevirtual 245	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
      //   735: pop
      //   736: aload 14
      //   738: astore_3
      //   739: aload 13
      //   741: invokevirtual 246	java/lang/StringBuffer:length	()I
      //   744: ldc -9
      //   746: if_icmple -36 -> 710
      //   749: aload 14
      //   751: astore_3
      //   752: aload 13
      //   754: invokevirtual 248	java/lang/StringBuffer:toString	()Ljava/lang/String;
      //   757: astore 15
      //   759: aload 15
      //   761: astore_3
      //   762: aload_2
      //   763: astore 13
      //   765: iload 7
      //   767: istore 8
      //   769: aload 18
      //   771: astore 12
      //   773: aload 14
      //   775: invokevirtual 251	java/io/InputStreamReader:close	()V
      //   778: goto +22 -> 800
      //   781: astore_1
      //   782: aload 15
      //   784: astore_3
      //   785: aload_2
      //   786: astore 13
      //   788: iload 7
      //   790: istore 8
      //   792: aload 18
      //   794: astore 12
      //   796: aload_1
      //   797: invokevirtual 252	java/io/IOException:printStackTrace	()V
      //   800: aload 15
      //   802: astore_3
      //   803: aload_2
      //   804: astore 13
      //   806: iload 7
      //   808: istore 8
      //   810: aload 18
      //   812: astore 12
      //   814: aload 16
      //   816: invokevirtual 255	java/io/InputStream:close	()V
      //   819: aload 15
      //   821: astore_3
      //   822: goto +688 -> 1510
      //   825: astore 14
      //   827: aload 15
      //   829: astore_1
      //   830: goto +210 -> 1040
      //   833: astore 12
      //   835: goto +126 -> 961
      //   838: astore 14
      //   840: aconst_null
      //   841: astore 15
      //   843: goto +225 -> 1068
      //   846: aconst_null
      //   847: astore 14
      //   849: aload 14
      //   851: astore_3
      //   852: aload_0
      //   853: aload 19
      //   855: invokespecial 257	com/tencent/tbs/common/sniffer/SnifferCore$SniffExtensions:httpGetCallback	(Ljava/lang/String;)V
      //   858: aload 14
      //   860: ifnull +44 -> 904
      //   863: aload_1
      //   864: astore_3
      //   865: aload_2
      //   866: astore 13
      //   868: iload 7
      //   870: istore 8
      //   872: aload 18
      //   874: astore 12
      //   876: aload 14
      //   878: invokevirtual 251	java/io/InputStreamReader:close	()V
      //   881: goto +23 -> 904
      //   884: astore 14
      //   886: aload_1
      //   887: astore_3
      //   888: aload_2
      //   889: astore 13
      //   891: iload 7
      //   893: istore 8
      //   895: aload 18
      //   897: astore 12
      //   899: aload 14
      //   901: invokevirtual 252	java/io/IOException:printStackTrace	()V
      //   904: aload_1
      //   905: astore_3
      //   906: aload_2
      //   907: astore 13
      //   909: iload 7
      //   911: istore 8
      //   913: aload 18
      //   915: astore 12
      //   917: aload 16
      //   919: invokevirtual 255	java/io/InputStream:close	()V
      //   922: goto +23 -> 945
      //   925: astore 14
      //   927: aload_1
      //   928: astore_3
      //   929: aload_2
      //   930: astore 13
      //   932: iload 7
      //   934: istore 8
      //   936: aload 18
      //   938: astore 12
      //   940: aload 14
      //   942: invokevirtual 252	java/io/IOException:printStackTrace	()V
      //   945: aload 18
      //   947: ifnull +8 -> 955
      //   950: aload 18
      //   952: invokevirtual 260	java/net/HttpURLConnection:disconnect	()V
      //   955: return
      //   956: astore 12
      //   958: aconst_null
      //   959: astore 14
      //   961: aload 14
      //   963: astore_3
      //   964: aload 12
      //   966: invokevirtual 123	java/lang/Exception:printStackTrace	()V
      //   969: aload 14
      //   971: ifnull +44 -> 1015
      //   974: aload_1
      //   975: astore_3
      //   976: aload_2
      //   977: astore 13
      //   979: iload 7
      //   981: istore 8
      //   983: aload 18
      //   985: astore 12
      //   987: aload 14
      //   989: invokevirtual 251	java/io/InputStreamReader:close	()V
      //   992: goto +23 -> 1015
      //   995: astore 14
      //   997: aload_1
      //   998: astore_3
      //   999: aload_2
      //   1000: astore 13
      //   1002: iload 7
      //   1004: istore 8
      //   1006: aload 18
      //   1008: astore 12
      //   1010: aload 14
      //   1012: invokevirtual 252	java/io/IOException:printStackTrace	()V
      //   1015: aload_1
      //   1016: astore_3
      //   1017: aload_2
      //   1018: astore 13
      //   1020: iload 7
      //   1022: istore 8
      //   1024: aload 18
      //   1026: astore 12
      //   1028: aload 16
      //   1030: invokevirtual 255	java/io/InputStream:close	()V
      //   1033: aload_1
      //   1034: astore_3
      //   1035: goto +475 -> 1510
      //   1038: astore 14
      //   1040: aload_1
      //   1041: astore_3
      //   1042: aload_2
      //   1043: astore 13
      //   1045: iload 7
      //   1047: istore 8
      //   1049: aload 18
      //   1051: astore 12
      //   1053: aload 14
      //   1055: invokevirtual 252	java/io/IOException:printStackTrace	()V
      //   1058: aload_1
      //   1059: astore_3
      //   1060: goto +450 -> 1510
      //   1063: astore 14
      //   1065: aload_3
      //   1066: astore 15
      //   1068: aload 15
      //   1070: ifnull +44 -> 1114
      //   1073: aload_1
      //   1074: astore_3
      //   1075: aload_2
      //   1076: astore 13
      //   1078: iload 7
      //   1080: istore 8
      //   1082: aload 18
      //   1084: astore 12
      //   1086: aload 15
      //   1088: invokevirtual 251	java/io/InputStreamReader:close	()V
      //   1091: goto +23 -> 1114
      //   1094: astore 15
      //   1096: aload_1
      //   1097: astore_3
      //   1098: aload_2
      //   1099: astore 13
      //   1101: iload 7
      //   1103: istore 8
      //   1105: aload 18
      //   1107: astore 12
      //   1109: aload 15
      //   1111: invokevirtual 252	java/io/IOException:printStackTrace	()V
      //   1114: aload_1
      //   1115: astore_3
      //   1116: aload_2
      //   1117: astore 13
      //   1119: iload 7
      //   1121: istore 8
      //   1123: aload 18
      //   1125: astore 12
      //   1127: aload 16
      //   1129: invokevirtual 255	java/io/InputStream:close	()V
      //   1132: goto +23 -> 1155
      //   1135: astore 15
      //   1137: aload_1
      //   1138: astore_3
      //   1139: aload_2
      //   1140: astore 13
      //   1142: iload 7
      //   1144: istore 8
      //   1146: aload 18
      //   1148: astore 12
      //   1150: aload 15
      //   1152: invokevirtual 252	java/io/IOException:printStackTrace	()V
      //   1155: aload_1
      //   1156: astore_3
      //   1157: aload_2
      //   1158: astore 13
      //   1160: iload 7
      //   1162: istore 8
      //   1164: aload 18
      //   1166: astore 12
      //   1168: aload 14
      //   1170: athrow
      //   1171: aload_1
      //   1172: astore_3
      //   1173: aload_2
      //   1174: astore 13
      //   1176: iload 7
      //   1178: istore 8
      //   1180: aload 18
      //   1182: astore 12
      //   1184: aload 18
      //   1186: ldc_w 262
      //   1189: invokevirtual 41	java/net/HttpURLConnection:getHeaderField	(Ljava/lang/String;)Ljava/lang/String;
      //   1192: astore_2
      //   1193: aload_1
      //   1194: astore_3
      //   1195: aload_2
      //   1196: astore 13
      //   1198: iload 7
      //   1200: istore 8
      //   1202: aload 18
      //   1204: astore 12
      //   1206: aload_2
      //   1207: invokestatic 111	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   1210: istore 11
      //   1212: iload 11
      //   1214: ifne +26 -> 1240
      //   1217: aload 18
      //   1219: ifnull +8 -> 1227
      //   1222: aload 18
      //   1224: invokevirtual 260	java/net/HttpURLConnection:disconnect	()V
      //   1227: iload 9
      //   1229: iconst_1
      //   1230: iadd
      //   1231: istore 9
      //   1233: iload 7
      //   1235: istore 6
      //   1237: goto -908 -> 329
      //   1240: aload_1
      //   1241: astore_3
      //   1242: aload_2
      //   1243: astore 12
      //   1245: aload_3
      //   1246: astore_1
      //   1247: aload 4
      //   1249: astore 13
      //   1251: iload 7
      //   1253: istore 6
      //   1255: aload 18
      //   1257: ifnull +113 -> 1370
      //   1260: aload 18
      //   1262: invokevirtual 260	java/net/HttpURLConnection:disconnect	()V
      //   1265: aload_2
      //   1266: astore 12
      //   1268: aload_3
      //   1269: astore_1
      //   1270: aload 4
      //   1272: astore 13
      //   1274: iload 7
      //   1276: istore 6
      //   1278: goto +92 -> 1370
      //   1281: astore 14
      //   1283: iload 8
      //   1285: istore 6
      //   1287: aload 18
      //   1289: astore_2
      //   1290: aload_3
      //   1291: astore_1
      //   1292: goto +19 -> 1311
      //   1295: astore_1
      //   1296: aconst_null
      //   1297: astore 12
      //   1299: goto +40 -> 1339
      //   1302: astore 14
      //   1304: aconst_null
      //   1305: astore_3
      //   1306: aload_2
      //   1307: astore 13
      //   1309: aload_3
      //   1310: astore_2
      //   1311: aload_2
      //   1312: astore 12
      //   1314: aload 14
      //   1316: invokevirtual 123	java/lang/Exception:printStackTrace	()V
      //   1319: aload_2
      //   1320: ifnull +7 -> 1327
      //   1323: aload_2
      //   1324: invokevirtual 260	java/net/HttpURLConnection:disconnect	()V
      //   1327: aload 13
      //   1329: astore 12
      //   1331: aload 4
      //   1333: astore 13
      //   1335: goto +35 -> 1370
      //   1338: astore_1
      //   1339: aload 12
      //   1341: ifnull +8 -> 1349
      //   1344: aload 12
      //   1346: invokevirtual 260	java/net/HttpURLConnection:disconnect	()V
      //   1349: aload_1
      //   1350: athrow
      //   1351: aload_2
      //   1352: astore 12
      //   1354: aload 4
      //   1356: astore 13
      //   1358: goto +12 -> 1370
      //   1361: sipush 200
      //   1364: istore 6
      //   1366: aload 4
      //   1368: astore 13
      //   1370: aload_1
      //   1371: ifnonnull +10 -> 1381
      //   1374: aload_0
      //   1375: aload 19
      //   1377: invokespecial 257	com/tencent/tbs/common/sniffer/SnifferCore$SniffExtensions:httpGetCallback	(Ljava/lang/String;)V
      //   1380: return
      //   1381: new 113	org/json/JSONObject
      //   1384: dup
      //   1385: invokespecial 134	org/json/JSONObject:<init>	()V
      //   1388: astore_2
      //   1389: aload_2
      //   1390: ldc -120
      //   1392: iload 6
      //   1394: invokevirtual 140	org/json/JSONObject:put	(Ljava/lang/String;I)Lorg/json/JSONObject;
      //   1397: pop
      //   1398: aload_2
      //   1399: ldc_w 264
      //   1402: aload_1
      //   1403: invokevirtual 267	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      //   1406: pop
      //   1407: aload_2
      //   1408: ldc_w 269
      //   1411: aload 5
      //   1413: invokevirtual 267	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      //   1416: pop
      //   1417: aload_2
      //   1418: ldc_w 271
      //   1421: aload 12
      //   1423: invokevirtual 267	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      //   1426: pop
      //   1427: new 142	java/lang/StringBuilder
      //   1430: dup
      //   1431: invokespecial 143	java/lang/StringBuilder:<init>	()V
      //   1434: astore_1
      //   1435: aload_1
      //   1436: ldc -111
      //   1438: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1441: pop
      //   1442: aload_1
      //   1443: aload 13
      //   1445: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1448: pop
      //   1449: aload_1
      //   1450: ldc -105
      //   1452: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1455: pop
      //   1456: aload_1
      //   1457: aload_2
      //   1458: invokevirtual 154	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   1461: pop
      //   1462: aload_1
      //   1463: ldc -100
      //   1465: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1468: pop
      //   1469: aload_0
      //   1470: aload_1
      //   1471: invokevirtual 159	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   1474: invokespecial 257	com/tencent/tbs/common/sniffer/SnifferCore$SniffExtensions:httpGetCallback	(Ljava/lang/String;)V
      //   1477: return
      //   1478: aload_0
      //   1479: aload 19
      //   1481: invokespecial 257	com/tencent/tbs/common/sniffer/SnifferCore$SniffExtensions:httpGetCallback	(Ljava/lang/String;)V
      //   1484: return
      //   1485: aload_0
      //   1486: aload 19
      //   1488: invokespecial 257	com/tencent/tbs/common/sniffer/SnifferCore$SniffExtensions:httpGetCallback	(Ljava/lang/String;)V
      //   1491: return
      //   1492: astore_1
      //   1493: return
      //   1494: astore_3
      //   1495: goto -649 -> 846
      //   1498: astore_3
      //   1499: goto -650 -> 849
      //   1502: astore_1
      //   1503: goto -18 -> 1485
      //   1506: astore_1
      //   1507: goto -29 -> 1478
      //   1510: goto -268 -> 1242
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	1513	0	this	SniffExtensions
      //   0	1513	1	paramString1	String
      //   0	1513	2	paramString2	String
      //   0	1513	3	paramString3	String
      //   0	1513	4	paramString4	String
      //   0	1513	5	paramString5	String
      //   85	1308	6	i	int
      //   330	945	7	j	int
      //   361	923	8	k	int
      //   322	910	9	m	int
      //   412	12	10	n	int
      //   1210	3	11	bool	boolean
      //   26	3	12	str1	String
      //   41	1	12	localException1	Exception
      //   46	6	12	localException2	Exception
      //   100	713	12	localObject1	Object
      //   833	1	12	localException3	Exception
      //   874	65	12	localObject2	Object
      //   956	9	12	localException4	Exception
      //   985	437	12	localObject3	Object
      //   123	1321	13	localObject4	Object
      //   33	220	14	localObject5	Object
      //   272	10	14	localException5	Exception
      //   298	476	14	localObject6	Object
      //   825	1	14	localIOException1	java.io.IOException
      //   838	1	14	localObject7	Object
      //   847	30	14	localObject8	Object
      //   884	16	14	localIOException2	java.io.IOException
      //   925	16	14	localIOException3	java.io.IOException
      //   959	29	14	localObject9	Object
      //   995	16	14	localIOException4	java.io.IOException
      //   1038	16	14	localIOException5	java.io.IOException
      //   1063	106	14	localObject10	Object
      //   1281	1	14	localException6	Exception
      //   1302	13	14	localException7	Exception
      //   143	944	15	localObject11	Object
      //   1094	16	15	localIOException6	java.io.IOException
      //   1135	16	15	localIOException7	java.io.IOException
      //   248	880	16	localObject12	Object
      //   255	202	17	str2	String
      //   352	936	18	localHttpURLConnection	HttpURLConnection
      //   190	1297	19	str3	String
      //   474	16	20	localException8	Exception
      // Exception table:
      //   from	to	target	type
      //   19	28	41	java/lang/Exception
      //   8	19	46	java/lang/Exception
      //   244	250	260	java/lang/Exception
      //   236	244	264	java/lang/Exception
      //   227	236	268	java/lang/Exception
      //   205	214	272	java/lang/Exception
      //   218	227	272	java/lang/Exception
      //   407	414	474	java/lang/Exception
      //   438	449	474	java/lang/Exception
      //   453	465	474	java/lang/Exception
      //   773	778	781	java/io/IOException
      //   814	819	825	java/io/IOException
      //   687	698	833	java/lang/Exception
      //   701	710	833	java/lang/Exception
      //   713	720	833	java/lang/Exception
      //   728	736	833	java/lang/Exception
      //   739	749	833	java/lang/Exception
      //   752	759	833	java/lang/Exception
      //   665	684	838	finally
      //   876	881	884	java/io/IOException
      //   917	922	925	java/io/IOException
      //   665	684	956	java/lang/Exception
      //   987	992	995	java/io/IOException
      //   1028	1033	1038	java/io/IOException
      //   687	698	1063	finally
      //   701	710	1063	finally
      //   713	720	1063	finally
      //   728	736	1063	finally
      //   739	749	1063	finally
      //   752	759	1063	finally
      //   852	858	1063	finally
      //   964	969	1063	finally
      //   1086	1091	1094	java/io/IOException
      //   1127	1132	1135	java/io/IOException
      //   367	376	1281	java/lang/Exception
      //   389	398	1281	java/lang/Exception
      //   489	494	1281	java/lang/Exception
      //   507	511	1281	java/lang/Exception
      //   524	531	1281	java/lang/Exception
      //   544	552	1281	java/lang/Exception
      //   565	573	1281	java/lang/Exception
      //   586	593	1281	java/lang/Exception
      //   651	658	1281	java/lang/Exception
      //   773	778	1281	java/lang/Exception
      //   796	800	1281	java/lang/Exception
      //   814	819	1281	java/lang/Exception
      //   876	881	1281	java/lang/Exception
      //   899	904	1281	java/lang/Exception
      //   917	922	1281	java/lang/Exception
      //   940	945	1281	java/lang/Exception
      //   987	992	1281	java/lang/Exception
      //   1010	1015	1281	java/lang/Exception
      //   1028	1033	1281	java/lang/Exception
      //   1053	1058	1281	java/lang/Exception
      //   1086	1091	1281	java/lang/Exception
      //   1109	1114	1281	java/lang/Exception
      //   1127	1132	1281	java/lang/Exception
      //   1150	1155	1281	java/lang/Exception
      //   1168	1171	1281	java/lang/Exception
      //   1184	1193	1281	java/lang/Exception
      //   1206	1212	1281	java/lang/Exception
      //   338	354	1295	finally
      //   338	354	1302	java/lang/Exception
      //   367	376	1338	finally
      //   389	398	1338	finally
      //   407	414	1338	finally
      //   438	449	1338	finally
      //   453	465	1338	finally
      //   489	494	1338	finally
      //   507	511	1338	finally
      //   524	531	1338	finally
      //   544	552	1338	finally
      //   565	573	1338	finally
      //   586	593	1338	finally
      //   651	658	1338	finally
      //   773	778	1338	finally
      //   796	800	1338	finally
      //   814	819	1338	finally
      //   876	881	1338	finally
      //   899	904	1338	finally
      //   917	922	1338	finally
      //   940	945	1338	finally
      //   987	992	1338	finally
      //   1010	1015	1338	finally
      //   1028	1033	1338	finally
      //   1053	1058	1338	finally
      //   1086	1091	1338	finally
      //   1109	1114	1338	finally
      //   1127	1132	1338	finally
      //   1150	1155	1338	finally
      //   1168	1171	1338	finally
      //   1184	1193	1338	finally
      //   1206	1212	1338	finally
      //   1314	1319	1338	finally
      //   125	136	1492	org/json/JSONException
      //   665	684	1494	java/lang/Error
      //   687	698	1498	java/lang/Error
      //   701	710	1498	java/lang/Error
      //   713	720	1498	java/lang/Error
      //   728	736	1498	java/lang/Error
      //   739	749	1498	java/lang/Error
      //   752	759	1498	java/lang/Error
      //   1389	1427	1502	org/json/JSONException
      //   1427	1477	1506	java/lang/OutOfMemoryError
    }
    
    @JavascriptInterface
    public String getBrowserSignature(String paramString)
    {
      if (this.mTools.mWebView != null) {
        localObject1 = this.mTools.mWebView.getUrl();
      } else {
        localObject1 = "";
      }
      Object localObject1 = UrlUtils.getHost((String)localObject1);
      if (localObject1 != null)
      {
        if (!((String)localObject1).endsWith("qq.com")) {
          return null;
        }
        try
        {
          localObject1 = (WifiManager)SnifferCore.this.mContext.getApplicationContext().getSystemService("wifi");
          if (localObject1 == null) {
            localObject1 = null;
          } else {
            localObject1 = ((WifiManager)localObject1).getConnectionInfo();
          }
        }
        catch (Exception paramString)
        {
          paramString.printStackTrace();
          return null;
        }
      }
      for (localObject1 = ((WifiInfo)localObject1).getMacAddress();; localObject1 = "")
      {
        Object localObject2 = localObject1;
        if (localObject1 != null)
        {
          localObject1 = ((String)localObject1).replace(":", "");
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("mac:");
          ((StringBuilder)localObject2).append((String)localObject1);
          ((StringBuilder)localObject2).append(";");
          localObject2 = ((StringBuilder)localObject2).toString();
        }
        localObject1 = (TelephonyManager)SnifferCore.this.mContext.getSystemService("phone");
        Object localObject3;
        if (localObject1 != null) {
          try
          {
            localObject3 = new StringBuilder();
            ((StringBuilder)localObject3).append("imsi:");
            ((StringBuilder)localObject3).append(((TelephonyManager)localObject1).getSubscriberId());
            ((StringBuilder)localObject3).append(";");
            localObject3 = ((StringBuilder)localObject3).toString();
          }
          catch (Exception paramString)
          {
            paramString.printStackTrace();
            return null;
          }
        } else {
          localObject3 = null;
        }
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append(paramString);
        ((StringBuilder)localObject1).append(";");
        localObject1 = ((StringBuilder)localObject1).toString();
        paramString = new StringBuilder();
        paramString.append("bver:");
        paramString.append(SnifferCore.sBrowserVersion);
        paramString.append(";");
        String str2 = paramString.toString();
        paramString = new StringBuilder();
        paramString.append("guid:");
        paramString.append(SnifferCore.sGuid);
        String str1 = paramString.toString();
        if ((localObject1 != null) && (((String)localObject1).length() <= 117))
        {
          paramString = (String)localObject1;
          if (localObject2 != null)
          {
            paramString = (String)localObject1;
            if (((String)localObject1).length() + ((String)localObject2).length() <= 117)
            {
              paramString = new StringBuilder();
              paramString.append((String)localObject1);
              paramString.append((String)localObject2);
              paramString = paramString.toString();
            }
          }
          localObject1 = paramString;
          if (localObject3 != null)
          {
            localObject1 = paramString;
            if (paramString.length() + ((String)localObject3).length() <= 117)
            {
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append(paramString);
              ((StringBuilder)localObject1).append((String)localObject3);
              localObject1 = ((StringBuilder)localObject1).toString();
            }
          }
          paramString = (String)localObject1;
          if (str2 != null)
          {
            paramString = (String)localObject1;
            if (((String)localObject1).length() + str2.length() <= 117)
            {
              paramString = new StringBuilder();
              paramString.append((String)localObject1);
              paramString.append(str2);
              paramString = paramString.toString();
            }
          }
          localObject1 = paramString;
          if (str1 != null)
          {
            localObject1 = paramString;
            if (paramString.length() + str1.length() <= 117)
            {
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append(paramString);
              ((StringBuilder)localObject1).append(str1);
              localObject1 = ((StringBuilder)localObject1).toString();
            }
          }
          return RSAUtils.encrypt((String)localObject1);
        }
        return null;
        return null;
        if (localObject1 != null) {
          break;
        }
      }
    }
    
    public void setObserver(SniffObserver.InternalObserver paramInternalObserver)
    {
      this.mObserver = paramInternalObserver;
    }
    
    @JavascriptInterface
    public void sniffVideoHTTPGet(final String paramString)
    {
      new Thread("sniffVideoHTTPGet")
      {
        public void run()
        {
          SnifferCore.SniffExtensions.this.doSniffVideoHTTPGet(null, null, null, null, paramString);
        }
      }.start();
    }
    
    @JavascriptInterface
    public void sniffVideoHTTPGet(final String paramString1, final String paramString2, final String paramString3, final String paramString4)
    {
      new Thread("sniffVideoHTTPGet")
      {
        public void run()
        {
          SnifferCore.SniffExtensions.this.doSniffVideoHTTPGet(paramString1, paramString2, paramString3, paramString4, null);
        }
      }.start();
    }
    
    @JavascriptInterface
    public boolean sniffVideoResult(final String paramString)
    {
      try
      {
        paramString = new JSONObject(paramString);
        SnifferCore.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            for (;;)
            {
              int j;
              try
              {
                str2 = paramString.getString("cmd");
                if (str2 == null) {
                  return;
                }
                boolean bool = str2.equals("notify");
                String str1 = null;
                if (bool)
                {
                  if (SnifferCore.SniffExtensions.this.mObserver == null) {
                    break;
                  }
                  JSONArray localJSONArray = paramString.getJSONArray("urls");
                  int k = paramString.getInt("clarityNum");
                  int m = paramString.getInt("status");
                  j = paramString.getInt("curIndex");
                  if (paramString.has("script"))
                  {
                    str1 = paramString.getString("script");
                    if (!paramString.has("userAgent")) {
                      break label296;
                    }
                    str2 = paramString.getString("userAgent");
                    ArrayList localArrayList = new ArrayList();
                    i = 0;
                    if (i < localJSONArray.length())
                    {
                      localArrayList.add((String)localJSONArray.get(i));
                      i += 1;
                      continue;
                    }
                    if (localArrayList.size() != 0) {
                      break label302;
                    }
                    localArrayList = null;
                    i = -1;
                    SnifferCore.SniffExtensions.this.mObserver.onSniffCompleted(localArrayList, i, k, str2, m, str1);
                  }
                }
                else
                {
                  if (str2.equals("string"))
                  {
                    if (SnifferCore.SniffExtensions.this.mObserver != null)
                    {
                      if (paramString.has("content")) {
                        str1 = paramString.getString("content");
                      }
                      SnifferCore.SniffExtensions.this.mObserver.onReceivedString(str1);
                    }
                    return;
                  }
                  return;
                }
              }
              catch (Exception localException)
              {
                localException.printStackTrace();
                return;
              }
              Object localObject = null;
              continue;
              label296:
              String str2 = null;
              continue;
              label302:
              int i = j;
            }
          }
        });
        return true;
      }
      catch (Exception paramString)
      {
        for (;;) {}
      }
      return false;
    }
  }
  
  class SniffTools
  {
    boolean mBusy = false;
    SnifferCore.SniffExtensions mJSExtension;
    IX5WebViewBase mWebView;
    
    SniffTools() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\sniffer\SnifferCore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */