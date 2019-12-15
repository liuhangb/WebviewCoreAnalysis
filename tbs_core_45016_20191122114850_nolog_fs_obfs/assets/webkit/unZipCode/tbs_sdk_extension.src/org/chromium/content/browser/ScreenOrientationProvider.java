package org.chromium.content.browser;

import android.app.Activity;
import android.content.Context;
import java.lang.ref.WeakReference;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content_public.browser.ScreenOrientationDelegate;
import org.chromium.ui.base.WindowAndroid;
import org.chromium.ui.display.DisplayAndroid;

@JNINamespace("content")
public class ScreenOrientationProvider
{
  private static ScreenOrientationDelegate a;
  
  private static int a(byte paramByte, WindowAndroid paramWindowAndroid, Context paramContext)
  {
    switch (paramByte)
    {
    default: 
      return -1;
    case 8: 
      if (paramWindowAndroid != null) {
        paramWindowAndroid = paramWindowAndroid.getDisplay();
      } else {
        paramWindowAndroid = DisplayAndroid.getNonMultiDisplay(paramContext);
      }
      paramByte = paramWindowAndroid.getRotation();
      if ((paramByte != 0) && (paramByte != 2))
      {
        if (paramWindowAndroid.getDisplayHeight() < paramWindowAndroid.getDisplayWidth()) {
          return 1;
        }
        return 0;
      }
      if (paramWindowAndroid.getDisplayHeight() >= paramWindowAndroid.getDisplayWidth()) {
        return 1;
      }
      return 0;
    case 7: 
      return 7;
    case 6: 
      return 6;
    case 5: 
      return 10;
    case 4: 
      return 8;
    case 3: 
      return 0;
    case 2: 
      return 9;
    case 1: 
      return 1;
    }
    return -1;
  }
  
  @CalledByNative
  static boolean isOrientationLockEnabled()
  {
    ScreenOrientationDelegate localScreenOrientationDelegate = a;
    return (localScreenOrientationDelegate == null) || (localScreenOrientationDelegate.canLockOrientation());
  }
  
  @CalledByNative
  public static void lockOrientation(WindowAndroid paramWindowAndroid, byte paramByte)
  {
    Object localObject = a;
    if ((localObject != null) && (!((ScreenOrientationDelegate)localObject).canLockOrientation())) {
      return;
    }
    if (paramWindowAndroid == null) {
      return;
    }
    localObject = (Activity)paramWindowAndroid.getActivity().get();
    if (localObject == null) {
      return;
    }
    int i = a(paramByte, paramWindowAndroid, (Context)localObject);
    if (i == -1) {
      return;
    }
    ((Activity)localObject).setRequestedOrientation(i);
  }
  
  public static void setOrientationDelegate(ScreenOrientationDelegate paramScreenOrientationDelegate)
  {
    a = paramScreenOrientationDelegate;
  }
  
  /* Error */
  @CalledByNative
  public static void unlockOrientation(WindowAndroid paramWindowAndroid)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +4 -> 5
    //   4: return
    //   5: aload_0
    //   6: invokevirtual 54	org/chromium/ui/base/WindowAndroid:getActivity	()Ljava/lang/ref/WeakReference;
    //   9: invokevirtual 60	java/lang/ref/WeakReference:get	()Ljava/lang/Object;
    //   12: checkcast 62	android/app/Activity
    //   15: astore 4
    //   17: aload 4
    //   19: ifnonnull +4 -> 23
    //   22: return
    //   23: aload 4
    //   25: invokevirtual 78	android/app/Activity:getIntent	()Landroid/content/Intent;
    //   28: ldc 80
    //   30: iconst_0
    //   31: invokevirtual 86	android/content/Intent:getIntExtra	(Ljava/lang/String;I)I
    //   34: i2b
    //   35: aload_0
    //   36: aload 4
    //   38: invokestatic 64	org/chromium/content/browser/ScreenOrientationProvider:a	(BLorg/chromium/ui/base/WindowAndroid;Landroid/content/Context;)I
    //   41: istore_2
    //   42: iload_2
    //   43: istore_1
    //   44: iload_2
    //   45: iconst_m1
    //   46: if_icmpne +85 -> 131
    //   49: aload 4
    //   51: invokevirtual 90	android/app/Activity:getPackageManager	()Landroid/content/pm/PackageManager;
    //   54: aload 4
    //   56: invokevirtual 94	android/app/Activity:getComponentName	()Landroid/content/ComponentName;
    //   59: sipush 128
    //   62: invokevirtual 100	android/content/pm/PackageManager:getActivityInfo	(Landroid/content/ComponentName;I)Landroid/content/pm/ActivityInfo;
    //   65: getfield 106	android/content/pm/ActivityInfo:screenOrientation	I
    //   68: istore_1
    //   69: goto +62 -> 131
    //   72: astore_0
    //   73: getstatic 42	org/chromium/content/browser/ScreenOrientationProvider:a	Lorg/chromium/content_public/browser/ScreenOrientationDelegate;
    //   76: astore 5
    //   78: aload 5
    //   80: ifnull +16 -> 96
    //   83: aload 5
    //   85: aload 4
    //   87: iload_2
    //   88: invokeinterface 110 3 0
    //   93: ifeq +9 -> 102
    //   96: aload 4
    //   98: iload_2
    //   99: invokevirtual 68	android/app/Activity:setRequestedOrientation	(I)V
    //   102: aload_0
    //   103: athrow
    //   104: getstatic 42	org/chromium/content/browser/ScreenOrientationProvider:a	Lorg/chromium/content_public/browser/ScreenOrientationDelegate;
    //   107: astore_0
    //   108: iload_2
    //   109: istore_3
    //   110: aload_0
    //   111: ifnull +44 -> 155
    //   114: aload_0
    //   115: aload 4
    //   117: iload_2
    //   118: invokeinterface 110 3 0
    //   123: ifeq +38 -> 161
    //   126: iload_2
    //   127: istore_3
    //   128: goto +27 -> 155
    //   131: getstatic 42	org/chromium/content/browser/ScreenOrientationProvider:a	Lorg/chromium/content_public/browser/ScreenOrientationDelegate;
    //   134: astore_0
    //   135: iload_1
    //   136: istore_3
    //   137: aload_0
    //   138: ifnull +17 -> 155
    //   141: aload_0
    //   142: aload 4
    //   144: iload_1
    //   145: invokeinterface 110 3 0
    //   150: ifeq +11 -> 161
    //   153: iload_1
    //   154: istore_3
    //   155: aload 4
    //   157: iload_3
    //   158: invokevirtual 68	android/app/Activity:setRequestedOrientation	(I)V
    //   161: return
    //   162: astore_0
    //   163: goto -59 -> 104
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	166	0	paramWindowAndroid	WindowAndroid
    //   43	111	1	i	int
    //   41	86	2	j	int
    //   109	49	3	k	int
    //   15	141	4	localActivity	Activity
    //   76	8	5	localScreenOrientationDelegate	ScreenOrientationDelegate
    // Exception table:
    //   from	to	target	type
    //   49	69	72	finally
    //   49	69	162	android/content/pm/PackageManager$NameNotFoundException
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\ScreenOrientationProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */