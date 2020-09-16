package entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static com.baomidou.mybatisplus.annotation.IdType.*;

/**
 * <p>
 *
 * </p>
 *
 * @author fuyi
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode()
@AllArgsConstructor
@NoArgsConstructor
public class User{

//    @TableId(type = ASSIGN_ID)
//    private Long id;
    @TableId(type = ASSIGN_UUID)
    private String id;
    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 邮箱
     */
    private String email;


}
