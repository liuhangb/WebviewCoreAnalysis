package com.tencent.smtt.net;

import android.text.TextUtils;
import android.util.SparseArray;
import android.webview.chromium.WebViewContentsClientAdapter;
import android.webview.chromium.tencent.TencentWebViewContentsClientAdapter;
import com.tencent.common.http.Apn;
import com.tencent.smtt.util.X5Log;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.smtt.webkit.u.a;
import com.tencent.smtt.webkit.u.d;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class e
{
  private static int jdField_a_of_type_Int = 5;
  private static e jdField_a_of_type_ComTencentSmttNetE;
  private static String jdField_a_of_type_JavaLangString = "PageExceptionRecorder";
  private static ConcurrentLinkedQueue<b> jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue;
  private SparseArray<b> jdField_a_of_type_AndroidUtilSparseArray = null;
  
  public static e a()
  {
    if (jdField_a_of_type_ComTencentSmttNetE == null) {
      jdField_a_of_type_ComTencentSmttNetE = new e();
    }
    return jdField_a_of_type_ComTencentSmttNetE;
  }
  
  public void a()
  {
    for (;;)
    {
      Object localObject1 = jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue;
      if ((localObject1 == null) || (((ConcurrentLinkedQueue)localObject1).isEmpty())) {
        break;
      }
      localObject1 = (b)jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.poll();
      if (localObject1 != null)
      {
        HashMap localHashMap = new HashMap();
        Object localObject2 = ((b)localObject1).jdField_a_of_type_JavaUtilArrayList;
        int m = 0;
        int i;
        if (localObject2 != null)
        {
          i = 0;
          int k;
          for (int j = 1; i < ((b)localObject1).jdField_a_of_type_JavaUtilArrayList.size(); j = k)
          {
            localObject2 = (c)((b)localObject1).jdField_a_of_type_JavaUtilArrayList.get(i);
            k = j;
            if (localObject2 != null)
            {
              Object localObject3;
              if ((((c)localObject2).e == 0) && (j <= 2))
              {
                localObject3 = new StringBuilder();
                ((StringBuilder)localObject3).append("sub");
                ((StringBuilder)localObject3).append(j);
                ((StringBuilder)localObject3).append("_err_url");
                localHashMap.put(((StringBuilder)localObject3).toString(), ((c)localObject2).jdField_a_of_type_JavaLangString);
                localObject3 = new StringBuilder();
                ((StringBuilder)localObject3).append("sub");
                ((StringBuilder)localObject3).append(j);
                ((StringBuilder)localObject3).append("_err_id");
                localObject3 = ((StringBuilder)localObject3).toString();
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append(((c)localObject2).jdField_a_of_type_Int);
                localStringBuilder.append("");
                localHashMap.put(localObject3, localStringBuilder.toString());
                localObject3 = new StringBuilder();
                ((StringBuilder)localObject3).append("sub");
                ((StringBuilder)localObject3).append(j);
                ((StringBuilder)localObject3).append("_err_msg");
                localHashMap.put(((StringBuilder)localObject3).toString(), ((c)localObject2).a());
                k = j + 1;
              }
              else if (((c)localObject2).e == 1)
              {
                localHashMap.put("cert_err_url", ((c)localObject2).jdField_a_of_type_JavaLangString);
                localObject3 = new StringBuilder();
                ((StringBuilder)localObject3).append(((c)localObject2).jdField_a_of_type_Int);
                ((StringBuilder)localObject3).append("");
                localHashMap.put("cert_err_id", ((StringBuilder)localObject3).toString());
                localHashMap.put("cert_err_msg", ((c)localObject2).a());
                k = j;
              }
              else
              {
                k = j;
                if (((c)localObject2).e == 2)
                {
                  localHashMap.put("decode_err_url", ((c)localObject2).jdField_a_of_type_JavaLangString);
                  localObject3 = new StringBuilder();
                  ((StringBuilder)localObject3).append(((c)localObject2).jdField_a_of_type_Int);
                  ((StringBuilder)localObject3).append("");
                  localHashMap.put("decode_err_id", ((StringBuilder)localObject3).toString());
                  localHashMap.put("decode_err_msg", ((c)localObject2).a());
                  k = j;
                }
              }
            }
            i += 1;
          }
        }
        if (((b)localObject1).jdField_b_of_type_JavaUtilArrayList != null)
        {
          i = m;
          while (i < ((b)localObject1).jdField_b_of_type_JavaUtilArrayList.size())
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("badjs_");
            ((StringBuilder)localObject2).append(i);
            localHashMap.put(((StringBuilder)localObject2).toString(), ((a)((b)localObject1).jdField_b_of_type_JavaUtilArrayList.get(i)).a());
            i += 1;
          }
        }
        localHashMap.put("page_url", ((b)localObject1).jdField_a_of_type_JavaLangString);
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(((b)localObject1).jdField_a_of_type_Int);
        ((StringBuilder)localObject2).append("");
        localHashMap.put("apn", ((StringBuilder)localObject2).toString());
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(((b)localObject1).jdField_b_of_type_Int);
        ((StringBuilder)localObject2).append("");
        localHashMap.put("blank_screen_statue", ((StringBuilder)localObject2).toString());
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(((b)localObject1).jdField_a_of_type_Long);
        ((StringBuilder)localObject2).append("");
        localHashMap.put("timestamp", ((StringBuilder)localObject2).toString());
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(((b)localObject1).jdField_c_of_type_Int);
        ((StringBuilder)localObject2).append("");
        localHashMap.put("zx_type", ((StringBuilder)localObject2).toString());
        if (com.tencent.smtt.webkit.e.a().n()) {
          localHashMap.put("kingcard", Boolean.toString(SmttServiceProxy.getInstance().isKingCardUser()));
        }
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(((b)localObject1).jdField_d_of_type_Int);
        ((StringBuilder)localObject2).append("");
        localHashMap.put("main_error_id", ((StringBuilder)localObject2).toString());
        localHashMap.put("main_server_ip", ((b)localObject1).jdField_b_of_type_JavaLangString);
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(((b)localObject1).f);
        ((StringBuilder)localObject2).append("");
        localHashMap.put("main_conn_info", ((StringBuilder)localObject2).toString());
        SmttServiceProxy.getInstance().upLoadToBeacon("MTT_CORE_PAGE_INFO_V2", localHashMap);
      }
    }
  }
  
  public void a(int paramInt, String paramString1, String paramString2)
  {
    b localb = (b)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramInt);
    if ((localb != null) && (!TextUtils.isEmpty(paramString1)) && (!TextUtils.isEmpty(paramString2)) && (paramString1.equals(localb.jdField_a_of_type_JavaLangString))) {
      localb.jdField_a_of_type_JavaLangString = paramString2;
    }
  }
  
  public void a(WebViewContentsClientAdapter paramWebViewContentsClientAdapter)
  {
    paramWebViewContentsClientAdapter = (b)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramWebViewContentsClientAdapter.hashCode());
    if (paramWebViewContentsClientAdapter != null) {
      paramWebViewContentsClientAdapter.jdField_c_of_type_Boolean = true;
    }
  }
  
  public void a(WebViewContentsClientAdapter paramWebViewContentsClientAdapter, String paramString, boolean paramBoolean, int paramInt)
  {
    paramString = new b(paramString, Apn.sApnTypeS, paramBoolean, paramInt);
    this.jdField_a_of_type_AndroidUtilSparseArray.put(paramWebViewContentsClientAdapter.hashCode(), paramString);
  }
  
  public void a(TencentWebViewContentsClientAdapter paramTencentWebViewContentsClientAdapter, int paramInt1, int paramInt2)
  {
    b localb = (b)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramTencentWebViewContentsClientAdapter.hashCode());
    if (localb != null)
    {
      if (paramInt1 != 0)
      {
        localb.jdField_b_of_type_Int = paramInt1;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Options=PageException; ERROR=BlankScreen; Status=");
        localStringBuilder.append(paramInt1);
        localStringBuilder.append(" url=");
        localStringBuilder.append(localb.jdField_a_of_type_JavaLangString);
        X5Log.i("LOADER", localStringBuilder.toString());
        paramTencentWebViewContentsClientAdapter.onDetectedBlankScreen(localb.jdField_a_of_type_JavaLangString, paramInt1);
      }
      if ((localb.jdField_b_of_type_Boolean) || (localb.jdField_c_of_type_Boolean) || (localb.jdField_b_of_type_JavaUtilArrayList != null) || (localb.jdField_a_of_type_JavaUtilArrayList != null))
      {
        if (jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue == null) {
          jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue = new ConcurrentLinkedQueue();
        }
        jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.add(localb);
      }
      this.jdField_a_of_type_AndroidUtilSparseArray.remove(paramTencentWebViewContentsClientAdapter.hashCode());
    }
    a();
  }
  
  public void a(u.a parama)
  {
    if (parama == null) {
      return;
    }
    b localb = (b)this.jdField_a_of_type_AndroidUtilSparseArray.get(parama.jdField_a_of_type_Int);
    if (localb != null) {
      localb.a(new a(parama.jdField_a_of_type_JavaLangString, parama.jdField_b_of_type_JavaLangString, parama.jdField_c_of_type_JavaLangString));
    }
  }
  
  public void a(u.d paramd)
  {
    if (paramd == null) {
      return;
    }
    b localb = (b)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramd.g);
    if (localb != null)
    {
      if ((localb.jdField_a_of_type_Int == 0) && (paramd.jdField_a_of_type_Int == -10)) {
        return;
      }
      if (paramd.jdField_a_of_type_JavaLangString.equalsIgnoreCase("about:blank")) {
        return;
      }
      if ((paramd.jdField_b_of_type_JavaLangString != null) && (!paramd.jdField_b_of_type_JavaLangString.equalsIgnoreCase("127.0.0.1")))
      {
        if (paramd.jdField_b_of_type_JavaLangString.equalsIgnoreCase("localhost")) {
          return;
        }
        if (paramd.jdField_b_of_type_JavaLangString.equalsIgnoreCase("servicewechat.com")) {
          return;
        }
        if ((paramd.jdField_d_of_type_Int == 0) && (paramd.e == 0))
        {
          localb.jdField_b_of_type_Boolean = true;
          localb.jdField_d_of_type_Int = paramd.jdField_a_of_type_Int;
          if (paramd.jdField_b_of_type_Int >= 400) {
            localb.jdField_d_of_type_Int = paramd.jdField_b_of_type_Int;
          }
          localb.jdField_b_of_type_JavaLangString = paramd.jdField_d_of_type_JavaLangString;
          localb.e = paramd.jdField_c_of_type_Int;
          localb.f = paramd.f;
          return;
        }
        localb.a(new c(paramd.e, paramd.jdField_a_of_type_JavaLangString, paramd.jdField_c_of_type_JavaLangString, paramd.jdField_a_of_type_Int, paramd.jdField_b_of_type_Int, paramd.jdField_c_of_type_Int, paramd.jdField_d_of_type_Int, paramd.jdField_d_of_type_JavaLangString, paramd.jdField_a_of_type_Long, paramd.f));
        return;
      }
      return;
    }
  }
  
  public void b(WebViewContentsClientAdapter paramWebViewContentsClientAdapter)
  {
    paramWebViewContentsClientAdapter = (b)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramWebViewContentsClientAdapter.hashCode());
    if (paramWebViewContentsClientAdapter != null) {
      paramWebViewContentsClientAdapter.jdField_d_of_type_Boolean = true;
    }
  }
  
  public class a
  {
    String jdField_a_of_type_JavaLangString;
    String b;
    String c;
    
    public a(String paramString1, String paramString2, String paramString3)
    {
      this.jdField_a_of_type_JavaLangString = paramString1;
      this.b = paramString2;
      this.c = paramString3;
    }
    
    public String a()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
      localStringBuilder.append("||");
      localStringBuilder.append(this.b);
      localStringBuilder.append("||");
      localStringBuilder.append(this.c);
      return localStringBuilder.toString();
    }
  }
  
  public class b
  {
    int jdField_a_of_type_Int;
    long jdField_a_of_type_Long = 0L;
    String jdField_a_of_type_JavaLangString;
    ArrayList<e.c> jdField_a_of_type_JavaUtilArrayList = null;
    boolean jdField_a_of_type_Boolean;
    int jdField_b_of_type_Int = 0;
    String jdField_b_of_type_JavaLangString = "";
    ArrayList<e.a> jdField_b_of_type_JavaUtilArrayList = null;
    boolean jdField_b_of_type_Boolean = false;
    int jdField_c_of_type_Int = 0;
    boolean jdField_c_of_type_Boolean = false;
    int jdField_d_of_type_Int = 0;
    boolean jdField_d_of_type_Boolean = false;
    int e = 0;
    int f;
    
    b(String paramString, int paramInt1, boolean paramBoolean, int paramInt2)
    {
      this.jdField_a_of_type_JavaLangString = paramString;
      this.jdField_a_of_type_Int = paramInt1;
      this.jdField_a_of_type_Boolean = paramBoolean;
      this.jdField_a_of_type_Long = System.currentTimeMillis();
      this.jdField_c_of_type_Int = paramInt2;
    }
    
    void a(e.a parama)
    {
      if (this.jdField_b_of_type_JavaUtilArrayList == null) {
        this.jdField_b_of_type_JavaUtilArrayList = new ArrayList();
      }
      if (this.jdField_b_of_type_JavaUtilArrayList.size() < e.a()) {
        this.jdField_b_of_type_JavaUtilArrayList.add(parama);
      }
    }
    
    void a(e.c paramc)
    {
      if (this.jdField_a_of_type_JavaUtilArrayList == null) {
        this.jdField_a_of_type_JavaUtilArrayList = new ArrayList();
      }
      if (this.jdField_a_of_type_JavaUtilArrayList.size() < e.a()) {
        this.jdField_a_of_type_JavaUtilArrayList.add(paramc);
      }
    }
  }
  
  public class c
  {
    int jdField_a_of_type_Int;
    long jdField_a_of_type_Long;
    String jdField_a_of_type_JavaLangString;
    int jdField_b_of_type_Int;
    String jdField_b_of_type_JavaLangString;
    int jdField_c_of_type_Int;
    String jdField_c_of_type_JavaLangString;
    int d;
    int e;
    int f;
    
    c(int paramInt1, String paramString1, String paramString2, int paramInt2, int paramInt3, int paramInt4, int paramInt5, String paramString3, long paramLong, int paramInt6)
    {
      this.e = paramInt1;
      this.d = paramInt5;
      this.jdField_a_of_type_Int = paramInt2;
      this.jdField_b_of_type_JavaLangString = paramString2;
      this.jdField_b_of_type_Int = paramInt3;
      this.jdField_c_of_type_Int = paramInt4;
      this.d = paramInt5;
      this.jdField_c_of_type_JavaLangString = paramString3;
      this.jdField_a_of_type_Long = paramLong;
      this.f = paramInt6;
      if (paramInt3 >= 400) {
        this.jdField_a_of_type_Int = this.jdField_b_of_type_Int;
      }
      if ((paramString1 != null) && (paramString1.length() > 300))
      {
        this.jdField_a_of_type_JavaLangString = paramString1.substring(0, 250);
        return;
      }
      this.jdField_a_of_type_JavaLangString = paramString1;
    }
    
    public String a()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.jdField_c_of_type_Int);
      localStringBuilder.append("||");
      localStringBuilder.append(this.d);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_c_of_type_JavaLangString);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_a_of_type_Long);
      localStringBuilder.append("||");
      localStringBuilder.append(this.jdField_b_of_type_JavaLangString);
      localStringBuilder.append("||");
      localStringBuilder.append(this.f);
      return localStringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */