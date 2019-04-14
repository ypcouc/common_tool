package com.ypc.common.utils;

import java.io.Closeable;
import java.io.IOException;

public abstract class SerializeTranscoder {

	public abstract byte[] serialize(Object value);
    
    public abstract Object deserialize(byte[] in) throws IOException;
    
    public void close(Closeable closeable) {
      if (closeable != null) {
        try {
          closeable.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
      }
    }
}
