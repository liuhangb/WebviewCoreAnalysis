package com.tencent.tbs.core.webkit;

import java.io.Serializable;

public abstract class WebBackForwardList
  implements Serializable, Cloneable
{
  protected abstract WebBackForwardList clone();
  
  public abstract int getCurrentIndex();
  
  public abstract WebHistoryItem getCurrentItem();
  
  public abstract WebHistoryItem getItemAtIndex(int paramInt);
  
  public abstract int getSize();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\WebBackForwardList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */