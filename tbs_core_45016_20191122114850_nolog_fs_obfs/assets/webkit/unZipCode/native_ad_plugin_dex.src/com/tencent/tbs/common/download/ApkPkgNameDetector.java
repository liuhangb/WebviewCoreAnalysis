package com.tencent.tbs.common.download;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.ValueCallback;
import com.tencent.common.http.RequesterFactory;
import com.tencent.common.utils.ByteUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.Md5Utils;
import com.tencent.common.utils.StringUtils;
import com.tencent.common.utils.UrlUtils;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.common.wup.WUPTaskProxy;
import com.tencent.mtt.base.task.Task;
import com.tencent.mtt.browser.download.engine.DownloadInfo;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.download.MTT.ChannelUrlDetectReq;
import com.tencent.tbs.common.download.MTT.ChannelUrlDetectResp;
import com.tencent.tbs.common.download.MTT.DistReqHeader;
import com.tencent.tbs.common.wup.WUPRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class ApkPkgNameDetector
{
  public static final String BC_APK_DST_PERFORMANCE = "BC_APK_DST_PERFORMANCE";
  static final String TAG = "ApkPkgNameDetector";
  private static ApkPkgNameDetector mInstance;
  private static Context mcontext;
  ConcurrentHashMap<String, Detector> mDetectorMap = new ConcurrentHashMap();
  private ArrayList<String> mDomainList = null;
  ConcurrentHashMap<String, DetectListener> mListenerMap = new ConcurrentHashMap();
  public int mSegmentSize = 256;
  ConcurrentHashMap<String, DetectStatus> mStatusMap = new ConcurrentHashMap();
  public String mTargetMarketPkgName = "";
  
  public static final ApkPkgNameDetector getInstance(Context paramContext)
  {
    try
    {
      if (mInstance == null)
      {
        mcontext = paramContext;
        mInstance = new ApkPkgNameDetector();
      }
      paramContext = mInstance;
      return paramContext;
    }
    finally {}
  }
  
  public void addDetectorTask(DownloadInfo paramDownloadInfo, DetectListener paramDetectListener)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:659)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:820)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:843)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public void addDetectorTask(String paramString, final ValueCallback<String> paramValueCallback)
  {
    DownloadInfo localDownloadInfo = new DownloadInfo();
    localDownloadInfo.url = paramString;
    addDetectorTask(localDownloadInfo, new DetectListener()
    {
      public void onResult(ApkPkgNameDetector.DetectStatus paramAnonymousDetectStatus)
      {
        try
        {
          JSONObject localJSONObject = new JSONObject();
          if (paramAnonymousDetectStatus.mStatus == 1)
          {
            localJSONObject.put("ret", true);
            localJSONObject.put("appName", paramAnonymousDetectStatus.mDetectResult.appName);
            localJSONObject.put("apkFileSize", paramAnonymousDetectStatus.mDetectResult.apkFileSize);
            localJSONObject.put("apkIconUrl", paramAnonymousDetectStatus.mDetectResult.apkIconUrl);
            localJSONObject.put("apkType", paramAnonymousDetectStatus.mDetectResult.apkType);
            localJSONObject.put("packageName", paramAnonymousDetectStatus.mDetectResult.packageName);
            paramValueCallback.onReceiveValue(localJSONObject.toString());
            return;
          }
          localJSONObject.put("ret", false);
          paramValueCallback.onReceiveValue(localJSONObject.toString());
          return;
        }
        catch (JSONException paramAnonymousDetectStatus)
        {
          paramAnonymousDetectStatus.printStackTrace();
        }
      }
    });
  }
  
  void detectFailed(String paramString)
  {
    LogUtils.d("ApkPkgNameDetector", "============md5检测包名失败==================");
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("替换的地址：");
    ((StringBuilder)localObject).append(paramString);
    LogUtils.d("ApkPkgNameDetector", ((StringBuilder)localObject).toString());
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    DetectListener localDetectListener = (DetectListener)this.mListenerMap.get(paramString);
    if (localDetectListener == null) {
      return;
    }
    localObject = (DetectStatus)this.mStatusMap.get(paramString);
    paramString = (String)localObject;
    if (localObject == null) {
      paramString = new DetectStatus();
    }
    paramString.mStatus = -1;
    localDetectListener.onResult(paramString);
  }
  
  public void removeDetectorTask(String paramString)
  {
    LogUtils.d("ApkPkgNameDetector", "removeDetectorTask");
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    this.mListenerMap.remove(paramString);
    this.mStatusMap.remove(paramString);
    paramString = (Detector)this.mDetectorMap.remove(paramString);
    if (paramString != null) {
      paramString.cancel();
    }
  }
  
  public static abstract interface DetectListener
  {
    public abstract void onResult(ApkPkgNameDetector.DetectStatus paramDetectStatus);
  }
  
  public static class DetectStatus
  {
    public ChannelUrlDetectResp mDetectResult = null;
    public int mStatus = -1;
  }
  
  public class Detector
    implements IWUPRequestCallBack
  {
    public static final int ECODE_CANNCELED = -7000;
    public static final int ECODE_CHANEL_URL_GETTED = 1000;
    public static final int ECODE_ERROR = -2000;
    public static final int ECODE_EXCEPTION = -3000;
    public static final int ECODE_IN_BLACK_LIST = -8000;
    public static final int ECODE_IOEXCEPTION_WHEN_READ = -3001;
    public static final int ECODE_NOT_FOUND = -6000;
    public static final int ECODE_ONLY_PKG_NAME = 1001;
    public static final int ECODE_ON_WUP_ERROR_RESP = -5003;
    public static final int ECODE_ON_WUP_TASK_FAIL = -5001;
    public static final int ECODE_OUT_OF_302_RETRY_TIMES = -1001;
    public static final int ECODE_OUT_OF_RANGE_RETRY_TIMES = -1002;
    public static final int ECODE_OUT_OF_RETRY_TIMES = -1000;
    public static final int ECODE_SIZE_ERROR = -4000;
    public static final int ECODE_WUP_ERROR_DATA = -5002;
    public static final int ECODE_WUP_SEND_FAIL = -5000;
    byte[] mBuffer = new byte[this.mBufferSize];
    int mBufferSize = ApkPkgNameDetector.this.mSegmentSize;
    boolean mCanceled = false;
    String mErrorDes = "";
    int mFromTbs = 0;
    boolean mIsPostTask = false;
    String mOriginalUrl = "";
    String mPostData = "";
    String mRefer = "";
    int mRetryTimes = 0;
    int mRetryTimes302 = 0;
    int mRetryTimesEndRange = 0;
    int mRetryTimesNoNet = 0;
    int mRetryTimesRange = 0;
    String mTargetUrl = "";
    String mTimeCostConnect = "";
    long mTimeCostRead = -1L;
    long mTimeCostWUP = -1L;
    long mTimestampCreate = System.currentTimeMillis();
    long mTimestampWupSended = -1L;
    
    protected Detector(int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
    {
      this.mOriginalUrl = paramString1;
      this.mTargetUrl = paramString1;
      this.mRefer = paramString2;
      this.mPostData = paramString3;
      this.mIsPostTask = paramBoolean;
      this.mFromTbs = paramInt;
    }
    
    public void cancel()
    {
      this.mCanceled = true;
    }
    
    public void onWUPTaskFail(WUPRequestBase paramWUPRequestBase)
    {
      this.mTimeCostWUP = (System.currentTimeMillis() - this.mTimestampWupSended);
      LogUtils.d("ApkPkgNameDetector", "onWUPTaskFail");
      String str2 = "";
      String str1 = str2;
      if (paramWUPRequestBase != null)
      {
        str1 = str2;
        if ((paramWUPRequestBase.getBindObject() instanceof String)) {
          str1 = (String)paramWUPRequestBase.getBindObject();
        }
      }
      ApkPkgNameDetector.this.detectFailed(str1);
      reportDetectPerformance(60535);
    }
    
    public void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase)
    {
      this.mTimeCostWUP = (System.currentTimeMillis() - this.mTimestampWupSended);
      LogUtils.d("ApkPkgNameDetector", "onWUPTaskSuccess");
      Object localObject2 = "";
      Object localObject1 = localObject2;
      if (paramWUPRequestBase != null)
      {
        localObject1 = localObject2;
        if ((paramWUPRequestBase.getBindObject() instanceof String)) {
          localObject1 = (String)paramWUPRequestBase.getBindObject();
        }
      }
      if (TextUtils.isEmpty((CharSequence)localObject1)) {
        return;
      }
      if (paramWUPResponseBase == null)
      {
        ApkPkgNameDetector.this.detectFailed((String)localObject1);
        reportDetectPerformance(60533);
        return;
      }
      paramWUPRequestBase = paramWUPResponseBase.get("resp");
      if ((paramWUPRequestBase instanceof ChannelUrlDetectResp))
      {
        localObject2 = (ChannelUrlDetectResp)paramWUPRequestBase;
        paramWUPRequestBase = new StringBuilder();
        paramWUPRequestBase.append("detectResp ： ");
        paramWUPRequestBase.append(localObject2);
        LogUtils.d("ApkPkgNameDetector", paramWUPRequestBase.toString());
        if ((TextUtils.isEmpty(((ChannelUrlDetectResp)localObject2).channelUrl)) && (TextUtils.isEmpty(((ChannelUrlDetectResp)localObject2).packageName)) && ((((ChannelUrlDetectResp)localObject2).apkSwitch & 0x1) == 0))
        {
          ApkPkgNameDetector.this.detectFailed((String)localObject1);
          reportDetectPerformance(59536);
          return;
        }
        int i = 1001;
        if ((((ChannelUrlDetectResp)localObject2).apkSwitch & 0x1) > 0) {
          i = 57536;
        } else if (!TextUtils.isEmpty(((ChannelUrlDetectResp)localObject2).channelUrl)) {
          i = 1000;
        }
        ApkPkgNameDetector.DetectListener localDetectListener = (ApkPkgNameDetector.DetectListener)ApkPkgNameDetector.this.mListenerMap.get(localObject1);
        if (localDetectListener == null)
        {
          reportDetectPerformance(i);
          return;
        }
        paramWUPResponseBase = (ApkPkgNameDetector.DetectStatus)ApkPkgNameDetector.this.mStatusMap.get(localObject1);
        paramWUPRequestBase = paramWUPResponseBase;
        if (paramWUPResponseBase == null) {
          paramWUPRequestBase = new ApkPkgNameDetector.DetectStatus();
        }
        paramWUPRequestBase.mStatus = 1;
        paramWUPRequestBase.mDetectResult = ((ChannelUrlDetectResp)localObject2);
        LogUtils.d("ApkPkgNameDetector", "============md5检测包名成功==================");
        paramWUPResponseBase = new StringBuilder();
        paramWUPResponseBase.append("替换的地址：");
        paramWUPResponseBase.append((String)localObject1);
        LogUtils.d("ApkPkgNameDetector", paramWUPResponseBase.toString());
        paramWUPResponseBase = new StringBuilder();
        paramWUPResponseBase.append("包名：");
        paramWUPResponseBase.append(((ChannelUrlDetectResp)localObject2).packageName);
        LogUtils.d("ApkPkgNameDetector", paramWUPResponseBase.toString());
        paramWUPResponseBase = new StringBuilder();
        paramWUPResponseBase.append("渠道url：");
        paramWUPResponseBase.append(((ChannelUrlDetectResp)localObject2).channelUrl);
        LogUtils.d("ApkPkgNameDetector", paramWUPResponseBase.toString());
        localDetectListener.onResult(paramWUPRequestBase);
        reportDetectPerformance(i);
        return;
      }
      ApkPkgNameDetector.this.detectFailed((String)localObject1);
      reportDetectPerformance(60534);
    }
    
    void reportDetectPerformance(int paramInt)
    {
      LogUtils.d("ApkPkgNameDetector", "===============stat performance==============");
      HashMap localHashMap = new HashMap();
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramInt);
      ((StringBuilder)localObject).append("");
      localHashMap.put("ECode", ((StringBuilder)localObject).toString());
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("ECode : ");
      ((StringBuilder)localObject).append(paramInt);
      LogUtils.d("ApkPkgNameDetector", ((StringBuilder)localObject).toString());
      boolean bool = TextUtils.isEmpty(this.mErrorDes);
      paramInt = 0;
      if (!bool)
      {
        if (this.mErrorDes.length() > 200) {
          this.mErrorDes = this.mErrorDes.substring(0, 199);
        }
        localHashMap.put("EDes", this.mErrorDes);
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("EDes : ");
        ((StringBuilder)localObject).append(this.mErrorDes);
        LogUtils.d("ApkPkgNameDetector", ((StringBuilder)localObject).toString());
      }
      if (!this.mTimeCostConnect.contains("_"))
      {
        localHashMap.put("TimeCostConnect", this.mTimeCostConnect);
      }
      else
      {
        localObject = this.mTimeCostConnect.split("_");
        if (localObject != null)
        {
          long l = 0L;
          while (paramInt < localObject.length)
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("TimeCostConnect_");
            localStringBuilder.append(paramInt);
            localHashMap.put(localStringBuilder.toString(), localObject[paramInt]);
            l += StringUtils.parseLong(localObject[paramInt], 0L);
            paramInt += 1;
          }
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append(l);
          ((StringBuilder)localObject).append("");
          localHashMap.put("TimeCostConnect", ((StringBuilder)localObject).toString());
        }
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("TimeCostConnect : ");
      ((StringBuilder)localObject).append(this.mTimeCostConnect);
      LogUtils.d("ApkPkgNameDetector", ((StringBuilder)localObject).toString());
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(this.mTimeCostRead);
      ((StringBuilder)localObject).append("");
      localHashMap.put("TimeCostRead", ((StringBuilder)localObject).toString());
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("TimeCostRead : ");
      ((StringBuilder)localObject).append(this.mTimeCostRead);
      LogUtils.d("ApkPkgNameDetector", ((StringBuilder)localObject).toString());
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(this.mTimeCostWUP);
      ((StringBuilder)localObject).append("");
      localHashMap.put("TimeCostWup", ((StringBuilder)localObject).toString());
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("TimeCostWup : ");
      ((StringBuilder)localObject).append(this.mTimeCostWUP);
      LogUtils.d("ApkPkgNameDetector", ((StringBuilder)localObject).toString());
      if (this.mRetryTimes > 0)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(this.mRetryTimes);
        ((StringBuilder)localObject).append("");
        localHashMap.put("RetryTimes", ((StringBuilder)localObject).toString());
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("RetryTimes : ");
        ((StringBuilder)localObject).append(this.mRetryTimes);
        LogUtils.d("ApkPkgNameDetector", ((StringBuilder)localObject).toString());
      }
      if (this.mRetryTimes302 > 0)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(this.mRetryTimes302);
        ((StringBuilder)localObject).append("");
        localHashMap.put("RetryTimes302", ((StringBuilder)localObject).toString());
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("mRetryTimes302 : ");
        ((StringBuilder)localObject).append(this.mRetryTimes302);
        LogUtils.d("ApkPkgNameDetector", ((StringBuilder)localObject).toString());
      }
      if (this.mRetryTimesNoNet > 0)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(this.mRetryTimesNoNet);
        ((StringBuilder)localObject).append("");
        localHashMap.put("RetryTimesNoNet", ((StringBuilder)localObject).toString());
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("RetryTimesNoNet : ");
        ((StringBuilder)localObject).append(this.mRetryTimesNoNet);
        LogUtils.d("ApkPkgNameDetector", ((StringBuilder)localObject).toString());
      }
      if (this.mRetryTimesRange > 0)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(this.mRetryTimesRange);
        ((StringBuilder)localObject).append("");
        localHashMap.put("RetryTimesRange", ((StringBuilder)localObject).toString());
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("RetryTimesRange : ");
        ((StringBuilder)localObject).append(this.mRetryTimesRange);
        LogUtils.d("ApkPkgNameDetector", ((StringBuilder)localObject).toString());
      }
      localObject = UrlUtils.getHost(this.mOriginalUrl);
      localHashMap.put("Host", localObject);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Host : ");
      localStringBuilder.append((String)localObject);
      LogUtils.d("ApkPkgNameDetector", localStringBuilder.toString());
      localHashMap.put("Url", this.mOriginalUrl);
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Url : ");
      ((StringBuilder)localObject).append(this.mOriginalUrl);
      LogUtils.d("ApkPkgNameDetector", ((StringBuilder)localObject).toString());
      X5CoreBeaconUploader.getInstance(ApkPkgNameDetector.mcontext).upLoadToBeacon("BC_APK_DST_PERFORMANCE", localHashMap);
    }
    
    public void start()
    {
      new DetectTask().start();
    }
    
    public class DetectTask
      extends Task
    {
      public static final int FIRE_THREHOLD = 1000;
      private int MAX_RETRY_TIMES = 1;
      private String mRangeString = "";
      private int mReadTimeout = 20000;
      private boolean mSurportEndRange = true;
      private Thread mThread;
      
      protected DetectTask()
      {
        this.mMttRequest = RequesterFactory.getMttRequestBase();
      }
      
      private void downloadFinished()
      {
        LogUtils.d("ApkPkgNameDetector", "get md5 String start!");
        Object localObject1 = ByteUtils.byteToHexString(Md5Utils.getMD5(ApkPkgNameDetector.Detector.this.mBuffer));
        LogUtils.d("ApkPkgNameDetector", "get md5 String end!");
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("MD5 : ");
        ((StringBuilder)localObject2).append((String)localObject1);
        LogUtils.d("ApkPkgNameDetector", ((StringBuilder)localObject2).toString());
        LogUtils.d("ApkPkgNameDetector", "downloadFinished!!");
        DistReqHeader localDistReqHeader = new DistReqHeader();
        localObject2 = new ChannelUrlDetectReq();
        ((ChannelUrlDetectReq)localObject2).stHeader = localDistReqHeader;
        ((ChannelUrlDetectReq)localObject2).segmentSize = ApkPkgNameDetector.this.mSegmentSize;
        ((ChannelUrlDetectReq)localObject2).segmentMd5 = ((String)localObject1);
        ((ChannelUrlDetectReq)localObject2).referer = ApkPkgNameDetector.Detector.this.mRefer;
        ((ChannelUrlDetectReq)localObject2).url = ApkPkgNameDetector.Detector.this.mOriginalUrl;
        ((ChannelUrlDetectReq)localObject2).versionCode = 620;
        ((ChannelUrlDetectReq)localObject2).iFromTBS = ApkPkgNameDetector.Detector.this.mFromTbs;
        localObject1 = new WUPRequest("appdistribution", "dectectChannelUrl");
        ((WUPRequest)localObject1).setEncodeName("UTF-8");
        ((WUPRequest)localObject1).put("req", localObject2);
        ((WUPRequest)localObject1).setNeedEncrypt(true);
        ((WUPRequest)localObject1).setBindObject(ApkPkgNameDetector.Detector.this.mOriginalUrl);
        ((WUPRequest)localObject1).setRequestCallBack(ApkPkgNameDetector.Detector.this);
        ApkPkgNameDetector.Detector.this.mTimestampWupSended = System.currentTimeMillis();
        if (WUPTaskProxy.send((WUPRequestBase)localObject1))
        {
          LogUtils.d("ApkPkgNameDetector", "wup has been sent!!");
          return;
        }
        ApkPkgNameDetector.Detector.this.mTimeCostWUP = (System.currentTimeMillis() - ApkPkgNameDetector.Detector.this.mTimestampWupSended);
        ApkPkgNameDetector.Detector.this.reportDetectPerformance(60536);
        ApkPkgNameDetector.this.detectFailed(ApkPkgNameDetector.Detector.this.mOriginalUrl);
      }
      
      private void handleSleep(long paramLong)
      {
        try
        {
          Thread.sleep(paramLong);
        }
        catch (InterruptedException localInterruptedException)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Interrupted while sleeping to retry - ");
          localStringBuilder.append(localInterruptedException);
          LogUtils.d("ApkPkgNameDetector", localStringBuilder.toString());
        }
        ApkPkgNameDetector.Detector localDetector = ApkPkgNameDetector.Detector.this;
        localDetector.mRetryTimes += 1;
      }
      
      private void taskCanceled()
      {
        ApkPkgNameDetector.this.detectFailed(ApkPkgNameDetector.Detector.this.mOriginalUrl);
        ApkPkgNameDetector.Detector.this.reportDetectPerformance(58536);
        LogUtils.d("ApkPkgNameDetector", "closeQuietly start");
        closeQuietly();
        LogUtils.d("ApkPkgNameDetector", "closeQuietly end");
      }
      
      public void cancel() {}
      
      /* Error */
      public void doRun()
      {
        // Byte code:
        //   0: ldc 56
        //   2: ldc -20
        //   4: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   7: iconst_0
        //   8: istore 4
        //   10: iconst_0
        //   11: istore_3
        //   12: aload_0
        //   13: iconst_0
        //   14: putfield 239	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mCanceled	Z
        //   17: aload_0
        //   18: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   21: aload_0
        //   22: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   25: getfield 242	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mTargetUrl	Ljava/lang/String;
        //   28: invokevirtual 247	com/tencent/common/http/MttRequestBase:setUrl	(Ljava/lang/String;)V
        //   31: aload_0
        //   32: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   35: aload_0
        //   36: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   39: getfield 123	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRefer	Ljava/lang/String;
        //   42: invokevirtual 250	com/tencent/common/http/MttRequestBase:setReferer	(Ljava/lang/String;)V
        //   45: aload_0
        //   46: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   49: astore 11
        //   51: aload 11
        //   53: iconst_0
        //   54: putfield 218	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes	I
        //   57: aload 11
        //   59: iconst_0
        //   60: putfield 253	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimesNoNet	I
        //   63: aload 11
        //   65: iconst_0
        //   66: putfield 256	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes302	I
        //   69: aload_0
        //   70: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   73: getfield 218	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes	I
        //   76: aload_0
        //   77: getfield 34	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:MAX_RETRY_TIMES	I
        //   80: if_icmple +31 -> 111
        //   83: aload_0
        //   84: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   87: sipush 64536
        //   90: invokevirtual 198	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:reportDetectPerformance	(I)V
        //   93: aload_0
        //   94: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   97: getfield 111	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:this$0	Lcom/tencent/tbs/common/download/ApkPkgNameDetector;
        //   100: aload_0
        //   101: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   104: getfield 129	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mOriginalUrl	Ljava/lang/String;
        //   107: invokevirtual 201	com/tencent/tbs/common/download/ApkPkgNameDetector:detectFailed	(Ljava/lang/String;)V
        //   110: return
        //   111: aload_0
        //   112: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   115: getfield 256	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes302	I
        //   118: bipush 8
        //   120: if_icmple +31 -> 151
        //   123: aload_0
        //   124: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   127: sipush 64535
        //   130: invokevirtual 198	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:reportDetectPerformance	(I)V
        //   133: aload_0
        //   134: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   137: getfield 111	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:this$0	Lcom/tencent/tbs/common/download/ApkPkgNameDetector;
        //   140: aload_0
        //   141: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   144: getfield 129	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mOriginalUrl	Ljava/lang/String;
        //   147: invokevirtual 201	com/tencent/tbs/common/download/ApkPkgNameDetector:detectFailed	(Ljava/lang/String;)V
        //   150: return
        //   151: aload_0
        //   152: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   155: getfield 259	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimesRange	I
        //   158: iconst_1
        //   159: if_icmple +31 -> 190
        //   162: aload_0
        //   163: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   166: sipush 64534
        //   169: invokevirtual 198	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:reportDetectPerformance	(I)V
        //   172: aload_0
        //   173: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   176: getfield 111	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:this$0	Lcom/tencent/tbs/common/download/ApkPkgNameDetector;
        //   179: aload_0
        //   180: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   183: getfield 129	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mOriginalUrl	Ljava/lang/String;
        //   186: invokevirtual 201	com/tencent/tbs/common/download/ApkPkgNameDetector:detectFailed	(Ljava/lang/String;)V
        //   189: return
        //   190: aload_0
        //   191: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   194: getfield 262	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimesEndRange	I
        //   197: iconst_1
        //   198: if_icmple +31 -> 229
        //   201: aload_0
        //   202: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   205: sipush 64534
        //   208: invokevirtual 198	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:reportDetectPerformance	(I)V
        //   211: aload_0
        //   212: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   215: getfield 111	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:this$0	Lcom/tencent/tbs/common/download/ApkPkgNameDetector;
        //   218: aload_0
        //   219: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   222: getfield 129	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mOriginalUrl	Ljava/lang/String;
        //   225: invokevirtual 201	com/tencent/tbs/common/download/ApkPkgNameDetector:detectFailed	(Ljava/lang/String;)V
        //   228: return
        //   229: new 264	java/net/URL
        //   232: dup
        //   233: aload_0
        //   234: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   237: invokevirtual 267	com/tencent/common/http/MttRequestBase:getUrl	()Ljava/lang/String;
        //   240: invokespecial 269	java/net/URL:<init>	(Ljava/lang/String;)V
        //   243: invokevirtual 272	java/net/URL:getHost	()Ljava/lang/String;
        //   246: astore 11
        //   248: aload 11
        //   250: invokestatic 278	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
        //   253: ifne +34 -> 287
        //   256: aload 11
        //   258: ldc_w 280
        //   261: invokevirtual 285	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
        //   264: ifeq +23 -> 287
        //   267: aload 11
        //   269: ldc_w 287
        //   272: invokevirtual 285	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
        //   275: istore 6
        //   277: iload 6
        //   279: ifeq +8 -> 287
        //   282: iconst_1
        //   283: istore_1
        //   284: goto +5 -> 289
        //   287: iconst_0
        //   288: istore_1
        //   289: aload_0
        //   290: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   293: invokevirtual 267	com/tencent/common/http/MttRequestBase:getUrl	()Ljava/lang/String;
        //   296: astore 11
        //   298: iload_1
        //   299: istore_2
        //   300: aload 11
        //   302: invokestatic 278	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
        //   305: ifne +83 -> 388
        //   308: iload_1
        //   309: istore_2
        //   310: aload 11
        //   312: ldc_w 287
        //   315: invokevirtual 285	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
        //   318: ifeq +70 -> 388
        //   321: aload 11
        //   323: ldc_w 289
        //   326: invokestatic 295	java/net/URLEncoder:encode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   329: ldc_w 297
        //   332: ldc_w 299
        //   335: invokevirtual 302	java/lang/String:replaceAll	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   338: ldc_w 304
        //   341: ldc_w 306
        //   344: invokevirtual 302	java/lang/String:replaceAll	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   347: ldc_w 308
        //   350: ldc_w 310
        //   353: invokevirtual 302	java/lang/String:replaceAll	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   356: astore 11
        //   358: aload_0
        //   359: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   362: aload 11
        //   364: invokevirtual 247	com/tencent/common/http/MttRequestBase:setUrl	(Ljava/lang/String;)V
        //   367: iload_1
        //   368: istore_2
        //   369: goto +19 -> 388
        //   372: astore 11
        //   374: goto +7 -> 381
        //   377: astore 11
        //   379: iconst_0
        //   380: istore_1
        //   381: aload 11
        //   383: invokevirtual 313	java/lang/Exception:printStackTrace	()V
        //   386: iload_1
        //   387: istore_2
        //   388: invokestatic 319	com/tencent/tbs/common/utils/DeviceUtils:getSdkVersion	()I
        //   391: bipush 19
        //   393: if_icmpne +5 -> 398
        //   396: iconst_1
        //   397: istore_2
        //   398: aload_0
        //   399: iload_2
        //   400: invokestatic 323	com/tencent/common/http/RequesterFactory:getRequester	(I)Lcom/tencent/common/http/Requester;
        //   403: putfield 327	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mRequester	Lcom/tencent/common/http/Requester;
        //   406: aload_0
        //   407: getfield 327	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mRequester	Lcom/tencent/common/http/Requester;
        //   410: aload_0
        //   411: getfield 36	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mReadTimeout	I
        //   414: invokevirtual 332	com/tencent/common/http/Requester:setReadTimeout	(I)V
        //   417: ldc 56
        //   419: ldc_w 334
        //   422: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   425: aload_0
        //   426: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   429: ldc_w 336
        //   432: ldc_w 338
        //   435: invokevirtual 341	com/tencent/common/http/MttRequestBase:addHeader	(Ljava/lang/String;Ljava/lang/String;)V
        //   438: aload_0
        //   439: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   442: invokevirtual 267	com/tencent/common/http/MttRequestBase:getUrl	()Ljava/lang/String;
        //   445: astore 11
        //   447: aload 11
        //   449: invokestatic 278	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
        //   452: ifne +19 -> 471
        //   455: aload 11
        //   457: ldc_w 343
        //   460: invokevirtual 285	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
        //   463: ifeq +8 -> 471
        //   466: aload_0
        //   467: iconst_0
        //   468: putfield 42	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mSurportEndRange	Z
        //   471: aload_0
        //   472: getfield 42	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mSurportEndRange	Z
        //   475: ifeq +86 -> 561
        //   478: aload_0
        //   479: getfield 40	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mRangeString	Ljava/lang/String;
        //   482: invokestatic 278	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
        //   485: ifeq +59 -> 544
        //   488: aload_0
        //   489: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   492: astore 11
        //   494: new 84	java/lang/StringBuilder
        //   497: dup
        //   498: invokespecial 85	java/lang/StringBuilder:<init>	()V
        //   501: astore 12
        //   503: aload 12
        //   505: ldc_w 345
        //   508: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   511: pop
        //   512: aload 12
        //   514: aload_0
        //   515: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   518: getfield 111	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:this$0	Lcom/tencent/tbs/common/download/ApkPkgNameDetector;
        //   521: getfield 114	com/tencent/tbs/common/download/ApkPkgNameDetector:mSegmentSize	I
        //   524: invokevirtual 348	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   527: pop
        //   528: aload 11
        //   530: ldc_w 350
        //   533: aload 12
        //   535: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   538: invokevirtual 341	com/tencent/common/http/MttRequestBase:addHeader	(Ljava/lang/String;Ljava/lang/String;)V
        //   541: goto +60 -> 601
        //   544: aload_0
        //   545: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   548: ldc_w 350
        //   551: aload_0
        //   552: getfield 40	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mRangeString	Ljava/lang/String;
        //   555: invokevirtual 341	com/tencent/common/http/MttRequestBase:addHeader	(Ljava/lang/String;Ljava/lang/String;)V
        //   558: goto +43 -> 601
        //   561: aload_0
        //   562: getfield 40	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mRangeString	Ljava/lang/String;
        //   565: invokestatic 278	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
        //   568: ifeq +19 -> 587
        //   571: aload_0
        //   572: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   575: ldc_w 350
        //   578: ldc_w 352
        //   581: invokevirtual 341	com/tencent/common/http/MttRequestBase:addHeader	(Ljava/lang/String;Ljava/lang/String;)V
        //   584: goto +17 -> 601
        //   587: aload_0
        //   588: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   591: ldc_w 350
        //   594: aload_0
        //   595: getfield 40	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mRangeString	Ljava/lang/String;
        //   598: invokevirtual 341	com/tencent/common/http/MttRequestBase:addHeader	(Ljava/lang/String;Ljava/lang/String;)V
        //   601: aload_0
        //   602: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   605: getfield 355	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mIsPostTask	Z
        //   608: ifeq +25 -> 633
        //   611: aload_0
        //   612: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   615: iconst_1
        //   616: invokevirtual 359	com/tencent/common/http/MttRequestBase:setMethod	(B)V
        //   619: aload_0
        //   620: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   623: aload_0
        //   624: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   627: getfield 362	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mPostData	Ljava/lang/String;
        //   630: invokevirtual 365	com/tencent/common/http/MttRequestBase:setPostData	(Ljava/lang/String;)V
        //   633: aload_0
        //   634: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   637: getfield 218	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes	I
        //   640: iconst_1
        //   641: if_icmple +44 -> 685
        //   644: aload_0
        //   645: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   648: getfield 123	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRefer	Ljava/lang/String;
        //   651: invokestatic 278	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
        //   654: ifeq +17 -> 671
        //   657: aload_0
        //   658: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   661: aload_0
        //   662: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   665: getfield 129	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mOriginalUrl	Ljava/lang/String;
        //   668: putfield 123	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRefer	Ljava/lang/String;
        //   671: aload_0
        //   672: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   675: aload_0
        //   676: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   679: getfield 123	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRefer	Ljava/lang/String;
        //   682: invokevirtual 250	com/tencent/common/http/MttRequestBase:setReferer	(Ljava/lang/String;)V
        //   685: ldc 56
        //   687: ldc_w 367
        //   690: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   693: invokestatic 179	java/lang/System:currentTimeMillis	()J
        //   696: lstore 7
        //   698: aload_0
        //   699: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   702: ldc_w 369
        //   705: ldc 38
        //   707: invokevirtual 372	com/tencent/common/http/MttRequestBase:replaceHeader	(Ljava/lang/String;Ljava/lang/String;)V
        //   710: aload_0
        //   711: getfield 327	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mRequester	Lcom/tencent/common/http/Requester;
        //   714: aload_0
        //   715: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   718: invokevirtual 375	com/tencent/common/http/Requester:execute	(Lcom/tencent/common/http/MttRequestBase;)Lcom/tencent/common/http/MttResponse;
        //   721: astore 13
        //   723: invokestatic 179	java/lang/System:currentTimeMillis	()J
        //   726: lstore 9
        //   728: ldc 56
        //   730: ldc_w 377
        //   733: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   736: aload_0
        //   737: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   740: getfield 380	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mTimeCostConnect	Ljava/lang/String;
        //   743: invokestatic 278	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
        //   746: ifne +48 -> 794
        //   749: new 84	java/lang/StringBuilder
        //   752: dup
        //   753: invokespecial 85	java/lang/StringBuilder:<init>	()V
        //   756: astore 11
        //   758: aload_0
        //   759: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   762: astore 12
        //   764: aload 11
        //   766: aload 12
        //   768: getfield 380	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mTimeCostConnect	Ljava/lang/String;
        //   771: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   774: pop
        //   775: aload 11
        //   777: ldc_w 280
        //   780: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   783: pop
        //   784: aload 12
        //   786: aload 11
        //   788: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   791: putfield 380	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mTimeCostConnect	Ljava/lang/String;
        //   794: new 84	java/lang/StringBuilder
        //   797: dup
        //   798: invokespecial 85	java/lang/StringBuilder:<init>	()V
        //   801: astore 11
        //   803: aload_0
        //   804: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   807: astore 12
        //   809: aload 11
        //   811: aload 12
        //   813: getfield 380	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mTimeCostConnect	Ljava/lang/String;
        //   816: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   819: pop
        //   820: aload 11
        //   822: lload 9
        //   824: lload 7
        //   826: lsub
        //   827: invokevirtual 383	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
        //   830: pop
        //   831: aload 12
        //   833: aload 11
        //   835: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   838: putfield 380	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mTimeCostConnect	Ljava/lang/String;
        //   841: aload_0
        //   842: aload 13
        //   844: invokevirtual 387	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:setMttResponse	(Lcom/tencent/common/http/MttResponse;)V
        //   847: aload 13
        //   849: invokevirtual 393	com/tencent/common/http/MttResponse:getStatusCode	()Ljava/lang/Integer;
        //   852: invokevirtual 398	java/lang/Integer:intValue	()I
        //   855: istore_2
        //   856: aload_0
        //   857: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   860: getfield 399	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mCanceled	Z
        //   863: ifeq +26 -> 889
        //   866: aload_0
        //   867: invokespecial 401	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:taskCanceled	()V
        //   870: ldc 56
        //   872: ldc -35
        //   874: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   877: aload_0
        //   878: invokevirtual 224	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:closeQuietly	()V
        //   881: ldc 56
        //   883: ldc -30
        //   885: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   888: return
        //   889: iload_2
        //   890: sipush 200
        //   893: if_icmpeq +610 -> 1503
        //   896: iload_2
        //   897: sipush 206
        //   900: if_icmpne +6 -> 906
        //   903: goto +600 -> 1503
        //   906: iload_2
        //   907: sipush 300
        //   910: if_icmplt +185 -> 1095
        //   913: iload_2
        //   914: sipush 307
        //   917: if_icmpgt +178 -> 1095
        //   920: aload 13
        //   922: invokevirtual 404	com/tencent/common/http/MttResponse:getLocation	()Ljava/lang/String;
        //   925: astore 11
        //   927: aload 11
        //   929: invokestatic 278	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
        //   932: ifne +142 -> 1074
        //   935: aload_0
        //   936: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   939: invokevirtual 267	com/tencent/common/http/MttRequestBase:getUrl	()Ljava/lang/String;
        //   942: astore 12
        //   944: new 84	java/lang/StringBuilder
        //   947: dup
        //   948: invokespecial 85	java/lang/StringBuilder:<init>	()V
        //   951: astore 13
        //   953: aload 13
        //   955: ldc_w 406
        //   958: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   961: pop
        //   962: aload 13
        //   964: aload 12
        //   966: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   969: pop
        //   970: ldc 56
        //   972: aload 13
        //   974: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   977: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   980: aload_0
        //   981: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   984: aload 12
        //   986: aload 11
        //   988: invokestatic 411	com/tencent/tbs/common/utils/QBUrlUtils:resolveBase	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   991: invokestatic 417	com/tencent/common/utils/UrlUtils:prepareUrl	(Ljava/lang/String;)Ljava/lang/String;
        //   994: invokevirtual 247	com/tencent/common/http/MttRequestBase:setUrl	(Ljava/lang/String;)V
        //   997: new 84	java/lang/StringBuilder
        //   1000: dup
        //   1001: invokespecial 85	java/lang/StringBuilder:<init>	()V
        //   1004: astore 12
        //   1006: aload 12
        //   1008: ldc_w 419
        //   1011: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1014: pop
        //   1015: aload 12
        //   1017: aload 11
        //   1019: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1022: pop
        //   1023: ldc 56
        //   1025: aload 12
        //   1027: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   1030: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   1033: new 84	java/lang/StringBuilder
        //   1036: dup
        //   1037: invokespecial 85	java/lang/StringBuilder:<init>	()V
        //   1040: astore 11
        //   1042: aload 11
        //   1044: ldc_w 421
        //   1047: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1050: pop
        //   1051: aload 11
        //   1053: aload_0
        //   1054: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   1057: invokevirtual 267	com/tencent/common/http/MttRequestBase:getUrl	()Ljava/lang/String;
        //   1060: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1063: pop
        //   1064: ldc 56
        //   1066: aload 11
        //   1068: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   1071: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   1074: aload_0
        //   1075: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1078: astore 11
        //   1080: aload 11
        //   1082: aload 11
        //   1084: getfield 256	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes302	I
        //   1087: iconst_1
        //   1088: iadd
        //   1089: putfield 256	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes302	I
        //   1092: goto +2041 -> 3133
        //   1095: aload_0
        //   1096: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1099: getfield 129	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mOriginalUrl	Ljava/lang/String;
        //   1102: astore 11
        //   1104: aload_0
        //   1105: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   1108: invokevirtual 267	com/tencent/common/http/MttRequestBase:getUrl	()Ljava/lang/String;
        //   1111: astore 12
        //   1113: aload 11
        //   1115: invokestatic 278	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
        //   1118: ifne +2332 -> 3450
        //   1121: aload 11
        //   1123: aload 12
        //   1125: invokevirtual 425	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
        //   1128: ifne +2322 -> 3450
        //   1131: aload_0
        //   1132: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   1135: aload 11
        //   1137: invokevirtual 247	com/tencent/common/http/MttRequestBase:setUrl	(Ljava/lang/String;)V
        //   1140: aload_0
        //   1141: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1144: aload_0
        //   1145: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1148: getfield 129	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mOriginalUrl	Ljava/lang/String;
        //   1151: putfield 242	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mTargetUrl	Ljava/lang/String;
        //   1154: goto +2296 -> 3450
        //   1157: iload_2
        //   1158: sipush 202
        //   1161: if_icmpne +24 -> 1185
        //   1164: aload_0
        //   1165: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1168: astore 11
        //   1170: aload 11
        //   1172: aload 11
        //   1174: getfield 218	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes	I
        //   1177: iconst_1
        //   1178: iadd
        //   1179: putfield 218	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes	I
        //   1182: goto +1951 -> 3133
        //   1185: aload_0
        //   1186: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1189: getfield 218	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes	I
        //   1192: aload_0
        //   1193: getfield 34	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:MAX_RETRY_TIMES	I
        //   1196: if_icmpge +93 -> 1289
        //   1199: iload_2
        //   1200: sipush 503
        //   1203: if_icmpne +86 -> 1289
        //   1206: aload 13
        //   1208: invokevirtual 428	com/tencent/common/http/MttResponse:getRetryAfter	()J
        //   1211: lstore 9
        //   1213: lload 9
        //   1215: lstore 7
        //   1217: lload 9
        //   1219: lconst_0
        //   1220: lcmp
        //   1221: ifgt +8 -> 1229
        //   1224: ldc2_w 429
        //   1227: lstore 7
        //   1229: aload_0
        //   1230: lload 7
        //   1232: invokespecial 432	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:handleSleep	(J)V
        //   1235: aload_0
        //   1236: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1239: getfield 399	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mCanceled	Z
        //   1242: ifeq +26 -> 1268
        //   1245: aload_0
        //   1246: invokespecial 401	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:taskCanceled	()V
        //   1249: ldc 56
        //   1251: ldc -35
        //   1253: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   1256: aload_0
        //   1257: invokevirtual 224	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:closeQuietly	()V
        //   1260: ldc 56
        //   1262: ldc -30
        //   1264: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   1267: return
        //   1268: aload_0
        //   1269: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1272: astore 11
        //   1274: aload 11
        //   1276: aload 11
        //   1278: getfield 218	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes	I
        //   1281: iconst_1
        //   1282: iadd
        //   1283: putfield 218	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes	I
        //   1286: goto +1847 -> 3133
        //   1289: aload_0
        //   1290: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1293: getfield 218	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes	I
        //   1296: aload_0
        //   1297: getfield 34	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:MAX_RETRY_TIMES	I
        //   1300: if_icmpge +86 -> 1386
        //   1303: iload_2
        //   1304: sipush 408
        //   1307: if_icmpeq +17 -> 1324
        //   1310: iload_2
        //   1311: sipush 504
        //   1314: if_icmpeq +10 -> 1324
        //   1317: iload_2
        //   1318: sipush 502
        //   1321: if_icmpne +65 -> 1386
        //   1324: aload_0
        //   1325: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1328: getfield 399	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mCanceled	Z
        //   1331: ifeq +26 -> 1357
        //   1334: aload_0
        //   1335: invokespecial 401	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:taskCanceled	()V
        //   1338: ldc 56
        //   1340: ldc -35
        //   1342: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   1345: aload_0
        //   1346: invokevirtual 224	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:closeQuietly	()V
        //   1349: ldc 56
        //   1351: ldc -30
        //   1353: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   1356: return
        //   1357: ldc 56
        //   1359: ldc_w 434
        //   1362: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   1365: aload_0
        //   1366: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1369: astore 11
        //   1371: aload 11
        //   1373: aload 11
        //   1375: getfield 218	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes	I
        //   1378: iconst_1
        //   1379: iadd
        //   1380: putfield 218	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes	I
        //   1383: goto +1750 -> 3133
        //   1386: aload_0
        //   1387: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1390: iload_2
        //   1391: invokevirtual 198	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:reportDetectPerformance	(I)V
        //   1394: aload_0
        //   1395: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1398: getfield 111	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:this$0	Lcom/tencent/tbs/common/download/ApkPkgNameDetector;
        //   1401: aload_0
        //   1402: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1405: getfield 129	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mOriginalUrl	Ljava/lang/String;
        //   1408: invokevirtual 201	com/tencent/tbs/common/download/ApkPkgNameDetector:detectFailed	(Ljava/lang/String;)V
        //   1411: goto +798 -> 2209
        //   1414: aload_0
        //   1415: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   1418: ldc_w 350
        //   1421: invokevirtual 437	com/tencent/common/http/MttRequestBase:getHeader	(Ljava/lang/String;)Ljava/lang/String;
        //   1424: ldc_w 345
        //   1427: invokevirtual 440	java/lang/String:startsWith	(Ljava/lang/String;)Z
        //   1430: ifeq +29 -> 1459
        //   1433: aload_0
        //   1434: iconst_0
        //   1435: putfield 42	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mSurportEndRange	Z
        //   1438: aload_0
        //   1439: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1442: astore 11
        //   1444: aload 11
        //   1446: aload 11
        //   1448: getfield 262	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimesEndRange	I
        //   1451: iconst_1
        //   1452: iadd
        //   1453: putfield 262	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimesEndRange	I
        //   1456: goto +1677 -> 3133
        //   1459: aload_0
        //   1460: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1463: iload_2
        //   1464: invokevirtual 198	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:reportDetectPerformance	(I)V
        //   1467: aload_0
        //   1468: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1471: getfield 111	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:this$0	Lcom/tencent/tbs/common/download/ApkPkgNameDetector;
        //   1474: aload_0
        //   1475: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1478: getfield 129	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mOriginalUrl	Ljava/lang/String;
        //   1481: invokevirtual 201	com/tencent/tbs/common/download/ApkPkgNameDetector:detectFailed	(Ljava/lang/String;)V
        //   1484: ldc 56
        //   1486: ldc -35
        //   1488: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   1491: aload_0
        //   1492: invokevirtual 224	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:closeQuietly	()V
        //   1495: ldc 56
        //   1497: ldc -30
        //   1499: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   1502: return
        //   1503: new 84	java/lang/StringBuilder
        //   1506: dup
        //   1507: invokespecial 85	java/lang/StringBuilder:<init>	()V
        //   1510: astore 11
        //   1512: aload 11
        //   1514: aload_0
        //   1515: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   1518: pop
        //   1519: aload 11
        //   1521: ldc_w 442
        //   1524: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1527: pop
        //   1528: ldc 56
        //   1530: aload 11
        //   1532: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   1535: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   1538: ldc 56
        //   1540: ldc_w 444
        //   1543: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   1546: new 84	java/lang/StringBuilder
        //   1549: dup
        //   1550: invokespecial 85	java/lang/StringBuilder:<init>	()V
        //   1553: astore 11
        //   1555: aload 11
        //   1557: ldc_w 446
        //   1560: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1563: pop
        //   1564: aload 11
        //   1566: aload 13
        //   1568: invokevirtual 450	com/tencent/common/http/MttResponse:getContentType	()Lcom/tencent/common/http/ContentType;
        //   1571: getfield 455	com/tencent/common/http/ContentType:mType	Ljava/lang/String;
        //   1574: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1577: pop
        //   1578: aload 11
        //   1580: ldc_w 310
        //   1583: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1586: pop
        //   1587: aload 11
        //   1589: aload 13
        //   1591: invokevirtual 450	com/tencent/common/http/MttResponse:getContentType	()Lcom/tencent/common/http/ContentType;
        //   1594: getfield 458	com/tencent/common/http/ContentType:mTypeValue	Ljava/lang/String;
        //   1597: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1600: pop
        //   1601: ldc 56
        //   1603: aload 11
        //   1605: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   1608: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   1611: new 84	java/lang/StringBuilder
        //   1614: dup
        //   1615: invokespecial 85	java/lang/StringBuilder:<init>	()V
        //   1618: astore 11
        //   1620: aload 11
        //   1622: ldc_w 460
        //   1625: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1628: pop
        //   1629: aload 11
        //   1631: aload 13
        //   1633: invokevirtual 463	com/tencent/common/http/MttResponse:getContentDisposition	()Ljava/lang/String;
        //   1636: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1639: pop
        //   1640: ldc 56
        //   1642: aload 11
        //   1644: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   1647: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   1650: new 84	java/lang/StringBuilder
        //   1653: dup
        //   1654: invokespecial 85	java/lang/StringBuilder:<init>	()V
        //   1657: astore 11
        //   1659: aload 11
        //   1661: ldc_w 465
        //   1664: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1667: pop
        //   1668: aload 11
        //   1670: aload 13
        //   1672: invokevirtual 468	com/tencent/common/http/MttResponse:getContentLength	()J
        //   1675: invokevirtual 383	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
        //   1678: pop
        //   1679: ldc 56
        //   1681: aload 11
        //   1683: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   1686: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   1689: new 84	java/lang/StringBuilder
        //   1692: dup
        //   1693: invokespecial 85	java/lang/StringBuilder:<init>	()V
        //   1696: astore 11
        //   1698: aload 11
        //   1700: ldc_w 470
        //   1703: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1706: pop
        //   1707: aload 11
        //   1709: aload 13
        //   1711: invokevirtual 473	com/tencent/common/http/MttResponse:getContentEncoding	()Ljava/lang/String;
        //   1714: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1717: pop
        //   1718: ldc 56
        //   1720: aload 11
        //   1722: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   1725: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   1728: new 84	java/lang/StringBuilder
        //   1731: dup
        //   1732: invokespecial 85	java/lang/StringBuilder:<init>	()V
        //   1735: astore 11
        //   1737: aload 11
        //   1739: ldc_w 475
        //   1742: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1745: pop
        //   1746: aload 11
        //   1748: aload 13
        //   1750: invokevirtual 478	com/tencent/common/http/MttResponse:getContentRange	()Ljava/lang/String;
        //   1753: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1756: pop
        //   1757: ldc 56
        //   1759: aload 11
        //   1761: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   1764: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   1767: aload 13
        //   1769: invokevirtual 468	com/tencent/common/http/MttResponse:getContentLength	()J
        //   1772: lstore 7
        //   1774: aload 13
        //   1776: invokevirtual 478	com/tencent/common/http/MttResponse:getContentRange	()Ljava/lang/String;
        //   1779: astore 11
        //   1781: aload 11
        //   1783: invokestatic 278	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
        //   1786: ifne +183 -> 1969
        //   1789: lload 7
        //   1791: aload_0
        //   1792: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1795: getfield 111	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:this$0	Lcom/tencent/tbs/common/download/ApkPkgNameDetector;
        //   1798: getfield 114	com/tencent/tbs/common/download/ApkPkgNameDetector:mSegmentSize	I
        //   1801: iconst_1
        //   1802: iadd
        //   1803: i2l
        //   1804: lcmp
        //   1805: ifeq +14 -> 1819
        //   1808: aload 11
        //   1810: ldc_w 480
        //   1813: invokevirtual 285	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
        //   1816: ifeq +153 -> 1969
        //   1819: aload 11
        //   1821: ldc_w 310
        //   1824: invokevirtual 484	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
        //   1827: astore 11
        //   1829: aload 11
        //   1831: ifnull +138 -> 1969
        //   1834: aload 11
        //   1836: arraylength
        //   1837: istore_1
        //   1838: iload_1
        //   1839: iconst_2
        //   1840: if_icmpne +129 -> 1969
        //   1843: aload 11
        //   1845: iconst_1
        //   1846: aaload
        //   1847: invokestatic 490	java/lang/Long:parseLong	(Ljava/lang/String;)J
        //   1850: lstore 7
        //   1852: lload 7
        //   1854: aload_0
        //   1855: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1858: getfield 111	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:this$0	Lcom/tencent/tbs/common/download/ApkPkgNameDetector;
        //   1861: getfield 114	com/tencent/tbs/common/download/ApkPkgNameDetector:mSegmentSize	I
        //   1864: i2l
        //   1865: lcmp
        //   1866: ifle +103 -> 1969
        //   1869: new 84	java/lang/StringBuilder
        //   1872: dup
        //   1873: invokespecial 85	java/lang/StringBuilder:<init>	()V
        //   1876: astore 11
        //   1878: aload 11
        //   1880: ldc_w 492
        //   1883: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1886: pop
        //   1887: aload 11
        //   1889: lload 7
        //   1891: aload_0
        //   1892: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1895: getfield 111	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:this$0	Lcom/tencent/tbs/common/download/ApkPkgNameDetector;
        //   1898: getfield 114	com/tencent/tbs/common/download/ApkPkgNameDetector:mSegmentSize	I
        //   1901: i2l
        //   1902: lsub
        //   1903: invokevirtual 383	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
        //   1906: pop
        //   1907: aload 11
        //   1909: ldc_w 494
        //   1912: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1915: pop
        //   1916: aload 11
        //   1918: lload 7
        //   1920: lconst_1
        //   1921: lsub
        //   1922: invokevirtual 383	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
        //   1925: pop
        //   1926: aload_0
        //   1927: aload 11
        //   1929: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   1932: putfield 40	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mRangeString	Ljava/lang/String;
        //   1935: aload_0
        //   1936: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   1939: astore 11
        //   1941: aload 11
        //   1943: aload 11
        //   1945: getfield 259	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimesRange	I
        //   1948: iconst_1
        //   1949: iadd
        //   1950: putfield 259	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimesRange	I
        //   1953: aload_0
        //   1954: invokevirtual 224	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:closeQuietly	()V
        //   1957: goto +1176 -> 3133
        //   1960: astore 11
        //   1962: ldc 56
        //   1964: aload 11
        //   1966: invokestatic 498	com/tencent/common/utils/LogUtils:e	(Ljava/lang/String;Ljava/lang/Throwable;)V
        //   1969: invokestatic 179	java/lang/System:currentTimeMillis	()J
        //   1972: lstore 7
        //   1974: aconst_null
        //   1975: astore 12
        //   1977: aconst_null
        //   1978: astore 11
        //   1980: aload 13
        //   1982: invokevirtual 502	com/tencent/common/http/MttResponse:getInputStream	()Lcom/tencent/common/http/MttInputStream;
        //   1985: astore 13
        //   1987: aload 13
        //   1989: ifnull +110 -> 2099
        //   1992: iconst_0
        //   1993: istore_1
        //   1994: aload 13
        //   1996: astore 11
        //   1998: aload 13
        //   2000: astore 12
        //   2002: aload_0
        //   2003: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2006: getfield 505	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mBufferSize	I
        //   2009: iload_1
        //   2010: isub
        //   2011: ifle +64 -> 2075
        //   2014: aload 13
        //   2016: astore 11
        //   2018: aload 13
        //   2020: astore 12
        //   2022: aload_0
        //   2023: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2026: getfield 399	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mCanceled	Z
        //   2029: ifeq +6 -> 2035
        //   2032: goto +43 -> 2075
        //   2035: aload 13
        //   2037: astore 11
        //   2039: aload 13
        //   2041: astore 12
        //   2043: aload 13
        //   2045: aload_0
        //   2046: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2049: getfield 68	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mBuffer	[B
        //   2052: iload_1
        //   2053: aload_0
        //   2054: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2057: getfield 505	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mBufferSize	I
        //   2060: iload_1
        //   2061: isub
        //   2062: invokevirtual 511	com/tencent/common/http/MttInputStream:read	([BII)I
        //   2065: istore 5
        //   2067: iload 5
        //   2069: ifge +1405 -> 3474
        //   2072: goto +3 -> 2075
        //   2075: aload 13
        //   2077: astore 11
        //   2079: aload 13
        //   2081: astore 12
        //   2083: aload_0
        //   2084: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2087: invokestatic 179	java/lang/System:currentTimeMillis	()J
        //   2090: lload 7
        //   2092: lsub
        //   2093: putfield 514	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mTimeCostRead	J
        //   2096: goto +5 -> 2101
        //   2099: iconst_0
        //   2100: istore_1
        //   2101: aload 13
        //   2103: ifnull +8 -> 2111
        //   2106: aload 13
        //   2108: invokevirtual 517	com/tencent/common/http/MttInputStream:close	()V
        //   2111: aload_0
        //   2112: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2115: getfield 505	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mBufferSize	I
        //   2118: iload_1
        //   2119: if_icmpne +26 -> 2145
        //   2122: aload_0
        //   2123: invokespecial 519	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:downloadFinished	()V
        //   2126: ldc 56
        //   2128: ldc -35
        //   2130: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   2133: aload_0
        //   2134: invokevirtual 224	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:closeQuietly	()V
        //   2137: ldc 56
        //   2139: ldc -30
        //   2141: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   2144: return
        //   2145: aload_0
        //   2146: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2149: getfield 505	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mBufferSize	I
        //   2152: iload_1
        //   2153: if_icmpeq +56 -> 2209
        //   2156: aload_0
        //   2157: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2160: getfield 399	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mCanceled	Z
        //   2163: ifeq +16 -> 2179
        //   2166: aload_0
        //   2167: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2170: sipush 58536
        //   2173: invokevirtual 198	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:reportDetectPerformance	(I)V
        //   2176: goto +13 -> 2189
        //   2179: aload_0
        //   2180: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2183: sipush 61536
        //   2186: invokevirtual 198	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:reportDetectPerformance	(I)V
        //   2189: aload_0
        //   2190: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2193: getfield 111	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:this$0	Lcom/tencent/tbs/common/download/ApkPkgNameDetector;
        //   2196: aload_0
        //   2197: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2200: getfield 129	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mOriginalUrl	Ljava/lang/String;
        //   2203: invokevirtual 201	com/tencent/tbs/common/download/ApkPkgNameDetector:detectFailed	(Ljava/lang/String;)V
        //   2206: goto -80 -> 2126
        //   2209: ldc 56
        //   2211: ldc -35
        //   2213: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   2216: aload_0
        //   2217: invokevirtual 224	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:closeQuietly	()V
        //   2220: ldc 56
        //   2222: ldc -30
        //   2224: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   2227: return
        //   2228: astore 12
        //   2230: goto +654 -> 2884
        //   2233: astore 13
        //   2235: aload 12
        //   2237: astore 11
        //   2239: aload 13
        //   2241: invokevirtual 520	java/io/IOException:printStackTrace	()V
        //   2244: aload 12
        //   2246: astore 11
        //   2248: aload_0
        //   2249: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2252: getfield 399	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mCanceled	Z
        //   2255: ifeq +40 -> 2295
        //   2258: aload 12
        //   2260: astore 11
        //   2262: aload_0
        //   2263: invokespecial 401	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:taskCanceled	()V
        //   2266: aload 12
        //   2268: ifnull +8 -> 2276
        //   2271: aload 12
        //   2273: invokevirtual 517	com/tencent/common/http/MttInputStream:close	()V
        //   2276: ldc 56
        //   2278: ldc -35
        //   2280: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   2283: aload_0
        //   2284: invokevirtual 224	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:closeQuietly	()V
        //   2287: ldc 56
        //   2289: ldc -30
        //   2291: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   2294: return
        //   2295: aload 12
        //   2297: astore 11
        //   2299: aload 13
        //   2301: instanceof 522
        //   2304: ifne +15 -> 2319
        //   2307: aload 12
        //   2309: astore 11
        //   2311: aload 13
        //   2313: instanceof 524
        //   2316: ifeq +70 -> 2386
        //   2319: aload 12
        //   2321: astore 11
        //   2323: invokestatic 530	com/tencent/common/http/Apn:isNetworkConnected	()Z
        //   2326: ifeq +60 -> 2386
        //   2329: aload 12
        //   2331: astore 11
        //   2333: aload_0
        //   2334: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2337: getfield 218	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes	I
        //   2340: aload_0
        //   2341: getfield 34	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:MAX_RETRY_TIMES	I
        //   2344: if_icmpge +42 -> 2386
        //   2347: aload 12
        //   2349: astore 11
        //   2351: aload_0
        //   2352: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2355: astore 13
        //   2357: aload 12
        //   2359: astore 11
        //   2361: aload 13
        //   2363: aload 13
        //   2365: getfield 218	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes	I
        //   2368: iconst_1
        //   2369: iadd
        //   2370: putfield 218	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes	I
        //   2373: aload 12
        //   2375: ifnull +758 -> 3133
        //   2378: aload 12
        //   2380: invokevirtual 517	com/tencent/common/http/MttInputStream:close	()V
        //   2383: goto +750 -> 3133
        //   2386: aload 12
        //   2388: astore 11
        //   2390: aload 13
        //   2392: instanceof 234
        //   2395: ifeq +131 -> 2526
        //   2398: aload 12
        //   2400: astore 11
        //   2402: invokestatic 530	com/tencent/common/http/Apn:isNetworkConnected	()Z
        //   2405: ifeq +121 -> 2526
        //   2408: aload 12
        //   2410: astore 11
        //   2412: aload_0
        //   2413: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2416: getfield 533	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mErrorDes	Ljava/lang/String;
        //   2419: ldc_w 535
        //   2422: invokevirtual 285	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
        //   2425: ifeq +101 -> 2526
        //   2428: aload 12
        //   2430: astore 11
        //   2432: aload_0
        //   2433: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2436: ldc 38
        //   2438: putfield 533	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mErrorDes	Ljava/lang/String;
        //   2441: aload 12
        //   2443: astore 11
        //   2445: aload_0
        //   2446: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2449: getfield 399	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mCanceled	Z
        //   2452: ifeq +40 -> 2492
        //   2455: aload 12
        //   2457: astore 11
        //   2459: aload_0
        //   2460: invokespecial 401	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:taskCanceled	()V
        //   2463: aload 12
        //   2465: ifnull +8 -> 2473
        //   2468: aload 12
        //   2470: invokevirtual 517	com/tencent/common/http/MttInputStream:close	()V
        //   2473: ldc 56
        //   2475: ldc -35
        //   2477: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   2480: aload_0
        //   2481: invokevirtual 224	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:closeQuietly	()V
        //   2484: ldc 56
        //   2486: ldc -30
        //   2488: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   2491: return
        //   2492: aload 12
        //   2494: astore 11
        //   2496: aload_0
        //   2497: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2500: astore 13
        //   2502: aload 12
        //   2504: astore 11
        //   2506: aload 13
        //   2508: aload 13
        //   2510: getfield 218	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes	I
        //   2513: iconst_1
        //   2514: iadd
        //   2515: putfield 218	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes	I
        //   2518: aload 12
        //   2520: ifnull +613 -> 3133
        //   2523: goto -145 -> 2378
        //   2526: aload 12
        //   2528: astore 11
        //   2530: invokestatic 530	com/tencent/common/http/Apn:isNetworkConnected	()Z
        //   2533: ifne +103 -> 2636
        //   2536: aload 12
        //   2538: astore 11
        //   2540: aload_0
        //   2541: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2544: getfield 253	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimesNoNet	I
        //   2547: iconst_2
        //   2548: if_icmpge +88 -> 2636
        //   2551: aload 12
        //   2553: astore 11
        //   2555: aload_0
        //   2556: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2559: astore 13
        //   2561: aload 12
        //   2563: astore 11
        //   2565: aload 13
        //   2567: aload 13
        //   2569: getfield 253	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimesNoNet	I
        //   2572: iconst_1
        //   2573: iadd
        //   2574: putfield 253	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimesNoNet	I
        //   2577: aload 12
        //   2579: astore 11
        //   2581: aload_0
        //   2582: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2585: getfield 399	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mCanceled	Z
        //   2588: ifeq +40 -> 2628
        //   2591: aload 12
        //   2593: astore 11
        //   2595: aload_0
        //   2596: invokespecial 401	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:taskCanceled	()V
        //   2599: aload 12
        //   2601: ifnull +8 -> 2609
        //   2604: aload 12
        //   2606: invokevirtual 517	com/tencent/common/http/MttInputStream:close	()V
        //   2609: ldc 56
        //   2611: ldc -35
        //   2613: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   2616: aload_0
        //   2617: invokevirtual 224	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:closeQuietly	()V
        //   2620: ldc 56
        //   2622: ldc -30
        //   2624: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   2627: return
        //   2628: aload 12
        //   2630: ifnull +503 -> 3133
        //   2633: goto -255 -> 2378
        //   2636: aload 12
        //   2638: astore 11
        //   2640: aload_0
        //   2641: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2644: aload 13
        //   2646: invokevirtual 536	java/io/IOException:toString	()Ljava/lang/String;
        //   2649: putfield 533	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mErrorDes	Ljava/lang/String;
        //   2652: aload 12
        //   2654: astore 11
        //   2656: aload 13
        //   2658: invokevirtual 540	java/io/IOException:getStackTrace	()[Ljava/lang/StackTraceElement;
        //   2661: astore 13
        //   2663: aload 12
        //   2665: astore 11
        //   2667: aload 13
        //   2669: arraylength
        //   2670: istore 5
        //   2672: iconst_0
        //   2673: istore_1
        //   2674: iload_1
        //   2675: iload 5
        //   2677: if_icmpge +83 -> 2760
        //   2680: aload 13
        //   2682: iload_1
        //   2683: aaload
        //   2684: astore 14
        //   2686: aload 12
        //   2688: astore 11
        //   2690: new 84	java/lang/StringBuilder
        //   2693: dup
        //   2694: invokespecial 85	java/lang/StringBuilder:<init>	()V
        //   2697: astore 15
        //   2699: aload 12
        //   2701: astore 11
        //   2703: aload_0
        //   2704: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2707: astore 16
        //   2709: aload 12
        //   2711: astore 11
        //   2713: aload 15
        //   2715: aload 16
        //   2717: getfield 533	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mErrorDes	Ljava/lang/String;
        //   2720: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   2723: pop
        //   2724: aload 12
        //   2726: astore 11
        //   2728: aload 15
        //   2730: aload 14
        //   2732: invokevirtual 543	java/lang/StackTraceElement:toString	()Ljava/lang/String;
        //   2735: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   2738: pop
        //   2739: aload 12
        //   2741: astore 11
        //   2743: aload 16
        //   2745: aload 15
        //   2747: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   2750: putfield 533	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mErrorDes	Ljava/lang/String;
        //   2753: iload_1
        //   2754: iconst_1
        //   2755: iadd
        //   2756: istore_1
        //   2757: goto -83 -> 2674
        //   2760: aload 12
        //   2762: astore 11
        //   2764: aload_0
        //   2765: iconst_5
        //   2766: putfield 547	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mStatus	B
        //   2769: aload 12
        //   2771: astore 11
        //   2773: new 84	java/lang/StringBuilder
        //   2776: dup
        //   2777: invokespecial 85	java/lang/StringBuilder:<init>	()V
        //   2780: astore 13
        //   2782: aload 12
        //   2784: astore 11
        //   2786: aload 13
        //   2788: ldc_w 549
        //   2791: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   2794: pop
        //   2795: aload 12
        //   2797: astore 11
        //   2799: aload 13
        //   2801: iload_2
        //   2802: invokevirtual 348	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   2805: pop
        //   2806: aload 12
        //   2808: astore 11
        //   2810: ldc 56
        //   2812: aload 13
        //   2814: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   2817: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   2820: aload 12
        //   2822: astore 11
        //   2824: aload_0
        //   2825: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2828: sipush 62535
        //   2831: invokevirtual 198	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:reportDetectPerformance	(I)V
        //   2834: aload 12
        //   2836: astore 11
        //   2838: aload_0
        //   2839: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2842: getfield 111	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:this$0	Lcom/tencent/tbs/common/download/ApkPkgNameDetector;
        //   2845: aload_0
        //   2846: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2849: getfield 129	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mOriginalUrl	Ljava/lang/String;
        //   2852: invokevirtual 201	com/tencent/tbs/common/download/ApkPkgNameDetector:detectFailed	(Ljava/lang/String;)V
        //   2855: aload 12
        //   2857: ifnull +8 -> 2865
        //   2860: aload 12
        //   2862: invokevirtual 517	com/tencent/common/http/MttInputStream:close	()V
        //   2865: ldc 56
        //   2867: ldc -35
        //   2869: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   2872: aload_0
        //   2873: invokevirtual 224	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:closeQuietly	()V
        //   2876: ldc 56
        //   2878: ldc -30
        //   2880: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   2883: return
        //   2884: aload 11
        //   2886: ifnull +8 -> 2894
        //   2889: aload 11
        //   2891: invokevirtual 517	com/tencent/common/http/MttInputStream:close	()V
        //   2894: aload 12
        //   2896: athrow
        //   2897: astore 11
        //   2899: goto +530 -> 3429
        //   2902: astore 11
        //   2904: aload_0
        //   2905: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2908: getfield 399	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mCanceled	Z
        //   2911: ifeq +26 -> 2937
        //   2914: aload_0
        //   2915: invokespecial 401	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:taskCanceled	()V
        //   2918: ldc 56
        //   2920: ldc -35
        //   2922: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   2925: aload_0
        //   2926: invokevirtual 224	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:closeQuietly	()V
        //   2929: ldc 56
        //   2931: ldc -30
        //   2933: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   2936: return
        //   2937: aload 11
        //   2939: invokevirtual 313	java/lang/Exception:printStackTrace	()V
        //   2942: new 84	java/lang/StringBuilder
        //   2945: dup
        //   2946: invokespecial 85	java/lang/StringBuilder:<init>	()V
        //   2949: astore 12
        //   2951: aload 12
        //   2953: ldc_w 551
        //   2956: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   2959: pop
        //   2960: aload 12
        //   2962: aload_0
        //   2963: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   2966: getfield 218	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes	I
        //   2969: invokevirtual 348	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   2972: pop
        //   2973: aload 12
        //   2975: ldc_w 553
        //   2978: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   2981: pop
        //   2982: aload 12
        //   2984: aload 11
        //   2986: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   2989: pop
        //   2990: ldc 56
        //   2992: aload 12
        //   2994: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   2997: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   3000: aload_0
        //   3001: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   3004: getfield 218	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes	I
        //   3007: aload_0
        //   3008: getfield 34	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:MAX_RETRY_TIMES	I
        //   3011: if_icmpge +143 -> 3154
        //   3014: aload_0
        //   3015: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   3018: getfield 399	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mCanceled	Z
        //   3021: ifeq +26 -> 3047
        //   3024: aload_0
        //   3025: invokespecial 401	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:taskCanceled	()V
        //   3028: ldc 56
        //   3030: ldc -35
        //   3032: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   3035: aload_0
        //   3036: invokevirtual 224	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:closeQuietly	()V
        //   3039: ldc 56
        //   3041: ldc -30
        //   3043: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   3046: return
        //   3047: invokestatic 530	com/tencent/common/http/Apn:isNetworkConnected	()Z
        //   3050: ifeq +65 -> 3115
        //   3053: aload_0
        //   3054: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   3057: getfield 129	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mOriginalUrl	Ljava/lang/String;
        //   3060: astore 11
        //   3062: aload_0
        //   3063: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   3066: invokevirtual 267	com/tencent/common/http/MttRequestBase:getUrl	()Ljava/lang/String;
        //   3069: astore 12
        //   3071: aload 11
        //   3073: invokestatic 278	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
        //   3076: ifne +39 -> 3115
        //   3079: aload 11
        //   3081: aload 12
        //   3083: invokevirtual 425	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
        //   3086: ifne +29 -> 3115
        //   3089: aload_0
        //   3090: getfield 52	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
        //   3093: aload 11
        //   3095: invokevirtual 247	com/tencent/common/http/MttRequestBase:setUrl	(Ljava/lang/String;)V
        //   3098: aload_0
        //   3099: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   3102: aload_0
        //   3103: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   3106: getfield 129	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mOriginalUrl	Ljava/lang/String;
        //   3109: putfield 242	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mTargetUrl	Ljava/lang/String;
        //   3112: goto +21 -> 3133
        //   3115: aload_0
        //   3116: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   3119: astore 11
        //   3121: aload 11
        //   3123: aload 11
        //   3125: getfield 218	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes	I
        //   3128: iconst_1
        //   3129: iadd
        //   3130: putfield 218	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mRetryTimes	I
        //   3133: ldc 56
        //   3135: ldc -35
        //   3137: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   3140: aload_0
        //   3141: invokevirtual 224	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:closeQuietly	()V
        //   3144: ldc 56
        //   3146: ldc -30
        //   3148: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   3151: goto -3082 -> 69
        //   3154: aload_0
        //   3155: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   3158: aload 11
        //   3160: invokevirtual 554	java/lang/Exception:toString	()Ljava/lang/String;
        //   3163: putfield 533	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mErrorDes	Ljava/lang/String;
        //   3166: aload 11
        //   3168: invokevirtual 555	java/lang/Exception:getStackTrace	()[Ljava/lang/StackTraceElement;
        //   3171: astore 11
        //   3173: aload 11
        //   3175: arraylength
        //   3176: istore_2
        //   3177: iload_3
        //   3178: istore_1
        //   3179: iload_1
        //   3180: iload_2
        //   3181: if_icmpge +63 -> 3244
        //   3184: aload 11
        //   3186: iload_1
        //   3187: aaload
        //   3188: astore 12
        //   3190: new 84	java/lang/StringBuilder
        //   3193: dup
        //   3194: invokespecial 85	java/lang/StringBuilder:<init>	()V
        //   3197: astore 13
        //   3199: aload_0
        //   3200: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   3203: astore 14
        //   3205: aload 13
        //   3207: aload 14
        //   3209: getfield 533	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mErrorDes	Ljava/lang/String;
        //   3212: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   3215: pop
        //   3216: aload 13
        //   3218: aload 12
        //   3220: invokevirtual 543	java/lang/StackTraceElement:toString	()Ljava/lang/String;
        //   3223: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   3226: pop
        //   3227: aload 14
        //   3229: aload 13
        //   3231: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   3234: putfield 533	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mErrorDes	Ljava/lang/String;
        //   3237: iload_1
        //   3238: iconst_1
        //   3239: iadd
        //   3240: istore_1
        //   3241: goto -62 -> 3179
        //   3244: aload_0
        //   3245: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   3248: sipush 62536
        //   3251: invokevirtual 198	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:reportDetectPerformance	(I)V
        //   3254: aload_0
        //   3255: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   3258: getfield 111	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:this$0	Lcom/tencent/tbs/common/download/ApkPkgNameDetector;
        //   3261: aload_0
        //   3262: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   3265: getfield 129	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mOriginalUrl	Ljava/lang/String;
        //   3268: invokevirtual 201	com/tencent/tbs/common/download/ApkPkgNameDetector:detectFailed	(Ljava/lang/String;)V
        //   3271: ldc 56
        //   3273: ldc -35
        //   3275: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   3278: aload_0
        //   3279: invokevirtual 224	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:closeQuietly	()V
        //   3282: ldc 56
        //   3284: ldc -30
        //   3286: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   3289: return
        //   3290: astore 11
        //   3292: aload_0
        //   3293: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   3296: aload 11
        //   3298: invokevirtual 556	java/lang/Error:toString	()Ljava/lang/String;
        //   3301: putfield 533	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mErrorDes	Ljava/lang/String;
        //   3304: aload 11
        //   3306: invokevirtual 557	java/lang/Error:getStackTrace	()[Ljava/lang/StackTraceElement;
        //   3309: astore 11
        //   3311: aload 11
        //   3313: arraylength
        //   3314: istore_2
        //   3315: iload 4
        //   3317: istore_1
        //   3318: iload_1
        //   3319: iload_2
        //   3320: if_icmpge +63 -> 3383
        //   3323: aload 11
        //   3325: iload_1
        //   3326: aaload
        //   3327: astore 12
        //   3329: new 84	java/lang/StringBuilder
        //   3332: dup
        //   3333: invokespecial 85	java/lang/StringBuilder:<init>	()V
        //   3336: astore 13
        //   3338: aload_0
        //   3339: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   3342: astore 14
        //   3344: aload 13
        //   3346: aload 14
        //   3348: getfield 533	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mErrorDes	Ljava/lang/String;
        //   3351: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   3354: pop
        //   3355: aload 13
        //   3357: aload 12
        //   3359: invokevirtual 543	java/lang/StackTraceElement:toString	()Ljava/lang/String;
        //   3362: invokevirtual 91	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   3365: pop
        //   3366: aload 14
        //   3368: aload 13
        //   3370: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   3373: putfield 533	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mErrorDes	Ljava/lang/String;
        //   3376: iload_1
        //   3377: iconst_1
        //   3378: iadd
        //   3379: istore_1
        //   3380: goto -62 -> 3318
        //   3383: aload_0
        //   3384: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   3387: sipush 63536
        //   3390: invokevirtual 198	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:reportDetectPerformance	(I)V
        //   3393: aload_0
        //   3394: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   3397: getfield 111	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:this$0	Lcom/tencent/tbs/common/download/ApkPkgNameDetector;
        //   3400: aload_0
        //   3401: getfield 29	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:this$1	Lcom/tencent/tbs/common/download/ApkPkgNameDetector$Detector;
        //   3404: getfield 129	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector:mOriginalUrl	Ljava/lang/String;
        //   3407: invokevirtual 201	com/tencent/tbs/common/download/ApkPkgNameDetector:detectFailed	(Ljava/lang/String;)V
        //   3410: ldc 56
        //   3412: ldc -35
        //   3414: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   3417: aload_0
        //   3418: invokevirtual 224	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:closeQuietly	()V
        //   3421: ldc 56
        //   3423: ldc -30
        //   3425: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   3428: return
        //   3429: ldc 56
        //   3431: ldc -35
        //   3433: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   3436: aload_0
        //   3437: invokevirtual 224	com/tencent/tbs/common/download/ApkPkgNameDetector$Detector$DetectTask:closeQuietly	()V
        //   3440: ldc 56
        //   3442: ldc -30
        //   3444: invokestatic 64	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
        //   3447: aload 11
        //   3449: athrow
        //   3450: iload_2
        //   3451: sipush 416
        //   3454: if_icmpeq -2040 -> 1414
        //   3457: iload_2
        //   3458: sipush 403
        //   3461: if_icmpeq -2047 -> 1414
        //   3464: iload_2
        //   3465: sipush 406
        //   3468: if_icmpne -2311 -> 1157
        //   3471: goto -2057 -> 1414
        //   3474: iload_1
        //   3475: iload 5
        //   3477: iadd
        //   3478: istore_1
        //   3479: goto -1485 -> 1994
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	3482	0	this	DetectTask
        //   283	3196	1	i	int
        //   299	3170	2	j	int
        //   11	3167	3	k	int
        //   8	3308	4	m	int
        //   2065	1413	5	n	int
        //   275	3	6	bool	boolean
        //   696	1395	7	l1	long
        //   726	492	9	l2	long
        //   49	314	11	localObject1	Object
        //   372	1	11	localException1	Exception
        //   377	5	11	localException2	Exception
        //   445	1499	11	localObject2	Object
        //   1960	5	11	localException3	Exception
        //   1978	912	11	localObject3	Object
        //   2897	1	11	localObject4	Object
        //   2902	83	11	localException4	Exception
        //   3060	125	11	localObject5	Object
        //   3290	15	11	localError	Error
        //   3309	139	11	arrayOfStackTraceElement	StackTraceElement[]
        //   501	1581	12	localObject6	Object
        //   2228	667	12	localObject7	Object
        //   2949	409	12	localObject8	Object
        //   721	1386	13	localObject9	Object
        //   2233	79	13	localIOException	java.io.IOException
        //   2355	1014	13	localObject10	Object
        //   2684	683	14	localDetector1	ApkPkgNameDetector.Detector
        //   2697	49	15	localStringBuilder	StringBuilder
        //   2707	37	16	localDetector2	ApkPkgNameDetector.Detector
        // Exception table:
        //   from	to	target	type
        //   289	298	372	java/lang/Exception
        //   300	308	372	java/lang/Exception
        //   310	367	372	java/lang/Exception
        //   229	277	377	java/lang/Exception
        //   1843	1957	1960	java/lang/Exception
        //   1980	1987	2228	finally
        //   2002	2014	2228	finally
        //   2022	2032	2228	finally
        //   2043	2067	2228	finally
        //   2083	2096	2228	finally
        //   2239	2244	2228	finally
        //   2248	2258	2228	finally
        //   2262	2266	2228	finally
        //   2299	2307	2228	finally
        //   2311	2319	2228	finally
        //   2323	2329	2228	finally
        //   2333	2347	2228	finally
        //   2351	2357	2228	finally
        //   2361	2373	2228	finally
        //   2390	2398	2228	finally
        //   2402	2408	2228	finally
        //   2412	2428	2228	finally
        //   2432	2441	2228	finally
        //   2445	2455	2228	finally
        //   2459	2463	2228	finally
        //   2496	2502	2228	finally
        //   2506	2518	2228	finally
        //   2530	2536	2228	finally
        //   2540	2551	2228	finally
        //   2555	2561	2228	finally
        //   2565	2577	2228	finally
        //   2581	2591	2228	finally
        //   2595	2599	2228	finally
        //   2640	2652	2228	finally
        //   2656	2663	2228	finally
        //   2667	2672	2228	finally
        //   2690	2699	2228	finally
        //   2703	2709	2228	finally
        //   2713	2724	2228	finally
        //   2728	2739	2228	finally
        //   2743	2753	2228	finally
        //   2764	2769	2228	finally
        //   2773	2782	2228	finally
        //   2786	2795	2228	finally
        //   2799	2806	2228	finally
        //   2810	2820	2228	finally
        //   2824	2834	2228	finally
        //   2838	2855	2228	finally
        //   1980	1987	2233	java/io/IOException
        //   2002	2014	2233	java/io/IOException
        //   2022	2032	2233	java/io/IOException
        //   2043	2067	2233	java/io/IOException
        //   2083	2096	2233	java/io/IOException
        //   417	471	2897	finally
        //   471	541	2897	finally
        //   544	558	2897	finally
        //   561	584	2897	finally
        //   587	601	2897	finally
        //   601	633	2897	finally
        //   633	671	2897	finally
        //   671	685	2897	finally
        //   685	794	2897	finally
        //   794	870	2897	finally
        //   920	1074	2897	finally
        //   1074	1092	2897	finally
        //   1095	1154	2897	finally
        //   1164	1182	2897	finally
        //   1185	1199	2897	finally
        //   1206	1213	2897	finally
        //   1229	1249	2897	finally
        //   1268	1286	2897	finally
        //   1289	1303	2897	finally
        //   1324	1338	2897	finally
        //   1357	1383	2897	finally
        //   1386	1411	2897	finally
        //   1414	1456	2897	finally
        //   1459	1484	2897	finally
        //   1503	1819	2897	finally
        //   1819	1829	2897	finally
        //   1834	1838	2897	finally
        //   1843	1957	2897	finally
        //   1962	1969	2897	finally
        //   1969	1974	2897	finally
        //   2106	2111	2897	finally
        //   2111	2126	2897	finally
        //   2145	2176	2897	finally
        //   2179	2189	2897	finally
        //   2189	2206	2897	finally
        //   2271	2276	2897	finally
        //   2378	2383	2897	finally
        //   2468	2473	2897	finally
        //   2604	2609	2897	finally
        //   2860	2865	2897	finally
        //   2889	2894	2897	finally
        //   2894	2897	2897	finally
        //   2904	2918	2897	finally
        //   2937	3028	2897	finally
        //   3047	3112	2897	finally
        //   3115	3133	2897	finally
        //   3154	3177	2897	finally
        //   3190	3237	2897	finally
        //   3244	3271	2897	finally
        //   3292	3315	2897	finally
        //   3329	3376	2897	finally
        //   3383	3410	2897	finally
        //   417	471	2902	java/lang/Exception
        //   471	541	2902	java/lang/Exception
        //   544	558	2902	java/lang/Exception
        //   561	584	2902	java/lang/Exception
        //   587	601	2902	java/lang/Exception
        //   601	633	2902	java/lang/Exception
        //   633	671	2902	java/lang/Exception
        //   671	685	2902	java/lang/Exception
        //   685	794	2902	java/lang/Exception
        //   794	870	2902	java/lang/Exception
        //   920	1074	2902	java/lang/Exception
        //   1074	1092	2902	java/lang/Exception
        //   1095	1154	2902	java/lang/Exception
        //   1164	1182	2902	java/lang/Exception
        //   1185	1199	2902	java/lang/Exception
        //   1206	1213	2902	java/lang/Exception
        //   1229	1249	2902	java/lang/Exception
        //   1268	1286	2902	java/lang/Exception
        //   1289	1303	2902	java/lang/Exception
        //   1324	1338	2902	java/lang/Exception
        //   1357	1383	2902	java/lang/Exception
        //   1386	1411	2902	java/lang/Exception
        //   1414	1456	2902	java/lang/Exception
        //   1459	1484	2902	java/lang/Exception
        //   1503	1819	2902	java/lang/Exception
        //   1819	1829	2902	java/lang/Exception
        //   1834	1838	2902	java/lang/Exception
        //   1962	1969	2902	java/lang/Exception
        //   1969	1974	2902	java/lang/Exception
        //   2106	2111	2902	java/lang/Exception
        //   2111	2126	2902	java/lang/Exception
        //   2145	2176	2902	java/lang/Exception
        //   2179	2189	2902	java/lang/Exception
        //   2189	2206	2902	java/lang/Exception
        //   2271	2276	2902	java/lang/Exception
        //   2378	2383	2902	java/lang/Exception
        //   2468	2473	2902	java/lang/Exception
        //   2604	2609	2902	java/lang/Exception
        //   2860	2865	2902	java/lang/Exception
        //   2889	2894	2902	java/lang/Exception
        //   2894	2897	2902	java/lang/Exception
        //   417	471	3290	java/lang/Error
        //   471	541	3290	java/lang/Error
        //   544	558	3290	java/lang/Error
        //   561	584	3290	java/lang/Error
        //   587	601	3290	java/lang/Error
        //   601	633	3290	java/lang/Error
        //   633	671	3290	java/lang/Error
        //   671	685	3290	java/lang/Error
        //   685	794	3290	java/lang/Error
        //   794	870	3290	java/lang/Error
        //   920	1074	3290	java/lang/Error
        //   1074	1092	3290	java/lang/Error
        //   1095	1154	3290	java/lang/Error
        //   1164	1182	3290	java/lang/Error
        //   1185	1199	3290	java/lang/Error
        //   1206	1213	3290	java/lang/Error
        //   1229	1249	3290	java/lang/Error
        //   1268	1286	3290	java/lang/Error
        //   1289	1303	3290	java/lang/Error
        //   1324	1338	3290	java/lang/Error
        //   1357	1383	3290	java/lang/Error
        //   1386	1411	3290	java/lang/Error
        //   1414	1456	3290	java/lang/Error
        //   1459	1484	3290	java/lang/Error
        //   1503	1819	3290	java/lang/Error
        //   1819	1829	3290	java/lang/Error
        //   1834	1838	3290	java/lang/Error
        //   1843	1957	3290	java/lang/Error
        //   1962	1969	3290	java/lang/Error
        //   1969	1974	3290	java/lang/Error
        //   2106	2111	3290	java/lang/Error
        //   2111	2126	3290	java/lang/Error
        //   2145	2176	3290	java/lang/Error
        //   2179	2189	3290	java/lang/Error
        //   2189	2206	3290	java/lang/Error
        //   2271	2276	3290	java/lang/Error
        //   2378	2383	3290	java/lang/Error
        //   2468	2473	3290	java/lang/Error
        //   2604	2609	3290	java/lang/Error
        //   2860	2865	3290	java/lang/Error
        //   2889	2894	3290	java/lang/Error
        //   2894	2897	3290	java/lang/Error
      }
      
      public void start()
      {
        this.mThread = new Thread()
        {
          public void run()
          {
            ApkPkgNameDetector.Detector.DetectTask.this.run();
          }
        };
        this.mThread.setName("ApkPkgNameDetector DetectTask");
        this.mThread.start();
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\ApkPkgNameDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */