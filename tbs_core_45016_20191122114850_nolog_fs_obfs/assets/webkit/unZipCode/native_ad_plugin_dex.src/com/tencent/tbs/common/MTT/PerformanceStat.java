package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;

public final class PerformanceStat
  extends JceStruct
  implements Cloneable
{
  static ArrayList<ReqRecord> cache_vReqRecord;
  public int iNetLoadTime = 0;
  public int iPageVisitCount = 0;
  public byte iProxyType = 0;
  public double iSubAvgConnectTime = 0.0D;
  public double iSubAvgDNSTime = 0.0D;
  public double iSubAvgWaitRspTime = 0.0D;
  public int iSubHTTPRequestCount = 0;
  public int iSubResouceCostTime = 0;
  public int iSubResourceSize = 0;
  public int iTotalTime = 0;
  public String sAPN = "";
  public String sDevice = "";
  public String sProxyIP = "";
  public String sQQ = "";
  public String sRemoteIP = "";
  public String sUrl = "";
  public ArrayList<ReqRecord> vReqRecord = null;
  
  public PerformanceStat() {}
  
  public PerformanceStat(String paramString1, String paramString2, String paramString3, String paramString4, byte paramByte, String paramString5, String paramString6, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, ArrayList<ReqRecord> paramArrayList, int paramInt9)
  {
    this.sDevice = paramString1;
    this.sQQ = paramString2;
    this.sUrl = paramString3;
    this.sAPN = paramString4;
    this.iProxyType = paramByte;
    this.sRemoteIP = paramString5;
    this.sProxyIP = paramString6;
    this.iTotalTime = paramInt1;
    this.iSubAvgDNSTime = paramInt2;
    this.iSubAvgConnectTime = paramInt3;
    this.iSubAvgWaitRspTime = paramInt4;
    this.iSubResourceSize = paramInt5;
    this.iSubResouceCostTime = paramInt6;
    this.iSubHTTPRequestCount = paramInt7;
    this.iPageVisitCount = paramInt8;
    this.vReqRecord = paramArrayList;
    this.iNetLoadTime = paramInt9;
  }
  
  public void addValues(PerformanceStat paramPerformanceStat)
  {
    if (paramPerformanceStat == null) {
      return;
    }
    this.iTotalTime += paramPerformanceStat.iTotalTime;
    this.iSubAvgDNSTime += paramPerformanceStat.iSubAvgDNSTime;
    this.iSubAvgConnectTime += paramPerformanceStat.iSubAvgConnectTime;
    this.iSubAvgWaitRspTime += paramPerformanceStat.iSubAvgWaitRspTime;
    this.iSubResourceSize += paramPerformanceStat.iSubResourceSize;
    this.iSubResouceCostTime += paramPerformanceStat.iSubResouceCostTime;
    this.iSubHTTPRequestCount += paramPerformanceStat.iSubHTTPRequestCount;
    this.iPageVisitCount += paramPerformanceStat.iPageVisitCount;
    this.iNetLoadTime += paramPerformanceStat.iNetLoadTime;
    if (paramPerformanceStat.vReqRecord != null)
    {
      if (this.vReqRecord == null) {
        this.vReqRecord = new ArrayList();
      }
      ArrayList localArrayList1 = this.vReqRecord;
      ArrayList localArrayList2 = paramPerformanceStat.vReqRecord;
      if (localArrayList1 == localArrayList2) {
        return;
      }
      int i = 0;
      int j = localArrayList2.size();
      while (i < j)
      {
        this.vReqRecord.add(paramPerformanceStat.vReqRecord.get(i));
        i += 1;
      }
    }
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
    return null;
  }
  
  public void deserialize(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    paramArrayOfByte = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte, paramInt1, paramInt2));
    this.sDevice = paramArrayOfByte.readUTF();
    this.sQQ = paramArrayOfByte.readUTF();
    this.sUrl = paramArrayOfByte.readUTF();
    this.sAPN = paramArrayOfByte.readUTF();
    this.iProxyType = paramArrayOfByte.readByte();
    this.sRemoteIP = paramArrayOfByte.readUTF();
    this.sProxyIP = paramArrayOfByte.readUTF();
    this.iTotalTime = paramArrayOfByte.readInt();
    this.iSubAvgDNSTime = paramArrayOfByte.readDouble();
    this.iSubAvgConnectTime = paramArrayOfByte.readDouble();
    this.iSubAvgWaitRspTime = paramArrayOfByte.readDouble();
    this.iSubResourceSize = paramArrayOfByte.readInt();
    this.iSubResouceCostTime = paramArrayOfByte.readInt();
    this.iSubHTTPRequestCount = paramArrayOfByte.readInt();
    this.iPageVisitCount = paramArrayOfByte.readInt();
    paramInt2 = paramArrayOfByte.readInt();
    if (paramInt2 > 0)
    {
      this.vReqRecord = new ArrayList(paramInt2);
      paramInt1 = 0;
      while (paramInt1 < paramInt2)
      {
        ReqRecord localReqRecord = new ReqRecord();
        ByteBuffer localByteBuffer = FileUtils.read(paramArrayOfByte, paramArrayOfByte.readInt());
        localReqRecord.deserialize(localByteBuffer.array(), 0, localByteBuffer.position());
        FileUtils.getInstance().releaseByteBuffer(localByteBuffer);
        this.vReqRecord.add(localReqRecord);
        paramInt1 += 1;
      }
    }
    this.iNetLoadTime = paramArrayOfByte.readInt();
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = false;
    if (paramObject == null) {
      return false;
    }
    paramObject = (PerformanceStat)paramObject;
    boolean bool1 = bool2;
    if (StringUtils.isStringEqual(this.sDevice, ((PerformanceStat)paramObject).sDevice))
    {
      bool1 = bool2;
      if (StringUtils.isStringEqual(this.sQQ, ((PerformanceStat)paramObject).sQQ))
      {
        bool1 = bool2;
        if (StringUtils.isStringEqual(this.sUrl, ((PerformanceStat)paramObject).sUrl))
        {
          bool1 = bool2;
          if (StringUtils.isStringEqual(this.sAPN, ((PerformanceStat)paramObject).sAPN))
          {
            bool1 = bool2;
            if (this.iProxyType == ((PerformanceStat)paramObject).iProxyType)
            {
              bool1 = bool2;
              if (StringUtils.isStringEqual(this.sRemoteIP, ((PerformanceStat)paramObject).sRemoteIP))
              {
                bool1 = bool2;
                if (StringUtils.isStringEqual(this.sProxyIP, ((PerformanceStat)paramObject).sProxyIP))
                {
                  bool1 = bool2;
                  if (this.iTotalTime == ((PerformanceStat)paramObject).iTotalTime)
                  {
                    bool1 = bool2;
                    if (this.iSubAvgDNSTime == ((PerformanceStat)paramObject).iSubAvgDNSTime)
                    {
                      bool1 = bool2;
                      if (this.iSubAvgConnectTime == ((PerformanceStat)paramObject).iSubAvgConnectTime)
                      {
                        bool1 = bool2;
                        if (this.iSubAvgWaitRspTime == ((PerformanceStat)paramObject).iSubAvgWaitRspTime)
                        {
                          bool1 = bool2;
                          if (this.iSubResourceSize == ((PerformanceStat)paramObject).iSubResourceSize)
                          {
                            bool1 = bool2;
                            if (this.iSubResouceCostTime == ((PerformanceStat)paramObject).iSubResouceCostTime)
                            {
                              bool1 = bool2;
                              if (this.iSubHTTPRequestCount == ((PerformanceStat)paramObject).iSubHTTPRequestCount)
                              {
                                bool1 = bool2;
                                if (this.iPageVisitCount == ((PerformanceStat)paramObject).iPageVisitCount)
                                {
                                  bool1 = bool2;
                                  if (this.vReqRecord == ((PerformanceStat)paramObject).vReqRecord)
                                  {
                                    bool1 = bool2;
                                    if (this.iNetLoadTime == ((PerformanceStat)paramObject).iNetLoadTime) {
                                      bool1 = true;
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return bool1;
  }
  
  public int hashCode()
  {
    try
    {
      throw new Exception("Need define key first!");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sDevice = paramJceInputStream.readString(0, false);
    this.sQQ = paramJceInputStream.readString(1, false);
    this.sUrl = paramJceInputStream.readString(2, false);
    this.sAPN = paramJceInputStream.readString(3, false);
    this.iProxyType = paramJceInputStream.read(this.iProxyType, 4, false);
    this.sRemoteIP = paramJceInputStream.readString(5, false);
    this.sProxyIP = paramJceInputStream.readString(6, false);
    this.iTotalTime = paramJceInputStream.read(this.iTotalTime, 7, false);
    this.iSubAvgDNSTime = paramJceInputStream.read((int)this.iSubAvgDNSTime, 8, false);
    this.iSubAvgConnectTime = paramJceInputStream.read((int)this.iSubAvgConnectTime, 9, false);
    this.iSubAvgWaitRspTime = paramJceInputStream.read((int)this.iSubAvgWaitRspTime, 10, false);
    this.iSubResourceSize = paramJceInputStream.read(this.iSubResourceSize, 11, false);
    this.iSubResouceCostTime = paramJceInputStream.read(this.iSubResouceCostTime, 12, false);
    this.iSubHTTPRequestCount = paramJceInputStream.read(this.iSubHTTPRequestCount, 13, false);
    this.iPageVisitCount = paramJceInputStream.read(this.iPageVisitCount, 14, false);
    if (cache_vReqRecord == null)
    {
      cache_vReqRecord = new ArrayList();
      ReqRecord localReqRecord = new ReqRecord();
      cache_vReqRecord.add(localReqRecord);
    }
    this.vReqRecord = ((ArrayList)paramJceInputStream.read(cache_vReqRecord, 15, false));
    this.iTotalTime = paramJceInputStream.read(this.iNetLoadTime, 16, false);
  }
  
  public byte[] serialize()
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    if (this.sDevice == null) {
      this.sDevice = "";
    }
    localDataOutputStream.writeUTF(this.sDevice);
    if (this.sQQ == null) {
      this.sQQ = "";
    }
    localDataOutputStream.writeUTF(this.sQQ);
    if (this.sUrl == null) {
      this.sUrl = "";
    }
    localDataOutputStream.writeUTF(this.sUrl);
    if (this.sAPN == null) {
      this.sAPN = "";
    }
    localDataOutputStream.writeUTF(this.sAPN);
    localDataOutputStream.writeByte(this.iProxyType);
    if (this.sRemoteIP == null) {
      this.sRemoteIP = "";
    }
    localDataOutputStream.writeUTF(this.sRemoteIP);
    if (this.sProxyIP == null) {
      this.sProxyIP = "";
    }
    localDataOutputStream.writeUTF(this.sProxyIP);
    localDataOutputStream.writeInt(this.iTotalTime);
    localDataOutputStream.writeDouble(this.iSubAvgDNSTime);
    localDataOutputStream.writeDouble(this.iSubAvgConnectTime);
    localDataOutputStream.writeDouble(this.iSubAvgWaitRspTime);
    localDataOutputStream.writeInt(this.iSubResourceSize);
    localDataOutputStream.writeInt(this.iSubResouceCostTime);
    localDataOutputStream.writeInt(this.iSubHTTPRequestCount);
    localDataOutputStream.writeInt(this.iPageVisitCount);
    Object localObject = this.vReqRecord;
    if (localObject == null)
    {
      localDataOutputStream.writeInt(-1);
    }
    else
    {
      int j = ((ArrayList)localObject).size();
      localDataOutputStream.writeInt(j);
      int i = 0;
      while (i < j)
      {
        localObject = ((ReqRecord)this.vReqRecord.get(i)).serialize();
        localDataOutputStream.writeInt(localObject.length);
        localDataOutputStream.write((byte[])localObject, 0, localObject.length);
        i += 1;
      }
    }
    localDataOutputStream.writeInt(this.iNetLoadTime);
    localByteArrayOutputStream.flush();
    return localByteArrayOutputStream.toByteArray();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("PerformanceStat [sDevice=");
    localStringBuilder.append(this.sDevice);
    localStringBuilder.append(", sQQ=");
    localStringBuilder.append(this.sQQ);
    localStringBuilder.append(", sUrl=");
    localStringBuilder.append(this.sUrl);
    localStringBuilder.append(", sAPN=");
    localStringBuilder.append(this.sAPN);
    localStringBuilder.append(", iProxyType=");
    localStringBuilder.append(this.iProxyType);
    localStringBuilder.append(", sRemoteIP=");
    localStringBuilder.append(this.sRemoteIP);
    localStringBuilder.append(", sProxyIP=");
    localStringBuilder.append(this.sProxyIP);
    localStringBuilder.append(", iTotalTime=");
    localStringBuilder.append(this.iTotalTime);
    localStringBuilder.append(", iSubAvgDNSTime=");
    localStringBuilder.append(this.iSubAvgDNSTime);
    localStringBuilder.append(", iSubAvgConnectTime=");
    localStringBuilder.append(this.iSubAvgConnectTime);
    localStringBuilder.append(", iSubAvgWaitRspTime=");
    localStringBuilder.append(this.iSubAvgWaitRspTime);
    localStringBuilder.append(", iSubResourceSize=");
    localStringBuilder.append(this.iSubResourceSize);
    localStringBuilder.append(", iSubResouceCostTime=");
    localStringBuilder.append(this.iSubResouceCostTime);
    localStringBuilder.append(", iSubHTTPRequestCount=");
    localStringBuilder.append(this.iSubHTTPRequestCount);
    localStringBuilder.append(", iPageVisitCount=");
    localStringBuilder.append(this.iPageVisitCount);
    localStringBuilder.append(", vReqRecord=");
    localStringBuilder.append(this.vReqRecord);
    localStringBuilder.append(", iNetLoadTime=");
    localStringBuilder.append(this.iNetLoadTime);
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    Object localObject = this.sDevice;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 0);
    }
    localObject = this.sQQ;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 1);
    }
    localObject = this.sUrl;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 2);
    }
    localObject = this.sAPN;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 3);
    }
    paramJceOutputStream.write(this.iProxyType, 4);
    localObject = this.sRemoteIP;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 5);
    }
    localObject = this.sProxyIP;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 6);
    }
    paramJceOutputStream.write(this.iTotalTime, 7);
    paramJceOutputStream.write((int)this.iSubAvgDNSTime, 8);
    paramJceOutputStream.write((int)this.iSubAvgConnectTime, 9);
    paramJceOutputStream.write((int)this.iSubAvgWaitRspTime, 10);
    paramJceOutputStream.write(this.iSubResourceSize, 11);
    paramJceOutputStream.write(this.iSubResouceCostTime, 12);
    paramJceOutputStream.write(this.iSubHTTPRequestCount, 13);
    paramJceOutputStream.write(this.iPageVisitCount, 14);
    localObject = this.vReqRecord;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 15);
    }
    paramJceOutputStream.write(this.iNetLoadTime, 16);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\PerformanceStat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */