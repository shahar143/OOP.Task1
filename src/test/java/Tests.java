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
    GroupAdmin groupAdmin; //ConcreteMember testing groupAdmin instance.
    ConcreteMember concreteMember; //ConcreteMember testing instance.
    GroupAdmin usbObservable; //GroupAdmin testing instance.

    @BeforeEach
    void setUp(){
        groupAdmin = new GroupAdmin();
        concreteMember = new ConcreteMember("Ely");

        usbObservable = new GroupAdmin();
        usbObservable.append("testing");
        usbObservable.notify_members();

    }

    //groupAdminTests
    @Test
    void register(){
        ConcreteMember member = new ConcreteMember("Yosi", usbObservable);
        assertTrue(usbObservable.containsMember(member)); //Check whenever the member was successfully registered.
    }

    @Test
    void getUpdatesAfterRegistration(){
        ConcreteMember member = new ConcreteMember("Shimon", usbObservable);
        usbObservable.append(" class");
        usbObservable.notify_members();
        assertEquals(usbObservable.getCurrentUSB().toString(), member.getLocalUSB().toString()); //Compare observer and observable String values.
    }

    @Test
    void unregister() {
        ConcreteMember member = new ConcreteMember("Yosi", usbObservable);
        usbObservable.unregister(member);
        assertFalse(usbObservable.containsMember(member)); //Check if the unregistered member is not in the member list of the observable.
    }

    @Test
    void insert() {
        UndoableStringBuilder beforeInsert = usbObservable.getCurrentUSB(); //Instance of UndoableStringBuilder before insertion.
        UndoableStringBuilder afterInsert = beforeInsert.insert(0 , "this is "); //After insertion
        assertEquals("this is testing", afterInsert.toString()); //Compare string values with instance of UndoableStringBuilder.
    }

    @Test
    void insert1() {
        //Error check of situation where offset is out of bounds.
        //Passes if the exception is handled.
        assertDoesNotThrow(() -> usbObservable.insert(usbObservable.toString().length() + 100, "hello"));
    }

    @Test
    void insert2() {
        //Error check of situation where offset is out of bounds.
        //Passes if the exception is handled.
        assertDoesNotThrow(() -> usbObservable.insert(-1, "hello"));
    }

    @Test
    void insert3() {
        usbObservable.insert(0,null);
        assertEquals("nulltesting", usbObservable.getCurrentUSB().toString()); //null insertion comparison.
    }
    @Test
    void append() {
        UndoableStringBuilder beforeAppend = usbObservable.getCurrentUSB();
        UndoableStringBuilder afterAppend = beforeAppend.append(" you");
        assertEquals("testing you", afterAppend.toString()); //String comparison on references after appending.
    }

    @Test
    void append1() {
        usbObservable.append(null);
        assertEquals("testingnull", usbObservable.getCurrentUSB().toString()); //null appending test
    }

    @Test
    void delete(){
        usbObservable.delete(0,usbObservable.getCurrentUSB().toString().length());
        assertEquals("", usbObservable.getCurrentUSB().toString(), "Results don't match after deletion"); //Deletion test
    }


    @Test
    void delete1(){
        //Error check of situation where deletion bounds are not correct.
        //Passes if the exception is handled.
        assertDoesNotThrow(() -> usbObservable.delete(5,3));
    }

    @Test
    void delete2(){
        //Error check of situation where deletion bounds are not correct.
        //Passes if the exception is handled.
        assertDoesNotThrow(() -> usbObservable.delete(-2, 3));
    }

    @Test
    void delete3() {
        //Error check of situation where deletion bounds are not correct.
        //Passes if the exception is handled.
        assertDoesNotThrow(() -> usbObservable.delete(10, -2));
    }

    @Test
    void delete4() {
        //Error check of situation where deletion bounds are not correct.
        //Passes if the exception is handled.
        assertDoesNotThrow(() -> usbObservable.delete(20, 1000));
    }

    @Test
    void undo(){
        //Test undo function value correctness and error checking
        //Error test passes if exception is handled.
        usbObservable.append(" New String");
        assertDoesNotThrow(() -> usbObservable.undo());
        assertDoesNotThrow(() -> usbObservable.undo());
        assertDoesNotThrow(() -> usbObservable.undo());
        assertDoesNotThrow(() -> usbObservable.undo());
        assertEquals("", usbObservable.getCurrentUSB().toString());
    }

    @Test
    void notify_members() {
        //Tests members getting the string change notifications.
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
        //Test simple subscription, given the member is not subscribed to an observable class yet.
        ArrayList<Member> expectedList = new ArrayList<>();
        expectedList.add(concreteMember);
        concreteMember.subscribe(groupAdmin);

        Assertions.assertEquals(expectedList, groupAdmin.getMembers());
    }

    @Test
    void subscribeToSameGroup(){
        //Test subscribe in situation where member is already subscribed to the same observable given.
        ArrayList<Member> expectedList = new ArrayList<>();
        expectedList.add(concreteMember);
        concreteMember.subscribe(groupAdmin);

        concreteMember.subscribe(groupAdmin);

        Assertions.assertEquals(expectedList, groupAdmin.getMembers());
    }

    @Test
    void subscribeToDifferentGroup(){
        //Test subscribe in situation where member wants to subscribe to a different observable, when he is already subscribed to an existing one,
        //different from the one he wants to subscribe to now.
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
        //Test simple unsubscribe. Unsubscribe member from the groupAdmin he is subscribed to now.
        ArrayList<Member> expectedList = new ArrayList<>();
        expectedList.add(concreteMember);
        concreteMember.subscribe(groupAdmin);

        concreteMember.unSubscribe();
        expectedList.remove(concreteMember);

        Assertions.assertEquals(expectedList, groupAdmin.getMembers());
    }

    @Test
    void unSubscribeUnregisteredMember(){
        //Test unsubscribe situation where member wishes to unsubscribe from an observable he is already unsubscribed to.
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
        //Test unsubscribe situation where a member who was never in a groupAdmin wishes to unsubscribe from it.
        ArrayList<Member> expectedList = new ArrayList<>();
        expectedList.add(concreteMember);
        concreteMember.subscribe(groupAdmin);

        ConcreteMember otherMember = new ConcreteMember("name");
        otherMember.unSubscribe();
        expectedList.remove(otherMember);

        Assertions.assertEquals(expectedList, groupAdmin.getMembers());
    }

    @Test
    void testGetLocalUSB() {
        //Test if the function returns the instance of it.
        Assertions.assertInstanceOf(UndoableStringBuilder.class, groupAdmin.getCurrentUSB());
    }

    @Test
    void updateInsert() {
        //Test update function on a single member.
        UndoableStringBuilder undoAbleStringBuilder = new UndoableStringBuilder();
        undoAbleStringBuilder.insert(0, "Hello");
        undoAbleStringBuilder.insert(5, " world");

        concreteMember.update(undoAbleStringBuilder);
        String expectedResult = "Hello world";

        Assertions.assertEquals(expectedResult, undoAbleStringBuilder.toString());
    }

    @Test
    void updateAppend(){
        //Test append function on a single member.
        UndoableStringBuilder undoAbleStringBuilder = new UndoableStringBuilder();
        undoAbleStringBuilder.append("Hello");
        undoAbleStringBuilder.append("world");

        concreteMember.update(undoAbleStringBuilder);
        String expectedResult = "Helloworld";

        Assertions.assertEquals(expectedResult, undoAbleStringBuilder.toString());
    }

    @Test
    void updateDelete(){
        //Test delete function on a single member.
        UndoableStringBuilder undoAbleStringBuilder = new UndoableStringBuilder();
        undoAbleStringBuilder.append("Hello");
        undoAbleStringBuilder.delete(4,5);

        concreteMember.update(undoAbleStringBuilder);
        String expectedResult = "Hell";

        Assertions.assertEquals(expectedResult, undoAbleStringBuilder.toString());
    }

    @Test
    void updateUndo(){
        //Test undo function on a single member.
        UndoableStringBuilder undoAbleStringBuilder = new UndoableStringBuilder();
        undoAbleStringBuilder.append("Hello");
        undoAbleStringBuilder.undo();

        concreteMember.update(undoAbleStringBuilder);
        String expectedResult = "";

        Assertions.assertEquals(expectedResult, undoAbleStringBuilder.toString());
    }


    @Test
    void memoryMemberSubscriptionTest(){
        //Test memory change of member side subscribing to the groupAdmin.
        ConcreteMember member1 = new ConcreteMember("Ely");
        ConcreteMember member2 = new ConcreteMember("Shahar");
        ConcreteMember member3 = new ConcreteMember("A very very very long name");

        logger.info(()->JvmUtilities.objectFootprint(member1));
        logger.info(()->JvmUtilities.objectFootprint(member2));
        logger.info(()->JvmUtilities.objectFootprint(member3));

        member1.subscribe(groupAdmin);
        member2.subscribe(groupAdmin);
        member3.subscribe(groupAdmin);

        logger.info(()->JvmUtilities.objectFootprint(member1));
        logger.info(()->JvmUtilities.objectFootprint(member2));
        logger.info(()->JvmUtilities.objectFootprint(member3));
    }


    @Test
    void memoryRegisteringTest(){
        //Test registration memory change
        System.out.println("Empty groupAdmin memory size");
        logger.info(() -> JvmUtilities.objectFootprint(groupAdmin));

        groupAdmin.register(concreteMember);
        groupAdmin.register(new ConcreteMember("Shahar"));

        System.out.println("groupAdmin memory size after registering 2 members");
        logger.info(() -> JvmUtilities.objectFootprint(groupAdmin));
    }


    @Test
    void memoryUnregisterTest(){
        //Test Unregistering memory change
        System.out.println("Empty groupAdmin memory size");
        logger.info(() -> JvmUtilities.objectFootprint(groupAdmin));

        groupAdmin.register(concreteMember);
        logger.info(() -> JvmUtilities.objectFootprint(groupAdmin));
        logger.info(() -> JvmUtilities.objectFootprint(concreteMember));

        groupAdmin.unregister(concreteMember);
        logger.info(() -> JvmUtilities.objectFootprint(groupAdmin));
        logger.info(() -> JvmUtilities.objectFootprint(concreteMember));

    }



    @Test
    void memoryAppendTest(){
        //Test append memory change on both subject and observer.
        concreteMember.subscribe(groupAdmin);
        logger.info(() -> JvmUtilities.objectFootprint(groupAdmin));

        groupAdmin.append("A very long string to append");
        logger.info(() -> JvmUtilities.objectFootprint(groupAdmin));
        logger.info(() -> JvmUtilities.objectFootprint(concreteMember));

    }

    @Test
    void memoryInsertTest(){
        //Test insert memory change on both subject and observer.
        concreteMember.subscribe(groupAdmin);
        logger.info(() -> JvmUtilities.objectFootprint(groupAdmin));

        groupAdmin.insert(0, "A very very very very long string to insert");
        logger.info(() -> JvmUtilities.objectFootprint(groupAdmin));
        logger.info(() -> JvmUtilities.objectFootprint(concreteMember));
    }

    @Test
    void memoryDeleteTest(){
        //Test delete memory change on both subject and observer.
        concreteMember.subscribe(groupAdmin);
        logger.info(() -> JvmUtilities.objectFootprint(groupAdmin));

        groupAdmin.append("A very very very very long string to append");
        logger.info(() -> JvmUtilities.objectFootprint(groupAdmin));

        groupAdmin.delete(0, 10);
        logger.info(() -> JvmUtilities.objectFootprint(groupAdmin));
        logger.info(() -> JvmUtilities.objectFootprint(concreteMember));
    }


    @Test
    void memoryUndoTest(){
        //Test undo memory change on both subject and observer.
        concreteMember.subscribe(groupAdmin);
        logger.info(() -> JvmUtilities.objectFootprint(groupAdmin));

        groupAdmin.append("A very very very very long string to append");
        logger.info(() -> JvmUtilities.objectFootprint(groupAdmin));

        groupAdmin.undo();

        logger.info(() -> JvmUtilities.objectFootprint(groupAdmin));
        logger.info(() -> JvmUtilities.objectFootprint(concreteMember));
    }
}
