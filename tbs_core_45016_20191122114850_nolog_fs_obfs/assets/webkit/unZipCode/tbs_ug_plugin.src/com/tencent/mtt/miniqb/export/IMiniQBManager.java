package com.tencent.mtt.miniqb.export;

import android.content.Context;
import android.webkit.ValueCallback;
import dalvik.system.DexClassLoader;
import java.util.Map;

public abstract interface IMiniQBManager
{
  public abstract int closeMiniQB(ValueCallback<String> paramValueCallback);
  
  public abstract void initMiniQB(Context paramContext, DexClassLoader paramDexClassLoader, String paramString);
  
  public abstract int openMiniQB(Context paramContext, String paramString1, String paramString2, Map<String, String> paramMap, ValueCallback<String> paramValueCallback);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\miniqb\export\IMiniQBManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */