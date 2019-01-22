package org.bitcoincore.api.rawtransactions.entity;

public class ScriptSig {

    private String asm;
    private String hex;

    public String getAsm() {
        return asm;
    }

    public void setAsm(String asm) {
        this.asm = asm;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ScriptSig{");
        sb.append("asm='").append(asm).append('\'');
        sb.append(", hex='").append(hex).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
