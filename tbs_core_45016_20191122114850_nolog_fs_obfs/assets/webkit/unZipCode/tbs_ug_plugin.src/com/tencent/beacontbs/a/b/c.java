package com.tencent.beacontbs.a.b;

import android.content.Context;
import com.tencent.beacontbs.a.e;

public final class c
  implements Runnable
{
  private static long jdField_a_of_type_Long;
  private Context jdField_a_of_type_AndroidContentContext = null;
  
  public c(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
  }
  
  private static long a()
  {
    try
    {
      long l = jdField_a_of_type_Long;
      return l;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static void a(long paramLong)
  {
    try
    {
      jdField_a_of_type_Long = paramLong;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  private static boolean a(Context paramContext)
  {
    boolean bool = true;
    try
    {
      int i = Integer.parseInt(com.tencent.beacontbs.a.a.a(paramContext, "querytimes", "0"));
      String str = com.tencent.beacontbs.a.a.a(paramContext, "initsdkdate", "");
      if (!e.g().equals(str))
      {
        com.tencent.beacontbs.a.a.b(paramContext, e.g());
        i = 0;
      }
      if (i <= d.a().b())
      {
        com.tencent.beacontbs.a.a.a(paramContext, String.valueOf(i + 1));
        return false;
      }
    }
    catch (Exception paramContext)
    {
      label77:
      for (;;) {}
    }
    try
    {
      com.tencent.beacontbs.c.a.e(" sdk init max times", new Object[0]);
      return true;
    }
    catch (Exception paramContext)
    {
      break label77;
    }
    bool = false;
    com.tencent.beacontbs.c.a.c(" set init times failed! ", new Object[0]);
    return bool;
  }
  
  /* Error */
  public final void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 18	com/tencent/beacontbs/a/b/c:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   4: invokestatic 95	com/tencent/beacontbs/a/e:a	(Landroid/content/Context;)Lcom/tencent/beacontbs/a/b/g;
    //   7: astore 6
    //   9: aload 6
    //   11: ifnull +70 -> 81
    //   14: aload 6
    //   16: invokevirtual 99	com/tencent/beacontbs/a/b/g:a	()I
    //   19: bipush 101
    //   21: if_icmpne +60 -> 81
    //   24: aload 6
    //   26: invokevirtual 102	com/tencent/beacontbs/a/b/g:a	()[B
    //   29: ifnull +52 -> 81
    //   32: aload_0
    //   33: getfield 18	com/tencent/beacontbs/a/b/c:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   36: invokestatic 107	com/tencent/beacontbs/a/b/b:a	(Landroid/content/Context;)Lcom/tencent/beacontbs/a/b/b;
    //   39: invokevirtual 110	com/tencent/beacontbs/a/b/b:a	()Lcom/tencent/beacontbs/upload/e;
    //   42: astore 7
    //   44: aload 7
    //   46: ifnull +35 -> 81
    //   49: aload 7
    //   51: bipush 101
    //   53: aload 6
    //   55: invokevirtual 102	com/tencent/beacontbs/a/b/g:a	()[B
    //   58: iconst_0
    //   59: invokevirtual 115	com/tencent/beacontbs/upload/e:a	(I[BZ)V
    //   62: ldc 117
    //   64: iconst_0
    //   65: anewarray 4	java/lang/Object
    //   68: invokestatic 82	com/tencent/beacontbs/c/a:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   71: goto +10 -> 81
    //   74: astore 6
    //   76: aload 6
    //   78: invokestatic 120	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   81: aload_0
    //   82: getfield 18	com/tencent/beacontbs/a/b/c:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   85: invokestatic 107	com/tencent/beacontbs/a/b/b:a	(Landroid/content/Context;)Lcom/tencent/beacontbs/a/b/b;
    //   88: astore 7
    //   90: aload 7
    //   92: invokevirtual 121	com/tencent/beacontbs/a/b/b:a	()I
    //   95: ifne +189 -> 284
    //   98: ldc 123
    //   100: iconst_0
    //   101: anewarray 4	java/lang/Object
    //   104: invokestatic 82	com/tencent/beacontbs/c/a:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   107: aload 7
    //   109: iconst_1
    //   110: invokevirtual 126	com/tencent/beacontbs/a/b/b:a	(I)V
    //   113: aload_0
    //   114: getfield 18	com/tencent/beacontbs/a/b/c:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   117: invokestatic 128	com/tencent/beacontbs/a/a:b	(Landroid/content/Context;)Z
    //   120: istore_3
    //   121: ldc -126
    //   123: iconst_1
    //   124: anewarray 4	java/lang/Object
    //   127: dup
    //   128: iconst_0
    //   129: iload_3
    //   130: invokestatic 135	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   133: aastore
    //   134: invokestatic 82	com/tencent/beacontbs/c/a:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   137: iload_3
    //   138: ifeq +102 -> 240
    //   141: ldc -119
    //   143: iconst_1
    //   144: anewarray 4	java/lang/Object
    //   147: dup
    //   148: iconst_0
    //   149: aload_0
    //   150: getfield 18	com/tencent/beacontbs/a/b/c:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   153: aconst_null
    //   154: invokestatic 142	com/tencent/beacontbs/a/a/a:a	(Landroid/content/Context;[I)I
    //   157: invokestatic 145	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   160: aastore
    //   161: invokestatic 82	com/tencent/beacontbs/c/a:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   164: aload 7
    //   166: monitorenter
    //   167: ldc -109
    //   169: iconst_0
    //   170: anewarray 4	java/lang/Object
    //   173: invokestatic 82	com/tencent/beacontbs/c/a:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   176: aload 7
    //   178: invokevirtual 150	com/tencent/beacontbs/a/b/b:a	()[Lcom/tencent/beacontbs/a/b/a;
    //   181: astore 6
    //   183: aload 7
    //   185: invokevirtual 152	com/tencent/beacontbs/a/b/b:b	()V
    //   188: aload 7
    //   190: monitorexit
    //   191: aload 6
    //   193: ifnull +91 -> 284
    //   196: ldc -102
    //   198: iconst_0
    //   199: anewarray 4	java/lang/Object
    //   202: invokestatic 82	com/tencent/beacontbs/c/a:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   205: aload 6
    //   207: arraylength
    //   208: istore_2
    //   209: iconst_0
    //   210: istore_1
    //   211: iload_1
    //   212: iload_2
    //   213: if_icmpge +71 -> 284
    //   216: aload 6
    //   218: iload_1
    //   219: aaload
    //   220: invokeinterface 158 1 0
    //   225: iload_1
    //   226: iconst_1
    //   227: iadd
    //   228: istore_1
    //   229: goto -18 -> 211
    //   232: astore 6
    //   234: aload 7
    //   236: monitorexit
    //   237: aload 6
    //   239: athrow
    //   240: invokestatic 160	com/tencent/beacontbs/a/b/c:a	()J
    //   243: lstore 4
    //   245: lload 4
    //   247: lconst_0
    //   248: lcmp
    //   249: ifle +35 -> 284
    //   252: ldc -94
    //   254: iconst_1
    //   255: anewarray 4	java/lang/Object
    //   258: dup
    //   259: iconst_0
    //   260: lload 4
    //   262: invokestatic 167	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   265: aastore
    //   266: invokestatic 82	com/tencent/beacontbs/c/a:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   269: lload 4
    //   271: invokestatic 172	java/lang/Thread:sleep	(J)V
    //   274: goto +10 -> 284
    //   277: astore 6
    //   279: aload 6
    //   281: invokestatic 120	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   284: aload 7
    //   286: monitorenter
    //   287: ldc -82
    //   289: iconst_0
    //   290: anewarray 4	java/lang/Object
    //   293: invokestatic 82	com/tencent/beacontbs/c/a:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   296: aload 7
    //   298: invokevirtual 150	com/tencent/beacontbs/a/b/b:a	()[Lcom/tencent/beacontbs/a/b/a;
    //   301: astore 6
    //   303: aload 7
    //   305: iconst_2
    //   306: invokevirtual 126	com/tencent/beacontbs/a/b/b:a	(I)V
    //   309: aload 7
    //   311: monitorexit
    //   312: aload 6
    //   314: ifnull +39 -> 353
    //   317: ldc -80
    //   319: iconst_0
    //   320: anewarray 4	java/lang/Object
    //   323: invokestatic 82	com/tencent/beacontbs/c/a:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   326: aload 6
    //   328: arraylength
    //   329: istore_2
    //   330: iconst_0
    //   331: istore_1
    //   332: iload_1
    //   333: iload_2
    //   334: if_icmpge +19 -> 353
    //   337: aload 6
    //   339: iload_1
    //   340: aaload
    //   341: invokeinterface 178 1 0
    //   346: iload_1
    //   347: iconst_1
    //   348: iadd
    //   349: istore_1
    //   350: goto -18 -> 332
    //   353: invokestatic 65	com/tencent/beacontbs/a/b/d:a	()Lcom/tencent/beacontbs/a/b/d;
    //   356: invokevirtual 181	com/tencent/beacontbs/a/b/d:a	()Z
    //   359: ifne +115 -> 474
    //   362: invokestatic 184	com/tencent/beacontbs/a/b/b:a	()Lcom/tencent/beacontbs/upload/g;
    //   365: astore 6
    //   367: iconst_0
    //   368: istore_1
    //   369: aload 6
    //   371: ifnonnull +36 -> 407
    //   374: iload_1
    //   375: iconst_1
    //   376: iadd
    //   377: istore_1
    //   378: iload_1
    //   379: iconst_5
    //   380: if_icmpge +27 -> 407
    //   383: ldc2_w 185
    //   386: invokestatic 172	java/lang/Thread:sleep	(J)V
    //   389: goto +10 -> 399
    //   392: astore 6
    //   394: aload 6
    //   396: invokestatic 120	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   399: invokestatic 184	com/tencent/beacontbs/a/b/b:a	()Lcom/tencent/beacontbs/upload/g;
    //   402: astore 6
    //   404: goto -35 -> 369
    //   407: aload 6
    //   409: ifnull +56 -> 465
    //   412: aload_0
    //   413: getfield 18	com/tencent/beacontbs/a/b/c:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   416: invokestatic 188	com/tencent/beacontbs/a/b/c:a	(Landroid/content/Context;)Z
    //   419: ifeq +25 -> 444
    //   422: ldc -66
    //   424: iconst_0
    //   425: anewarray 4	java/lang/Object
    //   428: invokestatic 87	com/tencent/beacontbs/c/a:c	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   431: aload_0
    //   432: getfield 18	com/tencent/beacontbs/a/b/c:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   435: invokestatic 107	com/tencent/beacontbs/a/b/b:a	(Landroid/content/Context;)Lcom/tencent/beacontbs/a/b/b;
    //   438: invokevirtual 191	com/tencent/beacontbs/a/b/b:a	()V
    //   441: goto +33 -> 474
    //   444: aload 6
    //   446: new 193	com/tencent/beacontbs/upload/c
    //   449: dup
    //   450: aload_0
    //   451: getfield 18	com/tencent/beacontbs/a/b/c:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   454: invokespecial 195	com/tencent/beacontbs/upload/c:<init>	(Landroid/content/Context;)V
    //   457: invokeinterface 200 2 0
    //   462: goto +12 -> 474
    //   465: ldc -54
    //   467: iconst_0
    //   468: anewarray 4	java/lang/Object
    //   471: invokestatic 205	com/tencent/beacontbs/c/a:h	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   474: ldc -49
    //   476: iconst_0
    //   477: anewarray 4	java/lang/Object
    //   480: invokestatic 205	com/tencent/beacontbs/c/a:h	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   483: aload 7
    //   485: monitorenter
    //   486: aload 7
    //   488: invokevirtual 150	com/tencent/beacontbs/a/b/b:a	()[Lcom/tencent/beacontbs/a/b/a;
    //   491: astore 6
    //   493: aload 7
    //   495: iconst_3
    //   496: invokevirtual 126	com/tencent/beacontbs/a/b/b:a	(I)V
    //   499: aload 7
    //   501: monitorexit
    //   502: aload 6
    //   504: ifnull +39 -> 543
    //   507: ldc -47
    //   509: iconst_0
    //   510: anewarray 4	java/lang/Object
    //   513: invokestatic 82	com/tencent/beacontbs/c/a:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   516: aload 6
    //   518: arraylength
    //   519: istore_2
    //   520: iconst_0
    //   521: istore_1
    //   522: iload_1
    //   523: iload_2
    //   524: if_icmpge +19 -> 543
    //   527: aload 6
    //   529: iload_1
    //   530: aaload
    //   531: invokeinterface 210 1 0
    //   536: iload_1
    //   537: iconst_1
    //   538: iadd
    //   539: istore_1
    //   540: goto -18 -> 522
    //   543: aload_0
    //   544: getfield 18	com/tencent/beacontbs/a/b/c:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   547: invokestatic 107	com/tencent/beacontbs/a/b/b:a	(Landroid/content/Context;)Lcom/tencent/beacontbs/a/b/b;
    //   550: invokevirtual 211	com/tencent/beacontbs/a/b/b:a	()Lcom/tencent/beacontbs/a/b/d;
    //   553: astore 6
    //   555: aload 6
    //   557: ifnonnull +13 -> 570
    //   560: ldc -43
    //   562: iconst_0
    //   563: anewarray 4	java/lang/Object
    //   566: invokestatic 216	com/tencent/beacontbs/c/a:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   569: return
    //   570: aload 6
    //   572: invokevirtual 217	com/tencent/beacontbs/a/b/d:a	()I
    //   575: ldc -38
    //   577: imul
    //   578: i2l
    //   579: lstore 4
    //   581: lload 4
    //   583: lconst_0
    //   584: lcmp
    //   585: ifle +41 -> 626
    //   588: invokestatic 223	com/tencent/beacontbs/a/b:a	()Lcom/tencent/beacontbs/a/b;
    //   591: aload_0
    //   592: lload 4
    //   594: invokevirtual 226	com/tencent/beacontbs/a/b:a	(Ljava/lang/Runnable;J)V
    //   597: ldc -28
    //   599: iconst_1
    //   600: anewarray 4	java/lang/Object
    //   603: dup
    //   604: iconst_0
    //   605: lload 4
    //   607: invokestatic 167	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   610: aastore
    //   611: invokestatic 205	com/tencent/beacontbs/c/a:h	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   614: aload_0
    //   615: getfield 18	com/tencent/beacontbs/a/b/c:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   618: invokestatic 107	com/tencent/beacontbs/a/b/b:a	(Landroid/content/Context;)Lcom/tencent/beacontbs/a/b/b;
    //   621: iconst_4
    //   622: invokevirtual 126	com/tencent/beacontbs/a/b/b:a	(I)V
    //   625: return
    //   626: ldc -26
    //   628: iconst_0
    //   629: anewarray 4	java/lang/Object
    //   632: invokestatic 205	com/tencent/beacontbs/c/a:h	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   635: return
    //   636: astore 6
    //   638: aload 7
    //   640: monitorexit
    //   641: aload 6
    //   643: athrow
    //   644: astore 6
    //   646: aload 7
    //   648: monitorexit
    //   649: aload 6
    //   651: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	652	0	this	c
    //   210	330	1	i	int
    //   208	317	2	j	int
    //   120	18	3	bool	boolean
    //   243	363	4	l	long
    //   7	47	6	localg	g
    //   74	3	6	localThrowable	Throwable
    //   181	36	6	arrayOfa	a[]
    //   232	6	6	localObject1	Object
    //   277	3	6	localInterruptedException1	InterruptedException
    //   301	69	6	localObject2	Object
    //   392	3	6	localInterruptedException2	InterruptedException
    //   402	169	6	localObject3	Object
    //   636	6	6	localObject4	Object
    //   644	6	6	localObject5	Object
    //   42	605	7	localObject6	Object
    // Exception table:
    //   from	to	target	type
    //   32	44	74	java/lang/Throwable
    //   49	71	74	java/lang/Throwable
    //   167	191	232	finally
    //   234	237	232	finally
    //   269	274	277	java/lang/InterruptedException
    //   383	389	392	java/lang/InterruptedException
    //   486	502	636	finally
    //   638	641	636	finally
    //   287	312	644	finally
    //   646	649	644	finally
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\a\b\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */