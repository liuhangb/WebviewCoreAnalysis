package com.tencent.beacontbs.event;

import android.content.Context;
import com.tencent.beacontbs.a.b.d.a;
import com.tencent.beacontbs.a.c;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

public final class k
  extends Observable
  implements com.tencent.beacontbs.a.b.a, com.tencent.beacontbs.a.b.e, com.tencent.beacontbs.a.b.f
{
  private static k jdField_a_of_type_ComTencentBeacontbsEventK;
  private int jdField_a_of_type_Int = 0;
  public final Context a;
  private com.tencent.beacontbs.a.g jdField_a_of_type_ComTencentBeacontbsAG = null;
  public g a;
  private h jdField_a_of_type_ComTencentBeacontbsEventH = null;
  private com.tencent.beacontbs.upload.g jdField_a_of_type_ComTencentBeacontbsUploadG;
  private Object jdField_a_of_type_JavaLangObject = new Object();
  private List<e> jdField_a_of_type_JavaUtilList = null;
  private boolean jdField_a_of_type_Boolean = true;
  private h jdField_b_of_type_ComTencentBeacontbsEventH = null;
  private boolean jdField_b_of_type_Boolean = false;
  private boolean c;
  
  private k(Context paramContext, com.tencent.beacontbs.upload.g paramg, com.tencent.beacontbs.upload.a parama)
  {
    this.jdField_a_of_type_ComTencentBeacontbsEventG = null;
    if (paramContext == null)
    {
      com.tencent.beacontbs.c.a.c(" the context is null! init UserActionRecord failed!", new Object[0]);
      this.jdField_a_of_type_AndroidContentContext = null;
      return;
    }
    Context localContext = paramContext.getApplicationContext();
    if (localContext != null) {
      this.jdField_a_of_type_AndroidContentContext = localContext;
    } else {
      this.jdField_a_of_type_AndroidContentContext = paramContext;
    }
    if (c.a() == null) {
      c.a(this.jdField_a_of_type_AndroidContentContext);
    }
    if (this.jdField_a_of_type_JavaUtilList == null) {
      this.jdField_a_of_type_JavaUtilList = Collections.synchronizedList(new ArrayList(5));
    }
    this.jdField_a_of_type_ComTencentBeacontbsUploadG = paramg;
    if (paramg != null) {
      paramg.a(parama);
    }
    parama = com.tencent.beacontbs.a.b.b.a(this.jdField_a_of_type_AndroidContentContext);
    parama.a(this);
    parama.a(this);
    parama.a(this);
    parama.a(0, paramg);
    parama.a(1, paramg);
    this.jdField_a_of_type_ComTencentBeacontbsEventG = new g();
    this.jdField_a_of_type_ComTencentBeacontbsEventH = new a(paramContext);
    this.jdField_b_of_type_ComTencentBeacontbsEventH = new i(paramContext);
    this.jdField_a_of_type_Boolean = true;
    if (com.tencent.beacontbs.upload.h.a(paramContext).a()) {
      new com.tencent.beacontbs.a.d().a(this.jdField_a_of_type_AndroidContentContext);
    }
    this.jdField_a_of_type_ComTencentBeacontbsAG = new com.tencent.beacontbs.a.g();
  }
  
  private h a()
  {
    try
    {
      h localh = this.jdField_a_of_type_ComTencentBeacontbsEventH;
      return localh;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static k a()
  {
    try
    {
      k localk = jdField_a_of_type_ComTencentBeacontbsEventK;
      return localk;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static k a(Context paramContext, com.tencent.beacontbs.upload.g paramg, com.tencent.beacontbs.upload.a parama)
  {
    try
    {
      if (jdField_a_of_type_ComTencentBeacontbsEventK == null)
      {
        com.tencent.beacontbs.c.a.e(" create ua instance ", new Object[0]);
        jdField_a_of_type_ComTencentBeacontbsEventK = new k(paramContext, paramg, parama);
      }
      com.tencent.beacontbs.c.a.e(" return ua instance ", new Object[0]);
      paramContext = jdField_a_of_type_ComTencentBeacontbsEventK;
      return paramContext;
    }
    finally {}
  }
  
  public static com.tencent.beacontbs.upload.g a(Context paramContext, boolean paramBoolean)
  {
    try
    {
      paramContext = com.tencent.beacontbs.upload.h.a(paramContext, paramBoolean);
      return paramContext;
    }
    finally
    {
      paramContext = finally;
      throw paramContext;
    }
  }
  
  private void a(int paramInt)
  {
    try
    {
      this.jdField_a_of_type_Int = paramInt;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static void a(boolean paramBoolean)
  {
    Object localObject = a();
    if (localObject != null)
    {
      h localh = ((k)localObject).a();
      if (localh != null) {
        localh.b(paramBoolean);
      }
      localObject = ((k)localObject).b();
      if (localObject != null) {
        ((h)localObject).b(paramBoolean);
      }
    }
  }
  
  public static boolean a(String paramString, boolean paramBoolean1, long paramLong1, long paramLong2, Map<String, String> paramMap, boolean paramBoolean2, boolean paramBoolean3)
  {
    com.tencent.beacontbs.c.a.e(" onUA: %s,%b,%d,%d,%b,%b", new Object[] { paramString, Boolean.valueOf(paramBoolean1), Long.valueOf(paramLong1), Long.valueOf(paramLong2), Boolean.valueOf(paramBoolean2), Boolean.valueOf(paramBoolean3) });
    k localk = a();
    if ((localk != null) && (!localk.d()))
    {
      localk.jdField_a_of_type_JavaUtilList.add(new e(paramString, paramBoolean1, paramLong1, paramLong2, paramMap, paramBoolean2));
      return true;
    }
    if (!b()) {
      return false;
    }
    if (localk.jdField_a_of_type_ComTencentBeacontbsEventG.a(paramString))
    {
      com.tencent.beacontbs.c.a.c("onUserAction return false, because eventName:[%s] is not allowed in server strategy!", new Object[] { paramString });
      return false;
    }
    if ((paramBoolean1) && ((!paramBoolean1) || (!localk.jdField_a_of_type_ComTencentBeacontbsEventG.b(paramString))))
    {
      com.tencent.beacontbs.c.a.c("onUserAction return false, because eventName:[%s] is sampled by svr rate!", new Object[] { paramString });
      return false;
    }
    h localh;
    if (paramBoolean2) {
      localh = localk.b();
    } else {
      localh = localk.a();
    }
    if (localh != null)
    {
      paramString = com.tencent.beacontbs.a.e.a(localk.jdField_a_of_type_AndroidContentContext, paramString, paramBoolean1, paramLong1, paramLong2, paramMap, paramBoolean3);
      if (paramString == null)
      {
        com.tencent.beacontbs.c.a.c("createdRecordBean bean is null, return false!", new Object[0]);
        return false;
      }
      return localh.a(paramString);
    }
    return false;
  }
  
  public static boolean a(boolean paramBoolean)
  {
    k localk = a();
    if (localk == null)
    {
      com.tencent.beacontbs.c.a.c(" ua module not ready!", new Object[0]);
      return false;
    }
    if (!b()) {
      return false;
    }
    return localk.b(paramBoolean);
  }
  
  private h b()
  {
    try
    {
      h localh = this.jdField_b_of_type_ComTencentBeacontbsEventH;
      return localh;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  private static boolean b()
  {
    k localk = a();
    if (localk == null)
    {
      com.tencent.beacontbs.c.a.d("isModuleAble:not init ua", new Object[0]);
      return false;
    }
    boolean bool2 = localk.a();
    boolean bool1 = bool2;
    if (bool2)
    {
      bool1 = bool2;
      if (localk.c()) {
        bool1 = localk.d();
      }
    }
    return bool1;
  }
  
  private boolean c()
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
  
  private void d()
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
  
  private boolean d()
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
  
  private void e()
  {
    try
    {
      if ((this.jdField_a_of_type_JavaUtilList != null) && (this.jdField_a_of_type_JavaUtilList.size() > 0))
      {
        Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
        while (localIterator.hasNext())
        {
          e locale = (e)localIterator.next();
          a(locale.jdField_a_of_type_JavaLangString, locale.jdField_a_of_type_Boolean, locale.jdField_a_of_type_Long, locale.jdField_b_of_type_Long, locale.jdField_a_of_type_JavaUtilMap, locale.jdField_b_of_type_Boolean, false);
        }
        this.jdField_a_of_type_JavaUtilList.clear();
      }
      return;
    }
    finally {}
  }
  
  private void f()
  {
    try
    {
      if ((!a()) && (!this.jdField_a_of_type_ComTencentBeacontbsEventG.a())) {
        return;
      }
      Object localObject = com.tencent.beacontbs.a.b.b.a(this.jdField_a_of_type_AndroidContentContext);
      if (localObject != null)
      {
        localObject = ((com.tencent.beacontbs.a.b.b)localObject).a().a(1);
        if (localObject != null)
        {
          localObject = ((d.a)localObject).a();
          if ((localObject != null) && (!"".equals(((String)localObject).trim()))) {
            new f(this.jdField_a_of_type_AndroidContentContext).b();
          }
        }
      }
      return;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder(" startHeart failed! ");
      localStringBuilder.append(localException.getMessage());
      com.tencent.beacontbs.c.a.c(localStringBuilder.toString(), new Object[0]);
    }
  }
  
  private void g()
  {
    int i;
    if (this.jdField_a_of_type_ComTencentBeacontbsEventG.d())
    {
      i = 1;
      if (!"".equals(com.tencent.beacontbs.a.a.a(this.jdField_a_of_type_AndroidContentContext, "LAUEVE_DENGTA", ""))) {
        com.tencent.beacontbs.c.a.a("AppLaunchedEvent has been uploaded!", new Object[0]);
      }
    }
    else
    {
      i = 0;
    }
    if (com.tencent.beacontbs.a.e.a(this.jdField_a_of_type_AndroidContentContext) == null)
    {
      com.tencent.beacontbs.c.a.c(" DeviceInfo == null?,return", new Object[0]);
      return;
    }
    HashMap localHashMap = new HashMap();
    localHashMap.put("A33", com.tencent.beacontbs.a.e.e(this.jdField_a_of_type_AndroidContentContext));
    localHashMap.put("A63", "Y");
    if (com.tencent.beacontbs.a.a.a(this.jdField_a_of_type_AndroidContentContext)) {
      localHashMap.put("A21", "Y");
    } else {
      localHashMap.put("A21", "N");
    }
    if (com.tencent.beacontbs.a.b.b.a(this.jdField_a_of_type_AndroidContentContext).b()) {
      localHashMap.put("A45", "Y");
    } else {
      localHashMap.put("A45", "N");
    }
    if (com.tencent.beacontbs.a.a.d(this.jdField_a_of_type_AndroidContentContext)) {
      localHashMap.put("A66", "F");
    } else {
      localHashMap.put("A66", "B");
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(com.tencent.beacontbs.a.a.a(this.jdField_a_of_type_AndroidContentContext));
    localHashMap.put("A68", ((StringBuilder)localObject).toString());
    if (com.tencent.beacontbs.a.a.jdField_a_of_type_Boolean) {
      localObject = "Y";
    } else {
      localObject = "N";
    }
    localHashMap.put("A85", localObject);
    localHashMap.put("A9", com.tencent.beacontbs.a.e.d());
    localHashMap.put("A14", com.tencent.beacontbs.a.e.e());
    boolean bool = a("rqd_applaunched", true, 0L, 0L, localHashMap, true, false);
    if ((i != 0) && (bool)) {
      com.tencent.beacontbs.a.a.a(this.jdField_a_of_type_AndroidContentContext, "LAUEVE_DENGTA", com.tencent.beacontbs.a.e.g());
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
  
  public final com.tencent.beacontbs.upload.g a()
  {
    return this.jdField_a_of_type_ComTencentBeacontbsUploadG;
  }
  
  public final void a()
  {
    a(a() + 1);
  }
  
  public final void a(com.tencent.beacontbs.a.b.d paramd)
  {
    if (paramd != null)
    {
      paramd = paramd.a(1);
      if (paramd != null)
      {
        boolean bool = paramd.a();
        if (a() != bool)
        {
          com.tencent.beacontbs.c.a.f("UAR onCommonStrategyChange setUsable:%b ", new Object[] { Boolean.valueOf(bool) });
          b(bool);
        }
      }
    }
  }
  
  public final void a(Map<String, String> paramMap)
  {
    if ((paramMap != null) && (paramMap.size() > 0))
    {
      g localg = this.jdField_a_of_type_ComTencentBeacontbsEventG;
      if (localg != null) {
        localg.a(paramMap);
      }
    }
  }
  
  public final boolean a()
  {
    try
    {
      boolean bool = this.c;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void b()
  {
    d();
    for (;;)
    {
      try
      {
        Object localObject = com.tencent.beacontbs.a.b.b.a(this.jdField_a_of_type_AndroidContentContext).a();
        if (localObject != null)
        {
          localObject = ((com.tencent.beacontbs.a.b.d)localObject).a(1);
          if ((localObject != null) && (this.jdField_a_of_type_ComTencentBeacontbsEventG != null))
          {
            Set localSet = ((d.a)localObject).a();
            if ((localSet != null) && (localSet.size() > 0)) {
              this.jdField_a_of_type_ComTencentBeacontbsEventG.a(localSet);
            }
            localSet = ((d.a)localObject).b();
            if ((localSet != null) && (localSet.size() > 0)) {
              this.jdField_a_of_type_ComTencentBeacontbsEventG.b(localSet);
            }
          }
          if ((a()) && (localObject != null))
          {
            if (!a()) {
              break label264;
            }
            i = com.tencent.beacontbs.a.e.b(this.jdField_a_of_type_AndroidContentContext);
            break label266;
            if (i != 0)
            {
              com.tencent.beacontbs.c.a.e(" asyn up module %d", new Object[] { Integer.valueOf(1) });
              com.tencent.beacontbs.a.b.a().a(new Runnable()
              {
                public final void run()
                {
                  k.this.b(true);
                }
              });
            }
          }
          else
          {
            com.tencent.beacontbs.c.a.b("event module is disable", new Object[0]);
          }
        }
      }
      catch (Throwable localThrowable)
      {
        com.tencent.beacontbs.c.a.a(localThrowable);
        com.tencent.beacontbs.c.a.d(" common query end error %s", new Object[] { localThrowable.toString() });
      }
      e();
      if (a() < 2)
      {
        com.tencent.beacontbs.upload.h localh = com.tencent.beacontbs.upload.h.a(this.jdField_a_of_type_AndroidContentContext);
        if ((localh.a()) && (localh.b()))
        {
          f();
          g();
          if (this.jdField_a_of_type_ComTencentBeacontbsEventG.e())
          {
            this.jdField_a_of_type_ComTencentBeacontbsAG.setChanged();
            this.jdField_a_of_type_ComTencentBeacontbsAG.notifyObservers(this.jdField_a_of_type_AndroidContentContext);
          }
        }
      }
      return;
      label264:
      int i = -1;
      label266:
      if (i > 0) {
        i = 1;
      } else {
        i = 0;
      }
    }
  }
  
  public final void b(boolean paramBoolean)
  {
    try
    {
      if (paramBoolean != a())
      {
        h localh = a();
        if (localh != null) {
          localh.a(paramBoolean);
        }
        localh = b();
        if (localh != null) {
          localh.a(paramBoolean);
        }
        this.c = paramBoolean;
      }
      return;
    }
    finally {}
  }
  
  public final boolean b(boolean paramBoolean)
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      int i;
      if (a()) {
        i = com.tencent.beacontbs.a.e.b(this.jdField_a_of_type_AndroidContentContext);
      } else {
        i = -1;
      }
      if (i > 0) {
        try
        {
          if (this.jdField_a_of_type_ComTencentBeacontbsUploadG != null)
          {
            b localb = new b(this.jdField_a_of_type_AndroidContentContext);
            localb.a(paramBoolean);
            this.jdField_a_of_type_ComTencentBeacontbsUploadG.a(localb);
          }
          return true;
        }
        catch (Throwable localThrowable)
        {
          com.tencent.beacontbs.c.a.c(" up common error: %s", new Object[] { localThrowable.toString() });
          com.tencent.beacontbs.c.a.a(localThrowable);
        }
      }
      return false;
    }
  }
  
  public final void c()
  {
    Context localContext = this.jdField_a_of_type_AndroidContentContext;
    com.tencent.beacontbs.c.a.a(" RecordDAO.deleteRecords() start", new Object[0]);
    com.tencent.beacontbs.c.a.e(" ua first clean :%d", new Object[] { Integer.valueOf(com.tencent.beacontbs.a.a.a.a(localContext, new int[] { 1 })) });
    com.tencent.beacontbs.c.a.e(" ua remove strategy :%d", new Object[] { Integer.valueOf(com.tencent.beacontbs.a.e.a(this.jdField_a_of_type_AndroidContentContext)) });
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\event\k.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */