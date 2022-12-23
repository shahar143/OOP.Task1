package observer;


import java.util.ArrayList;

public class GroupAdmin implements Sender{
    private ArrayList<Member> members;
    private final UndoableStringBuilder usb;

    public GroupAdmin(){
        this.members = new ArrayList<>();
        usb = new UndoableStringBuilder();
    }

    @Override
    public void register(Member obj) {
        members.add(obj);
    }

    @Override
    public void unregister(Member obj) {
        members.remove(obj);
    }

    @Override
    public void insert(int offset, String obj) {
        usb.insert(offset, obj);
        notify_members();
    }

    @Override
    public void append(String obj) {
        usb.append(obj);
        notify_members();
    }

    @Override
    public void delete(int start, int end) {
        usb.delete(start, end);
        notify_members();
    }

    @Override
    public void undo() {
        usb.undo();
        notify_members();
    }

    public void notify_members(){
        for(Member member: members){
            member.update(usb);
        }
        System.out.println();
    }
}
