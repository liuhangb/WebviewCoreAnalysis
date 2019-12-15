package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;

public final class UniPluginRsp
  extends JceStruct
{
  static ArrayList<PluginItem> cache_vPluginList;
  public String sMd5 = "";
  public ArrayList<PluginItem> vPluginList = null;
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sMd5 = paramJceInputStream.readString(0, true);
    if (cache_vPluginList == null)
    {
      cache_vPluginList = new ArrayList();
      PluginItem localPluginItem = new PluginItem();
      cache_vPluginList.add(localPluginItem);
    }
    this.vPluginList = ((ArrayList)paramJceInputStream.read(cache_vPluginList, 1, true));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sMd5, 0);
    paramJceOutputStream.write(this.vPluginList, 1);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\UniPluginRsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */