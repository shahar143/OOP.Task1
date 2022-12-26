package observer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ConcreteMemberTest {
    GroupAdmin groupAdmin;
    ConcreteMember concreteMember;

    @BeforeEach
    void setUp(){
        groupAdmin = new GroupAdmin();
        concreteMember = new ConcreteMember("Ely");
    }

    @Test
    void subscribeNonExistent() {
        ArrayList<Member> expectedList = new ArrayList<>();
        expectedList.add(concreteMember);
        concreteMember.subscribe(groupAdmin);

        Assertions.assertEquals(expectedList, groupAdmin.getMembers());
    }

    @Test
    void subscribeToSameGroup(){
        ArrayList<Member> expectedList = new ArrayList<>();
        expectedList.add(concreteMember);
        concreteMember.subscribe(groupAdmin);

        concreteMember.subscribe(groupAdmin);

        Assertions.assertEquals(expectedList, groupAdmin.getMembers());
    }

    @Test
    void subscribeToDifferentGroup(){
        ArrayList<Member> expectedListGroup = new ArrayList<>();
        ArrayList<Member> expectedListGroup1 = new ArrayList<>();
        GroupAdmin groupAdmin1 = new GroupAdmin();

        expectedListGroup.add(concreteMember);
        concreteMember.subscribe(groupAdmin);

        concreteMember.subscribe(groupAdmin1);
        expectedListGroup1.add(concreteMember);
        expectedListGroup.remove(concreteMember);

        Assertions.assertEquals(expectedListGroup, groupAdmin.getMembers());
        Assertions.assertEquals(expectedListGroup1, groupAdmin1.getMembers());
    }

    @Test
    void unSubscribeExistingMember() {
        ArrayList<Member> expectedList = new ArrayList<>();
        expectedList.add(concreteMember);
        concreteMember.subscribe(groupAdmin);

        concreteMember.unSubscribe();
        expectedList.remove(concreteMember);

        Assertions.assertEquals(expectedList, groupAdmin.getMembers());
    }

    @Test
    void unSubscribUnregisteredMember(){
        ArrayList<Member> expectedList = new ArrayList<>();
        expectedList.add(concreteMember);
        concreteMember.subscribe(groupAdmin);

        concreteMember.unSubscribe();
        expectedList.remove(concreteMember);

        concreteMember.unSubscribe();

        Assertions.assertEquals(expectedList, groupAdmin.getMembers());
    }

    @Test
    void unSubscribeDifferentUnregisteredMember(){
        ArrayList<Member> expectedList = new ArrayList<>();
        expectedList.add(concreteMember);
        concreteMember.subscribe(groupAdmin);

        ConcreteMember otherMember = new ConcreteMember("name");
        otherMember.unSubscribe();
        expectedList.remove(otherMember);

        Assertions.assertEquals(expectedList, groupAdmin.getMembers());
    }

    @Test
    void getLocalUSB() {
        Assertions.assertInstanceOf(UndoAbleStringBuilder.class, groupAdmin.getCurrentUsb());
    }

    @Test
    void updateInsert() {
        UndoAbleStringBuilder undoAbleStringBuilder = new UndoAbleStringBuilder();
        undoAbleStringBuilder.insert(0, "Hello");
        undoAbleStringBuilder.insert(5, " world");

        concreteMember.update(undoAbleStringBuilder);
        String expectedResult = "Hello world";

        Assertions.assertEquals(expectedResult, undoAbleStringBuilder.toString());
    }

    @Test
    void updateAppend(){
        UndoAbleStringBuilder undoAbleStringBuilder = new UndoAbleStringBuilder();
        undoAbleStringBuilder.append("Hello");
        undoAbleStringBuilder.append("world");

        concreteMember.update(undoAbleStringBuilder);
        String expectedResult = "Helloworld";

        Assertions.assertEquals(expectedResult, undoAbleStringBuilder.toString());
    }

    @Test
    void updateDelete(){
        UndoAbleStringBuilder undoAbleStringBuilder = new UndoAbleStringBuilder();
        undoAbleStringBuilder.append("Hello");
        undoAbleStringBuilder.delete(4,5);

        concreteMember.update(undoAbleStringBuilder);
        String expectedResult = "Hell";

        Assertions.assertEquals(expectedResult, undoAbleStringBuilder.toString());
    }

    @Test
    void updateUndo(){
        UndoAbleStringBuilder undoAbleStringBuilder = new UndoAbleStringBuilder();
        undoAbleStringBuilder.append("Hello");
        undoAbleStringBuilder.undo();

        concreteMember.update(undoAbleStringBuilder);
        String expectedResult = "";

        Assertions.assertEquals(expectedResult, undoAbleStringBuilder.toString());
    }

}