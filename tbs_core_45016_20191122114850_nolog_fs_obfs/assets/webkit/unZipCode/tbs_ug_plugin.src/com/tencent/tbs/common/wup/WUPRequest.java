package com.tencent.tbs.common.wup;

import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.RequestPolicy;
import com.tencent.common.wup.WUPRequestBase;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class WUPRequest
  extends WUPRequestBase
{
  boolean mEmerge = true;
  
  public WUPRequest() {}
  
  public WUPRequest(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }
  
  public WUPRequest(String paramString1, String paramString2, IWUPRequestCallBack paramIWUPRequestCallBack)
  {
    super(paramString1, paramString2, paramIWUPRequestCallBack);
  }
  
  protected void checkClassLoader(Object paramObject)
  {
    try
    {
      setClassLoader(paramObject.getClass().getClassLoader());
      return;
    }
    catch (Exception paramObject) {}
  }
  
  public boolean readFrom(DataInputStream paramDataInputStream)
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
        }
        this.mType = paramDataInputStream.readByte();
        this.mRequestID = paramDataInputStream.readInt();
        RequestPolicy[] arrayOfRequestPolicy = RequestPolicy.values();
        i = paramDataInputStream.readInt();
        if (i < arrayOfRequestPolicy.length) {
          this.mRequestPolicy = arrayOfRequestPolicy[i];
        }
        if (paramDataInputStream.readInt() == 1)
        {
          bool = true;
          this.mIsFromService = bool;
          if (paramDataInputStream.readInt() != 1) {
            break label248;
          }
          bool = true;
          this.mNeedCloseConnection = bool;
          if (paramDataInputStream.readInt() != 1) {
            break label253;
          }
          bool = true;
          this.mNeedEncrypt = bool;
          this.mUrl = paramDataInputStream.readUTF();
          if (paramDataInputStream.readInt() != 1) {
            break label258;
          }
          bool = true;
          this.mNeedStatFlow = bool;
          this.mPacketSize = paramDataInputStream.readInt();
          if (paramDataInputStream.readInt() != 1) {
            break label263;
          }
          bool = true;
          this.mIsBackgroudTask = bool;
          this.mEncodeName = paramDataInputStream.readUTF();
          this.mNetworkStatus = paramDataInputStream.readInt();
          this.mRequestName = paramDataInputStream.readUTF();
          if (paramDataInputStream.readInt() != 1) {
            break label268;
          }
          bool = true;
          this.mHasReplied = bool;
          this.mClassNamePrefs = paramDataInputStream.readUTF();
          return true;
        }
      }
      catch (Exception|OutOfMemoryError paramDataInputStream)
      {
        return false;
      }
      boolean bool = false;
      continue;
      label248:
      bool = false;
      continue;
      label253:
      bool = false;
      continue;
      label258:
      bool = false;
      continue;
      label263:
      bool = false;
      continue;
      label268:
      bool = false;
    }
  }
  
  public void setEmergencyTag(boolean paramBoolean)
  {
    this.mEmerge = paramBoolean;
  }
  
  public boolean writeTo(DataOutputStream paramDataOutputStream, int paramInt)
  {
    for (;;)
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
        if (arrayOfByte1 != null)
        {
          paramDataOutputStream.writeInt(arrayOfByte1.length);
          paramDataOutputStream.write(arrayOfByte1);
        }
        else
        {
          paramDataOutputStream.writeInt(0);
        }
        paramDataOutputStream.writeByte(this.mType);
        paramDataOutputStream.writeInt(this.mRequestID);
        paramDataOutputStream.writeInt(this.mRequestPolicy.ordinal());
        if (this.mIsFromService)
        {
          paramInt = 1;
          paramDataOutputStream.writeInt(paramInt);
          if (!this.mNeedCloseConnection) {
            break label246;
          }
          paramInt = 1;
          paramDataOutputStream.writeInt(paramInt);
          if (!this.mNeedEncrypt) {
            break label251;
          }
          paramInt = 1;
          paramDataOutputStream.writeInt(paramInt);
          paramDataOutputStream.writeUTF(this.mUrl);
          if (!this.mNeedStatFlow) {
            break label256;
          }
          paramInt = 1;
          paramDataOutputStream.writeInt(paramInt);
          paramDataOutputStream.writeInt(this.mPacketSize);
          if (!this.mIsBackgroudTask) {
            break label261;
          }
          paramInt = 1;
          paramDataOutputStream.writeInt(paramInt);
          paramDataOutputStream.writeUTF(this.mEncodeName);
          paramDataOutputStream.writeInt(this.mNetworkStatus);
          paramDataOutputStream.writeUTF(this.mRequestName);
          if (!this.mHasReplied) {
            break label266;
          }
          paramInt = 1;
          paramDataOutputStream.writeInt(paramInt);
          paramDataOutputStream.writeUTF(this.mClassNamePrefs);
          return true;
        }
      }
      catch (Exception|OutOfMemoryError paramDataOutputStream)
      {
        return false;
      }
      paramInt = 0;
      continue;
      label246:
      paramInt = 0;
      continue;
      label251:
      paramInt = 0;
      continue;
      label256:
      paramInt = 0;
      continue;
      label261:
      paramInt = 0;
      continue;
      label266:
      paramInt = 0;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wup\WUPRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */