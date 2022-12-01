package springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
public class User {
    private Integer id;

    private String name;

    private String username;

    private Integer age;

    private Integer account = 0;

    private Integer score = 0;

    private String sex;

    private String phone;

    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createtime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updatetime;

    private boolean status;
}
