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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class HwActivityPickerDialog
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
  private LinearLayout ll_other_app;
  private Map<String, Drawable> mDrawables;
  private boolean mGridViewDisable;
  private boolean mIsDownload;
  private boolean mOneLineLayout;
  private ArrayList<String> pkgList;
  private View selected_qb;
  Map<String, Integer> sortWeight = new HashMap();
  private TextView tv_progress;
  private TextView tv_state;
  private View v_progress_bar;
  
  public HwActivityPickerDialog(Context paramContext, Map<String, Drawable> paramMap, boolean paramBoolean)
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
    this.mIsDownload = false;
    this.mOneLineLayout = true;
    this.mGridViewDisable = false;
    this.mIsDownload = paramBoolean;
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
        paramAnonymousString1 = (Integer)HwActivityPickerDialog.this.sortWeight.get(paramAnonymousString1);
        int j = 0;
        int i;
        if (paramAnonymousString1 == null) {
          i = 0;
        } else {
          i = paramAnonymousString1.intValue();
        }
        paramAnonymousString1 = (Integer)HwActivityPickerDialog.this.sortWeight.get(paramAnonymousString2);
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
  
  private View initMultiLineGridView()
  {
    GridView localGridView = (GridView)this.contentView.findViewById(loadIdentifierResource("multi_line", "id"));
    localGridView.setVisibility(0);
    this.adapter = new BaseAdapter()
    {
      public int getCount()
      {
        return HwActivityPickerDialog.this.mDrawables.size();
      }
      
      public Object getItem(int paramAnonymousInt)
      {
        return null;
      }
      
      public long getItemId(int paramAnonymousInt)
      {
        return paramAnonymousInt;
      }
      
      public View getView(final int paramAnonymousInt, View paramAnonymousView, ViewGroup paramAnonymousViewGroup)
      {
        paramAnonymousView = (String)HwActivityPickerDialog.this.pkgList.get(paramAnonymousInt);
        paramAnonymousViewGroup = HwActivityPickerDialog.inflateLayout("x5_hw_picker_app_item");
        ImageView localImageView = (ImageView)paramAnonymousViewGroup.findViewById(HwActivityPickerDialog.loadIdentifierResource("app_logo", "id"));
        View localView = paramAnonymousViewGroup.findViewById(HwActivityPickerDialog.loadIdentifierResource("checked_mark", "id"));
        Drawable localDrawable = (Drawable)HwActivityPickerDialog.this.mDrawables.get(paramAnonymousView);
        if (localDrawable != null) {
          localImageView.setImageDrawable(localDrawable);
        }
        if ((paramAnonymousView != null) && (paramAnonymousView.equals(HwActivityPickerDialog.this.checkedPkg))) {
          localView.setVisibility(0);
        } else {
          localView.setVisibility(8);
        }
        paramAnonymousViewGroup.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymous2View)
          {
            if (!HwActivityPickerDialog.this.mGridViewDisable)
            {
              HwActivityPickerDialog.access$002(HwActivityPickerDialog.this, (String)HwActivityPickerDialog.this.pkgList.get(paramAnonymousInt));
              HwActivityPickerDialog.this.selected_qb.setVisibility(8);
              HwActivityPickerDialog.6.this.notifyDataSetChanged();
            }
          }
        });
        if (HwActivityPickerDialog.this.mGridViewDisable) {
          paramAnonymousViewGroup.setAlpha(0.2F);
        }
        return paramAnonymousViewGroup;
      }
    };
    localGridView.setAdapter(this.adapter);
    this.mOneLineLayout = false;
    return localGridView;
  }
  
  private View initOneLineGridView()
  {
    final int i = Math.min(4, this.mDrawables.size());
    final GridView localGridView = (GridView)this.contentView.findViewById(loadIdentifierResource("one_line", "id"));
    localGridView.setNumColumns(i);
    this.adapter = new BaseAdapter()
    {
      public int getCount()
      {
        return i;
      }
      
      public Object getItem(int paramAnonymousInt)
      {
        return null;
      }
      
      public long getItemId(int paramAnonymousInt)
      {
        return paramAnonymousInt;
      }
      
      public View getView(final int paramAnonymousInt, View paramAnonymousView, ViewGroup paramAnonymousViewGroup)
      {
        paramAnonymousView = (String)HwActivityPickerDialog.this.pkgList.get(paramAnonymousInt);
        paramAnonymousViewGroup = HwActivityPickerDialog.inflateLayout("x5_hw_picker_app_item");
        ImageView localImageView = (ImageView)paramAnonymousViewGroup.findViewById(HwActivityPickerDialog.loadIdentifierResource("app_logo", "id"));
        View localView = paramAnonymousViewGroup.findViewById(HwActivityPickerDialog.loadIdentifierResource("checked_mark", "id"));
        if ((HwActivityPickerDialog.this.mDrawables.size() > 4) && (paramAnonymousInt == 3))
        {
          localImageView.setImageResource(HwActivityPickerDialog.loadIdentifierResource("x5_hw_picker_more", "drawable"));
          localView.setVisibility(8);
          paramAnonymousViewGroup.setOnClickListener(new View.OnClickListener()
          {
            public void onClick(View paramAnonymous2View)
            {
              if (!HwActivityPickerDialog.this.mGridViewDisable)
              {
                HwActivityPickerDialog.this.initMultiLineGridView();
                ((ViewGroup)HwActivityPickerDialog.5.this.val$one_line.getParent()).removeView(HwActivityPickerDialog.5.this.val$one_line);
              }
            }
          });
          return paramAnonymousViewGroup;
        }
        Drawable localDrawable = (Drawable)HwActivityPickerDialog.this.mDrawables.get(paramAnonymousView);
        if (localDrawable != null) {
          localImageView.setImageDrawable(localDrawable);
        }
        if ((paramAnonymousView != null) && (paramAnonymousView.equals(HwActivityPickerDialog.this.checkedPkg))) {
          localView.setVisibility(0);
        } else {
          localView.setVisibility(8);
        }
        paramAnonymousViewGroup.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymous2View)
          {
            if (!HwActivityPickerDialog.this.mGridViewDisable)
            {
              HwActivityPickerDialog.access$002(HwActivityPickerDialog.this, (String)HwActivityPickerDialog.this.pkgList.get(paramAnonymousInt));
              HwActivityPickerDialog.this.selected_qb.setVisibility(8);
              HwActivityPickerDialog.5.this.notifyDataSetChanged();
            }
          }
        });
        return paramAnonymousViewGroup;
      }
    };
    localGridView.setAdapter(this.adapter);
    this.mOneLineLayout = true;
    return localGridView;
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
          HwActivityPickerDialog.this.contentView.post(new Runnable()
          {
            public void run()
            {
              HwActivityPickerDialog.this.tv_state.setText("已下载");
              HwActivityPickerDialog.this.btn_confirm.setVisibility(0);
              HwActivityPickerDialog.this.fl_progress.setVisibility(8);
            }
          });
        }
        
        public void onDownloadStarted()
        {
          HwActivityPickerDialog.this.contentView.post(new Runnable()
          {
            public void run()
            {
              HwActivityPickerDialog.this.tv_state.setText("下载中");
              HwActivityPickerDialog.this.btn_confirm.setVisibility(8);
              HwActivityPickerDialog.this.fl_progress.setVisibility(0);
              HwActivityPickerDialog.this.v_progress_bar.setTranslationX(0.0F);
              HwActivityPickerDialog.this.tv_progress.setText("0%");
            }
          });
        }
        
        public void onProgress(final int paramAnonymousInt)
        {
          HwActivityPickerDialog.this.contentView.post(new Runnable()
          {
            public void run()
            {
              int i = HwActivityPickerDialog.this.v_progress_bar.getMeasuredWidth() * paramAnonymousInt / 100;
              Object localObject = ObjectAnimator.ofFloat(HwActivityPickerDialog.this.v_progress_bar, "translationX", new float[] { i });
              ((ObjectAnimator)localObject).setDuration(100L);
              ((ObjectAnimator)localObject).start();
              localObject = HwActivityPickerDialog.this.tv_progress;
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
    this.contentView = inflateLayout("x5_hw_picker_layout");
    this.selected_qb = this.contentView.findViewById(loadIdentifierResource("selected_qb", "id"));
    this.tv_state = ((TextView)this.contentView.findViewById(loadIdentifierResource("tv_state", "id")));
    this.fl_progress = ((FrameLayout)this.contentView.findViewById(loadIdentifierResource("fl_progress", "id")));
    this.tv_progress = ((TextView)this.contentView.findViewById(loadIdentifierResource("tv_progress", "id")));
    this.btn_confirm = ((Button)this.contentView.findViewById(loadIdentifierResource("btn_confirm", "id")));
    this.v_progress_bar = this.contentView.findViewById(loadIdentifierResource("v_progress_bar", "id"));
    this.ll_other_app = ((LinearLayout)this.contentView.findViewById(loadIdentifierResource("ll_other_app", "id")));
    paramBundle = (TextView)this.contentView.findViewById(loadIdentifierResource("tv_tips1", "id"));
    final Object localObject = (TextView)this.contentView.findViewById(loadIdentifierResource("tv_tips2", "id"));
    TextView localTextView = (TextView)this.contentView.findViewById(loadIdentifierResource("tv_tips3", "id"));
    if (this.mIsDownload)
    {
      paramBundle.setText("推荐下载方式");
      ((TextView)localObject).setText("其他下载方式");
      localTextView.setText("以上其他下载方式内的APP向系统声明可接管下载请求\n但可能下载失败且有安全风险");
    }
    else
    {
      paramBundle.setText("推荐打开方式");
      ((TextView)localObject).setText("其他打开方式");
      localTextView.setText("以上APP向系统声明可接管打开请求\n但可能打开失败且有安全风险");
    }
    paramBundle = this.mDrawables;
    if ((paramBundle == null) || (paramBundle.size() == 0)) {
      this.ll_other_app.setVisibility(8);
    }
    this.btn_confirm.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = new StringBuilder();
        paramAnonymousView.append("checkedPkg:");
        paramAnonymousView.append(HwActivityPickerDialog.this.checkedPkg);
        paramAnonymousView.toString();
        if (HwActivityPickerDialog.this.iPickerDialogCallback != null)
        {
          paramAnonymousView = HwActivityPickerDialog.this;
          boolean bool = true;
          HwActivityPickerDialog.access$202(paramAnonymousView, true);
          if (HwActivityPickerDialog.this.checkedPkg == null)
          {
            if (HwActivityPickerDialog.this.mOneLineLayout) {
              paramAnonymousView = (GridView)HwActivityPickerDialog.this.contentView.findViewById(HwActivityPickerDialog.loadIdentifierResource("one_line", "id"));
            } else {
              paramAnonymousView = (GridView)HwActivityPickerDialog.this.contentView.findViewById(HwActivityPickerDialog.loadIdentifierResource("multi_line", "id"));
            }
            int j = paramAnonymousView.getCount();
            int i = 0;
            while (i < j)
            {
              if (paramAnonymousView.getChildAt(i) != null) {
                paramAnonymousView.getChildAt(i).setAlpha(0.2F);
              }
              i += 1;
            }
          }
          paramAnonymousView = HwActivityPickerDialog.this.iPickerDialogCallback;
          String str = HwActivityPickerDialog.this.checkedPkg;
          if (HwActivityPickerDialog.this.checkedPkg != null) {
            bool = false;
          }
          paramAnonymousView.onAppPicked(str, bool);
        }
      }
    });
    paramBundle = (ImageView)this.contentView.findViewById(loadIdentifierResource("qb_logo", "id"));
    localObject = (ViewGroup)paramBundle.getParent().getParent();
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
        HwActivityPickerDialog.access$002(HwActivityPickerDialog.this, null);
        if (HwActivityPickerDialog.this.adapter != null) {
          HwActivityPickerDialog.this.adapter.notifyDataSetChanged();
        }
        HwActivityPickerDialog.this.selected_qb.setVisibility(0);
      }
    });
    initOneLineGridView();
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\dialog\HwActivityPickerDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */