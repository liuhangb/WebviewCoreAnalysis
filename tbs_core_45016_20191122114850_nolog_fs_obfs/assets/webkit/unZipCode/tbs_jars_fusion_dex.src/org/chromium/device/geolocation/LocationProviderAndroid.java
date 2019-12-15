package org.chromium.device.geolocation;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import java.util.List;
import org.chromium.base.ContextUtils;
import org.chromium.base.Log;
import org.chromium.base.ThreadUtils;
import org.chromium.base.VisibleForTesting;

public class LocationProviderAndroid
  implements LocationListener, LocationProviderFactory.LocationProvider
{
  private static final String TAG = "cr_LocationProvider";
  private boolean mIsRunning;
  private LocationManager mLocationManager;
  
  private void createLocationManagerIfNeeded()
  {
    if (this.mLocationManager != null) {
      return;
    }
    this.mLocationManager = ((LocationManager)ContextUtils.getApplicationContext().getSystemService("location"));
    if (this.mLocationManager == null) {
      Log.e("cr_LocationProvider", "Could not get location manager.", new Object[0]);
    }
  }
  
  private boolean isOnlyPassiveLocationProviderEnabled()
  {
    List localList = this.mLocationManager.getProviders(true);
    return (localList != null) && (localList.size() == 1) && (((String)localList.get(0)).equals("passive"));
  }
  
  private void registerForLocationUpdates(boolean paramBoolean)
  {
    createLocationManagerIfNeeded();
    if (usePassiveOneShotLocation()) {
      return;
    }
    this.mIsRunning = true;
    try
    {
      Criteria localCriteria = new Criteria();
      if (paramBoolean) {
        localCriteria.setAccuracy(1);
      }
      this.mLocationManager.requestLocationUpdates(0L, 0.0F, localCriteria, this, ThreadUtils.getUiThreadLooper());
      return;
    }
    catch (SecurityException localSecurityException)
    {
      for (;;) {}
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;) {}
    }
    Log.e("cr_LocationProvider", "Caught IllegalArgumentException registering for location updates.", new Object[0]);
    unregisterFromLocationUpdates();
    return;
    Log.e("cr_LocationProvider", "Caught security exception while registering for location updates from the system. The application does not have sufficient geolocation permissions.", new Object[0]);
    unregisterFromLocationUpdates();
    LocationProviderAdapter.newErrorAvailable("application does not have sufficient geolocation permissions.");
  }
  
  private void unregisterFromLocationUpdates()
  {
    if (!this.mIsRunning) {
      return;
    }
    this.mIsRunning = false;
    this.mLocationManager.removeUpdates(this);
  }
  
  private boolean usePassiveOneShotLocation()
  {
    if (!isOnlyPassiveLocationProviderEnabled()) {
      return false;
    }
    final Location localLocation = this.mLocationManager.getLastKnownLocation("passive");
    if (localLocation != null) {
      ThreadUtils.runOnUiThread(new Runnable()
      {
        public void run()
        {
          LocationProviderAdapter.onNewLocationAvailable(localLocation);
        }
      });
    }
    return true;
  }
  
  public boolean isRunning()
  {
    ThreadUtils.assertOnUiThread();
    return this.mIsRunning;
  }
  
  public void onLocationChanged(Location paramLocation)
  {
    if (this.mIsRunning) {
      LocationProviderAdapter.onNewLocationAvailable(paramLocation);
    }
  }
  
  public void onProviderDisabled(String paramString) {}
  
  public void onProviderEnabled(String paramString) {}
  
  public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle) {}
  
  @VisibleForTesting
  public void setLocationManagerForTesting(LocationManager paramLocationManager)
  {
    this.mLocationManager = paramLocationManager;
  }
  
  public void start(boolean paramBoolean)
  {
    ThreadUtils.assertOnUiThread();
    unregisterFromLocationUpdates();
    registerForLocationUpdates(paramBoolean);
  }
  
  public void stop()
  {
    ThreadUtils.assertOnUiThread();
    unregisterFromLocationUpdates();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\geolocation\LocationProviderAndroid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */