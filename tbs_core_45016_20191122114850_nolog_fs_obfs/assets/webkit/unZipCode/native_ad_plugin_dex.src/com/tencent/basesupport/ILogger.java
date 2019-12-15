package com.tencent.basesupport;

import com.tencent.common.manifest.annotation.Service;

@Service
public abstract interface ILogger
{
  public static final ModuleProxy<ILogger> PROXY = ModuleProxy.newProxy(ILogger.class);
  
  public abstract void log(int paramInt, String paramString1, String paramString2);
  
  public abstract void log(int paramInt, String paramString1, String paramString2, Throwable paramThrowable);
  
  public abstract void log(int paramInt, String paramString, Throwable paramThrowable);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\basesupport\ILogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */