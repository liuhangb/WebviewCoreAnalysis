package com.tencent.smtt.webkit;

import android.database.sqlite.SQLiteDatabase;
import com.tencent.tbs.core.webkit.GeolocationPermissions;
import java.util.HashMap;
import java.util.Map;
import org.chromium.base.annotations.UsedByReflection;

public final class SmttPermanentPermissions
{
  private static final String COL_CATEGORY = "category";
  private static final String COL_ID = "id";
  private static final String COL_ORIGIN = "origin";
  private static final String COL_PERMISSION = "permission";
  private static final String SMTT_PERMISSION_TABLE = "SmttPermissions";
  private static final String SQL_CREATE_QBE_TABLE_STATEMENT = "CREATE TABLE SmttPermissions(id INTEGER PRIMARY KEY,category TEXT, origin TEXT, permission INTEGER);";
  private static SmttPermanentPermissions sInstance;
  private SQLiteDatabase mDatabase;
  private Map<String, Map<String, Integer>> mPermissions;
  
  /* Error */
  private SmttPermanentPermissions()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 38	java/lang/Object:<init>	()V
    //   4: aconst_null
    //   5: astore_3
    //   6: aconst_null
    //   7: astore 4
    //   9: aload_0
    //   10: aconst_null
    //   11: putfield 40	com/tencent/smtt/webkit/SmttPermanentPermissions:mPermissions	Ljava/util/Map;
    //   14: aload_0
    //   15: aconst_null
    //   16: putfield 42	com/tencent/smtt/webkit/SmttPermanentPermissions:mDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   19: new 44	java/lang/StringBuilder
    //   22: dup
    //   23: invokespecial 45	java/lang/StringBuilder:<init>	()V
    //   26: astore 5
    //   28: aload 5
    //   30: ldc 47
    //   32: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   35: pop
    //   36: aload 5
    //   38: invokestatic 57	com/tencent/smtt/webkit/ContextHolder:getInstance	()Lcom/tencent/smtt/webkit/ContextHolder;
    //   41: invokevirtual 61	com/tencent/smtt/webkit/ContextHolder:getContext	()Landroid/content/Context;
    //   44: invokevirtual 67	android/content/Context:getPackageName	()Ljava/lang/String;
    //   47: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   50: pop
    //   51: aload 5
    //   53: ldc 69
    //   55: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   58: pop
    //   59: aload_0
    //   60: aload 5
    //   62: invokevirtual 72	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   65: aconst_null
    //   66: invokestatic 78	android/database/sqlite/SQLiteDatabase:openOrCreateDatabase	(Ljava/lang/String;Landroid/database/sqlite/SQLiteDatabase$CursorFactory;)Landroid/database/sqlite/SQLiteDatabase;
    //   69: putfield 42	com/tencent/smtt/webkit/SmttPermanentPermissions:mDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   72: aload_0
    //   73: invokespecial 82	com/tencent/smtt/webkit/SmttPermanentPermissions:isPermissionTableExist	()Z
    //   76: ifne +12 -> 88
    //   79: aload_0
    //   80: getfield 42	com/tencent/smtt/webkit/SmttPermanentPermissions:mDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   83: ldc 23
    //   85: invokevirtual 86	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   88: aload_0
    //   89: new 88	java/util/HashMap
    //   92: dup
    //   93: invokespecial 89	java/util/HashMap:<init>	()V
    //   96: putfield 40	com/tencent/smtt/webkit/SmttPermanentPermissions:mPermissions	Ljava/util/Map;
    //   99: aload_0
    //   100: getfield 42	com/tencent/smtt/webkit/SmttPermanentPermissions:mDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   103: ldc 20
    //   105: aconst_null
    //   106: aconst_null
    //   107: aconst_null
    //   108: aconst_null
    //   109: aconst_null
    //   110: aconst_null
    //   111: invokevirtual 93	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   114: astore 5
    //   116: aload 5
    //   118: astore 4
    //   120: aload 5
    //   122: astore_3
    //   123: aload 5
    //   125: invokeinterface 99 1 0
    //   130: istore_2
    //   131: iload_2
    //   132: ifle +157 -> 289
    //   135: aload 5
    //   137: astore 4
    //   139: aload 5
    //   141: astore_3
    //   142: aload 5
    //   144: invokeinterface 102 1 0
    //   149: pop
    //   150: iconst_0
    //   151: istore_1
    //   152: iload_1
    //   153: iload_2
    //   154: if_icmpge +135 -> 289
    //   157: aload 5
    //   159: astore 4
    //   161: aload 5
    //   163: astore_3
    //   164: aload 5
    //   166: iconst_1
    //   167: invokeinterface 106 2 0
    //   172: astore 6
    //   174: aload 5
    //   176: astore 4
    //   178: aload 5
    //   180: astore_3
    //   181: aload_0
    //   182: getfield 40	com/tencent/smtt/webkit/SmttPermanentPermissions:mPermissions	Ljava/util/Map;
    //   185: aload 6
    //   187: invokeinterface 112 2 0
    //   192: ifne +29 -> 221
    //   195: aload 5
    //   197: astore 4
    //   199: aload 5
    //   201: astore_3
    //   202: aload_0
    //   203: getfield 40	com/tencent/smtt/webkit/SmttPermanentPermissions:mPermissions	Ljava/util/Map;
    //   206: aload 6
    //   208: new 88	java/util/HashMap
    //   211: dup
    //   212: invokespecial 89	java/util/HashMap:<init>	()V
    //   215: invokeinterface 116 3 0
    //   220: pop
    //   221: aload 5
    //   223: astore 4
    //   225: aload 5
    //   227: astore_3
    //   228: aload_0
    //   229: getfield 40	com/tencent/smtt/webkit/SmttPermanentPermissions:mPermissions	Ljava/util/Map;
    //   232: aload 6
    //   234: invokeinterface 120 2 0
    //   239: checkcast 108	java/util/Map
    //   242: aload 5
    //   244: iconst_2
    //   245: invokeinterface 106 2 0
    //   250: aload 5
    //   252: iconst_3
    //   253: invokeinterface 124 2 0
    //   258: invokestatic 130	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   261: invokeinterface 116 3 0
    //   266: pop
    //   267: aload 5
    //   269: astore 4
    //   271: aload 5
    //   273: astore_3
    //   274: aload 5
    //   276: invokeinterface 133 1 0
    //   281: pop
    //   282: iload_1
    //   283: iconst_1
    //   284: iadd
    //   285: istore_1
    //   286: goto -134 -> 152
    //   289: aload 5
    //   291: ifnull +33 -> 324
    //   294: aload 5
    //   296: astore_3
    //   297: goto +21 -> 318
    //   300: astore_3
    //   301: goto +24 -> 325
    //   304: astore 5
    //   306: aload_3
    //   307: astore 4
    //   309: aload 5
    //   311: invokevirtual 136	java/lang/Exception:printStackTrace	()V
    //   314: aload_3
    //   315: ifnull +9 -> 324
    //   318: aload_3
    //   319: invokeinterface 139 1 0
    //   324: return
    //   325: aload 4
    //   327: ifnull +10 -> 337
    //   330: aload 4
    //   332: invokeinterface 139 1 0
    //   337: aload_3
    //   338: athrow
    //   339: astore_3
    //   340: aload_3
    //   341: invokevirtual 136	java/lang/Exception:printStackTrace	()V
    //   344: aload_0
    //   345: aconst_null
    //   346: putfield 42	com/tencent/smtt/webkit/SmttPermanentPermissions:mDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   349: aload_0
    //   350: new 88	java/util/HashMap
    //   353: dup
    //   354: invokespecial 89	java/util/HashMap:<init>	()V
    //   357: putfield 40	com/tencent/smtt/webkit/SmttPermanentPermissions:mPermissions	Ljava/util/Map;
    //   360: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	361	0	this	SmttPermanentPermissions
    //   151	135	1	i	int
    //   130	25	2	j	int
    //   5	292	3	localObject1	Object
    //   300	38	3	localObject2	Object
    //   339	2	3	localException1	Exception
    //   7	324	4	localObject3	Object
    //   26	269	5	localObject4	Object
    //   304	6	5	localException2	Exception
    //   172	61	6	str	String
    // Exception table:
    //   from	to	target	type
    //   99	116	300	finally
    //   123	131	300	finally
    //   142	150	300	finally
    //   164	174	300	finally
    //   181	195	300	finally
    //   202	221	300	finally
    //   228	267	300	finally
    //   274	282	300	finally
    //   309	314	300	finally
    //   99	116	304	java/lang/Exception
    //   123	131	304	java/lang/Exception
    //   142	150	304	java/lang/Exception
    //   164	174	304	java/lang/Exception
    //   181	195	304	java/lang/Exception
    //   202	221	304	java/lang/Exception
    //   228	267	304	java/lang/Exception
    //   274	282	304	java/lang/Exception
    //   19	88	339	java/lang/Exception
  }
  
  @UsedByReflection("X5CoreWizard.java")
  public static SmttPermanentPermissions getInstance()
  {
    try
    {
      SmttPermanentPermissions localSmttPermanentPermissions;
      if ((sInstance != null) && (sInstance.mDatabase != null))
      {
        localSmttPermanentPermissions = sInstance;
      }
      else
      {
        localSmttPermanentPermissions = new SmttPermanentPermissions();
        sInstance = localSmttPermanentPermissions;
      }
      return localSmttPermanentPermissions;
    }
    finally {}
  }
  
  /* Error */
  private boolean isPermissionTableExist()
  {
    // Byte code:
    //   0: new 44	java/lang/StringBuilder
    //   3: dup
    //   4: ldc -107
    //   6: invokespecial 151	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   9: astore 5
    //   11: aload 5
    //   13: ldc 20
    //   15: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   18: pop
    //   19: aload 5
    //   21: ldc -103
    //   23: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   26: pop
    //   27: aconst_null
    //   28: astore 4
    //   30: aconst_null
    //   31: astore_3
    //   32: aload_0
    //   33: getfield 42	com/tencent/smtt/webkit/SmttPermanentPermissions:mDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   36: aload 5
    //   38: invokevirtual 72	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   41: aconst_null
    //   42: invokevirtual 157	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   45: astore 5
    //   47: aload 5
    //   49: astore_3
    //   50: aload 5
    //   52: astore 4
    //   54: aload 5
    //   56: invokeinterface 133 1 0
    //   61: istore_2
    //   62: iload_2
    //   63: istore_1
    //   64: aload 5
    //   66: ifnull +41 -> 107
    //   69: aload 5
    //   71: invokeinterface 139 1 0
    //   76: iload_2
    //   77: ireturn
    //   78: astore 4
    //   80: goto +29 -> 109
    //   83: astore 5
    //   85: aload 4
    //   87: astore_3
    //   88: aload 5
    //   90: invokevirtual 136	java/lang/Exception:printStackTrace	()V
    //   93: aload 4
    //   95: ifnull +10 -> 105
    //   98: aload 4
    //   100: invokeinterface 139 1 0
    //   105: iconst_0
    //   106: istore_1
    //   107: iload_1
    //   108: ireturn
    //   109: aload_3
    //   110: ifnull +9 -> 119
    //   113: aload_3
    //   114: invokeinterface 139 1 0
    //   119: aload 4
    //   121: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	122	0	this	SmttPermanentPermissions
    //   63	45	1	bool1	boolean
    //   61	16	2	bool2	boolean
    //   31	83	3	localObject1	Object
    //   28	25	4	localObject2	Object
    //   78	42	4	localObject3	Object
    //   9	61	5	localObject4	Object
    //   83	6	5	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   32	47	78	finally
    //   54	62	78	finally
    //   88	93	78	finally
    //   32	47	83	java/lang/Exception
    //   54	62	83	java/lang/Exception
  }
  
  public boolean checkPermission(String paramString1, String paramString2)
    throws SmttPermissionException
  {
    if ((this.mPermissions.containsKey(paramString1)) && (((Map)this.mPermissions.get(paramString1)).containsKey(paramString2))) {
      return ((Integer)((Map)this.mPermissions.get(paramString1)).get(paramString2)).intValue() > 0;
    }
    throw new SmttPermissionException(-2);
  }
  
  @UsedByReflection("X5CoreWizard.java")
  public void clearAllPermanentPermission()
  {
    if (!this.mPermissions.isEmpty())
    {
      this.mPermissions.clear();
      this.mDatabase.delete("SmttPermissions", null, null);
    }
    GeolocationPermissions.getInstance().clearAll();
    j.a().a();
  }
  
  public void clearPermanentPermission(String paramString)
  {
    if (this.mPermissions.containsKey(paramString))
    {
      this.mPermissions.remove(paramString);
      this.mDatabase.delete("SmttPermissions", "category=?", new String[] { paramString });
    }
  }
  
  public Map<String, Map<String, Integer>> getAllPermissions()
  {
    return this.mPermissions;
  }
  
  public Map<String, Integer> getPermissions(String paramString)
  {
    Map localMap = (Map)this.mPermissions.get(paramString);
    paramString = localMap;
    if (localMap == null) {
      paramString = new HashMap();
    }
    return paramString;
  }
  
  public long removePermanentPermission(String paramString1, String paramString2)
  {
    if ((this.mPermissions.containsKey(paramString1)) && (((Map)this.mPermissions.get(paramString1)).containsKey(paramString2)))
    {
      ((Map)this.mPermissions.get(paramString1)).remove(paramString2);
      if (((Map)this.mPermissions.get(paramString1)).size() < 1) {
        this.mPermissions.remove(paramString1);
      }
      return this.mDatabase.delete("SmttPermissions", "category=? and origin=?", new String[] { paramString1, paramString2 });
    }
    return 0L;
  }
  
  /* Error */
  public long updatePermanentPermission(String paramString1, String paramString2, boolean paramBoolean)
  {
    // Byte code:
    //   0: new 221	android/content/ContentValues
    //   3: dup
    //   4: invokespecial 222	android/content/ContentValues:<init>	()V
    //   7: astore 12
    //   9: aload 12
    //   11: ldc 8
    //   13: aload_1
    //   14: invokevirtual 225	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   17: aload 12
    //   19: ldc 14
    //   21: aload_2
    //   22: invokevirtual 225	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   25: iload_3
    //   26: ifeq +9 -> 35
    //   29: iconst_1
    //   30: istore 4
    //   32: goto +6 -> 38
    //   35: iconst_m1
    //   36: istore 4
    //   38: aload 12
    //   40: ldc 17
    //   42: iload 4
    //   44: invokestatic 130	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   47: invokevirtual 228	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   50: aconst_null
    //   51: astore 11
    //   53: aconst_null
    //   54: astore 9
    //   56: aload_0
    //   57: getfield 42	com/tencent/smtt/webkit/SmttPermanentPermissions:mDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   60: ldc 20
    //   62: aconst_null
    //   63: ldc -39
    //   65: iconst_2
    //   66: anewarray 203	java/lang/String
    //   69: dup
    //   70: iconst_0
    //   71: aload_1
    //   72: aastore
    //   73: dup
    //   74: iconst_1
    //   75: aload_2
    //   76: aastore
    //   77: aconst_null
    //   78: aconst_null
    //   79: aconst_null
    //   80: invokevirtual 93	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   83: astore 10
    //   85: aload 10
    //   87: invokeinterface 99 1 0
    //   92: ifle +34 -> 126
    //   95: aload_0
    //   96: getfield 42	com/tencent/smtt/webkit/SmttPermanentPermissions:mDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   99: ldc 20
    //   101: aload 12
    //   103: ldc -39
    //   105: iconst_2
    //   106: anewarray 203	java/lang/String
    //   109: dup
    //   110: iconst_0
    //   111: aload_1
    //   112: aastore
    //   113: dup
    //   114: iconst_1
    //   115: aload_2
    //   116: aastore
    //   117: invokevirtual 232	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   120: i2l
    //   121: lstore 5
    //   123: goto +17 -> 140
    //   126: aload_0
    //   127: getfield 42	com/tencent/smtt/webkit/SmttPermanentPermissions:mDatabase	Landroid/database/sqlite/SQLiteDatabase;
    //   130: ldc 20
    //   132: aconst_null
    //   133: aload 12
    //   135: invokevirtual 236	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   138: lstore 5
    //   140: lload 5
    //   142: lstore 7
    //   144: aload 10
    //   146: ifnull +70 -> 216
    //   149: aload 10
    //   151: invokeinterface 139 1 0
    //   156: lload 5
    //   158: lstore 7
    //   160: goto +56 -> 216
    //   163: astore_1
    //   164: aload 10
    //   166: astore 9
    //   168: goto +122 -> 290
    //   171: astore 11
    //   173: goto +17 -> 190
    //   176: astore_1
    //   177: goto +113 -> 290
    //   180: astore 9
    //   182: aload 11
    //   184: astore 10
    //   186: aload 9
    //   188: astore 11
    //   190: aload 10
    //   192: astore 9
    //   194: aload 11
    //   196: invokevirtual 136	java/lang/Exception:printStackTrace	()V
    //   199: aload 10
    //   201: ifnull +10 -> 211
    //   204: aload 10
    //   206: invokeinterface 139 1 0
    //   211: ldc2_w 237
    //   214: lstore 7
    //   216: aload_0
    //   217: getfield 40	com/tencent/smtt/webkit/SmttPermanentPermissions:mPermissions	Ljava/util/Map;
    //   220: aload_1
    //   221: invokeinterface 112 2 0
    //   226: ifne +21 -> 247
    //   229: aload_0
    //   230: getfield 40	com/tencent/smtt/webkit/SmttPermanentPermissions:mPermissions	Ljava/util/Map;
    //   233: aload_1
    //   234: new 88	java/util/HashMap
    //   237: dup
    //   238: invokespecial 89	java/util/HashMap:<init>	()V
    //   241: invokeinterface 116 3 0
    //   246: pop
    //   247: aload_0
    //   248: getfield 40	com/tencent/smtt/webkit/SmttPermanentPermissions:mPermissions	Ljava/util/Map;
    //   251: aload_1
    //   252: invokeinterface 120 2 0
    //   257: checkcast 108	java/util/Map
    //   260: astore_1
    //   261: iload_3
    //   262: ifeq +9 -> 271
    //   265: iconst_1
    //   266: istore 4
    //   268: goto +6 -> 274
    //   271: iconst_m1
    //   272: istore 4
    //   274: aload_1
    //   275: aload_2
    //   276: iload 4
    //   278: invokestatic 130	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   281: invokeinterface 116 3 0
    //   286: pop
    //   287: lload 7
    //   289: lreturn
    //   290: aload 9
    //   292: ifnull +10 -> 302
    //   295: aload 9
    //   297: invokeinterface 139 1 0
    //   302: aload_1
    //   303: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	304	0	this	SmttPermanentPermissions
    //   0	304	1	paramString1	String
    //   0	304	2	paramString2	String
    //   0	304	3	paramBoolean	boolean
    //   30	247	4	i	int
    //   121	36	5	l1	long
    //   142	146	7	l2	long
    //   54	113	9	localObject1	Object
    //   180	7	9	localException1	Exception
    //   192	104	9	localObject2	Object
    //   83	122	10	localObject3	Object
    //   51	1	11	localObject4	Object
    //   171	12	11	localException2	Exception
    //   188	7	11	localObject5	Object
    //   7	127	12	localContentValues	android.content.ContentValues
    // Exception table:
    //   from	to	target	type
    //   85	123	163	finally
    //   126	140	163	finally
    //   85	123	171	java/lang/Exception
    //   126	140	171	java/lang/Exception
    //   56	85	176	finally
    //   194	199	176	finally
    //   56	85	180	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\SmttPermanentPermissions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */