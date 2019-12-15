package com.tencent.common.http;

import com.tencent.common.manifest.AppManifest;
import com.tencent.common.manifest.annotation.Extension;

public class RequesterFactory
{
  public static final int IMPL_HC = 1;
  public static final int IMPL_JDK = 0;
  public static final int IMPL_SYS = 2;
  private static int jdField_a_of_type_Int = 1;
  private static ICookieManagerFactory jdField_a_of_type_ComTencentCommonHttpRequesterFactory$ICookieManagerFactory;
  private static IFlowObsever jdField_a_of_type_ComTencentCommonHttpRequesterFactory$IFlowObsever;
  private static IRequestObsever jdField_a_of_type_ComTencentCommonHttpRequesterFactory$IRequestObsever;
  private static IRequesterFactory jdField_a_of_type_ComTencentCommonHttpRequesterFactory$IRequesterFactory;
  
  public static IFlowObsever getFlowObsever()
  {
    return jdField_a_of_type_ComTencentCommonHttpRequesterFactory$IFlowObsever;
  }
  
  public static ICookieManagerFactory getICookieManagerFactory()
  {
    ICookieManagerFactory localICookieManagerFactory = jdField_a_of_type_ComTencentCommonHttpRequesterFactory$ICookieManagerFactory;
    if (localICookieManagerFactory != null) {
      return localICookieManagerFactory;
    }
    try
    {
      localICookieManagerFactory = jdField_a_of_type_ComTencentCommonHttpRequesterFactory$ICookieManagerFactory;
      if (localICookieManagerFactory == null) {
        try
        {
          jdField_a_of_type_ComTencentCommonHttpRequesterFactory$ICookieManagerFactory = (ICookieManagerFactory)AppManifest.getInstance().queryExtension(ICookieManagerFactory.class, null);
        }
        catch (Throwable localThrowable)
        {
          jdField_a_of_type_ComTencentCommonHttpRequesterFactory$ICookieManagerFactory = null;
          localThrowable.printStackTrace();
        }
      }
      return jdField_a_of_type_ComTencentCommonHttpRequesterFactory$ICookieManagerFactory;
    }
    finally {}
  }
  
  public static MttRequestBase getMttRequestBase()
  {
    if (getICookieManagerFactory() != null)
    {
      MttRequest localMttRequest = new MttRequest();
      localMttRequest.setCookieManager(getICookieManagerFactory().getCookieManager());
      return localMttRequest;
    }
    return new MttRequestBase();
  }
  
  public static IRequestObsever getRequestObsever()
  {
    return jdField_a_of_type_ComTencentCommonHttpRequesterFactory$IRequestObsever;
  }
  
  public static Requester getRequester()
  {
    Object localObject = jdField_a_of_type_ComTencentCommonHttpRequesterFactory$IRequesterFactory;
    if (localObject != null) {
      localObject = ((IRequesterFactory)localObject).getRequester(jdField_a_of_type_Int);
    } else {
      localObject = null;
    }
    if (localObject != null) {
      return (Requester)localObject;
    }
    int i = jdField_a_of_type_Int;
    if ((i != 0) && (i != 2))
    {
      if (getICookieManagerFactory() != null)
      {
        localObject = new HttpClientRequester();
        ((Requester)localObject).setCookieManager(getICookieManagerFactory().getCookieManager());
        return (Requester)localObject;
      }
      return new HttpClientRequesterBase();
    }
    if (getICookieManagerFactory() != null)
    {
      localObject = new HttpRequester();
      ((Requester)localObject).setCookieManager(getICookieManagerFactory().getCookieManager());
      return (Requester)localObject;
    }
    return new HttpRequesterBase();
  }
  
  public static Requester getRequester(int paramInt)
  {
    Object localObject = jdField_a_of_type_ComTencentCommonHttpRequesterFactory$IRequesterFactory;
    if (localObject != null) {
      localObject = ((IRequesterFactory)localObject).getRequester(paramInt);
    } else {
      localObject = null;
    }
    if (localObject != null) {
      return (Requester)localObject;
    }
    if ((paramInt != 0) && (paramInt != 2))
    {
      if (getICookieManagerFactory() != null)
      {
        localObject = new HttpClientRequester();
        ((Requester)localObject).setCookieManager(getICookieManagerFactory().getCookieManager());
        return (Requester)localObject;
      }
      return new HttpClientRequesterBase();
    }
    if (getICookieManagerFactory() != null)
    {
      localObject = new HttpRequester();
      ((Requester)localObject).setCookieManager(getICookieManagerFactory().getCookieManager());
      return (Requester)localObject;
    }
    return new HttpRequesterBase();
  }
  
  public static Requester getRequester(int paramInt1, int paramInt2)
  {
    return getRequester(paramInt2);
  }
  
  public static Requester getRequesterBase(int paramInt)
  {
    Object localObject = jdField_a_of_type_ComTencentCommonHttpRequesterFactory$IRequesterFactory;
    if (localObject != null) {
      localObject = ((IRequesterFactory)localObject).getRequesterBase(paramInt);
    } else {
      localObject = null;
    }
    if (localObject != null) {
      return (Requester)localObject;
    }
    if (paramInt == 1) {
      return new HttpClientRequesterBase();
    }
    return new HttpRequesterBase();
  }
  
  public static void setCookieFactory(ICookieManagerFactory paramICookieManagerFactory)
  {
    jdField_a_of_type_ComTencentCommonHttpRequesterFactory$ICookieManagerFactory = paramICookieManagerFactory;
  }
  
  public static void setFlowObsever(IFlowObsever paramIFlowObsever)
  {
    jdField_a_of_type_ComTencentCommonHttpRequesterFactory$IFlowObsever = paramIFlowObsever;
  }
  
  public static void setRequestObsever(IRequestObsever paramIRequestObsever)
  {
    jdField_a_of_type_ComTencentCommonHttpRequesterFactory$IRequestObsever = paramIRequestObsever;
  }
  
  public static void setRequesterFactory(IRequesterFactory paramIRequesterFactory)
  {
    jdField_a_of_type_ComTencentCommonHttpRequesterFactory$IRequesterFactory = paramIRequesterFactory;
  }
  
  @Extension
  public static abstract interface ICookieManagerFactory
  {
    public abstract IHttpCookieManager getCookieManager();
  }
  
  public static abstract interface IFlowObsever
  {
    public abstract void onRequestFlow(MttRequestBase paramMttRequestBase, int paramInt1, int paramInt2);
  }
  
  public static abstract interface IRequestObsever
  {
    public abstract void onRequestComplete(MttRequestBase paramMttRequestBase);
  }
  
  public static abstract interface IRequesterFactory
  {
    public abstract Requester getRequester(int paramInt);
    
    public abstract Requester getRequesterBase(int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\RequesterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */