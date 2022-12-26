import observer.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);
    GroupAdmin groupAdmin;
    ConcreteMember concreteMember;
    GroupAdmin usbObservable = new GroupAdmin();

    @BeforeEach
    void setUp(){
        groupAdmin = new GroupAdmin();
        concreteMember = new ConcreteMember("Ely");

        usbObservable.append("testing");
        usbObservable.notify_members();

    }

    //groupAdminTests
    @Test
    void register(){
        ConcreteMember member = new ConcreteMember("Yosi", usbObservable);
        assertTrue(usbObservable.containsMember(member));
    }

    @Test
    void getUpdatesAfterRegistration(){
        ConcreteMember member = new ConcreteMember("Shimon", usbObservable);
        usbObservable.append(" class");
        usbObservable.notify_members();
        assertEquals(usbObservable.getCurrentUSB().toString(), member.getLocalUSB().toString());
    }

    @Test
    void unregister() {
        ConcreteMember member = new ConcreteMember("Yosi", usbObservable);
        usbObservable.unregister(member);
        assertFalse(usbObservable.containsMember(member));
    }

    @Test
    void insert() {
        UndoableStringBuilder beforeInsert = usbObservable.getCurrentUSB();
        UndoableStringBuilder afterInsert = beforeInsert.insert(0 , "this is ");
        assertEquals("this is testing", afterInsert.toString());
    }

    @Test
    void insert1() {
        assertDoesNotThrow(() -> usbObservable.insert(usbObservable.toString().length() + 100, "hello"));
    }

    @Test
    void insert2() {
        assertDoesNotThrow(() -> usbObservable.insert(-1, "hello"));
    }

    @Test
    void insert3() {
        usbObservable.insert(0,null);
        assertEquals("nulltesting", usbObservable.getCurrentUSB().toString());
    }
    @Test
    void append() {
        UndoableStringBuilder beforeInsert = usbObservable.getCurrentUSB();
        UndoableStringBuilder afterInsert = beforeInsert.append(" you");
        assertEquals("testing you", afterInsert.toString());
    }

    @Test
    void append1() {
        usbObservable.append(null);
        assertEquals("testingnull", usbObservable.getCurrentUSB().toString());
    }

    @Test
    void delete(){
        usbObservable.delete(0,usbObservable.getCurrentUSB().toString().length());
        assertEquals("", usbObservable.getCurrentUSB().toString(), "Results don't match");
    }


    @Test
    void delete1(){
        assertDoesNotThrow(() -> usbObservable.delete(5,3));
    }

    @Test
    void delete2(){
        assertDoesNotThrow(() -> usbObservable.delete(-2, 3));
    }

    @Test
    void delete3() {
        assertDoesNotThrow(() -> usbObservable.delete(10, -2));
    }

    @Test
    void delete4() {
        assertDoesNotThrow(() -> usbObservable.delete(20, 1000));
    }

    @Test
    void undo(){
        usbObservable.append(" New String");
        assertDoesNotThrow(() -> usbObservable.undo());
        assertDoesNotThrow(() -> usbObservable.undo());
        assertDoesNotThrow(() -> usbObservable.undo());
        assertDoesNotThrow(() -> usbObservable.undo());
        assertEquals("", usbObservable.getCurrentUSB().toString());
    }

    @Test
    void notify_members() {
        ConcreteMember member1 = new ConcreteMember("Yosi", usbObservable);
        ConcreteMember member2 = new ConcreteMember("Shimon", usbObservable);
        usbObservable.append(" my");
        usbObservable.notify_members();
        assertEquals(usbObservable.getCurrentUSB(), member1.getLocalUSB());
        assertEquals(usbObservable.getCurrentUSB(), member2.getLocalUSB());
    }

    //concreteMemberTests
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
        Assertions.assertInstanceOf(UndoAbleStringBuilder.class, groupAdmin.getCurrentUSB());
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




    // stub method to check external dependencies compatibility
    @Test
    public void memoryTest(){
        GroupAdmin usbObservable = new GroupAdmin();
        usbObservable.append("hello");
        ConcreteMember member1 = new ConcreteMember("Eli", usbObservable);
        ConcreteMember member2 = new ConcreteMember("Shahar", usbObservable);

        System.out.println("member1 foot print: ");
        logger.info(()->JvmUtilities.objectFootprint(member1));
        //logger.info(()->JvmUtilities.objectTotalSize(member1));
        System.out.println();

        System.out.println("member2 foot print: ");
        logger.info(()->JvmUtilities.objectFootprint(member2));
        //logger.info(()->JvmUtilities.objectTotalSize(member2));
        System.out.println();

        usbObservable.append("hello");

        System.out.println("member1 foot print after append(): ");
        logger.info(()->JvmUtilities.objectFootprint(member1));
        //logger.info(()->JvmUtilities.objectTotalSize(member1));
        System.out.println();

        System.out.println("member2 foot print after append(): ");
        logger.info(()->JvmUtilities.objectFootprint(member2));
        //logger.info(()->JvmUtilities.objectTotalSize(member2));
        System.out.println();

        //logger.info(() -> JvmUtilities.jvmInfo());
    }
}
