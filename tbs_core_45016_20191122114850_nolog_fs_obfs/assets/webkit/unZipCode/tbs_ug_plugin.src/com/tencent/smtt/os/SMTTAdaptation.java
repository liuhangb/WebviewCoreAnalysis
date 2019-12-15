package com.tencent.smtt.os;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.tencent.smtt.util.X5Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SMTTAdaptation
{
  public static final int SDK_10 = 1;
  public static final int SDK_100 = 29;
  public static final int SDK_11 = 2;
  public static final int SDK_15 = 3;
  public static final int SDK_16 = 4;
  public static final int SDK_20 = 5;
  public static final int SDK_201 = 6;
  public static final int SDK_21 = 7;
  public static final int SDK_22 = 8;
  public static final int SDK_232 = 9;
  public static final int SDK_234 = 10;
  public static final int SDK_30 = 11;
  public static final int SDK_31 = 12;
  public static final int SDK_32 = 13;
  public static final int SDK_402 = 14;
  public static final int SDK_404 = 15;
  public static final int SDK_41 = 16;
  public static final int SDK_42 = 17;
  public static final int SDK_43 = 18;
  public static final int SDK_44 = 19;
  public static final int SDK_50 = 21;
  public static final int SDK_51 = 22;
  public static final int SDK_60 = 23;
  public static final int SDK_70 = 24;
  public static final int SDK_90 = 28;
  private static final String TAG = "SMTTAdaptation";
  private static Method sBluetoothManagerGetAdapterMethod;
  private static Method sDisplayListCanvasCallDrawGLFunction2JMethod;
  private static Method sDisplayListCanvasDrawGLFunctor2Method;
  private static Method sDrawFunctorGetFunctionTableMethod;
  private static Method sHardwareCanvasCallDrawGLFunction2JMethod;
  private static Method sHardwareCanvasCallDrawGLFunctionIMethod;
  private static Method sHardwareCanvasCallDrawGLFunctionJMethod;
  private static boolean sHasGot = false;
  private static boolean sIsBuildForArch64 = false;
  private static Method sSurfaceTextureReleaseTexImageMethod;
  private static Method sViewExecuteHardwareActionMethod = getMethod(19, "android.view.View", "executeHardwareAction", new Class[] { Runnable.class });
  private static Method sViewIsAttachedToWindowMethod;
  private static Method sViewRootImplAttachFunctorMethod = getMethod(16, "android.view.ViewRootImpl", "attachFunctor", new Class[] { Integer.TYPE });
  private static Method sViewRootImplDetachFunctorIMethod;
  private static Method sViewRootImplDetachFunctorJMethod;
  private static Method sViewRootImplInvokeFunctorMethod = getMethod(21, "android.view.ViewRootImpl", "invokeFunctor", new Class[] { Long.TYPE, Boolean.TYPE });
  private static Object sWebViewDelegate;
  private static Method sWebViewDelegateCallDrawGlFunction;
  private static Method sWebViewDelegateDrawWebViewFunctorMethod;
  
  static
  {
    sViewRootImplDetachFunctorIMethod = getMethod(16, "android.view.ViewRootImpl", "detachFunctor", new Class[] { Integer.TYPE });
    sViewRootImplDetachFunctorJMethod = getMethod(21, "android.view.ViewRootImpl", "detachFunctor", new Class[] { Long.TYPE });
    sHardwareCanvasCallDrawGLFunctionIMethod = getMethod(11, "android.view.HardwareCanvas", "callDrawGLFunction", new Class[] { Integer.TYPE });
    sHardwareCanvasCallDrawGLFunctionJMethod = getMethod(21, "android.view.HardwareCanvas", "callDrawGLFunction", new Class[] { Long.TYPE });
    sHardwareCanvasCallDrawGLFunction2JMethod = getMethod(22, "android.view.HardwareCanvas", "callDrawGLFunction2", new Class[] { Long.TYPE });
    sDisplayListCanvasCallDrawGLFunction2JMethod = getMethod(22, "android.view.DisplayListCanvas", "callDrawGLFunction2", new Class[] { Long.TYPE });
    sDisplayListCanvasDrawGLFunctor2Method = getMethod(24, "android.view.DisplayListCanvas", "drawGLFunctor2", new Class[] { Long.TYPE, Runnable.class });
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("getWebViewDelegateFunction Build.VERSION.RELEASE=");
    ((StringBuilder)localObject1).append(Build.VERSION.RELEASE);
    ((StringBuilder)localObject1).append(", Build.VERSION.SDK_INT=");
    ((StringBuilder)localObject1).append(Build.VERSION.SDK_INT);
    sRecord("SMTTAdaptation", ((StringBuilder)localObject1).toString());
    if ((Build.VERSION.SDK_INT >= 29) && (UpdateWebViewDelegate()))
    {
      Object localObject2 = Long.TYPE;
      localObject1 = Integer.TYPE;
      try
      {
        sWebViewDelegateCallDrawGlFunction = Class.forName("android.webkit.WebViewDelegate").getMethod("callDrawGlFunction", new Class[] { Canvas.class, localObject2, Runnable.class });
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("getWebViewDelegateFunction sWebViewDelegateCallDrawGlFunction=");
        ((StringBuilder)localObject2).append(sWebViewDelegateCallDrawGlFunction);
        sRecord("SMTTAdaptation", ((StringBuilder)localObject2).toString());
        sWebViewDelegateCallDrawGlFunction.setAccessible(true);
        sWebViewDelegateDrawWebViewFunctorMethod = Class.forName("android.webkit.WebViewDelegate").getMethod("drawWebViewFunctor", new Class[] { Canvas.class, localObject1 });
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("getWebViewDelegateFunction sWebViewDelegateDrawWebViewFunctorMethod=");
        ((StringBuilder)localObject1).append(sWebViewDelegateDrawWebViewFunctorMethod);
        sRecord("SMTTAdaptation", ((StringBuilder)localObject1).toString());
        sWebViewDelegateDrawWebViewFunctorMethod.setAccessible(true);
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("getWebViewDelegateFunction Exception_2 e=");
        ((StringBuilder)localObject2).append(localClassNotFoundException.toString());
        sRecord("SMTTAdaptation", ((StringBuilder)localObject2).toString());
        org.chromium.tencent.TencentAwContent.ENABLE_SW_DRAW = true;
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("getWebViewDelegateFunction Exception_1 e=");
        ((StringBuilder)localObject2).append(localNoSuchMethodException.toString());
        sRecord("SMTTAdaptation", ((StringBuilder)localObject2).toString());
        org.chromium.tencent.TencentAwContent.ENABLE_SW_DRAW = true;
      }
    }
    else
    {
      sRecord("SMTTAdaptation", "getWebViewDelegateFunction ALL_NULL");
      sWebViewDelegateCallDrawGlFunction = null;
      sWebViewDelegateDrawWebViewFunctorMethod = null;
    }
    Class[] arrayOfClass = (Class[])null;
    sBluetoothManagerGetAdapterMethod = getMethod(18, "android.bluetooth.BluetoothManager", "getAdapter", arrayOfClass);
    sSurfaceTextureReleaseTexImageMethod = getMethod(19, "android.graphics.SurfaceTexture", "releaseTexImage", arrayOfClass);
    sViewIsAttachedToWindowMethod = getMethod(19, "android.view.View", "isAttachedToWindow", arrayOfClass);
  }
  
  public static long GetFunctionTable()
  {
    Method localMethod = sDrawFunctorGetFunctionTableMethod;
    if (localMethod != null) {
      try
      {
        long l = ((Long)localMethod.invoke(null, new Object[0])).longValue();
        return l;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    return 0L;
  }
  
  private static boolean UpdateWebViewDelegate()
  {
    try
    {
      Object localObject1 = Class.class.getDeclaredMethod("getDeclaredConstructor", new Class[] { Class[].class });
      ((Method)localObject1).setAccessible(true);
      localObject1 = (Constructor)((Method)localObject1).invoke(Class.forName("android.webkit.WebViewDelegate"), new Class[] { null });
      ((Constructor)localObject1).setAccessible(true);
      sWebViewDelegate = ((Constructor)localObject1).newInstance(new Object[0]);
      if (sWebViewDelegate != null)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("UpdateWebViewDelegate TRUE Direct_Construct sWebViewDelegate=");
        ((StringBuilder)localObject1).append(sWebViewDelegate);
        sRecord("SMTTAdaptation", ((StringBuilder)localObject1).toString());
        return true;
      }
    }
    catch (Exception localException1)
    {
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("UpdateWebViewDelegate Direct_Construct e=");
      ((StringBuilder)localObject3).append(localException1.toString());
      sRecord("SMTTAdaptation", ((StringBuilder)localObject3).toString());
    }
    try
    {
      localObject2 = Class.forName("android.webkit.WebView").getDeclaredMethod("getFactory", new Class[0]);
      ((Method)localObject2).setAccessible(true);
      Object localObject4 = ((Method)localObject2).invoke(null, new Object[0]);
      localObject2 = Class.forName("android.webkit.WebViewFactoryProvider").getDeclaredMethod("getWebViewClassLoader", new Class[0]);
      ((Method)localObject2).setAccessible(true);
      localObject2 = (ClassLoader)((Method)localObject2).invoke(localObject4, new Object[0]);
      sDrawFunctorGetFunctionTableMethod = ((ClassLoader)localObject2).loadClass("com.android.webview.chromium.DrawFunctor").getDeclaredMethod("nativeGetFunctionTable", new Class[0]);
      sDrawFunctorGetFunctionTableMethod.setAccessible(true);
      localClass = Class.forName("android.webkit.WebViewDelegate");
      ArrayList localArrayList1 = new ArrayList();
      localArrayList1.addAll(Arrays.asList(((ClassLoader)localObject2).loadClass("com.android.webview.chromium.WebViewChromiumFactoryProvider").getFields()));
      localArrayList1.addAll(Arrays.asList(((ClassLoader)localObject2).loadClass("com.android.webview.chromium.WebViewChromiumFactoryProvider").getDeclaredFields()));
      i = 0;
      if (i >= localArrayList1.size()) {
        break label599;
      }
      ((Field)localArrayList1.get(i)).setAccessible(true);
      if (Modifier.isStatic(((Field)localArrayList1.get(i)).getModifiers())) {
        localObject2 = ((Field)localArrayList1.get(i)).get(null);
      } else {
        localObject2 = ((Field)localArrayList1.get(i)).get(localObject4);
      }
    }
    catch (Exception localException2)
    {
      Object localObject2;
      Class localClass;
      int i;
      label377:
      ArrayList localArrayList2;
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("UpdateWebViewDelegate SysClass_Fields e=");
      ((StringBuilder)localObject3).append(localException2.toString());
      sRecord("SMTTAdaptation", ((StringBuilder)localObject3).toString());
      sRecord("SMTTAdaptation", "UpdateWebViewDelegate FALSE");
      return false;
    }
    localArrayList2 = new ArrayList();
    Object localObject3 = localObject2.getClass();
    label418:
    int j;
    for (;;)
    {
      if (localObject3 != null)
      {
        localArrayList2.addAll(Arrays.asList(((Class)localObject3).getDeclaredFields()));
        localObject3 = ((Class)localObject3).getSuperclass();
        continue;
        if (j < localArrayList2.size())
        {
          ((Field)localArrayList2.get(j)).setAccessible(true);
          if (Modifier.isStatic(((Field)localArrayList2.get(j)).getModifiers()))
          {
            localObject3 = ((Field)localArrayList2.get(j)).get(null);
            break label621;
          }
          localObject3 = ((Field)localArrayList2.get(j)).get(localObject2);
          break label621;
          label502:
          if (localObject3.getClass() == localClass)
          {
            sWebViewDelegate = localObject3;
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("UpdateWebViewDelegate TRUE SysClass_Fields sWebViewDelegate=");
            ((StringBuilder)localObject2).append(sWebViewDelegate);
            sRecord("SMTTAdaptation", ((StringBuilder)localObject2).toString());
            return true;
          }
        }
      }
    }
    for (;;)
    {
      j += 1;
      break label418;
      for (;;)
      {
        i += 1;
        break;
        label599:
        if (localException2 != null) {
          break label377;
        }
      }
      j = 0;
      break label418;
      label621:
      if (localObject3 != null) {
        break label502;
      }
    }
  }
  
  public static int callDrawGLFunction(Object paramObject, long paramLong)
  {
    Method localMethod = sHardwareCanvasCallDrawGLFunctionIMethod;
    if (localMethod != null)
    {
      try
      {
        paramObject = localMethod.invoke(paramObject, new Object[] { Integer.valueOf((int)paramLong) });
      }
      catch (Exception paramObject)
      {
        ((Exception)paramObject).printStackTrace();
        break label154;
      }
    }
    else
    {
      localMethod = sHardwareCanvasCallDrawGLFunctionJMethod;
      if (localMethod != null)
      {
        try
        {
          paramObject = localMethod.invoke(paramObject, new Object[] { Long.valueOf(paramLong) });
        }
        catch (Exception paramObject)
        {
          ((Exception)paramObject).printStackTrace();
          break label154;
        }
      }
      else
      {
        localMethod = sHardwareCanvasCallDrawGLFunction2JMethod;
        if (localMethod != null)
        {
          try
          {
            paramObject = localMethod.invoke(paramObject, new Object[] { Long.valueOf(paramLong) });
          }
          catch (Exception paramObject)
          {
            ((Exception)paramObject).printStackTrace();
            break label154;
          }
        }
        else
        {
          localMethod = sDisplayListCanvasCallDrawGLFunction2JMethod;
          if (localMethod != null) {
            try
            {
              paramObject = localMethod.invoke(paramObject, new Object[] { Long.valueOf(paramLong) });
            }
            catch (Exception paramObject)
            {
              ((Exception)paramObject).printStackTrace();
            }
          }
        }
      }
    }
    label154:
    paramObject = null;
    boolean bool = paramObject instanceof Boolean;
    int i = 2;
    if (bool)
    {
      if (((Boolean)paramObject).booleanValue()) {
        i = 0;
      }
      return i;
    }
    if ((paramObject instanceof Integer)) {
      return ((Integer)paramObject).intValue();
    }
    return 2;
  }
  
  public static void detachFunctor(Object paramObject, long paramLong)
  {
    Method localMethod = sViewRootImplDetachFunctorIMethod;
    if (localMethod != null) {
      try
      {
        localMethod.invoke(paramObject, new Object[] { Integer.valueOf((int)paramLong) });
        return;
      }
      catch (Exception paramObject)
      {
        ((Exception)paramObject).printStackTrace();
        return;
      }
    }
    localMethod = sViewRootImplDetachFunctorJMethod;
    if (localMethod != null) {
      try
      {
        localMethod.invoke(paramObject, new Object[] { Long.valueOf(paramLong) });
        return;
      }
      catch (Exception paramObject)
      {
        ((Exception)paramObject).printStackTrace();
      }
    }
  }
  
  public static void drawGLFunctor2(Object paramObject, long paramLong, Runnable paramRunnable)
  {
    Method localMethod = sDisplayListCanvasDrawGLFunctor2Method;
    if (localMethod != null) {
      try
      {
        localMethod.invoke(paramObject, new Object[] { Long.valueOf(paramLong), paramRunnable });
        return;
      }
      catch (Exception paramObject)
      {
        ((Exception)paramObject).printStackTrace();
        return;
      }
    }
    localMethod = sWebViewDelegateCallDrawGlFunction;
    if (localMethod != null)
    {
      Object localObject = sWebViewDelegate;
      if (localObject != null) {
        try
        {
          localMethod.invoke(localObject, new Object[] { paramObject, Long.valueOf(paramLong), paramRunnable });
          return;
        }
        catch (Exception paramObject)
        {
          ((Exception)paramObject).printStackTrace();
          return;
        }
      }
    }
    sRecord("SMTTAdaptation", "drawGLFunctor2 ENABLE_SW_DRAW");
    org.chromium.tencent.TencentAwContent.ENABLE_SW_DRAW = true;
  }
  
  public static void drawWebViewFunctor(Object paramObject, int paramInt)
  {
    Method localMethod = sWebViewDelegateDrawWebViewFunctorMethod;
    if (localMethod != null)
    {
      Object localObject = sWebViewDelegate;
      if (localObject != null) {
        try
        {
          localMethod.invoke(localObject, new Object[] { paramObject, Integer.valueOf(paramInt) });
          return;
        }
        catch (Exception paramObject)
        {
          ((Exception)paramObject).printStackTrace();
          return;
        }
      }
    }
    sRecord("SMTTAdaptation", "drawWebViewFunctor ENABLE_SW_DRAW");
    org.chromium.tencent.TencentAwContent.ENABLE_SW_DRAW = true;
  }
  
  public static Bitmap getBitmap(Canvas paramCanvas)
  {
    if (paramCanvas == null) {
      return null;
    }
    try
    {
      Field localField = Canvas.class.getDeclaredField("mBitmap");
      localField.setAccessible(true);
      paramCanvas = (Bitmap)localField.get(paramCanvas);
      return paramCanvas;
    }
    catch (Throwable paramCanvas)
    {
      paramCanvas.printStackTrace();
    }
    return null;
  }
  
  public static InputMethodManager getInputMethodManager(Context paramContext)
  {
    int i = Build.VERSION.SDK_INT;
    return (InputMethodManager)paramContext.getSystemService("input_method");
  }
  
  public static boolean getIsBuildForArch64()
  {
    if (!sHasGot)
    {
      sIsBuildForArch64 = nativeGetIsBuildForArch64();
      sHasGot = true;
    }
    return sIsBuildForArch64;
  }
  
  public static KeyCharacterMap getKeyCharacterMap()
  {
    if (Build.VERSION.SDK_INT < 11) {
      return KeyCharacterMap.load(0);
    }
    if (Build.VERSION.SDK_INT <= 17) {
      return KeyCharacterMap.load(-1);
    }
    return null;
  }
  
  private static Method getMethod(int paramInt, String paramString1, String paramString2, Class<?>[] paramArrayOfClass)
  {
    if (Build.VERSION.SDK_INT < paramInt) {
      return null;
    }
    try
    {
      paramString1 = Class.forName(paramString1).getMethod(paramString2, paramArrayOfClass);
      return paramString1;
    }
    catch (NoSuchMethodException|ClassNotFoundException paramString1) {}
    return null;
  }
  
  public static int getScreenWidth(Context paramContext)
  {
    try
    {
      int i = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getWidth();
      return i;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return 0;
  }
  
  public static boolean invokeCanvasIsHardwareAccelerated(Canvas paramCanvas)
  {
    if (Build.VERSION.SDK_INT >= 11) {
      return paramCanvas.isHardwareAccelerated();
    }
    return false;
  }
  
  public static void invokeFunctor(Object paramObject, long paramLong, boolean paramBoolean)
  {
    Method localMethod;
    if (Build.VERSION.SDK_INT >= 21)
    {
      localMethod = sViewRootImplInvokeFunctorMethod;
      if (localMethod == null) {
        return;
      }
      try
      {
        localMethod.invoke(paramObject, new Object[] { Long.valueOf(paramLong), Boolean.valueOf(paramBoolean) });
        return;
      }
      catch (Exception paramObject)
      {
        ((Exception)paramObject).printStackTrace();
        return;
      }
    }
    if (Build.VERSION.SDK_INT >= 16)
    {
      localMethod = sViewRootImplAttachFunctorMethod;
      if (localMethod != null) {
        try
        {
          localMethod.invoke(paramObject, new Object[] { Integer.valueOf((int)paramLong) });
          return;
        }
        catch (Exception paramObject)
        {
          ((Exception)paramObject).printStackTrace();
        }
      }
    }
  }
  
  private static native boolean nativeGetIsBuildForArch64();
  
  private static void sRecord(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString1);
    localStringBuilder.append(" ");
    localStringBuilder.append(paramString2);
    X5Log.i("RENDER", localStringBuilder.toString());
  }
  
  public static void setTranslationY(View paramView, float paramFloat)
  {
    if (paramView == null) {
      return;
    }
    if (Build.VERSION.SDK_INT < 11)
    {
      Object localObject = paramView.getTag();
      if ((localObject != null) && ((localObject instanceof Integer))) {
        paramView.offsetTopAndBottom(((Integer)localObject).intValue() + (int)paramFloat - paramView.getTop());
      }
    }
    else
    {
      paramView.setTranslationY(paramFloat);
    }
  }
  
  public static boolean viewExecuteHardwareAction(View paramView, Runnable paramRunnable)
  {
    Method localMethod = sViewExecuteHardwareActionMethod;
    if (localMethod == null) {
      return false;
    }
    try
    {
      boolean bool = ((Boolean)localMethod.invoke(paramView, new Object[] { paramRunnable })).booleanValue();
      return bool;
    }
    catch (Exception paramView)
    {
      paramView.printStackTrace();
    }
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\os\SMTTAdaptation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */