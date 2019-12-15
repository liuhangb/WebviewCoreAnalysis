package com.tencent.tbs.tbsshell.partner.miniqb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.ValueCallback;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.miniqb.export.IMiniQBDexLoaderProvider;
import com.tencent.mtt.miniqb.export.IMiniQBManager;
import com.tencent.mtt.miniqb.export.MiniQBEngine;
import com.tencent.tbs.common.log.LogManager;
import com.tencent.tbs.tbsshell.partner.miniqb.upgrade.MiniQBUpgradeManager;
import com.tencent.tbs.tbsshell.partner.player.TbsVideoManager;
import dalvik.system.DexClassLoader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Map;

public class TbsMiniQBProxy
{
  private static final String TBS_MINIQB_CLASS_NAME = "com.tencent.mtt.miniqb.base.MidPage";
  private static final String TBS_MINIQB_INFO_CLASS_NAME = "com.tencent.mtt.miniqb.utils.MiniQbInfoUtils";
  private static DexClassLoader mBaseLoader;
  private static WeakReference<Context> mContext;
  private static IMiniQBDexLoaderProvider mDexProvider;
  private static boolean mIsInited = false;
  private static TbsMiniQBProxy mMidPageProxy;
  private static DexClassLoader mMiniQBLoader;
  private static IMiniQBManager mMiniqbManager;
  private static String mPath;
  
  public static boolean canOpenFile(Context paramContext, String paramString)
  {
    if (isMiniQBEnvironmentInited())
    {
      paramContext = invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "canOpenFile", new Class[] { Context.class, String.class }, new Object[] { paramContext, paramString });
      if ((paramContext != null) && ((paramContext instanceof Boolean)))
      {
        bool = ((Boolean)paramContext).booleanValue();
        break label64;
      }
    }
    boolean bool = false;
    label64:
    if (!bool) {
      LogManager.logL1(9021, "canOpenFile()=false", new Object[0]);
    }
    return bool;
  }
  
  private static boolean canUseMiniQB()
  {
    boolean bool1 = MiniQBUpgradeManager.getInstance().isMiniQBDisabled() ^ true;
    boolean bool2 = isMiniQBEnvironmentInited();
    if ((!bool1) || (!bool2)) {
      LogManager.logL1(9020, "enable=%b, inited=%b", new Object[] { Boolean.valueOf(bool1), Boolean.valueOf(bool2) });
    }
    return (bool1) && (bool2);
  }
  
  public static void closeFileReader()
  {
    LogUtils.d("filereader", "closeFileReader");
    if (isMiniQBEnvironmentInited()) {
      invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "closeFileReader", new Class[0], new Object[0]);
    }
  }
  
  public static void closeMiniQB()
  {
    if (isMiniQBEnvironmentInited()) {
      invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "closeMiniQB", new Class[0], new Object[0]);
    }
  }
  
  public static void closeMiniQB(String paramString)
  {
    if (isMiniQBEnvironmentInited()) {
      invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "closeMiniQB", new Class[] { String.class }, new Object[] { paramString });
    }
  }
  
  public static TbsMiniQBProxy getInstance()
  {
    if (mMidPageProxy == null) {
      mMidPageProxy = new TbsMiniQBProxy();
    }
    return mMidPageProxy;
  }
  
  public static String getMiniQbVersion()
  {
    if (isMiniQBEnvironmentInited())
    {
      Object localObject = invokeStaticMethod("com.tencent.mtt.miniqb.utils.MiniQbInfoUtils", "getWholeMiniQbVersion", new Class[0], new Object[0]);
      if ((localObject != null) && ((localObject instanceof String))) {
        return (String)localObject;
      }
    }
    return "";
  }
  
  public static String getMiniQbVersionName()
  {
    String str2 = "";
    String str1 = str2;
    if (isMiniQBEnvironmentInited())
    {
      Object localObject = invokeStaticMethod("com.tencent.mtt.miniqb.utils.MiniQbInfoUtils", "getWholeMiniQbVersionName", new Class[0], new Object[0]);
      str1 = str2;
      if (localObject != null)
      {
        str1 = str2;
        if ((localObject instanceof String)) {
          str1 = (String)localObject;
        }
      }
    }
    MiniQBUpgradeManager.getInstance().doMiniQBUpgradeTask();
    return str1;
  }
  
  public static String getPath()
  {
    String str = mPath;
    if ((str == null) || (TextUtils.isEmpty(str))) {
      mPath = MiniQBUpgradeManager.getInstance().getMiniQBLoadPath();
    }
    return mPath;
  }
  
  public static void handleAdsUrl(Context paramContext, Object paramObject, String paramString, boolean paramBoolean)
  {
    if (isMiniQBEnvironmentInited()) {
      invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "handleAdsUrl", new Class[] { Context.class, Object.class, String.class, Boolean.TYPE }, new Object[] { paramContext, paramObject, paramString, Boolean.valueOf(paramBoolean) });
    }
  }
  
  public static void handleAdsUrl(Context paramContext, Object paramObject, String paramString1, boolean paramBoolean, String paramString2, int paramInt)
  {
    if (isMiniQBEnvironmentInited()) {
      invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "handleAdsUrl", new Class[] { Context.class, Object.class, String.class, Boolean.TYPE, String.class, Integer.TYPE }, new Object[] { paramContext, paramObject, paramString1, Boolean.valueOf(paramBoolean), paramString2, Integer.valueOf(paramInt) });
    }
  }
  
  public static void handleWupCallback(Object paramObject1, Object paramObject2, byte[] paramArrayOfByte, boolean paramBoolean)
  {
    if (isMiniQBEnvironmentInited())
    {
      LogUtils.d("TbsMiniQBProxy", "handleWupCallback at shell");
      invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "handleWupCallback", new Class[] { Object.class, Object.class, byte[].class, Boolean.TYPE }, new Object[] { paramObject1, paramObject2, paramArrayOfByte, Boolean.valueOf(paramBoolean) });
    }
  }
  
  @SuppressLint({"NewApi"})
  private static Object invokeStaticMethod(String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    try
    {
      paramArrayOfClass = mMiniQBLoader.loadClass(paramString1).getMethod(paramString2, paramArrayOfClass);
      paramArrayOfClass.setAccessible(true);
      paramArrayOfClass = paramArrayOfClass.invoke(null, paramVarArgs);
      return paramArrayOfClass;
    }
    catch (Throwable paramArrayOfClass)
    {
      LogManager.logL1(9022, "method=%s, error=%s", new Object[] { paramString2, String.valueOf(paramArrayOfClass) });
      paramVarArgs = new StringBuilder();
      paramVarArgs.append("'");
      paramVarArgs.append(paramString1);
      paramVarArgs.append("' invoke static method '");
      paramVarArgs.append(paramString2);
      paramVarArgs.append("' failed");
      Log.e("invokeStaticMethod", paramVarArgs.toString(), paramArrayOfClass);
    }
    return null;
  }
  
  public static boolean isMiniQBEnvironmentInited()
  {
    Object localObject = mContext;
    if (localObject != null)
    {
      if (((WeakReference)localObject).get() == null) {
        return false;
      }
      boolean bool = mIsInited;
      if (bool) {
        return bool;
      }
      MiniQBEngine.getInstance().init((Context)mContext.get(), getPath(), getPath(), mDexProvider);
      mMiniqbManager = MiniQBEngine.getInstance().getMiniQBManager();
      if (mMiniQBLoader == null) {
        mMiniQBLoader = MiniQBEngine.getInstance().getMiniQBDexLoader();
      }
      if ((mMiniQBLoader != null) && (mBaseLoader != null)) {
        mIsInited = true;
      } else {
        mIsInited = false;
      }
      localObject = mMiniqbManager;
      if (localObject != null) {
        ((IMiniQBManager)localObject).initMiniQB((Context)mContext.get(), mBaseLoader, getPath());
      }
      return mIsInited;
    }
    return false;
  }
  
  public static void miniQBStarted() {}
  
  private static void pauseVideo() {}
  
  public static void startDownloadFromWebPage(Context paramContext, int paramInt, String paramString1, String paramString2, String paramString3)
  {
    if (isMiniQBEnvironmentInited()) {
      invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "startDownloadFromWebPage", new Class[] { Context.class, Integer.TYPE, String.class, String.class, String.class }, new Object[] { paramContext, Integer.valueOf(paramInt), paramString1, paramString2, paramString3 });
    }
  }
  
  public static int startMiniQB(Context paramContext, String paramString1, String paramString2)
  {
    if (canUseMiniQB())
    {
      paramContext = invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "startQBWeb", new Class[] { Context.class, String.class, String.class }, new Object[] { paramContext, paramString1, paramString2 });
      if ((paramContext != null) && ((paramContext instanceof Integer))) {
        return ((Integer)paramContext).intValue();
      }
    }
    return -1;
  }
  
  public static int startMiniQB(Context paramContext, String paramString1, String paramString2, Map<String, String> paramMap)
  {
    if (canUseMiniQB())
    {
      paramContext = invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "startMiniQB", new Class[] { Context.class, String.class, String.class, Map.class }, new Object[] { paramContext, paramString1, paramString2, paramMap });
      if ((paramContext != null) && ((paramContext instanceof Integer))) {
        return ((Integer)paramContext).intValue();
      }
    }
    return -1;
  }
  
  public static int startMiniQB(Context paramContext, String paramString1, String paramString2, Map<String, String> paramMap, ValueCallback<String> paramValueCallback)
  {
    if (canUseMiniQB())
    {
      Object localObject = invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "startMiniQB", new Class[] { Context.class, String.class, String.class, Map.class, ValueCallback.class }, new Object[] { paramContext, paramString1, paramString2, paramMap, paramValueCallback });
      paramValueCallback = (ValueCallback<String>)localObject;
      if (localObject == null) {
        paramValueCallback = invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "startMiniQB", new Class[] { Context.class, String.class, String.class, Map.class }, new Object[] { paramContext, paramString1, paramString2, paramMap });
      }
      if ((paramValueCallback != null) && ((paramValueCallback instanceof Integer))) {
        return ((Integer)paramValueCallback).intValue();
      }
      return -3;
    }
    return -1;
  }
  
  public static int startMiniQBFramework(Context paramContext, String paramString, int paramInt, Bundle paramBundle, ValueCallback<Integer> paramValueCallback)
  {
    if (canUseMiniQB())
    {
      paramContext = invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "startMiniQBFramework", new Class[] { Context.class, String.class, Integer.TYPE, Bundle.class, ValueCallback.class }, new Object[] { paramContext, paramString, Integer.valueOf(paramInt), paramBundle, paramValueCallback });
      if ((paramContext != null) && ((paramContext instanceof Integer))) {
        return ((Integer)paramContext).intValue();
      }
      return -3;
    }
    paramValueCallback.onReceiveValue(Integer.valueOf(-1));
    return -1;
  }
  
  public static int startMiniQBWithImages(Context paramContext, Map<String, Bitmap> paramMap, Map<String, Boolean> paramMap1, int paramInt, String paramString)
  {
    if (canUseMiniQB())
    {
      paramContext = invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "startMiniQBWithImages", new Class[] { Context.class, Map.class, Map.class, Integer.TYPE, String.class }, new Object[] { paramContext, paramMap, paramMap1, Integer.valueOf(paramInt), paramString });
      if ((paramContext != null) && ((paramContext instanceof Integer))) {
        return ((Integer)paramContext).intValue();
      }
      return -3;
    }
    return -1;
  }
  
  public static int startQBWeb(Context paramContext, String paramString)
  {
    if (canUseMiniQB())
    {
      paramContext = invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "startQBWeb", new Class[] { Context.class, String.class }, new Object[] { paramContext, paramString });
      if ((paramContext != null) && ((paramContext instanceof Integer))) {
        return ((Integer)paramContext).intValue();
      }
    }
    return -1;
  }
  
  public static int startQBWeb(Context paramContext, String paramString1, String paramString2, int paramInt, Point paramPoint1, Point paramPoint2)
  {
    if (canUseMiniQB())
    {
      paramContext = invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "startQBWeb", new Class[] { Context.class, String.class, String.class, Integer.TYPE }, new Object[] { paramContext, paramString1, paramString2, Integer.valueOf(paramInt) });
      if ((paramContext != null) && ((paramContext instanceof Integer))) {
        return ((Integer)paramContext).intValue();
      }
    }
    return -1;
  }
  
  public static int startQBWebWithNightFullscreenMode(Context paramContext, String paramString, int paramInt)
  {
    if (canUseMiniQB())
    {
      paramContext = invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "startQBWebWithNightFullscreenMode", new Class[] { Context.class, String.class, Integer.TYPE }, new Object[] { paramContext, paramString, Integer.valueOf(paramInt) });
      if ((paramContext != null) && ((paramContext instanceof Integer))) {
        return ((Integer)paramContext).intValue();
      }
    }
    return -1;
  }
  
  public static void useQBWebLoadUrl(String paramString)
  {
    if (canUseMiniQB()) {
      invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "useQBWebLoadUrl", new Class[] { String.class }, new Object[] { paramString });
    }
  }
  
  public void createMiniQBShortCut(Context paramContext, String paramString1, String paramString2, Bitmap paramBitmap)
  {
    if (isMiniQBEnvironmentInited()) {
      invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "createMiniQBShortCut", new Class[] { Context.class, String.class, String.class, Bitmap.class }, new Object[] { paramContext, paramString1, paramString2, paramBitmap });
    }
  }
  
  public String getMiniQBUA()
  {
    if (isMiniQBEnvironmentInited())
    {
      Object localObject = invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "getMiniQBUA", new Class[0], new Object[0]);
      if ((localObject != null) && ((localObject instanceof String))) {
        return (String)localObject;
      }
    }
    return null;
  }
  
  public String getMiniQBVC()
  {
    if (isMiniQBEnvironmentInited())
    {
      Object localObject = invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "getMiniQBVC", new Class[0], new Object[0]);
      if ((localObject != null) && ((localObject instanceof String))) {
        return (String)localObject;
      }
    }
    return null;
  }
  
  public String getMiniQBVcWithoutLoad()
  {
    if (mIsInited) {
      return getMiniQBVC();
    }
    return null;
  }
  
  public void init(Context paramContext, DexClassLoader paramDexClassLoader, IMiniQBDexLoaderProvider paramIMiniQBDexLoaderProvider)
  {
    if (paramIMiniQBDexLoaderProvider == null) {
      return;
    }
    mDexProvider = paramIMiniQBDexLoaderProvider;
    mContext = new WeakReference(paramContext);
  }
  
  public boolean isMiniQBShortCutExist(Context paramContext, String paramString1, String paramString2)
  {
    if (isMiniQBEnvironmentInited())
    {
      paramContext = invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "isMiniQBShortCutExist", new Class[] { Context.class, String.class, String.class }, new Object[] { paramContext, paramString1, paramString2 });
      if ((paramContext != null) && ((paramContext instanceof Boolean))) {
        return ((Boolean)paramContext).booleanValue();
      }
    }
    return false;
  }
  
  public void onDownloadStart(Context paramContext, String paramString1, String paramString2, byte[] paramArrayOfByte, String paramString3, String paramString4, String paramString5, long paramLong, String paramString6, String paramString7)
  {
    if (isMiniQBEnvironmentInited()) {
      invokeStaticMethod("com.tencent.mtt.miniqb.base.MidPage", "onDownloadStart", new Class[] { Context.class, String.class, String.class, byte[].class, String.class, String.class, String.class, Long.TYPE, String.class, String.class }, new Object[] { paramContext, paramString1, paramString2, paramArrayOfByte, paramString3, paramString4, paramString5, Long.valueOf(paramLong), paramString6, paramString7 });
    }
  }
  
  public void setDexLoader(DexClassLoader paramDexClassLoader)
  {
    mBaseLoader = paramDexClassLoader;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\TbsMiniQBProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */