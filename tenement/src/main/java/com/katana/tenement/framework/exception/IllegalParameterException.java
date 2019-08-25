package com.katana.tenement.framework.exception;

/**
 * 非法参数异常，http status：400
 */
public class IllegalParameterException extends BaseException {

    public IllegalParameterException(CodeOption CodeOption) {
        super(CodeOption.getSubCode(), CodeOption.getSubMsg());
    }

    public IllegalParameterException(CodeOption CodeOption, String subMsg) {
        super(CodeOption.getSubCode(), subMsg);
    }

    public enum CodeOption {

        /**
         * 参数，格式不对、非法值、越界等
         */
        INVALID_PARAMETER("invalid-parameter", "参数无效"),

        /**
         * 文件写入失败，重试
         */
        UPLOAD_FAIL("upload-fail", "文件上传失败"),

        /**
         * 文件扩展名无效
         */
        INVALID_FILE_EXTENSION("invalid-file-extension", "文件扩展名无效"),

        /**
         * 文件大小无效
         */
        INVALID_FILE_SIZE("invalid-file-size", "文件大小异常"),

        INVALID_SIGNATURE("invalid-signature", "无效签名");

        private String subCode;
        private String subMsg;
        CodeOption(String subCode, String subMsg) {
            this.subCode = subCode;
            this.subMsg = subMsg;
        }

        public String getSubCode() {
            return subCode;
        }

        public String getSubMsg() {
            return subMsg;
        }

    }


}
