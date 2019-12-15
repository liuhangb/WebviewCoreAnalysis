package com.tencent.mtt.external.reader;

import android.view.View;

public abstract interface IReaderFile
{
  public static final int READER_BACK_BTN = 7;
  public static final int READER_EVENT_OTHER_OPEN = 3;
  public static final int READER_EVENT_RESUME = 4;
  public static final int READER_EVENT_SAV = 2;
  public static final int READER_EVENT_SEND = 1;
  public static final int READER_LAST_CHAPTER = 8;
  public static final int READER_NEXT_CHAPTER = 9;
  public static final int READER_TYPE_APK = 14;
  public static final int READER_TYPE_CHM = 13;
  public static final int READER_TYPE_DOCUMENT = 0;
  public static final int READER_TYPE_DOCUMENT_ONLINE = 5;
  public static final int READER_TYPE_DOCUMENT_ZIP = 6;
  public static final int READER_TYPE_MUSIC = 7;
  public static final int READER_TYPE_MUSIC_LIST = 9;
  public static final int READER_TYPE_MUSIC_ONLINE = 8;
  public static final int READER_TYPE_NOT_SUPPORT = 10;
  public static final int READER_TYPE_SVG = 11;
  public static final int READER_TYPE_SVG_URL = 12;
  
  public abstract boolean askCanGoback();
  
  public abstract int getReaderType();
  
  public abstract View getRootView();
  
  public abstract boolean handleEvent(int paramInt);
  
  public abstract int load();
  
  public abstract void notifySkinChanged();
  
  public abstract void onSizeChanged(int paramInt1, int paramInt2);
  
  public abstract void save();
  
  public abstract void unload();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\IReaderFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */