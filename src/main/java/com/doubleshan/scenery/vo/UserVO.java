package com.doubleshan.scenery.vo;

import com.doubleshan.scenery.model.User;

public class UserVO {
    public String id;
    public String nickname;
    public String avatar;
    public int points;
    public String phone;
    public String role;
    public String department;
    public Boolean disabled;

    public static UserVO from(User u) {
        UserVO vo = new UserVO();
        vo.id = u.getId();
        vo.nickname = u.getNickname();
        vo.avatar = u.getAvatar();
        vo.points = u.getPoints();
        vo.phone = u.getPhone();
        vo.role = u.getRole();
        vo.department = u.getDepartment();
        vo.disabled = u.isDisabled();
        return vo;
    }
}
