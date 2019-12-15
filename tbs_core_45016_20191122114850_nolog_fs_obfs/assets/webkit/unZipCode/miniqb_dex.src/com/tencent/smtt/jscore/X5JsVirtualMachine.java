package com.tencent.smtt.jscore;

import android.content.Context;
import android.os.Looper;
import com.tencent.smtt.export.external.jscore.interfaces.IX5JsContext;
import com.tencent.smtt.export.external.jscore.interfaces.IX5JsVirtualMachine;
import org.chromium.base.annotations.UsedByReflection;

public class X5JsVirtualMachine
  implements IX5JsVirtualMachine
{
  private static final String CLAZZ_NAME = "X5JsVirtualMachine";
  private final X5JsVirtualMachineImpl mJsVirtualMachine;
  
  @UsedByReflection("WebCoreProxy.java")
  public X5JsVirtualMachine(Context paramContext, Looper paramLooper)
  {
    this.mJsVirtualMachine = X5JsCoreFactory.createJsVirtualMachine(paramContext, paramLooper);
  }
  
  public IX5JsContext createJsContext()
  {
    return new X5JsContext(this, this.mJsVirtualMachine.createJsContext());
  }
  
  public void destroy()
  {
    this.mJsVirtualMachine.destroy();
  }
  
  public Looper getLooper()
  {
    return this.mJsVirtualMachine.getLooper();
  }
  
  public void onPause()
  {
    this.mJsVirtualMachine.onPause();
  }
  
  public void onResume()
  {
    this.mJsVirtualMachine.onResume();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jscore\X5JsVirtualMachine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */