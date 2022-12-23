package observer;

public class Ex1 {
    public static void main(String[] args) {
        GroupAdmin groupAdmin = new GroupAdmin();
        Member member1 = new ConcreteMember("Ely");
        Member member2 = new ConcreteMember("Shahar");
        groupAdmin.register(member1);
        groupAdmin.register(member2);

        groupAdmin.append("Hello");
        groupAdmin.append(" World");

        groupAdmin.unregister(member1);

        groupAdmin.delete(0, 6);





    }
}
