package com.tencent.mtt.miniqb.export;

import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;

public class MiniQBEngine
{
  private static MiniQBEngine jdField_a_of_type_ComTencentMttMiniqbExportMiniQBEngine;
  public static DexLoader mDexLoader;
  public static String sDstDexPath;
  public static String sRscDexPath;
  public static String sSoLoadPath;
  private IMiniQBDexLoaderProvider jdField_a_of_type_ComTencentMttMiniqbExportIMiniQBDexLoaderProvider = null;
  private IMiniQBManager jdField_a_of_type_ComTencentMttMiniqbExportIMiniQBManager = null;
  Object jdField_a_of_type_JavaLangObject = new Object();
  private boolean jdField_a_of_type_Boolean = false;
  
  private IMiniQBManager a()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      if ((mDexLoader != null) && (this.jdField_a_of_type_ComTencentMttMiniqbExportIMiniQBManager == null)) {
        this.jdField_a_of_type_ComTencentMttMiniqbExportIMiniQBManager = ((IMiniQBManager)mDexLoader.invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPageManager", "getInstance", null, null));
      }
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("initMiniQBManager mMiniQBManager= ");
      ((StringBuilder)localObject2).append(this.jdField_a_of_type_ComTencentMttMiniqbExportIMiniQBManager);
      Log.d("MiniQBEngine", ((StringBuilder)localObject2).toString());
      localObject2 = this.jdField_a_of_type_ComTencentMttMiniqbExportIMiniQBManager;
      return (IMiniQBManager)localObject2;
    }
  }
  
  private void a()
  {
    ??? = new StringBuilder();
    ((StringBuilder)???).append("loadDex ");
    ((StringBuilder)???).append(Thread.currentThread());
    Log.d("MiniQBEngine", ((StringBuilder)???).toString());
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      if (mDexLoader == null)
      {
        StringBuilder localStringBuilder;
        if (!this.jdField_a_of_type_Boolean)
        {
          this.jdField_a_of_type_Boolean = true;
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("start load");
          localStringBuilder.append(Thread.currentThread());
          Log.d("MiniQBEngine", localStringBuilder.toString());
          new a(this, this.jdField_a_of_type_ComTencentMttMiniqbExportIMiniQBDexLoaderProvider).start();
        }
        try
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("mDexLoader wait ");
          localStringBuilder.append(Thread.currentThread());
          Log.d("MiniQBEngine", localStringBuilder.toString());
          this.jdField_a_of_type_JavaLangObject.wait(1500L);
          Log.d("MiniQBEngine", "mDexLoader wait done");
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
        }
      }
      return;
    }
  }
  
  public static MiniQBEngine getInstance()
  {
    try
    {
      if (jdField_a_of_type_ComTencentMttMiniqbExportMiniQBEngine == null) {
        jdField_a_of_type_ComTencentMttMiniqbExportMiniQBEngine = new MiniQBEngine();
      }
      MiniQBEngine localMiniQBEngine = jdField_a_of_type_ComTencentMttMiniqbExportMiniQBEngine;
      return localMiniQBEngine;
    }
    finally {}
  }
  
  void a(DexLoader paramDexLoader)
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      this.jdField_a_of_type_Boolean = false;
      mDexLoader = paramDexLoader;
      this.jdField_a_of_type_JavaLangObject.notifyAll();
      return;
    }
  }
  
  public DexClassLoader getMiniQBDexLoader()
  {
    DexLoader localDexLoader = mDexLoader;
    if (localDexLoader != null) {
      return localDexLoader.getDexLoader();
    }
    return null;
  }
  
  public IMiniQBManager getMiniQBManager()
  {
    try
    {
      a();
      IMiniQBManager localIMiniQBManager = a();
      return localIMiniQBManager;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void init(Context paramContext, String paramString1, String paramString2, IMiniQBDexLoaderProvider paramIMiniQBDexLoaderProvider)
  {
    if (paramContext != null)
    {
      sRscDexPath = paramString1;
      sDstDexPath = paramString2;
      this.jdField_a_of_type_ComTencentMttMiniqbExportIMiniQBDexLoaderProvider = paramIMiniQBDexLoaderProvider;
      return;
    }
    throw new NullPointerException("context must be no null!");
  }
  
  private static class a
    extends Thread
  {
    IMiniQBDexLoaderProvider jdField_a_of_type_ComTencentMttMiniqbExportIMiniQBDexLoaderProvider;
    MiniQBEngine jdField_a_of_type_ComTencentMttMiniqbExportMiniQBEngine;
    
    public a(MiniQBEngine paramMiniQBEngine, IMiniQBDexLoaderProvider paramIMiniQBDexLoaderProvider)
    {
      super();
      this.jdField_a_of_type_ComTencentMttMiniqbExportMiniQBEngine = paramMiniQBEngine;
      this.jdField_a_of_type_ComTencentMttMiniqbExportIMiniQBDexLoaderProvider = paramIMiniQBDexLoaderProvider;
    }
    
    public void run()
    {
      this.jdField_a_of_type_ComTencentMttMiniqbExportMiniQBEngine.a(new DexLoader(this.jdField_a_of_type_ComTencentMttMiniqbExportIMiniQBDexLoaderProvider.getDexClassLoader()));
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\miniqb\export\MiniQBEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */