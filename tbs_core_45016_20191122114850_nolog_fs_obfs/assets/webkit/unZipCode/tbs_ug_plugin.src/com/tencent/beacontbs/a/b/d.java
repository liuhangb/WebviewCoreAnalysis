package com.tencent.beacontbs.a.b;

import android.content.Context;
import android.util.Base64;
import android.util.SparseArray;
import com.tencent.beacontbs.a.e;
import com.tencent.beacontbs.c.a;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class d
{
  private static d jdField_a_of_type_ComTencentBeacontbsABD;
  private byte jdField_a_of_type_Byte = 3;
  private int jdField_a_of_type_Int = 360;
  private SparseArray<a> jdField_a_of_type_AndroidUtilSparseArray = null;
  private String jdField_a_of_type_JavaLangString = "http://oth.str.mdt.qq.com:8080/analytics/upload";
  private Map<String, String> jdField_a_of_type_JavaUtilMap = null;
  private boolean jdField_a_of_type_Boolean = true;
  private byte jdField_b_of_type_Byte = 2;
  private int jdField_b_of_type_Int = 100;
  private String jdField_b_of_type_JavaLangString = "";
  private String c = "";
  private String d = "";
  
  private d()
  {
    this.jdField_a_of_type_AndroidUtilSparseArray.put(1, new a(1));
    this.jdField_a_of_type_AndroidUtilSparseArray.put(2, new a(2));
    this.jdField_a_of_type_AndroidUtilSparseArray.put(3, new a(3));
  }
  
  public static d a()
  {
    if (jdField_a_of_type_ComTencentBeacontbsABD == null) {
      try
      {
        if (jdField_a_of_type_ComTencentBeacontbsABD == null) {
          jdField_a_of_type_ComTencentBeacontbsABD = new d();
        }
      }
      finally {}
    }
    return jdField_a_of_type_ComTencentBeacontbsABD;
  }
  
  private void b(String paramString)
  {
    try
    {
      this.c = paramString;
      this.jdField_b_of_type_JavaLangString = Base64.encodeToString(e.a(paramString), 2);
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  private void c(String paramString)
  {
    try
    {
      this.d = paramString;
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public final byte a()
  {
    try
    {
      byte b1 = this.jdField_a_of_type_Byte;
      return b1;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final int a()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public final SparseArray<a> a()
  {
    try
    {
      if (this.jdField_a_of_type_AndroidUtilSparseArray != null)
      {
        new com.tencent.beacontbs.c.b();
        SparseArray localSparseArray = com.tencent.beacontbs.c.b.a(this.jdField_a_of_type_AndroidUtilSparseArray);
        return localSparseArray;
      }
      return null;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final a a(int paramInt)
  {
    try
    {
      if (this.jdField_a_of_type_AndroidUtilSparseArray != null)
      {
        a locala = (a)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramInt);
        return locala;
      }
      return null;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final String a()
  {
    return e.a(a().jdField_a_of_type_Boolean ^ true, false, false, this.jdField_a_of_type_JavaLangString);
  }
  
  public final String a(String paramString)
  {
    Object localObject = (a)this.jdField_a_of_type_AndroidUtilSparseArray.get(1);
    if (localObject != null)
    {
      localObject = ((a)localObject).a();
      if (localObject != null) {
        return (String)((Map)localObject).get(paramString);
      }
    }
    return null;
  }
  
  public final Map<String, String> a()
  {
    return this.jdField_a_of_type_JavaUtilMap;
  }
  
  public final void a(int paramInt)
  {
    this.jdField_a_of_type_Int = paramInt;
  }
  
  final void a(Context paramContext)
  {
    Object[] arrayOfObject;
    long l3;
    long l1;
    long l2;
    label55:
    try
    {
      arrayOfObject = e.a(paramContext, "sid");
      if ((arrayOfObject != null) && (arrayOfObject.length == 3))
      {
        l3 = new Date().getTime() / 1000L;
        l1 = 0L;
      }
    }
    finally {}
    try
    {
      l2 = ((Long)arrayOfObject[2]).longValue();
      l1 = l2;
    }
    catch (Exception localException)
    {
      break label55;
    }
    if (l1 > l3) {
      c((String)arrayOfObject[1]);
    }
    b(e.f(paramContext));
  }
  
  public final void a(final Context paramContext, final String paramString1, final String paramString2)
  {
    try
    {
      this.d = paramString1;
      paramContext = new Runnable()
      {
        public final void run()
        {
          String str = paramString1;
          try
          {
            l1 = e.a(paramString2).getTime() / 1000L;
          }
          catch (Exception localException)
          {
            long l1;
            long l2;
            Context localContext;
            for (;;) {}
          }
          l1 = 0L;
          l2 = l1;
          if (l1 == 0L) {
            l2 = new Date().getTime() / 1000L + 86400L;
          }
          localContext = paramContext;
          d.this.getClass();
          e.a(localContext, "sid", new Object[] { str, Long.valueOf(l2) });
        }
      };
      com.tencent.beacontbs.a.b.a().a(paramContext);
      return;
    }
    finally
    {
      paramContext = finally;
      throw paramContext;
    }
  }
  
  public final void a(String paramString)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
  }
  
  public final void a(Map<String, String> paramMap)
  {
    this.jdField_a_of_type_JavaUtilMap = paramMap;
  }
  
  public final void a(boolean paramBoolean)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
  }
  
  public final boolean a()
  {
    try
    {
      if (this.jdField_a_of_type_JavaUtilMap != null)
      {
        String str = (String)this.jdField_a_of_type_JavaUtilMap.get("zeroPeak");
        if ((str != null) && ("y".equalsIgnoreCase(str)))
        {
          int i = Calendar.getInstance().get(11);
          if (i == 0) {
            return true;
          }
        }
      }
      return false;
    }
    finally {}
  }
  
  public final byte b()
  {
    try
    {
      byte b1 = this.jdField_b_of_type_Byte;
      return b1;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final int b()
  {
    Object localObject = this.jdField_a_of_type_JavaUtilMap;
    int i;
    if (localObject != null)
    {
      localObject = (String)((Map)localObject).get("maxStrategyQueryOneDay");
      if ((localObject != null) && (!((String)localObject).trim().equals(""))) {
        i = this.jdField_b_of_type_Int;
      }
    }
    try
    {
      int j = Integer.valueOf((String)localObject).intValue();
      return j;
    }
    catch (Exception localException) {}
    return this.jdField_b_of_type_Int;
    return i;
  }
  
  public final String b()
  {
    try
    {
      String str = this.jdField_b_of_type_JavaLangString;
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final boolean b()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public final int c()
  {
    Object localObject = (a)this.jdField_a_of_type_AndroidUtilSparseArray.get(1);
    if (localObject != null)
    {
      localObject = ((a)localObject).a();
      if (localObject != null)
      {
        localObject = (String)((Map)localObject).get("socketPort");
        if (localObject != null) {
          return Integer.valueOf((String)localObject).intValue();
        }
      }
    }
    return 8081;
  }
  
  public final String c()
  {
    try
    {
      String str = this.c;
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final String d()
  {
    try
    {
      String str = this.d;
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static final class a
  {
    private final int jdField_a_of_type_Int;
    private String jdField_a_of_type_JavaLangString = "http://oth.eve.mdt.qq.com:8080/analytics/upload";
    private Map<String, String> jdField_a_of_type_JavaUtilMap = null;
    private Set<String> jdField_a_of_type_JavaUtilSet = null;
    private boolean jdField_a_of_type_Boolean = false;
    private Set<String> b = null;
    
    public a(int paramInt)
    {
      this.jdField_a_of_type_Int = paramInt;
    }
    
    public final int a()
    {
      return this.jdField_a_of_type_Int;
    }
    
    public final String a()
    {
      boolean bool3 = d.a().b();
      String str = d.a().a("stopTest");
      boolean bool1 = false;
      boolean bool2;
      if (str != null) {
        bool2 = "y".equals(str);
      } else {
        bool2 = false;
      }
      boolean bool4 = a.b;
      if (!bool2) {
        bool1 = true;
      }
      return e.a(bool3 ^ true, true, bool4 & bool1, this.jdField_a_of_type_JavaLangString);
    }
    
    public final Map<String, String> a()
    {
      return this.jdField_a_of_type_JavaUtilMap;
    }
    
    public final Set<String> a()
    {
      return this.jdField_a_of_type_JavaUtilSet;
    }
    
    public final void a(String paramString)
    {
      this.jdField_a_of_type_JavaLangString = paramString;
    }
    
    public final void a(Map<String, String> paramMap)
    {
      this.jdField_a_of_type_JavaUtilMap = paramMap;
    }
    
    public final void a(Set<String> paramSet)
    {
      this.jdField_a_of_type_JavaUtilSet = paramSet;
    }
    
    public final void a(boolean paramBoolean)
    {
      this.jdField_a_of_type_Boolean = paramBoolean;
    }
    
    public final boolean a()
    {
      return this.jdField_a_of_type_Boolean;
    }
    
    public final Set<String> b()
    {
      return this.b;
    }
    
    public final void b(Set<String> paramSet)
    {
      this.b = paramSet;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\a\b\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */