package org.chromium.content.browser.accessibility.captioning;

import android.content.Context;
import android.os.Build.VERSION;

public class CaptioningBridgeFactory
{
  public static SystemCaptioningBridge getSystemCaptioningBridge(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return KitKatCaptioningBridge.getInstance(paramContext);
    }
    return new EmptyCaptioningBridge();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\accessibility\captioning\CaptioningBridgeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */