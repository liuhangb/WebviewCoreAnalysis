package org.chromium.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;

@JNINamespace("base::android")
public class PowerMonitor
{
  private static PowerMonitor jdField_a_of_type_OrgChromiumBasePowerMonitor;
  private boolean b;
  
  private static void b(Intent paramIntent)
  {
    if ((!jdField_a_of_type_Boolean) && (jdField_a_of_type_OrgChromiumBasePowerMonitor == null)) {
      throw new AssertionError();
    }
    int i = paramIntent.getIntExtra("plugged", -1);
    paramIntent = jdField_a_of_type_OrgChromiumBasePowerMonitor;
    boolean bool = true;
    if ((i == 2) || (i == 1)) {
      bool = false;
    }
    paramIntent.b = bool;
    nativeOnBatteryChargingChanged();
  }
  
  public static void create()
  {
    
    if (jdField_a_of_type_OrgChromiumBasePowerMonitor != null) {
      return;
    }
    Context localContext = ContextUtils.getApplicationContext();
    jdField_a_of_type_OrgChromiumBasePowerMonitor = new PowerMonitor();
    Object localObject = X5ApiCompatibilityUtils.x5RegisterReceiver(localContext, null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
    if (localObject != null) {
      b((Intent)localObject);
    }
    localObject = new IntentFilter();
    ((IntentFilter)localObject).addAction("android.intent.action.ACTION_POWER_CONNECTED");
    ((IntentFilter)localObject).addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
    X5ApiCompatibilityUtils.x5RegisterReceiver(localContext, new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
      {
        PowerMonitor.a(paramAnonymousIntent);
      }
    }, (IntentFilter)localObject);
  }
  
  public static void createForTests()
  {
    jdField_a_of_type_OrgChromiumBasePowerMonitor = new PowerMonitor();
  }
  
  @CalledByNative
  private static boolean isBatteryPower()
  {
    if (jdField_a_of_type_OrgChromiumBasePowerMonitor == null) {
      create();
    }
    return jdField_a_of_type_OrgChromiumBasePowerMonitor.b;
  }
  
  private static native void nativeOnBatteryChargingChanged();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\PowerMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */