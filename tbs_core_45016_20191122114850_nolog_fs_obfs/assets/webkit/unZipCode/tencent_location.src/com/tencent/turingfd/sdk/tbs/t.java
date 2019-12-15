package com.tencent.turingfd.sdk.tbs;

import android.os.IBinder;

public final class t
{
  public static q a;
  public static final String a;
  public static final String b = o.a(o.p);
  
  static
  {
    jdField_a_of_type_JavaLangString = o.a(o.o);
  }
  
  public static IBinder a(String paramString)
  {
    try
    {
      Object localObject = jdField_a_of_type_ComTencentTuringfdSdkTbsQ;
      if (localObject == null)
      {
        localObject = r.a(jdField_a_of_type_JavaLangString, b, null, null, null);
        if (!(localObject instanceof IBinder))
        {
          localObject = null;
        }
        else
        {
          jdField_a_of_type_ComTencentTuringfdSdkTbsQ = u.a((IBinder)localObject);
          localObject = jdField_a_of_type_ComTencentTuringfdSdkTbsQ;
        }
      }
      localObject = (v)localObject;
      paramString = ((v)localObject).a(paramString);
      return paramString;
    }
    catch (Throwable paramString) {}
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\turingfd\sdk\tbs\t.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */