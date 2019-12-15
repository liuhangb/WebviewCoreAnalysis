package com.tencent.common.http;

import com.tencent.mtt.AppInfoHolder;
import com.tencent.mtt.AppInfoHolder.AppInfoID;
import java.net.HttpURLConnection;

public class HttpRequester
  extends HttpRequesterBase
{
  protected void fillUserAgent()
  {
    if ((this.mMttRequest.getUserAgent() != null) && (!this.mIsRemoveHeader)) {
      this.mMttRequest.addHeader("User-Agent", this.mMttRequest.getUserAgent());
    }
    if (this.mMttRequest.getRequestType() == 104) {
      this.mMttRequest.addHeader("User-Agent", AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_USER_AGENT));
    }
  }
  
  protected void setCookie()
  {
    if ((this.mCookieEnable) && (this.mCookieManager != null)) {
      this.mCookieManager.setCookie(this.mUrl, this.mHttpConnection.getHeaderFields());
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\HttpRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */