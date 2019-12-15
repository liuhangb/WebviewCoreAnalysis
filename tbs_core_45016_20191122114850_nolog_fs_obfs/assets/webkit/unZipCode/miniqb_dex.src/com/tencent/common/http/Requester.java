package com.tencent.common.http;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public abstract class Requester
  implements IFlowListener
{
  public static final int GPRS_CONNECT_TIME_OUT = 20000;
  public static final int GPRS_READ_TIME_OUT = 30000;
  protected static final int MAX_RELOCATION_TIMES = 5;
  static final int MAX_RETRY_TIMES = 3;
  public static final int REQUEST_LOCAL = 2;
  public static final int REQUEST_REMOTE = 1;
  public static final int RSP_CHECK_RESULT_OK = 1;
  public static final int RSP_CHECK_RESULT_PENDING = 3;
  public static final int RSP_CHECK_RESULT_RELOCATION = 4;
  public static final int RSP_CHECK_RESULT_RETRY = 2;
  public static final String TAG = "Requester";
  public static final int WIFI_CONNECT_TIME_OUT = 10000;
  public static final int WIFI_READ_TIME_OUT = 20000;
  protected boolean Q_DEBUG = true;
  protected int mApn;
  protected int mConnectTimeout = -1;
  protected Context mContext = null;
  protected boolean mCookieEnable = true;
  protected IHttpCookieManager mCookieManager = null;
  protected boolean mDisableProxy = false;
  protected a mExceptionHandler = new a();
  protected MttInputStream mInputStream;
  protected IRequstIntercepter mInterceptor;
  protected boolean mIsCanceled = false;
  protected boolean mIsRemoveHeader = false;
  protected boolean mIsX5 = true;
  protected MttRequestBase mMttRequest;
  protected MttResponse mMttResponse;
  protected int mQueenRequestTimes = 1;
  protected int mReadTimeout = -1;
  protected int mRelocationTimes = 0;
  protected int mRetryTimes = 0;
  
  public abstract void abort();
  
  protected int checkStatusCode(MttRequestBase paramMttRequestBase, int paramInt, String paramString, long paramLong)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[");
    localStringBuilder.append(getTag());
    localStringBuilder.append("]statusCode: ");
    localStringBuilder.append(paramInt);
    localStringBuilder.append(", queen:");
    boolean bool;
    if ((paramMttRequestBase.isQueenProxyEnable()) && (paramMttRequestBase.getQueenConfig() != null)) {
      bool = true;
    } else {
      bool = false;
    }
    localStringBuilder.append(bool);
    localStringBuilder.append(", retry:");
    localStringBuilder.append(this.mRetryTimes);
    localStringBuilder.append(" [");
    localStringBuilder.append(paramMttRequestBase.getExecuteUrl());
    Object localObject;
    if (!TextUtils.equals(paramMttRequestBase.getExecuteUrl(), paramMttRequestBase.getUrl()))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("(");
      ((StringBuilder)localObject).append(paramMttRequestBase.getUrl());
      ((StringBuilder)localObject).append(")");
      localObject = ((StringBuilder)localObject).toString();
    }
    else
    {
      localObject = "";
    }
    localStringBuilder.append((String)localObject);
    localStringBuilder.append("]");
    if (!TextUtils.isEmpty(paramString))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("->[");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append("]");
      localObject = ((StringBuilder)localObject).toString();
    }
    else
    {
      localObject = "";
    }
    localStringBuilder.append((String)localObject);
    FLogger.d("Requester", localStringBuilder.toString());
    if (paramInt == MttResponse.UNKNOWN_STATUS.intValue())
    {
      if ((paramMttRequestBase.isQueenProxyEnable()) && (paramMttRequestBase.getQueenConfig() != null) && (Apn.isMobileNetwork(true)))
      {
        paramString = new StringBuilder();
        paramString.append("switch queen proxy address... [");
        paramString.append(paramMttRequestBase.getExecuteUrl());
        paramString.append("]");
        FLogger.d("Requester", paramString.toString());
        if (paramMttRequestBase.getQueenConfig().isHttpsRequest) {
          QueenConfig.switchHttpsProxy();
        } else {
          QueenConfig.switchHttpProxy();
        }
        QueenConfig.reportProxyError(paramInt, paramMttRequestBase.getExecuteUrl(), paramMttRequestBase.getRequestType(), paramMttRequestBase.getQueenConfig().token);
        return 2;
      }
      return 1;
    }
    if ((paramMttRequestBase.isDirectionalEnable()) && (FreeFlow.isFreeFlowUser()))
    {
      if (paramInt == 913)
      {
        FreeFlow.onReceiveProxyStatusCode(paramInt);
        paramMttRequestBase.setDirectionalEnable(false);
        return 2;
      }
      return 1;
    }
    if ((paramMttRequestBase.isQueenProxyEnable()) && (paramMttRequestBase.getQueenConfig() != null))
    {
      localObject = paramMttRequestBase.getQueenConfig();
      if ((820 != paramInt) && (821 != paramInt))
      {
        if (paramInt == 822)
        {
          paramString = new StringBuilder();
          paramString.append("queen proxy statusCode: ");
          paramString.append(paramInt);
          paramString.append(" [");
          paramString.append(paramMttRequestBase.getExecuteUrl());
          paramString.append("]");
          FLogger.d("Requester", paramString.toString());
          this.mRetryTimes -= 1;
          paramMttRequestBase.setQueenProxyEnable(false);
          paramMttRequestBase.setIsQueenFlow(false);
          paramMttRequestBase.setQueenStatusCode(paramInt);
          paramMttRequestBase.setQueenErrorCode(3);
          QueenConfig.reportProxyError(paramInt, paramMttRequestBase.getExecuteUrl(), paramMttRequestBase.getRequestType(), ((QueenConfig.QueenConfigInfo)localObject).token);
          return 2;
        }
        if (paramInt == 823)
        {
          paramString = new StringBuilder();
          paramString.append("queen proxy statusCode: ");
          paramString.append(paramInt);
          paramString.append(" [");
          paramString.append(paramMttRequestBase.getExecuteUrl());
          paramString.append("]");
          FLogger.d("Requester", paramString.toString());
          this.mQueenRequestTimes += 1;
          paramMttRequestBase.setQueenStatusCode(paramInt);
          paramMttRequestBase.setQueenErrorCode(3);
          QueenConfig.reportProxyError(paramInt, paramMttRequestBase.getExecuteUrl(), paramMttRequestBase.getRequestType(), ((QueenConfig.QueenConfigInfo)localObject).token);
          return 3;
        }
        if (paramInt >= 824)
        {
          this.mRetryTimes -= 1;
          paramMttRequestBase.setQueenProxyEnable(false);
          paramMttRequestBase.setIsQueenFlow(false);
          QueenConfig.reportProxyError(paramInt, paramMttRequestBase.getExecuteUrl(), paramMttRequestBase.getRequestType(), paramMttRequestBase.getHeader("Q-Token"));
          return 2;
        }
        if (paramInt >= 800)
        {
          paramMttRequestBase.setQueenStatusCode(paramInt);
          QueenConfig.reportProxyError(paramInt, paramMttRequestBase.getExecuteUrl(), paramMttRequestBase.getRequestType(), ((QueenConfig.QueenConfigInfo)localObject).token);
        }
      }
      else
      {
        paramString = new StringBuilder();
        paramString.append("queen proxy statusCode: ");
        paramString.append(paramInt);
        paramString.append(" [");
        paramString.append(paramMttRequestBase.getExecuteUrl());
        paramString.append("]");
        FLogger.d("Requester", paramString.toString());
        paramMttRequestBase.setQueenStatusCode(paramInt);
        QueenConfig.refreshTokenIfNeed();
        QueenConfig.reportProxyError(paramInt, paramMttRequestBase.getExecuteUrl(), paramMttRequestBase.getRequestType(), ((QueenConfig.QueenConfigInfo)localObject).token);
        return 3;
      }
    }
    if ((paramMttRequestBase.isQueenProxyEnable()) && (paramMttRequestBase.isInstanceFollowRedirects()) && (!TextUtils.isEmpty(paramString)) && (this.mRelocationTimes < 5))
    {
      paramMttRequestBase.setRedirectUrl(paramString);
      this.mRelocationTimes += 1;
      return 4;
    }
    if (QueenConfig.isQueenEnable())
    {
      if ((paramMttRequestBase.isQueenProxyEnable()) && (paramMttRequestBase.getQueenConfig() != null) && (paramMttRequestBase.getQueenErrorCode() != 5)) {
        bool = false;
      } else {
        bool = true;
      }
      QueenConfig.report(paramMttRequestBase.getQueenErrorCode(), paramMttRequestBase.getUrl(), paramMttRequestBase.getReferer(), paramInt, paramLong, paramMttRequestBase.getRequestType(), paramMttRequestBase.getQueenStatusCode(), paramMttRequestBase.getIp(), bool, null);
    }
    return 1;
  }
  
  public abstract void close();
  
  public abstract MttResponse execute(MttRequestBase paramMttRequestBase)
    throws Exception;
  
  public int getApn()
  {
    return this.mApn;
  }
  
  public int getConnectTimeoutByApnType()
  {
    if (this.mApn == 4) {
      return 10000;
    }
    return 20000;
  }
  
  public boolean getCookieEnable()
  {
    return this.mCookieEnable;
  }
  
  public int getReadTimeoutByApnType()
  {
    if (this.mApn == 4) {
      return 20000;
    }
    return 30000;
  }
  
  public MttResponse getResponse()
  {
    return null;
  }
  
  protected String getTag()
  {
    return "";
  }
  
  protected int handleException(MttRequestBase paramMttRequestBase, Throwable paramThrowable)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[");
    localStringBuilder.append(getTag());
    localStringBuilder.append("] onExecption: ");
    localStringBuilder.append(paramThrowable.toString());
    localStringBuilder.append(", queen:");
    boolean bool;
    if ((paramMttRequestBase.isQueenProxyEnable()) && (paramMttRequestBase.getQueenConfig() != null)) {
      bool = true;
    } else {
      bool = false;
    }
    localStringBuilder.append(bool);
    localStringBuilder.append(", retry:");
    localStringBuilder.append(this.mRetryTimes);
    localStringBuilder.append(" [");
    localStringBuilder.append(paramMttRequestBase.getExecuteUrl());
    localStringBuilder.append("]");
    FLogger.d("Requester", localStringBuilder.toString());
    return 1;
  }
  
  public void onFlow(int paramInt1, int paramInt2)
  {
    if (this.mMttRequest != null)
    {
      RequesterFactory.IFlowObsever localIFlowObsever = RequesterFactory.getFlowObsever();
      if (localIFlowObsever != null) {
        localIFlowObsever.onRequestFlow(this.mMttRequest, paramInt1, paramInt2);
      }
    }
  }
  
  void onInputStreamException(Exception paramException)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[");
    ((StringBuilder)localObject).append(getTag());
    ((StringBuilder)localObject).append("] onInputStreamException: ");
    ((StringBuilder)localObject).append(paramException);
    ((StringBuilder)localObject).append(", isCanceled: ");
    ((StringBuilder)localObject).append(this.mIsCanceled);
    ((StringBuilder)localObject).append(", queen:");
    boolean bool;
    if ((this.mMttRequest.isQueenProxyEnable()) && (this.mMttRequest.getQueenConfig() != null)) {
      bool = true;
    } else {
      bool = false;
    }
    ((StringBuilder)localObject).append(bool);
    ((StringBuilder)localObject).append(", retry:");
    ((StringBuilder)localObject).append(this.mRetryTimes);
    ((StringBuilder)localObject).append(" [");
    ((StringBuilder)localObject).append(this.mMttRequest.getExecuteUrl());
    ((StringBuilder)localObject).append("]");
    FLogger.d("Requester", ((StringBuilder)localObject).toString());
    if (this.mIsCanceled) {
      return;
    }
    localObject = this.mMttRequest;
    if ((localObject != null) && (((MttRequestBase)localObject).isQueenProxyEnable()) && (((MttRequestBase)localObject).getQueenConfig() != null) && (((paramException instanceof SocketTimeoutException)) || ((paramException instanceof SocketException))) && (Apn.isMobileNetwork(true)))
    {
      paramException = new StringBuilder();
      paramException.append("switch queen proxy address... [");
      paramException.append(((MttRequestBase)localObject).getUrl());
      paramException.append("]");
      FLogger.d("Requester", paramException.toString());
      if (((MttRequestBase)localObject).getQueenConfig().isHttpsRequest)
      {
        QueenConfig.switchHttpsProxy();
        return;
      }
      QueenConfig.switchHttpProxy();
    }
  }
  
  protected ContentType parseContentType(String paramString1, String paramString2)
  {
    String str = paramString1;
    if (paramString1 == null)
    {
      paramString2 = MimeTypeMap.getFileExtensionFromUrl(paramString2);
      str = paramString1;
      if (paramString2 != null) {
        str = MttMimeTypeMap.getSingleton().getMimeTypeFromExtension(paramString2);
      }
    }
    paramString2 = null;
    ContentType localContentType = new ContentType("text", "html", null);
    if (str != null)
    {
      str = str.trim();
      if (!"".equals(str))
      {
        int i = str.indexOf(';');
        paramString1 = str;
        if (i != -1)
        {
          paramString1 = str.substring(0, i);
          paramString2 = str.substring(i + 1);
        }
        if (paramString1 != null)
        {
          i = paramString1.indexOf('/');
          if (i != -1)
          {
            localContentType.mType = paramString1.substring(0, i);
            localContentType.mTypeValue = paramString1.substring(i + 1);
          }
          else
          {
            localContentType.mType = paramString1;
          }
        }
        if (paramString2 != null)
        {
          i = paramString2.indexOf('=');
          if (i != -1) {
            localContentType.mEncoding = paramString2.substring(i + 1);
          }
        }
      }
    }
    return localContentType;
  }
  
  public void setApn(int paramInt)
  {
    this.mApn = paramInt;
  }
  
  public void setConnectTimeout(int paramInt)
  {
    this.mConnectTimeout = paramInt;
  }
  
  public void setContext(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public void setCookieEnable(boolean paramBoolean)
  {
    this.mCookieEnable = paramBoolean;
  }
  
  public void setCookieManager(IHttpCookieManager paramIHttpCookieManager)
  {
    this.mCookieManager = paramIHttpCookieManager;
  }
  
  public void setDisableProxy(boolean paramBoolean)
  {
    this.mDisableProxy = paramBoolean;
    MttRequestBase localMttRequestBase = this.mMttRequest;
    if (localMttRequestBase != null) {
      localMttRequestBase.setProxyDisable(paramBoolean);
    }
  }
  
  public void setIntercepter(IRequstIntercepter paramIRequstIntercepter)
  {
    this.mInterceptor = paramIRequstIntercepter;
  }
  
  public void setIsRemoveHeader(boolean paramBoolean)
  {
    this.mIsRemoveHeader = paramBoolean;
  }
  
  public void setIsX5(boolean paramBoolean)
  {
    this.mIsX5 = paramBoolean;
  }
  
  public void setQDebugEnable(boolean paramBoolean)
  {
    this.Q_DEBUG = paramBoolean;
  }
  
  public void setReadTimeout(int paramInt)
  {
    this.mReadTimeout = paramInt;
  }
  
  class a
    implements MttInputStream.IExceptionHandler
  {
    a() {}
    
    public boolean handleException(Exception paramException)
    {
      Requester.this.onInputStreamException(paramException);
      return false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\Requester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */