package cn.tedu.knows.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RegisterVo implements Serializable {

    //@NotBlank表示判断下面属性不能为空
    //注解汇总的message表示属性为空时的错误提示信息
    @NotBlank(message = "邀请码不能为空")
    private String inviteCode;

    @NotBlank(message = "手机号(用户名)不能为空")
    //@Pattern表示下面属性值要符合指定正则表达式
    //regexp执行正则表达式,message当属性值不符合正则时的错误信息
    @Pattern(regexp = "^1\\d{10}$",message = "手机号格式不正确")
    private String phone;

    @NotBlank(message = "昵称不能为空")
    @Pattern(regexp = "^.{2,20}$",message = "昵称是2~20个字符")
    private String nickname;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^\\w{6,20}$",message = "密码是6~20位数字字母'_'")
    private String password;

    @NotBlank(message = "密码确认不能为空")
    private String confirm;

}
