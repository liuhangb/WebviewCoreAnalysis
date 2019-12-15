package com.tencent.common.threadpool;

import com.tencent.basesupport.ModuleProxy;
import com.tencent.common.manifest.annotation.Service;
import java.util.Comparator;

public class AbstractTaskComparator
{
  public static final ModuleProxy<ITaskComparator> PROXY = ModuleProxy.newProxy(ITaskComparator.class, new DefaultTaskComparator());
  
  public static ITaskComparator get()
  {
    return (ITaskComparator)PROXY.get();
  }
  
  public static class DefaultTaskComparator
    implements AbstractTaskComparator.ITaskComparator
  {
    public int compare(Runnable paramRunnable1, Runnable paramRunnable2)
    {
      if (((paramRunnable1 instanceof Comparable)) && ((paramRunnable2 instanceof Comparable))) {
        return ((Comparable)paramRunnable1).compareTo(paramRunnable2);
      }
      return paramRunnable1.hashCode() - paramRunnable2.hashCode();
    }
  }
  
  @Service
  public static abstract interface ITaskComparator
    extends Comparator<Runnable>
  {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\threadpool\AbstractTaskComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */