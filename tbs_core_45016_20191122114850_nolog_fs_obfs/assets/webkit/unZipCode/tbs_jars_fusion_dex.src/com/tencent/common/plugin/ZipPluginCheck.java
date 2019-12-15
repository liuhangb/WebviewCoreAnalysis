package com.tencent.common.plugin;

import android.text.TextUtils;
import com.tencent.common.utils.ByteUtils;
import com.tencent.common.utils.Md5Utils;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class ZipPluginCheck
{
  public static final String PLUGIN_ID_FILE = "_plugins.dat";
  public static final int PLUGIN_TYPE_DOC = 9;
  public static final int PLUGIN_TYPE_DOCX = 5;
  public static final int PLUGIN_TYPE_PDF = 3;
  public static final int PLUGIN_TYPE_PPT = 4;
  public static final int PLUGIN_TYPE_PPTX = 6;
  public static final byte PLUGIN_TYPE_QVOD = 2;
  public static final byte PLUGIN_TYPE_UNKNOWN = 0;
  public static final byte PLUGIN_TYPE_WONDER = 1;
  public static final int PLUGIN_TYPE_XLS = 8;
  public static final int PLUGIN_TYPE_XLSDOC = 10;
  public static final int PLUGIN_TYPE_XLSX = 7;
  public static final String PLUGIN_UI_ID = "6401";
  public static String PLUING_CHECK_FILE = "check_file_";
  static ZipPluginCheck jdField_a_of_type_ComTencentCommonPluginZipPluginCheck = null;
  static Map<String, String> jdField_a_of_type_JavaUtilMap = new HashMap();
  static String[] jdField_a_of_type_ArrayOfJavaLangString = new String[15];
  private final int jdField_a_of_type_Int = 11;
  private final int b = 12;
  private final int c = 13;
  private final int d = 14;
  
  public ZipPluginCheck()
  {
    String[] arrayOfString = jdField_a_of_type_ArrayOfJavaLangString;
    arrayOfString[1] = "wonder";
    arrayOfString[2] = "qvod";
    arrayOfString[3] = "pdf";
    arrayOfString[4] = "ppt";
    arrayOfString[5] = "docx";
    arrayOfString[6] = "pptx";
    arrayOfString[7] = "xlsx";
    arrayOfString[8] = "xls";
    arrayOfString[9] = "doc";
    arrayOfString[10] = "xlsdoc";
    arrayOfString[11] = "epub";
    arrayOfString[12] = "chm";
    arrayOfString[13] = "txt";
    arrayOfString[14] = "menu";
  }
  
  /* Error */
  public static int checkLocalPluginNotModified(File paramFile, String paramString)
  {
    // Byte code:
    //   0: new 109	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 110	java/lang/StringBuilder:<init>	()V
    //   7: astore 6
    //   9: aload 6
    //   11: aload_1
    //   12: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   15: pop
    //   16: aload 6
    //   18: ldc 11
    //   20: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: pop
    //   24: new 116	java/io/File
    //   27: dup
    //   28: aload_0
    //   29: aload 6
    //   31: invokevirtual 120	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   34: invokespecial 123	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   37: astore_0
    //   38: aload_0
    //   39: invokevirtual 127	java/io/File:exists	()Z
    //   42: istore 5
    //   44: iconst_2
    //   45: istore_2
    //   46: iload 5
    //   48: ifne +5 -> 53
    //   51: iconst_2
    //   52: ireturn
    //   53: new 129	java/util/Properties
    //   56: dup
    //   57: invokespecial 130	java/util/Properties:<init>	()V
    //   60: astore 9
    //   62: aconst_null
    //   63: astore 6
    //   65: new 132	java/io/FileInputStream
    //   68: dup
    //   69: aload_0
    //   70: invokespecial 135	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   73: astore 7
    //   75: new 137	java/io/BufferedInputStream
    //   78: dup
    //   79: aload 7
    //   81: invokespecial 140	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   84: astore_0
    //   85: aload 9
    //   87: aload_0
    //   88: invokevirtual 143	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   91: aload 9
    //   93: ldc -111
    //   95: invokevirtual 149	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   98: iconst_0
    //   99: invokestatic 155	com/tencent/common/utils/StringUtils:parseInt	(Ljava/lang/String;I)I
    //   102: istore_3
    //   103: aload 7
    //   105: invokevirtual 160	java/io/InputStream:close	()V
    //   108: aload_0
    //   109: invokevirtual 161	java/io/BufferedInputStream:close	()V
    //   112: goto +8 -> 120
    //   115: astore_0
    //   116: aload_0
    //   117: invokevirtual 164	java/lang/Exception:printStackTrace	()V
    //   120: iconst_0
    //   121: istore_2
    //   122: goto +93 -> 215
    //   125: astore_1
    //   126: aload 7
    //   128: astore 6
    //   130: goto +217 -> 347
    //   133: astore 6
    //   135: goto +17 -> 152
    //   138: astore_1
    //   139: aconst_null
    //   140: astore_0
    //   141: aload 7
    //   143: astore 6
    //   145: goto +202 -> 347
    //   148: astore 6
    //   150: aconst_null
    //   151: astore_0
    //   152: aload 7
    //   154: astore 8
    //   156: aload 6
    //   158: astore 7
    //   160: aload 8
    //   162: astore 6
    //   164: goto +16 -> 180
    //   167: astore_1
    //   168: aconst_null
    //   169: astore_0
    //   170: aload_0
    //   171: astore 6
    //   173: goto +174 -> 347
    //   176: astore 7
    //   178: aconst_null
    //   179: astore_0
    //   180: aload 7
    //   182: invokevirtual 165	java/io/IOException:printStackTrace	()V
    //   185: aload 6
    //   187: ifnull +11 -> 198
    //   190: aload 6
    //   192: invokevirtual 160	java/io/InputStream:close	()V
    //   195: goto +3 -> 198
    //   198: aload_0
    //   199: ifnull +14 -> 213
    //   202: aload_0
    //   203: invokevirtual 161	java/io/BufferedInputStream:close	()V
    //   206: goto +7 -> 213
    //   209: aload_0
    //   210: invokevirtual 164	java/lang/Exception:printStackTrace	()V
    //   213: iconst_0
    //   214: istore_3
    //   215: iload_2
    //   216: ifeq +5 -> 221
    //   219: iload_2
    //   220: ireturn
    //   221: iconst_0
    //   222: istore 4
    //   224: iload 4
    //   226: iload_3
    //   227: if_icmpge +117 -> 344
    //   230: new 109	java/lang/StringBuilder
    //   233: dup
    //   234: invokespecial 110	java/lang/StringBuilder:<init>	()V
    //   237: astore_0
    //   238: aload_0
    //   239: getstatic 167	com/tencent/common/plugin/ZipPluginCheck:PLUING_CHECK_FILE	Ljava/lang/String;
    //   242: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   245: pop
    //   246: aload_0
    //   247: iload 4
    //   249: invokevirtual 170	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   252: pop
    //   253: aload 9
    //   255: aload_0
    //   256: invokevirtual 120	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   259: invokevirtual 149	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   262: astore_0
    //   263: aload_0
    //   264: invokestatic 176	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   267: ifeq +5 -> 272
    //   270: iconst_1
    //   271: ireturn
    //   272: new 116	java/io/File
    //   275: dup
    //   276: aload_0
    //   277: invokespecial 179	java/io/File:<init>	(Ljava/lang/String;)V
    //   280: astore 6
    //   282: aload 6
    //   284: invokevirtual 182	java/io/File:getName	()Ljava/lang/String;
    //   287: invokestatic 176	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   290: ifeq +5 -> 295
    //   293: iconst_1
    //   294: ireturn
    //   295: aload 9
    //   297: aload 6
    //   299: invokevirtual 182	java/io/File:getName	()Ljava/lang/String;
    //   302: invokevirtual 149	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   305: astore_0
    //   306: aload 6
    //   308: aload_1
    //   309: invokestatic 186	com/tencent/common/plugin/ZipPluginCheck:genMd5	(Ljava/io/File;Ljava/lang/String;)Ljava/lang/String;
    //   312: astore 6
    //   314: aload 6
    //   316: ifnull +26 -> 342
    //   319: aload 6
    //   321: aload_0
    //   322: invokevirtual 190	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   325: ifne +6 -> 331
    //   328: goto +14 -> 342
    //   331: iload 4
    //   333: iconst_1
    //   334: iadd
    //   335: istore 4
    //   337: iconst_0
    //   338: istore_2
    //   339: goto -115 -> 224
    //   342: iconst_1
    //   343: istore_2
    //   344: iload_2
    //   345: ireturn
    //   346: astore_1
    //   347: aload 6
    //   349: ifnull +11 -> 360
    //   352: aload 6
    //   354: invokevirtual 160	java/io/InputStream:close	()V
    //   357: goto +3 -> 360
    //   360: aload_0
    //   361: ifnull +14 -> 375
    //   364: aload_0
    //   365: invokevirtual 161	java/io/BufferedInputStream:close	()V
    //   368: goto +7 -> 375
    //   371: aload_0
    //   372: invokevirtual 164	java/lang/Exception:printStackTrace	()V
    //   375: aload_1
    //   376: athrow
    //   377: astore_0
    //   378: goto -169 -> 209
    //   381: astore_0
    //   382: goto -11 -> 371
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	385	0	paramFile	File
    //   0	385	1	paramString	String
    //   45	300	2	i	int
    //   102	126	3	j	int
    //   222	114	4	k	int
    //   42	5	5	bool	boolean
    //   7	122	6	localObject1	Object
    //   133	1	6	localIOException1	IOException
    //   143	1	6	localObject2	Object
    //   148	9	6	localIOException2	IOException
    //   162	191	6	localObject3	Object
    //   73	86	7	localObject4	Object
    //   176	5	7	localIOException3	IOException
    //   154	7	8	localObject5	Object
    //   60	236	9	localProperties	java.util.Properties
    // Exception table:
    //   from	to	target	type
    //   103	112	115	java/lang/Exception
    //   85	103	125	finally
    //   85	103	133	java/io/IOException
    //   75	85	138	finally
    //   75	85	148	java/io/IOException
    //   65	75	167	finally
    //   65	75	176	java/io/IOException
    //   180	185	346	finally
    //   190	195	377	java/lang/Exception
    //   202	206	377	java/lang/Exception
    //   352	357	381	java/lang/Exception
    //   364	368	381	java/lang/Exception
  }
  
  /* Error */
  public static boolean checkPluginNotDelete(File paramFile)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +275 -> 276
    //   4: aload_0
    //   5: invokevirtual 127	java/io/File:exists	()Z
    //   8: ifne +5 -> 13
    //   11: iconst_0
    //   12: ireturn
    //   13: new 129	java/util/Properties
    //   16: dup
    //   17: invokespecial 130	java/util/Properties:<init>	()V
    //   20: astore 6
    //   22: aconst_null
    //   23: astore 5
    //   25: new 132	java/io/FileInputStream
    //   28: dup
    //   29: aload_0
    //   30: invokespecial 135	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   33: astore_3
    //   34: new 137	java/io/BufferedInputStream
    //   37: dup
    //   38: aload_3
    //   39: invokespecial 140	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   42: astore 5
    //   44: aload 6
    //   46: aload 5
    //   48: invokevirtual 143	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   51: aload 6
    //   53: ldc -111
    //   55: invokevirtual 149	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   58: iconst_0
    //   59: invokestatic 155	com/tencent/common/utils/StringUtils:parseInt	(Ljava/lang/String;I)I
    //   62: istore_1
    //   63: aload_3
    //   64: invokevirtual 160	java/io/InputStream:close	()V
    //   67: aload 5
    //   69: invokevirtual 161	java/io/BufferedInputStream:close	()V
    //   72: goto +99 -> 171
    //   75: astore_0
    //   76: aload_0
    //   77: invokevirtual 164	java/lang/Exception:printStackTrace	()V
    //   80: goto +91 -> 171
    //   83: astore_0
    //   84: aload_3
    //   85: astore 4
    //   87: aload 5
    //   89: astore_3
    //   90: goto +156 -> 246
    //   93: astore_0
    //   94: aload 5
    //   96: astore 4
    //   98: goto +20 -> 118
    //   101: astore_0
    //   102: aconst_null
    //   103: astore 5
    //   105: aload_3
    //   106: astore 4
    //   108: aload 5
    //   110: astore_3
    //   111: goto +135 -> 246
    //   114: astore_0
    //   115: aconst_null
    //   116: astore 4
    //   118: goto +19 -> 137
    //   121: astore_0
    //   122: aconst_null
    //   123: astore_3
    //   124: aload_3
    //   125: astore 4
    //   127: goto +119 -> 246
    //   130: astore_0
    //   131: aconst_null
    //   132: astore 4
    //   134: aload 5
    //   136: astore_3
    //   137: aload_0
    //   138: invokevirtual 165	java/io/IOException:printStackTrace	()V
    //   141: aload_3
    //   142: ifnull +10 -> 152
    //   145: aload_3
    //   146: invokevirtual 160	java/io/InputStream:close	()V
    //   149: goto +3 -> 152
    //   152: aload 4
    //   154: ifnull +15 -> 169
    //   157: aload 4
    //   159: invokevirtual 161	java/io/BufferedInputStream:close	()V
    //   162: goto +7 -> 169
    //   165: aload_0
    //   166: invokevirtual 164	java/lang/Exception:printStackTrace	()V
    //   169: iconst_0
    //   170: istore_1
    //   171: iconst_0
    //   172: istore_2
    //   173: iload_2
    //   174: iload_1
    //   175: if_icmpge +56 -> 231
    //   178: new 109	java/lang/StringBuilder
    //   181: dup
    //   182: invokespecial 110	java/lang/StringBuilder:<init>	()V
    //   185: astore_0
    //   186: aload_0
    //   187: getstatic 167	com/tencent/common/plugin/ZipPluginCheck:PLUING_CHECK_FILE	Ljava/lang/String;
    //   190: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   193: pop
    //   194: aload_0
    //   195: iload_2
    //   196: invokevirtual 170	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   199: pop
    //   200: new 116	java/io/File
    //   203: dup
    //   204: aload 6
    //   206: aload_0
    //   207: invokevirtual 120	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   210: invokevirtual 149	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   213: invokespecial 179	java/io/File:<init>	(Ljava/lang/String;)V
    //   216: invokevirtual 127	java/io/File:exists	()Z
    //   219: ifne +5 -> 224
    //   222: iconst_0
    //   223: ireturn
    //   224: iload_2
    //   225: iconst_1
    //   226: iadd
    //   227: istore_2
    //   228: goto -55 -> 173
    //   231: iconst_1
    //   232: ireturn
    //   233: astore 5
    //   235: aload_3
    //   236: astore_0
    //   237: aload 4
    //   239: astore_3
    //   240: aload_0
    //   241: astore 4
    //   243: aload 5
    //   245: astore_0
    //   246: aload 4
    //   248: ifnull +11 -> 259
    //   251: aload 4
    //   253: invokevirtual 160	java/io/InputStream:close	()V
    //   256: goto +3 -> 259
    //   259: aload_3
    //   260: ifnull +14 -> 274
    //   263: aload_3
    //   264: invokevirtual 161	java/io/BufferedInputStream:close	()V
    //   267: goto +7 -> 274
    //   270: aload_3
    //   271: invokevirtual 164	java/lang/Exception:printStackTrace	()V
    //   274: aload_0
    //   275: athrow
    //   276: iconst_0
    //   277: ireturn
    //   278: astore_0
    //   279: goto -114 -> 165
    //   282: astore_3
    //   283: goto -13 -> 270
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	286	0	paramFile	File
    //   62	114	1	i	int
    //   172	56	2	j	int
    //   33	238	3	localObject1	Object
    //   282	1	3	localException	Exception
    //   85	167	4	localObject2	Object
    //   23	112	5	localBufferedInputStream	BufferedInputStream
    //   233	11	5	localObject3	Object
    //   20	185	6	localProperties	java.util.Properties
    // Exception table:
    //   from	to	target	type
    //   63	72	75	java/lang/Exception
    //   44	63	83	finally
    //   44	63	93	java/io/IOException
    //   34	44	101	finally
    //   34	44	114	java/io/IOException
    //   25	34	121	finally
    //   25	34	130	java/io/IOException
    //   137	141	233	finally
    //   145	149	278	java/lang/Exception
    //   157	162	278	java/lang/Exception
    //   251	256	282	java/lang/Exception
    //   263	267	282	java/lang/Exception
  }
  
  /* Error */
  public static boolean genCheckList(File paramFile, int paramInt, String paramString, File[] paramArrayOfFile)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 6
    //   3: aload_3
    //   4: ifnonnull +5 -> 9
    //   7: iconst_0
    //   8: ireturn
    //   9: new 129	java/util/Properties
    //   12: dup
    //   13: invokespecial 130	java/util/Properties:<init>	()V
    //   16: astore 10
    //   18: new 109	java/lang/StringBuilder
    //   21: dup
    //   22: invokespecial 110	java/lang/StringBuilder:<init>	()V
    //   25: astore 9
    //   27: iconst_0
    //   28: istore 4
    //   30: iload 4
    //   32: aload_3
    //   33: arraylength
    //   34: if_icmpge +150 -> 184
    //   37: aload_3
    //   38: iload 4
    //   40: aaload
    //   41: astore 11
    //   43: aload 11
    //   45: invokevirtual 182	java/io/File:getName	()Ljava/lang/String;
    //   48: astore 12
    //   50: aload 11
    //   52: aload_2
    //   53: invokestatic 186	com/tencent/common/plugin/ZipPluginCheck:genMd5	(Ljava/io/File;Ljava/lang/String;)Ljava/lang/String;
    //   56: astore 8
    //   58: aload 8
    //   60: astore 7
    //   62: aload 8
    //   64: ifnonnull +7 -> 71
    //   67: ldc -60
    //   69: astore 7
    //   71: new 109	java/lang/StringBuilder
    //   74: dup
    //   75: invokespecial 110	java/lang/StringBuilder:<init>	()V
    //   78: astore 8
    //   80: aload 8
    //   82: aload 12
    //   84: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: pop
    //   88: aload 8
    //   90: ldc -58
    //   92: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: pop
    //   96: aload 8
    //   98: aload 7
    //   100: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   103: pop
    //   104: aload 8
    //   106: ldc -56
    //   108: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: pop
    //   112: aload 9
    //   114: aload 8
    //   116: invokevirtual 120	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   119: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   122: pop
    //   123: aload 10
    //   125: aload 12
    //   127: aload 7
    //   129: invokevirtual 204	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   132: pop
    //   133: new 109	java/lang/StringBuilder
    //   136: dup
    //   137: invokespecial 110	java/lang/StringBuilder:<init>	()V
    //   140: astore 7
    //   142: aload 7
    //   144: getstatic 167	com/tencent/common/plugin/ZipPluginCheck:PLUING_CHECK_FILE	Ljava/lang/String;
    //   147: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   150: pop
    //   151: aload 7
    //   153: iload 4
    //   155: invokevirtual 170	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   158: pop
    //   159: aload 10
    //   161: aload 7
    //   163: invokevirtual 120	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   166: aload 11
    //   168: invokevirtual 207	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   171: invokevirtual 204	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   174: pop
    //   175: iload 4
    //   177: iconst_1
    //   178: iadd
    //   179: istore 4
    //   181: goto -151 -> 30
    //   184: new 109	java/lang/StringBuilder
    //   187: dup
    //   188: invokespecial 110	java/lang/StringBuilder:<init>	()V
    //   191: astore 7
    //   193: aload 7
    //   195: aload_2
    //   196: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   199: pop
    //   200: aload 7
    //   202: ldc 11
    //   204: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   207: pop
    //   208: new 116	java/io/File
    //   211: dup
    //   212: aload_0
    //   213: aload 7
    //   215: invokevirtual 120	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   218: invokespecial 123	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   221: invokevirtual 207	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   224: astore 11
    //   226: aconst_null
    //   227: astore 8
    //   229: aconst_null
    //   230: astore_0
    //   231: new 209	java/io/FileOutputStream
    //   234: dup
    //   235: aload 11
    //   237: invokespecial 210	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   240: astore 7
    //   242: aload 10
    //   244: ldc -111
    //   246: aload_3
    //   247: arraylength
    //   248: invokestatic 214	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   251: invokevirtual 204	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   254: pop
    //   255: new 109	java/lang/StringBuilder
    //   258: dup
    //   259: invokespecial 110	java/lang/StringBuilder:<init>	()V
    //   262: astore_0
    //   263: aload_0
    //   264: ldc -40
    //   266: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   269: pop
    //   270: aload_0
    //   271: aload_3
    //   272: arraylength
    //   273: invokevirtual 170	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   276: pop
    //   277: aload_0
    //   278: ldc -56
    //   280: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   283: pop
    //   284: aload 9
    //   286: aload_0
    //   287: invokevirtual 120	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   290: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   293: pop
    //   294: new 109	java/lang/StringBuilder
    //   297: dup
    //   298: invokespecial 110	java/lang/StringBuilder:<init>	()V
    //   301: astore_0
    //   302: aload_0
    //   303: iload_1
    //   304: invokevirtual 170	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   307: pop
    //   308: aload_0
    //   309: ldc -60
    //   311: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   314: pop
    //   315: aload 10
    //   317: ldc -38
    //   319: aload_0
    //   320: invokevirtual 120	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   323: invokevirtual 204	java/util/Properties:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;
    //   326: pop
    //   327: new 109	java/lang/StringBuilder
    //   330: dup
    //   331: invokespecial 110	java/lang/StringBuilder:<init>	()V
    //   334: astore_0
    //   335: aload_0
    //   336: ldc -36
    //   338: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   341: pop
    //   342: aload_0
    //   343: iload_1
    //   344: invokevirtual 170	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   347: pop
    //   348: aload_0
    //   349: ldc -56
    //   351: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   354: pop
    //   355: aload 9
    //   357: aload_0
    //   358: invokevirtual 120	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   361: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   364: pop
    //   365: aload 10
    //   367: aload 7
    //   369: ldc -34
    //   371: invokevirtual 226	java/util/Properties:save	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   374: new 228	com/tencent/common/utils/LinuxToolsJni
    //   377: dup
    //   378: invokespecial 229	com/tencent/common/utils/LinuxToolsJni:<init>	()V
    //   381: astore_0
    //   382: getstatic 233	com/tencent/common/utils/LinuxToolsJni:gJniloaded	Z
    //   385: iconst_1
    //   386: if_icmpne +35 -> 421
    //   389: getstatic 238	com/tencent/common/plugin/QBPluginServiceImpl:mFileMode	Ljava/lang/String;
    //   392: ldc -16
    //   394: invokevirtual 244	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   397: ifeq +15 -> 412
    //   400: aload_0
    //   401: aload 11
    //   403: ldc -10
    //   405: invokevirtual 250	com/tencent/common/utils/LinuxToolsJni:Chmod	(Ljava/lang/String;Ljava/lang/String;)I
    //   408: pop
    //   409: goto +12 -> 421
    //   412: aload_0
    //   413: aload 11
    //   415: ldc -4
    //   417: invokevirtual 250	com/tencent/common/utils/LinuxToolsJni:Chmod	(Ljava/lang/String;Ljava/lang/String;)I
    //   420: pop
    //   421: aload 7
    //   423: invokevirtual 253	java/io/FileOutputStream:close	()V
    //   426: goto +8 -> 434
    //   429: astore_0
    //   430: aload_0
    //   431: invokevirtual 165	java/io/IOException:printStackTrace	()V
    //   434: iconst_1
    //   435: istore 5
    //   437: goto +66 -> 503
    //   440: astore_0
    //   441: goto +75 -> 516
    //   444: astore_0
    //   445: aload 7
    //   447: astore_3
    //   448: aload_0
    //   449: astore 7
    //   451: goto +17 -> 468
    //   454: astore_2
    //   455: aload_0
    //   456: astore 7
    //   458: aload_2
    //   459: astore_0
    //   460: goto +56 -> 516
    //   463: astore 7
    //   465: aload 8
    //   467: astore_3
    //   468: aload_3
    //   469: astore_0
    //   470: aload 7
    //   472: invokevirtual 165	java/io/IOException:printStackTrace	()V
    //   475: iload 6
    //   477: istore 5
    //   479: aload_3
    //   480: ifnull +23 -> 503
    //   483: aload_3
    //   484: invokevirtual 253	java/io/FileOutputStream:close	()V
    //   487: iload 6
    //   489: istore 5
    //   491: goto +12 -> 503
    //   494: astore_0
    //   495: aload_0
    //   496: invokevirtual 165	java/io/IOException:printStackTrace	()V
    //   499: iload 6
    //   501: istore 5
    //   503: aload_2
    //   504: iconst_3
    //   505: aload 9
    //   507: invokevirtual 120	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   510: invokestatic 259	com/tencent/common/plugin/PluginStatBehavior:setPluginData	(Ljava/lang/String;ILjava/lang/String;)V
    //   513: iload 5
    //   515: ireturn
    //   516: aload 7
    //   518: ifnull +16 -> 534
    //   521: aload 7
    //   523: invokevirtual 253	java/io/FileOutputStream:close	()V
    //   526: goto +8 -> 534
    //   529: astore_2
    //   530: aload_2
    //   531: invokevirtual 165	java/io/IOException:printStackTrace	()V
    //   534: aload_0
    //   535: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	536	0	paramFile	File
    //   0	536	1	paramInt	int
    //   0	536	2	paramString	String
    //   0	536	3	paramArrayOfFile	File[]
    //   28	152	4	i	int
    //   435	79	5	bool1	boolean
    //   1	499	6	bool2	boolean
    //   60	397	7	localObject1	Object
    //   463	59	7	localIOException	IOException
    //   56	410	8	localObject2	Object
    //   25	481	9	localStringBuilder	StringBuilder
    //   16	350	10	localProperties	java.util.Properties
    //   41	373	11	localObject3	Object
    //   48	78	12	str	String
    // Exception table:
    //   from	to	target	type
    //   421	426	429	java/io/IOException
    //   242	409	440	finally
    //   412	421	440	finally
    //   242	409	444	java/io/IOException
    //   412	421	444	java/io/IOException
    //   231	242	454	finally
    //   470	475	454	finally
    //   231	242	463	java/io/IOException
    //   483	487	494	java/io/IOException
    //   521	526	529	java/io/IOException
  }
  
  public static String genMd5(File paramFile, String paramString)
  {
    paramFile = new a(paramFile, paramString);
    paramString = ByteUtils.byteToHexString(Md5Utils.getMD5(paramFile));
    paramFile.close();
    return paramString;
  }
  
  public static ZipPluginCheck getInstance()
  {
    if (jdField_a_of_type_ComTencentCommonPluginZipPluginCheck == null) {
      jdField_a_of_type_ComTencentCommonPluginZipPluginCheck = new ZipPluginCheck();
    }
    return jdField_a_of_type_ComTencentCommonPluginZipPluginCheck;
  }
  
  /* Error */
  public static String getPluginVerCode(File paramFile, String paramString)
  {
    // Byte code:
    //   0: ldc -60
    //   2: astore 5
    //   4: new 109	java/lang/StringBuilder
    //   7: dup
    //   8: invokespecial 110	java/lang/StringBuilder:<init>	()V
    //   11: astore_3
    //   12: aload_3
    //   13: aload_1
    //   14: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   17: pop
    //   18: aload_3
    //   19: ldc 11
    //   21: invokevirtual 114	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: pop
    //   25: new 116	java/io/File
    //   28: dup
    //   29: aload_0
    //   30: aload_3
    //   31: invokevirtual 120	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   34: invokespecial 123	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   37: astore_0
    //   38: aload_0
    //   39: invokevirtual 127	java/io/File:exists	()Z
    //   42: istore_2
    //   43: aconst_null
    //   44: astore 4
    //   46: aconst_null
    //   47: astore_1
    //   48: iload_2
    //   49: ifne +5 -> 54
    //   52: aconst_null
    //   53: areturn
    //   54: new 129	java/util/Properties
    //   57: dup
    //   58: invokespecial 130	java/util/Properties:<init>	()V
    //   61: astore 6
    //   63: new 132	java/io/FileInputStream
    //   66: dup
    //   67: aload_0
    //   68: invokespecial 135	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   71: astore_0
    //   72: new 137	java/io/BufferedInputStream
    //   75: dup
    //   76: aload_0
    //   77: invokespecial 140	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   80: astore_3
    //   81: aload 6
    //   83: aload_3
    //   84: invokevirtual 143	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   87: aload 6
    //   89: ldc -38
    //   91: invokevirtual 149	java/util/Properties:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   94: astore_1
    //   95: aload_1
    //   96: astore 4
    //   98: aload_0
    //   99: invokevirtual 160	java/io/InputStream:close	()V
    //   102: aload_1
    //   103: astore 4
    //   105: aload_3
    //   106: invokevirtual 161	java/io/BufferedInputStream:close	()V
    //   109: aload_1
    //   110: areturn
    //   111: astore_0
    //   112: aload_0
    //   113: invokevirtual 164	java/lang/Exception:printStackTrace	()V
    //   116: aload 4
    //   118: areturn
    //   119: astore 5
    //   121: aload_3
    //   122: astore 4
    //   124: aload_0
    //   125: astore_1
    //   126: aload 5
    //   128: astore_3
    //   129: goto +71 -> 200
    //   132: astore_1
    //   133: goto +10 -> 143
    //   136: astore_3
    //   137: goto +65 -> 202
    //   140: astore_1
    //   141: aconst_null
    //   142: astore_3
    //   143: aload_0
    //   144: astore 4
    //   146: aload_3
    //   147: astore_0
    //   148: aload_1
    //   149: astore_3
    //   150: aload 4
    //   152: astore_1
    //   153: goto +12 -> 165
    //   156: astore_3
    //   157: aconst_null
    //   158: astore_0
    //   159: goto +43 -> 202
    //   162: astore_3
    //   163: aconst_null
    //   164: astore_0
    //   165: aload_3
    //   166: invokevirtual 165	java/io/IOException:printStackTrace	()V
    //   169: aload_1
    //   170: ifnull +11 -> 181
    //   173: aload 5
    //   175: astore 4
    //   177: aload_1
    //   178: invokevirtual 160	java/io/InputStream:close	()V
    //   181: aload_0
    //   182: ifnull +11 -> 193
    //   185: aload 5
    //   187: astore 4
    //   189: aload_0
    //   190: invokevirtual 161	java/io/BufferedInputStream:close	()V
    //   193: ldc -60
    //   195: areturn
    //   196: astore_3
    //   197: aload_0
    //   198: astore 4
    //   200: aload_1
    //   201: astore_0
    //   202: aload_0
    //   203: ifnull +10 -> 213
    //   206: aload_0
    //   207: invokevirtual 160	java/io/InputStream:close	()V
    //   210: goto +3 -> 213
    //   213: aload 4
    //   215: ifnull +15 -> 230
    //   218: aload 4
    //   220: invokevirtual 161	java/io/BufferedInputStream:close	()V
    //   223: goto +7 -> 230
    //   226: aload_0
    //   227: invokevirtual 164	java/lang/Exception:printStackTrace	()V
    //   230: aload_3
    //   231: athrow
    //   232: astore_0
    //   233: goto -7 -> 226
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	236	0	paramFile	File
    //   0	236	1	paramString	String
    //   42	7	2	bool	boolean
    //   11	118	3	localObject1	Object
    //   136	1	3	localObject2	Object
    //   142	8	3	str1	String
    //   156	1	3	localObject3	Object
    //   162	4	3	localIOException	IOException
    //   196	35	3	localObject4	Object
    //   44	175	4	localObject5	Object
    //   2	1	5	str2	String
    //   119	67	5	localObject6	Object
    //   61	27	6	localProperties	java.util.Properties
    // Exception table:
    //   from	to	target	type
    //   98	102	111	java/lang/Exception
    //   105	109	111	java/lang/Exception
    //   177	181	111	java/lang/Exception
    //   189	193	111	java/lang/Exception
    //   81	95	119	finally
    //   81	95	132	java/io/IOException
    //   72	81	136	finally
    //   72	81	140	java/io/IOException
    //   63	72	156	finally
    //   63	72	162	java/io/IOException
    //   165	169	196	finally
    //   206	210	232	java/lang/Exception
    //   218	223	232	java/lang/Exception
  }
  
  public String genMd5(String paramString)
  {
    Object localObject = QBPluginServiceImpl.getInstance().getPluginConfigInfo(paramString);
    String str = "6401";
    if (localObject != null) {
      str = ((QBPluginServiceImpl.PluginConfigInfo)localObject).compatableId;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(str);
    ((StringBuilder)localObject).append(paramString);
    return Md5Utils.getMD5(((StringBuilder)localObject).toString());
  }
  
  static class a
    extends InputStream
  {
    BufferedInputStream jdField_a_of_type_JavaIoBufferedInputStream;
    FileInputStream jdField_a_of_type_JavaIoFileInputStream;
    String jdField_a_of_type_JavaLangString;
    boolean jdField_a_of_type_Boolean = false;
    
    public a(File paramFile, String paramString)
    {
      try
      {
        this.jdField_a_of_type_JavaIoFileInputStream = new FileInputStream(paramFile);
        this.jdField_a_of_type_JavaIoBufferedInputStream = new BufferedInputStream(this.jdField_a_of_type_JavaIoFileInputStream);
        this.jdField_a_of_type_JavaLangString = paramString;
        return;
      }
      catch (FileNotFoundException paramFile)
      {
        paramFile.printStackTrace();
      }
    }
    
    public void close()
    {
      try
      {
        if (this.jdField_a_of_type_JavaIoFileInputStream != null) {
          this.jdField_a_of_type_JavaIoFileInputStream.close();
        }
        if (this.jdField_a_of_type_JavaIoBufferedInputStream != null)
        {
          this.jdField_a_of_type_JavaIoBufferedInputStream.close();
          return;
        }
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
    }
    
    public int read()
      throws IOException
    {
      byte[] arrayOfByte = new byte[1];
      read(arrayOfByte);
      return arrayOfByte[0];
    }
    
    public int read(byte[] paramArrayOfByte)
      throws IOException
    {
      if (this.jdField_a_of_type_Boolean) {
        return -1;
      }
      int j = this.jdField_a_of_type_JavaIoBufferedInputStream.read(paramArrayOfByte);
      int i = j;
      if (j <= 0)
      {
        i = j;
        if (!TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString))
        {
          i = j;
          if (QBPluginServiceImpl.getInstance().getPluginConfigInfo(this.jdField_a_of_type_JavaLangString) != null)
          {
            i = j;
            if (!TextUtils.isEmpty(QBPluginServiceImpl.getInstance().getPluginConfigInfo(this.jdField_a_of_type_JavaLangString).compatableId))
            {
              String str = QBPluginServiceImpl.getInstance().getPluginConfigInfo(this.jdField_a_of_type_JavaLangString).compatableId;
              System.arraycopy(str.getBytes("iso8859-1"), 0, paramArrayOfByte, 0, str.length());
              i = str.length();
            }
          }
        }
        this.jdField_a_of_type_Boolean = true;
      }
      return i;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\ZipPluginCheck.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */