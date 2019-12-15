package com.tencent.smtt.memory;

import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.util.HashMap;

public class a
{
  private String jdField_a_of_type_JavaLangString;
  private HashMap<String, String> jdField_a_of_type_JavaUtilHashMap;
  
  public static void a(String paramString, HashMap<String, String> paramHashMap)
  {
    SmttServiceProxy.getInstance().onBeaconReport(paramString, paramHashMap);
  }
  
  public String a()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public HashMap<String, String> a()
  {
    return this.jdField_a_of_type_JavaUtilHashMap;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\memory\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */