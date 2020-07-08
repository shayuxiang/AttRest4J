package online.attrest.core._enum;

public enum AttClientFrame
{
    JQuery(0x01) ,
    Vue(0x02),
    React(0x04),
    Angular2(0x08),
    Avalon(0x10);

    private final int value;

    // 构造器默认也只能是private, 从而保证构造函数只能在内部使用
    AttClientFrame(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}