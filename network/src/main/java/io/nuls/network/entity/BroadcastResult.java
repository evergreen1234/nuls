package io.nuls.network.entity;

import java.util.List;

/**
 * @author vivi
 * @Date 2017.11.01
 */
public class BroadcastResult {

    private boolean success;

    private String message;

    private String hash;

    private List<Peer> broadcastPeers;

    private int waitReplyCount;

    private int repliedCount;

    public BroadcastResult(){

    }

    public BroadcastResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    @Override
    public String toString() {
        return "";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public List<Peer> getBroadcastPeers() {
        return broadcastPeers;
    }

    public void setBroadcastPeers(List<Peer> broadcastPeers) {
        this.broadcastPeers = broadcastPeers;
    }

    public int getWaitReplyCount() {
        return waitReplyCount;
    }

    public void setWaitReplyCount(int waitReplyCount) {
        this.waitReplyCount = waitReplyCount;
    }

    public int getRepliedCount() {
        return repliedCount;
    }

    public void setRepliedCount(int repliedCount) {
        this.repliedCount = repliedCount;
    }
}
