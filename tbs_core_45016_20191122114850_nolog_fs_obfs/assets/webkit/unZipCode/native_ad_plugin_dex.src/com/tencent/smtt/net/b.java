package com.tencent.smtt.net;

import android.text.TextUtils;
import android.util.SparseArray;
import com.tencent.common.http.Apn;
import com.tencent.smtt.util.MttLog;
import com.tencent.smtt.webkit.WebViewChromiumExtension;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class b
{
  private static int jdField_a_of_type_Int = 20;
  private static b jdField_a_of_type_ComTencentSmttNetB;
  private static String jdField_a_of_type_JavaLangString = "DirectAdblockReporter";
  private static ConcurrentLinkedQueue<a> jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue;
  public static boolean a = true;
  private static boolean b = false;
  private SparseArray<a> jdField_a_of_type_AndroidUtilSparseArray = null;
  
  public static b a()
  {
    if (jdField_a_of_type_ComTencentSmttNetB == null) {
      jdField_a_of_type_ComTencentSmttNetB = new b();
    }
    return jdField_a_of_type_ComTencentSmttNetB;
  }
  
  public void a()
  {
    for (;;)
    {
      Object localObject1 = jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue;
      if ((localObject1 == null) || (((ConcurrentLinkedQueue)localObject1).isEmpty())) {
        break;
      }
      localObject1 = (a)jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.poll();
      if ((localObject1 != null) && ((((a)localObject1).jdField_a_of_type_JavaUtilArrayList != null) || (((a)localObject1).jdField_b_of_type_Int != 0)))
      {
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("MOVE sendDirectAdblockInfoToServer We begin to report Direct AD block info for Page Url ");
        ((StringBuilder)localObject2).append(((a)localObject1).jdField_a_of_type_JavaLangString);
        MttLog.d("ADBlock", ((StringBuilder)localObject2).toString());
        if (((a)localObject1).jdField_a_of_type_JavaUtilArrayList != null)
        {
          localObject2 = (String[])((a)localObject1).jdField_a_of_type_JavaUtilArrayList.toArray(new String[((a)localObject1).jdField_a_of_type_JavaUtilArrayList.size()]);
          int i;
          if ((localObject2 != null) && (((a)localObject1).jdField_b_of_type_Int <= 0)) {
            i = 0;
          } else {
            i = ((a)localObject1).jdField_b_of_type_Int;
          }
          SmttServiceProxy.getInstance().onReportTbsDAE(((a)localObject1).jdField_a_of_type_JavaLangString, ((a)localObject1).jdField_a_of_type_Long, ((a)localObject1).jdField_b_of_type_JavaLangString, ((a)localObject1).jdField_a_of_type_Int, (String[])localObject2, i);
        }
        else
        {
          SmttServiceProxy.getInstance().onReportTbsDAE(((a)localObject1).jdField_a_of_type_JavaLangString, ((a)localObject1).jdField_a_of_type_Long, ((a)localObject1).jdField_b_of_type_JavaLangString, ((a)localObject1).jdField_a_of_type_Int, null, ((a)localObject1).jdField_b_of_type_Int);
        }
      }
    }
  }
  
  public void a(WebViewChromiumExtension paramWebViewChromiumExtension)
  {
    if (!jdField_a_of_type_Boolean) {
      return;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("MOVE handleDirectAdblockInfo WebViewChromiumExtension = ");
    ((StringBuilder)localObject).append(paramWebViewChromiumExtension);
    MttLog.d("ADBlock", ((StringBuilder)localObject).toString());
    localObject = (a)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramWebViewChromiumExtension.hashCode());
    if (localObject != null)
    {
      if (jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue == null) {
        jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue = new ConcurrentLinkedQueue();
      }
      jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.add(localObject);
      this.jdField_a_of_type_AndroidUtilSparseArray.remove(paramWebViewChromiumExtension.hashCode());
    }
  }
  
  public void a(WebViewChromiumExtension paramWebViewChromiumExtension, String paramString)
  {
    if (!jdField_a_of_type_Boolean) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("MOVE initNewDirectAdblockInfo WebViewChromiumExtension = ");
    localStringBuilder.append(paramWebViewChromiumExtension.hashCode());
    localStringBuilder.append(" pageUrl = ");
    localStringBuilder.append(paramString);
    MttLog.d("ADBlock", localStringBuilder.toString());
    paramString = new a(paramString, Apn.getApnTypeS());
    this.jdField_a_of_type_AndroidUtilSparseArray.put(paramWebViewChromiumExtension.hashCode(), paramString);
  }
  
  public void a(WebViewChromiumExtension paramWebViewChromiumExtension, String paramString, int paramInt)
  {
    if (jdField_a_of_type_Boolean)
    {
      if (TextUtils.isEmpty(paramString)) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("MOVE recordDirectAdHiddenNum WebViewChromiumExtension = ");
      localStringBuilder.append(paramWebViewChromiumExtension);
      localStringBuilder.append(" pageUrl = ");
      localStringBuilder.append(paramString);
      localStringBuilder.append(" hiddenNum = ");
      localStringBuilder.append(paramInt);
      MttLog.d("ADBlock", localStringBuilder.toString());
      paramWebViewChromiumExtension = (a)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramWebViewChromiumExtension.hashCode());
      if (paramWebViewChromiumExtension != null) {
        paramWebViewChromiumExtension.jdField_b_of_type_Int = paramInt;
      }
      return;
    }
  }
  
  public void a(WebViewChromiumExtension paramWebViewChromiumExtension, String paramString1, String paramString2)
  {
    if (jdField_a_of_type_Boolean)
    {
      if (TextUtils.isEmpty(paramString1)) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("MOVE recordDirectAdblockInfo WebViewChromiumExtension = ");
      localStringBuilder.append(paramWebViewChromiumExtension);
      localStringBuilder.append(" pageUrl = ");
      localStringBuilder.append(paramString1);
      localStringBuilder.append(" adUrl = ");
      localStringBuilder.append(paramString2);
      MttLog.d("ADBlock", localStringBuilder.toString());
      paramWebViewChromiumExtension = (a)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramWebViewChromiumExtension.hashCode());
      if (paramWebViewChromiumExtension != null) {
        paramWebViewChromiumExtension.a(paramString2);
      }
      return;
    }
  }
  
  public class a
  {
    int jdField_a_of_type_Int;
    long jdField_a_of_type_Long = 0L;
    String jdField_a_of_type_JavaLangString;
    ArrayList<String> jdField_a_of_type_JavaUtilArrayList = null;
    int jdField_b_of_type_Int = 0;
    String jdField_b_of_type_JavaLangString = "defaultCellId";
    
    a(String paramString, int paramInt)
    {
      this.jdField_a_of_type_JavaLangString = paramString;
      this.jdField_a_of_type_Int = paramInt;
      this.jdField_a_of_type_Long = System.currentTimeMillis();
    }
    
    void a(String paramString)
    {
      if (this.jdField_a_of_type_JavaUtilArrayList == null) {
        this.jdField_a_of_type_JavaUtilArrayList = new ArrayList();
      }
      if ((this.jdField_a_of_type_JavaUtilArrayList.size() < 32) && (paramString.length() <= 1024)) {
        this.jdField_a_of_type_JavaUtilArrayList.add(paramString);
      }
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("pageUrl=");
      localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
      localStringBuilder.append("|");
      localStringBuilder.append(this.jdField_a_of_type_Int);
      localStringBuilder.append("|");
      localStringBuilder.append(this.jdField_a_of_type_Long);
      localStringBuilder.append("|");
      localStringBuilder.append(this.jdField_b_of_type_Int);
      localStringBuilder.append("|");
      localStringBuilder.append(this.jdField_a_of_type_JavaUtilArrayList.toString());
      localStringBuilder.append("|");
      localStringBuilder.append(this.jdField_b_of_type_JavaLangString);
      return localStringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */