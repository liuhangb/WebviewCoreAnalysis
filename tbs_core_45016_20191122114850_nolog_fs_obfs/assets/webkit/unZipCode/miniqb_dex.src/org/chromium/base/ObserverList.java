package org.chromium.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ObserverList<E>
  implements Iterable<E>
{
  private int jdField_a_of_type_Int;
  private int jdField_b_of_type_Int;
  private boolean jdField_b_of_type_Boolean;
  public final List<E> mObservers = new ArrayList();
  
  private int a()
  {
    return this.mObservers.size();
  }
  
  private E a(int paramInt)
  {
    return (E)this.mObservers.get(paramInt);
  }
  
  private void a()
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Int != 0)) {
      throw new AssertionError();
    }
    int i = this.mObservers.size() - 1;
    while (i >= 0)
    {
      if (this.mObservers.get(i) == null) {
        this.mObservers.remove(i);
      }
      i -= 1;
    }
  }
  
  private void b()
  {
    this.jdField_a_of_type_Int += 1;
  }
  
  private void c()
  {
    this.jdField_a_of_type_Int -= 1;
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Int < 0)) {
      throw new AssertionError();
    }
    if (this.jdField_a_of_type_Int > 0) {
      return;
    }
    if (!this.jdField_b_of_type_Boolean) {
      return;
    }
    this.jdField_b_of_type_Boolean = false;
    a();
  }
  
  public boolean addObserver(E paramE)
  {
    if ((paramE != null) && (!this.mObservers.contains(paramE)))
    {
      boolean bool = this.mObservers.add(paramE);
      if ((!jdField_a_of_type_Boolean) && (!bool)) {
        throw new AssertionError();
      }
      this.jdField_b_of_type_Int += 1;
      return true;
    }
    return false;
  }
  
  public void clear()
  {
    int k = 0;
    this.jdField_b_of_type_Int = 0;
    if (this.jdField_a_of_type_Int == 0)
    {
      this.mObservers.clear();
      return;
    }
    int m = this.mObservers.size();
    boolean bool = this.jdField_b_of_type_Boolean;
    if (m != 0) {
      i = 1;
    } else {
      i = 0;
    }
    this.jdField_b_of_type_Boolean = (bool | i);
    int i = k;
    while (i < m)
    {
      this.mObservers.set(i, null);
      int j;
      i += 1;
    }
  }
  
  public boolean hasObserver(E paramE)
  {
    return this.mObservers.contains(paramE);
  }
  
  public boolean isEmpty()
  {
    return this.jdField_b_of_type_Int == 0;
  }
  
  public Iterator<E> iterator()
  {
    return new ObserverListIterator(null);
  }
  
  public boolean removeObserver(E paramE)
  {
    if (paramE == null) {
      return false;
    }
    int i = this.mObservers.indexOf(paramE);
    if (i == -1) {
      return false;
    }
    if (this.jdField_a_of_type_Int == 0)
    {
      this.mObservers.remove(i);
    }
    else
    {
      this.jdField_b_of_type_Boolean = true;
      this.mObservers.set(i, null);
    }
    this.jdField_b_of_type_Int -= 1;
    if (!jdField_a_of_type_Boolean)
    {
      if (this.jdField_b_of_type_Int >= 0) {
        return true;
      }
      throw new AssertionError();
    }
    return true;
  }
  
  public RewindableIterator<E> rewindableIterator()
  {
    return new ObserverListIterator(null);
  }
  
  public int size()
  {
    return this.jdField_b_of_type_Int;
  }
  
  private class ObserverListIterator
    implements ObserverList.RewindableIterator<E>
  {
    private int jdField_a_of_type_Int;
    private boolean jdField_a_of_type_Boolean;
    private int b;
    
    private ObserverListIterator()
    {
      ObserverList.a(ObserverList.this);
      this.jdField_a_of_type_Int = ObserverList.a(ObserverList.this);
    }
    
    private void a()
    {
      if (!this.jdField_a_of_type_Boolean)
      {
        this.jdField_a_of_type_Boolean = true;
        ObserverList.b(ObserverList.this);
      }
    }
    
    public boolean hasNext()
    {
      int i = this.b;
      while ((i < this.jdField_a_of_type_Int) && (ObserverList.a(ObserverList.this, i) == null)) {
        i += 1;
      }
      if (i < this.jdField_a_of_type_Int) {
        return true;
      }
      a();
      return false;
    }
    
    public E next()
    {
      for (;;)
      {
        i = this.b;
        if ((i >= this.jdField_a_of_type_Int) || (ObserverList.a(ObserverList.this, i) != null)) {
          break;
        }
        this.b += 1;
      }
      int i = this.b;
      if (i < this.jdField_a_of_type_Int)
      {
        ObserverList localObserverList = ObserverList.this;
        this.b = (i + 1);
        return (E)ObserverList.a(localObserverList, i);
      }
      a();
      throw new NoSuchElementException();
    }
    
    public void remove()
    {
      throw new UnsupportedOperationException();
    }
    
    public void rewind()
    {
      a();
      ObserverList.a(ObserverList.this);
      this.jdField_a_of_type_Int = ObserverList.a(ObserverList.this);
      this.jdField_a_of_type_Boolean = false;
      this.b = 0;
    }
  }
  
  public static abstract interface RewindableIterator<E>
    extends Iterator<E>
  {
    public abstract void rewind();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\ObserverList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */