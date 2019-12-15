package org.chromium.android_webview;

import android.support.annotation.IntDef;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({0L, 1L, 2L})
public @interface SafeBrowsingAction
{
  public static final int BACK_TO_SAFETY = 2;
  public static final int PROCEED = 1;
  public static final int SHOW_INTERSTITIAL = 0;
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\SafeBrowsingAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */