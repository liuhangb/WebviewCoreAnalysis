package org.chromium.device.battery;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import org.chromium.base.ContextUtils;
import org.chromium.base.Log;
import org.chromium.base.VisibleForTesting;
import org.chromium.device.mojom.BatteryStatus;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;

class BatteryStatusManager
{
  private final BroadcastReceiver jdField_a_of_type_AndroidContentBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      BatteryStatusManager.this.a(paramAnonymousIntent);
    }
  };
  private final IntentFilter jdField_a_of_type_AndroidContentIntentFilter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
  private AndroidBatteryManagerWrapper jdField_a_of_type_OrgChromiumDeviceBatteryBatteryStatusManager$AndroidBatteryManagerWrapper;
  private final BatteryStatusCallback jdField_a_of_type_OrgChromiumDeviceBatteryBatteryStatusManager$BatteryStatusCallback;
  private final boolean b;
  private boolean c;
  
  BatteryStatusManager(BatteryStatusCallback paramBatteryStatusCallback)
  {
    this(paramBatteryStatusCallback, bool, localAndroidBatteryManagerWrapper);
  }
  
  private BatteryStatusManager(BatteryStatusCallback paramBatteryStatusCallback, boolean paramBoolean, AndroidBatteryManagerWrapper paramAndroidBatteryManagerWrapper)
  {
    this.jdField_a_of_type_OrgChromiumDeviceBatteryBatteryStatusManager$BatteryStatusCallback = paramBatteryStatusCallback;
    this.b = paramBoolean;
    this.jdField_a_of_type_OrgChromiumDeviceBatteryBatteryStatusManager$AndroidBatteryManagerWrapper = paramAndroidBatteryManagerWrapper;
  }
  
  private void a(BatteryStatus paramBatteryStatus)
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumDeviceBatteryBatteryStatusManager$AndroidBatteryManagerWrapper == null)) {
      throw new AssertionError();
    }
    double d1 = this.jdField_a_of_type_OrgChromiumDeviceBatteryBatteryStatusManager$AndroidBatteryManagerWrapper.a(4);
    Double.isNaN(d1);
    d1 /= 100.0D;
    double d2 = this.jdField_a_of_type_OrgChromiumDeviceBatteryBatteryStatusManager$AndroidBatteryManagerWrapper.a(1);
    double d3 = this.jdField_a_of_type_OrgChromiumDeviceBatteryBatteryStatusManager$AndroidBatteryManagerWrapper.a(3);
    if (paramBatteryStatus.jdField_a_of_type_Boolean)
    {
      if ((paramBatteryStatus.jdField_a_of_type_Double == Double.POSITIVE_INFINITY) && (d3 > 0.0D))
      {
        Double.isNaN(d2);
        Double.isNaN(d3);
        paramBatteryStatus.jdField_a_of_type_Double = Math.ceil((1.0D - d1) * (d2 / d3) * 3600.0D);
      }
    }
    else if (d3 < 0.0D)
    {
      Double.isNaN(d3);
      d3 = -d3;
      Double.isNaN(d2);
      paramBatteryStatus.b = Math.floor(d1 * (d2 / d3) * 3600.0D);
    }
  }
  
  void a()
  {
    if (this.c)
    {
      X5ApiCompatibilityUtils.x5UnRegisterReceiver(ContextUtils.getApplicationContext(), this.jdField_a_of_type_AndroidContentBroadcastReceiver);
      this.c = false;
    }
  }
  
  @VisibleForTesting
  void a(Intent paramIntent)
  {
    boolean bool = paramIntent.getAction().equals("android.intent.action.BATTERY_CHANGED");
    int i = 0;
    if (!bool)
    {
      Log.e("BatteryStatusManager", "Unexpected intent.", new Object[0]);
      return;
    }
    if (this.b) {
      bool = true;
    } else {
      bool = paramIntent.getBooleanExtra("present", false);
    }
    int j = paramIntent.getIntExtra("plugged", -1);
    if ((bool) && (j != -1))
    {
      int k = paramIntent.getIntExtra("level", -1);
      int m = paramIntent.getIntExtra("scale", -1);
      double d1 = k;
      double d2 = m;
      Double.isNaN(d1);
      Double.isNaN(d2);
      double d3 = d1 / d2;
      d2 = 0.0D;
      if (d3 >= 0.0D)
      {
        d1 = d3;
        if (d3 <= 1.0D) {}
      }
      else
      {
        d1 = 1.0D;
      }
      if (j != 0) {
        bool = true;
      } else {
        bool = false;
      }
      if (paramIntent.getIntExtra("status", -1) == 5) {
        i = 1;
      }
      if ((!bool) || (i == 0)) {
        d2 = Double.POSITIVE_INFINITY;
      }
      paramIntent = new BatteryStatus();
      paramIntent.jdField_a_of_type_Boolean = bool;
      paramIntent.jdField_a_of_type_Double = d2;
      paramIntent.b = Double.POSITIVE_INFINITY;
      paramIntent.c = d1;
      if (this.jdField_a_of_type_OrgChromiumDeviceBatteryBatteryStatusManager$AndroidBatteryManagerWrapper != null) {
        a(paramIntent);
      }
      this.jdField_a_of_type_OrgChromiumDeviceBatteryBatteryStatusManager$BatteryStatusCallback.onBatteryStatusChanged(paramIntent);
      return;
    }
    this.jdField_a_of_type_OrgChromiumDeviceBatteryBatteryStatusManager$BatteryStatusCallback.onBatteryStatusChanged(new BatteryStatus());
  }
  
  boolean a()
  {
    if ((!this.c) && (X5ApiCompatibilityUtils.x5RegisterReceiver(ContextUtils.getApplicationContext(), this.jdField_a_of_type_AndroidContentBroadcastReceiver, this.jdField_a_of_type_AndroidContentIntentFilter) != null)) {
      this.c = true;
    }
    return this.c;
  }
  
  @VisibleForTesting
  static class AndroidBatteryManagerWrapper
  {
    private final BatteryManager a;
    
    protected AndroidBatteryManagerWrapper(BatteryManager paramBatteryManager)
    {
      this.a = paramBatteryManager;
    }
    
    @TargetApi(21)
    public int a(int paramInt)
    {
      return this.a.getIntProperty(paramInt);
    }
  }
  
  static abstract interface BatteryStatusCallback
  {
    public abstract void onBatteryStatusChanged(BatteryStatus paramBatteryStatus);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\battery\BatteryStatusManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */