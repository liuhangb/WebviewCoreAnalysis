package com.tencent.smtt.net;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class h
{
  private static Handler jdField_a_of_type_AndroidOsHandler;
  private static Looper jdField_a_of_type_AndroidOsLooper;
  public HashMap<String, String> a;
  public HashMap<String, String> b = null;
  
  public h()
  {
    this.jdField_a_of_type_JavaUtilHashMap = null;
    a();
  }
  
  public static h a()
  {
    return a.a();
  }
  
  private void a()
  {
    this.jdField_a_of_type_JavaUtilHashMap = new HashMap();
    this.b = new HashMap();
    a(SmttServiceProxy.getInstance().getECommerceFetchjsList());
    jdField_a_of_type_AndroidOsLooper = BrowserExecutorSupplier.getLooperForRunShortTime();
    jdField_a_of_type_AndroidOsHandler = new Handler(jdField_a_of_type_AndroidOsLooper)
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAnonymousMessage.what != 2) {
          return;
        }
        paramAnonymousMessage = (String[])paramAnonymousMessage.obj;
        if (paramAnonymousMessage[1] != null) {
          h.this.b.put(paramAnonymousMessage[0], h.a(h.this, paramAnonymousMessage[1]));
        }
      }
    };
  }
  
  private void a(List<String> paramList)
  {
    if (paramList != null)
    {
      int i = 0;
      while (i < paramList.size())
      {
        String[] arrayOfString = ((String)paramList.get(i)).split("\\|");
        if (arrayOfString.length == 2) {
          try
          {
            this.jdField_a_of_type_JavaUtilHashMap.put(arrayOfString[0].trim(), arrayOfString[1].trim());
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
        }
        i += 1;
      }
    }
  }
  
  /* Error */
  private String b(String paramString)
  {
    // Byte code:
    //   0: ldc 97
    //   2: astore 7
    //   4: aload_1
    //   5: ifnull +366 -> 371
    //   8: aload_1
    //   9: invokevirtual 101	java/lang/String:isEmpty	()Z
    //   12: ifne +359 -> 371
    //   15: iconst_0
    //   16: istore_3
    //   17: iconst_0
    //   18: istore 4
    //   20: iconst_0
    //   21: istore 5
    //   23: iconst_0
    //   24: istore_2
    //   25: new 103	java/net/URL
    //   28: dup
    //   29: aload_1
    //   30: invokespecial 106	java/net/URL:<init>	(Ljava/lang/String;)V
    //   33: invokevirtual 110	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   36: checkcast 112	java/net/HttpURLConnection
    //   39: astore_1
    //   40: iload_2
    //   41: istore_3
    //   42: iload 5
    //   44: istore 4
    //   46: aload_1
    //   47: sipush 4000
    //   50: invokevirtual 116	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   53: iload_2
    //   54: istore_3
    //   55: iload 5
    //   57: istore 4
    //   59: aload_1
    //   60: sipush 4000
    //   63: invokevirtual 119	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   66: iload_2
    //   67: istore_3
    //   68: iload 5
    //   70: istore 4
    //   72: aload_1
    //   73: ldc 121
    //   75: invokestatic 42	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   78: invokevirtual 124	com/tencent/smtt/webkit/service/SmttServiceProxy:getQUA2	()Ljava/lang/String;
    //   81: invokevirtual 128	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   84: iload_2
    //   85: istore_3
    //   86: iload 5
    //   88: istore 4
    //   90: aload_1
    //   91: ldc -126
    //   93: invokestatic 42	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   96: invokevirtual 133	com/tencent/smtt/webkit/service/SmttServiceProxy:getGUID	()Ljava/lang/String;
    //   99: invokevirtual 128	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   102: iload_2
    //   103: istore_3
    //   104: iload 5
    //   106: istore 4
    //   108: aload_1
    //   109: ldc -121
    //   111: invokestatic 42	com/tencent/smtt/webkit/service/SmttServiceProxy:getInstance	()Lcom/tencent/smtt/webkit/service/SmttServiceProxy;
    //   114: invokevirtual 138	com/tencent/smtt/webkit/service/SmttServiceProxy:getQAuth	()Ljava/lang/String;
    //   117: invokevirtual 128	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   120: iload_2
    //   121: istore_3
    //   122: iload 5
    //   124: istore 4
    //   126: aload_1
    //   127: invokevirtual 141	java/net/HttpURLConnection:connect	()V
    //   130: iload_2
    //   131: istore_3
    //   132: iload 5
    //   134: istore 4
    //   136: aload_1
    //   137: invokevirtual 145	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   140: astore 6
    //   142: iload_2
    //   143: istore_3
    //   144: iload 5
    //   146: istore 4
    //   148: aload_1
    //   149: invokevirtual 148	java/net/HttpURLConnection:getResponseCode	()I
    //   152: istore_2
    //   153: iload_2
    //   154: istore_3
    //   155: iload_2
    //   156: istore 4
    //   158: new 150	java/io/BufferedReader
    //   161: dup
    //   162: new 152	java/io/InputStreamReader
    //   165: dup
    //   166: aload 6
    //   168: invokespecial 155	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   171: invokespecial 158	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   174: astore 6
    //   176: iload_2
    //   177: istore_3
    //   178: iload_2
    //   179: istore 4
    //   181: new 160	java/lang/StringBuffer
    //   184: dup
    //   185: invokespecial 161	java/lang/StringBuffer:<init>	()V
    //   188: astore 8
    //   190: iload_2
    //   191: istore_3
    //   192: iload_2
    //   193: istore 4
    //   195: aload 6
    //   197: invokevirtual 164	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   200: astore 9
    //   202: aload 9
    //   204: ifnull +32 -> 236
    //   207: iload_2
    //   208: istore_3
    //   209: iload_2
    //   210: istore 4
    //   212: aload 8
    //   214: aload 9
    //   216: invokevirtual 168	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   219: pop
    //   220: iload_2
    //   221: istore_3
    //   222: iload_2
    //   223: istore 4
    //   225: aload 8
    //   227: ldc -86
    //   229: invokevirtual 168	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   232: pop
    //   233: goto -43 -> 190
    //   236: iload_2
    //   237: istore_3
    //   238: iload_2
    //   239: istore 4
    //   241: aload 8
    //   243: invokevirtual 173	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   246: astore 8
    //   248: aload 8
    //   250: astore 6
    //   252: iload_2
    //   253: istore_3
    //   254: aload_1
    //   255: ifnull +13 -> 268
    //   258: aload 8
    //   260: astore 6
    //   262: aload_1
    //   263: invokevirtual 176	java/net/HttpURLConnection:disconnect	()V
    //   266: iload_2
    //   267: istore_3
    //   268: getstatic 62	com/tencent/smtt/net/h:jdField_a_of_type_AndroidOsHandler	Landroid/os/Handler;
    //   271: invokevirtual 182	android/os/Handler:obtainMessage	()Landroid/os/Message;
    //   274: astore_1
    //   275: aload_1
    //   276: bipush 6
    //   278: putfield 188	android/os/Message:what	I
    //   281: aload_1
    //   282: iload_3
    //   283: putfield 191	android/os/Message:arg1	I
    //   286: getstatic 62	com/tencent/smtt/net/h:jdField_a_of_type_AndroidOsHandler	Landroid/os/Handler;
    //   289: aload_1
    //   290: invokevirtual 195	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
    //   293: pop
    //   294: aload 6
    //   296: areturn
    //   297: astore 6
    //   299: goto +13 -> 312
    //   302: iload 4
    //   304: istore_2
    //   305: goto +49 -> 354
    //   308: astore 6
    //   310: aconst_null
    //   311: astore_1
    //   312: aload_1
    //   313: ifnull +7 -> 320
    //   316: aload_1
    //   317: invokevirtual 176	java/net/HttpURLConnection:disconnect	()V
    //   320: getstatic 62	com/tencent/smtt/net/h:jdField_a_of_type_AndroidOsHandler	Landroid/os/Handler;
    //   323: invokevirtual 182	android/os/Handler:obtainMessage	()Landroid/os/Message;
    //   326: astore_1
    //   327: aload_1
    //   328: bipush 6
    //   330: putfield 188	android/os/Message:what	I
    //   333: aload_1
    //   334: iload_3
    //   335: putfield 191	android/os/Message:arg1	I
    //   338: getstatic 62	com/tencent/smtt/net/h:jdField_a_of_type_AndroidOsHandler	Landroid/os/Handler;
    //   341: aload_1
    //   342: invokevirtual 195	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
    //   345: pop
    //   346: aload 6
    //   348: athrow
    //   349: aconst_null
    //   350: astore_1
    //   351: iload 4
    //   353: istore_2
    //   354: aload 7
    //   356: astore 6
    //   358: iload_2
    //   359: istore_3
    //   360: aload_1
    //   361: ifnull -93 -> 268
    //   364: aload 7
    //   366: astore 6
    //   368: goto -106 -> 262
    //   371: ldc 97
    //   373: areturn
    //   374: astore_1
    //   375: goto -26 -> 349
    //   378: astore 6
    //   380: goto -78 -> 302
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	383	0	this	h
    //   0	383	1	paramString	String
    //   24	335	2	i	int
    //   16	344	3	j	int
    //   18	334	4	k	int
    //   21	124	5	m	int
    //   140	155	6	localObject1	Object
    //   297	1	6	localObject2	Object
    //   308	39	6	localObject3	Object
    //   356	11	6	str1	String
    //   378	1	6	localException	Exception
    //   2	363	7	str2	String
    //   188	71	8	localObject4	Object
    //   200	15	9	str3	String
    // Exception table:
    //   from	to	target	type
    //   46	53	297	finally
    //   59	66	297	finally
    //   72	84	297	finally
    //   90	102	297	finally
    //   108	120	297	finally
    //   126	130	297	finally
    //   136	142	297	finally
    //   148	153	297	finally
    //   158	176	297	finally
    //   181	190	297	finally
    //   195	202	297	finally
    //   212	220	297	finally
    //   225	233	297	finally
    //   241	248	297	finally
    //   25	40	308	finally
    //   25	40	374	java/lang/Exception
    //   46	53	378	java/lang/Exception
    //   59	66	378	java/lang/Exception
    //   72	84	378	java/lang/Exception
    //   90	102	378	java/lang/Exception
    //   108	120	378	java/lang/Exception
    //   126	130	378	java/lang/Exception
    //   136	142	378	java/lang/Exception
    //   148	153	378	java/lang/Exception
    //   158	176	378	java/lang/Exception
    //   181	190	378	java/lang/Exception
    //   195	202	378	java/lang/Exception
    //   212	220	378	java/lang/Exception
    //   225	233	378	java/lang/Exception
    //   241	248	378	java/lang/Exception
  }
  
  public String a(String paramString)
  {
    String str1 = "";
    if (paramString == null) {
      return "";
    }
    Iterator localIterator = this.jdField_a_of_type_JavaUtilHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str2 = (String)localIterator.next();
      if ((str2 != null) && (paramString.startsWith(str2))) {
        str1 = (String)this.b.get(str2);
      }
    }
    return str1;
  }
  
  public void a(String paramString)
  {
    if (paramString == null) {
      return;
    }
    Iterator localIterator = this.jdField_a_of_type_JavaUtilHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if ((str != null) && (paramString.startsWith(str)) && (this.b.get(str) == null))
      {
        Message localMessage = jdField_a_of_type_AndroidOsHandler.obtainMessage();
        localMessage.what = 2;
        localMessage.obj = new String[] { str, (String)this.jdField_a_of_type_JavaUtilHashMap.get(str) };
        jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
      }
    }
  }
  
  public void b(String paramString)
  {
    if ((paramString != null) && (!paramString.isEmpty())) {
      try
      {
        JSONArray localJSONArray = new JSONArray(paramString);
        int i = 0;
        while (i < localJSONArray.length())
        {
          JSONObject localJSONObject = localJSONArray.getJSONObject(i);
          Iterator localIterator = localJSONObject.keys();
          HashMap localHashMap = new HashMap();
          while (localIterator.hasNext())
          {
            Object localObject1 = "";
            Object localObject2 = (String)localIterator.next();
            paramString = (String)localObject1;
            if (localObject2 != null)
            {
              paramString = (String)localObject1;
              if (!((String)localObject2).isEmpty()) {
                paramString = localJSONObject.getString((String)localObject2);
              }
            }
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("");
            ((StringBuilder)localObject1).append((String)localObject2);
            localObject1 = ((StringBuilder)localObject1).toString();
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("");
            ((StringBuilder)localObject2).append(paramString);
            localHashMap.put(localObject1, ((StringBuilder)localObject2).toString());
          }
          SmttServiceProxy.getInstance().reportJsFetchResults(localHashMap);
          i += 1;
        }
        return;
      }
      catch (JSONException paramString)
      {
        paramString.printStackTrace();
      }
    }
  }
  
  private static class a
  {
    private static final h a = new h();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */