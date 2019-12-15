package org.chromium.android_webview;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
public class AwWebResourceResponse
{
  private int jdField_a_of_type_Int;
  private InputStream jdField_a_of_type_JavaIoInputStream;
  private String jdField_a_of_type_JavaLangString;
  private Map<String, String> jdField_a_of_type_JavaUtilMap;
  private String[] jdField_a_of_type_ArrayOfJavaLangString;
  private String jdField_b_of_type_JavaLangString;
  private String[] jdField_b_of_type_ArrayOfJavaLangString;
  private String c;
  
  public AwWebResourceResponse(String paramString1, String paramString2, InputStream paramInputStream)
  {
    this.jdField_a_of_type_JavaLangString = paramString1;
    this.jdField_b_of_type_JavaLangString = paramString2;
    this.jdField_a_of_type_JavaIoInputStream = paramInputStream;
  }
  
  public AwWebResourceResponse(String paramString1, String paramString2, InputStream paramInputStream, int paramInt, String paramString3, Map<String, String> paramMap)
  {
    this(paramString1, paramString2, paramInputStream);
    this.jdField_a_of_type_Int = paramInt;
    this.c = paramString3;
    this.jdField_a_of_type_JavaUtilMap = paramMap;
  }
  
  private void a()
  {
    Object localObject = this.jdField_a_of_type_JavaUtilMap;
    if (localObject != null)
    {
      if (this.jdField_a_of_type_ArrayOfJavaLangString != null) {
        return;
      }
      this.jdField_a_of_type_ArrayOfJavaLangString = new String[((Map)localObject).size()];
      this.jdField_b_of_type_ArrayOfJavaLangString = new String[this.jdField_a_of_type_JavaUtilMap.size()];
      int i = 0;
      localObject = this.jdField_a_of_type_JavaUtilMap.entrySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
        this.jdField_a_of_type_ArrayOfJavaLangString[i] = ((String)localEntry.getKey());
        this.jdField_b_of_type_ArrayOfJavaLangString[i] = ((String)localEntry.getValue());
        i += 1;
      }
      return;
    }
  }
  
  @CalledByNative
  private String[] getResponseHeaderNames()
  {
    a();
    return this.jdField_a_of_type_ArrayOfJavaLangString;
  }
  
  @CalledByNative
  private String[] getResponseHeaderValues()
  {
    a();
    return this.jdField_b_of_type_ArrayOfJavaLangString;
  }
  
  @CalledByNative
  public String getCharset()
  {
    return this.jdField_b_of_type_JavaLangString;
  }
  
  @CalledByNative
  public InputStream getData()
  {
    return this.jdField_a_of_type_JavaIoInputStream;
  }
  
  @CalledByNative
  public String getMimeType()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  @CalledByNative
  public String getReasonPhrase()
  {
    return this.c;
  }
  
  public Map<String, String> getResponseHeaders()
  {
    return this.jdField_a_of_type_JavaUtilMap;
  }
  
  @CalledByNative
  public int getStatusCode()
  {
    return this.jdField_a_of_type_Int;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwWebResourceResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */