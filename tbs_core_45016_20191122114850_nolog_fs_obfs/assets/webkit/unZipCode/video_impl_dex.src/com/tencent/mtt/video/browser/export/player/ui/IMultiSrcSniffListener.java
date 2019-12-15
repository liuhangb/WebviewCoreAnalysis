package com.tencent.mtt.video.browser.export.player.ui;

import java.util.ArrayList;

public abstract interface IMultiSrcSniffListener
{
  public abstract void onMultiSrcSniffComplete(ArrayList<String> paramArrayList, ArrayList<Integer> paramArrayList1, int paramInt);
  
  public abstract void onSrcPicTaskComplete(byte[] paramArrayOfByte, String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\player\ui\IMultiSrcSniffListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */