package observer;

public class Ex1 {
    public static void main(String[] args) {
        GroupAdmin groupAdmin = new GroupAdmin();
        ConcreteMember member1 = new ConcreteMember("Ely", groupAdmin);
        ConcreteMember member2 = new ConcreteMember("Shahar", groupAdmin);

        groupAdmin.append("Hello members");
        System.out.println(member2.getUpdateStack());
        System.out.println(member1.getUpdateStack());

        groupAdmin.delete(0, 6);
        //BUG consider removing stack attribute from ConcreteMember.
        System.out.println(member2.getUpdateStack());
        System.out.println(member1.getUpdateStack());

        groupAdmin.unregister(member1);
        groupAdmin.unregister(member2);

        groupAdmin.append("Update members");
        //BUG Members get updated after unregistering from groupAdmin.
        System.out.println(member2.getUpdateStack());
        System.out.println(member1.getUpdateStack());

    }
}
