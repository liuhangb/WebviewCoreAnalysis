package com.tencent.tbs.core.partner.ad;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import com.tencent.common.http.Apn;
import com.tencent.common.http.ContentType;
import com.tencent.common.http.MttRequestBase;
import com.tencent.common.http.MttResponse;
import com.tencent.mtt.base.task.NetworkTask;
import com.tencent.mtt.base.task.NetworkTask.NetworkTaskCallback;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.partner.ad.stats.AdStatsInfoRecorder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class AdRequest
{
  private static final String ADURL = "http://recmd.html5.qq.com/node/ajax?action=getadnew";
  private static final int HTTP_READ_TIMEOUT = 5000;
  private static final int MAX_REQUEST_TIME = 10000;
  private static final int MSG_LOAD_DONE = 1;
  private static final int MSG_LOAD_FAILED = 2;
  private boolean mAbort = false;
  private IAdRequestInterface mAdWebViewController;
  private ContentInfo mContentInfo;
  private String mErrorType = "0";
  private Handler mHandler = new MyHandler(this);
  private int mHttpCode;
  private String mMiniProgramPkgName = null;
  private String mPmad = null;
  private String mReLocation = null;
  private String mSourcePage = null;
  private long mStartRequestTime;
  private int mTbsAdUrlIndex = 0;
  
  public AdRequest(IAdRequestInterface paramIAdRequestInterface)
  {
    this.mAdWebViewController = paramIAdRequestInterface;
    this.mMiniProgramPkgName = paramIAdRequestInterface.getMiniProgramPkgName();
  }
  
  private void LoadFailed()
  {
    if (this.mAbort) {
      return;
    }
    Object localObject;
    if (SystemClock.elapsedRealtime() - this.mStartRequestTime < 10000L)
    {
      int i = this.mHttpCode;
      if ((i == 301) || (i == 302))
      {
        localObject = getNextRequestUrl(this.mSourcePage);
        if (TextUtils.isEmpty((CharSequence)localObject)) {
          return;
        }
        RequestAdImpl((String)localObject);
        return;
      }
    }
    if ((this.mHttpCode == 404) || ("0".equals(this.mPmad)) || (TextUtils.isEmpty(this.mPmad)))
    {
      localObject = this.mAdWebViewController;
      if (localObject != null) {
        ((IAdRequestInterface)localObject).loadAdWithData("0", null, this.mErrorType);
      }
    }
    this.mContentInfo = null;
    this.mAdWebViewController = null;
  }
  
  private int RequestAdImpl(String paramString)
  {
    paramString = new NetworkTask(paramString, new NetworkTask.NetworkTaskCallback()
    {
      public void onTaskFailed(MttRequestBase paramAnonymousMttRequestBase, int paramAnonymousInt)
      {
        AdRequest.access$302(AdRequest.this, String.valueOf(paramAnonymousInt));
        paramAnonymousMttRequestBase = AdRequest.this.mHandler.obtainMessage(2);
        AdRequest.this.mHandler.sendMessage(paramAnonymousMttRequestBase);
        AdStatsInfoRecorder.getInstance().sendStatsInfoToTbsAdServer("REQUEST_FAILED", AdRequest.this.mSourcePage, null, "taskFailed=true");
      }
      
      public void onTaskSuccess(MttRequestBase paramAnonymousMttRequestBase, MttResponse paramAnonymousMttResponse)
      {
        AdRequest.access$202(AdRequest.this, paramAnonymousMttResponse.getStatusCode().intValue());
        paramAnonymousMttRequestBase = paramAnonymousMttResponse.getHeaderField("errorType");
        if (!TextUtils.isEmpty(paramAnonymousMttRequestBase)) {
          AdRequest.access$302(AdRequest.this, paramAnonymousMttRequestBase);
        } else {
          AdRequest.access$302(AdRequest.this, "0");
        }
        for (;;)
        {
          try
          {
            if ((AdRequest.this.mHttpCode != 301) && (AdRequest.this.mHttpCode != 302))
            {
              if ((AdRequest.this.mHttpCode >= 400) && (AdRequest.this.mHttpCode < 600))
              {
                AdRequest.this.getResponsBody(paramAnonymousMttResponse.getErrorStream(), "utf-8");
                AdRequest.access$402(AdRequest.this, paramAnonymousMttResponse.getHeaderField("pmad"));
              }
              else if ((AdRequest.this.mHttpCode == 200) && (!AdRequest.this.mAbort))
              {
                Object localObject2 = paramAnonymousMttResponse.getContentType().toString();
                localObject1 = paramAnonymousMttResponse.getContentType().mEncoding;
                paramAnonymousMttRequestBase = paramAnonymousMttResponse.getHeaderField("pmad");
                if (!TextUtils.isEmpty(paramAnonymousMttRequestBase)) {
                  AdRequest.access$402(AdRequest.this, paramAnonymousMttRequestBase);
                }
                AdRequest localAdRequest = AdRequest.this;
                paramAnonymousMttResponse = paramAnonymousMttResponse.getInputStream();
                if (!TextUtils.isEmpty((CharSequence)localObject1)) {
                  break label603;
                }
                paramAnonymousMttRequestBase = "utf-8";
                paramAnonymousMttRequestBase = localAdRequest.getResponsBody(paramAnonymousMttResponse, paramAnonymousMttRequestBase);
                if ((!AdRequest.this.mAbort) && (!TextUtils.isEmpty(paramAnonymousMttRequestBase)) && (!TextUtils.isEmpty(AdRequest.this.mPmad)))
                {
                  AdRequest.access$802(AdRequest.this, new ContentInfo(paramAnonymousMttRequestBase, (String)localObject2, (String)localObject1));
                  paramAnonymousMttRequestBase = AdRequest.this.mHandler.obtainMessage(1);
                  AdRequest.this.mHandler.sendMessage(paramAnonymousMttRequestBase);
                  AdStatsInfoRecorder.getInstance().sendStatsInfoToTbsAdServer("REQUEST_SUCCEED", AdRequest.this.mSourcePage, null, "taskSucceed=true");
                  return;
                }
                paramAnonymousMttResponse = AdStatsInfoRecorder.getInstance();
                localObject1 = AdRequest.this.mSourcePage;
                localObject2 = new StringBuilder();
                ((StringBuilder)localObject2).append("contentEmpty=");
                ((StringBuilder)localObject2).append(TextUtils.isEmpty(paramAnonymousMttRequestBase));
                ((StringBuilder)localObject2).append(";pmad=");
                if (AdRequest.this.mPmad == null) {
                  paramAnonymousMttRequestBase = "";
                } else {
                  paramAnonymousMttRequestBase = AdRequest.this.mPmad;
                }
                ((StringBuilder)localObject2).append(paramAnonymousMttRequestBase);
                paramAnonymousMttResponse.sendStatsInfoToTbsAdServer("REQUEST_FAILED", (String)localObject1, null, ((StringBuilder)localObject2).toString());
              }
            }
            else
            {
              AdRequest.access$402(AdRequest.this, paramAnonymousMttResponse.getHeaderField("pmad"));
              AdRequest.access$502(AdRequest.this, paramAnonymousMttResponse.getLocation());
            }
          }
          catch (IOException paramAnonymousMttRequestBase)
          {
            paramAnonymousMttRequestBase.printStackTrace();
          }
          paramAnonymousMttRequestBase = AdRequest.this.mHandler.obtainMessage(2);
          AdRequest.this.mHandler.sendMessage(paramAnonymousMttRequestBase);
          if ((AdRequest.this.mHttpCode == 200) && (AdRequest.this.mAbort))
          {
            paramAnonymousMttRequestBase = AdStatsInfoRecorder.getInstance();
            paramAnonymousMttResponse = AdRequest.this.mSourcePage;
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("taskAborted=");
            ((StringBuilder)localObject1).append(AdRequest.this.mAbort);
            paramAnonymousMttRequestBase.sendStatsInfoToTbsAdServer("REQUEST_ABORTED", paramAnonymousMttResponse, null, ((StringBuilder)localObject1).toString());
            return;
          }
          paramAnonymousMttRequestBase = AdStatsInfoRecorder.getInstance();
          paramAnonymousMttResponse = AdRequest.this.mSourcePage;
          Object localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("failedCode=");
          ((StringBuilder)localObject1).append(AdRequest.this.mHttpCode);
          paramAnonymousMttRequestBase.sendStatsInfoToTbsAdServer("REQUEST_FAILED", paramAnonymousMttResponse, null, ((StringBuilder)localObject1).toString());
          return;
          label603:
          paramAnonymousMttRequestBase = (MttRequestBase)localObject1;
        }
      }
    });
    fillRequestHeader(paramString);
    paramString.getMttRequest().setUseCaches(false);
    paramString.start();
    AdStatsInfoRecorder.getInstance().sendStatsInfoToTbsAdServer("REQUEST_STARTED", this.mSourcePage, null, "taskStarted=true");
    return 0;
  }
  
  private NetworkTask fillRequestHeader(NetworkTask paramNetworkTask)
  {
    try
    {
      paramNetworkTask.setConnectTimeout(5000);
      paramNetworkTask.setReadTimeout(5000);
      paramNetworkTask.addHeader("Q-GUID", SmttServiceProxy.getInstance().getGUID());
      paramNetworkTask.addHeader("Q-UA2", SmttServiceProxy.getInstance().getQUA2());
      paramNetworkTask.addHeader("NetType", Apn.getApnName(Apn.getApnTypeS()));
      paramNetworkTask.addHeader("QAID", SmttServiceProxy.getInstance().getQAID());
      paramNetworkTask.addHeader("QIMEI", SmttServiceProxy.getInstance().getQIMEI());
      String str = SmttServiceProxy.getInstance().getTAID();
      if (!TextUtils.isEmpty(str)) {
        paramNetworkTask.addHeader("QTAID", str);
      }
      str = SmttServiceProxy.getInstance().getOAID();
      if (!TextUtils.isEmpty(str))
      {
        paramNetworkTask.addHeader("QOAID", str);
        return paramNetworkTask;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return paramNetworkTask;
  }
  
  private String getNextRequestUrl(String paramString)
  {
    Object localObject2 = "http://recmd.html5.qq.com/node/ajax?action=getadnew";
    Object localObject1 = this.mReLocation;
    if (localObject1 != null)
    {
      this.mReLocation = null;
      return (String)localObject1;
    }
    ArrayList localArrayList = SmttServiceProxy.getInstance().getTbsAdUrl();
    localObject1 = localObject2;
    if (localArrayList != null)
    {
      localObject1 = localObject2;
      if (localArrayList.size() > 0)
      {
        i = localArrayList.size();
        j = this.mTbsAdUrlIndex;
        if (i > j)
        {
          localObject1 = (String)localArrayList.get(j);
          this.mTbsAdUrlIndex += 1;
        }
        else
        {
          localObject1 = (String)localArrayList.get(0);
          this.mTbsAdUrlIndex = 1;
        }
      }
    }
    int j = AdInfoGenerater.getNativeAdFlag();
    int i = j;
    if (this.mMiniProgramPkgName != null) {
      i = j | 0x80;
    }
    if (((String)localObject1).indexOf("?") > 0)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("&requireType=");
      ((StringBuilder)localObject2).append(i);
      localObject1 = ((StringBuilder)localObject2).toString();
    }
    else
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("?requireType=");
      ((StringBuilder)localObject2).append(i);
      localObject1 = ((StringBuilder)localObject2).toString();
    }
    localObject2 = localObject1;
    if (this.mMiniProgramPkgName != null)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("&weapppkg=");
      ((StringBuilder)localObject2).append(this.mMiniProgramPkgName);
      localObject2 = ((StringBuilder)localObject2).toString();
    }
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append((String)localObject2);
    ((StringBuilder)localObject1).append("&thirdUrl=");
    ((StringBuilder)localObject1).append(paramString);
    return ((StringBuilder)localObject1).toString();
  }
  
  @SuppressLint({"NewApi"})
  private String getResponsBody(InputStream paramInputStream, String paramString)
    throws IOException
  {
    byte[] arrayOfByte = new byte['က'];
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    for (;;)
    {
      int i = paramInputStream.read(arrayOfByte);
      if (i == -1) {
        break;
      }
      localByteArrayOutputStream.write(arrayOfByte, 0, i);
    }
    return new String(localByteArrayOutputStream.toByteArray(), Charset.forName(paramString));
  }
  
  private void notifyLoadAd()
  {
    IAdRequestInterface localIAdRequestInterface = this.mAdWebViewController;
    if (localIAdRequestInterface != null)
    {
      if (this.mAbort) {
        return;
      }
      localIAdRequestInterface.loadAdWithData(this.mPmad, this.mContentInfo, this.mErrorType);
      return;
    }
  }
  
  public void AbortRequest()
  {
    this.mAbort = true;
    this.mAdWebViewController = null;
  }
  
  public void RequestAd(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    this.mSourcePage = paramString;
    this.mAbort = false;
    this.mStartRequestTime = SystemClock.elapsedRealtime();
    paramString = getNextRequestUrl(this.mSourcePage);
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    RequestAdImpl(paramString);
  }
  
  public static abstract interface IAdRequestInterface
  {
    public abstract String getMiniProgramPkgName();
    
    public abstract void loadAdWithData(String paramString1, ContentInfo paramContentInfo, String paramString2);
  }
  
  private static class MyHandler
    extends Handler
  {
    private final WeakReference<AdRequest> mAdRequest;
    
    public MyHandler(AdRequest paramAdRequest)
    {
      this.mAdRequest = new WeakReference(paramAdRequest);
    }
    
    public void handleMessage(Message paramMessage)
    {
      AdRequest localAdRequest = (AdRequest)this.mAdRequest.get();
      if (localAdRequest == null) {
        return;
      }
      switch (paramMessage.what)
      {
      default: 
        return;
      case 2: 
        localAdRequest.LoadFailed();
        return;
      }
      localAdRequest.notifyLoadAd();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\ad\AdRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */