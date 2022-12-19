package observer;

import java.util.ArrayList;

public class GroupAdmin implements  Sender{
    ArrayList<Member> members = new ArrayList<>();
    UndoableStringBuilder usb;

    public GroupAdmin(){
        members = new ArrayList<>();
        usb = new UndoableStringBuilder();
    }

    @Override
    public void register(Member obj) {
        if(!(members.contains(obj))) members.add(obj);
    }

    @Override
    public void unregister(Member obj) {
        members.remove(obj);
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
        for(int i = 0; i < members.size(); i++){
            members.get(i).update(change);
        }
    }

    public UndoableStringBuilder getCurrentString(){
        return usb;
    }
}
