package org.lf.admin.api.baseapi.enums;


public enum ExcelEnum {
    account_mark("标注用户管理表单");
    private String fileName;

    ExcelEnum(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
