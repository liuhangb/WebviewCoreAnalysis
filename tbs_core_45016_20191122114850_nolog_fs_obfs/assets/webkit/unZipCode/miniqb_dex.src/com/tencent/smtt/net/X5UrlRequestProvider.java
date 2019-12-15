package com.tencent.smtt.net;

import android.util.Pair;
import com.tencent.smtt.export.external.interfaces.UrlRequest;
import com.tencent.smtt.export.external.interfaces.UrlRequest.Callback;
import com.tencent.smtt.export.external.interfaces.X5netException;
import com.tencent.smtt.util.X5Log;
import java.nio.ByteBuffer;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import javax.annotation.concurrent.GuardedBy;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.NativeClassQualifiedName;
import org.chromium.base.annotations.UsedByReflection;

@JNINamespace("tencent")
public class X5UrlRequestProvider
  extends UrlRequest
{
  private static final String TAG = "X5UrlRequestProvider";
  private final l.a mCallback;
  private final boolean mDisableCache;
  private X5netException mException;
  private final Executor mExecutor;
  private String mInitialBody;
  private String mInitialMethod;
  private String mInitialUrl;
  private b mOnReadCompletedTask;
  @GuardedBy("mUrlRequestProviderLock")
  private boolean mPreConnected = false;
  private final int mPriority;
  private long mReceivedByteCountFromRedirects;
  private final a mRequestHeaders = new a(null);
  private UrlResponseInfoImpl mResponseInfo;
  @GuardedBy("mUrlRequestProviderLock")
  private boolean mStarted = false;
  @GuardedBy("mUrlRequestProviderLock")
  private final List<String> mUrlChain = new ArrayList();
  @GuardedBy("mUrlRequestProviderLock")
  private long mUrlRequestProvider;
  private final Object mUrlRequestProviderLock = new Object();
  @GuardedBy("mUrlRequestProviderLock")
  private boolean mWaitingOnRead = false;
  @GuardedBy("mUrlRequestProviderLock")
  private boolean mWaitingOnRedirect = false;
  private boolean x5_get_context_error_;
  
  X5UrlRequestProvider(String paramString1, int paramInt, UrlRequest.Callback paramCallback, Executor paramExecutor, boolean paramBoolean, String paramString2, ArrayList<Pair<String, String>> paramArrayList, String paramString3)
  {
    this.mCallback = new l.a(paramCallback);
    this.mExecutor = paramExecutor;
    this.mInitialUrl = paramString1;
    this.mPriority = paramInt;
    this.mDisableCache = paramBoolean;
    this.mInitialMethod = paramString2;
    this.mInitialBody = paramString3;
    paramString1 = paramArrayList.iterator();
    while (paramString1.hasNext())
    {
      paramCallback = (Pair)paramString1.next();
      this.mRequestHeaders.add(new AbstractMap.SimpleImmutableEntry(paramCallback.first, paramCallback.second));
    }
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static X5UrlRequestProvider GetX5UrlRequestProvider(String paramString1, int paramInt, UrlRequest.Callback paramCallback, Executor paramExecutor, boolean paramBoolean, String paramString2, ArrayList<Pair<String, String>> paramArrayList, String paramString3)
  {
    X5Log.d("X5UrlRequestProvider", "call GetX5UrlRequestProvider");
    return new X5UrlRequestProvider(paramString1, paramInt, paramCallback, paramExecutor, paramBoolean, paramString2, paramArrayList, paramString3);
  }
  
  private void checkNotStarted()
  {
    synchronized (this.mUrlRequestProviderLock)
    {
      if ((!this.mStarted) && (!isDoneLocked())) {
        return;
      }
      throw new IllegalStateException("Request is already started.");
    }
  }
  
  private void destroyRequestProvider(boolean paramBoolean)
  {
    synchronized (this.mUrlRequestProviderLock)
    {
      if (this.mUrlRequestProvider == 0L) {
        return;
      }
      nativeDestroy(this.mUrlRequestProvider, paramBoolean);
      this.mUrlRequestProvider = 0L;
      return;
    }
  }
  
  private void failWithException(final X5netException paramX5netException)
  {
    this.mException = paramX5netException;
    synchronized (this.mUrlRequestProviderLock)
    {
      if (isDoneLocked()) {
        return;
      }
      destroyRequestProvider(false);
      paramX5netException = new Runnable()
      {
        public void run()
        {
          try
          {
            X5UrlRequestProvider.this.mCallback.a(X5UrlRequestProvider.this, X5UrlRequestProvider.this.mResponseInfo, paramX5netException);
            return;
          }
          catch (Exception localException)
          {
            X5Log.d("X5UrlRequestProvider", "Exception in onFailed method", localException);
          }
        }
      };
      try
      {
        this.mExecutor.execute(paramX5netException);
        return;
      }
      catch (RejectedExecutionException paramX5netException)
      {
        X5Log.d("X5UrlRequestProvider", "Exception posting task to executor", paramX5netException);
        return;
      }
    }
  }
  
  @GuardedBy("mUrlRequestProviderLock")
  private boolean isDoneLocked()
  {
    return (this.mStarted) && (this.mUrlRequestProvider == 0L);
  }
  
  private int mapUrlRequestErrorToApiErrorCode(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Unknown error code: ");
    localStringBuilder.append(paramInt);
    X5Log.d("X5UrlRequestProvider", localStringBuilder.toString());
    return paramInt;
  }
  
  @NativeClassQualifiedName("X5UrlRequestProvider")
  private native boolean nativeAddRequestHeader(long paramLong, String paramString1, String paramString2);
  
  private native long nativeCreateX5UrlRequestProvider(String paramString, int paramInt, boolean paramBoolean);
  
  @NativeClassQualifiedName("X5UrlRequestProvider")
  private native void nativeDestroy(long paramLong, boolean paramBoolean);
  
  @NativeClassQualifiedName("X5UrlRequestProvider")
  private native void nativeFollowDeferredRedirect(long paramLong);
  
  @NativeClassQualifiedName("X5UrlRequestProvider")
  private native void nativePreConnect(long paramLong);
  
  @NativeClassQualifiedName("X5UrlRequestProvider")
  private native boolean nativeReadData(long paramLong, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2);
  
  @NativeClassQualifiedName("X5UrlRequestProvider")
  private native boolean nativeSetHttpBody(long paramLong, String paramString);
  
  @NativeClassQualifiedName("X5UrlRequestProvider")
  private native boolean nativeSetHttpMethod(long paramLong, String paramString);
  
  @NativeClassQualifiedName("X5UrlRequestProvider")
  private native void nativeStart(long paramLong);
  
  @NativeClassQualifiedName("X5UrlRequestProvider")
  private native void nativeStartWithPreConnectedRequest(long paramLong, String paramString);
  
  private void onCallbackException(Exception paramException)
  {
    a locala = new a("Exception received from UrlRequest.Callback", paramException);
    X5Log.d("X5UrlRequestProvider", "Exception in CalledByNative method", paramException);
    failWithException(locala);
  }
  
  @CalledByNative
  private void onCanceled()
  {
    X5Log.d("X5UrlRequestProvider", "call java onCanceled");
    postTaskToExecutor(new Runnable()
    {
      public void run()
      {
        try
        {
          X5UrlRequestProvider.this.mCallback.c(X5UrlRequestProvider.this, X5UrlRequestProvider.this.mResponseInfo);
          return;
        }
        catch (Exception localException)
        {
          X5Log.d("X5UrlRequestProvider", "Exception in onCanceled method", localException);
        }
      }
    });
  }
  
  @CalledByNative
  private void onError(int paramInt1, int paramInt2, int paramInt3, String paramString, long paramLong)
  {
    Object localObject = this.mResponseInfo;
    if (localObject != null) {
      ((UrlResponseInfoImpl)localObject).setReceivedByteCount(this.mReceivedByteCountFromRedirects + paramLong);
    }
    if (paramInt1 == 10)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Exception in X5UrlRequest: ");
      ((StringBuilder)localObject).append(paramString);
      failWithException(new j(((StringBuilder)localObject).toString(), paramInt2, paramInt3));
      return;
    }
    paramInt1 = mapUrlRequestErrorToApiErrorCode(paramInt1);
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Exception in X5UrlRequest: ");
    ((StringBuilder)localObject).append(paramString);
    failWithException(new d(((StringBuilder)localObject).toString(), paramInt1, paramInt2));
  }
  
  @CalledByNative
  private void onGetContextError()
  {
    this.x5_get_context_error_ = true;
    failWithException(new n("Exception in X5UrlRequest,need X5 environment", null));
  }
  
  @CalledByNative
  private void onReadCompleted(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, long paramLong)
  {
    X5Log.d("X5UrlRequestProvider", "call java onReadCompleted");
    this.mResponseInfo.setReceivedByteCount(this.mReceivedByteCountFromRedirects + paramLong);
    if ((paramByteBuffer.position() == paramInt2) && (paramByteBuffer.limit() == paramInt3))
    {
      if (this.mOnReadCompletedTask == null) {
        this.mOnReadCompletedTask = new b(null);
      }
      paramByteBuffer.position(paramInt2 + paramInt1);
      b localb = this.mOnReadCompletedTask;
      localb.a = paramByteBuffer;
      postTaskToExecutor(localb);
      return;
    }
    failWithException(new n("ByteBuffer modified externally during read", null));
  }
  
  @CalledByNative
  private void onRedirectReceived(final String paramString1, int paramInt, final String paramString2, String[] paramArrayOfString, boolean paramBoolean, String paramString3, String paramString4, long paramLong)
  {
    X5Log.d("X5UrlRequestProvider", "call java onRedirectReceived");
    paramString2 = prepareResponseInfoOnNetworkThread(paramInt, paramString2, paramArrayOfString, paramBoolean, paramString3, paramString4);
    this.mReceivedByteCountFromRedirects += paramLong;
    paramString2.setReceivedByteCount(this.mReceivedByteCountFromRedirects);
    this.mUrlChain.add(paramString1);
    postTaskToExecutor(new Runnable()
    {
      public void run()
      {
        ??? = new StringBuilder();
        ((StringBuilder)???).append("onRedirectReceived-callback currentThreadName=");
        ((StringBuilder)???).append(Thread.currentThread().getName());
        X5Log.d("X5UrlRequestProvider", ((StringBuilder)???).toString());
        synchronized (X5UrlRequestProvider.this.mUrlRequestProviderLock)
        {
          if (X5UrlRequestProvider.this.isDoneLocked()) {
            return;
          }
          X5UrlRequestProvider.access$702(X5UrlRequestProvider.this, true);
          try
          {
            X5UrlRequestProvider.this.mCallback.a(X5UrlRequestProvider.this, paramString2, paramString1);
            return;
          }
          catch (Exception localException)
          {
            X5UrlRequestProvider.this.onCallbackException(localException);
            return;
          }
        }
      }
    });
  }
  
  @CalledByNative
  private void onResponseStarted(int paramInt, String paramString1, String[] paramArrayOfString, boolean paramBoolean, String paramString2, String paramString3)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("call java onResponseStarted:negotiatedProtocol:");
    localStringBuilder.append(paramString2);
    X5Log.d("X5UrlRequestProvider", localStringBuilder.toString());
    this.mResponseInfo = prepareResponseInfoOnNetworkThread(paramInt, paramString1, paramArrayOfString, paramBoolean, paramString2, paramString3);
    paramString1 = new Runnable()
    {
      public void run()
      {
        ??? = new StringBuilder();
        ((StringBuilder)???).append("onResponseStarted-callback currentThreadName=");
        ((StringBuilder)???).append(Thread.currentThread().getName());
        X5Log.d("X5UrlRequestProvider", ((StringBuilder)???).toString());
        synchronized (X5UrlRequestProvider.this.mUrlRequestProviderLock)
        {
          if (X5UrlRequestProvider.this.isDoneLocked()) {
            return;
          }
          X5UrlRequestProvider.access$302(X5UrlRequestProvider.this, true);
          try
          {
            X5UrlRequestProvider.this.mCallback.a(X5UrlRequestProvider.this, X5UrlRequestProvider.this.mResponseInfo);
            return;
          }
          catch (Exception localException)
          {
            X5UrlRequestProvider.this.onCallbackException(localException);
            return;
          }
        }
      }
    };
    try
    {
      this.mExecutor.execute(paramString1);
      return;
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
  }
  
  @CalledByNative
  private void onSucceeded(long paramLong)
  {
    X5Log.d("X5UrlRequestProvider", "call java onSucceeded");
    this.mResponseInfo.setReceivedByteCount(this.mReceivedByteCountFromRedirects + paramLong);
    postTaskToExecutor(new Runnable()
    {
      public void run()
      {
        synchronized (X5UrlRequestProvider.this.mUrlRequestProviderLock)
        {
          if (X5UrlRequestProvider.this.isDoneLocked()) {
            return;
          }
          X5UrlRequestProvider.this.destroyRequestProvider(false);
          try
          {
            X5UrlRequestProvider.this.mCallback.b(X5UrlRequestProvider.this, X5UrlRequestProvider.this.mResponseInfo);
            return;
          }
          catch (Exception localException)
          {
            X5Log.d("X5UrlRequestProvider", "Exception in onComplete method", localException);
            return;
          }
        }
      }
    });
  }
  
  private void postTaskToExecutor(Runnable paramRunnable)
  {
    try
    {
      this.mExecutor.execute(paramRunnable);
      return;
    }
    catch (RejectedExecutionException paramRunnable)
    {
      X5Log.e("X5UrlRequestProvider", "Exception posting task to executor", paramRunnable);
      failWithException(new n("Exception posting task to executor", paramRunnable));
    }
  }
  
  private void preConnectInternalLocked()
  {
    nativePreConnect(this.mUrlRequestProvider);
  }
  
  private UrlResponseInfoImpl prepareResponseInfoOnNetworkThread(int paramInt, String paramString1, String[] paramArrayOfString, boolean paramBoolean, String paramString2, String paramString3)
  {
    a locala = new a(null);
    int i = 0;
    while (i < paramArrayOfString.length)
    {
      locala.add(new AbstractMap.SimpleImmutableEntry(paramArrayOfString[i], paramArrayOfString[(i + 1)]));
      i += 2;
    }
    return new UrlResponseInfoImpl(new ArrayList(this.mUrlChain), paramInt, paramString1, locala, paramBoolean, paramString2, paramString3);
  }
  
  private void startInternalLocked()
  {
    synchronized (this.mUrlRequestProviderLock)
    {
      nativeStart(this.mUrlRequestProvider);
      return;
    }
  }
  
  public void cancel()
  {
    synchronized (this.mUrlRequestProviderLock)
    {
      if ((!isDoneLocked()) && (this.mStarted))
      {
        destroyRequestProvider(true);
        return;
      }
      return;
    }
  }
  
  public void followRedirect()
  {
    synchronized (this.mUrlRequestProviderLock)
    {
      if (this.mWaitingOnRedirect)
      {
        this.mWaitingOnRedirect = false;
        if (isDoneLocked()) {
          return;
        }
        nativeFollowDeferredRedirect(this.mUrlRequestProvider);
        return;
      }
      throw new IllegalStateException("No redirect to follow.");
    }
  }
  
  public boolean isDone()
  {
    synchronized (this.mUrlRequestProviderLock)
    {
      boolean bool = isDoneLocked();
      return bool;
    }
  }
  
  public void preconnect()
    throws Exception
  {
    synchronized (this.mUrlRequestProviderLock)
    {
      checkNotStarted();
      try
      {
        this.x5_get_context_error_ = false;
        this.mUrlRequestProvider = nativeCreateX5UrlRequestProvider(this.mInitialUrl, this.mPriority, this.mDisableCache);
        boolean bool = this.x5_get_context_error_;
        if (bool) {
          return;
        }
        Object localObject2;
        if ((this.mInitialMethod != null) && (!nativeSetHttpMethod(this.mUrlRequestProvider, this.mInitialMethod)))
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("Invalid http method ");
          ((StringBuilder)localObject2).append(this.mInitialMethod);
          throw new IllegalArgumentException(((StringBuilder)localObject2).toString());
        }
        if ((this.mInitialBody != null) && (!nativeSetHttpBody(this.mUrlRequestProvider, this.mInitialBody)))
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("Invalid http body ");
          ((StringBuilder)localObject2).append(this.mInitialBody);
          throw new IllegalArgumentException(((StringBuilder)localObject2).toString());
        }
        Object localObject4 = this.mRequestHeaders.iterator();
        while (((Iterator)localObject4).hasNext())
        {
          localObject2 = (Map.Entry)((Iterator)localObject4).next();
          if (!nativeAddRequestHeader(this.mUrlRequestProvider, (String)((Map.Entry)localObject2).getKey(), (String)((Map.Entry)localObject2).getValue()))
          {
            localObject4 = new StringBuilder();
            ((StringBuilder)localObject4).append("Invalid header ");
            ((StringBuilder)localObject4).append((String)((Map.Entry)localObject2).getKey());
            ((StringBuilder)localObject4).append("=");
            ((StringBuilder)localObject4).append((String)((Map.Entry)localObject2).getValue());
            throw new IllegalArgumentException(((StringBuilder)localObject4).toString());
          }
        }
        this.mPreConnected = true;
        this.mStarted = true;
        preConnectInternalLocked();
        return;
      }
      catch (RuntimeException localRuntimeException)
      {
        destroyRequestProvider(false);
        throw localRuntimeException;
      }
    }
  }
  
  public void read(ByteBuffer paramByteBuffer)
  {
    i.b(paramByteBuffer);
    i.a(paramByteBuffer);
    synchronized (this.mUrlRequestProviderLock)
    {
      if (this.mWaitingOnRead)
      {
        this.mWaitingOnRead = false;
        if (isDoneLocked()) {
          return;
        }
        if (nativeReadData(this.mUrlRequestProvider, paramByteBuffer, paramByteBuffer.position(), paramByteBuffer.limit())) {
          return;
        }
        this.mWaitingOnRead = true;
        throw new IllegalArgumentException("Unable to call native read");
      }
      throw new IllegalStateException("Unexpected read attempt.");
    }
  }
  
  public void start()
  {
    synchronized (this.mUrlRequestProviderLock)
    {
      checkNotStarted();
      try
      {
        this.x5_get_context_error_ = false;
        this.mUrlRequestProvider = nativeCreateX5UrlRequestProvider(this.mInitialUrl, this.mPriority, this.mDisableCache);
        boolean bool = this.x5_get_context_error_;
        if (bool) {
          return;
        }
        Object localObject2;
        if ((this.mInitialMethod != null) && (!nativeSetHttpMethod(this.mUrlRequestProvider, this.mInitialMethod)))
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("Invalid http method ");
          ((StringBuilder)localObject2).append(this.mInitialMethod);
          throw new IllegalArgumentException(((StringBuilder)localObject2).toString());
        }
        if ((this.mInitialBody != null) && (!nativeSetHttpBody(this.mUrlRequestProvider, this.mInitialBody)))
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("Invalid http body ");
          ((StringBuilder)localObject2).append(this.mInitialBody);
          throw new IllegalArgumentException(((StringBuilder)localObject2).toString());
        }
        Object localObject4 = this.mRequestHeaders.iterator();
        while (((Iterator)localObject4).hasNext())
        {
          localObject2 = (Map.Entry)((Iterator)localObject4).next();
          if (!nativeAddRequestHeader(this.mUrlRequestProvider, (String)((Map.Entry)localObject2).getKey(), (String)((Map.Entry)localObject2).getValue()))
          {
            localObject4 = new StringBuilder();
            ((StringBuilder)localObject4).append("Invalid header ");
            ((StringBuilder)localObject4).append((String)((Map.Entry)localObject2).getKey());
            ((StringBuilder)localObject4).append("=");
            ((StringBuilder)localObject4).append((String)((Map.Entry)localObject2).getValue());
            throw new IllegalArgumentException(((StringBuilder)localObject4).toString());
          }
        }
        this.mStarted = true;
        startInternalLocked();
        return;
      }
      catch (RuntimeException localRuntimeException)
      {
        destroyRequestProvider(false);
        throw localRuntimeException;
      }
    }
  }
  
  public void startWithPreConnectedRequest(String paramString)
  {
    synchronized (this.mUrlRequestProviderLock)
    {
      if (!this.mPreConnected) {
        return;
      }
      nativeStartWithPreConnectedRequest(this.mUrlRequestProvider, paramString);
      return;
    }
  }
  
  private static final class a
    extends ArrayList<Map.Entry<String, String>>
  {}
  
  private final class b
    implements Runnable
  {
    ByteBuffer jdField_a_of_type_JavaNioByteBuffer;
    
    private b() {}
    
    public void run()
    {
      ByteBuffer localByteBuffer = this.jdField_a_of_type_JavaNioByteBuffer;
      this.jdField_a_of_type_JavaNioByteBuffer = null;
      try
      {
        synchronized (X5UrlRequestProvider.this.mUrlRequestProviderLock)
        {
          if (X5UrlRequestProvider.this.isDoneLocked()) {
            return;
          }
          X5UrlRequestProvider.access$302(X5UrlRequestProvider.this, true);
          X5UrlRequestProvider.this.mCallback.a(X5UrlRequestProvider.this, X5UrlRequestProvider.this.mResponseInfo, localByteBuffer);
          return;
        }
        return;
      }
      catch (Exception localException)
      {
        X5UrlRequestProvider.this.onCallbackException(localException);
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\X5UrlRequestProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */