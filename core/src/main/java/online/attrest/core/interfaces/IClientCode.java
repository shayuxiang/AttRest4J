package online.attrest.core.interfaces;

import online.attrest.core._enum.DriverMode;

public interface IClientCode {

    String ToCode(DriverMode mode);

    void SetHost(String Host);
}
