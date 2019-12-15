package MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class FingerSearchRsp
  extends JceStruct
  implements Cloneable
{
  public int iIndex = 0;
  public int iIsMainOrSub = 0;
  public int iSessionID = 0;
  public String sWord = "";
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sWord = paramJceInputStream.readString(0, false);
    this.iIndex = paramJceInputStream.read(this.iIndex, 1, false);
    this.iIsMainOrSub = paramJceInputStream.read(this.iIsMainOrSub, 2, false);
    this.iSessionID = paramJceInputStream.read(this.iSessionID, 3, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    String str = this.sWord;
    if (str != null) {
      paramJceOutputStream.write(str, 0);
    }
    paramJceOutputStream.write(this.iIndex, 1);
    paramJceOutputStream.write(this.iIsMainOrSub, 2);
    paramJceOutputStream.write(this.iSessionID, 3);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\MTT\FingerSearchRsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */