package com.query;
public class AccountCondition {
    public int id;
    public byte Flag_NBS;
    public String NBS;

    public byte Flag_OB22;
    public String OB22;

    public AccountCondition(int id, byte flag_NBS, String NBS, byte flag_OB22, String OB22) {
        this.id = id;
        Flag_NBS = flag_NBS;
        this.NBS = NBS;
        Flag_OB22 = flag_OB22;
        this.OB22 = OB22;
    }
}
