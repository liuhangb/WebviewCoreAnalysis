package com.tencent.common.wup;

import android.text.TextUtils;
import com.taf.HexUtil;
import com.tencent.basesupport.FLogger;
import com.tencent.common.http.Apn;
import com.tencent.common.http.IRequstIntercepter;
import com.tencent.common.http.MttRequestBase;
import com.tencent.common.http.MttResponse;
import com.tencent.common.http.Requester;
import com.tencent.common.http.RequesterFactory;
import com.tencent.common.serverconfig.DnsManager;
import com.tencent.common.serverconfig.DnsManager.DnsData;
import com.tencent.common.serverconfig.WupProxyDomainRouter;
import com.tencent.common.serverconfig.WupProxyDomainRouter.WupProxyAddress;
import com.tencent.common.serverconfig.WupServerConfigsWrapper;
import com.tencent.common.utils.ByteUtils;
import com.tencent.common.utils.FileUtilsF;
import com.tencent.common.utils.UrlUtils;
import com.tencent.common.wup.base.MttWupRequest;
import com.tencent.common.wup.base.WupConnectionPool;
import com.tencent.common.wup.base.WupOaepEncryptController;
import com.tencent.common.wup.base.WupTimeOutController;
import com.tencent.common.wup.interfaces.IWUPClientProxy;
import com.tencent.common.wup.security.MttTokenProvider;
import com.tencent.common.wup.security.MttWupToken;
import com.tencent.common.wup.security.WupEncryptHelper;
import com.tencent.mtt.base.net.frame.ByteArrayPool;
import com.tencent.mtt.base.net.frame.PoolingByteArrayOutputStream;
import com.tencent.mtt.base.task.ITaskExecutors;
import com.tencent.mtt.base.task.Task;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

public class WUPTask
  extends Task
  implements IRequstIntercepter
{
  public static final int TOKEN_ERROR_RETRY_TIMES = 3;
  private byte jdField_a_of_type_Byte = 0;
  private int jdField_a_of_type_Int = 0;
  private DnsManager.DnsData jdField_a_of_type_ComTencentCommonServerconfigDnsManager$DnsData = null;
  private RequestPolicy jdField_a_of_type_ComTencentCommonWupRequestPolicy = RequestPolicy.MAX_RETRY_POLICY;
  private WUPTaskClient jdField_a_of_type_ComTencentCommonWupWUPTaskClient = null;
  private WupTimeOutController jdField_a_of_type_ComTencentCommonWupBaseWupTimeOutController = WupTimeOutController.getInstance();
  private IWUPClientProxy jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy = null;
  private MttWupToken jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken = null;
  private Object jdField_a_of_type_JavaLangObject;
  private String jdField_a_of_type_JavaLangString;
  private boolean jdField_a_of_type_Boolean = false;
  private byte[] jdField_a_of_type_ArrayOfByte;
  private int jdField_b_of_type_Int = 1;
  private String jdField_b_of_type_JavaLangString = null;
  private boolean jdField_b_of_type_Boolean = false;
  private byte[] jdField_b_of_type_ArrayOfByte;
  private int jdField_c_of_type_Int = 2;
  private String jdField_c_of_type_JavaLangString = null;
  private boolean jdField_c_of_type_Boolean = false;
  private byte[] jdField_c_of_type_ArrayOfByte = null;
  private String jdField_d_of_type_JavaLangString = "";
  private boolean jdField_d_of_type_Boolean = false;
  private String jdField_e_of_type_JavaLangString = null;
  private boolean jdField_e_of_type_Boolean = true;
  private boolean f = false;
  private boolean g = false;
  private boolean h = false;
  private boolean i = false;
  private boolean j = false;
  public ArrayList<String> mStatPath = new ArrayList();
  
  public WUPTask(byte[] paramArrayOfByte, String paramString, boolean paramBoolean1, boolean paramBoolean2, WUPTaskClient paramWUPTaskClient)
  {
    this.jdField_d_of_type_Boolean = paramBoolean2;
    this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient = paramWUPTaskClient;
    this.jdField_d_of_type_JavaLangString = paramString;
    this.jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy = this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a();
    a(paramArrayOfByte, paramBoolean1);
  }
  
  private DnsManager.DnsData a()
  {
    boolean bool = WupProxyDomainRouter.isWupProxyDomains(this.jdField_a_of_type_JavaLangString);
    DnsManager.DnsData localDnsData2 = null;
    if (!bool) {
      return null;
    }
    DnsManager.DnsData localDnsData1 = localDnsData2;
    try
    {
      WupProxyDomainRouter.WupProxyAddress localWupProxyAddress = WupProxyDomainRouter.getInstance().getWupProxyHostName();
      localDnsData1 = localDnsData2;
      localDnsData2 = DnsManager.getInstance().getIPAddressSync(localWupProxyAddress.host);
      localDnsData1 = localDnsData2;
      if (localDnsData2 != null)
      {
        localDnsData1 = localDnsData2;
        localDnsData2.mPort = localWupProxyAddress.port;
        return localDnsData2;
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    return localDnsData1;
  }
  
  private void a()
  {
    if ((this.mErrorCode <= 63535) && (this.mErrorCode >= 63527))
    {
      this.jdField_a_of_type_ComTencentCommonWupBaseWupTimeOutController.enlargeWUPNetTimeOut();
      DnsManager.DnsData localDnsData = this.jdField_a_of_type_ComTencentCommonServerconfigDnsManager$DnsData;
      if (localDnsData != null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Increase FailTime for ");
        localStringBuilder.append(localDnsData);
        FLogger.d("DnsManager", localStringBuilder.toString());
        localDnsData.mFailTimes += 1;
      }
    }
    else
    {
      this.jdField_a_of_type_ComTencentCommonWupBaseWupTimeOutController.restoreWUPNetTimeOut();
    }
  }
  
  private void a(MttResponse paramMttResponse)
  {
    String str = paramMttResponse.getQSZip();
    if (!TextUtils.isEmpty(str))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("rsp gzip: ");
      ((StringBuilder)localObject).append(str);
      ((StringBuilder)localObject).toString();
      if ("gzip".equalsIgnoreCase(str.trim().toLowerCase())) {
        this.jdField_c_of_type_Boolean = true;
      }
    }
    else
    {
      FLogger.d("WUPTask", "getQSZip == null");
    }
    str = paramMttResponse.getQEncrypt();
    if (!TextUtils.isEmpty(str))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("rsp encrypt: ");
      ((StringBuilder)localObject).append(str);
      ((StringBuilder)localObject).toString();
      str = str.trim().toLowerCase();
      if (("12".equalsIgnoreCase(str)) || ("17".equalsIgnoreCase(str))) {
        this.jdField_b_of_type_Boolean = true;
      }
    }
    else
    {
      FLogger.d("WUPTask", "getQEncrypt == null");
    }
    str = paramMttResponse.getWupEnvironment();
    if (!TextUtils.isEmpty(str))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("rsp env: ");
      ((StringBuilder)localObject).append(str);
      ((StringBuilder)localObject).toString();
      if ("test".equalsIgnoreCase(str.trim().toLowerCase())) {
        this.h = true;
      }
    }
    str = paramMttResponse.getTokenExpireSpan();
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("rsp expireSpan: ");
    ((StringBuilder)localObject).append(str);
    ((StringBuilder)localObject).toString();
    paramMttResponse = paramMttResponse.getQToken();
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("rsp token: ");
    ((StringBuilder)localObject).append(paramMttResponse);
    ((StringBuilder)localObject).toString();
    if ((!TextUtils.isEmpty(str)) && (!TextUtils.isEmpty(paramMttResponse)))
    {
      localObject = this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken;
      if ((localObject != null) && (((MttWupToken)localObject).setTokenParam(paramMttResponse, str))) {
        MttTokenProvider.getInstance().saveCurrentTokenToFile();
      }
    }
  }
  
  private void a(String paramString, Throwable paramThrowable)
  {
    String str1;
    if (paramThrowable != null)
    {
      if (TextUtils.isEmpty(paramString)) {
        return;
      }
      str1 = new String();
      localObject1 = "";
      str2 = str1;
    }
    try
    {
      String str3 = paramThrowable.getMessage();
      int k = 1;
      for (;;)
      {
        localObject2 = str1;
        localObject3 = str3;
        str2 = str1;
        localObject1 = str3;
        if (k >= paramThrowable.getStackTrace().length) {
          break;
        }
        str2 = str1;
        localObject1 = str3;
        localObject2 = new StringBuilder();
        str2 = str1;
        localObject1 = str3;
        ((StringBuilder)localObject2).append(str1);
        str2 = str1;
        localObject1 = str3;
        ((StringBuilder)localObject2).append("\\");
        str2 = str1;
        localObject1 = str3;
        ((StringBuilder)localObject2).append(paramThrowable.getStackTrace()[k]);
        str2 = str1;
        localObject1 = str3;
        str1 = ((StringBuilder)localObject2).toString();
        k += 1;
      }
    }
    catch (Throwable paramThrowable)
    {
      for (;;)
      {
        Object localObject2 = str2;
        Object localObject3 = localObject1;
      }
    }
    if (TextUtils.isEmpty((CharSequence)localObject2)) {
      return;
    }
    paramThrowable = new HashMap();
    paramThrowable.put("wup_err_code", paramString);
    paramThrowable.put("wup_err_stack", localObject2);
    paramThrowable.put("wup_err_msg", localObject3);
    this.jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy.reportStatInfo("WUP_EXCEPTION_INFO", paramThrowable);
    return;
  }
  
  private void a(Throwable paramThrowable, boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" WUP Encryption failed, error=");
    localStringBuilder.append(paramThrowable.getMessage());
    FLogger.d("CeasonTestWUP", localStringBuilder.toString());
    paramThrowable.printStackTrace();
    this.jdField_c_of_type_JavaLangString = "";
    if (paramBoolean)
    {
      this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a().disableOAPEPadding();
      a("oaep_enc_fail", paramThrowable);
      return;
    }
    a("rsa_enc_fail", paramThrowable);
  }
  
  /* Error */
  private void a(byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore 5
    //   6: new 322	java/io/ByteArrayOutputStream
    //   9: dup
    //   10: invokespecial 323	java/io/ByteArrayOutputStream:<init>	()V
    //   13: astore_3
    //   14: aload_3
    //   15: astore_2
    //   16: new 325	java/util/zip/GZIPOutputStream
    //   19: dup
    //   20: aload_3
    //   21: invokespecial 328	java/util/zip/GZIPOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   24: astore 6
    //   26: aload_3
    //   27: astore_2
    //   28: aload 6
    //   30: aload_1
    //   31: invokevirtual 331	java/util/zip/GZIPOutputStream:write	([B)V
    //   34: aload_3
    //   35: astore_2
    //   36: aload 6
    //   38: invokevirtual 334	java/util/zip/GZIPOutputStream:finish	()V
    //   41: aload_3
    //   42: astore_2
    //   43: aload 6
    //   45: invokevirtual 337	java/util/zip/GZIPOutputStream:close	()V
    //   48: aload_3
    //   49: astore_2
    //   50: aload_3
    //   51: invokevirtual 341	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   54: astore_1
    //   55: aload_1
    //   56: astore_2
    //   57: aload_3
    //   58: invokevirtual 342	java/io/ByteArrayOutputStream:close	()V
    //   61: aload_1
    //   62: astore_2
    //   63: goto +97 -> 160
    //   66: astore_1
    //   67: aload_1
    //   68: invokevirtual 160	java/lang/Throwable:printStackTrace	()V
    //   71: goto +89 -> 160
    //   74: astore_2
    //   75: aload_3
    //   76: astore_1
    //   77: aload_2
    //   78: astore_3
    //   79: goto +12 -> 91
    //   82: astore_1
    //   83: aconst_null
    //   84: astore_2
    //   85: goto +98 -> 183
    //   88: astore_3
    //   89: aconst_null
    //   90: astore_1
    //   91: aload_1
    //   92: astore_2
    //   93: new 168	java/lang/StringBuilder
    //   96: dup
    //   97: invokespecial 169	java/lang/StringBuilder:<init>	()V
    //   100: astore 6
    //   102: aload_1
    //   103: astore_2
    //   104: aload 6
    //   106: ldc_w 344
    //   109: invokevirtual 175	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   112: pop
    //   113: aload_1
    //   114: astore_2
    //   115: aload 6
    //   117: aload_3
    //   118: invokevirtual 273	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   121: invokevirtual 175	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   124: pop
    //   125: aload_1
    //   126: astore_2
    //   127: ldc -32
    //   129: aload 6
    //   131: invokevirtual 184	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   134: invokestatic 189	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   137: aload_1
    //   138: astore_2
    //   139: aload_3
    //   140: invokevirtual 160	java/lang/Throwable:printStackTrace	()V
    //   143: aload 4
    //   145: astore_2
    //   146: aload_1
    //   147: ifnull +13 -> 160
    //   150: aload 5
    //   152: astore_2
    //   153: aload_1
    //   154: invokevirtual 342	java/io/ByteArrayOutputStream:close	()V
    //   157: aload 4
    //   159: astore_2
    //   160: aload_2
    //   161: ifnull +20 -> 181
    //   164: aload_0
    //   165: aload_2
    //   166: putfield 346	com/tencent/common/wup/WUPTask:jdField_b_of_type_ArrayOfByte	[B
    //   169: aload_0
    //   170: getfield 350	com/tencent/common/wup/WUPTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
    //   173: ldc_w 352
    //   176: ldc -45
    //   178: invokevirtual 357	com/tencent/common/http/MttRequestBase:addHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   181: return
    //   182: astore_1
    //   183: aload_2
    //   184: ifnull +15 -> 199
    //   187: aload_2
    //   188: invokevirtual 342	java/io/ByteArrayOutputStream:close	()V
    //   191: goto +8 -> 199
    //   194: astore_2
    //   195: aload_2
    //   196: invokevirtual 160	java/lang/Throwable:printStackTrace	()V
    //   199: aload_1
    //   200: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	201	0	this	WUPTask
    //   0	201	1	paramArrayOfByte	byte[]
    //   15	48	2	localObject1	Object
    //   74	4	2	localThrowable1	Throwable
    //   84	104	2	localObject2	Object
    //   194	2	2	localThrowable2	Throwable
    //   13	66	3	localObject3	Object
    //   88	52	3	localThrowable3	Throwable
    //   1	157	4	localObject4	Object
    //   4	147	5	localObject5	Object
    //   24	106	6	localObject6	Object
    // Exception table:
    //   from	to	target	type
    //   57	61	66	java/lang/Throwable
    //   153	157	66	java/lang/Throwable
    //   16	26	74	java/lang/Throwable
    //   28	34	74	java/lang/Throwable
    //   36	41	74	java/lang/Throwable
    //   43	48	74	java/lang/Throwable
    //   50	55	74	java/lang/Throwable
    //   6	14	82	finally
    //   6	14	88	java/lang/Throwable
    //   16	26	182	finally
    //   28	34	182	finally
    //   36	41	182	finally
    //   43	48	182	finally
    //   50	55	182	finally
    //   93	102	182	finally
    //   104	113	182	finally
    //   115	125	182	finally
    //   127	137	182	finally
    //   139	143	182	finally
    //   187	191	194	java/lang/Throwable
  }
  
  private void a(byte[] paramArrayOfByte, boolean paramBoolean)
  {
    this.jdField_b_of_type_ArrayOfByte = paramArrayOfByte;
    this.mMttRequest = new MttWupRequest();
    this.mMttRequest.setQueenProxyEnable(false);
    this.mMttRequest.setIsWupRequest(true);
    this.mMttRequest.setRequestType((byte)105);
    if (this.jdField_e_of_type_Boolean)
    {
      this.mMttRequest.replaceHeader("User-Agent", "MQQBrowser");
      this.mMttRequest.replaceHeader("Accept", "*/*");
    }
    this.mMttRequest.addHeader("Content-Type", "application/multipart-formdata");
    this.mMttRequest.addHeader("Accept-Encoding", "identity");
    if (!TextUtils.isEmpty(this.jdField_d_of_type_JavaLangString)) {
      this.mMttRequest.addHeader("Q-EXT-INF", this.jdField_d_of_type_JavaLangString);
    }
    paramArrayOfByte = ByteUtils.byteToHexString(this.jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy.getByteGuid());
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("guid: ");
    ((StringBuilder)localObject).append(paramArrayOfByte);
    FLogger.d("WUPTask", ((StringBuilder)localObject).toString());
    localObject = this.jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy.getQUA(false);
    String str = this.jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy.getQUA2_V3();
    if (localObject != null) {
      this.mMttRequest.addHeader("Q-UA", (String)localObject);
    }
    if (str != null) {
      this.mMttRequest.addHeader("Q-UA2", str);
    }
    if (paramArrayOfByte != null) {
      this.mMttRequest.addHeader("Q-GUID", paramArrayOfByte);
    }
    this.mMttRequest.setMethod((byte)1);
    this.g = paramBoolean;
  }
  
  private boolean a()
  {
    long l = System.currentTimeMillis();
    this.mErrorCode = 0;
    int k = this.jdField_a_of_type_Int;
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("retryTimes = ");
    ((StringBuilder)localObject1).append(k);
    ((StringBuilder)localObject1).append(", request policy=");
    ((StringBuilder)localObject1).append(this.jdField_a_of_type_ComTencentCommonWupRequestPolicy);
    ((StringBuilder)localObject1).toString();
    localObject1 = this.mStatPath;
    Object localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("wtrc_");
    ((StringBuilder)localObject3).append(k - 1);
    ((ArrayList)localObject1).remove(((StringBuilder)localObject3).toString());
    localObject1 = this.mStatPath;
    localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("wtrc_");
    ((StringBuilder)localObject3).append(k);
    ((ArrayList)localObject1).add(((StringBuilder)localObject3).toString());
    this.mRequester = RequesterFactory.getRequester(2);
    this.mRequester.setIntercepter(this);
    this.mRequester.setIsRemoveHeader(this.jdField_e_of_type_Boolean);
    if (this.i) {
      k = this.jdField_a_of_type_ComTencentCommonWupBaseWupTimeOutController.getLeastReadTimeOut();
    } else {
      k = this.jdField_a_of_type_ComTencentCommonWupBaseWupTimeOutController.getReadTimeOut();
    }
    int m;
    if (this.i) {
      m = this.jdField_a_of_type_ComTencentCommonWupBaseWupTimeOutController.getDefaultConnTimeOut();
    } else {
      m = this.jdField_a_of_type_ComTencentCommonWupBaseWupTimeOutController.getConnTimeOut();
    }
    this.mRequester.setConnectTimeout(m);
    this.mRequester.setReadTimeout(k);
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("connect timeout = ");
    ((StringBuilder)localObject1).append(m);
    ((StringBuilder)localObject1).append(", read timeout = ");
    ((StringBuilder)localObject1).append(k);
    ((StringBuilder)localObject1).toString();
    this.mRequester.setCookieEnable(false);
    if (this.jdField_d_of_type_Boolean) {
      this.mRequester.setQDebugEnable(false);
    }
    int n = UrlUtils.isIpv6Url(this.jdField_a_of_type_JavaLangString) ^ true;
    for (;;)
    {
      try
      {
        try
        {
          this.mStatPath.add("wtsn");
          localObject1 = this.mRequester.execute(this.mMttRequest);
          this.mStatPath.add("wten");
          this.mNetworkStatus = this.mMttRequest.mNetworkStatus;
          setMttResponse((MttResponse)localObject1);
          k = ((MttResponse)localObject1).getStatusCode().intValue();
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("MttResponse : ");
          ((StringBuilder)localObject3).append(k);
          ((StringBuilder)localObject3).toString();
          localObject3 = this.mStatPath;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("wtsc_");
          localStringBuilder.append(k);
          ((ArrayList)localObject3).add(localStringBuilder.toString());
          if (k == 200)
          {
            a((MttResponse)localObject1);
            localObject3 = ((MttResponse)localObject1).getInputStream();
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("mttResponse.getContentLength():");
            localStringBuilder.append(((MttResponse)localObject1).getContentLength());
            localStringBuilder.toString();
            if (localObject3 != null)
            {
              this.jdField_a_of_type_ArrayOfByte = a((InputStream)localObject3, (MttResponse)localObject1);
              if (this.jdField_a_of_type_ArrayOfByte != null) {
                ((MttResponse)localObject1).setFlow(this.jdField_a_of_type_ArrayOfByte.length);
              }
            }
            else
            {
              this.mStatPath.add("wtei");
            }
            this.mStatPath.add("wtno");
            this.mStatus = 3;
            return true;
          }
          if ((k >= 700) && (k <= 702)) {
            try
            {
              this.mErrorCode = 63524;
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append("server_dec_error_");
              ((StringBuilder)localObject1).append(k);
              localObject1 = ((StringBuilder)localObject1).toString();
              localObject3 = new StringBuilder();
              ((StringBuilder)localObject3).append("Current Padding is ");
              ((StringBuilder)localObject3).append(this.jdField_a_of_type_Byte);
              ((StringBuilder)localObject3).append(", is using token? ");
              if (this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken == null) {
                break label1782;
              }
              bool = TextUtils.isEmpty(this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken.mToken);
              ((StringBuilder)localObject3).append(bool);
              a((String)localObject1, new Throwable(((StringBuilder)localObject3).toString()));
              if ((this.jdField_a_of_type_Byte == 2) && (k == 702)) {
                this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a().disableOAPEPadding();
              }
              if (this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken != null) {
                this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken.setExpire(true);
              }
              return true;
            }
            catch (Throwable localThrowable1)
            {
              n = 0;
              continue;
            }
            catch (OutOfMemoryError localOutOfMemoryError1)
            {
              n = 0;
              continue;
            }
            catch (Exception localException1)
            {
              n = 0;
              continue;
            }
            catch (IOException localIOException1)
            {
              n = 0;
              continue;
            }
            catch (SocketTimeoutException localSocketTimeoutException1)
            {
              n = 0;
              continue;
            }
            catch (SocketException localSocketException1)
            {
              n = 0;
              continue;
            }
            catch (ConnectException localConnectException1)
            {
              n = 0;
              continue;
            }
            catch (ProtocolException localProtocolException1)
            {
              n = 0;
              continue;
            }
            catch (UnknownHostException localUnknownHostException1)
            {
              n = 0;
            }
          }
          this.mErrorCode = 63535;
          if (this.mRequester != null) {
            this.mRequester.close();
          }
          this.mNetTimeList.add(Long.valueOf(System.currentTimeMillis() - l));
          k = n;
          if (this.i) {
            continue;
          }
          k = n;
          this.jdField_a_of_type_ComTencentCommonWupBaseWupTimeOutController.addRequestTime(System.currentTimeMillis() - l);
        }
        catch (Throwable localThrowable2) {}catch (OutOfMemoryError localOutOfMemoryError2)
        {
          continue;
        }
        catch (Exception localException2)
        {
          continue;
        }
        catch (IOException localIOException2)
        {
          continue;
        }
        catch (SocketTimeoutException localSocketTimeoutException2)
        {
          continue;
        }
        catch (SocketException localSocketException2)
        {
          continue;
        }
        catch (ConnectException localConnectException2)
        {
          continue;
        }
        catch (ProtocolException localProtocolException2)
        {
          continue;
        }
        catch (UnknownHostException localUnknownHostException2)
        {
          continue;
        }
        FLogger.e("WUPTask", localUnknownHostException2);
        this.mFailedReason = localUnknownHostException2;
        this.mStatPath.add("wtme");
        this.mErrorCode = 63531;
        if (this.mRequester != null) {
          this.mRequester.close();
        }
        this.mNetTimeList.add(Long.valueOf(System.currentTimeMillis() - l));
        k = n;
        if (!this.i)
        {
          k = n;
          continue;
          this.mFailedReason = localUnknownHostException2;
          this.mStatPath.add("wtme");
          this.mErrorCode = 63530;
          if (this.mRequester != null) {
            this.mRequester.close();
          }
          this.mNetTimeList.add(Long.valueOf(System.currentTimeMillis() - l));
          k = n;
          if (!this.i)
          {
            k = n;
            continue;
            localUnknownHostException2.printStackTrace();
            this.mFailedReason = localUnknownHostException2;
            this.mStatPath.add("wtme");
            this.mErrorCode = 63531;
            if (this.mRequester != null) {
              this.mRequester.close();
            }
            this.mNetTimeList.add(Long.valueOf(System.currentTimeMillis() - l));
            k = n;
            if (!this.i)
            {
              k = n;
              continue;
              localUnknownHostException2.printStackTrace();
              this.mFailedReason = localUnknownHostException2;
              this.mStatPath.add("wtme");
              this.mErrorCode = 63527;
              if (this.mRequester != null) {
                this.mRequester.close();
              }
              this.mNetTimeList.add(Long.valueOf(System.currentTimeMillis() - l));
              k = n;
              if (!this.i)
              {
                k = n;
                continue;
                localUnknownHostException2.printStackTrace();
                this.mFailedReason = localUnknownHostException2;
                this.mStatPath.add("wtme");
                this.mErrorCode = 63532;
                if (this.mRequester != null) {
                  this.mRequester.close();
                }
                this.mNetTimeList.add(Long.valueOf(System.currentTimeMillis() - l));
                k = n;
                if (!this.i)
                {
                  k = n;
                  continue;
                  localUnknownHostException2.printStackTrace();
                  this.mFailedReason = localUnknownHostException2;
                  this.mStatPath.add("wtme");
                  this.mErrorCode = 63533;
                  if (this.mRequester != null) {
                    this.mRequester.close();
                  }
                  this.mNetTimeList.add(Long.valueOf(System.currentTimeMillis() - l));
                  k = n;
                  if (!this.i)
                  {
                    k = n;
                    continue;
                    FLogger.e("WUPTask", localUnknownHostException2);
                    this.mFailedReason = localUnknownHostException2;
                    this.mStatPath.add("wtme");
                    this.mErrorCode = 63528;
                    if (this.mRequester != null) {
                      this.mRequester.close();
                    }
                    this.mNetTimeList.add(Long.valueOf(System.currentTimeMillis() - l));
                    k = n;
                    if (!this.i)
                    {
                      k = n;
                      continue;
                      FLogger.e("WUPTask", localUnknownHostException2);
                      this.mFailedReason = localUnknownHostException2;
                      this.mStatPath.add("wtme");
                      this.mErrorCode = 63529;
                      if (this.mRequester != null) {
                        this.mRequester.close();
                      }
                      this.mNetTimeList.add(Long.valueOf(System.currentTimeMillis() - l));
                      k = n;
                      if (!this.i)
                      {
                        k = n;
                        continue;
                        localUnknownHostException2.printStackTrace();
                        this.mFailedReason = localUnknownHostException2;
                        this.mStatPath.add("wtme");
                        this.mErrorCode = 63534;
                        if (this.mRequester != null) {
                          this.mRequester.close();
                        }
                        this.mNetTimeList.add(Long.valueOf(System.currentTimeMillis() - l));
                        k = n;
                        if (!this.i)
                        {
                          k = n;
                          continue;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
        if ((!this.jdField_a_of_type_Boolean) && (k != 0)) {
          return false;
        }
        FLogger.d("WUPTask", "Check update task canceled!");
        this.mStatPath.add("wtnd");
        return true;
      }
      finally
      {
        if (this.mRequester != null) {
          this.mRequester.close();
        }
        this.mNetTimeList.add(Long.valueOf(System.currentTimeMillis() - l));
        if (!this.i) {
          this.jdField_a_of_type_ComTencentCommonWupBaseWupTimeOutController.addRequestTime(System.currentTimeMillis() - l);
        }
      }
      label1782:
      boolean bool = false;
    }
  }
  
  private byte[] a(InputStream paramInputStream, MttResponse paramMttResponse)
    throws OutOfMemoryError, IOException
  {
    ByteArrayPool localByteArrayPool = ByteArrayPool.getInstance();
    if (localByteArrayPool == null)
    {
      paramInputStream = FileUtilsF.toByteArray(paramInputStream);
      paramMttResponse = new byte[paramInputStream.position()];
      paramInputStream.position(0);
      paramInputStream.get(paramMttResponse);
      FileUtilsF.getInstance().releaseByteBuffer(paramInputStream);
      return paramMttResponse;
    }
    PoolingByteArrayOutputStream localPoolingByteArrayOutputStream = new PoolingByteArrayOutputStream(localByteArrayPool, (int)paramMttResponse.getContentLength());
    paramMttResponse = null;
    try
    {
      byte[] arrayOfByte = localByteArrayPool.getBuf(2048);
      for (;;)
      {
        paramMttResponse = arrayOfByte;
        int k = paramInputStream.read(arrayOfByte);
        if (k == -1) {
          break;
        }
        paramMttResponse = arrayOfByte;
        localPoolingByteArrayOutputStream.write(arrayOfByte, 0, k);
      }
      paramMttResponse = arrayOfByte;
      paramInputStream = localPoolingByteArrayOutputStream.toByteArray();
      return paramInputStream;
    }
    finally
    {
      localByteArrayPool.returnBuf(paramMttResponse);
      localPoolingByteArrayOutputStream.close();
    }
  }
  
  private void b()
  {
    this.mThreadWaitTime = (System.currentTimeMillis() - this.mThreadWaitTime);
    if ((this.jdField_a_of_type_ComTencentCommonWupRequestPolicy == RequestPolicy.NO_RETRY_POLICY) || (this.jdField_a_of_type_ComTencentCommonWupRequestPolicy == RequestPolicy.FAST_MODE_POLICY)) {
      this.jdField_c_of_type_Int = 1;
    }
    boolean bool;
    if (this.jdField_a_of_type_ComTencentCommonWupRequestPolicy == RequestPolicy.FAST_MODE_POLICY) {
      bool = true;
    } else {
      bool = false;
    }
    this.i = bool;
    a(this.jdField_b_of_type_ArrayOfByte);
    if (this.g) {
      b(this.jdField_b_of_type_ArrayOfByte);
    }
    this.mMttRequest.setPostData(this.jdField_b_of_type_ArrayOfByte);
    this.mStatus = 5;
    Object localObject1 = WupServerConfigsWrapper.getWupProxyAddress(this.jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy);
    if (TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString))
    {
      this.jdField_a_of_type_JavaLangString = ((String)localObject1);
      localObject1 = this.jdField_a_of_type_JavaLangObject;
      if ((localObject1 instanceof WUPRequestBase))
      {
        ((WUPRequestBase)localObject1).mStatAddressReason = WupServerConfigsWrapper.getWupAddressReason();
        ((WUPRequestBase)this.jdField_a_of_type_JavaLangObject).mStatWupListIndex = WupServerConfigsWrapper.getWupAddressIndex(this.jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy);
      }
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("get from wup list = ");
      ((StringBuilder)localObject1).append(this.jdField_a_of_type_JavaLangString);
      FLogger.d("wup-ip-list", ((StringBuilder)localObject1).toString());
    }
    else if (this.jdField_a_of_type_JavaLangString.equalsIgnoreCase((String)localObject1))
    {
      localObject1 = this.jdField_a_of_type_JavaLangObject;
      if ((localObject1 instanceof WUPRequestBase))
      {
        ((WUPRequestBase)localObject1).mStatAddressReason = WupServerConfigsWrapper.getWupAddressReason();
        ((WUPRequestBase)this.jdField_a_of_type_JavaLangObject).mStatWupListIndex = WupServerConfigsWrapper.getWupAddressIndex(this.jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy);
      }
    }
    this.jdField_b_of_type_JavaLangString = new String(this.jdField_a_of_type_JavaLangString);
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("--- wup task run---  url before dns: ");
    ((StringBuilder)localObject1).append(this.jdField_a_of_type_JavaLangString);
    ((StringBuilder)localObject1).toString();
    this.jdField_a_of_type_ComTencentCommonServerconfigDnsManager$DnsData = a();
    localObject1 = this.jdField_a_of_type_ComTencentCommonServerconfigDnsManager$DnsData;
    if ((localObject1 != null) && (!TextUtils.isEmpty(((DnsManager.DnsData)localObject1).mIP)) && (this.jdField_a_of_type_ComTencentCommonServerconfigDnsManager$DnsData.mPort >= 80))
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("http://");
      ((StringBuilder)localObject1).append(this.jdField_a_of_type_ComTencentCommonServerconfigDnsManager$DnsData.mIP);
      ((StringBuilder)localObject1).append(":");
      ((StringBuilder)localObject1).append(this.jdField_a_of_type_ComTencentCommonServerconfigDnsManager$DnsData.mPort);
      this.jdField_a_of_type_JavaLangString = ((StringBuilder)localObject1).toString();
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("need resolve domain, get ip from dns = ");
      ((StringBuilder)localObject1).append(this.jdField_a_of_type_JavaLangString);
      FLogger.d("wup-ip-list", ((StringBuilder)localObject1).toString());
    }
    localObject1 = new String(this.jdField_a_of_type_JavaLangString);
    if (Apn.getApnType() == 2)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("/cmwap");
      localObject1 = ((StringBuilder)localObject2).toString();
      this.mMttRequest.setUseWapProxy(true);
    }
    else
    {
      this.mMttRequest.setUseWapProxy(false);
    }
    Object localObject2 = localObject1;
    if (this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken != null)
    {
      localObject2 = localObject1;
      if (!TextUtils.isEmpty(this.jdField_c_of_type_JavaLangString))
      {
        localObject2 = localObject1;
        if (!this.mMttRequest.getUseWapProxy())
        {
          localObject2 = localObject1;
          if (!((String)localObject1).endsWith("/"))
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append((String)localObject1);
            ((StringBuilder)localObject2).append("/");
            localObject2 = ((StringBuilder)localObject2).toString();
          }
        }
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append((String)localObject2);
        ((StringBuilder)localObject1).append("?");
        ((StringBuilder)localObject1).append(this.jdField_c_of_type_JavaLangString);
        localObject2 = ((StringBuilder)localObject1).toString();
      }
    }
    this.mMttRequest.setUrl((String)localObject2);
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("--- wup task run---  url: ");
    ((StringBuilder)localObject1).append(this.mMttRequest.getUrl());
    FLogger.d("WUPTask", ((StringBuilder)localObject1).toString());
  }
  
  private void b(byte[] paramArrayOfByte)
  {
    byte b1 = this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a().getWupEncryptType();
    MttWupToken localMttWupToken = null;
    if (b1 == 2) {}
    byte[] arrayOfByte;
    try
    {
      this.jdField_e_of_type_JavaLangString = WupEncryptHelper.generateInitVectorStr();
      this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken = MttTokenProvider.getInstance().getCurrentWupToken();
      arrayOfByte = this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken.encryptWithToken(paramArrayOfByte, this.jdField_e_of_type_JavaLangString);
      paramArrayOfByte = this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken.encryptWithToken(this.jdField_a_of_type_ComTencentCommonWupInterfacesIWUPClientProxy.getByteGuid(), this.jdField_e_of_type_JavaLangString);
    }
    catch (Throwable paramArrayOfByte)
    {
      paramArrayOfByte.printStackTrace();
      this.jdField_e_of_type_JavaLangString = null;
      a("aes_enc_fail", paramArrayOfByte);
      arrayOfByte = null;
      paramArrayOfByte = localMttWupToken;
    }
    if ((arrayOfByte != null) && (paramArrayOfByte != null))
    {
      localMttWupToken = this.jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken;
      if (localMttWupToken != null)
      {
        boolean bool = false;
        try
        {
          this.jdField_c_of_type_JavaLangString = localMttWupToken.buildUrlParam(b1, this.jdField_e_of_type_JavaLangString);
          if (!TextUtils.isEmpty(this.jdField_c_of_type_JavaLangString))
          {
            this.mMttRequest.addHeader("Q-GUID", HexUtil.bytes2HexStr(paramArrayOfByte));
            this.jdField_b_of_type_ArrayOfByte = arrayOfByte;
            this.jdField_a_of_type_Byte = b1;
            return;
          }
        }
        catch (Throwable paramArrayOfByte)
        {
          a(paramArrayOfByte, false);
          return;
        }
        catch (Exception paramArrayOfByte)
        {
          if (b1 == 2) {
            bool = true;
          }
          a(paramArrayOfByte, bool);
        }
      }
    }
  }
  
  /* Error */
  byte[] a(boolean paramBoolean1, boolean paramBoolean2)
  {
    // Byte code:
    //   0: iload_1
    //   1: ifeq +64 -> 65
    //   4: aload_0
    //   5: getfield 560	com/tencent/common/wup/WUPTask:jdField_a_of_type_ArrayOfByte	[B
    //   8: ifnull +30 -> 38
    //   11: aload_0
    //   12: getfield 69	com/tencent/common/wup/WUPTask:jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken	Lcom/tencent/common/wup/security/MttWupToken;
    //   15: ifnull +23 -> 38
    //   18: aload_0
    //   19: getfield 69	com/tencent/common/wup/WUPTask:jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken	Lcom/tencent/common/wup/security/MttWupToken;
    //   22: aload_0
    //   23: getfield 560	com/tencent/common/wup/WUPTask:jdField_a_of_type_ArrayOfByte	[B
    //   26: aload_0
    //   27: getfield 81	com/tencent/common/wup/WUPTask:jdField_e_of_type_JavaLangString	Ljava/lang/String;
    //   30: invokevirtual 792	com/tencent/common/wup/security/MttWupToken:decryptWithToken	([BLjava/lang/String;)[B
    //   33: astore 4
    //   35: goto +36 -> 71
    //   38: ldc -32
    //   40: ldc_w 794
    //   43: invokestatic 189	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   46: aconst_null
    //   47: astore 4
    //   49: goto +22 -> 71
    //   52: astore 4
    //   54: aload 4
    //   56: invokevirtual 160	java/lang/Throwable:printStackTrace	()V
    //   59: aconst_null
    //   60: astore 4
    //   62: goto +9 -> 71
    //   65: aload_0
    //   66: getfield 560	com/tencent/common/wup/WUPTask:jdField_a_of_type_ArrayOfByte	[B
    //   69: astore 4
    //   71: aload 4
    //   73: astore 5
    //   75: iload_2
    //   76: ifeq +420 -> 496
    //   79: aload 4
    //   81: astore 5
    //   83: aload 4
    //   85: ifnull +411 -> 496
    //   88: new 796	java/io/ByteArrayInputStream
    //   91: dup
    //   92: aload 4
    //   94: invokespecial 798	java/io/ByteArrayInputStream:<init>	([B)V
    //   97: astore 5
    //   99: new 800	java/util/zip/GZIPInputStream
    //   102: dup
    //   103: aload 5
    //   105: invokespecial 803	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   108: astore 7
    //   110: sipush 1024
    //   113: newarray <illegal type>
    //   115: astore 6
    //   117: new 322	java/io/ByteArrayOutputStream
    //   120: dup
    //   121: invokespecial 323	java/io/ByteArrayOutputStream:<init>	()V
    //   124: astore 4
    //   126: aload 4
    //   128: astore 8
    //   130: aload 7
    //   132: astore 9
    //   134: aload 5
    //   136: astore 10
    //   138: aload 7
    //   140: aload 6
    //   142: iconst_0
    //   143: aload 6
    //   145: arraylength
    //   146: invokevirtual 806	java/util/zip/GZIPInputStream:read	([BII)I
    //   149: istore_3
    //   150: iload_3
    //   151: iconst_m1
    //   152: if_icmpeq +27 -> 179
    //   155: aload 4
    //   157: astore 8
    //   159: aload 7
    //   161: astore 9
    //   163: aload 5
    //   165: astore 10
    //   167: aload 4
    //   169: aload 6
    //   171: iconst_0
    //   172: iload_3
    //   173: invokevirtual 807	java/io/ByteArrayOutputStream:write	([BII)V
    //   176: goto -50 -> 126
    //   179: aload 4
    //   181: astore 8
    //   183: aload 7
    //   185: astore 9
    //   187: aload 5
    //   189: astore 10
    //   191: aload 4
    //   193: invokevirtual 341	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   196: astore 6
    //   198: aload 4
    //   200: astore 8
    //   202: aload 7
    //   204: astore 9
    //   206: aload 5
    //   208: astore 10
    //   210: aload 4
    //   212: invokevirtual 810	java/io/ByteArrayOutputStream:flush	()V
    //   215: aload 4
    //   217: astore 8
    //   219: aload 7
    //   221: astore 9
    //   223: aload 5
    //   225: astore 10
    //   227: aload 4
    //   229: invokevirtual 342	java/io/ByteArrayOutputStream:close	()V
    //   232: aload 4
    //   234: astore 8
    //   236: aload 7
    //   238: astore 9
    //   240: aload 5
    //   242: astore 10
    //   244: aload 7
    //   246: invokevirtual 811	java/util/zip/GZIPInputStream:close	()V
    //   249: aload 4
    //   251: astore 8
    //   253: aload 7
    //   255: astore 9
    //   257: aload 5
    //   259: astore 10
    //   261: aload 5
    //   263: invokevirtual 812	java/io/ByteArrayInputStream:close	()V
    //   266: aload 4
    //   268: invokevirtual 810	java/io/ByteArrayOutputStream:flush	()V
    //   271: aload 4
    //   273: invokevirtual 342	java/io/ByteArrayOutputStream:close	()V
    //   276: aload 7
    //   278: invokevirtual 811	java/util/zip/GZIPInputStream:close	()V
    //   281: aload 5
    //   283: invokevirtual 812	java/io/ByteArrayInputStream:close	()V
    //   286: goto +10 -> 296
    //   289: astore 4
    //   291: aload 4
    //   293: invokevirtual 160	java/lang/Throwable:printStackTrace	()V
    //   296: aload 6
    //   298: astore 5
    //   300: goto +196 -> 496
    //   303: astore 6
    //   305: goto +63 -> 368
    //   308: astore 4
    //   310: aconst_null
    //   311: astore 8
    //   313: goto +134 -> 447
    //   316: astore 6
    //   318: aconst_null
    //   319: astore 4
    //   321: goto +47 -> 368
    //   324: astore 4
    //   326: goto +20 -> 346
    //   329: astore 6
    //   331: aconst_null
    //   332: astore 7
    //   334: aload 7
    //   336: astore 4
    //   338: goto +30 -> 368
    //   341: astore 4
    //   343: aconst_null
    //   344: astore 5
    //   346: aconst_null
    //   347: astore 8
    //   349: aconst_null
    //   350: astore 7
    //   352: goto +95 -> 447
    //   355: astore 6
    //   357: aconst_null
    //   358: astore 7
    //   360: aload 7
    //   362: astore 5
    //   364: aload 5
    //   366: astore 4
    //   368: aload 4
    //   370: astore 8
    //   372: aload 7
    //   374: astore 9
    //   376: aload 5
    //   378: astore 10
    //   380: aload 6
    //   382: invokevirtual 160	java/lang/Throwable:printStackTrace	()V
    //   385: aload 4
    //   387: ifnull +16 -> 403
    //   390: aload 4
    //   392: invokevirtual 810	java/io/ByteArrayOutputStream:flush	()V
    //   395: aload 4
    //   397: invokevirtual 342	java/io/ByteArrayOutputStream:close	()V
    //   400: goto +3 -> 403
    //   403: aload 7
    //   405: ifnull +8 -> 413
    //   408: aload 7
    //   410: invokevirtual 811	java/util/zip/GZIPInputStream:close	()V
    //   413: aload 5
    //   415: ifnull +16 -> 431
    //   418: aload 5
    //   420: invokevirtual 812	java/io/ByteArrayInputStream:close	()V
    //   423: goto +8 -> 431
    //   426: aload 4
    //   428: invokevirtual 160	java/lang/Throwable:printStackTrace	()V
    //   431: aconst_null
    //   432: astore 5
    //   434: goto +62 -> 496
    //   437: astore 4
    //   439: aload 10
    //   441: astore 5
    //   443: aload 9
    //   445: astore 7
    //   447: aload 8
    //   449: ifnull +16 -> 465
    //   452: aload 8
    //   454: invokevirtual 810	java/io/ByteArrayOutputStream:flush	()V
    //   457: aload 8
    //   459: invokevirtual 342	java/io/ByteArrayOutputStream:close	()V
    //   462: goto +3 -> 465
    //   465: aload 7
    //   467: ifnull +8 -> 475
    //   470: aload 7
    //   472: invokevirtual 811	java/util/zip/GZIPInputStream:close	()V
    //   475: aload 5
    //   477: ifnull +16 -> 493
    //   480: aload 5
    //   482: invokevirtual 812	java/io/ByteArrayInputStream:close	()V
    //   485: goto +8 -> 493
    //   488: aload 5
    //   490: invokevirtual 160	java/lang/Throwable:printStackTrace	()V
    //   493: aload 4
    //   495: athrow
    //   496: new 168	java/lang/StringBuilder
    //   499: dup
    //   500: invokespecial 169	java/lang/StringBuilder:<init>	()V
    //   503: astore 4
    //   505: aload 4
    //   507: ldc_w 814
    //   510: invokevirtual 175	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   513: pop
    //   514: aload 4
    //   516: aload 5
    //   518: invokevirtual 178	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   521: pop
    //   522: aload 4
    //   524: invokevirtual 184	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   527: pop
    //   528: aload 5
    //   530: areturn
    //   531: astore 4
    //   533: goto -107 -> 426
    //   536: astore 5
    //   538: goto -50 -> 488
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	541	0	this	WUPTask
    //   0	541	1	paramBoolean1	boolean
    //   0	541	2	paramBoolean2	boolean
    //   149	24	3	k	int
    //   33	15	4	arrayOfByte1	byte[]
    //   52	3	4	localThrowable1	Throwable
    //   60	212	4	localObject1	Object
    //   289	3	4	localThrowable2	Throwable
    //   308	1	4	localObject2	Object
    //   319	1	4	localObject3	Object
    //   324	1	4	localObject4	Object
    //   336	1	4	localObject5	Object
    //   341	1	4	localObject6	Object
    //   366	61	4	localObject7	Object
    //   437	57	4	localObject8	Object
    //   503	20	4	localStringBuilder	StringBuilder
    //   531	1	4	localThrowable3	Throwable
    //   73	456	5	localObject9	Object
    //   536	1	5	localThrowable4	Throwable
    //   115	182	6	arrayOfByte2	byte[]
    //   303	1	6	localThrowable5	Throwable
    //   316	1	6	localThrowable6	Throwable
    //   329	1	6	localThrowable7	Throwable
    //   355	26	6	localThrowable8	Throwable
    //   108	363	7	localObject10	Object
    //   128	330	8	localObject11	Object
    //   132	312	9	localObject12	Object
    //   136	304	10	localObject13	Object
    // Exception table:
    //   from	to	target	type
    //   4	35	52	java/lang/Throwable
    //   38	46	52	java/lang/Throwable
    //   266	286	289	java/lang/Throwable
    //   138	150	303	java/lang/Throwable
    //   167	176	303	java/lang/Throwable
    //   191	198	303	java/lang/Throwable
    //   210	215	303	java/lang/Throwable
    //   227	232	303	java/lang/Throwable
    //   244	249	303	java/lang/Throwable
    //   261	266	303	java/lang/Throwable
    //   110	126	308	finally
    //   110	126	316	java/lang/Throwable
    //   99	110	324	finally
    //   99	110	329	java/lang/Throwable
    //   88	99	341	finally
    //   88	99	355	java/lang/Throwable
    //   138	150	437	finally
    //   167	176	437	finally
    //   191	198	437	finally
    //   210	215	437	finally
    //   227	232	437	finally
    //   244	249	437	finally
    //   261	266	437	finally
    //   380	385	437	finally
    //   390	400	531	java/lang/Throwable
    //   408	413	531	java/lang/Throwable
    //   418	423	531	java/lang/Throwable
    //   452	462	536	java/lang/Throwable
    //   470	475	536	java/lang/Throwable
    //   480	485	536	java/lang/Throwable
  }
  
  public void cancel()
  {
    setMttResponse(null);
    this.jdField_a_of_type_Boolean = true;
    this.mStatus = 6;
  }
  
  public void doRun()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("doRun, tag: ");
    localStringBuilder.append(this.mTag);
    localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("isRetring: ");
    localStringBuilder.append(isRetring());
    localStringBuilder.append(", retry times: ");
    localStringBuilder.append(this.jdField_a_of_type_Int);
    localStringBuilder.toString();
    if (!isRetring())
    {
      this.mRunningState = 1;
      b();
      this.mStatPath.add("wts");
    }
    else
    {
      this.mRunningState = 1;
    }
    if ((this.jdField_a_of_type_Int < this.jdField_c_of_type_Int) && (!this.jdField_a_of_type_Boolean))
    {
      if (!a())
      {
        this.jdField_a_of_type_Int += 1;
        if (this.jdField_a_of_type_Int < this.jdField_c_of_type_Int) {
          this.mRunningState = 3;
        }
      }
      if (!this.i) {
        a();
      }
    }
    if (this.jdField_a_of_type_Boolean) {
      this.mStatus = 6;
    }
    if (this.mRunningState != 3)
    {
      this.mStatPath.add("wtd");
      this.mRunningState = 2;
    }
    if (!isRetring())
    {
      handleResponse();
      return;
    }
    retry();
  }
  
  public Object getBindObject()
  {
    return this.jdField_a_of_type_JavaLangObject;
  }
  
  protected String getDnsIP()
  {
    DnsManager.DnsData localDnsData = this.jdField_a_of_type_ComTencentCommonServerconfigDnsManager$DnsData;
    if (localDnsData == null) {
      return "";
    }
    return localDnsData.mIP;
  }
  
  protected String getDnsType()
  {
    DnsManager.DnsData localDnsData = this.jdField_a_of_type_ComTencentCommonServerconfigDnsManager$DnsData;
    if (localDnsData == null) {
      return "";
    }
    return localDnsData.mType;
  }
  
  public int getPacketSize()
  {
    return this.jdField_b_of_type_Int;
  }
  
  /* Error */
  public byte[] getResponseData()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 63	com/tencent/common/wup/WUPTask:jdField_c_of_type_ArrayOfByte	[B
    //   4: astore_2
    //   5: aload_2
    //   6: ifnull +5 -> 11
    //   9: aload_2
    //   10: areturn
    //   11: aload_0
    //   12: getfield 560	com/tencent/common/wup/WUPTask:jdField_a_of_type_ArrayOfByte	[B
    //   15: astore_2
    //   16: aload_0
    //   17: aload_2
    //   18: putfield 63	com/tencent/common/wup/WUPTask:jdField_c_of_type_ArrayOfByte	[B
    //   21: aload_0
    //   22: getfield 46	com/tencent/common/wup/WUPTask:jdField_b_of_type_Boolean	Z
    //   25: ifeq +100 -> 125
    //   28: aload_2
    //   29: ifnull +29 -> 58
    //   32: aload_0
    //   33: getfield 69	com/tencent/common/wup/WUPTask:jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken	Lcom/tencent/common/wup/security/MttWupToken;
    //   36: ifnull +22 -> 58
    //   39: aload_0
    //   40: aload_0
    //   41: getfield 69	com/tencent/common/wup/WUPTask:jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken	Lcom/tencent/common/wup/security/MttWupToken;
    //   44: aload_2
    //   45: aload_0
    //   46: getfield 81	com/tencent/common/wup/WUPTask:jdField_e_of_type_JavaLangString	Ljava/lang/String;
    //   49: invokevirtual 792	com/tencent/common/wup/security/MttWupToken:decryptWithToken	([BLjava/lang/String;)[B
    //   52: putfield 63	com/tencent/common/wup/WUPTask:jdField_c_of_type_ArrayOfByte	[B
    //   55: goto +70 -> 125
    //   58: ldc -32
    //   60: ldc_w 858
    //   63: invokestatic 189	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   66: goto +59 -> 125
    //   69: astore_2
    //   70: aload_2
    //   71: invokevirtual 160	java/lang/Throwable:printStackTrace	()V
    //   74: aload_0
    //   75: ldc_w 860
    //   78: aload_2
    //   79: invokespecial 317	com/tencent/common/wup/WUPTask:a	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   82: aload_0
    //   83: getfield 69	com/tencent/common/wup/WUPTask:jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken	Lcom/tencent/common/wup/security/MttWupToken;
    //   86: ifnull +34 -> 120
    //   89: aload_0
    //   90: getfield 71	com/tencent/common/wup/WUPTask:jdField_c_of_type_JavaLangString	Ljava/lang/String;
    //   93: invokestatic 207	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   96: ifne +24 -> 120
    //   99: aload_0
    //   100: getfield 71	com/tencent/common/wup/WUPTask:jdField_c_of_type_JavaLangString	Ljava/lang/String;
    //   103: ldc_w 862
    //   106: invokevirtual 865	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   109: ifeq +11 -> 120
    //   112: aload_0
    //   113: getfield 69	com/tencent/common/wup/WUPTask:jdField_a_of_type_ComTencentCommonWupSecurityMttWupToken	Lcom/tencent/common/wup/security/MttWupToken;
    //   116: iconst_1
    //   117: invokevirtual 602	com/tencent/common/wup/security/MttWupToken:setExpire	(Z)V
    //   120: aload_0
    //   121: aconst_null
    //   122: putfield 63	com/tencent/common/wup/WUPTask:jdField_c_of_type_ArrayOfByte	[B
    //   125: aload_0
    //   126: getfield 48	com/tencent/common/wup/WUPTask:jdField_c_of_type_Boolean	Z
    //   129: ifeq +355 -> 484
    //   132: aload_0
    //   133: getfield 63	com/tencent/common/wup/WUPTask:jdField_c_of_type_ArrayOfByte	[B
    //   136: astore_2
    //   137: aload_2
    //   138: ifnull +346 -> 484
    //   141: new 796	java/io/ByteArrayInputStream
    //   144: dup
    //   145: aload_2
    //   146: invokespecial 798	java/io/ByteArrayInputStream:<init>	([B)V
    //   149: astore_2
    //   150: new 800	java/util/zip/GZIPInputStream
    //   153: dup
    //   154: aload_2
    //   155: invokespecial 803	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   158: astore_3
    //   159: sipush 1024
    //   162: newarray <illegal type>
    //   164: astore 4
    //   166: new 322	java/io/ByteArrayOutputStream
    //   169: dup
    //   170: invokespecial 323	java/io/ByteArrayOutputStream:<init>	()V
    //   173: astore 9
    //   175: aload_3
    //   176: astore 5
    //   178: aload_2
    //   179: astore 7
    //   181: aload 9
    //   183: astore 8
    //   185: aload_3
    //   186: aload 4
    //   188: iconst_0
    //   189: aload 4
    //   191: arraylength
    //   192: invokevirtual 806	java/util/zip/GZIPInputStream:read	([BII)I
    //   195: istore_1
    //   196: iload_1
    //   197: iconst_m1
    //   198: if_icmpeq +25 -> 223
    //   201: aload_3
    //   202: astore 5
    //   204: aload_2
    //   205: astore 7
    //   207: aload 9
    //   209: astore 8
    //   211: aload 9
    //   213: aload 4
    //   215: iconst_0
    //   216: iload_1
    //   217: invokevirtual 807	java/io/ByteArrayOutputStream:write	([BII)V
    //   220: goto -45 -> 175
    //   223: aload_3
    //   224: astore 5
    //   226: aload_2
    //   227: astore 7
    //   229: aload 9
    //   231: astore 8
    //   233: aload_0
    //   234: aload 9
    //   236: invokevirtual 341	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   239: putfield 63	com/tencent/common/wup/WUPTask:jdField_c_of_type_ArrayOfByte	[B
    //   242: aload 9
    //   244: invokevirtual 810	java/io/ByteArrayOutputStream:flush	()V
    //   247: aload 9
    //   249: invokevirtual 342	java/io/ByteArrayOutputStream:close	()V
    //   252: aload_3
    //   253: invokevirtual 811	java/util/zip/GZIPInputStream:close	()V
    //   256: aload_2
    //   257: invokevirtual 812	java/io/ByteArrayInputStream:close	()V
    //   260: goto +224 -> 484
    //   263: astore 4
    //   265: aload_2
    //   266: astore 6
    //   268: aload 9
    //   270: astore_2
    //   271: goto +69 -> 340
    //   274: astore 4
    //   276: aconst_null
    //   277: astore 8
    //   279: goto +161 -> 440
    //   282: astore 4
    //   284: aconst_null
    //   285: astore 5
    //   287: aload_2
    //   288: astore 6
    //   290: aload 5
    //   292: astore_2
    //   293: goto +47 -> 340
    //   296: astore_3
    //   297: goto +22 -> 319
    //   300: astore 4
    //   302: aconst_null
    //   303: astore_3
    //   304: aload_3
    //   305: astore 5
    //   307: aload_2
    //   308: astore 6
    //   310: aload 5
    //   312: astore_2
    //   313: goto +27 -> 340
    //   316: astore_3
    //   317: aconst_null
    //   318: astore_2
    //   319: aconst_null
    //   320: astore 8
    //   322: aload_3
    //   323: astore 4
    //   325: aconst_null
    //   326: astore_3
    //   327: goto +113 -> 440
    //   330: astore 4
    //   332: aconst_null
    //   333: astore_3
    //   334: aload_3
    //   335: astore 6
    //   337: aload 6
    //   339: astore_2
    //   340: aload_3
    //   341: astore 5
    //   343: aload 6
    //   345: astore 7
    //   347: aload_2
    //   348: astore 8
    //   350: aload 4
    //   352: invokevirtual 160	java/lang/Throwable:printStackTrace	()V
    //   355: aload_3
    //   356: astore 5
    //   358: aload 6
    //   360: astore 7
    //   362: aload_2
    //   363: astore 8
    //   365: aload_0
    //   366: ldc_w 867
    //   369: aload 4
    //   371: invokespecial 317	com/tencent/common/wup/WUPTask:a	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   374: aload_3
    //   375: astore 5
    //   377: aload 6
    //   379: astore 7
    //   381: aload_2
    //   382: astore 8
    //   384: aload_0
    //   385: aconst_null
    //   386: putfield 63	com/tencent/common/wup/WUPTask:jdField_c_of_type_ArrayOfByte	[B
    //   389: aload_2
    //   390: ifnull +14 -> 404
    //   393: aload_2
    //   394: invokevirtual 810	java/io/ByteArrayOutputStream:flush	()V
    //   397: aload_2
    //   398: invokevirtual 342	java/io/ByteArrayOutputStream:close	()V
    //   401: goto +3 -> 404
    //   404: aload_3
    //   405: ifnull +7 -> 412
    //   408: aload_3
    //   409: invokevirtual 811	java/util/zip/GZIPInputStream:close	()V
    //   412: aload 6
    //   414: ifnull +70 -> 484
    //   417: aload 6
    //   419: invokevirtual 812	java/io/ByteArrayInputStream:close	()V
    //   422: goto +62 -> 484
    //   425: aload_2
    //   426: invokevirtual 160	java/lang/Throwable:printStackTrace	()V
    //   429: goto +55 -> 484
    //   432: astore 4
    //   434: aload 7
    //   436: astore_2
    //   437: aload 5
    //   439: astore_3
    //   440: aload 8
    //   442: ifnull +16 -> 458
    //   445: aload 8
    //   447: invokevirtual 810	java/io/ByteArrayOutputStream:flush	()V
    //   450: aload 8
    //   452: invokevirtual 342	java/io/ByteArrayOutputStream:close	()V
    //   455: goto +3 -> 458
    //   458: aload_3
    //   459: ifnull +7 -> 466
    //   462: aload_3
    //   463: invokevirtual 811	java/util/zip/GZIPInputStream:close	()V
    //   466: aload_2
    //   467: ifnull +14 -> 481
    //   470: aload_2
    //   471: invokevirtual 812	java/io/ByteArrayInputStream:close	()V
    //   474: goto +7 -> 481
    //   477: aload_2
    //   478: invokevirtual 160	java/lang/Throwable:printStackTrace	()V
    //   481: aload 4
    //   483: athrow
    //   484: new 168	java/lang/StringBuilder
    //   487: dup
    //   488: invokespecial 169	java/lang/StringBuilder:<init>	()V
    //   491: astore_2
    //   492: aload_2
    //   493: ldc_w 814
    //   496: invokevirtual 175	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   499: pop
    //   500: aload_2
    //   501: aload_0
    //   502: getfield 63	com/tencent/common/wup/WUPTask:jdField_c_of_type_ArrayOfByte	[B
    //   505: invokevirtual 178	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   508: pop
    //   509: aload_2
    //   510: invokevirtual 184	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   513: pop
    //   514: aload_0
    //   515: getfield 63	com/tencent/common/wup/WUPTask:jdField_c_of_type_ArrayOfByte	[B
    //   518: areturn
    //   519: astore_2
    //   520: goto -95 -> 425
    //   523: astore_2
    //   524: goto -47 -> 477
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	527	0	this	WUPTask
    //   195	22	1	k	int
    //   4	41	2	arrayOfByte1	byte[]
    //   69	10	2	localThrowable1	Throwable
    //   136	374	2	localObject1	Object
    //   519	1	2	localThrowable2	Throwable
    //   523	1	2	localThrowable3	Throwable
    //   158	95	3	localGZIPInputStream	java.util.zip.GZIPInputStream
    //   296	1	3	localObject2	Object
    //   303	2	3	localObject3	Object
    //   316	7	3	localObject4	Object
    //   326	137	3	localObject5	Object
    //   164	50	4	arrayOfByte2	byte[]
    //   263	1	4	localThrowable4	Throwable
    //   274	1	4	localObject6	Object
    //   282	1	4	localThrowable5	Throwable
    //   300	1	4	localThrowable6	Throwable
    //   323	1	4	localObject7	Object
    //   330	40	4	localThrowable7	Throwable
    //   432	50	4	localObject8	Object
    //   176	262	5	localObject9	Object
    //   266	152	6	localObject10	Object
    //   179	256	7	localObject11	Object
    //   183	268	8	localObject12	Object
    //   173	96	9	localByteArrayOutputStream	java.io.ByteArrayOutputStream
    // Exception table:
    //   from	to	target	type
    //   32	55	69	java/lang/Throwable
    //   58	66	69	java/lang/Throwable
    //   185	196	263	java/lang/Throwable
    //   211	220	263	java/lang/Throwable
    //   233	242	263	java/lang/Throwable
    //   159	175	274	finally
    //   159	175	282	java/lang/Throwable
    //   150	159	296	finally
    //   150	159	300	java/lang/Throwable
    //   141	150	316	finally
    //   141	150	330	java/lang/Throwable
    //   185	196	432	finally
    //   211	220	432	finally
    //   233	242	432	finally
    //   350	355	432	finally
    //   365	374	432	finally
    //   384	389	432	finally
    //   242	260	519	java/lang/Throwable
    //   393	401	519	java/lang/Throwable
    //   408	412	519	java/lang/Throwable
    //   417	422	519	java/lang/Throwable
    //   445	455	523	java/lang/Throwable
    //   462	466	523	java/lang/Throwable
    //   470	474	523	java/lang/Throwable
  }
  
  public ArrayList<String> getStatPath()
  {
    return this.mStatPath;
  }
  
  public String getTaskUrl()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  protected String getTaskUrlBeforeDns()
  {
    return this.jdField_b_of_type_JavaLangString;
  }
  
  public void handleResponse()
  {
    Runnable local1 = new Runnable()
    {
      public void run()
      {
        WUPTask localWUPTask = WUPTask.this;
        localWUPTask.fireObserverEvent(localWUPTask.mStatus);
        WUPTask.this.finish("done");
      }
    };
    if ((this.mTaskExecutors != null) && (this.mTaskExecutors.getDeliveryExecutor() != null))
    {
      this.mTaskExecutors.getDeliveryExecutor().execute(local1);
      return;
    }
    local1.run();
  }
  
  public boolean hasSetServer()
  {
    return this.f;
  }
  
  protected boolean isRespFromTestServer()
  {
    return this.h;
  }
  
  public void onIntercept(HttpURLConnection paramHttpURLConnection)
  {
    if (paramHttpURLConnection != null)
    {
      if (!this.j) {
        return;
      }
      boolean bool2 = false;
      WupConnectionPool localWupConnectionPool = this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a();
      boolean bool1 = bool2;
      if (localWupConnectionPool != null)
      {
        bool1 = bool2;
        if (localWupConnectionPool.isValid()) {
          bool1 = localWupConnectionPool.attachConnection(paramHttpURLConnection);
        }
      }
      if (!bool1) {
        setConnectionClose();
      }
      return;
    }
  }
  
  public void setBindObject(Object paramObject)
  {
    this.jdField_a_of_type_JavaLangObject = paramObject;
    if ((paramObject instanceof WUPRequestBase))
    {
      paramObject = (WUPRequestBase)paramObject;
      if (this.mMttRequest != null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(((WUPRequestBase)paramObject).getServerName());
        localStringBuilder.append("/");
        localStringBuilder.append(((WUPRequestBase)paramObject).getFuncName());
        paramObject = localStringBuilder.toString();
        this.mMttRequest.setTag((String)paramObject);
      }
    }
  }
  
  protected void setKeepAliveEnable(boolean paramBoolean)
  {
    if ((paramBoolean) && (this.jdField_a_of_type_ComTencentCommonWupWUPTaskClient.a().isValid())) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    }
    this.j = paramBoolean;
    if (paramBoolean)
    {
      this.mMttRequest.addHeader("Connection", "keep-alive");
      return;
    }
    super.setConnectionClose();
  }
  
  public void setPacketSize(int paramInt)
  {
    this.jdField_b_of_type_Int = paramInt;
  }
  
  protected void setRequestPolicy(RequestPolicy paramRequestPolicy)
  {
    this.jdField_a_of_type_ComTencentCommonWupRequestPolicy = paramRequestPolicy;
  }
  
  public void setUrl(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    this.f = true;
    MttRequestBase localMttRequestBase = this.mMttRequest;
    this.jdField_a_of_type_JavaLangString = paramString;
    localMttRequestBase.setUrl(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\WUPTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */