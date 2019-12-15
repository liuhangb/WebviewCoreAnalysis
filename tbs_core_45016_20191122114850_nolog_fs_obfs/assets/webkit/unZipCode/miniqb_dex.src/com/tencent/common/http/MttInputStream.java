package com.tencent.common.http;

import com.tencent.basesupport.FLogger;
import java.io.IOException;
import java.io.InputStream;

public class MttInputStream
  extends InputStream
{
  private int jdField_a_of_type_Int;
  private IFlowListener jdField_a_of_type_ComTencentCommonHttpIFlowListener = null;
  private IExceptionHandler jdField_a_of_type_ComTencentCommonHttpMttInputStream$IExceptionHandler = null;
  private InputStream jdField_a_of_type_JavaIoInputStream;
  private boolean jdField_a_of_type_Boolean = true;
  
  public MttInputStream(InputStream paramInputStream, boolean paramBoolean)
  {
    this.jdField_a_of_type_JavaIoInputStream = paramInputStream;
    this.jdField_a_of_type_Boolean = paramBoolean;
    this.jdField_a_of_type_Int = 0;
  }
  
  void a(int paramInt)
  {
    if (paramInt < 1) {
      return;
    }
    if (this.jdField_a_of_type_Boolean) {
      this.jdField_a_of_type_Int += paramInt;
    }
    IFlowListener localIFlowListener = this.jdField_a_of_type_ComTencentCommonHttpIFlowListener;
    if (localIFlowListener != null) {
      localIFlowListener.onFlow(paramInt, 1);
    }
  }
  
  boolean a(Exception paramException)
  {
    IExceptionHandler localIExceptionHandler = this.jdField_a_of_type_ComTencentCommonHttpMttInputStream$IExceptionHandler;
    return (localIExceptionHandler != null) && (localIExceptionHandler.handleException(paramException));
  }
  
  public int available()
    throws IOException
  {
    InputStream localInputStream = this.jdField_a_of_type_JavaIoInputStream;
    if (localInputStream != null) {
      try
      {
        int i = localInputStream.available();
        return i;
      }
      catch (Exception localException)
      {
        FLogger.e("MttInputStream", localException);
        if (!a(localException)) {
          throw localException;
        }
      }
    }
    return 0;
  }
  
  public void close()
    throws IOException
  {
    InputStream localInputStream = this.jdField_a_of_type_JavaIoInputStream;
    if (localInputStream != null) {
      localInputStream.close();
    }
  }
  
  public int getFlow()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public InputStream getImpl()
  {
    return this;
  }
  
  public void mark(int paramInt)
  {
    try
    {
      if (this.jdField_a_of_type_JavaIoInputStream != null) {
        this.jdField_a_of_type_JavaIoInputStream.mark(paramInt);
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean markSupported()
  {
    InputStream localInputStream = this.jdField_a_of_type_JavaIoInputStream;
    if (localInputStream != null) {
      return localInputStream.markSupported();
    }
    return false;
  }
  
  public int read()
    throws IOException
  {
    InputStream localInputStream = this.jdField_a_of_type_JavaIoInputStream;
    int j = 0;
    int i = 0;
    if (localInputStream != null)
    {
      j = i;
      try
      {
        i = localInputStream.read();
        j = i;
        if (i > 0)
        {
          j = i;
          a(1);
          return i;
        }
      }
      catch (Exception localException)
      {
        FLogger.e("MttInputStream", localException);
        if (a(localException)) {
          return j;
        }
        throw localException;
      }
    }
    return j;
  }
  
  public int read(byte[] paramArrayOfByte)
    throws IOException
  {
    InputStream localInputStream = this.jdField_a_of_type_JavaIoInputStream;
    int i;
    if (localInputStream != null) {
      try
      {
        i = localInputStream.read(paramArrayOfByte);
      }
      catch (Exception paramArrayOfByte)
      {
        FLogger.e("MttInputStream", paramArrayOfByte);
        if (!a(paramArrayOfByte)) {
          throw paramArrayOfByte;
        }
      }
    } else {
      i = 0;
    }
    a(i);
    return i;
  }
  
  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    InputStream localInputStream = this.jdField_a_of_type_JavaIoInputStream;
    if (localInputStream != null) {
      try
      {
        paramInt1 = localInputStream.read(paramArrayOfByte, paramInt1, paramInt2);
      }
      catch (Exception paramArrayOfByte)
      {
        FLogger.e("MttInputStream", paramArrayOfByte);
        if (!a(paramArrayOfByte)) {
          throw paramArrayOfByte;
        }
      }
    } else {
      paramInt1 = 0;
    }
    a(paramInt1);
    return paramInt1;
  }
  
  public int readFully(byte[] paramArrayOfByte, int paramInt1, int paramInt2, recvFullyBack paramrecvFullyBack)
    throws IOException
  {
    int i;
    if ((paramInt2 != 0) && (this.jdField_a_of_type_JavaIoInputStream != null) && (paramArrayOfByte != null))
    {
      i = 0;
      while (paramInt2 > 0) {
        try
        {
          int j = this.jdField_a_of_type_JavaIoInputStream.read(paramArrayOfByte, paramInt1, paramInt2);
          if (j < 0)
          {
            if (i != 0) {
              break label116;
            }
            return j;
          }
          if (paramrecvFullyBack != null) {
            paramrecvFullyBack.recvDataLen(j);
          }
          a(j);
          i += j;
          paramInt1 += j;
          paramInt2 -= j;
        }
        catch (Exception paramArrayOfByte)
        {
          FLogger.e("MttInputStream", paramArrayOfByte);
          if (a(paramArrayOfByte)) {
            return i;
          }
          throw paramArrayOfByte;
        }
      }
      return i;
    }
    return -2;
    label116:
    return i;
  }
  
  public void reset()
    throws IOException
  {
    try
    {
      if (this.jdField_a_of_type_JavaIoInputStream != null) {
        this.jdField_a_of_type_JavaIoInputStream.reset();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setExceptionHandler(IExceptionHandler paramIExceptionHandler)
  {
    this.jdField_a_of_type_ComTencentCommonHttpMttInputStream$IExceptionHandler = paramIExceptionHandler;
  }
  
  public void setFlowListener(IFlowListener paramIFlowListener)
  {
    this.jdField_a_of_type_ComTencentCommonHttpIFlowListener = paramIFlowListener;
  }
  
  public long skip(long paramLong)
    throws IOException
  {
    InputStream localInputStream = this.jdField_a_of_type_JavaIoInputStream;
    if (localInputStream != null) {
      try
      {
        paramLong = localInputStream.skip(paramLong);
        return paramLong;
      }
      catch (Exception localException)
      {
        FLogger.e("MttInputStream", localException);
        if (!a(localException)) {
          throw localException;
        }
      }
    }
    return 0L;
  }
  
  public static abstract interface IExceptionHandler
  {
    public abstract boolean handleException(Exception paramException);
  }
  
  public static abstract interface recvFullyBack
  {
    public abstract void recvDataLen(int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\MttInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */