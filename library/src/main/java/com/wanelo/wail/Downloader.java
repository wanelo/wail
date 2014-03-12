package com.wanelo.wail;

import com.wanelo.wail.io.MarkableInputStream;
import com.wanelo.wail.io.RecyclableBufferedInputStream;
import com.wanelo.wail.pool.ByteArrayPool;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {

    private ByteArrayPool byteArrayPool;

    public Downloader(ByteArrayPool byteArrayPool) {
        this.byteArrayPool = byteArrayPool;
    }

    protected HttpURLConnection openConnection(String uri) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(uri).openConnection();
        connection.setConnectTimeout(Defaults.DEFAULT_CONNECT_TIMEOUT);
        connection.setReadTimeout(Defaults.DEFAULT_READ_TIMEOUT);
        return connection;
    }

    public InputStream load(String uri) throws IOException {
        HttpURLConnection connection = openConnection(uri);
        connection.setUseCaches(true);
        int responseCode = connection.getResponseCode();
        if (responseCode >= 300) {
            connection.disconnect();
            throw new IOException(responseCode + " " + connection.getResponseMessage());
        }

        InputStream is = connection.getInputStream();
        InputStream result = new RecyclableBufferedInputStream(is, byteArrayPool);
        return result;
    }
}
