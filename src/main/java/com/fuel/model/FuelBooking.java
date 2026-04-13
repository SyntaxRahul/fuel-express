package com.fuel.model;

public class FuelBooking {

	private Integer bookingId;
	private Integer customerId;
	private Integer pumpId;

	private String pumpName;
	private String fuelType;

	private Double quantity;
	private String vehicleNumber;
	private String deliveryTimeSlot;
	private String deliveryAddress;
	private String paymentMethod;

	private Double baseCost;
	private Double deliveryCharge;
	private Double tax;
	private Double totalAmount;

	private String status;
	private Double latitude;
	private Double longitude;

	private String riderStatus;
	private String riderId;
	private String orderDate;

	private int otp;
	private double riderEarning;

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getPumpId() {
		return pumpId;
	}

	public void setPumpId(Integer pumpId) {
		this.pumpId = pumpId;
	}

	public String getPumpName() {
		return pumpName;
	}

	public void setPumpName(String pumpName) {
		this.pumpName = pumpName;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getDeliveryTimeSlot() {
		return deliveryTimeSlot;
	}

	public void setDeliveryTimeSlot(String deliveryTimeSlot) {
		this.deliveryTimeSlot = deliveryTimeSlot;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Double getBaseCost() {
		return baseCost;
	}

	public void setBaseCost(Double baseCost) {
		this.baseCost = baseCost;
	}

	public Double getDeliveryCharge() {
		return deliveryCharge;
	}

	public void setDeliveryCharge(Double deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getRiderStatus() {
		return riderStatus;
	}

	public void setRiderStatus(String riderStatus) {
		this.riderStatus = riderStatus;
	}

	public String getRiderId() {
		return riderId;
	}

	public void setRiderId(String riderId) {
		this.riderId = riderId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public double getRiderEarning() {
		return riderEarning;
	}

	public void setRiderEarning(double riderEarning) {
		this.riderEarning = riderEarning;
	}

}
