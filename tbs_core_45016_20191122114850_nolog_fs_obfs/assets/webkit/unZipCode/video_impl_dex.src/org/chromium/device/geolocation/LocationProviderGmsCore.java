package org.chromium.device.geolocation;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import org.chromium.base.ThreadUtils;

public class LocationProviderGmsCore
  implements LocationProviderFactory.LocationProvider
{
  private static final String TAG = "cr_LocationProvider";
  private static final long UPDATE_INTERVAL_FAST_MS = 500L;
  private static final long UPDATE_INTERVAL_MS = 1000L;
  private boolean mEnablehighAccuracy;
  
  LocationProviderGmsCore(Context paramContext) {}
  
  public static boolean isGooglePlayServicesAvailable(Context paramContext)
  {
    return false;
  }
  
  public boolean isRunning()
  {
    return false;
  }
  
  public void onConnected(Bundle paramBundle) {}
  
  public void onConnectionSuspended(int paramInt) {}
  
  public void onLocationChanged(Location paramLocation)
  {
    LocationProviderAdapter.onNewLocationAvailable(paramLocation);
  }
  
  public void start(boolean paramBoolean) {}
  
  public void stop() {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\geolocation\LocationProviderGmsCore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */