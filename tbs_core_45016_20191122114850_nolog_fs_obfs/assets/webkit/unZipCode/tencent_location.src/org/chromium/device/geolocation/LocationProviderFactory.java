package org.chromium.device.geolocation;

import org.chromium.base.ContextUtils;
import org.chromium.base.VisibleForTesting;

public class LocationProviderFactory
{
  private static LocationProvider sProviderImpl;
  private static boolean sUseGmsCoreLocationProvider;
  
  public static LocationProvider create()
  {
    LocationProvider localLocationProvider = sProviderImpl;
    if (localLocationProvider != null) {
      return localLocationProvider;
    }
    if ((sUseGmsCoreLocationProvider) && (LocationProviderGmsCore.isGooglePlayServicesAvailable(ContextUtils.getApplicationContext()))) {
      sProviderImpl = new LocationProviderGmsCore(ContextUtils.getApplicationContext());
    } else {
      sProviderImpl = new LocationProviderAndroid();
    }
    return sProviderImpl;
  }
  
  @VisibleForTesting
  public static void setLocationProviderImpl(LocationProvider paramLocationProvider)
  {
    sProviderImpl = paramLocationProvider;
  }
  
  public static void useGmsCoreLocationProvider()
  {
    sUseGmsCoreLocationProvider = true;
  }
  
  public static abstract interface LocationProvider
  {
    public abstract boolean isRunning();
    
    public abstract void start(boolean paramBoolean);
    
    public abstract void stop();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\geolocation\LocationProviderFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */