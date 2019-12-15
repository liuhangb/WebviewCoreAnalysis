package com.tencent.common.http;

import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.net.ssl.HostnameVerifier;

public class MttRequestBase
{
  private static final String ACCEPT = "application/vnd.wap.xhtml+xml,application/xml,text/vnd.wap.wml,text/html,application/xhtml+xml,image/jpeg;q=0.5,image/png;q=0.5,image/gif;q=0.5,image/*;q=0.6,video/*,audio/*,*/*;q=0.6";
  private static final String ACCEPT_ENCODING = "gzip";
  public static final String CONNECTION = "Close";
  public static final byte HTTPS_TRUST_ALL = 1;
  public static final byte HTTPS_TRUST_DEFAULT = 0;
  public static final byte HTTPS_TRUST_NOT = 2;
  public static final byte HTTPS_VER_STRATEGY_DEFAULT = 0;
  public static final byte HTTPS_VER_STRATEGY_MAXHIGH = 1;
  public static final byte METHOD_DELETE = 5;
  public static final byte METHOD_GET = 0;
  public static final byte METHOD_HEAD = 2;
  public static final String METHOD_NAME_DELETE = "DELETE";
  public static final String METHOD_NAME_GET = "GET";
  public static final String METHOD_NAME_HEAD = "HEAD";
  public static final String METHOD_NAME_OPTIONS = "OPTIONS";
  public static final String METHOD_NAME_POST = "POST";
  public static final String METHOD_NAME_PUT = "PUT";
  public static final String METHOD_NAME_TRACE = "TRACE";
  public static final byte METHOD_OPTIONS = 3;
  public static final byte METHOD_POST = 1;
  public static final byte METHOD_PUT = 4;
  public static final byte METHOD_TRACE = 6;
  public static final byte NETWORK_STATUS_CONNECTED = 2;
  public static final byte NETWORK_STATUS_CREATED = 1;
  public static final byte NETWORK_STATUS_RECEIVED = 4;
  public static final byte NETWORK_STATUS_SENDED = 3;
  public static final byte NETWORK_STATUS_UNKNOW = -1;
  public static final byte REQUEST_APK_DETECT = 114;
  public static final byte REQUEST_DIRECT = 102;
  public static final byte REQUEST_DNS = 118;
  public static final byte REQUEST_FEEDS_REPORT = 108;
  public static final byte REQUEST_FILE_DOWNLOAD = 104;
  public static final byte REQUEST_HTTP_COM = 112;
  public static final byte REQUEST_HTTP_UPLOAD = 113;
  public static final byte REQUEST_MUSIC = 107;
  public static final byte REQUEST_NORMAL = 101;
  public static final byte REQUEST_NOVEL = 111;
  public static final byte REQUEST_PICTURE = 106;
  public static final byte REQUEST_SEARCH = 110;
  public static final byte REQUEST_SYS_MEDIA = 119;
  public static final byte REQUEST_URL_CONNECTION = 103;
  public static final byte REQUEST_VIDEO_CACHE = 115;
  public static final byte REQUEST_VIDEO_DOWNLOAD = 116;
  public static final byte REQUEST_VIDEO_REPORT = 109;
  public static final byte REQUEST_WUP = 105;
  public static final byte REQUEST_X5_AUDIO = 120;
  private static String USER_AGENT;
  private boolean directionalEnable = true;
  private boolean mCheckCache = false;
  int mConnectTimeout = -1;
  private String mCookie;
  protected IHttpCookieManager mCookieManager = null;
  protected String mDirectionalUrl = null;
  private int mDownFlow = 0;
  public boolean mForceNoReferer = false;
  private HashMap<String, String> mHeaderMap = new HashMap();
  private HostnameVerifier mHostVerifier = null;
  private int mHttpsVerStrategy = 0;
  private boolean mInstanceFollowRedirects = false;
  protected String mIp = null;
  private boolean mIsBackgroudRequest = false;
  private boolean mIsDisableSSLV3 = false;
  private boolean mIsProxyDisable = false;
  boolean mIsQueenFlow = false;
  boolean mIsQueenProxyEnable = true;
  private boolean mIsWupRequest = false;
  private byte mMethod;
  private boolean mNeedCache = true;
  private boolean mNeedRefresh = false;
  public byte mNetworkStatus;
  private IPostDataBuf mPostData;
  private int mPostFlow = 0;
  QueenConfig.QueenConfigInfo mQueenConfig = null;
  int mQueenErrorCode = -1;
  int mQueenStatusCode = -1;
  int mReadTimeout = -1;
  protected String mRedirectUrl = null;
  private String mReferer;
  private IRequestInterceptor mRequestInterceptor;
  private HashMap<String, String> mRequestProperty;
  private byte mRequestType = 101;
  private String mTag = "";
  protected String mTargetUrl = null;
  private int mTrustCertificatesType = 0;
  protected String mUrl;
  private boolean mUseCaches = true;
  private boolean mUseWapProxy = false;
  private String mUserAgent;
  
  public MttRequestBase()
  {
    initUserAgent();
    addHeader("Accept", "application/vnd.wap.xhtml+xml,application/xml,text/vnd.wap.wml,text/html,application/xhtml+xml,image/jpeg;q=0.5,image/png;q=0.5,image/gif;q=0.5,image/*;q=0.6,video/*,audio/*,*/*;q=0.6");
    addHeader("Accept-Encoding", "gzip");
    String str = USER_AGENT;
    if (str != null)
    {
      addHeader("User-Agent", str);
      return;
    }
    System.err.println("user_agent is null!");
  }
  
  public static String getDefaultUserAgent()
  {
    if (USER_AGENT == null) {
      initUserAgent();
    }
    return USER_AGENT;
  }
  
  public static String getRequestTypeName(int paramInt)
  {
    switch (paramInt)
    {
    case 117: 
    default: 
      return "default";
    case 120: 
      return "x5audio";
    case 119: 
      return "sysmedia";
    case 118: 
      return "dns";
    case 116: 
      return "videodownload";
    case 115: 
      return "videocache";
    case 114: 
      return "apkdetect";
    case 113: 
      return "httpupload";
    case 112: 
      return "httpcom";
    case 111: 
      return "novel";
    case 110: 
      return "search";
    case 109: 
      return "videoreport";
    case 108: 
      return "feedsreport";
    case 107: 
      return "music";
    case 106: 
      return "picture";
    case 105: 
      return "wup";
    case 104: 
      return "download";
    case 103: 
      return "urlConnection";
    }
    return "directrequest";
  }
  
  private static void initUserAgent()
  {
    if (USER_AGENT == null)
    {
      Object localObject = Locale.getDefault();
      StringBuffer localStringBuffer = new StringBuffer();
      String str = Build.VERSION.RELEASE;
      if (!TextUtils.isEmpty(str)) {
        localStringBuffer.append(str);
      } else {
        localStringBuffer.append("1.0");
      }
      localStringBuffer.append("; ");
      str = ((Locale)localObject).getLanguage();
      if (str != null)
      {
        localStringBuffer.append(str.toLowerCase());
        localObject = ((Locale)localObject).getCountry();
        if (localObject != null)
        {
          localStringBuffer.append("-");
          localStringBuffer.append(((String)localObject).toLowerCase());
        }
      }
      else
      {
        localStringBuffer.append("en");
      }
      if ((Build.VERSION.SDK_INT > 3) && ("REL".equals(Build.VERSION.CODENAME)))
      {
        localObject = Build.MODEL;
        if (!TextUtils.isEmpty((CharSequence)localObject))
        {
          localStringBuffer.append("; ");
          localStringBuffer.append((String)localObject);
        }
      }
      localObject = Build.ID;
      if (!TextUtils.isEmpty((CharSequence)localObject))
      {
        localStringBuffer.append(" Build/");
        localStringBuffer.append((String)localObject);
      }
      USER_AGENT = String.format("Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko) Mobile Safari/533.1", new Object[] { localStringBuffer });
    }
  }
  
  public void addHeader(String paramString1, String paramString2)
  {
    this.mHeaderMap.put(paramString1, paramString2);
    this.mPostFlow += paramString2.length();
    this.mPostFlow += paramString1.length();
  }
  
  public void addHeaders(Map<String, String> paramMap)
  {
    if (paramMap != null)
    {
      if (paramMap.size() == 0) {
        return;
      }
      paramMap = paramMap.entrySet().iterator();
      while (paramMap.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramMap.next();
        addHeader((String)localEntry.getKey(), (String)localEntry.getValue());
      }
      return;
    }
  }
  
  public void addHeaders(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (getRequestType() == 104) {
      addHeader("Accept-Encoding", "identity");
    }
    if ((!TextUtils.isEmpty(getReferer())) && (!paramBoolean1)) {
      addHeader("Referer", getReferer());
    } else if (this.mForceNoReferer) {
      addHeader("Referer", this.mUrl.toString());
    }
    if (paramBoolean2) {
      fillCookies();
    }
    if (paramBoolean3) {
      fillQHeaders();
    }
  }
  
  void doHeaderIntercept()
  {
    IRequestInterceptor localIRequestInterceptor = this.mRequestInterceptor;
    if (localIRequestInterceptor != null) {
      localIRequestInterceptor.onHeaderIntercept(this, getHeaders());
    }
  }
  
  protected void fillCookies()
  {
    if (!TextUtils.isEmpty(this.mCookie)) {
      addHeader("Cookie", this.mCookie);
    }
  }
  
  protected void fillQHeaders() {}
  
  public String getCookie()
  {
    return this.mCookie;
  }
  
  public int getDownFlow()
  {
    return this.mDownFlow;
  }
  
  public String getExecuteUrl()
  {
    if (this.directionalEnable) {
      localObject2 = this.mDirectionalUrl;
    } else {
      localObject2 = null;
    }
    Object localObject1 = localObject2;
    if (TextUtils.isEmpty((CharSequence)localObject2)) {
      localObject1 = getTargetUrl();
    }
    Object localObject2 = localObject1;
    if (TextUtils.isEmpty((CharSequence)localObject1)) {
      localObject2 = getRedirectUrl();
    }
    localObject1 = localObject2;
    if (TextUtils.isEmpty((CharSequence)localObject2)) {
      localObject1 = getUrl();
    }
    return (String)localObject1;
  }
  
  public int getFlow()
  {
    return this.mPostFlow;
  }
  
  public String getHeader(String paramString)
  {
    return (String)this.mHeaderMap.get(paramString);
  }
  
  public Map<String, String> getHeaders()
  {
    return this.mHeaderMap;
  }
  
  public HostnameVerifier getHostVerifier()
  {
    return this.mHostVerifier;
  }
  
  public int getHttpsVerStrategy()
  {
    return this.mHttpsVerStrategy;
  }
  
  public String getIp()
  {
    return this.mIp;
  }
  
  public boolean getIsBackGroudRequest()
  {
    return this.mIsBackgroudRequest;
  }
  
  public boolean getIsDisableSSLV3()
  {
    return this.mIsDisableSSLV3;
  }
  
  public boolean getIsWupRequest()
  {
    return this.mIsWupRequest;
  }
  
  public byte getMethod()
  {
    return this.mMethod;
  }
  
  public String getMethodName()
  {
    int i = this.mMethod;
    if (i == 1) {
      return "POST";
    }
    if (i == 2) {
      return "HEAD";
    }
    if (i == 3) {
      return "OPTIONS";
    }
    if (i == 4) {
      return "PUT";
    }
    if (i == 5) {
      return "DELETE";
    }
    if (i == 6) {
      return "TRACE";
    }
    return "GET";
  }
  
  public IPostDataBuf getPostData()
  {
    return this.mPostData;
  }
  
  public QueenConfig.QueenConfigInfo getQueenConfig()
  {
    return this.mQueenConfig;
  }
  
  public int getQueenErrorCode()
  {
    return this.mQueenErrorCode;
  }
  
  public int getQueenStatusCode()
  {
    return this.mQueenStatusCode;
  }
  
  public String getRedirectUrl()
  {
    return this.mRedirectUrl;
  }
  
  public String getReferer()
  {
    return this.mReferer;
  }
  
  public HashMap<String, String> getRequestProperty()
  {
    return this.mRequestProperty;
  }
  
  public byte getRequestType()
  {
    return this.mRequestType;
  }
  
  public String getTag()
  {
    return this.mTag;
  }
  
  public String getTargetUrl()
  {
    return this.mTargetUrl;
  }
  
  public int getTrustCertificatesType()
  {
    return this.mTrustCertificatesType;
  }
  
  public String getUrl()
  {
    return this.mUrl;
  }
  
  public boolean getUseWapProxy()
  {
    return this.mUseWapProxy;
  }
  
  public String getUserAgent()
  {
    if (TextUtils.isEmpty(this.mUserAgent)) {
      return USER_AGENT;
    }
    return this.mUserAgent;
  }
  
  public boolean hasHeader(String paramString)
  {
    return this.mHeaderMap.containsKey(paramString);
  }
  
  public boolean isCheckCache()
  {
    return this.mCheckCache;
  }
  
  public boolean isDirectionalEnable()
  {
    return this.directionalEnable;
  }
  
  public boolean isInstanceFollowRedirects()
  {
    return this.mInstanceFollowRedirects;
  }
  
  public boolean isNeedCache()
  {
    return this.mNeedCache;
  }
  
  public boolean isNeedRefresh()
  {
    return this.mNeedRefresh;
  }
  
  public boolean isPostEnable()
  {
    IPostDataBuf localIPostDataBuf = this.mPostData;
    boolean bool = true;
    if (localIPostDataBuf != null)
    {
      int i = this.mMethod;
      if (i == 1) {
        return bool;
      }
      if (i == 4) {
        return true;
      }
    }
    bool = false;
    return bool;
  }
  
  public boolean isProxyDisable()
  {
    return this.mIsProxyDisable;
  }
  
  public boolean isQueenFlow()
  {
    return this.mIsQueenFlow;
  }
  
  public boolean isQueenProxyEnable()
  {
    return this.mIsQueenProxyEnable;
  }
  
  public boolean isUseCaches()
  {
    return this.mUseCaches;
  }
  
  public String key()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.mUrl);
    localStringBuilder.append(hashCode());
    return localStringBuilder.toString();
  }
  
  public void preparePerform()
  {
    this.mTargetUrl = null;
    this.mIp = null;
    this.mIsQueenFlow = false;
    this.mQueenConfig = null;
  }
  
  public void removeHeader(String paramString)
  {
    this.mHeaderMap.remove(paramString);
  }
  
  public void replaceHeader(String paramString1, String paramString2)
  {
    if (!TextUtils.isEmpty(paramString1))
    {
      if (TextUtils.isEmpty(paramString2)) {
        return;
      }
      this.mHeaderMap.remove(paramString1);
      this.mHeaderMap.put(paramString1, paramString2);
      return;
    }
  }
  
  public void setCheckCache(boolean paramBoolean)
  {
    this.mCheckCache = paramBoolean;
  }
  
  public void setConnectTimeout(int paramInt)
  {
    this.mConnectTimeout = paramInt;
  }
  
  public void setCookie(String paramString)
  {
    this.mCookie = paramString;
  }
  
  public void setCookieManager(IHttpCookieManager paramIHttpCookieManager)
  {
    this.mCookieManager = paramIHttpCookieManager;
  }
  
  public void setDirectionalEnable(boolean paramBoolean)
  {
    this.directionalEnable = paramBoolean;
  }
  
  public void setDirectionalUrl(String paramString)
  {
    this.mDirectionalUrl = paramString;
  }
  
  public void setDownFlow(int paramInt)
  {
    this.mDownFlow = paramInt;
  }
  
  public void setHostVerifier(HostnameVerifier paramHostnameVerifier)
  {
    this.mHostVerifier = paramHostnameVerifier;
  }
  
  public void setHttpsVerStrategy(int paramInt)
  {
    this.mHttpsVerStrategy = paramInt;
  }
  
  public void setInstanceFollowRedirects(boolean paramBoolean)
  {
    this.mInstanceFollowRedirects = paramBoolean;
  }
  
  public void setIp(String paramString)
  {
    this.mIp = paramString;
  }
  
  public void setIsBackgroudRequest(boolean paramBoolean)
  {
    this.mIsBackgroudRequest = paramBoolean;
  }
  
  public void setIsDisableSSLV3(boolean paramBoolean)
  {
    this.mIsDisableSSLV3 = paramBoolean;
  }
  
  public void setIsQueenFlow(boolean paramBoolean)
  {
    this.mIsQueenFlow = paramBoolean;
  }
  
  public void setIsWupRequest(boolean paramBoolean)
  {
    this.mIsWupRequest = paramBoolean;
  }
  
  public void setMethod(byte paramByte)
  {
    this.mMethod = paramByte;
  }
  
  public void setNeedCache(boolean paramBoolean)
  {
    this.mNeedCache = paramBoolean;
  }
  
  public void setNeedRefresh(boolean paramBoolean)
  {
    this.mNeedRefresh = paramBoolean;
  }
  
  public void setPostData(IPostDataBuf paramIPostDataBuf)
  {
    if (paramIPostDataBuf == null) {
      return;
    }
    this.mPostFlow = paramIPostDataBuf.getLen();
    this.mPostData = paramIPostDataBuf;
  }
  
  public void setPostData(String paramString)
  {
    setPostData(paramString, null);
  }
  
  public void setPostData(String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1)) {
      return;
    }
    if (paramString2 == null) {}
    try
    {
      paramString2 = paramString1.getBytes();
      break label26;
      paramString2 = paramString1.getBytes(paramString2);
      label26:
      this.mPostFlow = paramString2.length;
      setPostData(paramString2);
      return;
    }
    catch (UnsupportedEncodingException paramString2)
    {
      setPostData(paramString1.getBytes());
      paramString2.printStackTrace();
    }
  }
  
  public void setPostData(byte[] paramArrayOfByte)
  {
    this.mPostData = PostDataBuilder.create();
    this.mPostData.setPostData(paramArrayOfByte);
    if (paramArrayOfByte != null) {
      this.mPostFlow = paramArrayOfByte.length;
    }
  }
  
  public void setProxyDisable(boolean paramBoolean)
  {
    this.mIsProxyDisable = paramBoolean;
  }
  
  public void setQueenConfig(QueenConfig.QueenConfigInfo paramQueenConfigInfo)
  {
    this.mQueenConfig = paramQueenConfigInfo;
  }
  
  public void setQueenErrorCode(int paramInt)
  {
    this.mQueenErrorCode = paramInt;
  }
  
  public void setQueenProxyEnable(boolean paramBoolean)
  {
    this.mIsQueenProxyEnable = paramBoolean;
    if (!paramBoolean) {
      setQueenConfig(null);
    }
  }
  
  public void setQueenStatusCode(int paramInt)
  {
    this.mQueenStatusCode = paramInt;
  }
  
  public void setReadTimeout(int paramInt)
  {
    this.mReadTimeout = paramInt;
  }
  
  public void setRedirectUrl(String paramString)
  {
    this.mRedirectUrl = paramString;
  }
  
  public void setReferer(String paramString)
  {
    this.mReferer = paramString;
  }
  
  public void setRequestInterceptor(IRequestInterceptor paramIRequestInterceptor)
  {
    this.mRequestInterceptor = paramIRequestInterceptor;
  }
  
  public void setRequestProperty(String paramString1, String paramString2)
  {
    if (this.mRequestProperty == null) {
      this.mRequestProperty = new HashMap();
    }
    this.mRequestProperty.put(paramString1, paramString2);
  }
  
  public void setRequestType(byte paramByte)
  {
    this.mRequestType = paramByte;
  }
  
  public void setTag(String paramString)
  {
    this.mTag = paramString;
  }
  
  public void setTargetUrl(String paramString)
  {
    this.mTargetUrl = paramString;
  }
  
  public void setTrustCertificatesType(int paramInt)
  {
    this.mTrustCertificatesType = paramInt;
  }
  
  public void setUrl(String paramString)
  {
    this.mUrl = paramString;
  }
  
  public void setUseCaches(boolean paramBoolean)
  {
    this.mUseCaches = paramBoolean;
  }
  
  public void setUseWapProxy(boolean paramBoolean)
  {
    this.mUseWapProxy = paramBoolean;
  }
  
  public void setUserAgent(String paramString)
  {
    this.mUserAgent = paramString;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    StringBuilder localStringBuilder2 = new StringBuilder();
    localStringBuilder2.append("Method : ");
    localStringBuilder2.append(getMethodName());
    localStringBuilder2.append("\n");
    localStringBuilder1.append(localStringBuilder2.toString());
    localStringBuilder2 = new StringBuilder();
    localStringBuilder2.append("NUrl : ");
    localStringBuilder2.append(getUrl());
    localStringBuilder2.append("\n");
    localStringBuilder1.append(localStringBuilder2.toString());
    localStringBuilder2 = new StringBuilder();
    localStringBuilder2.append("RequestType : ");
    localStringBuilder2.append(getRequestType());
    localStringBuilder2.append("\n");
    localStringBuilder1.append(localStringBuilder2.toString());
    return localStringBuilder1.toString();
  }
  
  public static abstract interface IRequestInterceptor
  {
    public abstract void onHeaderIntercept(MttRequestBase paramMttRequestBase, Map<String, String> paramMap);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\MttRequestBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */