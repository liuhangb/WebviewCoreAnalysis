package com.tencent.common.http;

import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.mtt.AppInfoHolder;
import com.tencent.mtt.AppInfoHolder.AppInfoID;
import java.net.URL;
import java.util.List;
import java.util.Map;
import org.apache.http.client.methods.HttpRequestBase;

public class HttpClientRequester
  extends HttpClientRequesterBase
{
  protected void fillCookies()
  {
    if ((this.mCookieEnable) && (this.mCookieManager != null))
    {
      String str = this.mCookieManager.getCookie(this.mUrl.toString());
      if (!TextUtils.isEmpty(str)) {
        this.mHttpRequest.setHeader("Cookie", str);
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("CookieStr : ");
      localStringBuilder.append(str);
      FLogger.d("HttpClientRequester", localStringBuilder.toString());
    }
  }
  
  protected void fillQHeaders()
  {
    Object localObject = AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_QUA_IF_ENABLED);
    StringBuilder localStringBuilder;
    if (localObject != null)
    {
      this.mHttpRequest.setHeader("Q-UA", (String)localObject);
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Q-UA : ");
      localStringBuilder.append((String)localObject);
      FLogger.d("HttpClientRequester", localStringBuilder.toString());
    }
    this.mHttpRequest.setHeader("Q-UA2", AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_QUA2_3));
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Url = ");
    ((StringBuilder)localObject).append(this.mUrl);
    ((StringBuilder)localObject).append(" -- need QHead = ");
    boolean bool;
    if ((this.mCookieManager != null) && (this.mCookieManager.isQQDomain(this.mUrl))) {
      bool = true;
    } else {
      bool = false;
    }
    ((StringBuilder)localObject).append(bool);
    FLogger.d("HttpClientRequester", ((StringBuilder)localObject).toString());
    if ((this.mCookieManager != null) && (this.mCookieManager.isQQDomain(this.mUrl)))
    {
      localObject = this.mCookieManager.getQCookie(this.mUrl.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Set Qcookie Domain:");
      localStringBuilder.append(this.mUrl.getHost());
      FLogger.d("COOKIE", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Set Qcookie 1 :");
      localStringBuilder.append((String)localObject);
      FLogger.d("COOKIE", localStringBuilder.toString());
      if (!TextUtils.isEmpty((CharSequence)localObject))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("Set Qcookie:");
        localStringBuilder.append((String)localObject);
        FLogger.d("COOKIE", localStringBuilder.toString());
        this.mHttpRequest.setHeader("QCookie", (String)localObject);
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("QCookieStr : ");
      localStringBuilder.append((String)localObject);
      FLogger.d("HttpClientRequester", localStringBuilder.toString());
      localObject = AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_GUID);
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Q-GUID : ");
      localStringBuilder.append((String)localObject);
      FLogger.d("HttpClientRequester", localStringBuilder.toString());
      if (!TextUtils.isEmpty((CharSequence)localObject)) {
        this.mHttpRequest.setHeader("Q-GUID", (String)localObject);
      }
      localObject = AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_QAUTH);
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Q-AUTH : ");
      localStringBuilder.append((String)localObject);
      FLogger.d("HttpClientRequester", localStringBuilder.toString());
      this.mHttpRequest.setHeader("Q-Auth", (String)localObject);
    }
  }
  
  protected void fillUserAgent()
  {
    if (this.mMttRequest.getRequestType() == 104)
    {
      this.mMttRequest.addHeader("User-Agent", AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_USER_AGENT));
      return;
    }
    this.mMttRequest.addHeader("User-Agent", this.mMttRequest.getUserAgent());
  }
  
  public void setCookie(Map<String, List<String>> paramMap)
  {
    if ((this.mCookieEnable) && (this.mCookieManager != null)) {
      this.mCookieManager.setCookie(this.mUrl, paramMap);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\HttpClientRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */