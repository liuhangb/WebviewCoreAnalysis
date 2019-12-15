package org.chromium.tencent;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import java.lang.reflect.Field;
import org.chromium.base.annotations.CalledByNative;

public class SystemTypeFace
{
  private static boolean DEBUG = false;
  private static long DEFAULT_BOLD = 0L;
  private static long DEFAULT_BOLD_ITALIC = getNativeInst(Typeface.defaultFromStyle(3));
  private static long DEFAULT_ITALIC = 0L;
  private static long DEFAULT_NORMAL = 0L;
  private static Paint DEFAULT_PAINT = new Paint();
  private static Paint DEFAULT_PAINT_FONT = new Paint();
  private static final String TAG = "SystemTypeFace";
  
  static
  {
    DEFAULT_NORMAL = getNativeInst(Typeface.defaultFromStyle(0));
    DEFAULT_BOLD = getNativeInst(Typeface.defaultFromStyle(1));
    DEFAULT_ITALIC = getNativeInst(Typeface.defaultFromStyle(2));
  }
  
  private static long getNativeInst(Typeface paramTypeface)
  {
    Object localObject = paramTypeface.getClass();
    try
    {
      localObject = ((Class)localObject).getDeclaredField("native_instance");
      ((Field)localObject).setAccessible(true);
      long l;
      if (Build.VERSION.SDK_INT >= 21) {
        l = ((Long)((Field)localObject).get(paramTypeface)).longValue();
      } else {
        l = ((Integer)((Field)localObject).get(paramTypeface)).intValue();
      }
      boolean bool = DEBUG;
      return l;
    }
    catch (IllegalAccessException paramTypeface)
    {
      paramTypeface.printStackTrace();
    }
    catch (IllegalArgumentException paramTypeface)
    {
      paramTypeface.printStackTrace();
    }
    catch (NoSuchFieldException paramTypeface)
    {
      paramTypeface.printStackTrace();
    }
    return 0L;
  }
  
  public static long getPaintNativeInst(Paint paramPaint)
  {
    Object localObject = paramPaint.getClass();
    try
    {
      localObject = ((Class)localObject).getDeclaredField("mNativePaint");
      ((Field)localObject).setAccessible(true);
      long l;
      if (Build.VERSION.SDK_INT >= 21) {
        l = ((Long)((Field)localObject).get(paramPaint)).longValue();
      } else {
        l = ((Integer)((Field)localObject).get(paramPaint)).intValue();
      }
      boolean bool = DEBUG;
      return l;
    }
    catch (IllegalAccessException paramPaint)
    {
      paramPaint.printStackTrace();
    }
    catch (IllegalArgumentException paramPaint)
    {
      paramPaint.printStackTrace();
    }
    catch (NoSuchFieldException paramPaint)
    {
      paramPaint.printStackTrace();
    }
    return 0L;
  }
  
  @CalledByNative
  public static long getPublicPaintNativeInst()
  {
    return getPaintNativeInst(DEFAULT_PAINT);
  }
  
  @CalledByNative
  public static long nextDynFallbackSkpaint(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return 0L;
    case 3: 
      DEFAULT_PAINT_FONT.setTypeface(Typeface.defaultFromStyle(3));
      DEFAULT_PAINT_FONT.ascent();
      return getPaintNativeInst(DEFAULT_PAINT_FONT);
    case 2: 
      DEFAULT_PAINT_FONT.setTypeface(Typeface.defaultFromStyle(2));
      DEFAULT_PAINT_FONT.ascent();
      return getPaintNativeInst(DEFAULT_PAINT_FONT);
    case 1: 
      DEFAULT_PAINT_FONT.setTypeface(Typeface.defaultFromStyle(1));
      DEFAULT_PAINT_FONT.ascent();
      return getPaintNativeInst(DEFAULT_PAINT_FONT);
    }
    DEFAULT_PAINT_FONT.setTypeface(Typeface.defaultFromStyle(0));
    DEFAULT_PAINT_FONT.ascent();
    return getPaintNativeInst(DEFAULT_PAINT_FONT);
  }
  
  @CalledByNative
  public static long nextDynFallbackTypeface(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return 0L;
    case 3: 
      return DEFAULT_BOLD_ITALIC;
    case 2: 
      return DEFAULT_ITALIC;
    case 1: 
      return DEFAULT_BOLD;
    }
    return DEFAULT_NORMAL;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\SystemTypeFace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */