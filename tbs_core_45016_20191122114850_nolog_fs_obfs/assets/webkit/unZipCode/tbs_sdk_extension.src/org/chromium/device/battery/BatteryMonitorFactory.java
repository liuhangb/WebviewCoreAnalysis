package org.chromium.device.battery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.chromium.base.Log;
import org.chromium.base.ThreadUtils;
import org.chromium.device.mojom.BatteryMonitor;
import org.chromium.device.mojom.BatteryStatus;
import org.chromium.services.service_manager.InterfaceFactory;

public class BatteryMonitorFactory
  implements InterfaceFactory<BatteryMonitor>
{
  private final HashSet<BatteryMonitorImpl> jdField_a_of_type_JavaUtilHashSet = new HashSet();
  private final BatteryStatusManager.BatteryStatusCallback jdField_a_of_type_OrgChromiumDeviceBatteryBatteryStatusManager$BatteryStatusCallback = new BatteryStatusManager.BatteryStatusCallback()
  {
    public void onBatteryStatusChanged(BatteryStatus paramAnonymousBatteryStatus)
    {
      ThreadUtils.assertOnUiThread();
      Iterator localIterator = new ArrayList(BatteryMonitorFactory.a(BatteryMonitorFactory.this)).iterator();
      while (localIterator.hasNext()) {
        ((BatteryMonitorImpl)localIterator.next()).a(paramAnonymousBatteryStatus);
      }
    }
  };
  private final BatteryStatusManager jdField_a_of_type_OrgChromiumDeviceBatteryBatteryStatusManager = new BatteryStatusManager(this.jdField_a_of_type_OrgChromiumDeviceBatteryBatteryStatusManager$BatteryStatusCallback);
  
  public BatteryMonitor a()
  {
    
    if ((this.jdField_a_of_type_JavaUtilHashSet.isEmpty()) && (!this.jdField_a_of_type_OrgChromiumDeviceBatteryBatteryStatusManager.a())) {
      Log.e("BattMonitorFactory", "BatteryStatusManager failed to start.", new Object[0]);
    }
    BatteryMonitorImpl localBatteryMonitorImpl = new BatteryMonitorImpl(this);
    this.jdField_a_of_type_JavaUtilHashSet.add(localBatteryMonitorImpl);
    return localBatteryMonitorImpl;
  }
  
  void a(BatteryMonitorImpl paramBatteryMonitorImpl)
  {
    
    if ((!jdField_a_of_type_Boolean) && (!this.jdField_a_of_type_JavaUtilHashSet.contains(paramBatteryMonitorImpl))) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_JavaUtilHashSet.remove(paramBatteryMonitorImpl);
    if (this.jdField_a_of_type_JavaUtilHashSet.isEmpty()) {
      this.jdField_a_of_type_OrgChromiumDeviceBatteryBatteryStatusManager.a();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\battery\BatteryMonitorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */