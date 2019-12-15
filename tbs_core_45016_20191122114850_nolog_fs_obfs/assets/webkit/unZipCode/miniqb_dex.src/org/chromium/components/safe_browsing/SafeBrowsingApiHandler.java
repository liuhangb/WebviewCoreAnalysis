package org.chromium.components.safe_browsing;

import android.content.Context;
import android.support.annotation.IntDef;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract interface SafeBrowsingApiHandler
{
  public abstract boolean init(Context paramContext, Observer paramObserver);
  
  public abstract void startUriLookup(long paramLong, String paramString, int[] paramArrayOfInt);
  
  public static abstract interface Observer
  {
    public abstract void onUrlCheckDone(long paramLong1, int paramInt, String paramString, long paramLong2);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  @IntDef({-1L, 0L, 1L})
  public static @interface SafeBrowsingResult {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\safe_browsing\SafeBrowsingApiHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */