package com.tencent.common.threadpool;

import com.tencent.basesupport.FLogger;
import java.util.LinkedList;

public class SequenceRunnable
  implements Runnable
{
  public static final String TAG = "SequenceRunnable";
  private LinkedList<ISequenceItem> jdField_a_of_type_JavaUtilLinkedList = null;
  private boolean jdField_a_of_type_Boolean = false;
  
  public void addItem(ISequenceItem paramISequenceItem)
  {
    if (paramISequenceItem == null) {
      return;
    }
    FLogger.d("SequenceRunnable", "addItem knock door");
    synchronized (this.jdField_a_of_type_JavaUtilLinkedList)
    {
      FLogger.d("SequenceRunnable", "addItem entered");
      if (!this.jdField_a_of_type_JavaUtilLinkedList.contains(paramISequenceItem))
      {
        this.jdField_a_of_type_JavaUtilLinkedList.add(paramISequenceItem);
        this.jdField_a_of_type_JavaUtilLinkedList.notify();
      }
      return;
    }
  }
  
  public void removeItem(ISequenceItem paramISequenceItem)
  {
    if (paramISequenceItem == null) {
      return;
    }
    FLogger.d("SequenceRunnable", "removeItem knock door");
    synchronized (this.jdField_a_of_type_JavaUtilLinkedList)
    {
      FLogger.d("SequenceRunnable", "removeItem entered");
      this.jdField_a_of_type_JavaUtilLinkedList.remove(paramISequenceItem);
      paramISequenceItem.deActive();
      return;
    }
  }
  
  public void run()
  {
    FLogger.d("SequenceRunnable", "Sequence Thread Start");
    this.jdField_a_of_type_Boolean = true;
    while (this.jdField_a_of_type_Boolean)
    {
      FLogger.d("SequenceRunnable", "loadThread knock door");
      synchronized (this.jdField_a_of_type_JavaUtilLinkedList)
      {
        FLogger.d("SequenceRunnable", "loadThread entered");
        while (this.jdField_a_of_type_JavaUtilLinkedList.size() == 0)
        {
          boolean bool = this.jdField_a_of_type_Boolean;
          if (!bool) {
            break;
          }
          try
          {
            FLogger.d("SequenceRunnable", "mSequenceList is empty");
            this.jdField_a_of_type_JavaUtilLinkedList.wait();
          }
          catch (InterruptedException localInterruptedException)
          {
            Object localObject1;
            for (;;) {}
          }
          FLogger.d("SequenceRunnable", "Interrupted while wait new task.");
        }
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("Task Count -----------> ");
        ((StringBuilder)localObject1).append(this.jdField_a_of_type_JavaUtilLinkedList.size());
        FLogger.d("SequenceRunnable", ((StringBuilder)localObject1).toString());
        localObject1 = (ISequenceItem)this.jdField_a_of_type_JavaUtilLinkedList.poll();
        FLogger.d("SequenceRunnable", "Processed one task");
        if (localObject1 != null) {
          ((ISequenceItem)localObject1).active();
        }
        Thread.yield();
      }
    }
  }
  
  public void stop()
  {
    this.jdField_a_of_type_Boolean = false;
    synchronized (this.jdField_a_of_type_JavaUtilLinkedList)
    {
      this.jdField_a_of_type_JavaUtilLinkedList.notify();
      this.jdField_a_of_type_JavaUtilLinkedList.clear();
      return;
    }
  }
  
  public static abstract interface ISequenceItem
  {
    public abstract void active();
    
    public abstract void deActive();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\threadpool\SequenceRunnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */