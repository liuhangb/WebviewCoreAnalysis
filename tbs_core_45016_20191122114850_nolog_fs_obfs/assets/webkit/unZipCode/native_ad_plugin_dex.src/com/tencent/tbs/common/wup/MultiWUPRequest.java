package com.tencent.tbs.common.wup;

import com.tencent.common.wup.MultiWUPRequestBase;

public class MultiWUPRequest
  extends MultiWUPRequestBase
{
  public boolean addWUPRequest(WUPRequest paramWUPRequest)
  {
    checkClassLoader(paramWUPRequest);
    return super.addWUPRequest(paramWUPRequest);
  }
  
  protected void checkClassLoader(Object paramObject)
  {
    if (paramObject == null) {
      return;
    }
    Object localObject = null;
    try
    {
      paramObject = paramObject.getClass().getClassLoader();
      if (paramObject == null) {
        return;
      }
      if ((this.mClassLoader == null) || (paramObject != this.mClassLoader)) {
        this.mClassLoader = ((ClassLoader)paramObject);
      }
      return;
    }
    catch (Throwable paramObject)
    {
      for (;;)
      {
        paramObject = localObject;
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wup\MultiWUPRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */