package org.chromium.content.browser.accessibility;

import android.support.annotation.IntDef;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({0L, 1L, 2L, 3L, 4L, 5L})
public @interface ScrollDirection
{
  public static final int BACKWARD = 1;
  public static final int DOWN = 3;
  public static final int FORWARD = 0;
  public static final int LEFT = 4;
  public static final int RIGHT = 5;
  public static final int UP = 2;
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\accessibility\ScrollDirection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */