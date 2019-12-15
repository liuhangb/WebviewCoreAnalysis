package org.chromium.net;

import org.chromium.base.ApplicationStatus;
import org.chromium.base.ApplicationStatus.ApplicationStateListener;
import org.chromium.base.VisibleForTesting;

public class RegistrationPolicyApplicationStatus
  extends NetworkChangeNotifierAutoDetect.RegistrationPolicy
  implements ApplicationStatus.ApplicationStateListener
{
  private boolean b;
  
  @VisibleForTesting
  int a()
  {
    return ApplicationStatus.getStateForApplication();
  }
  
  protected void a(NetworkChangeNotifierAutoDetect paramNetworkChangeNotifierAutoDetect)
  {
    super.a(paramNetworkChangeNotifierAutoDetect);
    ApplicationStatus.registerApplicationStateListener(this);
    onApplicationStateChange(a());
  }
  
  protected void destroy()
  {
    if (this.b) {
      return;
    }
    ApplicationStatus.unregisterApplicationStateListener(this);
    this.b = true;
  }
  
  public void onApplicationStateChange(int paramInt)
  {
    if (paramInt == 1)
    {
      register();
      return;
    }
    if (paramInt == 2) {
      unregister();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\net\RegistrationPolicyApplicationStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */