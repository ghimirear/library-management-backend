package com.library.library.model;
import lombok.Data;
import java.io.Serializable;
@Data
public class BookRatingId implements Serializable {
    private Long userId;
    private Long bookId;
}
