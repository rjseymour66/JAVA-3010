package interfaces;

import java.time.LocalDate;

public interface IPerson {

    public void setName(String p_name);
    public void setAddress(String p_address);
    public void setDateOfBirth(LocalDate p_dateOfBirth);

    public String getName();
    public String getAddress();
    public LocalDate getDateOfBirth();
}
