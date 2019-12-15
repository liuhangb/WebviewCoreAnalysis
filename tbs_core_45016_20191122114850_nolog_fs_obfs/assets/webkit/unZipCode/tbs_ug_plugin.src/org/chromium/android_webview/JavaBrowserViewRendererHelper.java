package org.chromium.android_webview;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.util.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
public class JavaBrowserViewRendererHelper
{
  @CalledByNative
  private static Bitmap createBitmap(int paramInt1, int paramInt2, Canvas paramCanvas)
  {
    int j = paramInt1;
    int i = paramInt2;
    if (paramCanvas != null)
    {
      j = Math.min(paramInt1, paramCanvas.getMaximumBitmapWidth());
      i = Math.min(paramInt2, paramCanvas.getMaximumBitmapHeight());
    }
    try
    {
      paramCanvas = Bitmap.createBitmap(j, i, Bitmap.Config.ARGB_8888);
      return paramCanvas;
    }
    catch (OutOfMemoryError paramCanvas)
    {
      for (;;) {}
    }
    Log.e("JavaBrowserViewRendererHelper", "Error allocating bitmap");
    return null;
  }
  
  @CalledByNative
  private static void drawBitmapIntoCanvas(Bitmap paramBitmap, Canvas paramCanvas, int paramInt1, int paramInt2)
  {
    paramCanvas.translate(paramInt1, paramInt2);
    paramCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, null);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\JavaBrowserViewRendererHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */