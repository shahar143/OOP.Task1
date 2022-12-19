package observer;

import java.util.Stack;

public class ConcreteMember implements Member{
    Stack<UndoableStringBuilder> updateStack;
    UndoableStringBuilder string;
    GroupAdmin group;
    private String nickName;
    boolean pushUpdates;

    public ConcreteMember(String nickName, GroupAdmin group){
        this.nickName = nickName;
        this.group = group;
        subscribe();
        this.string = group.getCurrentString();
        this.updateStack = new Stack<>();
        this.pushUpdates = true;
    }
    @Override
    public void update(UndoableStringBuilder usb) {
        updateStack.push(usb);
        if(pushUpdates == true) this.string = updateStack.pop();
    }

    public void subscribe(){
        group.register(this);
    }
}


