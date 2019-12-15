package com.tencent.smtt.webkit;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.util.d;
import com.tencent.smtt.util.l;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.UsedByReflection;
import org.chromium.tencent.SharedResource;
import org.chromium.tencent.SharedResource.TbsResourcesFetcher;

@JNINamespace("tencent")
public class SmttResource
  extends SharedResource
  implements SharedResource.TbsResourcesFetcher
{
  private static final String LOGTAG = "SmttResource";
  
  public static Bitmap getBitmap(String paramString)
  {
    Object localObject;
    try
    {
      localObject = new BitmapFactory.Options();
      paramString = decodeResource(loadIdentifierResource(paramString, "drawable"), (BitmapFactory.Options)localObject);
      return paramString;
    }
    catch (Exception paramString)
    {
      localObject = new BitmapDrawable().getBitmap();
      paramString.printStackTrace();
      SmttServiceProxy localSmttServiceProxy = SmttServiceProxy.getInstance();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Resource not found: ");
      localStringBuilder.append(paramString);
      localSmttServiceProxy.reportTbsError(3004, localStringBuilder.toString());
    }
    return (Bitmap)localObject;
  }
  
  public static HashMap<String, Drawable> getBrowserListIcons(Context paramContext, Intent paramIntent, TencentWebViewProxy paramTencentWebViewProxy)
  {
    if (paramContext == null) {
      return null;
    }
    if (!"com.tencent.mm".equals(paramContext.getApplicationInfo().packageName))
    {
      paramTencentWebViewProxy = new HashMap();
      paramContext = paramContext.getPackageManager();
      paramIntent = paramContext.queryIntentActivities(paramIntent, 65536).iterator();
      while (paramIntent.hasNext())
      {
        localObject1 = (ResolveInfo)paramIntent.next();
        paramTencentWebViewProxy.put(((ResolveInfo)localObject1).activityInfo.packageName, ((ResolveInfo)localObject1).loadIcon(paramContext));
      }
      return paramTencentWebViewProxy;
    }
    localObject1 = new HashMap();
    try
    {
      paramContext = paramContext.getPackageManager();
      Object localObject2 = paramContext.queryIntentActivities(paramIntent, 65536);
      paramIntent = paramTencentWebViewProxy.getWebViewClientExtension();
      paramTencentWebViewProxy = ((List)localObject2).iterator();
      while (paramTencentWebViewProxy.hasNext())
      {
        localObject2 = ((ResolveInfo)paramTencentWebViewProxy.next()).activityInfo.packageName;
        int i = paramContext.getApplicationInfo((String)localObject2, 128).icon;
        Object localObject3 = new Bundle();
        ((Bundle)localObject3).putInt("resourceId", i);
        ((Bundle)localObject3).putString("packageName", (String)localObject2);
        localObject3 = paramIntent.onMiscCallBack("getDrawable", (Bundle)localObject3);
        if ((localObject3 instanceof Drawable)) {
          ((HashMap)localObject1).put(localObject2, (Drawable)localObject3);
        }
      }
      return (HashMap<String, Drawable>)localObject1;
    }
    catch (Throwable paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  @CalledByNative
  private static byte[] getDataResource(int paramInt1, int paramInt2)
  {
    Object localObject = m.a(mContext, paramInt1);
    if (localObject != null) {
      try
      {
        localObject = getResourceByteArray(paramInt1, (String)localObject);
        return (byte[])localObject;
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
        SmttServiceProxy localSmttServiceProxy = SmttServiceProxy.getInstance();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Resource not found: ");
        localStringBuilder.append(localThrowable);
        localSmttServiceProxy.reportTbsError(3004, localStringBuilder.toString());
      }
    }
    return new byte[0];
  }
  
  @CalledByNative
  private static String getLocalizedString(int paramInt)
  {
    return "";
  }
  
  private static byte[] getResourceByteArray(int paramInt, String paramString)
    throws Throwable
  {
    if (paramInt == 10001)
    {
      byte[] arrayOfByte = f.a();
      if (arrayOfByte != null) {
        return arrayOfByte;
      }
    }
    return d.a(mResource.getAssets().open(paramString));
  }
  
  @CalledByNative
  private static byte[] getSmallSmartBoxSearchVideoResource(String paramString1, String paramString2)
  {
    return l.a().a(paramString1, paramString2);
  }
  
  @CalledByNative
  private static byte[] getSmartBoxSearchVideoResource(String paramString1, String paramString2, String paramString3)
  {
    return l.a().a(paramString1, paramString2, paramString3);
  }
  
  public static Context getThemeContextWrapper(Context paramContext, String paramString)
  {
    Context localContext = SmttServiceProxy.getInstance().newPluginContextWrapper(paramContext, mResPath);
    if (localContext == null)
    {
      q.a("SmttResource", "#getThemeContextWrapper -- resContext is null!");
      return paramContext;
    }
    return new ContextThemeWrapper(localContext, loadIdentifierResource(paramString, "style"));
  }
  
  @UsedByReflection("X5CoreInit.java")
  public static void updateContext(Context paramContext, String paramString)
  {
    mResPath = paramString;
    init(paramContext);
    if (SharedResource.needTbsResourceAdaptation(paramContext)) {
      setResourceFetcher(new SmttResource());
    }
  }
  
  public Context newTbsResourcesContext(Context paramContext)
  {
    return SmttServiceProxy.getInstance().newPluginContextWrapper(paramContext, mResPath);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\SmttResource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */