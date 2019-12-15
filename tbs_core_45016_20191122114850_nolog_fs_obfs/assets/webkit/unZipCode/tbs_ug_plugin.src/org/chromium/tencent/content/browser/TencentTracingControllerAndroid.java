package org.chromium.tencent.content.browser;

import android.content.Context;
import org.chromium.content.browser.TracingControllerAndroid;

public class TencentTracingControllerAndroid
  extends TracingControllerAndroid
{
  public TencentTracingControllerAndroid(Context paramContext)
  {
    super(paramContext);
  }
  
  public void setTbsMiniProgramDebugEnable(boolean paramBoolean)
  {
    initializeNativeControllerIfNeeded();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\content\browser\TencentTracingControllerAndroid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */