package com.tencent.tbs.common.ar.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;
import java.util.Collection;

public final class ImageMarkerRecognitionReq
  extends JceStruct
{
  static ARUserInfo cache_stUserInfo = new ARUserInfo();
  static ArrayList<String> cache_vAccessID = new ArrayList();
  static byte[] cache_vImage;
  public int iScene = 0;
  public long lTimeStamp = 0L;
  public String sAppID = "";
  public String sSig = "";
  public ARUserInfo stUserInfo = null;
  public ArrayList<String> vAccessID = null;
  public byte[] vImage = null;
  
  static
  {
    cache_vAccessID.add("");
    cache_vImage = (byte[])new byte[1];
    ((byte[])cache_vImage)[0] = 0;
  }
  
  public ImageMarkerRecognitionReq() {}
  
  public ImageMarkerRecognitionReq(ARUserInfo paramARUserInfo, String paramString1, ArrayList<String> paramArrayList, String paramString2, int paramInt, byte[] paramArrayOfByte, long paramLong)
  {
    this.stUserInfo = paramARUserInfo;
    this.sAppID = paramString1;
    this.vAccessID = paramArrayList;
    this.sSig = paramString2;
    this.iScene = paramInt;
    this.vImage = paramArrayOfByte;
    this.lTimeStamp = paramLong;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.stUserInfo = ((ARUserInfo)paramJceInputStream.read(cache_stUserInfo, 0, false));
    this.sAppID = paramJceInputStream.readString(1, false);
    this.vAccessID = ((ArrayList)paramJceInputStream.read(cache_vAccessID, 2, false));
    this.sSig = paramJceInputStream.readString(3, false);
    this.iScene = paramJceInputStream.read(this.iScene, 4, false);
    this.vImage = ((byte[])paramJceInputStream.read(cache_vImage, 5, false));
    this.lTimeStamp = paramJceInputStream.read(this.lTimeStamp, 6, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    Object localObject = this.stUserInfo;
    if (localObject != null) {
      paramJceOutputStream.write((JceStruct)localObject, 0);
    }
    localObject = this.sAppID;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 1);
    }
    localObject = this.vAccessID;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 2);
    }
    localObject = this.sSig;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 3);
    }
    paramJceOutputStream.write(this.iScene, 4);
    localObject = this.vImage;
    if (localObject != null) {
      paramJceOutputStream.write((byte[])localObject, 5);
    }
    paramJceOutputStream.write(this.lTimeStamp, 6);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ar\MTT\ImageMarkerRecognitionReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */