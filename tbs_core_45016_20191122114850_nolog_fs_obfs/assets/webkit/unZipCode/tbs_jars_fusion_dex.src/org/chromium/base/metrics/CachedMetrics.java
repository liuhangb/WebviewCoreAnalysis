package org.chromium.base.metrics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.chromium.base.library_loader.LibraryLoader;

public class CachedMetrics
{
  public static void commitCachedMetrics()
  {
    synchronized ()
    {
      Iterator localIterator = CachedMetric.a().iterator();
      while (localIterator.hasNext()) {
        ((CachedMetric)localIterator.next()).commitAndClear();
      }
      return;
    }
  }
  
  public static class ActionEvent
    extends CachedMetrics.CachedMetric
  {
    private int a;
    
    public ActionEvent(String paramString)
    {
      super();
    }
    
    private void a()
    {
      RecordUserAction.record(this.jdField_a_of_type_JavaLangString);
    }
    
    protected void commitAndClear()
    {
      while (this.jdField_a_of_type_Int > 0)
      {
        a();
        this.jdField_a_of_type_Int -= 1;
      }
    }
    
    public void record()
    {
      synchronized ()
      {
        if (LibraryLoader.isInitialized())
        {
          a();
        }
        else
        {
          this.jdField_a_of_type_Int += 1;
          addToCache();
        }
        return;
      }
    }
  }
  
  public static class BooleanHistogramSample
    extends CachedMetrics.CachedMetric
  {
    private final List<Boolean> a;
    
    public BooleanHistogramSample(String paramString)
    {
      super();
      this.jdField_a_of_type_JavaUtilList = new ArrayList();
    }
    
    private void a(boolean paramBoolean)
    {
      RecordHistogram.recordBooleanHistogram(this.jdField_a_of_type_JavaLangString, paramBoolean);
    }
    
    protected void commitAndClear()
    {
      Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
      while (localIterator.hasNext()) {
        a(((Boolean)localIterator.next()).booleanValue());
      }
      this.jdField_a_of_type_JavaUtilList.clear();
    }
    
    public void record(boolean paramBoolean)
    {
      synchronized ()
      {
        if (LibraryLoader.isInitialized())
        {
          a(paramBoolean);
        }
        else
        {
          this.jdField_a_of_type_JavaUtilList.add(Boolean.valueOf(paramBoolean));
          addToCache();
        }
        return;
      }
    }
  }
  
  private static abstract class CachedMetric
  {
    private static final List<CachedMetric> a;
    protected final String a;
    protected boolean a;
    
    static
    {
      jdField_a_of_type_JavaUtilList = new ArrayList();
    }
    
    protected CachedMetric(String paramString)
    {
      this.jdField_a_of_type_JavaLangString = paramString;
    }
    
    protected final void addToCache()
    {
      if ((!b) && (!Thread.holdsLock(jdField_a_of_type_JavaUtilList))) {
        throw new AssertionError();
      }
      if (this.jdField_a_of_type_Boolean) {
        return;
      }
      jdField_a_of_type_JavaUtilList.add(this);
      this.jdField_a_of_type_Boolean = true;
    }
    
    protected abstract void commitAndClear();
  }
  
  public static class EnumeratedHistogramSample
    extends CachedMetrics.CachedMetric
  {
    private final int jdField_a_of_type_Int;
    private final List<Integer> jdField_a_of_type_JavaUtilList = new ArrayList();
    
    public EnumeratedHistogramSample(String paramString, int paramInt)
    {
      super();
      this.jdField_a_of_type_Int = paramInt;
    }
    
    private void a(int paramInt)
    {
      RecordHistogram.recordEnumeratedHistogram(this.jdField_a_of_type_JavaLangString, paramInt, this.jdField_a_of_type_Int);
    }
    
    protected void commitAndClear()
    {
      Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
      while (localIterator.hasNext()) {
        a(((Integer)localIterator.next()).intValue());
      }
      this.jdField_a_of_type_JavaUtilList.clear();
    }
    
    public void record(int paramInt)
    {
      synchronized ()
      {
        if (LibraryLoader.isInitialized())
        {
          a(paramInt);
        }
        else
        {
          this.jdField_a_of_type_JavaUtilList.add(Integer.valueOf(paramInt));
          addToCache();
        }
        return;
      }
    }
  }
  
  public static class SparseHistogramSample
    extends CachedMetrics.CachedMetric
  {
    private final List<Integer> a;
    
    public SparseHistogramSample(String paramString)
    {
      super();
      this.jdField_a_of_type_JavaUtilList = new ArrayList();
    }
    
    private void a(int paramInt)
    {
      RecordHistogram.recordSparseSlowlyHistogram(this.jdField_a_of_type_JavaLangString, paramInt);
    }
    
    protected void commitAndClear()
    {
      Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
      while (localIterator.hasNext()) {
        a(((Integer)localIterator.next()).intValue());
      }
      this.jdField_a_of_type_JavaUtilList.clear();
    }
    
    public void record(int paramInt)
    {
      synchronized ()
      {
        if (LibraryLoader.isInitialized())
        {
          a(paramInt);
        }
        else
        {
          this.jdField_a_of_type_JavaUtilList.add(Integer.valueOf(paramInt));
          addToCache();
        }
        return;
      }
    }
  }
  
  public static class TimesHistogramSample
    extends CachedMetrics.CachedMetric
  {
    private final List<Long> jdField_a_of_type_JavaUtilList = new ArrayList();
    private final TimeUnit jdField_a_of_type_JavaUtilConcurrentTimeUnit;
    
    public TimesHistogramSample(String paramString, TimeUnit paramTimeUnit)
    {
      super();
      this.jdField_a_of_type_JavaUtilConcurrentTimeUnit = paramTimeUnit;
    }
    
    private void a(long paramLong)
    {
      RecordHistogram.recordTimesHistogram(this.jdField_a_of_type_JavaLangString, paramLong, this.jdField_a_of_type_JavaUtilConcurrentTimeUnit);
    }
    
    protected void commitAndClear()
    {
      Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
      while (localIterator.hasNext()) {
        a(((Long)localIterator.next()).longValue());
      }
      this.jdField_a_of_type_JavaUtilList.clear();
    }
    
    public void record(long paramLong)
    {
      synchronized ()
      {
        if (LibraryLoader.isInitialized())
        {
          a(paramLong);
        }
        else
        {
          this.jdField_a_of_type_JavaUtilList.add(Long.valueOf(paramLong));
          addToCache();
        }
        return;
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\metrics\CachedMetrics.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */