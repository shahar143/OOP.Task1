package observer;

public class ConcreteMember implements Member{
    private final String name;
    private UndoableStringBuilder localUSB;

    public ConcreteMember(String name){
        this.name = name;
        this.localUSB = null;
    }

    @Override
    public void update(UndoableStringBuilder usb) {
        this.localUSB = usb;
        System.out.println(this.name + " has a new value -> " + this.localUSB);
    }
}


