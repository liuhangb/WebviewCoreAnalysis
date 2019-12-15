package org.chromium.base.metrics;

import android.support.annotation.IntDef;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({0L, 1L})
public @interface JSONVerbosityLevel
{
  public static final int JSON_VERBOSITY_LEVEL_FULL = 0;
  public static final int JSON_VERBOSITY_LEVEL_OMIT_BUCKETS = 1;
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\metrics\JSONVerbosityLevel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */