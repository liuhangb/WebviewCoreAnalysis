package org.chromium.base.library_loader;

import android.support.annotation.IntDef;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({0L, 1L, 2L, 3L, 4L})
public @interface LibraryProcessType
{
  public static final int PROCESS_BROWSER = 1;
  public static final int PROCESS_CHILD = 2;
  public static final int PROCESS_UNINITIALIZED = 0;
  public static final int PROCESS_WEBVIEW = 3;
  public static final int PROCESS_WEBVIEW_CHILD = 4;
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\library_loader\LibraryProcessType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */