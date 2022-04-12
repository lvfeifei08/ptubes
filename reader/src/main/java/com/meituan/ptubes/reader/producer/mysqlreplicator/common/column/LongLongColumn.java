package com.meituan.ptubes.reader.producer.mysqlreplicator.common.column;

public final class LongLongColumn implements Column {
	private static final long serialVersionUID = 4159913884779393654L;

	public static final long MIN_VALUE = Long.MIN_VALUE;
	public static final long MAX_VALUE = Long.MAX_VALUE;

	private final long value;

	private LongLongColumn(long value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(this.value);
	}

	@Override public Long getValue() {
		return this.value;
	}

	public static final LongLongColumn valueOf(long value) {
		return new LongLongColumn(value);
	}
}
