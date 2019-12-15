package org.chromium.content_public.browser;

import android.graphics.Bitmap;
import android.graphics.Rect;
import java.util.List;

public abstract interface ImageDownloadCallback
{
  public abstract void onFinishDownloadImage(int paramInt1, int paramInt2, String paramString, List<Bitmap> paramList, List<Rect> paramList1);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\ImageDownloadCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */