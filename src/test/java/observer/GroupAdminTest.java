package observer;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class GroupAdminTest {
    GroupAdmin usbObservable = new GroupAdmin();

    @BeforeEach
    void before(){
        usbObservable.append("testing");
        usbObservable.notify_members();
    }
    @Test
    void register(){
        ConcreteMember member = new ConcreteMember("Yosi", usbObservable);
        assertTrue(usbObservable.Contains(member));
    }

    @Test
    void getUpdatesAfterRegistration(){
        ConcreteMember member = new ConcreteMember("Shimon", usbObservable);
        usbObservable.append(" class");
        usbObservable.notify_members();
        assertEquals(usbObservable.getCurrentUSB(), member.getLocalUSB());
    }

    @Test
    void unregister() {
        ConcreteMember member = new ConcreteMember("Yosi", usbObservable);
        usbObservable.unregister(member);
        assertFalse(usbObservable.Contains(member));
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
}