package com.tencent.tbs.common.http;

import android.text.TextUtils;
import com.tencent.common.http.IHttpCookieManager;
import com.tencent.common.http.MttRequestBase;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.baseinfo.TbsUserInfo;
import com.tencent.tbs.common.utils.QBUrlUtils;
import com.tencent.tbs.common.utils.TbsInfoUtils;

public class MttRequest
  extends MttRequestBase
{
  protected void fillCookies()
  {
    String str;
    if (this.mCookieManager != null) {
      str = this.mCookieManager.getCookie(this.mUrl.toString());
    } else {
      str = null;
    }
    if (str != null) {
      addHeader("Cookie", str);
    }
  }
  
  protected void fillQHeaders()
  {
    String str = TbsInfoUtils.getQUA();
    if ((TbsBaseModuleShell.getEnableQua1()) && (str != null) && (str.length() > 0)) {
      addHeader("Q-UA", str);
    }
    str = TbsInfoUtils.getQUA2();
    if (str != null) {
      addHeader("Q-UA2", str);
    }
    if (QBUrlUtils.isQQDomain(this.mUrl, false))
    {
      str = "";
      if (this.mCookieManager != null) {
        str = this.mCookieManager.getQCookie(this.mUrl.toString());
      }
      if (!TextUtils.isEmpty(str)) {
        addHeader("QCookie", str);
      }
      if (!getIsWupRequest())
      {
        str = GUIDFactory.getInstance().getStrGuid();
        if ((str != null) && (!"".equals(str))) {
          addHeader("Q-GUID", str);
        }
      }
      str = TbsUserInfo.getInstance().getQAuth();
      if ((str != null) && (!"".equals(str))) {
        addHeader("Q-Auth", str);
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\http\MttRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */