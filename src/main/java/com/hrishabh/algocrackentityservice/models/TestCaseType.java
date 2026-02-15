package com.hrishabh.algocrackentityservice.models;

/**
 * Represents the visibility/purpose of a testcase.
 */
public enum TestCaseType {
    /**
     * Visible to users, shown in testcase panel, used for "Run"
     */
    DEFAULT,

    /**
     * Not visible to users, used only during "Submit" for final judging
     */
    HIDDEN
}
