package com.tencent.mtt.external.reader;

import android.app.Activity;
import android.view.View;
import dalvik.system.DexClassLoader;

public abstract interface IReader
{
  public static final int ANIMATIONCOLOR = 25;
  public static final int ANIMATIONTYPE = 21;
  public static final int ANIMATIONTYPE_BEZIER = 3;
  public static final int ANIMATIONTYPE_NONE = 0;
  public static final int ANIMATIONTYPE_SLIDE = 1;
  public static final int ANIMATIONTYPE_SMOOTH = 2;
  public static final int CACHECOMPLETE = 20;
  public static final int CHANGECOLOR = 15;
  public static final int CHANGEFONT = 4;
  public static final int CHANGEMODE = 22;
  public static final int CHANGERESOUCEPATH = 26;
  public static final int CHECK_SCALE_ABILITY = 1;
  public static final int CHM_CAN_GO_NEXT_URL = 304;
  public static final int CHM_CAN_GO_PREV_URL = 303;
  public static final int CHM_GET_NEXT_URL = 306;
  public static final int CHM_GET_PREV_URL = 305;
  public static final int CHM_PARSERURL = 301;
  public static final int CHM_UPDATECURRENTURL = 302;
  public static final int DISPLAY_VIEW_MARGIN = 3;
  public static final int ENABLE_EDIT_FEATURE = 316;
  public static final int ERR_CODE_CHECK_FILE_FORMAT_FAIL = -101;
  public static final int ERR_CODE_NEED_PASSWORD = -100;
  public static final int ERR_CODE_NO_ERROR = 0;
  public static final int EXITSELECTMODE = 33;
  public static final int FILMMODE = 24;
  public static final int FLIPMODE = 6;
  public static final int GETCHARSPACE = 14;
  public static final int GETFONTSIZE = 10;
  public static final int GETLINESPACE = 12;
  public static final int GETPOSITION = 8;
  public static final int GETSELECTCONTENT = 32;
  public static final int GET_COPY_STRING = 10005;
  public static final int GET_NAME = 10001;
  public static final int GET_SCREEN_INFO = 59;
  public static final int GET_VERSION = 10000;
  public static final int GOTOPOSITION = 7;
  public static final int HANDLE_BACK_PRESS = 20000;
  public static final int NEXTPAGE = 17;
  public static final int NOVELMODE = 2;
  public static final int OPENCHAPTER = 19;
  public static final int PDF_GETCONTENT = 201;
  public static final int PDF_GET_OUTLINE_DATA = 308;
  public static final int PDF_RESPONSE_PASSWORD = 309;
  public static final int PDF_SEEK_PROGRESS = 307;
  public static final int PREVPAGE = 18;
  public static final int QUERY_ENCRYPT_SUPPORT = 312;
  public static final int QUERY_FIND_MODE_SUPPORT = 311;
  public static final int REFRESH = 5;
  public static final int SCREENSHOT = 28;
  public static final int SCROLLMODE = 23;
  public static final int SETACCESSORYENABLE = 27;
  public static final int SETBATTERY = 30;
  public static final int SETBATTERYBG = 16;
  public static final int SETCHARSPACE = 13;
  public static final int SETFONTSIZE = 9;
  public static final int SETLINESPACE = 11;
  public static final int SETPURCHASEVIEW = 35;
  public static final int SETSELECTBARBITMAP = 31;
  public static final int SETTIME = 29;
  public static final int SET_ADVIEW = 71;
  public static final int SET_BROWSER_MODE = 10002;
  public static final int SET_EXTRA_INFO = 58;
  public static final int SET_FIND_MODE = 310;
  public static final int SET_FIND_NEXT = 323;
  public static final int SET_FIND_PREV = 322;
  public static final int SET_FIND_TEXT = 321;
  public static final int SUPPORT_FITSCREEN = 10003;
  public static final int TTS_CONTENT = 0;
  public static final int TTS_ENDCHAPTER = 2;
  public static final int TTS_NEXTPAGE = 1;
  public static final int UPDATENOVELINFO = 34;
  
  public abstract void addPlugin(DexClassLoader paramDexClassLoader, String paramString);
  
  public abstract void doAction(int paramInt, Object paramObject1, Object paramObject2);
  
  public abstract boolean isDocumentTop();
  
  public abstract void openBook(String paramString1, String paramString2);
  
  public abstract void setActivity(Activity paramActivity);
  
  public abstract void setLibsPath(String paramString1, String paramString2);
  
  public abstract void setListener(IReaderCallbackListener paramIReaderCallbackListener);
  
  public abstract void setMainView(View paramView);
  
  public abstract void setNightMode(boolean paramBoolean);
  
  public abstract void setRootView(View paramView);
  
  public abstract void toFinish();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\IReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */