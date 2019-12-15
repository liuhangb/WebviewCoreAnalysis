package com.tencent.common.threadpool.debug;

import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ThreadPoolSnapshot
{
  private static Handler jdField_a_of_type_AndroidOsHandler = null;
  private static volatile HandlerThread jdField_a_of_type_AndroidOsHandlerThread;
  static final b jdField_a_of_type_ComTencentCommonThreadpoolDebugThreadPoolSnapshot$b = new b();
  private static volatile boolean jdField_a_of_type_Boolean;
  static final b b = new b();
  
  static
  {
    jdField_a_of_type_Boolean = false;
    jdField_a_of_type_AndroidOsHandlerThread = null;
  }
  
  private static void a(Runnable paramRunnable)
  {
    if (!jdField_a_of_type_Boolean) {
      return;
    }
    if (jdField_a_of_type_AndroidOsHandlerThread == null) {
      try
      {
        HandlerThread localHandlerThread = jdField_a_of_type_AndroidOsHandlerThread;
        if (localHandlerThread == null) {
          try
          {
            localHandlerThread = new HandlerThread("ThreadPoolSnapshot");
            localHandlerThread.start();
            jdField_a_of_type_AndroidOsHandler = new Handler(localHandlerThread.getLooper());
            jdField_a_of_type_AndroidOsHandlerThread = localHandlerThread;
          }
          catch (Throwable localThrowable)
          {
            localThrowable.printStackTrace();
            jdField_a_of_type_Boolean = false;
          }
        }
      }
      finally {}
    }
    Handler localHandler = jdField_a_of_type_AndroidOsHandler;
    if (localHandler != null) {
      localHandler.post(paramRunnable);
    }
  }
  
  public static void add(final String paramString, Runnable paramRunnable)
  {
    if (!jdField_a_of_type_Boolean) {
      return;
    }
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    a(new Runnable()
    {
      public void run()
      {
        ThreadPoolSnapshot.a.a(null, this.jdField_a_of_type_JavaLangRunnable, paramString);
      }
    });
  }
  
  public static void afterRun(Runnable paramRunnable)
  {
    if (!jdField_a_of_type_Boolean) {
      return;
    }
    a(new Runnable()
    {
      public void run()
      {
        ThreadPoolSnapshot.b.a(this.a);
      }
    });
  }
  
  public static void beforeRun(Thread paramThread, Runnable paramRunnable, final String paramString)
  {
    if (!jdField_a_of_type_Boolean) {
      return;
    }
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    final Thread localThread = paramThread;
    if (paramThread == null) {
      localThread = Thread.currentThread();
    }
    a(new Runnable()
    {
      public void run()
      {
        ThreadPoolSnapshot.a.a(this.jdField_a_of_type_JavaLangRunnable);
        ThreadPoolSnapshot.b.a(localThread, this.jdField_a_of_type_JavaLangRunnable, paramString);
      }
    });
  }
  
  public static void enable(boolean paramBoolean)
  {
    jdField_a_of_type_Boolean = paramBoolean;
  }
  
  public static boolean isEnabled()
  {
    return jdField_a_of_type_Boolean;
  }
  
  public static void snapshot(ISnapshotReceiver paramISnapshotReceiver)
  {
    a(new Runnable()
    {
      public void run()
      {
        ThreadPoolSnapshot.Snapshot localSnapshot = new ThreadPoolSnapshot.Snapshot();
        Iterator localIterator = ThreadPoolSnapshot.b.a.entrySet().iterator();
        Object localObject3;
        String str;
        Object localObject2;
        Object localObject1;
        while (localIterator.hasNext())
        {
          localObject3 = (Map.Entry)localIterator.next();
          str = (String)((Map.Entry)localObject3).getKey();
          localObject2 = (List)localSnapshot.threadTrace.get(str);
          localObject1 = localObject2;
          if (localObject2 == null)
          {
            localObject1 = new LinkedList();
            localSnapshot.threadTrace.put(str, localObject1);
          }
          localObject2 = ((Set)((Map.Entry)localObject3).getValue()).iterator();
          while (((Iterator)localObject2).hasNext())
          {
            localObject3 = (Thread)((ThreadPoolSnapshot.a)((Iterator)localObject2).next()).a.get();
            if (localObject3 == null) {
              ((Iterator)localObject2).remove();
            } else {
              ((List)localObject1).add(((Thread)localObject3).getStackTrace());
            }
          }
        }
        localIterator = ThreadPoolSnapshot.a.a.entrySet().iterator();
        while (localIterator.hasNext())
        {
          localObject3 = (Map.Entry)localIterator.next();
          str = (String)((Map.Entry)localObject3).getKey();
          localObject2 = (List)localSnapshot.pendingTasks.get(str);
          localObject1 = localObject2;
          if (localObject2 == null)
          {
            localObject1 = new LinkedList();
            localSnapshot.pendingTasks.put(str, localObject1);
          }
          localObject2 = ((Set)((Map.Entry)localObject3).getValue()).iterator();
          while (((Iterator)localObject2).hasNext())
          {
            localObject3 = (Runnable)((ThreadPoolSnapshot.a)((Iterator)localObject2).next()).b.get();
            if (localObject3 == null) {
              ((Iterator)localObject2).remove();
            } else {
              ((List)localObject1).add(localObject3.getClass());
            }
          }
        }
        this.a.onSnapshot(localSnapshot);
      }
    });
  }
  
  public static abstract interface ISnapshotReceiver
  {
    public abstract void onSnapshot(ThreadPoolSnapshot.Snapshot paramSnapshot);
  }
  
  public static class Snapshot
  {
    public final Map<String, List<Class<?>>> pendingTasks = new HashMap();
    public final Map<String, List<StackTraceElement[]>> threadTrace = new HashMap();
  }
  
  static class a
  {
    final String jdField_a_of_type_JavaLangString;
    final WeakReference<Thread> jdField_a_of_type_JavaLangRefWeakReference;
    final WeakReference<Runnable> b;
    
    a(Thread paramThread, Runnable paramRunnable, String paramString)
    {
      if (paramThread == null) {
        paramThread = null;
      } else {
        paramThread = new WeakReference(paramThread);
      }
      this.jdField_a_of_type_JavaLangRefWeakReference = paramThread;
      this.b = new WeakReference(paramRunnable);
      this.jdField_a_of_type_JavaLangString = paramString;
    }
  }
  
  static class b
  {
    final Map<String, Set<ThreadPoolSnapshot.a>> a = new HashMap();
    final Map<Integer, ThreadPoolSnapshot.a> b = new HashMap();
    
    void a(Runnable paramRunnable)
    {
      int i = paramRunnable.hashCode();
      paramRunnable = (ThreadPoolSnapshot.a)this.b.get(Integer.valueOf(i));
      if (paramRunnable != null)
      {
        this.b.remove(Integer.valueOf(i));
        Set localSet = (Set)this.a.get(paramRunnable.a);
        if (localSet != null)
        {
          localSet.remove(paramRunnable);
          if (localSet.isEmpty()) {
            this.a.remove(paramRunnable.a);
          }
        }
      }
    }
    
    void a(Thread paramThread, Runnable paramRunnable, String paramString)
    {
      ThreadPoolSnapshot.a locala = new ThreadPoolSnapshot.a(paramThread, paramRunnable, paramString);
      this.b.put(Integer.valueOf(paramRunnable.hashCode()), locala);
      paramRunnable = (Set)this.a.get(paramString);
      paramThread = paramRunnable;
      if (paramRunnable == null)
      {
        paramThread = new HashSet();
        this.a.put(paramString, paramThread);
      }
      paramThread.add(locala);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\threadpool\debug\ThreadPoolSnapshot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */