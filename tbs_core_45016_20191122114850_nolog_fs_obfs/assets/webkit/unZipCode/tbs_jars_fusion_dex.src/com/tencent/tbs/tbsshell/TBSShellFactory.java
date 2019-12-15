package com.tencent.tbs.tbsshell;

import com.tencent.common.http.Apn;
import com.tencent.tbs.tbsshell.common.ISmttServiceMtt;
import com.tencent.tbs.tbsshell.common.ISmttServicePartner;
import com.tencent.tbs.tbsshell.common.SmttServiceCommon;
import java.lang.reflect.Constructor;

public class TBSShellFactory
{
  public static final boolean BUILD_MODE_PARTNER = true;
  private static SmttServiceCommon mServiceCommon;
  private static ISmttServiceMtt mServiceMtt;
  private static ISmttServicePartner mServicePartner;
  private static Object mSmttServiceLock = new Object();
  private static TBSShellDelegate mTbsShellDelegate;
  private static Object mTbsShellLock = new Object();
  private static Object mWebCoreReflectionLock = new Object();
  private static IWebCoreReflectionPartner mWebCoreReflectionPartner;
  
  public static SmttServiceCommon getSmttServiceCommon()
  {
    if (mServiceCommon == null)
    {
      mServiceCommon = (SmttServiceCommon)getSmttServicePartner();
      SmttServiceCommon localSmttServiceCommon = mServiceCommon;
      if (localSmttServiceCommon != null) {
        localSmttServiceCommon.setCurrentApnType(Apn.getApnNameWithBSSID(Apn.getApnTypeS()));
      }
    }
    return mServiceCommon;
  }
  
  public static ISmttServiceMtt getSmttServiceMtt()
  {
    try
    {
      ISmttServiceMtt localISmttServiceMtt = mServiceMtt;
      localISmttServiceMtt = mServiceMtt;
      return localISmttServiceMtt;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static ISmttServicePartner getSmttServicePartner()
  {
    try
    {
      ISmttServicePartner localISmttServicePartner1 = mServicePartner;
      if (localISmttServicePartner1 == null) {
        try
        {
          mServicePartner = (ISmttServicePartner)Class.forName("com.tencent.tbs.tbsshell.partner.SmttServicePartner").getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Throwable localThrowable)
        {
          throw new RuntimeException(localThrowable);
        }
      }
      ISmttServicePartner localISmttServicePartner2 = mServicePartner;
      return localISmttServicePartner2;
    }
    finally {}
  }
  
  public static TBSShellDelegate getTbsShellDelegate()
  {
    try
    {
      TBSShellDelegate localTBSShellDelegate1 = mTbsShellDelegate;
      if (localTBSShellDelegate1 == null) {
        try
        {
          mTbsShellDelegate = (TBSShellDelegate)Class.forName("com.tencent.tbs.tbsshell.partner.TBSShellPartner").getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Throwable localThrowable)
        {
          localThrowable.printStackTrace();
          throw new RuntimeException(localThrowable);
        }
      }
      TBSShellDelegate localTBSShellDelegate2 = mTbsShellDelegate;
      return localTBSShellDelegate2;
    }
    finally {}
  }
  
  public static IWebCoreReflectionPartner getWebCoreProxyPartner()
  {
    try
    {
      if (mWebCoreReflectionPartner == null)
      {
        IWebCoreReflectionPartner localIWebCoreReflectionPartner1 = mWebCoreReflectionPartner;
        if (localIWebCoreReflectionPartner1 == null) {
          try
          {
            mWebCoreReflectionPartner = (IWebCoreReflectionPartner)Class.forName("com.tencent.tbs.tbsshell.partner.WebCoreReflectionPartnerImpl").getConstructor(new Class[0]).newInstance(new Object[0]);
          }
          catch (Throwable localThrowable)
          {
            throw new RuntimeException(localThrowable);
          }
        }
      }
      IWebCoreReflectionPartner localIWebCoreReflectionPartner2 = mWebCoreReflectionPartner;
      return localIWebCoreReflectionPartner2;
    }
    finally {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\TBSShellFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */