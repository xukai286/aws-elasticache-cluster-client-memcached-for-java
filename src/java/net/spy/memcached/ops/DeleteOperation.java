// Copyright (c) 2006  Dustin Sallings <dustin@spy.net>
// arch-tag: B96AC63D-7931-49D2-8DFA-39BA859C485E

package net.spy.memcached.ops;

import java.nio.ByteBuffer;

public class DeleteOperation extends Operation {

	private static final int OVERHEAD=32;

	private String key=null;
	private int when=0;

	public DeleteOperation(String k, int w) {
		super();
		key=k;
		when=w;
	}

	@Override
	public void handleLine(String line) {
		getLogger().info("Delete of %s returned %s", key, line);
		assert line.equals("DELETED") || line.equals("NOT_FOUND");
		transitionState(State.COMPLETE);
	}

	@Override
	public void initialize() {
		ByteBuffer b=ByteBuffer.allocate(key.length() + OVERHEAD);
		setArguments(b, "delete", key, when);
		b.flip();
		setBuffer(b);
	}

}
