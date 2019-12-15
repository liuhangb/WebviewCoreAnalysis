package com.tencent.tbs.tbsshell.partner.miniqb;

import android.os.Bundle;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.tbs.common.wup.WUPRequest;
import com.tencent.tbs.common.wup.WUPTaskProxy;
import java.util.ArrayList;

public class ShellWupHandler
  implements IWUPRequestCallBack
{
  private static final String TAG = "ShellWupHandler";
  private static ShellWupHandler sHandler;
  
  public static ShellWupHandler getInstance()
  {
    if (sHandler == null) {
      sHandler = new ShellWupHandler();
    }
    return sHandler;
  }
  
  public void handleMiniQBWupRequest(Object paramObject1, Object paramObject2, byte[] paramArrayOfByte, Bundle paramBundle)
  {
    if ((paramObject1 != null) && (paramArrayOfByte != null))
    {
      if (paramBundle == null) {
        return;
      }
      WUPRequest localWUPRequest = new WUPRequest();
      localWUPRequest.setEncodeName(paramBundle.getString("EncodeName"));
      localWUPRequest.setFuncName(paramBundle.getString("FuncName"));
      localWUPRequest.setRequestName(paramBundle.getString("RequestName"));
      localWUPRequest.setServerName(paramBundle.getString("ServerName"));
      localWUPRequest.setUrl(paramBundle.getString("Url"));
      localWUPRequest.setHasReplied(paramBundle.getBoolean("HasReplied", false));
      localWUPRequest.setIsBackgroudTask(paramBundle.getBoolean("IsBackGroudTask", false));
      localWUPRequest.setIsFromService(paramBundle.getBoolean("IsFromService", false));
      localWUPRequest.setNeedCloseConnection(paramBundle.getBoolean("NeedCloseConnection", true));
      localWUPRequest.setNeedEncrypt(paramBundle.getBoolean("NeedEncrypt", true));
      localWUPRequest.setNeedStatFlow(paramBundle.getBoolean("NeedStatFlow", true));
      localWUPRequest.setNetworkStatus(paramBundle.getInt("NetworkStatus", -1));
      localWUPRequest.setPackageSize(paramBundle.getInt("PackageSize", 1));
      localWUPRequest.setRequstID(paramBundle.getInt("RequstID", Integer.MIN_VALUE));
      if (paramObject2 != null)
      {
        paramBundle = new ArrayList();
        paramBundle.add(paramObject1);
        paramBundle.add(paramObject2);
        localWUPRequest.setBindObject(paramBundle);
      }
      else
      {
        LogUtils.e("ShellWupHandler", new Throwable("NO CALLBACK BOND with this WUPRequest, Is there a problem ?"));
      }
      localWUPRequest.setPostData(paramArrayOfByte);
      localWUPRequest.setRequestCallBack(this);
      localWUPRequest.setType((byte)1);
      paramObject1 = new StringBuilder();
      ((StringBuilder)paramObject1).append("MiniQB send request at shell, serverName=");
      ((StringBuilder)paramObject1).append(localWUPRequest.getServerName());
      ((StringBuilder)paramObject1).append(", funcName=");
      ((StringBuilder)paramObject1).append(localWUPRequest.getFuncName());
      LogUtils.d("ShellWupHandler", ((StringBuilder)paramObject1).toString());
      WUPTaskProxy.send(localWUPRequest);
      return;
    }
  }
  
  public void onWUPTaskFail(WUPRequestBase paramWUPRequestBase)
  {
    LogUtils.d("ShellWupHandler", "onWUPTaskfail");
    if (paramWUPRequestBase == null) {
      return;
    }
    if (paramWUPRequestBase.getType() == 1)
    {
      Object localObject1 = paramWUPRequestBase.getBindObject();
      if (localObject1 != null)
      {
        if (!(localObject1 instanceof ArrayList)) {
          return;
        }
        Object localObject2 = (ArrayList)localObject1;
        if (((ArrayList)localObject2).size() != 2) {
          return;
        }
        localObject1 = ((ArrayList)localObject2).get(0);
        localObject2 = ((ArrayList)localObject2).get(1);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onWUPTaskFail at shell, serverName=");
        localStringBuilder.append(paramWUPRequestBase.getServerName());
        localStringBuilder.append(", funcName=");
        localStringBuilder.append(paramWUPRequestBase.getFuncName());
        LogUtils.d("ShellWupHandler", localStringBuilder.toString());
        TbsMiniQBProxy.handleWupCallback(localObject1, localObject2, null, false);
        return;
      }
      return;
    }
  }
  
  public void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase)
  {
    LogUtils.d("ShellWupHandler", "onWUPTaskSuccess");
    if (paramWUPRequestBase == null) {
      return;
    }
    if (paramWUPResponseBase == null)
    {
      onWUPTaskFail(paramWUPRequestBase);
      return;
    }
    if (paramWUPRequestBase.getType() == 1)
    {
      Object localObject1 = paramWUPRequestBase.getBindObject();
      if (localObject1 != null)
      {
        if (!(localObject1 instanceof ArrayList)) {
          return;
        }
        Object localObject2 = (ArrayList)localObject1;
        if (((ArrayList)localObject2).size() != 2) {
          return;
        }
        localObject1 = ((ArrayList)localObject2).get(0);
        localObject2 = ((ArrayList)localObject2).get(1);
        paramWUPResponseBase = paramWUPResponseBase.getOrglResponseData();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onWUPTaskSuccess at shell, serverName=");
        localStringBuilder.append(paramWUPRequestBase.getServerName());
        localStringBuilder.append(", funcName=");
        localStringBuilder.append(paramWUPRequestBase.getFuncName());
        LogUtils.d("ShellWupHandler", localStringBuilder.toString());
        TbsMiniQBProxy.handleWupCallback(localObject1, localObject2, paramWUPResponseBase, true);
        return;
      }
      return;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\ShellWupHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */