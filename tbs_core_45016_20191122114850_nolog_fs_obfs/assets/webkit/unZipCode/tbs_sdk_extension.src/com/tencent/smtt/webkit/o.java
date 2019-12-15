package com.tencent.smtt.webkit;

import android.location.Location;
import android.os.Bundle;
import android.webkit.ValueCallback;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import org.chromium.device.geolocation.LocationProviderAdapter;
import org.chromium.device.geolocation.LocationProviderFactory.LocationProvider;

public class o
  implements LocationProviderFactory.LocationProvider
{
  private static o jdField_a_of_type_ComTencentSmttWebkitO;
  private boolean jdField_a_of_type_Boolean;
  
  public static o a()
  {
    if (jdField_a_of_type_ComTencentSmttWebkitO == null) {
      jdField_a_of_type_ComTencentSmttWebkitO = new o();
    }
    return jdField_a_of_type_ComTencentSmttWebkitO;
  }
  
  private void a(Location paramLocation)
  {
    LocationProviderAdapter.onNewLocationAvailable(paramLocation);
  }
  
  public boolean isRunning()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public void start(boolean paramBoolean)
  {
    ValueCallback local1 = new ValueCallback()
    {
      public void a(Location paramAnonymousLocation)
      {
        o.a(o.this, paramAnonymousLocation);
      }
    };
    ValueCallback local2 = new ValueCallback()
    {
      public void a(Bundle paramAnonymousBundle)
      {
        LocationProviderAdapter.newErrorAvailable(paramAnonymousBundle.getString("message"));
      }
    };
    SmttServiceProxy.getInstance().onGeolocationStopUpdating();
    SmttServiceProxy.getInstance().onGeolocationStartUpdating(local1, local2, paramBoolean);
    this.jdField_a_of_type_Boolean = true;
  }
  
  public void stop()
  {
    SmttServiceProxy.getInstance().onGeolocationStopUpdating();
    this.jdField_a_of_type_Boolean = false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\o.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */