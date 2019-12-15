package org.chromium.content.browser;

import android.content.Context;
import org.chromium.base.CommandLine;
import org.chromium.ui.base.DeviceFormFactor;

public class DeviceUtils
{
  public static void addDeviceSpecificUserAgentSwitch(Context paramContext)
  {
    if (!DeviceFormFactor.isTablet()) {
      CommandLine.getInstance().appendSwitch("use-mobile-user-agent");
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\DeviceUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */