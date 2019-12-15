package com.tencent.tbs.common.service;

public class SntpClient
{
  private static final int NTP_MODE_CLIENT = 3;
  private static final int NTP_PACKET_SIZE = 48;
  private static final int NTP_PORT = 123;
  private static final int NTP_VERSION = 3;
  private static final long OFFSET_1900_TO_1970 = 2208988800L;
  private static final int ORIGINATE_TIME_OFFSET = 24;
  private static final int RECEIVE_TIME_OFFSET = 32;
  private static final int REFERENCE_TIME_OFFSET = 16;
  private static final String TAG = "SntpClient";
  private static final int TRANSMIT_TIME_OFFSET = 40;
  private long mNtpTime;
  private long mNtpTimeReference;
  private long mRoundTripTime;
  
  private long read32(byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramArrayOfByte[paramInt];
    int j = paramArrayOfByte[(paramInt + 1)];
    int k = paramArrayOfByte[(paramInt + 2)];
    int m = paramArrayOfByte[(paramInt + 3)];
    paramInt = i;
    if ((i & 0x80) == 128) {
      paramInt = (i & 0x7F) + 128;
    }
    i = j;
    if ((j & 0x80) == 128) {
      i = (j & 0x7F) + 128;
    }
    j = k;
    if ((k & 0x80) == 128) {
      j = (k & 0x7F) + 128;
    }
    k = m;
    if ((m & 0x80) == 128) {
      k = (m & 0x7F) + 128;
    }
    return (paramInt << 24) + (i << 16) + (j << 8) + k;
  }
  
  private long readTimeStamp(byte[] paramArrayOfByte, int paramInt)
  {
    return (read32(paramArrayOfByte, paramInt) - 2208988800L) * 1000L + read32(paramArrayOfByte, paramInt + 4) * 1000L / 4294967296L;
  }
  
  private void writeTimeStamp(byte[] paramArrayOfByte, int paramInt, long paramLong)
  {
    long l1 = paramLong / 1000L;
    long l2 = l1 + 2208988800L;
    int i = paramInt + 1;
    paramArrayOfByte[paramInt] = ((byte)(int)(l2 >> 24));
    paramInt = i + 1;
    paramArrayOfByte[i] = ((byte)(int)(l2 >> 16));
    i = paramInt + 1;
    paramArrayOfByte[paramInt] = ((byte)(int)(l2 >> 8));
    paramInt = i + 1;
    paramArrayOfByte[i] = ((byte)(int)(l2 >> 0));
    paramLong = (paramLong - l1 * 1000L) * 4294967296L / 1000L;
    i = paramInt + 1;
    paramArrayOfByte[paramInt] = ((byte)(int)(paramLong >> 24));
    paramInt = i + 1;
    paramArrayOfByte[i] = ((byte)(int)(paramLong >> 16));
    paramArrayOfByte[paramInt] = ((byte)(int)(paramLong >> 8));
    paramArrayOfByte[(paramInt + 1)] = ((byte)(int)(Math.random() * 255.0D));
  }
  
  public long getNtpTime()
  {
    return this.mNtpTime;
  }
  
  public long getNtpTimeReference()
  {
    return this.mNtpTimeReference;
  }
  
  public long getRoundTripTime()
  {
    return this.mRoundTripTime;
  }
  
  /* Error */
  public boolean requestTime(String paramString, int paramInt)
  {
    // Byte code:
    //   0: new 71	java/net/DatagramSocket
    //   3: dup
    //   4: invokespecial 72	java/net/DatagramSocket:<init>	()V
    //   7: astore 15
    //   9: aload 15
    //   11: iload_2
    //   12: invokevirtual 76	java/net/DatagramSocket:setSoTimeout	(I)V
    //   15: aload_1
    //   16: invokestatic 82	java/net/InetAddress:getByName	(Ljava/lang/String;)Ljava/net/InetAddress;
    //   19: astore 16
    //   21: bipush 48
    //   23: newarray <illegal type>
    //   25: astore_1
    //   26: new 84	java/net/DatagramPacket
    //   29: dup
    //   30: aload_1
    //   31: aload_1
    //   32: arraylength
    //   33: aload 16
    //   35: bipush 123
    //   37: invokespecial 87	java/net/DatagramPacket:<init>	([BILjava/net/InetAddress;I)V
    //   40: astore 16
    //   42: aload_1
    //   43: iconst_0
    //   44: bipush 27
    //   46: bastore
    //   47: invokestatic 92	java/lang/System:currentTimeMillis	()J
    //   50: lstore 7
    //   52: invokestatic 97	android/os/SystemClock:elapsedRealtime	()J
    //   55: lstore 5
    //   57: aload_0
    //   58: aload_1
    //   59: bipush 40
    //   61: lload 7
    //   63: invokespecial 99	com/tencent/tbs/common/service/SntpClient:writeTimeStamp	([BIJ)V
    //   66: aload 15
    //   68: aload 16
    //   70: invokevirtual 103	java/net/DatagramSocket:send	(Ljava/net/DatagramPacket;)V
    //   73: aload 15
    //   75: new 84	java/net/DatagramPacket
    //   78: dup
    //   79: aload_1
    //   80: aload_1
    //   81: arraylength
    //   82: invokespecial 106	java/net/DatagramPacket:<init>	([BI)V
    //   85: invokevirtual 109	java/net/DatagramSocket:receive	(Ljava/net/DatagramPacket;)V
    //   88: invokestatic 97	android/os/SystemClock:elapsedRealtime	()J
    //   91: lstore_3
    //   92: lload_3
    //   93: lload 5
    //   95: lsub
    //   96: lstore 5
    //   98: lload 7
    //   100: lload 5
    //   102: ladd
    //   103: lstore 7
    //   105: aload_0
    //   106: aload_1
    //   107: bipush 24
    //   109: invokespecial 111	com/tencent/tbs/common/service/SntpClient:readTimeStamp	([BI)J
    //   112: lstore 9
    //   114: aload_0
    //   115: aload_1
    //   116: bipush 32
    //   118: invokespecial 111	com/tencent/tbs/common/service/SntpClient:readTimeStamp	([BI)J
    //   121: lstore 11
    //   123: aload_0
    //   124: aload_1
    //   125: bipush 40
    //   127: invokespecial 111	com/tencent/tbs/common/service/SntpClient:readTimeStamp	([BI)J
    //   130: lstore 13
    //   132: aload_0
    //   133: lload 7
    //   135: lload 11
    //   137: lload 9
    //   139: lsub
    //   140: lload 13
    //   142: lload 7
    //   144: lsub
    //   145: ladd
    //   146: ldc2_w 112
    //   149: ldiv
    //   150: ladd
    //   151: putfield 59	com/tencent/tbs/common/service/SntpClient:mNtpTime	J
    //   154: aload_0
    //   155: lload_3
    //   156: putfield 62	com/tencent/tbs/common/service/SntpClient:mNtpTimeReference	J
    //   159: aload_0
    //   160: lload 5
    //   162: lload 13
    //   164: lload 11
    //   166: lsub
    //   167: lsub
    //   168: putfield 65	com/tencent/tbs/common/service/SntpClient:mRoundTripTime	J
    //   171: aload 15
    //   173: invokevirtual 116	java/net/DatagramSocket:close	()V
    //   176: iconst_1
    //   177: ireturn
    //   178: astore_1
    //   179: goto +10 -> 189
    //   182: goto +22 -> 204
    //   185: astore_1
    //   186: aconst_null
    //   187: astore 15
    //   189: aload 15
    //   191: ifnull +8 -> 199
    //   194: aload 15
    //   196: invokevirtual 116	java/net/DatagramSocket:close	()V
    //   199: aload_1
    //   200: athrow
    //   201: aconst_null
    //   202: astore 15
    //   204: aload 15
    //   206: ifnull +8 -> 214
    //   209: aload 15
    //   211: invokevirtual 116	java/net/DatagramSocket:close	()V
    //   214: iconst_0
    //   215: ireturn
    //   216: astore_1
    //   217: goto -16 -> 201
    //   220: astore_1
    //   221: goto -39 -> 182
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	224	0	this	SntpClient
    //   0	224	1	paramString	String
    //   0	224	2	paramInt	int
    //   91	65	3	l1	long
    //   55	106	5	l2	long
    //   50	93	7	l3	long
    //   112	26	9	l4	long
    //   121	44	11	l5	long
    //   130	33	13	l6	long
    //   7	203	15	localDatagramSocket	java.net.DatagramSocket
    //   19	50	16	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   9	42	178	finally
    //   47	92	178	finally
    //   105	171	178	finally
    //   0	9	185	finally
    //   0	9	216	java/lang/Exception
    //   9	42	220	java/lang/Exception
    //   47	92	220	java/lang/Exception
    //   105	171	220	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\service\SntpClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */