package business.salesOrderMgmt.controls;

import java.math.BigDecimal;

import javax.ejb.Stateless;

@Stateless
public class DiscountCalculator {
	
	public BigDecimal getDiscount(String promoCode) {
			if (promoCode != null) {
				// For prototype purposes, set discount to $5.00
				return new BigDecimal(5.00);
			} else {
				return BigDecimal.ZERO;
			}
	}

}
