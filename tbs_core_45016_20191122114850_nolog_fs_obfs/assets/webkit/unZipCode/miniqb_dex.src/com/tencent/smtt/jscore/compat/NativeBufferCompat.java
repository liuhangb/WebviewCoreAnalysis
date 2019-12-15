package com.tencent.smtt.jscore.compat;

import android.webkit.JavascriptInterface;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class NativeBufferCompat
{
  public static final String NATIVE_BUFFER_SCRIPT = "function getNativeBufferId() {   if (nativeBufferCompat) {       return nativeBufferCompat.getNativeBufferId();   }   return -1;}function setNativeBuffer(id, bytes) {   if (nativeBufferCompat) {       return nativeBufferCompat.setNativeBuffer(id, bytes);   }}function getNativeBuffer(id) {   if (nativeBufferCompat) {       return nativeBufferCompat.getNativeBuffer(id);   }}";
  private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
  private HashMap<Integer, byte[]> mBuffers = new HashMap();
  
  public void destroy()
  {
    this.mBuffers.clear();
  }
  
  @JavascriptInterface
  public byte[] getNativeBuffer(int paramInt)
  {
    return (byte[])this.mBuffers.remove(Integer.valueOf(paramInt));
  }
  
  @JavascriptInterface
  public int getNativeBufferId()
  {
    int k;
    int i;
    do
    {
      k = sNextGeneratedId.get();
      int j = k + 1;
      i = j;
      if (j > 16777215) {
        i = 1;
      }
    } while (!sNextGeneratedId.compareAndSet(k, i));
    return k;
  }
  
  @JavascriptInterface
  public void setNativeBuffer(int paramInt, byte[] paramArrayOfByte)
  {
    this.mBuffers.put(Integer.valueOf(paramInt), paramArrayOfByte);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jscore\compat\NativeBufferCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */