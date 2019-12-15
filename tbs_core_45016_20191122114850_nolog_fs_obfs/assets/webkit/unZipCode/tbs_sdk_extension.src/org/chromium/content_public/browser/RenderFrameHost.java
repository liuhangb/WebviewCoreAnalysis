package org.chromium.content_public.browser;

import org.chromium.base.Callback;
import org.chromium.services.service_manager.InterfaceProvider;

public abstract interface RenderFrameHost
{
  public abstract void getCanonicalUrlForSharing(Callback<String> paramCallback);
  
  public abstract String getLastCommittedURL();
  
  public abstract InterfaceProvider getRemoteInterfaces();
  
  public abstract void setHasReceivedUserGesture();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\RenderFrameHost.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */