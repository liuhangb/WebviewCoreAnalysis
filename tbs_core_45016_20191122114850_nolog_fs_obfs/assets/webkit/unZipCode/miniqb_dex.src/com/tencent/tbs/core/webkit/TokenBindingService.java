package com.tencent.tbs.core.webkit;

import android.net.Uri;
import java.security.KeyPair;

public abstract class TokenBindingService
{
  public static final String KEY_ALGORITHM_ECDSAP256 = "ECDSAP256";
  public static final String KEY_ALGORITHM_RSA2048_PKCS_1_5 = "RSA2048_PKCS_1.5";
  public static final String KEY_ALGORITHM_RSA2048_PSS = "RSA2048PSS";
  
  public static TokenBindingService getInstance()
  {
    return WebViewFactory.getProvider().getTokenBindingService();
  }
  
  public abstract void deleteAllKeys(ValueCallback<Boolean> paramValueCallback);
  
  public abstract void deleteKey(Uri paramUri, ValueCallback<Boolean> paramValueCallback);
  
  public abstract void enableTokenBinding();
  
  public abstract void getKey(Uri paramUri, String[] paramArrayOfString, ValueCallback<TokenBindingKey> paramValueCallback);
  
  public static abstract class TokenBindingKey
  {
    public abstract String getAlgorithm();
    
    public abstract KeyPair getKeyPair();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\TokenBindingService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */