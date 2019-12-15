package org.chromium.content.browser.selection;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import org.chromium.base.ApiCompatibilityUtils;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("content")
public class HandleViewResources
{
  private static final int[] jdField_a_of_type_ArrayOfInt = { 16843461 };
  private static final int[] b = { 16843463 };
  private static final int[] c = { 16843462 };
  
  private static Bitmap a(Context paramContext, int[] paramArrayOfInt)
  {
    Object localObject1 = paramContext;
    if (paramContext == null) {
      localObject1 = ContextUtils.getApplicationContext();
    }
    paramContext = ((Context)localObject1).getTheme().obtainStyledAttributes(paramArrayOfInt);
    int i = paramContext.getResourceId(paramContext.getIndex(0), 0);
    Object localObject2 = paramContext.getResources();
    paramContext.recycle();
    paramContext = Bitmap.Config.ARGB_8888;
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inJustDecodeBounds = false;
    localOptions.inPreferredConfig = paramContext;
    Bitmap localBitmap = BitmapFactory.decodeResource((Resources)localObject2, i, localOptions);
    if (localBitmap != null) {
      return localBitmap;
    }
    if (localObject2 != ((Context)localObject1).getResources())
    {
      localObject2 = BitmapFactory.decodeResource(((Context)localObject1).getResources(), i, localOptions);
      if (localObject2 != null) {
        return (Bitmap)localObject2;
      }
    }
    paramArrayOfInt = a((Context)localObject1, paramArrayOfInt);
    if ((!jdField_a_of_type_Boolean) && (paramArrayOfInt == null)) {
      throw new AssertionError();
    }
    i = paramArrayOfInt.getIntrinsicWidth();
    int j = paramArrayOfInt.getIntrinsicHeight();
    paramContext = Bitmap.createBitmap(i, j, paramContext);
    localObject1 = new Canvas(paramContext);
    paramArrayOfInt.setBounds(0, 0, i, j);
    paramArrayOfInt.draw((Canvas)localObject1);
    return paramContext;
  }
  
  private static Drawable a(Context paramContext, int[] paramArrayOfInt)
  {
    TypedArray localTypedArray = paramContext.getTheme().obtainStyledAttributes(paramArrayOfInt);
    Drawable localDrawable = localTypedArray.getDrawable(0);
    paramArrayOfInt = localDrawable;
    if (localDrawable == null) {}
    try
    {
      paramArrayOfInt = ApiCompatibilityUtils.getDrawable(paramContext.getResources(), localTypedArray.getResourceId(0, 0));
      localTypedArray.recycle();
      return paramArrayOfInt;
    }
    catch (Resources.NotFoundException paramContext)
    {
      for (;;)
      {
        paramArrayOfInt = localDrawable;
      }
    }
  }
  
  @CalledByNative
  private static Bitmap getCenterHandleBitmap(Context paramContext)
  {
    return a(paramContext, b);
  }
  
  public static Drawable getCenterHandleDrawable(Context paramContext)
  {
    return a(paramContext, b);
  }
  
  @CalledByNative
  public static float getHandleHorizontalPaddingRatio()
  {
    return 0.25F;
  }
  
  @CalledByNative
  private static Bitmap getLeftHandleBitmap(Context paramContext)
  {
    return a(paramContext, jdField_a_of_type_ArrayOfInt);
  }
  
  public static Drawable getLeftHandleDrawable(Context paramContext)
  {
    return a(paramContext, jdField_a_of_type_ArrayOfInt);
  }
  
  @CalledByNative
  private static Bitmap getRightHandleBitmap(Context paramContext)
  {
    return a(paramContext, c);
  }
  
  public static Drawable getRightHandleDrawable(Context paramContext)
  {
    return a(paramContext, c);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\selection\HandleViewResources.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */