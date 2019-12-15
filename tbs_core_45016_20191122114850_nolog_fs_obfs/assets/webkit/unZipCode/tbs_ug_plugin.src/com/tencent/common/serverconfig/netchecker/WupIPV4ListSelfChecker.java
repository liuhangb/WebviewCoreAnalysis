package com.tencent.common.serverconfig.netchecker;

import android.content.Context;
import com.tencent.basesupport.FLogger;
import com.tencent.common.serverconfig.IPListDataManager;
import java.util.ArrayList;
import java.util.List;

public class WupIPV4ListSelfChecker
  extends BaseWupSelfChecker
{
  private String a = "";
  
  public WupIPV4ListSelfChecker(String paramString, Context paramContext)
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
    ArrayList localArrayList = IPListDataManager.getInstance().getServerList(this.a);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("doStartSelfCheck: currentIps = ");
    localStringBuilder.append(localArrayList);
    FLogger.d("WupIPListSelfChecker", localStringBuilder.toString());
    if ((localArrayList != null) && (!localArrayList.isEmpty()))
    {
      if (localArrayList.size() > 10)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("doStartSelfCheck: ip size more than 10, ignore, size = ");
        localStringBuilder.append(localArrayList.size());
        FLogger.d("WupIPListSelfChecker", localStringBuilder.toString());
        return null;
      }
      return localArrayList;
    }
    FLogger.d("WupIPListSelfChecker", "doStartSelfCheck: param not available, retrun ");
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\serverconfig\netchecker\WupIPV4ListSelfChecker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */