package uz.husan.ordermanagment.message;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ResponseMessage {
    private Boolean success;
    private String message;
    private Object data;
}
