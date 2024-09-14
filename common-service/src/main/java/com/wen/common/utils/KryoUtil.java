package com.wen.common.utils;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.wen.common.domain.auth.dto.LoginDTO;
import com.wen.common.domain.auth.dto.LoginUser;
import com.wen.common.domain.notification.UserNotification;
import com.wen.common.domain.notification.dto.CommentNotificationDTO;
import com.wen.common.domain.notification.dto.LikePostNotificationDTO;
import com.wen.common.domain.user.Userinfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;

public class KryoUtil {

    private static final Kryo kryo = new Kryo();

    static {
        kryo.register(ArrayList.class);
        kryo.register(Userinfo.class);
        kryo.register(LoginUser.class);
        kryo.register(LocalDateTime.class);
        kryo.register(UserNotification.class);
        kryo.register(LikePostNotificationDTO.class);
        kryo.register(CommentNotificationDTO.class);
    }

    public static byte[] serialize(Object obj) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Output output = new Output(outputStream);
        kryo.writeObject(output, obj);
        output.close();
        return outputStream.toByteArray();
    }

    public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        Input input = new Input(inputStream);
        return kryo.readObject(input, clazz);
    }

    //将对象序列化为string类型
    public static String serializeToString(Object obj) {
        byte[] serialized = serialize(obj);
        return Base64.getEncoder().encodeToString(serialized);
    }

    public static <T> T deserializeFromString(String serializedString, Class<T> clazz) {
        byte[] serialized = Base64.getDecoder().decode(serializedString);
        return deserialize(serialized, clazz);
    }
}
