package com.tencent.common.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;

public class DBUtils
{
  public static final int DATA_ADD = 1;
  public static final int DATA_CHANGED = 2;
  public static final int DATA_DEL = 0;
  public static final int DBVERSION_NOCONTROL = 1;
  public static final int DB_INSERT_ERROR = -1;
  public static final int DB_NO_SPACE = -2;
  
  private static void a(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, String paramString3, ContentValues paramContentValues)
  {
    paramSQLiteDatabase.beginTransaction();
    try
    {
      paramSQLiteDatabase.execSQL(paramString2);
      if (!TextUtils.isEmpty(paramString3)) {
        paramSQLiteDatabase.execSQL(paramString3);
      }
      if (paramContentValues != null) {
        paramSQLiteDatabase.insert(paramString1, null, paramContentValues);
      }
      paramSQLiteDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      paramSQLiteDatabase.endTransaction();
    }
  }
  
  /* Error */
  private static void a(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, String paramString3, String paramString4, ContentValues paramContentValues)
  {
    // Byte code:
    //   0: new 53	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   7: astore 6
    //   9: aload 6
    //   11: ldc 56
    //   13: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   16: pop
    //   17: aload 6
    //   19: aload_1
    //   20: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: pop
    //   24: ldc 62
    //   26: aload 6
    //   28: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   31: invokestatic 72	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   34: aload_0
    //   35: invokevirtual 28	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   38: aload_0
    //   39: aload_1
    //   40: invokestatic 76	com/tencent/common/utils/DBUtils:existTable	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Z
    //   43: ifeq +15 -> 58
    //   46: ldc 62
    //   48: ldc 78
    //   50: invokestatic 72	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   53: aload_0
    //   54: aload_3
    //   55: invokevirtual 32	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   58: aload_0
    //   59: aload_2
    //   60: invokevirtual 32	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   63: aload 5
    //   65: ifnull +12 -> 77
    //   68: aload_0
    //   69: aload_1
    //   70: aconst_null
    //   71: aload 5
    //   73: invokevirtual 42	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   76: pop2
    //   77: aload 4
    //   79: invokestatic 38	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   82: ifne +9 -> 91
    //   85: aload_0
    //   86: aload 4
    //   88: invokevirtual 32	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   91: aload_0
    //   92: invokevirtual 45	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   95: goto +12 -> 107
    //   98: astore_1
    //   99: goto +13 -> 112
    //   102: astore_1
    //   103: aload_1
    //   104: invokevirtual 81	java/lang/Exception:printStackTrace	()V
    //   107: aload_0
    //   108: invokevirtual 48	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   111: return
    //   112: aload_0
    //   113: invokevirtual 48	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   116: aload_1
    //   117: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	118	0	paramSQLiteDatabase	SQLiteDatabase
    //   0	118	1	paramString1	String
    //   0	118	2	paramString2	String
    //   0	118	3	paramString3	String
    //   0	118	4	paramString4	String
    //   0	118	5	paramContentValues	ContentValues
    //   7	20	6	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   38	58	98	finally
    //   58	63	98	finally
    //   68	77	98	finally
    //   77	91	98	finally
    //   91	95	98	finally
    //   103	107	98	finally
    //   38	58	102	java/lang/Exception
    //   58	63	102	java/lang/Exception
    //   68	77	102	java/lang/Exception
    //   77	91	102	java/lang/Exception
    //   91	95	102	java/lang/Exception
  }
  
  /* Error */
  private static boolean a(SQLiteDatabase paramSQLiteDatabase, String paramString1, String[] paramArrayOfString, String paramString2, String paramString3, String paramString4, String paramString5, ContentValues paramContentValues)
  {
    // Byte code:
    //   0: new 53	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   7: astore 11
    //   9: aload 11
    //   11: ldc 86
    //   13: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   16: pop
    //   17: aload 11
    //   19: aload_1
    //   20: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: pop
    //   24: ldc 62
    //   26: aload 11
    //   28: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   31: invokestatic 72	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   34: aload_0
    //   35: aload_1
    //   36: invokestatic 76	com/tencent/common/utils/DBUtils:existTable	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Z
    //   39: istore 10
    //   41: goto +13 -> 54
    //   44: astore 11
    //   46: aload 11
    //   48: invokevirtual 81	java/lang/Exception:printStackTrace	()V
    //   51: iconst_0
    //   52: istore 10
    //   54: iload 10
    //   56: ifeq +898 -> 954
    //   59: new 53	java/lang/StringBuilder
    //   62: dup
    //   63: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   66: astore 11
    //   68: aload 11
    //   70: ldc 88
    //   72: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   75: pop
    //   76: aload 11
    //   78: aload_1
    //   79: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   82: pop
    //   83: ldc 62
    //   85: aload 11
    //   87: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   90: invokestatic 72	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   93: new 53	java/lang/StringBuilder
    //   96: dup
    //   97: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   100: astore 11
    //   102: aload 11
    //   104: ldc 90
    //   106: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   109: pop
    //   110: aload 11
    //   112: aload_1
    //   113: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   116: pop
    //   117: aload 11
    //   119: ldc 92
    //   121: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   124: pop
    //   125: aload 11
    //   127: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   130: astore 12
    //   132: aconst_null
    //   133: astore 11
    //   135: aconst_null
    //   136: astore 13
    //   138: aload_0
    //   139: aload 12
    //   141: aconst_null
    //   142: invokevirtual 96	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   145: astore 12
    //   147: aload 12
    //   149: astore 13
    //   151: aload 12
    //   153: astore 11
    //   155: new 53	java/lang/StringBuilder
    //   158: dup
    //   159: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   162: astore 14
    //   164: aload 12
    //   166: astore 13
    //   168: aload 12
    //   170: astore 11
    //   172: aload 14
    //   174: ldc 98
    //   176: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   179: pop
    //   180: aload 12
    //   182: astore 13
    //   184: aload 12
    //   186: astore 11
    //   188: aload 14
    //   190: aload 12
    //   192: invokevirtual 101	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   195: pop
    //   196: aload 12
    //   198: astore 13
    //   200: aload 12
    //   202: astore 11
    //   204: ldc 62
    //   206: aload 14
    //   208: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   211: invokestatic 72	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   214: aload 12
    //   216: ifnull +650 -> 866
    //   219: aload 12
    //   221: astore 13
    //   223: aload 12
    //   225: astore 11
    //   227: aload 12
    //   229: invokeinterface 107 1 0
    //   234: ifeq +632 -> 866
    //   237: aload 12
    //   239: astore 13
    //   241: aload 12
    //   243: astore 11
    //   245: ldc 62
    //   247: ldc 109
    //   249: invokestatic 72	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   252: aload 12
    //   254: astore 13
    //   256: aload 12
    //   258: astore 11
    //   260: new 53	java/lang/StringBuilder
    //   263: dup
    //   264: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   267: astore 4
    //   269: aload 12
    //   271: astore 13
    //   273: aload 12
    //   275: astore 11
    //   277: aload 4
    //   279: ldc 98
    //   281: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   284: pop
    //   285: aload 12
    //   287: astore 13
    //   289: aload 12
    //   291: astore 11
    //   293: aload 4
    //   295: aload 12
    //   297: invokevirtual 101	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   300: pop
    //   301: aload 12
    //   303: astore 13
    //   305: aload 12
    //   307: astore 11
    //   309: ldc 62
    //   311: aload 4
    //   313: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   316: invokestatic 72	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   319: aload 12
    //   321: astore 13
    //   323: aload 12
    //   325: astore 11
    //   327: new 53	java/lang/StringBuilder
    //   330: dup
    //   331: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   334: astore 4
    //   336: aload 12
    //   338: astore 13
    //   340: aload 12
    //   342: astore 11
    //   344: aload_2
    //   345: arraylength
    //   346: istore 9
    //   348: iconst_0
    //   349: istore 8
    //   351: iload 8
    //   353: iload 9
    //   355: if_icmpge +132 -> 487
    //   358: aload_2
    //   359: iload 8
    //   361: aaload
    //   362: astore 7
    //   364: aload 12
    //   366: astore 13
    //   368: aload 12
    //   370: astore 11
    //   372: aload 12
    //   374: aload 7
    //   376: invokeinterface 113 2 0
    //   381: iconst_m1
    //   382: if_icmple +584 -> 966
    //   385: aload 12
    //   387: astore 13
    //   389: aload 12
    //   391: astore 11
    //   393: aload 4
    //   395: ldc 115
    //   397: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   400: pop
    //   401: aload 12
    //   403: astore 13
    //   405: aload 12
    //   407: astore 11
    //   409: aload 4
    //   411: aload 7
    //   413: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   416: pop
    //   417: aload 12
    //   419: astore 13
    //   421: aload 12
    //   423: astore 11
    //   425: new 53	java/lang/StringBuilder
    //   428: dup
    //   429: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   432: astore 14
    //   434: aload 12
    //   436: astore 13
    //   438: aload 12
    //   440: astore 11
    //   442: aload 14
    //   444: ldc 117
    //   446: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   449: pop
    //   450: aload 12
    //   452: astore 13
    //   454: aload 12
    //   456: astore 11
    //   458: aload 14
    //   460: aload 7
    //   462: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   465: pop
    //   466: aload 12
    //   468: astore 13
    //   470: aload 12
    //   472: astore 11
    //   474: ldc 62
    //   476: aload 14
    //   478: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   481: invokestatic 72	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   484: goto +482 -> 966
    //   487: aload 12
    //   489: astore 13
    //   491: aload 12
    //   493: astore 11
    //   495: aload 4
    //   497: invokevirtual 121	java/lang/StringBuilder:length	()I
    //   500: istore 8
    //   502: iload 8
    //   504: ifgt +17 -> 521
    //   507: aload 12
    //   509: ifnull +10 -> 519
    //   512: aload 12
    //   514: invokeinterface 124 1 0
    //   519: iconst_0
    //   520: ireturn
    //   521: aload 12
    //   523: astore 13
    //   525: aload 12
    //   527: astore 11
    //   529: aload 4
    //   531: iconst_0
    //   532: iconst_1
    //   533: invokevirtual 128	java/lang/StringBuilder:delete	(II)Ljava/lang/StringBuilder;
    //   536: pop
    //   537: aload 12
    //   539: astore 13
    //   541: aload 12
    //   543: astore 11
    //   545: aload_0
    //   546: invokevirtual 28	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   549: ldc 62
    //   551: ldc -126
    //   553: invokestatic 72	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   556: aload 6
    //   558: invokestatic 38	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   561: ifne +9 -> 570
    //   564: aload_0
    //   565: aload 6
    //   567: invokevirtual 32	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   570: new 53	java/lang/StringBuilder
    //   573: dup
    //   574: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   577: astore_2
    //   578: aload_2
    //   579: ldc -124
    //   581: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   584: pop
    //   585: aload_2
    //   586: aload_1
    //   587: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   590: pop
    //   591: aload_2
    //   592: ldc -122
    //   594: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   597: pop
    //   598: aload_2
    //   599: aload_1
    //   600: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   603: pop
    //   604: aload_2
    //   605: ldc -120
    //   607: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   610: pop
    //   611: aload_0
    //   612: aload_2
    //   613: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   616: invokevirtual 32	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   619: aload_0
    //   620: aload_3
    //   621: invokevirtual 32	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   624: new 53	java/lang/StringBuilder
    //   627: dup
    //   628: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   631: astore_2
    //   632: aload_2
    //   633: ldc -118
    //   635: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   638: pop
    //   639: aload_2
    //   640: aload_1
    //   641: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   644: pop
    //   645: aload_2
    //   646: ldc -116
    //   648: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   651: pop
    //   652: aload_2
    //   653: aload 4
    //   655: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   658: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   661: pop
    //   662: aload_2
    //   663: ldc -114
    //   665: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   668: pop
    //   669: aload_2
    //   670: aload 4
    //   672: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   675: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   678: pop
    //   679: aload_2
    //   680: ldc -112
    //   682: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   685: pop
    //   686: aload_2
    //   687: aload_1
    //   688: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   691: pop
    //   692: aload_2
    //   693: ldc -120
    //   695: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   698: pop
    //   699: aload_0
    //   700: aload_2
    //   701: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   704: invokevirtual 32	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   707: new 53	java/lang/StringBuilder
    //   710: dup
    //   711: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   714: astore_2
    //   715: aload_2
    //   716: ldc -110
    //   718: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   721: pop
    //   722: aload_2
    //   723: aload_1
    //   724: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   727: pop
    //   728: aload_2
    //   729: ldc -120
    //   731: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   734: pop
    //   735: aload_0
    //   736: aload_2
    //   737: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   740: invokevirtual 32	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   743: aload 5
    //   745: invokestatic 38	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   748: ifne +9 -> 757
    //   751: aload_0
    //   752: aload 5
    //   754: invokevirtual 32	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   757: ldc 62
    //   759: ldc -108
    //   761: invokestatic 72	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   764: aload_0
    //   765: invokevirtual 45	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   768: aload 12
    //   770: astore 13
    //   772: aload 12
    //   774: astore 11
    //   776: aload_0
    //   777: invokevirtual 48	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   780: goto +121 -> 901
    //   783: astore_1
    //   784: goto +60 -> 844
    //   787: astore_1
    //   788: new 53	java/lang/StringBuilder
    //   791: dup
    //   792: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   795: astore_2
    //   796: aload_2
    //   797: ldc -106
    //   799: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   802: pop
    //   803: aload_2
    //   804: aload_1
    //   805: invokevirtual 101	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   808: pop
    //   809: ldc 62
    //   811: aload_2
    //   812: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   815: invokestatic 72	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   818: aload 12
    //   820: astore 13
    //   822: aload 12
    //   824: astore 11
    //   826: aload_0
    //   827: invokevirtual 48	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   830: aload 12
    //   832: ifnull +10 -> 842
    //   835: aload 12
    //   837: invokeinterface 124 1 0
    //   842: iconst_0
    //   843: ireturn
    //   844: aload 12
    //   846: astore 13
    //   848: aload 12
    //   850: astore 11
    //   852: aload_0
    //   853: invokevirtual 48	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   856: aload 12
    //   858: astore 13
    //   860: aload 12
    //   862: astore 11
    //   864: aload_1
    //   865: athrow
    //   866: aload 12
    //   868: astore 13
    //   870: aload 12
    //   872: astore 11
    //   874: ldc 62
    //   876: ldc -104
    //   878: invokestatic 72	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   881: aload 12
    //   883: astore 13
    //   885: aload 12
    //   887: astore 11
    //   889: aload_0
    //   890: aload_1
    //   891: aload_3
    //   892: aload 4
    //   894: aload 5
    //   896: aload 7
    //   898: invokestatic 154	com/tencent/common/utils/DBUtils:a	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)V
    //   901: aload 12
    //   903: ifnull +61 -> 964
    //   906: aload 12
    //   908: astore 11
    //   910: goto +21 -> 931
    //   913: astore_0
    //   914: goto +26 -> 940
    //   917: astore_0
    //   918: aload 11
    //   920: astore 13
    //   922: aload_0
    //   923: invokevirtual 81	java/lang/Exception:printStackTrace	()V
    //   926: aload 11
    //   928: ifnull +36 -> 964
    //   931: aload 11
    //   933: invokeinterface 124 1 0
    //   938: iconst_1
    //   939: ireturn
    //   940: aload 13
    //   942: ifnull +10 -> 952
    //   945: aload 13
    //   947: invokeinterface 124 1 0
    //   952: aload_0
    //   953: athrow
    //   954: aload_0
    //   955: aload_1
    //   956: aload_3
    //   957: aload 5
    //   959: aload 7
    //   961: invokestatic 156	com/tencent/common/utils/DBUtils:a	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)V
    //   964: iconst_1
    //   965: ireturn
    //   966: iload 8
    //   968: iconst_1
    //   969: iadd
    //   970: istore 8
    //   972: goto -621 -> 351
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	975	0	paramSQLiteDatabase	SQLiteDatabase
    //   0	975	1	paramString1	String
    //   0	975	2	paramArrayOfString	String[]
    //   0	975	3	paramString2	String
    //   0	975	4	paramString3	String
    //   0	975	5	paramString4	String
    //   0	975	6	paramString5	String
    //   0	975	7	paramContentValues	ContentValues
    //   349	622	8	i	int
    //   346	10	9	j	int
    //   39	16	10	bool	boolean
    //   7	20	11	localStringBuilder1	StringBuilder
    //   44	3	11	localException	Exception
    //   66	866	11	localObject1	Object
    //   130	777	12	localObject2	Object
    //   136	810	13	localObject3	Object
    //   162	315	14	localStringBuilder2	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   34	41	44	java/lang/Exception
    //   549	570	783	finally
    //   570	757	783	finally
    //   757	768	783	finally
    //   788	818	783	finally
    //   549	570	787	android/database/SQLException
    //   570	757	787	android/database/SQLException
    //   757	768	787	android/database/SQLException
    //   138	147	913	finally
    //   155	164	913	finally
    //   172	180	913	finally
    //   188	196	913	finally
    //   204	214	913	finally
    //   227	237	913	finally
    //   245	252	913	finally
    //   260	269	913	finally
    //   277	285	913	finally
    //   293	301	913	finally
    //   309	319	913	finally
    //   327	336	913	finally
    //   344	348	913	finally
    //   372	385	913	finally
    //   393	401	913	finally
    //   409	417	913	finally
    //   425	434	913	finally
    //   442	450	913	finally
    //   458	466	913	finally
    //   474	484	913	finally
    //   495	502	913	finally
    //   529	537	913	finally
    //   545	549	913	finally
    //   776	780	913	finally
    //   826	830	913	finally
    //   852	856	913	finally
    //   864	866	913	finally
    //   874	881	913	finally
    //   889	901	913	finally
    //   922	926	913	finally
    //   138	147	917	java/lang/Exception
    //   155	164	917	java/lang/Exception
    //   172	180	917	java/lang/Exception
    //   188	196	917	java/lang/Exception
    //   204	214	917	java/lang/Exception
    //   227	237	917	java/lang/Exception
    //   245	252	917	java/lang/Exception
    //   260	269	917	java/lang/Exception
    //   277	285	917	java/lang/Exception
    //   293	301	917	java/lang/Exception
    //   309	319	917	java/lang/Exception
    //   327	336	917	java/lang/Exception
    //   344	348	917	java/lang/Exception
    //   372	385	917	java/lang/Exception
    //   393	401	917	java/lang/Exception
    //   409	417	917	java/lang/Exception
    //   425	434	917	java/lang/Exception
    //   442	450	917	java/lang/Exception
    //   458	466	917	java/lang/Exception
    //   474	484	917	java/lang/Exception
    //   495	502	917	java/lang/Exception
    //   529	537	917	java/lang/Exception
    //   545	549	917	java/lang/Exception
    //   776	780	917	java/lang/Exception
    //   826	830	917	java/lang/Exception
    //   852	856	917	java/lang/Exception
    //   864	866	917	java/lang/Exception
    //   874	881	917	java/lang/Exception
    //   889	901	917	java/lang/Exception
  }
  
  /* Error */
  public static int batchInsert(SQLiteDatabase paramSQLiteDatabase, String paramString, java.util.List<ContentValues> paramList)
    throws Exception
  {
    // Byte code:
    //   0: iconst_m1
    //   1: istore_3
    //   2: iload_3
    //   3: istore 5
    //   5: aload_0
    //   6: ifnull +109 -> 115
    //   9: iload_3
    //   10: istore 4
    //   12: aload_0
    //   13: invokevirtual 28	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   16: iload_3
    //   17: istore 4
    //   19: aload_2
    //   20: invokeinterface 164 1 0
    //   25: astore_2
    //   26: iload_3
    //   27: istore 4
    //   29: aload_2
    //   30: invokeinterface 169 1 0
    //   35: ifeq +27 -> 62
    //   38: iload_3
    //   39: istore 4
    //   41: aload_0
    //   42: aload_1
    //   43: ldc -85
    //   45: aload_2
    //   46: invokeinterface 175 1 0
    //   51: checkcast 177	android/content/ContentValues
    //   54: invokevirtual 42	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   57: l2i
    //   58: istore_3
    //   59: goto -33 -> 26
    //   62: iload_3
    //   63: istore 4
    //   65: aload_0
    //   66: invokevirtual 45	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   69: iload_3
    //   70: istore 5
    //   72: aload_0
    //   73: ifnull +42 -> 115
    //   76: aload_0
    //   77: invokevirtual 48	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   80: iload_3
    //   81: ireturn
    //   82: astore_1
    //   83: goto +22 -> 105
    //   86: astore_1
    //   87: aload_1
    //   88: invokevirtual 81	java/lang/Exception:printStackTrace	()V
    //   91: iload 4
    //   93: istore 5
    //   95: aload_0
    //   96: ifnull +19 -> 115
    //   99: iload 4
    //   101: istore_3
    //   102: goto -26 -> 76
    //   105: aload_0
    //   106: ifnull +7 -> 113
    //   109: aload_0
    //   110: invokevirtual 48	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   113: aload_1
    //   114: athrow
    //   115: iload 5
    //   117: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	118	0	paramSQLiteDatabase	SQLiteDatabase
    //   0	118	1	paramString	String
    //   0	118	2	paramList	java.util.List<ContentValues>
    //   1	101	3	i	int
    //   10	90	4	j	int
    //   3	113	5	k	int
    // Exception table:
    //   from	to	target	type
    //   12	16	82	finally
    //   19	26	82	finally
    //   29	38	82	finally
    //   41	59	82	finally
    //   65	69	82	finally
    //   87	91	82	finally
    //   12	16	86	java/lang/Exception
    //   19	26	86	java/lang/Exception
    //   29	38	86	java/lang/Exception
    //   41	59	86	java/lang/Exception
    //   65	69	86	java/lang/Exception
  }
  
  public static void beginTransaction(SQLiteDatabase paramSQLiteDatabase)
    throws Exception
  {
    paramSQLiteDatabase.beginTransaction();
  }
  
  public static void checkUpgrade(SQLiteDatabase paramSQLiteDatabase, String paramString1, String[] paramArrayOfString, String paramString2, String paramString3, String paramString4, String paramString5, ContentValues paramContentValues)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("checkUpgrade: ");
    ((StringBuilder)localObject).append(paramString1);
    FLogger.d("DBUtils", ((StringBuilder)localObject).toString());
    if (paramSQLiteDatabase == null) {
      return;
    }
    localObject = paramString3;
    if (TextUtils.isEmpty(paramString3))
    {
      paramString3 = new StringBuilder();
      paramString3.append("DROP TABLE ");
      paramString3.append(paramString1);
      paramString3.append(";");
      localObject = paramString3.toString();
    }
    if (!a(paramSQLiteDatabase, paramString1, paramArrayOfString, paramString2, (String)localObject, paramString4, paramString5, paramContentValues)) {
      a(paramSQLiteDatabase, paramString1, paramString2, (String)localObject, paramString4, paramContentValues);
    }
  }
  
  public static int clearTable(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, String[] paramArrayOfString)
    throws Exception
  {
    return paramSQLiteDatabase.delete(paramString1, paramString2, paramArrayOfString);
  }
  
  public static void clearTable(SQLiteDatabase paramSQLiteDatabase, String paramString)
    throws Exception
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DELETE FROM ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(";");
    paramSQLiteDatabase.execSQL(localStringBuilder.toString());
  }
  
  public static void clearTable(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2)
    throws Exception
  {
    if (TextUtils.isEmpty(paramString2))
    {
      clearTable(paramSQLiteDatabase, paramString1);
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DELETE FROM ");
    localStringBuilder.append(paramString1);
    localStringBuilder.append(" WHERE ");
    localStringBuilder.append(paramString2);
    localStringBuilder.append(";");
    paramSQLiteDatabase.execSQL(localStringBuilder.toString());
  }
  
  public static int delete(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2)
    throws Exception
  {
    return delete(paramSQLiteDatabase, paramString1, paramString2, null);
  }
  
  public static int delete(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, String[] paramArrayOfString)
    throws Exception
  {
    return paramSQLiteDatabase.delete(paramString1, paramString2, paramArrayOfString);
  }
  
  public static void deleteTable(SQLiteDatabase paramSQLiteDatabase, String paramString)
    throws Exception
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DROP TABLE ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(";");
    paramSQLiteDatabase.execSQL(localStringBuilder.toString());
  }
  
  public static void endTransaction(SQLiteDatabase paramSQLiteDatabase)
    throws Exception
  {
    try
    {
      paramSQLiteDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      paramSQLiteDatabase.endTransaction();
    }
  }
  
  public static void endTransactionOnly(SQLiteDatabase paramSQLiteDatabase)
  {
    try
    {
      paramSQLiteDatabase.endTransaction();
      return;
    }
    catch (Exception paramSQLiteDatabase)
    {
      paramSQLiteDatabase.printStackTrace();
    }
  }
  
  public static void execSQL(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    paramSQLiteDatabase.execSQL(paramString);
  }
  
  public static boolean exist(SQLiteDatabase paramSQLiteDatabase, String paramString)
    throws Exception
  {
    return existTable(paramSQLiteDatabase, paramString);
  }
  
  public static boolean existTable(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    long l = System.currentTimeMillis();
    StringBuilder localStringBuilder = new StringBuilder("select 1 from sqlite_master where type='table' and name='");
    localStringBuilder.append(paramString);
    localStringBuilder.append("';");
    SQLiteDatabase localSQLiteDatabase = null;
    try
    {
      paramSQLiteDatabase = paramSQLiteDatabase.rawQuery(localStringBuilder.toString(), null);
      localSQLiteDatabase = paramSQLiteDatabase;
      boolean bool = paramSQLiteDatabase.moveToNext();
      if (paramSQLiteDatabase != null) {
        paramSQLiteDatabase.close();
      }
      paramSQLiteDatabase = new StringBuilder();
      paramSQLiteDatabase.append(paramString);
      paramSQLiteDatabase.append(" exist: ");
      paramSQLiteDatabase.append(bool);
      paramSQLiteDatabase.append(", used time: ");
      paramSQLiteDatabase.append(System.currentTimeMillis() - l);
      FLogger.d("DBUtils", paramSQLiteDatabase.toString());
      return bool;
    }
    finally
    {
      if (localSQLiteDatabase != null) {
        localSQLiteDatabase.close();
      }
    }
  }
  
  public static int getRowCount(SQLiteDatabase paramSQLiteDatabase, String paramString)
    throws Exception
  {
    return getRowCount(paramSQLiteDatabase, paramString, null);
  }
  
  public static int getRowCount(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2)
    throws Exception
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    try
    {
      StringBuilder localStringBuilder = new StringBuilder("select count(1) from ");
      localObject1 = localObject2;
      localStringBuilder.append(paramString1);
      if (paramString2 != null)
      {
        localObject1 = localObject2;
        paramString1 = new StringBuilder();
        localObject1 = localObject2;
        paramString1.append(" where ");
        localObject1 = localObject2;
        paramString1.append(paramString2);
        localObject1 = localObject2;
        localStringBuilder.append(paramString1.toString());
      }
      localObject1 = localObject2;
      localStringBuilder.append(";");
      localObject1 = localObject2;
      paramSQLiteDatabase = query(paramSQLiteDatabase, localStringBuilder.toString());
      localObject1 = paramSQLiteDatabase;
      boolean bool = paramSQLiteDatabase.moveToFirst();
      int i = 0;
      if (bool)
      {
        localObject1 = paramSQLiteDatabase;
        i = paramSQLiteDatabase.getInt(0);
      }
      if (paramSQLiteDatabase != null) {
        paramSQLiteDatabase.close();
      }
      return i;
    }
    finally
    {
      if (localObject1 != null) {
        ((Cursor)localObject1).close();
      }
    }
  }
  
  public static boolean inTransaction(SQLiteDatabase paramSQLiteDatabase)
    throws Exception
  {
    return paramSQLiteDatabase.inTransaction();
  }
  
  public static int insert(SQLiteDatabase paramSQLiteDatabase, String paramString, ContentValues paramContentValues)
    throws Exception
  {
    return (int)paramSQLiteDatabase.insert(paramString, "Null", paramContentValues);
  }
  
  /* Error */
  public static boolean isColumnExist(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 7
    //   3: iconst_0
    //   4: istore 8
    //   6: iconst_0
    //   7: istore 6
    //   9: aload_0
    //   10: ifnull +220 -> 230
    //   13: aload_1
    //   14: invokestatic 38	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   17: ifne +213 -> 230
    //   20: aload_2
    //   21: invokestatic 38	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   24: ifeq +5 -> 29
    //   27: iconst_0
    //   28: ireturn
    //   29: new 53	java/lang/StringBuilder
    //   32: dup
    //   33: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   36: astore 9
    //   38: aload 9
    //   40: ldc 90
    //   42: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: pop
    //   46: aload 9
    //   48: aload_1
    //   49: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: pop
    //   53: aload 9
    //   55: ldc_w 258
    //   58: invokevirtual 60	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   61: pop
    //   62: aload 9
    //   64: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   67: astore 10
    //   69: aconst_null
    //   70: astore_1
    //   71: aconst_null
    //   72: astore 9
    //   74: aload_0
    //   75: aload 10
    //   77: aconst_null
    //   78: invokevirtual 96	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   81: astore_0
    //   82: iload 6
    //   84: istore 5
    //   86: aload_0
    //   87: ifnull +80 -> 167
    //   90: aload_0
    //   91: astore 9
    //   93: aload_0
    //   94: astore_1
    //   95: aload_0
    //   96: invokeinterface 262 1 0
    //   101: astore 10
    //   103: iload 6
    //   105: istore 5
    //   107: aload 10
    //   109: ifnull +58 -> 167
    //   112: aload_0
    //   113: astore 9
    //   115: aload_0
    //   116: astore_1
    //   117: aload 10
    //   119: arraylength
    //   120: istore 4
    //   122: iconst_0
    //   123: istore_3
    //   124: iload 6
    //   126: istore 5
    //   128: iload_3
    //   129: iload 4
    //   131: if_icmpge +36 -> 167
    //   134: aload_0
    //   135: astore 9
    //   137: aload_0
    //   138: astore_1
    //   139: aload_2
    //   140: aload 10
    //   142: iload_3
    //   143: aaload
    //   144: invokevirtual 268	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   147: istore 5
    //   149: iload 5
    //   151: ifeq +9 -> 160
    //   154: iconst_1
    //   155: istore 5
    //   157: goto +10 -> 167
    //   160: iload_3
    //   161: iconst_1
    //   162: iadd
    //   163: istore_3
    //   164: goto -40 -> 124
    //   167: iload 5
    //   169: istore 6
    //   171: aload_0
    //   172: ifnull +41 -> 213
    //   175: aload_0
    //   176: invokeinterface 124 1 0
    //   181: iload 5
    //   183: ireturn
    //   184: astore_0
    //   185: goto +31 -> 216
    //   188: astore_0
    //   189: aload_1
    //   190: astore 9
    //   192: aload_0
    //   193: invokevirtual 81	java/lang/Exception:printStackTrace	()V
    //   196: iload 8
    //   198: istore 6
    //   200: aload_1
    //   201: ifnull +12 -> 213
    //   204: iload 7
    //   206: istore 5
    //   208: aload_1
    //   209: astore_0
    //   210: goto -35 -> 175
    //   213: iload 6
    //   215: ireturn
    //   216: aload 9
    //   218: ifnull +10 -> 228
    //   221: aload 9
    //   223: invokeinterface 124 1 0
    //   228: aload_0
    //   229: athrow
    //   230: iconst_0
    //   231: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	232	0	paramSQLiteDatabase	SQLiteDatabase
    //   0	232	1	paramString1	String
    //   0	232	2	paramString2	String
    //   123	41	3	i	int
    //   120	12	4	j	int
    //   84	123	5	bool1	boolean
    //   7	207	6	bool2	boolean
    //   1	204	7	bool3	boolean
    //   4	193	8	bool4	boolean
    //   36	186	9	localObject1	Object
    //   67	74	10	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   74	82	184	finally
    //   95	103	184	finally
    //   117	122	184	finally
    //   139	149	184	finally
    //   192	196	184	finally
    //   74	82	188	java/lang/Exception
    //   95	103	188	java/lang/Exception
    //   117	122	188	java/lang/Exception
    //   139	149	188	java/lang/Exception
  }
  
  public static Cursor listAll(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2)
    throws Exception
  {
    return query(paramSQLiteDatabase, false, paramString1, null, null, null, paramString2, null);
  }
  
  public static SQLiteDatabase openDatabase(String paramString)
    throws Exception
  {
    return SQLiteDatabase.openDatabase(paramString, null, 268435456);
  }
  
  public static Cursor query(SQLiteDatabase paramSQLiteDatabase, String paramString)
    throws Exception
  {
    return paramSQLiteDatabase.rawQuery(paramString, null);
  }
  
  public static Cursor query(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2)
    throws Exception
  {
    return query(paramSQLiteDatabase, false, paramString1, paramString2, null, null, null, null);
  }
  
  public static Cursor query(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    return query(paramSQLiteDatabase, false, paramString1, paramString2, null, null, paramString3, null);
  }
  
  public static Cursor query(SQLiteDatabase paramSQLiteDatabase, String paramString1, String[] paramArrayOfString1, String paramString2, String[] paramArrayOfString2, String paramString3)
    throws Exception
  {
    return paramSQLiteDatabase.query(paramString1, paramArrayOfString1, paramString2, paramArrayOfString2, null, null, paramString3);
  }
  
  public static Cursor query(SQLiteDatabase paramSQLiteDatabase, boolean paramBoolean, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
    throws Exception
  {
    return paramSQLiteDatabase.query(paramBoolean, paramString1, new String[] { "*" }, paramString2, null, paramString3, paramString4, paramString5, paramString6);
  }
  
  public static Cursor rawQuery(SQLiteDatabase paramSQLiteDatabase, String paramString, String[] paramArrayOfString)
    throws Exception
  {
    return paramSQLiteDatabase.rawQuery(paramString, paramArrayOfString);
  }
  
  public static void replace(SQLiteDatabase paramSQLiteDatabase, String paramString, ContentValues paramContentValues)
    throws Exception
  {
    paramSQLiteDatabase.replace(paramString, "Null", paramContentValues);
  }
  
  public static int update(SQLiteDatabase paramSQLiteDatabase, String paramString1, ContentValues paramContentValues, String paramString2)
    throws Exception
  {
    return update(paramSQLiteDatabase, paramString1, paramContentValues, paramString2, null);
  }
  
  public static int update(SQLiteDatabase paramSQLiteDatabase, String paramString1, ContentValues paramContentValues, String paramString2, String[] paramArrayOfString)
    throws Exception
  {
    return paramSQLiteDatabase.update(paramString1, paramContentValues, paramString2, paramArrayOfString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\DBUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */