package observer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class GroupAdminTest {
    GroupAdmin groupAdmin;
    ConcreteMember concreteMember1;
    ConcreteMember concreteMember2;
    @BeforeEach
    void setUp(){
        groupAdmin = new GroupAdmin();
        concreteMember1 = new ConcreteMember("Ely");
        concreteMember2 = new ConcreteMember("Shahar");
    }

    @Test
    void testRegisterMemberNotFound() {
        ArrayList<Member> testList= new ArrayList<>(); //Contains a list of the 2 members expected.
        groupAdmin.register(concreteMember1);
        testList.add(concreteMember1);
        groupAdmin.register(concreteMember2);
        testList.add(concreteMember2);

        Assertions.assertEquals(testList, groupAdmin.getMembers());
    }

    @Test
    void testRegisterExistingMember(){
        //Same insertions as before.
        ArrayList<Member> testList= new ArrayList<>(); //Contains a list of the 2 members expected.
        groupAdmin.register(concreteMember1);
        testList.add(concreteMember1);
        groupAdmin.register(concreteMember2);
        testList.add(concreteMember2);

        //Repeated insertion testing. Should avoid duplication.
        groupAdmin.register(concreteMember1);
        groupAdmin.register(concreteMember2);
        groupAdmin.register(concreteMember2);
        groupAdmin.register(concreteMember1);

        Assertions.assertEquals(testList, groupAdmin.getMembers());
    }

    @Test
    void unregisterExistingMember() {
        ArrayList<Member> testList= new ArrayList<>(); //Contains a list of the 2 members expected.
        groupAdmin.register(concreteMember1);
        groupAdmin.register(concreteMember2);

        groupAdmin.unregister(concreteMember1);
        groupAdmin.unregister(concreteMember2);

        Assertions.assertEquals(testList, groupAdmin.getMembers());
    }

    @Test
    void unregisterNonExistingMember(){
        ArrayList<Member> testList= new ArrayList<>(); //Contains a list of the 2 members expected.

        groupAdmin.unregister(concreteMember1);
        groupAdmin.unregister(concreteMember2);

        Assertions.assertEquals(testList, groupAdmin.getMembers());
    }

    @Test
    void insertToEmptyString() {
        String expectedString = "Hello";
        groupAdmin.insert(0,"Hello");
        Assertions.assertEquals(expectedString, groupAdmin.getUsb().toString());
    }

    @Test
    void insertToNonEmptyString(){
        String beforeInsertion = "Hello";
        groupAdmin.insert(0, beforeInsertion);

        String afterInsertion = "HellWorldo";
        groupAdmin.insert(4, "World");
        Assertions.assertEquals(afterInsertion, groupAdmin.getUsb().toString());
    }

    @Test
    void insertErrorMinusTest(){
        String beforeInsertion = "Hello";
        groupAdmin.insert(0, beforeInsertion);

        String insertedString = "world";

        Assertions.assertDoesNotThrow(() -> groupAdmin.insert(-1, insertedString));
    }

    @Test
    void appendEmptyString() {
        String expectedString = "Hello world";
        groupAdmin.append("Hello world");

        Assertions.assertEquals(expectedString, groupAdmin.getUsb().toString());
    }

    @Test
    void appendNonEmptyString(){
        String oldString = "Hello world";
        groupAdmin.append(oldString);

        String appendedString = " I am born";
        String newString = "Hello world" + appendedString;
        groupAdmin.append(appendedString);

        Assertions.assertEquals(newString, groupAdmin.getUsb().toString());
    }

    @Test
    void delete() {

    }

    @Test
    void undo() {
    }

    @Test
    void notify_members() {
    }
}