package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class UserBehaviorPV
  extends JceStruct
{
  public static final String MERGE_GUID_NONE_MAIN_HOST = "ZZTG1";
  public static final String MERGE_GUID_QB_NONE_SD_FILE = "ZZTG2";
  public static final String MERGE_GUID_REPORT_LAST_FAILED = "ZZTG4";
  public static final String MERGE_GUID_SAVE_FAILED = "ZZTG5";
  public static final String MERGE_GUID_SAVE_OK = "ZZTG6";
  public static final String MERGE_GUID_UPLOAD_FAILED = "ZZTG7";
  public static final String MERGE_GUID_UPLOAD_OK = "ZZTG8";
  public static final String X5_GAME_PLAYER_HOVER_BUTTON_MOV = "ASCD04";
  public static final String X5_GAME_PLAYER_MENU_CLOSE_BACKKEY = "ASCD03";
  public static final String X5_GAME_PLAYER_MENU_DISPLAY = "ASCD01";
  public static final String X5_GAME_PLAYER_MENU_DISPLAY_USER_TOUCH = "ASCD02";
  public static final String X5_GAME_PLAYER_MENU_GAME_EXIT = "ASCD05";
  public static final String X5_GAME_PLAYER_MENU_GAME_MORE = "ASCD08";
  public static final String X5_GAME_PLAYER_MENU_GAME_SEND_DESKTOP = "ASCD06";
  public static final String X5_GAME_PLAYER_MENU_GAME_SHARE = "ASCD07";
  public static final String X5_GAME_PLAYER_SHARE_TO_QQ = "ASCD11";
  public static final String X5_GAME_PLAYER_SHARE_TO_QZONE = "ASCD13";
  public static final String X5_GAME_PLAYER_SHARE_TO_TIMELINE = "ASCD10";
  public static final String X5_GAME_PLAYER_SHARE_TO_WX = "ASCD12";
  public String mBehaviorAction = "";
  public int mBehaviorType = -1;
  public boolean mIsAccu = false;
  public int mPV = 0;
  
  public UserBehaviorPV()
  {
    setBehaviorType(this.mBehaviorType);
    setIPV(this.mPV);
    setBehaviorAction(this.mBehaviorAction);
  }
  
  public UserBehaviorPV(int paramInt1, int paramInt2)
  {
    setBehaviorType(paramInt1);
    setIPV(paramInt2);
  }
  
  public UserBehaviorPV(int paramInt1, int paramInt2, String paramString)
  {
    setBehaviorType(paramInt1);
    setIPV(paramInt2);
    setBehaviorAction(paramString);
  }
  
  private static void appendSetting(StringBuilder paramStringBuilder, String paramString, int paramInt)
  {
    if (paramStringBuilder.length() > 0) {
      paramStringBuilder.append('&');
    }
    paramStringBuilder.append(paramString);
    paramStringBuilder.append('=');
    paramStringBuilder.append(paramInt);
  }
  
  private static void appendSetting(StringBuilder paramStringBuilder, String paramString, boolean paramBoolean)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public String getIPV()
  {
    return Integer.toString(this.mPV);
  }
  
  public String getKeyValue()
  {
    return this.mBehaviorAction;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.mBehaviorType = paramJceInputStream.read(this.mBehaviorType, 1, true);
    this.mPV = paramJceInputStream.read(this.mPV, 2, true);
    this.mBehaviorAction = paramJceInputStream.read(this.mBehaviorAction, 3, true);
  }
  
  public void setBehaviorAction(String paramString)
  {
    this.mBehaviorAction = paramString;
  }
  
  public void setBehaviorType(int paramInt)
  {
    this.mBehaviorType = paramInt;
  }
  
  public void setIPV(int paramInt)
  {
    this.mPV = paramInt;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.mBehaviorType, 1);
    paramJceOutputStream.write(this.mPV, 2);
    paramJceOutputStream.write(this.mBehaviorAction, 3);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\UserBehaviorPV.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */