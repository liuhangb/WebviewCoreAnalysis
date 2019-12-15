package com.tencent.tbs.common.wifi.state;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tencent.common.utils.LogUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class StateMachine
{
  public static final boolean HANDLED = true;
  public static final boolean NOT_HANDLED = false;
  private static final int SM_INIT_CMD = -2;
  private static final int SM_QUIT_CMD = -1;
  private static final String TAG = "StateMachine";
  private String mName;
  private SmHandler mSmHandler;
  
  protected StateMachine(String paramString, Handler paramHandler)
  {
    initStateMachine(paramString, paramHandler.getLooper());
  }
  
  protected StateMachine(String paramString, Looper paramLooper)
  {
    initStateMachine(paramString, paramLooper);
  }
  
  private void initStateMachine(String paramString, Looper paramLooper)
  {
    this.mName = paramString;
    this.mSmHandler = new SmHandler(paramLooper, this, null);
  }
  
  protected final void addState(State paramState)
  {
    this.mSmHandler.addState(paramState, null);
  }
  
  protected final void addState(State paramState1, State paramState2)
  {
    this.mSmHandler.addState(paramState1, paramState2);
  }
  
  protected final void deferMessage(Message paramMessage)
  {
    this.mSmHandler.deferMessage(paramMessage);
  }
  
  protected final Message getCurrentMessage()
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return null;
    }
    return localSmHandler.getCurrentMessage();
  }
  
  protected final IState getCurrentState()
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return null;
    }
    return localSmHandler.getCurrentState();
  }
  
  public final Handler getHandler()
  {
    return this.mSmHandler;
  }
  
  protected String getLogRecString(Message paramMessage)
  {
    return "";
  }
  
  public final String getName()
  {
    return this.mName;
  }
  
  protected String getWhatToString(int paramInt)
  {
    return null;
  }
  
  protected void haltedProcessMessage(Message paramMessage) {}
  
  public boolean isDbg()
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return false;
    }
    return localSmHandler.isDbg();
  }
  
  protected final boolean isQuit(Message paramMessage)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return paramMessage.what == -1;
    }
    return localSmHandler.isQuit(paramMessage);
  }
  
  public final Message obtainMessage()
  {
    return Message.obtain(this.mSmHandler);
  }
  
  public final Message obtainMessage(int paramInt)
  {
    return Message.obtain(this.mSmHandler, paramInt);
  }
  
  public final Message obtainMessage(int paramInt1, int paramInt2)
  {
    return Message.obtain(this.mSmHandler, paramInt1, paramInt2, 0);
  }
  
  public final Message obtainMessage(int paramInt1, int paramInt2, int paramInt3)
  {
    return Message.obtain(this.mSmHandler, paramInt1, paramInt2, paramInt3);
  }
  
  public final Message obtainMessage(int paramInt1, int paramInt2, int paramInt3, Object paramObject)
  {
    return Message.obtain(this.mSmHandler, paramInt1, paramInt2, paramInt3, paramObject);
  }
  
  public final Message obtainMessage(int paramInt, Object paramObject)
  {
    return Message.obtain(this.mSmHandler, paramInt, paramObject);
  }
  
  protected void onHalting() {}
  
  protected void onQuitting() {}
  
  protected final void quit()
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.quit();
  }
  
  protected final void quitNow()
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.quitNow();
  }
  
  protected boolean recordLogRec(Message paramMessage)
  {
    return true;
  }
  
  public final void removeMessages(int paramInt)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.removeMessages(paramInt);
  }
  
  public final void sendMessage(int paramInt)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.sendMessage(obtainMessage(paramInt));
  }
  
  public final void sendMessage(int paramInt1, int paramInt2)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.sendMessage(obtainMessage(paramInt1, paramInt2));
  }
  
  public final void sendMessage(int paramInt1, int paramInt2, int paramInt3)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.sendMessage(obtainMessage(paramInt1, paramInt2, paramInt3));
  }
  
  public final void sendMessage(int paramInt1, int paramInt2, int paramInt3, Object paramObject)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.sendMessage(obtainMessage(paramInt1, paramInt2, paramInt3, paramObject));
  }
  
  public final void sendMessage(int paramInt, Object paramObject)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.sendMessage(obtainMessage(paramInt, paramObject));
  }
  
  public final void sendMessage(Message paramMessage)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.sendMessage(paramMessage);
  }
  
  protected final void sendMessageAtFrontOfQueue(int paramInt)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.sendMessageAtFrontOfQueue(obtainMessage(paramInt));
  }
  
  protected final void sendMessageAtFrontOfQueue(int paramInt1, int paramInt2)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.sendMessageAtFrontOfQueue(obtainMessage(paramInt1, paramInt2));
  }
  
  protected final void sendMessageAtFrontOfQueue(int paramInt1, int paramInt2, int paramInt3)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.sendMessageAtFrontOfQueue(obtainMessage(paramInt1, paramInt2, paramInt3));
  }
  
  protected final void sendMessageAtFrontOfQueue(int paramInt1, int paramInt2, int paramInt3, Object paramObject)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.sendMessageAtFrontOfQueue(obtainMessage(paramInt1, paramInt2, paramInt3, paramObject));
  }
  
  protected final void sendMessageAtFrontOfQueue(int paramInt, Object paramObject)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.sendMessageAtFrontOfQueue(obtainMessage(paramInt, paramObject));
  }
  
  protected final void sendMessageAtFrontOfQueue(Message paramMessage)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.sendMessageAtFrontOfQueue(paramMessage);
  }
  
  public final void sendMessageDelayed(int paramInt1, int paramInt2, int paramInt3, long paramLong)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.sendMessageDelayed(obtainMessage(paramInt1, paramInt2, paramInt3), paramLong);
  }
  
  public final void sendMessageDelayed(int paramInt1, int paramInt2, int paramInt3, Object paramObject, long paramLong)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.sendMessageDelayed(obtainMessage(paramInt1, paramInt2, paramInt3, paramObject), paramLong);
  }
  
  public final void sendMessageDelayed(int paramInt1, int paramInt2, long paramLong)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.sendMessageDelayed(obtainMessage(paramInt1, paramInt2), paramLong);
  }
  
  public final void sendMessageDelayed(int paramInt, long paramLong)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.sendMessageDelayed(obtainMessage(paramInt), paramLong);
  }
  
  public final void sendMessageDelayed(int paramInt, Object paramObject, long paramLong)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.sendMessageDelayed(obtainMessage(paramInt, paramObject), paramLong);
  }
  
  public final void sendMessageDelayed(Message paramMessage, long paramLong)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.sendMessageDelayed(paramMessage, paramLong);
  }
  
  public void setDbg(boolean paramBoolean)
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.setDbg(paramBoolean);
  }
  
  protected final void setInitialState(State paramState)
  {
    this.mSmHandler.setInitialState(paramState);
  }
  
  public void start()
  {
    SmHandler localSmHandler = this.mSmHandler;
    if (localSmHandler == null) {
      return;
    }
    localSmHandler.completeConstruction();
  }
  
  protected final void transitionTo(IState paramIState)
  {
    this.mSmHandler.transitionTo(paramIState);
  }
  
  protected final void transitionToHaltingState()
  {
    SmHandler localSmHandler = this.mSmHandler;
    localSmHandler.transitionTo(localSmHandler.mHaltingState);
  }
  
  protected void unhandledMessage(Message paramMessage)
  {
    if (this.mSmHandler.mDbg)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(" - unhandledMessage: msg.what=");
      localStringBuilder.append(paramMessage.what);
      LogUtils.d("StateMachine", localStringBuilder.toString());
    }
  }
  
  private static class SmHandler
    extends Handler
  {
    private static final Object mSmHandlerObj = new Object();
    private boolean mDbg = false;
    private ArrayList<Message> mDeferredMessages = new ArrayList();
    private State mDestState;
    private HaltingState mHaltingState = new HaltingState(null);
    private boolean mHasQuit = false;
    private State mInitialState;
    private boolean mIsConstructionCompleted;
    private Message mMsg;
    private QuittingState mQuittingState = new QuittingState(null);
    private StateMachine mSm;
    private HashMap<State, StateInfo> mStateInfo = new HashMap();
    private StateInfo[] mStateStack;
    private int mStateStackTopIndex = -1;
    private StateInfo[] mTempStateStack;
    private int mTempStateStackCount;
    
    private SmHandler(Looper paramLooper, StateMachine paramStateMachine)
    {
      super();
      this.mSm = paramStateMachine;
      addState(this.mHaltingState, null);
      addState(this.mQuittingState, null);
    }
    
    private final StateInfo addState(State paramState1, State paramState2)
    {
      if (this.mDbg)
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("addStateInternal: E state=");
        ((StringBuilder)localObject2).append(paramState1.getName());
        ((StringBuilder)localObject2).append(",parent=");
        if (paramState2 == null) {
          localObject1 = "";
        } else {
          localObject1 = paramState2.getName();
        }
        ((StringBuilder)localObject2).append((String)localObject1);
        LogUtils.d("StateMachine", ((StringBuilder)localObject2).toString());
      }
      if (paramState2 != null)
      {
        localObject1 = (StateInfo)this.mStateInfo.get(paramState2);
        if (localObject1 == null) {
          paramState2 = addState(paramState2, null);
        } else {
          paramState2 = (State)localObject1;
        }
      }
      else
      {
        paramState2 = null;
      }
      Object localObject2 = (StateInfo)this.mStateInfo.get(paramState1);
      Object localObject1 = localObject2;
      if (localObject2 == null)
      {
        localObject1 = new StateInfo(null);
        this.mStateInfo.put(paramState1, localObject1);
      }
      if ((((StateInfo)localObject1).parentStateInfo != null) && (((StateInfo)localObject1).parentStateInfo != paramState2)) {
        throw new RuntimeException("state already added");
      }
      ((StateInfo)localObject1).state = paramState1;
      ((StateInfo)localObject1).parentStateInfo = paramState2;
      ((StateInfo)localObject1).active = false;
      paramState1 = new StringBuilder();
      paramState1.append("addStateInternal: X stateInfo: ");
      paramState1.append(localObject1);
      LogUtils.d("StateMachine", paramState1.toString());
      return (StateInfo)localObject1;
    }
    
    private final void cleanupAfterQuitting()
    {
      StateMachine.access$302(this.mSm, null);
      this.mSm = null;
      this.mMsg = null;
      this.mStateStack = null;
      this.mTempStateStack = null;
      this.mStateInfo.clear();
      this.mInitialState = null;
      this.mDestState = null;
      this.mDeferredMessages.clear();
      this.mHasQuit = true;
    }
    
    private final void completeConstruction()
    {
      LogUtils.d("StateMachine", "completeConstruction: E");
      Iterator localIterator = this.mStateInfo.values().iterator();
      int j = 0;
      while (localIterator.hasNext())
      {
        localObject = (StateInfo)localIterator.next();
        int i = 0;
        while (localObject != null)
        {
          localObject = ((StateInfo)localObject).parentStateInfo;
          i += 1;
        }
        if (j < i) {
          j = i;
        }
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("completeConstruction: maxDepth=");
      ((StringBuilder)localObject).append(j);
      LogUtils.d("StateMachine", ((StringBuilder)localObject).toString());
      this.mStateStack = new StateInfo[j];
      this.mTempStateStack = new StateInfo[j];
      setupInitialStateStack();
      sendMessageAtFrontOfQueue(obtainMessage(-2, mSmHandlerObj));
      LogUtils.d("StateMachine", "completeConstruction: X");
    }
    
    private final void deferMessage(Message paramMessage)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("deferMessage: msg=");
      ((StringBuilder)localObject).append(paramMessage.what);
      LogUtils.d("StateMachine", ((StringBuilder)localObject).toString());
      localObject = obtainMessage();
      ((Message)localObject).copyFrom(paramMessage);
      this.mDeferredMessages.add(localObject);
    }
    
    private final Message getCurrentMessage()
    {
      return this.mMsg;
    }
    
    private final IState getCurrentState()
    {
      return this.mStateStack[this.mStateStackTopIndex].state;
    }
    
    private final void invokeEnterMethods(int paramInt)
    {
      while (paramInt <= this.mStateStackTopIndex)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("invokeEnterMethods: ");
        localStringBuilder.append(this.mStateStack[paramInt].state.getName());
        LogUtils.d("StateMachine", localStringBuilder.toString());
        this.mStateStack[paramInt].state.enter();
        this.mStateStack[paramInt].active = true;
        paramInt += 1;
      }
    }
    
    private final void invokeExitMethods(StateInfo paramStateInfo)
    {
      for (;;)
      {
        int i = this.mStateStackTopIndex;
        if (i < 0) {
          break;
        }
        Object localObject = this.mStateStack;
        if (localObject[i] == paramStateInfo) {
          break;
        }
        localObject = localObject[i].state;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("invokeExitMethods: ");
        localStringBuilder.append(((State)localObject).getName());
        LogUtils.d("StateMachine", localStringBuilder.toString());
        ((State)localObject).exit();
        localObject = this.mStateStack;
        i = this.mStateStackTopIndex;
        localObject[i].active = false;
        this.mStateStackTopIndex = (i - 1);
      }
    }
    
    private final boolean isDbg()
    {
      return this.mDbg;
    }
    
    private final boolean isQuit(Message paramMessage)
    {
      return (paramMessage.what == -1) && (paramMessage.obj == mSmHandlerObj);
    }
    
    private final void moveDeferredMessageAtFrontOfQueue()
    {
      int i = this.mDeferredMessages.size() - 1;
      while (i >= 0)
      {
        Message localMessage = (Message)this.mDeferredMessages.get(i);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("moveDeferredMessageAtFrontOfQueue; what=");
        localStringBuilder.append(localMessage.what);
        LogUtils.d("StateMachine", localStringBuilder.toString());
        sendMessageAtFrontOfQueue(localMessage);
        i -= 1;
      }
      this.mDeferredMessages.clear();
    }
    
    private final int moveTempStateStackToStateStack()
    {
      int k = this.mStateStackTopIndex + 1;
      int i = this.mTempStateStackCount - 1;
      int j = k;
      StringBuilder localStringBuilder;
      while (i >= 0)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("moveTempStackToStateStack: i=");
        localStringBuilder.append(i);
        localStringBuilder.append(",j=");
        localStringBuilder.append(j);
        LogUtils.d("StateMachine", localStringBuilder.toString());
        this.mStateStack[j] = this.mTempStateStack[i];
        j += 1;
        i -= 1;
      }
      this.mStateStackTopIndex = (j - 1);
      if (this.mDbg)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("moveTempStackToStateStack: X mStateStackTop=");
        localStringBuilder.append(this.mStateStackTopIndex);
        localStringBuilder.append(",startingIndex=");
        localStringBuilder.append(k);
        localStringBuilder.append(",Top=");
        localStringBuilder.append(this.mStateStack[this.mStateStackTopIndex].state.getName());
        LogUtils.d("StateMachine", localStringBuilder.toString());
      }
      return k;
    }
    
    private void performTransitions(State paramState, Message paramMessage)
    {
      paramState = this.mStateStack[this.mStateStackTopIndex].state;
      if (this.mSm.recordLogRec(this.mMsg))
      {
        paramState = paramMessage.obj;
        paramState = mSmHandlerObj;
      }
      paramState = this.mDestState;
      paramMessage = paramState;
      if (paramState != null)
      {
        for (;;)
        {
          LogUtils.d("StateMachine", "handleMessage: new destination call exit/enter");
          invokeExitMethods(setupTempStateStackWithStatesToEnter(paramState));
          invokeEnterMethods(moveTempStateStackToStateStack());
          moveDeferredMessageAtFrontOfQueue();
          paramMessage = this.mDestState;
          if (paramState == paramMessage) {
            break;
          }
          paramState = paramMessage;
        }
        this.mDestState = null;
        paramMessage = paramState;
      }
      if (paramMessage != null)
      {
        if (paramMessage == this.mQuittingState)
        {
          this.mSm.onQuitting();
          cleanupAfterQuitting();
          return;
        }
        if (paramMessage == this.mHaltingState) {
          this.mSm.onHalting();
        }
      }
    }
    
    private final State processMsg(Message paramMessage)
    {
      Object localObject2 = this.mStateStack[this.mStateStackTopIndex];
      if (this.mDbg)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("processMsg: ");
        ((StringBuilder)localObject1).append(((StateInfo)localObject2).state.getName());
        LogUtils.d("StateMachine", ((StringBuilder)localObject1).toString());
      }
      Object localObject1 = localObject2;
      if (isQuit(paramMessage)) {
        transitionTo(this.mQuittingState);
      } else {
        for (;;)
        {
          localObject2 = localObject1;
          if (((StateInfo)localObject1).state.processMessage(paramMessage)) {
            break;
          }
          localObject2 = ((StateInfo)localObject1).parentStateInfo;
          if (localObject2 == null)
          {
            this.mSm.unhandledMessage(paramMessage);
            break;
          }
          localObject1 = localObject2;
          if (this.mDbg)
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("processMsg: ");
            ((StringBuilder)localObject1).append(((StateInfo)localObject2).state.getName());
            LogUtils.d("StateMachine", ((StringBuilder)localObject1).toString());
            localObject1 = localObject2;
          }
        }
      }
      if (localObject2 != null) {
        return ((StateInfo)localObject2).state;
      }
      return null;
    }
    
    private final void quit()
    {
      LogUtils.d("StateMachine", "quit:");
      sendMessage(obtainMessage(-1, mSmHandlerObj));
    }
    
    private final void quitNow()
    {
      LogUtils.d("StateMachine", "quitNow:");
      sendMessageAtFrontOfQueue(obtainMessage(-1, mSmHandlerObj));
    }
    
    private final void setDbg(boolean paramBoolean)
    {
      this.mDbg = paramBoolean;
    }
    
    private final void setInitialState(State paramState)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("setInitialState: initialState=");
      localStringBuilder.append(paramState.getName());
      LogUtils.d("StateMachine", localStringBuilder.toString());
      this.mInitialState = paramState;
    }
    
    private final void setupInitialStateStack()
    {
      if (this.mDbg)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("setupInitialStateStack: E mInitialState=");
        ((StringBuilder)localObject).append(this.mInitialState.getName());
        LogUtils.d("StateMachine", ((StringBuilder)localObject).toString());
      }
      Object localObject = (StateInfo)this.mStateInfo.get(this.mInitialState);
      for (int i = 0;; i = this.mTempStateStackCount + 1)
      {
        this.mTempStateStackCount = i;
        if (localObject == null) {
          break;
        }
        this.mTempStateStack[this.mTempStateStackCount] = localObject;
        localObject = ((StateInfo)localObject).parentStateInfo;
      }
      this.mStateStackTopIndex = -1;
      moveTempStateStackToStateStack();
    }
    
    private final StateInfo setupTempStateStackWithStatesToEnter(State paramState)
    {
      this.mTempStateStackCount = 0;
      paramState = (StateInfo)this.mStateInfo.get(paramState);
      Object localObject;
      do
      {
        localObject = this.mTempStateStack;
        int i = this.mTempStateStackCount;
        this.mTempStateStackCount = (i + 1);
        localObject[i] = paramState;
        localObject = paramState.parentStateInfo;
        if (localObject == null) {
          break;
        }
        paramState = (State)localObject;
      } while (!((StateInfo)localObject).active);
      if (this.mDbg)
      {
        paramState = new StringBuilder();
        paramState.append("setupTempStateStackWithStatesToEnter: X mTempStateStackCount=");
        paramState.append(this.mTempStateStackCount);
        paramState.append(",curStateInfo: ");
        paramState.append(localObject);
        LogUtils.d("StateMachine", paramState.toString());
      }
      return (StateInfo)localObject;
    }
    
    private final void transitionTo(IState paramIState)
    {
      this.mDestState = ((State)paramIState);
      paramIState = new StringBuilder();
      paramIState.append("transitionTo: destState=");
      paramIState.append(this.mDestState.getName());
      LogUtils.d("StateMachine", paramIState.toString());
    }
    
    public final void handleMessage(Message paramMessage)
    {
      if (!this.mHasQuit)
      {
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("handleMessage: E msg.what=");
        ((StringBuilder)localObject).append(paramMessage.what);
        LogUtils.d("StateMachine", ((StringBuilder)localObject).toString());
        this.mMsg = paramMessage;
        localObject = null;
        boolean bool = this.mIsConstructionCompleted;
        if (bool)
        {
          localObject = processMsg(paramMessage);
        }
        else
        {
          if ((bool) || (this.mMsg.what != -2) || (this.mMsg.obj != mSmHandlerObj)) {
            break label134;
          }
          this.mIsConstructionCompleted = true;
          invokeEnterMethods(0);
        }
        performTransitions((State)localObject, paramMessage);
        if ((this.mDbg) && (this.mSm != null))
        {
          LogUtils.d("StateMachine", "handleMessage: X");
          return;
          label134:
          return;
        }
      }
    }
    
    private class HaltingState
      extends State
    {
      private HaltingState() {}
      
      public boolean processMessage(Message paramMessage)
      {
        StateMachine.this.haltedProcessMessage(paramMessage);
        return true;
      }
    }
    
    private class QuittingState
      extends State
    {
      private QuittingState() {}
      
      public boolean processMessage(Message paramMessage)
      {
        return false;
      }
    }
    
    private class StateInfo
    {
      boolean active;
      StateInfo parentStateInfo;
      State state;
      
      private StateInfo() {}
      
      public String toString()
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("state=");
        localStringBuilder.append(this.state.getName());
        localStringBuilder.append(",active=");
        localStringBuilder.append(this.active);
        localStringBuilder.append(",parent=");
        Object localObject = this.parentStateInfo;
        if (localObject == null) {
          localObject = "null";
        } else {
          localObject = ((StateInfo)localObject).state.getName();
        }
        localStringBuilder.append((String)localObject);
        return localStringBuilder.toString();
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\state\StateMachine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */