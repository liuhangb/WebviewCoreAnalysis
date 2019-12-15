package com.tencent.tbs.common.ar.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class ARRetrievalInfo
  extends JceStruct
{
  static CoordinInfo cache_stCoodinInfo = new CoordinInfo();
  public double dProb = 0.0D;
  public String sLabel = "";
  public CoordinInfo stCoodinInfo = null;
  
  public ARRetrievalInfo() {}
  
  public ARRetrievalInfo(String paramString, CoordinInfo paramCoordinInfo, double paramDouble)
  {
    this.sLabel = paramString;
    this.stCoodinInfo = paramCoordinInfo;
    this.dProb = paramDouble;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sLabel = paramJceInputStream.readString(0, false);
    this.stCoodinInfo = ((CoordinInfo)paramJceInputStream.read(cache_stCoodinInfo, 1, false));
    this.dProb = paramJceInputStream.read(this.dProb, 2, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    Object localObject = this.sLabel;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 0);
    }
    localObject = this.stCoodinInfo;
    if (localObject != null) {
      paramJceOutputStream.write((JceStruct)localObject, 1);
    }
    paramJceOutputStream.write(this.dProb, 2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ar\MTT\ARRetrievalInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */