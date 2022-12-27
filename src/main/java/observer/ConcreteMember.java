package observer;


/**
 * The observer class
 */
public class ConcreteMember implements Member{
    private final String name;
    private UndoAbleStringBuilder localUSB;
    private GroupAdmin groupAdmin;

    public ConcreteMember(String name){
        this.name = name;
        this.localUSB = null;
    }

    public ConcreteMember(String name, GroupAdmin groupAdmin){
        this.name = name;
        subscribe(groupAdmin);
    }


    /**
     * An instance which calls this method subscribes to the given groupAdmin instance if it is not subscribed to it yet.
     * If the ConcreteMember instance already subscribed to the given groupAdmin, don't do anything.
     * If the ConcreteMember instance wants to subscribe to a different groupAdmin, perform switching of the groupAdmins.
     * @param groupAdmin The observable the ConcreteMember instance wishes to subscribe to.
     */
    public void subscribe(GroupAdmin groupAdmin){
        //ConcreteMember is already subscribed to this.groupAdmin, but wants to subscribe to a different one in the given parameter.
        if(this.groupAdmin != null && this.groupAdmin != groupAdmin){
            unSubscribe();
            groupAdmin.register(this);
        }
        else if(this.groupAdmin != null){
            System.err.println("Already subscribed");
        }
        else
            groupAdmin.register(this);

        this.groupAdmin = groupAdmin; //After each case, point to the given groupAdmin reference.
    }


    /**
     *  Unsubscribe the given ConcreteMember instance from its current groupAdmin reference by nullifying the groupAdmin reference,
     *  and unregistering it from the member list of groupAdmin, as long as it is referencing an observable groupAdmin.
     */
    public void unSubscribe(){
        if(this.groupAdmin != null){
            this.groupAdmin.unregister(this);
            this.groupAdmin = null;
        }
        else
            System.err.println("You are already unsubscribed to a group");
    }


    /**
     * Gets the UndoAbleStringBuilder object.
     * @return UndoAbleStringBuilder object reference.
     */
    public UndoAbleStringBuilder getLocalUSB(){
        return this.localUSB;
    }


    /**
     * Updates the ConcreteMember instance reference of UndoableStringBuilder and prints to the user the new value which the new
     * reference holds.
     * @param usb Reference of UndoableStringBuilder from which we get its string value.
     */
    @Override
    public void update(UndoableStringBuilder usb) {
        this.localUSB = (UndoAbleStringBuilder) usb;
        System.out.println(this.name + " has a new value -> " + this.localUSB);
    }

    @Override
    public String toString() {
        return "ConcreteMember{" +
                "name='" + name + '\'' +
                '}';
    }
}


