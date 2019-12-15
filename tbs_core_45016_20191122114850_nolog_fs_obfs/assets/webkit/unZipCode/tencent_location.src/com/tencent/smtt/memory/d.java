package com.tencent.smtt.memory;

import java.util.regex.Pattern;

public class d
{
  private static Pattern jdField_a_of_type_JavaUtilRegexPattern;
  private static boolean jdField_a_of_type_Boolean = false;
  private static Pattern b;
  private static Pattern c;
  
  /* Error */
  private static void b(a parama)
  {
    // Byte code:
    //   0: invokestatic 26	android/os/Process:myPid	()I
    //   3: istore 4
    //   5: getstatic 28	com/tencent/smtt/memory/d:jdField_a_of_type_Boolean	Z
    //   8: ifne +37 -> 45
    //   11: ldc 30
    //   13: bipush 8
    //   15: invokestatic 36	java/util/regex/Pattern:compile	(Ljava/lang/String;I)Ljava/util/regex/Pattern;
    //   18: putstatic 38	com/tencent/smtt/memory/d:jdField_a_of_type_JavaUtilRegexPattern	Ljava/util/regex/Pattern;
    //   21: ldc 40
    //   23: bipush 8
    //   25: invokestatic 36	java/util/regex/Pattern:compile	(Ljava/lang/String;I)Ljava/util/regex/Pattern;
    //   28: putstatic 42	com/tencent/smtt/memory/d:b	Ljava/util/regex/Pattern;
    //   31: ldc 44
    //   33: bipush 8
    //   35: invokestatic 36	java/util/regex/Pattern:compile	(Ljava/lang/String;I)Ljava/util/regex/Pattern;
    //   38: putstatic 46	com/tencent/smtt/memory/d:c	Ljava/util/regex/Pattern;
    //   41: iconst_1
    //   42: putstatic 28	com/tencent/smtt/memory/d:jdField_a_of_type_Boolean	Z
    //   45: invokestatic 52	android/os/StrictMode:allowThreadDiskReads	()Landroid/os/StrictMode$ThreadPolicy;
    //   48: astore 5
    //   50: new 54	java/lang/StringBuilder
    //   53: dup
    //   54: invokespecial 57	java/lang/StringBuilder:<init>	()V
    //   57: astore 6
    //   59: aload 6
    //   61: ldc 59
    //   63: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: pop
    //   67: aload 6
    //   69: iload 4
    //   71: invokevirtual 66	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   74: pop
    //   75: aload 6
    //   77: ldc 68
    //   79: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   82: pop
    //   83: new 70	java/io/FileReader
    //   86: dup
    //   87: aload 6
    //   89: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   92: invokespecial 77	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   95: astore 6
    //   97: new 79	java/io/BufferedReader
    //   100: dup
    //   101: aload 6
    //   103: invokespecial 82	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   106: astore 7
    //   108: iconst_0
    //   109: istore_3
    //   110: iconst_0
    //   111: istore_2
    //   112: iconst_0
    //   113: istore_1
    //   114: aload 7
    //   116: invokevirtual 85	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   119: astore 8
    //   121: aload 8
    //   123: ifnonnull +44 -> 167
    //   126: new 54	java/lang/StringBuilder
    //   129: dup
    //   130: invokespecial 57	java/lang/StringBuilder:<init>	()V
    //   133: astore_0
    //   134: aload_0
    //   135: ldc 59
    //   137: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   140: pop
    //   141: aload_0
    //   142: iload 4
    //   144: invokevirtual 66	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   147: pop
    //   148: aload_0
    //   149: ldc 87
    //   151: invokevirtual 63	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   154: pop
    //   155: ldc 89
    //   157: aload_0
    //   158: invokevirtual 74	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   161: invokestatic 94	com/tencent/smtt/memory/c:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   164: goto +135 -> 299
    //   167: iload_3
    //   168: ifne +39 -> 207
    //   171: getstatic 38	com/tencent/smtt/memory/d:jdField_a_of_type_JavaUtilRegexPattern	Ljava/util/regex/Pattern;
    //   174: aload 8
    //   176: invokevirtual 98	java/util/regex/Pattern:matcher	(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
    //   179: astore 9
    //   181: aload 9
    //   183: invokevirtual 104	java/util/regex/Matcher:find	()Z
    //   186: ifeq +21 -> 207
    //   189: aload 9
    //   191: iconst_1
    //   192: invokevirtual 108	java/util/regex/Matcher:group	(I)Ljava/lang/String;
    //   195: invokestatic 114	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   198: istore_3
    //   199: aload_0
    //   200: iload_3
    //   201: invokevirtual 117	com/tencent/smtt/memory/d$a:a	(I)V
    //   204: goto -90 -> 114
    //   207: iload_2
    //   208: ifne +39 -> 247
    //   211: getstatic 42	com/tencent/smtt/memory/d:b	Ljava/util/regex/Pattern;
    //   214: aload 8
    //   216: invokevirtual 98	java/util/regex/Pattern:matcher	(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
    //   219: astore 9
    //   221: aload 9
    //   223: invokevirtual 104	java/util/regex/Matcher:find	()Z
    //   226: ifeq +21 -> 247
    //   229: aload 9
    //   231: iconst_1
    //   232: invokevirtual 108	java/util/regex/Matcher:group	(I)Ljava/lang/String;
    //   235: invokestatic 114	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   238: istore_2
    //   239: aload_0
    //   240: iload_2
    //   241: invokevirtual 119	com/tencent/smtt/memory/d$a:b	(I)V
    //   244: goto -130 -> 114
    //   247: iload_1
    //   248: ifne +39 -> 287
    //   251: getstatic 46	com/tencent/smtt/memory/d:c	Ljava/util/regex/Pattern;
    //   254: aload 8
    //   256: invokevirtual 98	java/util/regex/Pattern:matcher	(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
    //   259: astore 8
    //   261: aload 8
    //   263: invokevirtual 104	java/util/regex/Matcher:find	()Z
    //   266: ifeq +21 -> 287
    //   269: aload 8
    //   271: iconst_1
    //   272: invokevirtual 108	java/util/regex/Matcher:group	(I)Ljava/lang/String;
    //   275: invokestatic 114	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   278: istore_1
    //   279: aload_0
    //   280: iload_1
    //   281: invokevirtual 121	com/tencent/smtt/memory/d$a:c	(I)V
    //   284: goto -170 -> 114
    //   287: iload_3
    //   288: ifeq -174 -> 114
    //   291: iload_2
    //   292: ifeq -178 -> 114
    //   295: iload_1
    //   296: ifeq -182 -> 114
    //   299: aload 7
    //   301: invokevirtual 124	java/io/BufferedReader:close	()V
    //   304: aload 6
    //   306: invokevirtual 125	java/io/FileReader:close	()V
    //   309: goto +28 -> 337
    //   312: astore_0
    //   313: aload 7
    //   315: invokevirtual 124	java/io/BufferedReader:close	()V
    //   318: aload_0
    //   319: athrow
    //   320: astore_0
    //   321: aload 6
    //   323: invokevirtual 125	java/io/FileReader:close	()V
    //   326: aload_0
    //   327: athrow
    //   328: astore_0
    //   329: goto +14 -> 343
    //   332: astore_0
    //   333: aload_0
    //   334: invokestatic 128	com/tencent/smtt/memory/c:a	(Ljava/lang/Throwable;)V
    //   337: aload 5
    //   339: invokestatic 132	android/os/StrictMode:setThreadPolicy	(Landroid/os/StrictMode$ThreadPolicy;)V
    //   342: return
    //   343: aload 5
    //   345: invokestatic 132	android/os/StrictMode:setThreadPolicy	(Landroid/os/StrictMode$ThreadPolicy;)V
    //   348: aload_0
    //   349: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	350	0	parama	a
    //   113	183	1	i	int
    //   111	181	2	j	int
    //   109	179	3	k	int
    //   3	140	4	m	int
    //   48	296	5	localThreadPolicy	android.os.StrictMode.ThreadPolicy
    //   57	265	6	localObject1	Object
    //   106	208	7	localBufferedReader	java.io.BufferedReader
    //   119	151	8	localObject2	Object
    //   179	51	9	localMatcher	java.util.regex.Matcher
    // Exception table:
    //   from	to	target	type
    //   114	121	312	finally
    //   126	164	312	finally
    //   171	204	312	finally
    //   211	244	312	finally
    //   251	284	312	finally
    //   97	108	320	finally
    //   299	304	320	finally
    //   313	320	320	finally
    //   50	97	328	finally
    //   304	309	328	finally
    //   321	328	328	finally
    //   333	337	328	finally
    //   50	97	332	java/lang/Throwable
    //   304	309	332	java/lang/Throwable
    //   321	328	332	java/lang/Throwable
  }
  
  public static class a
  {
    private int a = 0;
    private int b = 0;
    private int c = 0;
    
    public static a a()
    {
      a locala = new a();
      try
      {
        d.a(locala);
        return locala;
      }
      catch (Throwable localThrowable)
      {
        c.a(localThrowable);
      }
      return locala;
    }
    
    public int a()
    {
      return this.b;
    }
    
    void a(int paramInt)
    {
      this.a = paramInt;
    }
    
    public int b()
    {
      return this.c;
    }
    
    void b(int paramInt)
    {
      this.b = paramInt;
    }
    
    void c(int paramInt)
    {
      this.c = paramInt;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\memory\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */