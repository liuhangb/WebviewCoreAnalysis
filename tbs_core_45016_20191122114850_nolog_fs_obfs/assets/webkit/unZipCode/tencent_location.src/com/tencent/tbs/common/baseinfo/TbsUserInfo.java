package com.tencent.tbs.common.baseinfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.common.http.Apn;
import com.tencent.common.serverconfig.IPListDataManager;
import com.tencent.common.serverconfig.IPListUtils;
import com.tencent.common.serverconfig.WupServerConfigsWrapper;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.ThreadUtils;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.tbs.common.MTT.CRExtraAdRule;
import com.tencent.tbs.common.MTT.GetAdBlockSourceReq;
import com.tencent.tbs.common.MTT.GetAdBlockSourceRsp;
import com.tencent.tbs.common.MTT.GetAdFilterSourceReq;
import com.tencent.tbs.common.MTT.GetAdFilterSourceRsp;
import com.tencent.tbs.common.MTT.GetSubsetAdRuleReq;
import com.tencent.tbs.common.MTT.GetSubsetAdRuleRsp;
import com.tencent.tbs.common.MTT.JoinIPInfo;
import com.tencent.tbs.common.MTT.RouteIPListReq;
import com.tencent.tbs.common.MTT.RouteIPListRsp;
import com.tencent.tbs.common.MTT.SourceFile;
import com.tencent.tbs.common.MTT.TBSJSApiApiNames;
import com.tencent.tbs.common.MTT.TokenInfoReq;
import com.tencent.tbs.common.MTT.TokenInfoRsp;
import com.tencent.tbs.common.MTT.UserBase;
import com.tencent.tbs.common.MTT.jsTemplateReq;
import com.tencent.tbs.common.MTT.jsTemplateResp;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.utils.NetworkUtils;
import com.tencent.tbs.common.utils.TbsFileUtils;
import com.tencent.tbs.common.utils.TbsInfoUtils;
import com.tencent.tbs.common.wup.WUPRequest;
import com.tencent.tbs.common.wup.WUPTaskProxy;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.Vector;
import org.json.JSONException;
import org.json.JSONObject;

public class TbsUserInfo
  implements IWUPRequestCallBack
{
  public static final String CHANNEL_NAME = ".channel";
  public static final String FILE_CHANNEL = "channel.ini";
  private static final int GET_IPLIST_LONG_RETRY_SPAN = 150000;
  private static final int GET_IPLIST_SHORT_RETRY_SPAN = 30000;
  public static final String HTTP2_APN = "_http2";
  public static final String HTTPSTunnel_APN = "_httpsTunnel";
  public static final String KEY_CHANNEL = "CHANNEL";
  private static final int MSG_SEND_IPLIST_REQ = 1;
  private static long PreRefreshTokenTime = 1800L;
  public static final String QUIC_APN = "_quic";
  private static final String TAG = "TesUserInfo";
  private static long TokenRefreshInterval = 120L;
  private static final int WALLED_GARDEN_SOCKET_TIMEOUT_MS = 4000;
  private static volatile TbsUserInfo mInstance;
  private static int mIpListFetchNumber;
  private static Map<String, TBSJSApiApiNames> mJSAPIWhiteList;
  public static String mJsApiSAuth;
  public final int DC_URL_RSP = 0;
  private final long MIN_IP_LIST_REQUEST_SPAN_NANO = 15000000000L;
  public final int OC_URL_RSP = 1;
  private String currentApn = "";
  public boolean initiated = false;
  private int ipv6SwitchNum = 0;
  public StringBuffer livelogMsg = new StringBuffer();
  public String mAdBlockSourceMD5;
  public String mAdJSMD5;
  private int mDomainTime = 0;
  public String mHttpsTunnelToken = null;
  public long mHttpsTunnelTokenExpireTime = -1L;
  private Handler mIplistHandler = new IplistHandler();
  public int mIsOCProxy = 0;
  public String mJsTemplate;
  public String mJsTemplateMD5;
  private int mLastIplistApn = Apn.getApnTypeS();
  private long mLastIplistTime = 0L;
  private long mLastRefreshToken = -1L;
  public int mMD5Value;
  private int mMaxIpv6Switch = 5;
  public int mMaxReportNumber;
  public String mModuleMD5;
  protected String mOriChannelID = "";
  private Map<String, Integer> mProtocolFlag = new HashMap();
  private Object mProtocolFlagLock = new byte[1];
  private int mProxyType = 1;
  private String mQAuth;
  public Hashtable<String, ProxyInfo> mQProxyList;
  private IServiceManager mServiceManager = null;
  public ArrayList<String> mSocketServerList;
  private int mStatState = 0;
  public String mSubsetAdRuleMD5;
  private ArrayList<String> mWupPushProxyList;
  private boolean mcanUseIPV6 = false;
  private StringBuffer userinfoMsg = new StringBuffer();
  
  private TbsUserInfo()
  {
    init();
  }
  
  private String apnTypeToName(int paramInt)
  {
    Object localObject2 = getApn(paramInt);
    paramInt = getDebugProxyType();
    Object localObject1;
    if ((paramInt >= 1) && (paramInt <= 4))
    {
      if (paramInt != 1)
      {
        localObject1 = localObject2;
        if (paramInt != 3) {}
      }
      else
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append((String)localObject2);
        ((StringBuilder)localObject1).append("_quic");
        localObject1 = ((StringBuilder)localObject1).toString();
      }
      if (paramInt != 2)
      {
        localObject2 = localObject1;
        if (paramInt != 4) {}
      }
      else
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append("_http2");
        localObject2 = ((StringBuilder)localObject2).toString();
      }
      return (String)localObject2;
    }
    paramInt = getProxyAddressType();
    if (paramInt == 1)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_http2");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else
    {
      localObject1 = localObject2;
      if (paramInt == 2)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append((String)localObject2);
        ((StringBuilder)localObject1).append("_quic");
        localObject1 = ((StringBuilder)localObject1).toString();
      }
    }
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append(" TesUserInfo apnTypeToName apnName:");
    ((StringBuilder)localObject2).append((String)localObject1);
    LogUtils.d("x5-ip-list", ((StringBuilder)localObject2).toString());
    return (String)localObject1;
  }
  
  /* Error */
  private void fetchAdBlockSourceResource(String paramString)
  {
    // Byte code:
    //   0: new 205	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 206	java/lang/StringBuilder:<init>	()V
    //   7: astore_3
    //   8: aload_3
    //   9: ldc -21
    //   11: invokevirtual 210	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   14: pop
    //   15: aload_3
    //   16: aload_1
    //   17: invokevirtual 210	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   20: pop
    //   21: ldc -19
    //   23: aload_3
    //   24: invokevirtual 214	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   27: invokestatic 227	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   30: aload_1
    //   31: invokestatic 243	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   34: ifeq +4 -> 38
    //   37: return
    //   38: new 245	java/net/URL
    //   41: dup
    //   42: aload_1
    //   43: invokespecial 247	java/net/URL:<init>	(Ljava/lang/String;)V
    //   46: invokevirtual 251	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   49: checkcast 253	java/net/HttpURLConnection
    //   52: astore_1
    //   53: aload_1
    //   54: sipush 4000
    //   57: invokevirtual 257	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   60: aload_1
    //   61: sipush 4000
    //   64: invokevirtual 260	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   67: aload_1
    //   68: invokevirtual 263	java/net/HttpURLConnection:connect	()V
    //   71: aload_1
    //   72: invokevirtual 267	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   75: astore_3
    //   76: aload_1
    //   77: invokevirtual 270	java/net/HttpURLConnection:getResponseCode	()I
    //   80: pop
    //   81: new 272	java/io/FileOutputStream
    //   84: dup
    //   85: invokestatic 278	com/tencent/tbs/common/baseinfo/TbsBaseModuleShell:getCallerContext	()Landroid/content/Context;
    //   88: invokestatic 284	com/tencent/tbs/common/utils/TbsFileUtils:getAdBlockSourceFile	(Landroid/content/Context;)Ljava/io/File;
    //   91: invokespecial 287	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   94: astore 4
    //   96: ldc_w 288
    //   99: newarray <illegal type>
    //   101: astore 5
    //   103: aload_3
    //   104: aload 5
    //   106: invokevirtual 294	java/io/InputStream:read	([B)I
    //   109: istore_2
    //   110: iload_2
    //   111: iconst_m1
    //   112: if_icmpeq +15 -> 127
    //   115: aload 4
    //   117: aload 5
    //   119: iconst_0
    //   120: iload_2
    //   121: invokevirtual 298	java/io/FileOutputStream:write	([BII)V
    //   124: goto -21 -> 103
    //   127: aload 4
    //   129: invokevirtual 301	java/io/FileOutputStream:close	()V
    //   132: aload_1
    //   133: ifnull +56 -> 189
    //   136: goto +49 -> 185
    //   139: astore_3
    //   140: aload_1
    //   141: astore 4
    //   143: goto +15 -> 158
    //   146: goto +26 -> 172
    //   149: goto +32 -> 181
    //   152: astore_1
    //   153: aconst_null
    //   154: astore 4
    //   156: aload_1
    //   157: astore_3
    //   158: aload 4
    //   160: ifnull +8 -> 168
    //   163: aload 4
    //   165: invokevirtual 304	java/net/HttpURLConnection:disconnect	()V
    //   168: aload_3
    //   169: athrow
    //   170: aconst_null
    //   171: astore_1
    //   172: aload_1
    //   173: ifnull +16 -> 189
    //   176: goto +9 -> 185
    //   179: aconst_null
    //   180: astore_1
    //   181: aload_1
    //   182: ifnull +7 -> 189
    //   185: aload_1
    //   186: invokevirtual 304	java/net/HttpURLConnection:disconnect	()V
    //   189: return
    //   190: astore_1
    //   191: goto -12 -> 179
    //   194: astore_1
    //   195: goto -25 -> 170
    //   198: astore_3
    //   199: goto -50 -> 149
    //   202: astore_3
    //   203: goto -57 -> 146
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	206	0	this	TbsUserInfo
    //   0	206	1	paramString	String
    //   109	12	2	i	int
    //   7	97	3	localObject1	Object
    //   139	1	3	localObject2	Object
    //   157	12	3	str	String
    //   198	1	3	localException	Exception
    //   202	1	3	localOutOfMemoryError	OutOfMemoryError
    //   94	70	4	localObject3	Object
    //   101	17	5	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   53	103	139	finally
    //   103	110	139	finally
    //   115	124	139	finally
    //   127	132	139	finally
    //   38	53	152	finally
    //   38	53	190	java/lang/Exception
    //   38	53	194	java/lang/OutOfMemoryError
    //   53	103	198	java/lang/Exception
    //   103	110	198	java/lang/Exception
    //   115	124	198	java/lang/Exception
    //   127	132	198	java/lang/Exception
    //   53	103	202	java/lang/OutOfMemoryError
    //   103	110	202	java/lang/OutOfMemoryError
    //   115	124	202	java/lang/OutOfMemoryError
    //   127	132	202	java/lang/OutOfMemoryError
  }
  
  /* Error */
  private void fetchSmartAdFitlerResource(int paramInt, String paramString)
  {
    // Byte code:
    //   0: new 205	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 206	java/lang/StringBuilder:<init>	()V
    //   7: astore_3
    //   8: aload_3
    //   9: ldc_w 308
    //   12: invokevirtual 210	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   15: pop
    //   16: aload_3
    //   17: aload_2
    //   18: invokevirtual 210	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   21: pop
    //   22: ldc -19
    //   24: aload_3
    //   25: invokevirtual 214	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   28: invokestatic 227	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   31: aload_2
    //   32: invokestatic 243	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   35: ifeq +4 -> 39
    //   38: return
    //   39: new 245	java/net/URL
    //   42: dup
    //   43: aload_2
    //   44: invokespecial 247	java/net/URL:<init>	(Ljava/lang/String;)V
    //   47: invokevirtual 251	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   50: checkcast 253	java/net/HttpURLConnection
    //   53: astore_2
    //   54: aload_2
    //   55: sipush 4000
    //   58: invokevirtual 257	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   61: aload_2
    //   62: sipush 4000
    //   65: invokevirtual 260	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   68: aload_2
    //   69: invokevirtual 263	java/net/HttpURLConnection:connect	()V
    //   72: aload_2
    //   73: invokevirtual 267	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   76: astore_3
    //   77: aload_2
    //   78: invokevirtual 270	java/net/HttpURLConnection:getResponseCode	()I
    //   81: pop
    //   82: new 310	java/io/BufferedReader
    //   85: dup
    //   86: new 312	java/io/InputStreamReader
    //   89: dup
    //   90: aload_3
    //   91: invokespecial 315	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   94: invokespecial 318	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   97: astore_3
    //   98: new 148	java/lang/StringBuffer
    //   101: dup
    //   102: invokespecial 149	java/lang/StringBuffer:<init>	()V
    //   105: astore 4
    //   107: aload_3
    //   108: invokevirtual 321	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   111: astore 5
    //   113: aload 5
    //   115: ifnull +23 -> 138
    //   118: aload 4
    //   120: aload 5
    //   122: invokevirtual 324	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   125: pop
    //   126: aload 4
    //   128: ldc_w 326
    //   131: invokevirtual 324	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   134: pop
    //   135: goto -28 -> 107
    //   138: aload 4
    //   140: invokevirtual 327	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   143: astore 4
    //   145: iload_1
    //   146: iconst_1
    //   147: if_icmpne +13 -> 160
    //   150: invokestatic 278	com/tencent/tbs/common/baseinfo/TbsBaseModuleShell:getCallerContext	()Landroid/content/Context;
    //   153: invokestatic 330	com/tencent/tbs/common/utils/TbsFileUtils:getSAFMFile	(Landroid/content/Context;)Ljava/io/File;
    //   156: astore_3
    //   157: goto +10 -> 167
    //   160: invokestatic 278	com/tencent/tbs/common/baseinfo/TbsBaseModuleShell:getCallerContext	()Landroid/content/Context;
    //   163: invokestatic 333	com/tencent/tbs/common/utils/TbsFileUtils:getSAFJFile	(Landroid/content/Context;)Ljava/io/File;
    //   166: astore_3
    //   167: aload_0
    //   168: aload_3
    //   169: aload 4
    //   171: invokespecial 337	com/tencent/tbs/common/baseinfo/TbsUserInfo:saveRspData	(Ljava/io/File;Ljava/lang/String;)V
    //   174: aload_2
    //   175: ifnull +48 -> 223
    //   178: goto +41 -> 219
    //   181: astore_3
    //   182: goto +12 -> 194
    //   185: goto +21 -> 206
    //   188: goto +27 -> 215
    //   191: astore_3
    //   192: aconst_null
    //   193: astore_2
    //   194: aload_2
    //   195: ifnull +7 -> 202
    //   198: aload_2
    //   199: invokevirtual 304	java/net/HttpURLConnection:disconnect	()V
    //   202: aload_3
    //   203: athrow
    //   204: aconst_null
    //   205: astore_2
    //   206: aload_2
    //   207: ifnull +16 -> 223
    //   210: goto +9 -> 219
    //   213: aconst_null
    //   214: astore_2
    //   215: aload_2
    //   216: ifnull +7 -> 223
    //   219: aload_2
    //   220: invokevirtual 304	java/net/HttpURLConnection:disconnect	()V
    //   223: return
    //   224: astore_2
    //   225: goto -12 -> 213
    //   228: astore_2
    //   229: goto -25 -> 204
    //   232: astore_3
    //   233: goto -45 -> 188
    //   236: astore_3
    //   237: goto -52 -> 185
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	240	0	this	TbsUserInfo
    //   0	240	1	paramInt	int
    //   0	240	2	paramString	String
    //   7	162	3	localObject1	Object
    //   181	1	3	localObject2	Object
    //   191	12	3	localObject3	Object
    //   232	1	3	localException	Exception
    //   236	1	3	localOutOfMemoryError	OutOfMemoryError
    //   105	65	4	localObject4	Object
    //   111	10	5	str	String
    // Exception table:
    //   from	to	target	type
    //   54	107	181	finally
    //   107	113	181	finally
    //   118	135	181	finally
    //   138	145	181	finally
    //   150	157	181	finally
    //   160	167	181	finally
    //   167	174	181	finally
    //   39	54	191	finally
    //   39	54	224	java/lang/Exception
    //   39	54	228	java/lang/OutOfMemoryError
    //   54	107	232	java/lang/Exception
    //   107	113	232	java/lang/Exception
    //   118	135	232	java/lang/Exception
    //   138	145	232	java/lang/Exception
    //   150	157	232	java/lang/Exception
    //   160	167	232	java/lang/Exception
    //   167	174	232	java/lang/Exception
    //   54	107	236	java/lang/OutOfMemoryError
    //   107	113	236	java/lang/OutOfMemoryError
    //   118	135	236	java/lang/OutOfMemoryError
    //   138	145	236	java/lang/OutOfMemoryError
    //   150	157	236	java/lang/OutOfMemoryError
    //   160	167	236	java/lang/OutOfMemoryError
    //   167	174	236	java/lang/OutOfMemoryError
  }
  
  public static String getAppBroadcastQbDomain()
  {
    Object localObject = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(48);
    if (!((ArrayList)localObject).isEmpty())
    {
      localObject = (String)((ArrayList)localObject).get(0);
      if (!TextUtils.isEmpty((CharSequence)localObject)) {
        return ((String)localObject).trim();
      }
    }
    return "";
  }
  
  public static String getCurrentChannelID()
  {
    return null;
  }
  
  private int getDebugProxyType()
  {
    return TbsBaseModuleShell.getCallerContext().getSharedPreferences("x5_proxy_setting", 0).getInt("connect_status_new", 0);
  }
  
  public static TbsUserInfo getInstance()
  {
    if (mInstance == null) {
      try
      {
        if (mInstance == null) {
          mInstance = new TbsUserInfo();
        }
      }
      finally {}
    }
    return mInstance;
  }
  
  public static String getShareQbDomain()
  {
    Object localObject = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(46);
    if (!((ArrayList)localObject).isEmpty())
    {
      localObject = (String)((ArrayList)localObject).get(0);
      if (!TextUtils.isEmpty((CharSequence)localObject)) {
        return ((String)localObject).trim();
      }
    }
    return "";
  }
  
  private static long hourToMillis(int paramInt)
  {
    return paramInt * 60L * 60L * 1000L;
  }
  
  private boolean loadDomainInfo(byte paramByte)
  {
    return TbsDomainListDataProvider.getInstance(paramByte).addDomainList(TbsDomainListDataProvider.getInstance(paramByte).loadJsonObject());
  }
  
  private boolean loadUserInfoData()
  {
    Object localObject1 = TbsUserInfoDataProvider.getInstance().getUserInfoData(1003);
    Object localObject4;
    Object localObject7;
    Object localObject9;
    Object localObject8;
    if (localObject1 != null) {
      try
      {
        localObject1 = new JSONObject(localObject1.toString());
        localObject4 = ((JSONObject)localObject1).keys();
        if (((Iterator)localObject4).hasNext()) {
          this.mQProxyList.clear();
        }
        while (((Iterator)localObject4).hasNext())
        {
          localObject7 = (String)((Iterator)localObject4).next();
          localObject9 = ((JSONObject)localObject1).getJSONObject((String)localObject7);
          localObject8 = new ProxyInfo(null);
          ((ProxyInfo)localObject8).JsonToProxyInfo((JSONObject)localObject9);
          localObject9 = new StringBuilder();
          ((StringBuilder)localObject9).append("loadUserInfoData mQProxyList key=");
          ((StringBuilder)localObject9).append((String)localObject7);
          ((StringBuilder)localObject9).append(" proxyInfo=");
          ((StringBuilder)localObject9).append(localObject8);
          LogUtils.d("TheSimCardOfGreatKing", ((StringBuilder)localObject9).toString());
          this.mQProxyList.put(localObject7, localObject8);
        }
        localObject4 = TbsUserInfoDataProvider.getInstance().getUserInfoData(1028);
      }
      catch (JSONException localJSONException)
      {
        LogUtils.d("TesUserInfo", Log.getStackTraceString(localJSONException));
      }
    }
    if (localObject4 != null) {
      try
      {
        synchronized (this.mProtocolFlagLock)
        {
          this.mProtocolFlag.clear();
          localObject4 = new JSONObject(localObject4.toString());
          localObject7 = ((JSONObject)localObject4).keys();
          while (((Iterator)localObject7).hasNext())
          {
            localObject8 = (String)((Iterator)localObject7).next();
            localObject9 = new StringBuilder();
            ((StringBuilder)localObject9).append("loadUserInfoData mProtocolFlag key=");
            ((StringBuilder)localObject9).append((String)localObject8);
            ((StringBuilder)localObject9).append(" value=");
            ((StringBuilder)localObject9).append(((JSONObject)localObject4).optInt((String)localObject8));
            LogUtils.d("TheSimCardOfGreatKing", ((StringBuilder)localObject9).toString());
            this.mProtocolFlag.put(localObject8, Integer.valueOf(((JSONObject)localObject4).optInt((String)localObject8)));
          }
        }
        localObject3 = TbsUserInfoDataProvider.getInstance().getUserInfoData(1101);
      }
      catch (Exception localException1)
      {
        LogUtils.d("TesUserInfo", Log.getStackTraceString(localException1));
      }
    }
    if ((localObject3 != null) && (!TextUtils.isEmpty(localObject3.toString())))
    {
      this.mHttpsTunnelToken = localObject3.toString();
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("loadUserInfoData mHttpsTunnelToken=");
      ((StringBuilder)localObject3).append(this.mHttpsTunnelToken);
      LogUtils.d("TheSimCardOfGreatKing", ((StringBuilder)localObject3).toString());
    }
    Object localObject3 = TbsUserInfoDataProvider.getInstance().getUserInfoData(1102);
    if ((localObject3 != null) && (!TextUtils.isEmpty(localObject3.toString()))) {}
    try
    {
      this.mHttpsTunnelTokenExpireTime = Integer.parseInt(localObject3.toString());
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("loadUserInfoData mHttpsTunnelTokenExpireTime=");
      ((StringBuilder)localObject3).append(this.mHttpsTunnelTokenExpireTime);
      ((StringBuilder)localObject3).append(" life=");
      ((StringBuilder)localObject3).append(this.mHttpsTunnelTokenExpireTime - System.currentTimeMillis() / 1000L);
      ((StringBuilder)localObject3).append("s");
      LogUtils.d("TheSimCardOfGreatKing", ((StringBuilder)localObject3).toString());
    }
    catch (Exception localException2)
    {
      for (;;) {}
    }
    localObject3 = TbsUserInfoDataProvider.getInstance().getUserInfoData(1005);
    if (localObject3 != null)
    {
      localObject3 = stringsToList(localObject3.toString());
      if (((ArrayList)localObject3).size() > 1)
      {
        this.mSocketServerList.clear();
        this.mSocketServerList.addAll((Collection)localObject3);
      }
    }
    localObject3 = TbsUserInfoDataProvider.getInstance().getUserInfoData(1006);
    if (localObject3 != null)
    {
      localObject3 = stringsToList(localObject3.toString());
      if (((ArrayList)localObject3).size() > 1)
      {
        this.mWupPushProxyList.clear();
        this.mWupPushProxyList.addAll((Collection)localObject3);
      }
    }
    localObject3 = TbsUserInfoDataProvider.getInstance().getUserInfoData(1011);
    if ((localObject3 != null) && (!TextUtils.isEmpty(localObject3.toString()))) {}
    try
    {
      this.mMD5Value = Integer.parseInt(localObject3.toString());
    }
    catch (Exception localException3)
    {
      for (;;) {}
    }
    localObject3 = TbsUserInfoDataProvider.getInstance().getUserInfoData(1012);
    if ((localObject3 != null) && (!TextUtils.isEmpty(localObject3.toString()))) {}
    try
    {
      this.mMaxReportNumber = Integer.parseInt(localObject3.toString());
    }
    catch (Exception localException4)
    {
      for (;;) {}
    }
    localObject3 = TbsUserInfoDataProvider.getInstance().getUserInfoData(1013);
    if ((localObject3 != null) && (!TextUtils.isEmpty(localObject3.toString()))) {}
    try
    {
      this.mDomainTime = Integer.parseInt(localObject3.toString());
    }
    catch (Exception localException5)
    {
      for (;;) {}
    }
    localObject3 = TbsUserInfoDataProvider.getInstance().getUserInfoData(1022);
    if ((localObject3 != null) && (!TextUtils.isEmpty(localObject3.toString()))) {}
    try
    {
      this.mProxyType = Integer.parseInt(localObject3.toString());
    }
    catch (Exception localException6)
    {
      Object localObject6;
      for (;;) {}
    }
    localObject3 = TbsUserInfoDataProvider.getInstance().getUserInfoData(1111);
    localObject6 = TbsUserInfoDataProvider.getInstance().getUserInfoData(1112);
    if ((localObject3 != null) && (!TextUtils.isEmpty(localObject3.toString())) && (localObject6 != null) && (!TextUtils.isEmpty(localObject6.toString())))
    {
      this.mAdJSMD5 = localObject3.toString();
      this.mModuleMD5 = localObject6.toString();
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("loadUserInfoData mAdJSMD5=");
      ((StringBuilder)localObject3).append(this.mAdJSMD5);
      ((StringBuilder)localObject3).append(" mModuleMD5=");
      ((StringBuilder)localObject3).append(this.mModuleMD5);
      LogUtils.d("AdFilter", ((StringBuilder)localObject3).toString());
    }
    localObject3 = TbsUserInfoDataProvider.getInstance().getUserInfoData(1113);
    if ((localObject3 != null) && (!TextUtils.isEmpty(localObject3.toString())))
    {
      this.mAdBlockSourceMD5 = localObject3.toString();
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("loadUserInfoData mAdBlockSourceMD5=");
      ((StringBuilder)localObject3).append(this.mAdBlockSourceMD5);
      LogUtils.d("AdFilter", ((StringBuilder)localObject3).toString());
    }
    localObject3 = TbsUserInfoDataProvider.getInstance().getUserInfoData(1114);
    if ((localObject3 != null) && (!TextUtils.isEmpty(localObject3.toString())))
    {
      this.mSubsetAdRuleMD5 = localObject3.toString();
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("loadUserInfoData mSubsetAdRuleMD5=");
      ((StringBuilder)localObject3).append(this.mSubsetAdRuleMD5);
      LogUtils.d("AdFilter", ((StringBuilder)localObject3).toString());
    }
    return true;
  }
  
  private void saveADMD5()
  {
    TbsUserInfoDataProvider.getInstance().addUserInfoData(1111, this.mAdJSMD5);
    TbsUserInfoDataProvider.getInstance().addUserInfoData(1112, this.mModuleMD5);
  }
  
  private void saveAdBlockSourceMD5()
  {
    TbsUserInfoDataProvider.getInstance().addUserInfoData(1113, this.mAdBlockSourceMD5);
  }
  
  private void saveHTTPSTunnelToken(String paramString, long paramLong)
  {
    TbsUserInfoDataProvider.getInstance().addUserInfoData(1101, paramString);
    long l = paramLong;
    if (paramLong < 60L) {
      l = 60L;
    }
    TbsUserInfoDataProvider.getInstance().addUserInfoData(1102, Long.valueOf(System.currentTimeMillis() / 1000L + l));
    this.mHttpsTunnelToken = paramString;
    this.mHttpsTunnelTokenExpireTime = (System.currentTimeMillis() / 1000L + l);
    if (l <= 60L)
    {
      TokenRefreshInterval = 30L;
      PreRefreshTokenTime = 30L;
    }
    else if (l <= 600L)
    {
      TokenRefreshInterval = 60L;
      PreRefreshTokenTime = 60L;
    }
    else
    {
      TokenRefreshInterval = 600L;
      PreRefreshTokenTime = l / 2L;
    }
    paramString = new StringBuilder();
    paramString.append("saveHTTPSTunnelToken tunnelToken=");
    paramString.append(this.mHttpsTunnelToken);
    paramString.append(" expireTime=");
    paramString.append(l);
    paramString.append(" mHttpsTunnelTokenExpireTime=");
    paramString.append(this.mHttpsTunnelTokenExpireTime);
    LogUtils.d("TheSimCardOfGreatKing", paramString.toString());
    TbsUserInfoDataProvider.getInstance().saveUserInfoData();
  }
  
  private void saveJsTemplateArgu()
  {
    TbsUserInfoDataProvider.getInstance().addUserInfoData(1018, this.mJsTemplate);
    TbsUserInfoDataProvider.getInstance().addUserInfoData(1019, this.mJsTemplateMD5);
  }
  
  private void saveQProtocolFlagToJson()
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      synchronized (this.mProtocolFlagLock)
      {
        Iterator localIterator = this.mProtocolFlag.entrySet().iterator();
        while (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          localJSONObject.put((String)localEntry.getKey(), localEntry.getValue());
        }
      }
      return;
    }
    catch (JSONException localJSONException)
    {
      appendUserinfoLiveLog("Error: JSONException");
      localJSONException.printStackTrace();
      TbsUserInfoDataProvider.getInstance().addUserInfoData(1028, localJSONObject.toString());
    }
  }
  
  private void saveRspData(File paramFile, String paramString)
  {
    if ((paramFile != null) && (paramString == null)) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("saveRspData  data.length=");
    localStringBuilder.append(paramString.length());
    localStringBuilder.append(" file=");
    localStringBuilder.append(paramFile.toString());
    LogUtils.d("AdFilter", localStringBuilder.toString());
    FileUtils.save(paramFile, paramString.getBytes());
  }
  
  private void saveSubsetAdRuleMD5()
  {
    TbsUserInfoDataProvider.getInstance().addUserInfoData(1114, this.mSubsetAdRuleMD5);
  }
  
  private void saveSubsetAdRuleToFile(File paramFile, String paramString)
  {
    if (paramFile != null)
    {
      if (paramString == null) {
        return;
      }
      long l = System.currentTimeMillis();
      try
      {
        paramString = paramString.getBytes();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("saveSubsetAdRuleToFile bytes =");
        localStringBuilder.append(paramString.length);
        localStringBuilder.append(", encrypt time = ");
        localStringBuilder.append(System.currentTimeMillis() - l);
        LogUtils.d("AdFilter", localStringBuilder.toString());
        FileUtils.save(paramFile, paramString);
        return;
      }
      catch (Exception paramFile)
      {
        resetSubsetAdRuleMD5();
        LogUtils.d("AdFilter", Log.getStackTraceString(paramFile));
        return;
      }
    }
  }
  
  private void saveWupProxyList(String paramString, ArrayList<String> paramArrayList)
  {
    WupServerConfigsWrapper.saveWupProxyList(paramString, paramArrayList);
    IPListDataManager.getInstance(TbsBaseModuleShell.getCallerContext()).saveServerList();
  }
  
  private void saveWupSocketList(ArrayList<String> paramArrayList)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("saveWupSocketList: ");
    localStringBuilder.append(paramArrayList);
    LogUtils.d("TesUserInfo", localStringBuilder.toString());
    if (paramArrayList != null)
    {
      if (paramArrayList.size() < 1) {
        return;
      }
      setSocketServerList(paramArrayList);
      return;
    }
  }
  
  public static void setJSApiDomainList(Map<String, TBSJSApiApiNames> paramMap)
  {
    mJSAPIWhiteList = paramMap;
  }
  
  public void appendLiveLog(String paramString)
  {
    StringBuffer localStringBuffer = this.livelogMsg;
    if (localStringBuffer != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("(");
      localStringBuilder.append(System.currentTimeMillis());
      localStringBuilder.append(")");
      localStringBuilder.append(paramString);
      localStringBuilder.append("; ");
      localStringBuffer.append(localStringBuilder.toString());
    }
  }
  
  public void appendUserinfoLiveLog(String paramString)
  {
    Object localObject = this.userinfoMsg;
    if ((localObject != null) && (((StringBuffer)localObject).length() <= 1000))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(" ");
      ((StringBuilder)localObject).append(paramString);
      LogUtils.d("appendUserinfoLiveLog", ((StringBuilder)localObject).toString());
      localObject = this.userinfoMsg;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("(");
      localStringBuilder.append(System.currentTimeMillis());
      localStringBuilder.append(")");
      localStringBuilder.append(paramString);
      localStringBuilder.append("; ");
      ((StringBuffer)localObject).append(localStringBuilder.toString());
    }
  }
  
  public void checkKingCardCondition()
  {
    LogUtils.d("TheSimCardOfGreatKing", "---TbsUserInfo checkKingCardCondition BuildConfig.BUILD_TBS_MODE=true");
    LogUtils.d("TheSimCardOfGreatKing", "---TbsUserInfo checkKingCardCondition end");
  }
  
  public String getAdBlockSourceMD5Value()
  {
    return this.mAdBlockSourceMD5;
  }
  
  public WUPRequest getAdFilterScriptTemplateRequest()
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("login, getAdFilterScriptTemplateRequest synchronously. JsTemplateMD5=");
    ((StringBuilder)localObject1).append(this.mJsTemplateMD5);
    ((StringBuilder)localObject1).append(" jsTemplate.size()=");
    Object localObject2 = this.mJsTemplate;
    int i;
    if (localObject2 != null) {
      i = ((String)localObject2).length();
    } else {
      i = 0;
    }
    ((StringBuilder)localObject1).append(i);
    LogUtils.d("adblock", ((StringBuilder)localObject1).toString());
    localObject1 = new WUPRequest("ad_block_solution", "getJsTemplate");
    localObject2 = new jsTemplateReq();
    ((jsTemplateReq)localObject2).sUserLocalMd5 = this.mJsTemplateMD5;
    ((WUPRequest)localObject1).put("req", localObject2);
    ((WUPRequest)localObject1).setRequestCallBack(this);
    ((WUPRequest)localObject1).setType((byte)55);
    return (WUPRequest)localObject1;
  }
  
  public String getAdJsMD5Value()
  {
    return this.mAdJSMD5;
  }
  
  public WUPRequest getAdblockSourceRequest()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("login, getAdblockSourceRequest synchronously. thread=");
    ((StringBuilder)localObject).append(Thread.currentThread().getName());
    ((StringBuilder)localObject).append(" mAdBlockSourceMD5=");
    ((StringBuilder)localObject).append(this.mAdBlockSourceMD5);
    LogUtils.d("AdFilter", ((StringBuilder)localObject).toString());
    localObject = new WUPRequest("directadfilterservice", "getAdBlockSource");
    GetAdBlockSourceReq localGetAdBlockSourceReq = new GetAdBlockSourceReq();
    localGetAdBlockSourceReq.setSGuid(GUIDFactory.getInstance().getStrGuid());
    localGetAdBlockSourceReq.setSQua(TbsInfoUtils.getQUA2());
    if (TbsFileUtils.getAdBlockSourceFile(TbsBaseModuleShell.getCallerContext()).exists())
    {
      localGetAdBlockSourceReq.setSLastMd5(this.mAdBlockSourceMD5);
    }
    else
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Error=AdBlockSource_NOT_EXIST mAdBlockSourceMD5=");
      localStringBuilder.append(this.mAdBlockSourceMD5);
      appendLiveLog(localStringBuilder.toString());
      localGetAdBlockSourceReq.setSLastMd5("");
    }
    ((WUPRequest)localObject).put("req", localGetAdBlockSourceReq);
    ((WUPRequest)localObject).setRequestCallBack(this);
    ((WUPRequest)localObject).setType((byte)61);
    return (WUPRequest)localObject;
  }
  
  public String getApn(int paramInt)
  {
    this.currentApn = Apn.getApnNameWithBSSID(paramInt);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("TbsUserInfo getApn = ");
    localStringBuilder.append(this.currentApn);
    LogUtils.d("TesUserInfo", localStringBuilder.toString());
    return this.currentApn;
  }
  
  public int getDomainTime()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getDomainTime:");
    localStringBuilder.append(this.mDomainTime);
    LogUtils.d("TesUserInfo", localStringBuilder.toString());
    return this.mDomainTime;
  }
  
  public WUPRequest getHTTPSTunnelTokenRequest()
  {
    WUPRequest localWUPRequest = new WUPRequest("httpWupToken", "getTokenInfo");
    TokenInfoReq localTokenInfoReq = new TokenInfoReq();
    localTokenInfoReq.sGuid = GUIDFactory.getInstance().getStrGuid();
    localTokenInfoReq.sQua2 = TbsInfoUtils.getQUA2();
    localWUPRequest.put("req", localTokenInfoReq);
    localWUPRequest.setRequestCallBack(this);
    localWUPRequest.setType((byte)56);
    this.mLastRefreshToken = (System.currentTimeMillis() / 1000L);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getHTTPSTunnelToken req.sGuid=");
    localStringBuilder.append(localTokenInfoReq.sGuid);
    localStringBuilder.append(" req.sQua2=");
    localStringBuilder.append(localTokenInfoReq.sQua2);
    LogUtils.d("TheSimCardOfGreatKing", localStringBuilder.toString());
    return localWUPRequest;
  }
  
  public String getHttpsTunnelToken()
  {
    LogUtils.d("TheSimCardOfGreatKing", "TbsUserInfo getHttpsTunnelToken");
    long l1 = System.currentTimeMillis() / 1000L;
    long l2 = this.mHttpsTunnelTokenExpireTime;
    Object localObject;
    if (l2 != -1L)
    {
      if ((l1 < l2) && (!TextUtils.isEmpty(this.mHttpsTunnelToken)))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("TbsUserInfo getHttpsTunnelToken  1 Token is Valid mHttpsTunnelTokenExpireTime=");
        ((StringBuilder)localObject).append(this.mHttpsTunnelTokenExpireTime);
        LogUtils.d("TheSimCardOfGreatKing", ((StringBuilder)localObject).toString());
        if (this.mHttpsTunnelTokenExpireTime - l1 < PreRefreshTokenTime) {
          refreshToken();
        }
        return this.mHttpsTunnelToken;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("TbsUserInfo getHttpsTunnelToken  1 Token is Exipred or mHttpsTunnelToken is Empty mHttpsTunnelToken=");
      ((StringBuilder)localObject).append(this.mHttpsTunnelToken);
      LogUtils.d("TheSimCardOfGreatKing", ((StringBuilder)localObject).toString());
    }
    else
    {
      LogUtils.d("TheSimCardOfGreatKing", "TbsUserInfo getHttpsTunnelToken 2 data doesn't initial, read from local file now.");
      localObject = TbsUserInfoDataProvider.getInstance().getUserInfoData(1102);
      if ((localObject != null) && (!TextUtils.isEmpty(localObject.toString()))) {
        this.mHttpsTunnelTokenExpireTime = Integer.parseInt(localObject.toString());
      }
      if (l1 < this.mHttpsTunnelTokenExpireTime)
      {
        localObject = TbsUserInfoDataProvider.getInstance().getUserInfoData(1101);
        if ((localObject != null) && (!TextUtils.isEmpty(localObject.toString())))
        {
          this.mHttpsTunnelToken = localObject.toString();
          LogUtils.d("TheSimCardOfGreatKing", "TbsUserInfo getHttpsTunnelToken 2 read from local file And Token is Valid");
          if (this.mHttpsTunnelTokenExpireTime - l1 < PreRefreshTokenTime) {
            refreshToken();
          }
          return this.mHttpsTunnelToken;
        }
        LogUtils.d("TheSimCardOfGreatKing", "TbsUserInfo getHttpsTunnelToken 2 read from local file but Token is Empty");
      }
      else
      {
        LogUtils.d("TheSimCardOfGreatKing", "TbsUserInfo getHttpsTunnelToken 2 read from local file but Token is Exipred or local file dones't exist this data.");
      }
    }
    refreshToken();
    return "";
  }
  
  public void getIPList(int paramInt, final boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("sending ip-list request, onlyQProxyList=");
    localStringBuilder.append(paramBoolean);
    LogUtils.d("x5-ip-list", localStringBuilder.toString());
    try
    {
      new Thread(new Runnable()
      {
        public void run()
        {
          WUPTaskProxy.send(TbsUserInfo.this.getIPListRequest(paramBoolean));
        }
      }).start();
      return;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      localOutOfMemoryError.printStackTrace();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public WUPRequest getIPListReqsOnBoot()
  {
    LogUtils.d("x5-ip-list", "getIPListReqsOnBoot login, get ip-list synchronously.");
    Object localObject = new ArrayList();
    ((ArrayList)localObject).add(Integer.valueOf(1));
    ((ArrayList)localObject).add(Integer.valueOf(7));
    ((ArrayList)localObject).add(Integer.valueOf(10));
    ((ArrayList)localObject).add(Integer.valueOf(13));
    ((ArrayList)localObject).add(Integer.valueOf(14));
    localObject = getIPListRequest((ArrayList)localObject);
    ((WUPRequest)localObject).setType((byte)54);
    return (WUPRequest)localObject;
  }
  
  public WUPRequest getIPListRequest(ArrayList<Integer> paramArrayList)
  {
    LogUtils.d("x5-ip-list", "login, get ip-list synchronously.");
    WUPRequest localWUPRequest = new WUPRequest("proxyip", "getIPListByRouter");
    RouteIPListReq localRouteIPListReq = new RouteIPListReq();
    Context localContext = TbsBaseModuleShell.getCallerContext();
    Object localObject = (UserBase)UserBase.getInstance().clone();
    ((UserBase)localObject).sAPN = getApn(Apn.getApnTypeS());
    localRouteIPListReq.stUB = ((UserBase)localObject);
    localRouteIPListReq.vIPType = paramArrayList;
    localRouteIPListReq.iSubType = IPListUtils.getConnectType(localContext);
    if (localRouteIPListReq.iSubType == 1)
    {
      localObject = IPListUtils.getWifiBSSID(localContext);
      paramArrayList = (ArrayList<Integer>)localObject;
      if (TextUtils.isEmpty((CharSequence)localObject)) {
        paramArrayList = "UNKNOW";
      }
      localRouteIPListReq.sExtraInfo = paramArrayList;
    }
    else
    {
      localObject = IPListUtils.getConnectExtraInfo(localContext);
      paramArrayList = (ArrayList<Integer>)localObject;
      if (TextUtils.isEmpty((CharSequence)localObject)) {
        paramArrayList = "UNKNOW";
      }
      localRouteIPListReq.sExtraInfo = paramArrayList;
    }
    paramArrayList = new StringBuilder();
    localObject = IPListUtils.getMCC(localContext);
    if (TextUtils.isEmpty((CharSequence)localObject)) {
      paramArrayList.append("NULL");
    } else {
      paramArrayList.append((String)localObject);
    }
    localObject = IPListUtils.getMNC(localContext);
    if (TextUtils.isEmpty((CharSequence)localObject)) {
      paramArrayList.append("NULL");
    } else {
      paramArrayList.append((String)localObject);
    }
    localRouteIPListReq.sMCCMNC = paramArrayList.toString();
    localObject = IPListUtils.getConnectTypeName(localContext);
    paramArrayList = (ArrayList<Integer>)localObject;
    if (TextUtils.isEmpty((CharSequence)localObject)) {
      paramArrayList = "UNKNOW";
    }
    localRouteIPListReq.sTypeName = paramArrayList;
    localWUPRequest.put("req", localRouteIPListReq);
    localWUPRequest.setRequestCallBack(this);
    localWUPRequest.setType((byte)53);
    localWUPRequest.setUrl(WupServerConfigsWrapper.PROXY_DOMAIN);
    return localWUPRequest;
  }
  
  public WUPRequest getIPListRequest(boolean paramBoolean)
  {
    LogUtils.d("x5-ip-list", "getIPListRequest login, get ip-list synchronously.");
    Object localObject = new ArrayList();
    ((ArrayList)localObject).add(Integer.valueOf(13));
    ((ArrayList)localObject).add(Integer.valueOf(14));
    if (!paramBoolean)
    {
      ((ArrayList)localObject).add(Integer.valueOf(7));
      ((ArrayList)localObject).add(Integer.valueOf(10));
    }
    localObject = getIPListRequest((ArrayList)localObject);
    ((WUPRequest)localObject).setType((byte)54);
    return (WUPRequest)localObject;
  }
  
  public Map<String, TBSJSApiApiNames> getJSApiDomainList()
  {
    return mJSAPIWhiteList;
  }
  
  public String getJsTemplate()
  {
    return this.mJsTemplate;
  }
  
  public ArrayList<String> getLbsDontAlertWhiteList()
  {
    ArrayList localArrayList1 = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(97);
    if (localArrayList1.isEmpty())
    {
      ArrayList localArrayList2 = TbsDomainWhiteListManager.getDefalutLbsDontAlertWhiteList();
      if ((localArrayList2 != null) && (localArrayList2.size() > 0))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("load default lbs white list, list = ");
        localStringBuilder.append(localArrayList2.toString());
        LogUtils.d("TesUserInfo", localStringBuilder.toString());
        localArrayList1.addAll(localArrayList2);
      }
    }
    return localArrayList1;
  }
  
  public String getLivelogStr()
  {
    String str = this.livelogMsg.toString();
    StringBuffer localStringBuffer = this.livelogMsg;
    localStringBuffer.delete(0, localStringBuffer.length());
    return str;
  }
  
  public String getModuleMD5Value()
  {
    return this.mModuleMD5;
  }
  
  public String getOriChannelID()
  {
    return this.mOriChannelID;
  }
  
  public int getProtocolFlag()
  {
    String str = getApn(Apn.sApnTypeS);
    if (this.mProtocolFlag.containsKey(str)) {
      return ((Integer)this.mProtocolFlag.get(str)).intValue();
    }
    return 0;
  }
  
  public String getProtocolListString()
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    synchronized (this.mProtocolFlag)
    {
      Iterator localIterator = this.mProtocolFlag.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        StringBuilder localStringBuilder2 = new StringBuilder();
        localStringBuilder2.append("< ");
        localStringBuilder2.append((String)localEntry.getKey());
        localStringBuilder2.append(", ");
        localStringBuilder2.append(localEntry.getValue());
        localStringBuilder2.append(" >\r\n");
        localStringBuilder1.append(localStringBuilder2.toString());
      }
      return localStringBuilder1.toString();
    }
  }
  
  public int getProxyAddressType()
  {
    if (!PublicSettingManager.getInstance().getQuicProxySwitch()) {
      return 1;
    }
    int i = this.mProxyType;
    if ((i != 1) && (i != 2))
    {
      Object localObject = TbsUserInfoDataProvider.getInstance().getUserInfoData(1022);
      if ((localObject != null) && (!TextUtils.isEmpty(localObject.toString()))) {
        this.mProxyType = Integer.parseInt(localObject.toString());
      } else {
        this.mProxyType = 1;
      }
      return this.mProxyType;
    }
    return this.mProxyType;
  }
  
  public String getQAuth()
  {
    return this.mQAuth;
  }
  
  /* Error */
  public String getQProxyAddress(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 430	com/tencent/tbs/common/baseinfo/TbsUserInfo:mQProxyList	Ljava/util/Hashtable;
    //   6: astore 5
    //   8: aload 5
    //   10: monitorenter
    //   11: iload_2
    //   12: ifeq +10 -> 22
    //   15: ldc 32
    //   17: astore 4
    //   19: goto +10 -> 29
    //   22: aload_0
    //   23: iload_1
    //   24: invokespecial 189	com/tencent/tbs/common/baseinfo/TbsUserInfo:apnTypeToName	(I)Ljava/lang/String;
    //   27: astore 4
    //   29: aload_0
    //   30: getfield 430	com/tencent/tbs/common/baseinfo/TbsUserInfo:mQProxyList	Ljava/util/Hashtable;
    //   33: aload 4
    //   35: invokevirtual 979	java/util/Hashtable:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   38: checkcast 13	com/tencent/tbs/common/baseinfo/TbsUserInfo$ProxyInfo
    //   41: astore 4
    //   43: aload 4
    //   45: ifnull +46 -> 91
    //   48: iload_3
    //   49: ifeq +18 -> 67
    //   52: aload 4
    //   54: invokevirtual 982	com/tencent/tbs/common/baseinfo/TbsUserInfo$ProxyInfo:getIPV6Proxy	()Ljava/lang/String;
    //   57: astore 4
    //   59: aload 5
    //   61: monitorexit
    //   62: aload_0
    //   63: monitorexit
    //   64: aload 4
    //   66: areturn
    //   67: aload_0
    //   68: aload 4
    //   70: invokevirtual 985	com/tencent/tbs/common/baseinfo/TbsUserInfo$ProxyInfo:getType	()I
    //   73: putfield 136	com/tencent/tbs/common/baseinfo/TbsUserInfo:mIsOCProxy	I
    //   76: aload 4
    //   78: invokevirtual 988	com/tencent/tbs/common/baseinfo/TbsUserInfo$ProxyInfo:getProxy	()Ljava/lang/String;
    //   81: astore 4
    //   83: aload 5
    //   85: monitorexit
    //   86: aload_0
    //   87: monitorexit
    //   88: aload 4
    //   90: areturn
    //   91: aload 5
    //   93: monitorexit
    //   94: aload_0
    //   95: monitorexit
    //   96: aconst_null
    //   97: areturn
    //   98: astore 4
    //   100: aload 5
    //   102: monitorexit
    //   103: aload 4
    //   105: athrow
    //   106: astore 4
    //   108: aload_0
    //   109: monitorexit
    //   110: aload 4
    //   112: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	113	0	this	TbsUserInfo
    //   0	113	1	paramInt	int
    //   0	113	2	paramBoolean1	boolean
    //   0	113	3	paramBoolean2	boolean
    //   17	72	4	localObject1	Object
    //   98	6	4	localObject2	Object
    //   106	5	4	localObject3	Object
    //   6	95	5	localHashtable	Hashtable
    // Exception table:
    //   from	to	target	type
    //   22	29	98	finally
    //   29	43	98	finally
    //   52	62	98	finally
    //   67	86	98	finally
    //   91	94	98	finally
    //   100	103	98	finally
    //   2	11	106	finally
    //   103	106	106	finally
  }
  
  /* Error */
  public ArrayList<String> getQProxyAddressList(int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 430	com/tencent/tbs/common/baseinfo/TbsUserInfo:mQProxyList	Ljava/util/Hashtable;
    //   6: astore_2
    //   7: aload_2
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield 430	com/tencent/tbs/common/baseinfo/TbsUserInfo:mQProxyList	Ljava/util/Hashtable;
    //   13: aload_0
    //   14: iload_1
    //   15: invokespecial 189	com/tencent/tbs/common/baseinfo/TbsUserInfo:apnTypeToName	(I)Ljava/lang/String;
    //   18: invokevirtual 979	java/util/Hashtable:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   21: checkcast 13	com/tencent/tbs/common/baseinfo/TbsUserInfo$ProxyInfo
    //   24: astore_3
    //   25: aload_3
    //   26: ifnull +14 -> 40
    //   29: aload_3
    //   30: invokevirtual 992	com/tencent/tbs/common/baseinfo/TbsUserInfo$ProxyInfo:getProxyList	()Ljava/util/ArrayList;
    //   33: astore_3
    //   34: aload_2
    //   35: monitorexit
    //   36: aload_0
    //   37: monitorexit
    //   38: aload_3
    //   39: areturn
    //   40: aload_2
    //   41: monitorexit
    //   42: aload_0
    //   43: monitorexit
    //   44: aconst_null
    //   45: areturn
    //   46: astore_3
    //   47: aload_2
    //   48: monitorexit
    //   49: aload_3
    //   50: athrow
    //   51: astore_2
    //   52: aload_0
    //   53: monitorexit
    //   54: aload_2
    //   55: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	56	0	this	TbsUserInfo
    //   0	56	1	paramInt	int
    //   51	4	2	localObject1	Object
    //   24	15	3	localObject2	Object
    //   46	4	3	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   9	25	46	finally
    //   29	36	46	finally
    //   40	42	46	finally
    //   47	49	46	finally
    //   2	9	51	finally
    //   49	51	51	finally
  }
  
  public String getQProxyListString()
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    synchronized (this.mQProxyList)
    {
      Iterator localIterator = this.mQProxyList.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        StringBuilder localStringBuilder2 = new StringBuilder();
        localStringBuilder2.append("< ");
        localStringBuilder2.append((String)localEntry.getKey());
        localStringBuilder2.append(", ");
        localStringBuilder2.append(((ProxyInfo)localEntry.getValue()).toString());
        localStringBuilder2.append(" >\r\n");
        localStringBuilder1.append(localStringBuilder2.toString());
      }
      return localStringBuilder1.toString();
    }
  }
  
  public WUPRequest getSmartAdFilterSource()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("login, getSmartAdFilterSource synchronously. thread=");
    ((StringBuilder)localObject).append(Thread.currentThread().getName());
    ((StringBuilder)localObject).append(" mModuleMD5=");
    ((StringBuilder)localObject).append(this.mModuleMD5);
    ((StringBuilder)localObject).append(" mAdJSMD5=");
    ((StringBuilder)localObject).append(this.mAdJSMD5);
    LogUtils.d("AdFilter", ((StringBuilder)localObject).toString());
    localObject = new WUPRequest("directadfilterservice", "getAdFilterSource");
    GetAdFilterSourceReq localGetAdFilterSourceReq = new GetAdFilterSourceReq();
    localGetAdFilterSourceReq.setSGuid(GUIDFactory.getInstance().getStrGuid());
    localGetAdFilterSourceReq.setSQua(TbsInfoUtils.getQUA2());
    StringBuilder localStringBuilder;
    if (TbsFileUtils.getSAFJFile(TbsBaseModuleShell.getCallerContext()).exists())
    {
      localGetAdFilterSourceReq.setSLastJsMd5(this.mAdJSMD5);
    }
    else
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Error=JS_NOT_EXIST mAdJSMD5=");
      localStringBuilder.append(this.mAdJSMD5);
      appendLiveLog(localStringBuilder.toString());
      localGetAdFilterSourceReq.setSLastJsMd5("");
    }
    if (TbsFileUtils.getSAFMFile(TbsBaseModuleShell.getCallerContext()).exists())
    {
      localGetAdFilterSourceReq.setSLastModelMd5(this.mModuleMD5);
    }
    else
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Error=MODEL_NOT_EXIST mModuleMD5=");
      localStringBuilder.append(this.mModuleMD5);
      appendLiveLog(localStringBuilder.toString());
      localGetAdFilterSourceReq.setSLastModelMd5("");
    }
    ((WUPRequest)localObject).put("req", localGetAdFilterSourceReq);
    ((WUPRequest)localObject).setRequestCallBack(this);
    ((WUPRequest)localObject).setType((byte)60);
    return (WUPRequest)localObject;
  }
  
  public ArrayList<String> getSocketServerList()
  {
    ??? = this.mSocketServerList;
    if ((??? != null) && (???.size() >= 1))
    {
      ArrayList localArrayList2 = new ArrayList();
      synchronized (this.mSocketServerList)
      {
        Iterator localIterator = this.mSocketServerList.iterator();
        while (localIterator.hasNext()) {
          localArrayList2.add((String)localIterator.next());
        }
        return localArrayList2;
      }
    }
    return null;
  }
  
  public int getStatState()
  {
    return this.mStatState;
  }
  
  public String getSubsetAdRuleMD5Value()
  {
    return this.mSubsetAdRuleMD5;
  }
  
  public WUPRequest getSubsetAdRuleRequest()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("login, getSubsetAdRuleRequest synchronously. thread=");
    ((StringBuilder)localObject).append(Thread.currentThread().getName());
    ((StringBuilder)localObject).append(" mSubsetAdRuleMD5=");
    ((StringBuilder)localObject).append(this.mSubsetAdRuleMD5);
    LogUtils.d("AdFilter", ((StringBuilder)localObject).toString());
    localObject = new WUPRequest("directadfilterservice", "getSubsetAdRule");
    GetSubsetAdRuleReq localGetSubsetAdRuleReq = new GetSubsetAdRuleReq();
    localGetSubsetAdRuleReq.setSGuid(GUIDFactory.getInstance().getStrGuid());
    localGetSubsetAdRuleReq.setSQua2(TbsInfoUtils.getQUA2());
    if (TbsFileUtils.getAdBlockSourceFile(TbsBaseModuleShell.getCallerContext()).exists())
    {
      localGetSubsetAdRuleReq.setSLastMd5(this.mSubsetAdRuleMD5);
    }
    else
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Error=AdBlockSource_NOT_EXIST mSubsetAdRuleMD5=");
      localStringBuilder.append(this.mSubsetAdRuleMD5);
      appendLiveLog(localStringBuilder.toString());
      localGetSubsetAdRuleReq.setSLastMd5("");
    }
    ((WUPRequest)localObject).put("req", localGetSubsetAdRuleReq);
    ((WUPRequest)localObject).setRequestCallBack(this);
    ((WUPRequest)localObject).setType((byte)62);
    return (WUPRequest)localObject;
  }
  
  public String getUserinfoLivelogStr()
  {
    Object localObject2 = this.userinfoMsg.toString();
    if (TextUtils.isEmpty((CharSequence)localObject2)) {
      return "";
    }
    Object localObject1 = "Process=";
    if (TbsBaseModuleShell.getCallerContext() != null)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Process=");
      ((StringBuilder)localObject1).append(ThreadUtils.getCurrentProcessNameIngoreColon(TbsBaseModuleShell.getCallerContext()));
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append((String)localObject1);
    localStringBuilder.append((String)localObject2);
    localObject1 = localStringBuilder.toString();
    localObject2 = this.userinfoMsg;
    ((StringBuffer)localObject2).delete(0, ((StringBuffer)localObject2).length());
    return (String)localObject1;
  }
  
  public Vector<String> getWupProxyList()
  {
    try
    {
      Vector localVector = new Vector();
      Object localObject2 = IPListUtils.getWUPNetEnvironment(TbsBaseModuleShell.getCallerContext());
      localVector.addAll(IPListDataManager.getInstance(TbsBaseModuleShell.getCallerContext()).getServerList((String)localObject2));
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("getWupProxyList: ");
      ((StringBuilder)localObject2).append(localVector);
      LogUtils.d("TesUserInfo", ((StringBuilder)localObject2).toString());
      return localVector;
    }
    finally
    {
      localObject1 = finally;
      throw ((Throwable)localObject1);
    }
  }
  
  /* Error */
  public void handleHttpsTunnelProxyFail(int paramInt, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 430	com/tencent/tbs/common/baseinfo/TbsUserInfo:mQProxyList	Ljava/util/Hashtable;
    //   6: astore_3
    //   7: aload_3
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield 430	com/tencent/tbs/common/baseinfo/TbsUserInfo:mQProxyList	Ljava/util/Hashtable;
    //   13: ldc 32
    //   15: invokevirtual 979	java/util/Hashtable:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   18: checkcast 13	com/tencent/tbs/common/baseinfo/TbsUserInfo$ProxyInfo
    //   21: astore 4
    //   23: new 205	java/lang/StringBuilder
    //   26: dup
    //   27: invokespecial 206	java/lang/StringBuilder:<init>	()V
    //   30: astore 5
    //   32: aload 5
    //   34: ldc_w 1065
    //   37: invokevirtual 210	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   40: pop
    //   41: aload 5
    //   43: aload 4
    //   45: invokevirtual 457	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   48: pop
    //   49: ldc -35
    //   51: aload 5
    //   53: invokevirtual 214	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   56: invokestatic 227	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   59: aload 4
    //   61: ifnull +10 -> 71
    //   64: aload 4
    //   66: aload_2
    //   67: iconst_1
    //   68: invokevirtual 1069	com/tencent/tbs/common/baseinfo/TbsUserInfo$ProxyInfo:handleFail	(Ljava/lang/String;Z)V
    //   71: aload_3
    //   72: monitorexit
    //   73: aload_0
    //   74: monitorexit
    //   75: return
    //   76: astore_2
    //   77: aload_3
    //   78: monitorexit
    //   79: aload_2
    //   80: athrow
    //   81: astore_2
    //   82: aload_0
    //   83: monitorexit
    //   84: aload_2
    //   85: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	86	0	this	TbsUserInfo
    //   0	86	1	paramInt	int
    //   0	86	2	paramString	String
    //   21	44	4	localProxyInfo	ProxyInfo
    //   30	22	5	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   9	59	76	finally
    //   64	71	76	finally
    //   71	73	76	finally
    //   77	79	76	finally
    //   2	9	81	finally
    //   79	81	81	finally
  }
  
  /* Error */
  public void handleQProxyFailure(int paramInt, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 430	com/tencent/tbs/common/baseinfo/TbsUserInfo:mQProxyList	Ljava/util/Hashtable;
    //   6: astore_3
    //   7: aload_3
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield 430	com/tencent/tbs/common/baseinfo/TbsUserInfo:mQProxyList	Ljava/util/Hashtable;
    //   13: aload_0
    //   14: iload_1
    //   15: invokespecial 189	com/tencent/tbs/common/baseinfo/TbsUserInfo:apnTypeToName	(I)Ljava/lang/String;
    //   18: invokevirtual 979	java/util/Hashtable:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   21: checkcast 13	com/tencent/tbs/common/baseinfo/TbsUserInfo$ProxyInfo
    //   24: astore 4
    //   26: new 205	java/lang/StringBuilder
    //   29: dup
    //   30: invokespecial 206	java/lang/StringBuilder:<init>	()V
    //   33: astore 5
    //   35: aload 5
    //   37: ldc_w 1072
    //   40: invokevirtual 210	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   43: pop
    //   44: aload 5
    //   46: aload 4
    //   48: invokevirtual 457	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   51: pop
    //   52: ldc -35
    //   54: aload 5
    //   56: invokevirtual 214	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   59: invokestatic 227	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   62: aload 4
    //   64: ifnull +10 -> 74
    //   67: aload 4
    //   69: aload_2
    //   70: iconst_0
    //   71: invokevirtual 1069	com/tencent/tbs/common/baseinfo/TbsUserInfo$ProxyInfo:handleFail	(Ljava/lang/String;Z)V
    //   74: aload_3
    //   75: monitorexit
    //   76: aload_0
    //   77: monitorexit
    //   78: return
    //   79: astore_2
    //   80: aload_3
    //   81: monitorexit
    //   82: aload_2
    //   83: athrow
    //   84: astore_2
    //   85: aload_0
    //   86: monitorexit
    //   87: aload_2
    //   88: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	89	0	this	TbsUserInfo
    //   0	89	1	paramInt	int
    //   0	89	2	paramString	String
    //   24	44	4	localProxyInfo	ProxyInfo
    //   33	22	5	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   9	62	79	finally
    //   67	74	79	finally
    //   74	76	79	finally
    //   80	82	79	finally
    //   2	9	84	finally
    //   82	84	84	finally
  }
  
  /* Error */
  public void handleQProxySuccess(int paramInt, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 430	com/tencent/tbs/common/baseinfo/TbsUserInfo:mQProxyList	Ljava/util/Hashtable;
    //   6: astore_3
    //   7: aload_3
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield 430	com/tencent/tbs/common/baseinfo/TbsUserInfo:mQProxyList	Ljava/util/Hashtable;
    //   13: aload_0
    //   14: iload_1
    //   15: invokespecial 189	com/tencent/tbs/common/baseinfo/TbsUserInfo:apnTypeToName	(I)Ljava/lang/String;
    //   18: invokevirtual 979	java/util/Hashtable:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   21: checkcast 13	com/tencent/tbs/common/baseinfo/TbsUserInfo$ProxyInfo
    //   24: astore 4
    //   26: new 205	java/lang/StringBuilder
    //   29: dup
    //   30: invokespecial 206	java/lang/StringBuilder:<init>	()V
    //   33: astore 5
    //   35: aload 5
    //   37: ldc_w 1075
    //   40: invokevirtual 210	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   43: pop
    //   44: aload 5
    //   46: aload 4
    //   48: invokevirtual 457	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   51: pop
    //   52: ldc_w 1077
    //   55: aload 5
    //   57: invokevirtual 214	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   60: invokestatic 227	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   63: aload 4
    //   65: ifnull +9 -> 74
    //   68: aload 4
    //   70: aload_2
    //   71: invokevirtual 1080	com/tencent/tbs/common/baseinfo/TbsUserInfo$ProxyInfo:handleSuccess	(Ljava/lang/String;)V
    //   74: aload_3
    //   75: monitorexit
    //   76: aload_0
    //   77: monitorexit
    //   78: return
    //   79: astore_2
    //   80: aload_3
    //   81: monitorexit
    //   82: aload_2
    //   83: athrow
    //   84: astore_2
    //   85: aload_0
    //   86: monitorexit
    //   87: aload_2
    //   88: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	89	0	this	TbsUserInfo
    //   0	89	1	paramInt	int
    //   0	89	2	paramString	String
    //   24	45	4	localProxyInfo	ProxyInfo
    //   33	23	5	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   9	63	79	finally
    //   68	74	79	finally
    //   74	76	79	finally
    //   80	82	79	finally
    //   2	9	84	finally
    //   82	84	84	finally
  }
  
  public void init()
  {
    if (this.initiated) {
      return;
    }
    reset();
    if (TbsBaseModuleShell.getCallerContext() == null) {
      return;
    }
    LogUtils.d("TbsUserInfo", "TbsUserInfo init()");
    if (TbsFileUtils.getUserFile(TbsBaseModuleShell.getCallerContext()).exists()) {
      TbsFileUtils.getUserFile(TbsBaseModuleShell.getCallerContext()).delete();
    }
    if (TbsFileUtils.getUserInfoFile(TbsBaseModuleShell.getCallerContext()).exists()) {
      loadUserInfo();
    }
    if (TbsFileUtils.getDomainJsonFile(TbsBaseModuleShell.getCallerContext(), TbsBaseModuleShell.REQ_SRC_TBS).exists()) {
      loadDomainInfo(TbsBaseModuleShell.REQ_SRC_TBS);
    }
    if (TbsFileUtils.getDomainJsonFile(TbsBaseModuleShell.getCallerContext(), TbsBaseModuleShell.REQ_SRC_TBS_VIDEO).exists()) {
      loadDomainInfo(TbsBaseModuleShell.REQ_SRC_TBS_VIDEO);
    }
    if (TbsFileUtils.getDomainJsonFile(TbsBaseModuleShell.getCallerContext(), TbsBaseModuleShell.REQ_SRC_MINI_QB).exists()) {
      loadDomainInfo(TbsBaseModuleShell.REQ_SRC_MINI_QB);
    }
    this.initiated = true;
  }
  
  public boolean initiated()
  {
    return this.initiated;
  }
  
  public boolean isGreatKingCard()
  {
    return false;
  }
  
  public boolean isHttpsTunnelTokenValid()
  {
    return !TextUtils.isEmpty(getHttpsTunnelToken());
  }
  
  public boolean isIPV6Adress(String paramString)
  {
    if (paramString.split(":").length > 2)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(" isIPV6Adress + ipadress : ");
      localStringBuilder.append(paramString);
      LogUtils.d("x5-ip-list", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("x5-ip-list  isIPV6Adress + ipadress : ");
      localStringBuilder.append(paramString);
      appendLiveLog(localStringBuilder.toString());
      return true;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" isIPV4Adress + ipadress : ");
    localStringBuilder.append(paramString);
    LogUtils.d("x5-ip-list", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("x5-ip-list isIPV4Adress + ipadress : ");
    localStringBuilder.append(paramString);
    appendLiveLog(localStringBuilder.toString());
    return false;
  }
  
  public boolean loadUserInfo()
  {
    if (TbsUserInfoDataProvider.getInstance().addUserInfoData(TbsUserInfoDataProvider.getInstance().loadJsonObject())) {
      return loadUserInfoData();
    }
    return false;
  }
  
  public void onGetJsTemplate(WUPResponseBase paramWUPResponseBase)
  {
    Object localObject = paramWUPResponseBase.getReturnCode();
    if (localObject != null)
    {
      if (((Integer)localObject).intValue() < 0) {
        return;
      }
      paramWUPResponseBase = (jsTemplateResp)paramWUPResponseBase.get("resp");
      if (paramWUPResponseBase == null) {
        return;
      }
      if (this.mJsTemplateMD5.equals(paramWUPResponseBase.sContentMd5))
      {
        LogUtils.d("TesUserInfo", "onGetJsTemplate local equal server");
        return;
      }
      this.mJsTemplate = paramWUPResponseBase.sJsTemplateContent;
      this.mJsTemplateMD5 = paramWUPResponseBase.sContentMd5;
      paramWUPResponseBase = new StringBuilder();
      paramWUPResponseBase.append("onGetJsTemplate mJsTemplateMD5=");
      paramWUPResponseBase.append(this.mJsTemplateMD5);
      paramWUPResponseBase.append(" jsTemplate.size()=");
      localObject = this.mJsTemplate;
      int i;
      if (localObject != null) {
        i = ((String)localObject).length();
      } else {
        i = 0;
      }
      paramWUPResponseBase.append(i);
      LogUtils.d("TesUserInfo", paramWUPResponseBase.toString());
      saveJsTemplateArgu();
      TbsUserInfoDataProvider.getInstance().saveUserInfoData();
      return;
    }
  }
  
  public void onIPList(WUPResponseBase paramWUPResponseBase, ITbsSmttServiceCallback paramITbsSmttServiceCallback, int paramInt)
  {
    paramITbsSmttServiceCallback = paramWUPResponseBase.getReturnCode();
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(" onIPList result=");
    ((StringBuilder)localObject1).append(paramITbsSmttServiceCallback);
    LogUtils.d("x5-ip-list", ((StringBuilder)localObject1).toString());
    if ((paramITbsSmttServiceCallback != null) && (paramITbsSmttServiceCallback.intValue() >= 0))
    {
      paramITbsSmttServiceCallback = (RouteIPListRsp)paramWUPResponseBase.get("rsp");
      if (paramITbsSmttServiceCallback == null)
      {
        LogUtils.d("x5-ip-list", " onIPList response.get(rsp) is null");
        appendUserinfoLiveLog(" onIPList response.get(rsp) is null");
        return;
      }
      localObject1 = paramITbsSmttServiceCallback.vIPInfos;
      paramWUPResponseBase = new StringBuilder();
      paramWUPResponseBase.append(" onIPList proxy_type = ");
      paramWUPResponseBase.append(paramITbsSmttServiceCallback.iProxyType);
      paramWUPResponseBase.append(",  ipRsp.vIPInfos=");
      paramWUPResponseBase.append(paramITbsSmttServiceCallback.vIPInfos);
      LogUtils.d("x5-ip-list", paramWUPResponseBase.toString());
      paramWUPResponseBase = "{";
      localObject1 = ((ArrayList)localObject1).iterator();
      paramInt = 1;
      while (((Iterator)localObject1).hasNext())
      {
        JoinIPInfo localJoinIPInfo = (JoinIPInfo)((Iterator)localObject1).next();
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(" onIPList list.eIPType=");
        ((StringBuilder)localObject2).append(localJoinIPInfo.eIPType);
        LogUtils.d("x5-ip-list", ((StringBuilder)localObject2).toString());
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(paramWUPResponseBase);
        ((StringBuilder)localObject2).append(localJoinIPInfo.eIPType);
        ((StringBuilder)localObject2).append(",");
        paramWUPResponseBase = ((StringBuilder)localObject2).toString();
        int i = localJoinIPInfo.eIPType;
        if (i != 1)
        {
          if (i != 7)
          {
            if (i != 10)
            {
              if (i != 16)
              {
                switch (i)
                {
                default: 
                  break;
                case 14: 
                  TbsSmttServiceProxy.getInstance().setNeedCheckQProxy(false, false, false);
                  saveQuicProxyList(paramITbsSmttServiceCallback.sApn, paramITbsSmttServiceCallback.iProxyType, localJoinIPInfo.vIPList, localJoinIPInfo.iTotalPollNum, hourToMillis(localJoinIPInfo.iLifePeriod), localJoinIPInfo.vProxyListType);
                  localObject2 = new StringBuilder();
                  ((StringBuilder)localObject2).append("ip-list-_QUICPROXY-maxSwitchNum = ");
                  ((StringBuilder)localObject2).append(localJoinIPInfo.iTotalPollNum);
                  LogUtils.d("x5-ip-list", ((StringBuilder)localObject2).toString());
                  localObject2 = new StringBuilder();
                  ((StringBuilder)localObject2).append("ip-list-_QUICROXY-iLifePeriod = ");
                  ((StringBuilder)localObject2).append(localJoinIPInfo.iLifePeriod);
                  LogUtils.d("x5-ip-list", ((StringBuilder)localObject2).toString());
                  paramInt = 1;
                  break;
                case 13: 
                  TbsSmttServiceProxy.getInstance().setNeedCheckQProxy(false, false, false);
                  saveHttp2ProxyList(paramITbsSmttServiceCallback.sApn, paramITbsSmttServiceCallback.iProxyType, localJoinIPInfo.vIPList, localJoinIPInfo.iTotalPollNum, hourToMillis(localJoinIPInfo.iLifePeriod), localJoinIPInfo.vProxyListType);
                  localObject2 = new StringBuilder();
                  ((StringBuilder)localObject2).append("ip-list-_HTTP2PROXY-maxSwitchNum = ");
                  ((StringBuilder)localObject2).append(localJoinIPInfo.iTotalPollNum);
                  LogUtils.d("x5-ip-list", ((StringBuilder)localObject2).toString());
                  localObject2 = new StringBuilder();
                  ((StringBuilder)localObject2).append("ip-list-_HTTP2ROXY-iLifePeriod = ");
                  ((StringBuilder)localObject2).append(localJoinIPInfo.iLifePeriod);
                  LogUtils.d("x5-ip-list", ((StringBuilder)localObject2).toString());
                  paramInt = 1;
                  break;
                }
              }
              else
              {
                TbsSmttServiceProxy.getInstance().setNeedCheckQProxy(false, false, false);
                saveHttpsTunnelProxyList(paramITbsSmttServiceCallback.sApn, paramITbsSmttServiceCallback.iProxyType, localJoinIPInfo.vIPList, localJoinIPInfo.iTotalPollNum, hourToMillis(localJoinIPInfo.iLifePeriod), localJoinIPInfo.vProxyListType);
                localObject2 = new StringBuilder();
                ((StringBuilder)localObject2).append("ip-list-_HTTPSTUNNEL-maxSwitchNum = ");
                ((StringBuilder)localObject2).append(localJoinIPInfo.iTotalPollNum);
                LogUtils.d("x5-ip-list", ((StringBuilder)localObject2).toString());
                localObject2 = new StringBuilder();
                ((StringBuilder)localObject2).append("ip-list-_HTTPSTUNNEL-iLifePeriod = ");
                ((StringBuilder)localObject2).append(localJoinIPInfo.iLifePeriod);
                LogUtils.d("x5-ip-list", ((StringBuilder)localObject2).toString());
                paramInt = 0;
              }
            }
            else
            {
              localObject2 = this.mServiceManager;
              if (localObject2 != null) {
                ((IServiceManager)localObject2).onGetPushServerList(localJoinIPInfo.vIPList);
              }
              paramInt = 1;
            }
          }
          else
          {
            saveWupSocketList(localJoinIPInfo.vIPList);
            paramInt = 1;
          }
        }
        else
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append(paramITbsSmttServiceCallback.sTypeName);
          ((StringBuilder)localObject2).append(paramITbsSmttServiceCallback.iSubType);
          ((StringBuilder)localObject2).append(paramITbsSmttServiceCallback.sExtraInfo);
          ((StringBuilder)localObject2).append(paramITbsSmttServiceCallback.sMCCMNC);
          ((StringBuilder)localObject2).append("wup");
          localObject2 = ((StringBuilder)localObject2).toString();
          saveWupProxyList((String)localObject2, localJoinIPInfo.vIPList);
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("_WUPPROXY ");
          localStringBuilder.append(localJoinIPInfo.vIPList);
          localStringBuilder.append("   size=");
          localStringBuilder.append(localJoinIPInfo.vIPList.size());
          localStringBuilder.append(", netInfo=");
          localStringBuilder.append((String)localObject2);
          LogUtils.d("TesUserInfodns-ip poby", localStringBuilder.toString());
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("on Receive IPList in TBS, netinfo = ");
          localStringBuilder.append((String)localObject2);
          localStringBuilder.append(", onIPList: _WUPPROXY = ");
          localStringBuilder.append(localJoinIPInfo.vIPList);
          LogUtils.d("x5-ip-list", localStringBuilder.toString());
          paramInt = 0;
        }
        saveIPList(localJoinIPInfo.eIPType, localJoinIPInfo.vIPList);
      }
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(paramWUPResponseBase);
      ((StringBuilder)localObject1).append("}");
      paramWUPResponseBase = ((StringBuilder)localObject1).toString();
      if (paramITbsSmttServiceCallback.iProxyType == 0) {
        this.mProxyType = 1;
      } else {
        this.mProxyType = 1;
      }
      TbsUserInfoDataProvider.getInstance().addUserInfoData(1022, Integer.valueOf(this.mProxyType));
      saveQProxyListToJson();
      if (paramInt != 0)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("setBackProxyState apn=");
        ((StringBuilder)localObject1).append(paramITbsSmttServiceCallback.sApn);
        ((StringBuilder)localObject1).append(" ipRsp.bProxy=");
        ((StringBuilder)localObject1).append(paramITbsSmttServiceCallback.bProxy);
        LogUtils.d("x5-ip-list", ((StringBuilder)localObject1).toString());
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("Options=onIPList; eIPType=");
        ((StringBuilder)localObject1).append(paramWUPResponseBase);
        ((StringBuilder)localObject1).append(" iProxyType=");
        ((StringBuilder)localObject1).append(paramITbsSmttServiceCallback.iProxyType);
        ((StringBuilder)localObject1).append(" ipRsp.Apn=");
        ((StringBuilder)localObject1).append(paramITbsSmttServiceCallback.sApn);
        ((StringBuilder)localObject1).append("; ipRsp.bProxy=");
        ((StringBuilder)localObject1).append(paramITbsSmttServiceCallback.bProxy);
        appendUserinfoLiveLog(((StringBuilder)localObject1).toString());
        NetworkUtils.setBackProxyState(paramITbsSmttServiceCallback.sApn, paramITbsSmttServiceCallback.bProxy);
        PublicSettingManager.getInstance().setHasEverLogin(true);
        saveQProtocolFlagToJson();
        TbsUserInfoDataProvider.getInstance().saveUserInfoData();
      }
    }
    else
    {
      appendUserinfoLiveLog("onIPList result_error");
    }
  }
  
  public void onIPListFail(boolean paramBoolean, String paramString)
  {
    paramString = new StringBuilder();
    paramString.append("ip-list fetching failed, try again, onlyQProxy=");
    paramString.append(paramBoolean);
    LogUtils.d("x5-ip-list", paramString.toString());
    tryGetIPList(Apn.getApnTypeS(), false, paramBoolean, false);
  }
  
  public void onReceivedAdBlockSource(WUPResponseBase paramWUPResponseBase)
  {
    Object localObject = paramWUPResponseBase.getReturnCode();
    if ((localObject != null) && (((Integer)localObject).intValue() >= 0))
    {
      localObject = (GetAdBlockSourceRsp)paramWUPResponseBase.get("rsp");
      if (localObject == null)
      {
        LogUtils.d("AdFilter", "onReceivedAdBlockSource GetAdFilterSourceReq is Null");
        appendLiveLog("onReceivedAdBlockSource GetAdFilterSourceReq is Null");
        return;
      }
      if (((GetAdBlockSourceRsp)localObject).getIRet() != 0)
      {
        paramWUPResponseBase = new StringBuilder();
        paramWUPResponseBase.append("onReceivedAdBlockSource respCode=");
        paramWUPResponseBase.append(((GetAdBlockSourceRsp)localObject).getIRet());
        paramWUPResponseBase.append(" abnormal.");
        LogUtils.d("AdFilter", paramWUPResponseBase.toString());
        paramWUPResponseBase = new StringBuilder();
        paramWUPResponseBase.append("onReceivedAdBlockSource respCode=");
        paramWUPResponseBase.append(((GetAdBlockSourceRsp)localObject).getIRet());
        paramWUPResponseBase.append(" abnormal.");
        appendLiveLog(paramWUPResponseBase.toString());
        return;
      }
      paramWUPResponseBase = new StringBuilder();
      paramWUPResponseBase.append("onReceivedAdBlockSource getEUseType=");
      paramWUPResponseBase.append(((GetAdBlockSourceRsp)localObject).getEUseType());
      LogUtils.d("AdFilter", paramWUPResponseBase.toString());
      paramWUPResponseBase = new StringBuilder();
      paramWUPResponseBase.append("onReceivedAdBlockSource getEUseType=");
      paramWUPResponseBase.append(((GetAdBlockSourceRsp)localObject).getEUseType());
      appendLiveLog(paramWUPResponseBase.toString());
      StringBuilder localStringBuilder;
      if (((GetAdBlockSourceRsp)localObject).getEUseType() == 1)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("onReceivedAdBlockSource = _ET_DOWNLOAD mAdBlockSourceMD5=");
        localStringBuilder.append(this.mAdBlockSourceMD5);
        localStringBuilder.append(" new md5=");
        if (((GetAdBlockSourceRsp)localObject).getStFile() != null) {
          paramWUPResponseBase = ((GetAdBlockSourceRsp)localObject).getStFile().sMd5;
        } else {
          paramWUPResponseBase = "[AdBlockSource is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" url=");
        if (((GetAdBlockSourceRsp)localObject).getStFile() != null) {
          paramWUPResponseBase = ((GetAdBlockSourceRsp)localObject).getStFile().getSDownloadUrl();
        } else {
          paramWUPResponseBase = "[AdBlockSource is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" EState=");
        if (((GetAdBlockSourceRsp)localObject).getStFile() != null) {
          paramWUPResponseBase = Integer.valueOf(((GetAdBlockSourceRsp)localObject).getStFile().getEState());
        } else {
          paramWUPResponseBase = "[AdBlockSource is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        LogUtils.d("AdFilter", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("onReceivedAdBlockSource = _ET_DOWNLOAD mAdBlockSourceMD5=");
        localStringBuilder.append(this.mAdBlockSourceMD5);
        localStringBuilder.append(" new md5=");
        if (((GetAdBlockSourceRsp)localObject).getStFile() != null) {
          paramWUPResponseBase = ((GetAdBlockSourceRsp)localObject).getStFile().sMd5;
        } else {
          paramWUPResponseBase = "[AdBlockSource is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" url=");
        if (((GetAdBlockSourceRsp)localObject).getStFile() != null) {
          paramWUPResponseBase = ((GetAdBlockSourceRsp)localObject).getStFile().getSDownloadUrl();
        } else {
          paramWUPResponseBase = "[AdBlockSource is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" EState=");
        if (((GetAdBlockSourceRsp)localObject).getStFile() != null) {
          paramWUPResponseBase = Integer.valueOf(((GetAdBlockSourceRsp)localObject).getStFile().getEState());
        } else {
          paramWUPResponseBase = "[AdBlockSource is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        appendLiveLog(localStringBuilder.toString());
        if ((((GetAdBlockSourceRsp)localObject).getStFile() != null) && (((GetAdBlockSourceRsp)localObject).getStFile().getEState() != 3)) {
          if (!this.mAdBlockSourceMD5.equalsIgnoreCase(((GetAdBlockSourceRsp)localObject).getStFile().sMd5))
          {
            this.mAdBlockSourceMD5 = ((GetAdBlockSourceRsp)localObject).getStFile().sMd5;
            if (TbsFileUtils.getAdBlockSourceFile(TbsBaseModuleShell.getCallerContext()).exists()) {
              TbsFileUtils.getAdBlockSourceFile(TbsBaseModuleShell.getCallerContext()).delete();
            }
            fetchAdBlockSourceResource(((GetAdBlockSourceRsp)localObject).getStFile().getSDownloadUrl());
          }
          else if (!TbsFileUtils.getAdBlockSourceFile(TbsBaseModuleShell.getCallerContext()).exists())
          {
            fetchAdBlockSourceResource(((GetAdBlockSourceRsp)localObject).getStFile().getSDownloadUrl());
          }
        }
      }
      else
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("onReceivedAdBlockSource = _ET_USE_DIRECTmAdBlockSourceMD5=");
        localStringBuilder.append(this.mAdBlockSourceMD5);
        localStringBuilder.append(" new md5=");
        if (((GetAdBlockSourceRsp)localObject).getStFile() != null) {
          paramWUPResponseBase = ((GetAdBlockSourceRsp)localObject).getStFile().sMd5;
        } else {
          paramWUPResponseBase = "[AdBlockSource is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" AdBlockSource.size=");
        if (((GetAdBlockSourceRsp)localObject).getStFile() != null) {
          paramWUPResponseBase = Integer.valueOf(((GetAdBlockSourceRsp)localObject).getStFile().iFileSize);
        } else {
          paramWUPResponseBase = "[AdBlockSource is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" EState=");
        if (((GetAdBlockSourceRsp)localObject).getStFile() != null) {
          paramWUPResponseBase = Integer.valueOf(((GetAdBlockSourceRsp)localObject).getStFile().getEState());
        } else {
          paramWUPResponseBase = "[AdBlockSource is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        LogUtils.d("AdFilter", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("onReceivedAdBlockSource = _ET_USE_DIRECTmAdBlockSourceMD5=");
        localStringBuilder.append(this.mAdBlockSourceMD5);
        localStringBuilder.append(" new md5=");
        if (((GetAdBlockSourceRsp)localObject).getStFile() != null) {
          paramWUPResponseBase = ((GetAdBlockSourceRsp)localObject).getStFile().sMd5;
        } else {
          paramWUPResponseBase = "[AdBlockSource is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" AdBlockSource.size=");
        if (((GetAdBlockSourceRsp)localObject).getStFile() != null) {
          paramWUPResponseBase = Integer.valueOf(((GetAdBlockSourceRsp)localObject).getStFile().iFileSize);
        } else {
          paramWUPResponseBase = "[AdBlockSource is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" EState=");
        if (((GetAdBlockSourceRsp)localObject).getStFile() != null) {
          paramWUPResponseBase = Integer.valueOf(((GetAdBlockSourceRsp)localObject).getStFile().getEState());
        } else {
          paramWUPResponseBase = "[AdBlockSource is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        appendLiveLog(localStringBuilder.toString());
        if ((((GetAdBlockSourceRsp)localObject).getStFile() != null) && (((GetAdBlockSourceRsp)localObject).getStFile().getEState() != 3)) {
          if (!this.mAdBlockSourceMD5.equalsIgnoreCase(((GetAdBlockSourceRsp)localObject).getStFile().sMd5))
          {
            this.mAdBlockSourceMD5 = ((GetAdBlockSourceRsp)localObject).getStFile().sMd5;
            if (TbsFileUtils.getAdBlockSourceFile(TbsBaseModuleShell.getCallerContext()).exists()) {
              TbsFileUtils.getAdBlockSourceFile(TbsBaseModuleShell.getCallerContext()).delete();
            }
            saveRspData(TbsFileUtils.getAdBlockSourceFile(TbsBaseModuleShell.getCallerContext()), ((GetAdBlockSourceRsp)localObject).getStFile().sFile);
          }
          else if (!TbsFileUtils.getAdBlockSourceFile(TbsBaseModuleShell.getCallerContext()).exists())
          {
            this.mAdBlockSourceMD5 = "";
          }
        }
      }
      saveAdBlockSourceMD5();
      TbsUserInfoDataProvider.getInstance().saveUserInfoData();
      return;
    }
    paramWUPResponseBase = new StringBuilder();
    paramWUPResponseBase.append("onReceivedAdBlockSource response returnCode=");
    paramWUPResponseBase.append(localObject);
    paramWUPResponseBase.append(" abnormal.");
    LogUtils.d("AdFilter", paramWUPResponseBase.toString());
    paramWUPResponseBase = new StringBuilder();
    paramWUPResponseBase.append("onReceivedAdBlockSource response returnCode=");
    paramWUPResponseBase.append(localObject);
    paramWUPResponseBase.append(" abnormal.");
    appendLiveLog(paramWUPResponseBase.toString());
  }
  
  public void onReceivedHTTPSTunnelToken(WUPResponseBase paramWUPResponseBase)
  {
    Object localObject = paramWUPResponseBase.getReturnCode();
    if ((localObject != null) && (((Integer)localObject).intValue() >= 0))
    {
      paramWUPResponseBase = (TokenInfoRsp)paramWUPResponseBase.get("rsp");
      if (paramWUPResponseBase == null)
      {
        LogUtils.d("TheSimCardOfGreatKing", "onReceivedHTTPSTunnelToken TokenInfoRsp is Null");
        return;
      }
      if (paramWUPResponseBase.getRspCode() != 0)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("onReceivedHTTPSTunnelToken respCode=");
        ((StringBuilder)localObject).append(paramWUPResponseBase.getRspCode());
        ((StringBuilder)localObject).append(" abnormal.");
        LogUtils.d("TheSimCardOfGreatKing", ((StringBuilder)localObject).toString());
        return;
      }
      saveHTTPSTunnelToken(paramWUPResponseBase.getSToken(), paramWUPResponseBase.getIExpireTime());
      return;
    }
    paramWUPResponseBase = new StringBuilder();
    paramWUPResponseBase.append("onReceivedHTTPSTunnelToken response returnCode=");
    paramWUPResponseBase.append(localObject);
    paramWUPResponseBase.append(" abnormal.");
    LogUtils.d("TheSimCardOfGreatKing", paramWUPResponseBase.toString());
  }
  
  public void onReceivedSmartAdfitler(WUPResponseBase paramWUPResponseBase)
  {
    Object localObject = paramWUPResponseBase.getReturnCode();
    if ((localObject != null) && (((Integer)localObject).intValue() >= 0))
    {
      localObject = (GetAdFilterSourceRsp)paramWUPResponseBase.get("rsp");
      if (localObject == null)
      {
        LogUtils.d("AdFilter", "onReceivedSmartAdfitler GetAdFilterSourceReq is Null");
        appendLiveLog("onReceivedSmartAdfitler GetAdFilterSourceReq is Null");
        return;
      }
      if (((GetAdFilterSourceRsp)localObject).getIRet() != 0)
      {
        paramWUPResponseBase = new StringBuilder();
        paramWUPResponseBase.append("onReceivedSmartAdfitler respCode=");
        paramWUPResponseBase.append(((GetAdFilterSourceRsp)localObject).getIRet());
        paramWUPResponseBase.append(" abnormal.");
        LogUtils.d("AdFilter", paramWUPResponseBase.toString());
        paramWUPResponseBase = new StringBuilder();
        paramWUPResponseBase.append("onReceivedSmartAdfitler respCode=");
        paramWUPResponseBase.append(((GetAdFilterSourceRsp)localObject).getIRet());
        paramWUPResponseBase.append(" abnormal.");
        appendLiveLog(paramWUPResponseBase.toString());
        return;
      }
      paramWUPResponseBase = new StringBuilder();
      paramWUPResponseBase.append("onReceivedSmartAdfitler getEUseType=");
      paramWUPResponseBase.append(((GetAdFilterSourceRsp)localObject).getEUseType());
      LogUtils.d("AdFilter", paramWUPResponseBase.toString());
      paramWUPResponseBase = new StringBuilder();
      paramWUPResponseBase.append("onReceivedSmartAdfitler getEUseType=");
      paramWUPResponseBase.append(((GetAdFilterSourceRsp)localObject).getEUseType());
      appendLiveLog(paramWUPResponseBase.toString());
      StringBuilder localStringBuilder;
      if (((GetAdFilterSourceRsp)localObject).getEUseType() == 1)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("onReceivedSmartAdfitler js! _ET_DOWNLOAD mAdJSMD5=");
        localStringBuilder.append(this.mAdJSMD5);
        localStringBuilder.append(" new md5=");
        if (((GetAdFilterSourceRsp)localObject).getStJs() != null) {
          paramWUPResponseBase = ((GetAdFilterSourceRsp)localObject).getStJs().sMd5;
        } else {
          paramWUPResponseBase = "[StJs is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" url=");
        if (((GetAdFilterSourceRsp)localObject).getStJs() != null) {
          paramWUPResponseBase = ((GetAdFilterSourceRsp)localObject).getStJs().getSDownloadUrl();
        } else {
          paramWUPResponseBase = "[StJs is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" EState=");
        if (((GetAdFilterSourceRsp)localObject).getStJs() != null) {
          paramWUPResponseBase = Integer.valueOf(((GetAdFilterSourceRsp)localObject).getStJs().getEState());
        } else {
          paramWUPResponseBase = "[StJs is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        LogUtils.d("AdFilter", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("onReceivedSmartAdfitler js! _ET_DOWNLOAD mAdJSMD5=");
        localStringBuilder.append(this.mAdJSMD5);
        localStringBuilder.append(" new md5=");
        if (((GetAdFilterSourceRsp)localObject).getStJs() != null) {
          paramWUPResponseBase = ((GetAdFilterSourceRsp)localObject).getStJs().sMd5;
        } else {
          paramWUPResponseBase = "[StJs is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" url=");
        if (((GetAdFilterSourceRsp)localObject).getStJs() != null) {
          paramWUPResponseBase = ((GetAdFilterSourceRsp)localObject).getStJs().getSDownloadUrl();
        } else {
          paramWUPResponseBase = "[StJs is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" EState=");
        if (((GetAdFilterSourceRsp)localObject).getStJs() != null) {
          paramWUPResponseBase = Integer.valueOf(((GetAdFilterSourceRsp)localObject).getStJs().getEState());
        } else {
          paramWUPResponseBase = "[StJs is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        appendLiveLog(localStringBuilder.toString());
        if ((((GetAdFilterSourceRsp)localObject).getStJs() != null) && (((GetAdFilterSourceRsp)localObject).getStJs().getEState() != 3)) {
          if (!this.mAdJSMD5.equalsIgnoreCase(((GetAdFilterSourceRsp)localObject).getStJs().sMd5))
          {
            this.mAdJSMD5 = ((GetAdFilterSourceRsp)localObject).getStJs().sMd5;
            if (TbsFileUtils.getSAFJFile(TbsBaseModuleShell.getCallerContext()).exists()) {
              TbsFileUtils.getSAFJFile(TbsBaseModuleShell.getCallerContext()).delete();
            }
            fetchSmartAdFitlerResource(0, ((GetAdFilterSourceRsp)localObject).getStJs().getSDownloadUrl());
          }
          else if (!TbsFileUtils.getSAFJFile(TbsBaseModuleShell.getCallerContext()).exists())
          {
            fetchSmartAdFitlerResource(0, ((GetAdFilterSourceRsp)localObject).getStJs().getSDownloadUrl());
          }
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("onReceivedSmartAdfitler model! _ET_DOWNLOAD mModuleMD5=");
        localStringBuilder.append(this.mModuleMD5);
        localStringBuilder.append(" new md5=");
        if (((GetAdFilterSourceRsp)localObject).getStModel() != null) {
          paramWUPResponseBase = ((GetAdFilterSourceRsp)localObject).getStModel().sMd5;
        } else {
          paramWUPResponseBase = "[StModel is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" url=");
        if (((GetAdFilterSourceRsp)localObject).getStModel() != null) {
          paramWUPResponseBase = ((GetAdFilterSourceRsp)localObject).getStModel().getSDownloadUrl();
        } else {
          paramWUPResponseBase = "[StModel is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" EState=");
        if (((GetAdFilterSourceRsp)localObject).getStModel() != null) {
          paramWUPResponseBase = Integer.valueOf(((GetAdFilterSourceRsp)localObject).getStModel().getEState());
        } else {
          paramWUPResponseBase = "[StModel is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        LogUtils.d("AdFilter", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("onReceivedSmartAdfitler model! _ET_DOWNLOAD mModuleMD5=");
        localStringBuilder.append(this.mModuleMD5);
        localStringBuilder.append(" new md5=");
        if (((GetAdFilterSourceRsp)localObject).getStModel() != null) {
          paramWUPResponseBase = ((GetAdFilterSourceRsp)localObject).getStModel().sMd5;
        } else {
          paramWUPResponseBase = "[StModel is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" url=");
        if (((GetAdFilterSourceRsp)localObject).getStModel() != null) {
          paramWUPResponseBase = ((GetAdFilterSourceRsp)localObject).getStModel().getSDownloadUrl();
        } else {
          paramWUPResponseBase = "[StModel is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" EState=");
        if (((GetAdFilterSourceRsp)localObject).getStModel() != null) {
          paramWUPResponseBase = Integer.valueOf(((GetAdFilterSourceRsp)localObject).getStModel().getEState());
        } else {
          paramWUPResponseBase = "[StModel is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        appendLiveLog(localStringBuilder.toString());
        if ((((GetAdFilterSourceRsp)localObject).getStModel() != null) && (((GetAdFilterSourceRsp)localObject).getStModel().getEState() != 3)) {
          if (!this.mModuleMD5.equalsIgnoreCase(((GetAdFilterSourceRsp)localObject).getStModel().sMd5))
          {
            this.mModuleMD5 = ((GetAdFilterSourceRsp)localObject).getStModel().sMd5;
            if (TbsFileUtils.getSAFMFile(TbsBaseModuleShell.getCallerContext()).exists()) {
              TbsFileUtils.getSAFMFile(TbsBaseModuleShell.getCallerContext()).delete();
            }
            fetchSmartAdFitlerResource(1, ((GetAdFilterSourceRsp)localObject).getStModel().getSDownloadUrl());
          }
          else if (!TbsFileUtils.getSAFMFile(TbsBaseModuleShell.getCallerContext()).exists())
          {
            fetchSmartAdFitlerResource(1, ((GetAdFilterSourceRsp)localObject).getStModel().getSDownloadUrl());
          }
        }
      }
      else
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("onReceivedSmartAdfitler js! mAdJSMD5=");
        localStringBuilder.append(this.mAdJSMD5);
        localStringBuilder.append(" new md5=");
        if (((GetAdFilterSourceRsp)localObject).getStJs() != null) {
          paramWUPResponseBase = ((GetAdFilterSourceRsp)localObject).getStJs().sMd5;
        } else {
          paramWUPResponseBase = "[StJs is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" js.size=");
        if (((GetAdFilterSourceRsp)localObject).getStJs() != null) {
          paramWUPResponseBase = Integer.valueOf(((GetAdFilterSourceRsp)localObject).getStJs().iFileSize);
        } else {
          paramWUPResponseBase = "[StJs is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" EState=");
        if (((GetAdFilterSourceRsp)localObject).getStJs() != null) {
          paramWUPResponseBase = Integer.valueOf(((GetAdFilterSourceRsp)localObject).getStJs().getEState());
        } else {
          paramWUPResponseBase = "[StJs is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        LogUtils.d("AdFilter", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("onReceivedSmartAdfitler js! mAdJSMD5=");
        localStringBuilder.append(this.mAdJSMD5);
        localStringBuilder.append(" new md5=");
        if (((GetAdFilterSourceRsp)localObject).getStJs() != null) {
          paramWUPResponseBase = ((GetAdFilterSourceRsp)localObject).getStJs().sMd5;
        } else {
          paramWUPResponseBase = "[StJs is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" js.size=");
        if (((GetAdFilterSourceRsp)localObject).getStJs() != null) {
          paramWUPResponseBase = Integer.valueOf(((GetAdFilterSourceRsp)localObject).getStJs().iFileSize);
        } else {
          paramWUPResponseBase = "[StJs is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" EState=");
        if (((GetAdFilterSourceRsp)localObject).getStJs() != null) {
          paramWUPResponseBase = Integer.valueOf(((GetAdFilterSourceRsp)localObject).getStJs().getEState());
        } else {
          paramWUPResponseBase = "[StJs is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        appendLiveLog(localStringBuilder.toString());
        if ((((GetAdFilterSourceRsp)localObject).getStJs() != null) && (((GetAdFilterSourceRsp)localObject).getStJs().getEState() != 3)) {
          if (!this.mAdJSMD5.equalsIgnoreCase(((GetAdFilterSourceRsp)localObject).getStJs().sMd5))
          {
            this.mAdJSMD5 = ((GetAdFilterSourceRsp)localObject).getStJs().sMd5;
            if (TbsFileUtils.getSAFJFile(TbsBaseModuleShell.getCallerContext()).exists()) {
              TbsFileUtils.getSAFJFile(TbsBaseModuleShell.getCallerContext()).delete();
            }
            saveRspData(TbsFileUtils.getSAFJFile(TbsBaseModuleShell.getCallerContext()), ((GetAdFilterSourceRsp)localObject).getStJs().sFile);
          }
          else if (!TbsFileUtils.getSAFJFile(TbsBaseModuleShell.getCallerContext()).exists())
          {
            this.mAdJSMD5 = "";
          }
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("onReceivedSmartAdfitler model! _ET_DOWNLOAD mModuleMD5=");
        localStringBuilder.append(this.mModuleMD5);
        localStringBuilder.append(" new md5=");
        if (((GetAdFilterSourceRsp)localObject).getStModel() != null) {
          paramWUPResponseBase = ((GetAdFilterSourceRsp)localObject).getStModel().sMd5;
        } else {
          paramWUPResponseBase = "[StModel is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" model.size=");
        if (((GetAdFilterSourceRsp)localObject).getStModel() != null) {
          paramWUPResponseBase = Integer.valueOf(((GetAdFilterSourceRsp)localObject).getStModel().iFileSize);
        } else {
          paramWUPResponseBase = "[StModel is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" EState=");
        if (((GetAdFilterSourceRsp)localObject).getStModel() != null) {
          paramWUPResponseBase = Integer.valueOf(((GetAdFilterSourceRsp)localObject).getStModel().getEState());
        } else {
          paramWUPResponseBase = "[StModel is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        LogUtils.d("AdFilter", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("onReceivedSmartAdfitler model! _ET_DOWNLOAD mModuleMD5=");
        localStringBuilder.append(this.mModuleMD5);
        localStringBuilder.append(" new md5=");
        if (((GetAdFilterSourceRsp)localObject).getStModel() != null) {
          paramWUPResponseBase = ((GetAdFilterSourceRsp)localObject).getStModel().sMd5;
        } else {
          paramWUPResponseBase = "[StModel is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" model.size=");
        if (((GetAdFilterSourceRsp)localObject).getStModel() != null) {
          paramWUPResponseBase = Integer.valueOf(((GetAdFilterSourceRsp)localObject).getStModel().iFileSize);
        } else {
          paramWUPResponseBase = "[StModel is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        localStringBuilder.append(" EState=");
        if (((GetAdFilterSourceRsp)localObject).getStModel() != null) {
          paramWUPResponseBase = Integer.valueOf(((GetAdFilterSourceRsp)localObject).getStModel().getEState());
        } else {
          paramWUPResponseBase = "[StModel is null]";
        }
        localStringBuilder.append(paramWUPResponseBase);
        appendLiveLog(localStringBuilder.toString());
        if ((((GetAdFilterSourceRsp)localObject).getStModel() != null) && (((GetAdFilterSourceRsp)localObject).getStModel().getEState() != 3)) {
          if (!this.mModuleMD5.equalsIgnoreCase(((GetAdFilterSourceRsp)localObject).getStModel().sMd5))
          {
            this.mModuleMD5 = ((GetAdFilterSourceRsp)localObject).getStModel().sMd5;
            if (TbsFileUtils.getSAFMFile(TbsBaseModuleShell.getCallerContext()).exists()) {
              TbsFileUtils.getSAFMFile(TbsBaseModuleShell.getCallerContext()).delete();
            }
            saveRspData(TbsFileUtils.getSAFMFile(TbsBaseModuleShell.getCallerContext()), ((GetAdFilterSourceRsp)localObject).getStModel().sFile);
          }
          else if (!TbsFileUtils.getSAFMFile(TbsBaseModuleShell.getCallerContext()).exists())
          {
            this.mModuleMD5 = "";
          }
        }
      }
      saveADMD5();
      TbsUserInfoDataProvider.getInstance().saveUserInfoData();
      return;
    }
    paramWUPResponseBase = new StringBuilder();
    paramWUPResponseBase.append("onReceivedSmartAdfitler response returnCode=");
    paramWUPResponseBase.append(localObject);
    paramWUPResponseBase.append(" abnormal.");
    LogUtils.d("AdFilter", paramWUPResponseBase.toString());
    paramWUPResponseBase = new StringBuilder();
    paramWUPResponseBase.append("onReceivedSmartAdfitler response returnCode=");
    paramWUPResponseBase.append(localObject);
    paramWUPResponseBase.append(" abnormal.");
    appendLiveLog(paramWUPResponseBase.toString());
  }
  
  public void onReceivedSubsetAdRule(WUPResponseBase paramWUPResponseBase)
  {
    Object localObject1 = paramWUPResponseBase.getReturnCode();
    if ((localObject1 != null) && (((Integer)localObject1).intValue() >= 0))
    {
      paramWUPResponseBase = (GetSubsetAdRuleRsp)paramWUPResponseBase.get("rsp");
      if (paramWUPResponseBase == null)
      {
        LogUtils.d("AdFilter", "onReceivedSubsetAdRule GetSubsetAdRuleRsp is Null");
        appendLiveLog("onReceivedSubsetAdRule GetSubsetAdRuleRsp is Null");
        return;
      }
      if (paramWUPResponseBase.getIRet() != 0)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("onReceivedSubsetAdRule respCode=");
        ((StringBuilder)localObject1).append(paramWUPResponseBase.getIRet());
        ((StringBuilder)localObject1).append(" abnormal.");
        LogUtils.d("AdFilter", ((StringBuilder)localObject1).toString());
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("onReceivedSubsetAdRule respCode=");
        ((StringBuilder)localObject1).append(paramWUPResponseBase.getIRet());
        ((StringBuilder)localObject1).append(" abnormal.");
        appendLiveLog(((StringBuilder)localObject1).toString());
        return;
      }
      if (paramWUPResponseBase.getIRet() == 0)
      {
        this.mSubsetAdRuleMD5 = paramWUPResponseBase.getSMd5();
        if (TbsFileUtils.getSubsetAdRuleFile(TbsBaseModuleShell.getCallerContext()).exists()) {
          TbsFileUtils.getSubsetAdRuleFile(TbsBaseModuleShell.getCallerContext()).delete();
        }
        Object localObject3 = paramWUPResponseBase.getMapClientHideRule();
        Object localObject5 = paramWUPResponseBase.getMapClientFilterRule();
        Object localObject4 = paramWUPResponseBase.getMapClientFeatureRule();
        Object localObject2 = new ArrayList();
        localObject1 = new ArrayList();
        if (paramWUPResponseBase.getStCRExtraAdRule() != null)
        {
          localObject2 = paramWUPResponseBase.getStCRExtraAdRule().getVHideRule();
          localObject1 = paramWUPResponseBase.getStCRExtraAdRule().getVFilterRule();
        }
        paramWUPResponseBase = "";
        Object localObject6 = ((Map)localObject3).entrySet().iterator();
        int k;
        int j;
        for (;;)
        {
          boolean bool = ((Iterator)localObject6).hasNext();
          k = 0;
          i = 0;
          if (!bool) {
            break;
          }
          localObject3 = (Map.Entry)((Iterator)localObject6).next();
          Object localObject7 = new StringBuilder();
          ((StringBuilder)localObject7).append(paramWUPResponseBase);
          ((StringBuilder)localObject7).append("1 ");
          paramWUPResponseBase = ((StringBuilder)localObject7).toString();
          localObject7 = new StringBuilder();
          ((StringBuilder)localObject7).append(paramWUPResponseBase);
          ((StringBuilder)localObject7).append((String)((Map.Entry)localObject3).getKey());
          paramWUPResponseBase = ((StringBuilder)localObject7).toString();
          localObject7 = new StringBuilder();
          ((StringBuilder)localObject7).append(paramWUPResponseBase);
          ((StringBuilder)localObject7).append(" ");
          paramWUPResponseBase = ((StringBuilder)localObject7).toString();
          localObject7 = (ArrayList)((Map.Entry)localObject3).getValue();
          while (i < ((ArrayList)localObject7).size())
          {
            localObject3 = new StringBuilder();
            ((StringBuilder)localObject3).append(paramWUPResponseBase);
            ((StringBuilder)localObject3).append((String)((ArrayList)localObject7).get(i));
            localObject3 = ((StringBuilder)localObject3).toString();
            j = i + 1;
            i = j;
            paramWUPResponseBase = (WUPResponseBase)localObject3;
            if (j != ((ArrayList)localObject7).size())
            {
              paramWUPResponseBase = new StringBuilder();
              paramWUPResponseBase.append((String)localObject3);
              paramWUPResponseBase.append(",");
              paramWUPResponseBase = paramWUPResponseBase.toString();
              i = j;
            }
          }
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append(paramWUPResponseBase);
          ((StringBuilder)localObject3).append("\n");
          paramWUPResponseBase = ((StringBuilder)localObject3).toString();
        }
        localObject3 = ((Map)localObject5).entrySet().iterator();
        while (((Iterator)localObject3).hasNext())
        {
          localObject5 = (Map.Entry)((Iterator)localObject3).next();
          localObject6 = new StringBuilder();
          ((StringBuilder)localObject6).append(paramWUPResponseBase);
          ((StringBuilder)localObject6).append("2 ");
          paramWUPResponseBase = ((StringBuilder)localObject6).toString();
          localObject6 = new StringBuilder();
          ((StringBuilder)localObject6).append(paramWUPResponseBase);
          ((StringBuilder)localObject6).append((String)((Map.Entry)localObject5).getKey());
          paramWUPResponseBase = ((StringBuilder)localObject6).toString();
          localObject6 = new StringBuilder();
          ((StringBuilder)localObject6).append(paramWUPResponseBase);
          ((StringBuilder)localObject6).append(" ");
          paramWUPResponseBase = ((StringBuilder)localObject6).toString();
          localObject5 = (ArrayList)((Map.Entry)localObject5).getValue();
          i = 0;
          while (i < ((ArrayList)localObject5).size())
          {
            localObject6 = new StringBuilder();
            ((StringBuilder)localObject6).append(paramWUPResponseBase);
            ((StringBuilder)localObject6).append((String)((ArrayList)localObject5).get(i));
            paramWUPResponseBase = ((StringBuilder)localObject6).toString();
            localObject6 = new StringBuilder();
            ((StringBuilder)localObject6).append(paramWUPResponseBase);
            ((StringBuilder)localObject6).append(" ");
            paramWUPResponseBase = ((StringBuilder)localObject6).toString();
            i += 1;
          }
          localObject5 = new StringBuilder();
          ((StringBuilder)localObject5).append(paramWUPResponseBase);
          ((StringBuilder)localObject5).append("\n");
          paramWUPResponseBase = ((StringBuilder)localObject5).toString();
        }
        localObject3 = ((Map)localObject4).entrySet().iterator();
        while (((Iterator)localObject3).hasNext())
        {
          localObject4 = (Map.Entry)((Iterator)localObject3).next();
          localObject5 = new StringBuilder();
          ((StringBuilder)localObject5).append(paramWUPResponseBase);
          ((StringBuilder)localObject5).append("3 ");
          paramWUPResponseBase = ((StringBuilder)localObject5).toString();
          localObject5 = new StringBuilder();
          ((StringBuilder)localObject5).append(paramWUPResponseBase);
          ((StringBuilder)localObject5).append((String)((Map.Entry)localObject4).getKey());
          paramWUPResponseBase = ((StringBuilder)localObject5).toString();
          localObject5 = new StringBuilder();
          ((StringBuilder)localObject5).append(paramWUPResponseBase);
          ((StringBuilder)localObject5).append(" ");
          paramWUPResponseBase = ((StringBuilder)localObject5).toString();
          localObject4 = (ArrayList)((Map.Entry)localObject4).getValue();
          i = 0;
          while (i < ((ArrayList)localObject4).size())
          {
            localObject5 = new StringBuilder();
            ((StringBuilder)localObject5).append(paramWUPResponseBase);
            ((StringBuilder)localObject5).append((String)((ArrayList)localObject4).get(i));
            paramWUPResponseBase = ((StringBuilder)localObject5).toString();
            localObject5 = new StringBuilder();
            ((StringBuilder)localObject5).append(paramWUPResponseBase);
            ((StringBuilder)localObject5).append(" ");
            paramWUPResponseBase = ((StringBuilder)localObject5).toString();
            i += 1;
          }
          localObject4 = new StringBuilder();
          ((StringBuilder)localObject4).append(paramWUPResponseBase);
          ((StringBuilder)localObject4).append("\n");
          paramWUPResponseBase = ((StringBuilder)localObject4).toString();
        }
        int i = 0;
        for (;;)
        {
          localObject3 = paramWUPResponseBase;
          j = k;
          if (i >= ((ArrayList)localObject2).size()) {
            break;
          }
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append(paramWUPResponseBase);
          ((StringBuilder)localObject3).append("4 ");
          paramWUPResponseBase = ((StringBuilder)localObject3).toString();
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append(paramWUPResponseBase);
          ((StringBuilder)localObject3).append((String)((ArrayList)localObject2).get(i));
          paramWUPResponseBase = ((StringBuilder)localObject3).toString();
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("onReceivedSubsetAdRule adultHideRule=");
          ((StringBuilder)localObject3).append((String)((ArrayList)localObject2).get(i));
          ((StringBuilder)localObject3).toString();
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append(paramWUPResponseBase);
          ((StringBuilder)localObject3).append("\n");
          paramWUPResponseBase = ((StringBuilder)localObject3).toString();
          i += 1;
        }
        while (j < ((ArrayList)localObject1).size())
        {
          paramWUPResponseBase = new StringBuilder();
          paramWUPResponseBase.append((String)localObject3);
          paramWUPResponseBase.append("5 ");
          paramWUPResponseBase = paramWUPResponseBase.toString();
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append(paramWUPResponseBase);
          ((StringBuilder)localObject2).append((String)((ArrayList)localObject1).get(j));
          paramWUPResponseBase = ((StringBuilder)localObject2).toString();
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("onReceivedSubsetAdRule adultFilterRule=");
          ((StringBuilder)localObject2).append((String)((ArrayList)localObject1).get(j));
          ((StringBuilder)localObject2).toString();
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append(paramWUPResponseBase);
          ((StringBuilder)localObject2).append("\n");
          localObject3 = ((StringBuilder)localObject2).toString();
          j += 1;
        }
        saveSubsetAdRuleToFile(TbsFileUtils.getSubsetAdRuleFile(TbsBaseModuleShell.getCallerContext()), (String)localObject3);
      }
      else if (!TbsFileUtils.getSubsetAdRuleFile(TbsBaseModuleShell.getCallerContext()).exists())
      {
        this.mSubsetAdRuleMD5 = "";
      }
      saveSubsetAdRuleMD5();
      TbsUserInfoDataProvider.getInstance().saveUserInfoData();
      return;
    }
    paramWUPResponseBase = new StringBuilder();
    paramWUPResponseBase.append("onReceivedSubsetAdRule response returnCode=");
    paramWUPResponseBase.append(localObject1);
    paramWUPResponseBase.append(" abnormal.");
    LogUtils.d("AdFilter", paramWUPResponseBase.toString());
    paramWUPResponseBase = new StringBuilder();
    paramWUPResponseBase.append("onReceivedSubsetAdRule response returnCode=");
    paramWUPResponseBase.append(localObject1);
    paramWUPResponseBase.append(" abnormal.");
    appendLiveLog(paramWUPResponseBase.toString());
  }
  
  public void onWUPTaskFail(WUPRequestBase paramWUPRequestBase)
  {
    if (paramWUPRequestBase == null) {
      return;
    }
    StringBuilder localStringBuilder;
    switch (paramWUPRequestBase.getType())
    {
    case 57: 
    case 58: 
    case 59: 
    default: 
      return;
    case 61: 
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("===onWUPTaskFail=== WUP_ADBLOCK_SOURCE requestType=");
      localStringBuilder.append(paramWUPRequestBase.getType());
      LogUtils.d("AdFilter", localStringBuilder.toString());
      return;
    case 60: 
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("===onWUPTaskFail=== WUP_SMART_ADFILTER requestType=");
      localStringBuilder.append(paramWUPRequestBase.getType());
      LogUtils.d("AdFilter", localStringBuilder.toString());
      return;
    case 56: 
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("===onWUPTaskFail=== WUP_HTTPS_TUNNEL_TOKEN requestType=");
      localStringBuilder.append(paramWUPRequestBase.getType());
      LogUtils.d("TheSimCardOfGreatKing", localStringBuilder.toString());
      return;
    case 55: 
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("===onWUPTaskFail=== WUP_SCRIPT_TEMPLATE");
      localStringBuilder.append(paramWUPRequestBase.getType());
      LogUtils.d("TesUserInfo", localStringBuilder.toString());
      return;
    case 54: 
      onIPListFail(true, paramWUPRequestBase.getFailedReason());
      return;
    }
    onIPListFail(false, paramWUPRequestBase.getFailedReason());
  }
  
  public void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("===onWUPTaskSuccess=== Userinfo Iplist");
    localStringBuilder.append(paramWUPRequestBase.getType());
    LogUtils.d("WUPRequestCallBack", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("===onWUPTaskSuccess===");
    localStringBuilder.append(paramWUPRequestBase.getType());
    LogUtils.d("TesUserInfo", localStringBuilder.toString());
    switch (paramWUPRequestBase.getType())
    {
    case 57: 
    case 58: 
    case 59: 
    default: 
      
    case 62: 
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("===onWUPTaskSuccess=== WUP_SUBSET_ADRULE ");
      localStringBuilder.append(paramWUPRequestBase.getType());
      localStringBuilder.append(" response=");
      localStringBuilder.append(paramWUPResponseBase);
      LogUtils.d("AdFilter", localStringBuilder.toString());
      if (paramWUPResponseBase != null)
      {
        onReceivedSubsetAdRule(paramWUPResponseBase);
        return;
      }
      break;
    case 61: 
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("===onWUPTaskSuccess=== WUP_ADBLOCK_SOURCE ");
      localStringBuilder.append(paramWUPRequestBase.getType());
      localStringBuilder.append(" response=");
      localStringBuilder.append(paramWUPResponseBase);
      LogUtils.d("AdFilter", localStringBuilder.toString());
      if (paramWUPResponseBase != null)
      {
        onReceivedAdBlockSource(paramWUPResponseBase);
        return;
      }
      break;
    case 60: 
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("===onWUPTaskSuccess=== WUP_SMART_ADFILTER ");
      localStringBuilder.append(paramWUPRequestBase.getType());
      localStringBuilder.append(" response=");
      localStringBuilder.append(paramWUPResponseBase);
      LogUtils.d("AdFilter", localStringBuilder.toString());
      if (paramWUPResponseBase != null)
      {
        onReceivedSmartAdfitler(paramWUPResponseBase);
        return;
      }
      break;
    case 56: 
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("===onWUPTaskSuccess=== WUP_HTTPS_TUNNEL_TOKEN ");
      localStringBuilder.append(paramWUPRequestBase.getType());
      localStringBuilder.append(" response=");
      localStringBuilder.append(paramWUPResponseBase);
      LogUtils.d("TheSimCardOfGreatKing", localStringBuilder.toString());
      if (paramWUPResponseBase != null)
      {
        onReceivedHTTPSTunnelToken(paramWUPResponseBase);
        return;
      }
      break;
    case 55: 
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("===onWUPTaskSuccess=== WUP_SCRIPT_TEMPLATE ");
      localStringBuilder.append(paramWUPRequestBase.getType());
      localStringBuilder.append(" response=");
      localStringBuilder.append(paramWUPResponseBase);
      LogUtils.d("TesUserInfo", localStringBuilder.toString());
      if (paramWUPResponseBase != null)
      {
        onGetJsTemplate(paramWUPResponseBase);
        return;
      }
      break;
    case 53: 
    case 54: 
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("===onWUPTaskSuccess===WUP_REQUEST_TYPE_IP_LIST");
      localStringBuilder.append(paramWUPRequestBase.getType());
      localStringBuilder.append(" (response != null)=");
      boolean bool;
      if (paramWUPResponseBase != null) {
        bool = true;
      } else {
        bool = false;
      }
      localStringBuilder.append(bool);
      LogUtils.d("TesUserInfo", localStringBuilder.toString());
      if (paramWUPResponseBase != null) {
        onIPList(paramWUPResponseBase, (ITbsSmttServiceCallback)((WUPRequest)paramWUPRequestBase).getBindObject(), paramWUPRequestBase.getType());
      }
      break;
    }
  }
  
  public void refreshToken()
  {
    if ((this.mLastRefreshToken != -1L) && (System.currentTimeMillis() / 1000L - this.mLastRefreshToken < TokenRefreshInterval))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("refreshToken reject, minimum interval is ");
      localStringBuilder.append(TokenRefreshInterval);
      localStringBuilder.append("s");
      LogUtils.d("TheSimCardOfGreatKing", localStringBuilder.toString());
      return;
    }
    LogUtils.d("TheSimCardOfGreatKing", "refreshToken");
    WUPTaskProxy.send(getHTTPSTunnelTokenRequest());
  }
  
  public void reset()
  {
    if (this.mQAuth == null) {
      this.mQAuth = "";
    }
    setStatState(-1);
    setDomainTime(0);
    this.mOriChannelID = "";
    this.mQProxyList = new Hashtable();
    this.mSocketServerList = new ArrayList();
    this.mMaxReportNumber = 0;
    this.mWupPushProxyList = new ArrayList();
    this.mJsTemplateMD5 = "";
    this.mJsTemplate = "";
    this.mAdJSMD5 = "";
    this.mModuleMD5 = "";
    this.mAdBlockSourceMD5 = "";
    this.mSubsetAdRuleMD5 = "";
  }
  
  public void resetAdBlockSourceMD5()
  {
    this.mAdBlockSourceMD5 = "";
    saveAdBlockSourceMD5();
    TbsUserInfoDataProvider.getInstance().saveUserInfoData();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("resetAdBlockSourceMD5 mAdBlockSourceMD5=");
    localStringBuilder.append(this.mAdBlockSourceMD5);
    LogUtils.d("AdFilter", localStringBuilder.toString());
  }
  
  public void resetIPV6Status(boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("x5-ip-list resetIPV6Status + status : ");
    localStringBuilder.append(paramBoolean);
    appendLiveLog(localStringBuilder.toString());
    this.mcanUseIPV6 = paramBoolean;
  }
  
  public void resetJSAndModelMD5(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean1) {
      this.mAdJSMD5 = "";
    }
    if (paramBoolean2) {
      this.mModuleMD5 = "";
    }
    saveADMD5();
    TbsUserInfoDataProvider.getInstance().saveUserInfoData();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("resetJSAndModelMD5 mAdJSMD5=");
    localStringBuilder.append(this.mAdJSMD5);
    localStringBuilder.append(" mModuleMD5=");
    localStringBuilder.append(this.mModuleMD5);
    LogUtils.d("AdFilter", localStringBuilder.toString());
  }
  
  public void resetSubsetAdRuleMD5()
  {
    this.mSubsetAdRuleMD5 = "";
    saveAdBlockSourceMD5();
    TbsUserInfoDataProvider.getInstance().saveUserInfoData();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("resetAdBlockSourceMD5 mSubsetAdRuleMD5=");
    localStringBuilder.append(this.mSubsetAdRuleMD5);
    LogUtils.d("AdFilter", localStringBuilder.toString());
  }
  
  public void saveHttp2ProxyList(String paramString, int paramInt1, ArrayList<String> paramArrayList, int paramInt2, long paramLong, ArrayList<Integer> paramArrayList1)
  {
    if (paramArrayList != null)
    {
      if (paramArrayList.size() <= 0) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString);
      localStringBuilder.append("_http2");
      saveQProxyList(localStringBuilder.toString(), paramInt1, paramArrayList, paramInt2, paramLong, paramArrayList1);
      return;
    }
  }
  
  public void saveHttpsTunnelProxyList(String paramString, int paramInt1, ArrayList<String> paramArrayList, int paramInt2, long paramLong, ArrayList<Integer> paramArrayList1)
  {
    if (paramArrayList != null)
    {
      if (paramArrayList.size() <= 0) {
        return;
      }
      saveQProxyList("_httpsTunnel", paramInt1, paramArrayList, paramInt2, paramLong, paramArrayList1);
      return;
    }
  }
  
  public void saveIPList(int paramInt, ArrayList<String> paramArrayList)
  {
    int i;
    if (paramInt == 7) {
      i = 1005;
    } else if (paramInt == 10) {
      i = 1007;
    } else {
      i = -1;
    }
    if ((paramInt == 7) || (paramInt == 10)) {
      TbsUserInfoDataProvider.getInstance().addUserInfoData(i, paramArrayList.toString());
    }
  }
  
  public void saveQProxyList(String paramString, int paramInt1, ArrayList<String> paramArrayList, int paramInt2, long paramLong, ArrayList<Integer> paramArrayList1)
  {
    synchronized (this.mQProxyList)
    {
      ProxyInfo localProxyInfo = new ProxyInfo(null);
      localProxyInfo.setProxyList(paramString, paramInt1, paramArrayList, paramInt2, paramLong, paramArrayList1);
      this.mQProxyList.put(paramString, localProxyInfo);
      paramArrayList = new StringBuilder();
      paramArrayList.append("saving proxy-list to mQProxyList, type=");
      paramArrayList.append(paramString);
      paramArrayList.append(", ");
      paramArrayList.append(localProxyInfo);
      LogUtils.d("x5-ip-list", paramArrayList.toString());
      return;
    }
  }
  
  public void saveQProxyListToJson()
  {
    synchronized (this.mQProxyList)
    {
      Iterator localIterator = this.mQProxyList.keySet().iterator();
      JSONObject localJSONObject = new JSONObject();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        try
        {
          localJSONObject.put(str, ((ProxyInfo)this.mQProxyList.get(str)).StringToJson());
        }
        catch (JSONException localJSONException)
        {
          localJSONException.printStackTrace();
        }
      }
      TbsUserInfoDataProvider.getInstance().addUserInfoData(1003, localJSONObject.toString());
      return;
    }
  }
  
  public void saveQuicProxyList(String paramString, int paramInt1, ArrayList<String> paramArrayList, int paramInt2, long paramLong, ArrayList<Integer> paramArrayList1)
  {
    if (paramArrayList != null)
    {
      if (paramArrayList.size() <= 0) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString);
      localStringBuilder.append("_quic");
      saveQProxyList(localStringBuilder.toString(), paramInt1, paramArrayList, paramInt2, paramLong, paramArrayList1);
      return;
    }
  }
  
  public boolean serverAllowQProxy()
  {
    String str = getApn(Apn.sApnTypeS);
    boolean bool3 = this.mProtocolFlag.containsKey(str);
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (bool3)
    {
      bool1 = bool2;
      if (((Integer)this.mProtocolFlag.get(str)).intValue() == 1) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public int serverAllowQProxyStatus()
  {
    String str = getApn(Apn.sApnTypeS);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("serverAllowQProxyStatus mProtocolFlag sApn=");
    localStringBuilder.append(str);
    localStringBuilder.append(" mProtocolFlag.containsKey(sApn)=");
    localStringBuilder.append(this.mProtocolFlag.containsKey(str));
    LogUtils.d("TheSimCardOfGreatKing", localStringBuilder.toString());
    if (this.mProtocolFlag.containsKey(str)) {
      return ((Integer)this.mProtocolFlag.get(str)).intValue();
    }
    return -1;
  }
  
  public boolean serverSetDefaultDirect()
  {
    String str = getApn(Apn.sApnTypeS);
    return (this.mProtocolFlag.containsKey(str)) && (((Integer)this.mProtocolFlag.get(str)).intValue() == 0);
  }
  
  public void setDomainTime(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setDomainTime:");
    localStringBuilder.append(paramInt);
    LogUtils.d("TesUserInfo", localStringBuilder.toString());
    this.mDomainTime = paramInt;
  }
  
  public void setProtocolFlag(String paramString, int paramInt)
  {
    synchronized (this.mProtocolFlagLock)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("ProtocolFlagSize=");
      localStringBuilder.append(this.mProtocolFlag.size());
      localStringBuilder.append("; ");
      localStringBuilder.append(paramString);
      localStringBuilder.append("=");
      localStringBuilder.append(paramInt);
      appendUserinfoLiveLog(localStringBuilder.toString());
      this.mProtocolFlag.put(paramString, Integer.valueOf(paramInt));
      return;
    }
  }
  
  public void setQAuth(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setQAuth:");
    localStringBuilder.append(paramString);
    LogUtils.d("TesUserInfo", localStringBuilder.toString());
    this.mQAuth = paramString;
    if (this.mQAuth == null) {
      this.mQAuth = "";
    }
  }
  
  public void setServiceManger(IServiceManager paramIServiceManager)
  {
    this.mServiceManager = paramIServiceManager;
  }
  
  public void setSocketServerList(ArrayList<String> paramArrayList)
  {
    if (paramArrayList != null)
    {
      if (paramArrayList.size() < 1) {
        return;
      }
      synchronized (this.mSocketServerList)
      {
        this.mSocketServerList.clear();
        paramArrayList = paramArrayList.iterator();
        while (paramArrayList.hasNext())
        {
          String str = (String)paramArrayList.next();
          this.mSocketServerList.add(str);
        }
        return;
      }
    }
  }
  
  public void setStatState(int paramInt)
  {
    this.mStatState = paramInt;
  }
  
  public boolean shouldUpdateIPList(int paramInt)
  {
    try
    {
      ProxyInfo localProxyInfo = (ProxyInfo)this.mQProxyList.get(apnTypeToName(paramInt));
      if (localProxyInfo != null)
      {
        boolean bool = localProxyInfo.shouldKeep();
        return bool ^ true;
      }
      return true;
    }
    finally {}
  }
  
  public ArrayList<String> stringsToList(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    if (TextUtils.isEmpty(paramString)) {
      return localArrayList;
    }
    if (paramString.length() - 1 <= 1) {
      return localArrayList;
    }
    paramString = paramString.substring(1, paramString.length() - 1).split(",");
    if (paramString.length == 0) {
      return localArrayList;
    }
    int i = 0;
    while (i < paramString.length)
    {
      localArrayList.add(paramString[i].trim());
      i += 1;
    }
    return localArrayList;
  }
  
  public void tryGetIPList(int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge Z and I\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.useAs(TypeTransformer.java:868)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:668)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public class IplistHandler
    extends Handler
  {
    public IplistHandler()
    {
      super();
    }
    
    public void handleMessage(Message paramMessage)
    {
      int i = paramMessage.what;
      boolean bool = true;
      if (i != 1) {
        return;
      }
      TbsUserInfo localTbsUserInfo = TbsUserInfo.this;
      i = paramMessage.arg1;
      if (paramMessage.arg2 != 1) {
        bool = false;
      }
      localTbsUserInfo.getIPList(i, bool);
    }
  }
  
  private class ProxyInfo
  {
    private String mApnType;
    private ArrayList<String> mIPV4QProxyList = null;
    private ArrayList<String> mIPV6QProxyList = null;
    private ArrayList<Integer> mIsOCProxyList = null;
    private long mKeepUntil = 0L;
    private int mMaxSwitch = 10;
    private int mProxyType;
    private ArrayList<String> mQProxyList = null;
    private int mSwitchNum = 0;
    
    private ProxyInfo() {}
    
    public void JsonToProxyInfo(JSONObject paramJSONObject)
    {
      try
      {
        this.mApnType = paramJSONObject.getString("apnType");
        this.mProxyType = paramJSONObject.getInt("proxyType");
        Object localObject = paramJSONObject.getString("proxyList");
        boolean bool = TextUtils.isEmpty((CharSequence)localObject);
        int j = 0;
        int i;
        if (!bool)
        {
          localObject = ((String)localObject).substring(1, ((String)localObject).length() - 1).split(",");
          this.mQProxyList = new ArrayList(localObject.length);
          i = 0;
          while (i < localObject.length)
          {
            this.mQProxyList.add(localObject[i].trim());
            i += 1;
          }
        }
        this.mQProxyList = new ArrayList();
        this.mMaxSwitch = paramJSONObject.getInt("maxSwitch");
        this.mKeepUntil = paramJSONObject.getLong("keepUtil");
        localObject = paramJSONObject.getString("ipv6proxyList");
        if (!TextUtils.isEmpty((CharSequence)localObject))
        {
          localObject = ((String)localObject).substring(1, ((String)localObject).length() - 1);
          if ((localObject != null) && (localObject != "") && (((String)localObject).length() != 0))
          {
            localObject = ((String)localObject).split(",");
            this.mIPV6QProxyList = new ArrayList(localObject.length);
            i = 0;
            while (i < localObject.length)
            {
              this.mIPV6QProxyList.add(localObject[i].trim());
              i += 1;
            }
          }
          this.mIPV6QProxyList = new ArrayList();
        }
        else
        {
          this.mIPV6QProxyList = new ArrayList();
        }
        paramJSONObject = paramJSONObject.getString("ipv4proxyList");
        if (!TextUtils.isEmpty(paramJSONObject))
        {
          paramJSONObject = paramJSONObject.substring(1, paramJSONObject.length() - 1);
          if ((paramJSONObject != null) && (paramJSONObject != "") && (paramJSONObject.length() != 0))
          {
            paramJSONObject = paramJSONObject.split(",");
            this.mIPV4QProxyList = new ArrayList(paramJSONObject.length);
            i = j;
            while (i < paramJSONObject.length)
            {
              this.mIPV4QProxyList.add(paramJSONObject[i].trim());
              i += 1;
            }
          }
          this.mIPV4QProxyList = new ArrayList();
          return;
        }
        this.mIPV4QProxyList = new ArrayList();
        return;
      }
      catch (JSONException paramJSONObject)
      {
        paramJSONObject.printStackTrace();
      }
    }
    
    public JSONObject StringToJson()
    {
      JSONObject localJSONObject = new JSONObject();
      try
      {
        localJSONObject.put("apnType", this.mApnType);
        localJSONObject.put("proxyType", this.mProxyType);
        if (this.mQProxyList != null) {
          localJSONObject.put("proxyList", this.mQProxyList.toString());
        } else {
          localJSONObject.put("proxyList", "");
        }
        localJSONObject.put("maxSwitch", this.mMaxSwitch);
        localJSONObject.put("keepUtil", this.mKeepUntil);
        if (this.mIPV6QProxyList != null) {
          localJSONObject.put("ipv6proxyList", this.mIPV6QProxyList.toString());
        } else {
          localJSONObject.put("ipv6proxyList", "");
        }
        if (this.mIPV4QProxyList != null)
        {
          localJSONObject.put("ipv4proxyList", this.mIPV4QProxyList.toString());
          return localJSONObject;
        }
        localJSONObject.put("ipv4proxyList", "");
        return localJSONObject;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
      return localJSONObject;
    }
    
    public String getIPV6Proxy()
    {
      ArrayList localArrayList = this.mIPV6QProxyList;
      if ((localArrayList != null) && (localArrayList.size() != 0) && (PublicSettingManager.getInstance().getCanIpv6Proxy())) {
        return (String)this.mIPV6QProxyList.get(0);
      }
      return null;
    }
    
    public String getProxy()
    {
      Object localObject2;
      if (TbsUserInfo.this.mcanUseIPV6)
      {
        localObject1 = this.mIPV6QProxyList;
        if ((localObject1 != null) && (((ArrayList)localObject1).size() != 0) && (PublicSettingManager.getInstance().getCanIpv6Proxy()))
        {
          int i = new Random().nextInt(this.mIPV6QProxyList.size());
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append(" get IPV6 adress : ");
          ((StringBuilder)localObject1).append((String)this.mIPV6QProxyList.get(i));
          ((StringBuilder)localObject1).append("index");
          ((StringBuilder)localObject1).append(i);
          LogUtils.d("TesUserInfo", ((StringBuilder)localObject1).toString());
          localObject1 = TbsUserInfo.this;
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("TesUserInfo get IPV6 adress : ");
          ((StringBuilder)localObject2).append((String)this.mIPV6QProxyList.get(i));
          ((TbsUserInfo)localObject1).appendLiveLog(((StringBuilder)localObject2).toString());
          return (String)this.mIPV6QProxyList.get(i);
        }
      }
      Object localObject1 = this.mIPV4QProxyList;
      if ((localObject1 != null) && (((ArrayList)localObject1).size() != 0))
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("get IPV4 adress : ");
        localObject2 = this.mIPV4QProxyList;
        ((StringBuilder)localObject1).append((String)((ArrayList)localObject2).get(this.mSwitchNum % ((ArrayList)localObject2).size()));
        LogUtils.d("x5-ip-list", ((StringBuilder)localObject1).toString());
        localObject1 = TbsUserInfo.this;
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("x5-ip-list  get IPV4adress : ");
        ArrayList localArrayList = this.mIPV4QProxyList;
        ((StringBuilder)localObject2).append((String)localArrayList.get(this.mSwitchNum % localArrayList.size()));
        ((TbsUserInfo)localObject1).appendLiveLog(((StringBuilder)localObject2).toString());
        localObject1 = this.mIPV4QProxyList;
        return (String)((ArrayList)localObject1).get(this.mSwitchNum % ((ArrayList)localObject1).size());
      }
      return null;
    }
    
    public ArrayList<String> getProxyList()
    {
      return this.mQProxyList;
    }
    
    public int getType()
    {
      ArrayList localArrayList = this.mIsOCProxyList;
      if ((localArrayList != null) && (localArrayList.size() != 0))
      {
        localArrayList = this.mIsOCProxyList;
        return ((Integer)localArrayList.get(this.mSwitchNum % localArrayList.size())).intValue();
      }
      return 0;
    }
    
    public void handleFail(String arg1, boolean paramBoolean)
    {
      Object localObject1 = this.mQProxyList;
      if ((localObject1 != null) && (((ArrayList)localObject1).size() != 0) && ((this.mApnType.equals(TbsUserInfo.this.apnTypeToName(Apn.getApnTypeS()))) || (paramBoolean)))
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("mQProxyList.get(mSwitchNum % mQProxyList.size())=");
        Object localObject4 = this.mQProxyList;
        ((StringBuilder)localObject1).append((String)((ArrayList)localObject4).get(this.mSwitchNum % ((ArrayList)localObject4).size()));
        ((StringBuilder)localObject1).append(" proxyHost=");
        ((StringBuilder)localObject1).append(???);
        LogUtils.d("x5-ip-list", ((StringBuilder)localObject1).toString());
        if (TbsUserInfo.this.isIPV6Adress(???))
        {
          TbsUserInfo.access$208(TbsUserInfo.this);
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append(" IPV6 proxy handleFail + adress : ");
          ((StringBuilder)localObject1).append(???);
          ((StringBuilder)localObject1).append("ipv6SwitchNum =");
          ((StringBuilder)localObject1).append(TbsUserInfo.this.ipv6SwitchNum);
          ((StringBuilder)localObject1).append("mMaxIpv6Switch=");
          ((StringBuilder)localObject1).append(TbsUserInfo.this.mMaxIpv6Switch);
          LogUtils.d("x5-ip-list", ((StringBuilder)localObject1).toString());
          localObject1 = TbsUserInfo.this;
          localObject4 = new StringBuilder();
          ((StringBuilder)localObject4).append("TesUserInfo IPV6 proxy handleFail + adress: ");
          ((StringBuilder)localObject4).append(???);
          ((StringBuilder)localObject4).append(???);
          ((StringBuilder)localObject4).append("ipv6SwitchNum =");
          ((StringBuilder)localObject4).append(TbsUserInfo.this.ipv6SwitchNum);
          ((TbsUserInfo)localObject1).appendLiveLog(((StringBuilder)localObject4).toString());
          TbsUserInfo.this.resetIPV6Status(false);
          if (TbsUserInfo.this.ipv6SwitchNum > TbsUserInfo.this.mMaxIpv6Switch)
          {
            TbsUserInfo.access$202(TbsUserInfo.this, 0);
            synchronized (TbsUserInfo.this.mQProxyList)
            {
              TbsUserInfo.this.mQProxyList.remove(this.mApnType);
              TbsUserInfo.this.mIsOCProxy = 0;
              ??? = new StringBuilder();
              ???.append("ip-list is empty after the last failure, apnType=");
              ???.append(this.mApnType);
              ???.append(" isHttpsTunnel=");
              ???.append(paramBoolean);
              LogUtils.d("x5-ip-list", ???.toString());
              if (!paramBoolean)
              {
                TbsUserInfo.this.tryGetIPList(Apn.getApnTypeS(), true, true, false);
                return;
              }
              TbsUserInfo.this.tryGetIPList(Apn.getApnTypeS(), false, true, true);
              return;
            }
          }
        }
        else
        {
          ArrayList localArrayList = this.mIPV4QProxyList;
          if ((localArrayList != null) && (localArrayList.size() != 0))
          {
            localArrayList = this.mIPV4QProxyList;
            if (((String)localArrayList.get(this.mSwitchNum % localArrayList.size())).equals(???))
            {
              this.mSwitchNum += 1;
              ??? = new StringBuilder();
              ???.append("ip-list cursor moved, cursor=");
              ???.append(this.mSwitchNum % this.mIPV4QProxyList.size());
              ???.append(", currentProxy=");
              localArrayList = this.mIPV4QProxyList;
              ???.append((String)localArrayList.get(this.mSwitchNum % localArrayList.size()));
              ???.append(" SwitchNum=");
              ???.append(this.mSwitchNum);
              LogUtils.d("x5-ip-list", ???.toString());
              if (this.mSwitchNum > this.mMaxSwitch) {
                synchronized (TbsUserInfo.this.mQProxyList)
                {
                  TbsUserInfo.this.mQProxyList.remove(this.mApnType);
                  TbsUserInfo.this.mIsOCProxy = 0;
                  ??? = new StringBuilder();
                  ???.append("ip-list is empty after the last failure, apnType=");
                  ???.append(this.mApnType);
                  ???.append(" isHttpsTunnel=");
                  ???.append(paramBoolean);
                  LogUtils.d("x5-ip-list", ???.toString());
                  if (!paramBoolean)
                  {
                    TbsUserInfo.this.tryGetIPList(Apn.getApnTypeS(), true, true, false);
                    return;
                  }
                  TbsUserInfo.this.tryGetIPList(Apn.getApnTypeS(), false, true, true);
                  return;
                }
              }
              if (!paramBoolean) {
                TbsSmttServiceProxy.getInstance().setNeedCheckQProxy(true, paramBoolean, false);
              }
            }
          }
        }
      }
    }
    
    public void handleSuccess(String paramString)
    {
      LogUtils.d("ip-list-CheckQProxy", "Check QProxy success");
      TbsSmttServiceProxy.getInstance().setNeedCheckQProxy(false, false, false);
    }
    
    public void setProxyList(String paramString, int paramInt1, ArrayList<String> paramArrayList, int paramInt2, long paramLong, ArrayList<Integer> paramArrayList1)
    {
      this.mApnType = paramString;
      this.mProxyType = paramInt1;
      paramInt1 = 0;
      this.mSwitchNum = 0;
      this.mQProxyList = paramArrayList;
      this.mMaxSwitch = paramInt2;
      this.mKeepUntil = (System.currentTimeMillis() + paramLong);
      this.mIsOCProxyList = paramArrayList1;
      paramString = new ArrayList();
      paramArrayList = new ArrayList();
      while (paramInt1 < this.mQProxyList.size())
      {
        if (TbsUserInfo.this.isIPV6Adress((String)this.mQProxyList.get(paramInt1))) {
          paramString.add(this.mQProxyList.get(paramInt1));
        } else {
          paramArrayList.add(this.mQProxyList.get(paramInt1));
        }
        paramInt1 += 1;
      }
      this.mIPV6QProxyList = paramString;
      this.mIPV4QProxyList = paramArrayList;
    }
    
    public boolean shouldKeep()
    {
      return System.currentTimeMillis() < this.mKeepUntil;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("apn=");
      localStringBuilder.append(this.mApnType);
      localStringBuilder.append("; proxyType = ");
      localStringBuilder.append(this.mProxyType);
      localStringBuilder.append("; list = ");
      localStringBuilder.append(this.mQProxyList);
      localStringBuilder.append("; switch=");
      localStringBuilder.append(this.mSwitchNum);
      localStringBuilder.append(",");
      localStringBuilder.append(this.mIsOCProxyList);
      localStringBuilder.append(" maxSwitch=");
      localStringBuilder.append(this.mMaxSwitch);
      localStringBuilder.append(";ipv4list = ");
      localStringBuilder.append(this.mIPV4QProxyList);
      localStringBuilder.append(";ipv6list = ");
      localStringBuilder.append(this.mIPV6QProxyList);
      return localStringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\TbsUserInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */