package com.tencent.smtt.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import com.tencent.smtt.webkit.ContextHolder;
import java.lang.reflect.Method;

public class i
{
  private static volatile i jdField_a_of_type_ComTencentSmttUtilI;
  private Context jdField_a_of_type_AndroidContentContext;
  private TelephonyManager jdField_a_of_type_AndroidTelephonyTelephonyManager;
  private a jdField_a_of_type_ComTencentSmttUtilI$a;
  
  private i()
  {
    if (Looper.myLooper() == null) {
      Looper.prepare();
    }
    this.jdField_a_of_type_AndroidContentContext = ContextHolder.getInstance().getApplicationContext();
    Context localContext = this.jdField_a_of_type_AndroidContentContext;
    if (localContext != null) {
      this.jdField_a_of_type_AndroidTelephonyTelephonyManager = ((TelephonyManager)localContext.getSystemService("phone"));
    }
    this.jdField_a_of_type_ComTencentSmttUtilI$a = new a(null);
  }
  
  private int a()
  {
    Object localObject = this.jdField_a_of_type_AndroidContentContext;
    if (localObject != null) {
      localObject = (WifiManager)((Context)localObject).getSystemService("wifi");
    }
    try
    {
      localObject = ((WifiManager)localObject).getConnectionInfo();
      if ((localObject != null) && (((WifiInfo)localObject).getBSSID() != null))
      {
        ((WifiInfo)localObject).getSSID();
        int i = WifiManager.calculateSignalLevel(((WifiInfo)localObject).getRssi(), 5);
        return i;
      }
      return 0;
    }
    catch (Exception localException) {}
    return 0;
    return 0;
  }
  
  public static i a()
  {
    if (jdField_a_of_type_ComTencentSmttUtilI == null) {
      try
      {
        if (jdField_a_of_type_ComTencentSmttUtilI == null) {
          jdField_a_of_type_ComTencentSmttUtilI = new i();
        }
      }
      finally {}
    }
    return jdField_a_of_type_ComTencentSmttUtilI;
  }
  
  private int b()
  {
    TelephonyManager localTelephonyManager = this.jdField_a_of_type_AndroidTelephonyTelephonyManager;
    if (localTelephonyManager != null)
    {
      localTelephonyManager.listen(this.jdField_a_of_type_ComTencentSmttUtilI$a, 256);
      int i = this.jdField_a_of_type_ComTencentSmttUtilI$a.a();
      this.jdField_a_of_type_AndroidTelephonyTelephonyManager.listen(this.jdField_a_of_type_ComTencentSmttUtilI$a, 0);
      return i;
    }
    return 0;
  }
  
  public int a(boolean paramBoolean)
  {
    if (paramBoolean) {
      return a();
    }
    return b();
  }
  
  private class a
    extends PhoneStateListener
  {
    private int jdField_a_of_type_Int;
    
    private a() {}
    
    public int a()
    {
      return this.jdField_a_of_type_Int;
    }
    
    public void onSignalStrengthsChanged(SignalStrength paramSignalStrength)
    {
      super.onSignalStrengthsChanged(paramSignalStrength);
      try
      {
        Method localMethod = SignalStrength.class.getDeclaredMethod("getLevel", new Class[0]);
        localMethod.setAccessible(true);
        this.jdField_a_of_type_Int = ((Integer)localMethod.invoke(paramSignalStrength, new Object[0])).intValue();
        return;
      }
      catch (Exception paramSignalStrength)
      {
        this.jdField_a_of_type_Int = 0;
        paramSignalStrength.printStackTrace();
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */