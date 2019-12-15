package org.chromium.tencent.ui.base;

import android.content.Context;

public class PartnerClipboardProxy
{
  private static PartnerClipboardProxy instance;
  private IPartnerClipboardListener listener;
  
  public static PartnerClipboardProxy getInstance()
  {
    try
    {
      if (instance == null) {
        instance = new PartnerClipboardProxy();
      }
      return instance;
    }
    finally {}
  }
  
  public void clear(Context paramContext)
  {
    IPartnerClipboardListener localIPartnerClipboardListener = this.listener;
    if (localIPartnerClipboardListener != null) {
      localIPartnerClipboardListener.onClear(paramContext);
    }
  }
  
  public String getCoercedText(Context paramContext)
  {
    IPartnerClipboardListener localIPartnerClipboardListener = this.listener;
    if (localIPartnerClipboardListener != null) {
      return localIPartnerClipboardListener.onGetCoercedText(paramContext);
    }
    return "";
  }
  
  public boolean isListenerSeted()
  {
    return this.listener != null;
  }
  
  public void setIPartnerClipboardListener(IPartnerClipboardListener paramIPartnerClipboardListener)
  {
    this.listener = paramIPartnerClipboardListener;
  }
  
  public void setText(Context paramContext, String paramString)
  {
    IPartnerClipboardListener localIPartnerClipboardListener = this.listener;
    if (localIPartnerClipboardListener != null) {
      localIPartnerClipboardListener.onSetText(paramContext, paramString);
    }
  }
  
  public static abstract interface IPartnerClipboardListener
  {
    public abstract void onClear(Context paramContext);
    
    public abstract String onGetCoercedText(Context paramContext);
    
    public abstract void onSetText(Context paramContext, String paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\ui\base\PartnerClipboardProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */