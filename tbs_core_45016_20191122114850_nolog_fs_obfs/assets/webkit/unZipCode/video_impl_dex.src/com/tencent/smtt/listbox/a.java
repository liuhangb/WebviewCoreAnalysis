package com.tencent.smtt.listbox;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.SmttResource;
import java.io.IOException;
import java.lang.reflect.Constructor;

public class a
{
  private static float a;
  
  private static int a(byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramArrayOfByte[(paramInt + 0)];
    int j = paramArrayOfByte[(paramInt + 1)];
    int k = paramArrayOfByte[(paramInt + 2)];
    return paramArrayOfByte[(paramInt + 3)] << 24 | j << 8 | i | k << 16;
  }
  
  public static BitmapDrawable a(Context paramContext, Bitmap paramBitmap)
  {
    try
    {
      BitmapDrawable localBitmapDrawable = (BitmapDrawable)BitmapDrawable.class.getConstructor(new Class[] { Resources.class, Bitmap.class }).newInstance(new Object[] { ContextHolder.getInstance().getContext().getResources(), paramBitmap });
      return localBitmapDrawable;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      if (paramContext != null) {
        return new BitmapDrawable(paramContext.getResources(), paramBitmap);
      }
    }
    return new BitmapDrawable(paramBitmap);
  }
  
  public static Drawable a(Context paramContext, String paramString)
  {
    try
    {
      Object localObject = new BitmapFactory.Options();
      paramString = SmttResource.decodeResource(SmttResource.loadIdentifierResource(paramString, "drawable"), (BitmapFactory.Options)localObject);
      if (paramString == null) {
        return null;
      }
      if (paramString.getNinePatchChunk() == null) {
        return a(paramContext, paramString);
      }
      paramString.setDensity(paramContext.getResources().getDisplayMetrics().densityDpi);
      localObject = new Rect();
      a(paramString.getNinePatchChunk(), (Rect)localObject);
      paramContext = new NinePatchDrawable(paramContext.getResources(), paramString, paramString.getNinePatchChunk(), (Rect)localObject, null);
      paramContext.getPaint().setAntiAlias(true);
      paramContext.setFilterBitmap(true);
      return paramContext;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  public static Drawable a(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      StringBuilder localStringBuilder = new StringBuilder("skin/bmp/");
      localStringBuilder.append("lsjd");
      localStringBuilder.append("/");
      localStringBuilder.append(paramString);
      paramString = localStringBuilder.toString();
      try
      {
        paramString = SmttResource.getResourceContext().getAssets().open(paramString);
        if (paramString == null) {
          return null;
        }
        return Drawable.createFromStream(paramString, null);
      }
      catch (IOException paramString)
      {
        paramString.printStackTrace();
        Log.e("FileUtil", paramString.getMessage());
        return null;
      }
    }
    return null;
  }
  
  public static void a(byte[] paramArrayOfByte, Rect paramRect)
  {
    paramRect.left = a(paramArrayOfByte, 12);
    paramRect.right = a(paramArrayOfByte, 16);
    paramRect.top = a(paramArrayOfByte, 20);
    paramRect.bottom = a(paramArrayOfByte, 24);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\listbox\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */