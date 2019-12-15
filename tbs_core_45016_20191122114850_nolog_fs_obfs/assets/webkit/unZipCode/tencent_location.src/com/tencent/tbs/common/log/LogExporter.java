package com.tencent.tbs.common.log;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class LogExporter
{
  public static String exportAllHeaders()
  {
    return LogManager.getInstance().getAllHeaders();
  }
  
  public static String exportAllLogToFile(String paramString, float paramFloat, int paramInt)
  {
    try
    {
      Object localObject = new File(paramString);
      File localFile = ((File)localObject).getParentFile();
      if (!localFile.exists()) {
        localFile.mkdirs();
      }
      try
      {
        localObject = new FileWriter((File)localObject);
        try
        {
          if (LogManager.getInstance().getAllLogs((Writer)localObject, false, System.currentTimeMillis() - (paramFloat * 60.0F * 60.0F * 1000.0F), -1L, paramInt, new char[0]))
          {
            ((FileWriter)localObject).flush();
            ((FileWriter)localObject).close();
            return paramString;
          }
          ((FileWriter)localObject).flush();
          ((FileWriter)localObject).close();
          return null;
        }
        catch (Exception localException2)
        {
          paramString = (String)localObject;
          localObject = localException2;
        }
        localException1.printStackTrace();
      }
      catch (Exception localException1)
      {
        paramString = null;
      }
      if (paramString != null) {
        paramString.close();
      }
      return null;
    }
    catch (Throwable paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  public static Bitmap exportAllLogToImage(float paramFloat, int paramInt)
  {
    for (;;)
    {
      int i;
      int k;
      try
      {
        Object localObject1 = LogManager.getInstance();
        long l1 = System.currentTimeMillis();
        long l2 = (paramFloat * 60.0F * 60.0F * 1000.0F);
        int m = 0;
        localObject1 = ((LogManager)localObject1).getAllLogs(true, l1 - l2, -1L, paramInt, new char[0]);
        ArrayList localArrayList = new ArrayList(200);
        localArrayList.add(new LineRange(0, 0));
        paramInt = 0;
        i = 0;
        if (paramInt < ((String)localObject1).length())
        {
          if (((String)localObject1).charAt(paramInt) == '\r')
          {
            ((LineRange)localArrayList.get(localArrayList.size() - 1)).count = i;
            paramInt += 1;
            localArrayList.add(new LineRange(paramInt + 1, 0));
            j = 0;
            k = paramInt;
            i = j;
            if (j < 80) {
              break label453;
            }
            ((LineRange)localArrayList.get(localArrayList.size() - 1)).count = j;
            j = paramInt + 2;
            i = paramInt;
            if (j < ((String)localObject1).length())
            {
              i = paramInt;
              if (((String)localObject1).charAt(paramInt + 1) == '\r') {
                i = j;
              }
            }
            localArrayList.add(new LineRange(i + 1, 0));
            paramInt = 0;
            k = i;
            i = paramInt;
            break label453;
          }
        }
        else
        {
          if (i > 0) {
            ((LineRange)localArrayList.get(localArrayList.size() - 1)).count = i;
          } else {
            localArrayList.remove(localArrayList.size() - 1);
          }
          Bitmap localBitmap = Bitmap.createBitmap(720, (localArrayList.size() + 1) * 20, Bitmap.Config.RGB_565);
          Canvas localCanvas = new Canvas(localBitmap);
          localCanvas.drawColor(-1);
          Paint localPaint = new Paint();
          localPaint.setColor(-16777216);
          localPaint.setAntiAlias(true);
          localPaint.setTextSize(14.0F);
          paramInt = m;
          if (paramInt < localArrayList.size())
          {
            Object localObject2 = (LineRange)localArrayList.get(paramInt);
            localObject2 = ((String)localObject1).substring(((LineRange)localObject2).start, ((LineRange)localObject2).start + ((LineRange)localObject2).count);
            paramInt += 1;
            localCanvas.drawText((String)localObject2, 10.0F, paramInt * 20, localPaint);
            continue;
          }
          return localBitmap;
        }
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
        return null;
      }
      int j = i + 1;
      continue;
      label453:
      paramInt = k + 1;
    }
  }
  
  public static boolean exportAllLogToReport(float paramFloat, int paramInt)
  {
    LogManager.logReport(System.currentTimeMillis() - (paramFloat * 60.0F * 60.0F * 1000.0F), -1L, paramInt, new char[0]);
    return true;
  }
  
  public static String exportAllLogToString(float paramFloat, int paramInt)
  {
    return LogManager.getInstance().getAllLogs(false, System.currentTimeMillis() - (paramFloat * 60.0F * 60.0F * 1000.0F), -1L, paramInt, new char[0]);
  }
  
  public static String exportAllPreferences()
  {
    return LogManager.getInstance().getAllPreferences();
  }
  
  public static String exportAllRecords(float paramFloat, int paramInt)
  {
    return LogManager.getInstance().getAllRecords(false, System.currentTimeMillis() - (paramFloat * 60.0F * 60.0F * 1000.0F), -1L, paramInt, new char[0]);
  }
  
  private static class LineRange
  {
    public int count;
    public int start;
    
    public LineRange(int paramInt1, int paramInt2)
    {
      this.start = paramInt1;
      this.count = paramInt2;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\log\LogExporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */