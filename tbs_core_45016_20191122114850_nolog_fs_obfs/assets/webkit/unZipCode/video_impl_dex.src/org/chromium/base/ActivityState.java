package org.chromium.base;

import android.support.annotation.IntDef;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({1L, 2L, 3L, 4L, 5L, 6L})
public @interface ActivityState
{
  public static final int CREATED = 1;
  public static final int DESTROYED = 6;
  public static final int PAUSED = 4;
  public static final int RESUMED = 3;
  public static final int STARTED = 2;
  public static final int STOPPED = 5;
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\ActivityState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */