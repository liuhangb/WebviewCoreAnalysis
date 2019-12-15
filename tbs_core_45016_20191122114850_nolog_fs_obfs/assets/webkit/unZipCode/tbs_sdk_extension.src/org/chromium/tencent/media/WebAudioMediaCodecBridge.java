package org.chromium.tencent.media;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("media")
public class WebAudioMediaCodecBridge
{
  private static final String TAG = "cr.media";
  static final long TIMEOUT_MICROSECONDS = 500L;
  
  @CalledByNative
  private static String createTempFile(boolean paramBoolean)
    throws IOException
  {
    try
    {
      String str = File.createTempFile("webaudio", ".dat", ContextUtils.getApplicationContext().getCacheDir()).getAbsolutePath();
      return str;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return "";
  }
  
  @CalledByNative
  private static boolean decodeAudioFile(long paramLong1, int paramInt, long paramLong2)
  {
    try
    {
      boolean bool = decodeAudioFileInternal(ContextUtils.getApplicationContext(), paramLong1, paramInt, paramLong2);
      return bool;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    return false;
  }
  
  /* Error */
  private static boolean decodeAudioFileInternal(Context paramContext, long paramLong1, int paramInt, long paramLong2)
  {
    // Byte code:
    //   0: getstatic 73	android/os/Build$VERSION:SDK_INT	I
    //   3: bipush 16
    //   5: if_icmpge +5 -> 10
    //   8: iconst_0
    //   9: ireturn
    //   10: lload 4
    //   12: lconst_0
    //   13: lcmp
    //   14: iflt +658 -> 672
    //   17: lload 4
    //   19: ldc2_w 74
    //   22: lcmp
    //   23: ifle +6 -> 29
    //   26: goto +646 -> 672
    //   29: new 77	android/media/MediaExtractor
    //   32: dup
    //   33: invokespecial 78	android/media/MediaExtractor:<init>	()V
    //   36: astore 22
    //   38: iload_3
    //   39: invokestatic 84	android/os/ParcelFileDescriptor:adoptFd	(I)Landroid/os/ParcelFileDescriptor;
    //   42: astore 21
    //   44: aload 22
    //   46: aload 21
    //   48: invokevirtual 88	android/os/ParcelFileDescriptor:getFileDescriptor	()Ljava/io/FileDescriptor;
    //   51: lconst_0
    //   52: lload 4
    //   54: invokevirtual 92	android/media/MediaExtractor:setDataSource	(Ljava/io/FileDescriptor;JJ)V
    //   57: aload 22
    //   59: invokevirtual 96	android/media/MediaExtractor:getTrackCount	()I
    //   62: ifgt +11 -> 73
    //   65: aload 21
    //   67: invokevirtual 99	android/os/ParcelFileDescriptor:detachFd	()I
    //   70: pop
    //   71: iconst_0
    //   72: ireturn
    //   73: aload 22
    //   75: iconst_0
    //   76: invokevirtual 103	android/media/MediaExtractor:getTrackFormat	(I)Landroid/media/MediaFormat;
    //   79: astore_0
    //   80: aload_0
    //   81: ldc 105
    //   83: invokevirtual 111	android/media/MediaFormat:getInteger	(Ljava/lang/String;)I
    //   86: istore 14
    //   88: aload_0
    //   89: ldc 113
    //   91: invokevirtual 111	android/media/MediaFormat:getInteger	(Ljava/lang/String;)I
    //   94: istore 7
    //   96: aload_0
    //   97: ldc 115
    //   99: invokevirtual 119	android/media/MediaFormat:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   102: astore 19
    //   104: aload_0
    //   105: ldc 121
    //   107: invokevirtual 125	android/media/MediaFormat:containsKey	(Ljava/lang/String;)Z
    //   110: ifeq +14 -> 124
    //   113: aload_0
    //   114: ldc 121
    //   116: invokevirtual 129	android/media/MediaFormat:getLong	(Ljava/lang/String;)J
    //   119: lstore 4
    //   121: goto +6 -> 127
    //   124: lconst_0
    //   125: lstore 4
    //   127: lload 4
    //   129: lstore 16
    //   131: lload 4
    //   133: ldc2_w 74
    //   136: lcmp
    //   137: ifle +6 -> 143
    //   140: lconst_0
    //   141: lstore 16
    //   143: aload 19
    //   145: invokestatic 135	android/media/MediaCodec:createDecoderByType	(Ljava/lang/String;)Landroid/media/MediaCodec;
    //   148: astore 23
    //   150: aload 23
    //   152: aload_0
    //   153: aconst_null
    //   154: aconst_null
    //   155: iconst_0
    //   156: invokevirtual 139	android/media/MediaCodec:configure	(Landroid/media/MediaFormat;Landroid/view/Surface;Landroid/media/MediaCrypto;I)V
    //   159: aload 23
    //   161: invokevirtual 142	android/media/MediaCodec:start	()V
    //   164: aload 23
    //   166: invokevirtual 146	android/media/MediaCodec:getInputBuffers	()[Ljava/nio/ByteBuffer;
    //   169: astore 24
    //   171: aload 23
    //   173: invokevirtual 149	android/media/MediaCodec:getOutputBuffers	()[Ljava/nio/ByteBuffer;
    //   176: astore_0
    //   177: aload 22
    //   179: iconst_0
    //   180: invokevirtual 153	android/media/MediaExtractor:selectTrack	(I)V
    //   183: iload 14
    //   185: istore 8
    //   187: iconst_0
    //   188: istore 9
    //   190: iconst_0
    //   191: istore 6
    //   193: iconst_0
    //   194: istore_3
    //   195: iconst_0
    //   196: istore 18
    //   198: iload 9
    //   200: ifne +373 -> 573
    //   203: iload 6
    //   205: ifne +104 -> 309
    //   208: aload 23
    //   210: ldc2_w 14
    //   213: invokevirtual 157	android/media/MediaCodec:dequeueInputBuffer	(J)I
    //   216: istore 12
    //   218: iload 12
    //   220: iflt +86 -> 306
    //   223: aload 24
    //   225: iload 12
    //   227: aaload
    //   228: astore 19
    //   230: aload 22
    //   232: aload 19
    //   234: iconst_0
    //   235: invokevirtual 161	android/media/MediaExtractor:readSampleData	(Ljava/nio/ByteBuffer;I)I
    //   238: istore 10
    //   240: iload 10
    //   242: ifge +15 -> 257
    //   245: lconst_0
    //   246: lstore 4
    //   248: iconst_0
    //   249: istore 10
    //   251: iconst_1
    //   252: istore 6
    //   254: goto +10 -> 264
    //   257: aload 22
    //   259: invokevirtual 165	android/media/MediaExtractor:getSampleTime	()J
    //   262: lstore 4
    //   264: iload 6
    //   266: ifeq +9 -> 275
    //   269: iconst_4
    //   270: istore 11
    //   272: goto +6 -> 278
    //   275: iconst_0
    //   276: istore 11
    //   278: aload 23
    //   280: iload 12
    //   282: iconst_0
    //   283: iload 10
    //   285: lload 4
    //   287: iload 11
    //   289: invokevirtual 169	android/media/MediaCodec:queueInputBuffer	(IIIJI)V
    //   292: iload 6
    //   294: ifne +9 -> 303
    //   297: aload 22
    //   299: invokevirtual 173	android/media/MediaExtractor:advance	()Z
    //   302: pop
    //   303: goto +6 -> 309
    //   306: goto +3 -> 309
    //   309: new 175	android/media/MediaCodec$BufferInfo
    //   312: dup
    //   313: invokespecial 176	android/media/MediaCodec$BufferInfo:<init>	()V
    //   316: astore 20
    //   318: aload 23
    //   320: aload 20
    //   322: ldc2_w 14
    //   325: invokevirtual 180	android/media/MediaCodec:dequeueOutputBuffer	(Landroid/media/MediaCodec$BufferInfo;J)I
    //   328: istore 15
    //   330: iload 15
    //   332: iflt +117 -> 449
    //   335: aload_0
    //   336: iload 15
    //   338: aaload
    //   339: astore 19
    //   341: iload_3
    //   342: ifne +18 -> 360
    //   345: lload_1
    //   346: iload 14
    //   348: iload 7
    //   350: lload 16
    //   352: invokestatic 184	org/chromium/tencent/media/WebAudioMediaCodecBridge:nativeInitializeDestination	(JIIJ)V
    //   355: iconst_1
    //   356: istore_3
    //   357: goto +3 -> 360
    //   360: iload_3
    //   361: ifeq +26 -> 387
    //   364: aload 20
    //   366: getfield 187	android/media/MediaCodec$BufferInfo:size	I
    //   369: ifle +18 -> 387
    //   372: lload_1
    //   373: aload 19
    //   375: aload 20
    //   377: getfield 187	android/media/MediaCodec$BufferInfo:size	I
    //   380: iload 14
    //   382: iload 8
    //   384: invokestatic 191	org/chromium/tencent/media/WebAudioMediaCodecBridge:nativeOnChunkDecoded	(JLjava/nio/ByteBuffer;III)V
    //   387: aload 19
    //   389: invokevirtual 197	java/nio/ByteBuffer:clear	()Ljava/nio/Buffer;
    //   392: pop
    //   393: aload 23
    //   395: iload 15
    //   397: iconst_0
    //   398: invokevirtual 201	android/media/MediaCodec:releaseOutputBuffer	(IZ)V
    //   401: iload 9
    //   403: istore 13
    //   405: iload 8
    //   407: istore 12
    //   409: iload 7
    //   411: istore 11
    //   413: iload_3
    //   414: istore 10
    //   416: aload_0
    //   417: astore 19
    //   419: aload 20
    //   421: getfield 204	android/media/MediaCodec$BufferInfo:flags	I
    //   424: iconst_4
    //   425: iand
    //   426: ifeq +115 -> 541
    //   429: iconst_1
    //   430: istore 13
    //   432: iload 8
    //   434: istore 12
    //   436: iload 7
    //   438: istore 11
    //   440: iload_3
    //   441: istore 10
    //   443: aload_0
    //   444: astore 19
    //   446: goto +95 -> 541
    //   449: iload 15
    //   451: bipush -3
    //   453: if_icmpne +28 -> 481
    //   456: aload 23
    //   458: invokevirtual 149	android/media/MediaCodec:getOutputBuffers	()[Ljava/nio/ByteBuffer;
    //   461: astore 19
    //   463: iload 9
    //   465: istore 13
    //   467: iload 8
    //   469: istore 12
    //   471: iload 7
    //   473: istore 11
    //   475: iload_3
    //   476: istore 10
    //   478: goto +63 -> 541
    //   481: iload 9
    //   483: istore 13
    //   485: iload 8
    //   487: istore 12
    //   489: iload 7
    //   491: istore 11
    //   493: iload_3
    //   494: istore 10
    //   496: aload_0
    //   497: astore 19
    //   499: iload 15
    //   501: bipush -2
    //   503: if_icmpne +38 -> 541
    //   506: aload 23
    //   508: invokevirtual 208	android/media/MediaCodec:getOutputFormat	()Landroid/media/MediaFormat;
    //   511: astore 19
    //   513: aload 19
    //   515: ldc 105
    //   517: invokevirtual 111	android/media/MediaFormat:getInteger	(Ljava/lang/String;)I
    //   520: istore 12
    //   522: aload 19
    //   524: ldc 113
    //   526: invokevirtual 111	android/media/MediaFormat:getInteger	(Ljava/lang/String;)I
    //   529: istore 11
    //   531: aload_0
    //   532: astore 19
    //   534: iload_3
    //   535: istore 10
    //   537: iload 9
    //   539: istore 13
    //   541: iload 13
    //   543: istore 9
    //   545: iload 12
    //   547: istore 8
    //   549: iload 11
    //   551: istore 7
    //   553: iload 10
    //   555: istore_3
    //   556: aload 19
    //   558: astore_0
    //   559: goto -364 -> 195
    //   562: astore_0
    //   563: aload_0
    //   564: invokevirtual 209	java/lang/Exception:printStackTrace	()V
    //   567: iconst_0
    //   568: istore 18
    //   570: goto +6 -> 576
    //   573: iconst_1
    //   574: istore 18
    //   576: aload 21
    //   578: invokevirtual 99	android/os/ParcelFileDescriptor:detachFd	()I
    //   581: pop
    //   582: aload 23
    //   584: invokevirtual 212	android/media/MediaCodec:stop	()V
    //   587: aload 23
    //   589: invokevirtual 215	android/media/MediaCodec:release	()V
    //   592: iload 18
    //   594: ireturn
    //   595: aload 21
    //   597: invokevirtual 99	android/os/ParcelFileDescriptor:detachFd	()I
    //   600: pop
    //   601: iconst_0
    //   602: ireturn
    //   603: aload 21
    //   605: invokevirtual 99	android/os/ParcelFileDescriptor:detachFd	()I
    //   608: pop
    //   609: iconst_0
    //   610: ireturn
    //   611: aload 21
    //   613: invokevirtual 99	android/os/ParcelFileDescriptor:detachFd	()I
    //   616: pop
    //   617: iconst_0
    //   618: ireturn
    //   619: aload 21
    //   621: invokevirtual 99	android/os/ParcelFileDescriptor:detachFd	()I
    //   624: pop
    //   625: iconst_0
    //   626: ireturn
    //   627: aload 21
    //   629: invokevirtual 99	android/os/ParcelFileDescriptor:detachFd	()I
    //   632: pop
    //   633: iconst_0
    //   634: ireturn
    //   635: aload 21
    //   637: invokevirtual 99	android/os/ParcelFileDescriptor:detachFd	()I
    //   640: pop
    //   641: iconst_0
    //   642: ireturn
    //   643: aload 21
    //   645: invokevirtual 99	android/os/ParcelFileDescriptor:detachFd	()I
    //   648: pop
    //   649: iconst_0
    //   650: ireturn
    //   651: aload 21
    //   653: invokevirtual 99	android/os/ParcelFileDescriptor:detachFd	()I
    //   656: pop
    //   657: iconst_0
    //   658: ireturn
    //   659: astore_0
    //   660: aload_0
    //   661: invokevirtual 209	java/lang/Exception:printStackTrace	()V
    //   664: aload 21
    //   666: invokevirtual 99	android/os/ParcelFileDescriptor:detachFd	()I
    //   669: pop
    //   670: iconst_0
    //   671: ireturn
    //   672: iconst_0
    //   673: ireturn
    //   674: astore_0
    //   675: goto -24 -> 651
    //   678: astore_0
    //   679: goto -36 -> 643
    //   682: astore_0
    //   683: goto -48 -> 635
    //   686: astore 20
    //   688: goto -564 -> 124
    //   691: astore_0
    //   692: goto -65 -> 627
    //   695: astore_0
    //   696: goto -77 -> 619
    //   699: astore_0
    //   700: goto -89 -> 611
    //   703: astore_0
    //   704: goto -101 -> 603
    //   707: astore_0
    //   708: goto -113 -> 595
    //   711: astore_0
    //   712: goto -136 -> 576
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	715	0	paramContext	Context
    //   0	715	1	paramLong1	long
    //   0	715	3	paramInt	int
    //   0	715	4	paramLong2	long
    //   191	102	6	i	int
    //   94	458	7	j	int
    //   185	363	8	k	int
    //   188	356	9	m	int
    //   238	316	10	n	int
    //   270	280	11	i1	int
    //   216	330	12	i2	int
    //   403	139	13	i3	int
    //   86	295	14	i4	int
    //   328	176	15	i5	int
    //   129	222	16	l	long
    //   196	397	18	bool	boolean
    //   102	455	19	localObject	Object
    //   316	104	20	localBufferInfo	android.media.MediaCodec.BufferInfo
    //   686	1	20	localException	Exception
    //   42	623	21	localParcelFileDescriptor	android.os.ParcelFileDescriptor
    //   36	262	22	localMediaExtractor	android.media.MediaExtractor
    //   148	440	23	localMediaCodec	android.media.MediaCodec
    //   169	55	24	arrayOfByteBuffer	ByteBuffer[]
    // Exception table:
    //   from	to	target	type
    //   318	330	562	java/lang/Exception
    //   44	57	659	java/lang/Exception
    //   80	88	674	java/lang/Exception
    //   88	96	678	java/lang/Exception
    //   96	104	682	java/lang/Exception
    //   113	121	686	java/lang/Exception
    //   143	150	691	java/lang/Exception
    //   150	159	695	java/lang/Exception
    //   159	164	699	java/lang/Exception
    //   164	171	703	java/lang/Exception
    //   171	177	707	java/lang/Exception
    //   208	218	711	java/lang/Exception
    //   230	240	711	java/lang/Exception
    //   278	292	711	java/lang/Exception
  }
  
  private static native void nativeInitializeDestination(long paramLong1, int paramInt1, int paramInt2, long paramLong2);
  
  private static native void nativeOnChunkDecoded(long paramLong, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\media\WebAudioMediaCodecBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */