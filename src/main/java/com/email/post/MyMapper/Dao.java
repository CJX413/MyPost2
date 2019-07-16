package com.email.post.MyMapper;



import com.email.post.Model.PostModel;
import com.email.post.Model.ReceiveModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface Dao {
    @Insert("INSERT INTO post (sender, receiver, topical, content) VALUES(#{sender}, #{receiver}, #{topical}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addemail(PostModel post);
    @Select("SELECT * FROM post WHERE sender = #{sender}")
    List<PostModel> selectall(@Param("sender") String sender);
    @Select("SELECT * FROM post WHERE id = #{id}")
    PostModel selectByid(@Param("id") Integer id);


    @Select("SELECT max(sendtime) FROM Receive")
    Date SelectRecentTime();
    @Insert("INSERT INTO Receive (sender, receiver, topical, content, sendtime) VALUES(#{sender}, #{receiver}, #{topical}, #{content}, #{sendtime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int receivemail(ReceiveModel Receive);
    @Select("SELECT * FROM Receive WHERE id = #{id}")
    ReceiveModel selectByidR(@Param("id") Integer id);
    @Select("SELECT * FROM Receive WHERE receiver = #{receiver}")
    List<ReceiveModel> selectallR(@Param("receiver") String receiver);
}
