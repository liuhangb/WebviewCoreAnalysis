package org.chromium.android_webview;

import android.support.annotation.IntDef;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({0L, 1L, 2L, 3L})
public @interface ErrorUiType
{
  public static final int COUNT = 3;
  public static final int LOUD = 0;
  public static final int QUIET_GIANT = 2;
  public static final int QUIET_SMALL = 1;
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\ErrorUiType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */