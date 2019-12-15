package org.chromium.content.browser.input;

import android.support.annotation.IntDef;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({0L, 1L, 2L})
public @interface PopupItemType
{
  public static final int DISABLED = 1;
  public static final int ENABLED = 2;
  public static final int GROUP = 0;
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\input\PopupItemType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */