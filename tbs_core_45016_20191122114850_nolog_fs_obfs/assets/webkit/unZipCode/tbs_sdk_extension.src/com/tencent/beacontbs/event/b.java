package com.tencent.beacontbs.event;

import android.content.Context;
import com.tencent.beacontbs.a.e;
import com.tencent.beacontbs.d.c;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class b
  extends com.tencent.beacontbs.upload.b
{
  private List<Long> jdField_a_of_type_JavaUtilList = null;
  private boolean jdField_a_of_type_Boolean = false;
  private byte[] jdField_a_of_type_ArrayOfByte = null;
  private Long[] jdField_a_of_type_ArrayOfJavaLangLong = null;
  private Context b = null;
  
  public b(Context paramContext)
  {
    super(paramContext, 1, 4);
    this.b = paramContext;
  }
  
  private com.tencent.beacontbs.b.a.a a(List<j> paramList)
  {
    Object localObject1;
    int i;
    Object localObject2;
    if (paramList != null)
    {
      if (paramList.size() <= 0) {
        return null;
      }
      localObject1 = new ArrayList();
      int j = paramList.size();
      this.jdField_a_of_type_JavaUtilList = new ArrayList();
      i = 0;
      if (i < j) {}
      try
      {
        localObject2 = (j)paramList.get(i);
        Object localObject3 = ((j)localObject2).a();
        StringBuilder localStringBuilder = new StringBuilder(" bean.getTP: ");
        localStringBuilder.append(((j)localObject2).a());
        com.tencent.beacontbs.c.a.a(localStringBuilder.toString(), new Object[0]);
        if ((localObject3 == null) || (!"UA".equals(((j)localObject2).a()))) {
          break label377;
        }
        com.tencent.beacontbs.c.a.f(" Pack2Upload eventName:}%s ", new Object[] { ((j)localObject2).b() });
        localObject3 = e.a((j)localObject2);
        if (localObject3 != null) {
          ((ArrayList)localObject1).add(localObject3);
        } else {
          this.jdField_a_of_type_JavaUtilList.add(Long.valueOf(((j)localObject2).a()));
        }
      }
      catch (Throwable paramList)
      {
        com.tencent.beacontbs.c.a.a(paramList);
        com.tencent.beacontbs.c.a.d(" CommonRecordUploadDatas.encode2MixPackage() error1", new Object[0]);
      }
      if (this.jdField_a_of_type_JavaUtilList.size() > 0) {
        e.a(this.b, (Long[])this.jdField_a_of_type_JavaUtilList.toArray(new Long[this.jdField_a_of_type_JavaUtilList.size()]));
      }
      paramList = new StringBuilder(" up erList:");
      paramList.append(((ArrayList)localObject1).size());
      com.tencent.beacontbs.c.a.b(paramList.toString(), new Object[0]);
    }
    label377:
    label384:
    do
    {
      try
      {
        if (((ArrayList)localObject1).size() <= 0) {
          break label384;
        }
        paramList = new com.tencent.beacontbs.b.b.b();
        paramList.a = ((ArrayList)localObject1);
      }
      catch (Exception paramList)
      {
        com.tencent.beacontbs.c.a.a(paramList);
        com.tencent.beacontbs.c.a.d(" CommonRecordUploadDatas.encode2MixPackage() error2", new Object[0]);
        a();
        return null;
      }
      localObject1 = new HashMap();
      localObject2 = new com.tencent.beacontbs.d.b();
      paramList.a((com.tencent.beacontbs.d.b)localObject2);
      ((Map)localObject1).put(Integer.valueOf(1), ((com.tencent.beacontbs.d.b)localObject2).a());
      paramList = new com.tencent.beacontbs.b.a.a();
      paramList.a = ((Map)localObject1);
      return paramList;
      return null;
      i += 1;
      break;
      paramList = null;
    } while (paramList != null);
    return null;
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
  
  public final com.tencent.beacontbs.b.a.b a()
  {
    for (;;)
    {
      Object localObject4;
      try
      {
        Object localObject1 = k.a();
        Object localObject6 = null;
        if ((localObject1 != null) && (((k)localObject1).a()))
        {
          localObject4 = ((k)localObject1).a;
          if (localObject4 == null)
          {
            com.tencent.beacontbs.c.a.c(" imposiable! ua S not ready!", new Object[0]);
            return null;
          }
          try
          {
            if (!a()) {
              break label465;
            }
            localObject5 = com.tencent.beacontbs.a.a.a.a(this.b);
            if ((localObject5 == null) || (((List)localObject5).size() <= 0)) {
              break label465;
            }
            localObject1 = (byte[])((List)localObject5).get(3);
            this.jdField_a_of_type_JavaLangString = String.valueOf(((List)localObject5).get(1));
            this.c = ((Integer)((List)localObject5).get(4)).intValue();
            if (localObject1 != null)
            {
              localObject1 = a(this.jdField_a_of_type_Int, (byte[])localObject1);
              return (com.tencent.beacontbs.b.a.b)localObject1;
            }
            int j = ((g)localObject4).e();
            i = j;
            if (!e.a(this.b)) {
              i = j / 2;
            }
            if (i < 0) {
              break label470;
            }
            localObject4 = e.a(this.b, i);
            if ((localObject4 != null) && (((List)localObject4).size() > 0))
            {
              i = ((List)localObject4).size();
              com.tencent.beacontbs.c.a.h(" size:%d", new Object[] { Integer.valueOf(i) });
              this.c = i;
            }
          }
          catch (Throwable localThrowable)
          {
            Object localObject5;
            int i;
            com.tencent.beacontbs.c.a.a(localThrowable);
            com.tencent.beacontbs.c.a.d(" get req datas error: %s", new Object[] { localThrowable.toString() });
            return null;
          }
        }
      }
      finally {}
      try
      {
        localObject5 = a((List)localObject4);
      }
      catch (Exception localException2)
      {
        continue;
      }
      a();
      localObject5 = null;
      this.jdField_a_of_type_ArrayOfJavaLangLong = new Long[i];
      i = 0;
      if (i < this.jdField_a_of_type_ArrayOfJavaLangLong.length)
      {
        this.jdField_a_of_type_ArrayOfJavaLangLong[i] = Long.valueOf(((j)((List)localObject4).get(i)).a());
        i += 1;
      }
      else
      {
        ((List)localObject4).clear();
        if (localObject5 != null)
        {
          localObject1 = new com.tencent.beacontbs.d.b();
          ((c)localObject5).a((com.tencent.beacontbs.d.b)localObject1);
          localObject1 = ((com.tencent.beacontbs.d.b)localObject1).a();
        }
        this.jdField_a_of_type_ArrayOfByte = new byte[localObject1.length];
        System.arraycopy(localObject1, 0, this.jdField_a_of_type_ArrayOfByte, 0, localObject1.length);
        this.jdField_a_of_type_JavaLangString = e.a(this.jdField_a_of_type_AndroidContentContext, 4);
        com.tencent.beacontbs.c.a.a("comm rid:%s", new Object[] { this.jdField_a_of_type_JavaLangString });
        try
        {
          localObject1 = a(this.jdField_a_of_type_Int, (byte[])localObject1);
        }
        catch (Exception localException1)
        {
          continue;
        }
        a();
        localObject1 = localObject6;
        return (com.tencent.beacontbs.b.a.b)localObject1;
        com.tencent.beacontbs.c.a.h(" no up datas", new Object[0]);
        return null;
        com.tencent.beacontbs.c.a.c(" imposiable! ua not ready!", new Object[0]);
        return null;
        label465:
        Object localObject3 = null;
        continue;
        label470:
        localObject4 = null;
      }
    }
  }
  
  public final void a()
  {
    try
    {
      com.tencent.beacontbs.c.a.c(" encode failed, clear db data", new Object[0]);
      if ((this.jdField_a_of_type_ArrayOfJavaLangLong != null) && (this.jdField_a_of_type_ArrayOfJavaLangLong.length > 0))
      {
        int i = e.a(this.b, this.jdField_a_of_type_ArrayOfJavaLangLong);
        StringBuilder localStringBuilder = new StringBuilder(" remove num :");
        localStringBuilder.append(i);
        com.tencent.beacontbs.c.a.b(localStringBuilder.toString(), new Object[0]);
        this.jdField_a_of_type_ArrayOfJavaLangLong = null;
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void a(boolean paramBoolean)
  {
    try
    {
      this.jdField_a_of_type_Boolean = paramBoolean;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void b(boolean paramBoolean)
  {
    for (;;)
    {
      try
      {
        if (this.jdField_a_of_type_ArrayOfJavaLangLong != null)
        {
          i = this.jdField_a_of_type_ArrayOfJavaLangLong.length;
          if (i > 0)
          {
            int j = e.a(this.b, this.jdField_a_of_type_ArrayOfJavaLangLong);
            StringBuilder localStringBuilder = new StringBuilder(" t_event remove num :");
            localStringBuilder.append(j);
            com.tencent.beacontbs.c.a.b(localStringBuilder.toString(), new Object[0]);
          }
          this.jdField_a_of_type_ArrayOfJavaLangLong = null;
          if ((paramBoolean) && (a()))
          {
            com.tencent.beacontbs.a.a.a.a(this.b, this.jdField_a_of_type_JavaLangString);
          }
          else if ((!paramBoolean) && (this.jdField_a_of_type_ArrayOfByte != null))
          {
            com.tencent.beacontbs.c.a.a("comm rid2:%s", new Object[] { this.jdField_a_of_type_JavaLangString });
            com.tencent.beacontbs.a.a.a.a(this.b, this.jdField_a_of_type_ArrayOfByte, this.jdField_a_of_type_JavaLangString, i);
          }
          this.jdField_a_of_type_ArrayOfByte = null;
          return;
        }
      }
      finally {}
      int i = 0;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\event\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */