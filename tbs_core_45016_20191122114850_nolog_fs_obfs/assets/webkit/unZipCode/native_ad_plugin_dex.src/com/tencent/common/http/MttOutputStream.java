package com.tencent.common.http;

import java.io.IOException;
import java.io.OutputStream;

public class MttOutputStream
  extends OutputStream
{
  private int jdField_a_of_type_Int;
  private IFlowListener jdField_a_of_type_ComTencentCommonHttpIFlowListener = null;
  private OutputStream jdField_a_of_type_JavaIoOutputStream = null;
  
  public MttOutputStream(OutputStream paramOutputStream)
  {
    this.jdField_a_of_type_JavaIoOutputStream = paramOutputStream;
  }
  
  void a(int paramInt)
  {
    if (paramInt < 1) {
      return;
    }
    this.jdField_a_of_type_Int += paramInt;
    IFlowListener localIFlowListener = this.jdField_a_of_type_ComTencentCommonHttpIFlowListener;
    if (localIFlowListener != null) {
      localIFlowListener.onFlow(paramInt, 2);
    }
  }
  
  public void close()
    throws IOException
  {
    OutputStream localOutputStream = this.jdField_a_of_type_JavaIoOutputStream;
    if (localOutputStream != null) {
      localOutputStream.close();
    }
  }
  
  public void flush()
    throws IOException
  {
    OutputStream localOutputStream = this.jdField_a_of_type_JavaIoOutputStream;
    if (localOutputStream != null) {
      localOutputStream.flush();
    }
  }
  
  public int getFlow()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public void setFlowListener(IFlowListener paramIFlowListener)
  {
    this.jdField_a_of_type_ComTencentCommonHttpIFlowListener = paramIFlowListener;
  }
  
  public void write(int paramInt)
    throws IOException
  {
    OutputStream localOutputStream = this.jdField_a_of_type_JavaIoOutputStream;
    if (localOutputStream != null)
    {
      localOutputStream.write(paramInt);
      a(1);
    }
  }
  
  public void write(byte[] paramArrayOfByte)
    throws IOException
  {
    OutputStream localOutputStream = this.jdField_a_of_type_JavaIoOutputStream;
    if (localOutputStream != null)
    {
      localOutputStream.write(paramArrayOfByte);
      a(paramArrayOfByte.length);
    }
  }
  
  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    OutputStream localOutputStream = this.jdField_a_of_type_JavaIoOutputStream;
    if (localOutputStream != null)
    {
      localOutputStream.write(paramArrayOfByte, paramInt1, paramInt2);
      a(paramInt2);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\MttOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */