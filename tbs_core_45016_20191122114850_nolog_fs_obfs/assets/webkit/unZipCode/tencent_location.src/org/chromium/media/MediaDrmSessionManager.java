package org.chromium.media;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.chromium.base.ApiCompatibilityUtils;
import org.chromium.base.Callback;

class MediaDrmSessionManager
{
  private HashMap<ByteBuffer, SessionInfo> jdField_a_of_type_JavaUtilHashMap = new HashMap();
  private MediaDrmStorageBridge jdField_a_of_type_OrgChromiumMediaMediaDrmStorageBridge;
  private HashMap<ByteBuffer, SessionInfo> b = new HashMap();
  
  public MediaDrmSessionManager(MediaDrmStorageBridge paramMediaDrmStorageBridge)
  {
    this.jdField_a_of_type_OrgChromiumMediaMediaDrmStorageBridge = paramMediaDrmStorageBridge;
  }
  
  private SessionId a(HashMap<ByteBuffer, SessionInfo> paramHashMap, byte[] paramArrayOfByte)
  {
    paramHashMap = (SessionInfo)paramHashMap.get(ByteBuffer.wrap(paramArrayOfByte));
    if (paramHashMap == null) {
      return null;
    }
    return SessionInfo.a(paramHashMap);
  }
  
  List<SessionId> a()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.jdField_a_of_type_JavaUtilHashMap.values().iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(SessionInfo.a((SessionInfo)localIterator.next()));
    }
    return localArrayList;
  }
  
  SessionId a(byte[] paramArrayOfByte)
  {
    return a(this.jdField_a_of_type_JavaUtilHashMap, paramArrayOfByte);
  }
  
  SessionInfo a(SessionId paramSessionId)
  {
    return (SessionInfo)this.jdField_a_of_type_JavaUtilHashMap.get(ByteBuffer.wrap(paramSessionId.b()));
  }
  
  void a(SessionId paramSessionId)
  {
    paramSessionId = a(paramSessionId);
    if ((!jdField_a_of_type_Boolean) && (paramSessionId == null)) {
      throw new AssertionError();
    }
    if ((!jdField_a_of_type_Boolean) && (paramSessionId.a() != 2)) {
      throw new AssertionError();
    }
    SessionInfo.a(paramSessionId, 3);
  }
  
  void a(SessionId paramSessionId, String paramString, int paramInt)
  {
    paramString = new SessionInfo(paramSessionId, paramString, paramInt, null);
    this.jdField_a_of_type_JavaUtilHashMap.put(ByteBuffer.wrap(paramSessionId.b()), paramString);
    if (paramSessionId.a() != null) {
      this.b.put(ByteBuffer.wrap(paramSessionId.a()), paramString);
    }
  }
  
  void a(SessionId paramSessionId, Callback<Boolean> paramCallback)
  {
    SessionId.b(paramSessionId, null);
    this.jdField_a_of_type_OrgChromiumMediaMediaDrmStorageBridge.b(paramSessionId.b(), paramCallback);
  }
  
  void a(SessionId paramSessionId, byte[] paramArrayOfByte)
  {
    SessionInfo localSessionInfo = a(paramSessionId);
    if ((!jdField_a_of_type_Boolean) && (localSessionInfo == null)) {
      throw new AssertionError();
    }
    if ((!jdField_a_of_type_Boolean) && (!SessionInfo.a(localSessionInfo).a(paramSessionId))) {
      throw new AssertionError();
    }
    SessionId.a(paramSessionId, paramArrayOfByte);
    this.b.put(ByteBuffer.wrap(paramArrayOfByte), localSessionInfo);
  }
  
  void a(SessionId paramSessionId, byte[] paramArrayOfByte, Callback<Boolean> paramCallback)
  {
    if ((!jdField_a_of_type_Boolean) && (a(paramSessionId) == null)) {
      throw new AssertionError();
    }
    if ((!jdField_a_of_type_Boolean) && (a(paramSessionId).a() != 2)) {
      throw new AssertionError();
    }
    if ((!jdField_a_of_type_Boolean) && (paramSessionId.c() != null)) {
      throw new AssertionError();
    }
    SessionId.b(paramSessionId, paramArrayOfByte);
    this.jdField_a_of_type_OrgChromiumMediaMediaDrmStorageBridge.a(SessionInfo.a(a(paramSessionId)), paramCallback);
  }
  
  void a(byte[] paramArrayOfByte, final Callback<SessionId> paramCallback)
  {
    this.jdField_a_of_type_OrgChromiumMediaMediaDrmStorageBridge.a(paramArrayOfByte, new Callback()
    {
      public void a(MediaDrmStorageBridge.PersistentInfo paramAnonymousPersistentInfo)
      {
        if (paramAnonymousPersistentInfo == null)
        {
          paramCallback.onResult(null);
          return;
        }
        if ((!jdField_a_of_type_Boolean) && (MediaDrmSessionManager.this.a(paramAnonymousPersistentInfo.emeId()) != null)) {
          throw new AssertionError();
        }
        MediaDrmSessionManager.SessionInfo localSessionInfo = MediaDrmSessionManager.SessionInfo.a(paramAnonymousPersistentInfo);
        MediaDrmSessionManager.a(MediaDrmSessionManager.this).put(ByteBuffer.wrap(paramAnonymousPersistentInfo.emeId()), localSessionInfo);
        paramCallback.onResult(MediaDrmSessionManager.SessionInfo.a(localSessionInfo));
      }
    });
  }
  
  SessionId b(byte[] paramArrayOfByte)
  {
    return a(this.b, paramArrayOfByte);
  }
  
  void b(SessionId paramSessionId)
  {
    SessionInfo localSessionInfo = a(paramSessionId);
    if ((!jdField_a_of_type_Boolean) && (localSessionInfo == null)) {
      throw new AssertionError();
    }
    if ((!jdField_a_of_type_Boolean) && (!paramSessionId.a(SessionInfo.a(localSessionInfo)))) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_JavaUtilHashMap.remove(ByteBuffer.wrap(paramSessionId.b()));
    if (paramSessionId.a() != null) {
      this.b.remove(ByteBuffer.wrap(paramSessionId.a()));
    }
  }
  
  static class SessionId
  {
    private static final char[] jdField_a_of_type_ArrayOfChar = "0123456789ABCDEF".toCharArray();
    private final byte[] jdField_a_of_type_ArrayOfByte;
    private byte[] b;
    private byte[] c;
    
    private SessionId(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
    {
      if ((!jdField_a_of_type_Boolean) && (paramArrayOfByte1 == null)) {
        throw new AssertionError();
      }
      if ((!jdField_a_of_type_Boolean) && (paramArrayOfByte2 == null) && (paramArrayOfByte3 == null)) {
        throw new AssertionError();
      }
      this.jdField_a_of_type_ArrayOfByte = paramArrayOfByte1;
      this.b = paramArrayOfByte2;
      this.c = paramArrayOfByte3;
    }
    
    static String a(byte[] paramArrayOfByte)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      int i = 0;
      while (i < paramArrayOfByte.length)
      {
        localStringBuilder.append(jdField_a_of_type_ArrayOfChar[(paramArrayOfByte[i] >>> 4)]);
        localStringBuilder.append(jdField_a_of_type_ArrayOfChar[(paramArrayOfByte[i] & 0xF)]);
        i += 1;
      }
      return localStringBuilder.toString();
    }
    
    static SessionId a()
    {
      return b(new byte[0]);
    }
    
    static SessionId a(byte[] paramArrayOfByte)
    {
      return new SessionId(ApiCompatibilityUtils.getBytesUtf8(UUID.randomUUID().toString().replace('-', '0')), paramArrayOfByte, null);
    }
    
    private void a(byte[] paramArrayOfByte)
    {
      this.c = paramArrayOfByte;
    }
    
    static SessionId b(byte[] paramArrayOfByte)
    {
      return new SessionId(paramArrayOfByte, paramArrayOfByte, null);
    }
    
    private void b(byte[] paramArrayOfByte)
    {
      this.b = paramArrayOfByte;
    }
    
    boolean a(SessionId paramSessionId)
    {
      return Arrays.equals(this.jdField_a_of_type_ArrayOfByte, paramSessionId.b());
    }
    
    byte[] a()
    {
      return this.b;
    }
    
    byte[] b()
    {
      return this.jdField_a_of_type_ArrayOfByte;
    }
    
    byte[] c()
    {
      return this.c;
    }
  }
  
  static class SessionInfo
  {
    private int jdField_a_of_type_Int;
    private final String jdField_a_of_type_JavaLangString;
    private final MediaDrmSessionManager.SessionId jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId;
    
    private SessionInfo(MediaDrmSessionManager.SessionId paramSessionId, String paramString, int paramInt)
    {
      if ((!jdField_a_of_type_Boolean) && (paramSessionId == null)) {
        throw new AssertionError();
      }
      if ((!jdField_a_of_type_Boolean) && ((paramString == null) || (paramString.isEmpty()))) {
        throw new AssertionError();
      }
      this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId = paramSessionId;
      this.jdField_a_of_type_JavaLangString = paramString;
      this.jdField_a_of_type_Int = paramInt;
    }
    
    private MediaDrmSessionManager.SessionId a()
    {
      return this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId;
    }
    
    private MediaDrmStorageBridge.PersistentInfo a()
    {
      if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId.c() == null)) {
        throw new AssertionError();
      }
      return new MediaDrmStorageBridge.PersistentInfo(this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId.b(), this.jdField_a_of_type_OrgChromiumMediaMediaDrmSessionManager$SessionId.c(), this.jdField_a_of_type_JavaLangString);
    }
    
    private void a(int paramInt)
    {
      this.jdField_a_of_type_Int = paramInt;
    }
    
    private static SessionInfo b(MediaDrmStorageBridge.PersistentInfo paramPersistentInfo)
    {
      if ((!jdField_a_of_type_Boolean) && (paramPersistentInfo == null)) {
        throw new AssertionError();
      }
      if ((!jdField_a_of_type_Boolean) && (paramPersistentInfo.emeId() == null)) {
        throw new AssertionError();
      }
      if ((!jdField_a_of_type_Boolean) && (paramPersistentInfo.keySetId() == null)) {
        throw new AssertionError();
      }
      return new SessionInfo(new MediaDrmSessionManager.SessionId(paramPersistentInfo.emeId(), null, paramPersistentInfo.keySetId(), null), paramPersistentInfo.mimeType(), 2);
    }
    
    int a()
    {
      return this.jdField_a_of_type_Int;
    }
    
    String a()
    {
      return this.jdField_a_of_type_JavaLangString;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\MediaDrmSessionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */