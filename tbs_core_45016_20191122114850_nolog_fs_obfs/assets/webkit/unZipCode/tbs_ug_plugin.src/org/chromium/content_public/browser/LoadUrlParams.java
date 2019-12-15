package org.chromium.content_public.browser;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content_public.common.Referrer;
import org.chromium.content_public.common.ResourceRequestBody;

@JNINamespace("content")
public class LoadUrlParams
{
  int jdField_a_of_type_Int;
  String jdField_a_of_type_JavaLangString;
  private Map<String, String> jdField_a_of_type_JavaUtilMap;
  Referrer jdField_a_of_type_OrgChromiumContent_publicCommonReferrer;
  ResourceRequestBody jdField_a_of_type_OrgChromiumContent_publicCommonResourceRequestBody;
  boolean jdField_a_of_type_Boolean;
  int jdField_b_of_type_Int;
  String jdField_b_of_type_JavaLangString;
  boolean jdField_b_of_type_Boolean;
  int jdField_c_of_type_Int;
  String jdField_c_of_type_JavaLangString;
  boolean jdField_c_of_type_Boolean;
  String d;
  
  public LoadUrlParams(String paramString)
  {
    this(paramString, 0);
  }
  
  public LoadUrlParams(String paramString, int paramInt)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
    this.jdField_b_of_type_Int = paramInt;
    this.jdField_a_of_type_Int = 0;
    this.jdField_c_of_type_Int = 0;
    this.jdField_a_of_type_OrgChromiumContent_publicCommonResourceRequestBody = null;
    this.jdField_b_of_type_JavaLangString = null;
    this.jdField_c_of_type_JavaLangString = null;
    this.d = null;
  }
  
  private static String a(String paramString1, String paramString2, boolean paramBoolean, String paramString3)
  {
    StringBuilder localStringBuilder = new StringBuilder("data:");
    localStringBuilder.append(paramString2);
    if ((paramString3 != null) && (!paramString3.isEmpty()))
    {
      paramString2 = new StringBuilder();
      paramString2.append(";charset=");
      paramString2.append(paramString3);
      localStringBuilder.append(paramString2.toString());
    }
    if (paramBoolean) {
      localStringBuilder.append(";base64");
    }
    localStringBuilder.append(",");
    localStringBuilder.append(paramString1);
    return localStringBuilder.toString();
  }
  
  private String a(String paramString, boolean paramBoolean)
  {
    if (this.jdField_a_of_type_JavaUtilMap == null) {
      return null;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = this.jdField_a_of_type_JavaUtilMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if (localStringBuilder.length() > 0) {
        localStringBuilder.append(paramString);
      }
      localStringBuilder.append(((String)localEntry.getKey()).toLowerCase(Locale.US));
      localStringBuilder.append(":");
      localStringBuilder.append((String)localEntry.getValue());
    }
    if (paramBoolean) {
      localStringBuilder.append(paramString);
    }
    return localStringBuilder.toString();
  }
  
  public static LoadUrlParams a(String paramString1, String paramString2, boolean paramBoolean)
  {
    return a(paramString1, paramString2, paramBoolean, null);
  }
  
  public static LoadUrlParams a(String paramString1, String paramString2, boolean paramBoolean, String paramString3)
  {
    paramString1 = new LoadUrlParams(a(paramString1, paramString2, paramBoolean, paramString3));
    paramString1.a(2);
    paramString1.b(1);
    return paramString1;
  }
  
  public static LoadUrlParams a(String paramString1, String paramString2, boolean paramBoolean, String paramString3, String paramString4, String paramString5)
  {
    if ((paramString3 != null) && (paramString3.toLowerCase(Locale.US).startsWith("data:"))) {
      return a(paramString1, paramString2, paramBoolean, paramString5);
    }
    LoadUrlParams localLoadUrlParams = a("", paramString2, paramBoolean, paramString5);
    if (paramString3 == null) {
      paramString3 = "about:blank";
    }
    localLoadUrlParams.a(paramString3);
    if (paramString4 == null) {
      paramString4 = "about:blank";
    }
    localLoadUrlParams.b(paramString4);
    localLoadUrlParams.c(a(paramString1, paramString2, paramBoolean, paramString5));
    return localLoadUrlParams;
  }
  
  @VisibleForTesting
  public static LoadUrlParams a(String paramString, byte[] paramArrayOfByte)
  {
    paramString = new LoadUrlParams(paramString);
    paramString.a(1);
    paramString.b(1);
    paramString.a(ResourceRequestBody.a(paramArrayOfByte));
    return paramString;
  }
  
  private static native boolean nativeIsDataScheme(String paramString);
  
  public int a()
  {
    return this.jdField_b_of_type_Int;
  }
  
  public String a()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public Map<String, String> a()
  {
    return this.jdField_a_of_type_JavaUtilMap;
  }
  
  public Referrer a()
  {
    return this.jdField_a_of_type_OrgChromiumContent_publicCommonReferrer;
  }
  
  public ResourceRequestBody a()
  {
    return this.jdField_a_of_type_OrgChromiumContent_publicCommonResourceRequestBody;
  }
  
  public void a(int paramInt)
  {
    this.jdField_a_of_type_Int = paramInt;
  }
  
  public void a(String paramString)
  {
    this.jdField_b_of_type_JavaLangString = paramString;
  }
  
  public void a(Map<String, String> paramMap)
  {
    this.jdField_a_of_type_JavaUtilMap = paramMap;
  }
  
  public void a(Referrer paramReferrer)
  {
    this.jdField_a_of_type_OrgChromiumContent_publicCommonReferrer = paramReferrer;
  }
  
  public void a(ResourceRequestBody paramResourceRequestBody)
  {
    this.jdField_a_of_type_OrgChromiumContent_publicCommonResourceRequestBody = paramResourceRequestBody;
  }
  
  public void a(boolean paramBoolean)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
  }
  
  public boolean a()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public int b()
  {
    return this.jdField_c_of_type_Int;
  }
  
  public String b()
  {
    return this.jdField_b_of_type_JavaLangString;
  }
  
  public void b(int paramInt)
  {
    this.jdField_b_of_type_Int = paramInt;
  }
  
  public void b(String paramString)
  {
    this.jdField_c_of_type_JavaLangString = paramString;
  }
  
  public void b(boolean paramBoolean)
  {
    this.jdField_c_of_type_Boolean = paramBoolean;
  }
  
  public boolean b()
  {
    return this.jdField_b_of_type_Boolean;
  }
  
  public int c()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public String c()
  {
    return a("\n", false);
  }
  
  public void c(int paramInt)
  {
    this.jdField_c_of_type_Int = paramInt;
  }
  
  public void c(String paramString)
  {
    this.d = paramString;
  }
  
  public boolean c()
  {
    return this.jdField_c_of_type_Boolean;
  }
  
  public String d()
  {
    return a("\r\n", true);
  }
  
  public boolean d()
  {
    if ((this.jdField_b_of_type_JavaLangString == null) && (this.jdField_a_of_type_Int == 2)) {
      return true;
    }
    return nativeIsDataScheme(this.jdField_b_of_type_JavaLangString);
  }
  
  public String e()
  {
    return this.jdField_c_of_type_JavaLangString;
  }
  
  public String f()
  {
    return this.d;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\LoadUrlParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */