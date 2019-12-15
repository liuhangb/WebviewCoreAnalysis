package com.tencent.tbs.common.baseinfo;

import java.util.ArrayList;

public abstract interface ICommonConfigListService
{
  public abstract void onCommonConfigTaskFail(String paramString);
  
  public abstract void onCommonConfigTaskSuccess(int paramInt, ArrayList<String> paramArrayList);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\ICommonConfigListService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */