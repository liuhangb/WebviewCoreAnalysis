package org.chromium.android_webview.gfx;

import android.graphics.Canvas;

public abstract interface AwFunctor
{
  public abstract void destroy();
  
  public abstract long getNativeCompositorFrameConsumer();
  
  public abstract boolean requestDraw(Canvas paramCanvas);
  
  public abstract void trimMemory();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\gfx\AwFunctor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */