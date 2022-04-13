package com.meituan.ptubes.reader.producer.mysqlreplicator.common.event;

import com.meituan.ptubes.reader.producer.mysqlreplicator.common.binlog.BinlogEventV4Header;
import com.meituan.ptubes.reader.producer.mysqlreplicator.utils.MySQLConstants;
import com.meituan.ptubes.reader.producer.mysqlreplicator.utils.ToStringBuilder;

/**
 * Generated for a commit of a transaction that modifies one or more tables of an XA-capable storage engine.
 * Normal transactions are implemented by sending a QUERY_EVENT containing a BEGIN statement and a QUERY_EVENT
 * containing a COMMIT statement (or a ROLLBACK statement if the transaction is rolled back).
 * Strictly speaking, Xid_log_event is used if thd-&gt;transaction.xid_state.xid.get_my_xid() returns non-zero.
 */
public final class XidEvent extends AbstractBinlogEventV4 {
	public static final int EVENT_TYPE = MySQLConstants.XID_EVENT;

	private long xid;

	public XidEvent(BinlogEventV4Header header) {
		this.header = header;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("header", header).append("xid", xid).toString();
	}

	public long getXid() {
		return xid;
	}

	public void setXid(long xid) {
		this.xid = xid;
	}
}
