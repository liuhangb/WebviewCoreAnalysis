package com.tencent.common.http;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MttResponse
  extends ResponseBase
{
  public static final Integer UNKNOWN_STATUS = new Integer(-1);
  private int jdField_a_of_type_Int = 0;
  private long jdField_a_of_type_Long;
  private ContentType jdField_a_of_type_ComTencentCommonHttpContentType;
  private MttInputStream jdField_a_of_type_ComTencentCommonHttpMttInputStream;
  private Integer jdField_a_of_type_JavaLangInteger = UNKNOWN_STATUS;
  private String jdField_a_of_type_JavaLangString;
  private Map<String, List<String>> jdField_a_of_type_JavaUtilMap;
  private int jdField_b_of_type_Int = -1;
  private long jdField_b_of_type_Long;
  private MttInputStream jdField_b_of_type_ComTencentCommonHttpMttInputStream;
  private String jdField_b_of_type_JavaLangString;
  private Map<String, List<String>> jdField_b_of_type_JavaUtilMap = null;
  private String c;
  private String d;
  private String e;
  private String f;
  private String g;
  private String h = null;
  private String i = null;
  
  public long getContentLength()
  {
    return this.jdField_a_of_type_Long;
  }
  
  public ContentType getContentType()
  {
    return this.jdField_a_of_type_ComTencentCommonHttpContentType;
  }
  
  public Map<String, List<String>> getCookies()
  {
    return this.jdField_a_of_type_JavaUtilMap;
  }
  
  public String getETag()
  {
    return this.c;
  }
  
  public MttInputStream getErrorStream()
  {
    return this.jdField_b_of_type_ComTencentCommonHttpMttInputStream;
  }
  
  public int getFlow()
  {
    int j = this.jdField_b_of_type_Int;
    if (j >= 0) {
      return j + this.jdField_a_of_type_Int;
    }
    MttInputStream localMttInputStream = this.jdField_a_of_type_ComTencentCommonHttpMttInputStream;
    if (localMttInputStream != null) {
      return localMttInputStream.getFlow() + this.jdField_a_of_type_Int;
    }
    return this.jdField_a_of_type_Int;
  }
  
  public String getHeaderField(String paramString)
  {
    boolean bool = TextUtils.isEmpty(paramString);
    Object localObject1 = null;
    if (!bool)
    {
      Object localObject2 = this.jdField_b_of_type_JavaUtilMap;
      if (localObject2 == null) {
        return null;
      }
      localObject2 = (List)((Map)localObject2).get(paramString);
      paramString = (String)localObject1;
      if (localObject2 != null)
      {
        paramString = (String)localObject1;
        if (((List)localObject2).size() > 0) {
          paramString = (String)((List)localObject2).get(0);
        }
      }
      return paramString;
    }
    return null;
  }
  
  public List<String> getHeaderFields(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      Map localMap = this.jdField_b_of_type_JavaUtilMap;
      if (localMap != null) {
        return (List)localMap.get(paramString);
      }
    }
    return null;
  }
  
  public MttInputStream getInputStream()
  {
    return this.jdField_a_of_type_ComTencentCommonHttpMttInputStream;
  }
  
  public String getLocation()
  {
    return this.jdField_b_of_type_JavaLangString;
  }
  
  public String getQEncrypt()
  {
    return this.e;
  }
  
  public String getQSZip()
  {
    return this.d;
  }
  
  public String getQToken()
  {
    return this.f;
  }
  
  public String getResponseMessage()
  {
    return this.i;
  }
  
  public long getRetryAfter()
  {
    return this.jdField_b_of_type_Long;
  }
  
  public Map<String, List<String>> getRspHeaderMaps()
  {
    return this.jdField_b_of_type_JavaUtilMap;
  }
  
  public Integer getStatusCode()
  {
    Integer localInteger = this.jdField_a_of_type_JavaLangInteger;
    if (localInteger != null) {
      return localInteger;
    }
    return UNKNOWN_STATUS;
  }
  
  public String getTokenExpireSpan()
  {
    return this.g;
  }
  
  public String getVersion()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public String getWupEnvironment()
  {
    return this.h;
  }
  
  public void setContentLength(long paramLong)
  {
    this.jdField_a_of_type_Long = paramLong;
  }
  
  public void setContentType(ContentType paramContentType)
  {
    this.jdField_a_of_type_ComTencentCommonHttpContentType = paramContentType;
  }
  
  public void setCookies(Map<String, List<String>> paramMap)
  {
    this.jdField_a_of_type_JavaUtilMap = paramMap;
  }
  
  public void setETag(String paramString)
  {
    this.c = paramString;
  }
  
  public void setErrorStream(MttInputStream paramMttInputStream)
  {
    this.jdField_b_of_type_ComTencentCommonHttpMttInputStream = paramMttInputStream;
  }
  
  public void setFlow(int paramInt)
  {
    if (paramInt >= 0) {
      this.jdField_b_of_type_Int = paramInt;
    }
  }
  
  public void setFlow(Map<String, List<String>> paramMap)
  {
    if (paramMap == null) {
      return;
    }
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Iterator localIterator = ((List)((Map.Entry)paramMap.next()).getValue()).iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        try
        {
          this.jdField_a_of_type_Int += str.getBytes("UTF-8").length;
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          localUnsupportedEncodingException.printStackTrace();
        }
      }
    }
  }
  
  public void setInputStream(MttInputStream paramMttInputStream)
  {
    this.jdField_a_of_type_ComTencentCommonHttpMttInputStream = paramMttInputStream;
  }
  
  public void setLocation(String paramString)
  {
    this.jdField_b_of_type_JavaLangString = paramString;
  }
  
  public void setQEncrypt(String paramString)
  {
    this.e = paramString;
  }
  
  public void setQSZip(String paramString)
  {
    this.d = paramString;
  }
  
  public void setQToken(String paramString)
  {
    this.f = paramString;
  }
  
  public void setResponseMessage(String paramString)
  {
    this.i = paramString;
  }
  
  public void setRetryAfter(long paramLong)
  {
    this.jdField_b_of_type_Long = paramLong;
  }
  
  public void setRspHeaderMap(Map<String, List<String>> paramMap)
  {
    this.jdField_b_of_type_JavaUtilMap = paramMap;
  }
  
  public void setStatusCode(Integer paramInteger)
  {
    this.jdField_a_of_type_JavaLangInteger = paramInteger;
  }
  
  public void setTokenExpireSpan(String paramString)
  {
    this.g = paramString;
  }
  
  public void setVersion(String paramString)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
  }
  
  public void setWupEnvironment(String paramString)
  {
    this.h = paramString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\MttResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */