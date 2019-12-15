package com.tencent.common.serverconfig.netchecker;

import android.content.Context;
import com.tencent.common.serverconfig.WupProxyDomainRouter;
import java.util.ArrayList;
import java.util.List;

public class WupDomainSelfChecker
  extends BaseWupSelfChecker
{
  private String a = "";
  
  public WupDomainSelfChecker(String paramString, Context paramContext)
  {
    super(localStringBuilder.toString(), paramContext);
    this.a = paramString;
  }
  
  protected void onCheckComplete(String paramString, List<String> paramList)
  {
    if (this.mCallback != null) {
      this.mCallback.onSelfCheckResult(this.a, paramList);
    }
  }
  
  protected ArrayList<String> provideAddress()
  {
    return WupProxyDomainRouter.getAllWupProxyDomains();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\serverconfig\netchecker\WupDomainSelfChecker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */