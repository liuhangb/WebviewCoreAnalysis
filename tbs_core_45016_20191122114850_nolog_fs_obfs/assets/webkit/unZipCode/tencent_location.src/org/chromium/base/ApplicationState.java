package org.chromium.base;

import android.support.annotation.IntDef;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({0L, 1L, 2L, 3L, 4L})
public @interface ApplicationState
{
  public static final int HAS_DESTROYED_ACTIVITIES = 4;
  public static final int HAS_PAUSED_ACTIVITIES = 2;
  public static final int HAS_RUNNING_ACTIVITIES = 1;
  public static final int HAS_STOPPED_ACTIVITIES = 3;
  public static final int UNKNOWN = 0;
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\ApplicationState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */