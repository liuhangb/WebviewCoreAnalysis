package org.chromium.android_webview.renderer_priority;

import android.support.annotation.IntDef;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({-1L, 0L, 1L, 2L})
public @interface RendererPriority
{
  public static final int HIGH = 2;
  public static final int INITIAL = -1;
  public static final int LOW = 1;
  public static final int WAIVED = 0;
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\renderer_priority\RendererPriority.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */