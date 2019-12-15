package org.chromium.content_public.browser;

import android.os.Handler;
import org.chromium.base.annotations.UsedByReflection;

public abstract interface SmartClipProvider
{
  @UsedByReflection("ExternalOemSupport")
  public abstract void extractSmartClipData(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  @UsedByReflection("ExternalOemSupport")
  public abstract void setSmartClipResultHandler(Handler paramHandler);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\SmartClipProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */