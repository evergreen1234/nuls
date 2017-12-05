package io.nuls.consensus.entity;

import io.nuls.core.chain.entity.BaseNulsData;
import io.nuls.core.context.NulsContext;
import io.nuls.core.exception.NulsRuntimeException;
import io.nuls.core.utils.crypto.Utils;
import io.nuls.core.utils.io.NulsByteBuffer;
import io.nuls.core.utils.io.NulsOutputStreamBuffer;
import io.nuls.core.utils.log.Log;
import io.nuls.core.utils.str.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author Niels
 * @date 2017/12/4
 */
public class Agent extends BaseNulsData {
    private String address;
    private double deposit;
    private String delegateAddress;
    private double commissionRate;
    private String introduction;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public String getDelegateAddress() {
        return delegateAddress;
    }

    public void setDelegateAddress(String delegateAddress) {
        this.delegateAddress = delegateAddress;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    protected int dataSize() {
        int size = 0;
        try {
            size += address.getBytes(NulsContext.DEFAULT_ENCODING).length;
            size += Utils.double2Bytes(deposit).length;
            size += delegateAddress.getBytes(NulsContext.DEFAULT_ENCODING).length;
            size += Utils.double2Bytes(commissionRate).length;
            if (StringUtils.isNotBlank(this.introduction)) {
                size += introduction.getBytes(NulsContext.DEFAULT_ENCODING).length;
            } else {
                size += 1;
            }
        } catch (UnsupportedEncodingException e) {
            Log.error(e);
            throw new NulsRuntimeException(e);
        }
        return size;
    }

    @Override
    public void serializeToStream(NulsOutputStreamBuffer buffer) throws IOException {
        buffer.writeBytesWithLength(address);
        buffer.writeDouble(deposit);
        buffer.writeBytesWithLength(delegateAddress);
        buffer.writeDouble(commissionRate);
        buffer.writeBytesWithLength(introduction);
    }

    @Override
    protected void parseObject(NulsByteBuffer byteBuffer) {
        this.address = byteBuffer.readString();
        this.deposit = byteBuffer.readDouble();
        this.delegateAddress = byteBuffer.readString();
        this.commissionRate = byteBuffer.readDouble();
        this.introduction = byteBuffer.readString();
    }
}