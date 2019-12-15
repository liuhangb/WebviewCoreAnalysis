package com.tencent.common.utils;

import com.tencent.common.http.ContentType;
import java.util.HashMap;

public class ContentTypeUtils
{
  private static Object jdField_a_of_type_JavaLangObject = new Object();
  private static HashMap<String, ContentType> jdField_a_of_type_JavaUtilHashMap;
  
  private static HashMap<String, ContentType> a()
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      Object localObject2;
      if (jdField_a_of_type_JavaUtilHashMap != null)
      {
        localObject2 = jdField_a_of_type_JavaUtilHashMap;
        return (HashMap<String, ContentType>)localObject2;
      }
      if (jdField_a_of_type_JavaUtilHashMap == null)
      {
        jdField_a_of_type_JavaUtilHashMap = new HashMap();
        localObject2 = new ContentType("text", "html", "utf-8");
        jdField_a_of_type_JavaUtilHashMap.put("html", localObject2);
        jdField_a_of_type_JavaUtilHashMap.put("htm", localObject2);
        jdField_a_of_type_JavaUtilHashMap.put("txt", new ContentType("text", "plain", "utf-8"));
        jdField_a_of_type_JavaUtilHashMap.put("css", new ContentType("text", "css", "utf-8"));
        jdField_a_of_type_JavaUtilHashMap.put("js", new ContentType("text", "javascript", "utf-8"));
        jdField_a_of_type_JavaUtilHashMap.put("png", new ContentType("image", "png", "binary"));
        localObject2 = new ContentType("image", "jpeg", "binary");
        jdField_a_of_type_JavaUtilHashMap.put("jpg", localObject2);
        jdField_a_of_type_JavaUtilHashMap.put("jpeg", localObject2);
        jdField_a_of_type_JavaUtilHashMap.put("gif", new ContentType("image", "gif", "binary"));
      }
      return jdField_a_of_type_JavaUtilHashMap;
    }
  }
  
  public static ContentType getContentType(String paramString)
  {
    paramString = FileUtilsF.getFileExt(paramString);
    if (paramString != null) {
      paramString = (ContentType)a().get(paramString.toLowerCase());
    } else {
      paramString = null;
    }
    Object localObject = paramString;
    if (paramString == null) {
      localObject = new ContentType("application", "octet-stream", "binary");
    }
    return (ContentType)localObject;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\ContentTypeUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */