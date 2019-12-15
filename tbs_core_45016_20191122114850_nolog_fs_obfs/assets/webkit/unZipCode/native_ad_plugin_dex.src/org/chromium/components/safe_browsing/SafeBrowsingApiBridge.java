package org.chromium.components.safe_browsing;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.chromium.base.ContextUtils;
import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("safe_browsing")
public final class SafeBrowsingApiBridge
{
  private static Class<? extends SafeBrowsingApiHandler> a;
  
  @CalledByNative
  private static SafeBrowsingApiHandler create()
  {
    Object localObject1 = null;
    try
    {
      localObject2 = (SafeBrowsingApiHandler)a.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
      if (((SafeBrowsingApiHandler)localObject2).init(ContextUtils.getApplicationContext(), new SafeBrowsingApiHandler.Observer()
      {
        public void onUrlCheckDone(long paramAnonymousLong1, int paramAnonymousInt, String paramAnonymousString, long paramAnonymousLong2)
        {
          SafeBrowsingApiBridge.a(paramAnonymousLong1, paramAnonymousInt, paramAnonymousString, paramAnonymousLong2);
        }
      })) {
        localObject1 = localObject2;
      }
      return (SafeBrowsingApiHandler)localObject1;
    }
    catch (InvocationTargetException localInvocationTargetException) {}catch (NoSuchMethodException localNoSuchMethodException) {}catch (IllegalAccessException localIllegalAccessException) {}catch (InstantiationException localInstantiationException) {}catch (NullPointerException localNullPointerException) {}
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("Failed to init handler: ");
    ((StringBuilder)localObject2).append(localNullPointerException.getMessage());
    Log.e("ApiBridge", ((StringBuilder)localObject2).toString(), new Object[0]);
    return null;
  }
  
  private static native void nativeOnUrlCheckDone(long paramLong1, int paramInt, String paramString, long paramLong2);
  
  @CalledByNative
  private static void startUriLookup(SafeBrowsingApiHandler paramSafeBrowsingApiHandler, long paramLong, String paramString, int[] paramArrayOfInt)
  {
    paramSafeBrowsingApiHandler.startUriLookup(paramLong, paramString, paramArrayOfInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\safe_browsing\SafeBrowsingApiBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */