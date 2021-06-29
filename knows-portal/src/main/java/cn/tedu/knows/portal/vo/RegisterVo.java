package cn.tedu.knows.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RegisterVo implements Serializable {

    //@NotBlank表示判断下面属性不能为空
    //注解汇总的message表示属性为空时的错误提示信息
    @NotBlank(message = "邀请码不能为空")
    private String inviteCode;
    @NotBlank(message = "手机号(用户名)不能为空")
    private String phone;
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "密码确认不能为空")
    private String confirm;

}
