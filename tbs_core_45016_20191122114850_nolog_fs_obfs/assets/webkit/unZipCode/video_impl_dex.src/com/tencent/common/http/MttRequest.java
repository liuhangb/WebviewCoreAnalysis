package com.tencent.common.http;

import android.text.TextUtils;
import com.tencent.common.utils.UrlUtils;
import com.tencent.mtt.AppInfoHolder;
import com.tencent.mtt.AppInfoHolder.AppInfoID;
import java.net.URL;

public class MttRequest
  extends MttRequestBase
{
  protected void fillCookies()
  {
    if (this.mCookieManager != null)
    {
      String str = this.mCookieManager.getCookie(this.mUrl.toString());
      if (str != null) {
        addHeader("Cookie", str);
      }
    }
  }
  
  protected void fillQHeaders()
  {
    Object localObject = AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_QUA_IF_ENABLED);
    if (localObject != null) {
      addHeader("Q-UA", (String)localObject);
    }
    addHeader("Q-UA2", AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_QUA2_3));
    boolean bool2 = false;
    boolean bool1;
    try
    {
      localObject = UrlUtils.toURL(this.mUrl);
      bool1 = bool2;
      if (this.mCookieManager != null) {
        bool1 = this.mCookieManager.isQQDomain((URL)localObject);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      bool1 = bool2;
    }
    if ((bool1) && (this.mCookieManager != null))
    {
      String str = this.mCookieManager.getQCookie(this.mUrl.toString());
      if (!TextUtils.isEmpty(str)) {
        addHeader("QCookie", str);
      }
      addHeader("Q-GUID", AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_GUID));
      str = AppInfoHolder.getAppInfoByID(AppInfoHolder.AppInfoID.APP_INFO_QAUTH);
      if (!TextUtils.isEmpty(str)) {
        addHeader("Q-Auth", str);
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\MttRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */