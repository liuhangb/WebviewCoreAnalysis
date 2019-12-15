package com.tencent.mtt.base.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BaseWalledGardenTask
  implements Runnable
{
  private Object a = new Object();
  protected List<WalledGardenTaskObserver> mObservers;
  public Object mTag;
  
  public void addObserver(WalledGardenTaskObserver paramWalledGardenTaskObserver)
  {
    if (paramWalledGardenTaskObserver == null) {
      return;
    }
    synchronized (this.a)
    {
      if (this.mObservers == null) {
        this.mObservers = new ArrayList(3);
      }
      if (!this.mObservers.contains(paramWalledGardenTaskObserver)) {
        this.mObservers.add(paramWalledGardenTaskObserver);
      }
      return;
    }
  }
  
  public void cancel() {}
  
  public void fireObserverEvent()
  {
    synchronized (this.mObservers)
    {
      if (this.mObservers == null) {
        return;
      }
      Iterator localIterator = this.mObservers.iterator();
      while (localIterator.hasNext())
      {
        WalledGardenTaskObserver localWalledGardenTaskObserver = (WalledGardenTaskObserver)localIterator.next();
        if (localWalledGardenTaskObserver != null) {
          localWalledGardenTaskObserver.onDetectTaskComplete(this);
        }
      }
      return;
    }
  }
  
  public void removeObserver(WalledGardenTaskObserver paramWalledGardenTaskObserver)
  {
    if (paramWalledGardenTaskObserver == null) {
      return;
    }
    synchronized (this.a)
    {
      if ((this.mObservers != null) && (!this.mObservers.isEmpty())) {
        this.mObservers.remove(paramWalledGardenTaskObserver);
      }
      return;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\task\BaseWalledGardenTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */