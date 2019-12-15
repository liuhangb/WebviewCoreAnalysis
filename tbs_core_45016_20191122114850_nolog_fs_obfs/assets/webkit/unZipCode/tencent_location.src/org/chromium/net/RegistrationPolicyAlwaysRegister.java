package org.chromium.net;

public class RegistrationPolicyAlwaysRegister
  extends NetworkChangeNotifierAutoDetect.RegistrationPolicy
{
  protected void a(NetworkChangeNotifierAutoDetect paramNetworkChangeNotifierAutoDetect)
  {
    super.a(paramNetworkChangeNotifierAutoDetect);
    register();
  }
  
  protected void destroy() {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\net\RegistrationPolicyAlwaysRegister.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */