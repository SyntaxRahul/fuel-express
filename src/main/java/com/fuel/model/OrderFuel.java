package com.fuel.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderFuel {

	public enum OrderStatus {
		PENDING, CONFIRMED, DISPATCHED, DELIVERED, CANCELLED
	}

	private Long id;
	private String orderNumber;
	private Long customerId;
	private Long pumpId;
	private String fuelType;
	private BigDecimal quantity;
	private BigDecimal totalAmount;
	private String vehicleNumber;
	private String deliveryAddress;
	private OrderStatus status;
	private Timestamp orderDate;
	private Timestamp scheduledTime;
	private String paymentMethod;
	private Boolean paymentStatus;
	private BigDecimal customerLatitude;
	private BigDecimal customerLongitude;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private double distance;
	private String distanceFormatted;

	// Customer details (for display)
	private Customer customer;
	private PetrolPump petrolPump;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getPumpId() {
		return pumpId;
	}

	public void setPumpId(Long pumpId) {
		this.pumpId = pumpId;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	public Timestamp getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Timestamp scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Boolean getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(Boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public BigDecimal getCustomerLatitude() {
		return customerLatitude;
	}

	public void setCustomerLatitude(BigDecimal customerLatitude) {
		this.customerLatitude = customerLatitude;
	}

	public BigDecimal getCustomerLongitude() {
		return customerLongitude;
	}

	public void setCustomerLongitude(BigDecimal customerLongitude) {
		this.customerLongitude = customerLongitude;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getDistanceFormatted() {
		return distanceFormatted;
	}

	public void setDistanceFormatted(String distanceFormatted) {
		this.distanceFormatted = distanceFormatted;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public PetrolPump getPetrolPump() {
		return petrolPump;
	}

	public void setPetrolPump(PetrolPump petrolPump) {
		this.petrolPump = petrolPump;
	}

}