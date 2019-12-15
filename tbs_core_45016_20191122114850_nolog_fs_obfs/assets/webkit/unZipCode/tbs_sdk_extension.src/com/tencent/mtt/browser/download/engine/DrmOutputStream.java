package com.tencent.mtt.browser.download.engine;

import android.drm.DrmConvertedStatus;
import android.drm.DrmManagerClient;
import android.util.Log;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.UnknownServiceException;

public class DrmOutputStream
  extends OutputStream
{
  private int jdField_a_of_type_Int = -1;
  private final DrmManagerClient jdField_a_of_type_AndroidDrmDrmManagerClient;
  private final RandomAccessFile jdField_a_of_type_JavaIoRandomAccessFile;
  
  public DrmOutputStream(DrmManagerClient paramDrmManagerClient, RandomAccessFile paramRandomAccessFile, String paramString)
    throws IOException
  {
    this.jdField_a_of_type_AndroidDrmDrmManagerClient = paramDrmManagerClient;
    this.jdField_a_of_type_JavaIoRandomAccessFile = paramRandomAccessFile;
    this.jdField_a_of_type_Int = this.jdField_a_of_type_AndroidDrmDrmManagerClient.openConvertSession(paramString);
    if (this.jdField_a_of_type_Int != -1) {
      return;
    }
    paramDrmManagerClient = new StringBuilder();
    paramDrmManagerClient.append("Failed to open DRM session for ");
    paramDrmManagerClient.append(paramString);
    throw new UnknownServiceException(paramDrmManagerClient.toString());
  }
  
  public static void checkOffsetAndCount(int paramInt1, int paramInt2, int paramInt3)
  {
    if (((paramInt2 | paramInt3) >= 0) && (paramInt2 <= paramInt1) && (paramInt1 - paramInt2 >= paramInt3)) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("length=");
    localStringBuilder.append(paramInt1);
    localStringBuilder.append("; regionStart=");
    localStringBuilder.append(paramInt2);
    localStringBuilder.append("; regionLength=");
    localStringBuilder.append(paramInt3);
    throw new ArrayIndexOutOfBoundsException(localStringBuilder.toString());
  }
  
  public static void writeSingleByte(OutputStream paramOutputStream, int paramInt)
    throws IOException
  {
    paramOutputStream.write(new byte[] { (byte)(paramInt & 0xFF) });
  }
  
  public void close()
    throws IOException
  {
    if (this.jdField_a_of_type_Int == -1) {
      Log.w("DrmOutputStream", "Closing stream without finishing");
    }
    this.jdField_a_of_type_JavaIoRandomAccessFile.close();
  }
  
  public void finish()
    throws IOException
  {
    DrmConvertedStatus localDrmConvertedStatus = this.jdField_a_of_type_AndroidDrmDrmManagerClient.closeConvertSession(this.jdField_a_of_type_Int);
    if (localDrmConvertedStatus.statusCode == 1)
    {
      this.jdField_a_of_type_JavaIoRandomAccessFile.seek(localDrmConvertedStatus.offset);
      this.jdField_a_of_type_JavaIoRandomAccessFile.write(localDrmConvertedStatus.convertedData);
      this.jdField_a_of_type_Int = -1;
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Unexpected DRM status: ");
    localStringBuilder.append(localDrmConvertedStatus.statusCode);
    throw new IOException(localStringBuilder.toString());
  }
  
  public void write(int paramInt)
    throws IOException
  {
    writeSingleByte(this, paramInt);
  }
  
  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
    if (paramInt2 != paramArrayOfByte.length)
    {
      localObject = new byte[paramInt2];
      System.arraycopy(paramArrayOfByte, paramInt1, localObject, 0, paramInt2);
      paramArrayOfByte = (byte[])localObject;
    }
    paramArrayOfByte = this.jdField_a_of_type_AndroidDrmDrmManagerClient.convertData(this.jdField_a_of_type_Int, paramArrayOfByte);
    if (paramArrayOfByte.statusCode == 1)
    {
      this.jdField_a_of_type_JavaIoRandomAccessFile.write(paramArrayOfByte.convertedData);
      return;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Unexpected DRM status: ");
    ((StringBuilder)localObject).append(paramArrayOfByte.statusCode);
    throw new IOException(((StringBuilder)localObject).toString());
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\download\engine\DrmOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */