package org.chromium.mojo.bindings;

import org.chromium.mojo.system.Core;
import org.chromium.mojo.system.Handle;
import org.chromium.mojo.system.Watcher;

public class BindingsHelper
{
  public static final DataHeader a = new DataHeader(24, 0);
  
  public static int a(double paramDouble)
  {
    return a(Double.doubleToLongBits(paramDouble));
  }
  
  public static int a(int paramInt)
  {
    return paramInt + 8 - 1 & 0xFFFFFFF8;
  }
  
  public static int a(long paramLong)
  {
    return (int)(paramLong ^ paramLong >>> 32);
  }
  
  public static int a(Object paramObject)
  {
    if (paramObject == null) {
      return 0;
    }
    return paramObject.hashCode();
  }
  
  public static int a(boolean paramBoolean)
  {
    if (paramBoolean) {
      return 1231;
    }
    return 1237;
  }
  
  public static long a(long paramLong)
  {
    return paramLong + 8L - 1L & 0xFFFFFFFFFFFFFFF8;
  }
  
  static Watcher a(Handle paramHandle)
  {
    if (paramHandle.getCore() != null) {
      return paramHandle.getCore().getWatcher();
    }
    return null;
  }
  
  public static boolean a(int paramInt)
  {
    return (paramInt & 0x1) > 0;
  }
  
  public static boolean a(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == paramObject2) {
      return true;
    }
    if (paramObject1 == null) {
      return false;
    }
    return paramObject1.equals(paramObject2);
  }
  
  public static int b(int paramInt)
  {
    return paramInt;
  }
  
  public static boolean b(int paramInt)
  {
    return (paramInt & 0x2) > 0;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\BindingsHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */