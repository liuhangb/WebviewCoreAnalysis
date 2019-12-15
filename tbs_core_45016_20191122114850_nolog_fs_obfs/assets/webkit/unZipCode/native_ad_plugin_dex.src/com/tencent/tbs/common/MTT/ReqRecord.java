package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ReqRecord
  extends JceStruct
  implements Cloneable
{
  public int iConnectTime = 0;
  public int iConnectionUseCount = 0;
  public int iDNSTime = 0;
  public int iFirstScreenTime = 0;
  public int iFirstWordTime = 0;
  public long iRecordTime = 0L;
  public int iRecvRspTime = 0;
  public int iResType = 0;
  public int iRet = 0;
  public int iReuseConnectTime = 0;
  public int iSendingTime = 0;
  public int iSize = 0;
  public int iWaitRspTime = 0;
  public String sProxyData = "";
  public String sUrl = "";
  public String sWebSiteIP = "";
  
  public void deserialize(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    paramArrayOfByte = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte, paramInt1, paramInt2));
    this.sUrl = paramArrayOfByte.readUTF();
    this.iResType = paramArrayOfByte.readInt();
    this.iRecordTime = paramArrayOfByte.readLong();
    this.iRet = paramArrayOfByte.readInt();
    this.iDNSTime = paramArrayOfByte.readInt();
    this.iConnectTime = paramArrayOfByte.readInt();
    this.iSendingTime = paramArrayOfByte.readInt();
    this.iWaitRspTime = paramArrayOfByte.readInt();
    this.iRecvRspTime = paramArrayOfByte.readInt();
    this.iSize = paramArrayOfByte.readInt();
    this.sWebSiteIP = paramArrayOfByte.readUTF();
    this.sProxyData = paramArrayOfByte.readUTF();
    this.iReuseConnectTime = paramArrayOfByte.readInt();
    this.iFirstWordTime = paramArrayOfByte.readInt();
    this.iFirstScreenTime = paramArrayOfByte.readInt();
    this.iConnectionUseCount = paramArrayOfByte.readInt();
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sUrl = paramJceInputStream.readString(0, false);
    this.iResType = paramJceInputStream.read(this.iResType, 1, false);
    this.iRecordTime = paramJceInputStream.read(this.iRecordTime, 2, false);
    this.iRet = paramJceInputStream.read(this.iRet, 3, false);
    this.iDNSTime = paramJceInputStream.read(this.iDNSTime, 4, false);
    this.iConnectTime = paramJceInputStream.read(this.iConnectTime, 5, false);
    this.iSendingTime = paramJceInputStream.read(this.iSendingTime, 6, false);
    this.iWaitRspTime = paramJceInputStream.read(this.iWaitRspTime, 7, false);
    this.iRecvRspTime = paramJceInputStream.read(this.iRecvRspTime, 8, false);
    this.iSize = paramJceInputStream.read(this.iSize, 9, false);
    this.sWebSiteIP = paramJceInputStream.readString(10, false);
    this.sProxyData = paramJceInputStream.readString(11, false);
    this.iReuseConnectTime = paramJceInputStream.read(this.iReuseConnectTime, 12, false);
    this.iFirstWordTime = paramJceInputStream.read(this.iFirstWordTime, 13, false);
    this.iFirstScreenTime = paramJceInputStream.read(this.iFirstScreenTime, 14, false);
    this.iConnectionUseCount = paramJceInputStream.read(this.iConnectionUseCount, 15, false);
  }
  
  public byte[] serialize()
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    if (this.sUrl == null) {
      this.sUrl = "";
    }
    localDataOutputStream.writeUTF(this.sUrl);
    localDataOutputStream.writeInt(this.iResType);
    localDataOutputStream.writeLong(this.iRecordTime);
    localDataOutputStream.writeInt(this.iRet);
    localDataOutputStream.writeInt(this.iDNSTime);
    localDataOutputStream.writeInt(this.iConnectTime);
    localDataOutputStream.writeInt(this.iSendingTime);
    localDataOutputStream.writeInt(this.iWaitRspTime);
    localDataOutputStream.writeInt(this.iRecvRspTime);
    localDataOutputStream.writeInt(this.iSize);
    localDataOutputStream.writeUTF(this.sWebSiteIP);
    localDataOutputStream.writeUTF(this.sProxyData);
    localDataOutputStream.writeInt(this.iReuseConnectTime);
    localDataOutputStream.writeInt(this.iFirstWordTime);
    localDataOutputStream.writeInt(this.iFirstScreenTime);
    localDataOutputStream.writeInt(this.iConnectionUseCount);
    localByteArrayOutputStream.flush();
    return localByteArrayOutputStream.toByteArray();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ReqRecord [sUrl=");
    localStringBuilder.append(this.sUrl);
    localStringBuilder.append(", iResType=");
    localStringBuilder.append(this.iResType);
    localStringBuilder.append(", iRecordTime=");
    localStringBuilder.append(this.iRecordTime);
    localStringBuilder.append(", iRet=");
    localStringBuilder.append(this.iRet);
    localStringBuilder.append(", iDNSTime=");
    localStringBuilder.append(this.iDNSTime);
    localStringBuilder.append(", iConnectTime=");
    localStringBuilder.append(this.iConnectTime);
    localStringBuilder.append(", iSendingTime=");
    localStringBuilder.append(this.iSendingTime);
    localStringBuilder.append(", iWaitRspTime=");
    localStringBuilder.append(this.iWaitRspTime);
    localStringBuilder.append(", iRecvRspTime=");
    localStringBuilder.append(this.iRecvRspTime);
    localStringBuilder.append(", iSize=");
    localStringBuilder.append(this.iSize);
    localStringBuilder.append("sWebSiteIP=");
    localStringBuilder.append(this.sWebSiteIP);
    localStringBuilder.append("sProxyData=");
    localStringBuilder.append(this.sProxyData);
    localStringBuilder.append(", iConnectModuleFeedbackTime=");
    localStringBuilder.append(this.iReuseConnectTime);
    localStringBuilder.append(", iConnectionUseCount=");
    localStringBuilder.append(this.iConnectionUseCount);
    localStringBuilder.append(", iFirstWordTime=");
    localStringBuilder.append(this.iFirstWordTime);
    localStringBuilder.append(", iFirstScreenTime=");
    localStringBuilder.append(this.iFirstScreenTime);
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    String str = this.sUrl;
    if (str != null) {
      paramJceOutputStream.write(str, 0);
    }
    paramJceOutputStream.write(this.iResType, 1);
    paramJceOutputStream.write(this.iRecordTime, 2);
    paramJceOutputStream.write(this.iRet, 3);
    paramJceOutputStream.write(this.iDNSTime, 4);
    paramJceOutputStream.write(this.iConnectTime, 5);
    paramJceOutputStream.write(this.iSendingTime, 6);
    paramJceOutputStream.write(this.iWaitRspTime, 7);
    paramJceOutputStream.write(this.iRecvRspTime, 8);
    paramJceOutputStream.write(this.iSize, 9);
    str = this.sWebSiteIP;
    if (str != null) {
      paramJceOutputStream.write(str, 10);
    }
    str = this.sProxyData;
    if (str != null) {
      paramJceOutputStream.write(str, 11);
    }
    paramJceOutputStream.write(this.iReuseConnectTime, 12);
    paramJceOutputStream.write(this.iFirstWordTime, 13);
    paramJceOutputStream.write(this.iFirstScreenTime, 14);
    paramJceOutputStream.write(this.iConnectionUseCount, 15);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\ReqRecord.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */