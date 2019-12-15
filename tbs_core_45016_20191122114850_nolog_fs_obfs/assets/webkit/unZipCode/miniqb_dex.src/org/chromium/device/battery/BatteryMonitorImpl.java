package org.chromium.device.battery;

import org.chromium.base.Log;
import org.chromium.device.mojom.BatteryMonitor;
import org.chromium.device.mojom.BatteryMonitor.QueryNextStatusResponse;
import org.chromium.device.mojom.BatteryStatus;
import org.chromium.mojo.system.MojoException;

public class BatteryMonitorImpl
  implements BatteryMonitor
{
  private final BatteryMonitorFactory jdField_a_of_type_OrgChromiumDeviceBatteryBatteryMonitorFactory;
  private BatteryMonitor.QueryNextStatusResponse jdField_a_of_type_OrgChromiumDeviceMojomBatteryMonitor$QueryNextStatusResponse;
  private BatteryStatus jdField_a_of_type_OrgChromiumDeviceMojomBatteryStatus;
  private boolean jdField_a_of_type_Boolean;
  private boolean b;
  
  public BatteryMonitorImpl(BatteryMonitorFactory paramBatteryMonitorFactory)
  {
    this.jdField_a_of_type_OrgChromiumDeviceBatteryBatteryMonitorFactory = paramBatteryMonitorFactory;
    this.jdField_a_of_type_Boolean = false;
    this.b = true;
  }
  
  private void b()
  {
    if (this.b)
    {
      this.jdField_a_of_type_OrgChromiumDeviceBatteryBatteryMonitorFactory.a(this);
      this.b = false;
    }
  }
  
  void a()
  {
    this.jdField_a_of_type_OrgChromiumDeviceMojomBatteryMonitor$QueryNextStatusResponse.call(this.jdField_a_of_type_OrgChromiumDeviceMojomBatteryStatus);
    this.jdField_a_of_type_OrgChromiumDeviceMojomBatteryMonitor$QueryNextStatusResponse = null;
    this.jdField_a_of_type_Boolean = false;
  }
  
  void a(BatteryStatus paramBatteryStatus)
  {
    this.jdField_a_of_type_OrgChromiumDeviceMojomBatteryStatus = paramBatteryStatus;
    this.jdField_a_of_type_Boolean = true;
    if (this.jdField_a_of_type_OrgChromiumDeviceMojomBatteryMonitor$QueryNextStatusResponse != null) {
      a();
    }
  }
  
  public void close()
  {
    b();
  }
  
  public void onConnectionError(MojoException paramMojoException)
  {
    b();
  }
  
  public void queryNextStatus(BatteryMonitor.QueryNextStatusResponse paramQueryNextStatusResponse)
  {
    if (this.jdField_a_of_type_OrgChromiumDeviceMojomBatteryMonitor$QueryNextStatusResponse != null)
    {
      Log.e("BatteryMonitorImpl", "Overlapped call to queryNextStatus!", new Object[0]);
      b();
      return;
    }
    this.jdField_a_of_type_OrgChromiumDeviceMojomBatteryMonitor$QueryNextStatusResponse = paramQueryNextStatusResponse;
    if (this.jdField_a_of_type_Boolean) {
      a();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\battery\BatteryMonitorImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */