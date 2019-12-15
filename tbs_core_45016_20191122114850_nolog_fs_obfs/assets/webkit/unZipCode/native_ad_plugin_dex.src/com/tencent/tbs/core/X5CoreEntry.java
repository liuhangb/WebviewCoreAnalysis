package com.tencent.tbs.core;

import android.content.Context;
import com.tencent.smtt.export.external.interfaces.IX5CoreCacheManager;
import com.tencent.smtt.export.external.interfaces.IX5CoreCookieManager;
import com.tencent.smtt.export.external.interfaces.IX5CoreEntry;
import com.tencent.smtt.export.external.interfaces.IX5CoreGeolocationPermissions;
import com.tencent.smtt.export.external.interfaces.IX5CoreJsCore;
import com.tencent.smtt.export.external.interfaces.IX5CoreMessy;
import com.tencent.smtt.export.external.interfaces.IX5CoreNetwork;
import com.tencent.smtt.export.external.interfaces.IX5CoreOptimizedBitmap;
import com.tencent.smtt.export.external.interfaces.IX5CoreUrlUtil;
import com.tencent.smtt.export.external.interfaces.IX5CoreWebIconDB;
import com.tencent.smtt.export.external.interfaces.IX5CoreWebStorage;
import com.tencent.smtt.export.external.interfaces.IX5CoreWebViewDB;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import com.tencent.tbs.tbsshell.TBSShell;
import com.tencent.tbs.tbsshell.WebCoreProxy;
import dalvik.system.DexClassLoader;
import java.util.Map;

public class X5CoreEntry
  implements IX5CoreEntry
{
  private Map mOptions = null;
  
  public X5CoreEntry(Map paramMap)
  {
    this.mOptions = paramMap;
  }
  
  public boolean canUseX5()
  {
    return WebCoreProxy.canUseX5();
  }
  
  public IX5WebViewBase createX5WebView(Context paramContext, boolean paramBoolean)
  {
    return new X5WebViewAdapter(paramContext, paramBoolean);
  }
  
  public IX5CoreCacheManager getX5CoreCacheManager()
  {
    return X5CoreCacheManager.getInstance();
  }
  
  public IX5CoreCookieManager getX5CoreCookieManager()
  {
    return X5CoreCookieManager.getInstance();
  }
  
  public IX5CoreGeolocationPermissions getX5CoreGeolocationPermissions()
  {
    return X5CoreGeolocationPermissions.getInstance();
  }
  
  public IX5CoreJsCore getX5CoreJsCore()
  {
    return X5CoreJsCore.getInstance();
  }
  
  public IX5CoreMessy getX5CoreMessy()
  {
    return X5CoreMessy.getInstance();
  }
  
  public IX5CoreNetwork getX5CoreNetwork()
  {
    return X5CoreNetwork.getInstance();
  }
  
  public IX5CoreOptimizedBitmap getX5CoreOptimizedBitmap()
  {
    return X5CoreOptimizedBitmap.getInstance();
  }
  
  public IX5CoreUrlUtil getX5CoreUrlUtil()
  {
    return X5CoreUrlUtil.getInstance();
  }
  
  public IX5CoreWebIconDB getX5CoreWebIconDB()
  {
    return X5CoreWebIconDB.getInstance();
  }
  
  public IX5CoreWebStorage getX5CoreWebStorage()
  {
    return X5CoreWebStorage.getInstance();
  }
  
  public IX5CoreWebViewDB getX5CoreWebViewDB()
  {
    return X5CoreWebViewDB.getInstance();
  }
  
  public void initRuntimeEnvironment()
  {
    Context localContext1 = (Context)this.mOptions.get("callerContext");
    Context localContext2 = (Context)this.mOptions.get("hostContext");
    DexClassLoader localDexClassLoader = (DexClassLoader)this.mOptions.get("dexClassLoader");
    String str1 = (String)this.mOptions.get("installationDirectory");
    String str2 = (String)this.mOptions.get("optimizedDirectory");
    String str3 = (String)this.mOptions.get("librarySearchPath");
    TBSShell.initTesRuntimeEnvironment(localContext1, localContext2, localDexClassLoader, str1, str2);
  }
  
  public void initSettings(Map paramMap)
  {
    TBSShell.initTbsSettings(paramMap);
  }
  
  public void setQua1(String paramString)
  {
    TBSShell.setQua1FromUi(paramString);
  }
  
  public void setQua2_v3(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, boolean paramBoolean)
  {
    TBSShell.setQbInfoForQua2_v3(paramString1, paramString2, paramString3, paramString4, paramString5, paramBoolean);
  }
  
  public void setSdkVersionCode(int paramInt)
  {
    TBSShell.setTesSdkVersionCode(paramInt);
  }
  
  public void setSdkVersionName(String paramString)
  {
    TBSShell.setTesSdkVersionName(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\X5CoreEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */