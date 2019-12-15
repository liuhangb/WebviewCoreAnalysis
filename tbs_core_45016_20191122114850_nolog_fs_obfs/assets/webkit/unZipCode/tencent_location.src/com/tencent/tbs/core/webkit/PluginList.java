package com.tencent.tbs.core.webkit;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class PluginList
{
  private ArrayList<Plugin> mPlugins = new ArrayList();
  
  @Deprecated
  public void addPlugin(Plugin paramPlugin)
  {
    try
    {
      if (!this.mPlugins.contains(paramPlugin)) {
        this.mPlugins.add(paramPlugin);
      }
      return;
    }
    finally
    {
      paramPlugin = finally;
      throw paramPlugin;
    }
  }
  
  @Deprecated
  public void clear()
  {
    try
    {
      this.mPlugins.clear();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  @Deprecated
  public List getList()
  {
    try
    {
      ArrayList localArrayList = this.mPlugins;
      return localArrayList;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  /* Error */
  @Deprecated
  public void pluginClicked(android.content.Context paramContext, int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 17	com/tencent/tbs/core/webkit/PluginList:mPlugins	Ljava/util/ArrayList;
    //   6: iload_2
    //   7: invokevirtual 41	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   10: checkcast 43	com/tencent/tbs/core/webkit/Plugin
    //   13: aload_1
    //   14: invokevirtual 47	com/tencent/tbs/core/webkit/Plugin:dispatchClickEvent	(Landroid/content/Context;)V
    //   17: goto +8 -> 25
    //   20: astore_1
    //   21: aload_0
    //   22: monitorexit
    //   23: aload_1
    //   24: athrow
    //   25: aload_0
    //   26: monitorexit
    //   27: return
    //   28: astore_1
    //   29: goto -4 -> 25
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	32	0	this	PluginList
    //   0	32	1	paramContext	android.content.Context
    //   0	32	2	paramInt	int
    // Exception table:
    //   from	to	target	type
    //   2	17	20	finally
    //   2	17	28	java/lang/IndexOutOfBoundsException
  }
  
  @Deprecated
  public void removePlugin(Plugin paramPlugin)
  {
    try
    {
      int i = this.mPlugins.indexOf(paramPlugin);
      if (i != -1) {
        this.mPlugins.remove(i);
      }
      return;
    }
    finally {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\PluginList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */