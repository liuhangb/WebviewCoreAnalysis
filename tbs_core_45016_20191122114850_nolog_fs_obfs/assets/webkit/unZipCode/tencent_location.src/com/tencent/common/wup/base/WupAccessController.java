package com.tencent.common.wup.base;

import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.MultiWUPRequestBase;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.common.wup.interfaces.IWUPClientProxy;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class WupAccessController
{
  private IWUPClientProxy a = null;
  
  public WupAccessController(IWUPClientProxy paramIWUPClientProxy)
  {
    this.a = paramIWUPClientProxy;
  }
  
  private void a(ArrayList<WUPRequestBase> paramArrayList)
  {
    if (paramArrayList != null)
    {
      if (paramArrayList.size() <= 0) {
        return;
      }
      paramArrayList = paramArrayList.iterator();
      while (paramArrayList.hasNext())
      {
        WUPRequestBase localWUPRequestBase = (WUPRequestBase)paramArrayList.next();
        try
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("notifyServantUnavailable: req = ");
          localStringBuilder.append(localWUPRequestBase.getServerName());
          localStringBuilder.append("/");
          localStringBuilder.append(localWUPRequestBase.getFuncName());
          FLogger.d("WupAccessController", localStringBuilder.toString());
          localWUPRequestBase.setFailedReason(new Throwable("servant not available"));
          localWUPRequestBase.getRequestCallBack().onWUPTaskFail(localWUPRequestBase);
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
      return;
    }
  }
  
  private boolean a(WUPRequestBase paramWUPRequestBase)
  {
    if (paramWUPRequestBase == null) {
      return false;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(paramWUPRequestBase.getServerName());
    ((StringBuilder)localObject).append(paramWUPRequestBase.getFuncName());
    String str = ((StringBuilder)localObject).toString();
    if (TextUtils.isEmpty(str)) {
      return true;
    }
    try
    {
      localObject = this.a;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("wup_server_avail_time_");
      localStringBuilder.append(str);
      long l1 = ((IWUPClientProxy)localObject).getLongConfiguration(localStringBuilder.toString(), -1L);
      long l2 = System.currentTimeMillis();
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("checkWupServantAvalaible: req = ");
      localStringBuilder.append(paramWUPRequestBase.getServerName());
      localStringBuilder.append("/");
      localStringBuilder.append(paramWUPRequestBase.getFuncName());
      localStringBuilder.append(", enableTime = ");
      if (l1 > 0L) {
        localObject = new Date(l1);
      } else {
        localObject = Long.valueOf(l1);
      }
      localStringBuilder.append(localObject);
      FLogger.d("WupAccessController", localStringBuilder.toString());
      if (l2 > l1)
      {
        if (l1 > 0L)
        {
          localObject = this.a;
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("wup_server_avail_time_");
          localStringBuilder.append(str);
          ((IWUPClientProxy)localObject).setLongConfiguration(localStringBuilder.toString(), -1L);
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("checkWupServantAvalaible: ");
          ((StringBuilder)localObject).append(paramWUPRequestBase.getServerName());
          ((StringBuilder)localObject).append("/");
          ((StringBuilder)localObject).append(paramWUPRequestBase.getFuncName());
          ((StringBuilder)localObject).append(" can be sent now, clear flag");
          FLogger.d("WupAccessController", ((StringBuilder)localObject).toString());
        }
        return true;
      }
      return false;
    }
    catch (Exception paramWUPRequestBase)
    {
      paramWUPRequestBase.printStackTrace();
    }
    return false;
  }
  
  public void disableWupServant(WUPResponseBase paramWUPResponseBase, int paramInt)
  {
    if (this.a == null) {
      return;
    }
    if (paramWUPResponseBase != null)
    {
      if (paramInt <= 0) {
        return;
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramWUPResponseBase.getServantName());
      ((StringBuilder)localObject).append(paramWUPResponseBase.getFuncName());
      localObject = ((StringBuilder)localObject).toString();
      if (TextUtils.isEmpty((CharSequence)localObject)) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("disableWupServant: req = ");
      localStringBuilder.append(paramWUPResponseBase.getServantName());
      localStringBuilder.append("/");
      localStringBuilder.append(paramWUPResponseBase.getFuncName());
      localStringBuilder.append(" ,time = ");
      localStringBuilder.append(paramInt);
      FLogger.d("WupAccessController", localStringBuilder.toString());
      paramWUPResponseBase = this.a;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("wup_server_avail_time_");
      localStringBuilder.append((String)localObject);
      paramWUPResponseBase.setLongConfiguration(localStringBuilder.toString(), paramInt * 1000 + System.currentTimeMillis());
      return;
    }
  }
  
  public void handleEnableServer(String paramString)
  {
    if (this.a == null) {
      return;
    }
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    paramString = paramString.split(";");
    if (paramString != null)
    {
      if (paramString.length <= 0) {
        return;
      }
      int i = 0;
      while (i < paramString.length)
      {
        if (!TextUtils.isEmpty(paramString[i]))
        {
          IWUPClientProxy localIWUPClientProxy = this.a;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("wup_server_avail_time_");
          localStringBuilder.append(paramString[i]);
          localIWUPClientProxy.setLongConfiguration(localStringBuilder.toString(), -1L);
        }
        i += 1;
      }
      return;
    }
  }
  
  public boolean isWupServantAvalaible(WUPRequestBase paramWUPRequestBase)
  {
    Object localObject = this.a;
    boolean bool = true;
    if (localObject == null) {
      return true;
    }
    localObject = new ArrayList();
    if ((paramWUPRequestBase instanceof MultiWUPRequestBase))
    {
      paramWUPRequestBase = ((MultiWUPRequestBase)paramWUPRequestBase).getRequests();
      if (paramWUPRequestBase != null)
      {
        if (paramWUPRequestBase.size() <= 0) {
          return false;
        }
        ((ArrayList)localObject).addAll(paramWUPRequestBase);
        int i = paramWUPRequestBase.size() - 1;
        while (i >= 0)
        {
          WUPRequestBase localWUPRequestBase = (WUPRequestBase)paramWUPRequestBase.get(i);
          if (a(localWUPRequestBase)) {
            ((ArrayList)localObject).remove(localWUPRequestBase);
          } else {
            paramWUPRequestBase.remove(localWUPRequestBase);
          }
          i -= 1;
        }
        if (paramWUPRequestBase.size() <= 0) {
          bool = false;
        }
      }
      else
      {
        return false;
      }
    }
    else if (!a(paramWUPRequestBase))
    {
      ((ArrayList)localObject).add(paramWUPRequestBase);
      bool = false;
    }
    a((ArrayList)localObject);
    return bool;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\base\WupAccessController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */