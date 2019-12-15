package org.chromium.services.device;

import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.device.battery.BatteryMonitorFactory;
import org.chromium.device.mojom.BatteryMonitor;
import org.chromium.device.mojom.VibrationManager;
import org.chromium.device.vibration.VibrationManagerImpl.Factory;
import org.chromium.mojo.system.Core;
import org.chromium.mojo.system.UntypedHandle;
import org.chromium.mojo.system.impl.CoreImpl;
import org.chromium.services.service_manager.InterfaceRegistry;

@JNINamespace("device")
class InterfaceRegistrar
{
  @CalledByNative
  static void createInterfaceRegistryForContext(int paramInt)
  {
    InterfaceRegistry localInterfaceRegistry = InterfaceRegistry.a(CoreImpl.a().acquireNativeHandle(paramInt).toMessagePipeHandle());
    localInterfaceRegistry.a(BatteryMonitor.a, new BatteryMonitorFactory());
    localInterfaceRegistry.a(VibrationManager.a, new VibrationManagerImpl.Factory());
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\services\device\InterfaceRegistrar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */