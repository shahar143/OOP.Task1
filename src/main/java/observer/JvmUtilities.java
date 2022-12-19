package observer;

import org.openjdk.jol.info.GraphLayout;


/**
 * Utility class for tracking the Java Virtual Machine (JVM) resources allocated
 * by the Operating System
 */
public final class JvmUtilities {
    /**
     * @return JVM's process id, total memory allocated at the beginning of the
     * program as well as the available number of cores
     */
    public static String jvmInfo(){
        return "PID= " + ProcessHandle.current().pid()
                + ", Total Memory = " + Runtime.getRuntime().totalMemory()
                + ", Available Cores = " + Runtime.getRuntime().availableProcessors();
    }

    /**
     * Shallow and deep size footprint of the object/objects
     * @param roots an array of objects of unknown size
     * @return textual representation of the footprint
     */
    public static String objectFootprint(Object... roots){
        return "Address = " +GraphLayout.parseInstance(roots).toFootprint();
    }

    /**
     * computes the total (deep) size of the given object
     * @param roots an array of objects of unknown size
     * @return textual representation of the footprint
     */
    public static String objectTotalSize(Object... roots){
        return "Total Memory = " + GraphLayout.parseInstance(roots).totalSize();
    }

    public static String memoryStats(Object o){
        return JvmUtilities.objectTotalSize(o) +
                "\n" +JvmUtilities.objectFootprint(o);
    }



}
