package com.meituan.ptubes.reader.producer.mysqlreplicator.common.column;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import com.meituan.ptubes.reader.producer.mysqlreplicator.utils.CodecUtils;
import com.meituan.ptubes.reader.producer.mysqlreplicator.utils.MySQLConstants;
import com.meituan.ptubes.reader.producer.mysqlreplicator.utils.ToStringBuilder;
import com.meituan.ptubes.reader.producer.mysqlreplicator.utils.XDeserializer;

public final class Metadata implements Serializable {

	private static final long serialVersionUID = 4634414541769527837L;

	private final byte[] type;
	private final int[] metadata;

	public Metadata(byte[] type, int[] metadata) {
		this.type = type;
		this.metadata = metadata;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("metadata", Arrays.toString(metadata)).toString();
	}

	public byte getType(int column) {
		return this.type[column];
	}

	public int getMetadata(int column) {
		return this.metadata[column];
	}

	public static final Metadata valueOf(byte[] type, byte[] data) throws IOException {
		final int[] metadata = new int[type.length];
		final XDeserializer d = new XDeserializer(data);
		for (int i = 0; i < type.length; i++) {
			final int t = CodecUtils.toUnsigned(type[i]);
			switch (t) {
			case MySQLConstants.TYPE_FLOAT:
			case MySQLConstants.TYPE_DOUBLE:
			case MySQLConstants.TYPE_TINY_BLOB:
			case MySQLConstants.TYPE_BLOB:
			case MySQLConstants.TYPE_JSON:
			case MySQLConstants.TYPE_MEDIUM_BLOB:
			case MySQLConstants.TYPE_LONG_BLOB:
			case MySQLConstants.TYPE_GEOMETRY:
				metadata[i] = d.readInt(1);
				break;
			case MySQLConstants.TYPE_BIT:
			case MySQLConstants.TYPE_VARCHAR:
			case MySQLConstants.TYPE_NEWDECIMAL:
				// Little-endian
				metadata[i] = d.readInt(2);
				break;
			case MySQLConstants.TYPE_SET:
			case MySQLConstants.TYPE_ENUM:
			case MySQLConstants.TYPE_STRING:
				// Big-endian
				metadata[i] = CodecUtils.toInt(d.readBytes(2), 0, 2);
				break;
			case MySQLConstants.TYPE_TIME2:
			case MySQLConstants.TYPE_DATETIME2:
			case MySQLConstants.TYPE_TIMESTAMP2:
				metadata[i] = d.readInt(1);
				break;
			default:
				metadata[i] = 0;
			}
		}
		return new Metadata(type, metadata);
	}
}
