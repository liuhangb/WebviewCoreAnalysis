package com.tencent.tbs.tbsshell.common.x5core;

import android.content.Context;
import android.graphics.Point;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.ValueCallback;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.utils.ReflectionUtils;
import com.tencent.common.utils.UrlUtils;
import com.tencent.mtt.ContextHolder;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PageInfoCollector
{
  private static final String TAG = "PageInfoCollector";
  private static String mDomDistillerJS;
  private String PP;
  private String content;
  private String domain;
  private List<ImageInfo> images;
  private String[] keyword;
  private Point mAdLocation;
  private Point mWebviewSize;
  private String title;
  private String url;
  private List<VideoInfo> videos;
  
  public PageInfoCollector()
  {
    try
    {
      this.PP = ContextHolder.getAppContext().getPackageName();
      return;
    }
    catch (Exception localException) {}
  }
  
  private static JSONArray Array2JSONArray(String[] paramArrayOfString)
  {
    JSONArray localJSONArray = new JSONArray();
    if (paramArrayOfString == null) {
      return localJSONArray;
    }
    try
    {
      int j = paramArrayOfString.length;
      int i = 0;
      while (i < j)
      {
        localJSONArray.put(paramArrayOfString[i]);
        i += 1;
      }
      return localJSONArray;
    }
    catch (Exception paramArrayOfString) {}
    return localJSONArray;
  }
  
  private static JSONObject Point2JSONObject(Point paramPoint)
  {
    if (paramPoint == null) {
      return null;
    }
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("x", paramPoint.x);
      localJSONObject.put("y", paramPoint.y);
      return localJSONObject;
    }
    catch (Exception paramPoint) {}
    return localJSONObject;
  }
  
  private static JSONObject Size2JSONObject(Point paramPoint)
  {
    if (paramPoint == null) {
      return null;
    }
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("width", paramPoint.x);
      localJSONObject.put("height", paramPoint.y);
      return localJSONObject;
    }
    catch (Exception paramPoint) {}
    return localJSONObject;
  }
  
  private void evaluateJavascript(final IX5WebView paramIX5WebView, final String paramString, final ValueCallback<String> paramValueCallback)
  {
    paramIX5WebView = new Runnable()
    {
      public void run()
      {
        IX5WebView localIX5WebView = paramIX5WebView;
        String str = paramString;
        ValueCallback local1 = new ValueCallback()
        {
          public void onReceiveValue(String paramAnonymous2String)
          {
            if (PageInfoCollector.1.this.val$callback != null) {
              PageInfoCollector.1.this.val$callback.onReceiveValue(paramAnonymous2String);
            }
          }
        };
        ReflectionUtils.invokeInstance(localIX5WebView, "evaluateJavascript", new Class[] { String.class, ValueCallback.class }, new Object[] { str, local1 });
      }
    };
    if (Looper.getMainLooper() == Looper.myLooper())
    {
      paramIX5WebView.run();
      return;
    }
    BrowserExecutorSupplier.forMainThreadTasks().execute(paramIX5WebView);
  }
  
  private int onPageInfoReceive(JSONObject paramJSONObject)
  {
    Object localObject1 = paramJSONObject.optString("content", null);
    Object localObject2 = paramJSONObject.optString("url", null);
    Object localObject3 = paramJSONObject.optString("chapterTitle", null);
    boolean bool = TextUtils.isEmpty((CharSequence)localObject1);
    int k = 0;
    int i;
    if (!bool)
    {
      setContent((String)localObject1);
      i = 0;
    }
    else
    {
      i = -5;
    }
    if (!TextUtils.isEmpty((CharSequence)localObject2))
    {
      setUrl((String)localObject2);
      setDomain(UrlUtils.getHost((String)localObject2));
    }
    if (!TextUtils.isEmpty((CharSequence)localObject3)) {
      setTitle((String)localObject3);
    }
    localObject1 = paramJSONObject.optJSONArray("imageUrl");
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("onPageInfoReceive imageUrl:");
    ((StringBuilder)localObject2).append(localObject1);
    ((StringBuilder)localObject2).toString();
    int j;
    if (localObject1 != null)
    {
      localObject2 = new ArrayList();
      j = 0;
      while (j < ((JSONArray)localObject1).length())
      {
        localObject3 = ((JSONArray)localObject1).optJSONObject(j);
        if ((localObject3 != null) && (((JSONObject)localObject3).optString("1", null) != null)) {
          ((List)localObject2).add(((JSONObject)localObject3).optString("1", null));
        }
        j += 1;
      }
      setImages((List)localObject2, null);
    }
    localObject1 = paramJSONObject.optJSONArray("videos");
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("onPageInfoReceive videos:");
    ((StringBuilder)localObject2).append(localObject1);
    ((StringBuilder)localObject2).toString();
    if (localObject1 != null)
    {
      localObject2 = new ArrayList();
      localObject3 = new ArrayList();
      j = k;
      while (j < ((JSONArray)localObject1).length())
      {
        JSONObject localJSONObject = ((JSONArray)localObject1).optJSONObject(j);
        if ((localJSONObject != null) && (localJSONObject.optString("1", null) != null))
        {
          ((List)localObject2).add(localJSONObject.optString("1", null));
          ((List)localObject3).add(new Point(localJSONObject.optInt("2", 360), localJSONObject.optInt("3", 100)));
        }
        j += 1;
      }
      setVideos((List)localObject2, (List)localObject3);
    }
    paramJSONObject = paramJSONObject.optString("keywords", null);
    if (paramJSONObject != null) {
      setKeyword(paramJSONObject.split(","));
    }
    return i;
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public String getDomain()
  {
    return this.domain;
  }
  
  public JSONObject getJsonObject()
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("package", this.PP);
      localJSONObject.put("domain", this.domain);
      localJSONObject.put("url", this.url);
      localJSONObject.put("title", this.title);
      localJSONObject.put("keyword", Array2JSONArray(this.keyword));
      localJSONObject.put("content", this.content);
      localJSONObject.put("images", ImageInfo.List2JSONArray(this.images));
      localJSONObject.put("videos", VideoInfo.List2JSONArray(this.videos));
      localJSONObject.put("adLocation", Point2JSONObject(this.mAdLocation));
      localJSONObject.put("pageSize", Size2JSONObject(this.mWebviewSize));
      return localJSONObject;
    }
    catch (JSONException localJSONException)
    {
      for (;;) {}
    }
    return null;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void reset()
  {
    this.url = null;
    this.domain = null;
    this.title = null;
    this.content = null;
    this.keyword = null;
    this.images = null;
    this.videos = null;
    this.mAdLocation = null;
    this.mWebviewSize = null;
  }
  
  public void setAdLocaton(Point paramPoint)
  {
    this.mAdLocation = paramPoint;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setAdLocaton ");
    localStringBuilder.append(paramPoint.x);
    localStringBuilder.toString();
  }
  
  public void setContent(String paramString)
  {
    this.content = paramString;
  }
  
  public void setDomain(String paramString)
  {
    this.domain = paramString;
  }
  
  public void setImages(List<String> paramList, List<Point> paramList1)
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    while (i < paramList.size())
    {
      String str = (String)paramList.get(i);
      Point localPoint2 = new Point(360, 100);
      Point localPoint1 = localPoint2;
      if (paramList1 != null)
      {
        localPoint1 = localPoint2;
        if (paramList1.size() > i) {
          localPoint1 = (Point)paramList1.get(i);
        }
      }
      localArrayList.add(new ImageInfo(str, localPoint1));
      i += 1;
    }
    this.images = localArrayList;
  }
  
  public void setKeyword(String[] paramArrayOfString)
  {
    this.keyword = paramArrayOfString;
  }
  
  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
  
  public void setUrl(String paramString)
  {
    this.url = paramString;
  }
  
  public void setVideos(List<String> paramList, List<Point> paramList1)
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    while (i < paramList.size())
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("video:");
      ((StringBuilder)localObject).append((String)paramList.get(i));
      ((StringBuilder)localObject).toString();
      Point localPoint = new Point(360, 100);
      localObject = localPoint;
      if (paramList1 != null)
      {
        localObject = localPoint;
        if (paramList1.size() > i) {
          localObject = (Point)paramList1.get(i);
        }
      }
      localArrayList.add(new VideoInfo((String)paramList.get(i), (Point)localObject));
      i += 1;
    }
    this.videos = localArrayList;
  }
  
  public void setWebViewSize(Point paramPoint)
  {
    this.mWebviewSize = paramPoint;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setWebViewSize ");
    localStringBuilder.append(paramPoint.x);
    localStringBuilder.toString();
  }
  
  /* Error */
  public void startCollectorAsync(IX5WebView paramIX5WebView, final ValueCallback<Integer> paramValueCallback)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +4 -> 5
    //   4: return
    //   5: invokestatic 108	android/os/Looper:getMainLooper	()Landroid/os/Looper;
    //   8: invokestatic 111	android/os/Looper:myLooper	()Landroid/os/Looper;
    //   11: if_acmpne +33 -> 44
    //   14: aload_1
    //   15: invokeinterface 323 1 0
    //   20: astore_3
    //   21: aload_0
    //   22: aload_3
    //   23: invokevirtual 151	com/tencent/tbs/tbsshell/common/x5core/PageInfoCollector:setUrl	(Ljava/lang/String;)V
    //   26: aload_0
    //   27: aload_3
    //   28: invokestatic 157	com/tencent/common/utils/UrlUtils:getHost	(Ljava/lang/String;)Ljava/lang/String;
    //   31: invokevirtual 160	com/tencent/tbs/tbsshell/common/x5core/PageInfoCollector:setDomain	(Ljava/lang/String;)V
    //   34: aload_0
    //   35: aload_1
    //   36: invokeinterface 325 1 0
    //   41: invokevirtual 163	com/tencent/tbs/tbsshell/common/x5core/PageInfoCollector:setTitle	(Ljava/lang/String;)V
    //   44: getstatic 327	com/tencent/tbs/tbsshell/common/x5core/PageInfoCollector:mDomDistillerJS	Ljava/lang/String;
    //   47: ifnonnull +106 -> 153
    //   50: invokestatic 50	com/tencent/mtt/ContextHolder:getAppContext	()Landroid/content/Context;
    //   53: invokestatic 333	com/tencent/tbs/tbsshell/common/ad/TbsAdProxy:getInstance	(Landroid/content/Context;)Lcom/tencent/tbs/tbsshell/common/ad/TbsAdProxy;
    //   56: invokevirtual 337	com/tencent/tbs/tbsshell/common/ad/TbsAdProxy:getDomDistillerJS	()Ljava/io/InputStream;
    //   59: astore 5
    //   61: new 171	java/lang/StringBuilder
    //   64: dup
    //   65: invokespecial 172	java/lang/StringBuilder:<init>	()V
    //   68: astore_3
    //   69: aload_3
    //   70: ldc_w 339
    //   73: invokevirtual 178	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   76: pop
    //   77: aload_3
    //   78: aload 5
    //   80: invokevirtual 181	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   83: pop
    //   84: aload_3
    //   85: invokevirtual 184	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   88: pop
    //   89: aload 5
    //   91: ifnonnull +18 -> 109
    //   94: aload_2
    //   95: ifnull +13 -> 108
    //   98: aload_2
    //   99: iconst_m1
    //   100: invokestatic 345	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   103: invokeinterface 351 2 0
    //   108: return
    //   109: aconst_null
    //   110: astore_3
    //   111: aload 5
    //   113: ldc_w 353
    //   116: invokestatic 358	com/tencent/tbs/tbsshell/common/fingersearch/FileUtils:toString	(Ljava/io/InputStream;Ljava/lang/String;)Ljava/lang/String;
    //   119: astore 4
    //   121: aload 4
    //   123: astore_3
    //   124: aload 5
    //   126: invokevirtual 363	java/io/InputStream:close	()V
    //   129: aload_3
    //   130: ifnonnull +19 -> 149
    //   133: aload_2
    //   134: ifnull +14 -> 148
    //   137: aload_2
    //   138: bipush -2
    //   140: invokestatic 345	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   143: invokeinterface 351 2 0
    //   148: return
    //   149: aload_3
    //   150: putstatic 327	com/tencent/tbs/tbsshell/common/x5core/PageInfoCollector:mDomDistillerJS	Ljava/lang/String;
    //   153: aload_0
    //   154: aload_1
    //   155: getstatic 327	com/tencent/tbs/tbsshell/common/x5core/PageInfoCollector:mDomDistillerJS	Ljava/lang/String;
    //   158: new 10	com/tencent/tbs/tbsshell/common/x5core/PageInfoCollector$2
    //   161: dup
    //   162: aload_0
    //   163: aload_2
    //   164: invokespecial 366	com/tencent/tbs/tbsshell/common/x5core/PageInfoCollector$2:<init>	(Lcom/tencent/tbs/tbsshell/common/x5core/PageInfoCollector;Landroid/webkit/ValueCallback;)V
    //   167: invokespecial 368	com/tencent/tbs/tbsshell/common/x5core/PageInfoCollector:evaluateJavascript	(Lcom/tencent/smtt/export/internal/interfaces/IX5WebView;Ljava/lang/String;Landroid/webkit/ValueCallback;)V
    //   170: return
    //   171: astore 4
    //   173: goto -49 -> 124
    //   176: astore 4
    //   178: goto -49 -> 129
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	181	0	this	PageInfoCollector
    //   0	181	1	paramIX5WebView	IX5WebView
    //   0	181	2	paramValueCallback	ValueCallback<Integer>
    //   20	130	3	localObject	Object
    //   119	3	4	str	String
    //   171	1	4	localOutOfMemoryError	OutOfMemoryError
    //   176	1	4	localIOException	java.io.IOException
    //   59	66	5	localInputStream	java.io.InputStream
    // Exception table:
    //   from	to	target	type
    //   111	121	171	java/lang/OutOfMemoryError
    //   124	129	176	java/io/IOException
  }
  
  public void updateFiled()
  {
    if (TextUtils.isEmpty(this.domain)) {
      this.domain = UrlUtils.getHost(this.url);
    }
  }
  
  public static class ImageInfo
  {
    Point size;
    String url;
    
    public ImageInfo(String paramString, Point paramPoint)
    {
      this.url = paramString;
      this.size = paramPoint;
    }
    
    public static JSONArray List2JSONArray(List<ImageInfo> paramList)
    {
      JSONArray localJSONArray = new JSONArray();
      if (paramList == null) {
        return localJSONArray;
      }
      try
      {
        paramList = paramList.iterator();
        while (paramList.hasNext()) {
          localJSONArray.put(((ImageInfo)paramList.next()).toJSONObject());
        }
        return localJSONArray;
      }
      catch (Exception paramList) {}
      return localJSONArray;
    }
    
    public JSONObject toJSONObject()
    {
      JSONObject localJSONObject = new JSONObject();
      try
      {
        localJSONObject.put("url", this.url);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(this.size.x);
        localStringBuilder.append(",");
        localStringBuilder.append(this.size.y);
        localJSONObject.put("size", localStringBuilder.toString());
        return localJSONObject;
      }
      catch (Exception localException)
      {
        for (;;) {}
      }
      return null;
    }
  }
  
  public static class VideoInfo
  {
    Point size;
    String tag;
    String url;
    
    public VideoInfo(String paramString, Point paramPoint)
    {
      this.url = paramString;
      this.size = paramPoint;
    }
    
    public VideoInfo(String paramString1, Point paramPoint, String paramString2)
    {
      this.url = paramString1;
      this.size = paramPoint;
      this.tag = paramString2;
    }
    
    public static JSONArray List2JSONArray(List<VideoInfo> paramList)
    {
      JSONArray localJSONArray = new JSONArray();
      if (paramList == null) {
        return localJSONArray;
      }
      try
      {
        paramList = paramList.iterator();
        while (paramList.hasNext()) {
          localJSONArray.put(((VideoInfo)paramList.next()).toJSONObject());
        }
        return localJSONArray;
      }
      catch (Exception paramList) {}
      return localJSONArray;
    }
    
    public JSONObject toJSONObject()
    {
      JSONObject localJSONObject = new JSONObject();
      try
      {
        localJSONObject.put("url", this.url);
        localJSONObject.put("tag", this.tag);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(this.size.x);
        localStringBuilder.append(",");
        localStringBuilder.append(this.size.y);
        localJSONObject.put("size", localStringBuilder.toString());
        return localJSONObject;
      }
      catch (Exception localException)
      {
        for (;;) {}
      }
      return null;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\x5core\PageInfoCollector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */