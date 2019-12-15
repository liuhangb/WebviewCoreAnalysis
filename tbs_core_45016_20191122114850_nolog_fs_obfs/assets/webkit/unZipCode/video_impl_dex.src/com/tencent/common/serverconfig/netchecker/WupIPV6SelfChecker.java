package com.tencent.common.serverconfig.netchecker;

import android.content.Context;
import com.tencent.basesupport.FLogger;
import com.tencent.common.serverconfig.IPListDataManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WupIPV6SelfChecker
  extends BaseWupSelfChecker
{
  private String a = "";
  
  public WupIPV6SelfChecker(String paramString, Context paramContext)
  {
    super(localStringBuilder.toString(), paramContext);
    this.a = paramString;
  }
  
  public static void resetCheckTime()
  {
    Iterator localIterator = sCheckingMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ((str != null) && (str.endsWith("_ipv6"))) {
        localIterator.remove();
      }
    }
  }
  
  protected void onCheckComplete(String paramString, List<String> paramList)
  {
    if (this.mCallback != null) {
      this.mCallback.onSelfCheckResult(this.a, paramList);
    }
  }
  
  protected ArrayList<String> provideAddress()
  {
    ArrayList localArrayList = IPListDataManager.getInstance().getServerList(this.a, true);
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\serverconfig\netchecker\WupIPV6SelfChecker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */