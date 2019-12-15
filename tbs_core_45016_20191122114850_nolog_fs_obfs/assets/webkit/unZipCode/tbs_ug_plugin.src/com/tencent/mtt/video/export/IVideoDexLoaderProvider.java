package com.tencent.mtt.video.export;

import dalvik.system.DexClassLoader;

public abstract interface IVideoDexLoaderProvider
{
  public abstract DexClassLoader getDexClassLoader();
  
  public abstract DexClassLoader getDexClassLoader(String[] paramArrayOfString, String paramString1, String paramString2);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\export\IVideoDexLoaderProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */