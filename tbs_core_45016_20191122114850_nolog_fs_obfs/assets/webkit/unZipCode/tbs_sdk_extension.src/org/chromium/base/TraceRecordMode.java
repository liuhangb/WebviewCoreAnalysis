package org.chromium.base;

import android.support.annotation.IntDef;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({0L, 1L, 2L, 3L})
public @interface TraceRecordMode
{
  public static final int ECHO_TO_CONSOLE = 3;
  public static final int RECORD_AS_MUCH_AS_POSSIBLE = 2;
  public static final int RECORD_CONTINUOUSLY = 1;
  public static final int RECORD_UNTIL_FULL = 0;
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\TraceRecordMode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */