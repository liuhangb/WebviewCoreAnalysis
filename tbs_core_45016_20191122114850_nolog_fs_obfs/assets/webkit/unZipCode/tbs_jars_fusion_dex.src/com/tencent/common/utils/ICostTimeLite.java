package com.tencent.common.utils;

import com.tencent.basesupport.ModuleProxy;
import com.tencent.common.manifest.annotation.Service;

@Service
public abstract interface ICostTimeLite
{
  public static final ModuleProxy<ICostTimeLite> PROXY = ModuleProxy.newProxy(ICostTimeLite.class);
  
  public abstract void end(String paramString);
  
  public abstract void start(String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\ICostTimeLite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */