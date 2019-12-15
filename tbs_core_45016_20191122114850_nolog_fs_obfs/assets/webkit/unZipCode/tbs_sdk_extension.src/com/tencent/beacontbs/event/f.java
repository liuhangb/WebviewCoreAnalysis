package com.tencent.beacontbs.event;

import android.content.Context;
import com.tencent.beacontbs.a.b;
import com.tencent.beacontbs.a.e;
import com.tencent.beacontbs.upload.g;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class f
{
  private int jdField_a_of_type_Int = 20000;
  private Context jdField_a_of_type_AndroidContentContext;
  private j jdField_a_of_type_ComTencentBeacontbsEventJ;
  private Runnable jdField_a_of_type_JavaLangRunnable = new Runnable()
  {
    public final void run()
    {
      try
      {
        f.this.a();
        return;
      }
      catch (Throwable localThrowable)
      {
        com.tencent.beacontbs.c.a.a(localThrowable);
      }
    }
  };
  private int b = 0;
  
  public f(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    HashMap localHashMap = new HashMap(2);
    boolean bool = com.tencent.beacontbs.a.a.d(this.jdField_a_of_type_AndroidContentContext);
    e.a(paramContext);
    localHashMap.put("A33", e.e(paramContext));
    if (bool) {
      localHashMap.put("A66", "F");
    } else {
      localHashMap.put("A66", "B");
    }
    paramContext = new StringBuilder();
    paramContext.append(com.tencent.beacontbs.a.a.a(this.jdField_a_of_type_AndroidContentContext));
    localHashMap.put("A68", paramContext.toString());
    if (com.tencent.beacontbs.a.a.a) {
      paramContext = "Y";
    } else {
      paramContext = "N";
    }
    localHashMap.put("A85", paramContext);
    this.jdField_a_of_type_ComTencentBeacontbsEventJ = e.a(this.jdField_a_of_type_AndroidContentContext, "rqd_heartbeat", true, 0L, 0L, localHashMap, false);
  }
  
  private int a()
  {
    try
    {
      int i = this.b;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  private void a(int paramInt)
  {
    try
    {
      this.b = paramInt;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  protected final void a()
  {
    if (!e.b(this.jdField_a_of_type_AndroidContentContext)) {
      return;
    }
    g localg = k.a().a();
    if (localg != null)
    {
      ArrayList localArrayList = new ArrayList(2);
      localArrayList.add(this.jdField_a_of_type_ComTencentBeacontbsEventJ);
      localg.a(new i.a(this.jdField_a_of_type_AndroidContentContext, localArrayList));
    }
    a(a() + 1);
    if (a() % 10 == 0)
    {
      b.a().a(108, this.jdField_a_of_type_JavaLangRunnable, 600000L, this.jdField_a_of_type_Int);
      a(0);
    }
  }
  
  public final void b()
  {
    String str = com.tencent.beacontbs.a.a.a(this.jdField_a_of_type_AndroidContentContext, "HEART_DENGTA", "");
    if (e.g().equals(str))
    {
      com.tencent.beacontbs.c.a.a("heartbeat has been uploaded today!", new Object[0]);
      return;
    }
    b.a().a(108, this.jdField_a_of_type_JavaLangRunnable, 0L, this.jdField_a_of_type_Int);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\event\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */