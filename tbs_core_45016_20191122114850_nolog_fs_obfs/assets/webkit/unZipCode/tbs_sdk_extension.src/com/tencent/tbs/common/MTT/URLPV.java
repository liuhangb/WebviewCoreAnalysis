package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class URLPV
  extends JceStruct
{
  public int eEntryType = 0;
  public int iEntry = 0;
  public int iPV = 0;
  
  public URLPV() {}
  
  public URLPV(int paramInt1, int paramInt2, int paramInt3)
  {
    this.iEntry = paramInt1;
    this.eEntryType = paramInt2;
    this.iPV = paramInt3;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.iEntry = paramJceInputStream.read(this.iEntry, 0, true);
    this.eEntryType = paramJceInputStream.read(this.eEntryType, 1, true);
    this.iPV = paramJceInputStream.read(this.iPV, 2, true);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iEntry, 0);
    paramJceOutputStream.write(this.eEntryType, 1);
    paramJceOutputStream.write(this.iPV, 2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\URLPV.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */