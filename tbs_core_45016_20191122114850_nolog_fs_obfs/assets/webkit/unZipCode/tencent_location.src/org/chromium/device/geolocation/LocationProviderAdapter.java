package org.chromium.device.geolocation;

import android.location.Location;
import java.util.concurrent.FutureTask;
import org.chromium.base.Log;
import org.chromium.base.ThreadUtils;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.MainDex;

@VisibleForTesting
@MainDex
public class LocationProviderAdapter
{
  private static final String TAG = "cr_LocationProvider";
  private LocationProviderFactory.LocationProvider mImpl = LocationProviderFactory.create();
  
  @CalledByNative
  public static LocationProviderAdapter create()
  {
    return new LocationProviderAdapter();
  }
  
  private static native void nativeNewErrorAvailable(String paramString);
  
  private static native void nativeNewLocationAvailable(double paramDouble1, double paramDouble2, double paramDouble3, boolean paramBoolean1, double paramDouble4, boolean paramBoolean2, double paramDouble5, boolean paramBoolean3, double paramDouble6, boolean paramBoolean4, double paramDouble7);
  
  public static void newErrorAvailable(String paramString)
  {
    Log.e("cr_LocationProvider", "newErrorAvailable %s", new Object[] { paramString });
    nativeNewErrorAvailable(paramString);
  }
  
  public static void onNewLocationAvailable(Location paramLocation)
  {
    double d1 = paramLocation.getLatitude();
    double d2 = paramLocation.getLongitude();
    double d3 = paramLocation.getTime();
    Double.isNaN(d3);
    nativeNewLocationAvailable(d1, d2, d3 / 1000.0D, paramLocation.hasAltitude(), paramLocation.getAltitude(), paramLocation.hasAccuracy(), paramLocation.getAccuracy(), paramLocation.hasBearing(), paramLocation.getBearing(), paramLocation.hasSpeed(), paramLocation.getSpeed());
  }
  
  public boolean isRunning()
  {
    return this.mImpl.isRunning();
  }
  
  @CalledByNative
  public void start(final boolean paramBoolean)
  {
    ThreadUtils.runOnUiThread(new FutureTask(new Runnable()
    {
      public void run()
      {
        LocationProviderAdapter.this.mImpl.start(paramBoolean);
      }
    }, null));
  }
  
  @CalledByNative
  public void stop()
  {
    ThreadUtils.runOnUiThread(new FutureTask(new Runnable()
    {
      public void run()
      {
        LocationProviderAdapter.this.mImpl.stop();
      }
    }, null));
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\geolocation\LocationProviderAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */