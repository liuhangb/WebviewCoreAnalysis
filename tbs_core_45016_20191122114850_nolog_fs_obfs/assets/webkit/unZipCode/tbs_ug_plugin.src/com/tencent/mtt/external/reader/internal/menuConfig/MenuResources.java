package com.tencent.mtt.external.reader.internal.menuConfig;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.NinePatchDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.LongSparseArray;
import android.view.Display;
import android.view.WindowManager;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.resources.PluginContextWrapper;
import com.tencent.tbs.common.resources.TBSResources;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class MenuResources
{
  public static float DEVICE_DENSITY = -1.0F;
  public static final String TYPE_ANIM = "anim";
  public static final String TYPE_ARRAY = "array";
  public static final String TYPE_ATTR = "attr";
  public static final String TYPE_COLOR = "color";
  public static final String TYPE_DIMEN = "dimen";
  public static final String TYPE_DRAWABLE = "drawable";
  public static final String TYPE_ID = "id";
  public static final String TYPE_LAYOUT = "layout";
  public static final String TYPE_PLURALS = "plurals";
  public static final String TYPE_STRING = "string";
  public static final String TYPE_STYLE = "style";
  private static Context jdField_a_of_type_AndroidContentContext = null;
  private static Resources jdField_a_of_type_AndroidContentResResources;
  private static final LongSparseArray<WeakReference<Drawable.ConstantState>> jdField_a_of_type_AndroidUtilLongSparseArray = new LongSparseArray();
  private static PluginContextWrapper jdField_a_of_type_ComTencentTbsCommonResourcesPluginContextWrapper;
  private static Map<String, Integer> jdField_a_of_type_JavaUtilMap = new HashMap();
  
  static float a()
  {
    WindowManager localWindowManager = (WindowManager)jdField_a_of_type_AndroidContentContext.getSystemService("window");
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
    return localDisplayMetrics.density;
  }
  
  static int a(String paramString1, String paramString2)
  {
    checkInit();
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append("/");
    ((StringBuilder)localObject).append(paramString1);
    localObject = ((StringBuilder)localObject).toString();
    if (jdField_a_of_type_JavaUtilMap.containsKey(localObject)) {
      return ((Integer)jdField_a_of_type_JavaUtilMap.get(localObject)).intValue();
    }
    int i = jdField_a_of_type_AndroidContentResResources.getIdentifier(paramString1, paramString2, jdField_a_of_type_ComTencentTbsCommonResourcesPluginContextWrapper.getPackageName());
    if (i != 0)
    {
      jdField_a_of_type_JavaUtilMap.put(localObject, Integer.valueOf(i));
      return i;
    }
    throw new NullPointerException(paramString1);
  }
  
  static Drawable a(Bitmap paramBitmap, byte[] paramArrayOfByte, Rect paramRect, String paramString)
  {
    if (paramArrayOfByte != null) {
      return new NinePatchDrawable(jdField_a_of_type_AndroidContentResResources, paramBitmap, paramArrayOfByte, paramRect, paramString);
    }
    return new BitmapDrawable(jdField_a_of_type_AndroidContentResResources, paramBitmap);
  }
  
  static Drawable a(LongSparseArray<WeakReference<Drawable.ConstantState>> paramLongSparseArray, long paramLong)
  {
    Object localObject = (WeakReference)paramLongSparseArray.get(paramLong);
    if (localObject != null)
    {
      localObject = (Drawable.ConstantState)((WeakReference)localObject).get();
      if (localObject != null) {
        return ((Drawable.ConstantState)localObject).newDrawable(jdField_a_of_type_AndroidContentResResources);
      }
      paramLongSparseArray.delete(paramLong);
    }
    return null;
  }
  
  public static void checkInit()
  {
    if (jdField_a_of_type_AndroidContentResResources != null) {
      return;
    }
    throw new NullPointerException("TesResourceIsNotInit and u need call rice");
  }
  
  public static Bitmap decodeResource(String paramString, BitmapFactory.Options paramOptions)
  {
    checkInit();
    paramOptions = BitmapFactory.decodeResource(jdField_a_of_type_AndroidContentResResources, a(paramString, "drawable"), paramOptions);
    if (paramOptions != null) {
      return paramOptions;
    }
    paramOptions = new StringBuilder();
    paramOptions.append("decodeResource, id=");
    paramOptions.append(paramString);
    LogUtils.d("MenuResources", paramOptions.toString());
    return null;
  }
  
  public static Bitmap getBitmap(String paramString)
  {
    Object localObject2;
    try
    {
      Object localObject1 = new BitmapFactory.Options();
      ((BitmapFactory.Options)localObject1).inScreenDensity = ((int)DEVICE_DENSITY);
      localObject1 = decodeResource(paramString, (BitmapFactory.Options)localObject1);
    }
    catch (Throwable localThrowable)
    {
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("getColor, e=");
      ((StringBuilder)localObject3).append(localThrowable.getMessage());
      LogUtils.d("MenuResources", ((StringBuilder)localObject3).toString());
      localObject2 = null;
    }
    Object localObject3 = localObject2;
    if (localObject2 == null) {
      localObject3 = TBSResources.getBitmap(paramString);
    }
    return (Bitmap)localObject3;
  }
  
  public static int getColor(String paramString)
  {
    try
    {
      checkInit();
      int i = jdField_a_of_type_AndroidContentResResources.getColor(a(paramString, "color"));
      return i;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder;
      for (;;) {}
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("getColor, id=");
    localStringBuilder.append(paramString);
    LogUtils.d("MenuResources", localStringBuilder.toString());
    return TBSResources.getColor(paramString);
  }
  
  public static int getDimensionPixelSize(String paramString)
  {
    try
    {
      checkInit();
      int i = jdField_a_of_type_AndroidContentResResources.getDimensionPixelOffset(a(paramString, "dimen"));
      return i;
    }
    catch (Resources.NotFoundException localNotFoundException)
    {
      StringBuilder localStringBuilder;
      for (;;) {}
    }
    catch (NullPointerException localNullPointerException)
    {
      label81:
      for (;;) {}
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("loadIdentifierResource not found, id=");
    localStringBuilder.append(paramString);
    LogUtils.d("MenuResources", localStringBuilder.toString());
    break label81;
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("NotFoundException, getDimensionPixelSize, id=");
    localStringBuilder.append(paramString);
    LogUtils.d("MenuResources", localStringBuilder.toString());
    return TBSResources.getDimensionPixelSize(paramString);
  }
  
  public static Drawable getDrawable(String paramString)
  {
    for (;;)
    {
      try
      {
        checkInit();
        int i = a(paramString, "drawable");
        Object localObject1 = jdField_a_of_type_AndroidUtilLongSparseArray;
        long l = i;
        localObject1 = a((LongSparseArray)localObject1, l);
        if (localObject1 != null)
        {
          LogUtils.d("MenuResources", "getDrawableFromCache");
          return (Drawable)localObject1;
        }
        localObject3 = new Rect();
        Object localObject4 = new BitmapFactory.Options();
        ((BitmapFactory.Options)localObject4).inScreenDensity = ((int)DEVICE_DENSITY);
        Bitmap localBitmap = decodeResource(paramString, (BitmapFactory.Options)localObject4);
        if (localBitmap != null)
        {
          localObject4 = localBitmap.getNinePatchChunk();
          if (localObject4 != null)
          {
            localObject1 = localObject4;
            if (NinePatch.isNinePatchChunk((byte[])localObject4))
            {
              localObject1 = a(localBitmap, (byte[])localObject1, (Rect)localObject3, paramString);
              jdField_a_of_type_AndroidUtilLongSparseArray.put(l, new WeakReference(((Drawable)localObject1).getConstantState()));
            }
          }
        }
        else
        {
          return (Drawable)localObject1;
        }
      }
      catch (Throwable localThrowable)
      {
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("getDrawable, e=");
        ((StringBuilder)localObject3).append(localThrowable.getMessage());
        LogUtils.d("MenuResources", ((StringBuilder)localObject3).toString());
        return TBSResources.getDrawable(paramString);
      }
      Object localObject2 = null;
      Object localObject3 = localObject2;
    }
  }
  
  public static String getString(String paramString)
  {
    try
    {
      checkInit();
      localObject = jdField_a_of_type_AndroidContentResResources.getString(a(paramString, "string"));
      return (String)localObject;
    }
    catch (Resources.NotFoundException localNotFoundException)
    {
      Object localObject;
      for (;;) {}
    }
    catch (NullPointerException localNullPointerException)
    {
      label83:
      for (;;) {}
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("getString loadIdentifierResource not found, id=");
    ((StringBuilder)localObject).append(paramString);
    LogUtils.d("MenuResources", ((StringBuilder)localObject).toString());
    break label83;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("NotFoundException, getString, id=");
    ((StringBuilder)localObject).append(paramString);
    LogUtils.d("MenuResources", ((StringBuilder)localObject).toString());
    return TBSResources.getString(paramString);
  }
  
  public static String[] getStringArray(String paramString)
  {
    try
    {
      checkInit();
      localObject = jdField_a_of_type_AndroidContentResResources.getStringArray(a(paramString, "array"));
      return (String[])localObject;
    }
    catch (Resources.NotFoundException localNotFoundException)
    {
      Object localObject;
      for (;;) {}
    }
    catch (NullPointerException localNullPointerException)
    {
      label83:
      for (;;) {}
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("getStringArray loadIdentifierResource not found, id=");
    ((StringBuilder)localObject).append(paramString);
    LogUtils.d("MenuResources", ((StringBuilder)localObject).toString());
    break label83;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("NotFoundException, getStringArray, id=");
    ((StringBuilder)localObject).append(paramString);
    LogUtils.d("MenuResources", ((StringBuilder)localObject).toString());
    return TBSResources.getStringArray(paramString);
  }
  
  public static void init(Context paramContext, String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    jdField_a_of_type_AndroidContentContext = paramContext;
    jdField_a_of_type_ComTencentTbsCommonResourcesPluginContextWrapper = new PluginContextWrapper(paramContext, paramString);
    jdField_a_of_type_AndroidContentResResources = jdField_a_of_type_ComTencentTbsCommonResourcesPluginContextWrapper.getResources();
    DEVICE_DENSITY = a();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\menuConfig\MenuResources.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */