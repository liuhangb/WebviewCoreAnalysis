package org.chromium.android_webview;

import android.content.Context;
import android.net.http.SslError;
import android.os.Handler;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.x500.X500Principal;
import org.chromium.base.Callback;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
public class AwContentsClientBridge
{
  private static final String TAG = "AwContentsClientBridge";
  private AwContentsClient mClient;
  private Context mContext;
  private final ClientCertLookupTable mLookupTable;
  private long mNativeContentsClientBridge;
  
  public AwContentsClientBridge(Context paramContext, AwContentsClient paramAwContentsClient, ClientCertLookupTable paramClientCertLookupTable)
  {
    this.mContext = paramContext;
    this.mClient = paramAwContentsClient;
    this.mLookupTable = paramClientCertLookupTable;
  }
  
  protected AwContentsClientBridge(ClientCertLookupTable paramClientCertLookupTable)
  {
    this.mLookupTable = paramClientCertLookupTable;
  }
  
  private boolean allowCertificateError(int paramInt1, final byte[] paramArrayOfByte, final String paramString, final int paramInt2)
  {
    paramArrayOfByte = SslUtil.getCertificateFromDerBytes(paramArrayOfByte);
    if (paramArrayOfByte == null) {
      return false;
    }
    paramArrayOfByte = SslUtil.sslErrorFromNetErrorCode(paramInt1, paramArrayOfByte, paramString);
    paramString = new Callback()
    {
      public void onResult(final Boolean paramAnonymousBoolean)
      {
        ThreadUtils.runOnUiThread(new Runnable()
        {
          public void run()
          {
            AwContentsClientBridge.1.this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClientBridge.proceedSslError(paramAnonymousBoolean.booleanValue(), AwContentsClientBridge.1.this.jdField_a_of_type_Int);
          }
        });
      }
    };
    new Handler().post(new Runnable()
    {
      public void run()
      {
        AwContentsClientBridge.this.mClient.onReceivedSslError(paramString, paramArrayOfByte);
      }
    });
    return true;
  }
  
  @CalledByNative
  private void handleJsAlert(final String paramString1, final String paramString2, final int paramInt)
  {
    new Handler().post(new Runnable()
    {
      public void run()
      {
        JsResultHandler localJsResultHandler = new JsResultHandler(AwContentsClientBridge.this, paramInt);
        AwContentsClientBridge.this.mClient.handleJsAlert(paramString1, paramString2, localJsResultHandler);
      }
    });
  }
  
  @CalledByNative
  private void handleJsBeforeUnload(final String paramString1, final String paramString2, final int paramInt)
  {
    new Handler().post(new Runnable()
    {
      public void run()
      {
        JsResultHandler localJsResultHandler = new JsResultHandler(AwContentsClientBridge.this, paramInt);
        AwContentsClientBridge.this.mClient.handleJsBeforeUnload(paramString1, paramString2, localJsResultHandler);
      }
    });
  }
  
  @CalledByNative
  private void handleJsConfirm(final String paramString1, final String paramString2, final int paramInt)
  {
    new Handler().post(new Runnable()
    {
      public void run()
      {
        JsResultHandler localJsResultHandler = new JsResultHandler(AwContentsClientBridge.this, paramInt);
        AwContentsClientBridge.this.mClient.handleJsConfirm(paramString1, paramString2, localJsResultHandler);
      }
    });
  }
  
  @CalledByNative
  private void handleJsPrompt(final String paramString1, final String paramString2, final String paramString3, final int paramInt)
  {
    new Handler().post(new Runnable()
    {
      public void run()
      {
        JsResultHandler localJsResultHandler = new JsResultHandler(AwContentsClientBridge.this, paramInt);
        AwContentsClientBridge.this.mClient.handleJsPrompt(paramString1, paramString2, paramString3, localJsResultHandler);
      }
    });
  }
  
  private native void nativeCancelJsResult(long paramLong, int paramInt);
  
  private native void nativeConfirmJsResult(long paramLong, int paramInt, String paramString);
  
  private native void nativeProceedSslError(long paramLong, boolean paramBoolean, int paramInt);
  
  private native void nativeProvideClientCertificateResponse(long paramLong, int paramInt, byte[][] paramArrayOfByte, PrivateKey paramPrivateKey);
  
  private native void nativeTakeSafeBrowsingAction(long paramLong, int paramInt1, boolean paramBoolean, int paramInt2);
  
  @CalledByNative
  private void newDownload(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong)
  {
    this.mClient.getCallbackHelper().postOnDownloadStart(paramString1, paramString2, paramString3, paramString4, paramLong);
  }
  
  @CalledByNative
  private void newLoginRequest(String paramString1, String paramString2, String paramString3)
  {
    this.mClient.getCallbackHelper().postOnReceivedLoginRequest(paramString1, paramString2, paramString3);
  }
  
  @CalledByNative
  private void onReceivedError(String paramString1, boolean paramBoolean1, boolean paramBoolean2, String paramString2, String[] paramArrayOfString1, String[] paramArrayOfString2, int paramInt, String paramString3, boolean paramBoolean3)
  {
    AwContentsClient.AwWebResourceRequest localAwWebResourceRequest = new AwContentsClient.AwWebResourceRequest();
    localAwWebResourceRequest.url = paramString1;
    localAwWebResourceRequest.isMainFrame = paramBoolean1;
    localAwWebResourceRequest.hasUserGesture = paramBoolean2;
    localAwWebResourceRequest.method = paramString2;
    localAwWebResourceRequest.requestHeaders = new HashMap(paramArrayOfString1.length);
    int j = 0;
    int i = 0;
    while (i < paramArrayOfString1.length)
    {
      localAwWebResourceRequest.requestHeaders.put(paramArrayOfString1[i], paramArrayOfString2[i]);
      i += 1;
    }
    paramString1 = new AwContentsClient.AwWebResourceError();
    paramString1.errorCode = paramInt;
    paramString1.description = paramString3;
    paramString2 = AwContentsStatics.getUnreachableWebDataUrl();
    paramInt = j;
    if (paramString2 != null)
    {
      paramInt = j;
      if (paramString2.equals(localAwWebResourceRequest.url)) {
        paramInt = 1;
      }
    }
    if (((paramInt == 0) && (paramString1.errorCode != -3)) || (paramBoolean3))
    {
      if (paramBoolean3) {
        paramString1.errorCode = -16;
      } else {
        paramString1.errorCode = ErrorCodeConversionHelper.a(paramString1.errorCode);
      }
      this.mClient.getCallbackHelper().postOnReceivedError(localAwWebResourceRequest, paramString1);
      if (localAwWebResourceRequest.isMainFrame) {
        this.mClient.getCallbackHelper().postOnPageFinished(localAwWebResourceRequest.url);
      }
    }
  }
  
  @CalledByNative
  private void onReceivedHttpError(String paramString1, boolean paramBoolean1, boolean paramBoolean2, String paramString2, String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString3, String paramString4, int paramInt, String paramString5, String[] paramArrayOfString3, String[] paramArrayOfString4)
  {
    AwContentsClient.AwWebResourceRequest localAwWebResourceRequest = new AwContentsClient.AwWebResourceRequest();
    localAwWebResourceRequest.url = paramString1;
    localAwWebResourceRequest.isMainFrame = paramBoolean1;
    localAwWebResourceRequest.hasUserGesture = paramBoolean2;
    localAwWebResourceRequest.method = paramString2;
    localAwWebResourceRequest.requestHeaders = new HashMap(paramArrayOfString1.length);
    int j = 0;
    int i = 0;
    while (i < paramArrayOfString1.length)
    {
      localAwWebResourceRequest.requestHeaders.put(paramArrayOfString1[i], paramArrayOfString2[i]);
      i += 1;
    }
    paramArrayOfString1 = new HashMap(paramArrayOfString3.length);
    i = j;
    while (i < paramArrayOfString3.length)
    {
      if (!paramArrayOfString1.containsKey(paramArrayOfString3[i]))
      {
        paramArrayOfString1.put(paramArrayOfString3[i], paramArrayOfString4[i]);
      }
      else if (!paramArrayOfString4[i].isEmpty())
      {
        paramString2 = (String)paramArrayOfString1.get(paramArrayOfString3[i]);
        paramString1 = paramString2;
        if (!paramString2.isEmpty())
        {
          paramString1 = new StringBuilder();
          paramString1.append(paramString2);
          paramString1.append(", ");
          paramString1 = paramString1.toString();
        }
        paramString2 = paramArrayOfString3[i];
        paramArrayOfString2 = new StringBuilder();
        paramArrayOfString2.append(paramString1);
        paramArrayOfString2.append(paramArrayOfString4[i]);
        paramArrayOfString1.put(paramString2, paramArrayOfString2.toString());
      }
      i += 1;
    }
    paramString1 = new AwWebResourceResponse(paramString3, paramString4, null, paramInt, paramString5, paramArrayOfString1);
    this.mClient.getCallbackHelper().postOnReceivedHttpError(localAwWebResourceRequest, paramString1);
  }
  
  @CalledByNative
  private void setNativeContentsClientBridge(long paramLong)
  {
    this.mNativeContentsClientBridge = paramLong;
  }
  
  void cancelJsResult(int paramInt)
  {
    long l = this.mNativeContentsClientBridge;
    if (l == 0L) {
      return;
    }
    nativeCancelJsResult(l, paramInt);
  }
  
  void confirmJsResult(int paramInt, String paramString)
  {
    long l = this.mNativeContentsClientBridge;
    if (l == 0L) {
      return;
    }
    nativeConfirmJsResult(l, paramInt, paramString);
  }
  
  @CalledByNative
  public void onSafeBrowsingHit(String paramString1, boolean paramBoolean1, boolean paramBoolean2, String paramString2, String[] paramArrayOfString1, String[] paramArrayOfString2, int paramInt1, final int paramInt2)
  {
    AwContentsClient.AwWebResourceRequest localAwWebResourceRequest = new AwContentsClient.AwWebResourceRequest();
    localAwWebResourceRequest.url = paramString1;
    localAwWebResourceRequest.isMainFrame = paramBoolean1;
    localAwWebResourceRequest.hasUserGesture = paramBoolean2;
    localAwWebResourceRequest.method = paramString2;
    localAwWebResourceRequest.requestHeaders = new HashMap(paramArrayOfString1.length);
    int i = 0;
    while (i < paramArrayOfString1.length)
    {
      localAwWebResourceRequest.requestHeaders.put(paramArrayOfString1[i], paramArrayOfString2[i]);
      i += 1;
    }
    paramString1 = new Callback()
    {
      public void onResult(final AwSafeBrowsingResponse paramAnonymousAwSafeBrowsingResponse)
      {
        ThreadUtils.runOnUiThread(new Runnable()
        {
          public void run()
          {
            AwContentsClientBridge.7.this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClientBridge.nativeTakeSafeBrowsingAction(AwContentsClientBridge.7.this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContentsClientBridge.mNativeContentsClientBridge, paramAnonymousAwSafeBrowsingResponse.action(), paramAnonymousAwSafeBrowsingResponse.reporting(), AwContentsClientBridge.7.this.jdField_a_of_type_Int);
          }
        });
      }
    };
    this.mClient.getCallbackHelper().postOnSafeBrowsingHit(localAwWebResourceRequest, AwSafeBrowsingConversionHelper.convertThreatType(paramInt1), paramString1);
  }
  
  public void proceedSslError(boolean paramBoolean, int paramInt)
  {
    long l = this.mNativeContentsClientBridge;
    if (l == 0L) {
      return;
    }
    nativeProceedSslError(l, paramBoolean, paramInt);
  }
  
  @CalledByNative
  protected void selectClientCertificate(int paramInt1, String[] paramArrayOfString, byte[][] paramArrayOfByte, String paramString, int paramInt2)
  {
    ClientCertLookupTable.Cert localCert = this.mLookupTable.getCertData(paramString, paramInt2);
    boolean bool = this.mLookupTable.isDenied(paramString, paramInt2);
    X500Principal[] arrayOfX500Principal = null;
    if (bool)
    {
      nativeProvideClientCertificateResponse(this.mNativeContentsClientBridge, paramInt1, (byte[][])null, null);
      return;
    }
    if (localCert != null)
    {
      nativeProvideClientCertificateResponse(this.mNativeContentsClientBridge, paramInt1, localCert.jdField_a_of_type_Array2dOfByte, localCert.jdField_a_of_type_JavaSecurityPrivateKey);
      return;
    }
    int i;
    if (paramArrayOfByte.length > 0)
    {
      arrayOfX500Principal = new X500Principal[paramArrayOfByte.length];
      i = 0;
    }
    for (;;)
    {
      if (i >= paramArrayOfByte.length) {
        break label136;
      }
      try
      {
        arrayOfX500Principal[i] = new X500Principal(paramArrayOfByte[i]);
        i += 1;
      }
      catch (IllegalArgumentException paramArrayOfString)
      {
        for (;;) {}
      }
    }
    nativeProvideClientCertificateResponse(this.mNativeContentsClientBridge, paramInt1, (byte[][])null, null);
    return;
    label136:
    paramArrayOfByte = new ClientCertificateRequestCallback(paramInt1, paramString, paramInt2);
    this.mClient.onReceivedClientCertRequest(paramArrayOfByte, paramArrayOfString, arrayOfX500Principal, paramString, paramInt2);
  }
  
  public class ClientCertificateRequestCallback
  {
    private final int jdField_a_of_type_Int;
    private final String jdField_a_of_type_JavaLangString;
    private boolean jdField_a_of_type_Boolean;
    private final int b;
    
    public ClientCertificateRequestCallback(int paramInt1, String paramString, int paramInt2)
    {
      this.jdField_a_of_type_Int = paramInt1;
      this.jdField_a_of_type_JavaLangString = paramString;
      this.b = paramInt2;
    }
    
    private void a()
    {
      c();
      a(null, (byte[][])null);
    }
    
    private void a(PrivateKey paramPrivateKey, X509Certificate[] paramArrayOfX509Certificate)
    {
      c();
      byte[][] arrayOfByte;
      int i;
      if ((paramPrivateKey != null) && (paramArrayOfX509Certificate != null) && (paramArrayOfX509Certificate.length != 0))
      {
        arrayOfByte = new byte[paramArrayOfX509Certificate.length][];
        i = 0;
      }
      try
      {
        while (i < paramArrayOfX509Certificate.length)
        {
          arrayOfByte[i] = paramArrayOfX509Certificate[i].getEncoded();
          i += 1;
        }
        AwContentsClientBridge.this.mLookupTable.allow(this.jdField_a_of_type_JavaLangString, this.b, paramPrivateKey, arrayOfByte);
        a(paramPrivateKey, arrayOfByte);
        return;
      }
      catch (CertificateEncodingException paramPrivateKey)
      {
        for (;;) {}
      }
      a(null, (byte[][])null);
      return;
      a(null, (byte[][])null);
    }
    
    private void a(PrivateKey paramPrivateKey, byte[][] paramArrayOfByte)
    {
      if (AwContentsClientBridge.this.mNativeContentsClientBridge == 0L) {
        return;
      }
      AwContentsClientBridge localAwContentsClientBridge = AwContentsClientBridge.this;
      localAwContentsClientBridge.nativeProvideClientCertificateResponse(localAwContentsClientBridge.mNativeContentsClientBridge, this.jdField_a_of_type_Int, paramArrayOfByte, paramPrivateKey);
    }
    
    private void b()
    {
      c();
      AwContentsClientBridge.this.mLookupTable.deny(this.jdField_a_of_type_JavaLangString, this.b);
      a(null, (byte[][])null);
    }
    
    private void c()
    {
      if (!this.jdField_a_of_type_Boolean)
      {
        this.jdField_a_of_type_Boolean = true;
        return;
      }
      throw new IllegalStateException("The callback was already called.");
    }
    
    public void cancel()
    {
      ThreadUtils.runOnUiThread(new Runnable()
      {
        public void run()
        {
          AwContentsClientBridge.ClientCertificateRequestCallback.b(AwContentsClientBridge.ClientCertificateRequestCallback.this);
        }
      });
    }
    
    public void ignore()
    {
      ThreadUtils.runOnUiThread(new Runnable()
      {
        public void run()
        {
          AwContentsClientBridge.ClientCertificateRequestCallback.a(AwContentsClientBridge.ClientCertificateRequestCallback.this);
        }
      });
    }
    
    public void proceed(final PrivateKey paramPrivateKey, final X509Certificate[] paramArrayOfX509Certificate)
    {
      ThreadUtils.runOnUiThread(new Runnable()
      {
        public void run()
        {
          AwContentsClientBridge.ClientCertificateRequestCallback.a(AwContentsClientBridge.ClientCertificateRequestCallback.this, paramPrivateKey, paramArrayOfX509Certificate);
        }
      });
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwContentsClientBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */