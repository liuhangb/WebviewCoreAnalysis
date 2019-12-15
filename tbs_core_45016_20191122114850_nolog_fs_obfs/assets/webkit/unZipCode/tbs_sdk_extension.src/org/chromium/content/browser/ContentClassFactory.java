package org.chromium.content.browser;

import android.view.View;
import org.chromium.base.ThreadUtils;
import org.chromium.content.browser.selection.SelectionInsertionHandleObserver;

public class ContentClassFactory
{
  private static ContentClassFactory a;
  
  public static ContentClassFactory get()
  {
    
    if (a == null) {
      a = new ContentClassFactory();
    }
    return a;
  }
  
  public static void set(ContentClassFactory paramContentClassFactory)
  {
    ThreadUtils.assertOnUiThread();
    a = paramContentClassFactory;
  }
  
  public SelectionInsertionHandleObserver createHandleObserver(View paramView)
  {
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\ContentClassFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */