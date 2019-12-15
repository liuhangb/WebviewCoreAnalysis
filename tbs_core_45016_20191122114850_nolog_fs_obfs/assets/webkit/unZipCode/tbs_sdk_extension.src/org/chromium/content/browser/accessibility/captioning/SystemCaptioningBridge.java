package org.chromium.content.browser.accessibility.captioning;

public abstract interface SystemCaptioningBridge
{
  public abstract void addListener(SystemCaptioningBridgeListener paramSystemCaptioningBridgeListener);
  
  public abstract void removeListener(SystemCaptioningBridgeListener paramSystemCaptioningBridgeListener);
  
  public abstract void syncToListener(SystemCaptioningBridgeListener paramSystemCaptioningBridgeListener);
  
  public static abstract interface SystemCaptioningBridgeListener
  {
    public abstract void onSystemCaptioningChanged(TextTrackSettings paramTextTrackSettings);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\accessibility\captioning\SystemCaptioningBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */