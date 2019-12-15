package com.tencent.tbs.common.MTT;

import com.taf.JceDisplayer;
import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import com.taf.JceUtil;
import java.util.ArrayList;
import java.util.Collection;

public final class DomainInfo
  extends JceStruct
  implements Cloneable
{
  static int cache_eType;
  static ArrayList<String> cache_vDomain = new ArrayList();
  public int eType = 0;
  public String sMd5 = "";
  public ArrayList<String> vDomain = null;
  
  static
  {
    cache_vDomain.add("");
  }
  
  public DomainInfo() {}
  
  public DomainInfo(int paramInt, String paramString, ArrayList<String> paramArrayList)
  {
    this.eType = paramInt;
    this.sMd5 = paramString;
    this.vDomain = paramArrayList;
  }
  
  public String className()
  {
    return "MTT.DomainInfo";
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
  
  public void display(StringBuilder paramStringBuilder, int paramInt)
  {
    paramStringBuilder = new JceDisplayer(paramStringBuilder, paramInt);
    paramStringBuilder.display(this.eType, "eType");
    paramStringBuilder.display(this.sMd5, "sMd5");
    paramStringBuilder.display(this.vDomain, "vDomain");
  }
  
  public void displaySimple(StringBuilder paramStringBuilder, int paramInt)
  {
    paramStringBuilder = new JceDisplayer(paramStringBuilder, paramInt);
    paramStringBuilder.displaySimple(this.eType, true);
    paramStringBuilder.displaySimple(this.sMd5, true);
    paramStringBuilder.displaySimple(this.vDomain, false);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = false;
    if (paramObject == null) {
      return false;
    }
    paramObject = (DomainInfo)paramObject;
    boolean bool1 = bool2;
    if (JceUtil.equals(this.eType, ((DomainInfo)paramObject).eType))
    {
      bool1 = bool2;
      if (JceUtil.equals(this.sMd5, ((DomainInfo)paramObject).sMd5))
      {
        bool1 = bool2;
        if (JceUtil.equals(this.vDomain, ((DomainInfo)paramObject).vDomain)) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public String fullClassName()
  {
    return "MTT.DomainInfo";
  }
  
  public int getEType()
  {
    return this.eType;
  }
  
  public String getSMd5()
  {
    return this.sMd5;
  }
  
  public ArrayList<String> getVDomain()
  {
    return this.vDomain;
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
    this.eType = paramJceInputStream.read(this.eType, 0, false);
    this.sMd5 = paramJceInputStream.readString(1, false);
    this.vDomain = ((ArrayList)paramJceInputStream.read(cache_vDomain, 2, false));
  }
  
  public void setEType(int paramInt)
  {
    this.eType = paramInt;
  }
  
  public void setSMd5(String paramString)
  {
    this.sMd5 = paramString;
  }
  
  public void setVDomain(ArrayList<String> paramArrayList)
  {
    this.vDomain = paramArrayList;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.eType, 0);
    Object localObject = this.sMd5;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 1);
    }
    localObject = this.vDomain;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 2);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\DomainInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */