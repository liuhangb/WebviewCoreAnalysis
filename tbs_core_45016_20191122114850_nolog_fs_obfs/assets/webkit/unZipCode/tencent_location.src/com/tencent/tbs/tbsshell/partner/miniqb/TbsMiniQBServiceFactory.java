package com.tencent.tbs.tbsshell.partner.miniqb;

import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;

public class TbsMiniQBServiceFactory
{
  public static final byte IMPL_MINIQB = 1;
  public static final byte IMPL_TBS = 0;
  private static TbsMiniQBService mMiniQBInstance;
  
  public static TbsMiniQBService getTbsMiniQBService(byte paramByte)
  {
    if (mMiniQBInstance == null)
    {
      mMiniQBInstance = new TbsMiniQBService(paramByte);
      mMiniQBInstance.setContext(TbsBaseModuleShell.getCallerContext());
    }
    return mMiniQBInstance;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\TbsMiniQBServiceFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */