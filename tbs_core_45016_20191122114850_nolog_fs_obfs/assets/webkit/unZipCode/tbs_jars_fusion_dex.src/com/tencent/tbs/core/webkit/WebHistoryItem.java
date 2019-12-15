package com.tencent.tbs.core.webkit;

import android.graphics.Bitmap;

public abstract class WebHistoryItem
  implements Cloneable
{
  protected abstract WebHistoryItem clone();
  
  public abstract Bitmap getFavicon();
  
  @Deprecated
  public abstract int getId();
  
  public abstract String getOriginalUrl();
  
  public abstract String getTitle();
  
  public abstract String getUrl();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\WebHistoryItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */