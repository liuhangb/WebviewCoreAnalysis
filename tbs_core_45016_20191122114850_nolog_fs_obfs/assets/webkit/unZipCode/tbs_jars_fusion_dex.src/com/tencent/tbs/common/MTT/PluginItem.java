package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;
import java.util.Collection;

public final class PluginItem
  extends JceStruct
{
  static ArrayList<String> cache_vBackURL = new ArrayList();
  public int iLocation = 0;
  public int iOrder = 0;
  public int iPluginType = 0;
  public int iSize = 0;
  public int iUpdateType = 0;
  public int iVersion = 0;
  public String s256MD5 = "";
  public String sExt = "";
  public String sIconUrl = "";
  public String sPkgName = "";
  public String sSignature = "";
  public String sTips = "";
  public String sTitle = "";
  public String sUrl = "";
  public ArrayList<String> vBackURL = null;
  
  static
  {
    cache_vBackURL.add("");
  }
  
  public PluginItem() {}
  
  public PluginItem(int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, String paramString5, String paramString6, String paramString7, String paramString8, ArrayList<String> paramArrayList)
  {
    this.iUpdateType = paramInt1;
    this.sTitle = paramString1;
    this.sUrl = paramString2;
    this.sIconUrl = paramString3;
    this.sPkgName = paramString4;
    this.iPluginType = paramInt2;
    this.iVersion = paramInt3;
    this.iSize = paramInt4;
    this.iLocation = paramInt5;
    this.iOrder = paramInt6;
    this.sSignature = paramString5;
    this.sTips = paramString6;
    this.sExt = paramString7;
    this.s256MD5 = paramString8;
    this.vBackURL = paramArrayList;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.iUpdateType = paramJceInputStream.read(this.iUpdateType, 0, true);
    this.sTitle = paramJceInputStream.readString(1, true);
    this.sUrl = paramJceInputStream.readString(2, true);
    this.sIconUrl = paramJceInputStream.readString(3, true);
    this.sPkgName = paramJceInputStream.readString(4, true);
    this.iPluginType = paramJceInputStream.read(this.iPluginType, 5, true);
    this.iVersion = paramJceInputStream.read(this.iVersion, 6, true);
    this.iSize = paramJceInputStream.read(this.iSize, 7, true);
    this.iLocation = paramJceInputStream.read(this.iLocation, 8, true);
    this.iOrder = paramJceInputStream.read(this.iOrder, 9, true);
    this.sSignature = paramJceInputStream.readString(10, true);
    this.sTips = paramJceInputStream.readString(11, true);
    this.sExt = paramJceInputStream.readString(12, true);
    this.s256MD5 = paramJceInputStream.readString(13, false);
    this.vBackURL = ((ArrayList)paramJceInputStream.read(cache_vBackURL, 14, false));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iUpdateType, 0);
    paramJceOutputStream.write(this.sTitle, 1);
    paramJceOutputStream.write(this.sUrl, 2);
    paramJceOutputStream.write(this.sIconUrl, 3);
    paramJceOutputStream.write(this.sPkgName, 4);
    paramJceOutputStream.write(this.iPluginType, 5);
    paramJceOutputStream.write(this.iVersion, 6);
    paramJceOutputStream.write(this.iSize, 7);
    paramJceOutputStream.write(this.iLocation, 8);
    paramJceOutputStream.write(this.iOrder, 9);
    paramJceOutputStream.write(this.sSignature, 10);
    paramJceOutputStream.write(this.sTips, 11);
    paramJceOutputStream.write(this.sExt, 12);
    Object localObject = this.s256MD5;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 13);
    }
    localObject = this.vBackURL;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 14);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\PluginItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */