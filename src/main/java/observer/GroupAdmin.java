package observer;

import java.util.ArrayList;

/**
 * The observable class.
 */
public class GroupAdmin implements Sender{
    private final ArrayList<Member> members;
    private final UndoAbleStringBuilder usb;

    public GroupAdmin(){
        this.members = new ArrayList<>();
        usb = new UndoAbleStringBuilder();
    }


    /**
     * Gets the arraylist of members of the observable class.
     * @return Arraylist of members.
     */
    public ArrayList<Member> getMembers(){
        return this.members;
    }


    /**
     * Gets the observables UndoableStringBuilder reference.
     * @return UndoAbleStringBuilder reference.
     */
    public UndoAbleStringBuilder getCurrentUSB() {
        return usb;
    }


    /**
     * Registers an observer Member instance to the current groupAdmin observer instance,
     * as long as it isn't registered yet to the groupAdmin.
     * Does so by removing the member instance from the member list.
     * @param obj Member instance to register to the groupAdmin
     */
    @Override
    public void register(Member obj) {
        if(!members.contains(obj))
            members.add(obj);
    }


    /**
     * Unregisters an observer Member instance from the observable by removing the member
     * from the member list.
     * @param obj Member instance to unregister from the groupAdmin.
     */
    @Override
    public void unregister(Member obj) {
        members.remove(obj);
    }


    /**
     * Performs string insertion on the UndoAbleStringBuilder instance, which results in changes
     * in all members referencing it.
     * Afterwards, notifies all registered members on the changes performed by calling notify_members().
     * @param offset From which index insert string.
     * @param obj String we wish to insert.
     */
    @Override
    public void insert(int offset, String obj) {
        usb.insert(offset, obj);
        notify_members();
    }


    /**
     * Preforms String appending on the UndoAbleStringBuilder instance, which results in changes
     * in all members referencing it.
     * Afterwards, notifies all registered members on the changes performed by calling notify_members().
     * @param obj String we wish to append.
     */
    @Override
    public void append(String obj) {
        usb.append(obj);
        notify_members();
    }


    /**
     * Preforms String deletion on the UndoAbleStringBuilder instance, which results in changes
     * in all members referencing it.
     * Afterwards, notifies all registered members on the changes performed by calling notify_members().
     * @param start Starting index of the deletion. Included.
     * @param end Ending index of the deletion. Excluded.
     */
    @Override
    public void delete(int start, int end) {
        usb.delete(start, end);
        notify_members();
    }


    /**
     * Preforms String undoing on the UndoAbleStringBuilder instance, which results in changes
     * in all members referencing it.
     * Afterwards, notifies all registered members on the changes performed by calling notify_members().
     */
    @Override
    public void undo() {
        usb.undo();
        notify_members();
    }


    /**
     * Notifies all registered members in the observable instance of the changes performed
     * on the observer's UndoAbleStringBuilder instance by calling the update method on each registered member.
     */
    public void notify_members(){
        for(Member member: members){
            member.update(usb);
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "GroupAdmin{" +
                "members=" + members +
                '}';
    }

    public boolean containsMember(ConcreteMember member) {
        return this.members.contains(member);
    }
}
