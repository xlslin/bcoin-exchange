//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sd.fomin.gerbera.transaction;

import sd.fomin.gerbera.types.ULong;
import sd.fomin.gerbera.types.VarInt;
import sd.fomin.gerbera.util.ByteBuffer;
import sd.fomin.gerbera.util.HexUtils;

public abstract class Output {
    protected final OutputType type;
    protected final long satoshi;

    protected Output(OutputType type, long satoshi) {
        this.validateData(type, satoshi);
        this.type = type;
        this.satoshi = satoshi;
    }

    byte[] serializeForSigHash() {
        ByteBuffer serialized = new ByteBuffer(new byte[0]);
        serialized.append(ULong.of(this.satoshi).asLitEndBytes());
        byte[] lockingScript = this.getLockingScript();
        serialized.append(VarInt.of((long)lockingScript.length).asLitEndBytes());
        serialized.append(lockingScript);
        return serialized.bytes();
    }

    void fillTransaction(Transaction transaction) {
        transaction.addHeader("   Output (" + this.type.getDesc() + ")");
        transaction.addData("      Satoshi", ULong.of(this.satoshi).toString());
        byte[] lockingScript = this.getLockingScript();
        transaction.addData("      Lock length", VarInt.of((long)lockingScript.length).toString());
        transaction.addData("      Lock", HexUtils.asString(lockingScript));
    }

    protected abstract byte[] getLockingScript();

    long getSatoshi() {
        return this.satoshi;
    }

    private void validateData(OutputType type, long satoshi) {
        if (type == null) {
            throw new IllegalArgumentException("Output type must not be null");
        } else if (satoshi <= 0L) {
//            throw new IllegalArgumentException("Amount of satoshi must be a positive value");
        }
    }
}
