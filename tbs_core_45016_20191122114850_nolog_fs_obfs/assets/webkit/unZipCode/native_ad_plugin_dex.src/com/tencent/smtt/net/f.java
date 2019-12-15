package com.tencent.smtt.net;

import android.text.TextUtils;
import android.util.SparseArray;
import com.tencent.smtt.webkit.e;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.smtt.webkit.u.b;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class f
{
  private static f jdField_a_of_type_ComTencentSmttNetF;
  private static String jdField_a_of_type_JavaLangString = "PageLoadInfoManager";
  private static ConcurrentLinkedQueue<a> jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue;
  private int jdField_a_of_type_Int = -1;
  private SparseArray<a> jdField_a_of_type_AndroidUtilSparseArray = null;
  
  private a a(int paramInt)
  {
    try
    {
      a locala = (a)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramInt);
      return locala;
    }
    catch (ClassCastException localClassCastException)
    {
      for (;;) {}
    }
    return null;
  }
  
  public static f a()
  {
    if (jdField_a_of_type_ComTencentSmttNetF == null) {
      jdField_a_of_type_ComTencentSmttNetF = new f();
    }
    return jdField_a_of_type_ComTencentSmttNetF;
  }
  
  public void a()
  {
    for (;;)
    {
      Object localObject = jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue;
      if ((localObject == null) || (((ConcurrentLinkedQueue)localObject).isEmpty())) {
        break;
      }
      a locala = (a)jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.poll();
      if (locala != null)
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("url", a.a(locala));
        localHashMap.put("refer", a.b(locala));
        if (a.a(locala)) {
          localObject = "1";
        } else {
          localObject = "0";
        }
        localHashMap.put("proxy", localObject);
        if (a.b(locala)) {
          localObject = "0";
        } else {
          localObject = "1";
        }
        localHashMap.put("result", localObject);
        localHashMap.put("readTime", Long.toString(a.a(locala) - a.b(locala)));
        localHashMap.put("scrollRatio", String.valueOf(locala.b()));
        localHashMap.put("touchCount", String.valueOf(locala.a()));
        localHashMap.put("pageNumber", String.valueOf(locala.a()));
        localHashMap.put("statusCode", String.valueOf(locala.b()));
        localHashMap.put("contentLength", String.valueOf(locala.c()));
        if (e.a().a() != null) {
          localHashMap.put("wpid", e.a().a());
        }
        if (e.a().b() != null) {
          localHashMap.put("wpname", e.a().b());
        }
        if (e.a().c() != null)
        {
          localHashMap.put("weappid", e.a().c());
          localHashMap.put("weuser", e.a().d());
          localHashMap.put("wetitle", e.a().e());
          localHashMap.put("wedzx", e.a().f());
          localHashMap.put("weicon", e.a().g());
          localHashMap.put("wepath", e.a().h());
        }
        SmttServiceProxy.getInstance().uploadTbsDirectInfoToBeacon(localHashMap);
      }
    }
  }
  
  public void a(int paramInt)
  {
    a locala = a(paramInt);
    if (locala != null) {
      locala.a(true);
    }
  }
  
  public void a(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, int paramInt3)
  {
    a locala = a(paramInt1);
    if (locala != null)
    {
      locala.a(System.currentTimeMillis());
      locala.b(paramFloat1);
      locala.a(paramInt3);
      locala.a(paramFloat2);
      if (jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue == null) {
        jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue = new ConcurrentLinkedQueue();
      }
      jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.add(locala);
      this.jdField_a_of_type_AndroidUtilSparseArray.remove(paramInt1);
      a();
    }
  }
  
  public void a(int paramInt1, String paramString, int paramInt2)
  {
    a locala = a(paramInt1);
    if (locala != null)
    {
      paramInt2 = this.jdField_a_of_type_Int;
      if ((paramInt1 == paramInt2) || (paramInt2 == -1)) {
        locala.b(System.currentTimeMillis());
      }
    }
    if ((locala == null) && (paramString != null) && (paramString.startsWith("http")))
    {
      paramString = new a(paramString);
      paramString.b(System.currentTimeMillis());
      this.jdField_a_of_type_AndroidUtilSparseArray.put(paramInt1, paramString);
    }
  }
  
  public void a(int paramInt, String paramString1, String paramString2)
  {
    a locala = a(paramInt);
    if ((locala != null) && (locala != null) && (!TextUtils.isEmpty(paramString1)) && (!TextUtils.isEmpty(paramString2)) && (paramString1.equals(locala.a()))) {
      locala.a(paramString2);
    }
  }
  
  public void a(u.b paramb)
  {
    if (paramb == null) {
      return;
    }
    a locala = a(paramb.jdField_a_of_type_Int);
    if (locala != null)
    {
      a.a(locala, paramb.jdField_a_of_type_Boolean);
      a.a(locala, paramb.jdField_a_of_type_JavaLangString);
      locala.c(paramb.c);
      locala.b(paramb.b);
    }
  }
  
  public void a(String paramString, int paramInt)
  {
    paramString = a(paramInt);
    if (paramString != null) {
      paramString.b(true);
    }
  }
  
  public void b(int paramInt)
  {
    this.jdField_a_of_type_Int = paramInt;
  }
  
  public class a
  {
    private float jdField_a_of_type_Float = 0.0F;
    private int jdField_a_of_type_Int = 0;
    private long jdField_a_of_type_Long = 0L;
    private String jdField_a_of_type_JavaLangString;
    private boolean jdField_a_of_type_Boolean = false;
    private float jdField_b_of_type_Float = 0.0F;
    private int jdField_b_of_type_Int = 0;
    private long jdField_b_of_type_Long = 0L;
    private String jdField_b_of_type_JavaLangString;
    private boolean jdField_b_of_type_Boolean = false;
    private int jdField_c_of_type_Int = 0;
    private boolean jdField_c_of_type_Boolean = false;
    
    a(String paramString)
    {
      this.jdField_a_of_type_JavaLangString = paramString;
    }
    
    public float a()
    {
      return this.jdField_b_of_type_Float;
    }
    
    public int a()
    {
      return this.jdField_a_of_type_Int;
    }
    
    public String a()
    {
      return this.jdField_a_of_type_JavaLangString;
    }
    
    public void a(float paramFloat)
    {
      this.jdField_b_of_type_Float = paramFloat;
    }
    
    public void a(int paramInt)
    {
      this.jdField_a_of_type_Int = paramInt;
    }
    
    public void a(long paramLong)
    {
      this.jdField_b_of_type_Long = paramLong;
    }
    
    public void a(String paramString)
    {
      this.jdField_a_of_type_JavaLangString = paramString;
    }
    
    public void a(boolean paramBoolean)
    {
      this.jdField_c_of_type_Boolean = paramBoolean;
    }
    
    public float b()
    {
      return this.jdField_a_of_type_Float;
    }
    
    public int b()
    {
      return this.jdField_b_of_type_Int;
    }
    
    public void b(float paramFloat)
    {
      this.jdField_a_of_type_Float = paramFloat;
    }
    
    public void b(int paramInt)
    {
      this.jdField_b_of_type_Int = paramInt;
    }
    
    public void b(long paramLong)
    {
      this.jdField_a_of_type_Long = paramLong;
    }
    
    public void b(boolean paramBoolean)
    {
      this.jdField_b_of_type_Boolean = paramBoolean;
    }
    
    public int c()
    {
      return this.jdField_c_of_type_Int;
    }
    
    public void c(int paramInt)
    {
      this.jdField_c_of_type_Int = paramInt;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */