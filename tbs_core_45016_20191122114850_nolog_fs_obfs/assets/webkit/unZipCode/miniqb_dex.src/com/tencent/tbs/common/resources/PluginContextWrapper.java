package com.tencent.tbs.common.resources;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.view.LayoutInflater;

public final class PluginContextWrapper
  extends ContextWrapper
{
  private static final String TAG = "PluginContextWrapper";
  private final LayoutInflater mLayoutInflater;
  private Context mPlatformContext;
  private String mPluginPackageName;
  private Resources mPluginResources;
  private Resources.Theme mPluginTheme;
  
  public PluginContextWrapper(Context paramContext, String paramString)
  {
    super(paramContext);
    Object localObject = null;
    this.mPluginPackageName = null;
    this.mPlatformContext = paramContext;
    this.mLayoutInflater = new PluginLayoutInflater(this);
    this.mPluginResources = ApkUtil.getResources(paramContext, paramString);
    Resources localResources = this.mPluginResources;
    if (localResources != null)
    {
      this.mPluginTheme = localResources.newTheme();
      paramString = ApkUtil.getPackageInfo(paramContext, paramString);
      if (paramString == null) {
        paramContext = (Context)localObject;
      } else {
        paramContext = paramString.applicationInfo;
      }
      if (paramString != null)
      {
        if (paramContext == null) {
          return;
        }
        int i = paramContext.theme;
        this.mPluginPackageName = paramContext.packageName;
        paramContext = getBaseContext().getTheme();
        if (paramContext != null) {
          this.mPluginTheme.setTo(paramContext);
        }
        if (i != 0) {
          this.mPluginTheme.applyStyle(i, true);
        }
      }
      else {}
    }
  }
  
  private Intent fixedIntent(Intent paramIntent)
  {
    ComponentName localComponentName = paramIntent.getComponent();
    if (localComponentName != null) {
      paramIntent.setComponent(new ComponentName(getBaseContext(), localComponentName.getClassName()));
    }
    return paramIntent;
  }
  
  public static Context getPlatfromContext(Context paramContext)
  {
    if ((paramContext instanceof PluginContextWrapper)) {
      return ((PluginContextWrapper)paramContext).getPlatformContext();
    }
    return paramContext;
  }
  
  public Context getApplicationContext()
  {
    return this.mPlatformContext.getApplicationContext();
  }
  
  public AssetManager getAssets()
  {
    Object localObject = getResources();
    if (localObject != null) {
      localObject = ((Resources)localObject).getAssets();
    } else {
      localObject = null;
    }
    if (localObject != null) {
      return (AssetManager)localObject;
    }
    return super.getAssets();
  }
  
  public Context getBaseContext()
  {
    return this;
  }
  
  public LayoutInflater getLayoutInflater()
  {
    return this.mLayoutInflater;
  }
  
  public String getPackageName()
  {
    return this.mPluginPackageName;
  }
  
  public Context getPlatformContext()
  {
    return this.mPlatformContext;
  }
  
  public Resources getResources()
  {
    Resources localResources = this.mPluginResources;
    if (localResources != null) {
      return localResources;
    }
    return super.getResources();
  }
  
  public Object getSystemService(String paramString)
  {
    if ("layout_inflater".equals(paramString)) {
      return getLayoutInflater();
    }
    return super.getSystemService(paramString);
  }
  
  public Resources.Theme getTheme()
  {
    Resources.Theme localTheme = this.mPluginTheme;
    if (localTheme != null) {
      return localTheme;
    }
    return super.getTheme();
  }
  
  public void setTheme(int paramInt)
  {
    Resources.Theme localTheme = this.mPluginTheme;
    if (localTheme != null)
    {
      localTheme.applyStyle(paramInt, true);
      return;
    }
    super.setTheme(paramInt);
  }
  
  public void startActivity(Intent paramIntent)
  {
    super.startActivity(fixedIntent(paramIntent));
  }
  
  public ComponentName startService(Intent paramIntent)
  {
    return super.startService(fixedIntent(paramIntent));
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\resources\PluginContextWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */