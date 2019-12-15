package com.tencent.tbs.core.partner.camera;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Area;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webview.chromium.tencent.TencentWebViewContentsClientAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.ValueCallback;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.chromium.tencent.SharedResource;

public class X5CameraDialog
  extends Dialog
  implements SurfaceHolder.Callback
{
  private ImageButton backBtn;
  private int cameraPosition;
  private ImageButton captureBtn;
  private Activity mActivity;
  private Camera mCamera;
  private boolean mCameraWorking;
  private Context mContext;
  private boolean mFrontCamera;
  private int mFrontCameraIndex;
  private SurfaceHolder mHolder;
  private String mImagesPath;
  private View mPreviewView = null;
  private int mRotateDegree;
  private boolean mScreenLandScape;
  private TencentWebViewContentsClientAdapter mTencentWebViewContentsClientAdapter;
  private int mThemeResId;
  private X5CameraPreview mX5CameraPreviewDialog;
  private ValueCallback<Uri[]> mcallbackAdapter;
  private ImageView previewCameraIv;
  private SurfaceView surfaceSv;
  private ImageButton switchCameraBtn;
  
  public X5CameraDialog(Context paramContext, int paramInt1, String paramString, TencentWebViewContentsClientAdapter paramTencentWebViewContentsClientAdapter, ValueCallback<Uri[]> paramValueCallback, X5CameraPreview paramX5CameraPreview, Activity paramActivity, boolean paramBoolean, int paramInt2)
  {
    super(paramContext, paramInt1);
    int i = 1;
    this.cameraPosition = 1;
    this.mCameraWorking = false;
    this.mScreenLandScape = false;
    requestWindowFeature(1);
    getWindow().setFlags(1024, 1024);
    this.mContext = paramContext;
    this.mThemeResId = paramInt1;
    this.mImagesPath = paramString;
    this.mTencentWebViewContentsClientAdapter = paramTencentWebViewContentsClientAdapter;
    this.mcallbackAdapter = paramValueCallback;
    this.mX5CameraPreviewDialog = paramX5CameraPreview;
    this.mActivity = paramActivity;
    this.mFrontCamera = paramBoolean;
    this.mFrontCameraIndex = paramInt2;
    if (this.mFrontCamera) {
      this.cameraPosition = 0;
    }
    paramContext = paramContext.getPackageManager();
    paramInt1 = i;
    if (!paramContext.hasSystemFeature("android.hardware.camera"))
    {
      paramInt1 = i;
      if (!paramContext.hasSystemFeature("android.hardware.camera.front"))
      {
        paramInt1 = i;
        if (Build.VERSION.SDK_INT <= 9) {
          if (Camera.getNumberOfCameras() > 0) {
            paramInt1 = i;
          } else {
            paramInt1 = 0;
          }
        }
      }
    }
    if (paramInt1 == 0)
    {
      Log.e("camera", "no camera");
      return;
    }
    paramContext = SharedResource.getLayoutInflater().inflate(SharedResource.loadIdentifierResource("x5_activity_camera", "layout"), null);
    setContentView(paramContext);
    initView(paramContext);
    initData();
    if (this.mFrontCamera)
    {
      if (this.mCamera == null)
      {
        paramContext = new Camera.CameraInfo();
        Camera.getCameraInfo(this.mFrontCameraIndex, paramContext);
        this.mCamera = Camera.open(this.mFrontCameraIndex);
        setCameraDisplayOrientation(paramContext, this.mCamera);
      }
    }
    else {
      initCamera();
    }
    new Timer().schedule(new TimerTask()
    {
      public void run()
      {
        if (!X5CameraDialog.this.mFrontCamera)
        {
          X5CameraDialog localX5CameraDialog = X5CameraDialog.this;
          localX5CameraDialog.setAutoFocus(localX5CameraDialog.mCamera);
        }
      }
    }, 500L);
    paramInt2 = 1000;
    paramInt1 = paramInt2;
    if (Build.MANUFACTURER.contains("HUAWEI"))
    {
      paramInt1 = paramInt2;
      if (Build.MODEL.contains("GRA-UL")) {
        paramInt1 = 2000;
      }
    }
    new Timer().schedule(new TimerTask()
    {
      public void run()
      {
        if (X5CameraDialog.this.mX5CameraPreviewDialog != null) {
          X5CameraDialog.this.mX5CameraPreviewDialog.dismiss();
        }
      }
    }, paramInt1);
  }
  
  private void findBestPictureSizeIfNeeded(Camera paramCamera)
  {
    Camera.Parameters localParameters = paramCamera.getParameters();
    Object localObject1 = localParameters.getPictureSize();
    if ((((Camera.Size)localObject1).width < 1000) && (((Camera.Size)localObject1).height < 1000))
    {
      Object localObject2 = localParameters.get("picture-size-values");
      localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = localParameters.get("picture-size-value");
      }
      localObject1 = ((String)localObject1).split(",");
      int i10 = localObject1.length;
      int n = 0;
      int k = 0;
      int m = 0;
      int i7;
      for (int i1 = Integer.MAX_VALUE; n < i10; i1 = i7)
      {
        localObject2 = localObject1[n].trim();
        j = ((String)localObject2).indexOf('x');
        if (j == -1)
        {
          i4 = k;
          i6 = m;
          i7 = i1;
        }
        try
        {
          int i = Integer.parseInt(((String)localObject2).substring(0, j));
          j = Integer.parseInt(((String)localObject2).substring(j + 1));
          localObject2 = new Rect();
          this.mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame((Rect)localObject2);
          Point localPoint = new Point(((Rect)localObject2).width(), ((Rect)localObject2).height());
          int i9 = Math.abs(i - localPoint.x) + Math.abs(j - localPoint.y);
          int i2;
          if (i > j) {
            i2 = i;
          } else {
            i2 = j;
          }
          int i3;
          if (i > j) {
            i3 = j;
          } else {
            i3 = i;
          }
          int i5;
          if (((Rect)localObject2).width() > ((Rect)localObject2).height()) {
            i5 = ((Rect)localObject2).width();
          } else {
            i5 = ((Rect)localObject2).height();
          }
          int i8;
          if (((Rect)localObject2).width() > ((Rect)localObject2).height()) {
            i8 = ((Rect)localObject2).height();
          } else {
            i8 = ((Rect)localObject2).width();
          }
          if ((i2 == i5) && (i3 == i8))
          {
            k = i;
            break label441;
          }
          i4 = k;
          i6 = m;
          i7 = i1;
          if (i2 < i5) {
            break label417;
          }
          i4 = k;
          i6 = m;
          i7 = i1;
          if (i3 < i8) {
            break label417;
          }
          i4 = k;
          i6 = m;
          i7 = i1;
          if (i9 >= i1) {
            break label417;
          }
          i7 = i9;
          i4 = i;
          i6 = j;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          for (;;) {}
        }
        i7 = i1;
        int i6 = m;
        int i4 = k;
        label417:
        n += 1;
        k = i4;
        m = i6;
      }
      int j = m;
      label441:
      if ((k > 0) && (j > 0))
      {
        localParameters.setPictureSize(k, j);
        paramCamera.setParameters(localParameters);
        return;
      }
    }
  }
  
  /* Error */
  private android.graphics.Bitmap getBitmapByUrl(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aconst_null
    //   3: astore 4
    //   5: new 401	java/io/FileInputStream
    //   8: dup
    //   9: aload_1
    //   10: invokespecial 404	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   13: astore_2
    //   14: aload_2
    //   15: astore_1
    //   16: aload_2
    //   17: invokestatic 410	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   20: astore_3
    //   21: aload_3
    //   22: astore_1
    //   23: aload_2
    //   24: invokevirtual 413	java/io/FileInputStream:close	()V
    //   27: aload_3
    //   28: areturn
    //   29: astore_2
    //   30: aload_2
    //   31: invokevirtual 416	java/io/IOException:printStackTrace	()V
    //   34: aload_1
    //   35: areturn
    //   36: astore_3
    //   37: goto +12 -> 49
    //   40: astore_1
    //   41: aload_3
    //   42: astore_2
    //   43: goto +30 -> 73
    //   46: astore_3
    //   47: aconst_null
    //   48: astore_2
    //   49: aload_2
    //   50: astore_1
    //   51: aload_3
    //   52: invokevirtual 417	java/io/FileNotFoundException:printStackTrace	()V
    //   55: aload_2
    //   56: ifnull +10 -> 66
    //   59: aload 4
    //   61: astore_1
    //   62: aload_2
    //   63: invokevirtual 413	java/io/FileInputStream:close	()V
    //   66: aconst_null
    //   67: areturn
    //   68: astore_3
    //   69: aload_1
    //   70: astore_2
    //   71: aload_3
    //   72: astore_1
    //   73: aload_2
    //   74: ifnull +15 -> 89
    //   77: aload_2
    //   78: invokevirtual 413	java/io/FileInputStream:close	()V
    //   81: goto +8 -> 89
    //   84: astore_2
    //   85: aload_2
    //   86: invokevirtual 416	java/io/IOException:printStackTrace	()V
    //   89: aload_1
    //   90: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	91	0	this	X5CameraDialog
    //   0	91	1	paramString	String
    //   13	11	2	localFileInputStream	java.io.FileInputStream
    //   29	2	2	localIOException1	IOException
    //   42	36	2	localObject1	Object
    //   84	2	2	localIOException2	IOException
    //   1	27	3	localBitmap	android.graphics.Bitmap
    //   36	6	3	localFileNotFoundException1	java.io.FileNotFoundException
    //   46	6	3	localFileNotFoundException2	java.io.FileNotFoundException
    //   68	4	3	localObject2	Object
    //   3	57	4	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   23	27	29	java/io/IOException
    //   62	66	29	java/io/IOException
    //   16	21	36	java/io/FileNotFoundException
    //   5	14	40	finally
    //   5	14	46	java/io/FileNotFoundException
    //   16	21	68	finally
    //   51	55	68	finally
    //   77	81	84	java/io/IOException
  }
  
  private Camera getCamera()
  {
    Camera localCamera = Camera.open();
    localCamera.setDisplayOrientation(90);
    findBestPictureSizeIfNeeded(localCamera);
    setAutoFocus(localCamera);
    return localCamera;
  }
  
  private void goPreviewView(String paramString)
  {
    if (this.mPreviewView == null)
    {
      this.mPreviewView = SharedResource.getLayoutInflater().inflate(SharedResource.loadIdentifierResource("x5_camera_preview", "layout"), null);
      this.previewCameraIv = ((ImageView)this.mPreviewView.findViewById(SharedResource.loadIdentifierResource("x5_id_preview_camera_iv", "id")));
      if (!TextUtils.isEmpty(paramString)) {
        this.previewCameraIv.setImageBitmap(getBitmapByUrl(paramString));
      }
    }
    setContentView(this.mPreviewView);
  }
  
  private void initData()
  {
    this.mHolder = this.surfaceSv.getHolder();
    this.mHolder.addCallback(this);
  }
  
  private void initView(View paramView)
  {
    this.switchCameraBtn = ((ImageButton)paramView.findViewById(SharedResource.loadIdentifierResource("x5_id_switch_camera_btn", "id")));
    this.captureBtn = ((ImageButton)paramView.findViewById(SharedResource.loadIdentifierResource("x5_id_capture_btn", "id")));
    this.backBtn = ((ImageButton)paramView.findViewById(SharedResource.loadIdentifierResource("x5_id_camera_back", "id")));
    this.surfaceSv = ((SurfaceView)paramView.findViewById(SharedResource.loadIdentifierResource("x5_id_area_sv", "id")));
    int i = SmttResource.loadIdentifierResource("x5_camera_switch", "drawable");
    this.switchCameraBtn.setBackgroundResource(i);
    i = SmttResource.loadIdentifierResource("x5_camera_take", "drawable");
    this.captureBtn.setBackgroundResource(i);
    i = SmttResource.loadIdentifierResource("x5_camera_back", "drawable");
    this.backBtn.setBackgroundResource(i);
    this.backBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = new HashMap();
        paramAnonymousView.put("coreversion", ContextHolder.getInstance().getTbsCoreVersion());
        SmttServiceProxy.getInstance().upLoadToBeacon("CH0049", paramAnonymousView);
        X5CameraDialog.this.dismiss();
        X5CameraDialog.this.releaseCamera();
      }
    });
    this.switchCameraBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = new HashMap();
        paramAnonymousView.put("coreversion", ContextHolder.getInstance().getTbsCoreVersion());
        SmttServiceProxy.getInstance().upLoadToBeacon("CH0045", paramAnonymousView);
        paramAnonymousView = new Camera.CameraInfo();
        int j = Camera.getNumberOfCameras();
        int i = 0;
        while (i < j)
        {
          Camera.getCameraInfo(i, paramAnonymousView);
          if (X5CameraDialog.this.cameraPosition == 1)
          {
            if (paramAnonymousView.facing == 1)
            {
              X5CameraDialog.this.releaseCamera();
              X5CameraDialog.access$102(X5CameraDialog.this, Camera.open(i));
              X5CameraDialog.access$602(X5CameraDialog.this, i);
              X5CameraDialog localX5CameraDialog = X5CameraDialog.this;
              localX5CameraDialog.findBestPictureSizeIfNeeded(localX5CameraDialog.mCamera);
              localX5CameraDialog = X5CameraDialog.this;
              localX5CameraDialog.setCameraDisplayOrientation(paramAnonymousView, localX5CameraDialog.mCamera);
              paramAnonymousView = X5CameraDialog.this;
              paramAnonymousView.setStartPreview(paramAnonymousView.mCamera, X5CameraDialog.this.mHolder);
              X5CameraDialog.access$502(X5CameraDialog.this, 0);
            }
          }
          else if (paramAnonymousView.facing == 0)
          {
            X5CameraDialog.this.releaseCamera();
            paramAnonymousView = X5CameraDialog.this;
            X5CameraDialog.access$102(paramAnonymousView, paramAnonymousView.getCamera());
            paramAnonymousView = X5CameraDialog.this;
            paramAnonymousView.setStartPreview(paramAnonymousView.mCamera, X5CameraDialog.this.mHolder);
            X5CameraDialog.access$502(X5CameraDialog.this, 1);
            return;
          }
          i += 1;
        }
      }
    });
    this.captureBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (X5CameraDialog.this.mCameraWorking) {
          return;
        }
        paramAnonymousView = new HashMap();
        paramAnonymousView.put("coreversion", ContextHolder.getInstance().getTbsCoreVersion());
        SmttServiceProxy.getInstance().upLoadToBeacon("CH0046", paramAnonymousView);
        if (X5CameraDialog.this.mCamera != null)
        {
          X5CameraDialog.access$1202(X5CameraDialog.this, true);
          X5CameraDialog.this.mCamera.takePicture(new Camera.ShutterCallback()
          {
            public void onShutter() {}
          }, null, null, new Camera.PictureCallback()
          {
            /* Error */
            public void onPictureTaken(byte[] paramAnonymous2ArrayOfByte, Camera paramAnonymous2Camera)
            {
              // Byte code:
              //   0: new 27	java/lang/StringBuilder
              //   3: dup
              //   4: invokespecial 28	java/lang/StringBuilder:<init>	()V
              //   7: astore_2
              //   8: aload_2
              //   9: ldc 30
              //   11: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
              //   14: pop
              //   15: aload_2
              //   16: invokestatic 40	java/lang/System:currentTimeMillis	()J
              //   19: invokevirtual 43	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
              //   22: pop
              //   23: aload_2
              //   24: ldc 45
              //   26: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
              //   29: pop
              //   30: aload_2
              //   31: invokevirtual 49	java/lang/StringBuilder:toString	()Ljava/lang/String;
              //   34: pop
              //   35: iconst_1
              //   36: istore_3
              //   37: new 51	java/io/File
              //   40: dup
              //   41: aload_0
              //   42: getfield 17	com/tencent/tbs/core/partner/camera/X5CameraDialog$5$2:this$1	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog$5;
              //   45: getfield 55	com/tencent/tbs/core/partner/camera/X5CameraDialog$5:this$0	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;
              //   48: invokestatic 61	com/tencent/tbs/core/partner/camera/X5CameraDialog:access$1300	(Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;)Ljava/lang/String;
              //   51: invokespecial 64	java/io/File:<init>	(Ljava/lang/String;)V
              //   54: astore_2
              //   55: new 66	java/io/FileOutputStream
              //   58: dup
              //   59: aload_2
              //   60: invokespecial 69	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
              //   63: astore 5
              //   65: new 71	android/graphics/BitmapFactory$Options
              //   68: dup
              //   69: invokespecial 72	android/graphics/BitmapFactory$Options:<init>	()V
              //   72: astore 4
              //   74: aload 4
              //   76: iconst_2
              //   77: putfield 76	android/graphics/BitmapFactory$Options:inSampleSize	I
              //   80: aload_1
              //   81: iconst_0
              //   82: aload_1
              //   83: arraylength
              //   84: aload 4
              //   86: invokestatic 82	android/graphics/BitmapFactory:decodeByteArray	([BIILandroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
              //   89: astore_1
              //   90: new 84	android/graphics/Matrix
              //   93: dup
              //   94: invokespecial 85	android/graphics/Matrix:<init>	()V
              //   97: astore 4
              //   99: aload_0
              //   100: getfield 17	com/tencent/tbs/core/partner/camera/X5CameraDialog$5$2:this$1	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog$5;
              //   103: getfield 55	com/tencent/tbs/core/partner/camera/X5CameraDialog$5:this$0	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;
              //   106: invokestatic 89	com/tencent/tbs/core/partner/camera/X5CameraDialog:access$500	(Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;)I
              //   109: ifne +110 -> 219
              //   112: aload_0
              //   113: getfield 17	com/tencent/tbs/core/partner/camera/X5CameraDialog$5$2:this$1	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog$5;
              //   116: getfield 55	com/tencent/tbs/core/partner/camera/X5CameraDialog$5:this$0	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;
              //   119: iconst_1
              //   120: invokestatic 93	com/tencent/tbs/core/partner/camera/X5CameraDialog:access$002	(Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;Z)Z
              //   123: pop
              //   124: aload_0
              //   125: getfield 17	com/tencent/tbs/core/partner/camera/X5CameraDialog$5$2:this$1	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog$5;
              //   128: getfield 55	com/tencent/tbs/core/partner/camera/X5CameraDialog$5:this$0	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;
              //   131: invokestatic 97	com/tencent/tbs/core/partner/camera/X5CameraDialog:access$1400	(Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;)Z
              //   134: ifeq +23 -> 157
              //   137: aload 4
              //   139: aload_0
              //   140: getfield 17	com/tencent/tbs/core/partner/camera/X5CameraDialog$5$2:this$1	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog$5;
              //   143: getfield 55	com/tencent/tbs/core/partner/camera/X5CameraDialog$5:this$0	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;
              //   146: invokestatic 100	com/tencent/tbs/core/partner/camera/X5CameraDialog:access$1500	(Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;)I
              //   149: i2f
              //   150: invokevirtual 104	android/graphics/Matrix:postRotate	(F)Z
              //   153: pop
              //   154: goto +53 -> 207
              //   157: aload_0
              //   158: getfield 17	com/tencent/tbs/core/partner/camera/X5CameraDialog$5$2:this$1	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog$5;
              //   161: getfield 55	com/tencent/tbs/core/partner/camera/X5CameraDialog$5:this$0	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;
              //   164: invokestatic 100	com/tencent/tbs/core/partner/camera/X5CameraDialog:access$1500	(Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;)I
              //   167: bipush 90
              //   169: if_icmpne +14 -> 183
              //   172: aload 4
              //   174: ldc 105
              //   176: invokevirtual 104	android/graphics/Matrix:postRotate	(F)Z
              //   179: pop
              //   180: goto +27 -> 207
              //   183: aload_0
              //   184: getfield 17	com/tencent/tbs/core/partner/camera/X5CameraDialog$5$2:this$1	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog$5;
              //   187: getfield 55	com/tencent/tbs/core/partner/camera/X5CameraDialog$5:this$0	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;
              //   190: invokestatic 100	com/tencent/tbs/core/partner/camera/X5CameraDialog:access$1500	(Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;)I
              //   193: sipush 270
              //   196: if_icmpne +11 -> 207
              //   199: aload 4
              //   201: ldc 106
              //   203: invokevirtual 104	android/graphics/Matrix:postRotate	(F)Z
              //   206: pop
              //   207: aload 4
              //   209: ldc 107
              //   211: fconst_1
              //   212: invokevirtual 111	android/graphics/Matrix:postScale	(FF)Z
              //   215: pop
              //   216: goto +68 -> 284
              //   219: aload_0
              //   220: getfield 17	com/tencent/tbs/core/partner/camera/X5CameraDialog$5$2:this$1	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog$5;
              //   223: getfield 55	com/tencent/tbs/core/partner/camera/X5CameraDialog$5:this$0	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;
              //   226: iconst_0
              //   227: invokestatic 93	com/tencent/tbs/core/partner/camera/X5CameraDialog:access$002	(Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;Z)Z
              //   230: pop
              //   231: aload_0
              //   232: getfield 17	com/tencent/tbs/core/partner/camera/X5CameraDialog$5$2:this$1	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog$5;
              //   235: getfield 55	com/tencent/tbs/core/partner/camera/X5CameraDialog$5:this$0	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;
              //   238: invokestatic 97	com/tencent/tbs/core/partner/camera/X5CameraDialog:access$1400	(Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;)Z
              //   241: ifeq +22 -> 263
              //   244: aload 4
              //   246: fconst_0
              //   247: aload_1
              //   248: invokevirtual 117	android/graphics/Bitmap:getWidth	()I
              //   251: i2f
              //   252: aload_1
              //   253: invokevirtual 120	android/graphics/Bitmap:getHeight	()I
              //   256: i2f
              //   257: invokevirtual 124	android/graphics/Matrix:setRotate	(FFF)V
              //   260: goto +24 -> 284
              //   263: aload 4
              //   265: ldc 106
              //   267: aload_1
              //   268: invokevirtual 117	android/graphics/Bitmap:getWidth	()I
              //   271: i2f
              //   272: fconst_2
              //   273: fdiv
              //   274: aload_1
              //   275: invokevirtual 120	android/graphics/Bitmap:getHeight	()I
              //   278: i2f
              //   279: fconst_2
              //   280: fdiv
              //   281: invokevirtual 124	android/graphics/Matrix:setRotate	(FFF)V
              //   284: aload_1
              //   285: iconst_0
              //   286: iconst_0
              //   287: aload_1
              //   288: invokevirtual 117	android/graphics/Bitmap:getWidth	()I
              //   291: aload_1
              //   292: invokevirtual 120	android/graphics/Bitmap:getHeight	()I
              //   295: aload 4
              //   297: iconst_1
              //   298: invokestatic 128	android/graphics/Bitmap:createBitmap	(Landroid/graphics/Bitmap;IIIILandroid/graphics/Matrix;Z)Landroid/graphics/Bitmap;
              //   301: astore 4
              //   303: aload 4
              //   305: aload_1
              //   306: if_acmpeq +7 -> 313
              //   309: aload_1
              //   310: invokevirtual 131	android/graphics/Bitmap:recycle	()V
              //   313: aload 4
              //   315: getstatic 137	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
              //   318: bipush 80
              //   320: aload 5
              //   322: invokevirtual 141	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
              //   325: pop
              //   326: aload 4
              //   328: invokevirtual 131	android/graphics/Bitmap:recycle	()V
              //   331: aload 5
              //   333: invokevirtual 144	java/io/FileOutputStream:flush	()V
              //   336: aload_2
              //   337: astore 4
              //   339: aload 5
              //   341: invokevirtual 147	java/io/FileOutputStream:close	()V
              //   344: aload_2
              //   345: astore_1
              //   346: goto +62 -> 408
              //   349: astore_1
              //   350: goto +18 -> 368
              //   353: aconst_null
              //   354: astore 4
              //   356: aload_2
              //   357: astore_1
              //   358: aload 4
              //   360: astore_2
              //   361: goto +23 -> 384
              //   364: astore_1
              //   365: aconst_null
              //   366: astore 5
              //   368: aload 5
              //   370: ifnull +8 -> 378
              //   373: aload 5
              //   375: invokevirtual 147	java/io/FileOutputStream:close	()V
              //   378: aload_1
              //   379: athrow
              //   380: aconst_null
              //   381: astore_1
              //   382: aload_1
              //   383: astore_2
              //   384: aload_2
              //   385: ifnull +21 -> 406
              //   388: aload_1
              //   389: astore 4
              //   391: aload_2
              //   392: invokevirtual 147	java/io/FileOutputStream:close	()V
              //   395: goto +11 -> 406
              //   398: iconst_0
              //   399: istore_3
              //   400: aload 4
              //   402: astore_1
              //   403: goto +5 -> 408
              //   406: iconst_0
              //   407: istore_3
              //   408: iload_3
              //   409: ifeq +114 -> 523
              //   412: new 149	com/tencent/tbs/core/partner/camera/X5CameraPreview
              //   415: dup
              //   416: aload_0
              //   417: getfield 17	com/tencent/tbs/core/partner/camera/X5CameraDialog$5$2:this$1	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog$5;
              //   420: getfield 55	com/tencent/tbs/core/partner/camera/X5CameraDialog$5:this$0	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;
              //   423: invokestatic 153	com/tencent/tbs/core/partner/camera/X5CameraDialog:access$1600	(Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;)Landroid/content/Context;
              //   426: aload_0
              //   427: getfield 17	com/tencent/tbs/core/partner/camera/X5CameraDialog$5$2:this$1	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog$5;
              //   430: getfield 55	com/tencent/tbs/core/partner/camera/X5CameraDialog$5:this$0	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;
              //   433: invokestatic 156	com/tencent/tbs/core/partner/camera/X5CameraDialog:access$1700	(Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;)I
              //   436: aload_1
              //   437: invokevirtual 159	java/io/File:getAbsolutePath	()Ljava/lang/String;
              //   440: aload_0
              //   441: getfield 17	com/tencent/tbs/core/partner/camera/X5CameraDialog$5$2:this$1	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog$5;
              //   444: getfield 55	com/tencent/tbs/core/partner/camera/X5CameraDialog$5:this$0	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;
              //   447: aload_0
              //   448: getfield 17	com/tencent/tbs/core/partner/camera/X5CameraDialog$5$2:this$1	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog$5;
              //   451: getfield 55	com/tencent/tbs/core/partner/camera/X5CameraDialog$5:this$0	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;
              //   454: invokestatic 61	com/tencent/tbs/core/partner/camera/X5CameraDialog:access$1300	(Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;)Ljava/lang/String;
              //   457: aload_0
              //   458: getfield 17	com/tencent/tbs/core/partner/camera/X5CameraDialog$5$2:this$1	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog$5;
              //   461: getfield 55	com/tencent/tbs/core/partner/camera/X5CameraDialog$5:this$0	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;
              //   464: invokestatic 163	com/tencent/tbs/core/partner/camera/X5CameraDialog:access$1800	(Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;)Landroid/webview/chromium/tencent/TencentWebViewContentsClientAdapter;
              //   467: aload_0
              //   468: getfield 17	com/tencent/tbs/core/partner/camera/X5CameraDialog$5$2:this$1	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog$5;
              //   471: getfield 55	com/tencent/tbs/core/partner/camera/X5CameraDialog$5:this$0	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;
              //   474: invokestatic 167	com/tencent/tbs/core/partner/camera/X5CameraDialog:access$1900	(Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;)Lcom/tencent/tbs/core/webkit/ValueCallback;
              //   477: aload_0
              //   478: getfield 17	com/tencent/tbs/core/partner/camera/X5CameraDialog$5$2:this$1	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog$5;
              //   481: getfield 55	com/tencent/tbs/core/partner/camera/X5CameraDialog$5:this$0	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;
              //   484: invokestatic 171	com/tencent/tbs/core/partner/camera/X5CameraDialog:access$2000	(Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;)Landroid/app/Activity;
              //   487: aload_0
              //   488: getfield 17	com/tencent/tbs/core/partner/camera/X5CameraDialog$5$2:this$1	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog$5;
              //   491: getfield 55	com/tencent/tbs/core/partner/camera/X5CameraDialog$5:this$0	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;
              //   494: invokestatic 174	com/tencent/tbs/core/partner/camera/X5CameraDialog:access$000	(Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;)Z
              //   497: aload_0
              //   498: getfield 17	com/tencent/tbs/core/partner/camera/X5CameraDialog$5$2:this$1	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog$5;
              //   501: getfield 55	com/tencent/tbs/core/partner/camera/X5CameraDialog$5:this$0	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;
              //   504: invokestatic 177	com/tencent/tbs/core/partner/camera/X5CameraDialog:access$600	(Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;)I
              //   507: invokespecial 180	com/tencent/tbs/core/partner/camera/X5CameraPreview:<init>	(Landroid/content/Context;ILjava/lang/String;Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;Ljava/lang/String;Landroid/webview/chromium/tencent/TencentWebViewContentsClientAdapter;Lcom/tencent/tbs/core/webkit/ValueCallback;Landroid/app/Activity;ZI)V
              //   510: invokevirtual 183	com/tencent/tbs/core/partner/camera/X5CameraPreview:show	()V
              //   513: aload_0
              //   514: getfield 17	com/tencent/tbs/core/partner/camera/X5CameraDialog$5$2:this$1	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog$5;
              //   517: getfield 55	com/tencent/tbs/core/partner/camera/X5CameraDialog$5:this$0	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;
              //   520: invokestatic 187	com/tencent/tbs/core/partner/camera/X5CameraDialog:access$400	(Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;)V
              //   523: aload_0
              //   524: getfield 17	com/tencent/tbs/core/partner/camera/X5CameraDialog$5$2:this$1	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog$5;
              //   527: getfield 55	com/tencent/tbs/core/partner/camera/X5CameraDialog$5:this$0	Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;
              //   530: iconst_0
              //   531: invokestatic 190	com/tencent/tbs/core/partner/camera/X5CameraDialog:access$1202	(Lcom/tencent/tbs/core/partner/camera/X5CameraDialog;Z)Z
              //   534: pop
              //   535: return
              //   536: astore_1
              //   537: goto -157 -> 380
              //   540: astore_1
              //   541: goto -188 -> 353
              //   544: astore_1
              //   545: aload_2
              //   546: astore_1
              //   547: aload 5
              //   549: astore_2
              //   550: goto -166 -> 384
              //   553: astore_1
              //   554: goto -156 -> 398
              //   557: astore_2
              //   558: goto -180 -> 378
              // Local variable table:
              //   start	length	slot	name	signature
              //   0	561	0	this	2
              //   0	561	1	paramAnonymous2ArrayOfByte	byte[]
              //   0	561	2	paramAnonymous2Camera	Camera
              //   36	373	3	i	int
              //   72	329	4	localObject	Object
              //   63	485	5	localFileOutputStream	java.io.FileOutputStream
              // Exception table:
              //   from	to	target	type
              //   65	154	349	finally
              //   157	180	349	finally
              //   183	207	349	finally
              //   207	216	349	finally
              //   219	260	349	finally
              //   263	284	349	finally
              //   284	303	349	finally
              //   309	313	349	finally
              //   313	336	349	finally
              //   37	55	364	finally
              //   55	65	364	finally
              //   37	55	536	java/lang/Exception
              //   55	65	540	java/lang/Exception
              //   65	154	544	java/lang/Exception
              //   157	180	544	java/lang/Exception
              //   183	207	544	java/lang/Exception
              //   207	216	544	java/lang/Exception
              //   219	260	544	java/lang/Exception
              //   263	284	544	java/lang/Exception
              //   284	303	544	java/lang/Exception
              //   309	313	544	java/lang/Exception
              //   313	336	544	java/lang/Exception
              //   339	344	553	java/lang/Exception
              //   391	395	553	java/lang/Exception
              //   373	378	557	java/lang/Exception
            }
          });
        }
      }
    });
  }
  
  private void releaseCamera()
  {
    Camera localCamera = this.mCamera;
    if (localCamera != null)
    {
      localCamera.setPreviewCallback(null);
      this.mCamera.stopPreview();
      this.mCamera.release();
      this.mCamera = null;
    }
  }
  
  private void setAutoFocus(Camera paramCamera)
  {
    Camera.Parameters localParameters = paramCamera.getParameters();
    if (localParameters.getSupportedFocusModes().contains("continuous-picture")) {
      localParameters.setFocusMode("continuous-picture");
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new Camera.Area(new Rect(0, 0, 100, 100), 100));
    localParameters.setFocusAreas(localArrayList);
    paramCamera.setParameters(localParameters);
  }
  
  private void setCameraDisplayOrientation(Camera.CameraInfo paramCameraInfo, Camera paramCamera)
  {
    Activity localActivity = this.mActivity;
    if (localActivity == null) {
      return;
    }
    int k = localActivity.getWindowManager().getDefaultDisplay().getRotation();
    int j = 0;
    int i = j;
    switch (k)
    {
    default: 
      i = j;
      break;
    case 3: 
      i = 270;
      break;
    case 2: 
      i = 180;
      break;
    case 1: 
      i = 90;
    }
    i = (360 - (paramCameraInfo.orientation + i) % 360) % 360;
    paramCamera.setDisplayOrientation(i);
    this.mRotateDegree = i;
  }
  
  private void setStartPreview(Camera paramCamera, SurfaceHolder paramSurfaceHolder)
  {
    try
    {
      paramCamera.setPreviewDisplay(paramSurfaceHolder);
      paramCamera.startPreview();
      return;
    }
    catch (IOException paramCamera) {}
  }
  
  public void initCamera()
  {
    if (this.mCamera == null)
    {
      this.mCamera = getCamera();
      SurfaceHolder localSurfaceHolder = this.mHolder;
      if (localSurfaceHolder != null) {
        setStartPreview(this.mCamera, localSurfaceHolder);
      }
    }
  }
  
  public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.mHolder.getSurface() != null)
    {
      paramSurfaceHolder = this.mCamera;
      if (paramSurfaceHolder == null) {
        return;
      }
    }
    try
    {
      paramSurfaceHolder.stopPreview();
      if (paramInt2 > paramInt3)
      {
        this.mScreenLandScape = true;
        if (this.cameraPosition == 0) {
          new Timer().schedule(new TimerTask()
          {
            public void run()
            {
              Camera.CameraInfo localCameraInfo = new Camera.CameraInfo();
              Camera.getCameraInfo(X5CameraDialog.this.mFrontCameraIndex, localCameraInfo);
              X5CameraDialog localX5CameraDialog = X5CameraDialog.this;
              localX5CameraDialog.setCameraDisplayOrientation(localCameraInfo, localX5CameraDialog.mCamera);
            }
          }, 500L);
        } else {
          new Timer().schedule(new TimerTask()
          {
            public void run()
            {
              X5CameraDialog.this.mCamera.setDisplayOrientation(0);
            }
          }, 500L);
        }
      }
      else
      {
        this.mScreenLandScape = false;
        if (this.cameraPosition == 0) {
          new Timer().schedule(new TimerTask()
          {
            public void run()
            {
              Camera.CameraInfo localCameraInfo = new Camera.CameraInfo();
              Camera.getCameraInfo(X5CameraDialog.this.mFrontCameraIndex, localCameraInfo);
              X5CameraDialog localX5CameraDialog = X5CameraDialog.this;
              localX5CameraDialog.setCameraDisplayOrientation(localCameraInfo, localX5CameraDialog.mCamera);
            }
          }, 500L);
        } else {
          new Timer().schedule(new TimerTask()
          {
            public void run()
            {
              X5CameraDialog.this.mCamera.setDisplayOrientation(90);
            }
          }, 500L);
        }
      }
    }
    catch (Exception paramSurfaceHolder)
    {
      for (;;) {}
    }
    setStartPreview(this.mCamera, this.mHolder);
    return;
  }
  
  public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
  {
    if (this.cameraPosition == 0)
    {
      if (this.mCamera == null)
      {
        paramSurfaceHolder = new Camera.CameraInfo();
        Camera.getCameraInfo(this.mFrontCameraIndex, paramSurfaceHolder);
        this.mCamera = Camera.open(this.mFrontCameraIndex);
        setCameraDisplayOrientation(paramSurfaceHolder, this.mCamera);
      }
    }
    else {
      initCamera();
    }
    setStartPreview(this.mCamera, this.mHolder);
  }
  
  public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
  {
    releaseCamera();
    this.surfaceSv = null;
  }
  
  public void upload()
  {
    Object localObject1 = new File(this.mImagesPath);
    Uri localUri = Uri.fromFile((File)localObject1);
    this.mcallbackAdapter.onReceiveValue(new Uri[] { localUri });
    Object localObject2 = new HashMap();
    ((Map)localObject2).put("coreversion", ContextHolder.getInstance().getTbsCoreVersion());
    ((Map)localObject2).put("packageName", this.mContext.getApplicationInfo().packageName);
    SmttServiceProxy.getInstance().upLoadToBeacon("x5cameraupload", (Map)localObject2);
    localObject2 = new ContentValues();
    ((ContentValues)localObject2).put("_data", ((File)localObject1).toString());
    ((ContentValues)localObject2).put("description", "save image ---");
    ((ContentValues)localObject2).put("mime_type", "image/jpeg");
    getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, (ContentValues)localObject2);
    localObject1 = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", localUri);
    getContext().sendBroadcast((Intent)localObject1);
    dismiss();
    this.mTencentWebViewContentsClientAdapter.onClickToFileChooser();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\camera\X5CameraDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */