package com.tencent.tbs.common.ar.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;
import java.util.Collection;

public final class ImageMarkerRecognitionResp
  extends JceStruct
{
  static ArrayList<ARCloudMarkerInfo> cache_vMarker;
  static ArrayList<ARRetrievalInfo> cache_vRetrievalInfo = new ArrayList();
  public int iResponsType = 0;
  public int iRetCode = 0;
  public String sKey = "";
  public String sMsg = "";
  public ArrayList<ARCloudMarkerInfo> vMarker = null;
  public ArrayList<ARRetrievalInfo> vRetrievalInfo = null;
  
  static
  {
    Object localObject = new ARRetrievalInfo();
    cache_vRetrievalInfo.add(localObject);
    cache_vMarker = new ArrayList();
    localObject = new ARCloudMarkerInfo();
    cache_vMarker.add(localObject);
  }
  
  public ImageMarkerRecognitionResp() {}
  
  public ImageMarkerRecognitionResp(int paramInt1, String paramString1, int paramInt2, String paramString2, ArrayList<ARRetrievalInfo> paramArrayList, ArrayList<ARCloudMarkerInfo> paramArrayList1)
  {
    this.iRetCode = paramInt1;
    this.sMsg = paramString1;
    this.iResponsType = paramInt2;
    this.sKey = paramString2;
    this.vRetrievalInfo = paramArrayList;
    this.vMarker = paramArrayList1;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.iRetCode = paramJceInputStream.read(this.iRetCode, 0, false);
    this.sMsg = paramJceInputStream.readString(1, false);
    this.iResponsType = paramJceInputStream.read(this.iResponsType, 2, false);
    this.sKey = paramJceInputStream.readString(3, false);
    this.vRetrievalInfo = ((ArrayList)paramJceInputStream.read(cache_vRetrievalInfo, 4, false));
    this.vMarker = ((ArrayList)paramJceInputStream.read(cache_vMarker, 5, false));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iRetCode, 0);
    Object localObject = this.sMsg;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 1);
    }
    paramJceOutputStream.write(this.iResponsType, 2);
    localObject = this.sKey;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 3);
    }
    localObject = this.vRetrievalInfo;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 4);
    }
    localObject = this.vMarker;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 5);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ar\MTT\ImageMarkerRecognitionResp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */