package com.tencent.tbs.core.partner.camera;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webview.chromium.tencent.TencentWebViewContentsClientAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.ValueCallback;
import java.util.HashMap;
import java.util.Map;
import org.chromium.tencent.SharedResource;

public class X5CameraPreview
  extends Dialog
{
  private Activity mActivity;
  private Context mContext;
  private boolean mFrontCamera;
  private int mFrontCameraIndex;
  private String mImagesPath;
  private TencentWebViewContentsClientAdapter mTencentWebViewContentsClientAdapter;
  private X5CameraDialog mX5CameraDialog;
  private ValueCallback<Uri[]> mcallbackAdapter;
  private ImageView previewCameraIv;
  private TextView recaptureBtn;
  private TextView uploadBtn;
  
  public X5CameraPreview(Context paramContext, int paramInt1, String paramString1, X5CameraDialog paramX5CameraDialog, String paramString2, TencentWebViewContentsClientAdapter paramTencentWebViewContentsClientAdapter, ValueCallback<Uri[]> paramValueCallback, Activity paramActivity, boolean paramBoolean, int paramInt2)
  {
    super(paramContext, paramInt1);
    requestWindowFeature(1);
    getWindow().setFlags(1024, 1024);
    View localView = SharedResource.getLayoutInflater().inflate(SharedResource.loadIdentifierResource("x5_camera_preview", "layout"), null);
    setContentView(localView);
    this.previewCameraIv = ((ImageView)localView.findViewById(SharedResource.loadIdentifierResource("x5_id_preview_camera_iv", "id")));
    this.previewCameraIv.setBackgroundColor(-16777216);
    if (!TextUtils.isEmpty(paramString1)) {
      this.previewCameraIv.setImageBitmap(getBitmapByUrl(paramString1));
    }
    this.recaptureBtn = ((TextView)localView.findViewById(SharedResource.loadIdentifierResource("x5_id_recapture_btn", "id")));
    this.uploadBtn = ((TextView)localView.findViewById(SharedResource.loadIdentifierResource("x5_id_upload_btn", "id")));
    this.recaptureBtn.setText("重拍");
    this.uploadBtn.setText("确定");
    this.uploadBtn.setBackgroundColor(Color.rgb(76, 153, 249));
    this.recaptureBtn.setBackgroundColor(Color.rgb(239, 239, 239));
    this.uploadBtn.setTextColor(Color.rgb(255, 255, 255));
    this.uploadBtn.setTextSize(1, 16.0F);
    this.uploadBtn.setGravity(17);
    this.recaptureBtn.setTextColor(Color.rgb(36, 36, 36));
    this.recaptureBtn.setTextSize(1, 16.0F);
    this.recaptureBtn.setGravity(17);
    this.mContext = paramContext;
    this.mImagesPath = paramString2;
    this.mTencentWebViewContentsClientAdapter = paramTencentWebViewContentsClientAdapter;
    this.mcallbackAdapter = paramValueCallback;
    this.mActivity = paramActivity;
    this.mFrontCamera = paramBoolean;
    this.mFrontCameraIndex = paramInt2;
    setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        if ((paramAnonymousInt == 4) && (paramAnonymousKeyEvent.getAction() == 1))
        {
          X5CameraPreview.this.mX5CameraDialog.dismiss();
          X5CameraPreview.this.dismiss();
        }
        return false;
      }
    });
    this.recaptureBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = new HashMap();
        paramAnonymousView.put("coreversion", ContextHolder.getInstance().getTbsCoreVersion());
        SmttServiceProxy.getInstance().upLoadToBeacon("CH0047", paramAnonymousView);
        X5CameraPreview.this.mX5CameraDialog.dismiss();
        int i = SharedResource.loadIdentifierResource("x5_dialogTheme_fullscreen", "style");
        paramAnonymousView = X5CameraPreview.this.mContext;
        String str = X5CameraPreview.this.mImagesPath;
        TencentWebViewContentsClientAdapter localTencentWebViewContentsClientAdapter = X5CameraPreview.this.mTencentWebViewContentsClientAdapter;
        ValueCallback localValueCallback = X5CameraPreview.this.mcallbackAdapter;
        X5CameraPreview localX5CameraPreview = X5CameraPreview.this;
        new X5CameraDialog(paramAnonymousView, i, str, localTencentWebViewContentsClientAdapter, localValueCallback, localX5CameraPreview, localX5CameraPreview.mActivity, X5CameraPreview.this.mFrontCamera, X5CameraPreview.this.mFrontCameraIndex).show();
      }
    });
    this.uploadBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = new HashMap();
        paramAnonymousView.put("coreversion", ContextHolder.getInstance().getTbsCoreVersion());
        SmttServiceProxy.getInstance().upLoadToBeacon("CH0048", paramAnonymousView);
        X5CameraPreview.this.mX5CameraDialog.upload();
        X5CameraPreview.this.dismiss();
      }
    });
    this.mX5CameraDialog = paramX5CameraDialog;
  }
  
  /* Error */
  private android.graphics.Bitmap getBitmapByUrl(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aconst_null
    //   3: astore 4
    //   5: new 198	java/io/FileInputStream
    //   8: dup
    //   9: aload_1
    //   10: invokespecial 201	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   13: astore_2
    //   14: aload_2
    //   15: astore_1
    //   16: aload_2
    //   17: invokestatic 207	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   20: astore_3
    //   21: aload_3
    //   22: astore_1
    //   23: aload_2
    //   24: invokevirtual 211	java/io/FileInputStream:close	()V
    //   27: aload_3
    //   28: areturn
    //   29: astore_2
    //   30: aload_2
    //   31: invokevirtual 214	java/io/IOException:printStackTrace	()V
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
    //   52: invokevirtual 215	java/io/FileNotFoundException:printStackTrace	()V
    //   55: aload_2
    //   56: ifnull +10 -> 66
    //   59: aload 4
    //   61: astore_1
    //   62: aload_2
    //   63: invokevirtual 211	java/io/FileInputStream:close	()V
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
    //   78: invokevirtual 211	java/io/FileInputStream:close	()V
    //   81: goto +8 -> 89
    //   84: astore_2
    //   85: aload_2
    //   86: invokevirtual 214	java/io/IOException:printStackTrace	()V
    //   89: aload_1
    //   90: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	91	0	this	X5CameraPreview
    //   0	91	1	paramString	String
    //   13	11	2	localFileInputStream	java.io.FileInputStream
    //   29	2	2	localIOException1	java.io.IOException
    //   42	36	2	localObject1	Object
    //   84	2	2	localIOException2	java.io.IOException
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
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\camera\X5CameraPreview.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */