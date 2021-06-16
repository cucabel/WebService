package com.statistics.domain;

import java.math.BigDecimal;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

public class Statistic {

	private BigDecimal sum;
	private BigDecimal avg;
	private BigDecimal max;
	private BigDecimal min;
	private Long count;

	public Statistic() {
	}

	public Statistic(List<String> amounts) {
		calculateStatistics(amounts);
	}

	public BigDecimal getSum() {
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

	public BigDecimal getAvg() {
		return avg;
	}

	public void setAvg(BigDecimal avg) {
		this.avg = avg;
	}

	public BigDecimal getMax() {
		return max;
	}

	public void setMax(BigDecimal max) {
		this.max = max;
	}

	public BigDecimal getMin() {
		return min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	protected void calculateStatistics(List<String> amounts) {

		List<Double> i = amounts.stream().map(Double::parseDouble).collect(Collectors.toList());
		DoubleSummaryStatistics s = i.stream().mapToDouble(Double::doubleValue).summaryStatistics();

		this.sum = new BigDecimal(s.getSum()).setScale(2, BigDecimal.ROUND_HALF_UP);
		this.avg = new BigDecimal(s.getAverage()).setScale(2, BigDecimal.ROUND_HALF_UP);
		this.count = s.getCount();
		this.max = (count == 0) 
				? BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP)
				: new BigDecimal(s.getMax()).setScale(2, BigDecimal.ROUND_HALF_UP);
		this.min = (count == 0) 
				? BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP)
				: new BigDecimal(s.getMin()).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Statistic other = (Statistic) obj;
		if (avg == null) {
			if (other.avg != null)
				return false;
		} else if (!avg.equals(other.avg))
			return false;
		if (count == null) {
			if (other.count != null)
				return false;
		} else if (!count.equals(other.count))
			return false;
		if (max == null) {
			if (other.max != null)
				return false;
		} else if (!max.equals(other.max))
			return false;
		if (min == null) {
			if (other.min != null)
				return false;
		} else if (!min.equals(other.min))
			return false;
		if (sum == null) {
			if (other.sum != null)
				return false;
		} else if (!sum.equals(other.sum))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Statistic [sum=" + sum + ", avg=" + avg + ", max=" + max + ", min=" + min + ", count=" + count + "]";
	}

}
