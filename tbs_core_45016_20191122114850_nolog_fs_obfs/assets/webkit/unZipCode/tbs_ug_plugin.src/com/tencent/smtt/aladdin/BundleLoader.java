package com.tencent.smtt.aladdin;

import com.tencent.smtt.export.external.easel.interfaces.ILoader.Delegate;

class BundleLoader
  implements ILoader.Delegate
{
  public static abstract interface Listener
  {
    public abstract void onLoadComplete(BundleLoader paramBundleLoader, String paramString1, String paramString2);
    
    public abstract void onLoadFailed(BundleLoader paramBundleLoader);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\aladdin\BundleLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */