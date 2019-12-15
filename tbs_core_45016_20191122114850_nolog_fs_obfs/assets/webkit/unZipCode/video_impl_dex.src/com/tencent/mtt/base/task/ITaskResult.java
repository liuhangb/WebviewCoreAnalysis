package com.tencent.mtt.base.task;

import com.tencent.basesupport.ModuleProxy;
import com.tencent.common.http.MttResponse;
import com.tencent.common.manifest.annotation.Service;

@Service
public abstract interface ITaskResult
{
  public static final ModuleProxy<ITaskResult> PROXY = ModuleProxy.newProxy(ITaskResult.class);
  
  public abstract void handlePictureTaskResult(byte[] paramArrayOfByte, MttResponse paramMttResponse, String paramString1, String paramString2);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\task\ITaskResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */