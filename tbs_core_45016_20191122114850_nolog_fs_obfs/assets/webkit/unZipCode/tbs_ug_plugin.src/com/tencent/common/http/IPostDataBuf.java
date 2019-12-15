package com.tencent.common.http;

import java.io.IOException;
import java.io.OutputStream;

public abstract interface IPostDataBuf
{
  public abstract void addFieldData(String paramString1, String paramString2, String paramString3);
  
  public abstract void cancel();
  
  public abstract String getBoundary();
  
  public abstract int getLen();
  
  public abstract boolean hasValidData();
  
  public abstract boolean isUploadFile();
  
  public abstract void sendTo(OutputStream paramOutputStream)
    throws IOException, InterruptedException;
  
  public abstract void sendTo(OutputStream paramOutputStream, boolean paramBoolean)
    throws IOException, InterruptedException;
  
  public abstract void setPostData(byte[] paramArrayOfByte);
  
  public abstract byte[] toByteArray();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\IPostDataBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */