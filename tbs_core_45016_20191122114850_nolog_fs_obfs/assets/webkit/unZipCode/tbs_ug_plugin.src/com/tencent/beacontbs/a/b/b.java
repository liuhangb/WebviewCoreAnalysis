package com.tencent.beacontbs.a.b;

import android.content.Context;
import android.util.SparseArray;
import com.tencent.beacontbs.upload.g;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class b
{
  private static b jdField_a_of_type_ComTencentBeacontbsABB;
  private int jdField_a_of_type_Int = 0;
  protected Context a;
  private SparseArray<g> jdField_a_of_type_AndroidUtilSparseArray = new SparseArray(5);
  protected d a;
  private com.tencent.beacontbs.upload.e jdField_a_of_type_ComTencentBeacontbsUploadE = null;
  private Runnable jdField_a_of_type_JavaLangRunnable = null;
  private List<a> jdField_a_of_type_JavaUtilList = new ArrayList(5);
  private boolean jdField_a_of_type_Boolean = false;
  private SparseArray<f> jdField_b_of_type_AndroidUtilSparseArray = new SparseArray(2);
  private Runnable jdField_b_of_type_JavaLangRunnable = new Runnable()
  {
    public final void run()
    {
      if ((b.this.jdField_a_of_type_AndroidContentContext != null) && (b.this.jdField_a_of_type_ComTencentBeacontbsABD != null)) {
        b.this.jdField_a_of_type_ComTencentBeacontbsABD.a(b.this.jdField_a_of_type_AndroidContentContext);
      }
    }
  };
  private List<e> jdField_b_of_type_JavaUtilList = new ArrayList(5);
  private boolean jdField_b_of_type_Boolean = false;
  
  private b(Context paramContext)
  {
    this.jdField_a_of_type_ComTencentBeacontbsABD = null;
    this.jdField_a_of_type_AndroidContentContext = null;
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_ComTencentBeacontbsABD = d.a();
    com.tencent.beacontbs.a.b.a().a(this.jdField_b_of_type_JavaLangRunnable);
    this.jdField_a_of_type_ComTencentBeacontbsUploadE = new com.tencent.beacontbs.upload.e(paramContext);
    this.jdField_a_of_type_JavaLangRunnable = new c(paramContext);
    com.tencent.beacontbs.a.b.a().a(this.jdField_a_of_type_JavaLangRunnable);
  }
  
  private SparseArray<f> a()
  {
    try
    {
      SparseArray localSparseArray = this.jdField_b_of_type_AndroidUtilSparseArray;
      return localSparseArray;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static b a(Context paramContext)
  {
    try
    {
      if ((jdField_a_of_type_ComTencentBeacontbsABB == null) && (paramContext != null)) {
        jdField_a_of_type_ComTencentBeacontbsABB = new b(paramContext);
      }
      paramContext = jdField_a_of_type_ComTencentBeacontbsABB;
      return paramContext;
    }
    finally {}
  }
  
  public static g a()
  {
    try
    {
      if (jdField_a_of_type_ComTencentBeacontbsABB != null)
      {
        g localg = jdField_a_of_type_ComTencentBeacontbsABB.b();
        return localg;
      }
      return null;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  private g b()
  {
    try
    {
      if ((this.jdField_a_of_type_AndroidUtilSparseArray != null) && (this.jdField_a_of_type_AndroidUtilSparseArray.size() > 0))
      {
        g localg = (g)this.jdField_a_of_type_AndroidUtilSparseArray.get(0);
        return localg;
      }
      return null;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final int a()
  {
    try
    {
      int i = this.jdField_a_of_type_Int;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final d a()
  {
    try
    {
      d locald = this.jdField_a_of_type_ComTencentBeacontbsABD;
      return locald;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final com.tencent.beacontbs.upload.e a()
  {
    try
    {
      com.tencent.beacontbs.upload.e locale = this.jdField_a_of_type_ComTencentBeacontbsUploadE;
      return locale;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final Runnable a()
  {
    try
    {
      Runnable localRunnable = this.jdField_a_of_type_JavaLangRunnable;
      return localRunnable;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void a()
  {
    try
    {
      this.jdField_b_of_type_Boolean = true;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void a(int paramInt)
  {
    try
    {
      this.jdField_a_of_type_Int = paramInt;
      com.tencent.beacontbs.c.a.f("step:%d", new Object[] { Integer.valueOf(paramInt) });
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void a(int paramInt, g paramg)
  {
    try
    {
      if (this.jdField_a_of_type_AndroidUtilSparseArray != null)
      {
        if (paramg == null)
        {
          this.jdField_a_of_type_AndroidUtilSparseArray.remove(paramInt);
          return;
        }
        this.jdField_a_of_type_AndroidUtilSparseArray.put(paramInt, paramg);
        paramg.a(a());
      }
      return;
    }
    finally {}
  }
  
  public final void a(int paramInt, Map<String, String> paramMap)
  {
    Object localObject = a();
    if (localObject != null)
    {
      localObject = (f)((SparseArray)localObject).get(paramInt);
      if (localObject != null) {
        ((f)localObject).a(paramMap);
      }
    }
    if (paramInt == 1)
    {
      paramMap = this.jdField_a_of_type_ComTencentBeacontbsABD;
      localObject = paramMap.a("isSocketOnOff");
      if ((localObject != null) && ("n".equals(localObject)))
      {
        paramMap.a(false);
        return;
      }
      paramMap.a(true);
    }
  }
  
  public final void a(a parama)
  {
    if (parama == null) {
      return;
    }
    try
    {
      if (this.jdField_a_of_type_JavaUtilList == null) {
        this.jdField_a_of_type_JavaUtilList = new ArrayList();
      }
      if (!this.jdField_a_of_type_JavaUtilList.contains(parama))
      {
        this.jdField_a_of_type_JavaUtilList.add(parama);
        final int i = a();
        if (b())
        {
          com.tencent.beacontbs.c.a.e("add listener should notify app first run! %s", new Object[] { parama.toString() });
          com.tencent.beacontbs.a.b.a().a(new Runnable()
          {
            public final void run()
            {
              b.this.c();
            }
          });
        }
        if (i >= 2)
        {
          com.tencent.beacontbs.c.a.e("add listener should notify app start query! %s", new Object[] { parama.toString() });
          com.tencent.beacontbs.a.b.a().a(new Runnable()
          {
            public final void run()
            {
              b.this.a();
              if (i >= 3)
              {
                com.tencent.beacontbs.c.a.e("query finished should notify", new Object[0]);
                b.this.b();
              }
            }
          });
        }
      }
      return;
    }
    finally {}
  }
  
  public final void a(e parame)
  {
    if (parame != null) {}
    try
    {
      if ((this.jdField_b_of_type_JavaUtilList != null) && (!this.jdField_b_of_type_JavaUtilList.contains(parame))) {
        this.jdField_b_of_type_JavaUtilList.add(parame);
      }
    }
    finally {}
  }
  
  public final void a(f paramf)
  {
    if (paramf != null) {}
    try
    {
      if (this.jdField_b_of_type_AndroidUtilSparseArray != null) {
        this.jdField_b_of_type_AndroidUtilSparseArray.put(1, paramf);
      }
    }
    finally {}
  }
  
  public final boolean a()
  {
    try
    {
      boolean bool = this.jdField_b_of_type_Boolean;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final a[] a()
  {
    try
    {
      if ((this.jdField_a_of_type_JavaUtilList != null) && (this.jdField_a_of_type_JavaUtilList.size() > 0))
      {
        a[] arrayOfa = (a[])this.jdField_a_of_type_JavaUtilList.toArray(new a[0]);
        return arrayOfa;
      }
      return null;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final e[] a()
  {
    try
    {
      if ((this.jdField_b_of_type_JavaUtilList != null) && (this.jdField_b_of_type_JavaUtilList.size() > 0))
      {
        e[] arrayOfe = (e[])this.jdField_b_of_type_JavaUtilList.toArray(new e[0]);
        return arrayOfe;
      }
      return null;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  protected final void b()
  {
    try
    {
      this.jdField_a_of_type_Boolean = true;
      com.tencent.beacontbs.c.a.f("isFirst }%b", new Object[] { Boolean.valueOf(true) });
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final boolean b()
  {
    try
    {
      boolean bool = this.jdField_a_of_type_Boolean;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\a\b\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */