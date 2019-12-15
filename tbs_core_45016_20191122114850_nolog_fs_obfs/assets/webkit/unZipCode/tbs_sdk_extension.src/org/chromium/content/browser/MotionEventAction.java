package org.chromium.content.browser;

import android.support.annotation.IntDef;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({-1L, 0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L})
public @interface MotionEventAction
{
  public static final int CANCEL = 2;
  public static final int END = 3;
  public static final int HOVER_ENTER = 5;
  public static final int HOVER_EXIT = 6;
  public static final int HOVER_MOVE = 7;
  public static final int INVALID = -1;
  public static final int MOVE = 1;
  public static final int SCROLL = 4;
  public static final int START = 0;
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\MotionEventAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */