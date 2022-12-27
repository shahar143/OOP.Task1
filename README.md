# OOP.Task1
This task is about solving a problem by using the Observer design pattern in Java. The problem itseif is as follows:<br >
First, we created in the previous course task, a modified version of the built-in java class *StringBuilder*.<br >
The modified version we had to implement is called *UndoableStringBuilder*. And the main difference between *StringBuilder* and *UndoableStringBuilder*
is with the latter class's option to undo changes on the string modified by the class.<br >
<br >
Our goal in this task was to create an observable class called *GroupAdmin* and observer class called *ConcreteMember*, in which every *ConcreteMember*
instance which was registered to the *GroupAdmin* instance, recieved at the same time, a notification about the change. <br >
<br >
In addition, we also run tests on the functions implemented, and we run memory efficiency tests on the objects running in the program.

## The observer design pattern
The observer design pattern is about the idea of an admin which registers users who want to get notified on changes made in the admin.<br >
In programming, it means that the class which sends the updates on changes happening in it, and registers instances who will get the notifications is
called the observable class, and the instances which register to it are called observers.<br >
<br >
Simply put, The dynamic between observable and observers is that the observers are instances of a general observer class which register to
an observable class which will send all the members who are registered to it at the same time changes happening in it.<br >
The observable can also be called the subject class.<br >
<br >
This is the main design pattern idea, but those can be modified, for example, by allowing the observers the ability to register to the subject
themselves.

## Task structure
The source files of the task are located in the path:
> src/main/java/observer <br >

The test file is located in the path: <br > 
> src/test/java

Java source file names and their purpose:<br>
- ConcreteMember.java -> The observer class of the design pattern.
- GroupAdmin.java -> The subject/observable class of the design pattern.
- JvmUtilities.java -> A class containing functions for memory efficiency testing.
- Member.java -> Interface class which defines the functions for the observer class *ConcreteMember*
- Sender.java -> Interface class which defines the functions for the subject class *GroupAdmin*
- UndoableStringBuilder.java -> The class built in the previous task which supports the *StringBuilder*, with the modification of undoing changes

Java test file and its purpose: Tests.java -> Contains all tests of the *ConcreteMember* and *GroupAdmin* classes, and memory efficiency tests.
**NOTE:** JvmUtilities.java is also a class in the tests folder, without it, memory tests wouldn't be possible in the *tests* class.<br >

## Explanation of implementation
This section contains each important source file explanation of the implementation of the class and its methods.

### GroupAdmin.java 
*GroupAdmin* is the subject class of the general task algorithm.<br >
It holds 2 fields:
- ArrayList which holds in each of its places a *Member* object.
- UndoableStringBuilder instance.

In addition it implements the *Sender* interface, which declares the functions:
- register()
- unregister()
- insert()
- append()
- delete()
- undo()

In addition, *GroupAdmin* has the notify_memebrs() function. which takes care of sending an update
to each of the registered members. The notification of the update is taken care of in the
*ConcreteMember* class function called update(). <br>
<br>
register() and unregister() take care of registeting and unregistering *Member* instances by adding and removing them from the ArrayList, respectively.
<br>
The rest of the functions, are functions which are already defined and implemented in *UndoableStringBuilder*. Which means by calling those methods from a
*GroupAdmin* instance, they call the respective *UndoableStringBuilder* function which perfoms the string change itself.
And afterwards, in each of those functions, notify_members() function is called, which performs the change update to every registered member. <br >
<br >

### ConcreteMember.java
*ConcreteMember* is the observer class of the general task algorithm.<br >
It holds 3 fields:
- A string name which represents a member's name: Only for identification of instances.
- UndoableStringBuilder instance which will represent in each instance of *ConcreteMember* a shallow copy of the *UndoableStringBuilder* of the
*Groupadmin* the member instance is registered to.
- GroupAdmin instance in order to link between the *ConcreteMember* and the *GroupAdmin* it is registered to.

In addition, it implements the *Member* interface, which declares the function update().<br >
<br >

It also implements the functions subscribe() and unsubscribe(), which are the register() and unregister() of *ConcreteMember*, respectively,
meaning that they are called from the perspective of the *ConcreteMember*. <br >

The update() function, is the notification function which is called from the notify_members() function of *GroupAdmin*.<br >
it works by shallow copying the given *UndoableStringBuiler* reference of the *GroupAdmin* the member is subscribed to, which acts as an update
of the string value modification made in *GroupAdmin* to a memeber, and prints a message of notification to the user that the member has got the update
with the new string value.<br >
It works because of the idea of shallow copying. Meaning that the *UndoableStringBuilder* intance of *ConcreteMember* is actually holding a reference
to the *UndoableStringBuilder* of the *GroupAdmin* it is subscribed to. This is true for every *ConcreteMember* instance which is subscribed to the same
*GroupAdmin* instance which results in updates occuring in the same time to all the members subscribed to a *GroupAdmin*.<br >
<br >
Therefore, this dynamic is the observer design pattern dynamic, which gives us the desired outcome: Notifying all registered members of changes happening
to a string value stored in the subject.
