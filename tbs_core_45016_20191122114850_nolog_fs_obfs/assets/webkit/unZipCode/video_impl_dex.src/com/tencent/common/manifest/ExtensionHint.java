package com.tencent.common.manifest;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;

public class ExtensionHint
{
  public static class HintEntry<T>
    implements Map.Entry<String[], T>
  {
    final Implementation a;
    
    public HintEntry(Implementation paramImplementation)
    {
      this.a = paramImplementation;
    }
    
    public String[] getKey()
    {
      return this.a.hint;
    }
    
    public T getValue()
    {
      return (T)this.a.query();
    }
    
    public T setValue(T paramT)
    {
      return null;
    }
  }
  
  public static class HintIterable<T>
    implements Iterable<Map.Entry<String[], T>>
  {
    final ExtensionHint.HintIterator<T> a;
    
    public HintIterable(Collection<Implementation> paramCollection)
    {
      this.a = new ExtensionHint.HintIterator(paramCollection);
    }
    
    public Iterator<Map.Entry<String[], T>> iterator()
    {
      return this.a;
    }
  }
  
  public static class HintIterator<T>
    implements Iterator<Map.Entry<String[], T>>
  {
    Implementation jdField_a_of_type_ComTencentCommonManifestImplementation = null;
    final Iterator<Implementation> jdField_a_of_type_JavaUtilIterator;
    
    public HintIterator(Collection<Implementation> paramCollection)
    {
      this.jdField_a_of_type_JavaUtilIterator = paramCollection.iterator();
    }
    
    public boolean hasNext()
    {
      if (this.jdField_a_of_type_ComTencentCommonManifestImplementation != null) {
        return true;
      }
      while (this.jdField_a_of_type_JavaUtilIterator.hasNext())
      {
        this.jdField_a_of_type_ComTencentCommonManifestImplementation = ((Implementation)this.jdField_a_of_type_JavaUtilIterator.next());
        Implementation localImplementation = this.jdField_a_of_type_ComTencentCommonManifestImplementation;
        if ((localImplementation != null) && (localImplementation.hint.length > 0)) {
          return true;
        }
      }
      this.jdField_a_of_type_ComTencentCommonManifestImplementation = null;
      return false;
    }
    
    public Map.Entry<String[], T> next()
    {
      if ((this.jdField_a_of_type_ComTencentCommonManifestImplementation == null) && (!hasNext())) {
        return null;
      }
      ExtensionHint.HintEntry localHintEntry = new ExtensionHint.HintEntry(this.jdField_a_of_type_ComTencentCommonManifestImplementation);
      this.jdField_a_of_type_ComTencentCommonManifestImplementation = null;
      return localHintEntry;
    }
    
    public void remove() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\manifest\ExtensionHint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */