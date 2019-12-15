package com.tencent.beacontbs.event;

import android.content.Context;
import com.tencent.beacontbs.a.e;
import com.tencent.beacontbs.d.c;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class i
  implements h
{
  private Context jdField_a_of_type_AndroidContentContext;
  private Runnable jdField_a_of_type_JavaLangRunnable = new Runnable()
  {
    public final void run()
    {
      try
      {
        i.this.a();
        return;
      }
      catch (Throwable localThrowable)
      {
        com.tencent.beacontbs.c.a.a(localThrowable);
      }
    }
  };
  private List<j> jdField_a_of_type_JavaUtilList;
  private boolean jdField_a_of_type_Boolean = false;
  private Runnable b = new Runnable()
  {
    public final void run()
    {
      i.a(i.this);
    }
  };
  
  public i(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_JavaUtilList = Collections.synchronizedList(new ArrayList(25));
  }
  
  private List<j> a()
  {
    try
    {
      if ((this.jdField_a_of_type_JavaUtilList != null) && (this.jdField_a_of_type_JavaUtilList.size() > 0) && (a()))
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.addAll(this.jdField_a_of_type_JavaUtilList);
        this.jdField_a_of_type_JavaUtilList.clear();
        StringBuilder localStringBuilder = new StringBuilder(" get realEventCnt in Mem:");
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
    List localList = a();
    if ((localList != null) && (localList.size() > 0))
    {
      StringBuilder localStringBuilder = new StringBuilder(" dsb real events 2 db");
      localStringBuilder.append(localList.size());
      com.tencent.beacontbs.c.a.f(localStringBuilder.toString(), new Object[0]);
      e.a(this.jdField_a_of_type_AndroidContentContext, localList);
    }
  }
  
  protected final void a()
  {
    if (!a())
    {
      com.tencent.beacontbs.c.a.c(" err su 1R", new Object[0]);
      return;
    }
    List localList = a();
    if ((localList != null) && (localList.size() > 0))
    {
      Object localObject = k.a().a();
      if ((e.b(this.jdField_a_of_type_AndroidContentContext)) && (localObject != null))
      {
        StringBuilder localStringBuilder = new StringBuilder(" dsu real events 2 up ");
        localStringBuilder.append(localList.size());
        com.tencent.beacontbs.c.a.f(localStringBuilder.toString(), new Object[0]);
        ((com.tencent.beacontbs.upload.g)localObject).a(new a(this.jdField_a_of_type_AndroidContentContext, localList));
        return;
      }
      localObject = new StringBuilder(" dsu real events 2 db");
      ((StringBuilder)localObject).append(localList.size());
      com.tencent.beacontbs.c.a.f(((StringBuilder)localObject).toString(), new Object[0]);
      e.a(this.jdField_a_of_type_AndroidContentContext, localList);
      return;
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
          long l = k.a().a.b() * 1000;
          com.tencent.beacontbs.a.b.a().a(103, this.jdField_a_of_type_JavaLangRunnable, 5000L, l);
          return;
        }
        com.tencent.beacontbs.a.b.a().a(103);
        b(true);
        this.jdField_a_of_type_Boolean = paramBoolean;
      }
      return;
    }
    finally {}
  }
  
  public final boolean a(j paramj)
  {
    Object localObject;
    if (paramj == null) {
      localObject = "null";
    }
    try
    {
      localObject = paramj.b();
      com.tencent.beacontbs.c.a.f(" BF eN:%s   isRT:%b", new Object[] { localObject, Boolean.valueOf(true) });
      if ((this.jdField_a_of_type_AndroidContentContext != null) && (paramj != null))
      {
        if (!a())
        {
          com.tencent.beacontbs.c.a.c("processUA return false, isEnable is false !", new Object[0]);
          return false;
        }
        localObject = k.a().a;
        int i = ((g)localObject).a();
        long l = ((g)localObject).b() * 1000;
        if ((this.jdField_a_of_type_JavaUtilList.size() >= i) || (paramj.a()))
        {
          com.tencent.beacontbs.c.a.f(" BF mN!", new Object[0]);
          com.tencent.beacontbs.a.b.a().a(this.jdField_a_of_type_JavaLangRunnable);
          com.tencent.beacontbs.a.b.a().a(103, this.jdField_a_of_type_JavaLangRunnable, l, l);
        }
        this.jdField_a_of_type_JavaUtilList.add(paramj);
        if (this.jdField_a_of_type_JavaUtilList.size() >= i)
        {
          localObject = new StringBuilder(" err BF 3R! num:");
          ((StringBuilder)localObject).append(this.jdField_a_of_type_JavaUtilList.size());
          com.tencent.beacontbs.c.a.c(((StringBuilder)localObject).toString(), new Object[0]);
        }
        if ("rqd_applaunched".equals(paramj.b())) {
          com.tencent.beacontbs.a.b.a().a(this.jdField_a_of_type_JavaLangRunnable);
        }
        com.tencent.beacontbs.c.a.a("processUA:true!", new Object[0]);
        return true;
      }
      com.tencent.beacontbs.c.a.c("processUA return false, context is null or bean is null !", new Object[0]);
      return false;
    }
    finally {}
  }
  
  public final void b(boolean paramBoolean)
  {
    try
    {
      com.tencent.beacontbs.c.a.e("realtime process flush memory objects to db.", new Object[0]);
      if (paramBoolean)
      {
        b();
        return;
      }
      com.tencent.beacontbs.a.b.a().a(this.b);
      return;
    }
    finally {}
  }
  
  static final class a
    extends com.tencent.beacontbs.upload.b
  {
    private List<j> jdField_a_of_type_JavaUtilList = null;
    private boolean jdField_a_of_type_Boolean = false;
    private Long[] jdField_a_of_type_ArrayOfJavaLangLong = null;
    private Context b;
    
    public a(Context paramContext, List<j> paramList)
    {
      super(1, 2);
      this.jdField_a_of_type_JavaUtilList = paramList;
      this.b = paramContext;
      this.c = this.jdField_a_of_type_JavaUtilList.size();
      if ((this.jdField_a_of_type_JavaUtilList.size() == 1) && ("rqd_heartbeat".equals(((j)this.jdField_a_of_type_JavaUtilList.get(0)).b()))) {
        this.jdField_a_of_type_Boolean = true;
      }
      this.jdField_a_of_type_JavaLangString = e.a(paramContext, 2);
      com.tencent.beacontbs.c.a.a("real rid:%s", new Object[] { this.jdField_a_of_type_JavaLangString });
    }
    
    private static com.tencent.beacontbs.b.a.b a(int paramInt, List<j> paramList)
    {
      if (paramList != null)
      {
        if (paramList.size() <= 0) {
          return null;
        }
        try
        {
          Object localObject = new StringBuilder(" current size:");
          ((StringBuilder)localObject).append(paramList.size());
          com.tencent.beacontbs.c.a.b(((StringBuilder)localObject).toString(), new Object[0]);
          paramList = a(paramList);
          if (paramList == null) {
            return null;
          }
          localObject = new com.tencent.beacontbs.d.b();
          paramList.a((com.tencent.beacontbs.d.b)localObject);
          paramList = a(paramInt, ((com.tencent.beacontbs.d.b)localObject).a());
          return paramList;
        }
        catch (Throwable paramList)
        {
          com.tencent.beacontbs.c.a.a(paramList);
          com.tencent.beacontbs.c.a.d(" RealTimeRecordUploadDatas.encode2EventRecordPackage error}", new Object[0]);
          return null;
        }
      }
      return null;
    }
    
    private static com.tencent.beacontbs.b.b.b a(List<j> paramList)
    {
      if (paramList != null)
      {
        if (paramList.size() <= 0) {
          return null;
        }
        try
        {
          com.tencent.beacontbs.b.b.b localb = new com.tencent.beacontbs.b.b.b();
          ArrayList localArrayList = new ArrayList();
          paramList = paramList.iterator();
          while (paramList.hasNext())
          {
            com.tencent.beacontbs.b.b.a locala = e.a((j)paramList.next());
            if (locala != null) {
              localArrayList.add(locala);
            }
          }
          localb.a = localArrayList;
          com.tencent.beacontbs.c.a.b(" RealTimeRecordUploadDatas.encode2EventRecordPackage() end}", new Object[0]);
          return localb;
        }
        catch (Throwable paramList)
        {
          com.tencent.beacontbs.c.a.a(paramList);
          return null;
        }
      }
      return null;
    }
    
    public final com.tencent.beacontbs.b.a.b a()
    {
      try
      {
        com.tencent.beacontbs.c.a.b(" TUUD.GetUD start", new Object[0]);
        if (this.jdField_a_of_type_JavaUtilList != null)
        {
          int i = this.jdField_a_of_type_JavaUtilList.size();
          if (i > 0) {
            try
            {
              com.tencent.beacontbs.b.a.b localb = a(this.jdField_a_of_type_Int, this.jdField_a_of_type_JavaUtilList);
              if (localb != null) {
                return localb;
              }
            }
            catch (Throwable localThrowable)
            {
              com.tencent.beacontbs.c.a.a(localThrowable);
              com.tencent.beacontbs.c.a.d(" TUUD.GetUD start error", new Object[0]);
              return null;
            }
          }
        }
        return null;
      }
      finally {}
    }
    
    public final void b(boolean paramBoolean)
    {
      try
      {
        com.tencent.beacontbs.c.a.b(" TimeUpUploadDatas.done(), result:%b", new Object[] { Boolean.valueOf(paramBoolean) });
        if ((this.jdField_a_of_type_JavaUtilList != null) && (!paramBoolean))
        {
          com.tencent.beacontbs.c.a.f(" upload failed, save to db", new Object[0]);
          if (!this.jdField_a_of_type_Boolean)
          {
            this.jdField_a_of_type_ArrayOfJavaLangLong = e.a(this.b, this.jdField_a_of_type_JavaUtilList);
            this.jdField_a_of_type_JavaUtilList = null;
          }
        }
        if ((paramBoolean) && (this.jdField_a_of_type_Boolean))
        {
          Context localContext = this.b;
          com.tencent.beacontbs.a.b.a().a(108);
          com.tencent.beacontbs.a.a.a(localContext, "HEART_DENGTA", e.g());
          com.tencent.beacontbs.c.a.a("heartbeat uploaded sucess!", new Object[0]);
        }
        if ((paramBoolean) && (this.jdField_a_of_type_ArrayOfJavaLangLong != null)) {
          e.a(this.b, this.jdField_a_of_type_ArrayOfJavaLangLong);
        }
        if ((paramBoolean) && (this.jdField_a_of_type_ArrayOfJavaLangLong == null) && (this.jdField_a_of_type_JavaUtilList != null)) {
          this.jdField_a_of_type_JavaUtilList = null;
        }
        return;
      }
      finally {}
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\event\i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */