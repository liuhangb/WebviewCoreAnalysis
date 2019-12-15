package com.tencent.mtt.external.reader.export;

import android.content.Context;
import dalvik.system.DexClassLoader;

public abstract interface IReaderProxy
{
  public abstract void destroy();
  
  public abstract void doSearch(Context paramContext, String paramString);
  
  public abstract DexClassLoader getClassLoader();
  
  public abstract IReaderEventProxy getReaderEventProxy();
  
  public abstract IReaderWebViewProxy getWebViewProxy();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\export\IReaderProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */