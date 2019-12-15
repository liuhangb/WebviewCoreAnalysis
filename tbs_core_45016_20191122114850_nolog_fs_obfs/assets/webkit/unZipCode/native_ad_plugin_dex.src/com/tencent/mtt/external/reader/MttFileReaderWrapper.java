package com.tencent.mtt.external.reader;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.MttLoader;
import com.tencent.common.utils.MttLoader.BrowserInfo;
import com.tencent.common.utils.StringUtils;
import com.tencent.common.utils.ThreadUtils;
import com.tencent.mtt.external.reader.base.IReaderEvent;
import com.tencent.mtt.external.reader.base.ReaderCheck;
import com.tencent.mtt.external.reader.base.ReaderConfig;
import com.tencent.mtt.external.reader.base.ReaderController;
import com.tencent.mtt.external.reader.base.ReaderLoadingView;
import com.tencent.mtt.external.reader.base.ReaderMenuView;
import com.tencent.mtt.external.reader.base.ReaderPipe;
import com.tencent.mtt.external.reader.base.ReaderViewCreator;
import com.tencent.mtt.external.reader.export.IReaderProxy;
import com.tencent.mtt.external.reader.internal.FileLoadFailedView;
import com.tencent.mtt.external.reader.internal.MttFileLoadingView;
import com.tencent.mtt.external.reader.internal.MttFilePreDownload;
import com.tencent.mtt.external.reader.internal.MttLocalCheck;
import com.tencent.mtt.external.reader.internal.MttMenuCheck;
import com.tencent.mtt.external.reader.internal.MttMenuManager;
import com.tencent.mtt.external.reader.internal.MttReaderCheck;
import com.tencent.mtt.external.reader.internal.MttViewCreator;
import com.tencent.mtt.external.reader.internal.ReaderFileStatistic;
import com.tencent.mtt.external.reader.internal.menuConfig.MenuManager;
import com.tencent.mtt.external.reader.internal.menuConfig.MenuResources;
import com.tencent.mtt.external.reader.utils.AppEngine;
import com.tencent.mtt.external.reader.utils.FileOpenUtils;
import com.tencent.tbs.common.resources.TBSResources;
import com.tencent.tbs.common.stat.TBSStatManager;
import java.io.File;

public class MttFileReaderWrapper
  implements IReaderFile
{
  public static boolean USELOCAL = false;
  static MttMenuCheck jdField_a_of_type_ComTencentMttExternalReaderInternalMttMenuCheck;
  private static String jdField_b_of_type_JavaLangString = "MttFileReaderWrapper";
  int jdField_a_of_type_Int = 0;
  FrameLayout jdField_a_of_type_AndroidWidgetFrameLayout = null;
  IReaderEvent jdField_a_of_type_ComTencentMttExternalReaderBaseIReaderEvent = null;
  ReaderCheck jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCheck = null;
  ReaderConfig jdField_a_of_type_ComTencentMttExternalReaderBaseReaderConfig = new ReaderConfig();
  ReaderController jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController = null;
  ReaderLoadingView jdField_a_of_type_ComTencentMttExternalReaderBaseReaderLoadingView = null;
  ReaderMenuView jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuView = null;
  ReaderPipe jdField_a_of_type_ComTencentMttExternalReaderBaseReaderPipe = null;
  IReaderProxy jdField_a_of_type_ComTencentMttExternalReaderExportIReaderProxy = null;
  FileLoadFailedView jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadFailedView = null;
  MttMenuManager jdField_a_of_type_ComTencentMttExternalReaderInternalMttMenuManager = null;
  private ReaderFileStatistic jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFileStatistic = new ReaderFileStatistic();
  MenuManager jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager = null;
  String jdField_a_of_type_JavaLangString = "REQ_FEATURE_ID";
  boolean jdField_a_of_type_Boolean = false;
  FrameLayout jdField_b_of_type_AndroidWidgetFrameLayout = null;
  ReaderController jdField_b_of_type_ComTencentMttExternalReaderBaseReaderController = null;
  protected Context mContext = null;
  
  public MttFileReaderWrapper(Context paramContext, Bundle paramBundle, IReaderProxy paramIReaderProxy)
  {
    Object localObject1 = this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderProxy;
    if (localObject1 != null) {
      ((IReaderProxy)localObject1).destroy();
    }
    this.mContext = paramContext;
    AppEngine.getInstance().setApplicationContext(paramContext.getApplicationContext());
    if (jdField_a_of_type_ComTencentMttExternalReaderInternalMttMenuCheck == null) {
      jdField_a_of_type_ComTencentMttExternalReaderInternalMttMenuCheck = new MttMenuCheck();
    }
    MenuResources.init(paramContext.getApplicationContext(), jdField_a_of_type_ComTencentMttExternalReaderInternalMttMenuCheck.getMenuResPath());
    String str = paramBundle.getString("filePath");
    Object localObject2 = paramBundle.getString("tempPath");
    localObject1 = ThreadUtils.getCurrentProcessName(paramContext.getApplicationContext());
    Object localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append(".TbsReaderTemp");
    ((StringBuilder)localObject3).append(FileUtils.fixIllegalPath((String)localObject1));
    localObject1 = ((StringBuilder)localObject3).toString();
    localObject2 = new File((String)localObject2, (String)localObject1);
    if (!((File)localObject2).exists()) {
      ((File)localObject2).mkdir();
    }
    AppEngine.getInstance().setTempPath(((File)localObject2).getAbsolutePath());
    localObject3 = jdField_b_of_type_JavaLangString;
    localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("MttFileReaderWrapper filePath=");
    ((StringBuilder)localObject3).append((String)localObject1);
    ((StringBuilder)localObject3).toString();
    this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderProxy = paramIReaderProxy;
    localObject1 = paramBundle.getString("fileExt");
    paramIReaderProxy = (IReaderProxy)localObject1;
    if (TextUtils.isEmpty((CharSequence)localObject1)) {
      paramIReaderProxy = FileUtils.getFileExt(str);
    }
    if (!a(paramIReaderProxy)) {
      return;
    }
    if (TextUtils.isEmpty(paramIReaderProxy)) {
      paramIReaderProxy = "";
    } else {
      paramIReaderProxy = paramIReaderProxy.toLowerCase();
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFileStatistic.setFileSizeFromPath(str);
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFileStatistic.fileExt = paramIReaderProxy;
    this.jdField_a_of_type_AndroidWidgetFrameLayout = new FrameLayout(this.mContext);
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderConfig.font_size = TBSResources.getDimensionPixelSize("reader_dp_18");
    localObject1 = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderConfig;
    ((ReaderConfig)localObject1).select_holder_resouce_id = "x5_text_select_holder";
    ((ReaderConfig)localObject1).select_bar_with = TBSResources.getDimensionPixelSize("reader_select_bar_width");
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderConfig.select_bar_height = TBSResources.getDimensionPixelSize("reader_select_bar_height");
    if (a())
    {
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderConfig.show_menu = paramBundle.getBoolean("menu_show", true);
      i = paramBundle.getInt("set_content_view_height", 0);
    }
    else
    {
      i = 0;
    }
    this.jdField_a_of_type_Int = i;
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderConfig.tempPath = ((File)localObject2).getAbsolutePath();
    paramBundle = MttLoader.getBrowserInfo(paramContext.getApplicationContext());
    if ((paramBundle != null) && (paramBundle.ver > 0)) {
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderConfig.installed_qb_version = paramBundle.ver;
    }
    if (FileOpenUtils.isBasicReaderType(paramIReaderProxy)) {
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderConfig.txt_could_be_noval = isNovelMode(str, paramIReaderProxy);
    }
    paramBundle = new MttViewCreator();
    ReaderViewCreator.setViewCreator(paramBundle);
    paramBundle.setWebViewProxy(this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderProxy.getWebViewProxy());
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderLoadingView = new MttFileLoadingView(paramContext, false);
    paramBundle = new ReaderCallbackAction(this.mContext, this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderProxy);
    if (USELOCAL) {
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCheck = new MttLocalCheck(paramIReaderProxy, this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderProxy.getClassLoader());
    } else {
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCheck = new MttReaderCheck(this.mContext, paramIReaderProxy, this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderProxy.getClassLoader(), paramBundle, this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFileStatistic);
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCheck.setView(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderLoadingView);
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController = new ReaderController(paramContext, this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderCheck);
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderPipe = new ReaderPipe();
    this.jdField_b_of_type_AndroidWidgetFrameLayout = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.createReaderView(str, paramIReaderProxy, 0, i);
    this.jdField_a_of_type_AndroidWidgetFrameLayout.addView(this.jdField_b_of_type_AndroidWidgetFrameLayout, -1, -1);
    if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttMenuManager == null)
    {
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttMenuManager = new MttMenuManager(this.mContext, this.jdField_a_of_type_AndroidWidgetFrameLayout);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.attachMenu(this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttMenuManager);
    }
    if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager == null)
    {
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager = new MenuManager(this.mContext, this.jdField_a_of_type_AndroidWidgetFrameLayout, paramIReaderProxy, jdField_a_of_type_ComTencentMttExternalReaderInternalMttMenuCheck.getMenuPath());
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.attachMenu(this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager);
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.attachCallbackAction(paramBundle);
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.attachPipe(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderPipe);
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.attachStatistic(this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFileStatistic);
    int i = TBSResources.getColor("reader_font_color");
    int j = TBSResources.getColor("reader_bg_color");
    int k = TBSResources.getColor("reader_select_color");
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.setColor(i, j, k, false);
    MttFilePreDownload.getInstance().pause();
  }
  
  private boolean a(String paramString)
  {
    return (FileOpenUtils.isReaderFileExt(paramString)) || (FileOpenUtils.isBasicReaderType(paramString));
  }
  
  public static boolean isNovelMode(String paramString1, String paramString2)
  {
    boolean bool1 = paramString2.toLowerCase().equals("txt");
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    long l2 = 0L;
    paramString1 = new File(paramString1);
    long l1 = l2;
    if (paramString1.exists())
    {
      l1 = l2;
      if (paramString1.isFile()) {
        l1 = paramString1.length();
      }
    }
    bool1 = bool2;
    if (StringUtils.haveChineseChar(paramString1.getName()))
    {
      bool1 = bool2;
      if (l1 > 102400L) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public static boolean isSupportExt(String paramString)
  {
    return ReaderViewCreator.isSupportExt(paramString);
  }
  
  void a()
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderLoadingView.getFrameLayout() != null) {
      this.jdField_a_of_type_AndroidWidgetFrameLayout.removeView(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderLoadingView.getFrameLayout());
    }
  }
  
  void a(String paramString)
  {
    FrameLayout localFrameLayout1 = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderLoadingView.getFrameLayout();
    if (localFrameLayout1 != null)
    {
      ViewParent localViewParent = localFrameLayout1.getParent();
      FrameLayout localFrameLayout2 = this.jdField_a_of_type_AndroidWidgetFrameLayout;
      if (localViewParent != localFrameLayout2) {
        localFrameLayout2.addView(localFrameLayout1, new FrameLayout.LayoutParams(-1, -1));
      }
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderLoadingView.setText(TBSResources.getString(paramString));
  }
  
  boolean a()
  {
    return AppEngine.getInstance().isSetPkg() ^ true;
  }
  
  public boolean askCanGoback()
  {
    return this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.askCanGoback();
  }
  
  void b()
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseIReaderEvent = new IReaderEvent()
    {
      public boolean onUiEvent(int paramAnonymousInt, Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        if (2 == paramAnonymousInt)
        {
          MttFileReaderWrapper.this.a();
          if (paramAnonymousObject1 != null)
          {
            if (MttFileReaderWrapper.this.b == null)
            {
              LogUtils.d(MttFileReaderWrapper.a(), "onUiEvent:switch");
              paramAnonymousObject1 = (Bundle)paramAnonymousObject1;
              paramAnonymousObject2 = ((Bundle)paramAnonymousObject1).getString("ext");
              String str = ((Bundle)paramAnonymousObject1).getString("path");
              ReaderCallbackAction localReaderCallbackAction = new ReaderCallbackAction(MttFileReaderWrapper.this.mContext, MttFileReaderWrapper.this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderProxy);
              if (MttFileReaderWrapper.USELOCAL) {
                paramAnonymousObject1 = new MttLocalCheck((String)paramAnonymousObject2, MttFileReaderWrapper.this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderProxy.getClassLoader());
              } else {
                paramAnonymousObject1 = new MttReaderCheck(MttFileReaderWrapper.this.mContext, (String)paramAnonymousObject2, MttFileReaderWrapper.this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderProxy.getClassLoader(), localReaderCallbackAction, MttFileReaderWrapper.a(MttFileReaderWrapper.this));
              }
              ReaderConfig localReaderConfig = MttFileReaderWrapper.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderConfig.clone();
              localReaderConfig.scroll_mode = false;
              localReaderConfig.core_Using_One = false;
              localReaderConfig.pdf_contain_outline = false;
              localReaderConfig.txt_could_be_noval = false;
              MttFileReaderWrapper localMttFileReaderWrapper = MttFileReaderWrapper.this;
              localMttFileReaderWrapper.b = new ReaderController(localMttFileReaderWrapper.mContext, (ReaderCheck)paramAnonymousObject1);
              paramAnonymousObject1 = MttFileReaderWrapper.this.b.createReaderView(str, (String)paramAnonymousObject2, 0, MttFileReaderWrapper.this.jdField_a_of_type_Int);
              MttFileReaderWrapper.this.b.attachMenu(MttFileReaderWrapper.this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttMenuManager);
              MttFileReaderWrapper.this.b.attachMenu(MttFileReaderWrapper.this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager);
              MttFileReaderWrapper.this.b.attachPipe(MttFileReaderWrapper.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderPipe);
              MttFileReaderWrapper.this.b.attachStatistic(MttFileReaderWrapper.a(MttFileReaderWrapper.this));
              MttFileReaderWrapper.this.b.attachCallbackAction(localReaderCallbackAction);
              paramAnonymousInt = TBSResources.getColor("reader_font_color");
              int i = TBSResources.getColor("reader_bg_color");
              int j = TBSResources.getColor("reader_select_color");
              MttFileReaderWrapper.this.b.setColor(paramAnonymousInt, i, j, false);
              MttFileReaderWrapper.this.jdField_a_of_type_AndroidWidgetFrameLayout.addView((View)paramAnonymousObject1, new FrameLayout.LayoutParams(-1, -1));
              MttFileReaderWrapper.this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttMenuManager.focus();
              MttFileReaderWrapper.this.b.openReader(localReaderConfig);
              MttFileReaderWrapper.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.deactive();
              return true;
            }
            MttFileReaderWrapper.this.b.active();
            MttFileReaderWrapper.this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttMenuManager.setMenuVisiable(false, false);
            MttFileReaderWrapper.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.deactive();
            return true;
          }
          LogUtils.d(MttFileReaderWrapper.a(), "onUiEvent:switch:1");
          MttFileReaderWrapper.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.active();
          MttFileReaderWrapper.this.b.deactive();
          return true;
        }
        if (3 == paramAnonymousInt)
        {
          paramAnonymousInt = ((Integer)paramAnonymousObject1).intValue();
          MttFileReaderWrapper.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderLoadingView.setDetailProgress(paramAnonymousInt);
          return true;
        }
        if (5 == paramAnonymousInt)
        {
          MttFileReaderWrapper.this.f();
          return true;
        }
        if (4 == paramAnonymousInt)
        {
          MttFileReaderWrapper.this.a();
          return true;
        }
        if (6 == paramAnonymousInt)
        {
          MttFileReaderWrapper.this.a((String)paramAnonymousObject1);
          return true;
        }
        if (7 == paramAnonymousInt)
        {
          MttFileReaderWrapper.this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.doCallBackEvent(5025, paramAnonymousObject1, null);
          return true;
        }
        if (1 == paramAnonymousInt)
        {
          paramAnonymousInt = ((Integer)paramAnonymousObject1).intValue();
          if (paramAnonymousInt == 202)
          {
            MttFileReaderWrapper.this.b(TBSResources.getString("reader_pdf_tips_relayout_failed"));
            return true;
          }
          if (paramAnonymousInt == 207)
          {
            MttFileReaderWrapper.this.b(TBSResources.getString("reader_pdf_deselect_fit_screen"));
            return true;
          }
          MttFileReaderWrapper.this.c();
          return true;
        }
        if (8 == paramAnonymousInt) {
          return true;
        }
        if (9 == paramAnonymousInt) {
          return true;
        }
        if (10 == paramAnonymousInt) {
          return true;
        }
        if (11 == paramAnonymousInt)
        {
          MttFileReaderWrapper.jdField_a_of_type_ComTencentMttExternalReaderInternalMttMenuCheck.check();
          MttFilePreDownload.getInstance().start();
          return true;
        }
        if (12 == paramAnonymousInt) {
          MttFileReaderWrapper.this.jdField_a_of_type_Boolean = true;
        }
        return true;
      }
    };
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderPipe.setReceiveEvent(this.jdField_a_of_type_ComTencentMttExternalReaderBaseIReaderEvent);
  }
  
  void b(String paramString)
  {
    new AlertDialog.Builder(this.mContext).setMessage(paramString).setPositiveButton(TBSResources.getString("reader_ok"), null).show();
  }
  
  void c()
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadFailedView == null) {
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadFailedView = new FileLoadFailedView(this.mContext, this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderLoadingView.getFrameLayout(), null, FileLoadFailedView.FailedTypeFile, null);
    }
  }
  
  void d()
  {
    FileLoadFailedView localFileLoadFailedView = this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadFailedView;
    if (localFileLoadFailedView != null)
    {
      localFileLoadFailedView.clearAndRemoveFromParent();
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadFailedView = null;
    }
  }
  
  public void doCommand(int paramInt, Object paramObject1, Object paramObject2)
  {
    String str = jdField_b_of_type_JavaLangString;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("doCommand actionType:");
    localStringBuilder.append(paramInt);
    localStringBuilder.append(" args");
    localStringBuilder.append(paramObject1);
    localStringBuilder.append(" result");
    localStringBuilder.append(paramObject2);
    LogUtils.d(str, localStringBuilder.toString());
    if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttMenuManager.postEvent(paramInt, paramObject1, paramObject2) == -1) {
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.doCommand(paramInt, paramObject1, paramObject2);
    }
  }
  
  void e()
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderLoadingView.getFrameLayout() != null) {
      this.jdField_a_of_type_AndroidWidgetFrameLayout.addView(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderLoadingView.getFrameLayout(), new FrameLayout.LayoutParams(-1, -1));
    }
  }
  
  void f()
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderLoadingView.getFrameLayout() != null) {
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderLoadingView.getFrameLayout().bringToFront();
    }
  }
  
  public int getReaderType()
  {
    return 0;
  }
  
  public View getRootView()
  {
    return this.jdField_a_of_type_AndroidWidgetFrameLayout;
  }
  
  public boolean handleEvent(int paramInt)
  {
    return false;
  }
  
  public boolean isDocumentContentEnded()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public boolean isDocumentTop()
  {
    return this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.isDocumentTop();
  }
  
  public int load()
  {
    String str = jdField_b_of_type_JavaLangString;
    e();
    b();
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderPipe.setReceiveEvent(this.jdField_a_of_type_ComTencentMttExternalReaderBaseIReaderEvent);
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.openReader(this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderConfig);
    return 0;
  }
  
  public void notifySkinChanged()
  {
    int i = TBSResources.getColor("reader_font_color");
    int j = TBSResources.getColor("reader_bg_color");
    int k = TBSResources.getColor("reader_select_color");
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.setColor(i, j, k, false);
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderLoadingView.switchSkin();
    Object localObject = this.jdField_a_of_type_ComTencentMttExternalReaderInternalFileLoadFailedView;
    if (localObject != null) {
      ((FileLoadFailedView)localObject).switchSkin();
    }
    localObject = this.jdField_b_of_type_ComTencentMttExternalReaderBaseReaderController;
    if (localObject != null) {
      ((ReaderController)localObject).setColor(i, j, k, false);
    }
  }
  
  public void onSizeChanged(int paramInt1, int paramInt2)
  {
    Object localObject = jdField_b_of_type_JavaLangString;
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.onSizeChanged(paramInt1, paramInt2);
    localObject = this.jdField_b_of_type_ComTencentMttExternalReaderBaseReaderController;
    if (localObject != null) {
      ((ReaderController)localObject).onSizeChanged(paramInt1, paramInt2);
    }
  }
  
  public void save() {}
  
  public void setAdView(View paramView)
  {
    ReaderController localReaderController = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController;
    if (localReaderController != null) {
      localReaderController.setAdView(paramView);
    }
  }
  
  public void setTitleBarHeight(int paramInt)
  {
    ReaderController localReaderController = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController;
    if (localReaderController != null) {
      localReaderController.setTitleBarHeight(paramInt);
    }
  }
  
  public void unload()
  {
    Object localObject = jdField_b_of_type_JavaLangString;
    d();
    localObject = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderLoadingView;
    if (localObject != null) {
      ((ReaderLoadingView)localObject).destroy();
    }
    this.jdField_a_of_type_AndroidWidgetFrameLayout = null;
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.closeReader();
    localObject = this.jdField_b_of_type_ComTencentMttExternalReaderBaseReaderController;
    if (localObject != null) {
      ((ReaderController)localObject).closeReader();
    }
    localObject = this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderProxy;
    if (localObject != null)
    {
      ((IReaderProxy)localObject).destroy();
      this.jdField_a_of_type_ComTencentMttExternalReaderExportIReaderProxy = null;
    }
    localObject = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttMenuManager;
    if (localObject != null)
    {
      ((MttMenuManager)localObject).destory();
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttMenuManager = null;
    }
    localObject = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager;
    if (localObject != null)
    {
      ((MenuManager)localObject).destory();
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager = null;
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalReaderFileStatistic.addToStatManager(true);
    ReaderViewCreator.destroy();
    AppEngine.getInstance().setApplicationContext(null);
    LogUtils.d(jdField_b_of_type_JavaLangString, "unload:1");
    TBSStatManager.getInstance().save();
    LogUtils.d(jdField_b_of_type_JavaLangString, "unload:2");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\MttFileReaderWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */