package io.nuls.network.entity;

import io.nuls.core.chain.entity.NulsVersion;
import io.nuls.core.utils.date.TimeService;
import io.nuls.db.entity.PeerPo;

/**
 * @author vivi
 * @date 2017/11/30.
 */
public class PeerTransfer {


    public static void transferToPeer(Peer peer, PeerPo po) {
        peer.setFailCount(null);
        peer.setIp(po.getIp());
        peer.setPort(po.getPort());
        peer.setLastTime(po.getLastTime());
        peer.setVersion(new NulsVersion(po.getVersion()));
        peer.setHash(peer.getIp() + peer.getPort());
        peer.setFailCount(po.getFailCount());
    }


    public static PeerPo transferToPeerPo(Peer peer) {
        PeerPo po = new PeerPo();
        po.setFailCount(peer.getFailCount());
        po.setIp(peer.getIp());
        po.setPort(peer.getPort());
        po.setLastTime(peer.getLastTime());
        po.setMagicNum(peer.getNetwork().packetMagic());
        po.setVersion(peer.getVersion().getVersion());
        if (po.getLastTime() == null) {
            po.setLastTime(TimeService.currentTimeMillis());
        }
        return po;
    }
}
