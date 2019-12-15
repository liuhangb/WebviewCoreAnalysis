package com.tencent.common.plugin;

import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;

public class PluginCallbackHandler
  extends Handler
{
  protected QBPluginServiceImpl mQBPluginServiceImpl = null;
  protected ArrayList<IQBPluginCallback> mQBPluignCallbackList = null;
  
  public PluginCallbackHandler()
  {
    super(Looper.getMainLooper());
  }
  
  public void init(QBPluginServiceImpl paramQBPluginServiceImpl)
  {
    this.mQBPluginServiceImpl = paramQBPluginServiceImpl;
  }
  
  public void setCallBackList(ArrayList<IQBPluginCallback> paramArrayList)
  {
    this.mQBPluignCallbackList = paramArrayList;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\PluginCallbackHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */