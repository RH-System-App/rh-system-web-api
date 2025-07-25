package com.rhsystem.api.rhsystemapi.domain.history;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents information about a change or event in the system's history. The HistoryInfo class
 * contains details about a specific property change, its old and new values, the type of change,
 * and any related child changes in a hierarchical structure.
 */
public class HistoryInfo {

    /**
     * Represents the name of the property that has undergone a change or is associated
     * with the history record. This field is used to identify which specific property
     * in the system is being tracked by this history entry.
     */
    private String property;

    /**
     * Represents the new value of the property being tracked in the history record.
     * This field is used to capture the updated state of the property after a change
     * has occurred, complementing the old value to provide a complete record of
     * the modification.
     */
    private String value;

    /**
     * Represents the previous value of a property that has undergone a change.
     * This field is used to capture the state of the property before the modification
     * occurred. It complements the new value field to provide a complete history of
     * the change.
     */
    private String oldValue;

    /**
     * Represents the type of change or event associated with the history record.
     * This field is used to indicate whether the change was an update, delete, or create
     * operation in the system.
     */
    private HistoryType type;

    /**
     * Represents a collection of child history records associated with the current history entry.
     * Each child entry provides additional details about nested or related changes, forming a
     * hierarchical structure of history records. This field is primarily used when a property change
     * involves multiple related updates or when changes are grouped in a parent-child relationship.
     */
    private Collection<HistoryInfo> children = new ArrayList<>();


    /**
     * Retrieves the name of the property associated with the history record.
     *
     * @return the name of the property being tracked
     */
    public String getProperty() {
        return property;
    }

    /**
     * Sets the name of the property associated with the history record.
     *
     * @param property the name of the property to associate with the history record
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * Retrieves the current value of the property being tracked in the history record.
     *
     * @return the new value of the property
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the new value for the property being tracked in the history record.
     *
     * @param value the new value to associate with the property
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Retrieves the previous value of the property being tracked in the history record.
     *
     * @return the old value of the property before the change
     */
    public String getOldValue() {
        return oldValue;
    }

    /**
     * Sets the previous value of the property being tracked in the history record.
     *
     * @param oldValue the old value to set for the property
     */
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    /**
     * Retrieves the type of change or event associated with the history record.
     *
     * @return the type of change, represented as a value of the {@link HistoryType} enumeration
     */
    public HistoryType getType() {
        return type;
    }

    /**
     * Sets the type of change or event associated with the history record.
     *
     * @param type the type of change, represented as a value of the {@link HistoryType} enumeration
     */
    public void setType(HistoryType type) {
        this.type = type;
    }

    /**
     * Retrieves the child history records associated with this instance.
     *
     * @return a collection of child history records
     */
    public Collection<HistoryInfo> getChildren() {
        return children;
    }

    /**
     * Sets a collection of child history records associated with this instance.
     *
     * @param children the collection of child history records to associate
     */
    public void setChildren(Collection<HistoryInfo> children) {
        this.children = children;
    }
}
