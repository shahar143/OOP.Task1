import observer.ConcreteMember;
import observer.GroupAdmin;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);
    // stub method to check external dependencies compatibility
    @Test
    public void test(){
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
