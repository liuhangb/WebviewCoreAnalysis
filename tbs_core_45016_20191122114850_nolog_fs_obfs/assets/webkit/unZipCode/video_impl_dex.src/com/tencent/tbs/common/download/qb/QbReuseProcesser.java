package com.tencent.tbs.common.download.qb;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.stat.TBSStatManager;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class QbReuseProcesser
  implements IFileProcesser
{
  private static final short BYTE_DATA_LEN = 2;
  private static final String CHARSET = "utf-8";
  private static final String COMMENT_MAGIC = "TBS";
  
  /* Error */
  private boolean qbApkVersionIsNew(Context paramContext, File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 6
    //   3: aconst_null
    //   4: astore 4
    //   6: new 27	java/net/URL
    //   9: dup
    //   10: ldc 29
    //   12: invokespecial 32	java/net/URL:<init>	(Ljava/lang/String;)V
    //   15: invokevirtual 36	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   18: checkcast 38	java/net/HttpURLConnection
    //   21: astore 5
    //   23: aload 5
    //   25: sipush 4000
    //   28: invokevirtual 42	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   31: aload 5
    //   33: sipush 4000
    //   36: invokevirtual 45	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   39: aload 5
    //   41: ldc 47
    //   43: invokevirtual 50	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   46: aload 5
    //   48: ldc 52
    //   50: ldc 54
    //   52: invokevirtual 58	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   55: aload 5
    //   57: invokevirtual 62	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   60: astore 4
    //   62: goto +10 -> 72
    //   65: aload 5
    //   67: invokevirtual 65	java/net/HttpURLConnection:getErrorStream	()Ljava/io/InputStream;
    //   70: astore 4
    //   72: aload 5
    //   74: invokevirtual 68	java/net/HttpURLConnection:connect	()V
    //   77: new 70	java/io/BufferedReader
    //   80: dup
    //   81: new 72	java/io/InputStreamReader
    //   84: dup
    //   85: aload 4
    //   87: invokespecial 75	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   90: invokespecial 78	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   93: astore 4
    //   95: new 80	java/lang/StringBuffer
    //   98: dup
    //   99: invokespecial 81	java/lang/StringBuffer:<init>	()V
    //   102: astore 6
    //   104: aload 4
    //   106: invokevirtual 85	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   109: astore 7
    //   111: aload 7
    //   113: ifnull +22 -> 135
    //   116: aload 6
    //   118: aload 7
    //   120: invokevirtual 89	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   123: pop
    //   124: aload 6
    //   126: ldc 91
    //   128: invokevirtual 89	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   131: pop
    //   132: goto -28 -> 104
    //   135: aload 6
    //   137: invokevirtual 94	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   140: astore 4
    //   142: new 96	java/lang/StringBuilder
    //   145: dup
    //   146: invokespecial 97	java/lang/StringBuilder:<init>	()V
    //   149: astore 6
    //   151: aload 6
    //   153: ldc 99
    //   155: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   158: pop
    //   159: aload 6
    //   161: aload 4
    //   163: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   166: pop
    //   167: aload 6
    //   169: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   172: pop
    //   173: new 105	org/json/JSONObject
    //   176: dup
    //   177: aload 4
    //   179: invokespecial 106	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   182: ldc 108
    //   184: invokevirtual 112	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   187: ldc 114
    //   189: invokevirtual 120	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   192: iconst_1
    //   193: aaload
    //   194: ldc 122
    //   196: invokevirtual 120	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   199: astore 4
    //   201: aload 4
    //   203: iconst_0
    //   204: aaload
    //   205: iconst_0
    //   206: aload 4
    //   208: iconst_0
    //   209: aaload
    //   210: ldc 124
    //   212: invokevirtual 128	java/lang/String:lastIndexOf	(Ljava/lang/String;)I
    //   215: invokevirtual 132	java/lang/String:substring	(II)Ljava/lang/String;
    //   218: astore 4
    //   220: new 96	java/lang/StringBuilder
    //   223: dup
    //   224: invokespecial 97	java/lang/StringBuilder:<init>	()V
    //   227: astore 6
    //   229: aload 6
    //   231: ldc -122
    //   233: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   236: pop
    //   237: aload 6
    //   239: aload 4
    //   241: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   244: pop
    //   245: aload 6
    //   247: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   250: pop
    //   251: aload_1
    //   252: invokevirtual 140	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   255: aload_2
    //   256: invokevirtual 145	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   259: iconst_0
    //   260: invokevirtual 151	android/content/pm/PackageManager:getPackageArchiveInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   263: astore_1
    //   264: new 96	java/lang/StringBuilder
    //   267: dup
    //   268: invokespecial 97	java/lang/StringBuilder:<init>	()V
    //   271: astore_2
    //   272: aload_2
    //   273: ldc -103
    //   275: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   278: pop
    //   279: aload_2
    //   280: aload_1
    //   281: getfield 158	android/content/pm/PackageInfo:versionName	Ljava/lang/String;
    //   284: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   287: pop
    //   288: aload_2
    //   289: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   292: pop
    //   293: aload_1
    //   294: getfield 158	android/content/pm/PackageInfo:versionName	Ljava/lang/String;
    //   297: iconst_0
    //   298: aload_1
    //   299: getfield 158	android/content/pm/PackageInfo:versionName	Ljava/lang/String;
    //   302: ldc 124
    //   304: invokevirtual 128	java/lang/String:lastIndexOf	(Ljava/lang/String;)I
    //   307: invokevirtual 132	java/lang/String:substring	(II)Ljava/lang/String;
    //   310: astore_1
    //   311: new 96	java/lang/StringBuilder
    //   314: dup
    //   315: invokespecial 97	java/lang/StringBuilder:<init>	()V
    //   318: astore_2
    //   319: aload_2
    //   320: ldc -96
    //   322: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   325: pop
    //   326: aload_2
    //   327: aload_1
    //   328: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   331: pop
    //   332: aload_2
    //   333: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   336: pop
    //   337: aload 4
    //   339: aload_1
    //   340: invokevirtual 164	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   343: istore_3
    //   344: iload_3
    //   345: ifeq +10 -> 355
    //   348: aload 5
    //   350: invokevirtual 167	java/net/HttpURLConnection:disconnect	()V
    //   353: iconst_1
    //   354: ireturn
    //   355: aload 5
    //   357: invokevirtual 167	java/net/HttpURLConnection:disconnect	()V
    //   360: iconst_0
    //   361: ireturn
    //   362: astore_1
    //   363: goto +35 -> 398
    //   366: astore_2
    //   367: aload 5
    //   369: astore_1
    //   370: goto +15 -> 385
    //   373: astore_1
    //   374: aload 4
    //   376: astore 5
    //   378: goto +20 -> 398
    //   381: astore_2
    //   382: aload 6
    //   384: astore_1
    //   385: aload_1
    //   386: astore 4
    //   388: aload_2
    //   389: invokevirtual 170	java/lang/Exception:printStackTrace	()V
    //   392: aload_1
    //   393: invokevirtual 167	java/net/HttpURLConnection:disconnect	()V
    //   396: iconst_0
    //   397: ireturn
    //   398: aload 5
    //   400: invokevirtual 167	java/net/HttpURLConnection:disconnect	()V
    //   403: aload_1
    //   404: athrow
    //   405: astore 4
    //   407: goto -342 -> 65
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	410	0	this	QbReuseProcesser
    //   0	410	1	paramContext	Context
    //   0	410	2	paramFile	File
    //   343	2	3	bool	boolean
    //   4	383	4	localObject1	Object
    //   405	1	4	localException	Exception
    //   21	378	5	localObject2	Object
    //   1	382	6	localObject3	Object
    //   109	10	7	str	String
    // Exception table:
    //   from	to	target	type
    //   23	55	362	finally
    //   55	62	362	finally
    //   65	72	362	finally
    //   72	104	362	finally
    //   104	111	362	finally
    //   116	132	362	finally
    //   135	344	362	finally
    //   23	55	366	java/lang/Exception
    //   65	72	366	java/lang/Exception
    //   72	104	366	java/lang/Exception
    //   104	111	366	java/lang/Exception
    //   116	132	366	java/lang/Exception
    //   135	344	366	java/lang/Exception
    //   6	23	373	finally
    //   388	392	373	finally
    //   6	23	381	java/lang/Exception
    //   55	62	405	java/lang/Exception
  }
  
  /* Error */
  public static void writeFileComment(File paramFile, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +321 -> 322
    //   4: aload_0
    //   5: invokevirtual 178	java/io/File:exists	()Z
    //   8: ifeq +304 -> 312
    //   11: aload_1
    //   12: invokevirtual 182	java/lang/String:length	()I
    //   15: istore 4
    //   17: iload 4
    //   19: sipush 32767
    //   22: if_icmpgt +256 -> 278
    //   25: aconst_null
    //   26: astore 6
    //   28: aconst_null
    //   29: astore 5
    //   31: new 184	java/io/RandomAccessFile
    //   34: dup
    //   35: aload_0
    //   36: ldc -70
    //   38: invokespecial 188	java/io/RandomAccessFile:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   41: astore_0
    //   42: aload_0
    //   43: aload_0
    //   44: invokevirtual 191	java/io/RandomAccessFile:length	()J
    //   47: ldc2_w 192
    //   50: lsub
    //   51: invokevirtual 197	java/io/RandomAccessFile:seek	(J)V
    //   54: iload 4
    //   56: i2s
    //   57: istore_2
    //   58: iload_2
    //   59: iconst_2
    //   60: iadd
    //   61: iconst_3
    //   62: iadd
    //   63: istore 4
    //   65: iload 4
    //   67: sipush 32767
    //   70: if_icmpgt +137 -> 207
    //   73: iload 4
    //   75: i2s
    //   76: istore_3
    //   77: iconst_2
    //   78: invokestatic 203	java/nio/ByteBuffer:allocate	(I)Ljava/nio/ByteBuffer;
    //   81: astore 5
    //   83: aload 5
    //   85: getstatic 209	java/nio/ByteOrder:LITTLE_ENDIAN	Ljava/nio/ByteOrder;
    //   88: invokevirtual 213	java/nio/ByteBuffer:order	(Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;
    //   91: pop
    //   92: aload 5
    //   94: iload_3
    //   95: invokevirtual 217	java/nio/ByteBuffer:putShort	(S)Ljava/nio/ByteBuffer;
    //   98: pop
    //   99: aload 5
    //   101: invokevirtual 221	java/nio/ByteBuffer:flip	()Ljava/nio/Buffer;
    //   104: pop
    //   105: aload_0
    //   106: aload 5
    //   108: invokevirtual 225	java/nio/ByteBuffer:array	()[B
    //   111: invokevirtual 229	java/io/RandomAccessFile:write	([B)V
    //   114: aload_0
    //   115: aload_1
    //   116: ldc 13
    //   118: invokevirtual 233	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   121: invokevirtual 229	java/io/RandomAccessFile:write	([B)V
    //   124: iconst_2
    //   125: invokestatic 203	java/nio/ByteBuffer:allocate	(I)Ljava/nio/ByteBuffer;
    //   128: astore 5
    //   130: aload 5
    //   132: getstatic 209	java/nio/ByteOrder:LITTLE_ENDIAN	Ljava/nio/ByteOrder;
    //   135: invokevirtual 213	java/nio/ByteBuffer:order	(Ljava/nio/ByteOrder;)Ljava/nio/ByteBuffer;
    //   138: pop
    //   139: aload 5
    //   141: iload_2
    //   142: invokevirtual 217	java/nio/ByteBuffer:putShort	(S)Ljava/nio/ByteBuffer;
    //   145: pop
    //   146: aload 5
    //   148: invokevirtual 221	java/nio/ByteBuffer:flip	()Ljava/nio/Buffer;
    //   151: pop
    //   152: aload_0
    //   153: aload 5
    //   155: invokevirtual 225	java/nio/ByteBuffer:array	()[B
    //   158: invokevirtual 229	java/io/RandomAccessFile:write	([B)V
    //   161: aload_0
    //   162: ldc 16
    //   164: ldc 13
    //   166: invokevirtual 233	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   169: invokevirtual 229	java/io/RandomAccessFile:write	([B)V
    //   172: new 96	java/lang/StringBuilder
    //   175: dup
    //   176: invokespecial 97	java/lang/StringBuilder:<init>	()V
    //   179: astore 5
    //   181: aload 5
    //   183: ldc -21
    //   185: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   188: pop
    //   189: aload 5
    //   191: aload_1
    //   192: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   195: pop
    //   196: aload 5
    //   198: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   201: pop
    //   202: aload_0
    //   203: invokevirtual 238	java/io/RandomAccessFile:close	()V
    //   206: return
    //   207: new 96	java/lang/StringBuilder
    //   210: dup
    //   211: invokespecial 97	java/lang/StringBuilder:<init>	()V
    //   214: astore_1
    //   215: aload_1
    //   216: ldc -16
    //   218: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   221: pop
    //   222: aload_1
    //   223: iload 4
    //   225: invokevirtual 243	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   228: pop
    //   229: new 245	java/lang/IllegalArgumentException
    //   232: dup
    //   233: aload_1
    //   234: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   237: invokespecial 246	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   240: athrow
    //   241: astore_1
    //   242: goto +30 -> 272
    //   245: astore_1
    //   246: goto +14 -> 260
    //   249: astore_1
    //   250: aload 5
    //   252: astore_0
    //   253: goto +19 -> 272
    //   256: astore_1
    //   257: aload 6
    //   259: astore_0
    //   260: aload_0
    //   261: astore 5
    //   263: aload_1
    //   264: invokevirtual 247	java/io/IOException:printStackTrace	()V
    //   267: aload_0
    //   268: invokevirtual 238	java/io/RandomAccessFile:close	()V
    //   271: return
    //   272: aload_0
    //   273: invokevirtual 238	java/io/RandomAccessFile:close	()V
    //   276: aload_1
    //   277: athrow
    //   278: new 96	java/lang/StringBuilder
    //   281: dup
    //   282: invokespecial 97	java/lang/StringBuilder:<init>	()V
    //   285: astore_0
    //   286: aload_0
    //   287: ldc -16
    //   289: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   292: pop
    //   293: aload_0
    //   294: iload 4
    //   296: invokevirtual 243	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   299: pop
    //   300: new 245	java/lang/IllegalArgumentException
    //   303: dup
    //   304: aload_0
    //   305: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   308: invokespecial 246	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   311: athrow
    //   312: new 245	java/lang/IllegalArgumentException
    //   315: dup
    //   316: ldc -7
    //   318: invokespecial 246	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   321: athrow
    //   322: new 251	java/lang/NullPointerException
    //   325: dup
    //   326: ldc -3
    //   328: invokespecial 254	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   331: athrow
    //   332: astore_0
    //   333: return
    //   334: astore_0
    //   335: goto -59 -> 276
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	338	0	paramFile	File
    //   0	338	1	paramString	String
    //   57	85	2	s1	short
    //   76	19	3	s2	short
    //   15	280	4	i	int
    //   29	233	5	localObject1	Object
    //   26	232	6	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   42	54	241	finally
    //   77	202	241	finally
    //   207	241	241	finally
    //   42	54	245	java/io/IOException
    //   77	202	245	java/io/IOException
    //   207	241	245	java/io/IOException
    //   31	42	249	finally
    //   263	267	249	finally
    //   31	42	256	java/io/IOException
    //   202	206	332	java/lang/Exception
    //   267	271	332	java/lang/Exception
    //   272	276	334	java/lang/Exception
  }
  
  public IFileProcesser next()
  {
    return null;
  }
  
  public File process(Context paramContext, File paramFile, String paramString)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("process downloadUrl is ");
    ((StringBuilder)localObject1).append(paramString);
    ((StringBuilder)localObject1).toString();
    try
    {
      if (!Configuration.getInstance(paramContext).reuseQBApkEnabled())
      {
        if (paramString != null)
        {
          localObject1 = Uri.parse(paramString).getQueryParameter("channel");
          paramContext = (Context)localObject1;
          if (localObject1 == null) {
            paramContext = Uri.parse(paramString).getQueryParameter("channel_id");
          }
          paramString = ChannelUtil.getQbChannelIdByApkFile(paramFile.getAbsolutePath());
          if ((paramContext != null) && (paramString != null) && (paramContext.equals(paramString))) {
            return paramFile;
          }
        }
      }
      else if ((paramContext != null) && (paramFile != null) && (paramFile.exists()) && (paramString != null))
      {
        if (!qbApkVersionIsNew(paramContext, paramFile))
        {
          TBSStatManager.getInstance().userBehaviorStatistics("TBSQAIO");
          return null;
        }
        TBSStatManager.getInstance().userBehaviorStatistics("TBSQAIN");
        Object localObject2 = Uri.parse(paramString).getQueryParameter("channel");
        localObject1 = localObject2;
        if (localObject2 == null) {
          localObject1 = Uri.parse(paramString).getQueryParameter("channel_id");
        }
        paramString = new StringBuilder();
        paramString.append("process channelQBWant is ");
        paramString.append((String)localObject1);
        paramString.toString();
        paramString = new StringBuilder();
        paramString.append("{\"channel\":\"");
        paramString.append((String)localObject1);
        paramString.append("\"}");
        writeFileComment(paramFile, paramString.toString());
        paramString = ChannelUtil.getQbChannelIdByApkFile(paramFile.getAbsolutePath());
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("process QbChannel is ");
        ((StringBuilder)localObject2).append(paramString);
        ((StringBuilder)localObject2).toString();
        if (localObject1 != null) {
          TBSStatManager.getInstance().userBehaviorStatistics("TBSQRA");
        } else {
          TBSStatManager.getInstance().userBehaviorStatistics("TBSQRACN");
        }
        localObject2 = new HashMap();
        ((HashMap)localObject2).put("qbmainchannel", paramString);
        ((HashMap)localObject2).put("qbsubchannel", localObject1);
        X5CoreBeaconUploader.getInstance(paramContext).upLoadToBeacon("TBSREUSEQBAPK", (Map)localObject2);
        return paramFile;
      }
    }
    catch (Exception paramContext)
    {
      paramFile = new StringBuilder();
      paramFile.append("error !!!! ");
      paramFile.append(paramContext.getCause());
      paramFile.append(" ");
      paramFile.append(paramContext.getMessage());
      paramFile.toString();
      paramFile = new StringBuilder();
      paramFile.append("error !!!! ");
      paramFile.append(Log.getStackTraceString(paramContext));
      paramFile.toString();
      TBSStatManager.getInstance().userBehaviorStatistics("TBSQNRA");
      return null;
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\QbReuseProcesser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */