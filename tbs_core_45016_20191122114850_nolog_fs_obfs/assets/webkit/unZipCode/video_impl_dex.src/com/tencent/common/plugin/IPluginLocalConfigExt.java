package com.tencent.common.plugin;

import com.tencent.common.manifest.annotation.Extension;
import java.util.Map;

@Extension
public abstract interface IPluginLocalConfigExt
{
  public static final int CATAGORY_DEFAULT = 0;
  public static final int CATAGORY_UIINFO = 1;
  
  public abstract Map<String, QBPluginServiceImpl.PluginConfigInfo> addPluginLocalConfig(int paramInt, QBPluginServiceImpl.IPluginDir paramIPluginDir);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\IPluginLocalConfigExt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */