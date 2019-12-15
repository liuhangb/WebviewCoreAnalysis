package com.tencent.turingfd.sdk.tbs;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class aa
  implements Runnable
{
  /* Error */
  public aa(ab paramab, android.content.Context paramContext, Map paramMap)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_2
    //   2: putfield 13	com/tencent/turingfd/sdk/tbs/aa:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   5: aload_0
    //   6: aload_3
    //   7: putfield 15	com/tencent/turingfd/sdk/tbs/aa:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   10: aload_0
    //   11: invokespecial 18	java/lang/Object:<init>	()V
    //   14: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	15	0	this	aa
    //   0	15	1	paramab	ab
    //   0	15	2	paramContext	android.content.Context
    //   0	15	3	paramMap	Map
  }
  
  public void run()
  {
    SharedPreferences.Editor localEditor = ab.a(this.jdField_a_of_type_AndroidContentContext).edit();
    Iterator localIterator = this.jdField_a_of_type_JavaUtilMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      String str2 = (String)this.jdField_a_of_type_JavaUtilMap.get(str1);
      try
      {
        localEditor.putString(str1, l.b(l.a(str2.getBytes(), l.a())));
      }
      catch (Throwable localThrowable2)
      {
        for (;;)
        {
          try
          {
            localEditor.commit();
            return;
          }
          catch (Throwable localThrowable1) {}
          localThrowable2 = localThrowable2;
        }
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\turingfd\sdk\tbs\aa.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */