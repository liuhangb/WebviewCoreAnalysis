package com.tencent.tbs.common.ui.dialog;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.tencent.tbs.common.resources.TBSResources;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HwActivityPickerSearchDialog
  extends Dialog
  implements TBSDialog
{
  private static final String TAG = "HwActivityPickerDialog";
  private BaseAdapter adapter;
  private Button btn_confirm;
  private String checkedPkg;
  private View contentView;
  private FrameLayout fl_progress;
  private IPickerDialogCallback iPickerDialogCallback;
  private IPickerDialogHandler iPickerDialogHandler;
  private Map<String, Drawable> mDrawables;
  private ArrayList<String> pkgList;
  Map<String, Integer> sortWeight = new HashMap();
  private TextView tv_progress;
  private View v_progress_bar;
  
  public HwActivityPickerSearchDialog(Context paramContext, Map<String, Drawable> paramMap)
  {
    super(paramContext, loadIdentifierResource("x5_hw_picker_dialogTheme", "style"));
    this.sortWeight.put("com.taobao.taobao", Integer.valueOf(100));
    this.sortWeight.put("com.moji.mjweather", Integer.valueOf(98));
    this.sortWeight.put("com.ss.android.article.news", Integer.valueOf(97));
    this.sortWeight.put("com.ss.android.article.video", Integer.valueOf(96));
    this.sortWeight.put("com.youku.phone", Integer.valueOf(95));
    this.sortWeight.put("com.netease.cloudmusic", Integer.valueOf(93));
    this.sortWeight.put("com.sina.weibo", Integer.valueOf(92));
    this.sortWeight.put("com.cleanmaster.mguard_cn", Integer.valueOf(91));
    this.sortWeight.put("com.UCMobile", Integer.valueOf(90));
    this.sortWeight.put("com.android.browser", Integer.valueOf(-1));
    this.pkgList = new ArrayList();
    this.mDrawables = new HashMap();
    this.checkedPkg = null;
    this.adapter = null;
    if (paramMap != null) {
      this.mDrawables = new HashMap(paramMap);
    }
    paramContext = this.mDrawables.entrySet().iterator();
    while (paramContext.hasNext())
    {
      paramMap = (Map.Entry)paramContext.next();
      this.pkgList.add(paramMap.getKey());
    }
    Collections.sort(this.pkgList, new Comparator()
    {
      public int compare(String paramAnonymousString1, String paramAnonymousString2)
      {
        paramAnonymousString1 = (Integer)HwActivityPickerSearchDialog.this.sortWeight.get(paramAnonymousString1);
        int j = 0;
        int i;
        if (paramAnonymousString1 == null) {
          i = 0;
        } else {
          i = paramAnonymousString1.intValue();
        }
        paramAnonymousString1 = (Integer)HwActivityPickerSearchDialog.this.sortWeight.get(paramAnonymousString2);
        if (paramAnonymousString1 != null) {
          j = paramAnonymousString1.intValue();
        }
        return j - i;
      }
    });
  }
  
  private static View inflateLayout(String paramString)
  {
    return View.inflate(TBSResources.getResourceContext(), loadIdentifierResource(paramString, "layout"), null);
  }
  
  private static int loadIdentifierResource(String paramString1, String paramString2)
  {
    return TBSResources.loadIdentifierResource(paramString1, paramString2);
  }
  
  public IPickerDialogHandler getPickerDialogHandler()
  {
    if (this.iPickerDialogHandler == null) {
      this.iPickerDialogHandler = new IPickerDialogHandler()
      {
        public void onDownloadFinished()
        {
          HwActivityPickerSearchDialog.this.contentView.post(new Runnable()
          {
            public void run()
            {
              HwActivityPickerSearchDialog.this.btn_confirm.setVisibility(0);
              HwActivityPickerSearchDialog.this.fl_progress.setVisibility(8);
            }
          });
        }
        
        public void onDownloadStarted()
        {
          HwActivityPickerSearchDialog.this.contentView.post(new Runnable()
          {
            public void run()
            {
              HwActivityPickerSearchDialog.this.btn_confirm.setVisibility(8);
              HwActivityPickerSearchDialog.this.fl_progress.setVisibility(0);
              HwActivityPickerSearchDialog.this.v_progress_bar.setTranslationX(0.0F);
              HwActivityPickerSearchDialog.this.tv_progress.setText("0%");
            }
          });
        }
        
        public void onProgress(final int paramAnonymousInt)
        {
          HwActivityPickerSearchDialog.this.contentView.post(new Runnable()
          {
            public void run()
            {
              int i = HwActivityPickerSearchDialog.this.v_progress_bar.getMeasuredWidth() * paramAnonymousInt / 100;
              Object localObject = ObjectAnimator.ofFloat(HwActivityPickerSearchDialog.this.v_progress_bar, "translationX", new float[] { i });
              ((ObjectAnimator)localObject).setDuration(100L);
              ((ObjectAnimator)localObject).start();
              localObject = HwActivityPickerSearchDialog.this.tv_progress;
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append(paramAnonymousInt);
              localStringBuilder.append("%");
              ((TextView)localObject).setText(localStringBuilder.toString());
            }
          });
        }
      };
    }
    return this.iPickerDialogHandler;
  }
  
  protected void onCreate(final Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.contentView = inflateLayout("x5_hw_search_picker_layout");
    this.fl_progress = ((FrameLayout)this.contentView.findViewById(loadIdentifierResource("fl_progress", "id")));
    this.tv_progress = ((TextView)this.contentView.findViewById(loadIdentifierResource("tv_progress", "id")));
    this.btn_confirm = ((Button)this.contentView.findViewById(loadIdentifierResource("btn_confirm", "id")));
    this.v_progress_bar = this.contentView.findViewById(loadIdentifierResource("v_progress_bar", "id"));
    this.btn_confirm.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = new StringBuilder();
        paramAnonymousView.append("checkedPkg:");
        paramAnonymousView.append(HwActivityPickerSearchDialog.this.checkedPkg);
        paramAnonymousView.toString();
        if (HwActivityPickerSearchDialog.this.iPickerDialogCallback != null)
        {
          paramAnonymousView = HwActivityPickerSearchDialog.this.iPickerDialogCallback;
          String str = HwActivityPickerSearchDialog.this.checkedPkg;
          boolean bool;
          if (HwActivityPickerSearchDialog.this.checkedPkg == null) {
            bool = true;
          } else {
            bool = false;
          }
          paramAnonymousView.onAppPicked(str, bool);
        }
      }
    });
    paramBundle = (ImageView)this.contentView.findViewById(loadIdentifierResource("qb_logo", "id"));
    final Object localObject = (ViewGroup)paramBundle.getParent().getParent();
    ((ViewGroup)localObject).post(new Runnable()
    {
      public void run()
      {
        ViewGroup localViewGroup = localObject;
        localViewGroup.setTouchDelegate(new TouchDelegate(new Rect(0, 0, localViewGroup.getMeasuredWidth(), localObject.getMeasuredHeight()), paramBundle));
      }
    });
    paramBundle.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        HwActivityPickerSearchDialog.access$002(HwActivityPickerSearchDialog.this, null);
        if (HwActivityPickerSearchDialog.this.adapter != null) {
          HwActivityPickerSearchDialog.this.adapter.notifyDataSetChanged();
        }
      }
    });
    setCanceledOnTouchOutside(true);
    setContentView(this.contentView);
    paramBundle = getWindow();
    if (paramBundle != null)
    {
      paramBundle.getDecorView().setPadding(0, 0, 0, 0);
      localObject = paramBundle.getAttributes();
      ((WindowManager.LayoutParams)localObject).width = -1;
      ((WindowManager.LayoutParams)localObject).height = -2;
      paramBundle.setAttributes((WindowManager.LayoutParams)localObject);
      paramBundle.setGravity(80);
    }
  }
  
  public void setPickerDialogCallback(IPickerDialogCallback paramIPickerDialogCallback)
  {
    this.iPickerDialogCallback = paramIPickerDialogCallback;
  }
  
  public static abstract interface IPickerDialogCallback
  {
    public abstract void onAppPicked(String paramString, boolean paramBoolean);
  }
  
  public static abstract interface IPickerDialogHandler
  {
    public abstract void onDownloadFinished();
    
    public abstract void onDownloadStarted();
    
    public abstract void onProgress(int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\dialog\HwActivityPickerSearchDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */