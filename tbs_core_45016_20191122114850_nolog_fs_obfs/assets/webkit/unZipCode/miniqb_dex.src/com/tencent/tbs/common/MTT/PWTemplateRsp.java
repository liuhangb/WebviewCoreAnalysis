package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;

public final class PWTemplateRsp
  extends JceStruct
{
  static ArrayList<PWItemData> cache_vItemListData = new ArrayList();
  public ArrayList<PWItemData> vItemListData = null;
  
  static
  {
    PWItemData localPWItemData = new PWItemData();
    cache_vItemListData.add(localPWItemData);
  }
  
  public PWTemplateRsp() {}
  
  public PWTemplateRsp(ArrayList<PWItemData> paramArrayList)
  {
    this.vItemListData = paramArrayList;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.vItemListData = ((ArrayList)paramJceInputStream.read(cache_vItemListData, 0, true));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.vItemListData, 0);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\PWTemplateRsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */