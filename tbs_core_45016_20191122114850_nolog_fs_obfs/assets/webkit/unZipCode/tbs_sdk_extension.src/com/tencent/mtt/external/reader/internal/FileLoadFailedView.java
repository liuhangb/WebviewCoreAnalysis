package com.tencent.mtt.external.reader.internal;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.tbs.common.resources.TBSResources;

public class FileLoadFailedView
{
  public static int FailedTypeFile = 3;
  public static int FailedTypeFileNotSupport = 5;
  public static int FailedTypeOnlineFile = 4;
  public static int FailedTypeSo = 1;
  public static int FailedTypeSoNeedDownload = 2;
  public static final String KEY_FILE_TYPE = "KEY_FILE_TYPE";
  public static final String KEY_SO_SIZE = "KEY_SO_SIZE";
  public static int RELOAD_BUTTON_ID = 1;
  int jdField_a_of_type_Int = 0;
  private Context jdField_a_of_type_AndroidContentContext = null;
  private FrameLayout jdField_a_of_type_AndroidWidgetFrameLayout = null;
  LinearLayout jdField_a_of_type_AndroidWidgetLinearLayout = null;
  FileReLoadCallBack jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadFailedView$FileReLoadCallBack = null;
  String jdField_a_of_type_JavaLangString = "";
  private String b = "";
  
  public FileLoadFailedView(Context paramContext, FrameLayout paramFrameLayout, FileReLoadCallBack paramFileReLoadCallBack, int paramInt, Bundle paramBundle)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_AndroidWidgetFrameLayout = paramFrameLayout;
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadFailedView$FileReLoadCallBack = paramFileReLoadCallBack;
    this.jdField_a_of_type_Int = paramInt;
    if ((paramBundle != null) && (paramBundle.containsKey("KEY_SO_SIZE"))) {
      this.b = paramBundle.getString("KEY_SO_SIZE");
    }
    if ((paramBundle != null) && (paramBundle.containsKey("KEY_FILE_TYPE"))) {
      this.jdField_a_of_type_JavaLangString = paramBundle.getString("KEY_FILE_TYPE");
    }
    this.jdField_a_of_type_AndroidWidgetLinearLayout = new LinearLayout(paramContext);
    this.jdField_a_of_type_AndroidWidgetLinearLayout.setBackgroundColor(TBSResources.getColor("reader_theme_func_content_bkg_normal"));
    this.jdField_a_of_type_AndroidWidgetLinearLayout.setOrientation(1);
    paramFrameLayout = new LinearLayout(paramContext);
    paramBundle = new TextView(paramContext);
    paramBundle.setGravity(17);
    paramFileReLoadCallBack = new LinearLayout.LayoutParams(-1, -1, 1.0F);
    paramFrameLayout.setOrientation(1);
    paramFrameLayout.addView(paramBundle, paramFileReLoadCallBack);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
    if (this.jdField_a_of_type_Int != FailedTypeSoNeedDownload)
    {
      ImageView localImageView = new ImageView(paramContext);
      localImageView.setImageDrawable(TBSResources.getDrawable("reader_filesystem_watermark_default"));
      this.jdField_a_of_type_AndroidWidgetLinearLayout.addView(localImageView, localLayoutParams);
    }
    localLayoutParams.topMargin = TBSResources.getDimensionPixelSize("reader_dp_15");
    this.jdField_a_of_type_AndroidWidgetLinearLayout.addView(paramFrameLayout, localLayoutParams);
    paramInt = this.jdField_a_of_type_Int;
    if ((paramInt != FailedTypeSo) && (paramInt != FailedTypeOnlineFile))
    {
      if ((paramInt != FailedTypeFile) && (paramInt != FailedTypeFileNotSupport))
      {
        if (paramInt == FailedTypeSoNeedDownload)
        {
          paramBundle.setTextSize(0, TBSResources.getDimensionPixelSize("reader_textsize_15"));
          paramBundle.setTextColor(TBSResources.getColor("reader_theme_common_color_a4"));
          paramBundle.setText(TBSResources.getString("reader_file_plugin_load", new Object[] { this.jdField_a_of_type_JavaLangString, this.b }));
          paramContext = new TextView(paramContext);
          paramContext.setGravity(17);
          paramFileReLoadCallBack.topMargin = TBSResources.getDimensionPixelSize("reader_uifw_control_loading_default");
          paramFrameLayout.addView(paramContext, paramFileReLoadCallBack);
          paramContext.setTextSize(0, TBSResources.getDimensionPixelSize("reader_textsize_15"));
          paramContext.setText(TBSResources.getString("reader_load"));
          paramContext.setTextColor(TBSResources.getColor("reader_theme_common_color_b1"));
          paramContext.setOnClickListener(new View.OnClickListener()
          {
            public void onClick(View paramAnonymousView)
            {
              if (FileLoadFailedView.this.a != null) {
                FileLoadFailedView.this.a.fileSoReloadCallback();
              }
            }
          });
        }
      }
      else
      {
        paramBundle.setTextColor(TBSResources.getColor("reader_theme_common_color_a1"));
        paramBundle.setTextSize(0, TBSResources.getDimensionPixelSize("reader_textsize_15"));
        paramInt = this.jdField_a_of_type_Int;
        if (paramInt == FailedTypeFile) {
          paramBundle.setText(TBSResources.getString("reader_file_open_failed"));
        } else if (paramInt == FailedTypeFileNotSupport) {
          paramBundle.setText(TBSResources.getString("reader_unsupport_file_type"));
        }
      }
    }
    else
    {
      paramBundle.setTextColor(TBSResources.getColor("reader_theme_common_color_a1"));
      paramBundle.setTextSize(0, TBSResources.getDimensionPixelSize("reader_textsize_15"));
      paramInt = this.jdField_a_of_type_Int;
      if (paramInt == FailedTypeSo) {
        paramBundle.setText(TBSResources.getString("reader_so_load_failed"));
      } else if (paramInt == FailedTypeOnlineFile) {
        paramBundle.setText(TBSResources.getString("reader_file_load_failed"));
      }
      paramContext = new TextView(paramContext);
      paramContext.setGravity(17);
      paramFrameLayout.addView(paramContext, paramFileReLoadCallBack);
      paramContext.setId(RELOAD_BUTTON_ID);
      paramContext.setTextSize(0, TBSResources.getDimensionPixelSize("reader_textsize_15"));
      paramContext.setText(TBSResources.getString("reader_click_retry"));
      paramContext.setTextColor(TBSResources.getColor("reader_theme_common_color_b1"));
      paramContext.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if ((paramAnonymousView.getId() == FileLoadFailedView.RELOAD_BUTTON_ID) && (FileLoadFailedView.this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadFailedView$FileReLoadCallBack != null))
          {
            if (FileLoadFailedView.this.jdField_a_of_type_Int == FileLoadFailedView.FailedTypeSo)
            {
              FileLoadFailedView.this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadFailedView$FileReLoadCallBack.fileSoReloadCallback();
              return;
            }
            if (FileLoadFailedView.this.jdField_a_of_type_Int == FileLoadFailedView.FailedTypeOnlineFile) {
              FileLoadFailedView.this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadFailedView$FileReLoadCallBack.fileReLoadCallback();
            }
          }
        }
      });
    }
    this.jdField_a_of_type_AndroidWidgetLinearLayout.setGravity(17);
    this.jdField_a_of_type_AndroidWidgetFrameLayout.addView(this.jdField_a_of_type_AndroidWidgetLinearLayout, new FrameLayout.LayoutParams(-1, -1));
    this.jdField_a_of_type_AndroidWidgetFrameLayout.setBackgroundColor(-7829368);
  }
  
  public void clearAndRemoveFromParent()
  {
    LinearLayout localLinearLayout = this.jdField_a_of_type_AndroidWidgetLinearLayout;
    if (localLinearLayout != null)
    {
      localLinearLayout.removeAllViews();
      if (this.jdField_a_of_type_AndroidWidgetLinearLayout.getParent() != null) {
        this.jdField_a_of_type_AndroidWidgetFrameLayout.removeView(this.jdField_a_of_type_AndroidWidgetLinearLayout);
      }
      this.jdField_a_of_type_AndroidWidgetLinearLayout = null;
      this.jdField_a_of_type_AndroidWidgetFrameLayout = null;
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadFailedView$FileReLoadCallBack = null;
    this.jdField_a_of_type_AndroidContentContext = null;
  }
  
  public void switchSkin()
  {
    this.jdField_a_of_type_AndroidWidgetLinearLayout.setBackgroundColor(TBSResources.getColor("reader_theme_func_content_bkg_normal"));
  }
  
  public static abstract interface FileReLoadCallBack
  {
    public abstract void fileReLoadCallback();
    
    public abstract void fileSoReloadCallback();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\FileLoadFailedView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */