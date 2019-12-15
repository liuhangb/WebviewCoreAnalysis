package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;

public final class RouteUrlRsp
  extends JceStruct
{
  static ArrayList<String> cache_vNormalUrl = new ArrayList();
  static ArrayList<String> cache_vRegularUrl;
  public int iFlag = 0;
  public int iMD5Value = 0;
  public int iMaxReportNumber = 0;
  public int iType = 0;
  public ArrayList<String> vNormalUrl = null;
  public ArrayList<String> vRegularUrl = null;
  
  static
  {
    cache_vNormalUrl.add("");
    cache_vRegularUrl = new ArrayList();
    cache_vRegularUrl.add("");
  }
  
  public RouteUrlRsp() {}
  
  public RouteUrlRsp(int paramInt1, ArrayList<String> paramArrayList1, ArrayList<String> paramArrayList2, int paramInt2, int paramInt3, int paramInt4)
  {
    this.iFlag = paramInt1;
    this.vNormalUrl = paramArrayList1;
    this.vRegularUrl = paramArrayList2;
    this.iMD5Value = paramInt2;
    this.iMaxReportNumber = paramInt3;
    this.iType = paramInt4;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.iFlag = paramJceInputStream.read(this.iFlag, 0, true);
    this.vNormalUrl = ((ArrayList)paramJceInputStream.read(cache_vNormalUrl, 1, false));
    this.vRegularUrl = ((ArrayList)paramJceInputStream.read(cache_vRegularUrl, 2, false));
    this.iMD5Value = paramJceInputStream.read(this.iMD5Value, 3, false);
    this.iMaxReportNumber = paramJceInputStream.read(this.iMaxReportNumber, 4, false);
    this.iType = paramJceInputStream.read(this.iType, 5, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iFlag, 0);
    ArrayList localArrayList = this.vNormalUrl;
    if (localArrayList != null) {
      paramJceOutputStream.write(localArrayList, 1);
    }
    localArrayList = this.vRegularUrl;
    if (localArrayList != null) {
      paramJceOutputStream.write(localArrayList, 2);
    }
    paramJceOutputStream.write(this.iMD5Value, 3);
    paramJceOutputStream.write(this.iMaxReportNumber, 4);
    paramJceOutputStream.write(this.iType, 5);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\RouteUrlRsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */