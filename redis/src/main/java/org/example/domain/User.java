package org.example.domain;


import lombok.Data;

/* @ProjectName:    demo
 * @Package:        com.example.demo.domain
 * @ClassName:      User
 * @author:     jiangtao
 * @description:
 * @date:    2022/4/20 15:41
 * @version:    1.0
 */
@Data
public class User {

    private Integer id;

    private String name;

    private String password;
}
