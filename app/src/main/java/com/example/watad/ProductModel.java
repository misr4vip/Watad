package com.example.watad;

public class ProductModel {

    private String sellerId;
    private String productId;
    private String productName;
    private String productDisc;
    private String productPrice;
    private String condition;
    private boolean haveAnIssues;
    private String productPic;
    private boolean Confirmed;
    private String productType ;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDisc() {
        return productDisc;
    }

    public void setProductDisc(String productDisc) {
        this.productDisc = productDisc;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isHaveAnIssues() {
        return haveAnIssues;
    }

    public void setHaveAnIssues(boolean haveAnIssues) {
        this.haveAnIssues = haveAnIssues;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public boolean isConfirmed() {
        return Confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        Confirmed = confirmed;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
