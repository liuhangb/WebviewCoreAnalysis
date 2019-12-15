package com.tencent.tbs.core;

import android.content.Context;
import android.os.Looper;
import android.webkit.ValueCallback;
import com.tencent.smtt.export.external.interfaces.IX5CoreJsCore;
import com.tencent.smtt.export.external.jscore.interfaces.IX5JsVirtualMachine;
import com.tencent.smtt.jscore.X5JsContextImpl;
import com.tencent.smtt.jscore.X5JsValue;
import com.tencent.smtt.jscore.X5JsVirtualMachine;
import com.tencent.smtt.webkit.X5JavaBridge;
import com.tencent.tbs.common.settings.PublicSettingManager;
import java.nio.ByteBuffer;

public class X5CoreJsCore
  implements IX5CoreJsCore
{
  private static X5CoreJsCore sInstance;
  
  public static X5CoreJsCore getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new X5CoreJsCore();
      }
      X5CoreJsCore localX5CoreJsCore = sInstance;
      return localX5CoreJsCore;
    }
    finally {}
  }
  
  public void addJavascriptInterface(Object paramObject1, String paramString, Object paramObject2)
  {
    if ((paramObject2 instanceof X5JavaBridge)) {
      ((X5JavaBridge)paramObject2).addJavascriptInterface(paramObject1, paramString);
    }
  }
  
  public boolean canUseX5JsCore(Context paramContext)
  {
    paramContext = PublicSettingManager.getInstance();
    if (paramContext != null) {
      return paramContext.getCanUseX5Jscore();
    }
    return false;
  }
  
  public boolean canUseX5JsCoreNewAPI(Context paramContext)
  {
    paramContext = PublicSettingManager.getInstance();
    boolean bool2 = false;
    if (paramContext != null)
    {
      boolean bool1 = bool2;
      if (paramContext.getCanUseX5Jscore())
      {
        bool1 = bool2;
        if (paramContext.getCanUseX5JscoreNewAPI()) {
          bool1 = true;
        }
      }
      return bool1;
    }
    return false;
  }
  
  public boolean canX5JsCoreUseBuffer(Context paramContext)
  {
    X5CoreMessy.getInstance().doWupTask();
    paramContext = PublicSettingManager.getInstance();
    if (paramContext != null) {
      return paramContext.getX5JsCoreBufferSwitch();
    }
    return false;
  }
  
  public Object createX5JavaBridge(Context paramContext)
  {
    return new X5JavaBridge(paramContext);
  }
  
  public IX5JsVirtualMachine createX5JsVirtualMachine(Context paramContext, Looper paramLooper)
  {
    return new X5JsVirtualMachine(paramContext, paramLooper);
  }
  
  public Object currentContextData()
  {
    return X5JsContextImpl.currentContextData();
  }
  
  public void destroyX5JsCore(Object paramObject)
  {
    if ((paramObject instanceof X5JavaBridge)) {
      ((X5JavaBridge)paramObject).destroy();
    }
  }
  
  public void evaluateJavaScript(String paramString, ValueCallback<String> paramValueCallback, Object paramObject)
  {
    if ((paramObject instanceof X5JavaBridge)) {
      ((X5JavaBridge)paramObject).evaluateJavaScript(paramString, paramValueCallback);
    }
  }
  
  public ByteBuffer getNativeBuffer(Object paramObject, int paramInt)
  {
    if ((paramObject instanceof X5JavaBridge)) {
      return ((X5JavaBridge)paramObject).getNativeBuffer(paramInt);
    }
    return null;
  }
  
  public int getNativeBufferId(Object paramObject)
  {
    if ((paramObject instanceof X5JavaBridge)) {
      return ((X5JavaBridge)paramObject).getNativeBufferId();
    }
    return -1;
  }
  
  public void pause(Object paramObject)
  {
    if ((paramObject instanceof X5JavaBridge)) {
      ((X5JavaBridge)paramObject).pause();
    }
  }
  
  public void pauseTimers(Object paramObject)
  {
    if ((paramObject instanceof X5JavaBridge)) {
      ((X5JavaBridge)paramObject).pauseTimers();
    }
  }
  
  public void removeJavascriptInterface(String paramString, Object paramObject)
  {
    if ((paramObject instanceof X5JavaBridge)) {
      ((X5JavaBridge)paramObject).removeJavascriptInterface(paramString);
    }
  }
  
  public void resume(Object paramObject)
  {
    if ((paramObject instanceof X5JavaBridge)) {
      ((X5JavaBridge)paramObject).resume();
    }
  }
  
  public void resumeTimers(Object paramObject)
  {
    if ((paramObject instanceof X5JavaBridge)) {
      ((X5JavaBridge)paramObject).resumeTimers();
    }
  }
  
  public void setJsValueFactory(Object paramObject)
  {
    X5JsValue.setJsValueFactory(paramObject);
  }
  
  public void setNativeBuffer(Object paramObject, int paramInt, ByteBuffer paramByteBuffer)
  {
    if ((paramObject instanceof X5JavaBridge)) {
      ((X5JavaBridge)paramObject).setNativeBuffer(paramInt, paramByteBuffer);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\X5CoreJsCore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */