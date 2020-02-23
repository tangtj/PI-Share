package cn.tangtj.pishare.domain;

/**
 * @author tang
 * @date 2020/1/3
 */
public class R {

    private static final transient String CODE_SUCCESS = "0000";

    private static final transient String CODE_ERROR = "1000";

    private static final transient String CODE_MSG = "success";

    private String code;
    private String msg;
    private Object data;

    private R() {
    }

    private R(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回成功
     * 每个接口基本上都有自己的返回消息(MSG)
     * 不提供无参的方法
     *
     * @return ResultJsonMap
     */
    public static R ok() {
        return new R().code(CODE_SUCCESS).msg(CODE_MSG);
    }

    public static R ok(String msg) {
        return ok().msg(msg);
    }

    public static R ok(Object data) {
        return ok().data(data);
    }

    public static R error(String msg) {
        return ok().code(CODE_ERROR).msg(msg);
    }

    /**
     * 返回失败
     *
     * @param msg
     * @return R
     */
    public static R error(String code, String msg) {
        return error(msg).code(code);
    }

    /**
     * 返回失败，带有业务数据
     *
     * @param msg
     * @return R
     */
    public static R error(String code, String msg, Object data) {
        return error(code, msg).data(data);
    }

    public String getCode() {
        return code;
    }

    public R code(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public R msg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public R data(Object data) {
        this.data = data;
        return this;
    }
}