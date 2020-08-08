package com.google.mlkit.vision.demo.fragment;

public class PackageLinkData {
    private String originalCode;
    private String targetCode;
    private LinkStatus status;

    public enum LinkStatus {
        LINKED,
        NONE
    }

    public LinkStatus getStatus() {
        return status;
    }

    public void setStatus(LinkStatus status) {
        this.status = status;
    }

    public String getOriginalCode() {
        return originalCode;
    }

    public void setOriginalCode(String originalCode) {
        this.originalCode = originalCode;
    }

    public String getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }
}
