package com.tencent.mtt.browser.download.engine;

import java.util.concurrent.ConcurrentLinkedQueue;

public class DownloadDataBuffer
{
  private static SynchronizedPool jdField_a_of_type_ComTencentMttBrowserDownloadEngineSynchronizedPool;
  private volatile ConcurrentLinkedQueue<Buffer> jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue = new ConcurrentLinkedQueue();
  
  public static void GCAllBuffer()
  {
    SynchronizedPool localSynchronizedPool = jdField_a_of_type_ComTencentMttBrowserDownloadEngineSynchronizedPool;
    if (localSynchronizedPool != null) {
      localSynchronizedPool.GCAllBuffer();
    }
  }
  
  public static Buffer obtainBuffer()
  {
    try
    {
      if (jdField_a_of_type_ComTencentMttBrowserDownloadEngineSynchronizedPool == null) {
        jdField_a_of_type_ComTencentMttBrowserDownloadEngineSynchronizedPool = new SynchronizedPool(128);
      }
      Buffer localBuffer = jdField_a_of_type_ComTencentMttBrowserDownloadEngineSynchronizedPool.acquire();
      if (localBuffer != null)
      {
        localBuffer.a();
        return localBuffer;
      }
      return null;
    }
    finally {}
  }
  
  public static void recyleBuffer(Buffer paramBuffer)
  {
    if (paramBuffer != null) {
      jdField_a_of_type_ComTencentMttBrowserDownloadEngineSynchronizedPool.release(paramBuffer);
    }
  }
  
  public void appendItem(Buffer paramBuffer)
  {
    this.jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.offer(paramBuffer);
  }
  
  public Buffer peekItem()
  {
    return (Buffer)this.jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.peek();
  }
  
  public Buffer removeItem()
  {
    return (Buffer)this.jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.poll();
  }
  
  public int size()
  {
    return this.jdField_a_of_type_JavaUtilConcurrentConcurrentLinkedQueue.size();
  }
  
  public static class Buffer
  {
    public static int BUFFERLEN = 32768;
    public byte[] data;
    public int len = 0;
    public int mBufferSectionIndex = 0;
    public DownloadSection mDownloadSection;
    public long offset = -1L;
    public int sectionId = -1;
    public int taskId = -1;
    
    void a()
    {
      this.taskId = -1;
      this.sectionId = -1;
      this.offset = -1L;
      this.len = 0;
      if (this.data == null) {
        this.data = new byte[BUFFERLEN];
      }
    }
    
    public void initBuffer(int paramInt1, int paramInt2, long paramLong)
    {
      this.taskId = paramInt1;
      this.sectionId = paramInt2;
      this.offset = paramLong;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\download\engine\DownloadDataBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */