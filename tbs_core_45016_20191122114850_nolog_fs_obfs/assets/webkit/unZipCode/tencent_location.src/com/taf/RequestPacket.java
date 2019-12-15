package com.taf;

import java.util.Map;

public final class RequestPacket
  extends JceStruct
{
  static Object jdField_a_of_type_JavaLangObject = new Object();
  static Map<String, String> jdField_a_of_type_JavaUtilMap;
  static byte[] jdField_a_of_type_ArrayOfByte = null;
  public byte cPacketType = 0;
  public Map<String, String> context;
  public int iMessageType = 0;
  public int iRequestId = 0;
  public int iTimeout = 0;
  public short iVersion = 0;
  public byte[] sBuffer;
  public String sFuncName = null;
  public String sServantName = null;
  public Map<String, String> status;
  
  static
  {
    jdField_a_of_type_JavaUtilMap = null;
  }
  
  public Object clone()
  {
    try
    {
      Object localObject = super.clone();
      return localObject;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      for (;;) {}
    }
    if (jdField_a_of_type_Boolean) {
      return null;
    }
    throw new AssertionError();
  }
  
  /* Error */
  public void readFrom(JceInputStream paramJceInputStream)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_0
    //   3: getfield 49	com/taf/RequestPacket:iVersion	S
    //   6: iconst_1
    //   7: iconst_1
    //   8: invokevirtual 80	com/taf/JceInputStream:read	(SIZ)S
    //   11: putfield 49	com/taf/RequestPacket:iVersion	S
    //   14: aload_0
    //   15: aload_1
    //   16: aload_0
    //   17: getfield 51	com/taf/RequestPacket:cPacketType	B
    //   20: iconst_2
    //   21: iconst_1
    //   22: invokevirtual 83	com/taf/JceInputStream:read	(BIZ)B
    //   25: putfield 51	com/taf/RequestPacket:cPacketType	B
    //   28: aload_0
    //   29: aload_1
    //   30: aload_0
    //   31: getfield 53	com/taf/RequestPacket:iMessageType	I
    //   34: iconst_3
    //   35: iconst_1
    //   36: invokevirtual 86	com/taf/JceInputStream:read	(IIZ)I
    //   39: putfield 53	com/taf/RequestPacket:iMessageType	I
    //   42: aload_0
    //   43: aload_1
    //   44: aload_0
    //   45: getfield 55	com/taf/RequestPacket:iRequestId	I
    //   48: iconst_4
    //   49: iconst_1
    //   50: invokevirtual 86	com/taf/JceInputStream:read	(IIZ)I
    //   53: putfield 55	com/taf/RequestPacket:iRequestId	I
    //   56: aload_0
    //   57: aload_1
    //   58: iconst_5
    //   59: iconst_1
    //   60: invokevirtual 90	com/taf/JceInputStream:readString	(IZ)Ljava/lang/String;
    //   63: putfield 57	com/taf/RequestPacket:sServantName	Ljava/lang/String;
    //   66: aload_0
    //   67: aload_1
    //   68: bipush 6
    //   70: iconst_1
    //   71: invokevirtual 90	com/taf/JceInputStream:readString	(IZ)Ljava/lang/String;
    //   74: putfield 59	com/taf/RequestPacket:sFuncName	Ljava/lang/String;
    //   77: getstatic 36	com/taf/RequestPacket:jdField_a_of_type_ArrayOfByte	[B
    //   80: ifnonnull +13 -> 93
    //   83: iconst_1
    //   84: newarray <illegal type>
    //   86: dup
    //   87: iconst_0
    //   88: iconst_0
    //   89: bastore
    //   90: putstatic 36	com/taf/RequestPacket:jdField_a_of_type_ArrayOfByte	[B
    //   93: aload_0
    //   94: aload_1
    //   95: getstatic 36	com/taf/RequestPacket:jdField_a_of_type_ArrayOfByte	[B
    //   98: bipush 7
    //   100: iconst_1
    //   101: invokevirtual 93	com/taf/JceInputStream:read	([BIZ)[B
    //   104: checkcast 94	[B
    //   107: putfield 96	com/taf/RequestPacket:sBuffer	[B
    //   110: aload_0
    //   111: aload_1
    //   112: aload_0
    //   113: getfield 61	com/taf/RequestPacket:iTimeout	I
    //   116: bipush 8
    //   118: iconst_1
    //   119: invokevirtual 86	com/taf/JceInputStream:read	(IIZ)I
    //   122: putfield 61	com/taf/RequestPacket:iTimeout	I
    //   125: getstatic 45	com/taf/RequestPacket:jdField_a_of_type_JavaLangObject	Ljava/lang/Object;
    //   128: astore_2
    //   129: aload_2
    //   130: monitorenter
    //   131: getstatic 38	com/taf/RequestPacket:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   134: ifnonnull +26 -> 160
    //   137: new 98	java/util/HashMap
    //   140: dup
    //   141: invokespecial 99	java/util/HashMap:<init>	()V
    //   144: putstatic 38	com/taf/RequestPacket:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   147: getstatic 38	com/taf/RequestPacket:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   150: ldc 101
    //   152: ldc 101
    //   154: invokeinterface 107 3 0
    //   159: pop
    //   160: aload_2
    //   161: monitorexit
    //   162: aload_0
    //   163: aload_1
    //   164: getstatic 38	com/taf/RequestPacket:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   167: bipush 9
    //   169: iconst_1
    //   170: invokevirtual 110	com/taf/JceInputStream:read	(Ljava/lang/Object;IZ)Ljava/lang/Object;
    //   173: checkcast 103	java/util/Map
    //   176: putfield 112	com/taf/RequestPacket:context	Ljava/util/Map;
    //   179: aload_0
    //   180: aload_1
    //   181: getstatic 38	com/taf/RequestPacket:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   184: bipush 10
    //   186: iconst_1
    //   187: invokevirtual 110	com/taf/JceInputStream:read	(Ljava/lang/Object;IZ)Ljava/lang/Object;
    //   190: checkcast 103	java/util/Map
    //   193: putfield 114	com/taf/RequestPacket:status	Ljava/util/Map;
    //   196: return
    //   197: astore_1
    //   198: aload_2
    //   199: monitorexit
    //   200: aload_1
    //   201: athrow
    //   202: astore_1
    //   203: aload_1
    //   204: invokevirtual 117	java/lang/Exception:printStackTrace	()V
    //   207: getstatic 123	java/lang/System:out	Ljava/io/PrintStream;
    //   210: astore_2
    //   211: new 125	java/lang/StringBuilder
    //   214: dup
    //   215: invokespecial 126	java/lang/StringBuilder:<init>	()V
    //   218: astore_3
    //   219: aload_3
    //   220: ldc -128
    //   222: invokevirtual 132	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   225: pop
    //   226: aload_3
    //   227: aload_0
    //   228: getfield 96	com/taf/RequestPacket:sBuffer	[B
    //   231: invokestatic 138	com/taf/HexUtil:bytes2HexStr	([B)Ljava/lang/String;
    //   234: invokevirtual 132	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   237: pop
    //   238: aload_2
    //   239: aload_3
    //   240: invokevirtual 142	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   243: invokevirtual 148	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   246: new 150	java/lang/RuntimeException
    //   249: dup
    //   250: aload_1
    //   251: invokespecial 153	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   254: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	255	0	this	RequestPacket
    //   0	255	1	paramJceInputStream	JceInputStream
    //   218	22	3	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   131	160	197	finally
    //   160	162	197	finally
    //   198	200	197	finally
    //   0	93	202	java/lang/Exception
    //   93	131	202	java/lang/Exception
    //   162	196	202	java/lang/Exception
    //   200	202	202	java/lang/Exception
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iVersion, 1);
    paramJceOutputStream.write(this.cPacketType, 2);
    paramJceOutputStream.write(this.iMessageType, 3);
    paramJceOutputStream.write(this.iRequestId, 4);
    paramJceOutputStream.write(this.sServantName, 5);
    paramJceOutputStream.write(this.sFuncName, 6);
    paramJceOutputStream.write(this.sBuffer, 7);
    paramJceOutputStream.write(this.iTimeout, 8);
    paramJceOutputStream.write(this.context, 9);
    paramJceOutputStream.write(this.status, 10);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\taf\RequestPacket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */