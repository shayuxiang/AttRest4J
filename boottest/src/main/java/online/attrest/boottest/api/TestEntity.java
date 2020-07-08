package online.attrest.boottest.api;

public class TestEntity {
    private String Name;
    private String Addr;

    public void setAddr(String addr) {
        Addr = addr;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddr() {
        return Addr;
    }

    public String getName() {
        return Name;
    }
}