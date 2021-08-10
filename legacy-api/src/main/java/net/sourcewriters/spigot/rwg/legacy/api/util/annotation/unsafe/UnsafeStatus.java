package net.sourcewriters.spigot.rwg.legacy.api.util.annotation.unsafe;

public enum UnsafeStatus {

    /**
     * The marked method, class or package is currently work in progress and could
     * not work at all.
     * 
     * This means that a method could also return null in some cases as it could be
     * that the isn't implemented at all yet.
     */
    WORK_IN_PROGRESS,

    /**
     * The marked method, class or package could be changed in structure.
     * 
     * This means that the marked object could be moved into another package or
     * could be renamed or moved to an entirely new object.
     */
    STRUCTURE_CHANGES,

    /**
     * The marked method, class or package is subject to change.
     * 
     * This means that the marked object can be changed at any time without any
     * warning, it is still complete and this status is only a information that the
     * class, method or package can change at any time
     */
    SUBJECT_TO_CHANGE,

    /**
     * The marked method returns data that is missing some information.
     * 
     * This means that the marked method will return less data than the object would
     * normally offer through its functionallity
     */
    MISSING_DATA;

}
