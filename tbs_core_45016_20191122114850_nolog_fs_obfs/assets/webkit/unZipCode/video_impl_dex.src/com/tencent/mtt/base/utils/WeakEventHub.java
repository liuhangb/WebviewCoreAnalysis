package com.tencent.mtt.base.utils;

import com.tencent.basesupport.FLogger;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

public final class WeakEventHub<T>
{
  private final ArrayList<WeakReference<T>> a = new ArrayList();
  
  public Iterable<T> getNotifyListeners()
  {
    ArrayList localArrayList2 = new ArrayList(this.a.size());
    synchronized (this.a)
    {
      Iterator localIterator = this.a.iterator();
      while (localIterator.hasNext())
      {
        Object localObject2 = (WeakReference)localIterator.next();
        if (localObject2 != null)
        {
          localObject2 = ((WeakReference)localObject2).get();
          if (localObject2 == null) {
            localIterator.remove();
          } else {
            localArrayList2.add(localObject2);
          }
        }
      }
      return localArrayList2;
    }
  }
  
  public void registerListener(T paramT)
  {
    if (paramT == null) {
      return;
    }
    synchronized (this.a)
    {
      Iterator localIterator = this.a.iterator();
      while (localIterator.hasNext())
      {
        Object localObject = ((WeakReference)localIterator.next()).get();
        if (localObject == null) {
          localIterator.remove();
        } else if (localObject == paramT) {
          return;
        }
      }
      this.a.add(new WeakReference(paramT));
      paramT = new StringBuilder();
      paramT.append("registerListener count:");
      paramT.append(this.a.size());
      FLogger.d("WeakEventHub", paramT.toString());
      return;
    }
  }
  
  public int size()
  {
    return this.a.size();
  }
  
  public void unregisterListener(T paramT)
  {
    ArrayList localArrayList = this.a;
    if (paramT != null) {}
    try
    {
      Iterator localIterator = this.a.iterator();
      while (localIterator.hasNext())
      {
        Object localObject = (WeakReference)localIterator.next();
        if (localObject != null)
        {
          localObject = ((WeakReference)localObject).get();
          if ((localObject == null) || (localObject == paramT)) {
            localIterator.remove();
          }
        }
      }
      paramT = new StringBuilder();
      paramT.append("unregisterListener() count: ");
      paramT.append(this.a.size());
      FLogger.d("WeakEventHub", paramT.toString());
      return;
    }
    finally {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\WeakEventHub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */