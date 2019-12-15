package org.chromium.base;

import android.support.annotation.Nullable;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public class DiscardableReferencePool
{
  private final Set<DiscardableReference<?>> jdField_a_of_type_JavaUtilSet = Collections.newSetFromMap(new WeakHashMap());
  
  public void drain()
  {
    Iterator localIterator = this.jdField_a_of_type_JavaUtilSet.iterator();
    while (localIterator.hasNext()) {
      DiscardableReference.a((DiscardableReference)localIterator.next());
    }
    this.jdField_a_of_type_JavaUtilSet.clear();
  }
  
  public <T> DiscardableReference<T> put(T paramT)
  {
    if ((!jdField_a_of_type_Boolean) && (paramT == null)) {
      throw new AssertionError();
    }
    paramT = new DiscardableReference(paramT, null);
    this.jdField_a_of_type_JavaUtilSet.add(paramT);
    return paramT;
  }
  
  public static class DiscardableReference<T>
  {
    @Nullable
    private T jdField_a_of_type_JavaLangObject;
    
    private DiscardableReference(T paramT)
    {
      if ((!jdField_a_of_type_Boolean) && (paramT == null)) {
        throw new AssertionError();
      }
      this.jdField_a_of_type_JavaLangObject = paramT;
    }
    
    private void a()
    {
      if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_JavaLangObject == null)) {
        throw new AssertionError();
      }
      this.jdField_a_of_type_JavaLangObject = null;
    }
    
    @Nullable
    public T get()
    {
      return (T)this.jdField_a_of_type_JavaLangObject;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\DiscardableReferencePool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */