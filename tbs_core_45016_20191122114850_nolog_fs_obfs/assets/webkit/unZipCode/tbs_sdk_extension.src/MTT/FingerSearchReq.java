package MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class FingerSearchReq
  extends JceStruct
  implements Cloneable
{
  static byte[] cache_vGuid;
  public int iIndexSub = 0;
  public int iTagNewLineAfter = 0;
  public int iTagNewLineAfterSub = 0;
  public int iTagNewLineBefore = 0;
  public int iTagNewLineBeforeSub = 0;
  public int iTouchIndex = 0;
  public String sText = "";
  public String sTextSub = "";
  public String sTitle = "";
  public String sUrl = "";
  public byte[] vGuid = null;
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    if (cache_vGuid == null)
    {
      cache_vGuid = (byte[])new byte[1];
      ((byte[])cache_vGuid)[0] = 0;
    }
    this.vGuid = ((byte[])paramJceInputStream.read(cache_vGuid, 0, false));
    this.sUrl = paramJceInputStream.readString(1, false);
    this.sTitle = paramJceInputStream.readString(2, false);
    this.sText = paramJceInputStream.readString(3, false);
    this.iTouchIndex = paramJceInputStream.read(this.iTouchIndex, 4, false);
    this.iTagNewLineBefore = paramJceInputStream.read(this.iTagNewLineBefore, 5, false);
    this.iTagNewLineAfter = paramJceInputStream.read(this.iTagNewLineAfter, 6, false);
    this.sTextSub = paramJceInputStream.readString(7, false);
    this.iIndexSub = paramJceInputStream.read(this.iIndexSub, 8, false);
    this.iTagNewLineBeforeSub = paramJceInputStream.read(this.iTagNewLineBeforeSub, 9, false);
    this.iTagNewLineAfterSub = paramJceInputStream.read(this.iTagNewLineAfterSub, 10, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    Object localObject = this.vGuid;
    if (localObject != null) {
      paramJceOutputStream.write((byte[])localObject, 0);
    }
    localObject = this.sUrl;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 1);
    }
    localObject = this.sTitle;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 2);
    }
    localObject = this.sText;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 3);
    }
    paramJceOutputStream.write(this.iTouchIndex, 4);
    paramJceOutputStream.write(this.iTagNewLineBefore, 5);
    paramJceOutputStream.write(this.iTagNewLineAfter, 6);
    localObject = this.sTextSub;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 7);
    }
    paramJceOutputStream.write(this.iIndexSub, 8);
    paramJceOutputStream.write(this.iTagNewLineBeforeSub, 9);
    paramJceOutputStream.write(this.iTagNewLineAfterSub, 10);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\MTT\FingerSearchReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */