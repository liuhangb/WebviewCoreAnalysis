package com.tencent.mtt.external.reader;

public abstract interface IReaderCallbackListener
{
  public static final String BOOLEAN_FLAG_KEY = "bflag";
  public static final String BROWSER_READER_KEY_ADCONTENT = "ADContent";
  public static final String BROWSER_READER_KEY_ADDOWN = "ADDownload";
  public static final String BROWSER_READER_KEY_ADURL = "ADURL";
  public static final String BROWSER_READER_KEY_EXPOSEURL = "ADExposeUrl";
  public static final String BROWSER_READER_KEY_NAME = "ADName";
  public static final int BUTTON_AD = 1;
  public static final int BUTTON_QUAN = 2;
  public static final int CHM_PARSERRESULT = 301;
  public static final int CREATE_COMMENTVIEW = 31;
  public static final int CREATE_PURCHASEVIEW = 25;
  public static final int ENTER_SELECTMODE = 22;
  public static final int EPUB_ENCRYPTED = 401;
  public static final int EPUB_ERROREND = 499;
  public static final int EPUB_FONT_INFO = 45;
  public static final int EPUB_OPENFAILED = 400;
  public static final int EPUB_UNSUPPORTEDOPERATION = 402;
  public static final int EXIT_SELECTMODE = 23;
  public static final int FILE_NOT_EXIST = 2001;
  public static final int GET_CHAPTERCONTENTBYID = 9;
  public static final int HIDE_ANNOTATION_VIEW = 47;
  public static final int HIDE_BITMAP_MENU = 49;
  public static final int HIDE_LOADING = 17;
  public static final int HIDE_SELECTMENU = 21;
  public static final String KEY_ERR_CODE = "errcode";
  public static final String KEY_ERR_MSG = "errmsg";
  public static final int LOG = 30;
  public static final int NOTIFY_AddContentAll = 51;
  public static final int NOTIFY_CANCELTURNPAGE = 34;
  public static final int NOTIFY_CANDISPLAY = 12;
  public static final int NOTIFY_CHANGEFONTSIZEEND = 18;
  public static final int NOTIFY_CHANGEPAGE = 6;
  public static final int NOTIFY_CHAPTERCHANGE = 10;
  public static final int NOTIFY_COPYRESULT = 102;
  public static final int NOTIFY_Common_Info = 100;
  public static final int NOTIFY_ERRORCODE = 19;
  public static final int NOTIFY_EXTRACTORCHAPTER = 28;
  public static final int NOTIFY_FINDRESULT = 101;
  public static final int NOTIFY_ISATBEGIN = 13;
  public static final int NOTIFY_ISATEND = 14;
  public static final int NOTIFY_MENUPARSERED = 35;
  public static final int NOTIFY_NEEDCACHECHAPTER = 15;
  public static final int NOTIFY_NEXTCHAPTER = 8;
  public static final int NOTIFY_PAGECOUNT = 26;
  public static final int NOTIFY_PREVCHAPTER = 7;
  public static final int NOTIFY_RETRYFIND = 32;
  public static final int NOTIFY_SCROLL_INFO = 50;
  public static final int NOTIFY_SHOWAD = 29;
  public static final int NOTIFY_SPECIALCLICKED = 27;
  public static final int NOTIFY_STARTGESTURE = 11;
  public static final int NOTIFY_STARTTURNPAGE = 33;
  public static final int PDF_CONTAIN_OUTLINE = 204;
  public static final int PDF_GETCONTENT_ERROR = 202;
  public static final int PDF_GETCONTENT_ONPROGRESS = 203;
  public static final String PDF_GETCONTENT_PATH = "pdf_content_path";
  public static final int PDF_GETCONTENT_SUCCESS = 201;
  public static final int PDF_GETCONTENT_TERMINATED = 207;
  public static final int PDF_GETOUTLINE_FAILED = 206;
  public static final int PDF_GETOUTLINE_SUCCESS = 205;
  public static final int PDF_TOAST = 1;
  public static final int REPORT_ERROR = 210;
  public static final int RESPONSE_PASSWORD_VERIFY = 220;
  public static final int SET_WINDOW_NEED_GESTURE = 24;
  public static final int SHOW_ANNOTATION_VIEW = 46;
  public static final int SHOW_BITMAP_MENU = 48;
  public static final int SHOW_LOADING = 16;
  public static final int SHOW_SELECTMENU = 20;
  public static final int SNAPSHOT_DATA_READY = 209;
  public static final int SNAPSHOT_INIT_DATA = 208;
  public static final int WEBVIEW_ADDJSI = 3;
  public static final int WEBVIEW_CREATE = 2;
  public static final int WEBVIEW_DESTROY = 5;
  public static final int WEBVIEW_FITSCREEN = 2000;
  public static final int WEBVIEW_LOADDATA = 4;
  public static final int WEBVIEW_LOADURL = 1000;
  public static final int WEBVIEW_SUPPORT_PROMPT = 44;
  
  public abstract void callbackAction(int paramInt, Object paramObject1, Object paramObject2);
  
  public abstract void notifyScrollThumbRatio(float paramFloat);
  
  public abstract void onScaleBegin(float paramFloat);
  
  public abstract void onScaleEnd(float paramFloat);
  
  public abstract void onScroll(float paramFloat);
  
  public abstract void onScrollBegin(float paramFloat);
  
  public abstract void onScrollEnd(float paramFloat);
  
  public abstract void onSingleTap(int paramInt1, int paramInt2);
  
  public abstract void openBookFailed();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\IReaderCallbackListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */