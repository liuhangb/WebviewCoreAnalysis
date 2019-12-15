package org.chromium.content.browser;

import android.view.View;

public abstract interface ContentVideoViewEmbedder
{
  public abstract void enterFullscreenVideo(View paramView, boolean paramBoolean);
  
  public abstract void exitFullscreenVideo();
  
  public abstract void fullscreenVideoLoaded();
  
  public abstract void setSystemUiVisibility(boolean paramBoolean);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\ContentVideoViewEmbedder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */