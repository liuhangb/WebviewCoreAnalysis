package com.tencent.beacontbs.event;

import android.content.Context;
import com.tencent.beacontbs.a.b;
import com.tencent.beacontbs.a.e;
import java.util.ArrayList;
import java.util.List;

public final class a
  implements h
{
  private long jdField_a_of_type_Long = 60000L;
  private Context jdField_a_of_type_AndroidContentContext;
  private Object jdField_a_of_type_JavaLangObject = new Object();
  private Runnable jdField_a_of_type_JavaLangRunnable = new Runnable()
  {
    public final void run()
    {
      a.this.a();
    }
  };
  private List<j> jdField_a_of_type_JavaUtilList;
  private boolean jdField_a_of_type_Boolean = false;
  private Object jdField_b_of_type_JavaLangObject = new Object();
  private Runnable jdField_b_of_type_JavaLangRunnable = new Runnable()
  {
    public final void run()
    {
      a.a(a.this);
    }
  };
  private Object jdField_c_of_type_JavaLangObject = new Object();
  private Runnable jdField_c_of_type_JavaLangRunnable = new Runnable()
  {
    public final void run()
    {
      com.tencent.beacontbs.c.a.f(" db events to up", new Object[0]);
      try
      {
        k.a(false);
        return;
      }
      catch (Throwable localThrowable)
      {
        com.tencent.beacontbs.c.a.a(localThrowable);
      }
    }
  };
  
  public a(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_JavaUtilList = new ArrayList(25);
  }
  
  private List<j> a()
  {
    try
    {
      if ((this.jdField_a_of_type_JavaUtilList.size() > 0) && (a()))
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.addAll(this.jdField_a_of_type_JavaUtilList);
        this.jdField_a_of_type_JavaUtilList.clear();
        StringBuilder localStringBuilder = new StringBuilder(" get MN:");
        localStringBuilder.append(localArrayList.size());
        com.tencent.beacontbs.c.a.b(localStringBuilder.toString(), new Object[0]);
        return localArrayList;
      }
      return null;
    }
    finally {}
  }
  
  private boolean a()
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
  
  private void b()
  {
    synchronized (this.jdField_c_of_type_JavaLangObject)
    {
      if (!a())
      {
        com.tencent.beacontbs.c.a.c(" err su 1R", new Object[0]);
        return;
      }
      List localList = a();
      if ((localList != null) && (localList.size() > 0)) {
        e.a(this.jdField_a_of_type_AndroidContentContext, localList);
      }
      return;
    }
  }
  
  protected final void a()
  {
    for (;;)
    {
      synchronized (this.jdField_b_of_type_JavaLangObject)
      {
        if (!a())
        {
          com.tencent.beacontbs.c.a.c(" err su 1R", new Object[0]);
          return;
        }
        Object localObject2 = a();
        if ((localObject2 != null) && (((List)localObject2).size() > 0))
        {
          Object localObject4 = e.a(this.jdField_a_of_type_AndroidContentContext, (List)localObject2);
          localObject2 = com.tencent.beacontbs.upload.h.a(this.jdField_a_of_type_AndroidContentContext);
          if (localObject4 != null)
          {
            long l2 = k.a().a.e();
            long l1 = l2;
            if (e.a(this.jdField_a_of_type_AndroidContentContext))
            {
              localObject4 = new StringBuilder(" onwifi, so half mSZ ");
              ((StringBuilder)localObject4).append(l2);
              com.tencent.beacontbs.c.a.e(((StringBuilder)localObject4).toString(), new Object[0]);
              l1 = l2 / 2L;
            }
            i = e.b(this.jdField_a_of_type_AndroidContentContext);
            localObject4 = new StringBuilder("countCommomRecordNum: ");
            ((StringBuilder)localObject4).append(i);
            com.tencent.beacontbs.c.a.b(((StringBuilder)localObject4).toString(), new Object[0]);
            if (i < l1) {
              break label287;
            }
            i = 1;
            if ((i != 0) && (((com.tencent.beacontbs.upload.h)localObject2).a()) && (((com.tencent.beacontbs.upload.h)localObject2).b()))
            {
              this.jdField_c_of_type_JavaLangRunnable.run();
              com.tencent.beacontbs.c.a.e(" common max up", new Object[0]);
            }
          }
        }
        else
        {
          localObject2 = k.a().a;
          if (localObject2 == null) {
            break label292;
          }
          bool = ((g)localObject2).b();
          if (bool)
          {
            this.jdField_c_of_type_JavaLangRunnable.run();
            com.tencent.beacontbs.c.a.e(" common polling up", new Object[0]);
          }
        }
        return;
      }
      label287:
      int i = 0;
      continue;
      label292:
      boolean bool = false;
    }
  }
  
  public final void a(boolean paramBoolean)
  {
    try
    {
      if (this.jdField_a_of_type_Boolean != paramBoolean)
      {
        if (paramBoolean)
        {
          this.jdField_a_of_type_Boolean = paramBoolean;
          this.jdField_a_of_type_Long = (k.a().a.d() * 1000);
          b.a().a(102, this.jdField_a_of_type_JavaLangRunnable, this.jdField_a_of_type_Long, this.jdField_a_of_type_Long);
          return;
        }
        b.a().a(102);
        b.a().a(112);
        b(true);
        this.jdField_a_of_type_Boolean = paramBoolean;
      }
      return;
    }
    finally {}
  }
  
  public final boolean a(j paramj)
  {
    Object localObject2 = this.jdField_a_of_type_JavaLangObject;
    Object localObject1;
    if (paramj == null) {
      localObject1 = "null";
    }
    try
    {
      localObject1 = paramj.b();
      com.tencent.beacontbs.c.a.f(" BF eN:%s   isRT:%b ", new Object[] { localObject1, Boolean.valueOf(false) });
      if ((this.jdField_a_of_type_AndroidContentContext != null) && (paramj != null) && (this.jdField_a_of_type_Boolean))
      {
        if (!a())
        {
          com.tencent.beacontbs.c.a.d(" CommonProcess processUA return false, isEnable is false !", new Object[0]);
          return false;
        }
        localObject1 = k.a().a;
        int i = ((g)localObject1).c();
        this.jdField_a_of_type_Long = (((g)localObject1).d() * 1000);
        int j = this.jdField_a_of_type_JavaUtilList.size();
        if (j >= i)
        {
          com.tencent.beacontbs.c.a.f(" BF mN!", new Object[0]);
          b.a().a(this.jdField_a_of_type_JavaLangRunnable);
          b.a().a(102, this.jdField_a_of_type_JavaLangRunnable, this.jdField_a_of_type_Long, this.jdField_a_of_type_Long);
        }
        this.jdField_a_of_type_JavaUtilList.add(paramj);
        if (this.jdField_a_of_type_JavaUtilList.size() >= i)
        {
          paramj = new StringBuilder(" err BF 3R! list size:");
          paramj.append(j);
          com.tencent.beacontbs.c.a.c(paramj.toString(), new Object[0]);
        }
        com.tencent.beacontbs.c.a.a("CommonprocessUA:true!", new Object[0]);
        return true;
      }
      com.tencent.beacontbs.c.a.d(" err BF 1R", new Object[0]);
      return false;
    }
    finally {}
  }
  
  public final void b(boolean paramBoolean)
  {
    try
    {
      com.tencent.beacontbs.c.a.e("common process flush memory objects to db.", new Object[0]);
      if (paramBoolean)
      {
        b();
        return;
      }
      b.a().a(this.jdField_b_of_type_JavaLangRunnable);
      return;
    }
    finally {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\event\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */