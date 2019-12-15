package com.tencent.tbs.common.resources;

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
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.tencent.common.utils.LogUtils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class ResourcesImpl
{
  public static float DEVICE_DENSITY = -1.0F;
  private static final String LOGTAG = "ResourcesBase";
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
  private LongSparseArray<WeakReference<Drawable.ConstantState>> mDrawableCache = new LongSparseArray();
  private Context mHostContext;
  private Map<String, Integer> mIdentifiers = new HashMap();
  private Resources mResource;
  private Context mResourceContext;
  
  public ResourcesImpl(Resources paramResources, Context paramContext1, Map<String, Integer> paramMap, LongSparseArray<WeakReference<Drawable.ConstantState>> paramLongSparseArray, Context paramContext2)
  {
    this.mResource = paramResources;
    this.mResourceContext = paramContext1;
    this.mIdentifiers = paramMap;
    this.mDrawableCache = paramLongSparseArray;
    this.mHostContext = paramContext2;
    DEVICE_DENSITY = getDensity();
  }
  
  private Drawable drawableFromBitmap(Bitmap paramBitmap, byte[] paramArrayOfByte, Rect paramRect, String paramString)
  {
    if (paramArrayOfByte != null) {
      return new NinePatchDrawable(this.mResource, paramBitmap, paramArrayOfByte, paramRect, paramString);
    }
    return new BitmapDrawable(this.mResource, paramBitmap);
  }
  
  private Drawable getCachedDrawable(LongSparseArray<WeakReference<Drawable.ConstantState>> paramLongSparseArray, long paramLong)
  {
    try
    {
      Object localObject1 = (WeakReference)paramLongSparseArray.get(paramLong);
      if (localObject1 != null)
      {
        localObject1 = (Drawable.ConstantState)((WeakReference)localObject1).get();
        if (localObject1 != null)
        {
          localObject1 = ((Drawable.ConstantState)localObject1).newDrawable(this.mResource);
          return (Drawable)localObject1;
        }
        paramLongSparseArray.delete(paramLong);
      }
      return null;
    }
    finally {}
  }
  
  private float getDensity()
  {
    WindowManager localWindowManager = (WindowManager)this.mHostContext.getSystemService("window");
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
    return localDisplayMetrics.density;
  }
  
  private void handleExceptions(Throwable paramThrowable, boolean paramBoolean)
  {
    StringWriter localStringWriter = new StringWriter();
    paramThrowable.printStackTrace(new PrintWriter(localStringWriter));
    paramThrowable = new StringBuilder();
    paramThrowable.append("exceptions(");
    paramThrowable.append(paramBoolean);
    paramThrowable.append("):");
    paramThrowable.append(localStringWriter.toString());
    Log.e("ResourcesBase", paramThrowable.toString());
    if (paramBoolean) {
      System.exit(-1);
    }
  }
  
  public void checkInit()
  {
    if (this.mResource != null) {
      return;
    }
    throw new NullPointerException("TesResourceIsNotInit and u need call rice");
  }
  
  public Bitmap decodeResource(String paramString, BitmapFactory.Options paramOptions)
  {
    checkInit();
    paramOptions = BitmapFactory.decodeResource(this.mResource, loadIdentifierResource(paramString, "drawable"), paramOptions);
    if (paramOptions != null) {
      return paramOptions;
    }
    paramOptions = new StringBuilder();
    paramOptions.append("decodeResource:");
    paramOptions.append(paramString);
    handleExceptions(new Exception(paramOptions.toString()), true);
    return null;
  }
  
  public Animation getAnimation(String paramString)
  {
    checkInit();
    try
    {
      paramString = AnimationUtils.loadAnimation(this.mResourceContext, loadIdentifierResource(paramString, "anim"));
      return paramString;
    }
    catch (Resources.NotFoundException paramString)
    {
      handleExceptions(paramString, true);
    }
    return null;
  }
  
  public Bitmap getBitmap(String paramString)
  {
    try
    {
      BitmapFactory.Options localOptions = new BitmapFactory.Options();
      localOptions.inScreenDensity = ((int)DEVICE_DENSITY);
      paramString = decodeResource(paramString, localOptions);
      return paramString;
    }
    catch (Throwable paramString)
    {
      handleExceptions(paramString, false);
    }
    return null;
  }
  
  public int getColor(String paramString)
  {
    checkInit();
    try
    {
      int i = this.mResource.getColor(loadIdentifierResource(paramString, "color"));
      return i;
    }
    catch (Exception paramString)
    {
      handleExceptions(paramString, true);
    }
    return 0;
  }
  
  public int getDimensionPixelSize(String paramString)
  {
    checkInit();
    try
    {
      int i = this.mResource.getDimensionPixelOffset(loadIdentifierResource(paramString, "dimen"));
      return i;
    }
    catch (Resources.NotFoundException paramString)
    {
      handleExceptions(paramString, true);
    }
    return 0;
  }
  
  public Drawable getDrawable(String paramString)
  {
    checkInit();
    for (;;)
    {
      try
      {
        int i = loadIdentifierResource(paramString, "drawable");
        localObject1 = this.mDrawableCache;
        long l = i;
        localObject1 = getCachedDrawable((LongSparseArray)localObject1, l);
        if (localObject1 != null)
        {
          LogUtils.d("ResourcesBase", "getDrawableFromCache");
          return (Drawable)localObject1;
        }
        localObject2 = new Rect();
        Object localObject3 = new BitmapFactory.Options();
        ((BitmapFactory.Options)localObject3).inScreenDensity = ((int)DEVICE_DENSITY);
        Bitmap localBitmap = decodeResource(paramString, (BitmapFactory.Options)localObject3);
        if (localBitmap != null)
        {
          localObject3 = localBitmap.getNinePatchChunk();
          if (localObject3 != null)
          {
            localObject1 = localObject3;
            if (NinePatch.isNinePatchChunk((byte[])localObject3))
            {
              localObject1 = drawableFromBitmap(localBitmap, (byte[])localObject1, (Rect)localObject2, paramString);
              this.mDrawableCache.put(l, new WeakReference(((Drawable)localObject1).getConstantState()));
            }
          }
        }
        else
        {
          return (Drawable)localObject1;
        }
      }
      catch (Throwable paramString)
      {
        handleExceptions(paramString, true);
        return null;
      }
      Object localObject1 = null;
      Object localObject2 = localObject1;
    }
  }
  
  public int[] getIntArray(String paramString)
  {
    checkInit();
    return this.mResource.getIntArray(loadIdentifierResource(paramString, "array"));
  }
  
  @Deprecated
  public LayoutInflater getLayoutInflater()
  {
    checkInit();
    return ((PluginContextWrapper)this.mResourceContext).getLayoutInflater();
  }
  
  public String getQuantityString(String paramString, int paramInt)
  {
    checkInit();
    try
    {
      paramString = this.mResource.getQuantityString(loadIdentifierResource(paramString, "string"), paramInt);
      return paramString;
    }
    catch (Resources.NotFoundException paramString)
    {
      handleExceptions(paramString, true);
    }
    return null;
  }
  
  @Deprecated
  public String getResourceName(int paramInt)
  {
    checkInit();
    try
    {
      String str = this.mResource.getResourceName(paramInt);
      str = str.substring(str.indexOf("/") + 1, str.length());
      return str;
    }
    catch (Exception localException)
    {
      handleExceptions(localException, true);
    }
    return null;
  }
  
  public String getString(String paramString)
  {
    checkInit();
    try
    {
      paramString = this.mResource.getString(loadIdentifierResource(paramString, "string"));
      return paramString;
    }
    catch (Resources.NotFoundException paramString)
    {
      handleExceptions(paramString, true);
    }
    return null;
  }
  
  public String getString(String paramString1, String paramString2)
  {
    checkInit();
    try
    {
      paramString1 = this.mResource.getString(loadIdentifierResource(paramString1, paramString2));
      return paramString1;
    }
    catch (Resources.NotFoundException paramString1)
    {
      handleExceptions(paramString1, true);
    }
    return null;
  }
  
  public String getString(String paramString, Object... paramVarArgs)
  {
    checkInit();
    return String.format(getString(paramString), paramVarArgs);
  }
  
  public String[] getStringArray(String paramString)
  {
    checkInit();
    try
    {
      paramString = this.mResource.getStringArray(loadIdentifierResource(paramString, "array"));
      return paramString;
    }
    catch (Resources.NotFoundException paramString)
    {
      handleExceptions(paramString, true);
    }
    return null;
  }
  
  public String[] getStringArray(String paramString1, String paramString2)
  {
    checkInit();
    try
    {
      paramString1 = this.mResource.getStringArray(loadIdentifierResource(paramString1, paramString2));
      return paramString1;
    }
    catch (Resources.NotFoundException paramString1)
    {
      handleExceptions(paramString1, true);
    }
    return null;
  }
  
  public CharSequence getText(String paramString)
  {
    checkInit();
    try
    {
      paramString = this.mResource.getText(loadIdentifierResource(paramString, "string"));
      return paramString;
    }
    catch (Resources.NotFoundException paramString)
    {
      handleExceptions(paramString, true);
    }
    return null;
  }
  
  public CharSequence getText(String paramString1, String paramString2)
  {
    checkInit();
    try
    {
      paramString1 = this.mResource.getText(loadIdentifierResource(paramString1, paramString2));
      return paramString1;
    }
    catch (Resources.NotFoundException paramString1)
    {
      handleExceptions(paramString1, true);
    }
    return null;
  }
  
  public void getValue(String paramString, TypedValue paramTypedValue, boolean paramBoolean)
  {
    checkInit();
    try
    {
      this.mResource.getValue(paramString, paramTypedValue, paramBoolean);
      return;
    }
    catch (Resources.NotFoundException paramString)
    {
      handleExceptions(paramString, true);
    }
  }
  
  public int loadIdentifierResource(String paramString1, String paramString2)
  {
    checkInit();
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append("/");
    ((StringBuilder)localObject).append(paramString1);
    localObject = ((StringBuilder)localObject).toString();
    if (this.mIdentifiers.containsKey(localObject)) {
      return ((Integer)this.mIdentifiers.get(localObject)).intValue();
    }
    int i = this.mResource.getIdentifier(paramString1, paramString2, this.mResourceContext.getPackageName());
    if (i != 0)
    {
      this.mIdentifiers.put(localObject, Integer.valueOf(i));
      return i;
    }
    throw new NullPointerException(paramString1);
  }
  
  public void updateContext(Context paramContext) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\resources\ResourcesImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */