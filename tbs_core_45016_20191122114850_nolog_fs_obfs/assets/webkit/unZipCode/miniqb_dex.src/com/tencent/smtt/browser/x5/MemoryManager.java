package com.tencent.smtt.browser.x5;

import com.tencent.smtt.memory.MemoryChecker;
import org.chromium.base.annotations.UsedByReflection;

public class MemoryManager
{
  @UsedByReflection("WebCoreProxy.java")
  public static void CheckTrim(int paramInt)
  {
    MemoryChecker.trim(paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\browser\x5\MemoryManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */