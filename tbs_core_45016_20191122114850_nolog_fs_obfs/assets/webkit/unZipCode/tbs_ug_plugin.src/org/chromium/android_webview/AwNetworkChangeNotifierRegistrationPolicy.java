package org.chromium.android_webview;

import org.chromium.net.NetworkChangeNotifierAutoDetect;
import org.chromium.net.NetworkChangeNotifierAutoDetect.RegistrationPolicy;

public class AwNetworkChangeNotifierRegistrationPolicy
  extends NetworkChangeNotifierAutoDetect.RegistrationPolicy
  implements AwContentsLifecycleNotifier.Observer
{
  protected void a(NetworkChangeNotifierAutoDetect paramNetworkChangeNotifierAutoDetect)
  {
    super.a(paramNetworkChangeNotifierAutoDetect);
    AwContentsLifecycleNotifier.addObserver(this);
  }
  
  protected void destroy()
  {
    AwContentsLifecycleNotifier.removeObserver(this);
  }
  
  public void onFirstWebViewCreated()
  {
    register();
  }
  
  public void onLastWebViewDestroyed()
  {
    unregister();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwNetworkChangeNotifierRegistrationPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */