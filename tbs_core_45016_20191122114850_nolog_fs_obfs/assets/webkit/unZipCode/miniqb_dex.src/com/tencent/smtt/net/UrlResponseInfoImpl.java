package com.tencent.smtt.net;

import com.tencent.smtt.export.external.interfaces.UrlResponseInfo;
import com.tencent.smtt.export.external.interfaces.UrlResponseInfo.HeaderBlock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

public final class UrlResponseInfoImpl
  extends UrlResponseInfo
{
  private final int jdField_a_of_type_Int;
  private final a jdField_a_of_type_ComTencentSmttNetUrlResponseInfoImpl$a;
  private final String jdField_a_of_type_JavaLangString;
  private final List<String> jdField_a_of_type_JavaUtilList;
  private final AtomicLong jdField_a_of_type_JavaUtilConcurrentAtomicAtomicLong;
  private final boolean jdField_a_of_type_Boolean;
  private final String b;
  private final String c;
  
  public UrlResponseInfoImpl(List<String> paramList, int paramInt, String paramString1, List<Map.Entry<String, String>> paramList1, boolean paramBoolean, String paramString2, String paramString3)
  {
    this.jdField_a_of_type_JavaUtilList = Collections.unmodifiableList(paramList);
    this.jdField_a_of_type_Int = paramInt;
    this.jdField_a_of_type_JavaLangString = paramString1;
    this.jdField_a_of_type_ComTencentSmttNetUrlResponseInfoImpl$a = new a(Collections.unmodifiableList(paramList1));
    this.jdField_a_of_type_Boolean = paramBoolean;
    this.b = paramString2;
    this.c = paramString3;
    this.jdField_a_of_type_JavaUtilConcurrentAtomicAtomicLong = new AtomicLong();
  }
  
  public Map<String, List<String>> getAllHeaders()
  {
    return this.jdField_a_of_type_ComTencentSmttNetUrlResponseInfoImpl$a.a();
  }
  
  public List<Map.Entry<String, String>> getAllHeadersAsList()
  {
    return this.jdField_a_of_type_ComTencentSmttNetUrlResponseInfoImpl$a.a();
  }
  
  public int getHttpStatusCode()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public String getHttpStatusText()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public String getNegotiatedProtocol()
  {
    return this.b;
  }
  
  public String getProxyServer()
  {
    return this.c;
  }
  
  public long getReceivedByteCount()
  {
    return this.jdField_a_of_type_JavaUtilConcurrentAtomicAtomicLong.get();
  }
  
  public String getUrl()
  {
    List localList = this.jdField_a_of_type_JavaUtilList;
    return (String)localList.get(localList.size() - 1);
  }
  
  public List<String> getUrlChain()
  {
    return this.jdField_a_of_type_JavaUtilList;
  }
  
  public void setReceivedByteCount(long paramLong)
  {
    this.jdField_a_of_type_JavaUtilConcurrentAtomicAtomicLong.set(paramLong);
  }
  
  public String toString()
  {
    return String.format(Locale.ROOT, "UrlResponseInfo@[%s][%s]: urlChain = %s, httpStatus = %d %s, headers = %s, wasCached = %b, negotiatedProtocol = %s, proxyServer= %s, receivedByteCount = %d", new Object[] { Integer.toHexString(System.identityHashCode(this)), getUrl(), getUrlChain().toString(), Integer.valueOf(getHttpStatusCode()), getHttpStatusText(), getAllHeadersAsList().toString(), Boolean.valueOf(wasCached()), getNegotiatedProtocol(), getProxyServer(), Long.valueOf(getReceivedByteCount()) });
  }
  
  public boolean wasCached()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public static final class a
    extends UrlResponseInfo.HeaderBlock
  {
    private final List<Map.Entry<String, String>> jdField_a_of_type_JavaUtilList;
    private Map<String, List<String>> jdField_a_of_type_JavaUtilMap;
    
    a(List<Map.Entry<String, String>> paramList)
    {
      this.jdField_a_of_type_JavaUtilList = paramList;
    }
    
    public List<Map.Entry<String, String>> a()
    {
      return this.jdField_a_of_type_JavaUtilList;
    }
    
    public Map<String, List<String>> a()
    {
      Object localObject = this.jdField_a_of_type_JavaUtilMap;
      if (localObject != null) {
        return (Map<String, List<String>>)localObject;
      }
      localObject = new TreeMap(String.CASE_INSENSITIVE_ORDER);
      Iterator localIterator = this.jdField_a_of_type_JavaUtilList.iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        ArrayList localArrayList = new ArrayList();
        if (((Map)localObject).containsKey(localEntry.getKey())) {
          localArrayList.addAll((Collection)((Map)localObject).get(localEntry.getKey()));
        }
        localArrayList.add(localEntry.getValue());
        ((Map)localObject).put(localEntry.getKey(), Collections.unmodifiableList(localArrayList));
      }
      this.jdField_a_of_type_JavaUtilMap = Collections.unmodifiableMap((Map)localObject);
      return this.jdField_a_of_type_JavaUtilMap;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\UrlResponseInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */