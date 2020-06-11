package com.thoughtworks.capability.gtb.vo;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author itutry
 * @create 2020-05-21_16:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventVo {

  private String id;
  private String name;
  private EventType type;
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserialize.class)
  private LocalDateTime time;
  @JsonUnwrapped
  private UserVo user;

  /*
  * 自定义序列化器 北京时间 毫秒级
  * */
  private static class LocalDateTimeSerializer extends StdSerializer<LocalDateTime>{

    public LocalDateTimeSerializer() {
      super(LocalDateTime.class);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {

        gen.writeNumber(value.toInstant(ZoneOffset.of("+8")).toEpochMilli());

    }
  }

  /*
  * 自定义反序列化器 本地时区
  *
  * */

  private static class LocalDateTimeDeserialize extends StdDeserializer<LocalDateTime>{

    public LocalDateTimeDeserialize() {
      super(LocalDateTime.class);
    }

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      JsonNode node = p.getCodec().readTree(p);

      long instantValue= node.asLong();//取得instant值

      ZoneId zone = ZoneId.systemDefault();

      Instant instant= Instant.ofEpochMilli(instantValue);

      LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);//转换

      return localDateTime;
    }
  }
}
