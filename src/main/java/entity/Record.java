package entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long bookId;

    @Column
    private Long readerId;

    @Column
    private String dateOfIssue;

    @Column
    private String returnDate;

    public Record(Long bookId, Long readerId, String dateOfIssue, String returnDate) {
        this.bookId = bookId;
        this.readerId = readerId;
        this.dateOfIssue = dateOfIssue;
        this.returnDate = returnDate;
    }
}
