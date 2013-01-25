package skt.tmall.common.core;

import skt.tmall.common.core.OrderedPair;

/**
 * 현재 쓰이지 않는다.
 * 
 * @author leegt80
 * 
 */
public class OrderedPair implements Comparable<OrderedPair> {

	private int order;

	private Object value;

	/**
	 * @param order
	 * @param value
	 */
	public OrderedPair(int order, Object value) {
		this.order = order;
		this.value = value;
	}

	public int getOrder() {
		return order;
	}

	public Object getValue() {
		return value;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int compareTo(OrderedPair o) {
		if (this.order < o.getOrder()) {
			return -1;
		} else if (this.order > o.getOrder()) {
			return 1;
		} else {
			return 0;
		}
	}
}
