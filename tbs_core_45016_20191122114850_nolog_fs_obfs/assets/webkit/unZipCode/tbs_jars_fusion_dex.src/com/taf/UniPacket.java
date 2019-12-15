package com.taf;

import java.nio.ByteBuffer;
import java.util.HashMap;

public class UniPacket
  extends UniAttribute
{
  public static final int UniPacketHeadSize = 4;
  static Object jdField_a_of_type_JavaLangObject = new Object();
  static HashMap<String, byte[]> d;
  static HashMap<String, HashMap<String, byte[]>> e;
  public RequestPacket _package = new RequestPacket();
  private int jdField_a_of_type_Int = 0;
  
  public UniPacket()
  {
    this._package.iVersion = 2;
  }
  
  public UniPacket(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      useVersion3();
      return;
    }
    this._package.iVersion = 2;
  }
  
  private void a()
  {
    JceInputStream localJceInputStream = new JceInputStream(this._package.sBuffer);
    localJceInputStream.setServerEncoding(this.jdField_a_of_type_JavaLangString);
    if (d == null)
    {
      d = new HashMap();
      d.put("", new byte[0]);
    }
    this.c = localJceInputStream.readMap(d, 0, false);
  }
  
  private void b()
  {
    JceInputStream localJceInputStream = new JceInputStream(this._package.sBuffer);
    localJceInputStream.setServerEncoding(this.jdField_a_of_type_JavaLangString);
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if (e == null)
      {
        e = new HashMap();
        HashMap localHashMap = new HashMap();
        localHashMap.put("", new byte[0]);
        e.put("", localHashMap);
      }
      this.jdField_a_of_type_JavaUtilHashMap = localJceInputStream.readMap(e, 0, false);
      this.b = new HashMap();
      return;
    }
  }
  
  public void decode(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte.length >= 4)
    {
      ByteBuffer localByteBuffer = ByteBuffer.allocate(4);
      byte[] arrayOfByte = new byte[4];
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, arrayOfByte.length);
      localByteBuffer.put(arrayOfByte).flip();
      this.jdField_a_of_type_Int = localByteBuffer.getInt();
      try
      {
        paramArrayOfByte = new JceInputStream(paramArrayOfByte, 4);
        paramArrayOfByte.setServerEncoding(this.jdField_a_of_type_JavaLangString);
        this._package.readFrom(paramArrayOfByte);
        if (this._package.iVersion == 3)
        {
          a();
          return;
        }
        this.c = null;
        b();
        return;
      }
      catch (Exception paramArrayOfByte)
      {
        throw new RuntimeException(paramArrayOfByte);
      }
    }
    throw new IllegalArgumentException("decode package must include size head");
  }
  
  public void decodeVersion2(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte.length >= 4)
    {
      ByteBuffer localByteBuffer = ByteBuffer.allocate(4);
      byte[] arrayOfByte = new byte[4];
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, arrayOfByte.length);
      localByteBuffer.put(arrayOfByte).flip();
      this.jdField_a_of_type_Int = localByteBuffer.getInt();
      try
      {
        paramArrayOfByte = new JceInputStream(paramArrayOfByte, 4);
        paramArrayOfByte.setServerEncoding(this.jdField_a_of_type_JavaLangString);
        this._package.readFrom(paramArrayOfByte);
        b();
        return;
      }
      catch (Exception paramArrayOfByte)
      {
        throw new RuntimeException(paramArrayOfByte);
      }
    }
    throw new IllegalArgumentException("decode package must include size head");
  }
  
  public void decodeVersion3(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte.length >= 4) {
      try
      {
        paramArrayOfByte = new JceInputStream(paramArrayOfByte, 4);
        paramArrayOfByte.setServerEncoding(this.jdField_a_of_type_JavaLangString);
        this._package.readFrom(paramArrayOfByte);
        a();
        return;
      }
      catch (Exception paramArrayOfByte)
      {
        throw new RuntimeException(paramArrayOfByte);
      }
    }
    throw new IllegalArgumentException("decode package must include size head");
  }
  
  public byte[] encode()
  {
    if (this._package.iVersion == 2)
    {
      if ((this._package.sServantName != null) && (!this._package.sServantName.equals("")))
      {
        if ((this._package.sFuncName == null) || (this._package.sFuncName.equals(""))) {
          throw new IllegalArgumentException("funcName can not is null");
        }
      }
      else {
        throw new IllegalArgumentException("servantName can not is null");
      }
    }
    else
    {
      if (this._package.sServantName == null) {
        this._package.sServantName = "";
      }
      if (this._package.sFuncName == null) {
        this._package.sFuncName = "";
      }
    }
    JceOutputStream localJceOutputStream = JceSynchronizedPool.getInstance().acquireout();
    localJceOutputStream.setServerEncoding(this.jdField_a_of_type_JavaLangString);
    if ((this._package.iVersion != 2) && (this._package.iVersion != 1)) {
      localJceOutputStream.write(this.c, 0);
    } else {
      localJceOutputStream.write(this.jdField_a_of_type_JavaUtilHashMap, 0);
    }
    this._package.sBuffer = JceUtil.getJceBufArray(localJceOutputStream.getByteBuffer());
    localJceOutputStream.reInit();
    localJceOutputStream.setServerEncoding(this.jdField_a_of_type_JavaLangString);
    this._package.writeTo(localJceOutputStream);
    byte[] arrayOfByte = JceUtil.getJceBufArray(localJceOutputStream.getByteBuffer());
    int i = arrayOfByte.length + 4;
    ByteBuffer localByteBuffer = ByteBuffer.allocate(i);
    localByteBuffer.putInt(i).put(arrayOfByte).flip();
    JceSynchronizedPool.getInstance().releaseOut(localJceOutputStream);
    return localByteBuffer.array();
  }
  
  public String getFuncName()
  {
    return this._package.sFuncName;
  }
  
  public int getPackageVersion()
  {
    return this._package.iVersion;
  }
  
  public int getPacketSize()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public int getRequestId()
  {
    return this._package.iRequestId;
  }
  
  public String getServantName()
  {
    return this._package.sServantName;
  }
  
  public <T> void put(String paramString, T paramT)
  {
    if (!paramString.startsWith("."))
    {
      super.put(paramString, paramT);
      return;
    }
    paramT = new StringBuilder();
    paramT.append("put name can not startwith . , now is ");
    paramT.append(paramString);
    throw new IllegalArgumentException(paramT.toString());
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this._package.readFrom(paramJceInputStream);
  }
  
  public void setFuncName(String paramString)
  {
    this._package.sFuncName = paramString;
  }
  
  public void setRequestId(int paramInt)
  {
    this._package.iRequestId = paramInt;
  }
  
  public void setServantName(String paramString)
  {
    this._package.sServantName = paramString;
  }
  
  public void useVersion3()
  {
    super.useVersion3();
    this._package.iVersion = 3;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    this._package.writeTo(paramJceOutputStream);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\taf\UniPacket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */