package cz.schutzpetr.stock.server.commands.interfaces;

import cz.schutzpetr.stock.core.permission.Permission;

/**
 * Created by Petr Schutz on 24.03.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public interface CommandSender {

    /**
     * Gets the value of the specified permission, if set.
     * If a permission override is not set on this object, the default value of the permission will be returned
     *
     * @param permission Permission to get
     * @return value of permission
     */
    boolean hasPermission(Permission permission);

    /**
     * Gets the value of the specified permission, if set.
     * If a permission override is not set on this object, the default value of the permission will be returned
     *
     * @param name name of the permission
     * @return value of permission
     */
    default boolean hasPermission(String name) {
        //todo: perm system
        return hasPermission(new Permission(""));
    }


    /**
     * Sends this sendera message
     *
     * @param message Message to be displayed
     */
    void sendMessage(String message);

    /**
     * Sends this sender multiple messages
     *
     * @param messages An array of messages to be displayed
     */
    void sendMessage(String[] messages);
}
