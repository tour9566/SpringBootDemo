package org.example.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("tb_user")
public class User {
    @TableId
    private Integer id;
    @TableField("username")
    private String name;
    @TableField("password")
    private String password;
}
