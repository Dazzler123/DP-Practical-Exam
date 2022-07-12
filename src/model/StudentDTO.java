package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class StudentDTO {
    private String studentID;
    private String studentName;
    private String email;
    private String contact;
    private String address;
    private String nic;
}
