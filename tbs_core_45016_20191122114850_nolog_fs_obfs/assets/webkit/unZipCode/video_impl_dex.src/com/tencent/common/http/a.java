package com.tencent.common.http;

import android.text.TextUtils;
import java.io.IOException;
import java.io.OutputStream;

class a
  implements IPostDataBuf
{
  private static int b = 10;
  private static int c = 4096;
  private int jdField_a_of_type_Int;
  private boolean jdField_a_of_type_Boolean = false;
  private byte[] jdField_a_of_type_ArrayOfByte;
  
  public void addFieldData(String paramString1, String paramString2, String paramString3) {}
  
  public void cancel()
  {
    this.jdField_a_of_type_Boolean = true;
  }
  
  public String getBoundary()
  {
    Object localObject = this.jdField_a_of_type_ArrayOfByte;
    if ((localObject != null) && (localObject.length > 0))
    {
      localObject = new String((byte[])localObject);
      int i = ((String)localObject).indexOf("Content-Disposition");
      if (i > 3)
      {
        localObject = ((String)localObject).substring(2, i - 2);
        if (!TextUtils.isEmpty((CharSequence)localObject)) {
          return (String)localObject;
        }
      }
    }
    return null;
  }
  
  public int getLen()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public boolean hasValidData()
  {
    return this.jdField_a_of_type_Int >= 0;
  }
  
  public boolean isUploadFile()
  {
    return false;
  }
  
  public void sendTo(OutputStream paramOutputStream)
    throws IOException, InterruptedException
  {
    sendTo(paramOutputStream, false);
  }
  
  public void sendTo(OutputStream paramOutputStream, boolean paramBoolean)
    throws IOException, InterruptedException
  {
    if (paramOutputStream != null)
    {
      if (paramBoolean)
      {
        int i = 0;
        for (;;)
        {
          byte[] arrayOfByte = this.jdField_a_of_type_ArrayOfByte;
          if ((i >= arrayOfByte.length) || (this.jdField_a_of_type_Boolean)) {
            break;
          }
          int j = Math.min(c, arrayOfByte.length - i);
          paramOutputStream.write(this.jdField_a_of_type_ArrayOfByte, i, j);
          i += j;
          j = this.jdField_a_of_type_ArrayOfByte.length;
          Thread.sleep(b);
        }
      }
      paramOutputStream.write(this.jdField_a_of_type_ArrayOfByte);
    }
  }
  
  public void setPostData(byte[] paramArrayOfByte)
  {
    this.jdField_a_of_type_ArrayOfByte = paramArrayOfByte;
    int j = this.jdField_a_of_type_Int;
    int i;
    if (paramArrayOfByte != null) {
      i = paramArrayOfByte.length;
    } else {
      i = 0;
    }
    this.jdField_a_of_type_Int = (j + i);
  }
  
  public byte[] toByteArray()
  {
    return this.jdField_a_of_type_ArrayOfByte;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */