package com.tencent.common.wup.extension;

import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class WUPStatRequest
  extends WUPRequestBase
{
  public WUPStatRequest() {}
  
  public WUPStatRequest(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }
  
  public WUPStatRequest(String paramString1, String paramString2, IWUPRequestCallBack paramIWUPRequestCallBack)
  {
    super(paramString1, paramString2, paramIWUPRequestCallBack);
  }
  
  protected boolean readFrom(DataInputStream paramDataInputStream)
  {
    for (;;)
    {
      try
      {
        this.mServerName = paramDataInputStream.readUTF();
        this.mFuncName = paramDataInputStream.readUTF();
        int i = paramDataInputStream.readInt();
        if (i > 0)
        {
          this.mPostData = new byte[i];
          paramDataInputStream.read(this.mPostData);
          bool = true;
          this.mType = paramDataInputStream.readByte();
          this.mRequestID = paramDataInputStream.readInt();
          this.mUrl = paramDataInputStream.readUTF();
          this.mEncodeName = paramDataInputStream.readUTF();
          this.mClassNamePrefs = paramDataInputStream.readUTF();
          return bool;
        }
      }
      catch (Throwable paramDataInputStream)
      {
        return false;
      }
      boolean bool = false;
    }
  }
  
  protected boolean writeTo(DataOutputStream paramDataOutputStream, int paramInt)
  {
    try
    {
      paramDataOutputStream.writeUTF(this.mServerName);
      paramDataOutputStream.writeUTF(this.mFuncName);
      byte[] arrayOfByte2 = this.mPostData;
      byte[] arrayOfByte1 = arrayOfByte2;
      if (arrayOfByte2 == null) {
        arrayOfByte1 = getPostDataFromWUPRequest(paramInt);
      }
      if ((arrayOfByte1 == null) || (arrayOfByte1.length <= 0)) {
        break label115;
      }
      paramDataOutputStream.writeInt(arrayOfByte1.length);
      paramDataOutputStream.write(arrayOfByte1);
      bool = true;
    }
    catch (Throwable paramDataOutputStream)
    {
      for (;;)
      {
        continue;
        label115:
        boolean bool = false;
      }
    }
    paramDataOutputStream.writeByte(this.mType);
    paramDataOutputStream.writeInt(this.mRequestID);
    paramDataOutputStream.writeUTF(this.mUrl);
    paramDataOutputStream.writeUTF(this.mEncodeName);
    paramDataOutputStream.writeUTF(this.mClassNamePrefs);
    return bool;
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\extension\WUPStatRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */