package com.tencent.smtt.net;

import com.tencent.common.http.MttRequestBase;
import com.tencent.common.http.MttResponse;
import com.tencent.mtt.base.task.NetworkTask;
import com.tencent.mtt.base.task.NetworkTask.NetworkTaskCallback;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpTask
  implements NetworkTask.NetworkTaskCallback
{
  private int jdField_a_of_type_Int;
  private NetworkTask jdField_a_of_type_ComTencentMttBaseTaskNetworkTask;
  private final Listener jdField_a_of_type_ComTencentSmttNetHttpTask$Listener;
  private final a jdField_a_of_type_ComTencentSmttNetHttpTask$a;
  
  private void a(a parama)
  {
    this.jdField_a_of_type_ComTencentMttBaseTaskNetworkTask = new NetworkTask(parama.jdField_a_of_type_JavaLangString, parama.jdField_a_of_type_Byte, this);
    a(parama.jdField_a_of_type_JavaUtilMap, this.jdField_a_of_type_ComTencentMttBaseTaskNetworkTask);
    if (parama.jdField_a_of_type_ArrayOfByte != null) {
      this.jdField_a_of_type_ComTencentMttBaseTaskNetworkTask.setPostData(parama.jdField_a_of_type_ArrayOfByte);
    }
    this.jdField_a_of_type_ComTencentMttBaseTaskNetworkTask.getMttRequest().setCookieManager(null);
    this.jdField_a_of_type_ComTencentMttBaseTaskNetworkTask.getMttRequest().setTrustCertificatesType(1);
    this.jdField_a_of_type_ComTencentMttBaseTaskNetworkTask.getMttRequest().setUseCaches(false);
    this.jdField_a_of_type_ComTencentMttBaseTaskNetworkTask.getMttRequest().removeHeader("Accept");
    this.jdField_a_of_type_ComTencentMttBaseTaskNetworkTask.getMttRequest().removeHeader("Accept-Encoding");
    this.jdField_a_of_type_ComTencentMttBaseTaskNetworkTask.setConnectTimeout(60000);
    this.jdField_a_of_type_ComTencentMttBaseTaskNetworkTask.setReadTimeout(60000);
    this.jdField_a_of_type_ComTencentMttBaseTaskNetworkTask.start();
  }
  
  private static void a(Map<String, String> paramMap, NetworkTask paramNetworkTask)
  {
    if (paramMap == null) {
      return;
    }
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      paramNetworkTask.setRequestProperty((String)localEntry.getKey(), (String)localEntry.getValue());
    }
  }
  
  private static void a(Map<String, List<String>> paramMap, Map<String, Object> paramMap1)
  {
    if (paramMap != null)
    {
      if (paramMap1 == null) {
        return;
      }
      paramMap = paramMap.entrySet().iterator();
      while (paramMap.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramMap.next();
        List localList = (List)localEntry.getValue();
        if ((localEntry.getKey() != null) && (localList.size() > 0)) {
          if (localList.size() == 1) {
            paramMap1.put(localEntry.getKey(), localList.get(0));
          } else {
            paramMap1.put(localEntry.getKey(), localList);
          }
        }
      }
      return;
    }
  }
  
  private byte[] a(InputStream paramInputStream)
  {
    byte[] arrayOfByte = new byte['က'];
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    try
    {
      for (;;)
      {
        int i = paramInputStream.read(arrayOfByte);
        if (i == -1) {
          break;
        }
        localByteArrayOutputStream.write(arrayOfByte, 0, i);
      }
    }
    catch (IOException paramInputStream)
    {
      for (;;) {}
    }
    return localByteArrayOutputStream.toByteArray();
  }
  
  public void onTaskFailed(MttRequestBase paramMttRequestBase, int paramInt)
  {
    paramMttRequestBase = this.jdField_a_of_type_ComTencentSmttNetHttpTask$Listener;
    if (paramMttRequestBase != null) {
      paramMttRequestBase.onTaskFailed(paramInt);
    }
  }
  
  public void onTaskSuccess(MttRequestBase paramMttRequestBase, MttResponse paramMttResponse)
  {
    paramMttRequestBase = new b();
    paramMttRequestBase.jdField_a_of_type_Int = paramMttResponse.getStatusCode().intValue();
    if ((paramMttRequestBase.jdField_a_of_type_Int != 301) && (paramMttRequestBase.jdField_a_of_type_Int != 302))
    {
      if ((paramMttRequestBase.jdField_a_of_type_Int >= 400) && (paramMttRequestBase.jdField_a_of_type_Int < 600)) {
        paramMttRequestBase.jdField_a_of_type_ArrayOfByte = a(paramMttResponse.getErrorStream());
      } else {
        paramMttRequestBase.jdField_a_of_type_ArrayOfByte = a(paramMttResponse.getInputStream());
      }
    }
    else
    {
      this.jdField_a_of_type_Int += 1;
      if (this.jdField_a_of_type_Int <= 20)
      {
        this.jdField_a_of_type_ComTencentSmttNetHttpTask$a.jdField_a_of_type_JavaLangString = paramMttResponse.getLocation();
        a(this.jdField_a_of_type_ComTencentSmttNetHttpTask$a);
        return;
      }
      paramMttRequestBase.jdField_a_of_type_JavaLangString = "Redirected too many times.";
    }
    paramMttRequestBase.jdField_a_of_type_JavaUtilMap = new HashMap();
    a(paramMttResponse.getRspHeaderMaps(), paramMttRequestBase.jdField_a_of_type_JavaUtilMap);
    paramMttResponse = this.jdField_a_of_type_ComTencentSmttNetHttpTask$Listener;
    if (paramMttResponse != null) {
      paramMttResponse.onTaskSuccess(paramMttRequestBase);
    }
  }
  
  public static abstract interface Listener
  {
    public abstract void onTaskFailed(int paramInt);
    
    public abstract void onTaskSuccess(HttpTask.b paramb);
  }
  
  public static class a
  {
    public byte a;
    public String a;
    public Map<String, String> a;
    public byte[] a;
  }
  
  public static class b
  {
    public int a;
    public String a;
    public Map<String, Object> a;
    public byte[] a;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\HttpTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */