package com.tencent.tbs.common.baseinfo;

import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.settings.PublicSettingManager;
import java.util.ArrayList;

public class AdAppPushServiceConfig
  implements ICommonConfigListService
{
  private static final String TAG = "AdAppPushServiceConfig";
  
  public void onCommonConfigTaskFail(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onCommonConfigTaskFail errorCode = ");
    localStringBuilder.append(paramString);
    LogUtils.d("AdAppPushServiceConfig", localStringBuilder.toString());
  }
  
  public void onCommonConfigTaskSuccess(int paramInt, ArrayList<String> paramArrayList)
  {
    Object localObject = "";
    int i = 0;
    while (i < paramArrayList.size())
    {
      String str = (String)paramArrayList.get(i);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append((String)localObject);
      localStringBuilder.append(str);
      localStringBuilder.append(",");
      localObject = localStringBuilder.toString();
      i += 1;
    }
    if ("".equals(localObject)) {
      return;
    }
    paramArrayList = new StringBuilder();
    paramArrayList.append("[");
    paramArrayList.append(((String)localObject).substring(0, ((String)localObject).length() - 1));
    paramArrayList.append("]");
    paramArrayList = paramArrayList.toString();
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("onCommonConfigTaskSuccess pushServiceList = ");
    ((StringBuilder)localObject).append(paramArrayList);
    LogUtils.d("AdAppPushServiceConfig", ((StringBuilder)localObject).toString());
    PublicSettingManager.getInstance().setCommonConfigListContent(paramInt, paramArrayList);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\AdAppPushServiceConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */