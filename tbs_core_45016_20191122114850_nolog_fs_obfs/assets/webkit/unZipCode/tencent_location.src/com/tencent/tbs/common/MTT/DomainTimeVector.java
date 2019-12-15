package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;

public final class DomainTimeVector
  extends JceStruct
  implements Cloneable
{
  static ArrayList<String> cache_vDomainTime = new ArrayList();
  public ArrayList<String> vDomainTime = null;
  
  static
  {
    cache_vDomainTime.add("");
  }
  
  public DomainTimeVector() {}
  
  public DomainTimeVector(ArrayList<String> paramArrayList)
  {
    this.vDomainTime = paramArrayList;
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
  
  public ArrayList<String> getVDomainTime()
  {
    return this.vDomainTime;
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
    this.vDomainTime = ((ArrayList)paramJceInputStream.read(cache_vDomainTime, 0, true));
  }
  
  public void setVDomainTime(ArrayList<String> paramArrayList)
  {
    this.vDomainTime = paramArrayList;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.vDomainTime, 0);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\DomainTimeVector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */