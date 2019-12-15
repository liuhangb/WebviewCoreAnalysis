package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;

public final class ETPV
  extends JceStruct
{
  static ArrayList<URLPV> cache_stURLPV;
  public String sID = "";
  public String sURL = "";
  public ArrayList<URLPV> stURLPV = null;
  
  public ETPV() {}
  
  public ETPV(int paramInt, String paramString1, String paramString2)
  {
    this.sURL = paramString2;
    this.sID = paramString1;
    this.stURLPV = new ArrayList(1);
    paramString1 = new URLPV(2, paramInt, 0);
    this.stURLPV.add(paramString1);
  }
  
  public ETPV(String paramString, ArrayList<URLPV> paramArrayList)
  {
    this.sURL = paramString;
    this.stURLPV = paramArrayList;
  }
  
  public void addStatCount(int paramInt)
  {
    Object localObject = this.stURLPV;
    if (localObject != null)
    {
      localObject = (URLPV)((ArrayList)localObject).get(0);
      ((URLPV)localObject).iPV += paramInt;
    }
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sURL = paramJceInputStream.readString(0, true);
    if (cache_stURLPV == null)
    {
      cache_stURLPV = new ArrayList();
      URLPV localURLPV = new URLPV();
      cache_stURLPV.add(localURLPV);
    }
    this.stURLPV = ((ArrayList)paramJceInputStream.read(cache_stURLPV, 1, true));
    this.sID = paramJceInputStream.read(this.sID, 2, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    if (this.sURL == null) {
      this.sURL = "";
    }
    paramJceOutputStream.write(this.sURL, 0);
    ArrayList localArrayList = this.stURLPV;
    if (localArrayList != null) {
      paramJceOutputStream.write(localArrayList, 1);
    }
    if (this.sID == null) {
      this.sID = "";
    }
    paramJceOutputStream.write(this.sID, 2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\ETPV.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */