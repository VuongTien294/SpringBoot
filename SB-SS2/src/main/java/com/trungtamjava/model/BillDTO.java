package com.trungtamjava.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {
    public int id;
    public UserDTO buyer;
	public String status;
    public String buyDate;
    public double priceTotal;
    public Integer discountPercent;
    public String pay;
    public String couponsName;
}
