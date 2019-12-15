package com.tencent.tbs.core.webkit;

import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public abstract class ClientCertRequest
{
  public abstract void cancel();
  
  public abstract String getHost();
  
  public abstract String[] getKeyTypes();
  
  public abstract int getPort();
  
  public abstract Principal[] getPrincipals();
  
  public abstract void ignore();
  
  public abstract void proceed(PrivateKey paramPrivateKey, X509Certificate[] paramArrayOfX509Certificate);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\ClientCertRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */