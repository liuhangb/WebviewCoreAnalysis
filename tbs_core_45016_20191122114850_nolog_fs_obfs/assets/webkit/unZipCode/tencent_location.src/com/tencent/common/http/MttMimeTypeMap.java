package com.tencent.common.http;

import java.util.HashMap;

public class MttMimeTypeMap
{
  private static MttMimeTypeMap jdField_a_of_type_ComTencentCommonHttpMttMimeTypeMap;
  private HashMap<String, String> jdField_a_of_type_JavaUtilHashMap = new HashMap();
  
  public static MttMimeTypeMap getSingleton()
  {
    if (jdField_a_of_type_ComTencentCommonHttpMttMimeTypeMap == null)
    {
      jdField_a_of_type_ComTencentCommonHttpMttMimeTypeMap = new MttMimeTypeMap();
      jdField_a_of_type_ComTencentCommonHttpMttMimeTypeMap.jdField_a_of_type_JavaUtilHashMap.put("3gp", "video/3gpp");
      jdField_a_of_type_ComTencentCommonHttpMttMimeTypeMap.jdField_a_of_type_JavaUtilHashMap.put("chm", "text/plain");
      jdField_a_of_type_ComTencentCommonHttpMttMimeTypeMap.jdField_a_of_type_JavaUtilHashMap.put("ape", "audio/x-ape");
    }
    return jdField_a_of_type_ComTencentCommonHttpMttMimeTypeMap;
  }
  
  public String getMimeTypeFromExtension(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0))
    {
      String str = (String)this.jdField_a_of_type_JavaUtilHashMap.get(paramString.toLowerCase());
      if (str != null) {
        return str;
      }
    }
    return MimeTypeMap.getSingleton().getMimeTypeFromExtension(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\MttMimeTypeMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */