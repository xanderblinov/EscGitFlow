package com.esc.datacollector;

/**
 * Date: 08-Aug-15
 * Time: 12:18 PM
 *
 * @author xanderblinov
 */
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Deprecated
public class NumberFormat {

	public static void main(String[] args) {

		double decimal = 1247.27467;
		System.out.println("The test number: "+decimal);
		int decimalPlaces = 3;	// the scale for the decimal

		// use of BigDecimal class
		BigDecimal bd = new BigDecimal(decimal);


		DecimalFormat decFormat1 = new DecimalFormat("#.00");

		System.out.println("BigDecimal to price: "+decFormat1.format(bd.doubleValue()));


		// set the scale and round up if >= 0.5
		bd = bd.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
		double bigDecimal = bd.doubleValue();
		System.out.println("BigDecimal rounded in 3rd decimal: "+bigDecimal);

		// use of DecimalFormat
		DecimalFormat decFormat = new DecimalFormat("#.00");
		double formatDecimal = Double.parseDouble(decFormat.format(decimal));
		System.out.println("DecimalFormat rounded in 2nd decimal: "+formatDecimal);

		System.out.println("--------------------------------------");

		DecimalFormat numFormat;
		String number;

		// 2 digits before decimal point and then 2 digits (rounded)
		numFormat = new DecimalFormat("000.##");
		number = numFormat.format(-15.567);
		System.out.println("1. DecimalFormat with .: " + number);

		// string '$' in front of the number
		numFormat = new DecimalFormat("'$'00.####");
		number = numFormat.format(15.567);
		System.out.println("2. DecimalFormat with '$': " + number);

		// use of , to group numbers
		numFormat = new DecimalFormat("#,###,###");
		number = numFormat.format(1556789);
		System.out.println("3. DecimalFormat with ,: " + number);

		// use of % for percentage
		numFormat = new DecimalFormat("%");
		number = numFormat.format(0.15);
		System.out.println("4. DecimalFormat with percentage: " + number);

		// 2 digits before decimal point and 2 digits after
		numFormat = new DecimalFormat("00.00");
		number = numFormat.format(-15.567);
		System.out.println("5. DecimalFormat with 4 digits: " + number);

		// left part of decimal number
		numFormat = new DecimalFormat("##");
		number = numFormat.format(156.567);
		System.out.println("6. DecimalFormat with no decimal part: " + number);

		// 5 or less digits in the decimal part
		numFormat = new DecimalFormat(".#####");
		number = numFormat.format(1890.567);
		System.out.println("7. DecimalFormat with 5 or less digits (in decimal part): " + number);

		// string 'JCG' in front of the number
		numFormat = new DecimalFormat("'JCG'000.#");
		number = numFormat.format(15.567);
		System.out.println("8. DecimalFormat with 'JCG': " + number);
	}

}
