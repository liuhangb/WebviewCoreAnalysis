package org.chromium.media;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.media.DeniedByServerException;
import android.media.MediaCrypto;
import android.media.MediaCryptoException;
import android.media.MediaDrm;
import android.media.MediaDrm.KeyRequest;
import android.media.MediaDrm.KeyStatus;
import android.media.MediaDrm.MediaDrmStateException;
import android.media.MediaDrm.OnEventListener;
import android.media.MediaDrm.OnExpirationUpdateListener;
import android.media.MediaDrm.OnKeyStatusChangeListener;
import android.media.MediaDrm.ProvisionRequest;
import android.media.MediaDrmException;
import android.media.NotProvisionedException;
import android.media.UnsupportedSchemeException;
import android.os.Build.VERSION;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.chromium.base.ApiCompatibilityUtils;
import org.chromium.base.Callback;
import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;

@JNINamespace("media")
@SuppressLint({"WrongConstant"})
@TargetApi(19)
@MainDex
public class MediaDrmBridge
{
  private static final UUID jdField_a_of_type_JavaUtilUUID = UUID.fromString("edef8ba9-79d6-4ace-a3c8-27dcd51d21ed");
  private static final byte[] jdField_a_of_type_ArrayOfByte = { 0 };
  private static final byte[] jdField_b_of_type_ArrayOfByte = ApiCompatibilityUtils.getBytesUtf8("unprovision");
  private long jdField_a_of_type_Long;
  private MediaCrypto jdField_a_of_type_AndroidMediaMediaCrypto;
  private MediaDrm jdField_a_of_type_AndroidMediaMediaDrm;
  private ArrayDeque<PendingCreateSessionData> jdField_a_of_type_JavaUtilArrayDeque;
  private SessionEventDeferrer jdField_a_of_type_OrgChromiumMediaMediaDrmBridge$SessionEventDeferrer = null;
  private MediaDrmSessionManager.SessionId jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId;
  private MediaDrmSessionManager jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager;
  private MediaDrmStorageBridge jdField_a_of_type_OrgChromiumMediaMediaDrmStorageBridge;
  private UUID jdField_b_of_type_JavaUtilUUID;
  private boolean jdField_b_of_type_Boolean;
  private boolean c;
  private boolean d = false;
  
  @TargetApi(23)
  private MediaDrmBridge(UUID paramUUID, long paramLong1, long paramLong2)
    throws UnsupportedSchemeException
  {
    this.jdField_b_of_type_JavaUtilUUID = paramUUID;
    this.jdField_a_of_type_AndroidMediaMediaDrm = new MediaDrm(paramUUID);
    this.jdField_a_of_type_Long = paramLong1;
    if ((!jdField_a_of_type_Boolean) && (!a())) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_OrgChromiumMediaMediaDrmStorageBridge = new MediaDrmStorageBridge(paramLong2);
    this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager = new MediaDrmSessionManager(this.jdField_a_of_type_OrgChromiumMediaMediaDrmStorageBridge);
    this.jdField_a_of_type_JavaUtilArrayDeque = new ArrayDeque();
    this.jdField_b_of_type_Boolean = false;
    this.c = false;
    this.jdField_a_of_type_AndroidMediaMediaDrm.setOnEventListener(new EventListener(null));
    if (Build.VERSION.SDK_INT >= 23)
    {
      this.jdField_a_of_type_AndroidMediaMediaDrm.setOnExpirationUpdateListener(new ExpirationUpdateListener(null), null);
      this.jdField_a_of_type_AndroidMediaMediaDrm.setOnKeyStatusChangeListener(new KeyStatusChangeListener(null), null);
    }
    if (b())
    {
      this.jdField_a_of_type_AndroidMediaMediaDrm.setPropertyString("privacyMode", "enable");
      this.jdField_a_of_type_AndroidMediaMediaDrm.setPropertyString("sessionSharing", "enable");
    }
  }
  
  private MediaDrm.KeyRequest a(MediaDrmSessionManager.SessionId paramSessionId, byte[] paramArrayOfByte, String paramString, int paramInt, HashMap<String, String> paramHashMap)
    throws NotProvisionedException
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_AndroidMediaMediaDrm == null)) {
      throw new AssertionError();
    }
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId == null)) {
      throw new AssertionError();
    }
    if ((!jdField_a_of_type_Boolean) && (this.c)) {
      throw new AssertionError();
    }
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    if (paramInt == 3) {}
    try
    {
      paramSessionId = paramSessionId.c();
      break label108;
      paramSessionId = paramSessionId.a();
      label108:
      if ((!jdField_a_of_type_Boolean) && (paramSessionId == null)) {
        throw new AssertionError();
      }
      paramSessionId = this.jdField_a_of_type_AndroidMediaMediaDrm.getKeyRequest(paramSessionId, paramArrayOfByte, paramString, paramInt, paramHashMap);
      return paramSessionId;
    }
    catch (IllegalStateException paramSessionId)
    {
      if ((Build.VERSION.SDK_INT >= 21) && ((paramSessionId instanceof MediaDrm.MediaDrmStateException))) {
        Log.e("cr_media", "MediaDrmStateException fired during getKeyRequest().", new Object[] { paramSessionId });
      }
    }
    return null;
  }
  
  private static UUID a(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte.length != 16) {
      return null;
    }
    int i = 0;
    long l2 = 0L;
    long l1 = 0L;
    while (i < 8)
    {
      l1 = l1 << 8 | paramArrayOfByte[i] & 0xFF;
      i += 1;
    }
    i = 8;
    while (i < 16)
    {
      l2 = l2 << 8 | paramArrayOfByte[i] & 0xFF;
      i += 1;
    }
    return new UUID(l1, l2);
  }
  
  private MediaDrmSessionManager.SessionId a(byte[] paramArrayOfByte)
  {
    if (this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId == null)
    {
      Log.e("cr_media", "Session doesn't exist because media crypto session is not created.", new Object[0]);
      return null;
    }
    paramArrayOfByte = this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager.a(paramArrayOfByte);
    if (paramArrayOfByte == null) {
      return null;
    }
    if (!jdField_a_of_type_Boolean)
    {
      if (!this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId.a(paramArrayOfByte)) {
        return paramArrayOfByte;
      }
      throw new AssertionError();
    }
    return paramArrayOfByte;
  }
  
  private void a()
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_AndroidMediaMediaDrm == null)) {
      throw new AssertionError();
    }
    Object localObject = this.jdField_a_of_type_JavaUtilArrayDeque.iterator();
    while (((Iterator)localObject).hasNext()) {
      a(PendingCreateSessionData.a((PendingCreateSessionData)((Iterator)localObject).next()), "Create session aborted.");
    }
    this.jdField_a_of_type_JavaUtilArrayDeque.clear();
    this.jdField_a_of_type_JavaUtilArrayDeque = null;
    localObject = this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager.a().iterator();
    while (((Iterator)localObject).hasNext())
    {
      MediaDrmSessionManager.SessionId localSessionId = (MediaDrmSessionManager.SessionId)((Iterator)localObject).next();
      try
      {
        this.jdField_a_of_type_AndroidMediaMediaDrm.removeKeys(localSessionId.a());
      }
      catch (Exception localException)
      {
        Log.e("cr_media", "removeKeys failed: ", new Object[] { localException });
      }
      a(localSessionId);
      b(localSessionId);
    }
    this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager = new MediaDrmSessionManager(this.jdField_a_of_type_OrgChromiumMediaMediaDrmStorageBridge);
    localObject = this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId;
    if (localObject == null)
    {
      a(null);
    }
    else
    {
      a((MediaDrmSessionManager.SessionId)localObject);
      this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId = null;
    }
    if (this.jdField_b_of_type_Boolean)
    {
      this.jdField_b_of_type_Boolean = false;
      a(false);
    }
    localObject = this.jdField_a_of_type_AndroidMediaMediaDrm;
    if (localObject != null)
    {
      ((MediaDrm)localObject).release();
      this.jdField_a_of_type_AndroidMediaMediaDrm = null;
    }
    localObject = this.jdField_a_of_type_AndroidMediaMediaCrypto;
    if (localObject != null)
    {
      ((MediaCrypto)localObject).release();
      this.jdField_a_of_type_AndroidMediaMediaCrypto = null;
    }
  }
  
  private void a(long paramLong)
  {
    a(paramLong, MediaDrmSessionManager.SessionId.a());
  }
  
  private void a(long paramLong, String paramString)
  {
    Log.e("cr_media", "onPromiseRejected: %s", new Object[] { paramString });
    if (a()) {
      nativeOnPromiseRejected(this.jdField_a_of_type_Long, paramLong, paramString);
    }
  }
  
  private void a(long paramLong, MediaDrmSessionManager.SessionId paramSessionId)
  {
    if (a()) {
      nativeOnPromiseResolvedWithSession(this.jdField_a_of_type_Long, paramLong, paramSessionId.b());
    }
  }
  
  private void a(MediaCrypto paramMediaCrypto)
  {
    if (a()) {
      nativeOnMediaCryptoReady(this.jdField_a_of_type_Long, paramMediaCrypto);
    }
  }
  
  private void a(MediaDrmSessionManager.SessionId paramSessionId)
  {
    try
    {
      this.jdField_a_of_type_AndroidMediaMediaDrm.closeSession(paramSessionId.a());
      return;
    }
    catch (Exception paramSessionId)
    {
      Log.e("cr_media", "closeSession failed: ", new Object[] { paramSessionId });
    }
  }
  
  private void a(MediaDrmSessionManager.SessionId paramSessionId, final long paramLong)
  {
    try
    {
      byte[] arrayOfByte = a();
      if (arrayOfByte == null)
      {
        a(paramLong, "Failed to open session to load license");
        return;
      }
      this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager.a(paramSessionId, arrayOfByte);
      if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumMediaMediaDrmBridge$SessionEventDeferrer != null)) {
        throw new AssertionError();
      }
      this.jdField_a_of_type_OrgChromiumMediaMediaDrmBridge$SessionEventDeferrer = new SessionEventDeferrer(paramSessionId);
      if ((!jdField_a_of_type_Boolean) && (paramSessionId.c() == null)) {
        throw new AssertionError();
      }
      this.jdField_a_of_type_AndroidMediaMediaDrm.restoreKeys(paramSessionId.a(), paramSessionId.c());
      a(paramLong, paramSessionId);
      this.jdField_a_of_type_OrgChromiumMediaMediaDrmBridge$SessionEventDeferrer.a();
      this.jdField_a_of_type_OrgChromiumMediaMediaDrmBridge$SessionEventDeferrer = null;
      if (Build.VERSION.SDK_INT >= 23) {
        break label190;
      }
      a(paramSessionId, b(0).toArray(), true, false);
      return;
    }
    catch (NotProvisionedException paramSessionId)
    {
      for (;;) {}
    }
    catch (IllegalStateException localIllegalStateException)
    {
      label190:
      for (;;) {}
    }
    if (paramSessionId.a() == null)
    {
      a(paramLong);
      return;
    }
    a(paramSessionId);
    this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager.a(paramSessionId, new Callback()
    {
      public void a(Boolean paramAnonymousBoolean)
      {
        paramAnonymousBoolean.booleanValue();
        MediaDrmBridge.a(MediaDrmBridge.this, paramLong);
      }
    });
    return;
    if (jdField_a_of_type_Boolean) {
      return;
    }
    throw new AssertionError();
  }
  
  @TargetApi(23)
  private void a(MediaDrmSessionManager.SessionId paramSessionId, MediaDrm.KeyRequest paramKeyRequest)
  {
    if (!a()) {
      return;
    }
    int j;
    if (Build.VERSION.SDK_INT >= 23) {
      int i = paramKeyRequest.getRequestType();
    } else {
      j = paramKeyRequest.getDefaultUrl().isEmpty() ^ true;
    }
    nativeOnSessionMessage(this.jdField_a_of_type_Long, paramSessionId.b(), j, paramKeyRequest.getData());
  }
  
  private void a(MediaDrmSessionManager.SessionId paramSessionId, Runnable paramRunnable)
  {
    SessionEventDeferrer localSessionEventDeferrer = this.jdField_a_of_type_OrgChromiumMediaMediaDrmBridge$SessionEventDeferrer;
    if ((localSessionEventDeferrer != null) && (localSessionEventDeferrer.a(paramSessionId)))
    {
      this.jdField_a_of_type_OrgChromiumMediaMediaDrmBridge$SessionEventDeferrer.a(paramRunnable);
      return;
    }
    paramRunnable.run();
  }
  
  private void a(MediaDrmSessionManager.SessionId paramSessionId, Object[] paramArrayOfObject, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (a()) {
      nativeOnSessionKeysChange(this.jdField_a_of_type_Long, paramSessionId.b(), paramArrayOfObject, paramBoolean1, paramBoolean2);
    }
  }
  
  private void a(boolean paramBoolean)
  {
    if (a()) {
      nativeOnResetDeviceCredentialsCompleted(this.jdField_a_of_type_Long, paramBoolean);
    }
  }
  
  private void a(byte[] paramArrayOfByte, String paramString, int paramInt, HashMap<String, String> paramHashMap, long paramLong)
  {
    this.jdField_a_of_type_JavaUtilArrayDeque.offer(new PendingCreateSessionData(paramArrayOfByte, paramString, paramInt, paramHashMap, paramLong, null));
  }
  
  private boolean a()
  {
    return this.jdField_a_of_type_Long != 0L;
  }
  
  private boolean a(String paramString)
  {
    if ((!jdField_a_of_type_Boolean) && (Build.VERSION.SDK_INT < 23)) {
      throw new AssertionError();
    }
    if (!b()) {
      return true;
    }
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_AndroidMediaMediaDrm == null)) {
      throw new AssertionError();
    }
    if ((!jdField_a_of_type_Boolean) && (paramString.isEmpty())) {
      throw new AssertionError();
    }
    try
    {
      this.jdField_a_of_type_AndroidMediaMediaDrm.setPropertyString("origin", paramString);
      this.d = true;
      return true;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr_media", "Failed to set security origin %s", new Object[] { paramString, localIllegalStateException });
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      Log.e("cr_media", "Failed to set security origin %s", new Object[] { paramString, localIllegalArgumentException });
    }
    Log.e("cr_media", "Security origin %s not supported!", new Object[] { paramString });
    return false;
  }
  
  private byte[] a()
    throws NotProvisionedException
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_AndroidMediaMediaDrm == null)) {
      throw new AssertionError();
    }
    try
    {
      byte[] arrayOfByte = (byte[])this.jdField_a_of_type_AndroidMediaMediaDrm.openSession().clone();
      return arrayOfByte;
    }
    catch (MediaDrmException localMediaDrmException)
    {
      Log.e("cr_media", "Cannot open a new session", new Object[] { localMediaDrmException });
      a();
      return null;
    }
    catch (NotProvisionedException localNotProvisionedException)
    {
      throw localNotProvisionedException;
    }
    catch (RuntimeException localRuntimeException)
    {
      Log.e("cr_media", "Cannot open a new session", new Object[] { localRuntimeException });
      a();
    }
    return null;
  }
  
  private static List<KeyStatus> b(int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new KeyStatus(jdField_a_of_type_ArrayOfByte, paramInt, null));
    return localArrayList;
  }
  
  private MediaDrmSessionManager.SessionId b(byte[] paramArrayOfByte)
  {
    if (this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId == null)
    {
      Log.e("cr_media", "Session doesn't exist because media crypto session is not created.", new Object[0]);
      return null;
    }
    paramArrayOfByte = this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager.b(paramArrayOfByte);
    if (paramArrayOfByte == null) {
      return null;
    }
    if (!jdField_a_of_type_Boolean)
    {
      if (!this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId.a(paramArrayOfByte)) {
        return paramArrayOfByte;
      }
      throw new AssertionError();
    }
    return paramArrayOfByte;
  }
  
  private void b()
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_AndroidMediaMediaDrm == null)) {
      throw new AssertionError();
    }
    while ((this.jdField_a_of_type_AndroidMediaMediaDrm != null) && (!this.c) && (!this.jdField_a_of_type_JavaUtilArrayDeque.isEmpty()))
    {
      PendingCreateSessionData localPendingCreateSessionData = (PendingCreateSessionData)this.jdField_a_of_type_JavaUtilArrayDeque.poll();
      b(PendingCreateSessionData.a(localPendingCreateSessionData), PendingCreateSessionData.a(localPendingCreateSessionData), PendingCreateSessionData.a(localPendingCreateSessionData), PendingCreateSessionData.a(localPendingCreateSessionData), PendingCreateSessionData.a(localPendingCreateSessionData));
    }
  }
  
  private void b(long paramLong)
  {
    if (a()) {
      nativeOnPromiseResolved(this.jdField_a_of_type_Long, paramLong);
    }
  }
  
  private void b(MediaDrmSessionManager.SessionId paramSessionId)
  {
    if (a()) {
      nativeOnSessionClosed(this.jdField_a_of_type_Long, paramSessionId.b());
    }
  }
  
  private void b(MediaDrmSessionManager.SessionId paramSessionId, long paramLong)
  {
    if (a()) {
      nativeOnSessionExpirationUpdate(this.jdField_a_of_type_Long, paramSessionId.b(), paramLong);
    }
  }
  
  private void b(byte[] paramArrayOfByte, String paramString, int paramInt, HashMap<String, String> paramHashMap, long paramLong)
  {
    if (this.jdField_a_of_type_AndroidMediaMediaDrm == null)
    {
      Log.e("cr_media", "createSession() called when MediaDrm is null.", new Object[0]);
      a(paramLong, "MediaDrm released previously.");
      return;
    }
    if (this.c)
    {
      a(paramArrayOfByte, paramString, paramInt, paramHashMap, paramLong);
      return;
    }
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId == null)) {
      throw new AssertionError();
    }
    MediaDrmSessionManager.SessionId localSessionId2 = null;
    MediaDrmSessionManager.SessionId localSessionId1;
    int i;
    try
    {
      Object localObject = a();
      if (localObject == null)
      {
        a(paramLong, "Open session failed.");
        return;
      }
      localSessionId1 = localSessionId2;
      try
      {
        if ((!jdField_a_of_type_Boolean) && (paramInt != 1) && (paramInt != 2))
        {
          localSessionId1 = localSessionId2;
          throw new AssertionError();
        }
        if (paramInt == 2)
        {
          localSessionId1 = localSessionId2;
          localSessionId2 = MediaDrmSessionManager.SessionId.a((byte[])localObject);
        }
        else
        {
          localSessionId1 = localSessionId2;
          localSessionId2 = MediaDrmSessionManager.SessionId.b((byte[])localObject);
        }
        localSessionId1 = localSessionId2;
        localObject = a(localSessionId2, paramArrayOfByte, paramString, paramInt, paramHashMap);
        if (localObject == null)
        {
          localSessionId1 = localSessionId2;
          a(localSessionId2);
          localSessionId1 = localSessionId2;
          a(paramLong, "Generate request failed.");
          return;
        }
        localSessionId1 = localSessionId2;
        a(paramLong, localSessionId2);
        localSessionId1 = localSessionId2;
        a(localSessionId2, (MediaDrm.KeyRequest)localObject);
        localSessionId1 = localSessionId2;
        this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager.a(localSessionId2, paramString, paramInt);
        return;
      }
      catch (NotProvisionedException localNotProvisionedException1)
      {
        i = 1;
      }
    }
    catch (NotProvisionedException localNotProvisionedException2)
    {
      localSessionId1 = null;
      i = 0;
    }
    tmp272_269[0] = localNotProvisionedException2;
    Log.e("cr_media", "Device not provisioned", tmp272_269);
    if (i != 0) {
      a(localSessionId1);
    }
    a(paramArrayOfByte, paramString, paramInt, paramHashMap, paramLong);
    c();
  }
  
  private boolean b()
  {
    return this.jdField_b_of_type_JavaUtilUUID.equals(jdField_a_of_type_JavaUtilUUID);
  }
  
  private boolean b(String paramString)
  {
    if (!b()) {
      return true;
    }
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_AndroidMediaMediaDrm == null)) {
      throw new AssertionError();
    }
    if ((!jdField_a_of_type_Boolean) && (paramString.isEmpty())) {
      throw new AssertionError();
    }
    String str = this.jdField_a_of_type_AndroidMediaMediaDrm.getPropertyString("securityLevel");
    Log.e("cr_media", "Security level: current %s, new %s", new Object[] { str, paramString });
    if (paramString.equals(str)) {
      return true;
    }
    try
    {
      this.jdField_a_of_type_AndroidMediaMediaDrm.setPropertyString("securityLevel", paramString);
      return true;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("cr_media", "Failed to set security level %s", new Object[] { paramString, localIllegalStateException });
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      Log.e("cr_media", "Failed to set security level %s", new Object[] { paramString, localIllegalArgumentException });
    }
    Log.e("cr_media", "Security level %s not supported!", new Object[] { paramString });
    return false;
  }
  
  private void c()
  {
    if (this.c) {
      return;
    }
    this.c = true;
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_AndroidMediaMediaDrm == null)) {
      throw new AssertionError();
    }
    MediaDrm.ProvisionRequest localProvisionRequest = this.jdField_a_of_type_AndroidMediaMediaDrm.getProvisionRequest();
    if (a()) {
      nativeOnStartProvisioning(this.jdField_a_of_type_Long, localProvisionRequest.getDefaultUrl(), localProvisionRequest.getData());
    }
  }
  
  private boolean c()
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_AndroidMediaMediaDrm == null)) {
      throw new AssertionError();
    }
    if ((!jdField_a_of_type_Boolean) && (this.c)) {
      throw new AssertionError();
    }
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId != null)) {
      throw new AssertionError();
    }
    try
    {
      byte[] arrayOfByte = a();
      if (arrayOfByte == null)
      {
        Log.e("cr_media", "Cannot create MediaCrypto Session.", new Object[0]);
        return false;
      }
      this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId = MediaDrmSessionManager.SessionId.b(arrayOfByte);
      try
      {
        if (MediaCrypto.isCryptoSchemeSupported(this.jdField_b_of_type_JavaUtilUUID))
        {
          this.jdField_a_of_type_AndroidMediaMediaCrypto = new MediaCrypto(this.jdField_b_of_type_JavaUtilUUID, this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId.a());
          a(this.jdField_a_of_type_AndroidMediaMediaCrypto);
          return true;
        }
        Log.e("cr_media", "Cannot create MediaCrypto for unsupported scheme.", new Object[0]);
      }
      catch (MediaCryptoException localMediaCryptoException)
      {
        Log.e("cr_media", "Cannot create MediaCrypto", new Object[] { localMediaCryptoException });
      }
      a(this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId);
      this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId = null;
      return false;
    }
    catch (NotProvisionedException localNotProvisionedException)
    {
      for (;;) {}
    }
    c();
    return true;
  }
  
  @CalledByNative
  private void closeSession(byte[] paramArrayOfByte, long paramLong)
  {
    if (this.jdField_a_of_type_AndroidMediaMediaDrm == null)
    {
      a(paramLong, "closeSession() called when MediaDrm is null.");
      return;
    }
    Object localObject = a(paramArrayOfByte);
    if (localObject == null)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Invalid sessionId in closeSession(): ");
      ((StringBuilder)localObject).append(MediaDrmSessionManager.SessionId.a(paramArrayOfByte));
      a(paramLong, ((StringBuilder)localObject).toString());
      return;
    }
    try
    {
      this.jdField_a_of_type_AndroidMediaMediaDrm.removeKeys(((MediaDrmSessionManager.SessionId)localObject).a());
    }
    catch (Exception paramArrayOfByte)
    {
      Log.e("cr_media", "removeKeys failed: ", new Object[] { paramArrayOfByte });
    }
    a((MediaDrmSessionManager.SessionId)localObject);
    this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager.b((MediaDrmSessionManager.SessionId)localObject);
    b(paramLong);
    b((MediaDrmSessionManager.SessionId)localObject);
  }
  
  @CalledByNative
  private static MediaDrmBridge create(byte[] paramArrayOfByte, String paramString1, String paramString2, long paramLong1, long paramLong2)
  {
    paramArrayOfByte = a(paramArrayOfByte);
    if (paramArrayOfByte != null)
    {
      if (!MediaDrm.isCryptoSchemeSupported(paramArrayOfByte)) {
        return null;
      }
      try
      {
        paramArrayOfByte = new MediaDrmBridge(paramArrayOfByte, paramLong1, paramLong2);
        if ((!paramString2.isEmpty()) && (!paramArrayOfByte.b(paramString2))) {
          return null;
        }
        if ((!paramString1.isEmpty()) && (!paramArrayOfByte.a(paramString1))) {
          return null;
        }
        if (!paramArrayOfByte.c()) {
          return null;
        }
        return paramArrayOfByte;
      }
      catch (IllegalStateException paramArrayOfByte)
      {
        Log.e("cr_media", "Failed to create MediaDrmBridge", new Object[] { paramArrayOfByte });
        return null;
      }
      catch (IllegalArgumentException paramArrayOfByte)
      {
        Log.e("cr_media", "Failed to create MediaDrmBridge", new Object[] { paramArrayOfByte });
        return null;
      }
      catch (UnsupportedSchemeException paramArrayOfByte)
      {
        Log.e("cr_media", "Unsupported DRM scheme", new Object[] { paramArrayOfByte });
        return null;
      }
    }
    return null;
  }
  
  @CalledByNative
  private void createSessionFromNative(byte[] paramArrayOfByte, String paramString, int paramInt, String[] paramArrayOfString, long paramLong)
  {
    HashMap localHashMap = new HashMap();
    if (paramArrayOfString != null)
    {
      if (paramArrayOfString.length % 2 == 0)
      {
        int i = 0;
        while (i < paramArrayOfString.length)
        {
          localHashMap.put(paramArrayOfString[i], paramArrayOfString[(i + 1)]);
          i += 2;
        }
      }
      throw new IllegalArgumentException("Additional data array doesn't have equal keys/values");
    }
    b(paramArrayOfByte, paramString, paramInt, localHashMap, paramLong);
  }
  
  @CalledByNative
  private void destroy()
  {
    this.jdField_a_of_type_Long = 0L;
    if (this.jdField_a_of_type_AndroidMediaMediaDrm != null) {
      a();
    }
  }
  
  @CalledByNative
  private String getSecurityLevel()
  {
    if ((this.jdField_a_of_type_AndroidMediaMediaDrm != null) && (b())) {
      return this.jdField_a_of_type_AndroidMediaMediaDrm.getPropertyString("securityLevel");
    }
    Log.e("cr_media", "getSecurityLevel(): MediaDrm is null or security level is not supported.", new Object[0]);
    return "";
  }
  
  @CalledByNative
  private static boolean isCryptoSchemeSupported(byte[] paramArrayOfByte, String paramString)
  {
    paramArrayOfByte = a(paramArrayOfByte);
    if (paramString.isEmpty()) {
      return MediaDrm.isCryptoSchemeSupported(paramArrayOfByte);
    }
    return MediaDrm.isCryptoSchemeSupported(paramArrayOfByte, paramString);
  }
  
  @CalledByNative
  private void loadSession(byte[] paramArrayOfByte, final long paramLong)
  {
    if (this.c)
    {
      a(paramLong);
      return;
    }
    this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager.a(paramArrayOfByte, new Callback()
    {
      public void a(MediaDrmSessionManager.SessionId paramAnonymousSessionId)
      {
        if (paramAnonymousSessionId == null)
        {
          MediaDrmBridge.a(MediaDrmBridge.this, paramLong);
          return;
        }
        MediaDrmBridge.a(MediaDrmBridge.this, paramAnonymousSessionId, paramLong);
      }
    });
  }
  
  private native void nativeOnMediaCryptoReady(long paramLong, MediaCrypto paramMediaCrypto);
  
  private native void nativeOnPromiseRejected(long paramLong1, long paramLong2, String paramString);
  
  private native void nativeOnPromiseResolved(long paramLong1, long paramLong2);
  
  private native void nativeOnPromiseResolvedWithSession(long paramLong1, long paramLong2, byte[] paramArrayOfByte);
  
  private native void nativeOnResetDeviceCredentialsCompleted(long paramLong, boolean paramBoolean);
  
  private native void nativeOnSessionClosed(long paramLong, byte[] paramArrayOfByte);
  
  private native void nativeOnSessionExpirationUpdate(long paramLong1, byte[] paramArrayOfByte, long paramLong2);
  
  private native void nativeOnSessionKeysChange(long paramLong, byte[] paramArrayOfByte, Object[] paramArrayOfObject, boolean paramBoolean1, boolean paramBoolean2);
  
  private native void nativeOnSessionMessage(long paramLong, byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2);
  
  private native void nativeOnStartProvisioning(long paramLong, String paramString, byte[] paramArrayOfByte);
  
  @CalledByNative
  private void processProvisionResponse(boolean paramBoolean, byte[] paramArrayOfByte)
  {
    if (this.jdField_a_of_type_AndroidMediaMediaDrm == null) {
      return;
    }
    if ((!jdField_a_of_type_Boolean) && (!this.c)) {
      throw new AssertionError();
    }
    this.c = false;
    if (paramBoolean) {
      paramBoolean = a(paramArrayOfByte);
    } else {
      paramBoolean = false;
    }
    if (this.jdField_b_of_type_Boolean)
    {
      a(paramBoolean);
      this.jdField_b_of_type_Boolean = false;
    }
    if ((paramBoolean) && ((this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId != null) || (c())))
    {
      if (!this.d)
      {
        b();
        return;
      }
      this.jdField_a_of_type_OrgChromiumMediaMediaDrmStorageBridge.a(new Callback()
      {
        public void a(Boolean paramAnonymousBoolean)
        {
          if (!paramAnonymousBoolean.booleanValue())
          {
            Log.e("cr_media", "Failed to initialize storage for origin", new Object[0]);
            MediaDrmBridge.a(MediaDrmBridge.this);
            return;
          }
          MediaDrmBridge.b(MediaDrmBridge.this);
        }
      });
      return;
    }
    a();
  }
  
  @CalledByNative
  private void removeSession(byte[] paramArrayOfByte, long paramLong)
  {
    paramArrayOfByte = a(paramArrayOfByte);
    if (paramArrayOfByte == null)
    {
      a(paramLong, "Session doesn't exist");
      return;
    }
    Object localObject = this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager.a(paramArrayOfByte);
    if (((MediaDrmSessionManager.SessionInfo)localObject).a() != 2)
    {
      a(paramLong, "Removing temporary session isn't implemented");
      return;
    }
    if ((!jdField_a_of_type_Boolean) && (paramArrayOfByte.c() == null)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager.a(paramArrayOfByte);
    try
    {
      localObject = a(paramArrayOfByte, null, ((MediaDrmSessionManager.SessionInfo)localObject).a(), 3, null);
      if (localObject == null)
      {
        a(paramLong, "Fail to generate key release request");
        return;
      }
      b(paramLong);
      a(paramArrayOfByte, (MediaDrm.KeyRequest)localObject);
      return;
    }
    catch (NotProvisionedException paramArrayOfByte)
    {
      for (;;) {}
    }
    Log.e("cr_media", "removeSession called on unprovisioned device", new Object[0]);
    a(paramLong, "Unknown failure");
  }
  
  @CalledByNative
  private void resetDeviceCredentials()
  {
    if (this.jdField_a_of_type_AndroidMediaMediaDrm == null)
    {
      a(false);
      return;
    }
    this.jdField_b_of_type_Boolean = true;
    c();
  }
  
  @CalledByNative
  private boolean setServerCertificate(byte[] paramArrayOfByte)
  {
    if (!b()) {
      return true;
    }
    try
    {
      this.jdField_a_of_type_AndroidMediaMediaDrm.setPropertyByteArray("serviceCertificate", paramArrayOfByte);
      return true;
    }
    catch (IllegalStateException paramArrayOfByte)
    {
      Log.e("cr_media", "Failed to set server certificate", new Object[] { paramArrayOfByte });
      return false;
    }
    catch (IllegalArgumentException paramArrayOfByte)
    {
      Log.e("cr_media", "Failed to set server certificate", new Object[] { paramArrayOfByte });
    }
    return false;
  }
  
  @CalledByNative
  private void unprovision()
  {
    if (this.jdField_a_of_type_AndroidMediaMediaDrm == null) {
      return;
    }
    if (!this.d) {
      return;
    }
    a(jdField_b_of_type_ArrayOfByte);
  }
  
  @CalledByNative
  private void updateSession(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, long paramLong)
  {
    if (this.jdField_a_of_type_AndroidMediaMediaDrm == null)
    {
      a(paramLong, "updateSession() called when MediaDrm is null.");
      return;
    }
    MediaDrmSessionManager.SessionId localSessionId = a(paramArrayOfByte1);
    if (localSessionId == null)
    {
      if (jdField_a_of_type_Boolean)
      {
        paramArrayOfByte2 = new StringBuilder();
        paramArrayOfByte2.append("Invalid session in updateSession: ");
        paramArrayOfByte2.append(MediaDrmSessionManager.SessionId.a(paramArrayOfByte1));
        a(paramLong, paramArrayOfByte2.toString());
        return;
      }
      throw new AssertionError();
    }
    for (;;)
    {
      try
      {
        MediaDrmSessionManager.SessionInfo localSessionInfo = this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager.a(localSessionId);
        if (localSessionInfo.a() != 3) {
          break label305;
        }
        bool = true;
        if (bool)
        {
          if ((!jdField_a_of_type_Boolean) && (localSessionId.c() == null)) {
            throw new AssertionError();
          }
          this.jdField_a_of_type_AndroidMediaMediaDrm.provideKeyResponse(localSessionId.c(), paramArrayOfByte2);
          paramArrayOfByte1 = null;
        }
        else
        {
          paramArrayOfByte1 = this.jdField_a_of_type_AndroidMediaMediaDrm.provideKeyResponse(localSessionId.a(), paramArrayOfByte2);
        }
        paramArrayOfByte2 = new KeyUpdatedCallback(localSessionId, paramLong, bool);
        if (bool)
        {
          this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager.a(localSessionId, paramArrayOfByte2);
          return;
        }
        if ((localSessionInfo.a() == 2) && (paramArrayOfByte1 != null) && (paramArrayOfByte1.length > 0))
        {
          this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager.a(localSessionId, paramArrayOfByte1, paramArrayOfByte2);
          return;
        }
        paramArrayOfByte2.a(Boolean.valueOf(true));
        return;
      }
      catch (IllegalStateException paramArrayOfByte1)
      {
        Log.e("cr_media", "failed to provide key response", new Object[] { paramArrayOfByte1 });
      }
      catch (DeniedByServerException paramArrayOfByte1)
      {
        Log.e("cr_media", "failed to provide key response", new Object[] { paramArrayOfByte1 });
      }
      catch (NotProvisionedException paramArrayOfByte1)
      {
        Log.e("cr_media", "failed to provide key response", new Object[] { paramArrayOfByte1 });
      }
      a(paramLong, "Update session failed.");
      a();
      return;
      label305:
      boolean bool = false;
    }
  }
  
  boolean a(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length != 0)) {
      try
      {
        this.jdField_a_of_type_AndroidMediaMediaDrm.provideProvisionResponse(paramArrayOfByte);
        return true;
      }
      catch (IllegalStateException paramArrayOfByte)
      {
        Log.e("cr_media", "failed to provide provision response", new Object[] { paramArrayOfByte });
        return false;
      }
      catch (DeniedByServerException paramArrayOfByte)
      {
        Log.e("cr_media", "failed to provide provision response", new Object[] { paramArrayOfByte });
        return false;
      }
    }
    Log.e("cr_media", "Invalid provision response.", new Object[0]);
    return false;
  }
  
  @MainDex
  private class EventListener
    implements MediaDrm.OnEventListener
  {
    private EventListener() {}
    
    public void onEvent(MediaDrm paramMediaDrm, byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2)
    {
      if (paramArrayOfByte1 == null)
      {
        Log.e("cr_media", "EventListener: Null session.", new Object[0]);
        return;
      }
      paramMediaDrm = MediaDrmBridge.a(MediaDrmBridge.this, paramArrayOfByte1);
      boolean bool = true;
      if (paramMediaDrm == null)
      {
        Log.e("cr_media", "EventListener: Invalid session %s", new Object[] { MediaDrmSessionManager.SessionId.a(paramArrayOfByte1) });
        return;
      }
      paramArrayOfByte1 = MediaDrmBridge.a(MediaDrmBridge.this).a(paramMediaDrm);
      switch (paramInt1)
      {
      default: 
        paramMediaDrm = new StringBuilder();
        paramMediaDrm.append("Invalid DRM event ");
        paramMediaDrm.append(paramInt1);
        Log.e("cr_media", paramMediaDrm.toString(), new Object[0]);
        return;
      case 4: 
        if (jdField_a_of_type_Boolean) {
          return;
        }
        throw new AssertionError();
      case 3: 
        if (Build.VERSION.SDK_INT < 23)
        {
          paramArrayOfByte2 = MediaDrmBridge.this;
          Object[] arrayOfObject = MediaDrmBridge.a(1).toArray();
          if (paramArrayOfByte1.a() != 3) {
            bool = false;
          }
          MediaDrmBridge.a(paramArrayOfByte2, paramMediaDrm, arrayOfObject, false, bool);
          return;
        }
        break;
      case 2: 
        if (MediaDrmBridge.a(MediaDrmBridge.this)) {
          return;
        }
        break;
      }
      try
      {
        paramArrayOfByte1 = MediaDrmBridge.a(MediaDrmBridge.this, paramMediaDrm, paramArrayOfByte2, paramArrayOfByte1.a(), paramArrayOfByte1.a(), null);
        if (paramArrayOfByte1 != null)
        {
          MediaDrmBridge.a(MediaDrmBridge.this, paramMediaDrm, paramArrayOfByte1);
          return;
        }
        if (Build.VERSION.SDK_INT < 23) {
          MediaDrmBridge.a(MediaDrmBridge.this, paramMediaDrm, MediaDrmBridge.a(4).toArray(), false, false);
        }
        Log.e("cr_media", "EventListener: getKeyRequest failed.", new Object[0]);
        return;
      }
      catch (NotProvisionedException paramMediaDrm)
      {
        Log.e("cr_media", "Device not provisioned", new Object[] { paramMediaDrm });
        MediaDrmBridge.c(MediaDrmBridge.this);
      }
    }
  }
  
  @TargetApi(23)
  @MainDex
  private class ExpirationUpdateListener
    implements MediaDrm.OnExpirationUpdateListener
  {
    private ExpirationUpdateListener() {}
    
    public void onExpirationUpdate(final MediaDrm paramMediaDrm, byte[] paramArrayOfByte, final long paramLong)
    {
      paramMediaDrm = MediaDrmBridge.a(MediaDrmBridge.this, paramArrayOfByte);
      if ((!jdField_a_of_type_Boolean) && (paramMediaDrm == null)) {
        throw new AssertionError();
      }
      MediaDrmBridge.a(MediaDrmBridge.this, paramMediaDrm, new Runnable()
      {
        public void run()
        {
          MediaDrmBridge.b(MediaDrmBridge.this, paramMediaDrm, paramLong);
        }
      });
    }
  }
  
  @MainDex
  private static class KeyStatus
  {
    private final int jdField_a_of_type_Int;
    private final byte[] jdField_a_of_type_ArrayOfByte;
    
    private KeyStatus(byte[] paramArrayOfByte, int paramInt)
    {
      this.jdField_a_of_type_ArrayOfByte = paramArrayOfByte;
      this.jdField_a_of_type_Int = paramInt;
    }
    
    @CalledByNative("KeyStatus")
    private byte[] getKeyId()
    {
      return this.jdField_a_of_type_ArrayOfByte;
    }
    
    @CalledByNative("KeyStatus")
    private int getStatusCode()
    {
      return this.jdField_a_of_type_Int;
    }
  }
  
  @TargetApi(23)
  @MainDex
  private class KeyStatusChangeListener
    implements MediaDrm.OnKeyStatusChangeListener
  {
    private KeyStatusChangeListener() {}
    
    private List<MediaDrmBridge.KeyStatus> a(List<MediaDrm.KeyStatus> paramList)
    {
      ArrayList localArrayList = new ArrayList();
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        MediaDrm.KeyStatus localKeyStatus = (MediaDrm.KeyStatus)paramList.next();
        localArrayList.add(new MediaDrmBridge.KeyStatus(localKeyStatus.getKeyId(), localKeyStatus.getStatusCode(), null));
      }
      return localArrayList;
    }
    
    public void onKeyStatusChange(final MediaDrm paramMediaDrm, byte[] paramArrayOfByte, final List<MediaDrm.KeyStatus> paramList, final boolean paramBoolean)
    {
      paramMediaDrm = MediaDrmBridge.a(MediaDrmBridge.this, paramArrayOfByte);
      if ((!jdField_a_of_type_Boolean) && (paramMediaDrm == null)) {
        throw new AssertionError();
      }
      if ((!jdField_a_of_type_Boolean) && (MediaDrmBridge.a(MediaDrmBridge.this).a(paramMediaDrm) == null)) {
        throw new AssertionError();
      }
      final boolean bool;
      if (MediaDrmBridge.a(MediaDrmBridge.this).a(paramMediaDrm).a() == 3) {
        bool = true;
      } else {
        bool = false;
      }
      MediaDrmBridge.a(MediaDrmBridge.this, paramMediaDrm, new Runnable()
      {
        public void run()
        {
          MediaDrmBridge.a(MediaDrmBridge.this, paramMediaDrm, MediaDrmBridge.KeyStatusChangeListener.a(MediaDrmBridge.KeyStatusChangeListener.this, paramList).toArray(), paramBoolean, bool);
        }
      });
    }
  }
  
  @MainDex
  private class KeyUpdatedCallback
    implements Callback<Boolean>
  {
    private final long jdField_a_of_type_Long;
    private final MediaDrmSessionManager.SessionId jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId;
    private final boolean jdField_a_of_type_Boolean;
    
    KeyUpdatedCallback(MediaDrmSessionManager.SessionId paramSessionId, long paramLong, boolean paramBoolean)
    {
      this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId = paramSessionId;
      this.jdField_a_of_type_Long = paramLong;
      this.jdField_a_of_type_Boolean = paramBoolean;
    }
    
    public void a(Boolean paramBoolean)
    {
      if (!paramBoolean.booleanValue())
      {
        MediaDrmBridge.a(MediaDrmBridge.this, this.jdField_a_of_type_Long, "failed to update key after response accepted");
        return;
      }
      MediaDrmBridge.b(MediaDrmBridge.this, this.jdField_a_of_type_Long);
      if ((!this.jdField_a_of_type_Boolean) && (Build.VERSION.SDK_INT < 23)) {
        MediaDrmBridge.a(MediaDrmBridge.this, this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId, MediaDrmBridge.a(0).toArray(), true, this.jdField_a_of_type_Boolean);
      }
    }
  }
  
  @MainDex
  private static class PendingCreateSessionData
  {
    private final int jdField_a_of_type_Int;
    private final long jdField_a_of_type_Long;
    private final String jdField_a_of_type_JavaLangString;
    private final HashMap<String, String> jdField_a_of_type_JavaUtilHashMap;
    private final byte[] jdField_a_of_type_ArrayOfByte;
    
    private PendingCreateSessionData(byte[] paramArrayOfByte, String paramString, int paramInt, HashMap<String, String> paramHashMap, long paramLong)
    {
      this.jdField_a_of_type_ArrayOfByte = paramArrayOfByte;
      this.jdField_a_of_type_JavaLangString = paramString;
      if ((!jdField_a_of_type_Boolean) && (paramInt != 1) && (paramInt != 2)) {
        throw new AssertionError();
      }
      this.jdField_a_of_type_Int = paramInt;
      this.jdField_a_of_type_JavaUtilHashMap = paramHashMap;
      this.jdField_a_of_type_Long = paramLong;
    }
    
    private int a()
    {
      return this.jdField_a_of_type_Int;
    }
    
    private long a()
    {
      return this.jdField_a_of_type_Long;
    }
    
    private String a()
    {
      return this.jdField_a_of_type_JavaLangString;
    }
    
    private HashMap<String, String> a()
    {
      return this.jdField_a_of_type_JavaUtilHashMap;
    }
    
    private byte[] a()
    {
      return this.jdField_a_of_type_ArrayOfByte;
    }
  }
  
  private static class SessionEventDeferrer
  {
    private final ArrayList<Runnable> jdField_a_of_type_JavaUtilArrayList;
    private final MediaDrmSessionManager.SessionId jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId;
    
    SessionEventDeferrer(MediaDrmSessionManager.SessionId paramSessionId)
    {
      this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId = paramSessionId;
      this.jdField_a_of_type_JavaUtilArrayList = new ArrayList();
    }
    
    void a()
    {
      Iterator localIterator = this.jdField_a_of_type_JavaUtilArrayList.iterator();
      while (localIterator.hasNext()) {
        ((Runnable)localIterator.next()).run();
      }
      this.jdField_a_of_type_JavaUtilArrayList.clear();
    }
    
    void a(Runnable paramRunnable)
    {
      this.jdField_a_of_type_JavaUtilArrayList.add(paramRunnable);
    }
    
    boolean a(MediaDrmSessionManager.SessionId paramSessionId)
    {
      return this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId.a(paramSessionId);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\MediaDrmBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */