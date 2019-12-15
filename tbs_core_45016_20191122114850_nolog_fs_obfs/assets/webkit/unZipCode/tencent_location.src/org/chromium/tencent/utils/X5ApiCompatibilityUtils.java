package org.chromium.tencent.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.media.audiofx.AcousticEchoCanceler;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import java.lang.reflect.Method;

public class X5ApiCompatibilityUtils
{
  public static final int HONEYCOMB = 11;
  public static final int HONEYCOMB_MR1 = 12;
  public static final int HONEYCOMB_MR2 = 13;
  public static final int ICE_CREAM_SANDWICH = 14;
  public static final int ICE_CREAM_SANDWICH_MR1 = 15;
  public static final int JELLY_BEAN = 16;
  public static final int JELLY_BEAN_MR1 = 17;
  public static final int JELLY_BEAN_MR2 = 18;
  public static final int KITKAT = 19;
  public static final int KITKAT_WATCH = 20;
  public static final int L = 21;
  public static final int LOLLIPOP = 21;
  public static final int LOLLIPOP_MR1 = 22;
  public static final int M = 23;
  public static final int N = 24;
  public static final int N_MR1 = 25;
  public static final int O = 26;
  public static final int O_MR1 = 27;
  private static final String TAG = "X5ApiCompatibilityUtils";
  private static Method sBluetoothManagerGetAdapterMethod;
  private static Method sViewIsAttachedToWindowMethod;
  
  static
  {
    Class[] arrayOfClass = (Class[])null;
    sBluetoothManagerGetAdapterMethod = getMethod(18, "android.bluetooth.BluetoothManager", "getAdapter", arrayOfClass);
    sViewIsAttachedToWindowMethod = getMethod(19, "android.view.View", "isAttachedToWindow", arrayOfClass);
  }
  
  public static ParcelFileDescriptor adoptFd(int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 13) {
      return ParcelFileDescriptor.adoptFd(paramInt);
    }
    return null;
  }
  
  public static void beginTransactionNonExclusive(SQLiteDatabase paramSQLiteDatabase)
  {
    if (paramSQLiteDatabase == null) {
      return;
    }
    if (Build.VERSION.SDK_INT < 14)
    {
      paramSQLiteDatabase.beginTransaction();
      return;
    }
    paramSQLiteDatabase.beginTransactionNonExclusive();
  }
  
  public static int detachFd(ParcelFileDescriptor paramParcelFileDescriptor)
  {
    if (Build.VERSION.SDK_INT >= 12) {
      return paramParcelFileDescriptor.detachFd();
    }
    return -1;
  }
  
  public static BluetoothAdapter getAdapter(Context paramContext)
  {
    if (sBluetoothManagerGetAdapterMethod != null) {
      try
      {
        paramContext = paramContext.getSystemService("bluetooth");
        paramContext = (BluetoothAdapter)sBluetoothManagerGetAdapterMethod.invoke(paramContext, (Object[])null);
        return paramContext;
      }
      catch (Exception paramContext)
      {
        paramContext.printStackTrace();
      }
    }
    return null;
  }
  
  public static int getMaximumBitmapHeight(Canvas paramCanvas)
  {
    if (Build.VERSION.SDK_INT >= 14) {
      return paramCanvas.getMaximumBitmapHeight();
    }
    return 32766;
  }
  
  public static int getMaximumBitmapWidth(Canvas paramCanvas)
  {
    if (Build.VERSION.SDK_INT >= 14) {
      return paramCanvas.getMaximumBitmapWidth();
    }
    return 32766;
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
  
  public static boolean invokeCanvasIsHardwareAccelerated(Canvas paramCanvas)
  {
    if (Build.VERSION.SDK_INT >= 11) {
      return paramCanvas.isHardwareAccelerated();
    }
    return false;
  }
  
  public static boolean isAcousticEchoCancelerAvailable()
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return AcousticEchoCanceler.isAvailable();
    }
    return false;
  }
  
  public static boolean isAttachedToWindow(View paramView)
  {
    boolean bool1;
    if (paramView.getWindowVisibility() != 8) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    Method localMethod = sViewIsAttachedToWindowMethod;
    if (localMethod != null) {
      try
      {
        boolean bool2 = ((Boolean)localMethod.invoke(paramView, (Object[])null)).booleanValue();
        return bool2;
      }
      catch (Exception paramView)
      {
        paramView.printStackTrace();
      }
    }
    return bool1;
  }
  
  public static boolean isHTMLClipboardSupported()
  {
    return Build.VERSION.SDK_INT >= 16;
  }
  
  public static boolean isLayoutRtl(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return paramView.getLayoutDirection() == 1;
    }
    return false;
  }
  
  public static void mustBeOverride()
  {
    throw new RuntimeException("this function must be overide");
  }
  
  public static void removeOnGlobalLayoutListener(View paramView, ViewTreeObserver.OnGlobalLayoutListener paramOnGlobalLayoutListener)
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      paramView.getViewTreeObserver().removeOnGlobalLayoutListener(paramOnGlobalLayoutListener);
      return;
    }
    paramView.getViewTreeObserver().removeGlobalOnLayoutListener(paramOnGlobalLayoutListener);
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
  
  public static int smallestScreenWidthDp(Configuration paramConfiguration)
  {
    if (Build.VERSION.SDK_INT >= 13) {
      return paramConfiguration.smallestScreenWidthDp;
    }
    return 0;
  }
  
  public static Intent x5RegisterReceiver(Context paramContext, BroadcastReceiver paramBroadcastReceiver, IntentFilter paramIntentFilter)
  {
    return paramContext.registerReceiver(paramBroadcastReceiver, paramIntentFilter);
  }
  
  public static Intent x5RegisterReceiver(Context paramContext, BroadcastReceiver paramBroadcastReceiver, IntentFilter paramIntentFilter, String paramString, Handler paramHandler)
  {
    return paramContext.registerReceiver(paramBroadcastReceiver, paramIntentFilter, paramString, paramHandler);
  }
  
  public static void x5UnRegisterReceiver(Context paramContext, BroadcastReceiver paramBroadcastReceiver)
  {
    paramContext.unregisterReceiver(paramBroadcastReceiver);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\utils\X5ApiCompatibilityUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */