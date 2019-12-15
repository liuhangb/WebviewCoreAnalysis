package com.tencent.smtt.jscore.compat;

import android.content.Context;
import android.webkit.ValueCallback;
import com.tencent.smtt.jscore.X5JsContextImpl;
import com.tencent.smtt.jscore.X5JsCoreFactory;
import com.tencent.smtt.jscore.X5JsVirtualMachineImpl;
import java.nio.ByteBuffer;

public class X5JsCoreCompat
{
  private X5JsContextImpl mJsContext = null;
  private NativeBufferCompat mNativeBufferCompat;
  
  @Deprecated
  public X5JsCoreCompat(Context paramContext)
  {
    this.mJsContext = X5JsCoreFactory.createJsContext(paramContext);
    this.mNativeBufferCompat = new NativeBufferCompat();
    addJavascriptInterface(this.mNativeBufferCompat, "nativeBufferCompat");
    evaluateJavaScript("function getNativeBufferId() {   if (nativeBufferCompat) {       return nativeBufferCompat.getNativeBufferId();   }   return -1;}function setNativeBuffer(id, bytes) {   if (nativeBufferCompat) {       return nativeBufferCompat.setNativeBuffer(id, bytes);   }}function getNativeBuffer(id) {   if (nativeBufferCompat) {       return nativeBufferCompat.getNativeBuffer(id);   }}", null);
  }
  
  @Deprecated
  public void addJavascriptInterface(Object paramObject, String paramString)
  {
    X5JsContextImpl localX5JsContextImpl = this.mJsContext;
    if (localX5JsContextImpl == null) {
      return;
    }
    localX5JsContextImpl.addJavascriptInterface(paramObject, paramString);
  }
  
  @Deprecated
  public void destroy()
  {
    if (this.mJsContext == null) {
      return;
    }
    this.mNativeBufferCompat.destroy();
    this.mJsContext.virtualMachine().destroy();
    this.mJsContext = null;
  }
  
  @Deprecated
  public void evaluateJavaScript(String paramString, ValueCallback<String> paramValueCallback)
  {
    X5JsContextImpl localX5JsContextImpl = this.mJsContext;
    if (localX5JsContextImpl == null) {
      return;
    }
    localX5JsContextImpl.evaluateJavascript(paramString, paramValueCallback);
  }
  
  @Deprecated
  public ByteBuffer getNativeBuffer(int paramInt)
  {
    return ByteBuffer.wrap(this.mNativeBufferCompat.getNativeBuffer(paramInt));
  }
  
  @Deprecated
  public int getNativeBufferId()
  {
    return this.mNativeBufferCompat.getNativeBufferId();
  }
  
  @Deprecated
  public void pause()
  {
    X5JsContextImpl localX5JsContextImpl = this.mJsContext;
    if (localX5JsContextImpl == null) {
      return;
    }
    localX5JsContextImpl.virtualMachine().onPause();
  }
  
  @Deprecated
  public void pauseTimers()
  {
    pause();
  }
  
  @Deprecated
  public void removeJavascriptInterface(String paramString)
  {
    X5JsContextImpl localX5JsContextImpl = this.mJsContext;
    if (localX5JsContextImpl == null) {
      return;
    }
    localX5JsContextImpl.removeJavascriptInterface(paramString);
  }
  
  @Deprecated
  public void resume()
  {
    X5JsContextImpl localX5JsContextImpl = this.mJsContext;
    if (localX5JsContextImpl == null) {
      return;
    }
    localX5JsContextImpl.virtualMachine().onResume();
  }
  
  @Deprecated
  public void resumeTimers()
  {
    resume();
  }
  
  @Deprecated
  public void setNativeBuffer(int paramInt, ByteBuffer paramByteBuffer)
  {
    byte[] arrayOfByte = new byte[paramByteBuffer.capacity()];
    paramByteBuffer.get(arrayOfByte);
    this.mNativeBufferCompat.setNativeBuffer(paramInt, arrayOfByte);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jscore\compat\X5JsCoreCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */