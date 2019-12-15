package org.chromium.tencent;

import android.content.Context;
import android.net.http.SslCertificate;
import android.net.http.SslCertificate.DName;
import android.net.http.SslError;
import android.text.TextUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.cert.X509Certificate;
import org.chromium.android_webview.AwContentsClient;
import org.chromium.android_webview.AwContentsClientBridge;
import org.chromium.android_webview.ClientCertLookupTable;
import org.chromium.android_webview.JsResultHandler;
import org.chromium.android_webview.SslUtil;
import org.chromium.base.Callback;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.tencent.net.TencentAndroidCertVerifyResult;

public class TencentAwContentsClientBridge
  extends AwContentsClientBridge
{
  static final String CAMERA_OPEN_STATUS_CONFIRM = "camera_open_status_confirm";
  static final String CUSTOM_JS_CONFIRM_AUDIO_AUTO_PLAY = "audio_auto_play_confirm";
  static final long DEFAULT_TIME = 1509636124L;
  static final String GEOLOCATION_AUTHORIZED_STATUS_CONFIRM = "geolocation_authorized_status_confirm";
  private static final String TAG = "TencentAwContentsClientBridge";
  static final long TIME_ERROR_DEVIATION_IN_NTP = 864000L;
  static final long TIME_SWITCH_JUDGE_CAP_IN_AFTER_WHITELIST = 31536000L;
  static final long TIME_SWITCH_JUDGE_CAP_IN_BEFORE_WHITELIST = 172800L;
  static final String VIBRATION_AUTHORIZED_STATUS_CONFIRM = "vibration_authorized_status_confirm";
  private AwContentsClientExtension mContentsClientExtension;
  private Context mContext;
  
  public TencentAwContentsClientBridge(Context paramContext, AwContentsClient paramAwContentsClient, ClientCertLookupTable paramClientCertLookupTable)
  {
    super(paramContext, paramAwContentsClient, paramClientCertLookupTable);
    this.mContext = paramContext;
  }
  
  protected TencentAwContentsClientBridge(ClientCertLookupTable paramClientCertLookupTable)
  {
    super(paramClientCertLookupTable);
  }
  
  @CalledByNative
  private boolean allowCertificateError(int paramInt1, byte[] paramArrayOfByte, String paramString1, final int paramInt2, String paramString2)
  {
    Object localObject3 = SslUtil.getCertificateFromDerBytes(paramArrayOfByte);
    if (localObject3 == null) {
      return false;
    }
    SslError localSslError = SslUtil.sslErrorFromNetErrorCode(paramInt1, (SslCertificate)localObject3, paramString1);
    Callback local1 = new Callback()
    {
      public void onResult(final Boolean paramAnonymousBoolean)
      {
        ThreadUtils.runOnUiThread(new Runnable()
        {
          public void run()
          {
            TencentAwContentsClientBridge.this.proceedSslError(paramAnonymousBoolean.booleanValue(), TencentAwContentsClientBridge.1.this.val$id);
          }
        });
      }
    };
    long l1 = this.mContentsClientExtension.getRemoteServerTimeStamp();
    long l2 = this.mContentsClientExtension.getSntpTime();
    long l3 = System.currentTimeMillis() / 1000L;
    if (paramInt1 == 65335) {
      if (l2 > 1509636124L)
      {
        if (Math.abs(l3 - l2) > 864000L)
        {
          bool1 = true;
          break label176;
        }
      }
      else if (l1 > 1509636124L)
      {
        if ((l1 - l3 > 172800L) || (l3 - l1 > 31536000L))
        {
          bool1 = true;
          break label176;
        }
      }
      else if ((1509636124L - l3 > 172800L) || (l3 - 1509636124L > 31536000L))
      {
        bool1 = true;
        break label176;
      }
    }
    boolean bool1 = false;
    label176:
    paramArrayOfByte = null;
    try
    {
      localObject1 = new URL(paramString1).getHost();
      paramArrayOfByte = (byte[])localObject1;
      paramInt2 = TencentAndroidCertVerifyResult.getHostCertificateLength((String)localObject1);
    }
    catch (Exception localException)
    {
      Object localObject1;
      boolean bool2;
      Object localObject2;
      for (;;) {}
    }
    paramInt2 = 3;
    localObject1 = paramArrayOfByte;
    if ((paramInt2 == 1) && (paramInt1 == 65334)) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    localObject2 = "unknow";
    paramArrayOfByte = (byte[])localObject2;
    try
    {
      localSslError.getCertificate().getValidNotBefore();
      paramArrayOfByte = (byte[])localObject2;
      localSslError.getCertificate().getValidNotAfter();
      paramArrayOfByte = (byte[])localObject2;
      localSslError.getCertificate().getIssuedTo().getCName();
      paramArrayOfByte = (byte[])localObject2;
      localObject2 = getX509CertFromSslCert((SslCertificate)localObject3, "SHA256");
      paramArrayOfByte = (byte[])localObject2;
      localObject3 = getX509CertFromSslCert((SslCertificate)localObject3, "SHA1");
      paramArrayOfByte = (byte[])localObject2;
      localObject2 = localObject3;
    }
    catch (Error localError)
    {
      StringBuilder localStringBuilder;
      for (;;) {}
    }
    localObject2 = "unknow";
    localObject3 = this.mContentsClientExtension;
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("sha1:");
    localStringBuilder.append((String)localObject2);
    localStringBuilder.append("sha256:");
    localStringBuilder.append(paramArrayOfByte);
    ((AwContentsClientExtension)localObject3).recordCertErrorInfo(paramString1, (String)localObject1, localStringBuilder.toString(), paramInt1, paramString2, paramInt2);
    paramString2 = this.mContentsClientExtension;
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append((String)localObject2);
    ((StringBuilder)localObject1).append(":");
    ((StringBuilder)localObject1).append(Integer.toHexString(Math.abs(paramInt1) & 0xFF).toUpperCase());
    localObject1 = ((StringBuilder)localObject1).toString();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append(paramArrayOfByte);
    ((StringBuilder)localObject2).append(":");
    ((StringBuilder)localObject2).append(Integer.toHexString(Math.abs(paramInt1) & 0xFF).toUpperCase());
    paramString2.onReceivedSslError(local1, localSslError, paramString1, (String)localObject1, ((StringBuilder)localObject2).toString(), bool1, bool2);
    return true;
  }
  
  private String getX509CertFromSslCert(SslCertificate paramSslCertificate, String paramString)
  {
    label125:
    do
    {
      for (;;)
      {
        int i;
        try
        {
          localObject = paramSslCertificate.getClass();
          Field[] arrayOfField = ((Class)localObject).getDeclaredFields();
          int j = arrayOfField.length;
          i = 0;
          if (i >= j) {
            break;
          }
          Field localField = arrayOfField[i];
          if (!localField.getName().equals("mX509Certificate")) {
            break label125;
          }
          localField.setAccessible(true);
          paramSslCertificate = (X509Certificate)localField.get(paramSslCertificate);
        }
        catch (Exception paramSslCertificate)
        {
          Object localObject;
          paramSslCertificate.printStackTrace();
          return "null";
        }
        localObject = ((Class)localObject).getDeclaredMethod("getDigest", new Class[] { X509Certificate.class, String.class });
        ((Method)localObject).setAccessible(true);
        paramSslCertificate = (String)((Method)localObject).invoke(null, new Object[] { paramSslCertificate, paramString });
        return paramSslCertificate;
        i += 1;
      }
      paramSslCertificate = null;
    } while (paramSslCertificate != null);
    return "null";
  }
  
  @CalledByNative
  private void handleJsCustomConfirm(String paramString1, String paramString2, int paramInt)
  {
    JsResultHandler localJsResultHandler = new JsResultHandler(this, paramInt);
    if ((!TextUtils.isEmpty(paramString2)) && (this.mContentsClientExtension != null))
    {
      if (paramString2.equals("audio_auto_play_confirm"))
      {
        this.mContentsClientExtension.handleAudioAutoPlayPrompt(paramString1, localJsResultHandler);
        return;
      }
      if (paramString2.equals("geolocation_authorized_status_confirm"))
      {
        this.mContentsClientExtension.handleGeolocationAuthorizedStatus(paramString1, localJsResultHandler);
        return;
      }
      if (paramString2.equals("camera_open_status_confirm"))
      {
        this.mContentsClientExtension.handleCameraOpenAuthorized(paramString1, localJsResultHandler);
        return;
      }
      if (!paramString2.equals("vibration_authorized_status_confirm")) {}
    }
    try
    {
      this.mContentsClientExtension.handleVibrationAuthorized(paramString1, localJsResultHandler);
      return;
    }
    catch (Throwable paramString1)
    {
      for (;;) {}
    }
    localJsResultHandler.confirm();
    return;
    localJsResultHandler.cancel();
  }
  
  @CalledByNative
  private boolean shouldOverrideUrlLoading(String paramString1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt, String paramString2)
  {
    return this.mContentsClientExtension.shouldIgnoreNavigation(this.mContext, paramString1, paramBoolean3, paramBoolean1, paramBoolean2, paramInt, paramString2);
  }
  
  public void setAwContentsClientExtension(AwContentsClientExtension paramAwContentsClientExtension)
  {
    this.mContentsClientExtension = paramAwContentsClientExtension;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\TencentAwContentsClientBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */