package com.tencent.beacontbs.event;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public final class g
{
  private int jdField_a_of_type_Int = 12;
  private Map<String, Float> jdField_a_of_type_JavaUtilMap = null;
  private Set<String> jdField_a_of_type_JavaUtilSet = null;
  private boolean jdField_a_of_type_Boolean = false;
  private int jdField_b_of_type_Int = 60;
  private boolean jdField_b_of_type_Boolean = true;
  private int jdField_c_of_type_Int = 12;
  private boolean jdField_c_of_type_Boolean = false;
  private int jdField_d_of_type_Int = 60;
  private boolean jdField_d_of_type_Boolean = false;
  private int jdField_e_of_type_Int = 20;
  private boolean jdField_e_of_type_Boolean = false;
  
  public final int a()
  {
    try
    {
      int i = this.jdField_a_of_type_Int;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  /* Error */
  public final void a(Map<String, String> paramMap)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnull +548 -> 551
    //   6: aload_1
    //   7: ldc 50
    //   9: invokeinterface 56 2 0
    //   14: checkcast 58	java/lang/String
    //   17: astore_3
    //   18: aload_3
    //   19: ifnull +26 -> 45
    //   22: aload_3
    //   23: invokestatic 64	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   26: invokevirtual 67	java/lang/Integer:intValue	()I
    //   29: istore_2
    //   30: iload_2
    //   31: ifle +14 -> 45
    //   34: iload_2
    //   35: bipush 50
    //   37: if_icmpgt +8 -> 45
    //   40: aload_0
    //   41: iload_2
    //   42: putfield 21	com/tencent/beacontbs/event/g:jdField_a_of_type_Int	I
    //   45: aload_1
    //   46: ldc 69
    //   48: invokeinterface 56 2 0
    //   53: checkcast 58	java/lang/String
    //   56: astore_3
    //   57: aload_3
    //   58: ifnull +29 -> 87
    //   61: aload_3
    //   62: invokestatic 64	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   65: invokevirtual 67	java/lang/Integer:intValue	()I
    //   68: istore_2
    //   69: iload_2
    //   70: bipush 10
    //   72: if_icmplt +15 -> 87
    //   75: iload_2
    //   76: sipush 600
    //   79: if_icmpgt +8 -> 87
    //   82: aload_0
    //   83: iload_2
    //   84: putfield 23	com/tencent/beacontbs/event/g:jdField_b_of_type_Int	I
    //   87: aload_1
    //   88: ldc 71
    //   90: invokeinterface 56 2 0
    //   95: checkcast 58	java/lang/String
    //   98: astore_3
    //   99: aload_3
    //   100: ifnull +26 -> 126
    //   103: aload_3
    //   104: invokestatic 64	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   107: invokevirtual 67	java/lang/Integer:intValue	()I
    //   110: istore_2
    //   111: iload_2
    //   112: ifle +14 -> 126
    //   115: iload_2
    //   116: bipush 50
    //   118: if_icmpgt +8 -> 126
    //   121: aload_0
    //   122: iload_2
    //   123: putfield 25	com/tencent/beacontbs/event/g:jdField_c_of_type_Int	I
    //   126: aload_1
    //   127: ldc 73
    //   129: invokeinterface 56 2 0
    //   134: checkcast 58	java/lang/String
    //   137: astore_3
    //   138: aload_3
    //   139: ifnull +29 -> 168
    //   142: aload_3
    //   143: invokestatic 64	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   146: invokevirtual 67	java/lang/Integer:intValue	()I
    //   149: istore_2
    //   150: iload_2
    //   151: bipush 30
    //   153: if_icmplt +15 -> 168
    //   156: iload_2
    //   157: sipush 600
    //   160: if_icmpgt +8 -> 168
    //   163: aload_0
    //   164: iload_2
    //   165: putfield 27	com/tencent/beacontbs/event/g:jdField_d_of_type_Int	I
    //   168: aload_1
    //   169: ldc 75
    //   171: invokeinterface 56 2 0
    //   176: checkcast 58	java/lang/String
    //   179: astore_3
    //   180: aload_3
    //   181: ifnull +26 -> 207
    //   184: aload_3
    //   185: invokestatic 64	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   188: invokevirtual 67	java/lang/Integer:intValue	()I
    //   191: istore_2
    //   192: iload_2
    //   193: ifle +14 -> 207
    //   196: iload_2
    //   197: bipush 100
    //   199: if_icmpgt +8 -> 207
    //   202: aload_0
    //   203: iload_2
    //   204: putfield 29	com/tencent/beacontbs/event/g:jdField_e_of_type_Int	I
    //   207: aload_1
    //   208: ldc 77
    //   210: invokeinterface 56 2 0
    //   215: checkcast 58	java/lang/String
    //   218: astore_3
    //   219: aload_3
    //   220: ifnull +40 -> 260
    //   223: aload_3
    //   224: invokevirtual 81	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   227: ldc 83
    //   229: invokevirtual 87	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   232: ifeq +11 -> 243
    //   235: aload_0
    //   236: iconst_1
    //   237: putfield 33	com/tencent/beacontbs/event/g:jdField_b_of_type_Boolean	Z
    //   240: goto +20 -> 260
    //   243: aload_3
    //   244: invokevirtual 81	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   247: ldc 89
    //   249: invokevirtual 87	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   252: ifeq +8 -> 260
    //   255: aload_0
    //   256: iconst_0
    //   257: putfield 33	com/tencent/beacontbs/event/g:jdField_b_of_type_Boolean	Z
    //   260: aload_1
    //   261: ldc 91
    //   263: invokeinterface 56 2 0
    //   268: checkcast 58	java/lang/String
    //   271: astore_3
    //   272: aload_3
    //   273: ifnull +40 -> 313
    //   276: aload_3
    //   277: invokevirtual 81	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   280: ldc 83
    //   282: invokevirtual 87	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   285: ifeq +11 -> 296
    //   288: aload_0
    //   289: iconst_1
    //   290: putfield 41	com/tencent/beacontbs/event/g:jdField_d_of_type_Boolean	Z
    //   293: goto +20 -> 313
    //   296: aload_3
    //   297: invokevirtual 81	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   300: ldc 89
    //   302: invokevirtual 87	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   305: ifeq +8 -> 313
    //   308: aload_0
    //   309: iconst_0
    //   310: putfield 41	com/tencent/beacontbs/event/g:jdField_d_of_type_Boolean	Z
    //   313: aload_1
    //   314: ldc 93
    //   316: invokeinterface 56 2 0
    //   321: checkcast 58	java/lang/String
    //   324: astore_3
    //   325: aload_3
    //   326: ifnull +40 -> 366
    //   329: aload_3
    //   330: invokevirtual 81	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   333: ldc 83
    //   335: invokevirtual 87	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   338: ifeq +11 -> 349
    //   341: aload_0
    //   342: iconst_1
    //   343: putfield 43	com/tencent/beacontbs/event/g:jdField_e_of_type_Boolean	Z
    //   346: goto +20 -> 366
    //   349: aload_3
    //   350: invokevirtual 81	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   353: ldc 89
    //   355: invokevirtual 87	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   358: ifeq +8 -> 366
    //   361: aload_0
    //   362: iconst_0
    //   363: putfield 43	com/tencent/beacontbs/event/g:jdField_e_of_type_Boolean	Z
    //   366: aload_1
    //   367: ldc 95
    //   369: invokeinterface 56 2 0
    //   374: checkcast 58	java/lang/String
    //   377: astore_3
    //   378: aload_3
    //   379: ifnull +40 -> 419
    //   382: aload_3
    //   383: invokevirtual 81	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   386: ldc 83
    //   388: invokevirtual 87	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   391: ifeq +11 -> 402
    //   394: aload_0
    //   395: iconst_1
    //   396: putfield 31	com/tencent/beacontbs/event/g:jdField_a_of_type_Boolean	Z
    //   399: goto +20 -> 419
    //   402: aload_3
    //   403: invokevirtual 81	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   406: ldc 89
    //   408: invokevirtual 87	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   411: ifeq +8 -> 419
    //   414: aload_0
    //   415: iconst_0
    //   416: putfield 31	com/tencent/beacontbs/event/g:jdField_a_of_type_Boolean	Z
    //   419: aload_1
    //   420: ldc 97
    //   422: invokeinterface 56 2 0
    //   427: checkcast 58	java/lang/String
    //   430: astore_3
    //   431: aload_3
    //   432: ifnull +38 -> 470
    //   435: aload_3
    //   436: invokevirtual 81	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   439: ldc 83
    //   441: invokevirtual 87	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   444: ifeq +10 -> 454
    //   447: iconst_1
    //   448: putstatic 100	com/tencent/beacontbs/c/a:jdField_b_of_type_Boolean	Z
    //   451: goto +19 -> 470
    //   454: aload_3
    //   455: invokevirtual 81	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   458: ldc 89
    //   460: invokevirtual 87	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   463: ifeq +7 -> 470
    //   466: iconst_0
    //   467: putstatic 100	com/tencent/beacontbs/c/a:jdField_b_of_type_Boolean	Z
    //   470: aload_1
    //   471: ldc 102
    //   473: invokeinterface 56 2 0
    //   478: checkcast 58	java/lang/String
    //   481: astore_3
    //   482: aload_3
    //   483: ifnull +40 -> 523
    //   486: aload_3
    //   487: invokevirtual 81	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   490: ldc 83
    //   492: invokevirtual 87	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   495: ifeq +11 -> 506
    //   498: aload_0
    //   499: iconst_1
    //   500: putfield 39	com/tencent/beacontbs/event/g:jdField_c_of_type_Boolean	Z
    //   503: goto +20 -> 523
    //   506: aload_3
    //   507: invokevirtual 81	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   510: ldc 89
    //   512: invokevirtual 87	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   515: ifeq +8 -> 523
    //   518: aload_0
    //   519: iconst_0
    //   520: putfield 39	com/tencent/beacontbs/event/g:jdField_c_of_type_Boolean	Z
    //   523: aload_1
    //   524: ldc 104
    //   526: invokeinterface 56 2 0
    //   531: pop
    //   532: aload_0
    //   533: monitorexit
    //   534: return
    //   535: astore_1
    //   536: goto +11 -> 547
    //   539: astore_1
    //   540: aload_1
    //   541: invokestatic 107	com/tencent/beacontbs/c/a:a	(Ljava/lang/Throwable;)V
    //   544: goto +7 -> 551
    //   547: aload_0
    //   548: monitorexit
    //   549: aload_1
    //   550: athrow
    //   551: aload_0
    //   552: monitorexit
    //   553: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	554	0	this	g
    //   0	554	1	paramMap	Map<String, String>
    //   29	175	2	i	int
    //   17	490	3	str	String
    // Exception table:
    //   from	to	target	type
    //   6	18	535	finally
    //   22	30	535	finally
    //   40	45	535	finally
    //   45	57	535	finally
    //   61	69	535	finally
    //   82	87	535	finally
    //   87	99	535	finally
    //   103	111	535	finally
    //   121	126	535	finally
    //   126	138	535	finally
    //   142	150	535	finally
    //   163	168	535	finally
    //   168	180	535	finally
    //   184	192	535	finally
    //   202	207	535	finally
    //   207	219	535	finally
    //   223	240	535	finally
    //   243	260	535	finally
    //   260	272	535	finally
    //   276	293	535	finally
    //   296	313	535	finally
    //   313	325	535	finally
    //   329	346	535	finally
    //   349	366	535	finally
    //   366	378	535	finally
    //   382	399	535	finally
    //   402	419	535	finally
    //   419	431	535	finally
    //   435	451	535	finally
    //   454	470	535	finally
    //   470	482	535	finally
    //   486	503	535	finally
    //   506	523	535	finally
    //   523	532	535	finally
    //   540	544	535	finally
    //   6	18	539	java/lang/Exception
    //   22	30	539	java/lang/Exception
    //   40	45	539	java/lang/Exception
    //   45	57	539	java/lang/Exception
    //   61	69	539	java/lang/Exception
    //   82	87	539	java/lang/Exception
    //   87	99	539	java/lang/Exception
    //   103	111	539	java/lang/Exception
    //   121	126	539	java/lang/Exception
    //   126	138	539	java/lang/Exception
    //   142	150	539	java/lang/Exception
    //   163	168	539	java/lang/Exception
    //   168	180	539	java/lang/Exception
    //   184	192	539	java/lang/Exception
    //   202	207	539	java/lang/Exception
    //   207	219	539	java/lang/Exception
    //   223	240	539	java/lang/Exception
    //   243	260	539	java/lang/Exception
    //   260	272	539	java/lang/Exception
    //   276	293	539	java/lang/Exception
    //   296	313	539	java/lang/Exception
    //   313	325	539	java/lang/Exception
    //   329	346	539	java/lang/Exception
    //   349	366	539	java/lang/Exception
    //   366	378	539	java/lang/Exception
    //   382	399	539	java/lang/Exception
    //   402	419	539	java/lang/Exception
    //   419	431	539	java/lang/Exception
    //   435	451	539	java/lang/Exception
    //   454	470	539	java/lang/Exception
    //   470	482	539	java/lang/Exception
    //   486	503	539	java/lang/Exception
    //   506	523	539	java/lang/Exception
    //   523	532	539	java/lang/Exception
  }
  
  public final void a(Set<String> paramSet)
  {
    try
    {
      this.jdField_a_of_type_JavaUtilSet = paramSet;
      return;
    }
    finally
    {
      paramSet = finally;
      throw paramSet;
    }
  }
  
  public final boolean a()
  {
    return this.jdField_b_of_type_Boolean;
  }
  
  public final boolean a(String paramString)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    try
    {
      if (this.jdField_a_of_type_JavaUtilSet != null)
      {
        bool1 = bool2;
        if (this.jdField_a_of_type_JavaUtilSet.size() > 0) {
          bool1 = this.jdField_a_of_type_JavaUtilSet.contains(paramString);
        }
      }
      return bool1;
    }
    finally {}
  }
  
  public final int b()
  {
    try
    {
      int i = this.jdField_b_of_type_Int;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void b(Set<String> paramSet)
  {
    for (;;)
    {
      String[] arrayOfString;
      float f;
      try
      {
        if (this.jdField_a_of_type_JavaUtilMap == null) {
          this.jdField_a_of_type_JavaUtilMap = new HashMap();
        }
        paramSet = paramSet.iterator();
        if (paramSet.hasNext())
        {
          arrayOfString = ((String)paramSet.next()).split(",");
          int i = arrayOfString.length;
          if (i != 3) {
            continue;
          }
        }
      }
      finally {}
      try
      {
        f = Float.valueOf(arrayOfString[1]).floatValue() / Float.valueOf(arrayOfString[2]).floatValue();
        this.jdField_a_of_type_JavaUtilMap.put(arrayOfString[0].toLowerCase(), Float.valueOf(f));
      }
      catch (Exception localException) {}
      return;
    }
  }
  
  public final boolean b()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public final boolean b(String paramString)
  {
    try
    {
      Map localMap = this.jdField_a_of_type_JavaUtilMap;
      boolean bool = true;
      if ((localMap != null) && (this.jdField_a_of_type_JavaUtilMap.get(paramString) != null))
      {
        int i = (int)(((Float)this.jdField_a_of_type_JavaUtilMap.get(paramString.toLowerCase())).floatValue() * 1000.0F);
        int j = new Random().nextInt(1000);
        if (j + 1 > i) {
          bool = false;
        }
        return bool;
      }
      return true;
    }
    finally {}
  }
  
  public final int c()
  {
    try
    {
      int i = this.jdField_c_of_type_Int;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final boolean c()
  {
    return this.jdField_d_of_type_Boolean;
  }
  
  public final int d()
  {
    try
    {
      int i = this.jdField_d_of_type_Int;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final boolean d()
  {
    return this.jdField_e_of_type_Boolean;
  }
  
  public final int e()
  {
    try
    {
      int i = this.jdField_e_of_type_Int;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final boolean e()
  {
    return this.jdField_c_of_type_Boolean;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\event\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */