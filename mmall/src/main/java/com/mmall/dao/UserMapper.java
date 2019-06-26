package com.mmall.dao;

import com.mmall.model.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(@Param("username")String username);

    int checkEmail(@Param("email")String email);

    User selectLogin(@Param("username")String username, @Param("password")String password);

    String selectQuestionByUsername(@Param("username")String username);

    int checkAnswer(@Param("username")String username,
                    @Param("question")String question,
                    @Param("answer")String answer);

    int updatePasswordByUsername(@Param("username")String username,
                                 @Param("newPassword")String newPassword,
                                 @Param("updateTime") Integer updateTime);

    int checkPassword(@Param("password")String password,
                      @Param("userId")Integer userId);

    int checkEmailByUserId(@Param("email")String email,
                           @Param("userId")Integer userId);
}