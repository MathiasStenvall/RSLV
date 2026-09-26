package app.entities;

import lombok.Data;

@Data
public class Meta {

    private int total;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private String nextCursor;

}
