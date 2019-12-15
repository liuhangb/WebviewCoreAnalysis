package com.tencent.mtt.browser.download.engine;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.common.utils.FileUtilsF;
import com.tencent.downloadprovider.DownloadproviderHelper;
import com.tencent.mtt.base.utils.DLMttFileUtils;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HttpFileWriter
{
  public static final int MSG_DOWNLOADENGINE_TO_WRITETHREAD_ADD_TASK = 3;
  public static final int MSG_DOWNLOADENGINE_TO_WRITETHREAD_DEL_TASK = 4;
  public static final int MSG_DOWNLOADENGINE_TO_WRITETHREAD_RENAMEFILE = 1;
  public static final int MSG_DOWNLOADENGINE_TO_WRITETHREAD_WRITEFILE = 2;
  public static String TAG = "HttpFileWriter";
  static HttpFileWriter jdField_a_of_type_ComTencentMttBrowserDownloadEngineHttpFileWriter;
  Handler jdField_a_of_type_AndroidOsHandler = null;
  HandlerThread jdField_a_of_type_AndroidOsHandlerThread;
  ConcurrentHashMap<Integer, DownloadTask> jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap = new ConcurrentHashMap();
  boolean jdField_a_of_type_Boolean = false;
  
  public static HttpFileWriter getInstance()
  {
    if (jdField_a_of_type_ComTencentMttBrowserDownloadEngineHttpFileWriter == null) {
      jdField_a_of_type_ComTencentMttBrowserDownloadEngineHttpFileWriter = new HttpFileWriter();
    }
    return jdField_a_of_type_ComTencentMttBrowserDownloadEngineHttpFileWriter;
  }
  
  public DownloadTask addDownloadingTask(DownloadTask paramDownloadTask)
  {
    initWriteThread();
    if ((paramDownloadTask != null) && (paramDownloadTask.getDownloadTaskId() != -1))
    {
      Message localMessage = this.jdField_a_of_type_AndroidOsHandler.obtainMessage();
      localMessage.what = 3;
      localMessage.obj = paramDownloadTask;
      this.jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
      return paramDownloadTask;
    }
    return null;
  }
  
  public void initWriteThread()
  {
    try
    {
      HandlerThread localHandlerThread = this.jdField_a_of_type_AndroidOsHandlerThread;
      if (localHandlerThread != null) {
        return;
      }
      try
      {
        this.jdField_a_of_type_AndroidOsHandlerThread = new HandlerThread("httpWriteThread", 10);
        if (this.jdField_a_of_type_AndroidOsHandlerThread != null) {
          this.jdField_a_of_type_AndroidOsHandlerThread.start();
        }
        this.jdField_a_of_type_AndroidOsHandler = new Handler(this.jdField_a_of_type_AndroidOsHandlerThread.getLooper(), new Handler.Callback()
        {
          public boolean handleMessage(Message paramAnonymousMessage)
          {
            Object localObject1;
            switch (paramAnonymousMessage.what)
            {
            default: 
              return false;
            case 4: 
              paramAnonymousMessage = (DownloadTask)paramAnonymousMessage.obj;
              HttpFileWriter.this.jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.remove(Integer.valueOf(paramAnonymousMessage.getDownloadTaskId()));
              paramAnonymousMessage.quitWorkerThread();
              DownloadproviderHelper.c(paramAnonymousMessage.getDownloadTaskId());
              return false;
            case 3: 
              paramAnonymousMessage = (DownloadTask)paramAnonymousMessage.obj;
              if (!HttpFileWriter.this.jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.containsKey(Integer.valueOf(paramAnonymousMessage.getDownloadTaskId())))
              {
                HttpFileWriter.this.jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.put(Integer.valueOf(paramAnonymousMessage.getDownloadTaskId()), paramAnonymousMessage);
                return false;
              }
              break;
            case 2: 
              HttpFileWriter.this.jdField_a_of_type_Boolean = true;
              int i = 1;
              while ((i != 0) && (HttpFileWriter.this.jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap != null) && (!HttpFileWriter.this.jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.isEmpty()))
              {
                paramAnonymousMessage = HttpFileWriter.this.jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.entrySet().iterator();
                i = 0;
                while (paramAnonymousMessage.hasNext())
                {
                  localObject1 = (DownloadTask)((Map.Entry)paramAnonymousMessage.next()).getValue();
                  if (localObject1 != null)
                  {
                    int j = i;
                    if (((DownloadTask)localObject1).writeBuffer()) {
                      j = 1;
                    }
                    i = j;
                    if (((DownloadTask)localObject1).isDownloaderFinish())
                    {
                      i = j;
                      if (((DownloadTask)localObject1).isBufferEmpty())
                      {
                        ((DownloadTask)localObject1).tryToFinisDownloadTask();
                        HttpFileWriter.this.jdField_a_of_type_JavaUtilConcurrentConcurrentHashMap.remove(Integer.valueOf(((DownloadTask)localObject1).getDownloadTaskId()));
                        i = j;
                      }
                    }
                  }
                }
              }
              HttpFileWriter.this.jdField_a_of_type_Boolean = false;
              return false;
            case 1: 
              paramAnonymousMessage = (DownloadTask)paramAnonymousMessage.obj;
              localObject1 = paramAnonymousMessage.mDownloadOps.mValue;
              if (TextUtils.isEmpty((CharSequence)localObject1)) {
                return false;
              }
              Object localObject2 = paramAnonymousMessage.getFileName();
              if ((localObject2 != null) && (!((String)localObject2).equalsIgnoreCase((String)localObject1)) && (!FileUtilsF.isSameFileName((String)localObject2, (String)localObject1)))
              {
                localObject2 = DLMttFileUtils.getDownloadTypeIcon(paramAnonymousMessage.getFileName(), paramAnonymousMessage.getFileFolderPath());
                paramAnonymousMessage.rename((String)localObject1);
                paramAnonymousMessage.mDownloadOps.clear();
                if (localObject2 != null) {
                  DLMttFileUtils.saveDownloadFileTypeIcon((String)localObject1, paramAnonymousMessage.getFileFolderPath(), (Bitmap)localObject2);
                }
                localObject2 = new ContentValues();
                ((ContentValues)localObject2).put("id", Integer.valueOf(paramAnonymousMessage.getDownloadTaskId()));
                ((ContentValues)localObject2).put("filename", (String)localObject1);
                ((ContentValues)localObject2).put("download_operations", "");
                DownloadproviderHelper.a((ContentValues)localObject2, true);
              }
              break;
            }
            return false;
          }
        });
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
      return;
    }
    finally {}
  }
  
  public void removeDownloadingTask(DownloadTask paramDownloadTask, boolean paramBoolean)
  {
    paramDownloadTask.waitForAllBufferBeWritten();
    DownloadproviderHelper.a(paramDownloadTask);
    initWriteThread();
    if (paramDownloadTask.getDownloadTaskId() == -1) {
      return;
    }
    Message localMessage = this.jdField_a_of_type_AndroidOsHandler.obtainMessage();
    localMessage.what = 4;
    localMessage.obj = paramDownloadTask;
    this.jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
  }
  
  public void renameTaskFileName(DownloadTask paramDownloadTask)
  {
    initWriteThread();
    Message localMessage = this.jdField_a_of_type_AndroidOsHandler.obtainMessage();
    localMessage.what = 1;
    localMessage.obj = paramDownloadTask;
    this.jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
  }
  
  public void tellWriteThreadWriteFile()
  {
    initWriteThread();
    if (!this.jdField_a_of_type_Boolean) {
      this.jdField_a_of_type_AndroidOsHandler.sendEmptyMessage(2);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\download\engine\HttpFileWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */