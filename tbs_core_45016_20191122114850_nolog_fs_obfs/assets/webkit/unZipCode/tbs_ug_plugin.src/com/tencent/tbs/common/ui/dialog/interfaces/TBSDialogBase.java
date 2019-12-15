package com.tencent.tbs.common.ui.dialog.interfaces;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class TBSDialogBase
  extends Dialog
  implements TBSDialog
{
  public static final int PROPERTY_ADAPTER = 6;
  public static final int PROPERTY_BUTTONS = 3;
  public static final int PROPERTY_CANCEL = 4;
  public static final int PROPERTY_DRAWABLE = 10;
  public static final int PROPERTY_INTENT = 5;
  public static final int PROPERTY_INTENT_SENDING_CALLBACK = 7;
  public static final int PROPERTY_MESSAGE = 2;
  public static final int PROPERTY_MESSAGE_ALIGN = 8;
  public static final int PROPERTY_ONKEY = 9;
  public static final int PROPERTY_TITLE = 1;
  private static float sDensity = -1.0F;
  protected Map<Integer, Object> mProperties;
  private Context mResContext;
  
  public TBSDialogBase(Context paramContext)
  {
    super(paramContext, 16973835);
    initDensity(paramContext.getApplicationContext());
  }
  
  private void initDensity(Context paramContext)
  {
    if (sDensity < 0.0F)
    {
      paramContext = (WindowManager)paramContext.getSystemService("window");
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      paramContext.getDefaultDisplay().getMetrics(localDisplayMetrics);
      sDensity = localDisplayMetrics.density;
    }
  }
  
  public int dip2px(float paramFloat)
  {
    return (int)(paramFloat * sDensity + 0.5F);
  }
  
  protected View findViewById(String paramString)
  {
    return findViewById(this.mResContext.getResources().getIdentifier(paramString, "id", this.mResContext.getPackageName()));
  }
  
  protected int getIdentifier(String paramString1, String paramString2)
  {
    return this.mResContext.getResources().getIdentifier(paramString1, paramString2, this.mResContext.getPackageName());
  }
  
  public abstract void initUI();
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    initUI();
  }
  
  protected void setButton(Button paramButton, final DialogButton paramDialogButton)
  {
    paramButton.setText(paramDialogButton.label);
    paramButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (paramDialogButton.diListener != null) {
          paramDialogButton.diListener.onClick(TBSDialogBase.this, paramDialogButton.which);
        }
        if ((paramDialogButton.which == -1) || (paramDialogButton.which == -3) || (paramDialogButton.which == -2)) {
          TBSDialogBase.this.dismiss();
        }
      }
    });
  }
  
  public void setProperties(Map<Integer, Object> paramMap)
  {
    this.mProperties = paramMap;
  }
  
  protected List<DialogButton> sortButtons()
  {
    Object localObject = (Map)this.mProperties.get(Integer.valueOf(3));
    DialogButton localDialogButton1 = (DialogButton)((Map)localObject).get(Integer.valueOf(-1));
    DialogButton localDialogButton2 = (DialogButton)((Map)localObject).get(Integer.valueOf(-3));
    localObject = (DialogButton)((Map)localObject).get(Integer.valueOf(-2));
    ArrayList localArrayList = new ArrayList();
    if (localDialogButton1 != null) {
      localArrayList.add(localDialogButton1);
    }
    if (localDialogButton2 != null) {
      localArrayList.add(localDialogButton2);
    }
    if (localObject != null) {
      localArrayList.add(localObject);
    }
    return localArrayList;
  }
  
  public static class DialogButton
  {
    public DialogInterface.OnClickListener diListener;
    public String label;
    public int which;
    
    DialogButton(int paramInt, String paramString)
    {
      this(paramInt, paramString, null);
    }
    
    public DialogButton(int paramInt, String paramString, DialogInterface.OnClickListener paramOnClickListener)
    {
      this.which = paramInt;
      this.label = paramString;
      this.diListener = paramOnClickListener;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\dialog\interfaces\TBSDialogBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */