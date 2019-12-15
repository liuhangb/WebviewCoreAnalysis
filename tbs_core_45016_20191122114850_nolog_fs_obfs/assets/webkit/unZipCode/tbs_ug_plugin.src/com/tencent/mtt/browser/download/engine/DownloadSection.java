package com.tencent.mtt.browser.download.engine;

public class DownloadSection
{
  public static String TAG = "DownloadSection";
  public static String sDivider = "|";
  public static String sDividerRegularExpression = "\\|";
  private int jdField_a_of_type_Int = -1;
  private volatile long jdField_a_of_type_Long = 0L;
  private DownloadDataBuffer jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadDataBuffer;
  private String jdField_a_of_type_JavaLangString = "0";
  private String b = "-1";
  private String c = "0";
  public int mCurrentIndex = 0;
  
  public DownloadSection(int paramInt, DownloadDataBuffer paramDownloadDataBuffer)
  {
    this.jdField_a_of_type_Int = paramInt;
    DownloadDataBuffer localDownloadDataBuffer = paramDownloadDataBuffer;
    if (paramDownloadDataBuffer == null) {
      localDownloadDataBuffer = new DownloadDataBuffer();
    }
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadDataBuffer = localDownloadDataBuffer;
  }
  
  public void addSectionDownloadLen(long paramLong)
  {
    this.jdField_a_of_type_Long += paramLong;
  }
  
  public void addSectionWriteLen(int paramInt, long paramLong)
  {
    for (;;)
    {
      int i;
      synchronized (this.c)
      {
        String[] arrayOfString = this.c.split(sDividerRegularExpression);
        String str1 = "";
        i = 0;
        if (i < arrayOfString.length)
        {
          StringBuilder localStringBuilder;
          if (i == paramInt)
          {
            long l = Long.parseLong(arrayOfString[i]) + paramLong;
            if (i == arrayOfString.length - 1)
            {
              localStringBuilder = new StringBuilder();
              localStringBuilder.append(str1);
              localStringBuilder.append(String.valueOf(l));
              str1 = localStringBuilder.toString();
            }
            else
            {
              localStringBuilder = new StringBuilder();
              localStringBuilder.append(str1);
              localStringBuilder.append(String.valueOf(l));
              localStringBuilder.append(sDivider);
              str1 = localStringBuilder.toString();
            }
          }
          else if (i == arrayOfString.length - 1)
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append(str1);
            localStringBuilder.append(arrayOfString[i]);
            str1 = localStringBuilder.toString();
          }
          else
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append(str1);
            localStringBuilder.append(arrayOfString[i]);
            localStringBuilder.append(sDivider);
            str1 = localStringBuilder.toString();
          }
        }
        else
        {
          this.c = str1;
          return;
        }
      }
      i += 1;
    }
  }
  
  public void changeCurrentSectionEndPos(long paramLong)
  {
    for (;;)
    {
      int i;
      synchronized (this.b)
      {
        String[] arrayOfString = this.b.split(sDividerRegularExpression);
        String str1 = "";
        i = 0;
        if (i < arrayOfString.length)
        {
          StringBuilder localStringBuilder;
          if (i == this.mCurrentIndex)
          {
            if (i == arrayOfString.length - 1)
            {
              localStringBuilder = new StringBuilder();
              localStringBuilder.append(str1);
              localStringBuilder.append(paramLong);
              str1 = localStringBuilder.toString();
            }
            else
            {
              localStringBuilder = new StringBuilder();
              localStringBuilder.append(str1);
              localStringBuilder.append(paramLong);
              localStringBuilder.append(sDivider);
              str1 = localStringBuilder.toString();
            }
          }
          else if (i == arrayOfString.length - 1)
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append(str1);
            localStringBuilder.append(arrayOfString[i]);
            str1 = localStringBuilder.toString();
          }
          else
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append(str1);
            localStringBuilder.append(arrayOfString[i]);
            localStringBuilder.append(sDivider);
            str1 = localStringBuilder.toString();
          }
        }
        else
        {
          this.b = str1;
          return;
        }
      }
      i += 1;
    }
  }
  
  public void changeCurrentSectionWritenPos(long paramLong)
  {
    for (;;)
    {
      int i;
      synchronized (this.c)
      {
        String[] arrayOfString = this.c.split(sDividerRegularExpression);
        String str1 = "";
        i = 0;
        if (i < arrayOfString.length)
        {
          StringBuilder localStringBuilder;
          if (i == this.mCurrentIndex)
          {
            if (i == arrayOfString.length - 1)
            {
              localStringBuilder = new StringBuilder();
              localStringBuilder.append(str1);
              localStringBuilder.append(paramLong);
              str1 = localStringBuilder.toString();
            }
            else
            {
              localStringBuilder = new StringBuilder();
              localStringBuilder.append(str1);
              localStringBuilder.append(paramLong);
              localStringBuilder.append(sDivider);
              str1 = localStringBuilder.toString();
            }
          }
          else if (i == arrayOfString.length - 1)
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append(str1);
            localStringBuilder.append(arrayOfString[i]);
            str1 = localStringBuilder.toString();
          }
          else
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append(str1);
            localStringBuilder.append(arrayOfString[i]);
            localStringBuilder.append(sDivider);
            str1 = localStringBuilder.toString();
          }
        }
        else
        {
          this.c = str1;
          return;
        }
      }
      i += 1;
    }
  }
  
  public void correctCurrentIndex()
  {
    String[] arrayOfString1 = new String[1];
    arrayOfString1 = new String[1];
    arrayOfString1 = new String[1];
    synchronized (this.jdField_a_of_type_JavaLangString)
    {
      arrayOfString1 = this.jdField_a_of_type_JavaLangString.split(sDividerRegularExpression);
      synchronized (this.b)
      {
        ??? = this.b.split(sDividerRegularExpression);
        synchronized (this.c)
        {
          String[] arrayOfString2 = this.c.split(sDividerRegularExpression);
          int i = 0;
          while (i < arrayOfString1.length)
          {
            if (Long.parseLong(arrayOfString1[i]) + Long.parseLong(arrayOfString2[i]) - 1L < Long.parseLong(???[i]))
            {
              this.mCurrentIndex = i;
              return;
            }
            i += 1;
          }
          return;
        }
      }
    }
  }
  
  public DownloadDataBuffer dataBuffer()
  {
    return this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadDataBuffer;
  }
  
  public long getCurrentSectionEndPos()
  {
    for (;;)
    {
      synchronized (this.b)
      {
        String[] arrayOfString = this.b.split(sDividerRegularExpression);
        int i = this.mCurrentIndex;
        if (i < arrayOfString.length)
        {
          l = Long.parseLong(arrayOfString[i]);
          return l;
        }
      }
      long l = -1L;
    }
  }
  
  public long getCurrentSectionStartPos()
  {
    for (;;)
    {
      synchronized (this.jdField_a_of_type_JavaLangString)
      {
        String[] arrayOfString = this.jdField_a_of_type_JavaLangString.split(sDividerRegularExpression);
        int i = this.mCurrentIndex;
        if (i < arrayOfString.length)
        {
          l = Long.parseLong(arrayOfString[i]);
          return l;
        }
      }
      long l = 0L;
    }
  }
  
  public long getCurrentSectionWriteLen()
  {
    for (;;)
    {
      synchronized (this.c)
      {
        String[] arrayOfString = this.c.split(sDividerRegularExpression);
        int i = this.mCurrentIndex;
        if (i < arrayOfString.length)
        {
          l = Long.parseLong(arrayOfString[i]);
          return l;
        }
      }
      long l = 0L;
    }
  }
  
  public long getSectionDownloadLen()
  {
    return this.jdField_a_of_type_Long;
  }
  
  public String getSectionEndPos()
  {
    synchronized (this.b)
    {
      String str2 = this.b;
      return str2;
    }
  }
  
  public int getSectionId()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public long getSectionNextDownloadPos()
  {
    return getCurrentSectionStartPos() + this.jdField_a_of_type_Long;
  }
  
  public String getSectionStartPos()
  {
    synchronized (this.jdField_a_of_type_JavaLangString)
    {
      String str2 = this.jdField_a_of_type_JavaLangString;
      return str2;
    }
  }
  
  public String getSectionWriteLen()
  {
    synchronized (this.c)
    {
      String str2 = this.c;
      return str2;
    }
  }
  
  public boolean hasRemain()
  {
    synchronized (this.jdField_a_of_type_JavaLangString)
    {
      String[] arrayOfString = this.jdField_a_of_type_JavaLangString.split(sDividerRegularExpression);
      return this.mCurrentIndex < arrayOfString.length - 1;
    }
  }
  
  public boolean isDownloadFinish()
  {
    long l1 = getCurrentSectionEndPos();
    long l2 = getCurrentSectionStartPos();
    return (l1 > 0L) && (l2 + this.jdField_a_of_type_Long > l1);
  }
  
  public boolean isPending()
  {
    return false;
  }
  
  public boolean isWriteFinish()
  {
    return (getCurrentSectionEndPos() > 0L) && (getCurrentSectionStartPos() + getCurrentSectionWriteLen() > getCurrentSectionEndPos());
  }
  
  public void setDownloadDataBuffer(DownloadDataBuffer paramDownloadDataBuffer)
  {
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadDataBuffer = paramDownloadDataBuffer;
  }
  
  public void setSectionDownloadLen(long paramLong)
  {
    try
    {
      this.jdField_a_of_type_Long = paramLong;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setSectionEndPos(String paramString)
  {
    synchronized (this.b)
    {
      this.b = paramString;
      return;
    }
  }
  
  public void setSectionId(int paramInt)
  {
    this.jdField_a_of_type_Int = paramInt;
  }
  
  public void setSectionStartPos(String paramString)
  {
    synchronized (this.jdField_a_of_type_JavaLangString)
    {
      this.jdField_a_of_type_JavaLangString = paramString;
      return;
    }
  }
  
  public void setSectionWriteLen(String paramString)
  {
    synchronized (this.c)
    {
      this.c = paramString;
      return;
    }
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("sectionId=");
    localStringBuffer.append(this.jdField_a_of_type_Int);
    localStringBuffer.append(",");
    localStringBuffer.append("sectionStartPos=");
    localStringBuffer.append(getCurrentSectionStartPos());
    localStringBuffer.append(",");
    localStringBuffer.append("sectionEndPos=");
    localStringBuffer.append(getCurrentSectionEndPos());
    localStringBuffer.append(",");
    localStringBuffer.append("secDownloadLen=");
    localStringBuffer.append(this.jdField_a_of_type_Long);
    localStringBuffer.append(",");
    localStringBuffer.append("sectionWritePos=");
    localStringBuffer.append(getCurrentSectionWriteLen());
    return localStringBuffer.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\download\engine\DownloadSection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */