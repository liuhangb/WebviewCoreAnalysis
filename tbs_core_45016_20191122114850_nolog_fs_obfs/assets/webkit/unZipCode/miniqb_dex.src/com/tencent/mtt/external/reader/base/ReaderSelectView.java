package com.tencent.mtt.external.reader.base;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;

public class ReaderSelectView
  extends ReaderTypeView
{
  protected Context mContext = null;
  protected boolean mSupportEdit = false;
  
  public ReaderSelectView(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public void destroy()
  {
    this.mContext = null;
  }
  
  public void enterSelect() {}
  
  public void setBundle(Bundle paramBundle)
  {
    if (paramBundle != null) {
      this.mSupportEdit = paramBundle.getBoolean("supportedit", false);
    }
  }
  
  public void setSelectViewHidden(boolean paramBoolean) {}
  
  public void showSelectView(Rect paramRect, String paramString) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderSelectView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */