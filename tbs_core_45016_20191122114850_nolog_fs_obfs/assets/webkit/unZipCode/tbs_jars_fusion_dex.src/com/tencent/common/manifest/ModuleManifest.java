package com.tencent.common.manifest;

public abstract interface ModuleManifest
{
  public abstract EventReceiverImpl[] eventReceivers();
  
  public abstract Implementation[] extensionImpl();
  
  public abstract Implementation[] serviceImpl();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\manifest\ModuleManifest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */