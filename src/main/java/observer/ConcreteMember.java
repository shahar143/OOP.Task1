package observer;

import java.util.Stack;

public class ConcreteMember implements Member{
    private Stack<UndoableStringBuilder> updateStack;
    private UndoableStringBuilder usb;
    private GroupAdmin groupAdmin;
    private final String nickName;
    private boolean pushUpdates;

    public ConcreteMember(String nickName, GroupAdmin groupAdmin){
        this.nickName = nickName;
        setGroupAdmin(groupAdmin);
        registerMember();
        this.usb = groupAdmin.getCurrentString(); //Shallow copy of UndoableStringBuilder instance in the given groupAdmin.
        this.updateStack = new Stack<>();
        this.pushUpdates = true;
    }
    @Override
    public void update(UndoableStringBuilder usb) {
        this.updateStack.push(usb);
        if(pushUpdates){
            // this.usb = this.updateStack.pop();
            System.out.println("User " + this.nickName + " got notified with the message: " + this.usb);
        }

    }

    public void registerMember(){
        groupAdmin.register(this);
    }

    public Stack<UndoableStringBuilder> getUpdateStack(){
        System.out.print(this.nickName + " update stack -> ");
        return this.updateStack;
    }

    public boolean getPushUpdates(){
        return this.pushUpdates;
    }

    public void setPushUpdates() {
        this.pushUpdates = !getPushUpdates();
        System.out.println(this.nickName + " changed push updates status to " + this.pushUpdates);
    }

    public void setGroupAdmin(GroupAdmin groupAdmin){
        this.groupAdmin = groupAdmin;
    }

    @Override
    public String toString() {
        return this.nickName;
    }
}


