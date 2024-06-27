package com.sportyshoes.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PurchaseReportDTO {
    private Long productId;
    private String productName;
    private String categoryName;
    private int quantity;
    private BigDecimal totalAmount;
    private LocalDateTime purchaseDate;

    public PurchaseReportDTO(Long productId, String productName, String categoryName, int quantity, BigDecimal totalAmount, LocalDateTime purchaseDate) {
        this.setProductId(productId);
        this.setProductName(productName);
        this.setCategoryName(categoryName);
        this.setQuantity(quantity);
        this.setTotalAmount(totalAmount);
        this.setPurchaseDate(purchaseDate);
    }

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public LocalDateTime getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDateTime purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

}