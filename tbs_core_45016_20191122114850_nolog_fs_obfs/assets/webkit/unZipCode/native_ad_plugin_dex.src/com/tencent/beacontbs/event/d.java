package com.tencent.beacontbs.event;

import android.content.Context;
import com.tencent.beacontbs.a.e;
import java.util.HashMap;
import java.util.Map;

public final class d
{
  private static d jdField_a_of_type_ComTencentBeacontbsEventD;
  private String jdField_a_of_type_JavaLangString = "";
  private String b = "";
  private String c = "";
  private String d = "";
  private String e = "";
  private String f = "";
  
  private d(Context paramContext)
  {
    if (paramContext == null) {
      com.tencent.beacontbs.c.a.d(" DetailUserInfo context == null? pls check!", new Object[0]);
    }
    com.tencent.beacontbs.c.a.b(" start to create DetailUserInfo", new Object[0]);
    long l = System.currentTimeMillis();
    e.a(paramContext);
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(e.a(paramContext));
    this.jdField_a_of_type_JavaLangString = ((StringBuilder)localObject).toString();
    localObject = new StringBuilder(" imei:");
    ((StringBuilder)localObject).append(this.jdField_a_of_type_JavaLangString);
    com.tencent.beacontbs.c.a.b(((StringBuilder)localObject).toString(), new Object[0]);
    if (!"".equals(this.jdField_a_of_type_JavaLangString)) {}
    try
    {
      if ("".equals(com.tencent.beacontbs.a.a.a(paramContext, "IMEI_DENGTA", ""))) {
        com.tencent.beacontbs.a.a.a(paramContext, "IMEI_DENGTA", this.jdField_a_of_type_JavaLangString);
      }
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder;
      for (;;) {}
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(e.c(paramContext));
    this.d = ((StringBuilder)localObject).toString();
    this.e = com.tencent.beacontbs.a.a.a(paramContext, "QIMEI_DENGTA", "");
    if ("".equals(this.e))
    {
      if (this.jdField_a_of_type_JavaLangString.equals("")) {
        localObject = this.d;
      } else {
        localObject = this.jdField_a_of_type_JavaLangString;
      }
      this.e = ((String)localObject);
    }
    if (c.a == null) {
      c.a = new HashMap();
    }
    localObject = c.a;
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.e);
    ((Map)localObject).put("A3", localStringBuilder.toString());
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(e.d(paramContext));
    this.b = ((StringBuilder)localObject).toString();
    if ("".equals(this.b))
    {
      localStringBuilder = new StringBuilder();
      localObject = e.c("/sys/class/net/eth0/address");
      if ((!((String)localObject).trim().equals("")) && (((String)localObject).length() >= 17)) {
        localObject = ((String)localObject).toLowerCase().substring(0, 17);
      } else {
        localObject = "";
      }
      localStringBuilder.append((String)localObject);
      this.b = localStringBuilder.toString();
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(e.b(paramContext));
    this.c = ((StringBuilder)localObject).toString();
    new StringBuilder().append(e.d());
    paramContext = new StringBuilder();
    paramContext.append(e.e());
    paramContext.append("m");
    this.f = e.f();
    com.tencent.beacontbs.c.a.b(" detail create cost: %d  values:\n %s", new Object[] { Long.valueOf(System.currentTimeMillis() - l), toString() });
  }
  
  public static d a(Context paramContext)
  {
    try
    {
      if (jdField_a_of_type_ComTencentBeacontbsEventD == null) {
        jdField_a_of_type_ComTencentBeacontbsEventD = new d(paramContext);
      }
      paramContext = jdField_a_of_type_ComTencentBeacontbsEventD;
      return paramContext;
    }
    finally {}
  }
  
  public final String a()
  {
    try
    {
      String str = this.jdField_a_of_type_JavaLangString;
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final String b()
  {
    try
    {
      String str = this.b;
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
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
  
  public final String e()
  {
    try
    {
      String str = this.e;
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final String f()
  {
    return this.f;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\event\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */