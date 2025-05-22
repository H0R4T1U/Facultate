package model;

import java.util.Objects;

public interface Entity<ID>  {
    public ID getId();
    public void setId(ID id);

}