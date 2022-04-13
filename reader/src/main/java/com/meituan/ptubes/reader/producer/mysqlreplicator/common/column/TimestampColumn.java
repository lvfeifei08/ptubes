package com.meituan.ptubes.reader.producer.mysqlreplicator.common.column;

public final class TimestampColumn implements Column {
	private static final long serialVersionUID = 6334849626188321306L;

	private final java.sql.Timestamp value;

	private TimestampColumn(java.sql.Timestamp value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(this.value);
	}

	@Override public java.sql.Timestamp getValue() {
		return this.value;
	}

	public static final TimestampColumn valueOf(java.sql.Timestamp value) {
		return new TimestampColumn(value);
	}
}
