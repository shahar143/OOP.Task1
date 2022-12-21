package observer;

import java.util.ArrayList;

public class GroupAdmin implements Sender{
    private ArrayList<ConcreteMember> members;
    private UndoableStringBuilder usb;

    public GroupAdmin(){
        members = new ArrayList<>();
        usb = new UndoableStringBuilder();
    }

    @Override
    public void register(Member obj) {
        if(!(members.contains((ConcreteMember) obj))){
            members.add((ConcreteMember) obj);
            ((ConcreteMember) obj).setGroupAdmin(this);
        }
    }

    @Override
    public void unregister(Member obj) {
        members.remove((ConcreteMember) obj);
        ((ConcreteMember) obj).setGroupAdmin(null);
    }

    @Override
    public void insert(int offset, String obj) {
        usb.insert(offset, obj);
        notify_members(usb);
    }

    @Override
    public void append(String obj) {
        usb.append(obj);
        notify_members(usb);
    }

    @Override
    public void delete(int start, int end) {
        usb.delete(start, end);
        notify_members(usb);
    }

    @Override
    public void undo() {
        usb.undo();
        notify_members(usb);
    }

    public void notify_members(UndoableStringBuilder change){
        for (Member member : members) {
            member.update(change);
        }
    }

    public ArrayList<ConcreteMember> getMemberList(){
        return this.members;
    }

    public UndoableStringBuilder getCurrentString(){
        return usb;
    }
}
