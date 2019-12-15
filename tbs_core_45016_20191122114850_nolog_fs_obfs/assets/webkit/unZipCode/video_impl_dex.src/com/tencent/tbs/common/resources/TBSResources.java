package com.tencent.tbs.common.resources;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Process;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class TBSResources
{
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
  public static final boolean USE_PACKAGED_RESOURCE = false;
  private static final LongSparseArray<WeakReference<Drawable.ConstantState>> mDrawableCache = new LongSparseArray();
  private static Context mHostContext;
  private static Map<String, Integer> mIdentifiers = new HashMap();
  private static Resources mResource;
  private static String mResourceApkPath;
  private static Context mResourceContext;
  private static ResourcesImpl mTbsResource;
  private static boolean mUseReflection = false;
  
  public static void checkInit()
  {
    if (mTbsResource == null)
    {
      if (mHostContext != null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Note:TbsResorce is Reinited,current Thread is ");
        localStringBuilder.append(Process.myTid());
        Log.e("TBSResources", localStringBuilder.toString(), new Throwable());
        initTbsResourcesAsync();
        return;
      }
      throw new RuntimeException("TbsResource HostContext is null,please check has called TbsResource.init()");
    }
  }
  
  public static Bitmap decodeResource(String paramString, BitmapFactory.Options paramOptions)
  {
    checkInit();
    return mTbsResource.decodeResource(paramString, paramOptions);
  }
  
  public static Animation getAnimation(String paramString)
  {
    checkInit();
    return mTbsResource.getAnimation(paramString);
  }
  
  public static Bitmap getBitmap(String paramString)
  {
    checkInit();
    return mTbsResource.getBitmap(paramString);
  }
  
  public static int getColor(String paramString)
  {
    checkInit();
    return mTbsResource.getColor(paramString);
  }
  
  public static int getDimensionPixelSize(String paramString)
  {
    checkInit();
    return mTbsResource.getDimensionPixelSize(paramString);
  }
  
  public static Drawable getDrawable(String paramString)
  {
    checkInit();
    return mTbsResource.getDrawable(paramString);
  }
  
  public static int[] getIntArray(String paramString)
  {
    checkInit();
    return mTbsResource.getIntArray(paramString);
  }
  
  @Deprecated
  public static LayoutInflater getLayoutInflater()
  {
    checkInit();
    return mTbsResource.getLayoutInflater();
  }
  
  public static String getQuantityString(String paramString, int paramInt)
  {
    checkInit();
    return mTbsResource.getQuantityString(paramString, paramInt);
  }
  
  public static String getResourceApkPath()
  {
    return mResourceApkPath;
  }
  
  public static Context getResourceContext()
  {
    checkInit();
    return mResourceContext;
  }
  
  @Deprecated
  public static String getResourceName(int paramInt)
  {
    checkInit();
    return mTbsResource.getResourceName(paramInt);
  }
  
  public static Resources getResources()
  {
    checkInit();
    return mResource;
  }
  
  public static String getString(String paramString)
  {
    checkInit();
    return mTbsResource.getString(paramString);
  }
  
  public static String getString(String paramString1, String paramString2)
  {
    checkInit();
    return mTbsResource.getString(paramString1, paramString2);
  }
  
  public static String getString(String paramString, Object... paramVarArgs)
  {
    checkInit();
    return mTbsResource.getString(paramString, paramVarArgs);
  }
  
  public static String[] getStringArray(String paramString)
  {
    checkInit();
    return mTbsResource.getStringArray(paramString);
  }
  
  public static String[] getStringArray(String paramString1, String paramString2)
  {
    checkInit();
    return mTbsResource.getStringArray(paramString1, paramString2);
  }
  
  public static CharSequence getText(String paramString)
  {
    checkInit();
    return mTbsResource.getText(paramString);
  }
  
  public static CharSequence getText(String paramString1, String paramString2)
  {
    checkInit();
    return mTbsResource.getText(paramString1, paramString2);
  }
  
  public static void getValue(String paramString, TypedValue paramTypedValue, boolean paramBoolean)
  {
    checkInit();
    mTbsResource.getValue(paramString, paramTypedValue, paramBoolean);
  }
  
  public static void init(Context paramContext, String paramString)
  {
    mHostContext = paramContext;
    mResourceApkPath = paramString;
    initTbsResourcesAsync();
  }
  
  public static void initTbsResourcesAsync()
  {
    if (mTbsResource == null) {
      try
      {
        if (mTbsResource == null)
        {
          mResourceContext = new PluginContextWrapper(mHostContext, mResourceApkPath);
          mResource = mResourceContext.getResources();
          mTbsResource = new ResourcesImpl(mResource, mResourceContext, mIdentifiers, mDrawableCache, mHostContext);
        }
        return;
      }
      finally {}
    }
  }
  
  public static int loadIdentifierResource(String paramString1, String paramString2)
  {
    checkInit();
    return mTbsResource.loadIdentifierResource(paramString1, paramString2);
  }
  
  public static void setContext(Context paramContext, String paramString)
  {
    setContext(paramContext, paramString, false);
  }
  
  public static void setContext(Context paramContext, String paramString, boolean paramBoolean)
  {
    mUseReflection = paramBoolean;
    mHostContext = paramContext;
    mResourceApkPath = paramString;
  }
  
  public static boolean useReflection()
  {
    return mUseReflection;
  }
  
  public void updateContext(Context paramContext) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\resources\TBSResources.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */