package com.tencent.mtt.browser.download.engine;

public class SynchronizedPool
{
  private int jdField_a_of_type_Int = 0;
  private final DownloadDataBuffer.Buffer[] jdField_a_of_type_ArrayOfComTencentMttBrowserDownloadEngineDownloadDataBuffer$Buffer;
  private int b = 0;
  public int mPoolAvailableSize;
  
  public SynchronizedPool(int paramInt)
  {
    if (paramInt > 0)
    {
      this.b = paramInt;
      this.jdField_a_of_type_ArrayOfComTencentMttBrowserDownloadEngineDownloadDataBuffer$Buffer = new DownloadDataBuffer.Buffer[paramInt];
      return;
    }
    throw new IllegalArgumentException("The max pool size must be > 0");
  }
  
  private DownloadDataBuffer.Buffer a()
  {
    int i = this.mPoolAvailableSize;
    int j = i - 1;
    DownloadDataBuffer.Buffer[] arrayOfBuffer = this.jdField_a_of_type_ArrayOfComTencentMttBrowserDownloadEngineDownloadDataBuffer$Buffer;
    DownloadDataBuffer.Buffer localBuffer = arrayOfBuffer[j];
    arrayOfBuffer[j] = null;
    this.mPoolAvailableSize = (i - 1);
    return localBuffer;
  }
  
  private boolean a(DownloadDataBuffer.Buffer paramBuffer)
  {
    int i = 0;
    while (i < this.mPoolAvailableSize)
    {
      if (this.jdField_a_of_type_ArrayOfComTencentMttBrowserDownloadEngineDownloadDataBuffer$Buffer[i] == paramBuffer) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  public void GCAllBuffer()
  {
    synchronized (this.jdField_a_of_type_ArrayOfComTencentMttBrowserDownloadEngineDownloadDataBuffer$Buffer)
    {
      this.jdField_a_of_type_Int -= this.mPoolAvailableSize;
      int i = 0;
      this.mPoolAvailableSize = 0;
      while (i < this.b)
      {
        this.jdField_a_of_type_ArrayOfComTencentMttBrowserDownloadEngineDownloadDataBuffer$Buffer[i] = null;
        i += 1;
      }
      return;
    }
  }
  
  public DownloadDataBuffer.Buffer acquire()
  {
    synchronized (this.jdField_a_of_type_ArrayOfComTencentMttBrowserDownloadEngineDownloadDataBuffer$Buffer)
    {
      if (this.mPoolAvailableSize > 0)
      {
        localBuffer = a();
        return localBuffer;
      }
      if (this.jdField_a_of_type_Int < this.b)
      {
        localBuffer = new DownloadDataBuffer.Buffer();
        this.jdField_a_of_type_ArrayOfComTencentMttBrowserDownloadEngineDownloadDataBuffer$Buffer[this.mPoolAvailableSize] = localBuffer;
        this.mPoolAvailableSize += 1;
        this.jdField_a_of_type_Int += 1;
        localBuffer = a();
        return localBuffer;
      }
      DownloadDataBuffer.Buffer localBuffer = new DownloadDataBuffer.Buffer();
      return localBuffer;
    }
  }
  
  public boolean release(DownloadDataBuffer.Buffer paramBuffer)
  {
    synchronized (this.jdField_a_of_type_ArrayOfComTencentMttBrowserDownloadEngineDownloadDataBuffer$Buffer)
    {
      if (!a(paramBuffer))
      {
        if (this.mPoolAvailableSize < this.jdField_a_of_type_ArrayOfComTencentMttBrowserDownloadEngineDownloadDataBuffer$Buffer.length)
        {
          this.jdField_a_of_type_ArrayOfComTencentMttBrowserDownloadEngineDownloadDataBuffer$Buffer[this.mPoolAvailableSize] = paramBuffer;
          this.mPoolAvailableSize += 1;
          return true;
        }
        return true;
      }
      throw new IllegalStateException("Already in the pool!");
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\download\engine\SynchronizedPool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */