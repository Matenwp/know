package cn.tedu.knows.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RegisterVo implements Serializable {

    private String inviteCode;
    private String phone;
    private String nickname;
    private String password;
    private String confirm;

}
