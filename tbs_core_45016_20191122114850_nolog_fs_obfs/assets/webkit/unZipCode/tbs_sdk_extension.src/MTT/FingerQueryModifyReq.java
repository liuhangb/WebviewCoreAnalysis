package MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class FingerQueryModifyReq
  extends JceStruct
  implements Cloneable
{
  static byte[] cache_vGuid;
  public int iSessionID = 0;
  public String sParseWords = "";
  public String sQuery = "";
  public byte[] vGuid = null;
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    if (cache_vGuid == null)
    {
      cache_vGuid = (byte[])new byte[1];
      ((byte[])cache_vGuid)[0] = 0;
    }
    this.vGuid = ((byte[])paramJceInputStream.read(cache_vGuid, 0, false));
    this.sParseWords = paramJceInputStream.readString(1, false);
    this.sQuery = paramJceInputStream.readString(2, false);
    this.iSessionID = paramJceInputStream.read(this.iSessionID, 3, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    Object localObject = this.vGuid;
    if (localObject != null) {
      paramJceOutputStream.write((byte[])localObject, 0);
    }
    localObject = this.sParseWords;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 1);
    }
    localObject = this.sQuery;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 2);
    }
    paramJceOutputStream.write(this.iSessionID, 3);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\MTT\FingerQueryModifyReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */