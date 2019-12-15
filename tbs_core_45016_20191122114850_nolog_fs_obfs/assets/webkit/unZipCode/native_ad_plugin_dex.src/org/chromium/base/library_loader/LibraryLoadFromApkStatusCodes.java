package org.chromium.base.library_loader;

import android.support.annotation.IntDef;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({0L, 1L, 2L, 3L, 4L, 5L, 6L})
public @interface LibraryLoadFromApkStatusCodes
{
  public static final int MAX = 6;
  public static final int NOT_SUPPORTED_OBSOLETE = 1;
  public static final int SUCCESSFUL = 3;
  public static final int SUPPORTED_OBSOLETE = 2;
  public static final int UNKNOWN = 0;
  public static final int USED_NO_MAP_EXEC_SUPPORT_FALLBACK_OBSOLETE = 5;
  public static final int USED_UNPACK_LIBRARY_FALLBACK = 4;
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\library_loader\LibraryLoadFromApkStatusCodes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */