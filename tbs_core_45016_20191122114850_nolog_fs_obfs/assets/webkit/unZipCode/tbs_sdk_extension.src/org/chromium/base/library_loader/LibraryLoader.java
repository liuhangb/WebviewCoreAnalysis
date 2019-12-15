package org.chromium.base.library_loader;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Process;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.support.annotation.NonNull;
import android.system.Os;
import java.util.concurrent.atomic.AtomicBoolean;
import org.chromium.base.BuildConfig;
import org.chromium.base.CommandLine;
import org.chromium.base.ContextUtils;
import org.chromium.base.Log;
import org.chromium.base.SysUtils;
import org.chromium.base.TraceEvent;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;

@JNINamespace("base::android")
@MainDex
public class LibraryLoader
{
  private static final Object jdField_a_of_type_JavaLangObject = new Object();
  private static volatile LibraryLoader jdField_a_of_type_OrgChromiumBaseLibrary_loaderLibraryLoader;
  private static NativeLibraryPreloader jdField_a_of_type_OrgChromiumBaseLibrary_loaderNativeLibraryPreloader;
  private static boolean jdField_b_of_type_Boolean;
  private final int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long;
  private final AtomicBoolean jdField_a_of_type_JavaUtilConcurrentAtomicAtomicBoolean;
  private int jdField_b_of_type_Int = -1;
  private boolean c;
  private boolean d;
  private volatile boolean e;
  private boolean f;
  private boolean g;
  private boolean h;
  
  private LibraryLoader(int paramInt)
  {
    this.jdField_a_of_type_Int = paramInt;
    this.jdField_a_of_type_JavaUtilConcurrentAtomicAtomicBoolean = new AtomicBoolean();
  }
  
  @NonNull
  private static String a(String paramString1, String paramString2)
  {
    if ((!jdField_a_of_type_Boolean) && (!Linker.isInZipFile())) {
      throw new AssertionError();
    }
    boolean bool = Process.is64Bit() ^ true;
    String str;
    switch (NativeLibraries.sCpuFamily)
    {
    default: 
      throw new RuntimeException("Unknown CPU ABI for native libraries");
    case 3: 
      if (bool) {
        str = "x86";
      } else {
        str = "x86_64";
      }
      break;
    case 2: 
      if (bool) {
        str = "mips";
      } else {
        str = "mips64";
      }
      break;
    case 1: 
      if (bool) {
        str = "armeabi-v7a";
      } else {
        str = "arm64-v8a";
      }
      break;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString2);
    localStringBuilder.append("!/lib/");
    localStringBuilder.append(str);
    localStringBuilder.append("/crazy.");
    localStringBuilder.append(System.mapLibraryName(paramString1));
    return localStringBuilder.toString();
  }
  
  /* Error */
  private void a(Context paramContext)
  {
    // Byte code:
    //   0: ldc 127
    //   2: invokestatic 133	org/chromium/base/TraceEvent:scoped	(Ljava/lang/String;)Lorg/chromium/base/TraceEvent;
    //   5: astore 4
    //   7: aconst_null
    //   8: astore_3
    //   9: aload_3
    //   10: astore_2
    //   11: getstatic 38	org/chromium/base/library_loader/LibraryLoader:jdField_a_of_type_Boolean	Z
    //   14: ifne +24 -> 38
    //   17: aload_3
    //   18: astore_2
    //   19: invokestatic 136	org/chromium/base/library_loader/Linker:isUsed	()Z
    //   22: ifne +6 -> 28
    //   25: goto +13 -> 38
    //   28: aload_3
    //   29: astore_2
    //   30: new 67	java/lang/AssertionError
    //   33: dup
    //   34: invokespecial 68	java/lang/AssertionError:<init>	()V
    //   37: athrow
    //   38: aload_3
    //   39: astore_2
    //   40: getstatic 138	org/chromium/base/library_loader/LibraryLoader:jdField_a_of_type_OrgChromiumBaseLibrary_loaderNativeLibraryPreloader	Lorg/chromium/base/library_loader/NativeLibraryPreloader;
    //   43: ifnull +30 -> 73
    //   46: aload_3
    //   47: astore_2
    //   48: getstatic 140	org/chromium/base/library_loader/LibraryLoader:jdField_b_of_type_Boolean	Z
    //   51: ifne +22 -> 73
    //   54: aload_3
    //   55: astore_2
    //   56: aload_0
    //   57: getstatic 138	org/chromium/base/library_loader/LibraryLoader:jdField_a_of_type_OrgChromiumBaseLibrary_loaderNativeLibraryPreloader	Lorg/chromium/base/library_loader/NativeLibraryPreloader;
    //   60: aload_1
    //   61: invokevirtual 146	org/chromium/base/library_loader/NativeLibraryPreloader:loadLibrary	(Landroid/content/Context;)I
    //   64: putfield 47	org/chromium/base/library_loader/LibraryLoader:jdField_b_of_type_Int	I
    //   67: aload_3
    //   68: astore_2
    //   69: iconst_1
    //   70: putstatic 140	org/chromium/base/library_loader/LibraryLoader:jdField_b_of_type_Boolean	Z
    //   73: aload 4
    //   75: ifnull +8 -> 83
    //   78: aload 4
    //   80: invokevirtual 149	org/chromium/base/TraceEvent:close	()V
    //   83: return
    //   84: astore_1
    //   85: goto +8 -> 93
    //   88: astore_1
    //   89: aload_1
    //   90: astore_2
    //   91: aload_1
    //   92: athrow
    //   93: aload 4
    //   95: ifnull +29 -> 124
    //   98: aload_2
    //   99: ifnull +20 -> 119
    //   102: aload 4
    //   104: invokevirtual 149	org/chromium/base/TraceEvent:close	()V
    //   107: goto +17 -> 124
    //   110: astore_3
    //   111: aload_2
    //   112: aload_3
    //   113: invokevirtual 153	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   116: goto +8 -> 124
    //   119: aload 4
    //   121: invokevirtual 149	org/chromium/base/TraceEvent:close	()V
    //   124: aload_1
    //   125: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	126	0	this	LibraryLoader
    //   0	126	1	paramContext	Context
    //   10	102	2	localObject1	Object
    //   8	60	3	localObject2	Object
    //   110	3	3	localThrowable	Throwable
    //   5	115	4	localTraceEvent	TraceEvent
    // Exception table:
    //   from	to	target	type
    //   11	17	84	finally
    //   19	25	84	finally
    //   30	38	84	finally
    //   40	46	84	finally
    //   48	54	84	finally
    //   56	67	84	finally
    //   69	73	84	finally
    //   91	93	84	finally
    //   11	17	88	java/lang/Throwable
    //   19	25	88	java/lang/Throwable
    //   30	38	88	java/lang/Throwable
    //   40	46	88	java/lang/Throwable
    //   48	54	88	java/lang/Throwable
    //   56	67	88	java/lang/Throwable
    //   69	73	88	java/lang/Throwable
    //   102	107	110	java/lang/Throwable
  }
  
  private void a(Linker paramLinker, String paramString1, String paramString2)
  {
    if (paramLinker.isUsingBrowserSharedRelros()) {
      this.f = true;
    }
    try
    {
      paramLinker.loadLibrary(paramString1, paramString2);
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      for (;;) {}
    }
    this.g = true;
    paramLinker.loadLibraryNoFixedAddress(paramString1, paramString2);
    break label41;
    paramLinker.loadLibrary(paramString1, paramString2);
    label41:
    if (paramString1 != null) {
      this.h = true;
    }
  }
  
  private int b()
  {
    if ((!jdField_a_of_type_Boolean) && (!Linker.isUsed())) {
      throw new AssertionError();
    }
    if (this.h) {
      return 3;
    }
    return 0;
  }
  
  private void b()
  {
    if ((!jdField_a_of_type_Boolean) && (!this.c)) {
      throw new AssertionError();
    }
    if (this.d) {
      return;
    }
    CommandLine.enableNativeProxy();
    this.d = true;
  }
  
  /* Error */
  @android.annotation.SuppressLint({"DefaultLocale", "NewApi", "UnsafeDynamicallyLoadedCode"})
  private void b(Context paramContext)
    throws ProcessInitException
  {
    // Byte code:
    //   0: ldc -65
    //   2: invokestatic 133	org/chromium/base/TraceEvent:scoped	(Ljava/lang/String;)Lorg/chromium/base/TraceEvent;
    //   5: astore 9
    //   7: aconst_null
    //   8: astore 8
    //   10: aload 8
    //   12: astore 6
    //   14: aload_0
    //   15: getfield 176	org/chromium/base/library_loader/LibraryLoader:c	Z
    //   18: ifne +484 -> 502
    //   21: aload 8
    //   23: astore 6
    //   25: getstatic 38	org/chromium/base/library_loader/LibraryLoader:jdField_a_of_type_Boolean	Z
    //   28: ifne +29 -> 57
    //   31: aload 8
    //   33: astore 6
    //   35: aload_0
    //   36: getfield 193	org/chromium/base/library_loader/LibraryLoader:e	Z
    //   39: ifne +6 -> 45
    //   42: goto +15 -> 57
    //   45: aload 8
    //   47: astore 6
    //   49: new 67	java/lang/AssertionError
    //   52: dup
    //   53: invokespecial 68	java/lang/AssertionError:<init>	()V
    //   56: athrow
    //   57: aload 8
    //   59: astore 6
    //   61: invokestatic 199	android/os/SystemClock:uptimeMillis	()J
    //   64: lstore 4
    //   66: aload 8
    //   68: astore 6
    //   70: invokestatic 136	org/chromium/base/library_loader/Linker:isUsed	()Z
    //   73: ifeq +203 -> 276
    //   76: aload 8
    //   78: astore 6
    //   80: invokestatic 203	org/chromium/base/library_loader/Linker:getInstance	()Lorg/chromium/base/library_loader/Linker;
    //   83: astore 11
    //   85: aload 8
    //   87: astore 6
    //   89: aload 11
    //   91: invokevirtual 206	org/chromium/base/library_loader/Linker:prepareLibraryLoad	()V
    //   94: aload 8
    //   96: astore 6
    //   98: getstatic 210	org/chromium/base/library_loader/NativeLibraries:LIBRARIES	[Ljava/lang/String;
    //   101: astore 12
    //   103: aload 8
    //   105: astore 6
    //   107: aload 12
    //   109: arraylength
    //   110: istore_3
    //   111: iconst_0
    //   112: istore_2
    //   113: iload_2
    //   114: iload_3
    //   115: if_icmpge +149 -> 264
    //   118: aload 12
    //   120: iload_2
    //   121: aaload
    //   122: astore 10
    //   124: aload 8
    //   126: astore 6
    //   128: aload 11
    //   130: aload 10
    //   132: invokevirtual 214	org/chromium/base/library_loader/Linker:isChromiumLinkerLibrary	(Ljava/lang/String;)Z
    //   135: ifeq +6 -> 141
    //   138: goto +57 -> 195
    //   141: aload 8
    //   143: astore 6
    //   145: aload 10
    //   147: invokestatic 114	java/lang/System:mapLibraryName	(Ljava/lang/String;)Ljava/lang/String;
    //   150: astore 13
    //   152: aload 8
    //   154: astore 6
    //   156: invokestatic 65	org/chromium/base/library_loader/Linker:isInZipFile	()Z
    //   159: ifeq +19 -> 178
    //   162: aload 8
    //   164: astore 6
    //   166: aload_1
    //   167: invokevirtual 220	android/content/Context:getApplicationInfo	()Landroid/content/pm/ApplicationInfo;
    //   170: getfield 226	android/content/pm/ApplicationInfo:sourceDir	Ljava/lang/String;
    //   173: astore 7
    //   175: goto +6 -> 181
    //   178: aconst_null
    //   179: astore 7
    //   181: aload 8
    //   183: astore 6
    //   185: aload_0
    //   186: aload 11
    //   188: aload 7
    //   190: aload 13
    //   192: invokespecial 228	org/chromium/base/library_loader/LibraryLoader:a	(Lorg/chromium/base/library_loader/Linker;Ljava/lang/String;Ljava/lang/String;)V
    //   195: iload_2
    //   196: iconst_1
    //   197: iadd
    //   198: istore_2
    //   199: goto -86 -> 113
    //   202: astore_1
    //   203: aload 8
    //   205: astore 6
    //   207: new 99	java/lang/StringBuilder
    //   210: dup
    //   211: invokespecial 100	java/lang/StringBuilder:<init>	()V
    //   214: astore 7
    //   216: aload 8
    //   218: astore 6
    //   220: aload 7
    //   222: ldc -26
    //   224: invokevirtual 104	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   227: pop
    //   228: aload 8
    //   230: astore 6
    //   232: aload 7
    //   234: aload 10
    //   236: invokevirtual 104	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   239: pop
    //   240: aload 8
    //   242: astore 6
    //   244: ldc -24
    //   246: aload 7
    //   248: invokevirtual 118	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   251: iconst_0
    //   252: anewarray 4	java/lang/Object
    //   255: invokestatic 237	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   258: aload 8
    //   260: astore 6
    //   262: aload_1
    //   263: athrow
    //   264: aload 8
    //   266: astore 6
    //   268: aload 11
    //   270: invokevirtual 240	org/chromium/base/library_loader/Linker:finishLibraryLoad	()V
    //   273: goto +206 -> 479
    //   276: aload 8
    //   278: astore 6
    //   280: invokestatic 243	org/chromium/base/library_loader/LibraryLoader:setEnvForNative	()V
    //   283: aload 8
    //   285: astore 6
    //   287: aload_0
    //   288: aload_1
    //   289: invokespecial 245	org/chromium/base/library_loader/LibraryLoader:a	(Landroid/content/Context;)V
    //   292: aload 8
    //   294: astore 6
    //   296: getstatic 38	org/chromium/base/library_loader/LibraryLoader:jdField_a_of_type_Boolean	Z
    //   299: ifne +40 -> 339
    //   302: aload 8
    //   304: astore 6
    //   306: invokestatic 65	org/chromium/base/library_loader/Linker:isInZipFile	()Z
    //   309: ifeq +30 -> 339
    //   312: aload 8
    //   314: astore 6
    //   316: getstatic 250	android/os/Build$VERSION:SDK_INT	I
    //   319: bipush 23
    //   321: if_icmplt +6 -> 327
    //   324: goto +15 -> 339
    //   327: aload 8
    //   329: astore 6
    //   331: new 67	java/lang/AssertionError
    //   334: dup
    //   335: invokespecial 68	java/lang/AssertionError:<init>	()V
    //   338: athrow
    //   339: aload 8
    //   341: astore 6
    //   343: getstatic 210	org/chromium/base/library_loader/NativeLibraries:LIBRARIES	[Ljava/lang/String;
    //   346: astore 10
    //   348: aload 8
    //   350: astore 6
    //   352: aload 10
    //   354: arraylength
    //   355: istore_3
    //   356: iconst_0
    //   357: istore_2
    //   358: iload_2
    //   359: iload_3
    //   360: if_icmpge +119 -> 479
    //   363: aload 10
    //   365: iload_2
    //   366: aaload
    //   367: astore 7
    //   369: aload 8
    //   371: astore 6
    //   373: invokestatic 65	org/chromium/base/library_loader/Linker:isInZipFile	()Z
    //   376: ifne +15 -> 391
    //   379: aload 8
    //   381: astore 6
    //   383: aload 7
    //   385: invokestatic 252	java/lang/System:loadLibrary	(Ljava/lang/String;)V
    //   388: goto +22 -> 410
    //   391: aload 8
    //   393: astore 6
    //   395: aload 7
    //   397: aload_1
    //   398: invokevirtual 220	android/content/Context:getApplicationInfo	()Landroid/content/pm/ApplicationInfo;
    //   401: getfield 226	android/content/pm/ApplicationInfo:sourceDir	Ljava/lang/String;
    //   404: invokestatic 254	org/chromium/base/library_loader/LibraryLoader:a	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   407: invokestatic 257	java/lang/System:load	(Ljava/lang/String;)V
    //   410: iload_2
    //   411: iconst_1
    //   412: iadd
    //   413: istore_2
    //   414: goto -56 -> 358
    //   417: astore_1
    //   418: aload 8
    //   420: astore 6
    //   422: new 99	java/lang/StringBuilder
    //   425: dup
    //   426: invokespecial 100	java/lang/StringBuilder:<init>	()V
    //   429: astore 10
    //   431: aload 8
    //   433: astore 6
    //   435: aload 10
    //   437: ldc -26
    //   439: invokevirtual 104	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   442: pop
    //   443: aload 8
    //   445: astore 6
    //   447: aload 10
    //   449: aload 7
    //   451: invokevirtual 104	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   454: pop
    //   455: aload 8
    //   457: astore 6
    //   459: ldc -24
    //   461: aload 10
    //   463: invokevirtual 118	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   466: iconst_0
    //   467: anewarray 4	java/lang/Object
    //   470: invokestatic 237	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   473: aload 8
    //   475: astore 6
    //   477: aload_1
    //   478: athrow
    //   479: aload 8
    //   481: astore 6
    //   483: aload_0
    //   484: invokestatic 199	android/os/SystemClock:uptimeMillis	()J
    //   487: lload 4
    //   489: lsub
    //   490: putfield 259	org/chromium/base/library_loader/LibraryLoader:jdField_a_of_type_Long	J
    //   493: aload 8
    //   495: astore 6
    //   497: aload_0
    //   498: iconst_1
    //   499: putfield 176	org/chromium/base/library_loader/LibraryLoader:c	Z
    //   502: aload 9
    //   504: ifnull +8 -> 512
    //   507: aload 9
    //   509: invokevirtual 149	org/chromium/base/TraceEvent:close	()V
    //   512: return
    //   513: astore_1
    //   514: goto +9 -> 523
    //   517: astore_1
    //   518: aload_1
    //   519: astore 6
    //   521: aload_1
    //   522: athrow
    //   523: aload 9
    //   525: ifnull +33 -> 558
    //   528: aload 6
    //   530: ifnull +23 -> 553
    //   533: aload 9
    //   535: invokevirtual 149	org/chromium/base/TraceEvent:close	()V
    //   538: goto +20 -> 558
    //   541: astore 7
    //   543: aload 6
    //   545: aload 7
    //   547: invokevirtual 153	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   550: goto +8 -> 558
    //   553: aload 9
    //   555: invokevirtual 149	org/chromium/base/TraceEvent:close	()V
    //   558: aload_1
    //   559: athrow
    //   560: astore_1
    //   561: new 185	org/chromium/base/library_loader/ProcessInitException
    //   564: dup
    //   565: iconst_2
    //   566: aload_1
    //   567: invokespecial 262	org/chromium/base/library_loader/ProcessInitException:<init>	(ILjava/lang/Throwable;)V
    //   570: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	571	0	this	LibraryLoader
    //   0	571	1	paramContext	Context
    //   112	302	2	i	int
    //   110	251	3	j	int
    //   64	424	4	l	long
    //   12	532	6	localObject1	Object
    //   173	277	7	localObject2	Object
    //   541	5	7	localThrowable	Throwable
    //   8	486	8	localObject3	Object
    //   5	549	9	localTraceEvent	TraceEvent
    //   122	340	10	localObject4	Object
    //   83	186	11	localLinker	Linker
    //   101	18	12	arrayOfString	String[]
    //   150	41	13	str	String
    // Exception table:
    //   from	to	target	type
    //   185	195	202	java/lang/UnsatisfiedLinkError
    //   373	379	417	java/lang/UnsatisfiedLinkError
    //   383	388	417	java/lang/UnsatisfiedLinkError
    //   395	410	417	java/lang/UnsatisfiedLinkError
    //   14	21	513	finally
    //   25	31	513	finally
    //   35	42	513	finally
    //   49	57	513	finally
    //   61	66	513	finally
    //   70	76	513	finally
    //   80	85	513	finally
    //   89	94	513	finally
    //   98	103	513	finally
    //   107	111	513	finally
    //   128	138	513	finally
    //   145	152	513	finally
    //   156	162	513	finally
    //   166	175	513	finally
    //   185	195	513	finally
    //   207	216	513	finally
    //   220	228	513	finally
    //   232	240	513	finally
    //   244	258	513	finally
    //   262	264	513	finally
    //   268	273	513	finally
    //   280	283	513	finally
    //   287	292	513	finally
    //   296	302	513	finally
    //   306	312	513	finally
    //   316	324	513	finally
    //   331	339	513	finally
    //   343	348	513	finally
    //   352	356	513	finally
    //   373	379	513	finally
    //   383	388	513	finally
    //   395	410	513	finally
    //   422	431	513	finally
    //   435	443	513	finally
    //   447	455	513	finally
    //   459	473	513	finally
    //   477	479	513	finally
    //   483	493	513	finally
    //   497	502	513	finally
    //   521	523	513	finally
    //   14	21	517	java/lang/Throwable
    //   25	31	517	java/lang/Throwable
    //   35	42	517	java/lang/Throwable
    //   49	57	517	java/lang/Throwable
    //   61	66	517	java/lang/Throwable
    //   70	76	517	java/lang/Throwable
    //   80	85	517	java/lang/Throwable
    //   89	94	517	java/lang/Throwable
    //   98	103	517	java/lang/Throwable
    //   107	111	517	java/lang/Throwable
    //   128	138	517	java/lang/Throwable
    //   145	152	517	java/lang/Throwable
    //   156	162	517	java/lang/Throwable
    //   166	175	517	java/lang/Throwable
    //   185	195	517	java/lang/Throwable
    //   207	216	517	java/lang/Throwable
    //   220	228	517	java/lang/Throwable
    //   232	240	517	java/lang/Throwable
    //   244	258	517	java/lang/Throwable
    //   262	264	517	java/lang/Throwable
    //   268	273	517	java/lang/Throwable
    //   280	283	517	java/lang/Throwable
    //   287	292	517	java/lang/Throwable
    //   296	302	517	java/lang/Throwable
    //   306	312	517	java/lang/Throwable
    //   316	324	517	java/lang/Throwable
    //   331	339	517	java/lang/Throwable
    //   343	348	517	java/lang/Throwable
    //   352	356	517	java/lang/Throwable
    //   373	379	517	java/lang/Throwable
    //   383	388	517	java/lang/Throwable
    //   395	410	517	java/lang/Throwable
    //   422	431	517	java/lang/Throwable
    //   435	443	517	java/lang/Throwable
    //   447	455	517	java/lang/Throwable
    //   459	473	517	java/lang/Throwable
    //   477	479	517	java/lang/Throwable
    //   483	493	517	java/lang/Throwable
    //   497	502	517	java/lang/Throwable
    //   533	538	541	java/lang/Throwable
    //   0	7	560	java/lang/UnsatisfiedLinkError
    //   507	512	560	java/lang/UnsatisfiedLinkError
    //   533	538	560	java/lang/UnsatisfiedLinkError
    //   543	550	560	java/lang/UnsatisfiedLinkError
    //   553	558	560	java/lang/UnsatisfiedLinkError
    //   558	560	560	java/lang/UnsatisfiedLinkError
  }
  
  private static boolean b()
  {
    StrictMode.ThreadPolicy localThreadPolicy = StrictMode.allowThreadDiskReads();
    try
    {
      boolean bool = ContextUtils.getAppSharedPreferences().getBoolean("dont_prefetch_libraries", false);
      return bool;
    }
    finally
    {
      StrictMode.setThreadPolicy(localThreadPolicy);
    }
  }
  
  private void c()
    throws ProcessInitException
  {
    if (this.e) {
      return;
    }
    b();
    if (nativeLibraryLoaded())
    {
      if (NativeLibraries.a.equals(nativeGetVersionNumber()))
      {
        TraceEvent.registerNativeEnabledObserver();
        this.e = true;
        return;
      }
      throw new ProcessInitException(3);
    }
    Log.e("LibraryLoader", "error calling nativeLibraryLoaded", new Object[0]);
    throw new ProcessInitException(1);
  }
  
  private void d()
  {
    if (Linker.isUsed()) {
      nativeRecordChromiumAndroidLinkerBrowserHistogram(this.f, this.g, b(), this.jdField_a_of_type_Long);
    }
    if (jdField_a_of_type_OrgChromiumBaseLibrary_loaderNativeLibraryPreloader != null) {
      nativeRecordLibraryPreloaderBrowserHistogram(this.jdField_b_of_type_Int);
    }
  }
  
  public static LibraryLoader get(int paramInt)
    throws ProcessInitException
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if (jdField_a_of_type_OrgChromiumBaseLibrary_loaderLibraryLoader != null)
      {
        if (jdField_a_of_type_OrgChromiumBaseLibrary_loaderLibraryLoader.jdField_a_of_type_Int == paramInt)
        {
          localLibraryLoader = jdField_a_of_type_OrgChromiumBaseLibrary_loaderLibraryLoader;
          return localLibraryLoader;
        }
        throw new ProcessInitException(2);
      }
      jdField_a_of_type_OrgChromiumBaseLibrary_loaderLibraryLoader = new LibraryLoader(paramInt);
      LibraryLoader localLibraryLoader = jdField_a_of_type_OrgChromiumBaseLibrary_loaderLibraryLoader;
      return localLibraryLoader;
    }
  }
  
  @CalledByNative
  public static int getLibraryProcessType()
  {
    if (jdField_a_of_type_OrgChromiumBaseLibrary_loaderLibraryLoader == null) {
      return 0;
    }
    return jdField_a_of_type_OrgChromiumBaseLibrary_loaderLibraryLoader.jdField_a_of_type_Int;
  }
  
  public static boolean isInitialized()
  {
    return (jdField_a_of_type_OrgChromiumBaseLibrary_loaderLibraryLoader != null) && (jdField_a_of_type_OrgChromiumBaseLibrary_loaderLibraryLoader.e);
  }
  
  private static native boolean nativeForkAndPrefetchNativeLibrary();
  
  private native String nativeGetVersionNumber();
  
  private native boolean nativeLibraryLoaded();
  
  private static native int nativePercentageOfResidentNativeLibraryCode();
  
  private static native void nativePeriodicallyCollectResidency();
  
  private native void nativeRecordChromiumAndroidLinkerBrowserHistogram(boolean paramBoolean1, boolean paramBoolean2, int paramInt, long paramLong);
  
  private native void nativeRecordLibraryPreloaderBrowserHistogram(int paramInt);
  
  private native void nativeRegisterChromiumAndroidLinkerRendererHistogram(boolean paramBoolean1, boolean paramBoolean2, long paramLong);
  
  private native void nativeRegisterLibraryPreloaderRendererHistogram(int paramInt);
  
  public static void setDontPrefetchLibrariesOnNextRuns(boolean paramBoolean)
  {
    ContextUtils.getAppSharedPreferences().edit().putBoolean("dont_prefetch_libraries", paramBoolean).apply();
  }
  
  public static void setEnvForNative()
  {
    if ((BuildConfig.IS_UBSAN) && (Build.VERSION.SDK_INT >= 21)) {}
    try
    {
      Os.setenv("UBSAN_OPTIONS", "print_stacktrace=1 stack_trace_format='#%n pc %o %m' handle_segv=0 handle_sigbus=0 handle_sigfpe=0", true);
      return;
    }
    catch (Exception localException) {}
  }
  
  @VisibleForTesting
  public static void setLibraryLoaderForTesting(LibraryLoader paramLibraryLoader)
  {
    jdField_a_of_type_OrgChromiumBaseLibrary_loaderLibraryLoader = paramLibraryLoader;
  }
  
  public static void setNativeLibraryPreloader(NativeLibraryPreloader paramNativeLibraryPreloader)
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if ((!jdField_a_of_type_Boolean) && ((jdField_a_of_type_OrgChromiumBaseLibrary_loaderNativeLibraryPreloader != null) || ((jdField_a_of_type_OrgChromiumBaseLibrary_loaderLibraryLoader != null) && (jdField_a_of_type_OrgChromiumBaseLibrary_loaderLibraryLoader.c)))) {
        throw new AssertionError();
      }
      jdField_a_of_type_OrgChromiumBaseLibrary_loaderNativeLibraryPreloader = paramNativeLibraryPreloader;
      return;
    }
  }
  
  public void asyncPrefetchLibrariesToMemory()
  {
    
    if (b()) {
      return;
    }
    boolean bool = this.jdField_a_of_type_JavaUtilConcurrentAtomicAtomicBoolean.compareAndSet(false, true);
    if ((bool) && (CommandLine.getInstance().hasSwitch("log-native-library-residency")))
    {
      new Thread(new Runnable()
      {
        public void run() {}
      }).start();
      return;
    }
    new LibraryPrefetchTask(bool).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
  }
  
  public void ensureInitialized()
    throws ProcessInitException
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if (this.e) {
        return;
      }
      b(ContextUtils.getApplicationContext());
      c();
      return;
    }
  }
  
  public void initialize()
    throws ProcessInitException
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      c();
      return;
    }
  }
  
  public void loadNow()
    throws ProcessInitException
  {
    loadNowOverrideApplicationContext(ContextUtils.getApplicationContext());
  }
  
  public void loadNowOverrideApplicationContext(Context paramContext)
    throws ProcessInitException
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if ((this.c) && (paramContext != ContextUtils.getApplicationContext())) {
        throw new IllegalStateException("Attempt to load again from alternate context.");
      }
      b(paramContext);
      return;
    }
  }
  
  public void onNativeInitializationComplete()
  {
    d();
  }
  
  public void preloadNow()
  {
    preloadNowOverrideApplicationContext(ContextUtils.getApplicationContext());
  }
  
  public void preloadNowOverrideApplicationContext(Context paramContext)
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if (!Linker.isUsed()) {
        a(paramContext);
      }
      return;
    }
  }
  
  public void registerRendererProcessHistogram(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (Linker.isUsed()) {
      nativeRegisterChromiumAndroidLinkerRendererHistogram(paramBoolean1, paramBoolean2, this.jdField_a_of_type_Long);
    }
    if (jdField_a_of_type_OrgChromiumBaseLibrary_loaderNativeLibraryPreloader != null) {
      nativeRegisterLibraryPreloaderRendererHistogram(this.jdField_b_of_type_Int);
    }
  }
  
  public void switchCommandLineForWebView()
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      b();
      return;
    }
  }
  
  private static class LibraryPrefetchTask
    extends AsyncTask<Void, Void, Void>
  {
    private final boolean a;
    
    public LibraryPrefetchTask(boolean paramBoolean)
    {
      this.a = paramBoolean;
    }
    
    /* Error */
    protected Void a(Void... paramVarArgs)
    {
      // Byte code:
      //   0: ldc 23
      //   2: invokestatic 29	org/chromium/base/TraceEvent:scoped	(Ljava/lang/String;)Lorg/chromium/base/TraceEvent;
      //   5: astore 8
      //   7: aconst_null
      //   8: astore 7
      //   10: aload 7
      //   12: astore_1
      //   13: invokestatic 32	org/chromium/base/library_loader/LibraryLoader:a	()I
      //   16: istore_3
      //   17: aload 7
      //   19: astore_1
      //   20: aload_0
      //   21: getfield 17	org/chromium/base/library_loader/LibraryLoader$LibraryPrefetchTask:a	Z
      //   24: istore 5
      //   26: iconst_0
      //   27: istore 4
      //   29: iload 5
      //   31: ifeq +169 -> 200
      //   34: iload_3
      //   35: bipush 90
      //   37: if_icmpge +163 -> 200
      //   40: iconst_1
      //   41: istore_2
      //   42: goto +3 -> 45
      //   45: iload_2
      //   46: ifeq +11 -> 57
      //   49: aload 7
      //   51: astore_1
      //   52: invokestatic 35	org/chromium/base/library_loader/LibraryLoader:a	()Z
      //   55: istore 4
      //   57: iload_2
      //   58: ifeq +13 -> 71
      //   61: aload 7
      //   63: astore_1
      //   64: ldc 37
      //   66: iload 4
      //   68: invokestatic 43	org/chromium/base/metrics/RecordHistogram:recordBooleanHistogram	(Ljava/lang/String;Z)V
      //   71: iload_3
      //   72: iconst_m1
      //   73: if_icmpeq +66 -> 139
      //   76: aload 7
      //   78: astore_1
      //   79: new 45	java/lang/StringBuilder
      //   82: dup
      //   83: invokespecial 46	java/lang/StringBuilder:<init>	()V
      //   86: astore 9
      //   88: aload 7
      //   90: astore_1
      //   91: aload 9
      //   93: ldc 48
      //   95: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   98: pop
      //   99: aload 7
      //   101: astore_1
      //   102: aload_0
      //   103: getfield 17	org/chromium/base/library_loader/LibraryLoader$LibraryPrefetchTask:a	Z
      //   106: ifeq +99 -> 205
      //   109: ldc 54
      //   111: astore 6
      //   113: goto +3 -> 116
      //   116: aload 7
      //   118: astore_1
      //   119: aload 9
      //   121: aload 6
      //   123: invokevirtual 52	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   126: pop
      //   127: aload 7
      //   129: astore_1
      //   130: aload 9
      //   132: invokevirtual 58	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   135: iload_3
      //   136: invokestatic 62	org/chromium/base/metrics/RecordHistogram:recordPercentageHistogram	(Ljava/lang/String;I)V
      //   139: aload 8
      //   141: ifnull +8 -> 149
      //   144: aload 8
      //   146: invokevirtual 65	org/chromium/base/TraceEvent:close	()V
      //   149: aconst_null
      //   150: areturn
      //   151: astore 6
      //   153: goto +11 -> 164
      //   156: astore 6
      //   158: aload 6
      //   160: astore_1
      //   161: aload 6
      //   163: athrow
      //   164: aload 8
      //   166: ifnull +31 -> 197
      //   169: aload_1
      //   170: ifnull +22 -> 192
      //   173: aload 8
      //   175: invokevirtual 65	org/chromium/base/TraceEvent:close	()V
      //   178: goto +19 -> 197
      //   181: astore 7
      //   183: aload_1
      //   184: aload 7
      //   186: invokevirtual 69	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
      //   189: goto +8 -> 197
      //   192: aload 8
      //   194: invokevirtual 65	org/chromium/base/TraceEvent:close	()V
      //   197: aload 6
      //   199: athrow
      //   200: iconst_0
      //   201: istore_2
      //   202: goto -157 -> 45
      //   205: ldc 71
      //   207: astore 6
      //   209: goto -93 -> 116
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	212	0	this	LibraryPrefetchTask
      //   0	212	1	paramVarArgs	Void[]
      //   41	161	2	i	int
      //   16	120	3	j	int
      //   27	40	4	bool1	boolean
      //   24	6	5	bool2	boolean
      //   111	11	6	str1	String
      //   151	1	6	localObject1	Object
      //   156	42	6	localThrowable1	Throwable
      //   207	1	6	str2	String
      //   8	120	7	localObject2	Object
      //   181	4	7	localThrowable2	Throwable
      //   5	188	8	localTraceEvent	TraceEvent
      //   86	45	9	localStringBuilder	StringBuilder
      // Exception table:
      //   from	to	target	type
      //   13	17	151	finally
      //   20	26	151	finally
      //   52	57	151	finally
      //   64	71	151	finally
      //   79	88	151	finally
      //   91	99	151	finally
      //   102	109	151	finally
      //   119	127	151	finally
      //   130	139	151	finally
      //   161	164	151	finally
      //   13	17	156	java/lang/Throwable
      //   20	26	156	java/lang/Throwable
      //   52	57	156	java/lang/Throwable
      //   64	71	156	java/lang/Throwable
      //   79	88	156	java/lang/Throwable
      //   91	99	156	java/lang/Throwable
      //   102	109	156	java/lang/Throwable
      //   119	127	156	java/lang/Throwable
      //   130	139	156	java/lang/Throwable
      //   173	178	181	java/lang/Throwable
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\library_loader\LibraryLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */