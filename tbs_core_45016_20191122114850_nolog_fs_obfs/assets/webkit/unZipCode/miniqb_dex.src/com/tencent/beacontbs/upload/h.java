package com.tencent.beacontbs.upload;

import android.content.Context;
import android.util.SparseArray;
import com.tencent.beacontbs.b.a.c;
import java.util.ArrayList;
import java.util.List;

public final class h
  implements g
{
  private static h jdField_a_of_type_ComTencentBeacontbsUploadH;
  private Context jdField_a_of_type_AndroidContentContext;
  private SparseArray<e> jdField_a_of_type_AndroidUtilSparseArray = new SparseArray(5);
  private List<a> jdField_a_of_type_JavaUtilList = new ArrayList(5);
  private boolean jdField_a_of_type_Boolean;
  private boolean b;
  
  private h(Context paramContext, boolean paramBoolean)
  {
    Context localContext = null;
    this.jdField_a_of_type_AndroidContentContext = null;
    this.jdField_a_of_type_Boolean = true;
    this.b = true;
    if (paramContext != null) {
      localContext = paramContext.getApplicationContext();
    }
    if (localContext != null) {
      this.jdField_a_of_type_AndroidContentContext = localContext;
    } else {
      this.jdField_a_of_type_AndroidContentContext = paramContext;
    }
    this.jdField_a_of_type_Boolean = paramBoolean;
  }
  
  private SparseArray<e> a()
  {
    try
    {
      if ((this.jdField_a_of_type_AndroidUtilSparseArray != null) && (this.jdField_a_of_type_AndroidUtilSparseArray.size() > 0))
      {
        new com.tencent.beacontbs.c.b();
        SparseArray localSparseArray = com.tencent.beacontbs.c.b.a(this.jdField_a_of_type_AndroidUtilSparseArray);
        return localSparseArray;
      }
      return null;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  private static c a(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte != null) {}
    for (;;)
    {
      try
      {
        Object localObject = com.tencent.beacontbs.a.b.d.a();
        if (localObject == null) {
          break label88;
        }
        int i = ((com.tencent.beacontbs.a.b.d)localObject).a();
        paramArrayOfByte = com.tencent.beacontbs.a.e.b(paramArrayOfByte, ((com.tencent.beacontbs.a.b.d)localObject).b(), i, ((com.tencent.beacontbs.a.b.d)localObject).c());
        if (paramArrayOfByte != null)
        {
          localObject = new com.tencent.beacontbs.d.d();
          ((com.tencent.beacontbs.d.d)localObject).a(paramArrayOfByte);
          paramArrayOfByte = new c();
          com.tencent.beacontbs.c.a.b(" covert to ResponsePackage ", new Object[0]);
          paramArrayOfByte = (c)((com.tencent.beacontbs.d.d)localObject).a("detail", paramArrayOfByte);
          return paramArrayOfByte;
        }
      }
      catch (Throwable paramArrayOfByte)
      {
        com.tencent.beacontbs.c.a.a(paramArrayOfByte);
      }
      return null;
      label88:
      paramArrayOfByte = null;
    }
  }
  
  private d a()
  {
    try
    {
      d locald = d.a(this.jdField_a_of_type_AndroidContentContext);
      return locald;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static h a(Context paramContext)
  {
    try
    {
      if (jdField_a_of_type_ComTencentBeacontbsUploadH == null)
      {
        jdField_a_of_type_ComTencentBeacontbsUploadH = new h(paramContext, true);
        com.tencent.beacontbs.c.a.h(" create uphandler up:true", new Object[0]);
      }
      paramContext = jdField_a_of_type_ComTencentBeacontbsUploadH;
      return paramContext;
    }
    finally {}
  }
  
  public static h a(Context paramContext, boolean paramBoolean)
  {
    try
    {
      if (jdField_a_of_type_ComTencentBeacontbsUploadH == null)
      {
        jdField_a_of_type_ComTencentBeacontbsUploadH = new h(paramContext, paramBoolean);
        com.tencent.beacontbs.c.a.h(" create uphandler up: %b", new Object[] { Boolean.valueOf(paramBoolean) });
      }
      if (jdField_a_of_type_ComTencentBeacontbsUploadH.jdField_a_of_type_Boolean != paramBoolean)
      {
        jdField_a_of_type_ComTencentBeacontbsUploadH.jdField_a_of_type_Boolean = paramBoolean;
        com.tencent.beacontbs.c.a.h(" change uphandler up: %b", new Object[] { Boolean.valueOf(paramBoolean) });
      }
      paramContext = jdField_a_of_type_ComTencentBeacontbsUploadH;
      return paramContext;
    }
    finally {}
  }
  
  private void a(int paramInt1, int paramInt2, boolean paramBoolean, String paramString)
  {
    a[] arrayOfa = a();
    if (arrayOfa != null)
    {
      int j = arrayOfa.length;
      int i = 0;
      while (i < j)
      {
        arrayOfa[i].a(paramInt1, paramInt2, 0L, 0L, paramBoolean, paramString);
        i += 1;
      }
    }
  }
  
  private static boolean a(SparseArray<e> paramSparseArray, int paramInt, byte[] paramArrayOfByte)
  {
    if (paramSparseArray != null)
    {
      if (paramArrayOfByte == null) {
        return true;
      }
      if (paramInt != 103)
      {
        paramSparseArray = (e)paramSparseArray.get(paramInt);
        if (paramSparseArray == null)
        {
          com.tencent.beacontbs.c.a.c(" no handler key:%d", new Object[] { Integer.valueOf(paramInt) });
          return false;
        }
        try
        {
          com.tencent.beacontbs.c.a.b(" key:%d  handler: %s", new Object[] { Integer.valueOf(paramInt), paramSparseArray.getClass().toString() });
          paramSparseArray.a(paramInt, paramArrayOfByte, true);
          return true;
        }
        catch (Throwable paramSparseArray)
        {
          com.tencent.beacontbs.c.a.a(paramSparseArray);
          com.tencent.beacontbs.c.a.d(" handle error key:%d", new Object[] { Integer.valueOf(paramInt) });
          return false;
        }
      }
      return true;
    }
    return true;
  }
  
  private static byte[] a(b paramb)
  {
    if (paramb != null) {
      try
      {
        Object localObject1 = paramb.a();
        if (localObject1 != null)
        {
          com.tencent.beacontbs.c.a.b(" RequestPackage info appkey:%s sdkid:%s appVersion:%s cmd: %d", new Object[] { ((com.tencent.beacontbs.b.a.b)localObject1).jdField_a_of_type_JavaLangString, ((com.tencent.beacontbs.b.a.b)localObject1).c, ((com.tencent.beacontbs.b.a.b)localObject1).b, Integer.valueOf(((com.tencent.beacontbs.b.a.b)localObject1).jdField_a_of_type_Int) });
          Object localObject2 = new com.tencent.beacontbs.d.d();
          ((com.tencent.beacontbs.d.d)localObject2).a.jdField_a_of_type_Int = 1;
          ((com.tencent.beacontbs.d.d)localObject2).a.jdField_a_of_type_JavaLangString = "test";
          ((com.tencent.beacontbs.d.d)localObject2).a.b = "test";
          ((com.tencent.beacontbs.d.d)localObject2).a("detail", localObject1);
          localObject1 = ((com.tencent.beacontbs.d.d)localObject2).a();
          localObject2 = com.tencent.beacontbs.a.b.d.a();
          if (localObject2 != null)
          {
            int i = ((com.tencent.beacontbs.a.b.d)localObject2).a();
            localObject1 = com.tencent.beacontbs.a.e.a((byte[])localObject1, ((com.tencent.beacontbs.a.b.d)localObject2).b(), i, ((com.tencent.beacontbs.a.b.d)localObject2).c());
            return (byte[])localObject1;
          }
        }
      }
      catch (Throwable localThrowable)
      {
        com.tencent.beacontbs.c.a.d(" parseSendDatas error", new Object[0]);
        com.tencent.beacontbs.c.a.a(localThrowable);
        paramb.a();
      }
    }
    return null;
  }
  
  private a[] a()
  {
    try
    {
      if ((this.jdField_a_of_type_JavaUtilList != null) && (this.jdField_a_of_type_JavaUtilList.size() > 0))
      {
        a[] arrayOfa = (a[])this.jdField_a_of_type_JavaUtilList.toArray(new a[0]);
        return arrayOfa;
      }
      return null;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  /* Error */
  public final void a(b paramb)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 231	com/tencent/beacontbs/upload/b:a	()I
    //   4: istore 4
    //   6: aload_0
    //   7: getfield 38	com/tencent/beacontbs/upload/h:jdField_a_of_type_Boolean	Z
    //   10: ifeq +10 -> 20
    //   13: aload_0
    //   14: invokevirtual 234	com/tencent/beacontbs/upload/h:b	()Z
    //   17: ifne +57 -> 74
    //   20: iload 4
    //   22: iconst_2
    //   23: if_icmpne +17 -> 40
    //   26: ldc -20
    //   28: iconst_0
    //   29: anewarray 4	java/lang/Object
    //   32: invokestatic 121	com/tencent/beacontbs/c/a:h	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   35: aload_1
    //   36: iconst_0
    //   37: invokevirtual 239	com/tencent/beacontbs/upload/b:b	(Z)V
    //   40: aload_1
    //   41: getfield 241	com/tencent/beacontbs/upload/b:b	I
    //   44: ifeq +21 -> 65
    //   47: ldc -13
    //   49: iconst_1
    //   50: anewarray 4	java/lang/Object
    //   53: dup
    //   54: iconst_0
    //   55: iload 4
    //   57: invokestatic 155	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   60: aastore
    //   61: invokestatic 121	com/tencent/beacontbs/c/a:h	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   64: return
    //   65: ldc -11
    //   67: iconst_0
    //   68: anewarray 4	java/lang/Object
    //   71: invokestatic 121	com/tencent/beacontbs/c/a:h	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   74: aload_0
    //   75: getfield 36	com/tencent/beacontbs/upload/h:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   78: invokestatic 248	com/tencent/beacontbs/a/e:b	(Landroid/content/Context;)Z
    //   81: ifne +24 -> 105
    //   84: ldc -6
    //   86: iconst_0
    //   87: anewarray 4	java/lang/Object
    //   90: invokestatic 157	com/tencent/beacontbs/c/a:c	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   93: iload 4
    //   95: iconst_2
    //   96: if_icmpne +8 -> 104
    //   99: aload_1
    //   100: iconst_0
    //   101: invokevirtual 239	com/tencent/beacontbs/upload/b:b	(Z)V
    //   104: return
    //   105: aload_1
    //   106: invokevirtual 252	com/tencent/beacontbs/upload/b:b	()Ljava/lang/String;
    //   109: astore 12
    //   111: iconst_m1
    //   112: istore_3
    //   113: aload 12
    //   115: ifnull +1360 -> 1475
    //   118: ldc -2
    //   120: aload 12
    //   122: invokevirtual 259	java/lang/String:trim	()Ljava/lang/String;
    //   125: invokevirtual 263	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   128: ifeq +6 -> 134
    //   131: goto +1344 -> 1475
    //   134: aload_1
    //   135: invokestatic 265	com/tencent/beacontbs/upload/h:a	(Lcom/tencent/beacontbs/upload/b;)[B
    //   138: astore 13
    //   140: aload 13
    //   142: ifnonnull +25 -> 167
    //   145: ldc_w 267
    //   148: iconst_0
    //   149: anewarray 4	java/lang/Object
    //   152: invokestatic 157	com/tencent/beacontbs/c/a:c	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   155: aload_0
    //   156: iload 4
    //   158: iconst_m1
    //   159: iconst_0
    //   160: ldc_w 269
    //   163: invokespecial 271	com/tencent/beacontbs/upload/h:a	(IIZLjava/lang/String;)V
    //   166: return
    //   167: aload_0
    //   168: invokespecial 273	com/tencent/beacontbs/upload/h:a	()Lcom/tencent/beacontbs/upload/d;
    //   171: astore 14
    //   173: aload 14
    //   175: ifnonnull +25 -> 200
    //   178: ldc_w 275
    //   181: iconst_0
    //   182: anewarray 4	java/lang/Object
    //   185: invokestatic 176	com/tencent/beacontbs/c/a:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   188: aload_0
    //   189: iload 4
    //   191: iconst_m1
    //   192: iconst_0
    //   193: ldc_w 277
    //   196: invokespecial 271	com/tencent/beacontbs/upload/h:a	(IIZLjava/lang/String;)V
    //   199: return
    //   200: aload 14
    //   202: invokevirtual 279	com/tencent/beacontbs/upload/d:a	()Z
    //   205: ifne +197 -> 402
    //   208: aload_1
    //   209: invokevirtual 281	com/tencent/beacontbs/upload/b:a	()Ljava/lang/String;
    //   212: astore 10
    //   214: aload 10
    //   216: ifnull +33 -> 249
    //   219: new 283	java/lang/StringBuilder
    //   222: dup
    //   223: ldc_w 285
    //   226: invokespecial 288	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   229: astore 11
    //   231: aload 11
    //   233: aload 10
    //   235: invokevirtual 292	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   238: pop
    //   239: aload 11
    //   241: invokevirtual 293	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   244: astore 10
    //   246: goto +6 -> 252
    //   249: aconst_null
    //   250: astore 10
    //   252: invokestatic 67	com/tencent/beacontbs/a/b/d:a	()Lcom/tencent/beacontbs/a/b/d;
    //   255: astore 15
    //   257: aload 10
    //   259: astore 11
    //   261: aload 15
    //   263: ifnull +109 -> 372
    //   266: aload 15
    //   268: invokevirtual 295	com/tencent/beacontbs/a/b/d:d	()Ljava/lang/String;
    //   271: astore 15
    //   273: aload 10
    //   275: astore 11
    //   277: aload 15
    //   279: ifnull +93 -> 372
    //   282: aload 10
    //   284: astore 11
    //   286: ldc -2
    //   288: aload 15
    //   290: invokevirtual 263	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   293: ifne +79 -> 372
    //   296: aload 10
    //   298: ifnonnull +33 -> 331
    //   301: new 283	java/lang/StringBuilder
    //   304: dup
    //   305: ldc_w 297
    //   308: invokespecial 288	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   311: astore 10
    //   313: aload 10
    //   315: aload 15
    //   317: invokevirtual 292	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   320: pop
    //   321: aload 10
    //   323: invokevirtual 293	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   326: astore 11
    //   328: goto +44 -> 372
    //   331: new 283	java/lang/StringBuilder
    //   334: dup
    //   335: invokespecial 298	java/lang/StringBuilder:<init>	()V
    //   338: astore 11
    //   340: aload 11
    //   342: aload 10
    //   344: invokevirtual 292	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   347: pop
    //   348: aload 11
    //   350: ldc_w 300
    //   353: invokevirtual 292	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   356: pop
    //   357: aload 11
    //   359: aload 15
    //   361: invokevirtual 292	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   364: pop
    //   365: aload 11
    //   367: invokevirtual 293	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   370: astore 11
    //   372: aload 11
    //   374: ifnull +28 -> 402
    //   377: new 283	java/lang/StringBuilder
    //   380: dup
    //   381: invokespecial 298	java/lang/StringBuilder:<init>	()V
    //   384: astore 10
    //   386: aload 10
    //   388: aload 12
    //   390: invokevirtual 292	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   393: pop
    //   394: aload 10
    //   396: aload 11
    //   398: invokevirtual 292	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   401: pop
    //   402: ldc_w 302
    //   405: iconst_3
    //   406: anewarray 4	java/lang/Object
    //   409: dup
    //   410: iconst_0
    //   411: iload 4
    //   413: invokestatic 155	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   416: aastore
    //   417: dup
    //   418: iconst_1
    //   419: aload 12
    //   421: aastore
    //   422: dup
    //   423: iconst_2
    //   424: aload_1
    //   425: invokevirtual 163	java/lang/Object:getClass	()Ljava/lang/Class;
    //   428: invokevirtual 168	java/lang/Class:toString	()Ljava/lang/String;
    //   431: aastore
    //   432: invokestatic 121	com/tencent/beacontbs/c/a:h	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   435: iload_3
    //   436: istore_2
    //   437: aload 14
    //   439: invokevirtual 279	com/tencent/beacontbs/upload/d:a	()Z
    //   442: ifeq +211 -> 653
    //   445: iload_3
    //   446: istore_2
    //   447: aload 14
    //   449: aload 12
    //   451: invokestatic 67	com/tencent/beacontbs/a/b/d:a	()Lcom/tencent/beacontbs/a/b/d;
    //   454: invokevirtual 304	com/tencent/beacontbs/a/b/d:c	()I
    //   457: aload 13
    //   459: aload_1
    //   460: invokevirtual 307	com/tencent/beacontbs/upload/d:a	(Ljava/lang/String;I[BLcom/tencent/beacontbs/upload/b;)[B
    //   463: astore 11
    //   465: aload 11
    //   467: astore 10
    //   469: aload 11
    //   471: ifnull +243 -> 714
    //   474: iload_3
    //   475: istore_2
    //   476: new 309	com/tencent/beacontbs/b/a/e
    //   479: dup
    //   480: invokespecial 310	com/tencent/beacontbs/b/a/e:<init>	()V
    //   483: astore 10
    //   485: iload_3
    //   486: istore_2
    //   487: aload 10
    //   489: new 312	com/tencent/beacontbs/d/a
    //   492: dup
    //   493: aload 11
    //   495: invokespecial 314	com/tencent/beacontbs/d/a:<init>	([B)V
    //   498: invokevirtual 317	com/tencent/beacontbs/b/a/e:a	(Lcom/tencent/beacontbs/d/a;)V
    //   501: iload_3
    //   502: istore_2
    //   503: ldc_w 319
    //   506: iconst_4
    //   507: anewarray 4	java/lang/Object
    //   510: dup
    //   511: iconst_0
    //   512: aload 10
    //   514: getfield 322	com/tencent/beacontbs/b/a/e:jdField_a_of_type_ArrayOfByte	[B
    //   517: arraylength
    //   518: invokestatic 155	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   521: aastore
    //   522: dup
    //   523: iconst_1
    //   524: aload 10
    //   526: getfield 323	com/tencent/beacontbs/b/a/e:jdField_a_of_type_Int	I
    //   529: invokestatic 155	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   532: aastore
    //   533: dup
    //   534: iconst_2
    //   535: aload 10
    //   537: getfield 326	com/tencent/beacontbs/b/a/e:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   540: aastore
    //   541: dup
    //   542: iconst_3
    //   543: aload 10
    //   545: getfield 327	com/tencent/beacontbs/b/a/e:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   548: aastore
    //   549: invokestatic 97	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   552: iload_3
    //   553: istore_2
    //   554: aload 10
    //   556: getfield 326	com/tencent/beacontbs/b/a/e:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   559: astore 12
    //   561: iload_3
    //   562: istore_2
    //   563: aload 12
    //   565: ldc_w 329
    //   568: invokeinterface 334 2 0
    //   573: ifeq +68 -> 641
    //   576: iload_3
    //   577: istore_2
    //   578: aload 12
    //   580: ldc_w 336
    //   583: invokeinterface 334 2 0
    //   588: ifeq +53 -> 641
    //   591: iload_3
    //   592: istore_2
    //   593: aload 12
    //   595: ldc_w 329
    //   598: invokeinterface 339 2 0
    //   603: checkcast 256	java/lang/String
    //   606: astore 11
    //   608: iload_3
    //   609: istore_2
    //   610: aload 12
    //   612: ldc_w 336
    //   615: invokeinterface 339 2 0
    //   620: checkcast 256	java/lang/String
    //   623: astore 12
    //   625: iload_3
    //   626: istore_2
    //   627: invokestatic 67	com/tencent/beacontbs/a/b/d:a	()Lcom/tencent/beacontbs/a/b/d;
    //   630: aload_0
    //   631: getfield 36	com/tencent/beacontbs/upload/h:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   634: aload 11
    //   636: aload 12
    //   638: invokevirtual 342	com/tencent/beacontbs/a/b/d:a	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    //   641: iload_3
    //   642: istore_2
    //   643: aload 10
    //   645: getfield 322	com/tencent/beacontbs/b/a/e:jdField_a_of_type_ArrayOfByte	[B
    //   648: astore 10
    //   650: goto +64 -> 714
    //   653: iload_3
    //   654: istore_2
    //   655: aload 14
    //   657: aload 12
    //   659: aload 13
    //   661: aload_1
    //   662: invokevirtual 345	com/tencent/beacontbs/upload/d:a	(Ljava/lang/String;[BLcom/tencent/beacontbs/upload/b;)[B
    //   665: astore 10
    //   667: aload 10
    //   669: ifnonnull +839 -> 1508
    //   672: iload_3
    //   673: istore_2
    //   674: aload_1
    //   675: invokevirtual 231	com/tencent/beacontbs/upload/b:a	()I
    //   678: bipush 100
    //   680: if_icmpne +828 -> 1508
    //   683: iload_3
    //   684: istore_2
    //   685: ldc_w 347
    //   688: aload 12
    //   690: invokevirtual 263	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   693: ifne +815 -> 1508
    //   696: iload_3
    //   697: istore_2
    //   698: aload 14
    //   700: ldc_w 347
    //   703: aload 13
    //   705: aload_1
    //   706: invokevirtual 345	com/tencent/beacontbs/upload/d:a	(Ljava/lang/String;[BLcom/tencent/beacontbs/upload/b;)[B
    //   709: astore 10
    //   711: goto +3 -> 714
    //   714: iload_3
    //   715: istore_2
    //   716: aload 10
    //   718: invokestatic 349	com/tencent/beacontbs/upload/h:a	([B)Lcom/tencent/beacontbs/b/a/c;
    //   721: astore 10
    //   723: aload 10
    //   725: ifnull +786 -> 1511
    //   728: iload_3
    //   729: istore_2
    //   730: aload 10
    //   732: getfield 350	com/tencent/beacontbs/b/a/c:jdField_a_of_type_Int	I
    //   735: istore_3
    //   736: iload_3
    //   737: istore_2
    //   738: aload 10
    //   740: getfield 353	com/tencent/beacontbs/b/a/c:jdField_a_of_type_Byte	B
    //   743: istore 5
    //   745: iload 5
    //   747: ifne +9 -> 756
    //   750: iconst_1
    //   751: istore 7
    //   753: goto +6 -> 759
    //   756: iconst_0
    //   757: istore 7
    //   759: iload_3
    //   760: istore_2
    //   761: iload 7
    //   763: istore 9
    //   765: iload 7
    //   767: istore 8
    //   769: ldc_w 355
    //   772: iconst_2
    //   773: anewarray 4	java/lang/Object
    //   776: dup
    //   777: iconst_0
    //   778: aload 10
    //   780: getfield 350	com/tencent/beacontbs/b/a/c:jdField_a_of_type_Int	I
    //   783: invokestatic 155	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   786: aastore
    //   787: dup
    //   788: iconst_1
    //   789: aload 10
    //   791: getfield 353	com/tencent/beacontbs/b/a/c:jdField_a_of_type_Byte	B
    //   794: invokestatic 360	java/lang/Byte:valueOf	(B)Ljava/lang/Byte;
    //   797: aastore
    //   798: invokestatic 97	com/tencent/beacontbs/c/a:b	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   801: goto +3 -> 804
    //   804: aload 10
    //   806: ifnull +711 -> 1517
    //   809: iload_3
    //   810: istore_2
    //   811: iload 7
    //   813: istore 9
    //   815: iload 7
    //   817: istore 8
    //   819: invokestatic 365	com/tencent/beacontbs/a/c:a	()Lcom/tencent/beacontbs/a/c;
    //   822: astore 11
    //   824: aload 11
    //   826: ifnull +128 -> 954
    //   829: iload_3
    //   830: istore_2
    //   831: iload 7
    //   833: istore 9
    //   835: iload 7
    //   837: istore 8
    //   839: aload 10
    //   841: getfield 366	com/tencent/beacontbs/b/a/c:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   844: ifnull +26 -> 870
    //   847: iload_3
    //   848: istore_2
    //   849: iload 7
    //   851: istore 9
    //   853: iload 7
    //   855: istore 8
    //   857: aload 11
    //   859: aload 10
    //   861: getfield 366	com/tencent/beacontbs/b/a/c:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   864: invokevirtual 259	java/lang/String:trim	()Ljava/lang/String;
    //   867: invokevirtual 368	com/tencent/beacontbs/a/c:b	(Ljava/lang/String;)V
    //   870: iload_3
    //   871: istore_2
    //   872: iload 7
    //   874: istore 9
    //   876: iload 7
    //   878: istore 8
    //   880: new 370	java/util/Date
    //   883: dup
    //   884: invokespecial 371	java/util/Date:<init>	()V
    //   887: astore 12
    //   889: iload_3
    //   890: istore_2
    //   891: iload 7
    //   893: istore 9
    //   895: iload 7
    //   897: istore 8
    //   899: aload 11
    //   901: aload 10
    //   903: getfield 374	com/tencent/beacontbs/b/a/c:jdField_a_of_type_Long	J
    //   906: aload 12
    //   908: invokevirtual 378	java/util/Date:getTime	()J
    //   911: lsub
    //   912: invokevirtual 381	com/tencent/beacontbs/a/c:a	(J)V
    //   915: iload_3
    //   916: istore_2
    //   917: iload 7
    //   919: istore 9
    //   921: iload 7
    //   923: istore 8
    //   925: ldc_w 383
    //   928: iconst_2
    //   929: anewarray 4	java/lang/Object
    //   932: dup
    //   933: iconst_0
    //   934: aload 11
    //   936: invokevirtual 386	com/tencent/beacontbs/a/c:f	()Ljava/lang/String;
    //   939: aastore
    //   940: dup
    //   941: iconst_1
    //   942: aload 11
    //   944: invokevirtual 388	com/tencent/beacontbs/a/c:a	()J
    //   947: invokestatic 393	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   950: aastore
    //   951: invokestatic 121	com/tencent/beacontbs/c/a:h	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   954: iload_3
    //   955: istore_2
    //   956: iload 7
    //   958: istore 9
    //   960: iload 7
    //   962: istore 8
    //   964: aload 10
    //   966: getfield 394	com/tencent/beacontbs/b/a/c:jdField_a_of_type_ArrayOfByte	[B
    //   969: astore 11
    //   971: aload 11
    //   973: ifnonnull +26 -> 999
    //   976: iload_3
    //   977: istore_2
    //   978: iload 7
    //   980: istore 9
    //   982: iload 7
    //   984: istore 8
    //   986: ldc_w 396
    //   989: iconst_0
    //   990: anewarray 4	java/lang/Object
    //   993: invokestatic 121	com/tencent/beacontbs/c/a:h	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   996: goto +341 -> 1337
    //   999: iload_3
    //   1000: istore_2
    //   1001: iload 7
    //   1003: istore 9
    //   1005: iload 7
    //   1007: istore 8
    //   1009: aload_0
    //   1010: invokespecial 398	com/tencent/beacontbs/upload/h:a	()Landroid/util/SparseArray;
    //   1013: astore 12
    //   1015: aload 12
    //   1017: ifnull +297 -> 1314
    //   1020: iload_3
    //   1021: istore_2
    //   1022: iload 7
    //   1024: istore 9
    //   1026: iload 7
    //   1028: istore 8
    //   1030: aload 12
    //   1032: invokevirtual 51	android/util/SparseArray:size	()I
    //   1035: ifgt +6 -> 1041
    //   1038: goto +276 -> 1314
    //   1041: iload_3
    //   1042: istore_2
    //   1043: iload 7
    //   1045: istore 9
    //   1047: iload 7
    //   1049: istore 8
    //   1051: aload_1
    //   1052: invokevirtual 231	com/tencent/beacontbs/upload/b:a	()I
    //   1055: istore 5
    //   1057: iload_3
    //   1058: istore_2
    //   1059: iload 7
    //   1061: istore 9
    //   1063: iload 7
    //   1065: istore 8
    //   1067: aload 10
    //   1069: getfield 350	com/tencent/beacontbs/b/a/c:jdField_a_of_type_Int	I
    //   1072: istore 6
    //   1074: iload 6
    //   1076: ifne +26 -> 1102
    //   1079: iload_3
    //   1080: istore_2
    //   1081: iload 7
    //   1083: istore 9
    //   1085: iload 7
    //   1087: istore 8
    //   1089: ldc_w 400
    //   1092: iconst_0
    //   1093: anewarray 4	java/lang/Object
    //   1096: invokestatic 121	com/tencent/beacontbs/c/a:h	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   1099: goto +238 -> 1337
    //   1102: iload 5
    //   1104: iconst_4
    //   1105: if_icmpeq +140 -> 1245
    //   1108: iload 5
    //   1110: bipush 100
    //   1112: if_icmpeq +87 -> 1199
    //   1115: iload 5
    //   1117: bipush 102
    //   1119: if_icmpeq +34 -> 1153
    //   1122: iload_3
    //   1123: istore_2
    //   1124: iload 7
    //   1126: istore 9
    //   1128: iload 7
    //   1130: istore 8
    //   1132: ldc_w 402
    //   1135: iconst_1
    //   1136: anewarray 4	java/lang/Object
    //   1139: dup
    //   1140: iconst_0
    //   1141: iload 5
    //   1143: invokestatic 155	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1146: aastore
    //   1147: invokestatic 157	com/tencent/beacontbs/c/a:c	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   1150: goto +187 -> 1337
    //   1153: iload 6
    //   1155: bipush 103
    //   1157: if_icmpeq +134 -> 1291
    //   1160: iload_3
    //   1161: istore_2
    //   1162: iload 7
    //   1164: istore 9
    //   1166: iload 7
    //   1168: istore 8
    //   1170: ldc_w 404
    //   1173: iconst_2
    //   1174: anewarray 4	java/lang/Object
    //   1177: dup
    //   1178: iconst_0
    //   1179: iload 5
    //   1181: invokestatic 155	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1184: aastore
    //   1185: dup
    //   1186: iconst_1
    //   1187: iload 6
    //   1189: invokestatic 155	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1192: aastore
    //   1193: invokestatic 157	com/tencent/beacontbs/c/a:c	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   1196: goto +141 -> 1337
    //   1199: iload 6
    //   1201: bipush 101
    //   1203: if_icmpeq +88 -> 1291
    //   1206: iload_3
    //   1207: istore_2
    //   1208: iload 7
    //   1210: istore 9
    //   1212: iload 7
    //   1214: istore 8
    //   1216: ldc_w 406
    //   1219: iconst_2
    //   1220: anewarray 4	java/lang/Object
    //   1223: dup
    //   1224: iconst_0
    //   1225: iload 5
    //   1227: invokestatic 155	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1230: aastore
    //   1231: dup
    //   1232: iconst_1
    //   1233: iload 6
    //   1235: invokestatic 155	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1238: aastore
    //   1239: invokestatic 157	com/tencent/beacontbs/c/a:c	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   1242: goto +95 -> 1337
    //   1245: iload 6
    //   1247: bipush 105
    //   1249: if_icmpeq +42 -> 1291
    //   1252: iload_3
    //   1253: istore_2
    //   1254: iload 7
    //   1256: istore 9
    //   1258: iload 7
    //   1260: istore 8
    //   1262: ldc_w 406
    //   1265: iconst_2
    //   1266: anewarray 4	java/lang/Object
    //   1269: dup
    //   1270: iconst_0
    //   1271: iload 5
    //   1273: invokestatic 155	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1276: aastore
    //   1277: dup
    //   1278: iconst_1
    //   1279: iload 6
    //   1281: invokestatic 155	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1284: aastore
    //   1285: invokestatic 157	com/tencent/beacontbs/c/a:c	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   1288: goto +49 -> 1337
    //   1291: iload_3
    //   1292: istore_2
    //   1293: iload 7
    //   1295: istore 9
    //   1297: iload 7
    //   1299: istore 8
    //   1301: aload 12
    //   1303: iload 6
    //   1305: aload 11
    //   1307: invokestatic 408	com/tencent/beacontbs/upload/h:a	(Landroid/util/SparseArray;I[B)Z
    //   1310: pop
    //   1311: goto +26 -> 1337
    //   1314: iload_3
    //   1315: istore_2
    //   1316: iload 7
    //   1318: istore 9
    //   1320: iload 7
    //   1322: istore 8
    //   1324: ldc_w 410
    //   1327: iconst_0
    //   1328: anewarray 4	java/lang/Object
    //   1331: invokestatic 121	com/tencent/beacontbs/c/a:h	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   1334: goto +3 -> 1337
    //   1337: iload_3
    //   1338: istore_2
    //   1339: iload 7
    //   1341: istore 9
    //   1343: iload 7
    //   1345: istore 8
    //   1347: aload_0
    //   1348: iload 4
    //   1350: iload_3
    //   1351: iload 7
    //   1353: aconst_null
    //   1354: invokespecial 271	com/tencent/beacontbs/upload/h:a	(IIZLjava/lang/String;)V
    //   1357: aload_1
    //   1358: iload 7
    //   1360: invokevirtual 239	com/tencent/beacontbs/upload/b:b	(Z)V
    //   1363: return
    //   1364: astore 10
    //   1366: goto +16 -> 1382
    //   1369: astore 10
    //   1371: iconst_0
    //   1372: istore 8
    //   1374: goto +92 -> 1466
    //   1377: astore 10
    //   1379: iconst_0
    //   1380: istore 9
    //   1382: iload 9
    //   1384: istore 8
    //   1386: aload_0
    //   1387: iload 4
    //   1389: iload_2
    //   1390: iconst_0
    //   1391: aload 10
    //   1393: invokevirtual 411	java/lang/Throwable:toString	()Ljava/lang/String;
    //   1396: invokespecial 271	com/tencent/beacontbs/upload/h:a	(IIZLjava/lang/String;)V
    //   1399: iload 9
    //   1401: istore 8
    //   1403: ldc_w 413
    //   1406: iconst_1
    //   1407: anewarray 4	java/lang/Object
    //   1410: dup
    //   1411: iconst_0
    //   1412: aload 10
    //   1414: invokevirtual 411	java/lang/Throwable:toString	()Ljava/lang/String;
    //   1417: aastore
    //   1418: invokestatic 176	com/tencent/beacontbs/c/a:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   1421: iload 9
    //   1423: istore 8
    //   1425: invokestatic 67	com/tencent/beacontbs/a/b/d:a	()Lcom/tencent/beacontbs/a/b/d;
    //   1428: invokevirtual 414	com/tencent/beacontbs/a/b/d:b	()Z
    //   1431: ifeq +26 -> 1457
    //   1434: iload 9
    //   1436: istore 8
    //   1438: aload 10
    //   1440: instanceof 416
    //   1443: ifeq +14 -> 1457
    //   1446: iload 9
    //   1448: istore 8
    //   1450: invokestatic 67	com/tencent/beacontbs/a/b/d:a	()Lcom/tencent/beacontbs/a/b/d;
    //   1453: iconst_0
    //   1454: invokevirtual 418	com/tencent/beacontbs/a/b/d:a	(Z)V
    //   1457: aload_1
    //   1458: iload 9
    //   1460: invokevirtual 239	com/tencent/beacontbs/upload/b:b	(Z)V
    //   1463: return
    //   1464: astore 10
    //   1466: aload_1
    //   1467: iload 8
    //   1469: invokevirtual 239	com/tencent/beacontbs/upload/b:b	(Z)V
    //   1472: aload 10
    //   1474: athrow
    //   1475: ldc_w 420
    //   1478: iconst_0
    //   1479: anewarray 4	java/lang/Object
    //   1482: invokestatic 176	com/tencent/beacontbs/c/a:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   1485: iload 4
    //   1487: iconst_2
    //   1488: if_icmpne +8 -> 1496
    //   1491: aload_1
    //   1492: iconst_0
    //   1493: invokevirtual 239	com/tencent/beacontbs/upload/b:b	(Z)V
    //   1496: aload_0
    //   1497: iload 4
    //   1499: iconst_m1
    //   1500: iconst_0
    //   1501: ldc_w 422
    //   1504: invokespecial 271	com/tencent/beacontbs/upload/h:a	(IIZLjava/lang/String;)V
    //   1507: return
    //   1508: goto -794 -> 714
    //   1511: iconst_0
    //   1512: istore 7
    //   1514: goto -710 -> 804
    //   1517: goto -180 -> 1337
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1520	0	this	h
    //   0	1520	1	paramb	b
    //   436	954	2	i	int
    //   112	1239	3	j	int
    //   4	1494	4	k	int
    //   743	529	5	m	int
    //   1072	232	6	n	int
    //   751	762	7	bool1	boolean
    //   767	701	8	bool2	boolean
    //   763	696	9	bool3	boolean
    //   212	856	10	localObject1	Object
    //   1364	1	10	localThrowable1	Throwable
    //   1369	1	10	localObject2	Object
    //   1377	62	10	localThrowable2	Throwable
    //   1464	9	10	localObject3	Object
    //   229	1077	11	localObject4	Object
    //   109	1193	12	localObject5	Object
    //   138	566	13	arrayOfByte	byte[]
    //   171	528	14	locald	d
    //   255	105	15	localObject6	Object
    // Exception table:
    //   from	to	target	type
    //   769	801	1364	java/lang/Throwable
    //   819	824	1364	java/lang/Throwable
    //   839	847	1364	java/lang/Throwable
    //   857	870	1364	java/lang/Throwable
    //   880	889	1364	java/lang/Throwable
    //   899	915	1364	java/lang/Throwable
    //   925	954	1364	java/lang/Throwable
    //   964	971	1364	java/lang/Throwable
    //   986	996	1364	java/lang/Throwable
    //   1009	1015	1364	java/lang/Throwable
    //   1030	1038	1364	java/lang/Throwable
    //   1051	1057	1364	java/lang/Throwable
    //   1067	1074	1364	java/lang/Throwable
    //   1089	1099	1364	java/lang/Throwable
    //   1132	1150	1364	java/lang/Throwable
    //   1170	1196	1364	java/lang/Throwable
    //   1216	1242	1364	java/lang/Throwable
    //   1262	1288	1364	java/lang/Throwable
    //   1301	1311	1364	java/lang/Throwable
    //   1324	1334	1364	java/lang/Throwable
    //   1347	1357	1364	java/lang/Throwable
    //   437	445	1369	finally
    //   447	465	1369	finally
    //   476	485	1369	finally
    //   487	501	1369	finally
    //   503	552	1369	finally
    //   554	561	1369	finally
    //   563	576	1369	finally
    //   578	591	1369	finally
    //   593	608	1369	finally
    //   610	625	1369	finally
    //   627	641	1369	finally
    //   643	650	1369	finally
    //   655	667	1369	finally
    //   674	683	1369	finally
    //   685	696	1369	finally
    //   698	711	1369	finally
    //   716	723	1369	finally
    //   730	736	1369	finally
    //   738	745	1369	finally
    //   437	445	1377	java/lang/Throwable
    //   447	465	1377	java/lang/Throwable
    //   476	485	1377	java/lang/Throwable
    //   487	501	1377	java/lang/Throwable
    //   503	552	1377	java/lang/Throwable
    //   554	561	1377	java/lang/Throwable
    //   563	576	1377	java/lang/Throwable
    //   578	591	1377	java/lang/Throwable
    //   593	608	1377	java/lang/Throwable
    //   610	625	1377	java/lang/Throwable
    //   627	641	1377	java/lang/Throwable
    //   643	650	1377	java/lang/Throwable
    //   655	667	1377	java/lang/Throwable
    //   674	683	1377	java/lang/Throwable
    //   685	696	1377	java/lang/Throwable
    //   698	711	1377	java/lang/Throwable
    //   716	723	1377	java/lang/Throwable
    //   730	736	1377	java/lang/Throwable
    //   738	745	1377	java/lang/Throwable
    //   769	801	1464	finally
    //   819	824	1464	finally
    //   839	847	1464	finally
    //   857	870	1464	finally
    //   880	889	1464	finally
    //   899	915	1464	finally
    //   925	954	1464	finally
    //   964	971	1464	finally
    //   986	996	1464	finally
    //   1009	1015	1464	finally
    //   1030	1038	1464	finally
    //   1051	1057	1464	finally
    //   1067	1074	1464	finally
    //   1089	1099	1464	finally
    //   1132	1150	1464	finally
    //   1170	1196	1464	finally
    //   1216	1242	1464	finally
    //   1262	1288	1464	finally
    //   1301	1311	1464	finally
    //   1324	1334	1464	finally
    //   1347	1357	1464	finally
    //   1386	1399	1464	finally
    //   1403	1421	1464	finally
    //   1425	1434	1464	finally
    //   1438	1446	1464	finally
    //   1450	1457	1464	finally
  }
  
  public final boolean a()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public final boolean a(a parama)
  {
    if (parama == null) {
      return false;
    }
    try
    {
      if (!this.jdField_a_of_type_JavaUtilList.contains(parama)) {
        this.jdField_a_of_type_JavaUtilList.add(parama);
      }
      return true;
    }
    finally
    {
      parama = finally;
      throw parama;
    }
  }
  
  public final boolean a(e parame)
  {
    if (parame == null) {
      return false;
    }
    try
    {
      this.jdField_a_of_type_AndroidUtilSparseArray.append(101, parame);
      return true;
    }
    finally
    {
      parame = finally;
      throw parame;
    }
  }
  
  public final boolean b()
  {
    try
    {
      boolean bool = com.tencent.beacontbs.a.e.a(this.jdField_a_of_type_AndroidContentContext);
      if (bool) {
        return true;
      }
      bool = this.b;
      return bool;
    }
    finally {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\upload\h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */